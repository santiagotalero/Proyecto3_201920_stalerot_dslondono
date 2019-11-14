package model.data_structures;

import java.util.Iterator;

public interface IArrayList<T extends Comparable<T>> extends Iterable<T>
{
	// Methods

	/**
	 * Adds an element to the array. 
	 * @param element Element to add. 
	 */
	public void add(T element);

	/**
	 * @return The current size of the array. N >= 0. 
	 */
	public int size();

	/**
	 * @return True if the array is empty, false if contrary.
	 */
	public boolean isEmpty();

	/**
	 * @param i The elements position.
	 * @return Returns the element in the i-th position. null if invalid position.
	 */
	public T get(int i);

	/**
	 * @return The element if it exists in the array, null if it doesn't. 
	 */
	public T search(T dato);

	/**
	 * Deletes the first instance of the element in the array.
	 * @return The element if it exists in the array, null if it doesn't. 
	 */
	public T delete(T dato);

	/**
	 * Deletes the element in the i-th position. 
	 * @param i Position of the element to delete.
	 * @return The element in the i-th position, null if position is invalid. 
	 */
	public T delete(int i);

	/**
	 * @return The class' iterator.
	 */
	public Iterator<T> iterator();
}
