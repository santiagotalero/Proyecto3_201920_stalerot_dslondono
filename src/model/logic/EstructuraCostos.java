package model.logic;


public class EstructuraCostos <T extends Comparable<T>> implements Comparable<EstructuraCostos<T>> {
	
	private double haversine;
	private double tiempo;
	private double velocidad;
	public EstructuraCostos(double haversine, double tiempo, double velocidad) {
		super();
		this.haversine = haversine;
		this.tiempo = tiempo;
		this.velocidad = velocidad;
	}
	public double getHaversine() {
		return haversine;
	}
	public void setHaversine(double haversine) {
		this.haversine = haversine;
	}
	public double getTiempo() {
		return tiempo;
	}
	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}
	public double getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
	@Override
	public int compareTo(EstructuraCostos<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
