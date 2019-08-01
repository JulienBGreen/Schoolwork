import java.util.*;


public class Graph {
	public HashMap<String, Vertex> vertices;
	public ArrayList<Edge> edges;
	
	public Graph(){
		vertices = new HashMap<String, Vertex>();
		edges = new ArrayList<Edge>();
	}
	
	
	public void insertVertex(Vertex vertex){
		vertices.put(vertex.id, vertex);
	}
	
	public void insertNewEdge(Vertex vertex1, Vertex vertex2){
		Edge tempEdge = new Edge(vertex1, vertex2);
		edges.add(tempEdge);
		vertex1.insertEdge(tempEdge);
		vertex2.insertEdge(tempEdge);
	}
	
	public void insertEdge(Edge edge){
		edges.add(edge);
		edge.vertex1.insertEdge(edge);
		edge.vertex2.insertEdge(edge);
	}
	
	
	
	
}
