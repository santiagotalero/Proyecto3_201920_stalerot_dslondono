package model.logic;

public class Interseccion {
	private double longitud;
	private double latitud;
	private int MOVEMENT_ID;
	private int id;
	public Interseccion(double longitud, double latitud, int mOVEMENT_ID, int id) {
		super();
		this.longitud = longitud;
		this.latitud = latitud;
		MOVEMENT_ID = mOVEMENT_ID;
		this.id = id;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public int getMOVEMENT_ID() {
		return MOVEMENT_ID;
	}
	public void setMOVEMENT_ID(int mOVEMENT_ID) {
		MOVEMENT_ID = mOVEMENT_ID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
