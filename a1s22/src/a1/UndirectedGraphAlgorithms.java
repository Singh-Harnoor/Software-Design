package a1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class UndirectedGraphAlgorithms<T extends Comparable<T>> 
								implements GraphAlgorithms<T> {

	@Override
	public List<T> DFS(Graph<T> g, T initial) {
		// TASK 8: Implement this method.
		// Your implementation must be iterative.
		// Please use the algorithm found here:
		// https://en.wikipedia.org/wiki/Depth-first_search
		ArrayList<T> dfs = new ArrayList<T>(); //All the searched (visited) vertices.
		LinkedList<T> s1 = new LinkedList<T>();
		s1.addFirst(initial);
		while(!s1.isEmpty() ) {
			T v = s1.pop();
			g.setVisited(v); //Set it to visited; if already visited does not alter its state
			dfs.add(v);
			List<T> Adj = g.getAdjacent(v);
			for (T w : Adj) {
				if (!g.getVisited().contains(w) && !s1.contains(w)) { //Only pushes the object to the stack if it is not visited
					s1.push(w);;
				}
				
			}
		}
		return dfs;
	}


	@Override
	public boolean isConnected(Graph<T> g) {
		// TASK 9: Implement this method.
		// You must make meaningful use of the
		// visited variable of the UndirectedGraph
		// class and the DFS method above.
		// Hint: suppose we run a DFS from a random vertex
		// of the graph. What happens to the values of
		// visited map if the graph is connected?
		List<T> visited = DFS(g, g.getVertices().get(0)); //We start the DFS from the first element of the graph
		boolean connected = visited.containsAll(g.getVertices());
		return connected;
	}



}
