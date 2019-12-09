package model.logic;

import model.data_structures.Queue;

public class ShortestPathNode
{

	//-----------------------------------------------
	// Atributos
	//-----------------------------------------------
	
	private int cost;
	
	private Queue<Long> path;
	

	//-----------------------------------------------
	// Métodos
	//-----------------------------------------------
	
	public ShortestPathNode(int pCost , Queue<Long> pPath)
	{
		cost= pCost;
		path = pPath;
	}
	
	
	public void changePath(Queue<Long> pPath) 
	{
		path = pPath;
	}
	public void changeCost(int pCost) 
	{
		cost= pCost;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public Queue<Long> getPath()
	{
		return path;
	}
	
	
	
}
