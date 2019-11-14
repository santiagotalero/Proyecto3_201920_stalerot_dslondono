package model.data_structures;

import java.math.BigInteger;

/**
 * Class that represents a hash table with linear probing implementation.
 * @author Daniel del Castillo A.
 * @param <K> The key's class type.
 * @param <V> The value's class type.
 * <b>Invariant:<\b> M >= 2*N. Checks value-key relationship upon deleting a tuple.
 */
public class HashTableLinearProbing<K extends Comparable<K>, V> implements ISymbolTable<K, V>
{
	// Constants

	/**
	 * Constant that represents the table's loading factor.
	 */
	public static final double LOADING_FACTOR = 0.75;

	/**
	 * Constant that represents the hash table's initial capicity if not specified.
	 */
	public static final int CAPACIDAD_INICIAL = 11;


	// Attributes

	/**
	 * Number of key-value tuples.
	 */
	private int N;

	/**
	 * The table's size.
	 */
	private int M;

	/**
	 * Array of keys.
	 */
	private K[] keys;

	/**
	 * Array of values.
	 */
	private V[] values;

	// Constructor

	/**
	 * Creates a HashTableLinearProbing object with the preset initial capacity.
	 */
	public HashTableLinearProbing()
	{ this(CAPACIDAD_INICIAL); }

	/**
	 * Creates a HashTableLinearProbing object with the initial capacity given.
	 * @param pCap The table's initial capacity.
	 */
	public HashTableLinearProbing(int pCap)
	{
		N = 0;
		M = pCap;
		keys = (K[]) new Comparable[M];
		values = (V[]) new Object[M];
	}

	// Methods

	/**
	 * Puts the key-value tuple in the table. Removes the key from the table if the value is null.
	 * Updates the value if the key already exists.
	 * @param key The key of the tuple. 
	 * @param val The value of the tuple.
	 */
	public void put(K key, V val)
	{
		if(key == null)
			return;
		if(val == null)
		{
			delete(key);
			return;
		}
		if(N >= LOADING_FACTOR*M)
			resize(2*M);
		int i;
		for(i = hash(key); keys[i] != null; i = (i+1)%M)
		{
			if(keys[i].equals(key))
			{
				values[i] = val;
				return;
			}
		}
		keys[i] = key;
		values[i] = val;
		N++;
	}

	/**
	 * Returns the given key's assigned value.
	 * @param key The key whose value is desired.
	 * @return The value of the given key if it exists.
	 */
	public V get(K key)
	{
		if(key == null)
			return null;
		for(int i = hash(key); keys[i] != null; i = (i+1)%M)
		{
			if(keys[i].compareTo(key) == 0)
				return values[i];
		}
		return null;
	}

	/**
	 * Removes the key-value tuple from the table.
	 * @param key The key of tuple to remove.
	 */
	public void delete(K key)
	{
		if(key == null)
			return;
		if(!contains(key))
			return;
		int i = hash(key);
		while(!key.equals(keys[i]))
			i = (i+1)%M;
		keys[i] = null;
		values[i] = null;
		i = (i+1)%M;
		while(keys[i] != null)
		{
			K tempKey = keys[i];
			V tempVal = values[i];
			keys[i] = null;
			values[i] = null;
			N--;
			put(tempKey, tempVal);
			i = (i+1)%M;
		}
		N--;
		if(N > 0 && N <= M/8)
			resize(M/2);
		assert invariant();
	}

	/**
	 * Checks if a given key is stored in the table.
	 * @param key The key to check.
	 * @return True if there's an assigned value to the give key, False if contrary.
	 */
	public boolean contains(K key)
	{ return key != null ? get(key) != null : false; }

	/**
	 * @return True if the table is empty, False if contrary.
	 */
	public boolean isEmpty()
	{ return N == 0; }

	/**
	 * @return Number of key-value tuples in the table. N >= 0. 
	 */
	public int size()
	{ return N; }
	
	/**
	 * @return The table's maximum number of key-value tuples. M >= 0.
	 */
	public int maxSize()
	{ return M; }

	/**
	 * @return An Iterable object with the table's keys. Iterable != null. 
	 */
	public Iterable<K> keys()
	{
		Queue<K> queue = new Queue<K>();
		for(int i = 0; i < M; i++)
		{
			if(keys[i] != null)
				queue.enqueue(keys[i]);
		}
		return (Iterable<K>) queue;
	}

	/**
	 * @return The keys of the table.
	 */
	public K[] hashKeys()
	{ return keys; }

	/**
	 * Function in charge of assigning a unique table index identifier for each key.
	 * @param key The key to hash.
	 * @return Whole number index assigned to the given key. 
	 */
	private int hash(K key)
	{ return (key.hashCode() & 0x7fffffff) % M; }

	/**
	 * Resizes the table to the given capacity. Rehashes all the table's keys.
	 * @param pCap The table's new capacity.
	 */
	private void resize(Integer pCap)
	{
		HashTableLinearProbing<K, V> table = new HashTableLinearProbing<K, V>(pCap);
		for(int i = 0; i < M; i++)
		{
			if(keys[i] != null)
				table.put(keys[i], values[i]);
		}
		keys = table.keys;
		values = table.values;
		M = table.M;
	}

	// Invariant

	/**
	 * Ensures the correctness of the table: all key-value tuples remain unchainged and M >= 2*N.
	 * @return True if the table is correct, False if contrary.
	 */
	private boolean invariant()
	{
		if(M < 2*N)
			return false;
		for(int i = 0; i < M; i++)
		{
			if(keys[i] == null)
				continue;
			else if(get(keys[i]) != values[i])
				return false;
		}
		return true;
	}
}
