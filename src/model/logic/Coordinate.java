package model.logic;

public class Coordinate
{
	// Attributes
	
	double latitude;
	
	double longitude;
	
	// Constructor
	
	public Coordinate(double pLatitude, double pLongitude)
	{
		latitude = pLatitude;
		longitude = pLongitude;
	}
	
	// Methods
	
	public double getLatitude()
	{ return latitude; }
	
	public double getLongitude()
	{ return longitude; }
}
