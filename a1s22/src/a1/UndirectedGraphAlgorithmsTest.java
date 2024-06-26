package a1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class UndirectedGraphAlgorithmsTest {
	
	UndirectedGraphAlgorithms<String> uga;

	@Before
	public void setUp() throws Exception {
		uga = new UndirectedGraphAlgorithms<>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Graph<String> g = new UndirectedGraph<>();
		try {
			g.addVertex("A");
			g.addVertex("B");
			g.addVertex("C");
			g.addVertex("D");
			g.addVertex("E");
		}
		catch (VertexExistsException v) {
			
		}
		g.addEdge("A", "B");
		g.addEdge("A", "C");
		g.addEdge("B", "C");
		g.addEdge("D", "E");

		boolean result =  uga.isConnected(g);
		assertFalse(result);
		
	}

}
