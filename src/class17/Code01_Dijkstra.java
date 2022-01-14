package class17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

// no negative weight

/**
 * 1、单元最小路径问题 - 加强堆
 * 	1）由于普通的实现方式，在查找最小记录时，每查找一次就需要遍历一遍
 * 	2）使用加强堆结构，小堆顶，在每次修改结点距离时，让其帮忙自动调整堆
 *
 */
public class Code01_Dijkstra {

	public static HashMap<Node, Integer> dijkstra1(Node from) {
		HashMap<Node, Integer> distanceMap = new HashMap<>();
		distanceMap.put(from, 0);
		// 打过对号的点
		HashSet<Node> selectedNodes = new HashSet<>();
		Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		while (minNode != null) {
			// 原始点 -> minNode(跳转点) 最小距离distance
			int distance = distanceMap.get(minNode);
			for (Edge edge : minNode.edges) {
				Node toNode = edge.to;
				if (!distanceMap.containsKey(toNode)) {
					distanceMap.put(toNode, distance + edge.weight);
				} else { // toNode
					distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
				}
			}
			selectedNodes.add(minNode);
			minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		}
		return distanceMap;
	}

	public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
		Node minNode = null;
		int minDistance = Integer.MAX_VALUE;
		for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
			Node node = entry.getKey();
			int distance = entry.getValue();
			if (!touchedNodes.contains(node) && distance < minDistance) {
				minNode = node;
				minDistance = distance;
			}
		}
		return minNode;
	}

	// 结点距离封装
	public static class NodeRecord {
		// 当前结点
		public Node node;
		// 结点的距离
		public int distance;

		public NodeRecord(Node node, int distance) {
			this.node = node;
			this.distance = distance;
		}
	}

	// 加强堆
	public static class NodeHeap {
		// 堆！
		private Node[] nodes;
		// node -> 堆上的什么位置？,反向索引表
		private HashMap<Node, Integer> heapIndexMap;
		// 距离表
		private HashMap<Node, Integer> distanceMap;
		// 堆大小
		private int size;

		public NodeHeap(int size) {
			nodes = new Node[size];
			heapIndexMap = new HashMap<>();
			distanceMap = new HashMap<>();
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		// 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
		// 判断要不要更新，如果需要的话，就更新
		public void addOrUpdateOrIgnore(Node node, int distance) {
			// 1.如果结点在堆中，则更新距离
			if (inHeap(node)) { // update
				// 更新距离表，取得当前距离的距离与新距离的最小值
				distanceMap.put(node, Math.min(distanceMap.get(node), distance));
				// 更新了距离，那么需要重新调整堆，使得最小距离的结点在堆顶
				insertHeapify(node, heapIndexMap.get(node));
			}
			// 2.没有在堆里，那么就添加
			if (!isEntered(node)) { // add
				nodes[size] = node;
				heapIndexMap.put(node, size);
				distanceMap.put(node, distance);
				// 加入了新元素，重新调整堆，并将堆大小+1
				insertHeapify(node, size++);
			}
			// 不在堆里了，表示已经处理过了，无需做任何事情
			// ignore
		}

		public NodeRecord pop() {
			// 得到堆顶的元素
			NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
			// 将堆顶的元素与最后一个元素进行交换
			swap(0, size - 1); // 0 > size - 1    size - 1 > 0
			// 反向索引表中将其值置位 -1
			heapIndexMap.put(nodes[size - 1], -1);
			// 从距离表中删除
			distanceMap.remove(nodes[size - 1]);
			// free C++同学还要把原本堆顶节点析构，对java同学不必
			// 删除堆的最后一个元素
			nodes[size - 1] = null;
			// 删除了堆的元素，堆大小-1，交换到堆顶的元素往下沉
			heapify(0, --size);
			return nodeRecord;
		}

		private void insertHeapify(Node node, int index) {
			while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
				swap(index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		private void heapify(int index, int size) {
			int left = index * 2 + 1;
			while (left < size) {
				int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
						? left + 1
						: left;
				smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
				if (smallest == index) {
					break;
				}
				swap(smallest, index);
				index = smallest;
				left = index * 2 + 1;
			}
		}

		// 判断结点是否进过堆
		private boolean isEntered(Node node) {
			return heapIndexMap.containsKey(node);
		}

		// 判断结点是否在堆中，弹出的结点将被置换到最后的位置，反向索引表的距离将改为-1
		private boolean inHeap(Node node) {
			return isEntered(node) && heapIndexMap.get(node) != -1;
		}

		private void swap(int index1, int index2) {
			heapIndexMap.put(nodes[index1], index2);
			heapIndexMap.put(nodes[index2], index1);
			Node tmp = nodes[index1];
			nodes[index1] = nodes[index2];
			nodes[index2] = tmp;
		}
	}

	// 改进后的dijkstra算法
	// 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
	public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
		// 加强堆
		NodeHeap nodeHeap = new NodeHeap(size);
		// 第一个元素，自己到自己的距离为0
		nodeHeap.addOrUpdateOrIgnore(head, 0);
		HashMap<Node, Integer> result = new HashMap<>();
		// 堆不为空就继续
		while (!nodeHeap.isEmpty()) {
			// 弹出堆顶最小的元素
			NodeRecord record = nodeHeap.pop();
			// 当前结点
			Node cur = record.node;
			// 到当前结点的距离
			int distance = record.distance;
			// 得到当前结点的边
			for (Edge edge : cur.edges) {
				// 将边的另一头结点加入堆中，距离为前一个结点的距离+边的长度
				nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
			}
			// 收集结果
			result.put(cur, distance);
		}
		return result;
	}

}
