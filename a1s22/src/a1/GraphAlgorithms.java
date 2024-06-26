package a1;

import java.util.List;

interface GraphAlgorithms<T extends Comparable<T>> {

	/**
	 * Depth first search
	 */
	public List<T> DFS(Graph<T> g, T initial);
	
	/**
	 * Determine if an unoriented graph is connected
	 */
	
	public boolean isConnected(Graph<T> g);

}
