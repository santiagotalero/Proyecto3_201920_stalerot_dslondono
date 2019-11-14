package model.data_structures;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class that tests the Vertex<K, V, A> implementation with integers and strings.
 * <b>Pre:<\b> the Bag class should work properly.<br>
 */
public class VertexTest 
{
	// Attributes

	/**
	 * First vertex.
	 */
	private Vertex<Integer, String, Integer> vertex1;

	/**
	 * Second vertex.
	 */
	private Vertex<Integer, String, Integer> vertex2;

	// Setup

	/**
	 * Creates two distinct Vertex objects.
	 */
	public void setup()
	{
		vertex1 = new Vertex<Integer, String, Integer>(0, "Hello");
		vertex2 = new Vertex<Integer, String, Integer>(1, "Hola");
	}

	// Tests

	@Test
	/**
	 * Tests that vertexes are properly initialized.
	 */
	public void testInitialization()
	{
		setup();
		assertTrue("None of the vertexes should be null.", vertex1 != null && vertex2 != null);
		assertTrue("The vertex1's id should be 0.", 0 == vertex1.getId());
		assertTrue("The vertex1's value should be 'Hello'.", "Hello".compareTo(vertex1.getValue()) == 0);
		assertTrue("The vertex2's id should be 1.", 1 == vertex2.getId());
		assertTrue("The vertex2's value should be 'Hola'.", "Hola".compareTo(vertex2.getValue()) == 0);
		assertTrue("Adjacent vertexes' id bag should be empty.", vertex1.getAdjacentsId().isEmpty());
		assertTrue("Adjacent vertexes' id bag should be empty.", vertex2.getAdjacentsId().isEmpty());
	}

	@Test
	/**
	 * Tests that information is changed and retrieved properly.
	 */
	public void testChangeValue()
	{
		setup();
		vertex1.setId(123); vertex1.setValue("Bye");
		assertTrue("The vertex's id should be 123.", 123 == vertex1.getId());
		assertTrue("The vertex's value should be 'Bye'", "Bye".compareTo(vertex1.getValue()) == 0);
	}

	@Test
	/**
	 * Tests that vertexes are compared properly.
	 */
	public void testCompareVertexes()
	{
		setup();
		assertTrue("Vertex1 should be hierarchically smaller than vertex2.", vertex1.compareTo(vertex2) < 0);
		assertTrue("Vertex2 should be hierarchically higher than vertex1.", vertex2.compareTo(vertex1) > 0);
		assertTrue("Vertexes are equal (self-comparison should be 0).", vertex1.compareTo(vertex1) == 0);
		vertex2.setId(0);
		assertTrue("Vertexes are equal, comparison should be 0.", vertex1.compareTo(vertex2) == 0);
		assertTrue("Vertexes are equal, comparison should be 0.", vertex2.compareTo(vertex1) == 0);
		vertex1.setId(123);
		assertTrue("Vertex2 should be hierarchically smaller than vertex1.", vertex2.compareTo(vertex1) < 0);
		assertTrue("Vertex1 should be hierarchically bigger than vertex2.", vertex1.compareTo(vertex2) > 0);
	}

	@Test
	/**
	 * Tests that adjacents are added properly.
	 */
	public void testAdjacentsId()
	{
		setup();
		vertex1.addAdjacent(vertex2.getId());
		vertex2.addAdjacent(vertex1.getId());
		assertTrue("Adjacent vertexes' id bag shouldn't be empty.", !vertex1.getAdjacentsId().isEmpty());
		assertTrue("Adjacent vertexes' id bag shouldn't be empty.", !vertex2.getAdjacentsId().isEmpty());
		ArrayList<Integer> array1 = vertex1.getAdjacentsId();
		ArrayList<Integer> array2 = vertex2.getAdjacentsId();
		assertTrue("The bags should only have 1 item.", array1.size() == 1 && array2.size() == 1);
		for(Integer act : array1)
			assertTrue("The bag's only item should be 1.", act == 1);
		for(Integer act : array2)
			assertTrue("The bag's only item should be 0.", act == 0);
	}
}
