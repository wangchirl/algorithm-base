package class11;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1、二叉树的按层遍历（宽度优先遍历）
 * 	1）队列实现
 * 		1.放入头结点
 * 		2.弹出一个结点，弹出结点有左结点将左结点入队，有右结点再将右结点入队
 * 		3.周而复始，直到队列为空
 */
public class Code01_LevelTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static void level(Node head) {
		if (head == null) {
			return;
		}
		// 队列
		Queue<Node> queue = new LinkedList<>();
		// 1.头入队
		queue.add(head);
		while (!queue.isEmpty()) {
			// 弹出结点就打印
			Node cur = queue.poll();
			System.out.println(cur.value);
			// 2.有左结点就将左结点入队
			if (cur.left != null) {
				queue.add(cur.left);
			}
			// 3.有右结点就就右结点入队
			if (cur.right != null) {
				queue.add(cur.right);
			}
		}
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		level(head);
		System.out.println("========");
	}

}
