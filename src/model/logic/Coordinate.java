package model.logic;

public class Coordinate
{
	// Attributes
	
	double latitude;
	
	double longitude;
	
	int MOVEMENT_ID;
	
	// Constructor
	
	public Coordinate(double pLatitude, double pLongitude, int MID)
	{
		latitude = pLatitude;
		longitude = pLongitude;
		MOVEMENT_ID=MID;
	}
	
	// Methods
	
	public double getLatitude()
	{ return latitude; }
	
	public double getLongitude()
	{ return longitude; }
	
	public int getMOVEMENT_ID()
	{return MOVEMENT_ID;
	
	}
}
