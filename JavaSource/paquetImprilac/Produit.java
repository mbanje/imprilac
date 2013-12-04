package paquetImprilac;

import java.util.List;

public class Produit {
private int idProduit;
private String titre;
private int idChemin;
private int nbre_exemplaire_prod;
private boolean maq_presentee;
private int nbre_charge;
private Produit produit;
private List<Charge> listCharges;
private float montantTotalProd;

private Charge charge=null;



public Charge getCharge() {
	return charge;
}
public void setCharge(Charge charge) {
	this.charge = charge;
}
public float getMontantTotalProd() {
	return montantTotalProd;
}
public void setMontantTotalProd(float montantTotalProd) {
	this.montantTotalProd = montantTotalProd;
}
public int getIdChemin() {
	return idChemin;
}
public void setIdChemin(int idChemin) {
	this.idChemin = idChemin;
}
public int getIdProduit() {
	return idProduit;
}
public void setIdProduit(int idProduit) {
	this.idProduit = idProduit;
}
public String getTitre() {
	return titre;
}
public void setTitre(String titre) {
	this.titre = titre;
}
public int getNbre_exemplaire_prod() {
	return nbre_exemplaire_prod;
}
public void setNbre_exemplaire_prod(int nbre_exemplaire_prod) {
	this.nbre_exemplaire_prod = nbre_exemplaire_prod;
}
public boolean isMaq_presentee() {
	return maq_presentee;
}
public void setMaq_presentee(boolean maq_presentee) {
	this.maq_presentee = maq_presentee;
}
public int getNbre_charge() {
	return nbre_charge;
}
public void setNbre_charge(int nbre_charge) {
	this.nbre_charge = nbre_charge;
}
public Produit getProduit() {
	return produit;
}
public void setProduit(Produit produit) {
	this.produit = produit;
}
public List<Charge> getListCharges() {
	return listCharges;
}
public void setListCharges(List<Charge> listCharges) {
	this.listCharges = listCharges;
}



}
