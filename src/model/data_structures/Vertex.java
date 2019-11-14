package model.data_structures;

/**
 * Class that represents a graph vertex.
 * @author Daniel del Castillo A.
 * @param <K> The vertex's key type. Must be comparable.
 * @param <V> The vertex's value type. 
 * @param <A> The arc's information type.
 * <b>Invariant:<\b> adjacents != null && value != null && id != null.
 */
public class Vertex<K extends Comparable<K>, V, A extends Comparable<A>> implements Comparable<Vertex<K, V, A>>
{
	// Attributes

	/**
	 * Array with the adjacent vertexes id.
	 */
	private ArrayList<K> adjacentsId;

	/**
	 * The vertex's value.
	 */
	private V value;

	/**
	 * The vertex's identificacion.
	 */
	private K id;

	// Constructor

	/**
	 * Creates a Vertex object. Bag of adjacent vertexes' ids initialized as empty.
	 * @param pValue Value to store in the vertex.
	 * @param pId Identification of the vertex.
	 */
	public Vertex(K pId, V pValue)
	{
		adjacentsId = new ArrayList<K>(11);
		value = pValue;
		id = pId;
		//check();
	}

	// Methods

	/**
	 * Changes vertex's value by the value given.
	 * @param pValue The vertex's new value.
	 */
	public void setValue(V pValue)
	{ value = pValue; } //check(); }

	/**
	 * Changes the vertex's id by the id given.
	 * @param pId The vertex's new id.
	 */
	public void setId(K pId)
	{ id = pId; } //check(); }

	/**
	 * @return The vertex's bag with adjacent vertexes' ids. adjacentsId != null.
	 */
	public ArrayList<K> getAdjacentsId()
	{ return adjacentsId; } 

	/**
	 * @return The vertex's value. value != null.
	 */
	public V getValue()
	{ return value; }

	/**
	 * @return The vertex's id. id != null.
	 */
	public K getId()
	{ return id; }

	/**
	 * Adds the id of an adjacent vertex to the adjacentsId array.
	 * @param pId The adjacent vertex's id.
	 */
	public void addAdjacent(K pId)
	{ adjacentsId.add(pId); }

	/**
	 * Compares two vertices by their id. Uses the natural comparison of the class K.
	 * @return < 0 if the given vertex is greater, > 0 if contrary, 0 if they're equal.
	 */
	public int compareTo(Vertex<K, V, A> pVertex)
	{ return this.id.compareTo(pVertex.id); }

	/**
	 * @return The String representation of the vertex.
	 */
	public String toString()
	{ return "Id: " + id + " - Value: " + value; }

	// Invariant

	/**
	 * Ensures the correctness of the vertex object: id != null && value != null.
	 */
	//	private void check()
	//	{
	//		assert adjacents != null : "The adjacents bag cannot be null.";
	//		assert value != null : "The vertex's value cannot be null.";
	//		assert id != null : "The vertex's id cannot be null.";
	//	}
}
