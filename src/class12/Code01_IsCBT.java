package class12;

import java.util.LinkedList;

/**
 *
 * 1、判断一棵树是否完全二叉树
 * 	1）满二叉树
 * 	2）或者最后一层不满且从左到右依次变满的过程中
 *
 * 	原则：
 * 		1.当遇到一个结点，有右结点但是没有左结点，不是完全二叉树
 * 		2.当第一次遇到不满的结点时，剩下的结点必定是叶子结点，即剩下的结点的左右结点为null
 *
 *	二叉树递归套路
 *	1）假设以X节点为头，假设可以向X左树和X右树要任何信息
 * 	2）在上一步的假设下，讨论以X为头节点的树，得到答案的可能性（最重要）
 * 	3）列出所有可能性后，确定到底需要向左树和右树要什么样的信息
 * 	4）把左树信息和右树信息求全集，就是任何一棵子树都需要返回的信息S
 * 	5）递归函数都返回S，每一棵子树都这么要求
 * 	6）写代码，在代码中考虑如何把左树的信息和右树信息整合出整棵树的信息
 *
 *
 *	1.是否满二叉树
 *	2.子树是否平衡二叉树
 *	3.二叉树高度
 *
 * 	Info {
 * 	    bool isF;
 * 	    bool isCBT;
 * 	    int height;
 * 	}
 *
 *
 *
 */
public class Code01_IsCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isCBT1(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<>();
		// 是否遇到过左右两个孩子不双全的节点
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;
			if (
			// 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
			    (leaf && (l != null || r != null)) 
			    || 
			    (l == null && r != null)
			) {
				return false;
			}
			if (l != null) {
				queue.add(l);
			}
			if (r != null) {
				queue.add(r);
			}
			if (l == null || r == null) {
				leaf = true;
			}
		}
		return true;
	}

	public static boolean isCBT2(Node head) {
		if (head == null) {
			return true;
		}
		return process(head).isCBT;
	}

	// 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
	public static class Info {
		public boolean isFull;
		public boolean isCBT;
		public int height;

		public Info(boolean full, boolean cbt, int h) {
			isFull = full;
			isCBT = cbt;
			height = h;
		}
	}

	public static Info process(Node X) {
		if (X == null) {
			return new Info(true, true, 0);
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		
		
		
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		
		
		boolean isFull = leftInfo.isFull 
				&& 
				rightInfo.isFull 
				&& leftInfo.height == rightInfo.height;
		
		
		boolean isCBT = false;
		if (isFull) {
			isCBT = true;
		} else { // 以x为头整棵树，不满
			if (leftInfo.isCBT && rightInfo.isCBT) {
				
				
				if (leftInfo.isCBT 
						&& rightInfo.isFull 
						&& leftInfo.height == rightInfo.height + 1) {
					isCBT = true;
				}
				if (leftInfo.isFull 
						&& 
						rightInfo.isFull 
						&& leftInfo.height == rightInfo.height + 1) {
					isCBT = true;
				}
				if (leftInfo.isFull 
						&& rightInfo.isCBT && leftInfo.height == rightInfo.height) {
					isCBT = true;
				}
				
				
			}
		}
		return new Info(isFull, isCBT, height);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isCBT1(head) != isCBT2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
