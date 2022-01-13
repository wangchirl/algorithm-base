package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting

/**
 * 1、拓扑排序
 * 	1）结点走过的深度大的谁在前
 *	X 结点走过的深度 > Y 结点走过的深度，那么 X 结点在 Y 结点之前
 *
 */
public class Code03_TopologicalOrderDFS1 {

	// 不要提交这个类
	public static class DirectedGraphNode {
		public int label;
		public ArrayList<DirectedGraphNode> neighbors;

		public DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	}

	// 提交下面的
	public static class Record {
		public DirectedGraphNode node;
		// 深度
		public int deep;

		public Record(DirectedGraphNode n, int deep) {
			node = n;
			this.deep = deep;
		}
	}

	public static class MyComparator implements Comparator<Record> {
		// 深度大的排前面
		@Override
		public int compare(Record o1, Record o2) {
			return o2.deep - o1.deep;
		}
	}

	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		// 记录每个结点的深度信息
		HashMap<DirectedGraphNode, Record> order = new HashMap<>();
		for (DirectedGraphNode cur : graph) {
			f(cur, order);
		}
		// 拿出每个结点深度信息进行排序，深度大的排前面
		ArrayList<Record> recordArr = new ArrayList<>(order.values());
		recordArr.sort(new MyComparator());
		// 依次拿出排序后的值即为拓扑序
		ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
		for (Record r : recordArr) {
			ans.add(r.node);
		}
		return ans;
	}

	public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
		if (order.containsKey(cur)) {
			return order.get(cur);
		}
		int follow = 0;
		// 得到结点下所有子结点最大的深度
		for (DirectedGraphNode next : cur.neighbors) {
			follow = Math.max(follow, f(next, order).deep);
		}
		// 得到当前结点的深度 = 子结点最大深度 + 1
		Record ans = new Record(cur, follow + 1);
		order.put(cur, ans);
		return ans;
	}

}
