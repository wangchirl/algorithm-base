package class03;

/**
 * 2、单链表删除指定值的元素
 *  可能删除头结点
 *
 * 2、双链表删除指定值的元素
 *  可能删除头结点
 *
 */
public class Code02_DeleteGivenValue {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// head = removeValue(head, 2);
	public static Node removeValue(Node head, int num) {
		// head来到第一个不需要删的位置，删除的可能是头结点
		while (head != null) {
			if (head.value != num) {
				break;
			}
			head = head.next;
		}
		// 1 ) head == null
		// 2 ) head != null
		Node pre = head;
		Node cur = head;
		while (cur != null) {
			// 是需要删除的，调整 pre.next 指向要删除元素的 next
			if (cur.value == num) {
				pre.next = cur.next;
			// 否则，pre 往下跳
			} else {
				pre = cur;
			}
			// 继续往下找
			cur = cur.next;
		}
		return head;
	}

}
