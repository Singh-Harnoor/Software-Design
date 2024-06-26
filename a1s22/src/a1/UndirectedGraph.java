package a1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UndirectedGraph<T extends Comparable<T>> extends Graph<T> {

	private Map<T, Set<T>> graph;
	// the following class variable will be used by
	// UndiretedGraphAlgorithms class
	private Map<T, Boolean> visited;
	
	public UndirectedGraph() {
		this.graph = new HashMap<>();
		this.visited = new HashMap<>();
	}
	
	public boolean isEmpty() {
		// TASK 1: Complete this method
		// Hint: An empty graph contains zero vertices
		return (this.graph.size() == 0);
	}

	public int getSize() {
		// TASK 2: compute the size (number of vertices)
		return (this.graph.size());
	}
	
	@SuppressWarnings("unchecked")
	public void addVertex(T vertex) throws VertexExistsException {
		// TASK 3: Complete this method
		// Hint: A new vertex is by default not visited.
		if (this.graph.containsKey(vertex)) throw new VertexExistsException("Vertex Already Present in the graph");
		else {
			this.graph.put(vertex, new HashSet<T>());
			this.visited.put(vertex, false);
		}
			
	}
	
	public List<T> getAdjacent(T vertex) {
		return new ArrayList<>(graph.get(vertex));
	}

	public void addEdge(T fromVertex, T toVertex) {
		// TASK 4: Complete this method
		// Hint: Recall, both vertices already exist. Also,
		//       our graphs are not oriented, hence both edges
		//       need to be added.
		this.graph.get(fromVertex).add(toVertex); //An edge is added fromVertex to toVertex, 
												  //This adds toVertex in the list of vertices adjacent to fromVertex
		this.graph.get(toVertex).add(fromVertex); //Similar to formVertex to toVertex,
												  //This just adds the vertex from the other way around.
	}
	
	@Override
	public void reset() {
		// TASK 5: This method resets values
		// of visited map to false
		for (T key: this.graph.keySet()) {
			this.visited.put(key, false);//sets the value of visited to false of each key 
										 //Previous value could be false or true
		}
	}

	@Override
	public String toString() {
		// TASK 6: Override toString() method
		String s;
		s = "Graph:\n";
		s+= "Vertex: ";
		int i = this.graph.size();
		for (T key: this.graph.keySet()) {
			i--;
			s += key + " & Adjacent Vertices: ";
			s+= this.graph.get(key).toString();
			s+= "\n";
			if (i > 0) {
				s+= "Vertex: ";
			}
			
		} 
		return s;

	}

	@Override
	public List<T> getVertices() {
		return graph.keySet().stream().collect(Collectors.toList());
	}

	@Override
	public List<T> getVisited() {
		// TASK 7-0: code this method
		ArrayList<T> visit = new ArrayList<>();
		for (T key: this.visited.keySet()){
			if(this.visited.get(key) == true) {
				visit.add(key);
			}
		}
		return visit; //returns all the visited vertices and null in case of no visited vertex.
	}

	@Override
	public void setVisited(T vertex) {
		// TASK 7-1: code this method
		// you may assume vertex T exists in the graph
		this.visited.put(vertex, true); 
		
	}

}
