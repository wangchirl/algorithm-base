package class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * 贪心算法
 * 	1）最自然智慧的算法
 * 	2）用一种局部最功利的标准，总是做出在当前看来是最好的选择
 * 	3）难点在于证明局部最功利的标准可以得到全局最优解
 * 	4）对于贪心算法的学习主要以增加阅历和经验为主
 *
 * 	1、给定一个由字符串组成的数组strs，
 * 	   必须把所有的字符串拼接起来，
 * 	   返回所有可能的拼接结果中，字典序最小的结果
 *
 *	字符串长度不一样的情况：短的字符串后面添加最小的字符
 *
 *	失败的贪心：所有字符串按字典序排序后进行拼接（b,ba）=> （b0，ba）=> bba 其实最小的是 bab
 *	成功的贪心：按字符拼接后的字典序排序 a+b < b+a
 *
 */
public class Code05_LowestLexicography {

	public static String lowestString1(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		TreeSet<String> ans = process(strs);
		return ans.size() == 0 ? "" : ans.first();
	}

	// strs中所有字符串全排列，返回所有可能的结果
	public static TreeSet<String> process(String[] strs) {
		TreeSet<String> ans = new TreeSet<>();
		if (strs.length == 0) {
			ans.add("");
			return ans;
		}
		for (int i = 0; i < strs.length; i++) {
			String first = strs[i];
			String[] nexts = removeIndexString(strs, i);
			TreeSet<String> next = process(nexts);
			for (String cur : next) {
				ans.add(first + cur);
			}
		}
		return ans;
	}

	// {"abc", "cks", "bct"}
	// 0 1 2
	// removeIndexString(arr , 1) -> {"abc", "bct"}
	public static String[] removeIndexString(String[] arr, int index) {
		int N = arr.length;
		String[] ans = new String[N - 1];
		int ansIndex = 0;
		for (int i = 0; i < N; i++) {
			if (i != index) {
				ans[ansIndex++] = arr[i];
			}
		}
		return ans;
	}

	public static class MyComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			return (a + b).compareTo(b + a);
		}
	}

	public static String lowestString2(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		Arrays.sort(strs, new MyComparator());
		String res = "";
		for (int i = 0; i < strs.length; i++) {
			res += strs[i];
		}
		return res;
	}

	// for test
	public static String generateRandomString(int strLen) {
		char[] ans = new char[(int) (Math.random() * strLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			int value = (int) (Math.random() * 5);
			ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
		}
		return String.valueOf(ans);
	}

	// for test
	public static String[] generateRandomStringArray(int arrLen, int strLen) {
		String[] ans = new String[(int) (Math.random() * arrLen) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = generateRandomString(strLen);
		}
		return ans;
	}

	// for test
	public static String[] copyStringArray(String[] arr) {
		String[] ans = new String[arr.length];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = String.valueOf(arr[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		int arrLen = 6;
		int strLen = 5;
		int testTimes = 10000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String[] arr1 = generateRandomStringArray(arrLen, strLen);
			String[] arr2 = copyStringArray(arr1);
			if (!lowestString1(arr1).equals(lowestString2(arr2))) {
				for (String str : arr1) {
					System.out.print(str + ",");
				}
				System.out.println();
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
