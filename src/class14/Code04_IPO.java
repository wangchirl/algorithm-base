package class14;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 1、贪心算法
 * 	输入: 正数数组costs、正数数组profits、正数K、正数M
 * 	costs[i]表示i号项目的花费
 * 	profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * 	K表示你只能串行的最多做k个项目
 * 	M表示你初始的资金
 * 	说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 * 	输出：你最后获得的最大钱数。
 *
 * 	思路：
 * 	小根堆按花费排序，根据M找出能够做的项目放入大根堆
 * 	大根堆按利润排序，选择大根堆堆顶的项目来做，周而复始，执行K次
 */
public class Code04_IPO {

	// 最多K个项目
	// W是初始资金
	// Profits[] Capital[] 一定等长
	// 返回最终最大的资金
	public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
		// 1.小根堆，花费排序
		PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
		// 2.大根堆，利润排序
		PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
		for (int i = 0; i < Profits.length; i++) {
			minCostQ.add(new Program(Profits[i], Capital[i]));
		}
		for (int i = 0; i < K; i++) {
			// 3.选择出当前能够做的项目
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			// 能做的项目为空，提前结束
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			// 4.拿到堆顶的项目，得到利润，继续下一轮
			W += maxProfitQ.poll().p;
		}
		return W;
	}

	public static class Program {
		public int p;
		public int c;

		public Program(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	public static class MinCostComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.c - o2.c;
		}

	}

	public static class MaxProfitComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o2.p - o1.p;
		}

	}

}
