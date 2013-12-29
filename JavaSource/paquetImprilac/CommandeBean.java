package paquetImprilac;

import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CommandeBean {
	
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private int idPersonneConnecte;

	public int getIdPersonneConnecte() {
		return idPersonneConnecte;
	}

	public void setIdPersonneConnecte(int idPersonneConnecte) {
		this.idPersonneConnecte = idPersonneConnecte;
	}
	
	private int idChemin;
	
	public int getIdChemin() {
		return idChemin;
	}

	public void setIdChemin(int idChemin) {
		this.idChemin = idChemin;
	}

	private int idEtape;
	
	public int getIdEtape() {
		return idEtape;
	}

	public void setIdEtape(int idEtape) {
		this.idEtape = idEtape;
	}

	//-------------------------------------------------------------------------------	
	private List<SelectItem> listDesChemin;

	public List<SelectItem> getListDesChemin() {
		
		ResultSet res=null;
		
		if(listDesChemin==null)
			listDesChemin=new ArrayList<SelectItem>();
		else
			listDesChemin.clear();
		
		listDesChemin.add(new SelectItem(0,""));
		res=Connecteur.Extrairedonnees("select * from chemin");

		try {
			while(res.next())
			{listDesChemin.add(new SelectItem(res.getInt("Idchemin"),res.getString("Designation")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listDesChemin;
	}

	public void setListDesChemin(List<SelectItem> listDesChemin) {
		this.listDesChemin = listDesChemin;
	}
	
//--------------------------------------------------------------------------------
	private List<SelectItem> listDesEtapes;

	public List<SelectItem> getListDesEtapes() {
		
		ResultSet res=null;
		
		if(listDesEtapes==null)
			listDesEtapes=new ArrayList<SelectItem>();
		else
			listDesEtapes.clear();
		
		listDesEtapes.add(new SelectItem(0,""));
		res=Connecteur.Extrairedonnees("select * from etapes");

		try {
			while(res.next())
			{listDesEtapes.add(new SelectItem(res.getInt("Idetape"),res.getString("Designation")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listDesEtapes;
	}

	public void setListDesEtapes(List<SelectItem> listDesEtapes) {
		this.listDesEtapes = listDesEtapes;
	}
//-------------------------------------------------------------------------------
private List<SelectItem> listDesEtapesNonSurCheminDonn;

public List<SelectItem> getListDesEtapesNonSurCheminDonn() {
	
	ResultSet res=null;
	
	if(listDesEtapesNonSurCheminDonn==null)
		listDesEtapesNonSurCheminDonn=new ArrayList<SelectItem>();
	else
		listDesEtapesNonSurCheminDonn.clear();
	
	listDesEtapesNonSurCheminDonn.add(new SelectItem(0,""));
	//ON SELECTIONNE LES ETAPES NON ENCORE SUR LE CHEMIN DONNE
	res=Connecteur.Extrairedonnees("select * from etapes as eta where eta.Idetape not in (select che_eta.Idetape from chemin_etapes as che_eta where che_eta.idchemin="+this.idChemin+")");

	try {
		while(res.next())
		{listDesEtapesNonSurCheminDonn.add(new SelectItem(res.getInt("Idetape"),res.getString("Designation")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesEtapesNonSurCheminDonn;
}

public void setListDesEtapesNonSurCheminDonn(
		List<SelectItem> listDesEtapesNonSurCheminDonn) {
	this.listDesEtapesNonSurCheminDonn = listDesEtapesNonSurCheminDonn;
}

	//-------------------------------------------------------------------------------
	public void mettreEtapeSurChemin()
	{int n=-1;
	ResultSet res=null;
		if(this.idChemin==0)
	{
		message="SELECTIONNER LE CHEMIN S'IL VOUS PLAIT!!";
		return;
	}
		if(this.idEtape==0)
	{
		message="SELECTIONNER UNE ETAPE S'IL VOUS PLAIT!!";
		return;
	}
	//ON VERIFIE SI ON AVAIT DEJA FAIT CETTE COMBINAISON
	//BIEN QUE CA NE PEUT PAS ARRIVER
	//QUESTION DE PLUS DE SECURITE
	res=Connecteur.Extrairedonnees("select * from chemin_etapes where idchemin="+this.idChemin+" and Idetape="+this.idEtape+"");
	try {
		if(res.next())
		{
			message="CETTE ETAPE EXISTE DEJA SUR CE CHEMIN!!";
			return;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	n=Connecteur.Insererdonnees("insert into chemin_etapes (Idetape,idchemin) values ("+this.idEtape+","+this.idChemin+")");
	if(n==-1)
		message="ECHEC";
	else
		message="OPERATION REUSSIE!!";
	}
	
//-------------------------------------------------------------------------------
private String titre="LA SELECTION D'UN CHEMIN VISUALISE SES ETAPES";


public String getTitre() {
	return titre;
}

public void setTitre(String titre) {
	this.titre = titre;
}

private List<CheminOuEtape> listCheEta;
public List<CheminOuEtape> getListCheEta() {
	
	ResultSet res=null;
	
	if(listCheEta==null)
		listCheEta=new ArrayList<CheminOuEtape>();
	else
		listCheEta.clear();
	
	
	
	res=Connecteur.Extrairedonnees("select c.Designation,e.Designation from chemin as c,chemin_etapes as c_e,etapes as e where e.Idetape=c_e.Idetape and c_e.idchemin=c.Idchemin and c.Idchemin="+this.idChemin+"");
	int no=0,num=1;
	try {
		while(res.next())
		{CheminOuEtape p=new CheminOuEtape();
		p.setDesignationChe(res.getString("c.Designation"));
		p.setDesignationEta(res.getString("e.Designation"));
		p.setNum(num);
		listCheEta.add(p);
		if(no==0)
			titre="LISTE DES ETAPES SE TROUVANT SUR LE CHEMIN : '"+res.getString("c.Designation").toUpperCase()+"'";
		
		no++;
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(no==0)
		titre="LA SELECTION D'UN CHEMIN VISUALISE SES ETAPES";
	
	
	return listCheEta;
}

public void setListCheEta(List<CheminOuEtape> listCheEta) {
	this.listCheEta = listCheEta;
}

private List<SelectItem> listDesClients;
public List<SelectItem> getListDesClients() {
	
	ResultSet res=null;
	
	if(listDesClients==null)
		listDesClients=new ArrayList<SelectItem>();
	else
		listDesClients.clear();
	
	listDesClients.add(new SelectItem(0,""));
	
	res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient");

	try {
		while(res.next())
		{listDesClients.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+" "+res.getString("Prenompersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesClients;
}

public void setListDesClients(List<SelectItem> listDesClients) {
	this.listDesClients = listDesClients;
}

private int idClient;
public int getIdClient() {
	return idClient;
}

public void setIdClient(int idClient) {
	this.idClient = idClient;
}

private String nif=null;
private String societe=null;
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
Commande com=null;
public Commande getCom() {
	return com;
}

public void setCom(Commande com) {
	this.com = com;
}

private boolean activenif=false;
private boolean activesociete=false;
private boolean desactiveNomClient=false;



public boolean isDesactiveNomClient() {
	return desactiveNomClient;
}

public void setDesactiveNomClient(boolean desactiveNomClient) {
	this.desactiveNomClient = desactiveNomClient;
}

public boolean isActivenif() {
	return activenif;
}

public void setActivenif(boolean activenif) {
	this.activenif = activenif;
}

public boolean isActivesociete() {
	return activesociete;
}

public void setActivesociete(boolean activesociete) {
	this.activesociete = activesociete;
}

public void instancierCmd()
{System.out.println("!!!!!!");
if(this.idClient==0)
{message="SELECTIONNER LE CLIENT A QUI APPARTIENDRA LA COMMANDE S'IL VOUS PLAIT!!";
System.out.println("???????");
return;
	}



if((this.activenif==true)||(this.activesociete==true)||(this.showMessageCmd==true))
{message="VOUS VOULER CREER UNE AUTRE COMMANDE ALORS QU'IL Y A UNE AUTRE EN COURT!";
return;
}


this.com=new Commande();//INSTANCIATION D'UNE COMMANDE
//DEBUT INITIALISATION
com.setIdClient(this.idClient);
if(this.nif!=null)
	com.setNif(this.nif);
if(this.societe!=null)
	com.setSociete(this.societe);
com.setListProd(new ArrayList<Produit>());
com.setMontantTotal(0);
//FIN INITIALISATION
if(this.com!=null)
{
message="IL FAUT ENSUITE METTRE LES PRODUITS SUR CETTE COMMANDE!!";
//ON DESACTIVE LE NOM DU CLIENT,LE NIF ET LA SOCIETE, SIGNE QU'IL Y A UNE INSTANCE D'UNE COMMANDE
this.activenif=true;
this.activesociete=true;
this.desactiveNomClient=true;
this.showMessageCmd=true;

//ON ACTIVE LES CHAMPS D'AJOUT DES PRODUITS SUR UNE COMMANDE
this.desactiveChemin=false;
this.desactiveMaqPres=false;
this.desactiveNbreEx=false;
this.desactiveProd=false;
this.desactiveTitre=false;
//this.desactiveListProSurDmd=false;
}
else
message="CRETION DE LA DEMANDE ECHOUEE!!";
	}

//=================================PRODUIT===========================

private List<SelectItem> listDesProd;
public List<SelectItem> getListDesProd() {
	
	ResultSet res=null;
	
	if(listDesProd==null)
		listDesProd=new ArrayList<SelectItem>();
	else
		listDesProd.clear();
	
	listDesProd.add(new SelectItem(0,""));
	//ON SELECTIONNE LES ETAPES NON ENCORE SUR LE CHEMIN DONNE
	res=Connecteur.Extrairedonnees("select * from produits");

	try {
		while(res.next())
		{listDesProd.add(new SelectItem(res.getInt("Idprod"),res.getString("Type")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return listDesProd;
}

public void supprimerCmd()
{if((this.com==null)&&(this.showMessageCmd==false))
{message="IL N'Y A PAS DE COMMANDE A SUPPRIMER!!";
return;
	}
this.com=null;
this.activenif=false;
this.activesociete=false;
this.desactiveNomClient=false;
this.showMessageCmd=false;
this.idClient=0;

this.desactiveChemin=true;
this.desactiveMaqPres=true;
this.desactiveNbreEx=true;
this.desactiveProd=true;
this.desactiveTitre=true;
this.desactiveListCharges=true;
this.desactiveQuantiteCharges=true;

this.desactiveListProSurDmd=true;

this.montantPourLeProdSelectionne=0;
this.montantTotalDeLaCmd=0;

message="SUPPRESSION REUSSIE!!";
	}

public void setListDesProd(List<SelectItem> listDesProd) {
	this.listDesProd = listDesProd;
}

private int idProd;
public int getIdProd() {
	return idProd;
}

public void setIdProd(int idProd) {
	this.idProd = idProd;
}
private String titreProd=null;
public String getTitreProd() {
	return titreProd;
}

public void setTitreProd(String titreProd) {
	this.titreProd = titreProd;
}
private int nbrExemplaires;
public int getNbrExemplaires() {
	return nbrExemplaires;
}

public void setNbrExemplaires(int nbrExemplaires) {
	this.nbrExemplaires = nbrExemplaires;
}
private String maqPresente;
public String getMaqPresente() {
	return maqPresente;
}

public void setMaqPresente(String maqPresente) {
	this.maqPresente = maqPresente;
}
//========================================================================
public void ajouterProdSurCmd()
{
System.out.println("000");
if(this.com==null)
{message="VOUS N'AVEZ PAS CREE UNE COMMANDE";
return;
	}
System.out.println("111");
if(this.desactiveProd==true)
{message="CREER D'ABORD LA COMMANDE S'IL VOUS PLAIT!!";
return;
}
System.out.println("222");
if((this.idClient==0)||(this.com==null))
	{message="CREER D'ABORD LA COMMANDE S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("444");
if(this.idProd==0)
	{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
	System.out.println("aaabbb");
	return;
	}
System.out.println("555");
System.out.println("bbb");
if(this.titreProd.length()==0)
	{message="DONNER UN TITRE A CE PRODUIT S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("666");
if(this.nbrExemplaires==0)
	{message="SAISISSER LE NOMBRE D'EXEMPLAIRES COMMANDES S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("777");
if(this.maqPresente.length()==0)
	{message="INDIQUER SI LA MAQUETTE EST PRESENTE OU NON S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("this.idChemin"+this.idChemin);
System.out.println("888");
if(this.idChemin==0)
	{message="INDIQUER LE CHEMIN QUE VA PRENDRE LE PRODUIT S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("999");

//============FIN DE CONTROLE DES CHAMPS DE SAISI==================
System.out.println("aaa");
Produit p=new Produit();
ResultSet res=null;
System.out.println("bbb");
//ON INITIALISE UN PRODUIT AVANT DE L'AJOUTER SUR LA LISTE
p.setIdProduit(this.idProd);
p.setTitre(this.titreProd);
p.setNbre_exemplaire_prod(this.nbrExemplaires);
System.out.println("ccc");
if(this.maqPresente=="OUI")
	p.setMaq_presentee(true);
else
	p.setMaq_presentee(false);
p.setIdChemin(this.idChemin);

System.out.println("ddd");
res=Connecteur.Extrairedonnees("select * from chemin where Idchemin="+this.idChemin+"");
try {
	if(res.next())
	{
p.setMontantTotalProd(res.getFloat("cout"));
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

System.out.println("eee");
//ON VERIFIE D'ABORD S'IL N'Y AURAIT PAS DEJA SUR CETTE COMMANDE UN PRODUIT
//DU MEME NOM ET DU MEME TITRE.

int j=0;
if(this.com.getListProd()!=null)//LA LISTE DES PRODUITS EXISTE
{
	if(this.com.getListProd().size()>0)//IL Y A DES PRODUITS SUR LA LISTE DES PRODUITS
	{
		/*for(j=0;j<this.com.getListProd().size();j++)
		{if((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre()==this.titreProd))
				{message="DONNER UN AUTRE TITRE A CE PRODUIT S'IL VOUS PLAIT!!";
				break;
				}
		
		}*/
		System.out.println("DEBUT AFFICHAGE, J'AJOUTE UN PRODUIT SUR UNE COMMANDE");
		System.out.println("DEBUT AFFICHAGE, J'AJOUTE UN PRODUIT SUR UNE COMMANDE");
		
		System.out.println("this.com.getListProd().get(j).getIdProduit() "+this.com.getListProd().get(j).getIdProduit());
		System.out.println("this.idProd "+this.idProd);
		
		System.out.println("this.com.getListProd().get(j).getTitre()==this.titreProd "+this.com.getListProd().get(j).getTitre()==this.titreProd);
		System.out.println("this.com.getListProd().get(j).getTitre().equalsIgnoreCase(this.titreProd) "+this.com.getListProd().get(j).getTitre().equalsIgnoreCase(this.titreProd));
		
		
		System.out.println("FIN AFFICHAGE, J'AJOUTE UN PRODUIT SUR UNE COMMANDE");
		System.out.println("FIN AFFICHAGE, J'AJOUTE UN PRODUIT SUR UNE COMMANDE");
		//while((j<this.com.getListProd().size())&&!((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre()==this.titreProd)))
		//while((j<this.com.getListProd().size())&&!((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre().equalsIgnoreCase(this.titreProd))))		
		while((j<this.com.getListProd().size())&&!(this.com.getListProd().get(j).getTitre().equalsIgnoreCase(this.titreProd)))
		{System.out.println("''''''''");
		System.out.println(" sans !"+((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre()==this.titreProd)));
		System.out.println(" avec !"+!((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre()==this.titreProd)));
		
		System.out.println("this.com.getListProd().get(j).getIdProduit() "+this.com.getListProd().get(j).getIdProduit());
		System.out.println("this.idProd "+this.idProd);
		System.out.println("this.com.getListProd().get(j).getTitre() "+this.com.getListProd().get(j).getTitre());
		System.out.println("this.titreProd "+this.titreProd);
		System.out.println("j "+j);
			j++;
		System.out.println("j "+j);
		}
		
		if(j<this.com.getListProd().size())
		{
			message="DONNER UN AUTRE TITRE A CE PRODUIT!!";
			return;
		}
	}

 }
else//LA LISTE DES PRODUITS N'EXISTE PAS
{this.com.setListProd(new ArrayList<Produit>());
System.out.println("fff");
}
System.out.println("this.com.getListProd().size() AVANT AJOUT DU PRODUIT SUR UNE COMMANDE "+this.com.getListProd().size());
this.com.getListProd().add(p);
System.out.println("this.com.getListProd().size() APRES AJOUT DU PRODUIT SUR UNE COMMANDE "+this.com.getListProd().size());

message="METTEZ ENSUITE LES CHARGES SUR CE PRODUIT!!";

//ON ACTIVE LES CHAMPS POUR L'AJOUT DES CHARGES SUR UN PRODUIT
this.desactiveListCharges=false;
this.desactiveQuantiteCharges=false;
this.desactiveListProSurDmd=false;

//ON DESACTIVE LES CHAMPS POUR L'AJOUT DES PRODUITS SUR UNE COMMANDE
this.desactiveProd=true;
this.desactiveTitre=true;
this.desactiveNbreEx=true;
this.desactiveChemin=true;
this.desactiveMaqPres=true;

System.out.println("***************FIN1 DE ajouterProdSurCmd()*********");
calculMontantCmd();
System.out.println("***************FIN2 DE ajouterProdSurCmd()*********");
	}

private List<SelectItem> listDeroulanteDesProd;

public List<SelectItem> getListDeroulanteDesProd() {
	

	if(this.listDeroulanteDesProd==null)
		this.listDeroulanteDesProd=new ArrayList<SelectItem>();
	else
		this.listDeroulanteDesProd.clear();
	
	
	if((this.com==null)||(this.com.getListProd()==null))
	{	this.listDeroulanteDesProd=new ArrayList<SelectItem>();
		this.listDeroulanteDesProd.add(new SelectItem(0,""));
	}
	else
	{int j=0;
		
		//listDeroulanteDesProd.add(new SelectItem(0,""));
		listDeroulanteDesProd.add(new SelectItem(""));
		for(j=0;j<this.com.getListProd().size();j++)
		{
		//this.listDeroulanteDesProd.add(new SelectItem(this.com.getListProd().get(j).getIdProduit(),""+this.com.getListProd().get(j).getTitre()+""));
		this.listDeroulanteDesProd.add(new SelectItem(""+this.com.getListProd().get(j).getTitre()+""));
		
		}
		
	}
	
	return listDeroulanteDesProd;
}

public void setListDeroulanteDesProd(List<SelectItem> listDeroulanteDesProd) {
	this.listDeroulanteDesProd = listDeroulanteDesProd;
}

private int idProd2;
public int getIdProd2() {
	return idProd2;
}

public void setIdProd2(int idProd2) {
	this.idProd2 = idProd2;
}
private boolean showMessageCmd=false;
public boolean isShowMessageCmd() {
	return showMessageCmd;
}

public void setShowMessageCmd(boolean showMessageCmd) {
	this.showMessageCmd = showMessageCmd;
}

//=============================================
private boolean desactiveProd=true;
private boolean desactiveTitre=true;
private boolean desactiveNbreEx=true;
private boolean desactiveChemin=true;
private boolean desactiveMaqPres=true;


public boolean isDesactiveMaqPres() {
	return desactiveMaqPres;
}

public void setDesactiveMaqPres(boolean desactiveMaqPres) {
	this.desactiveMaqPres = desactiveMaqPres;
}

public boolean isDesactiveProd() {
	return desactiveProd;
}

public void setDesactiveProd(boolean desactiveProd) {
	this.desactiveProd = desactiveProd;
}

public boolean isDesactiveTitre() {
	return desactiveTitre;
}

public void setDesactiveTitre(boolean desactiveTitre) {
	this.desactiveTitre = desactiveTitre;
}

public boolean isDesactiveNbreEx() {
	return desactiveNbreEx;
}

public void setDesactiveNbreEx(boolean desactiveNbreEx) {
	this.desactiveNbreEx = desactiveNbreEx;
}

public boolean isDesactiveChemin() {
	return desactiveChemin;
}

public void setDesactiveChemin(boolean desactiveChemin) {
	this.desactiveChemin = desactiveChemin;
}

//=============================================

private boolean desactiveListProSurDmd=true;
public boolean isDesactiveListProSurDmd() {
	return desactiveListProSurDmd;
}

public void setDesactiveListProSurDmd(boolean desactiveListProSurDmd) {
	this.desactiveListProSurDmd = desactiveListProSurDmd;
}

//=================================CHARGES==========================
private boolean desactiveListCharges=true;
private boolean desactiveQuantiteCharges=true;
private int idCharge;
private float quantiteCharge;
private List<SelectItem> listDesChargesNonEncoreAjouter;



public List<SelectItem> getListDesChargesNonEncoreAjouter() {
	
	System.out.println("DEBUT DE getListDesChargesNonEncoreAjouter()");
	
	ResultSet res=null;
	if(listDesChargesNonEncoreAjouter==null)
		listDesChargesNonEncoreAjouter=new ArrayList<SelectItem>();
	else
		listDesChargesNonEncoreAjouter.clear();
	
	listDesChargesNonEncoreAjouter.add(new SelectItem(0," "));
	//IL FAUT D'ABORT SAVOIR LES CHARGES QUI SONT DEJA SUR LE PRODUIT
	//SELECTIONNE
	res=Connecteur.Extrairedonnees("select * from charges");
int i=0;	
	
if((this.com!=null)&&(this.com.getListProd()!=null)&&(this.com.getListProd().size()>0))
{
	System.out.println("bon-");
	
//if(this.titreProduit!=null)
//{


		try {
			while(res.next())
			{
listDesChargesNonEncoreAjouter.add(new SelectItem(res.getInt("Idcharge"),res.getString("Designation")+" "+res.getFloat("PU")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//}
//FIN DU BLOC DE SI this.titreProduit!=null

}
System.out.println("aimez?");
System.out.println("FIN DE getListDesChargesNonEncoreAjouter()");
	return listDesChargesNonEncoreAjouter;
}

public void setListDesChargesNonEncoreAjouter(
		List<SelectItem> listDesChargesNonEncoreAjouter) {
	this.listDesChargesNonEncoreAjouter = listDesChargesNonEncoreAjouter;
}

public float getQuantiteCharge() {
	return quantiteCharge;
}

public void setQuantiteCharge(float quantiteCharge) {
	this.quantiteCharge = quantiteCharge;
}

public int getIdCharge() {
	return idCharge;
}

public void setIdCharge(int idCharge) {
	this.idCharge = idCharge;
}

public boolean isDesactiveListCharges() {
	return desactiveListCharges;
}

public void setDesactiveListCharges(boolean desactiveListCharges) {
	this.desactiveListCharges = desactiveListCharges;
}

public boolean isDesactiveQuantiteCharges() {
	return desactiveQuantiteCharges;
}

public void setDesactiveQuantiteCharges(boolean desactiveQuantiteCharges) {
	this.desactiveQuantiteCharges = desactiveQuantiteCharges;
}


//FONCTION D'AJOUT DES CHARGES SUR UNE COMMANDE
public void ajouterChargesSurProduit()
{
System.out.println("DEBUT DE public void ajouterChargesSurProduit()");

if(this.com==null)
{message="VOUS DEVEZ D'ABORD CREER UNE COMMANDE!!";
return;
	}
if((this.com.getListProd()==null)||(this.com.getListProd().size()<1))
{message="VOUS N'AVEZ AUCUN PRODUIT SUR UNE COMANDE!!";
return;
	}

if(this.titreProduit.length()==0)
{message="SELECTIONNER UN TITRE DU PRODUIT S'IL VOUS PLAIT!!";
return;
	}
if(this.idCharge==0)
{message="SELECTIONNER UNE CHARGE S'IL VOUS PLIT!!";
return;
	}
if(this.quantiteCharge==0)
{message="SASISSER LA QUATITE DE CETTE CHARGE NECESSAIRE POUR CE PRODUIT!!";
return;
	}
if((this.desactiveListProSurDmd)||(this.desactiveListCharges)||(this.desactiveQuantiteCharges))
{message="VOUS DEVEZ D'ABORD AJOUTER LE PREDUIT SUR UNE COMMANDE!!";
return;
	}



if((this.com.getListProd()==null)||(this.com.getListProd().size()==0))
{message="VOUS DEVEZ D'ABORD METTRE UN PRODUIT SUR UNE COMMANDE";
return;
	}

System.out.println("========FIN DES CONTROLES, AVANT AJOUT CHARGE==================");
//PREPARONS UNE CHARGE

Charge c=new Charge();
c.setIdCharge(this.idCharge);
c.setQuantiteCharge(this.quantiteCharge);

//EXTRAYONS LE COUT DE CETTE CHARGE
ResultSet res=null;

res=Connecteur.Extrairedonnees("select * from charges where Idcharge="+this.idCharge+"");
try {
	if(res.next())
	{c.setPrixUni(res.getFloat("PU"));
	c.setDesignation(res.getString("Designation"));
		}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

int i=0;

while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
{
	i++;
	}



if(i<this.com.getListProd().size())
{   
	//LE IF CI-DESSOUS N'EST PLUS NECESSAIRE(CETTE LISTE EST ABANDONNEE)
	if(this.com.getListProd().get(i).getListCharges()==null)
		{this.com.getListProd().get(i).setListCharges(new ArrayList<Charge>());
		System.out.println("--------JE CREE UNE NOUVELLE LISTE DES CHARGES------");
		}
	
	//------------------------AUTRE MOYEN-------------------------
	if(this.com.getListProd().get(i).getCharge()==null)
		{this.com.getListProd().get(i).setCharge(c);
		//this.com.getListProd().get(i).setMontantTotalProd(this.com.getListProd().get(i).getMontantTotalProd()+(this.com.getListProd().get(i).getCharge().getPrixUni()*this.com.getListProd().get(i).getCharge().getQuantiteCharge()));
		}
	
	else//IL Y A UN OU PLUSIEURS CHARGES
	{Charge j=null;
	boolean inseree=false;
		j=this.com.getListProd().get(i).getCharge();
		while(j.getCharge()!=null)
		{
			if(j.getIdCharge()==c.getIdCharge())
			{	j.setQuantiteCharge(j.getQuantiteCharge()+c.getQuantiteCharge());
				inseree=true;
			}
			
			j=j.getCharge();
			
		}
		
		
		if((j.getCharge()==null)&&(inseree==false))
		{if(j.getIdCharge()==c.getIdCharge())
			j.setQuantiteCharge(j.getQuantiteCharge()+c.getQuantiteCharge());
		else
			j.setCharge(c);
		}
		else
			System.out.println("JE PASSE DANS LE ELSE DANS LA FONCTION D'AJOUT DES CHARGES!!");
		
	}
	//------------------------------------------------------------
	
	System.out.println("c.getDesignation() "+c.getDesignation());
	System.out.println("c.getPrixUni() "+c.getPrixUni());
	System.out.println("this.com.getListProd().get(i).getListCharges().size() avant ajout "+this.com.getListProd().get(i).getListCharges().size());
	this.com.getListProd().get(i).getListCharges().add(c);
	System.out.println("this.com.getListProd().get(i).getListCharges().size() apres ajout "+this.com.getListProd().get(i).getListCharges().size());

message="VOUS ETES EN TRAIN D'AJOUTER UNE CHARGE SUR UN PRODUIT!!";
	}
else
message="CAS BIZARRE!!";


calculMontantPrUnProd2();
calculMontantCmd();
System.out.println("FIN DE public void ajouterChargesSurProduit()");
}


private List<Charge> listDesChargePr1Prod=null;
public List<Charge> getListDesChargePr1Prod() {
	
	if(listDesChargePr1Prod==null)
		listDesChargePr1Prod=new ArrayList<Charge>();
	else
		listDesChargePr1Prod.clear();
	

/*	if((this.com==null)||(this.com.getListProd()==null)||(this.com.getListProd().size()<1)||(this.titreProduit.length()<1)||(this.desactiveListProSurDmd==true)||(this.desactiveListCharges==true)||(this.desactiveQuantiteCharges==true))
	{System.out.println("@@@@@@@@@@@@@@");
	return listDesChargePr1Prod;
	}*/
	
	
	//if(this.titreProd!=null)
	//{	
	if((this.com!=null)&&(this.com.getListProd()!=null)&&(this.com.getListProd().size()>0)&&(this.titreProduit!=null)&&(this.titreProduit.length()>0))
	{
	System.out.println("this.com.getListProd().get(0).getTitre()"+this.com.getListProd().get(0).getTitre());
		
		int i=0;
		
		//FAISONS i POINTER SUR LE PRODUIT VOULU.
		while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
		{System.out.println("JE PASSE DANS LA BOUCLE i= "+i);
			i++;
			}
	//	System.out.println("this.com.getListProd().get(i).getListCharges().size() "+this.com.getListProd().get(i).getListCharges().size());
		if(this.com.getListProd().get(i).getListCharges()==null)
		{System.out.println(",,,,,,,,,,");
			this.com.getListProd().get(i).setListCharges(new ArrayList<Charge>());
		}
		//if((this.com.getListProd().get(i).getListCharges()!=null)&&(this.com.getListProd().get(i).getListCharges().size()>0))
	if(this.com.getListProd().get(i).getListCharges()!=null)
		{System.out.println("this.com.getListProd().get(i).getListCharges().size() avant prise de la liste "+this.com.getListProd().get(i).getListCharges().size());
			listDesChargePr1Prod=this.com.getListProd().get(i).getListCharges();
		System.out.println("this.com.getListProd().get(i).getListCharges().size() apres prise de la liste "+this.com.getListProd().get(i).getListCharges().size());
		}
	
//=======DANS LA ZONE DE LA LISTE DE RECOURS========
		
	//RECUPERONS LE NOM DU PRODUIT(ON A SON id)
	int idPro=this.com.getListProd().get(i).getIdProduit();
	ResultSet res=Connecteur.Extrairedonnees("select * from produits where Idprod="+idPro+"");
	String typ=null;
	
	try {
		res.next();
		typ=res.getString("Type");
		this.partieDuTitreSurTableau=typ+" :"+this.titreProduit;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	if(this.com.getListProd().get(i).getCharge()!=null)
	{Charge c;
		this.listDesChargePr1Prod.add(this.com.getListProd().get(i).getCharge());
		c=this.com.getListProd().get(i).getCharge();
		while(c.getCharge()!=null)
		{c=c.getCharge();
		this.listDesChargePr1Prod.add(c);
		}
		
	}

	
}
	//}
	if((this.titreProduit==null)||(this.titreProduit.length()<1))
		this.partieDuTitreSurTableau="";
	
	return listDesChargePr1Prod;
}

public void setListDesChargePr1Prod(List<Charge> listDesChargePr1Prod) {
	this.listDesChargePr1Prod = listDesChargePr1Prod;
}

private String titreProduit;
public String getTitreProduit() {
	return titreProduit;
}

public void setTitreProduit(String titreProduit) {
	this.titreProduit = titreProduit;
}


public void finirAjoutDesCharges()
{
if((this.desactiveListProSurDmd)&&(this.desactiveListCharges=true)&&(this.desactiveQuantiteCharges))
{message="VOUS DEVEZ D'ABORD AJOUTER UN PRODUIT SUR UNE COMMANDE!!";
return;
	}
//ON DESACTIVE LES CHAMPS D'AJOUT DES CHARGES
this.desactiveListProSurDmd=true;
this.desactiveListCharges=true;
this.desactiveQuantiteCharges=true;

//ON ACTIVE LES CHAMPS D'AJOUT DES PRODUITS
this.desactiveProd=false;
this.desactiveTitre=false;
this.desactiveNbreEx=false;
this.desactiveMaqPres=false;
this.desactiveChemin=false;

message="OK";

	}

public void finirAjoutDesProdSurCmd()
{
	if((this.desactiveProd)&&(this.desactiveTitre=true)&&(this.desactiveNbreEx)&&(this.desactiveMaqPres)&&(this.desactiveChemin))
	{message="VOUS DEVEZ D'ABORD CREER UNE COMMANDE!!";
	return;
		}
	
	
	//ON DESACTIVE LES CHAMPS D'AJOUT DES PRODUITS
	this.desactiveProd=true;
	this.desactiveTitre=true;
	this.desactiveNbreEx=true;
	this.desactiveMaqPres=true;
	this.desactiveChemin=true;
	
	//ON ACTIVE LES CHAMPS D'AJOUT DES COMMANDES
	//this.showMessageCmd=false;
	this.desactiveNomClient=false;
	this.activenif=false;
	this.activesociete=false;

	message="OK";
	}


private String partieDuTitreSurTableau="";
public String getPartieDuTitreSurTableau() {
	return partieDuTitreSurTableau;
}

public void setPartieDuTitreSurTableau(String partieDuTitreSurTableau) {
	this.partieDuTitreSurTableau = partieDuTitreSurTableau;
}

public void OrienteDansAjoutDesCharges()
{if(this.com==null)
{message="IL N'Y A PAS DE COMMANDE EN COUR DE CREATION!!";
return;
	}

if((this.com.getListProd()==null)||(this.com.getListProd().size()<1))
{message="IL N'Y A AUCUN PRODUIT SUR CETTE COMMANDE!!";
return;
	}

this.desactiveNomClient=true;
this.activenif=true;
this.activesociete=true;


this.desactiveProd=true;
this.desactiveTitre=true;
this.desactiveNbreEx=true;
this.desactiveMaqPres=true;
this.desactiveChemin=true;


//ON ACTIVE LES CHAMPS D'AJOUT DES CHARGES

this.desactiveListProSurDmd=false;
this.desactiveListCharges=false;
this.desactiveQuantiteCharges=false;
	
	}
//==============================MONTANTS==========================
private float montantPourLeProdSelectionne=0;
private float montantTotalDeLaCmd=0;
public float getMontantPourLeProdSelectionne() {
	return montantPourLeProdSelectionne;
}

public void setMontantPourLeProdSelectionne(float montantPourLeProdSelectionne) {
	this.montantPourLeProdSelectionne = montantPourLeProdSelectionne;
}

public float getMontantTotalDeLaCmd() {
	return montantTotalDeLaCmd;
}

public void setMontantTotalDeLaCmd(float montantTotalDeLaCmd) {
	this.montantTotalDeLaCmd = montantTotalDeLaCmd;
}


public void calculMontantPrUnProd(ActionEvent e)
{System.out.println("DEBUT");

System.out.println("a");
if((this.com==null)||(this.com.getListProd()==null)||(this.com.getListProd().size()<1)||(this.titreProduit.length()<1)||(this.desactiveListProSurDmd==true)||(this.desactiveListCharges==true)||(this.desactiveQuantiteCharges==true))
	{this.montantPourLeProdSelectionne=0;
	System.out.println("b");
	return;
	}
System.out.println("c");
int i=0;

while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
{System.out.println("d");
	i++;
	}
System.out.println("e");
this.montantPourLeProdSelectionne=this.com.getListProd().get(i).getMontantTotalProd();
System.out.println("f");
System.out.println("this.montantPourLeProdSelectionne "+this.montantPourLeProdSelectionne);
if(this.com.getListProd().get(i).getCharge()!=null)//IL Y A DES CHARGES SUR CE PRODUIT
{Charge j=this.com.getListProd().get(i).getCharge();
System.out.println("g");
this.montantPourLeProdSelectionne=this.montantPourLeProdSelectionne+(j.getPrixUni()*j.getQuantiteCharge());
System.out.println("h");
while(j.getCharge()!=null)//IL Y A ENCORE UNE CHARGE
{j=j.getCharge();
System.out.println("i");
this.montantPourLeProdSelectionne=this.montantPourLeProdSelectionne+(j.getPrixUni()*j.getQuantiteCharge());
	}
System.out.println("j");
}
System.out.println("k");
	
System.out.println("FIN");
}




public void calculMontantPrUnProd2()
{System.out.println("DEBUT");

System.out.println("0");
if((this.com==null)||(this.com.getListProd()==null)||(this.com.getListProd().size()<1)||(this.titreProduit.length()<1)||(this.desactiveListProSurDmd==true)||(this.desactiveListCharges==true)||(this.desactiveQuantiteCharges==true))
	{this.montantPourLeProdSelectionne=0;
	return;
	}
System.out.println("1");
int i=0;

while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
{System.out.println("2");
	i++;
	}
System.out.println("3");
this.montantPourLeProdSelectionne=this.com.getListProd().get(i).getMontantTotalProd();
System.out.println("4");
if(this.com.getListProd().get(i).getCharge()!=null)//IL Y A DES CHARGES SUR CE PRODUIT
{Charge j=this.com.getListProd().get(i).getCharge();
this.montantPourLeProdSelectionne=this.montantPourLeProdSelectionne+(j.getPrixUni()*j.getQuantiteCharge());
System.out.println("5");
while(j.getCharge()!=null)//IL Y A ENCORE UNE CHARGE
{j=j.getCharge();
System.out.println("6");
this.montantPourLeProdSelectionne=this.montantPourLeProdSelectionne+(j.getPrixUni()*j.getQuantiteCharge());
	}
System.out.println("7");
}

//this.com.getListProd().get(i).setMontantTotalProd(this.montantPourLeProdSelectionne);

System.out.println("8");	
System.out.println("FIN");
}

public float calculMontantPrUnProd3(String titre)
{float m=0;

System.out.println("DEBUT");

System.out.println("0");
if(titre.length()<1)
	return m;
	
System.out.println("1");
int i=0;

while((i<this.com.getListProd().size())&&!(titre.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
{System.out.println("2");
	i++;
	}
System.out.println("3");
m=this.com.getListProd().get(i).getMontantTotalProd();
System.out.println("4");
if(this.com.getListProd().get(i).getCharge()!=null)//IL Y A DES CHARGES SUR CE PRODUIT
{Charge j=this.com.getListProd().get(i).getCharge();
m=m+(j.getPrixUni()*j.getQuantiteCharge());
System.out.println("5");
while(j.getCharge()!=null)//IL Y A ENCORE UNE CHARGE
{j=j.getCharge();
System.out.println("6");
m=m+(j.getPrixUni()*j.getQuantiteCharge());
	}
System.out.println("7");
}

//this.com.getListProd().get(i).setMontantTotalProd(this.montantPourLeProdSelectionne);

System.out.println("8");	
System.out.println("FIN");


return m;
	}


public void calculMontantCmd()
{
if((this.com==null)||(this.com.getListProd()==null)||(this.com.getListProd().size()<1))
{this.montantTotalDeLaCmd=0;
return;
}

this.montantTotalDeLaCmd=0;
int i=0;

while(i<this.com.getListProd().size())
{
this.montantTotalDeLaCmd+=calculMontantPrUnProd3(this.com.getListProd().get(i).getTitre());
	i++;
	}
	}

public int recupereIdDuDernierCmd()
{int i=-1;
ResultSet r=null;
r=Connecteur.Extrairedonnees("SELECT Idcmd FROM commande order by Idcmd desc limit 1");
if(r!=null)
	try {
		if(r.next()){
			i=r.getInt("Idcmd");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return i;
	}

public int recupereIdDuDernierP()
{int i=-1;
ResultSet r=null;
r=Connecteur.Extrairedonnees("SELECT Idfigurer FROM figurer order by Idfigurer desc limit 1");
if(r!=null)
	try {
		if(r.next()){
			i=r.getInt("Idfigurer");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return i;
	}

public void saveCmd()
{	
	if((this.desactiveNomClient==true)||(this.activenif==true)||(this.activesociete==true)||(this.desactiveProd==false)||(this.desactiveTitre==false)||(this.desactiveNbreEx==false)||(this.desactiveMaqPres==false)||(this.desactiveChemin==false)||(this.desactiveListProSurDmd==false)||(this.desactiveListCharges==false)||(this.desactiveQuantiteCharges==false))
	{
		message="VOUS DEVEZ D'ABORD CLOTURER L'AJOUT DES PRODUITS ET DES CHARGES AVANT D'ENREGISTER LA COMMANDE!!";
		return;
	}
	
	if(this.com==null)
	{message="IL N'Y A PAS DE COMMANDE CREE!!";
		return;
	}
	
	if((this.com.getListProd()==null)||(this.com.getListProd().size()<1))
	{message="IL N'Y A PAS DE PRODUITS SUR LA COMMANDE!!";
	return;	
	}
	
	int i;
	float montant_pr_1_exe;
	ResultSet res=null;
	int n=0,m=0,idCmd=-1,idFig;
	
	//J'ENREGISTRE UNE COMMANDE
	n=Connecteur.Insererdonnees("insert into commande (Idclient,NIF,Societe,Datecmd) values ("+this.com.getIdClient()+",'"+this.com.getNif()+"','"+this.com.getSociete()+"',now())");
	if(n!=-1)
		message="INSERTION REUSSIE!!";
	idCmd=recupereIdDuDernierCmd();
	
	
	for(i=0;i<this.com.getListProd().size();i++)
	{	montant_pr_1_exe=0;
		n=-1;
		m=-1;
		idFig=0;
		Produit p=this.com.getListProd().get(i);
		
		
		if(p.getNbre_exemplaire_prod()>0)
		{	//JE CALCUL ET GARDE LE MONTANT A PAYER POUR UN EXEMPLAIRE
			montant_pr_1_exe=p.getMontantTotalProd()/p.getNbre_exemplaire_prod();
			

			m=Connecteur.Insererdonnees("insert into figurer(Idcmd,Idprod,Idchemin,Titre,Nbre_tot_exemplr,Avecmaqt,Nbre_exemplr_paye,Montant_a_paye_pr_1_ex) values ("+idCmd+","+p.getIdProduit()+","+p.getIdChemin()+",'"+p.getTitre()+"',"+p.getNbre_exemplaire_prod()+","+p.isMaq_presentee()+",0,"+montant_pr_1_exe+")");	
			if(m!=-1)
				message="INSERTION REUSSIE!!";	
			
			//IL FAUT ENSUITE METTRE SON ETAT A "NON ENTAMME"
			idFig=recupereIdDuDernierP();
			m=-1;
			m=Connecteur.Insererdonnees("insert into historique_etapes (Idfigure,Date,Designation) values ("+idFig+",now(),'NON ENTAMME')");
			
			if(p.getCharge()!=null)
			{
			idFig=recupereIdDuDernierP();
			Charge c=p.getCharge();
			
			int t=-1;
			t=Connecteur.Insererdonnees("insert into disposer(Idfigure,Idcharge,Qtite_tot_charge,PU_charge) values ("+idFig+","+c.getIdCharge()+","+c.getQuantiteCharge()+",'"+c.getPrixUni()+"')");
			if(t!=-1)
				message="INSERTION REUSSIE!!";
			while(c.getCharge()!=null)
			{t=-1;
				c=c.getCharge();
				t=Connecteur.Insererdonnees("insert into disposer(Idfigure,Idcharge,Qtite_tot_charge,PU_charge) values ("+idFig+","+c.getIdCharge()+","+c.getQuantiteCharge()+",'"+c.getPrixUni()+"')");	
				if(t!=-1)
					message="INSERTION REUSSIE!!";
			}
			
			}
			
			
		}
		
	}
	
	this.com=null;
	this.titreProduit=null;
}



private List<SelectItem> listDesCliAyaDesCmdAvcDesProdEnCour;
private int idCli=0;
public int getIdCli() {
	return idCli;
}

public void setIdCli(int idCli) {
	this.idCli = idCli;
}

public List<SelectItem> getListDesCliAyaDesCmdAvcDesProdEnCour() {
	
	ResultSet res=null;
	
	if(listDesCliAyaDesCmdAvcDesProdEnCour==null)
		listDesCliAyaDesCmdAvcDesProdEnCour=new ArrayList<SelectItem>();
	else
		listDesCliAyaDesCmdAvcDesProdEnCour.clear();
	
	listDesCliAyaDesCmdAvcDesProdEnCour.add(new SelectItem(0,""));
	
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE')");

	try {
		while(res.next())
		{listDesCliAyaDesCmdAvcDesProdEnCour.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+" "+res.getString("Prenompersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesCliAyaDesCmdAvcDesProdEnCour;
}

public void setListDesCliAyaDesCmdAvcDesProdEnCour(
		List<SelectItem> listDesCliAyaDesCmdAvcDesProdEnCour) {
	this.listDesCliAyaDesCmdAvcDesProdEnCour = listDesCliAyaDesCmdAvcDesProdEnCour;
}



private List<SelectItem> listDesCmdAvcDesProdEnCour;
private int idComd=0;

public int getIdComd() {
	return idComd;
}

public void setIdComd(int idComd) {
	this.idComd = idComd;
}

public List<SelectItem> getListDesCmdAvcDesProdEnCour() {
	
	ResultSet res=null;
	
	if(listDesCmdAvcDesProdEnCour==null)
		listDesCmdAvcDesProdEnCour=new ArrayList<SelectItem>();
	else
		listDesCmdAvcDesProdEnCour.clear();
	
	listDesCmdAvcDesProdEnCour.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCli!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli+" and Idcmd in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE')");

	try {
		while(res.next())
		{listDesCmdAvcDesProdEnCour.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	return listDesCmdAvcDesProdEnCour;
}

public void setListDesCmdAvcDesProdEnCour(
		List<SelectItem> listDesCmdAvcDesProdEnCour) {
	this.listDesCmdAvcDesProdEnCour = listDesCmdAvcDesProdEnCour;
}


private List<SelectItem> listDesProdDuneCmdNonEncorTermns; 
private int idP=0;
public int getIdP() {
	return idP;
}

public void setIdP(int idP) {
	this.idP = idP;
}

public List<SelectItem> getListDesProdDuneCmdNonEncorTermns() {
	
	ResultSet res=null;
	
	if(listDesProdDuneCmdNonEncorTermns==null)
		listDesProdDuneCmdNonEncorTermns=new ArrayList<SelectItem>();
	else
		listDesProdDuneCmdNonEncorTermns.clear();
	
	listDesProdDuneCmdNonEncorTermns.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if((this.idComd!=0)&&(this.idCli!=0))//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	//res=Connecteur.Extrairedonnees("select fig.Idfigurer,pro.Type,fig.Titre from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE'");
	res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE')");
	
	
	
	try {
		while(res.next())
		{	
				
			listDesProdDuneCmdNonEncorTermns.add(new SelectItem(res.getInt("Idfigurer"),res.getString("Type")+" : "+res.getString("Titre")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesProdDuneCmdNonEncorTermns;
}

public void setListDesProdDuneCmdNonEncorTermns(
		List<SelectItem> listDesProdDuneCmdNonEncorTermns) {
	this.listDesProdDuneCmdNonEncorTermns = listDesProdDuneCmdNonEncorTermns;
}
























//DEBUT DE LA PARTIE DE MANIPULATION DE <rich:fileUpload>


private ArrayList<File> files = new ArrayList<File>();
public ArrayList<File> getFiles() {
	return files;
}

public void setFiles(ArrayList<File> files) {
	this.files = files;
}

public int getSize() {
    if (getFiles().size()>0){
        return getFiles().size();
    }else 
    {
        return 0;
    }
}

/*public void paint(OutputStream stream, Object object) throws IOException {
    stream.write(getFiles().get((Integer)object).getData());
}*/


/*private boolean showMessageMaquette=false;
public boolean isShowMessageMaquette() {
	return showMessageMaquette;
}

public void setShowMessageMaquette(boolean showMessageMaquette) {
	this.showMessageMaquette = showMessageMaquette;
}
*/
public void listener(UploadEvent event) throws Exception{

System.out.println("debut ecouteur");
UploadItem item = event.getUploadItem();
String nomDuFic=null,nomDuFic0=null;

Date d=new Date();
System.out.println("1");
nomDuFic0=item.getFileName();
System.out.println("2");
nomDuFic=""+d.getYear()+"_"+d.getMonth()+"_"+d.getDate()+"_"+d.getDay()+"_"+d.getHours()+"_"+d.getMinutes()+"_"+d.getSeconds()+"_"+item.getFileName();
System.out.println("3");
File file = new File(nomDuFic);
//file.setLength(item.getData().length);
//file.setName(nomDuFic);
//file.setData(item.getData());
files.add(file);


//============================================
//FAUT COPIER LE FICHIER CHARGE DANS LE REPERT
//============================================

FileOutputStream fos=null;

try{
	
fos = new FileOutputStream(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\maquettes\\"+nomDuFic));
fos.write(item.getData());
fos.close();

} catch (IOException e) {
e.printStackTrace();
}
}




public void saveMaquette()
{
if(this.idCli==0)
{	message="VOUS N'AVEZ PAS SELECTONNER UN CLIENT!!";
	return;
	}

if(this.idComd==0)
{message="VOUS N'AVEZ PAS SELECTIONNE DE COMMANDE!!";
return;
	}

if(this.idP==0)
{message="VOUS N'AVEZ PAS SELECTIONNE UN PRODUIT!!";
return;
	}

if(this.files.size()<1)
	{message="LA LISTE DES MAQUETTES EST VIDE!!";
	return;
	}
int n=-1;
for(int j=0;j<this.files.size();j++)
{System.out.println("+this.files.get(j).getName()"+this.files.get(j).getName());
String s=this.files.get(j).getName();
System.out.println("s"+s);
n=Connecteur.Insererdonnees("insert into maquette (Idfigurer,Nomphoto,Dtecreation) values ("+this.idP+",'"+s+"',now())");
}
if(n!=-1)
	message="OPERATION REUSSIE!!";
else
	message="OPERATION ECHOUEE!!";
	
this.files.clear();
	}
//FIN DE LA PARTIE DE MANIPULATION DE <rich:fileUpload>
























//DEBUT DE LA PARTIE POUR MARQUER A QUELLE ETAPE SE TROUVE UN PRODUIT

private int idcli;
public int getIdcli() {
	return idcli;
}

public void setIdcli(int idcli) {
	this.idcli = idcli;
}
private int idCo=0;
public int getIdCo() {
	return idCo;
}

public void setIdCo(int idCo) {
	this.idCo = idCo;
}

private int idPro=0;
public int getIdPro() {
	return idPro;
}

public void setIdPro(int idPro) {
	this.idPro = idPro;
}

private List<SelectItem> listDesEtapNonEncorMarqPrUnProd;

public List<SelectItem> getListDesEtapNonEncorMarqPrUnProd() {
	
	System.out.println("_@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@_");
	ResultSet res=null,r0=null;
	
	if(listDesEtapNonEncorMarqPrUnProd==null)
		listDesEtapNonEncorMarqPrUnProd=new ArrayList<SelectItem>();
	else
		listDesEtapNonEncorMarqPrUnProd.clear();
	
	listDesEtapNonEncorMarqPrUnProd.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if((this.idComd!=0)&&(this.idCli!=0)&&(this.idP!=0))//SI ON A SELECTIONNE UN CLIENT,
		//UNE COMMANDE ET UN PRODUIT
	{
	System.out.println("11");
	System.out.println("11");
	//res=Connecteur.Extrairedonnees("select et.Designation from figurer fig,chemin che,chemin_etapes c_e,etapes et where fig.Idcmd="+this.idComd+" and fig.Idprod="+this.idP+" and fig.Idchemin=che.Idchemin and che.Idchemin=c_e.idchemin and c_e.Idetape=et.Idetape");
	System.out.println("LE ID DESIGNANT UN PRODUIT D'UNE COMMANDE EST-> "+this.idP);
	res=Connecteur.Extrairedonnees("select et.Designation from figurer fig,chemin che,chemin_etapes c_e,etapes et where fig.Idfigurer="+this.idP+" and fig.Idchemin=che.Idchemin and che.Idchemin=c_e.idchemin and c_e.Idetape=et.Idetape");	
	try {
	System.out.println("@0");
		while(res.next())
		{
		int idf=this.idP;
		
		//idf=recupereIdfigure(this.idComd,this.idP);
		//if(idf==0)
			//break;
		//else
		//{
		r0=Connecteur.Extrairedonnees("select * from historique_etapes where Idfigure="+idf+" and Designation='"+res.getString("Designation")+"'");
		if(!r0.next())
		{
		listDesEtapNonEncorMarqPrUnProd.add(new SelectItem(res.getString("Designation")));
		System.out.println("@1");
		}
		
		//}
		
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesEtapNonEncorMarqPrUnProd;
}

public void setListDesEtapNonEncorMarqPrUnProd(
		List<SelectItem> listDesEtapNonEncorMarqPrUnProd) {
	this.listDesEtapNonEncorMarqPrUnProd = listDesEtapNonEncorMarqPrUnProd;
}

//FIN DE LA PARTIE POUR MARQUER A QUELLE ETAPE SE TROUVE UN PRODUIT

private String etap=null;
public String getEtap() {
	return etap;
}

public void setEtap(String etap) {
	this.etap = etap;
}

public void marquerEtape()
{
int idFig=0;
ResultSet r=null;

if(this.idCli==0)
{message="SELECTIONNER UN CLIENT S'IL VOUS PLAIT!!";
return;
	}

if(this.idComd==0)
{message="SELECTIONNER UNE COMMANDE S'IL VOUS PLAIT!!";
return;
	}

if(this.idP==0)
{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
return;
	}

if((this.etap.length()<1)||(this.etap.equalsIgnoreCase("0")||(this.etap==null)))
{message="SELECTIONNER UNE ETAPE S'IL VOUS PLAIT!!";
return;
	}

System.out.println("this.etap "+this.etap);
System.out.println("this.etap "+this.etap);

//r=Connecteur.Extrairedonnees("select Idfigurer from figurer where Idcmd="+this.idComd+" and Idprod="+this.idP+"");

try {
	//r.next();
	//idFig=r.getInt("Idfigurer");
	idFig=this.idP;
	//if(idFig==0)
		//return;
	
	int n=-1;
	
	r=null;
	r=Connecteur.Extrairedonnees("select * from historique_etapes where Idfigure="+idFig+" and Designation='"+this.etap+"'");
	
	if(r.next())
	{
		message="CETTE ETAPE EST DEJA MARQUEE!!";
		return;
	}
	
	n=Connecteur.Insererdonnees("insert into historique_etapes (Idfigure,Date,Designation) values ("+idFig+",now(),'"+this.etap+"')");
	
	if(n==-1)
	{
		message="ECHEC D'INSERTION!!";
		return;
	}
	
	message="INSERTION REUSSIE!!";
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}


private List<CheminOuEtape> listHistorique=null;
public List<CheminOuEtape> getListHistorique() {
	
	ResultSet res=null;
	
	if(listHistorique==null)
		listHistorique=new ArrayList<CheminOuEtape>();
	else
		listHistorique.clear();
	
	
	System.out.println("!!!!!!!!!");
	//res=Connecteur.Extrairedonnees("select p.Type,h.Date,h.Designation from produits p,figurer f,historique_etapes h where f.Idcmd="+this.idComd+" and f.Idprod="+this.idP+" and p.Idprod=f.Idprod and f.Idfigurer=h.Idfigure");
	res=Connecteur.Extrairedonnees("select p.Type,h.Date,h.Designation from produits p,figurer f,historique_etapes h where f.Idfigurer="+this.idP+" and p.Idprod=f.Idprod and f.Idfigurer=h.Idfigure");
	int num=1;
	try {
		while(res.next())
		{System.out.println("////////1");
		CheminOuEtape p=new CheminOuEtape();
		p.setDate(res.getDate("Date").toString());
		p.setDesignationP(res.getString("Type"));
		p.setEtap(res.getString("Designation"));
		p.setNum(num);
		listHistorique.add(p);
		
	System.out.println("////////2");	
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listHistorique;
}

public void setListHistorique(List<CheminOuEtape> listHistorique) {
	this.listHistorique = listHistorique;
}


public int recupereIdfigure(int idco,int idpro)
{int id=0;
ResultSet r=null;

r=Connecteur.Extrairedonnees("select Idfigurer from figurer where Idcmd="+idco+" and Idprod="+idpro+"");
try {
	if(r.next())
	{
		id=r.getInt("Idfigurer");
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

return id;
}





//DEBUT DE LA FONCTION QUI PERMET D'ENLEVER UNE ETAPE SUR UN CHEMIN
private CheminOuEtape cheOuEta=null;
public CheminOuEtape getCheOuEta() {
	return cheOuEta;
}

public void setCheOuEta(CheminOuEtape cheOuEta) {
	this.cheOuEta = cheOuEta;
}

public void enleverEtapeSurChem(ActionEvent e)
{
if(this.cheOuEta==null)//CAS IMPOSSIBLE
{message="AUCUNE ETAPE N'A ETE SUPPRIMEE!!";
return;
	}

if(this.idChemin==0)
{message="AUCUN CHEMIN N'A ETE SELECTIONNE!!";
return;
	}

ResultSet re=null;
re=Connecteur.Extrairedonnees("select * from etapes where Designation='"+this.cheOuEta.getDesignationEta()+"'");
int idE=0;

try {
	re.next();
	idE=re.getInt("Idetape");

int n=-1;
n=Connecteur.Insererdonnees("delete from chemin_etapes where idchemin="+this.idChemin+" and Idetape="+idE+"");

if(n==-1)
	message="SUPPRESSION DE L'ETAPE ECHOUEE!!";
else
	message="SUPPRESSION DE L'ETAPE REUSSIE!!";

this.cheOuEta=null;
//this.idChemin=0;
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

	}
//FIN DE LA FONCTION QUI PERMET D'ENLEVER UNE ETAPE SUR UN CHEMIN

private List<CheminOuEtape> listDesEtapDuChem;
public List<CheminOuEtape> getListDesEtapDuChem() {
	
	ResultSet res=null;
	
	if(listDesEtapDuChem==null)
		listDesEtapDuChem=new ArrayList<CheminOuEtape>();
	else
		listDesEtapDuChem.clear();
	
	
	
	res=Connecteur.Extrairedonnees("select c.Designation,e.Designation from chemin as c,chemin_etapes as c_e,etapes as e where e.Idetape=c_e.Idetape and c_e.idchemin=c.Idchemin and c.Idchemin="+this.idChemin+"");
	int no=0,num=1;
	try {
		while(res.next())
		{CheminOuEtape p=new CheminOuEtape();
		p.setDesignationChe(res.getString("c.Designation"));
		p.setDesignationEta(res.getString("e.Designation"));
		p.setNum(num);
		listDesEtapDuChem.add(p);
		if(no==0)
			titre="LISTE DES ETAPES SE TROUVANT SUR LE CHEMIN : '"+res.getString("c.Designation").toUpperCase()+"'";
		
		no++;
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(no==0)
		titre="LA SELECTION D'UN CHEMIN VISUALISE SES ETAPES";
	
	return listDesEtapDuChem;
}

public void setListDesEtapDuChem(List<CheminOuEtape> listDesEtapDuChem) {
	this.listDesEtapDuChem = listDesEtapDuChem;
}


}


