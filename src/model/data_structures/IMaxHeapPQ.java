package model.data_structures;

/**
 * Interface that represents the MaxHeapPQ's API.
 * @author Daniel del Castillo A.
 * @param <T> The item's class type.
 */
public interface IMaxHeapPQ<K extends Comparable<K>>
{
	// Methods

	/**
	 * Adds an element to the priority queue. If the element already exists and has a
	 * different priority, the element is updated in the queue.
	 * @param item Element to insert. 
	 */
	public void insert(K item);

	/**
	 * @return The item with the highest priority. null if the priority queue is empty. 
	 */
	public K max();

	/**
	 * @return The item with the highest priority and deletes it. null if the priority queue is empty.
	 */
	public K delMax();

	/**
	 * @return True if the priority queue is empty. False if contrary.
	 */
	public boolean isEmpty();
	
	/**
	 * @return The priority queue's size. N >= 0.
	 */
	public int size();
}
