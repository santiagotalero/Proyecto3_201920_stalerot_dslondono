package model.logic.ClasesArchivoJSon;

public class properties 
{
	private int cartodb_id;
	private String scacodigo;
	private int scatipo;
	private String scanombre;
	private double shape_leng;
	private double shape_area;
	private String MOVEMENT_ID;
	private String DISPLAY_NAME;
	public properties(int cartodb_id, String scacodigo, int scatipo, String scanombre, double shape_leng,
			double shape_area, String mOVEMENT_ID, String dISPLAY_NAME) {
		super();
		this.cartodb_id = cartodb_id;
		this.scacodigo = scacodigo;
		this.scatipo = scatipo;
		this.scanombre = scanombre;
		this.shape_leng = shape_leng;
		this.shape_area = shape_area;
		MOVEMENT_ID = mOVEMENT_ID;
		DISPLAY_NAME = dISPLAY_NAME;
	}
	public int getCartodb_id() {
		return cartodb_id;
	}
	public void setCartodb_id(int cartodb_id) {
		this.cartodb_id = cartodb_id;
	}
	public String getScacodigo() {
		return scacodigo;
	}
	public void setScacodigo(String scacodigo) {
		this.scacodigo = scacodigo;
	}
	public int getScatipo() {
		return scatipo;
	}
	public void setScatipo(int scatipo) {
		this.scatipo = scatipo;
	}
	public String getScanombre() {
		return scanombre;
	}
	public void setScanombre(String scanombre) {
		this.scanombre = scanombre;
	}
	public double getShape_leng() {
		return shape_leng;
	}
	public void setShape_leng(double shape_leng) {
		this.shape_leng = shape_leng;
	}
	public double getShape_area() {
		return shape_area;
	}
	public void setShape_area(double shape_area) {
		this.shape_area = shape_area;
	}
	public String getMOVEMENT_ID() {
		return MOVEMENT_ID;
	}
	public void setMOVEMENT_ID(String mOVEMENT_ID) {
		MOVEMENT_ID = mOVEMENT_ID;
	}
	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}
	public void setDISPLAY_NAME(String dISPLAY_NAME) {
		DISPLAY_NAME = dISPLAY_NAME;
	}
	
	
	
}
