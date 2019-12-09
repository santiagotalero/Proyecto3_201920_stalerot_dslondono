package model.data_structures;

import model.data_structures.ArrayList.IteratorArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * basic breadth first search in java
 */
public class BreadthFirstPaths 
{

	Vertex startVertex;
	Vertex goalVertex;

	public BreadthFirstPaths(Vertex start, Vertex goalVertex)
	{
		this.startVertex = start;
		this.goalVertex = goalVertex;
	}

	public boolean compute()
	{

		if(this.startVertex.equals(goalVertex))
		{
			System.out.println("Goal Vertex Found!");
			System.out.println(startVertex);
		}

		Queue<Vertex> queue = new LinkedList<>();
		ArrayList<Vertex> explored = new ArrayList<>();
		queue.add(this.startVertex);
		explored.add(startVertex);

		while(!queue.isEmpty())
		{
			Vertex current = queue.remove();
			if(current.equals(this.goalVertex)) 
			{
				System.out.println(explored);
				return true;
			}
			else
			{
				if(!current.getAdjacentsId().isEmpty()) 
				{ 
					queue.addAll((Collection<? extends Vertex>) current.getAdjacentsId()); 
				}
				else
					queue.addAll((Collection<? extends Vertex>) current.getAdjacentsId());
			}
			explored.add(current);
		}

		return false;

	}

}



