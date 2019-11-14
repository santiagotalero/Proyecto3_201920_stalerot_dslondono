package model.data_structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests the Arc<K, V, A> implementation with string and integers.
 * <b>Pre:<\b> the Vertex class should work properly.<br>
 */
public class ArcTest 
{
	// Attributes

	/**
	 * The first vertex.
	 */
	private Vertex<Integer, String, Integer> vertex1;

	/**
	 * The second vertex.
	 */
	private Vertex<Integer, String, Integer> vertex2;

	/**
	 * The third vertex.
	 */
	private Vertex<Integer, String, Integer> vertex3;

	/**
	 * The first arc.
	 */
	private Arc<Integer, String, Integer> arc1;

	/**
	 * The second arc.
	 */
	private Arc<Integer, String, Integer> arc2;

	// Setup

	@Before
	/**
	 * Creates three Vertex objects and two Arc objects.
	 */
	public void setup()
	{
		vertex1 = new Vertex<Integer, String, Integer>(0, "Hello");
		vertex2 = new Vertex<Integer, String, Integer>(1, "Hola");
		vertex3 = new Vertex<Integer, String, Integer>(2, "Hi");
		arc1 = new Arc<Integer, String, Integer>(vertex1, vertex2, 20);
		arc2 = new Arc<Integer, String, Integer>(vertex1, vertex3, 30);
	}

	// Tests

	@Test
	/**
	 * Tests that arcs are initialized properly.
	 */
	public void testInitialization()
	{
		assertTrue("The arcs shouldn't be null.", arc1 != null && arc2 != null);
		assertTrue("The arc1's start should be vertex1", arc1.getStart().compareTo(vertex1) == 0);
		assertTrue("The arc2's start should be vertex1", arc2.getStart().compareTo(vertex1) == 0);
		assertTrue("The arc1's finish should be vertex2", arc1.getFinish().compareTo(vertex2) == 0);
		assertTrue("The arc2's finish should be vertex3", arc2.getFinish().compareTo(vertex3) == 0);
		assertTrue("The arc1's info should be 20.", arc1.getInfo() == 20);
		assertTrue("The arc2's info should be 20.", arc2.getInfo() == 30);
	}

	@Test
	/**
	 * Tests that arcs retrieve vertexes properly.
	 */
	public void testGetDifferentThan()
	{
		assertTrue("The returned vertex should be vertex1.", arc1.getDifferentThan(vertex2).compareTo(vertex1) == 0);
		assertTrue("The returned vertex should be vertex2.", arc1.getDifferentThan(vertex1).compareTo(vertex2) == 0);
		assertTrue("The returned vertex should be vertex1.", arc2.getDifferentThan(vertex3).compareTo(vertex1) == 0);
		assertTrue("The returned vertex should be vertex3.", arc2.getDifferentThan(vertex1).compareTo(vertex3) == 0);
	}

	@Test
	/**
	 * Tests that arcs are compared properly.
	 */
	public void testCompareArcs()
	{
		assertTrue("The arc2 should be hierarchically higher than arc1.", arc2.compareTo(arc1) > 0);
		assertTrue("The arc1 should be hierarchically lower than arc2.", arc1.compareTo(arc2) < 0);
		assertTrue("The arc1 should be equal to iteself.", arc1.compareTo(arc1) == 0);
		arc2.setInfo(20);
		assertTrue("The arc1 should be equal to the arc2.", arc1.compareTo(arc2) == 0);
	}
}
