package paquetImprilac;

import java.util.List;

public class Commande {
	private int idClient;
	private String nif;
	private String societe;
	private int nbreDeProd;
	private List<Produit> listProd;
	private float montantTotal;
	private String bon;
	private int idcmd;
	
	
	
	
	public int getIdcmd() {
		return idcmd;
	}
	public void setIdcmd(int idcmd) {
		this.idcmd = idcmd;
	}
	public String getBon() {
		return bon;
	}
	public void setBon(String bon) {
		this.bon = bon;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public int getNbreDeProd() {
		return nbreDeProd;
	}
	public void setNbreDeProd(int nbreDeProd) {
		this.nbreDeProd = nbreDeProd;
	}
	public List<Produit> getListProd() {
		return listProd;
	}
	public void setListProd(List<Produit> listProd) {
		this.listProd = listProd;
	}
	public float getMontantTotal() {
		return montantTotal;
	}
	public void setMontantTotal(float montantTotal) {
		this.montantTotal = montantTotal;
	}
	
	
}
