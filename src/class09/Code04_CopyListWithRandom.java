package class09;

import java.util.HashMap;

// 测试链接 : https://leetcode.com/problems/copy-list-with-random-pointer/
/**
 * 1、链表拷贝
 * 	1）next指针
 * 	2）random指针
 *
 * 解题思路：
 * 	1）容器法 : 遍历链表，存入容器，建立新旧结点关系；再次遍历链表，找到新结点设置next和random结点
 * 	2）改链表法
 * 				 null
 * 		↓----↑    ↑
 * 		a -> b -> c -> null
 * 		↓---------↑
 *                     null null null
 * 		↓----------↑    ↑     ↑    ↑
 * 		a -> a' -> b -> b'-> c -> c'-> null
 * 		↓----↓---------------↑
 * 			 null
 *
 * 	      ↓----------↑   null null
 * 		↓----↓-----↑    ↑    ↑    ↑
 * 		a -> a' -> b -> b'-> c -> c'-> null
 * 		↓----↓---------------↑    ↑
 * 			 ↓--------------------↑
 *   1）添加新结点到旧结点后面
 *   2）设置新结点的 random结点
 *   3）分离新旧结点
 *   a a' 一对一对的遍历
 *   a' = a.next
 *   a.random 设置 a'.random：a'.random = a.random.next
 *   分离新旧结点
 */
public class Code04_CopyListWithRandom {

	public static class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
	}
	// 1.容器法
	public static Node copyRandomList1(Node head) {
		// key 老节点
		// value 新节点
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node cur = head;
		while (cur != null) {
			map.put(cur, new Node(cur.val));
			cur = cur.next;
		}
		cur = head;
		while (cur != null) {
			// cur 老
			// map.get(cur) 新
			// 新.next ->  cur.next克隆节点找到
			map.get(cur).next = map.get(cur.next);
			map.get(cur).random = map.get(cur.random);
			cur = cur.next;
		}
		return map.get(head);
	}

	// 2.改链表法
	public static Node copyRandomList2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		Node next = null;
		// 1 -> 2 -> 3 -> null
		// 1 -> 1' -> 2 -> 2' -> 3 -> 3'
		while (cur != null) {
			next = cur.next;
			cur.next = new Node(cur.val);
			cur.next.next = next;
			cur = next;
		}
		cur = head;
		Node copy = null;
		// 1 1' 2 2' 3 3'
		// 依次设置 1' 2' 3' random指针
		while (cur != null) {
			next = cur.next.next;
			copy = cur.next;
			copy.random = cur.random != null ? cur.random.next : null;
			cur = next;
		}
		Node res = head.next;
		cur = head;
		// 老 新 混在一起，next方向上，random正确
		// next方向上，把新老链表分离
		while (cur != null) {
			next = cur.next.next;
			copy = cur.next;
			cur.next = next;
			copy.next = next != null ? next.next : null;
			cur = next;
		}
		return res;
	}

}
