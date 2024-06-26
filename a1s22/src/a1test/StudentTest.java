package a1test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import a1.Graph;
import a1.UndirectedGraph;
import a1.UndirectedGraphAlgorithms;
import a1.VertexExistsException;
//Test 1-5 tests the functionality of UnDirected graph
//Test 6-10 tests the functionality of UnDirecctedGrapAlgorithms 

public class StudentTest {

	 /**
	  * TODO: Please write at least 5 test cases for testing @DirectedGraph.
	  * TODO: Please write at least 5 test cases for testing @DirectedGraphAlgorithms.
	  */
	
	@Test 
	public void test1() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		boolean empty = graph1.isEmpty();
		assertEquals(true, empty);
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
			graph1.addEdge("A", "B");
			graph1.addEdge("A", "C");
			graph1.addEdge("C", "D");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		empty = graph1.isEmpty();
		assertEquals(false, empty);
		String msg = graph1.toString();
		assertEquals("Graph:\nVertex: A & Adjacent Vertices: [B, C]\nVertex: B & Adjacent Vertices: [A]\nVertex: C & Adjacent Vertices: [A, D]\nVertex: D & Adjacent Vertices: [C]\n", msg);	
	}
	
	@Test 
	public void test2() {
		UndirectedGraph<Integer> graph1 = new UndirectedGraph<Integer>();
		boolean empty = graph1.isEmpty();
		assertEquals(true, empty);
		try{
			graph1.addVertex(1); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex(2);
			graph1.addVertex(3);
			graph1.addVertex(4); 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		empty = graph1.isEmpty();
		assertEquals(false, empty);
		List<Integer> visited = graph1.getAdjacent(1);
		assertTrue(visited.isEmpty());
		graph1.addEdge(1, 2);
		graph1.addEdge(1, 3);
		graph1.addEdge(1, 4);
		
		List<Integer> adjOf1 = graph1.getAdjacent(1);
		List<Integer> adjOf1Exp = new ArrayList<Integer>();
		adjOf1Exp.add(2);
		adjOf1Exp.add(3);
		adjOf1Exp.add(4);
		assertTrue(adjOf1.containsAll(adjOf1Exp));
		
		List<Integer> adjOf2 = graph1.getAdjacent(2);
		List<Integer> adjOf2Exp = new ArrayList<Integer>();
		assertTrue(adjOf2.containsAll(adjOf2Exp));
		
		graph1.setVisited(1);
		graph1.setVisited(4);
		visited = graph1.getVisited();
		List<Integer> vis = new ArrayList<Integer>();
		vis.add(1);
		vis.add(4);
		assertTrue(visited.containsAll(vis));
		graph1.setVisited(1);
		assertTrue(visited.containsAll(vis));
		vis.add(2);
		assertFalse(visited.containsAll(vis));
	
	}
	
	@Test 
	public void test3() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		boolean empty = graph1.isEmpty();
		assertEquals(true, empty);
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		graph1.setVisited("A");
		List<String> visited = graph1.getVisited();
		List<String> vis = new ArrayList<String>();
		vis.add("A");
		assertTrue(visited.containsAll(vis));
		graph1.reset();
		vis.remove(0);
		assertTrue(visited.containsAll(vis));
		
	}
	
	
	@Test 
	public void test4() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		boolean empty = graph1.isEmpty();
		assertEquals(true, empty);
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		List<String> vertices = graph1.getVertices();
		List<String> Vexpected = new ArrayList<String>();
		Vexpected.add("A");
		Vexpected.add("B");
		Vexpected.add("C");
		Vexpected.add("D");
		assertTrue(vertices.containsAll(Vexpected) && vertices.size() == Vexpected.size());
		assertEquals(4, graph1.getSize());
		
	}
	
	@Test 
	public void test5() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		UndirectedGraph<String> graph2 = new UndirectedGraph<String>();
 		boolean empty = graph1.isEmpty();
		assertEquals(true, empty);
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D");
			graph2.addVertex("A1");
			graph2.addVertex("B1");
			graph2.addVertex("C1");
			graph2.addVertex("D1");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		assertFalse(graph2.isEmpty());
		assertEquals(graph1.getSize(), graph2.getSize());
		graph1.addEdge("A", "B");
		graph1.addEdge("A", "D");
		graph1.addEdge("B", "C");
		graph1.addEdge("C", "D");
		graph1.addEdge("A", "C");
		graph1.addEdge("B", "D");
		graph1.setVisited("A");
		List<String> adj = graph1.getAdjacent("B");
		List<String> adjExp = new ArrayList<>();
		adjExp.add("A");
		adjExp.add("C");
		adjExp.add("D");
		assertTrue(adj.containsAll(adjExp));
		List<String> visExp = new ArrayList<>();
		visExp.add("A");
		assertTrue(visExp.containsAll(graph1.getVisited()));
		
		
		
	}
	//UndirectedGraphAlgorithms tests
	@Test 
	public void test6() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		UndirectedGraphAlgorithms<String> graph1Algorithm = new UndirectedGraphAlgorithms<String>();
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		graph1.addEdge("A", "B");
		graph1.addEdge("A", "D");
		graph1.addEdge("B", "C");
		graph1.addEdge("C", "D");
		
		List<String> visited = graph1Algorithm.DFS(graph1, "A"); 
		assertTrue(visited.containsAll(graph1.getVertices()));
		graph1.reset();
		List<String> visitedB = graph1Algorithm.DFS(graph1, "B"); 
		assertTrue(visitedB.containsAll(graph1.getVertices()));
		graph1.reset();
		List<String> visitedC = graph1Algorithm.DFS(graph1, "C"); 
		assertTrue(visitedC.containsAll(graph1.getVertices()));
		graph1.reset();
		List<String> visitedD = graph1Algorithm.DFS(graph1, "D"); 
		assertTrue(visitedD.containsAll(graph1.getVertices()));
		graph1.reset();
		
	}
	
	@Test 
	public void test7() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		UndirectedGraphAlgorithms<String> graph1Algorithm = new UndirectedGraphAlgorithms<String>();
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		graph1.addEdge("A", "B");
		graph1.addEdge("A", "D");
		graph1.addEdge("B", "C");
		graph1.addEdge("C", "D");
		
		assertTrue(graph1Algorithm.isConnected(graph1));
		graph1.reset();
		graph1.addEdge("A", "C");
		graph1.addEdge("B", "D");
		
		assertTrue(graph1Algorithm.isConnected(graph1));
	}
	
	@Test 
	public void test8() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		UndirectedGraphAlgorithms<String> graph1Algorithm = new UndirectedGraphAlgorithms<String>();
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		graph1.addEdge("A", "B");
		graph1.addEdge("C", "D");
		
		assertFalse(graph1Algorithm.isConnected(graph1));
		
		
	}
	
	
	@Test 
	public void test9() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		UndirectedGraphAlgorithms<String> graph1Algorithm = new UndirectedGraphAlgorithms<String>();
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
			graph1.addVertex("A"); //adding a vertex that already exists.
		}catch (Exception e) {
			assertEquals("Vertex Already Present in the graph", e.getMessage());
		}	
		graph1.addEdge("A", "B");
		graph1.addEdge("C", "D");
		
		assertFalse(graph1Algorithm.isConnected(graph1));
		
		
	}
	
	
	@Test 
	public void test10() {
		UndirectedGraph<String> graph1 = new UndirectedGraph<String>();
		UndirectedGraphAlgorithms<String> graph1Algorithm = new UndirectedGraphAlgorithms<String>();
		try{
			graph1.addVertex("A"); // adds vertices to the graph and throws an error if vertex exist
			graph1.addVertex("B");
			graph1.addVertex("C");
			graph1.addVertex("D"); 
			graph1.addVertex("A"); //adding a vertex that already exists.
		}catch (Exception e) {
			assertEquals("Vertex Already Present in the graph", e.getMessage());
		}	
		graph1.addEdge("A", "B");
		graph1.addEdge("C", "D");
		
		assertFalse(graph1Algorithm.isConnected(graph1));
		graph1.reset();
		graph1.addEdge("A", "C");
		assertTrue(graph1Algorithm.isConnected(graph1));
		List<String> visExp = graph1.getVisited();
		graph1.reset();
		List<String> vis = graph1Algorithm.DFS(graph1, graph1.getVertices().get(0));
		assertTrue(vis.containsAll(visExp));
	}

}
