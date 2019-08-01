import java.util.*;
public class Vertex {
	public String id;
	public ArrayList<Edge> edges;
	public int numEdges;
	
	public Vertex(String id){
		this.id = id;
		edges = new ArrayList<Edge>();
		numEdges = edges.size();
	}
	
	public void insertEdge(Edge edge){
		edges.add(edge);
		numEdges = edges.size();
	}
	
	
}
