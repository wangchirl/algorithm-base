package class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 1、二叉树的序列化 - 空结点不要忽略，存入特殊字符
 * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 * 以下代码全部实现了。
 * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
 * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
 * 比如如下两棵树
 *         __2
 *        /
 *       1
 *       和
 *       1__
 *          \
 *           2
 * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
 *
 * 	1）先序方式序列化
 * 	2）后序方式序列化
 * 	3）按层方式序列化
 *
 * 	反序列化：按什么方式序列化的，就按什么方式反序列化
 *
 */
public class Code02_SerializeAndReconstructTree {
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 1.先序方式序列化
	public static Queue<String> preSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		pres(head, ans);
		return ans;
	}

	public static void pres(Node head, Queue<String> ans) {
		// 空结点，存入 null
		if (head == null) {
			ans.add(null);
		} else {
			// 先加入值
			ans.add(String.valueOf(head.value));
			// 再左边结点序列化
			pres(head.left, ans);
			// 再右边结点序列化
			pres(head.right, ans);
		}
	}

	// 中序方式序列化，会存在歧义
	public static Queue<String> inSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		ins(head, ans);
		return ans;
	}

	public static void ins(Node head, Queue<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			ins(head.left, ans);
			ans.add(String.valueOf(head.value));
			ins(head.right, ans);
		}
	}

	// 2.后续方式序列化
	public static Queue<String> posSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		poss(head, ans);
		return ans;
	}

	public static void poss(Node head, Queue<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			// 先左边结点序列化
			poss(head.left, ans);
			// 后右边结点序列化
			poss(head.right, ans);
			// 再加入值
			ans.add(String.valueOf(head.value));
		}
	}

	// 1.1 先序方式反序列化
	public static Node buildByPreQueue(Queue<String> prelist) {
		if (prelist == null || prelist.size() == 0) {
			return null;
		}
		return preb(prelist);
	}

	public static Node preb(Queue<String> prelist) {
		// 弹出值
		String value = prelist.poll();
		if (value == null) {
			return null;
		}
		// 建立结点
		Node head = new Node(Integer.valueOf(value));
		// 先建立左结点
		head.left = preb(prelist);
		// 后建立右结点
		head.right = preb(prelist);
		return head;
	}

	// 2.1 后序方式反序列化 - 先倒一次，再按先序方式（区别是先右结点再左结点）
	public static Node buildByPosQueue(Queue<String> poslist) {
		if (poslist == null || poslist.size() == 0) {
			return null;
		}
		// 左右中  ->  stack(中右左)
		Stack<String> stack = new Stack<>();
		while (!poslist.isEmpty()) {
			stack.push(poslist.poll());
		}
		return posb(stack);
	}

	public static Node posb(Stack<String> posstack) {
		String value = posstack.pop();
		if (value == null) {
			return null;
		}
		Node head = new Node(Integer.valueOf(value));
		// 先建立右结点
		head.right = posb(posstack);
		// 后建立左结点
		head.left = posb(posstack);
		return head;
	}

	// 3.按层方式序列化
	public static Queue<String> levelSerial(Node head) {
		Queue<String> ans = new LinkedList<>();
		if (head == null) {
			ans.add(null);
		} else {
			// 加入结点值
			ans.add(String.valueOf(head.value));
			// 辅助队列，用来协助按层遍历
			Queue<Node> queue = new LinkedList<Node>();
			queue.add(head);
			// 二叉树按层遍历
			while (!queue.isEmpty()) {
				head = queue.poll(); // head 父   子
				// 先将左结点加入队列，为空则不加入辅助队列，结果队列加入null
				if (head.left != null) {
					ans.add(String.valueOf(head.left.value));
					queue.add(head.left);
				} else {
					ans.add(null);
				}
				// 在将右结点加入队列，为空则不加入辅助队列，结果队列加入null
				if (head.right != null) {
					ans.add(String.valueOf(head.right.value));
					queue.add(head.right);
				} else {
					ans.add(null);
				}
			}
		}
		return ans;
	}

	// 3.1 按层方式反序列化
	public static Node buildByLevelQueue(Queue<String> levelList) {
		if (levelList == null || levelList.size() == 0) {
			return null;
		}
		// 建立头结点
		Node head = generateNode(levelList.poll());
		Queue<Node> queue = new LinkedList<Node>();
		if (head != null) {
			queue.add(head);
		}
		Node node = null;
		while (!queue.isEmpty()) {
			node = queue.poll();
			// 当前结点的左结点
			node.left = generateNode(levelList.poll());
			// 当前结点的右结点
			node.right = generateNode(levelList.poll());
			// 左结点不为空，加入队列，后续需要设置其子结点
			if (node.left != null) {
				queue.add(node.left);
			}
			// 右结点不为空，加入队列，后续需要设置其子结点
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return head;
	}

	public static Node generateNode(String val) {
		if (val == null) {
			return null;
		}
		return new Node(Integer.valueOf(val));
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

	// for test
	public static boolean isSameValueStructure(Node head1, Node head2) {
		if (head1 == null && head2 != null) {
			return false;
		}
		if (head1 != null && head2 == null) {
			return false;
		}
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1.value != head2.value) {
			return false;
		}
		return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
	}

	// for test
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			Queue<String> pre = preSerial(head);
			Queue<String> pos = posSerial(head);
			Queue<String> level = levelSerial(head);
			Node preBuild = buildByPreQueue(pre);
			Node posBuild = buildByPosQueue(pos);
			Node levelBuild = buildByLevelQueue(level);
			if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish!");
	}


}
