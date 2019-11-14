package model.data_structures;

import java.util.Iterator;

/**
 * Class that represents a dinamic array of comparable elements.
 * @param <T> The element's class type.
 */
public class ArrayList<T extends Comparable<T>> implements IArrayList<T>
{
	// Attributes

	/**
	 * Maximum size of the array.
	 */
	private int max;

	/**
	 * Actual size of the array.
	 */
	private int N;

	/**
	 * Array of elements.
	 */
	private T elements[];

	// Constructor

	/**
	 * Creates an ArrayList object with default max size: 2.
	 */
	public ArrayList()
	{
		max = 2; 
		elements = (T[]) new Comparable[max];
		N = 0;
	}

	/**
	 * Creates an ArrayList object with given starting size.
	 * @param pSize The starting size of the array.
	 */
	public ArrayList(int pSize)
	{
		max = pSize > 0 ? pSize : 2;
		elements = (T[]) new Comparable[max];
		N = 0;
	}

	// Methods

	/**
	 * Adds an element to the array. 
	 * @param element Element to add. 
	 */
	public void add(T element)
	{
		if(N == max)
		{
			max *= 2;
			T[] copia = elements;
			elements = (T[]) new Comparable[max];
			for (int i = 0; i < N; i++)
				elements[i] = copia[i];
		}	
		elements[N] = element;
		N++;
	}

	/**
	 * @return The current size of the array. N >= 0. 
	 */
	public int size() 
	{ return N; }

	/**
	 * @return True if the array is empty, false if contrary.
	 */
	public boolean isEmpty()
	{ return N == 0; }

	/**
	 * @param i The elements position.
	 * @return Returns the element in the i-th position. null if invalid position.
	 */
	public T get(int i) 
	{ return i >= N || i < 0 ? null : elements[i]; }

	/**
	 * @return The element if it exists in the array, null if it doesn't. 
	 */
	public T search(T dato) 
	{
		for(T act : elements)
		{
			if(act == null)
				break;
			if(act.compareTo(dato) == 0)
				return act;
		}
		return null;
	}

	/**
	 * Deletes the first instance of the element in the array.
	 * @return The element if it exists in the array, null if it doesn't. 
	 */
	public T delete(T dato) 
	{
		int i = 0;
		T e = null;
		T[] copy = (T[]) new Comparable[max];
		for(T act : elements)
		{
			if(act == null)
				break;
			if(act.compareTo(dato) == 0 && e == null)
			{
				e = act;
				N--;
			}
			else
			{
				copy[i] = act;
				i++;
			}
		}
		elements = copy;
		return e;
	}

	/**
	 * Deletes the element in the i-th position. 
	 * @param i Position of the element to delete.
	 * @return The element in the i-th position, null if position is invalid. 
	 */
	public T delete(int i)
	{
		T e = null;
		if(i >= 0 && i < N)
		{
			int k = 0;
			T[] copia = (T[]) new Comparable[max];
			for(int j = 0; j < N; j++)
			{
				if(j != i)
				{
					copia[k] = elements[j];
					k++;
				}
				else
					e = elements[j];
			}
			N--;
			elements = copia;
		}
		return e;
	}

	/**
	 * @return The class' iterator.
	 */
	public Iterator<T> iterator() 
	{ return new IteratorArrayList<T>(); }
	
	/**
	 * @return The String representation of the array. 
	 */
	public String toString()
	{
		String s = "[";
		T element;
		for(int i = 0; i < N; i++)
		{
			element = elements[i];
			if(element == null)
				break;
			s += element.toString() + ", ";
		}
		return s.substring(0, s.length()-2) + "]";
	}

	/**
	 * Class that represents an iterator over the ArrayList class.
	 * @author Daniel del Castillo A.
	 * @param <T> The element's class type.
	 */
	private class IteratorArrayList<T> implements Iterator<T>
	{
		// Attributes

		/**
		 * Actual position.
		 */
		private int pos = 0;

		@Override
		/**
		 * @return True if there's a next element, false if contrary.
		 */
		public boolean hasNext() 
		{ return pos < N; }

		@Override
		/**
		 * @return The next element in the array.
		 */
		public T next() 
		{ return (T) elements[pos++]; }
	}
}
