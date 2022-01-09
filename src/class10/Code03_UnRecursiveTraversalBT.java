package class10;

import java.util.Stack;

/**
 * 1、非递归方式，二叉树遍历
 * 	1）先序遍历 - 一个栈
 * 		1.定义一个栈。压入头结点
 * 		2.栈不为空，栈顶元素出栈
 * 		3.弹出栈顶元素结点，弹出就打印，有右结点先压右结点，有左结点再压入左结点
 * 		4.周而复始，直到栈空
 *	2）后续遍历 - 两个栈，在先序遍历的基础上改一下左右结点压栈的顺序，得到头右左
 *				此时弹出不打印，而是压入另外一个栈，后面在对这个栈进行打印，得到左右头，后序遍历
 *		1.定义一个栈，压入头结点
 *		2.栈不为空，栈顶元素出栈
 *		3.弹出栈顶元素，弹出的元素压入到另外一个栈中
 *		4.弹出的结点有左结点就压入左结点，有右结点再压入右结点
 *		5.周而复始，直到栈空
 *		6.在对另外一个栈进行出栈操作，弹出就打印，直到栈空
 *	3）中序遍历 - 一个栈 （一颗树，可以被子树左边界分解掉）
 *		1.当前结点 cur，以cur为头的树的整条左边界进栈
 *		2.栈中弹出结点就打印，此时cur指向弹出元素的右结点
 *		3.如果右结点有左结点回到步骤1，否则回到步骤2
 *		4.周而复始，直到栈空
 */
public class Code03_UnRecursiveTraversalBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	// 1.先序遍历
	public static void pre(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();
				System.out.print(head.value + " ");
				// 有右结点先压右结点
				if (head.right != null) {
					stack.push(head.right);
				}
				// 有左结点再压入左结点
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	// 2.中序遍历
	public static void in(Node cur) {
		System.out.print("in-order: ");
		if (cur != null) {
			Stack<Node> stack = new Stack<Node>();
			// 栈不为空或cur不为空
			while (!stack.isEmpty() || cur != null) {
				if (cur != null) {
					// 压入栈
					stack.push(cur);
					// 往左边界跑
					cur = cur.left;
				} else {
					// 弹出结点就打印
					cur = stack.pop();
					System.out.print(cur.value + " ");
					// cur 来的弹出结点的右结点
					cur = cur.right;
				}
			}
		}
		System.out.println();
	}

	// 3.后续遍历
	public static void pos1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop(); // 头 右 左
				// 弹出的结点压入另外一个栈
				s2.push(head);
				// 先压入左
				if (head.left != null) {
					s1.push(head.left);
				}
				// 先压入右
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			// 遍历另外一个栈
			// 左 右 头
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	// 4.后续遍历 - 一个栈实现
	public static void pos2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {
					stack.push(c.right);
				} else {
					System.out.print(stack.pop().value + " ");
					h = c;
				}
			}
		}
		System.out.println();
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
		pos1(head);
		System.out.println("========");
		pos2(head);
		System.out.println("========");
	}

}
