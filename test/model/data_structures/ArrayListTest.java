package model.data_structures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests the correct functionality of the ArrayList data structure.
 */
public class ArrayListTest 
{
	// Attributes
	
	/**
	 * ArrayList object to test.
	 */
	ArrayList<Integer> array;
	
	// Setups
	
	@Before
	/**
	 * Sets up the array.
	 */
	public void setup()
	{
		array = new ArrayList<Integer>();
	}
	// Tests

	@Test
	/**
	 * Tests that the ArrayList is initialized properly.
	 *<b>Pre:<\b> <br>
	 *
	 */
	public void testInitialization()
	{
		assertNotNull("The array shouldn't be null.", array);
		assertTrue("The array should be empty.", array.isEmpty());
		assertTrue("The size of the array should be 0.", array.size() == 0);
	}
	
	@Test
	/**
	 * Tests that the add function works properly.
	 */
	public void testAdd()
	{
		array.add(0);
		assertTrue("The size of the array should be 1.", array.size() == 1);
		array.add(0);
		assertTrue("The size of the array should be 2.", array.size() == 2);
		array.add(1);
		assertTrue("The size of the array should be 3.", array.size() == 3);
	}
	
	@Test
	/**
	 * Tests that the search function works properly.
	 */
	public void testSearch()
	{
		Integer a = array.search(0);
		assertTrue("The array shouldn't find any element.", a == null);
		array.add(1);
		a = array.search(0);
		assertTrue("The array shouldn't find any element.", a == null);
		a = array.search(1);
		assertTrue("The array should find the element 1.", a == 1);
		assertTrue("The size of the array should remain at 1.", array.size() == 1);
		assertTrue("The array shouldn't change it's empty status.", !array.isEmpty());
	}
	
	@Test
	/**
	 * Tests that the get function works properly.
	 */
	public void testGet()
	{
		Integer a = array.get(1);
		assertNull("Since the index is out of bounds, the result should be null.", a);
		a = array.get(-1);
		assertNull("Since the index is out of bounds, the result should be null.", a);
		array.add(10);
		a = array.get(1);
		assertNull("Since the index is out of bounds, the result should be null.", a);
		a = array.get(-1);
		assertNull("Since the index is out of bounds, the result should be null.", a);
		a = array.get(0);
		assertTrue("The retrieved element should be 10.", a == 10);
		assertTrue("The size of the array should remain at 1.", array.size() == 1);
		assertTrue("The array shouldn't change it's empty status.", !array.isEmpty());
		// Repetition to ensure data wasn't modified.
		a = array.get(0);
		assertTrue("The retrieved element should be 10.", a == 10);
		assertTrue("The size of the array should remain at 1.", array.size() == 1);
		assertTrue("The array shouldn't change it's empty status.", !array.isEmpty());
		
	}
	
	@Test
	/**
	 * Tests that the delete element function works properly.
	 */
	public void testDeleteElement()
	{
		array.add(0); array.add(0); array.add(1); array.add(0); array.add(2);
		array.delete((Integer) 0);
		assertTrue("The array shouldn't delete more than one element.", array.size() == 4);
		// Tests that the zeroes are in correct positions:
		assertTrue("The element in the 0th position should be 0.", 0 == array.get(0));
		assertTrue("The element in the 1th position should be 1.", 1 == array.get(1));
		assertTrue("The element in the 2nd position should be 0.", 0 == array.get(2));
		assertTrue("The element in the 3rd position should be 2.", 2 == array.get(3));
		
		array.delete((Integer) 0);
		assertTrue("The array shouldn't delete more than one element.", array.size() == 3);
		array.delete((Integer) 0);
		assertTrue("The array shouldn't delete more than one element.", array.size() == 2);
		array.delete((Integer) 0);
		assertTrue("The array shouldn't delete any elements.", array.size() == 2);
		Integer a = array.search(1) + array.search(2);
		assertTrue("The array shouldn't the 1 and 2 elements.", a == 3);
		assertTrue("The array shouldn't change it's empty status.", !array.isEmpty());
		// Test positions
		a = array.get(0);
		assertTrue("The element in the 0th position should be 1.", a == 1);
		a = array.get(1);
		assertTrue("The element in the 1st position should be 2.", a == 2);
	}
	
	@Test
	/**
	 * Tests that the delete index function works properly.
	 *<b>Pre:<\b> <br>
	 *
	 */
	public void testDeleteIndex()
	{
		array.add(0); array.add(0); array.add(1); array.add(0); array.add(2);
		array.delete(1);
		assertTrue("The array shouldn't delete more than one element.", array.size() == 4);
		// Tests that the zeroes are in correct positions:
		assertTrue("The element in the 0th position should be 0.", 0 == array.get(0));
		assertTrue("The element in the 1th position should be 1.", 1 == array.get(1));
		assertTrue("The element in the 2nd position should be 0.", 0 == array.get(2));
		assertTrue("The element in the 3rd position should be 2.", 2 == array.get(3));
		
		array.delete(2);
		assertTrue("The array shouldn't delete more than one element.", array.size() == 3);
		array.delete(0);
		assertTrue("The array shouldn't delete more than one element.", array.size() == 2);
		Integer a = array.search(1) + array.search(2);
		assertTrue("The array shouldn't the 1 and 2 elements.", a == 3);
		assertTrue("The array shouldn't change it's empty status.", !array.isEmpty());
		// Test positions
		a = array.get(0);
		assertTrue("The element in the 0th position should be 1.", a == 1);
		a = array.get(1);
		assertTrue("The element in the 1st position should be 2.", a == 2);
	}
	
	@Test
	/**
	 * Tests that the ArrayList iterator works properly.
	 */
	public void testIterator()
	{
		int i = 0;
		Integer a = 0;
		Integer[] order = new Integer[6];
		array.add(5); array.add(4); array.add(3); array.add(2); array.add(1); array.add(0);
		for(Integer act : array)
		{
			a += act;
			order[i] = act;
			i++;
		}
		assertTrue("The array should have gone through itself entirely.", a == 5*(5+1)/2);
		assertTrue("The element in the 0th posisition should 5.", order[0] == 5);
		assertTrue("The element in the 1st posisition should 4.", order[1] == 4);
		assertTrue("The element in the 2nd posisition should 3.", order[2] == 3);
		assertTrue("The element in the 3rd posisition should 2.", order[3] == 2);
		assertTrue("The element in the 4th posisition should 1.", order[4] == 1);
		assertTrue("The element in the 5th posisition should 0.", order[5] == 0);
	}
}
