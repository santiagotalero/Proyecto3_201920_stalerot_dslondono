package model.logic.ClasesArchivoJSon;

public class Feature 
{
	private String type;
	private geometry geometry;
	private properties  properties;
	public Feature(String type, geometry geometrias, properties propiedades) {
		super();
		this.type = type;
		this.geometry = geometrias;
		this.properties = propiedades;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public geometry getGeometrias() {
		return geometry;
	}
	public void setGeometrias(geometry geometrias) {
		this.geometry = geometrias;
	}
	public properties getPropiedades() {
		return properties;
	}
	public void setPropiedades(properties propiedades) {
		this.properties = propiedades;
	}
	
	

}
