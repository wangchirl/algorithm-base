package class28;

public class Code02_AddShortestEnd {

	/**
	 * 字符串后面添加多少字符是回文
	 * 1、求包含最后一个字符的最长回文串
	 * 2、R 最先扩到最后的时候即是包含最后一个字符的时候
	 * 3、前面的字符逆序过来追加到最后
	 */
	public static String shortestEnd(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char[] str = manacherString(s);
		int[] pArr = new int[str.length];
		int C = -1;
		int R = -1;
		int maxContainsEnd = -1;
		for (int i = 0; i != str.length; i++) {
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			// R 最先扩到最后的时候，停
			if (R == str.length) {
				maxContainsEnd = pArr[i];
				break;
			}
		}
		char[] res = new char[s.length() - maxContainsEnd + 1];
		for (int i = 0; i < res.length; i++) {
			res[res.length - 1 - i] = str[i * 2 + 1];
		}
		return String.valueOf(res);
	}

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	public static void main(String[] args) {
		String str1 = "abcd123321";
		System.out.println(shortestEnd(str1));
	}

}
