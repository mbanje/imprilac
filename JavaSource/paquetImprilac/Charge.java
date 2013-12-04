package paquetImprilac;

public class Charge {
	private int idCharge;
	private float quantiteCharge;
	private float prixUni;
	private float prixTotCharPr1Prod;
	private String designation;
	private Charge charge=null;
	
	
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public float getPrixTotCharPr1Prod() {
		return prixTotCharPr1Prod;
	}
	public void setPrixTotCharPr1Prod(float prixTotCharPr1Prod) {
		this.prixTotCharPr1Prod = prixTotCharPr1Prod;
	}
	public float getPrixUni() {
		return prixUni;
	}
	public void setPrixUni(float prixUni) {
		this.prixUni = prixUni;
	}
	public int getIdCharge() {
		return idCharge;
	}
	public void setIdCharge(int idCharge) {
		this.idCharge = idCharge;
	}
	public float getQuantiteCharge() {
		return quantiteCharge;
	}
	public void setQuantiteCharge(float quantiteCharge) {
		this.quantiteCharge = quantiteCharge;
	}
	public Charge getCharge() {
		return charge;
	}
	public void setCharge(Charge charge) {
		this.charge = charge;
	}
	
}
