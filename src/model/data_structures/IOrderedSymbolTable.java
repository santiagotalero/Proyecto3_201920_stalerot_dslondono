package model.data_structures;


public interface IOrderedSymbolTable<Key extends Comparable<Key>, Value> 
{
	// Methods
	
	public void put(Key key, Value val);
	
	public Value get(Key key);
	
	public void delete(Key key);
	
	public boolean contains(Key key);
	
	public boolean isEmpty();
	
	public int size();
	
	public Key min();
	
	public Key max();
	
	public Key floor(Key key);
	
	public Key ceiling(Key key);
	
	public int rank(Key key);
	
	public Key select(int k);
	
	public void deleteMin();
	
	public void deleteMax();
	
	public int size(Key lo, Key hi);
	
	public Iterable<Key> keys();
	
	public Iterable<Key> keysInRange(Key lo, Key hi);
}
