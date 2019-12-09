package model.logic;

import model.data_structures.ArrayList;

public class VertexInfo implements Comparable<VertexInfo>
{
	// Attributes
	
	public long id;
	
	public Coordinate coordinates;
	
	public ArrayList<Integer> infractionsId;
	
	public VertexInfo(Coordinate pCoord, ArrayList<Integer> pInfractionsId, long pId)
	{
		id = pId;
		coordinates = pCoord;
		infractionsId = pInfractionsId;
	}
	
	// Methods
	public Coordinate getCoordinate()
	{
		return coordinates;
	}
	
	public int compareTo(VertexInfo pVI)
	{ return this.infractionsId.size() - pVI.infractionsId.size(); }
	
	public String toString()
	{
		return "Id " + id + ", " + infractionsId.size() + " infractions, " + coordinates.toString();
	}
}
