package model.data_structures;

import java.util.Iterator;

/**
 * Class that represents a priority queue using binary heap.
 * @author Daniel del Castillo A.
 * @param <K> The item's class type.
 * <b>Invariant:<\b> pq != null && N >= 0 && max >= 0.
 */
public class MaxHeapPQ<K extends Comparable<K>> implements Iterable<K>, IMaxHeapPQ<K>
{
	// Attributes

	/**
	 * Array of keys.
	 */
	private K[] pq;

	/**
	 * Size of the priority queue.
	 */
	private int N;

	/**
	 * Maximum size of the priority queue.
	 */
	private int max;

	// Constructors

	/**
	 * Creates a MaxHeapCP object: max = 2 && pq = Comparable[max+1] && N = 0.
	 */
	public MaxHeapPQ()
	{
		max = 2;
		pq = (K[]) new Comparable[max+1];
		N = 0;
		check();
	}

	/**
	 * Creates a MaxHeapCP object: max = pMax && pq = Comparable[max+1] && N = 0.
	 * @param pMax Given maximum capacity for the priority queue.
	 */
	public MaxHeapPQ(int pMax)
	{
		max = pMax;
		pq = (K[]) new Comparable[max+1];
		N = 0;
		check();
	}

	// Methods

	/**
	 * Adds an element to the priority queue. If the element already exists and has a
	 * different priority, the element is updated in the queue.
	 * @param item Element to insert. 
	 */
	public void insert(K item)
	{
		if(N == max)
		{
			max *= 2;
			K[] copia = pq;
			pq = (K[]) new Comparable[max+1];
			for(int i = 0; i <= N; i++)
				pq[i] = copia[i];
		}
		pq[++N] = item;
		swim(N);
		check();
	}

	/**
	 * @return The item with the highest priority. null if the priority queue is empty. 
	 */
	public K max()
	{ return pq[1]; }

	/**
	 * @return The item with the highest priority and deletes it. null if the priority queue is empty.
	 */
	public K delMax()
	{
		K max = null;
		if(!isEmpty())
		{
			max = pq[1];
			exchange(1, N--);
			pq[N+1] = null;
			sink(1);
		}
		check();
		return max;
	}

	/**
	 * @return True if the priority queue is empty. False if contrary.
	 */
	public boolean isEmpty()
	{ return N == 0; }

	/**
	 * Checks if the item at index i is hierarchically lower than the item at index j.
	 * @param i Index of the first item.
	 * @param j Index of the second item.
	 * @return True if pq[i] < pq[j], False if contrary.
	 */
	private boolean less(int i, int j)
	{ return pq[i].compareTo(pq[j]) < 0; }

	/**
	 * Switches two items at indexes i and j.
	 * @param i Index of the first item.
	 * @param j Index of the second item. 
	 */
	private void exchange(int i, int j)
	{ K t = pq[i]; pq[i] = pq[j]; pq[j] = t; }

	/**
	 * Executes the swim function for some item at position k (rises hierarchically through parents).
	 * @param k Index of the item to swim.
	 */
	private void swim(int k)
	{
		while(k > 1 && less(k/2, k))
		{
			exchange(k/2, k);
			k = k/2;
		}
	}

	/**
	 * Executes the sink function for some item at position k (falls hierarchically through sons).
	 * @param k Index of the item to sink.
	 */
	private void sink(int k)
	{
		while(2*k <= N)
		{
			int j = 2*k;
			if(j < N && less(j, j+1))
				j++;
			if(!less(k, j))
				break;
			exchange(k, j);
			k =j;
		}
	}

	/**
	 * @return The priority queue's size. N >= 0.
	 */
	public int size()
	{ return N; }

	/**
	 * @return The priority queue's iterator. iterator != null.
	 */
	public Iterator<K> iterator()
	{ return new HeapMaxPQIterator<K>(); }

	// Invariant

	/**
	 * Ensures the correctness of the priority queue: max >= 0 && N >= 0 && pq != null.
	 */
	private void check()
	{
		assert max >= 0 : "The maximum number of keays shouldn't be negative.";
		assert N >= 0 : "The number of keys shouldn't be negative.";
		assert pq != null : "The array of keys shouldn't be null.";
	}

	// Iterator

	/**
	 * Class that represents an iterator for the max heap priority queue.
	 * @author Daniel del Castillo A.
	 * @param <K> The stored element's class.
	 */
	private class HeapMaxPQIterator<K extends Comparable<K>> implements Iterator<K>
	{
		// Attributes

		/**
		 * A copy of the max heap priority queue.
		 */
		private MaxHeapPQ<K> copy;

		// Constructor

		/**
		 * Creates a HeapIterator object: recopies the priority queue such that the original
		 * priority queue items aren't modified through the delMax() method.
		 */
		public HeapMaxPQIterator() 
		{
			copy = new MaxHeapPQ<K>(N);
			for (int i = 1; i <= N; i++)
				copy.insert((K) pq[i]);
		}

		// Methods

		/**
		 * @return True if there's a next element, False if contrary.
		 */
		public boolean hasNext()  
		{ return !copy.isEmpty(); }

		/**
		 * @return The next element in the priority queue.
		 */
		public K next() 
		{ return copy.delMax(); }
	}
}
