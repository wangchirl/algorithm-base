package class05;

// 这道题直接在leetcode测评：
// https://leetcode.com/problems/count-of-range-sum/

/**
 * 1、前缀和
 * 假设 0 ~ i 整体累加和（前缀和）是 X
 * 题目要求满足 [L,U] 求必须以 i 位置结尾的子数组有多少个在 [L,U]范围上
 * 等同于求：i 之前的所有前缀和中有多少个前缀和在 [X-U,X-L] 上
 *
 * 2、归并排序的运用
 *
 */
public class Code01_CountOfRangeSum {

	public static int countRangeSum(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		// 1、求前缀和数组
		long[] sum = new long[nums.length];
		sum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}
		return process(sum, 0, sum.length - 1, lower, upper);
	}

	public static int process(long[] sum, int L, int R, int lower, int upper) {
		// 一个数的时候，其实就是原始数组 0 ~ L 范围的和，看一下是否在范围中
		if (L == R) {
			// 在指定范围中，则表示条件成立，返回一种方式，否则返回0表示没有满足的情况
			return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
		}
		int M = L + ((R - L) >> 1);
		return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
				+ merge(sum, L, M, R, lower, upper);
	}

	public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
		int ans = 0;
		int windowL = L;
		int windowR = L;
		// [windowL, windowR)
		// 右边数组的每个数，在左边数组找区间：即以 arr[i] 为结尾的情况下，满足条件的前缀和有多少
		for (int i = M + 1; i <= R; i++) {
			// 算出 i -1 前面需要满足的条件
			long min = arr[i] - upper;
			long max = arr[i] - lower;
			// 左边数组找到最大能满足的位置
			while (windowR <= M && arr[windowR] <= max) {
				windowR++;
			}
			// 左边数组找到最小能满足的位置
			while (windowL <= M && arr[windowL] < min) {
				windowL++;
			}
			// 统计出 最大位置 - 最小位置 的区间个数
			ans += windowR - windowL;
		}
		// 经典merge 过程
		long[] help = new long[R - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return ans;
	}

}
