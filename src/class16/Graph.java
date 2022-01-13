package class16;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图：
 * 常见表示图的结构
 * 	1）邻接表法
 * 	2）邻接矩阵法
 * 	3）除此之外还有其他众多的方式
 *
 * 我们定义的图结构
 */
public class Graph {
	// 点
	public HashMap<Integer, Node> nodes;
	// 边
	public HashSet<Edge> edges;
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
