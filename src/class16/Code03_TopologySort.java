package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 1、拓扑排序 - 不唯一  - 有向无环图
 * 	工程应用：包的依赖问题
 *
 *	1）入度为 0 的方式
 *
 * 	思路：
 * 	1）在图中找到所有入度为0的点输出
 * 	2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
 * 	3）图的所有点都被删除后，依次输出的顺序就是拓扑排序
 *
 * 	要求：有向图且其中没有环
 * 	应用：事件安排、编译顺序
 *
 */
public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		// key 某个节点   value 剩余的入度
		HashMap<Node, Integer> inMap = new HashMap<>();
		// 只有剩余入度为0的点，才进入这个队列
		Queue<Node> zeroInQueue = new LinkedList<>();
		// 图里每个元素取出来自己的入度
		for (Node node : graph.nodes.values()) {
			inMap.put(node, node.in);
			// 入度为 0 的
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		List<Node> result = new ArrayList<>();
		while (!zeroInQueue.isEmpty()) {
			Node cur = zeroInQueue.poll();
			result.add(cur);
			// 邻居
			for (Node next : cur.nexts) {
				// 入度 - 1
				inMap.put(next, inMap.get(next) - 1);
				// 入度为 0 了
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
