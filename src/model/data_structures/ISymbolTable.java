package model.data_structures;

/**
 * Interface that represents the SymbolTable's API.
 * @author Daniel del Castillo A.
 * @param <K> The key's class type.
 * @param <V> The value's class type.
 */
public interface ISymbolTable<K, V>
{
	// Methods

	/**
	 * Puts the key-value tuple in the table. Removes the key from the table if the value is null.
	 * Updates the value if the key already exists.
	 * @param key The key of the tuple. 
	 * @param val The value of the tuple.
	 */
	public void put(K key, V val);

	/**
	 * Returns the given key's assigned value.
	 * @param key The key whose value is desired.
	 * @return The value of the given key if it exists.
	 */
	public V get(K key);

	/**
	 * Removes the key-value tuple from the table.
	 * @param key The key of tuple to remove.
	 */
	public void delete(K key);

	/**
	 * Checks if a given key is stored in the table.
	 * @param key The key to check.
	 * @return True if there's an assigned value to the give key, False if contrary.
	 */
	public boolean contains(K key);

	/**
	 * @return True if the table is empty, False if contrary.
	 */
	public boolean isEmpty();

	/**
	 * @return Number of key-value tuples in the table. N >= 0. 
	 */
	public int size();

	/**
	 * @return An Iterable object with the table's keys. Iterable != null. 
	 */
	public Iterable<K> keys();
}
