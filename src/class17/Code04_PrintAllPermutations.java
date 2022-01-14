package class17;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、递归的尝试
 *  1）打印一个字符串的全部排列
 *  - 每次选择一个字符，将选择的字符从字符集合中剔除
 *  - 再次从字符集合中选择一个字符，然后再剔除选择的
 *  - 周而复始，直到没有字符可选
 *
 *  2）打印一个字符串的全部排列，要求不要出现重复的排列
 *   - 可以使用 set
 *   - 截枝的方式更高效
 */
public class Code04_PrintAllPermutations {

    public static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<Character>();
        for (char cha : str) {
            rest.add(cha);
        }
        String path = "";
        f(rest, path, ans);
        return ans;
    }

    // rest：剩下的字符集合
    // path：前面选择的字符组合
    // ans ：记录答案
    public static void f(ArrayList<Character> rest, String path, List<String> ans) {
        // base case 没有可选的字符了，直接添加答案
        if (rest.isEmpty()) {
            ans.add(path);
            return;
        }
        // 当前字符集合的大小
        int N = rest.size();
        for (int i = 0; i < N; i++) {
            // 选择当前字符
            char cur = rest.get(i);
            // 从字符集合中移除当前选中的字符
            rest.remove(i);
            // 去调下面的尝试
            f(rest, path + cur, ans);
            // 恢复现场，将前面移除的字符再次加入到当前的位置
            rest.add(i, cur);
        }
    }

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    // str：字符集合
    // index：当前位置
    // ans ：记录答案
    // [a,b,c] => [a,b,c] => [a,c,b]
    // [a,b,c] => [b,a,c] => [b,c,a]
    // [a,b,c] => [c,b,a] => [c,a,b]
    public static void g1(char[] str, int index, List<String> ans) {
        // base case 当前位置已经到最后了，添加答案
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }
        for (int i = index; i < str.length; i++) {
            // 交换当前的位置
            swap(str, index, i);
            // 去后续尝试
            g1(str, index + 1, ans);
            // 恢复现场，前面交换的位置再交换回来
            swap(str, index, i);
        }
    }

    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    // 去重版，也可以使用 set，但是这种方式比较高效 - 截枝的方式
    public static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                // 截枝
                if (!visited[str[i]]) {
                    visited[str[i]] = true;
                    swap(str, index, i);
                    g2(str, index + 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }

    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "abc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }

}
