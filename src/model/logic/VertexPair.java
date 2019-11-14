package model.logic;

/**
 * Class that represents a pair of vertexes.
 * @author Daniel del Castillo A.
 * @param <K> The key's class type.
 */
public class VertexPair<K extends Comparable<K>> implements Comparable<VertexPair<K>>
{
	// Attributes
	
	/**
	 * The first vertex's id.
	 */
	private K id1;
	
	/**
	 * The second vertex's id.
	 */
	private K id2;
	
	// Constructor
	
	/**
	 * Creates a VertexPair object with the given keys.
	 * @param pId1 The key of the first vertex.
	 * @param pId2 The key of the second vertex.
	 */
	public VertexPair(K pId1, K pId2)
	{
		id1 = pId1;
		id2 = pId2;
	}
	
	// Methods
	
	/**
	 * @return <0 if the vertex pair is greater, >0 if contrary, 0 if equal.
	 */
	public int compareTo(VertexPair<K> pVP)
	{
		if((id1.compareTo(pVP.id2) == 0 && id2.compareTo(pVP.id1) == 0))
			return 0;
		int cmp = id1.compareTo(pVP.id1);
		if(cmp == 0)
			return id2.compareTo(pVP.id2);
		else
			return cmp;
			
	}
	
	@Override
	/**
	 * Modified hashCode method for the arc hashTable implementation.
	 * @return a positive whole integer whithin intMax bounds. 
	 */
	public int hashCode() 
	{
		int res = Math.abs((id1.hashCode() & 0x7fffffff) - (id2.hashCode() & 0x7fffffff));
		int sum = (id1.hashCode() & 0x7fffffff) + (id2.hashCode() & 0x7fffffff);
		return (res + " - " + sum).hashCode();
	}
}
