package model.data_structures;

import java.util.Iterator;

/**
 * Interface that represents the Graph's API.
 * @param <K> The key's class type.
 * @param <V> The value's class type.
 * @param <A> The arc's information class type.
 */
public interface IGraph<K, V, A> 
{
	/**
	 * @return The number of vertexes. V >= 0.
	 */
	public int V();

	/**
	 * @return The number of edges. E >= 0.
	 */
	public int E();

	/**
	 * Adds a vertex to the graph with the given id and value.
	 * @param idVertex The id of the vertex.
	 * @param infoVertex The value of the vertex.
	 */
	public void addVertex(K idVertex, V infoVertex);

	/**
	 * Adds an edge to the graph between two given vertexes.
	 *<b>Pre:<\b> both vertexes exists in the table.<br>
	 * @param idVertexIni The key of the first vertex.
	 * @param idVertexFin The key of the second vertex.
	 * @param infoArc The information (weight) of the edge.
	 */
	public void addEdge(K idVertexIni, K idVertexFin, A infoArc);

	/**
	 * Gets the value of a given vertex.
	 *<b>Pre:<\b> the vertex exists.<br>
	 * @param idVertex The id of the vertex.
	 * @return The value of the given vertex. 
	 */
	public V getInfoVertex(K idVertex);

	/**
	 * Changes the value of a given vertex.
	 *<b>Pre:<\b> the vertex exists.<br>
	 * @param idVertex The id of the vertex.
	 * @param infoVertex The vertex's new value.
	 */
	public void setInfoVertex(K idVertex, V infoVertex);

	/**
	 * Gets the information (weight) of an arc between two vertexes.
	 *<b>Pre:<\b> the vertexes exists.<br>
	 * @param idVertexIni The first vertex.
	 * @param idVertexFin The second vertex.
	 * @return The information of the arc between the two vertexes. null if it doesn't exist.
	 */
	public A getInfoArc(K idVertexIni, K idVertexFin);

	/**
	 * Changes the information (weight) of an arc between two vertexes.
	 *<b>Pre:<\b> the vertexes exists.<br>
	 * @param idVertexIni The first vertex.
	 * @param idVertexFin The second vertex.
	 * @param infoArc The new arc's information.
	 */
	public void setInfoArc(K idVertexIni, K idVertexFin, A infoArc);

	/**
	 * Gets the adjacent vertexes' ids of the given vertex. 
	 *<b>Pre:<\b> the vertex exists.<br>
	 * @param idVertex The id of the vertex.
	 * @return The iterator of the ids of the vertex's adjacent vertexes' ids.
	 */
	public Iterator<K> adj(K idVertex);
}
