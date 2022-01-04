package class01;

/**
 * 7、局部最小值问题
 * 无需数组，正负0，任意相邻的数不相等，找一个局部最小值
 */
public class Code06_BSAwesome {

	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		// 最左边
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		// 最右边
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		// 至少3个数
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			// 中点大于左边，左侧找 R = M - 1
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			// 中点大于右侧，右边找 L = M + 1
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}

}
