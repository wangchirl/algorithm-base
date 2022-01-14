package class17;

import java.util.Stack;

/**
 * 1、递归的尝试
 * 	1）给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。 如何实现?
 *	  - 一个每次调用都返回栈底的方法
 * 	f(stack) 得到栈底元素
 * 	| 1 |     f1 [result=1,last = f2=?] => last = 3 ，压入自己的 result入栈 ，返回 last     |   |
 * 	| 2 |  => f2 [result=2,last = f3=?] => last = 3 ，压入自己的 result入栈 ，返回 last ==> | 1 |
 * 	| 3 |	  f3 [result=3,return]   													| 2 |
 * 	----                                                                                -----
 *
 * 	主函数：r(stack)
 * 	每次调用 f 函数，得到栈底元素，调用后续过程，最后压入 f 函数的返回值
 *
 *
 */
public class Code05_ReverseStackUsingRecursive {

	public static void reverse(Stack<Integer> stack) {
		// 栈空，直接返回
		if (stack.isEmpty()) {
			return;
		}
		// 得到栈底元素
		int i = f(stack);
		// 继续子过程
		reverse(stack);
		// 将栈底元素压入栈，子过程的元素先入栈，也即最后一个作为栈底的元素（原栈顶的元素）先入栈，实现逆序
		stack.push(i);
	}

	// 返回栈底元素的方法
	public static int f(Stack<Integer> stack) {
		// 弹出栈顶元素
		int result = stack.pop();
		// 栈空则表示到栈底了，那么就直接返回当前的值
		if (stack.isEmpty()) {
			return result;
		} else {
			// 栈没空，那么就调用子过程
			int last = f(stack);
			// 不是栈底的元素需要重新压入到栈中
			stack.push(result);
			// 返回子过程返回的值，也即层层调用过后，就是返回栈底元素
			return last;
		}
	}

	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
		reverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}
