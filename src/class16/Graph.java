package class16;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图结构
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
