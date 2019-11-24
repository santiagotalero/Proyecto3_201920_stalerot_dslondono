package model.logic.JSon;

public class ArcoJSon {
	
	private double Haversine;
	private int origen;
	private int destino;
	public ArcoJSon(double haversine, int origen, int destino) {
		super();
		Haversine = haversine;
		this.origen = origen;
		this.destino = destino;
	}
	public double getHaversine() {
		return Haversine;
	}
	public void setHaversine(double haversine) {
		Haversine = haversine;
	}
	public int getOrigen() {
		return origen;
	}
	public void setOrigen(int origen) {
		this.origen = origen;
	}
	public int getDestino() {
		return destino;
	}
	public void setDestino(int destino) {
		this.destino = destino;
	}
	

}
