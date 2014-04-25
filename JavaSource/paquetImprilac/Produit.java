package paquetImprilac;

import java.util.List;

public class Produit {

private int idProduit;
private String nomProduit;
private String titre;
private int idChemin;
private int nbre_exemplaire_prod;
//private boolean maq_presentee=false;
private String maq_presentee;
private int nbre_charge;
//private Produit produit;
private List<Charge> listCharges;
private float montantTotalProd;
private float coutChemin;

private int nbr_ex_dmd;
private int nbr_ex_djaPaye;
private int nbr_ex_paye;
private int nbr_ex_tot_non_paye;
private String icon;

private Charge charge=null;
private boolean showMessageQuantite;
//===

private int num=0;


public String getMaq_presentee() {
	return maq_presentee;
}
public void setMaq_presentee(String maq_presentee) {
	this.maq_presentee = maq_presentee;
}
private float cout_maqt=0;

public float getCout_maqt() {
	return cout_maqt;
}
public void setCout_maqt(float cout_maqt) {
	this.cout_maqt = cout_maqt;
}
public boolean isShowMessageQuantite() {
	return showMessageQuantite;
}
public void setShowMessageQuantite(boolean showMessageQuantite) {
	this.showMessageQuantite = showMessageQuantite;
}
public String getIcon() {
	return icon;
}
public void setIcon(String icon) {
	this.icon = icon;
}
public int getNbr_ex_tot_non_paye() {
	return nbr_ex_tot_non_paye;
}
public void setNbr_ex_tot_non_paye(int nbr_ex_tot_non_paye) {
	this.nbr_ex_tot_non_paye = nbr_ex_tot_non_paye;
}
public int getNbr_ex_dmd() {
	return nbr_ex_dmd;
}
public void setNbr_ex_dmd(int nbr_ex_dmd) {
	this.nbr_ex_dmd = nbr_ex_dmd;
}
public int getNbr_ex_djaPaye() {
	return nbr_ex_djaPaye;
}
public void setNbr_ex_djaPaye(int nbr_ex_djaPaye) {
	this.nbr_ex_djaPaye = nbr_ex_djaPaye;
}
public int getNbr_ex_paye() {
	return nbr_ex_paye;
}
public void setNbr_ex_paye(int nbr_ex_paye) {
	this.nbr_ex_paye = nbr_ex_paye;
}
public String getNomProduit() {
	return nomProduit;
}
public void setNomProduit(String nomProduit) {
	this.nomProduit = nomProduit;
}
public float getCoutChemin() {
	return coutChemin;
}
public void setCoutChemin(float coutChemin) {
	this.coutChemin = coutChemin;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
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
/*public boolean isMaq_presentee() {
	return maq_presentee;
}
public void setMaq_presentee(boolean maq_presentee) {
	this.maq_presentee = maq_presentee;
}*/
public int getNbre_charge() {
	return nbre_charge;
}
public void setNbre_charge(int nbre_charge) {
	this.nbre_charge = nbre_charge;
}
/*public Produit getProduit() {
	return produit;
}
public void setProduit(Produit produit) {
	this.produit = produit;
}*/
public List<Charge> getListCharges() {
	return listCharges;
}
public void setListCharges(List<Charge> listCharges) {
	this.listCharges = listCharges;
}



}
