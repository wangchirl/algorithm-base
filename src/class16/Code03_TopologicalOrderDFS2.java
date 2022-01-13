package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting

/**
 * 1、拓扑排序
 * 	1）点次方式
 * 	 1.每一个结点的点次 = 其下子结点的点次之和
 * 	 2.得到每一个结点的点次，然后按点次进行排序
 * 	 3.对排序后的结点依次取出即可得到拓扑序
 */
public class Code03_TopologicalOrderDFS2 {

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
		// 点次
		public long nodes;

		public Record(DirectedGraphNode n, long nodes) {
			node = n;
			this.nodes = nodes;
		}
	}

	public static class MyComparator implements Comparator<Record> {
		// 点次排序，点次大的排前面
		@Override
		public int compare(Record o1, Record o2) {
			return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
		}
	}

	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		// 缓存表
		HashMap<DirectedGraphNode, Record> order = new HashMap<>();
		// 得到每一个点的点次
		for (DirectedGraphNode cur : graph) {
			f(cur, order);
		}
		// 得到所有点的点次记录
		ArrayList<Record> recordArr = new ArrayList<>(order.values());
		// 按点次排序 - 点次少的放前面
		recordArr.sort(new MyComparator());
		ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
		// 排序好的依次取出
		for (Record r : recordArr) {
			ans.add(r.node);
		}
		return ans;
	}

	// 当前来到cur点，请返回cur点所到之处，所有的点次！
	// 返回（cur，点次）
	// 缓存！！！！！order   
	//  key : 某一个点的点次，之前算过了！
	//  value : 点次是多少
	public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
		if (order.containsKey(cur)) {
			return order.get(cur);
		}
		// cur的点次之前没算过！
		long nodes = 0;
		// 加上当前结点下所有子结点的结点数
		for (DirectedGraphNode next : cur.neighbors) {
			nodes += f(next, order).nodes;
		}
		// 得到当前结点的结点数 = 所有子结点的个数和 + 1
		Record ans = new Record(cur, nodes + 1);
		order.put(cur, ans);
		return ans;
	}

}
