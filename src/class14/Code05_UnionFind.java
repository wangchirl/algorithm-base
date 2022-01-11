package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 1、并查集
 * 	功能：
 * 	1）查询两个元素是否在一个集合 isSameSet（往上找父节点，看父节点是否一样）
 * 		- 优化1：找到父节点后，将沿途结点的父节点直接指向找到的父节点
 * 	2）合并2个结点为一个集合 union（大挂小）
 * 		- 优化2：小的集合挂在大的集合下面
 *
 * 	思路：
 * 	1.开始时，每个元素自己组成一个集合，即自己的父节点就是自己
 * 	2.记录每个集合的元素个数，起始时，每个元素自己组成一个集合，所以元素个数为 1
 * 	3.合并2个结点的过程：
 * 	  - 查看2个结点的父节点是否一样，不一样才需要合并
 * 	  - 找出2个结点所在集合的大小
 * 	  - 找出集合大的，将小的集合的父节点挂在大的父节点下
 * 	  - 修改找到的大的集合的父节点集合大小（大的集合size + 小的集合size）
 * 	  - 将小的集合大小记录删除掉
 * 	4.找一个元素父节点的过程：
 * 	  - 得到父节点，一致往上找，直到父节点是自身时停
 * 	  - 在往上找父节点的过程中，记录每一个经过的结点
 * 	  - 修改经过结点的父节点为最终找到的父节点
 */
public class Code05_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionFind<V> {
		// 对元素进行一次包装
		public HashMap<V, Node<V>> nodes;
		// 每个包装后元素的父节点
		public HashMap<Node<V>, Node<V>> parents;
		// 每个包装后元素的集合大小
		public HashMap<Node<V>, Integer> sizeMap;
		// 初始化：1.每个元素组成一个集合；自己的父节点就是自己；每个集合的大小都是 1
		public UnionFind(List<V> values) {
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		// 给你一个节点，请你往上到不能再往上，把代表返回
		public Node<V> findFather(Node<V> cur) {
			// 一个栈，记录沿途经过的结点
			Stack<Node<V>> path = new Stack<>();
			// 往上找父节点，直到父节点是自身时停
			while (cur != parents.get(cur)) {
				// 添加沿途经过的父节点
				path.push(cur);
				cur = parents.get(cur);
			}
			// 优化1：将沿途经过的结点的父节点设为最终找到的父节点
			while (!path.isEmpty()) {
				parents.put(path.pop(), cur);
			}
			return cur;
		}
		// 查找2个元素是否在一个集合
		public boolean isSameSet(V a, V b) {
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		// 合并2个元素为一个集合
		public void union(V a, V b) {
			// 找到2个元素的父节点
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));
			// 父节点不一样时才需要合并
			if (aHead != bHead) {
				// 找到2个元素所在集合的大小
				int aSetSize = sizeMap.get(aHead);
				int bSetSize = sizeMap.get(bHead);
				// 找到集合大的
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;
				// 优化2：将集合小的父节点直接指向大的
				parents.put(small, big);
				// 重新设置合并后的集合的大小
				sizeMap.put(big, aSetSize + bSetSize);
				// 合并到大集合的集合大小信息从表中删除，因为不在有用
				sizeMap.remove(small);
			}
		}

		// 一共有多少个集合
		public int sets() {
			return sizeMap.size();
		}

	}
}
