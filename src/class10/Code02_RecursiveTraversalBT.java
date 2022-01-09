package class10;

/**
 * 1、二叉树
 *	1）二叉树遍历
 *		1.先序：头左右
 *		2.中序：左头右
 *		3.后续：左右头
 *	2）递归序：每一个结点都来到三次
 *		   a
 *		  ↙ ↘
 *		b     c
 *	   ↙ ↘	 ↙ ↘
 *	  d   e	f   g
 *
 *	递归序：a b d d d b e e e b a c f f f c g g g c a
 * 	  先序：第一次来到就结点就结算
 * 	  	   a b d e c f g
 * 	  中序：第二次来到结点就结算
 * 	  	   d b e a f c g
 * 	  后续：第三次来到结点就结算
 * 	  	   d e b f g c a
 *
 *  结论：X结点在先序之前的数 与 X结点在后序之后的数 取交集，一定是X结点的父节点
 */
public class Code02_RecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static void f(Node head) {
		if (head == null) {
			return;
		}
		// 1 先序
		f(head.left);
		// 2 中序
		f(head.right);
		// 3 后序
	}

	// 先序打印所有节点
	public static void pre(Node head) {
		if (head == null) {
			return;
		}
		System.out.println(head.value);
		pre(head.left);
		pre(head.right);
	}

	public static void in(Node head) {
		if (head == null) {
			return;
		}
		in(head.left);
		System.out.println(head.value);
		in(head.right);
	}

	public static void pos(Node head) {
		if (head == null) {
			return;
		}
		pos(head.left);
		pos(head.right);
		System.out.println(head.value);
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);

		pre(head);
		System.out.println("========");
		in(head);
		System.out.println("========");
		pos(head);
		System.out.println("========");

	}

}
