package paquetImprilac;

public class CheminOuEtape {
	private int id;
	private int idChemOuEtap;
	private int num;
	private String designation;
	private float prixUni;
	
	private String designationChe;
	private String designationEta;
	
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDesignationChe() {
		return designationChe;
	}
	public void setDesignationChe(String designationChe) {
		this.designationChe = designationChe;
	}
	public String getDesignationEta() {
		return designationEta;
	}
	public void setDesignationEta(String designationEta) {
		this.designationEta = designationEta;
	}
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
