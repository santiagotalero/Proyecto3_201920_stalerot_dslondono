package model.data_structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests the graph's implementation with strings and integers.
 */
public class GraphTest 
{
	// Attributes

	/**
	 * A graph object.
	 */
	private Graph<Integer, String, Integer> graph;

	// Setup

	@Before
	/**
	 * Creates a graph object with four vertexes and four edges.
	 */
	public void setup()
	{
		graph = new Graph<Integer, String, Integer>();
		graph.addVertex(0, "Hello");
		graph.addVertex(1, "Hola");
		graph.addVertex(2, "Bye");
		graph.addVertex(3, "Hi");
		graph.addEdge(0, 1, 10);
		graph.addEdge(0, 2, 15);
		graph.addEdge(0, 3, 20);
		graph.addEdge(1, 2, 50);
	}

	// Tests

	@Test
	/**
	 * Tests that the graph is initialized properly.
	 */
	public void testInitialization()
	{
		assertTrue("The number of vertexes should be 4.", graph.V() == 4);
		System.out.println(graph.E());
		System.out.println(graph.V());
		assertTrue("The number of edges should be 4.", graph.E() == 4);
	}

	@Test
	/**
	 * Tests that the graph's vertexes and arcs are exactly as introduced: no extra vertexes or arcs.
	 */
	public void testVertexesAndArcs()
	{
		assertTrue("The vertex's value should be 'Hello'.", graph.getInfoVertex(0).compareTo("Hello") == 0);
		assertTrue("The vertex's value should be 'Hola'.", graph.getInfoVertex(1).compareTo("Hola") == 0);
		assertTrue("The vertex's value should be 'Bye'.", graph.getInfoVertex(2).compareTo("Bye") == 0);
		assertTrue("The vertex's value should be 'Hi'.", graph.getInfoVertex(3).compareTo("Hi") == 0);
		assertTrue("The arc's information should be 10.", graph.getInfoArc(0, 1) == 10);
		assertTrue("The arc's information should be 15.", graph.getInfoArc(0, 2) == 15);
		assertTrue("The arc's information should be 20.", graph.getInfoArc(0, 3) == 20);
		assertTrue("The arc's information should be 50.", graph.getInfoArc(1, 2) == 50);
		// Checking both ways.
		assertTrue("The arc's information should be 10.", graph.getInfoArc(1, 0) == 10);
		assertTrue("The arc's information should be 15.", graph.getInfoArc(2, 0) == 15);
		assertTrue("The arc's information should be 20.", graph.getInfoArc(3, 0) == 20);
		assertTrue("The arc's information should be 50.", graph.getInfoArc(2, 1) == 50);
		// Checking that certain arcs don't exist.
		assertNull(graph.getInfoArc(0, 0));
		assertNull(graph.getInfoArc(1, 1));
		assertNull(graph.getInfoArc(2, 2));
		assertNull(graph.getInfoArc(1, 3));
		assertNull(graph.getInfoArc(3, 1));
		assertNull(graph.getInfoArc(1, 3));
		assertNull(graph.getInfoArc(2, 3));
		assertNull(graph.getInfoArc(3, 2));
		// Due to the contract, checking for non-existent vertexes should fail.
		boolean failed = false;
		try
		{ graph.getInfoVertex(4); }
		catch(Exception e)
		{ failed = true; }
		assertTrue("The graph should have failed.", failed);
	}

	@Test
	/**
	 * Tests that the graph adds vertexes properly.
	 */
	public void testAddVertex()
	{
		graph.addVertex(100, "100");
		assertEquals("The vertex's value should be 100.", "100", graph.getInfoVertex(100));
		assertTrue("The number of vertexes should be 5.", graph.V() == 5);
		assertTrue("The number of edges should be 4.", graph.E() == 4);
		graph.addVertex(101, "101");
		assertEquals("The vertex's value should be 101.", "101", graph.getInfoVertex(101));
		assertTrue("The number of vertexes should be 6.", graph.V() == 6);
		assertTrue("The number of edges should be 4.", graph.E() == 4);
	}

	@Test
	/**
	 * Tests that the graph adds arcs properly.
	 */
	public void testAddArc()
	{
		graph.addVertex(100, "100");
		graph.addVertex(101, "101");
		graph.addEdge(100, 101, 200);
		assertTrue("The arc's information should be 200.", graph.getInfoArc(100, 101) == 200);
		assertTrue("The arc's information should be 200.", graph.getInfoArc(101, 100) == 200);
		assertTrue("The number of vertexes should be 6.", graph.V() == 6);
		assertTrue("The number of edges should be 5.", graph.E() == 5);
	}

	@Test
	/**
	 * Tests that the graph's vertexes set information properly.
	 */
	public void testChangeVertexInfo()
	{
		graph.setInfoVertex(0, "Goodbye");
		assertTrue("The vertex's new value should be 'Goodbye'.", graph.getInfoVertex(0).compareTo("Goodbye") == 0);
		// Makes sure nothing else changed:
		assertTrue("The vertex's value should be 'Hola'.", graph.getInfoVertex(1).compareTo("Hola") == 0);
		assertTrue("The vertex's value should be 'Bye'.", graph.getInfoVertex(2).compareTo("Bye") == 0);
		assertTrue("The vertex's value should be 'Hi'.", graph.getInfoVertex(3).compareTo("Hi") == 0);
		assertTrue("The arc's information should be 10.", graph.getInfoArc(0, 1) == 10);
		assertTrue("The arc's information should be 15.", graph.getInfoArc(0, 2) == 15);
		assertTrue("The arc's information should be 20.", graph.getInfoArc(0, 3) == 20);
		assertTrue("The arc's information should be 50.", graph.getInfoArc(1, 2) == 50);
		assertTrue("The number of vertexes should be 4.", graph.V() == 4);
		assertTrue("The number of edges should be 4.", graph.E() == 4);
		boolean failed = false;
		// Due to the contract, changing the value of non-existent vertexes should fail.
		try
		{ graph.setInfoVertex(4, "Test"); }
		catch(Exception e)
		{ failed = true; }
		assertTrue("The graph should have failed.", failed);
	}

	@Test
	/**
	 * Tests that graph's arcs set information properly.
	 */
	public void testChangeArcInfo()
	{
		graph.setInfoArc(0, 1, 100);
		assertTrue("The arc's new value should be 100.", graph.getInfoArc(0, 1) == 100);
		assertTrue("The arc's new value should be 100.", graph.getInfoArc(1, 0) == 100);
		// Makes sure nothing else changed:
		assertTrue("The vertex's value should be 'Hello'.", graph.getInfoVertex(0).compareTo("Hello") == 0);
		assertTrue("The vertex's value should be 'Hola'.", graph.getInfoVertex(1).compareTo("Hola") == 0);
		assertTrue("The vertex's value should be 'Bye'.", graph.getInfoVertex(2).compareTo("Bye") == 0);
		assertTrue("The vertex's value should be 'Hi'.", graph.getInfoVertex(3).compareTo("Hi") == 0);
		assertTrue("The arc's information should be 15.", graph.getInfoArc(0, 2) == 15);
		assertTrue("The arc's information should be 20.", graph.getInfoArc(0, 3) == 20);
		assertTrue("The arc's information should be 50.", graph.getInfoArc(1, 2) == 50);
		assertTrue("The number of vertexes should be 4.", graph.V() == 4);
		assertTrue("The number of edges should be 4.", graph.E() == 4);
	}

	@Test
	/**
	 * Tests the correctness of the vertex's adjacents.
	 *<b>Pre:<\b> the bag class works properly.<br>
	 */
	public void testAdjacents()
	{
		Iterator<Integer> it = (Iterator<Integer>) graph.adj(0);
		Integer[] nums = {4, 5, 6};
		int i = 0;
		while(it.hasNext())
		{
			nums[i] = it.next();
			i++;
		}
		// If there are more than three nodes, there should be an IndexOutOfBounds Exception.
		assertTrue("The number should be 3.", nums[0] == 1);
		assertTrue("The number should be 2.", nums[1] == 2);
		assertTrue("The number should be 1.", nums[2] == 3);
		// Tests a new vertex.
		graph.addVertex(4, "4");
		it = (Iterator<Integer>) graph.adj(4);
		i = 0;
		while(it.hasNext())
			i++;
		assertTrue("There shouldn't be any items in the adjacents bag.", i == 0);
	}
}
