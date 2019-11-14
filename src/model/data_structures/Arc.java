package model.data_structures;

/**
 * Class that represents a weighted arc between two vertexes.
 * @param <K> The vertex's key type. Must be comparable.
 * @param <V> The vertex's value type. 
 * @param <A> The arc's information type.
 * <b>Invariant:<\b> start != null && finish != null && info != null.
 */
public class Arc<K extends Comparable<K>, V, A extends Comparable<A>> implements Comparable<Arc<K, V, A>>
{
	// Attributes

	/**
	 * The vertex located at the finishing end of the arc.
	 */
	private Vertex<K, V, A> finish;

	/**
	 * The vertex located at the starting end of the arc.
	 */
	private Vertex<K, V, A> start;

	/**
	 * The information (weight) of the arc.
	 */
	private A info;

	// Constructor

	/**
	 * Creates an arc object. 
	 * @param pStart The vertex located at the starting end of the arc.
	 * @param pFinish The vertex located at the finishing end of the arc.
	 * @param pInfo Information (weight) of the arc.
	 */
	public Arc(Vertex<K, V, A> pStart, Vertex<K, V, A> pFinish, A pInfo)
	{
		finish = pFinish;
		start = pStart;
		info = pInfo;
		// check();
	}

	// Methods

	/**
	 * Changes the finishing end of the arc by the given vertex.
	 * @param pFinish The vertex that will become the finishing end.
	 */
	public void setFinish(Vertex<K, V, A> pFinish)
	{ finish = pFinish; } // check(); }

	/**
	 * Changes the starting end of the arc by the given vertex.
	 * @param pStart The vertex that will become the starting end.
	 */
	public void setStart(Vertex<K, V, A> pStart)
	{ start = pStart; } // check(); }

	/**
	 * Changes the information (weight) of the arc by the given element.
	 * @param pInfo The arc's new information.
	 */
	public void setInfo(A pInfo)
	{ info = pInfo; } // check(); }

	/**
	 * @return The finishing end of the arc. finish != null.
	 */
	public Vertex<K, V, A> getFinish()
	{ return finish; }

	/**
	 * @return The starting end of the arc. start != null.
	 */
	public Vertex<K, V, A> getStart()
	{ return start; }

	/**
	 * @return The arc's information. info != null.
	 */
	public A getInfo()
	{ return info; }

	/**
	 * @return The arc's other vertex than the one given, null if it doesn't exist. 
	 */
	public Vertex<K, V, A> getDifferentThan(Vertex<K, V, A> pId)
	{ return start.compareTo(pId) == 0 ? finish : (finish.compareTo(pId) == 0 ? start : null); }

	@Override
	/**
	 * Compars two arcs by their information (weight). Uses natural comparison of the class A.
	 * @return < 0 if the given arc is greater, > 0 if contrary, 0 if they're equal.
	 */
	public int compareTo(Arc<K, V, A> pArc)
	{ return this.info.compareTo(pArc.info); }

	/**
	 * @return The String representation of the arc.
	 */
	public String toString()
	{ return "Start: " + start.toString() + "; Finish: " + finish.toString() + "; Info: " + info; }

	// Invariant

	/**
	 * Ensures the correctness of the arc object: finish != null && start != null && info != null.
	 */
	//	private void check()
	//	{
	//		assert finish != null : "The finishing end of the arc cannot be null.";
	//		assert start != null : "The starting end of the arc cannot be null.";
	//		assert info != null : "The arc's information cannot be null.";
	//	}
}
