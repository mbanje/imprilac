package paquetImprilac;

public class CheminOuEtape {
	private int id;
	private int idChemOuEtap;
	private String designation;
	private float prixUni;
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getPrixUni() {
		return prixUni;
	}
	public void setPrixUni(float prixUni) {
		this.prixUni = prixUni;
	}
	public int getIdChemOuEtap() {
		return idChemOuEtap;
	}
	public void setIdChemOuEtap(int idChemOuEtap) {
		this.idChemOuEtap = idChemOuEtap;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
}
