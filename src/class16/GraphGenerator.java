package class16;

/**
 * 生成图
 */
public class GraphGenerator {

	// matrix 所有的边
	// N*3 的矩阵
	// [weight, from节点上面的值，to节点上面的值]
	// 
	// [ 5 , 0 , 7]
	// [ 3 , 0,  1]
	// 
	public static Graph createGraph(int[][] matrix) {
		// 创建图对象
		Graph graph = new Graph();
		// 遍历每一个结点的信息 [weight,from,to]
		for (int i = 0; i < matrix.length; i++) {
			 // 拿到每一条边， matrix[i] 
			int weight = matrix[i][0];
			int from = matrix[i][1];
			int to = matrix[i][2];
			// 看下结点是否已经有了,没有就创建结点
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new Node(from));
			}
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new Node(to));
			}
			// 得到结点
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);
			// 创建边
			Edge newEdge = new Edge(weight, fromNode, toNode);
			// from 结点的直接结点是 to
			fromNode.nexts.add(toNode);
			// from 结点的出度+1
			fromNode.out++;
			// to 结点的入度+1
			toNode.in++;
			// from 结点的边
			fromNode.edges.add(newEdge);
			// 整个图的边
			graph.edges.add(newEdge);
		}
		return graph;
	}

}
