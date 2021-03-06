package class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 1、递归的尝试
 * 	1）打印字符串的所有子序列问题
 * 	  - 要 index 位置的字符
 * 	  - 不要 index 位置的字符
 *
 * 	2）打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
 * 	  - 使用 set 集合
 */
public class Code03_PrintAllSubsquences {

	// s -> "abc" ->
	public static List<String> subs(String s) {
		char[] str = s.toCharArray();
		String path = "";
		List<String> ans = new ArrayList<>();
		process1(str, 0, ans, path);
		return ans;
	}

	// str 固定参数
	// 来到了str[index]字符，index是位置
	// str[0..index-1]已经走过了！之前的决定，都在path上
	// 之前的决定已经不能改变了，就是path
	// str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
	// 把所有生成的子序列，放入到ans里去
	public static void process1(char[] str, int index, List<String> ans, String path) {
		// base case，没有字符了，表示已经到最后了，直接添加答案
		if (index == str.length) {
			ans.add(path);
			return;
		}
		// 1.要了index位置的字符，path加上当前的字符，去下一个位置
		process1(str, index + 1, ans, path + String.valueOf(str[index]));
		// 2.没有要index位置的字符，path不变，去下一个位置
		process1(str, index + 1, ans, path);
	}

	// 去重的子序列，使用 set 集合接收，自动去重
	public static List<String> subsNoRepeat(String s) {
		char[] str = s.toCharArray();
		String path = "";
		HashSet<String> set = new HashSet<>();
		process2(str, 0, set, path);
		List<String> ans = new ArrayList<>();
		for (String cur : set) {
			ans.add(cur);
		}
		return ans;
	}

	public static void process2(char[] str, int index, HashSet<String> set, String path) {
		if (index == str.length) {
			set.add(path);
			return;
		}
		String no = path;
		process2(str, index + 1, set, no);
		String yes = path + String.valueOf(str[index]);
		process2(str, index + 1, set, yes);
	}

	public static void main(String[] args) {
		String test = "acccc";
		List<String> ans1 = subs(test);
		List<String> ans2 = subsNoRepeat(test);

		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=================");
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=================");

	}

}
