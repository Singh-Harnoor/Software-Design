package a1;

import java.util.List;
import java.util.Set;


public abstract class Graph<T extends Comparable<T>> {
	
	  /**
	   * 
	   * @return true if this graph is empty; otherwise return false
	   */
	  public abstract boolean isEmpty();
	  
	  /**
	   * 
	   * @return the number of vertices of the graph
	   */
	  public abstract int getSize();
	  
	  /**
	   * adds a new vertex to the graph
	   * 
	   * @param vertex, object of type T, if not already in this graph,
	   *                is added to the graph
	   * @throws VertexExistsException thrown if vertex already
	   *                               exists in the graph
	   */
	  public abstract void addVertex(T vertex) throws VertexExistsException;

	  /**
	   * adds an edge starting at fromVertex ending at toVertex
	   * @param fromVertex, object of type T, already in this graph
	   * @param toVertex, object of type T, already in this graph
	   */
	  public abstract void addEdge(T fromVertex, T toVertex);
	  
	  /**
	   * 
	   * @param vertex, existing vertex on the graph
	   * @return a collection of vertices, the other end of
	   *         edges starting at vertex, in no particular order
	   */
	  public abstract List<T> getAdjacent(T vertex);
	  
	  /**
	   * 
	   * @return a collection of all vertices of the graph in 
	   * no particular order
	   */
	  public abstract List<T> getVertices();
	  
	  /**
	   * 
	   * @return a collection of all visited vertices of the graph in 
	   * no particular order
	   */
	  public abstract List<T> getVisited();
	  
	  /**
	   * 
	   * @param vertex - set vertex as visited
	   */
	  public abstract void setVisited(T vertex);
	  
	  /**
	   * resets values
	   * of visited map to false
	   */
	  
	  public abstract void reset();
	  
}
