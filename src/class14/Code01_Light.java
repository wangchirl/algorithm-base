package class14;

import java.util.HashSet;

/**
 * 1、贪心算法
 *	给定一个字符串str，只由‘X’和‘.’两种字符构成。
 * ‘X’表示墙，不能放灯，也不需要点亮
 * ‘.’表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
 * 返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 *
 * 思路：
 * 	1）i 位置是 X，直接跳到 i+1 位置继续看
 * 	2）i 位置是 .,看 i+1 位置，如果 i+1位置是X，点灯，跳到 i+2位置继续看
 * 	3）i 位置是 . 且 i+1 位置也是 .,看 i+2位置，如果 i+2位置是 X，i位置或i+1位置随便点一个灯，跳到i+3位置继续看
 * 	4）i 位置是 . 且 i+1 位置也是 . 且 i+2位置还是 .,那么在 i+1 位置点灯，跳到 i+3 位置继续看
 */
public class Code01_Light {

	public static int minLight1(String road) {
		if (road == null || road.length() == 0) {
			return 0;
		}
		return process(road.toCharArray(), 0, new HashSet<>());
	}

	// str[index....]位置，自由选择放灯还是不放灯
	// str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
	// 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
	public static int process(char[] str, int index, HashSet<Integer> lights) {
		if (index == str.length) { // 结束的时候
			for (int i = 0; i < str.length; i++) {
				if (str[i] != 'X') { // 当前位置是点的话
					if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
						return Integer.MAX_VALUE;
					}
				}
			}
			return lights.size();
		} else { // str还没结束
			// i X .
			int no = process(str, index + 1, lights);
			int yes = Integer.MAX_VALUE;
			if (str[index] == '.') {
				lights.add(index);
				yes = process(str, index + 1, lights);
				lights.remove(index);
			}
			return Math.min(no, yes);
		}
	}

	public static int minLight2(String road) {
		char[] str = road.toCharArray();
		int i = 0;
		int light = 0;
		while (i < str.length) {
			// i位置是X,跳到下一个继续看
			if (str[i] == 'X') {
				i++;
			} else {
				// i位置是 . , 都需要点一个灯
				light++;
				// 到最后了，提前结束
				if (i + 1 == str.length) {
					break;
				} else { // 有i位置  i+ 1   X  .
					// i 位置是 .,i+1位置是X，点灯，跳到 i+2位置继续看
					if (str[i + 1] == 'X') {
						i = i + 2;
					} else {
						// i位置是.且i+1位置也是.,i+2位置是否为.都需要点灯，跳到 i+3位置继续看
						i = i + 3;
					}
				}
			}
		}
		return light;
	}

	// for test
	public static String randomString(int len) {
		char[] res = new char[(int) (Math.random() * len) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = Math.random() < 0.5 ? 'X' : '.';
		}
		return String.valueOf(res);
	}

	public static void main(String[] args) {
		int len = 20;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			String test = randomString(len);
			int ans1 = minLight1(test);
			int ans2 = minLight2(test);
			if (ans1 != ans2) {
				System.out.println("oops!");
			}
		}
		System.out.println("finish!");
	}
}
