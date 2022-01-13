package class16;

/**
 * 边结构
 */
public class Edge {
	// 权重
	public int weight;
	// 从哪个点出发
	public Node from;
	// 到哪个点
	public Node to;

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

}
