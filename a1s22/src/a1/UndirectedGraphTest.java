package a1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UndirectedGraphTest {

	
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSize() {
		UndirectedGraph<String> ug = new UndirectedGraph<>();;
		try {
			ug.addVertex("A");
			ug.addVertex("B");
			ug.addVertex("C");
		} catch (VertexExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int ugSize = ug.getSize();
		int expectedSize = 3;
		assertEquals(expectedSize, ugSize);
	}
	

	@Test
	public void testGetVertices() {
		UndirectedGraph<String> ug = new UndirectedGraph<>();;
		try {
			ug.addVertex("A");
			ug.addVertex("B");
			ug.addVertex("C");
		} catch (VertexExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> ugVertices = (ArrayList<String>) ug.getVertices();
		ugVertices.sort((s1, s2) -> s1.compareTo(s2));
		List<String> expectedVertices = new ArrayList<>();
		expectedVertices.add("A");
		expectedVertices.add("B");
		expectedVertices.add("C");
		assertEquals(expectedVertices, ugVertices);
	}
	
	@Test
	public void testGetAdjacent() {
		UndirectedGraph<String> ug = new UndirectedGraph<>();;
		try {
			ug.addVertex("A");
			ug.addVertex("B");
			ug.addVertex("C");
			ug.addEdge("A", "B");
			ug.addEdge("A", "C");
		} catch (VertexExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> aAdjacent = (ArrayList<String>) ug.getAdjacent("A");
		aAdjacent.sort((s1, s2) -> s1.compareTo(s2));
		List<String> expectedAdjacent = new ArrayList<>();
		expectedAdjacent.add("B");
		expectedAdjacent.add("C");
		assertEquals(expectedAdjacent, aAdjacent);
	}
	
	@Test
	public void testToString() {
		UndirectedGraph<String> ug = new UndirectedGraph<>();;
		try {
			ug.addVertex("A");
			ug.addVertex("B");
			ug.addVertex("C");
			ug.addEdge("A", "B");
			ug.addEdge("A", "C");
		} catch (VertexExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String toSt = ug.toString();
		String expected = "Graph:\nVertex: A & Adjacent Vertices: [B, C]\nVertex: B & Adjacent Vertices: [A]\nVertex: C & Adjacent Vertices: [A]\n";
	    assertEquals(expected, toSt);
	}

}