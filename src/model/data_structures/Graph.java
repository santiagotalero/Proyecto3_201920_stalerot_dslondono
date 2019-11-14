package model.data_structures;

import java.util.Iterator;

import model.logic.VertexPair;

/**
 * Class that represents a weighted graph.
 * @param <K> The key's class type.
 * @param <V> The value's class type.
 * @param <A> The arc's information class type.
 * <b>Invariant:<\b> V >= 0 && E >= 0 && vertexes != null.
 */
public class Graph<K extends Comparable<K>, V, A extends Comparable<A>>
{
	// Attributes

	/**
	 * The table that stores the vertexes.
	 */
	private HashTableLinearProbing<K, Vertex<K, V, A>> vertexes;
	private int V=vertexes.size();
	
	private HashTableLinearProbing<VertexPair<K>, Arc<K, V, A>> arcs;
	private int E=arcs.size();
	
	// Constructor

	/**
	 * Creates a Graph object.
	 */
	public Graph()
	{
		vertexes = new HashTableLinearProbing<K, Vertex<K, V, A>>();
		arcs = new HashTableLinearProbing<VertexPair<K>, Arc<K, V, A>>();
		// check();
	}
	
	/**
	 * Creates a Graph object.
	 * @param pSize The starting size of the nodes.
	 */
	public Graph(int pSize)
	{
		vertexes = new HashTableLinearProbing<K, Vertex<K, V, A>>(pSize);
		arcs = new HashTableLinearProbing<VertexPair<K>, Arc<K, V, A>>();
		// check();
	}

	/**
	 * Creates a Graph object-
	 * @param pVertex The starting size of the nodes.
	 * @param pArcs The starting size of the arcs.
	 */
	public Graph(int pVertex, int pArcs)
	{
		vertexes = new HashTableLinearProbing<K, Vertex<K, V, A>>(pVertex);
		arcs = new HashTableLinearProbing<VertexPair<K>, Arc<K, V, A>>(pArcs);
		// check();
	}

	// Methods

	/**
	 * @return The number of vertexes. V >= 0.
	 */
	public int V()
	{ return vertexes.size(); }

	/**
	 * @return The number of edges. E >= 0.
	 */
	public int E()
	{ return arcs.size(); }

	/**
	 * Adds a vertex to the graph with the given id and value.
	 * @param idVertex The id of the vertex.
	 * @param infoVertex The value of the vertex.
	 */
	public void addVertex(K idVertex, V infoVertex)
	{ vertexes.put(idVertex, new Vertex<K, V, A>(idVertex, infoVertex)); }

	/**
	 * Adds an edge to the graph between two given vertexes.
	 *<b>Pre:<\b> both vertexes exists in the table.<br>
	 * @param idVertexIni The key of the first vertex.
	 * @param idVertexFin The key of the second vertex.
	 * @param infoArc The information (weight) of the edge.
	 */
	public void addEdge(K idVertexIni, K idVertexFin, A infoArc)
	{
		Vertex<K, V, A> vertex1 = vertexes.get(idVertexIni);
		Vertex<K, V, A> vertex2 = vertexes.get(idVertexFin);
		vertex1.addAdjacent(idVertexFin);
		vertex2.addAdjacent(idVertexIni);
		VertexPair<K> vertexPair = new VertexPair<K>(idVertexIni, idVertexFin);
		Arc<K, V, A> arc = new Arc<K, V, A>(vertex1, vertex2, infoArc);
		arcs.put(vertexPair, arc);
	}

	/**
	 * Gets the value of a given vertex.
	 *<b>Pre:<\b> the vertex exists.<br>
	 * @param idVertex The id of the vertex.
	 * @return The value of the given vertex. 
	 */
	public V getInfoVertex(K idVertex)
	{ return vertexes.get(idVertex).getValue(); }

	/**
	 * Changes the value of a given vertex.
	 *<b>Pre:<\b> the vertex exists.<br>
	 * @param idVertex The id of the vertex.
	 * @param infoVertex The vertex's new value.
	 */
	public void setInfoVertex(K idVertex, V infoVertex)
	{ vertexes.get(idVertex).setValue(infoVertex); }

	/**
	 * Gets the information (weight) of an arc between two vertexes.
	 *<b>Pre:<\b> the vertexes exists.<br>
	 * @param idVertexIni The first vertex.
	 * @param idVertexFin The second vertex.
	 * @return The information of the arc between the two vertexes. null if it doesn't exist.
	 */
	public A getInfoArc(K idVertexIni, K idVertexFin)
	{
		VertexPair<K> vertexPair = new VertexPair<K>(idVertexIni, idVertexFin);
		Arc<K, V, A> arc = arcs.get(vertexPair);
		return arc == null ? null : arc.getInfo();
	}

	/**
	 * Changes the information (weight) of an arc between two vertexes.
	 *<b>Pre:<\b> the vertexes exists.<br>
	 * @param idVertexIni The first vertex.
	 * @param idVertexFin The second vertex.
	 * @param infoArc The new arc's information.
	 */
	public void setInfoArc(K idVertexIni, K idVertexFin, A infoArc)
	{
		VertexPair<K> vertexPair = new VertexPair<K>(idVertexIni, idVertexFin);
		Arc<K, V, A> arc = arcs.get(vertexPair);
		if(arc == null)
			return;
		arc.setInfo(infoArc);
	}

	/**
	 * Gets the adjacent vertexes' ids of the given vertex. 
	 *<b>Pre:<\b> the vertex exists.<br>
	 * @param idVertex The id of the vertex.
	 * @return The iterator of the ids of the vertex's adjacent vertexes' ids.
	 */
	public Iterable<K> adj(K idVertex)
	{ return (Iterable<K>) vertexes.get(idVertex).getAdjacentsId().iterator(); }
	
	// Aditional methods
	
	/**
	 * Deletes the vertices that have no adjacents.
	 */
	public void deleteLonelyVertexes()
	{
		K[] keys = vertexes.hashKeys();
		int M = vertexes.maxSize();
		Vertex<K, V, A> vertex;
		for(int i = 0; i < M; i++)
		{
			if(keys[i] != null)
			{
				vertex = vertexes.get(keys[i]);
				if(vertex.getAdjacentsId().isEmpty())
					vertexes.delete(keys[i]);
			}
		}
	}
	

	// Invariant

	/**
	 * Ensures the correctness of the graph: V >= 0 && E >= 0 && vertexes != null.
	 */
//	private void check()
//	{
//		assert vertexes != null : "The vertexes hash table cannot be null.";
//		assert V >= 0 : "The number of vertexes cannot be negative.";
//		assert E >= 0 : "The number of edges cannot be negative.";
//	}

}