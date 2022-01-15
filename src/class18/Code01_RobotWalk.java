package class18;

/**
 * 1、暴力递归到动态规划
 * 	1）机器人问题
 * 	假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 	开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * 	如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 	如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 	如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 	规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 	给定四个参数 N、M、K、P，返回方法数。
 *
 *	1.暴力递归
 *	  1）走到最左侧了，只能往右走
 *	  2）走到最右侧了，只能往左走
 *	  3）普遍位置，既可以往右走，又可以往左走，二者的方式之和
 *
 *	2.缓存法
 *	  1）分析流程中是否存在重复的求解过程
 *	  2）使用缓存表，表中存在了就直接返回
 *	  3）表中不存在就去计算，计算完后，记得将结果放入到缓存表中
 *
 *	3.动态规划
 *	  1）根据暴力递归改动态规划
 *	  2）找准 base case，先将 base case的值设置好
 *	  3）根据暴力递归实现找每个位置的依赖关系
 *
 */
public class Code01_RobotWalk {

	public static int ways1(int N, int start, int aim, int K) {
		if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
			return -1;
		}
		return process1(start, K, aim, N);
	}

	// 机器人当前来到的位置是cur，
	// 机器人还有rest步需要去走，
	// 最终的目标是aim，
	// 有哪些位置？1~N
	// 返回：机器人从cur出发，走过rest步之后，最终停在aim的方法数，是多少？
	public static int process1(int cur, int rest, int aim, int N) {
		// base case ，剩余步数已没了，如果当前位置是目标位置，那么表示找到一种方式返回1，否则没有返回0
		if (rest == 0) { // 如果已经不需要走了，走完了！
			return cur == aim ? 1 : 0;
		}
		// (cur, rest)
		// 到了最左侧了，只能往右走，步数-1
		if (cur == 1) { // 1 -> 2
			return process1(2, rest - 1, aim, N);
		}
		// (cur, rest)
		// 到了最右侧了，只能往左走，步数-1
		if (cur == N) { // N-1 <- N
			return process1(N - 1, rest - 1, aim, N);
		}
		// (cur, rest)
		// 普遍位置，既可以往左走，又可以往右走，二者之和即是可能性
		return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
	}

	public static int ways2(int N, int start, int aim, int K) {
		if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
			return -1;
		}
		// 缓存法，二维表是由暴力递归的可变参数决定的，-1 表示还没有计算过
		int[][] dp = new int[N + 1][K + 1];
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= K; j++) {
				dp[i][j] = -1;
			}
		}
		// dp就是缓存表
		// dp[cur][rest] == -1 -> process1(cur, rest)之前没算过！
		// dp[cur][rest] != -1 -> process1(cur, rest)之前算过！返回值，dp[cur][rest]
		// N+1 * K+1
		return process2(start, K, aim, N, dp);
	}

	// cur 范: 1 ~ N
	// rest 范：0 ~ K
	public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
		// 如果已经计算过了，直接返回缓存中的值
		if (dp[cur][rest] != -1) {
			return dp[cur][rest];
		}
		// 之前没算过！
		int ans = 0;
		if (rest == 0) {
			ans = cur == aim ? 1 : 0;
		} else if (cur == 1) {
			ans = process2(2, rest - 1, aim, N, dp);
		} else if (cur == N) {
			ans = process2(N - 1, rest - 1, aim, N, dp);
		} else {
			ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
		}
		// 返回之前，将计算的结果存入缓存中
		dp[cur][rest] = ans;
		return ans;
	}

	// 动态规划
	// 1.先确定 base case 位置的值
	// 2.根据暴力递归的实现来确定其余位置值的依赖关系
	// 3.第一行的每一个位置的值，依赖下一行的对应前一个位置的值
	// 4.最后一行的每一个位置的值，依赖其上一行的对应前一个位置的值
	// 5.普遍行的每一个位置的值，依赖其上一行的对应前一个位置的值 + 其下一行的对应前一个位置的值
	public static int ways3(int N, int start, int aim, int K) {
		if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
			return -1;
		}
		int[][] dp = new int[N + 1][K + 1];
		dp[aim][0] = 1;
		for (int rest = 1; rest <= K; rest++) {
			// 单独设置第一行
			dp[1][rest] = dp[2][rest - 1];
			// 普遍位置
			for (int cur = 2; cur < N; cur++) {
				dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
			}
			// 单独设置最后一行
			dp[N][rest] = dp[N - 1][rest - 1];
		}
		return dp[start][K];
	}

	public static void main(String[] args) {
		System.out.println(ways1(5, 2, 4, 6));
		System.out.println(ways2(5, 2, 4, 6));
		System.out.println(ways3(5, 2, 4, 6));
	}

}
