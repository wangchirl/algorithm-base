package class08;

import java.util.Arrays;

/**
 * 1、基数排序
 * 	1）准备 10 个桶
 * 	2）找到数据高位最大的，位数不够的使用零来补齐
 * 	3）首先按个位从左到右依次按照位上的数字进入对应的桶中
 * 	4）倒出桶里的数据，然后按照十位从左到右依次进入对应的桶中
 * 	5）再次倒出桶里的数据，然后按照百位从左到右依次进入对应的桶中... 周而复始直到最高位
 * 	6）倒出最后一次桶里的数据，全部有序
 *
 * 	优化版本：
 * 	1）每个位上从左到右统计每种数出现的次数
 * 	2）将前面统计的结果转换为前缀和
 * 	3）再从右往左进行遍历，看每个位上的数在前缀和中是几（比如：X），那么就就此时这个数放在X-1位上，并在前缀和中-1
 * 	4）依次进行，直到遍历完全部
 *
 * 	排序算法的稳定性：
 * 	稳定性是指同样大小的样本再排序之后不会改变相对次序
 * 	对基础类型来说，稳定性毫无意义
 * 	对非基础类型来说，稳定性有重要意义
 * 	有些排序算法可以实现成稳定的，而有些排序算法无论如何都实现不成稳定的
 *
 *			时间复杂度	额外空间复杂度		稳定性
 * 选择排序		O(N^2)			O(1)		无
 * 冒泡排序		O(N^2)			O(1)		有
 * 插入排序		O(N^2)			O(1)		有
 * 归并排序		O(N*logN)			O(N)		有
 * 随机快排		O(N*logN)			O(logN)		无
 * 堆排序		O(N*logN)			O(1)		无
 * ========================================================
 * 计数排序		O(N)			O(M)		有
 * 基数排序		O(N)			O(N)		有
 *
 * 1）不基于比较的排序，对样本数据有严格要求，不易改写
 * 2）基于比较的排序，只要规定好两个样本怎么比大小就可以直接复用
 * 3）基于比较的排序，时间复杂度的极限是O(N*logN)
 * 4）时间复杂度O(N*logN)、额外空间复杂度低于O(N)、且稳定的基于比较的排序是不存在的。
 * 5）为了绝对的速度选快排、为了省空间选堆排、为了稳定性选归并
 */
public class Code04_RadixSort {

	// only for no-negative value
	public static void radixSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		radixSort(arr, 0, arr.length - 1, maxbits(arr));
	}
	// 找到数组中的最大的数，并计算其有多少位
	public static int maxbits(int[] arr) {
		int max = Integer.MIN_VALUE;
		// 1.找数组中最大的数
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int res = 0;
		// 2.计算其有多少位
		while (max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}

	// arr[L..R]排序  ,  最大值的十进制位数digit
	public static void radixSort(int[] arr, int L, int R, int digit) {
		final int radix = 10;
		int i = 0, j = 0;
		// 有多少个数准备多少个辅助空间
		int[] help = new int[R - L + 1];
		for (int d = 1; d <= digit; d++) { // 有多少位就进出几次
			// 10个空间
		    // count[0] 当前位(d位)是0的数字有多少个
			// count[1] 当前位(d位)是(0和1)的数字有多少个
			// count[2] 当前位(d位)是(0、1和2)的数字有多少个
			// count[i] 当前位(d位)是(0~i)的数字有多少个
			int[] count = new int[radix]; // count[0..9]
			// 1.统计数组所有元素在当前位 d 上的数
			for (i = L; i <= R; i++) {
				// 103  1   3
				// 209  1   9
				j = getDigit(arr[i], d);
				count[j]++;
			}
			// 2.将统计的结果转换为前缀和
			for (i = 1; i < radix; i++) {
				count[i] = count[i] + count[i - 1];
			}
			// 3.从右往左遍历数组
			for (i = R; i >= L; i--) {
				j = getDigit(arr[i], d);
				// 将次数的数放在前缀和数组的下标元素-1的位置，前缀和-1
				help[count[j] - 1] = arr[i];
				count[j]--;
			}
			// 4.将此轮得到的结果赋值回原数组，进行下一位的操作
			for (i = L, j = 0; i <= R; i++, j++) {
				arr[i] = help[j];
			}
		}
	}
	// 计算出x当前位d上的数是几
	public static int getDigit(int x, int d) {
		return ((x / ((int) Math.pow(10, d - 1))) % 10);
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100000;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			radixSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		radixSort(arr);
		printArray(arr);

	}

}
