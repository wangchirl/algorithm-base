package class16;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

//undirected graph only
/**
 * 1、最小生成树 - 无向图
 *
 * 	1）K 算法：考察图的边，看结点是否在一个集合 - 贪心算法
 *	  1.边从小到大排序，依次进行考察每条边的from和to结点
 *	  2.边的from和to结点检查是否已经在一个集合，若已经在一个集合则舍弃 - 不能成环
 *	   - 检查是否在一个集合：并查集 isSameSet
 *
 *	思路：
 * 	1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
 * 	2）当前的边要么进入最小生成树的集合，要么丢弃
 * 	3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
 * 	4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
 * 	5）考察完所有边之后，最小生成树的集合也得到了
 */
public class Code04_Kruskal {

	// Union-Find Set
	public static class UnionFind {
		// key 某一个节点， value key节点往上的节点
		private HashMap<Node, Node> fatherMap;
		// key 某一个集合的代表节点, value key所在集合的节点个数
		private HashMap<Node, Integer> sizeMap;

		public UnionFind() {
			fatherMap = new HashMap<Node, Node>();
			sizeMap = new HashMap<Node, Integer>();
		}
		
		public void makeSets(Collection<Node> nodes) {
			fatherMap.clear();
			sizeMap.clear();
			for (Node node : nodes) {
				fatherMap.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		private Node findFather(Node n) {
			Stack<Node> path = new Stack<>();
			while(n != fatherMap.get(n)) {
				path.add(n);
				n = fatherMap.get(n);
			}
			while(!path.isEmpty()) {
				fatherMap.put(path.pop(), n);
			}
			return n;
		}

		public boolean isSameSet(Node a, Node b) {
			return findFather(a) == findFather(b);
		}

		public void union(Node a, Node b) {
			if (a == null || b == null) {
				return;
			}
			Node aDai = findFather(a);
			Node bDai = findFather(b);
			if (aDai != bDai) {
				int aSetSize = sizeMap.get(aDai);
				int bSetSize = sizeMap.get(bDai);
				if (aSetSize <= bSetSize) {
					fatherMap.put(aDai, bDai);
					sizeMap.put(bDai, aSetSize + bSetSize);
					sizeMap.remove(aDai);
				} else {
					fatherMap.put(bDai, aDai);
					sizeMap.put(aDai, aSetSize + bSetSize);
					sizeMap.remove(bDai);
				}
			}
		}
	}
	

	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	// K 算法
	public static Set<Edge> kruskalMST(Graph graph) {
		// 建立并查集
		UnionFind unionFind = new UnionFind();
		// 并查集初始化每一个结点
		unionFind.makeSets(graph.nodes.values());
		// 从小的边到大的边，依次弹出，小根堆！
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
		for (Edge edge : graph.edges) { // M 条边
			priorityQueue.add(edge);  // O(logM)
		}
		Set<Edge> result = new HashSet<>();
		while (!priorityQueue.isEmpty()) { // M 条边
			// 弹出当前边
			Edge edge = priorityQueue.poll(); // O(logM)
			// 检查边的2个结点from和to，查看它们是否在一个集合，不在则要当前边，否则丢弃
			if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1)
				result.add(edge);
				unionFind.union(edge.from, edge.to);
			}
		}
		return result;
	}
}
