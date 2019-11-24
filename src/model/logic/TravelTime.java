package model.logic;

public class TravelTime implements Comparable<TravelTime>
{
	//Atributos
	private int trimestre;
	
	private int sourceID;
	
	private int dstID;
	
	private int identificador;
	
	private double meanTravelTime;
	
	private double standardDeviationTravelTime;

	
	public TravelTime(int trimestre,int sourceID, int dstID, int hod, double meanTravelTime, double standardDeviationTravelTime) 
	{
		super();
		this.trimestre=trimestre;
		this.sourceID = sourceID;
		this.dstID = dstID;
		this.identificador = hod;
		this.meanTravelTime = meanTravelTime;
		this.standardDeviationTravelTime = standardDeviationTravelTime;

	}

	public int getTrimestre()
	{
		return trimestre;
	}
	public int getSourceID() {
		return sourceID;
	}

	public int getDstID() {
		return dstID;
	}

	public int getIdentificador() {
		return identificador;
	}

	public double getMeanTravelTime() {
		return meanTravelTime;
	}

	public double getStandardDeviationTravelTime() {
		return standardDeviationTravelTime;
	}

	
		public int compareTo( TravelTime viaje) 
	{
		int x = 0; 
		if( meanTravelTime < viaje.meanTravelTime) 
		{
			x= -1;
		}
		else if( meanTravelTime > viaje.meanTravelTime) 
		{
			x=  1;
		}
		
		return x; 
	}
		
		public int compareToo( int integer) 
		{
			int x = 0; 
			if( meanTravelTime < integer) 
			{
				x= -1;
			}
			else if( meanTravelTime > integer) 
			{
				x=  1;
			}
			
			return x; 
		}
	
	
	
	
	
}	
