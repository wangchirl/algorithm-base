package class03;

/**
 * 递归：方法调用自身
 * 1）子问题与大问题有同样的求解过程
 * 2）递归退出条件 base case
 * 3）问题分解，调用函数本身
 * 1、求最大值的递归实现
 *
 * 估算递归函数的复杂度：Master 公式
 * T(N) = a * T(N/b) + O(N^d)
 * a,b,d 为常数
 * logb a < d : O(N^d)
 * logb a > d : O(N^(logb a))
 * logb a == d : O(N^d * logN)
 */
public class Code08_GetMax {

	// 求arr中的最大值
	public static int getMax(int[] arr) {
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]范围上求最大值  L ... R   N
	public static int process(int[] arr, int L, int R) {
		// arr[L..R]范围上只有一个数，直接返回，base case
		if (L == R) { 
			return arr[L];
		}
		// L...R 不只一个数
		// mid = (L + R) / 2
		int mid = L + ((R - L) >> 1); // 中点
		// 找到左边最大值
		int leftMax = process(arr, L, mid);
		// 找到右边最大值
		int rightMax = process(arr, mid + 1, R);
		// 左右最大值求最大值
		return Math.max(leftMax, rightMax);
	}

}
