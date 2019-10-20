package model.logic.ClasesArchivoJSon;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class geometry 
{
	private String type;
	private ArrayList coordinates;
	public geometry(String type, ArrayList coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(ArrayList coordinates) {
		this.coordinates = coordinates;
	}
	
	
	
}
