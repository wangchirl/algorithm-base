package class05;

/**
 * 1、快排
 * 	1）荷兰国旗问题
 * 	1.小于X的放左边，大于X的放右边（左右）
 * 	(<= 区)[2,5,1,2,3] 目标 4
 * 	>> 当前数 <= 目标，当前数和(<= 区)下一个数交换，(<= 区向右扩)，当前数跳下一个
 * 	   当前数 > 目标，当前数跳下一个
 * 	2.小于X的放左边，等于X的放中间，大于X的放右边（左中右）
 * 	(< 区)[2,5,1,3,4](> 区) 目标 4
 * 	>> 当前数 < 目标，当前数和(< 区)下一个数交换，(< 区)向右扩，当前数跳下一个
 * 	   当前数 = 目标，当前数直接跳下一个
 * 	   当前数 > 目标，当前数和(> 区)前一个数交换，(> 区)向左扩，当前数停在原地（因为交换来的数还没看过呢）
 *
 * 	2）快速排序（没有目标数，拿数组最后一个数作为目标）arr[R]
 *
 */
public class Code02_PartitionAndQuickSort {

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// arr[L..R]上，以arr[R]位置的数做划分值
	// <= X > X
	// <= X X
	public static int partition(int[] arr, int L, int R) {
		if (L > R) {
			return -1;
		}
		if (L == R) {
			return L;
		}
		int lessEqual = L - 1;
		int index = L;
		while (index < R) {
			if (arr[index] <= arr[R]) {
				swap(arr, index, ++lessEqual);
			}
			index++;
		}
		swap(arr, ++lessEqual, R);
		return lessEqual;
	}

	// arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
	// 小于arr[R] | 等于arr[R] | 大于arr[R]
	public static int[] netherlandsFlag(int[] arr, int L, int R) {
		if (L > R) { // L...R L>R
			return new int[] { -1, -1 };
		}
		if (L == R) {
			return new int[] { L, R };
		}
		int less = L - 1; // < 区 右边界
		int more = R; // > 区 左边界
		int index = L;
		while (index < more) { // 当前位置，不能和 >区的左边界撞上
			if (arr[index] == arr[R]) {
				// 当前数相等目标，当前位置直接跳下一个
				index++;
			} else if (arr[index] < arr[R]) {
				// 当前数小于目标，与小于区域右边的一个数交换，当前位置跳下一个，小于区域右扩
				swap(arr, index++, ++less);
			} else { // >
				// 当前数大于目标，当前数与大于区域的前一个数交换，当前位置停在原地
				swap(arr, index, --more);
			}
		}
		// R 位置的数进行交换，放到等于区域
		swap(arr, more, R); // <[R]   =[R]   >[R]
		// 返回等于区域的区间范围边界
		return new int[] { less + 1, more };
	}

	public static void quickSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process1(arr, 0, arr.length - 1);
	}
	// 快排1.0版本O(N^2)，2个区域（小于等于arr[R],大于arr[R]）,一次搞定一个数
	public static void process1(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
		int M = partition(arr, L, R);
		// 左边递归
		process1(arr, L, M - 1);
		// 右边递归
		process1(arr, M + 1, R);
	}

	
	public static void quickSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process2(arr, 0, arr.length - 1);
	}

	// 快排2.0版本O(N^2)，3个区域（小于arr[R],大于arr[R],大于arr[R]）,一次搞定一批与目标相等的数
	public static void process2(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// [ equalArea[0]  ,  equalArea[0]]
		int[] equalArea = netherlandsFlag(arr, L, R);
		// 左边递归
		process2(arr, L, equalArea[0] - 1);
		// 右边递归
		process2(arr, equalArea[1] + 1, R);
	}

	
	public static void quickSort3(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process3(arr, 0, arr.length - 1);
	}

	// 快排3.0 随机快排O(N*logN), 不再使用最后一个数作为目标，随机在L..R选择一个交换到最后来作为目标
	public static void process3(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// 随机选择一个数作为目标，交换到最后一个位置
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
		int[] equalArea = netherlandsFlag(arr, L, R);
		// 左边递归
		process3(arr, L, equalArea[0] - 1);
		// 右边递归
		process3(arr, equalArea[1] + 1, R);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
			quickSort1(arr1);
			quickSort2(arr2);
			quickSort3(arr3);
			if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");

	}

}
