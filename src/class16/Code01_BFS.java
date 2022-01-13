package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 1、图的宽度优先遍历
 * 	1）类似二叉树的宽度遍历
 * 	2）队列
 * 	3）set
 * 	4）出队就打印
 *
 * 	处理过的不再二次处理，每次处理前加入 set
 */
public class Code01_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node start) {
		if (start == null) {
			return;
		}
		// 1.队列
		Queue<Node> queue = new LinkedList<>();
		// 2.set
		HashSet<Node> set = new HashSet<>();
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				// 不在set才处理
				if (!set.contains(next)) {
					// 加入set
					set.add(next);
					// 加入队列
					queue.add(next);
				}
			}
		}
	}

}
