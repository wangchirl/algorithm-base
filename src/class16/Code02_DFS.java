package class16;

import java.util.HashSet;
import java.util.Stack;

/**
 * 1、图的深度优先遍历
 * 	1）二叉树深度优先遍历
 * 	2）栈
 * 	3）set
 * 	4）入栈就打印
 * 	5）弹出结点重复压栈，因为可能还有其他子结点需要处理
 * 	6）压入当前结点的一个子结点后停
 *
 * 	一条路走到死
 *
 */
public class Code02_DFS {

	public static void dfs(Node node) {
		if (node == null) {
			return;
		}
		// 栈
		Stack<Node> stack = new Stack<>();
		// set
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			// 当前结点的子结点
			for (Node next : cur.nexts) {
				// 不在set的才处理
				if (!set.contains(next)) {
					// 重复压栈
					stack.push(cur);
					// 子结点压栈
					stack.push(next);
					// 加入set
					set.add(next);
					// 压栈就打印
					System.out.println(next.value);
					// 压入一个就停
					break;
				}
			}
		}
	}
	
	
	

}
