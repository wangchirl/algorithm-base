package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// OJ链接：https://www.lintcode.com/problem/topological-sorting

/**
 * 1、拓扑排序 - 有向无环图
 * 	1）指定的结构
 */
public class Code03_TopologicalOrderBFS {

	// 不要提交这个类
	public static class DirectedGraphNode {
		// 值
		public int label;
		// 子结点
		public ArrayList<DirectedGraphNode> neighbors;

		public DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	}

	// 提交下面的
	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		// 统计每个结点的邻居数
		HashMap<DirectedGraphNode, Integer> indegreeMap = new HashMap<>();
		// 第一次默认都是 0
		for (DirectedGraphNode cur : graph) {
			indegreeMap.put(cur, 0);
		}
		// 第二次排序，找到每个结点的邻居数
		for (DirectedGraphNode cur : graph) {
			for (DirectedGraphNode next : cur.neighbors) {
				// 每次邻居数 + 1
				indegreeMap.put(next, indegreeMap.get(next) + 1);
			}
		}
		// 邻居为 0 的入队
		Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
		for (DirectedGraphNode cur : indegreeMap.keySet()) {
			if (indegreeMap.get(cur) == 0) {
				zeroQueue.add(cur);
			}
		}
		// 出队时加入结果
		ArrayList<DirectedGraphNode> ans = new ArrayList<>();
		while (!zeroQueue.isEmpty()) {
			DirectedGraphNode cur = zeroQueue.poll();
			ans.add(cur);
			// 当前结点的邻居数 - 1
			for (DirectedGraphNode next : cur.neighbors) {
				indegreeMap.put(next, indegreeMap.get(next) - 1);
				// 将 减一后邻居为 0 的数入队
				if (indegreeMap.get(next) == 0) {
					zeroQueue.offer(next);
				}
			}
		}
		return ans;
	}

}
