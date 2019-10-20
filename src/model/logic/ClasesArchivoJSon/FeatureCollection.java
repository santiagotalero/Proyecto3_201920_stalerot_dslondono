package model.logic.ClasesArchivoJSon;

public class FeatureCollection 
{
	private String type;
	private Feature[] features;
	
	public FeatureCollection(String type, Feature[] features) {
		super();
		this.type = type;
		this.features = features;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Feature[] getFeatures() {
		return features;
	}

	public void setFeatures(Feature[] features) {
		this.features = features;
	}
	

	
}
