package paquetImprilac;

import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import java.awt.Desktop;
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
	
	public CommandeBean()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		 HttpSession session =(HttpSession)context.getExternalContext().getSession(true);  
		 
		 String dataConnect=(String)session.getAttribute("legal");
		 //String dataConnect=(String)session.getAttribute("idPersonneConnectee");
		 
		 if(dataConnect==null){
			 try {
				context.getExternalContext().redirect("/imprilac/Identification.jsf");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
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
		res=Connecteur.Extrairedonnees("select * from chemin where supprime=0");

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
	res=Connecteur.Extrairedonnees("select * from etapes as eta where eta.Idetape not in (select che_eta.Idetape from chemin_etapes as che_eta where che_eta.idchemin="+this.idChemin+") and eta.supprime=0");

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
	
	res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and c.supprimee=0 order by Nompersonne");

	try {
		while(res.next())
		{listDesClients.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"    "+res.getString("Prenompersonne")+"        "+res.getString("Idpersonne")));

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
private float coutMaqt=0;
public float getCoutMaqt() {
	return coutMaqt;
}
public void setCoutMaqt(float coutMaqt) {
	this.coutMaqt = coutMaqt;
}

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

if((this.coutMaqt==0)&&(this.maqPresente.equalsIgnoreCase("NON")))
		{
	message="SAISISSER LE COUT DE LA MAQUETTE S'IL VOUS PLAIT!!";
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
/*if(this.maqPresente=="OUI")
	p.setMaq_presentee(this.maqPresente);
else
{
	p.setMaq_presentee(false);
	p.setCout_maqt(this.coutMaqt);
}*/


	p.setMaq_presentee(this.maqPresente);
	if(this.maqPresente.equalsIgnoreCase("NON"))
	p.setCout_maqt(this.coutMaqt);
	else
	p.setCout_maqt(0);



p.setIdChemin(this.idChemin);

System.out.println("ddd");
res=Connecteur.Extrairedonnees("select * from chemin where Idchemin="+this.idChemin+"");
try {
	if(res.next())
	{
p.setMontantTotalProd(res.getFloat("cout"));
p.setCoutChemin(res.getFloat("cout"));
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
		else
		{	j++;
			p.setNum(j);
		}
	}else
		p.setNum(1);

 }
else//LA LISTE DES PRODUITS N'EXISTE PAS
{this.com.setListProd(new ArrayList<Produit>());
System.out.println("fff");
p.setNum(1);
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
				//
if(res.getString("Designation")!=null)
listDesChargesNonEncoreAjouter.add(new SelectItem(res.getInt("Idcharge"),res.getString("Designation")+"   "+res.getFloat("PU")));
else
{ResultSet r=null;
System.out.println("bbbbbb---------------bbbbbbb");
r=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+res.getInt("Idmat")+"");
if(r.next())
{System.out.println("ccccccccc-----------cccccccc");
listDesChargesNonEncoreAjouter.add(new SelectItem(res.getInt("Idcharge"),r.getString("Designation")+"   "+res.getFloat("PU")));

	}
	}
//
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
{message="VOUS DEVEZ D'ABORD AJOUTER LE PRODUIT SUR UNE COMMANDE!!";
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
	
	if(res.getInt("Idmat")!=0)
	{
		ResultSet r=null;
		r=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+res.getInt("Idmat")+"");
	
		if(r.next())
	c.setDesignation(r.getString("Designation"));
		
	}
	
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


private Charge selectedCharge;

public Charge getSelectedCharge() {
	return selectedCharge;
}

public void setSelectedCharge(Charge selectedCharge) {
	this.selectedCharge = selectedCharge;
}


public void enleverChargeSurProd(ActionEvent e)
{

	if(this.com==null)//CAS IMPOSSIBLE
	{message="VOUS DEVEZ D'ABORD CREER UNE COMMANDE!!";
	return;
		}
	if((this.com.getListProd()==null)||(this.com.getListProd().size()<1))//CAS IMPOSSIBLE
	{message="VOUS N'AVEZ AUCUN PRODUIT SUR UNE COMANDE!!";
	return;
		}
	if((this.desactiveListProSurDmd)||(this.desactiveListCharges)||(this.desactiveQuantiteCharges))
	{message="ACTIVER D'ABORD LA PARTIE D'AJOUT DES CHARGES!!";
	return;
		}
	if((this.com.getListProd()==null)||(this.com.getListProd().size()==0))//CAS IMPOSSIBLE
	{message="VOUS DEVEZ D'ABORD METTRE UN PRODUIT SUR UNE COMMANDE";
	return;
		}
	int i=0;
	while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
	{
		i++;
		}

	if(i<this.com.getListProd().size())
	{   
		
		Charge j1,j2=null;
		//boolean inseree=false;
		if(this.com.getListProd().get(i).getCharge()!=null)//TOUJOURS LE CAS
		{
			j1=this.com.getListProd().get(i).getCharge();
			if(j1.getIdCharge()==this.selectedCharge.getIdCharge())//IL Y AVAIS UNE SEULE CHARGE SUR CE PRODUIT
			{	
				this.com.getListProd().get(i).setCharge(j1.getCharge());	
			}
			else//IL Y AVAIS PLUS D'UNE SEULE CHARGE
			{while((j1.getCharge()!=null)&&(j1.getIdCharge()!=this.selectedCharge.getIdCharge()))
			{
				j2=j1;
				j1=j1.getCharge();
			}
			j2.setCharge(j1.getCharge());
			j1.setCharge(null);	
			}
			
			
			
		}
		else
			message="CAS BIZARRE!!";
		
		
		



	calculMontantPrUnProd2();
	calculMontantCmd();
	System.out.println("FIN DE public void ajouterChargesSurProduit()");
	
	
	}

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
	
	System.out.println("1");
	if((this.com!=null)&&(this.com.getListProd()!=null)&&(this.com.getListProd().size()>0)&&(this.titreProduit!=null)&&(this.titreProduit.length()>0))
	{System.out.println("2");
	System.out.println("this.com.getListProd().get(0).getTitre()"+this.com.getListProd().get(0).getTitre());
	System.out.println("3");
		int i=0;
		
		//FAISONS i POINTER SUR LE PRODUIT VOULU.
		while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
		{System.out.println("JE PASSE DANS LA BOUCLE i= "+i);
			i++;
			System.out.println("4");
			}
		System.out.println("5");
	//	System.out.println("this.com.getListProd().get(i).getListCharges().size() "+this.com.getListProd().get(i).getListCharges().size());
		if(this.com.getListProd().get(i).getListCharges()==null)
		{System.out.println(",,,,,,,,,,");
		System.out.println("6");
			this.com.getListProd().get(i).setListCharges(new ArrayList<Charge>());
		}
		System.out.println("7");
		//if((this.com.getListProd().get(i).getListCharges()!=null)&&(this.com.getListProd().get(i).getListCharges().size()>0))
	if(this.com.getListProd().get(i).getListCharges()!=null)
		{System.out.println("8");
		System.out.println("this.com.getListProd().get(i).getListCharges().size() avant prise de la liste "+this.com.getListProd().get(i).getListCharges().size());
			listDesChargePr1Prod=this.com.getListProd().get(i).getListCharges();
		System.out.println("this.com.getListProd().get(i).getListCharges().size() apres prise de la liste "+this.com.getListProd().get(i).getListCharges().size());
		System.out.println("9");
		}
	
//=======DANS LA ZONE DE LA LISTE DE RECOURS========
	System.out.println("10");
	//RECUPERONS LE NOM DU PRODUIT(ON A SON id)
	int idPro=this.com.getListProd().get(i).getIdProduit();
	ResultSet res=Connecteur.Extrairedonnees("select * from produits where Idprod="+idPro+"");
	String typ=null;
	System.out.println("11");
	try {System.out.println("12");
		res.next();
		typ=res.getString("Type");
		this.partieDuTitreSurTableau=typ+" :"+this.titreProduit;
		System.out.println("13");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	System.out.println("14");
	if(this.com.getListProd().get(i).getCharge()!=null)
	{Charge c;
	System.out.println("15");
		this.listDesChargePr1Prod.add(this.com.getListProd().get(i).getCharge());
		c=this.com.getListProd().get(i).getCharge();
		System.out.println("16");
		while(c.getCharge()!=null)
		{c=c.getCharge();
		System.out.println("17");
		this.listDesChargePr1Prod.add(c);
		}
		System.out.println("18");
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

this.idCharge=0;
this.titreProduit=null;
calculMontantPrUnProd2();
this.quantiteCharge=0;
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
	
	
	this.idProd=0;
	this.titreProd=null;
	this.nbrExemplaires=0;
	this.idChemin=0;
	this.coutMaqt=0;

	message="OK";
	}


private String partieDuTitreSurTableau="";
public String getPartieDuTitreSurTableau() {
	return partieDuTitreSurTableau;
}

public void setPartieDuTitreSurTableau(String partieDuTitreSurTableau) {
	this.partieDuTitreSurTableau = partieDuTitreSurTableau;
}

public void OrienteDansAjoutDesProd()
{if(this.com==null)
{message="IL N'Y A PAS DE COMMANDE EN COUR DE CREATION!!";
return;
	}

/*if((this.com.getListProd()==null)||(this.com.getListProd().size()<1))
{message="IL N'Y A AUCUN PRODUIT SUR CETTE COMMANDE!!";
return;
	}*/

this.desactiveNomClient=true;
this.activenif=true;
this.activesociete=true;


//ON ACTIVE LES CHAMPS D'AJOUT DES PRODUITS
this.desactiveProd=false;
this.desactiveTitre=false;
this.desactiveNbreEx=false;
this.desactiveMaqPres=false;
this.desactiveChemin=false;




this.desactiveListProSurDmd=true;
this.desactiveListCharges=true;
this.desactiveQuantiteCharges=true;

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
//this.montantPourLeProdSelectionne=this.com.getListProd().get(i).getMontantTotalProd();

this.montantPourLeProdSelectionne=this.com.getListProd().get(i).getCoutChemin()+this.com.getListProd().get(i).getCout_maqt();

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
this.com.getListProd().get(i).setMontantTotalProd(this.montantPourLeProdSelectionne);
System.out.println("FIN");
}




public void calculMontantPrUnProd2()
{System.out.println("DEBUT");

this.montantPourLeProdSelectionne=0;

System.out.println("[][][][][][][][][][][][][][]");
if((this.titreProduit==null)||(this.titreProduit.equalsIgnoreCase("")))
{System.out.println("[1][1][1][1][1][1][1][][1][1][1][1][1][1]");
	message="OK";
	return;
	}
System.out.println("[2][2][2][2][2][2][2][2][2][2][2][2][2][2]");


System.out.println("0");
//this.montantPourLeProdSelectionne=0;
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
//this.montantPourLeProdSelectionne=this.com.getListProd().get(i).getMontantTotalProd();
this.montantPourLeProdSelectionne=this.com.getListProd().get(i).getCoutChemin()+this.com.getListProd().get(i).getCout_maqt();


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

this.com.getListProd().get(i).setMontantTotalProd(this.montantPourLeProdSelectionne);
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
//m=this.com.getListProd().get(i).getMontantTotalProd();
m=this.com.getListProd().get(i).getCoutChemin()+this.com.getListProd().get(i).getCout_maqt();
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
this.com.setMontantTotal(this.montantTotalDeLaCmd);
}

private Produit p=null;
public Produit getP() {
	return p;
}
public void setP(Produit p) {
	this.p = p;
}

//DEBUT DE LA FONCTION QUI ENLEVE UN PRODUIT SUR UNE COMMANDE
public void deleteProd(ActionEvent ev)
{
	if(p==null)//CAS IMPOSSIBLE
	{message="PAS DE PRODUIT INDIQUE POUR LA SUPPRESSION!!";
	return;
		}
	
	if(this.com.getListProd().size()<1)//CAS IMPOSSIBLE
	{message="PAS DE PRODUIT INDIQUE POUR LA SUPPRESSION!!";
	return;
	}
	
	int j=0;
	while((j<this.com.getListProd().size())&&!(this.com.getListProd().get(j).getTitre().equalsIgnoreCase(p.getTitre())))
	{
		j++;
	}
	
	this.com.getListProd().remove(j);
	
	this.titreProduit=null;
	calculMontantPrUnProd2();
	calculMontantCmd();
	}
//FIN DE LA FONCTION QUI ENLEVE UN PRODUIT SUR UNE COMMANDE

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
	
	
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session =(HttpSession)context.getExternalContext().getSession(true); 
	
	
	int i;
	float montant_pr_1_exe;
	ResultSet res=null;
	int n=0,m=0,idCmd=-1,idFig;
	
	//J'ENREGISTRE UNE COMMANDE
	n=Connecteur.Insererdonnees("insert into commande (Idclient,Idchef_prod,NIF,Societe,Datecmd) values ("+this.com.getIdClient()+","+session.getAttribute("idPersonneConnectee")+",'"+this.com.getNif()+"','"+this.com.getSociete()+"',now())");
	if(n!=-1)
		message="INSERTION REUSSIE!!";
	idCmd=recupereIdDuDernierCmd();
	
	this.idPersonneConnecte=0;
	for(i=0;i<this.com.getListProd().size();i++)
	{	montant_pr_1_exe=0;
		n=-1;
		m=-1;
		idFig=0;
		Produit p=this.com.getListProd().get(i);
		
		
		if(p.getNbre_exemplaire_prod()>0)
		{	//JE CALCUL ET GARDE LE MONTANT A PAYER POUR UN EXEMPLAIRE
			montant_pr_1_exe=p.getMontantTotalProd()/p.getNbre_exemplaire_prod();
			

			m=Connecteur.Insererdonnees("insert into figurer(Idcmd,Idprod,Idchemin,Cout_Chemin,Titre,Nbre_tot_exemplr,Avecmaqt,cout_maqt,Nbre_exemplr_paye,Montant_a_paye_pr_1_ex,etat) values ("+idCmd+","+p.getIdProduit()+","+p.getIdChemin()+","+p.getCoutChemin()+",'"+p.getTitre()+"',"+p.getNbre_exemplaire_prod()+",'"+p.getMaq_presentee()+"',"+p.getCout_maqt()+",0,"+montant_pr_1_exe+",'COMMANDE')");	
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
			ResultSet r=null;
			
			
			//
			r=Connecteur.Extrairedonnees("select * from charges where Idcharge="+c.getIdCharge()+"");
			try {
				if(r.next())
				{
					if(r.getInt("Idmat")!=0)//CETTE CHARGE EST DE TYPE CHARGE MATERIEL
					{
						ResultSet r1=null;
						r1=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+r.getInt("Idmat")+"");
						if(r1.next())
						{
							int q=0;
							q=r1.getInt("qtiteRestteOChef");
							q=q-(int)c.getQuantiteCharge();
							
							int tt=-1;
							tt=Connecteur.Insererdonnees("update materiel set qtiteRestteOChef="+q+" where Idmateriel="+r.getInt("Idmat")+"");
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//
			
			
			if(t!=-1)
				message="INSERTION REUSSIE!!";
			while(c.getCharge()!=null)
			{t=-1;
				c=c.getCharge();
				t=Connecteur.Insererdonnees("insert into disposer(Idfigure,Idcharge,Qtite_tot_charge,PU_charge) values ("+idFig+","+c.getIdCharge()+","+c.getQuantiteCharge()+",'"+c.getPrixUni()+"')");	
				if(t!=-1)
					message="INSERTION REUSSIE!!";
	
				r=null;
				r=Connecteur.Extrairedonnees("select * from charges where Idcharge="+c.getIdCharge()+"");
				try {
					if(r.next())
					{
						if(r.getInt("Idmat")!=0)//CETTE CHARGE EST DE TYPE CHARGE MATERIEL
						{
							ResultSet r1=null;
							r1=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+r.getInt("Idmat")+"");
							if(r1.next())
							{
								int q=0;
								q=r1.getInt("qtiteRestteOChef");
								q=q-(int)c.getQuantiteCharge();
								
								int tt=-1;
								tt=Connecteur.Insererdonnees("update materiel set qtiteRestteOChef="+q+" where Idmateriel="+r.getInt("Idmat")+"");
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
				
				
			}
			
			}
			
			
		}
		
	}
	
	this.com=null;
	this.titreProduit=null;
	this.idProd=0;
	this.titreProd=null;
	this.nbrExemplaires=0;
	this.idChemin=0;
	this.coutMaqt=0;
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
	
	//res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE' and cli.supprimee=0) order by p.Nompersonne");
	//res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne not in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation ='TERMINEE' and cli.supprimee=0) order by p.Nompersonne");
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.etat!='COMMANDE') and p.Idpersonne in (select Idclient from client where supprimee=0) order by Nompersonne");

	try {
		while(res.next())
		{listDesCliAyaDesCmdAvcDesProdEnCour.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"    "+res.getString("Prenompersonne")+"      "+res.getString("Idpersonne")));

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



private List<SelectItem> listDesCliAyaDesCmdAvcDesProd;

public List<SelectItem> getListDesCliAyaDesCmdAvcDesProd() {
	
	ResultSet res=null;
	
	if(listDesCliAyaDesCmdAvcDesProd==null)
		listDesCliAyaDesCmdAvcDesProd=new ArrayList<SelectItem>();
	else
		listDesCliAyaDesCmdAvcDesProd.clear();
	
	listDesCliAyaDesCmdAvcDesProd.add(new SelectItem(0,""));
	
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE' and cli.supprimee=0) order by p.Nompersonne");

	try {
		while(res.next())
		{listDesCliAyaDesCmdAvcDesProd.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"    "+res.getString("Prenompersonne")+"      "+res.getString("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesCliAyaDesCmdAvcDesProd;
}

public void setListDesCliAyaDesCmdAvcDesProd(
		List<SelectItem> listDesCliAyaDesCmdAvcDesProd) {
	this.listDesCliAyaDesCmdAvcDesProd = listDesCliAyaDesCmdAvcDesProd;
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
	//res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli+" and Idcmd in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE')");
	//res=Connecteur.Extrairedonnees("select * from commande where Idclient="+this.idCli+" and Idcmd not in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idclient="+this.idCli+" and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation ='TERMINEE')");

	res=Connecteur.Extrairedonnees("select * from commande where Idclient="+this.idCli+" and Idcmd in (select com.Idcmd from commande com,figurer fig where com.Idcmd=fig.Idcmd and fig.etat!='COMMANDE')");

	System.out.println("A");
	try {
		while(res.next())
		{	System.out.println("A1");
			if((res.getString("bonCmd")!=null)||(res.getFloat("montantPaye")!=0))
			listDesCmdAvcDesProdEnCour.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

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
	//res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE')");
	res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer not in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation ='TERMINEE')");
	
	
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



//SI CE PRODUIT A L'ETAT 'EN ATTENTE DE TRAITEMENT'
//METTONS-LE DANS L'ETAT 'EN COURS DE TRAITEMENT'
ResultSet re=null;
re=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+this.idP+" and etat='EN ATTENTE DE TRAITEMENT'");
try {
	if(re.next())
	{
	int ne=-1;
	ne=Connecteur.Insererdonnees("update figurer set etat='EN COURS DE TRAITEMENT' where Idfigurer="+this.idP+"");
	}
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
//


	
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
	ResultSet res=null,r0=null,r1=null;
	
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
	int idf=this.idP;
		while(res.next())
		{
		//int idf=this.idP;
		
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
		
		
		r1=Connecteur.Extrairedonnees("select * from historique_etapes where Idfigure="+idf+" and Designation='TERMINE'");
		if(!r1.next())
		{
		listDesEtapNonEncorMarqPrUnProd.add(new SelectItem("TERMINE"));
		//System.out.println("@1");
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
	
	n=Connecteur.Insererdonnees("insert into historique_etapes (Idfigure,Idproducteur,Date,Designation) values ("+idFig+","+this.idPersonneConnecte+",now(),'"+this.etap+"')");
	
	this.idPersonneConnecte=0;
	
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
	res=Connecteur.Extrairedonnees("select p.Type,h.Idhisto_etape,h.Date,h.Designation from produits p,figurer f,historique_etapes h where f.Idfigurer="+this.idP+" and p.Idprod=f.Idprod and f.Idfigurer=h.Idfigure");
	int num=1;
	try {
		while(res.next())
		{System.out.println("////////1");
		CheminOuEtape p=new CheminOuEtape();
		p.setIdHisto(res.getInt("Idhisto_etape"));
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



private List<Maquette> listDesMaqD1ProdDonne;
public List<Maquette> getListDesMaqD1ProdDonne() {
	
ResultSet res=null;
	
	if(listDesMaqD1ProdDonne==null)
		listDesMaqD1ProdDonne=new ArrayList<Maquette>();
	else
		listDesMaqD1ProdDonne.clear();
	
	
	System.out.println("!!!!!!!!!");
	//res=Connecteur.Extrairedonnees("select p.Type,h.Date,h.Designation from produits p,figurer f,historique_etapes h where f.Idcmd="+this.idComd+" and f.Idprod="+this.idP+" and p.Idprod=f.Idprod and f.Idfigurer=h.Idfigure");
	res=Connecteur.Extrairedonnees("select * from maquette where Idfigurer="+this.idP+"");
	int num=1;
	try {
		while(res.next())
		{
		Maquette m=new Maquette();
		m.setNum(num);
		m.setIdMaql(res.getInt("Idmaquette"));
		m.setNomMaq(res.getString("Nomphoto"));
		
		listDesMaqD1ProdDonne.add(m);
		
	System.out.println("////////2");	
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesMaqD1ProdDonne;
}

public void setListDesMaqD1ProdDonne(List<Maquette> listDesMaqD1ProdDonne) {
	this.listDesMaqD1ProdDonne = listDesMaqD1ProdDonne;
}

private Maquette m=null;
public Maquette getM() {
	return m;
}

public void setM(Maquette m) {
	this.m = m;
}

//DEBUT DE LA FONCTION QUI EST UTILISEE POUR SUPPRIMER UNE MAQUETTE
public void deleteMaquette(ActionEvent e)
{if(m==null)//CAS IMPOSSIBLE
{message="PAS DE MAQUETTE INDIQUE POUR LA SUPPRESSION!!";
return;
	}

int idm=0;
idm=m.getIdMaql();
int n=-1;
n=Connecteur.Insererdonnees("delete from maquette where Idmaquette="+idm+"");
if(n==-1)
	message="OPERATION ECHOUEE!!";
else
	message="OPERATION REUSSIE!!";

m=null;
	}
//FIN DE LA FONCTION QUI EST UTILISEE POUR SUPPRIMER UNE MAQUETTE

//DEBUT DE LA FONCTION QUI EST UTILISEE POUR VISUALISER UNE MAQUETTE
public void visualiseMaquette(ActionEvent ev)
{if(m==null)//CAS IMPOSSIBLE
{message="PAS DE MAQUETTE INDIQUE POUR LA SUPPRESSION!!";
return;
	}

//int idm=0;
//idm=m.getIdMaql();

String nomMaq=null;
nomMaq=m.getNomMaq();

Desktop desk = Desktop.getDesktop();
try {
	desk.open(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\maquettes\\"+nomMaq));
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


this.pt=null;

	}
//FIN DE LA FONCTION QUI EST UTILISEE POUR VISUALISER UNE MAQUETTE



//DEBUT DE LA PARTIE POUR LA CREATION DES GROUPES DES EMPLOYES
private List<SelectItem> listDesCliAyaDesCmdAvcDesProdEnCour1;


public List<SelectItem> getListDesCliAyaDesCmdAvcDesProdEnCour1() {
	
	ResultSet res=null;
	
	if(listDesCliAyaDesCmdAvcDesProdEnCour1==null)
		listDesCliAyaDesCmdAvcDesProdEnCour1=new ArrayList<SelectItem>();
	else
		listDesCliAyaDesCmdAvcDesProdEnCour1.clear();
	
	listDesCliAyaDesCmdAvcDesProdEnCour1.add(new SelectItem(0,""));
	
	//res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE' and cli.supprimee=0) order by Nompersonne");
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.etat!='COMMANDE') and p.Idpersonne in (select Idclient from client where supprimee=0) order by Nompersonne");

	try {
		while(res.next())
		{listDesCliAyaDesCmdAvcDesProdEnCour1.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+" "+res.getString("Prenompersonne")+"   "+res.getString("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesCliAyaDesCmdAvcDesProdEnCour1;
}

public void setListDesCliAyaDesCmdAvcDesProdEnCour1(
		List<SelectItem> listDesCliAyaDesCmdAvcDesProdEnCour1) {
	this.listDesCliAyaDesCmdAvcDesProdEnCour1 = listDesCliAyaDesCmdAvcDesProdEnCour1;
}

private List<SelectItem> listDesCmdAvcDesProdEnCour1;

public List<SelectItem> getListDesCmdAvcDesProdEnCour1() {
	
	ResultSet res=null;
	
	if(listDesCmdAvcDesProdEnCour1==null)
		listDesCmdAvcDesProdEnCour1=new ArrayList<SelectItem>();
	else
		listDesCmdAvcDesProdEnCour1.clear();
	
	listDesCmdAvcDesProdEnCour1.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCli1!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	//res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli1+" and Idcmd in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE')");
	//res=Connecteur.Extrairedonnees("select * from commande where Idclient="+this.idCli1+" and Idcmd not in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idclient="+this.idCli1+" and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation ='TERMINEE')");
	res=Connecteur.Extrairedonnees("select * from commande where Idclient="+this.idCli1+" and Idcmd in (select com.Idcmd from commande com,figurer fig where com.Idcmd=fig.Idcmd and fig.etat!='COMMANDE')");

	try {
		while(res.next())
		{
		if((res.getString("bonCmd")!=null)||(res.getFloat("montantPaye")!=0))	
		listDesCmdAvcDesProdEnCour1.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesCmdAvcDesProdEnCour1;
}

public void setListDesCmdAvcDesProdEnCour1(
		List<SelectItem> listDesCmdAvcDesProdEnCour1) {
	this.listDesCmdAvcDesProdEnCour1 = listDesCmdAvcDesProdEnCour1;
}

private List<SelectItem> listDesProdDuneCmdNonEncorTermns1;



public List<SelectItem> getListDesProdDuneCmdNonEncorTermns1() {
	
	ResultSet res=null;
	
	if(listDesProdDuneCmdNonEncorTermns1==null)
		listDesProdDuneCmdNonEncorTermns1=new ArrayList<SelectItem>();
	else
		listDesProdDuneCmdNonEncorTermns1.clear();
	
	listDesProdDuneCmdNonEncorTermns1.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if((this.idComd1!=0)&&(this.idCli1!=0))//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	//res=Connecteur.Extrairedonnees("select fig.Idfigurer,pro.Type,fig.Titre from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE'");
	//res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd1+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd1+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE')");
	
	res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd1+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer not in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd1+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation ='TERMINEE')");

	
	try {
		while(res.next())
		{	
				
			listDesProdDuneCmdNonEncorTermns1.add(new SelectItem(res.getInt("Idfigurer"),res.getString("Type")+" : "+res.getString("Titre")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesProdDuneCmdNonEncorTermns1;
}

public void setListDesProdDuneCmdNonEncorTermns1(
		List<SelectItem> listDesProdDuneCmdNonEncorTermns1) {
	this.listDesProdDuneCmdNonEncorTermns1 = listDesProdDuneCmdNonEncorTermns1;
}

private List<SelectItem> listDesProducteursLibres;
public List<SelectItem> getListDesProducteursLibres() {
	
	
ResultSet res=null;
	
	if(listDesProducteursLibres==null)
		listDesProducteursLibres=new ArrayList<SelectItem>();
	else
		listDesProducteursLibres.clear();
	
	
	System.out.println("changeDateFormat(dteProvDeb) 0"+changeDateFormat(dteProvDeb));
	System.out.println("changeDateFormat(dteProvFin) 0"+changeDateFormat(dteProvFin));
	
/*	listDesProducteursLibres.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");*/	
	
	if((this.idComd1!=0)&&(this.idCli1!=0)&&(this.idP1!=0)&&(this.dteProvDeb!=null)&&(this.dteProvFin!=null))
	//SI ON A SELECTIONNE UN CLIENT, UNE COMMANDE ET UN PRODUIT
	{
	System.out.println("11");
	System.out.println("11");
	System.out.println("changeDateFormat(dteProvDeb) "+changeDateFormat(dteProvDeb));
	System.out.println("changeDateFormat(dteProvFin) "+changeDateFormat(dteProvFin));
	//res=Connecteur.Extrairedonnees("select fig.Idfigurer,pro.Type,fig.Titre from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE'");
	res=Connecteur.Extrairedonnees("select * from personne p,producteur pr where p.Idpersonne=pr.Idproduct and pr.Etatproducteur='DISPONIBLE' and pr.supprimee=0");
	
	listDesProducteursLibres.add(new SelectItem(""));
	
	try {
		while(res.next())
		{	
			System.out.println("ii");
			
			
			//VERIFIONS SI CE PRODUCTEUR N'EST PAS DEJA SUR LA LISTE
			boolean existe=false;
			if((listDesProducteursD1Group!=null)&&(listDesProducteursD1Group.size()>0))
			{	
				int i=0;
				while((i<listDesProducteursD1Group.size())&&(existe==false))
				{	if(res.getInt("Idpersonne")==listDesProducteursD1Group.get(i).getIdPersonne())
					existe=true;
					i++;
				}

			}
			
			
			
			//VERIFIONS SI CE PRODUCTEUR N'EST PAS OCCUPE DANS CET INTERVALLE DE TEMPS
			boolean occupe=false;
			ResultSet r=null;
			r=Connecteur.Extrairedonnees("select * from groupe_producteur gp,groupe g where gp.Idproduct="+res.getInt("Idpersonne")+" and gp.Idgroupe=g.Idgroupe and g.Dte_prov_debut<'"+changeDateFormat(this.dteProvDeb)+"' and g.Dte_prov_fin>'"+changeDateFormat(this.dteProvDeb)+"'");
			if(r.next())
				occupe=true;
			
			r=null;
			r=Connecteur.Extrairedonnees("select * from groupe_producteur gp,groupe g where gp.Idproduct="+res.getInt("Idpersonne")+" and gp.Idgroupe=g.Idgroupe and g.Dte_prov_debut>'"+changeDateFormat(this.dteProvDeb)+"' and g.Dte_prov_fin<'"+changeDateFormat(this.dteProvFin)+"'");
			if(r.next())
				occupe=true;

			r=null;
			r=Connecteur.Extrairedonnees("select * from groupe_producteur gp,groupe g where gp.Idproduct="+res.getInt("Idpersonne")+" and gp.Idgroupe=g.Idgroupe and g.Dte_prov_debut>'"+changeDateFormat(this.dteProvDeb)+"' and g.Dte_prov_debut<'"+changeDateFormat(this.dteProvFin)+"' and g.Dte_prov_fin>'"+changeDateFormat(this.dteProvFin)+"'");
			if(r.next())
				occupe=true;
			
			
			
			if((existe==false)&&(occupe==false))
			listDesProducteursLibres.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+" "+res.getString("Prenompersonne")+"  "+res.getInt("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesProducteursLibres;
}

public void setListDesProducteursLibres(
		List<SelectItem> listDesProducteursLibres) {
	this.listDesProducteursLibres = listDesProducteursLibres;
}

private int idProducteur;
public int getIdProducteur() {
	return idProducteur;
}

public void setIdProducteur(int idProducteur) {
	this.idProducteur = idProducteur;
}

private Date dteProvDeb;
private Date dteProvFin;
public Date getDteProvDeb() {
	return dteProvDeb;
}

public void setDteProvDeb(Date dteProvDeb) {
	this.dteProvDeb = dteProvDeb;
}

public Date getDteProvFin() {
	return dteProvFin;
}

public void setDteProvFin(Date dteProvFin) {
	this.dteProvFin = dteProvFin;
}

private List<Personne> listDesProducteursD1Group;
public List<Personne> getListDesProducteursD1Group() {
	
	
	return listDesProducteursD1Group;
}

public void setListDesProducteursD1Group(
		List<Personne> listDesProducteursD1Group) {
	this.listDesProducteursD1Group = listDesProducteursD1Group;
}

private int idCli1;
private int idComd1;
private int idP1;

public int getIdCli1() {
	return idCli1;
}

public void setIdCli1(int idCli1) {
	this.idCli1 = idCli1;
}

public int getIdComd1() {
	return idComd1;
}

public void setIdComd1(int idComd1) {
	this.idComd1 = idComd1;
}

public int getIdP1() {
	return idP1;
}

public void setIdP1(int idP1) {
	this.idP1 = idP1;
}


private boolean showMessageClient=false;
private boolean showMessageComd=false;
private boolean showMessageProd=false;
private boolean showMessageProducteur=false;



public boolean isShowMessageClient() {
	return showMessageClient;
}

public void setShowMessageClient(boolean showMessageClient) {
	this.showMessageClient = showMessageClient;
}

public boolean isShowMessageComd() {
	return showMessageComd;
}

public void setShowMessageComd(boolean showMessageComd) {
	this.showMessageComd = showMessageComd;
}

public boolean isShowMessageProd() {
	return showMessageProd;
}

public void setShowMessageProd(boolean showMessageProd) {
	this.showMessageProd = showMessageProd;
}

public boolean isShowMessageProducteur() {
	return showMessageProducteur;
}

public void setShowMessageProducteur(boolean showMessageProducteur) {
	this.showMessageProducteur = showMessageProducteur;
}

public void ajouteProducteurDsGroup()
{int j=0;

if(this.idCli1==0)
	{//message="CHOISISSER LE MATERIEL S'IL VOUS PLAIT!!!";
	//System.out.println("Dans idMateriel==0 "+idMateriel);
	this.showMessageClient=true;
	return;
	}

//System.out.println("Dans quantiteMateriel il y a"+this.quantiteMateriel);

if(this.idComd1==0)
	{//message="SAISISSER LA QUANTITE S'IL VOUS PLAIT!!!";
	this.showMessageComd=true;
	return;
	}

if(this.idP1==0)
{//message="SAISISSER LA QUANTITE S'IL VOUS PLAIT!!!";
this.showMessageProd=true;
return;
}

if(this.idProducteur==0)
{//message="SAISISSER LA QUANTITE S'IL VOUS PLAIT!!!";
this.showMessageProducteur=true;
return;
}


if(listDesProducteursD1Group==null)
	listDesProducteursD1Group=new ArrayList<Personne>();


//System.out.println("Size  :"+listDesProdDmd.size());
/*while((j<listDesProducteursD1Group.size())&&(listDesProdDmd.get(j).idMateriel!=this.idMateriel))
{j++;
	}

	if((this.listDesProdDmd.size()==1)&&(this.listDesProdDmd.get(0).idMateriel==this.idMateriel))
	j=0;
//if(listDesProdDmd.size()!=0)
if(j<listDesProdDmd.size())
{listDesProdDmd.get(j).quantiteMateriel+=this.quantiteMateriel;
return;
}



System.out.println("Size  :"+listDesProdDmd.size());*/

ResultSet r=Connecteur.Extrairedonnees("Select * from personne where Idpersonne="+this.idProducteur+"");


Personne p=new Personne();

int num=1;
if((listDesProducteursD1Group.size()>0))
	num=listDesProducteursD1Group.size()+1;

try {
	r.next();
	p.setIdPersonne(r.getInt("Idpersonne"));
	p.setNumPersonne(num);
	p.setNomPersonne(r.getString("Nompersonne"));
	p.setPrenomPersonne(r.getString("Prenompersonne"));
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
listDesProducteursD1Group.add(p);

if(listDesProducteursD1Group.size()>0)
	this.desactiveClient1=true;
	this.desactiveCmd1=true;
	this.desactivePro1=true;

//FIN  DE LA PARTIE POUR LA CREATION DES GROUPES DES EMPLOYES

}






public void listernInPutClient(ActionEvent e)
{	//System.out.println("Dans quantiteMateriel il y a"+this.quantiteMateriel);
	this.showMessageClient=false;
}

public void listernInPutCommande(ActionEvent e)
{	//System.out.println("Dans quantiteMateriel il y a"+this.quantiteMateriel);
	this.showMessageComd=false;
}

public void listernInPutProduit(ActionEvent e)
{	//System.out.println("Dans quantiteMateriel il y a"+this.quantiteMateriel);
	this.showMessageProd=false;
}

public void listernInPutProducteur(ActionEvent e)
{	System.out.println("avant this.showMessageProducteur"+showMessageProducteur);
	this.showMessageProducteur=false;
	System.out.println("apres this.showMessageProducteur"+showMessageProducteur);
}

public void annulerGroupe()
{
if((listDesProducteursD1Group==null)||(listDesProducteursD1Group.size()==0))
{message="IL N'Y A PAS DE GROUPE CREE!!";
return;
	}
if(listDesProducteursD1Group!=null)
	this.listDesProducteursD1Group=null;
this.desactiveClient1=false;
this.desactiveCmd1=false;
this.desactivePro1=false;
message="SUPPRESSION DU GROUPE REUSSIE!!";
	}

private boolean desactiveClient1=false;
private boolean desactiveCmd1=false;
private boolean desactivePro1=false;
public boolean isDesactiveClient1() {
	return desactiveClient1;
}

public void setDesactiveClient1(boolean desactiveClient1) {
	this.desactiveClient1 = desactiveClient1;
}

public boolean isDesactiveCmd1() {
	return desactiveCmd1;
}

public void setDesactiveCmd1(boolean desactiveCmd1) {
	this.desactiveCmd1 = desactiveCmd1;
}

public boolean isDesactivePro1() {
	return desactivePro1;
}

public void setDesactivePro1(boolean desactivePro1) {
	this.desactivePro1 = desactivePro1;
}

private Personne pe=null;
public Personne getPe() {
	return pe;
}

public void setPe(Personne pe) {
	this.pe = pe;
}
public void enleverPersonne(ActionEvent e)
{if(pe==null)//CAS IMPOSSIBLE
{message="PAS DE PERSONNE A SUPPRIMER!!";
return;
	}

int idPe=0;
idPe=pe.getIdPersonne();
int i=1;
//n=Connecteur.Insererdonnees("delete from p where Id="+idm+"");
while((i<=listDesProducteursD1Group.size())&&(listDesProducteursD1Group.get(i-1).getIdPersonne()!=idPe))
i++;

if(listDesProducteursD1Group.get(i-1).getIdPersonne()==idPe)
	listDesProducteursD1Group.remove(i-1);
	
i=1;
if((listDesProducteursD1Group!=null)&&(listDesProducteursD1Group.size()>0))
while((i<=listDesProducteursD1Group.size()))
{listDesProducteursD1Group.get(i-1).setNumPersonne(i);
i++;	
}


pe=null;
	}


//DEBUT DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE GROUPE
public int recuperID()
{
int i=-1;
ResultSet r=null;
r=Connecteur.Extrairedonnees("SELECT Idgroupe FROM groupe order by Idgroupe desc limit 1");
if(r!=null)
	try {
		if(r.next()){
			i=r.getInt("Idgroupe");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return i;
	}
//FIN DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE GROUPE

public String changeDateFormat(Date d)
{if(d==null)
	return "";
 Format f=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
  return f.format(d);
}

public void createGroup() 
{if((listDesProducteursD1Group==null)||(listDesProducteursD1Group.size()==0))
{message="IL N'Y A PAS DE GROUPE CREE!";
return;	
}
Date d=new Date();

if((this.dteProvDeb!=null))
System.out.println("this.dteProvDeb.getTime() "+this.dteProvDeb.getTime());

System.out.println("d.getTime() "+d.getTime());
System.out.println("d.getDate() "+d.getDate());
		
if((this.dteProvDeb!=null))
if(this.dteProvDeb.getDate()<d.getDate())
{message="LA DATE PROVISOIRE DEBUT N'EST PAS VALIDE!!";
return;
	}

if((this.dteProvDeb!=null)&&(this.dteProvFin!=null))
if(this.dteProvDeb.getTime()>this.dteProvFin.getTime())
	{message="LES DATES NE SONT PAS VALIDE!!";
	return;
		}
	

if(this.idP1==0)
{message="PAS DE PRODUIT SELECTIONNE!!";
return;
	}

int n=-1;

FacesContext context=FacesContext.getCurrentInstance();
HttpSession session=(HttpSession) context.getExternalContext().getSession(true);

if((this.dteProvDeb!=null)&&(this.dteProvFin!=null))
n=Connecteur.Insererdonnees("insert into groupe (Idchefprod,Idfigure,Dte_prov_debut,Dte_prov_fin) values ("+session.getAttribute("idPersonneConnectee")+","+this.idP1+",'"+changeDateFormat(this.dteProvDeb)+"','"+changeDateFormat(this.dteProvFin)+"')");

if((this.dteProvDeb==null)&&(this.dteProvFin==null))
	n=Connecteur.Insererdonnees("insert into groupe (Idchefprod,Idfigure) values ("+session.getAttribute("idPersonneConnectee")+","+this.idP1+")");

if((this.dteProvDeb!=null)&&(this.dteProvFin==null))
	n=Connecteur.Insererdonnees("insert into groupe (Idchefprod,Idfigure,Dte_prov_debut) values ("+session.getAttribute("idPersonneConnectee")+","+this.idP1+",'"+changeDateFormat(this.dteProvDeb)+"')");

if((this.dteProvDeb==null)&&(this.dteProvFin!=null))
	n=Connecteur.Insererdonnees("insert into groupe (Idchefprod,Idfigure,Dte_prov_fin) values ("+session.getAttribute("idPersonneConnectee")+","+this.idP1+",'"+changeDateFormat(this.dteProvFin)+"')");


if(n==-1)
{message="OPERATION DE CREATION DU GROUPE ECHOUEE!!";
return;
	}

int idg=-1;
idg=recuperID();
if(idg==-1)
{message="ECHEC DE RECUPERATION DU DERNIER GROUPE!!";
return;
	}
int k=-1;
//int kk=-1;
for(int m=0;m<listDesProducteursD1Group.size();m++)
{
	k=-1;
	k=Connecteur.Insererdonnees("insert into groupe_producteur values ("+idg+","+listDesProducteursD1Group.get(m).getIdPersonne()+")");
	
	//kk=Connecteur.Insererdonnees("update producteur set Etatproducteur='OCCUPE' where Idproduct="+listDesProducteursD1Group.get(m).getIdPersonne()+"");
}

if(k!=-1)
{
message="OPERATION REUSSIE!!";

this.desactiveClient1=false;
this.desactiveCmd1=false;
this.desactivePro1=false;


//SI CE PRODUIT A L'ETAT 'EN ATTENTE DE TRAITEMENT'
//METTONS-LE DANS L'ETAT 'EN COURS DE TRAITEMENT'
ResultSet re=null;
re=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+this.idP1+" and etat='EN ATTENTE DE TRAITEMENT'");
try {
	if(re.next())
	{
	int ne=-1;
	ne=Connecteur.Insererdonnees("update figurer set etat='EN COURS DE TRAITEMENT' where Idfigurer="+this.idP1+"");
	}
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
//





listDesProducteursD1Group.clear();
}
else
message="OPERATION ECHOUEE!!";

}
/*private List<SelectItem> listDesH1;
private List<SelectItem> listDesH2;
private List<SelectItem> listDesMin1;
private List<SelectItem> listDesMin2;
 
public List<SelectItem> getListDesH1() {
	
	if(listDesH1==null)
		listDesH1=new ArrayList<SelectItem>();
	else 
	listDesH1.clear();
	for(int i=0;i<25;i++)
	listDesH1.add(new SelectItem(i,""+i+""));
	
	return listDesH1;
}

public void setListDesH1(List<SelectItem> listDesH1) {
	this.listDesH1 = listDesH1;
}




public List<SelectItem> getListDesH2() {
	
	if(listDesH2==null)
		listDesH2=new ArrayList<SelectItem>();
	else 
		listDesH2.clear();
	for(int i=0;i<25;i++)
		listDesH2.add(new SelectItem(i,""+i+""));
	
	return listDesH2;
}

public void setListDesH2(List<SelectItem> listDesH2) {
	this.listDesH2 = listDesH2;
}

public List<SelectItem> getListDesMin1() {
	
	if(listDesMin1==null)
		listDesMin1=new ArrayList<SelectItem>();
	else 
		listDesMin1.clear();
	for(int i=0;i<60;i++)
		listDesMin1.add(new SelectItem(i,""+i+""));
	
	return listDesMin1;
}

public void setListDesMin1(List<SelectItem> listDesMin1) {
	this.listDesMin1 = listDesMin1;
}

public List<SelectItem> getListDesMin2() {
	
	if(listDesMin2==null)
		listDesMin2=new ArrayList<SelectItem>();
	else 
		listDesMin2.clear();
	for(int i=0;i<60;i++)
		listDesMin2.add(new SelectItem(i,""+i+""));
	
	return listDesMin2;
}

public void setListDesMin2(List<SelectItem> listDesMin2) {
	this.listDesMin2 = listDesMin2;
}*/

//DEBUT DE LA PARTIE POUR LE PAYEMENT
private List<SelectItem> listDesClientsAyaDesProTerminMNonPay;
public List<SelectItem> getListDesClientsAyaDesProTerminMNonPay() {
	
	ResultSet res=null;
	
	if(listDesClientsAyaDesProTerminMNonPay==null)
		listDesClientsAyaDesProTerminMNonPay=new ArrayList<SelectItem>();
	else
		listDesClientsAyaDesProTerminMNonPay.clear();
	
	listDesClientsAyaDesProTerminMNonPay.add(new SelectItem(0,""));
	
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation ='TERMINEE'and fig.Nbre_tot_exemplr>fig.Nbre_exemplr_paye) and p.Idpersonne in (select Idclient from client where supprimee=0) order by Nompersonne");

	try {
		while(res.next())
		{listDesClientsAyaDesProTerminMNonPay.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+" "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesClientsAyaDesProTerminMNonPay;
}

public void setListDesClientsAyaDesProTerminMNonPay(
		List<SelectItem> listDesClientsAyaDesProTerminMNonPay) {
	this.listDesClientsAyaDesProTerminMNonPay = listDesClientsAyaDesProTerminMNonPay;
}
private List<SelectItem> listDesClientsAyaDesProAPay;

public List<SelectItem> getListDesClientsAyaDesProAPay() {
	
	
	ResultSet res=null;
	
	if(listDesClientsAyaDesProAPay==null)
		listDesClientsAyaDesProAPay=new ArrayList<SelectItem>();
	else
		listDesClientsAyaDesProAPay.clear();
	
	listDesClientsAyaDesProAPay.add(new SelectItem(0,""));
	
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.etat!='PAYE' and fig.Nbre_tot_exemplr>fig.Nbre_exemplr_paye) and p.Idpersonne in (select Idclient from client where supprimee=0) order by Nompersonne");

	try {
		while(res.next())
		{listDesClientsAyaDesProAPay.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+" "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return listDesClientsAyaDesProAPay;
}

public void setListDesClientsAyaDesProAPay(
		List<SelectItem> listDesClientsAyaDesProAPay) {
	this.listDesClientsAyaDesProAPay = listDesClientsAyaDesProAPay;
}

private int idCli2;
public int getIdCli2() {
	return idCli2;
}

public void setIdCli2(int idCli2) {
	this.idCli2 = idCli2;
}


private List<SelectItem> listDesCmdAvcDesProdTermnMNonPaye;
public List<SelectItem> getListDesCmdAvcDesProdTermnMNonPaye() {
	
	ResultSet res=null;
	
	if(listDesCmdAvcDesProdTermnMNonPaye==null)
		listDesCmdAvcDesProdTermnMNonPaye=new ArrayList<SelectItem>();
	else
		listDesCmdAvcDesProdTermnMNonPaye.clear();
	
	listDesCmdAvcDesProdTermnMNonPaye.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCli2!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli2+" and Idcmd in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation ='TERMINEE' and fig.Nbre_tot_exemplr>fig.Nbre_exemplr_paye)");

	try {
		while(res.next())
		{listDesCmdAvcDesProdTermnMNonPaye.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesCmdAvcDesProdTermnMNonPaye;
}

public void setListDesCmdAvcDesProdTermnMNonPaye(
		List<SelectItem> listDesCmdAvcDesProdTermnMNonPaye) {
	this.listDesCmdAvcDesProdTermnMNonPaye = listDesCmdAvcDesProdTermnMNonPaye;
}


private List<SelectItem> listDesCmdAvcDesProdAPayer;
public List<SelectItem> getListDesCmdAvcDesProdAPayer() {
	
	
	ResultSet res=null;
	
	if(listDesCmdAvcDesProdAPayer==null)
		listDesCmdAvcDesProdAPayer=new ArrayList<SelectItem>();
	else
		listDesCmdAvcDesProdAPayer.clear();
	
	listDesCmdAvcDesProdAPayer.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCli2!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli2+" and Idcmd in (select com.Idcmd from commande com,figurer fig where com.Idcmd=fig.Idcmd and fig.etat!='PAYE' and fig.Nbre_tot_exemplr>fig.Nbre_exemplr_paye)");

	try {
		while(res.next())
		{listDesCmdAvcDesProdAPayer.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	return listDesCmdAvcDesProdAPayer;
}

public void setListDesCmdAvcDesProdAPayer(
		List<SelectItem> listDesCmdAvcDesProdAPayer) {
	this.listDesCmdAvcDesProdAPayer = listDesCmdAvcDesProdAPayer;
}

private int idComd2;
public int getIdComd2() {
	return idComd2;
}

public void setIdComd2(int idComd2) {
	this.idComd2 = idComd2;
}

private float montantTotal;
public float getMontantTotal() {
	return montantTotal;
}

public void setMontantTotal(float montantTotal) {
	this.montantTotal = montantTotal;
}


private List<Produit> listDesProdPourLePayement;
public List<Produit> getListDesProdPourLePayement() {
	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	if(listDesProdPourLePayement==null)
		listDesProdPourLePayement=new ArrayList<Produit>();
	else
		listDesProdPourLePayement.clear();
	
	System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	if((this.idCli2==0)||(this.idComd2==0))
	{	
		listDesProdPourLePayement.clear();
		return listDesProdPourLePayement;
	}
	
	System.out.println("cccccccccccccccccccccccccccccccccccccc");
	ResultSet res=null,res1=null;
	
	//res=Connecteur.Extrairedonnees("select * from commande c,figurer f,produits p, historique_etapes h where c.Idclient="+this.idCli2+" and c.Idcmd="+this.idComd2+" and c.Idcmd=f.Idcmd and f.Idfigurer=h.Idfigure and p.Idprod=f.Idprod and h.Designation='TERMINEE'");
	//res=Connecteur.Extrairedonnees("select * from commande c,figurer f,produits p where c.Idclient="+this.idCli2+" and c.Idcmd="+this.idComd2+" and p.Idprod=f.Idprod and f.etat!='PAYE'");
	res=Connecteur.Extrairedonnees("select * from commande c,figurer f where c.Idclient="+this.idCli2+" and c.Idcmd="+this.idComd2+" and c.Idcmd=f.Idcmd and f.etat!='PAYE'");

	int n=1;
	try {
		//if(res.next())
		//{System.out.println("dddddddddddddddddddddddddddddddddddddddddddd");
			while(res.next())
			{	System.out.println("==================================");
				System.out.println("==================================");
				Produit p=new Produit();
			    p.setNum(n);
			    p.setIdProduit(res.getInt("Idfigurer"));
			    
			    
			    //RECUPERONS LE TYPE DU PRODUIT
			    res1=Connecteur.Extrairedonnees("select * from produits where Idprod="+res.getInt("Idprod"));
			    if(res1.next())
			    {
			    	p.setNomProduit(res1.getString("Type"));
			    }
			    
			    res1=null;
			    
			    
			    p.setTitre(res.getString("Titre"));
			    p.setNbr_ex_dmd(res.getInt("Nbre_tot_exemplr"));
			    p.setNbr_ex_djaPaye(res.getInt("Nbre_exemplr_paye"));
			    p.setNbr_ex_tot_non_paye(res.getInt("Nbre_tot_exemplr")-res.getInt("Nbre_exemplr_paye"));
			    if(res.getInt("Nbre_tot_exemplr")<=res.getInt("Nbre_exemplr_paye"))
			    	p.setIcon("/image/updateBtn.png");
			    else
			    	p.setIcon("/image/close.png");
			    
			    p.setMontantTotalProd(res.getFloat("Montant_a_paye_pr_1_ex"));
			    p.setNbr_ex_paye(0);
			    p.setShowMessageQuantite(false);
			    n++;
			    listDesProdPourLePayement.add(p);
			}
			
		//}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesProdPourLePayement;
}

public void setListDesProdPourLePayement(List<Produit> listDesProdPourLePayement) {
	this.listDesProdPourLePayement = listDesProdPourLePayement;
}



public void savePayement()
{
	if((listDesProdPourLePayement==null)||(listDesProdPourLePayement.size()<1))
	{
		message="AUCUN PAYEMENT N'A ETE EFFECTUE!!";
		return;
	}
	
	boolean enregistrer=false;
	
	FacesContext context=FacesContext.getCurrentInstance();
	HttpSession session=(HttpSession) context.getExternalContext().getSession(true);
	
	
	for(int i=0;i<listDesProdPourLePayement.size();i++)
	{
		if(listDesProdPourLePayement.get(i).getNbr_ex_paye()>0)
			enregistrer=true;
	}

	if(enregistrer==false)
	{message="VOUS N'AVEZ SAISI AUCUNE QUANTITE!!";
	return;
	}
	
	enregistrer=true;
	int ii=0;
	
	while((ii<listDesProdPourLePayement.size())&&(enregistrer==true))
	{
		if(listDesProdPourLePayement.get(ii).getNbr_ex_paye()>listDesProdPourLePayement.get(ii).getNbr_ex_tot_non_paye())
		{
			enregistrer=false;
		}
		
		ii++;
	}
	
	if(enregistrer==false)
	{
		message="IL Y A UNE QUANTITE TROP GRANDE!!";
		return;
	}
	
	int n=-1;
	n=Connecteur.Insererdonnees("insert into payement (Idcmd,Idcaissier,Dte_payement,Montant_paye,Livre) values ("+this.idComd2+","+session.getAttribute("idPersonneConnectee")+",now(),"+this.montantTotal+",0)");
	
	if(n==-1)
	{
		message="ENREGISTREMENT DU PAYEMENT ECHOUE!!";
		return;
	}
	
	//SI IL Y A SUR CETTE COMMANDE AU MOINS UN PRODUIT AVEC L'ETAT COMMANDE
	//CE PAYEMENT EST UNE AVANCE POUR TOUS LES PRODUITS DE CETTE COMMANDE
	ResultSet re=null;
	re=Connecteur.Extrairedonnees("select * from figurer where Idcmd="+this.idComd2+" and etat='COMMANDE'");
	try {
		if(re.next())
		{
		int ne=-1;
		ne=Connecteur.Insererdonnees("update figurer set etat='EN ATTENTE DE TRAITEMENT' where Idcmd="+this.idComd2+"");
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//
	
int idpaye=0;
idpaye=recuperIDP();

if(idpaye==0)
{message="ECHEC DE RECUPERATION DU PAYEMENT";
return;
	}

int m=-1,mm=-1;
ResultSet r=null;
for(int k=0;k<listDesProdPourLePayement.size();k++)
{
if(listDesProdPourLePayement.get(k).getNbr_ex_paye()>0)
{m=Connecteur.Insererdonnees("insert into prod_paye (Idpayement,Designation,Qtite_paye) values ("+idpaye+",'"+listDesProdPourLePayement.get(k).getTitre()+"',"+listDesProdPourLePayement.get(k).getNbr_ex_paye()+")");


r=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+listDesProdPourLePayement.get(k).getIdProduit()+"");

try {
	if(r.next())
	{
	int nbre_ex=0,n1=0;
	nbre_ex=r.getInt("Nbre_exemplr_paye");
	nbre_ex=nbre_ex+listDesProdPourLePayement.get(k).getNbr_ex_paye();
	//METTONS A JOUR LE NOMBRE D'EXEMPLAIRE PAYE
	mm=Connecteur.Insererdonnees("update figurer set Nbre_exemplr_paye="+nbre_ex+" where Idfigurer="+listDesProdPourLePayement.get(k).getIdProduit()+"");
		
	//
	n1=r.getInt("Nbre_tot_exemplr");
	if(nbre_ex==n1)//ON VIENT DE PAYER TOUS LES EXEMPLAIRES
	{
		r=null;
		r=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+listDesProdPourLePayement.get(k).getIdProduit()+" and etat='TERMINE'");
		if(r.next())//SI CE PAYEMENT N'EST PAS UNE AVANCE, DONC, ON VIENT DE PAYER TOUS LES PRODUITS POUR LES RECUPERER
		{
			mm=-1;
			mm=Connecteur.Insererdonnees("update figurer set etat='PAYE' where Idfigurer="+listDesProdPourLePayement.get(k).getIdProduit()+"");

		}
	}
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}


r=null;
r=Connecteur.Extrairedonnees("select * from commande where Idcmd="+this.idComd2+"");
try {
	if(r.next())
	{
	this.montantTotal+=r.getFloat("montantPaye");
	mm=-1;
	mm=Connecteur.Insererdonnees("update commande set montantPaye="+this.montantTotal+" where Idcmd="+this.idComd2+"");
		}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
if(m==-1)
	message="ECHEC DE L'OPERATION!!";
else
	message="OPERATION REUSSIE!!";

this.montantTotal=0;
this.listDesProdPourLePayement.clear();
	}


public void calculMontantAPaye(ActionEvent e)
{System.out.println("DEBUT DE LA FONCTION QUI ECOUTE!");
this.montantTotal=0;
boolean ok=true;
int j=0;

while((j<listDesProdPourLePayement.size())&&(ok==true))
{if(listDesProdPourLePayement.get(j).getNbr_ex_paye()>listDesProdPourLePayement.get(j).getNbr_ex_tot_non_paye())
	ok=false;
j++;
}

/*for(int j=0;j<listDesProdPourLePayement.size();j++)
	{if(listDesProdPourLePayement.get(j).getNbr_ex_paye()>listDesProdPourLePayement.get(j).getNbr_ex_tot_non_paye())
		{listDesProdPourLePayement.get(j).setShowMessageQuantite(true);
		
		ok=false;
		}
	else
		listDesProdPourLePayement.get(j).setShowMessageQuantite(false);
	}*/
	
if(ok==false)
{this.showErreurDeQte=true;
	return;
}

this.showErreurDeQte=false;

/*if(listDesProdPourLePayement.size()<1)
{message="CAS IMPOSSIBLE!!";
return;
	}*/

for(int i=0;i<listDesProdPourLePayement.size();i++)
{System.out.println("this.montantTotal avant ajout "+this.montantTotal);
	this.montantTotal=this.montantTotal+(listDesProdPourLePayement.get(i).getMontantTotalProd()*listDesProdPourLePayement.get(i).getNbr_ex_paye());
System.out.println("this.montantTotal apres ajout "+this.montantTotal);	
}

	}


private boolean showErreurDeQte=false;
public boolean isShowErreurDeQte() {
	return showErreurDeQte;
}

public void setShowErreurDeQte(boolean showErreurDeQte) {
	this.showErreurDeQte = showErreurDeQte;
}

//DEBUT DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE payement
public int recuperIDP()
{
int i=-1;
ResultSet r=null;
r=Connecteur.Extrairedonnees("SELECT Idpayement FROM payement order by Idpayement desc limit 1");
if(r!=null)
	try {
		if(r.next()){
			i=r.getInt("Idpayement");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return i;
	}
//FIN DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE payement

public void listernChangeCmd(ActionEvent e)
{this.montantTotal=0;
	}

//FIN DE LA PARTIE POUR LE PAYEMENT



//DEBUT DE LA 2IEME ALTERNATIVE POUR MARQUER L'ETAPE A LAQUELLE SE TROUVE
//UN PRODUIT

private List<SelectItem> listDesProdEnCours;
public List<SelectItem> getListDesProdEnCours() {
	
	
	ResultSet res1,res2=null;
	
	if(listDesProdEnCours==null)
		listDesProdEnCours=new ArrayList<SelectItem>();
	else
		listDesProdEnCours.clear();
	
	listDesProdEnCours.add(new SelectItem(0,""));


	//res1=Connecteur.Extrairedonnees("select * from figurer f where f.Idfigurer not in(select fig.Idfigurer from figurer fig,historique_etapes hist where fig.Idfigurer=hist.Idfigure and hist.Designation ='TERMINEE')");
	res1=Connecteur.Extrairedonnees("select * from figurer f where f.etat!='COMMANDE' and f.etat!='TERMINE'");

	try {
		while(res1.next())
		{
			//CHERCHONS LE TYPE DU PRODUIT
			res2=Connecteur.Extrairedonnees("select * from produits where Idprod="+res1.getInt("Idprod")+"");
			if(res2.next())
			{
		listDesProdEnCours.add(new SelectItem(res1.getInt("Idfigurer"),""+res2.getString("Type")+"  "+res1.getString("Titre")));
			}
				
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return listDesProdEnCours;
}





public void setListDesProdEnCours(List<SelectItem> listDesProdEnCours) {
	this.listDesProdEnCours = listDesProdEnCours;
}


private List<SelectItem> dteProvDebEtFin;
public List<SelectItem> getDteProvDebEtFin() {
	
	
	ResultSet res1=null;
	
	if(dteProvDebEtFin==null)
		dteProvDebEtFin=new ArrayList<SelectItem>();
	else
		dteProvDebEtFin.clear();
	
	dteProvDebEtFin.add(new SelectItem(0,""));

if(this.idP!=0)
{
	res1=Connecteur.Extrairedonnees("select * from groupe where Idfigure="+this.idP+" and Dte_fin is null");
	try {
		while(res1.next())
		{

				dteProvDebEtFin.add(new SelectItem(res1.getInt("Idgroupe"),""+res1.getDate("Dte_prov_debut")+" - "+res1.getDate("Dte_prov_fin")+""));
		
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}	
	
	return dteProvDebEtFin;
}

public void setDteProvDebEtFin(List<SelectItem> dteProvDebEtFin) {
	this.dteProvDebEtFin = dteProvDebEtFin;
}

private String dteProvDeF;
public String getDteProvDeF() {
	return dteProvDeF;
}

public void setDteProvDeF(String dteProvDeF) {
	this.dteProvDeF = dteProvDeF;
}


private List<Personne> listDesMbresDuGpe;
public List<Personne> getListDesMbresDuGpe() {
	
	
	ResultSet res=null;
	
	if(listDesMbresDuGpe==null)
		listDesMbresDuGpe=new ArrayList<Personne>();
	else
		listDesMbresDuGpe.clear();

	System.out.println("////////0");
if(this.idg!=0)
	
	System.out.println("////////01");
	
	
	res=Connecteur.Extrairedonnees("select * from groupe g,groupe_producteur gp,producteur p,personne pe where g.Idgroupe="+this.idg+" and g.Idgroupe=gp.Idgroupe and gp.Idproduct=p.Idproduct and p.Idproduct=pe.Idpersonne and p.supprimee=0");
	int num=1;
	try {
		while(res.next())
		{System.out.println("////////1");
		Personne p=new Personne();
		p.setNumPersonne(num);
		p.setNomPersonne(res.getString("Nompersonne"));
		p.setPrenomPersonne(res.getString("Prenompersonne"));
		p.setIdPersonne(res.getInt("Idpersonne"));
		listDesMbresDuGpe.add(p);
		
	System.out.println("////////2");	
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return listDesMbresDuGpe;
}

public void setListDesMbresDuGpe(List<Personne> listDesMbresDuGpe) {
	this.listDesMbresDuGpe = listDesMbresDuGpe;
}

private int idg;
public int getIdg() {
	return idg;
}

public void setIdg(int idg) {
	this.idg = idg;
}

private List<SelectItem> listDesEtapNonEncorMarqPrUnProd1;








//==============

//System.out.println("_@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@_");
public List<SelectItem> getListDesEtapNonEncorMarqPrUnProd1() {
	
	
	ResultSet res=null,r0=null,r1=null;

	if(listDesEtapNonEncorMarqPrUnProd1==null)
		listDesEtapNonEncorMarqPrUnProd1=new ArrayList<SelectItem>();
	else
		listDesEtapNonEncorMarqPrUnProd1.clear();

	listDesEtapNonEncorMarqPrUnProd1.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	

	if(this.idP!=0)//SI ON A SELECTIONNE  UN PRODUIT
	{
	System.out.println("11");
	System.out.println("11");
	//res=Connecteur.Extrairedonnees("select et.Designation from figurer fig,chemin che,chemin_etapes c_e,etapes et where fig.Idcmd="+this.idComd+" and fig.Idprod="+this.idP+" and fig.Idchemin=che.Idchemin and che.Idchemin=c_e.idchemin and c_e.Idetape=et.Idetape");
	System.out.println("LE ID DESIGNANT UN PRODUIT D'UNE COMMANDE EST-> "+this.idP);
	res=Connecteur.Extrairedonnees("select et.Designation from figurer fig,chemin che,chemin_etapes c_e,etapes et where fig.Idfigurer="+this.idP+" and fig.Idchemin=che.Idchemin and che.Idchemin=c_e.idchemin and c_e.Idetape=et.Idetape");	
	try {
	System.out.println("@0");
	int idf=this.idP;
		while(res.next())
		{
		//int idf=this.idP;
		
		//idf=recupereIdfigure(this.idComd,this.idP);
		//if(idf==0)
			//break;
		//else
		//{
		r0=Connecteur.Extrairedonnees("select * from historique_etapes where Idfigure="+idf+" and Designation='"+res.getString("Designation")+"'");
		if(!r0.next())
		{
			listDesEtapNonEncorMarqPrUnProd1.add(new SelectItem(res.getString("Designation")));
		System.out.println("@1");
		}
		
		//}
		
		}
		
		
		r1=Connecteur.Extrairedonnees("select * from historique_etapes where Idfigure="+idf+" and Designation='TERMINEE'");
		if(!r1.next())
		{
			listDesEtapNonEncorMarqPrUnProd1.add(new SelectItem("TERMINE"));
		//System.out.println("@1");
		}
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	
	
	return listDesEtapNonEncorMarqPrUnProd1;
}

public void setListDesEtapNonEncorMarqPrUnProd1(
		List<SelectItem> listDesEtapNonEncorMarqPrUnProd1) {
	this.listDesEtapNonEncorMarqPrUnProd1 = listDesEtapNonEncorMarqPrUnProd1;
}


private String etap1;

public String getEtap1() {
	return etap1;
}

public void setEtap1(String etap1) {
	this.etap1 = etap1;
}




public void marquerEtape1()
{
int idFig=0;
ResultSet r=null;

if(this.idP==0)
{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
return;
	}

if((this.etap1.length()<1)||(this.etap1.equalsIgnoreCase("0")||(this.etap1==null)))
{message="SELECTIONNER UNE ETAPE S'IL VOUS PLAIT!!";
return;
	}

System.out.println("this.etap1 "+this.etap1);
System.out.println("this.etap1 "+this.etap1);


FacesContext context=FacesContext.getCurrentInstance();
HttpSession session=(HttpSession) context.getExternalContext().getSession(true);

//r=Connecteur.Extrairedonnees("select Idfigurer from figurer where Idcmd="+this.idComd+" and Idprod="+this.idP+"");

try {
	//r.next();
	//idFig=r.getInt("Idfigurer");
	idFig=this.idP;
	//if(idFig==0)
		//return;
	
	int n=-1;
	
	r=null;
	r=Connecteur.Extrairedonnees("select * from historique_etapes where Idfigure="+idFig+" and Designation='"+this.etap1+"'");
	
	if(r.next())
	{
		message="CETTE ETAPE EST DEJA MARQUEE!!";
		return;
	}
	
	
	//SI C'EST LE GERANT QUI VEUT MARQUER L'ETAPE
	//IL FAUT QU'IL SOIT D'ABORD DANS LA TABLE DES PRODUCTEURS
	r=null;
	r=Connecteur.Extrairedonnees("select * from producteur where Idproduct="+session.getAttribute("idPersonneConnectee"));
	if(!r.next())//IL FAUT D'ABORD L'ENREGISTRER DANS LA TABLE DES PRODUCTEURS
	{
		n=-1;
		n=Connecteur.Insererdonnees("insert into producteur(Idproduct,supprimee) values ("+session.getAttribute("idPersonneConnectee")+",1)");
	
		if(n==-1)
		{
			message="BIZARRE!CETTE PERSONNE N'EST PAS UN PRODUCTEUR!!";
			return;
		}
	}
	

	
	
	
	n=-1;
	n=Connecteur.Insererdonnees("insert into historique_etapes (Idfigure,Idproducteur,Date,Designation) values ("+idFig+","+session.getAttribute("idPersonneConnectee")+",now(),'"+this.etap1+"')");
	//n=Connecteur.Insererdonnees("insert into historique_etapes (Idfigure,Idproducteur,Date,Designation) values ("+idFig+",12,now(),'"+this.etap1+"')");
	
	//this.idPersonneConnecte=0;
	
	if(n==-1)
	{
		message="ECHEC D'INSERTION!!";
		return;
	}
	
	message="INSERTION REUSSIE!!";
	
	
	
	
	//SI CE PRODUIT A L'ETAT 'EN ATTENTE DE TRAITEMENT'
	//METTONS-LE DANS L'ETAT 'EN COURS DE TRAITEMENT'
	ResultSet re=null;
	re=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+this.idP+" and etat='EN ATTENTE DE TRAITEMENT'");
	try {
		if(re.next())
		{
		int ne=-1;
		ne=Connecteur.Insererdonnees("update figurer set etat='EN COURS DE TRAITEMENT' where Idfigurer="+this.idP+"");
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//
	
	if(this.etap1.equalsIgnoreCase("TERMINE"))
	{
		int ne=-1;
		ne=Connecteur.Insererdonnees("update figurer set etat='TERMINE' where Idfigurer="+this.idP+"");
		
		
		//S'IL A ETE PAYE D'AVANCE EN TOTALITE ON LE MET DIRECTEMENT A L'ETAT PAYE
		int n1=0,n2=0;
		re=null;
		re=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+this.idP+"");
		if(re.next())
		{
			n1=re.getInt("Nbre_tot_exemplr");
			n2=re.getInt("Nbre_exemplr_paye");
			if(n1==n2)
			{
				ne=-1;
				ne=Connecteur.Insererdonnees("update figurer set etat='PAYE' where Idfigurer="+this.idP+"");

			}
		}
		
	}
		
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}

public void marquerDbutTacheDuGpe()
{
	if(this.idP==0)
	{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
	return;
		}
	
	if(this.idg==0)
	{
	message="SELECTIONNER LE DELAIT ACCORDE AU GROUPE S'IL VOUS PLAIT!!";
	return;
	}
	
	ResultSet res=null;
	
	res=Connecteur.Extrairedonnees("select * from groupe where Idgroupe="+this.idg+" and Idfigure="+this.idP+" and Dte_debut is not null");
	try {
		if(res.next())
		{message="CE GROUPE A DEJA MARQUE LE COMMENCEMENT DE CETTE TACHE!!";
		return;	
		}
		
	
	int n=-1;
	n=Connecteur.Insererdonnees("update groupe set Dte_debut=now() where Idgroupe="+this.idg+" and Idfigure="+this.idP+"");
	if(n!=-1)
		message="OPERATION DE MARQUE DE DEBUT REUSSIE!!";
	else
		message="OPERATION DE MARQUE DE DEBUT ECHOUEE!!";
	
	
	
	//SI CE PRODUIT A L'ETAT 'EN ATTENTE DE TRAITEMENT'
	//METTONS-LE DANS L'ETAT 'EN COURS DE TRAITEMENT'
	ResultSet re=null;
	re=Connecteur.Extrairedonnees("select * from figurer where Idfigurer="+this.idP+" and etat='EN ATTENTE DE TRAITEMENT'");
	try {
		if(re.next())
		{
		int ne=-1;
		ne=Connecteur.Insererdonnees("update figurer set etat='EN COURS DE TRAITEMENT' where Idfigurer="+this.idP+"");
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//
	
	
	
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	
	
	}
public void marquerFinTacheDuGpe()
{
	if(this.idP==0)
	{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
	return;
		}
	
	if(this.idg==0)
	{
	message="SELECTIONNER LE DELAIT ACCORDE AU GROUPE S'IL VOUS PLAIT!!";
	return;
	}
	
	ResultSet res=null;
	
	res=Connecteur.Extrairedonnees("select * from groupe where Idgroupe="+this.idg+" and Idfigure="+this.idP+" and Dte_debut is null");
	try {
		if(res.next())
		{message="CE GROUPE N'A PAS MARQUE LE DEBUT DE LEUR TACHE!!";
		return;	
		}
	
	
	res=null;
	res=Connecteur.Extrairedonnees("select * from groupe where Idgroupe="+this.idg+" and Idfigure="+this.idP+" and Dte_fin is not null");
	
		if(res.next())
		{message="CE GROUPE A DEJA MARQUE LA FIN DE LEUR TACHE!!";
		return;	
		}
		
	
	int n=-1;
	n=Connecteur.Insererdonnees("update groupe set Dte_fin=now() where Idgroupe="+this.idg+" and Idfigure="+this.idP+"");
	if(n!=-1)
		message="OPERATION DE MARQUE DE FIN REUSSIE!!";
	else
		message="OPERATION DE MARQUE DE FIN ECHOUEE!!";
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}	


private CheminOuEtape etape;

public CheminOuEtape getEtape() {
	return etape;
}

public void setEtape(CheminOuEtape etape) {
	this.etape = etape;
}

public void supprimerEtape(ActionEvent e)
{
if(this.etape==null)//CAS IMPOSSIBLE
{message="CAS IMPOSSIBLE";
return;
	}

int n=-1;
n=Connecteur.Insererdonnees("delete from historique_etapes where Idhisto_etape="+this.etape.getIdHisto()+"");
if(n!=-1)
	message="SUPPRESSION REUSSIE!!";
else
	message="SUPPRESSION ECHOUEE!!";

	}

public void annulerDteDebut()
{
	if(this.idP==0)
	{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
	return;
		}
	
	if(this.idg==0)
	{
	message="SELECTIONNER LE DELAIT ACCORDE AU GROUPE S'IL VOUS PLAIT!!";
	return;
	}
	
	ResultSet res=null;
	
	res=Connecteur.Extrairedonnees("select * from groupe where Idgroupe="+this.idg+" and Idfigure="+this.idP+" and Dte_debut is null");
	
		try {
			if(res.next())
			{message="LA DATE DE DEBUT DE LA TACHE N'EST PAS MARQUEE POUR CE GROUPE!!";
			return;	
			}
			
			
			int n=-1;
			n=Connecteur.Insererdonnees("update groupe set Dte_debut=null where Idgroupe="+this.idg+" and Idfigure="+this.idP+"");
			if(n!=-1)
				message="OPERATION D'ANNULATION DU DEBUT REUSSIE!!";
			else
				message="OPERATION D'ANNULATION DU DEBUT ECHOUEE!!";	
		
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}



//FIN DE LA 2IEME ALTERNATIVE POUR MARQUER L'ETAPE A LAQUELLE SE TROUVE
//UN PRODUIT


//DEBUT DE LA PARTIE POUR LA LIVRAISON DES PRODUITS PAYES

private int idCli3;
public int getIdCli3() {
	return idCli3;
}

public void setIdCli3(int idCli3) {
	this.idCli3 = idCli3;
}

private List<SelectItem> listDesCliAyantDesProdPayeMNoLivre;
public List<SelectItem> getListDesCliAyantDesProdPayeMNoLivre() {
	
	

	
	ResultSet res=null;
	
/*	this.idCmd3=0;
	this.idPayet=0;*/
	
	if(listDesCliAyantDesProdPayeMNoLivre==null)
		listDesCliAyantDesProdPayeMNoLivre=new ArrayList<SelectItem>();
	else
		listDesCliAyantDesProdPayeMNoLivre.clear();
	
	
	
	listDesCliAyantDesProdPayeMNoLivre.add(new SelectItem(0,""));
	
	res=Connecteur.Extrairedonnees("select * from personne pe where pe.Idpersonne in (select c.Idclient from commande c,payement p where c.Idcmd=p.Idcmd and Livre=0) and pe.Idpersonne in(select Idclient from client where supprimee=0) order by Nompersonne");
	//res=Connecteur.Extrairedonnees("select * from personne pe where pe.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.etat!='PAYE') and pe.Idpersonne in(select Idclient from client where supprimee=0) order by Nompersonne");

	try {
		while(res.next())
		{listDesCliAyantDesProdPayeMNoLivre.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"   "+res.getString("Prenompersonne")+"  "+res.getInt("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

	
	
	
	return listDesCliAyantDesProdPayeMNoLivre;
}

public void setListDesCliAyantDesProdPayeMNoLivre(
		List<SelectItem> listDesCliAyantDesProdPayeMNoLivre) {
	this.listDesCliAyantDesProdPayeMNoLivre = listDesCliAyantDesProdPayeMNoLivre;
}

private int idCmd3;
public int getIdCmd3() {
	return idCmd3;
}

public void setIdCmd3(int idCmd3) {
	this.idCmd3 = idCmd3;
}

private List<SelectItem> listDesCmdAvecDesProdPayeMNoLivre;
public List<SelectItem> getListDesCmdAvecDesProdPayeMNoLivre() {
	
	ResultSet res=null;
	
	if(listDesCmdAvecDesProdPayeMNoLivre==null)
		listDesCmdAvecDesProdPayeMNoLivre=new ArrayList<SelectItem>();
	else
		listDesCmdAvecDesProdPayeMNoLivre.clear();
	
	listDesCmdAvecDesProdPayeMNoLivre.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCli3!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli3+" and Idcmd in (select c.Idcmd from commande c,payement p where c.Idcmd=p.Idcmd and Livre=0)");

	try {
		while(res.next())
		{listDesCmdAvecDesProdPayeMNoLivre.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	return listDesCmdAvecDesProdPayeMNoLivre;
}

public void setListDesCmdAvecDesProdPayeMNoLivre(
		List<SelectItem> listDesCmdAvecDesProdPayeMNoLivre) {
	this.listDesCmdAvecDesProdPayeMNoLivre = listDesCmdAvecDesProdPayeMNoLivre;
}


private int idPayet;
public int getIdPayet() {
	return idPayet;
}

public void setIdPayet(int idPayet) {
	this.idPayet = idPayet;
}

private List<SelectItem> listDesPayetNoLivres;
public List<SelectItem> getListDesPayetNoLivres() {

	
	ResultSet res=null;
	
	this.pt=null;
	
	if(listDesPayetNoLivres==null)
		listDesPayetNoLivres=new ArrayList<SelectItem>();
	else
		listDesPayetNoLivres.clear();
	
	listDesPayetNoLivres.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCmd3!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idpayement from payement where Idcmd="+this.idCmd3+" and Livre=0");

	try {
		while(res.next())
		{listDesPayetNoLivres.add(new SelectItem(res.getInt("Idpayement"),""+res.getInt("Idpayement")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}


	
	
	return listDesPayetNoLivres;
}

public void setListDesPayetNoLivres(List<SelectItem> listDesPayetNoLivres) {
	this.listDesPayetNoLivres = listDesPayetNoLivres;
}

private String nomEtPrenomCaissier=null;
public String getNomEtPrenomCaissier() {
	return nomEtPrenomCaissier;
}

public void setNomEtPrenomCaissier(String nomEtPrenomCaissier) {
	this.nomEtPrenomCaissier = nomEtPrenomCaissier;
}

public void ecouteChangePayement(ActionEvent ee)
{if(idPayet==0)
{this.nomEtPrenomCaissier=null;
return;
	}

ResultSet res=null;
res=Connecteur.Extrairedonnees("select * from payement p,personne pe where p.Idpayement="+this.idPayet+" and p.Idcaissier=pe.Idpersonne");
try {
	if(res.next())
	{this.nomEtPrenomCaissier=res.getString("Nompersonne")+"   "+res.getString("Prenompersonne");
		}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}


private List<Produit> listDesProdPayeMNonLivrePr1Payet;
public List<Produit> getListDesProdPayeMNonLivrePr1Payet() {
	
	

	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	if(listDesProdPayeMNonLivrePr1Payet==null)
		listDesProdPayeMNonLivrePr1Payet=new ArrayList<Produit>();
	else
		listDesProdPayeMNonLivrePr1Payet.clear();
	
	System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	if(this.idPayet==0)
	{	
		listDesProdPayeMNonLivrePr1Payet.clear();
		return listDesProdPayeMNonLivrePr1Payet;
	}
	
	System.out.println("cccccccccccccccccccccccccccccccccccccc");
	ResultSet res=null;
	
	res=Connecteur.Extrairedonnees("select * from prod_paye pro,payement p,commande co,figurer f,produits pt where p.Idpayement="+this.idPayet+" and pro.Idpayement=p.Idpayement and p.Idcmd=co.Idcmd and co.Idcmd=f.Idcmd and f.Titre=pro.Designation and f.Idprod=pt.Idprod");
	int n=1;
	try {
		//if(res.next())
		//{System.out.println("dddddddddddddddddddddddddddddddddddddddddddd");
			while(res.next())
			{	System.out.println("==================================");
				System.out.println("==================================");
				Produit p=new Produit();
			    p.setNum(n);
			    p.setTitre(res.getString("Designation"));
			    p.setNomProduit(res.getString("pt.Type"));
			    p.setNbr_ex_dmd(res.getInt("Nbre_tot_exemplr"));
			    p.setNbr_ex_djaPaye(res.getInt("Nbre_exemplr_paye"));
			    p.setNbr_ex_paye(res.getInt("Qtite_paye"));
			    p.setIdProduit(res.getInt("Idfigurer"));
			    n++;
			    listDesProdPayeMNonLivrePr1Payet.add(p);
			}
			
		//}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	
	return listDesProdPayeMNonLivrePr1Payet;
}

public void setListDesProdPayeMNonLivrePr1Payet(
		List<Produit> listDesProdPayeMNonLivrePr1Payet) {
	this.listDesProdPayeMNonLivrePr1Payet = listDesProdPayeMNonLivrePr1Payet;
}


public void marquerLivraison()
{
if(this.idCli3==0)
{message="SELECTIONNER UN CLIENT S'IL VOUS PLAIT!!";
return;
	}
if(this.idCmd3==0)
{message="SELECTIONNER UNE COMMANDE S'IL VOUS PLAIT!!";
return;
	}
if(this.idPayet==0)
{message="SELECTIONNER LE NUMERO DE PAYEMENT S'IL VOUS PLAIT!!";
return;
	}


int n=-1;
n=Connecteur.Insererdonnees("update payement set Livre=1 where Idpayement="+this.idPayet+"");
if(n==-1)
{message="OPERATION ECHOUEE!!";
return;
	}
message="OPERATION REUSSIE!!";


//AXTRAYONS L'ID DE LA COMMANDE IMPLIQUEE DANS CE PAYEMENT
int idcmd;
ResultSet res,res1,res2=null;
res=Connecteur.Extrairedonnees("select * from payement where Idpayement="+this.idPayet+"");
try {
	if(res.next())
	{
	idcmd=res.getInt("Idcmd");
	
	
	res1=Connecteur.Extrairedonnees("select * from commande c,figurer f where c.Idcmd="+idcmd+" and c.Idcmd=f.Idcmd and Nbre_exemplr_paye<Nbre_tot_exemplr");
	
	if(!res1.next())//TOUTE LA QUANTITE COMMANDEE EST PAYEE
	{
		
		
		res2=Connecteur.Extrairedonnees("select * from payement where Idcmd="+idcmd+" and Livre=0");
		
		if(!res2.next())//TOUS LES PRODUITS SONT LIVRES. DONC, CLOTURONS LA COMMANDE
		{
		int m=-1;
		m=Connecteur.Insererdonnees("update commande set Datecloture=now() where Idcmd="+idcmd+"");
		if(m!=-1)
			message="L'OPERATION EST REUSSIE ET LA COMMANDE EST CLOTUREE!!";
		}
	}
	
	
	
	//SI LA TOTALITE D'UN PRODUIT COMMANDE EST LIVREE, JE PENSES QU'IL FAUT LE 
	//METTRE A L'ETAT LIVRE.====>C'EST PAS FAIT.
	
	
	
	
	}
	
	this.idCli3=0;
	this.idCmd3=0;
	this.idPayet=0;
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}



private Produit pt=null;
public Produit getPt() {
	return pt;
}

public void setPt(Produit pt) {
	this.pt = pt;
}

private List<Maquette> listDesMaqtD1ProdSelectionne;
public List<Maquette> getListDesMaqtD1ProdSelectionne() {
	
	System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
	
	if(this.listDesMaqtD1ProdSelectionne==null)
		this.listDesMaqtD1ProdSelectionne=new ArrayList<Maquette>();
	else
		this.listDesMaqtD1ProdSelectionne.clear();
	
	if((pt==null)||(this.idCli3==0)||(this.idCmd3==0)||(this.idPayet==0))
	{	this.listDesMaqtD1ProdSelectionne.clear();
		return this.listDesMaqtD1ProdSelectionne;
	}
	
	ResultSet res=null;
	res=Connecteur.Extrairedonnees("select * from maquette where Idfigurer="+this.pt.getIdProduit()+"");
	
	try {
		
		int n=1;
		
		while(res.next())
		{
			Maquette m=new Maquette();
			m.setNum(n);
			m.setNomMaq(res.getString("Nomphoto"));
			this.listDesMaqtD1ProdSelectionne.add(m);
			
			n++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	return listDesMaqtD1ProdSelectionne;
}

public void setListDesMaqtD1ProdSelectionne(
		List<Maquette> listDesMaqtD1ProdSelectionne) {
	this.listDesMaqtD1ProdSelectionne = listDesMaqtD1ProdSelectionne;
}

//FIN DE LA PARTIE POUR LA LIVRAISON DES PRODUITS PAYES


private int idCli4=0;
public int getIdCli4() {
	return idCli4;
}

public void setIdCli4(int idCli4) {
	this.idCli4 = idCli4;
}

private List<SelectItem> listDesCliAyaDesCmdAvcDesProd1;

public List<SelectItem> getListDesCliAyaDesCmdAvcDesProd1() {
	
	
	ResultSet res=null;
	
	if(listDesCliAyaDesCmdAvcDesProd1==null)
		listDesCliAyaDesCmdAvcDesProd1=new ArrayList<SelectItem>();
	else
		listDesCliAyaDesCmdAvcDesProd1.clear();
	
	listDesCliAyaDesCmdAvcDesProd1.add(new SelectItem(0,""));
	
	//res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig,historique_etapes histo where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE' and cli.supprimee=0) order by p.Nompersonne");
	res=Connecteur.Extrairedonnees("select p.Idpersonne,p.Nompersonne,p.Prenompersonne from personne p where p.Idpersonne in (select cli.Idclient from client cli,commande com,figurer fig where cli.Idclient=com.Idclient and com.Idcmd=fig.Idcmd and fig.etat!='PAYE' and fig.Nbre_tot_exemplr>fig.Nbre_exemplr_paye) and p.Idpersonne in (select Idclient from client where supprimee=0) order by Nompersonne");

	try {
		while(res.next())
		{listDesCliAyaDesCmdAvcDesProd1.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"    "+res.getString("Prenompersonne")+"      "+res.getString("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return listDesCliAyaDesCmdAvcDesProd1;
}

public void setListDesCliAyaDesCmdAvcDesProd1(
		List<SelectItem> listDesCliAyaDesCmdAvcDesProd1) {
	this.listDesCliAyaDesCmdAvcDesProd1 = listDesCliAyaDesCmdAvcDesProd1;
}

private int idComd4=0;
public int getIdComd4() {
	return idComd4;
}
public void setIdComd4(int idComd4) {
	this.idComd4 = idComd4;
}

private List<SelectItem> listDesCmdAvcDesProdEnCour2;

public List<SelectItem> getListDesCmdAvcDesProdEnCour2() {
	
	ResultSet res=null;
	
	if(listDesCmdAvcDesProdEnCour2==null)
		listDesCmdAvcDesProdEnCour2=new ArrayList<SelectItem>();
	else
		listDesCmdAvcDesProdEnCour2.clear();
	
	listDesCmdAvcDesProdEnCour2.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if(this.idCli4!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli4+" and Idcmd in (select com.Idcmd from commande com,figurer fig,historique_etapes histo where com.Idcmd=fig.Idcmd and fig.Idfigurer=histo.Idfigure and histo.Designation !='TERMINEE')");

	try {
		while(res.next())
		{listDesCmdAvcDesProdEnCour2.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesCmdAvcDesProdEnCour2;
}
public void setListDesCmdAvcDesProdEnCour2(
		List<SelectItem> listDesCmdAvcDesProdEnCour2) {
	this.listDesCmdAvcDesProdEnCour2 = listDesCmdAvcDesProdEnCour2;
}

private List<SelectItem> listDesCmdAvcDesProdCmd;

public List<SelectItem> getListDesCmdAvcDesProdCmd() {
	
	ResultSet res=null;
	
	if(listDesCmdAvcDesProdCmd==null)
		listDesCmdAvcDesProdCmd=new ArrayList<SelectItem>();
	else
		listDesCmdAvcDesProdCmd.clear();
	
	listDesCmdAvcDesProdCmd.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");
	
	if(this.idCli4!=0)//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	res=Connecteur.Extrairedonnees("select Idcmd from commande where Idclient="+this.idCli4+" and Idcmd in (select com.Idcmd from commande com,figurer fig where com.Idcmd=fig.Idcmd and fig.etat!='PAYE' and fig.Nbre_tot_exemplr>fig.Nbre_exemplr_paye)");

	try {
		while(res.next())
		{listDesCmdAvcDesProdCmd.add(new SelectItem(res.getInt("Idcmd"),""+res.getInt("Idcmd")+""));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	return listDesCmdAvcDesProdCmd;
}

public void setListDesCmdAvcDesProdCmd(List<SelectItem> listDesCmdAvcDesProdCmd) {
	this.listDesCmdAvcDesProdCmd = listDesCmdAvcDesProdCmd;
}

private int idP4=0;
public int getIdP4() {
	return idP4;
}
public void setIdP4(int idP4) {
	this.idP4 = idP4;
}

private List<SelectItem> listDesProdDuneCmdNonEncorTermns2;

public List<SelectItem> getListDesProdDuneCmdNonEncorTermns2() {
	
	ResultSet res=null;
	
	if(listDesProdDuneCmdNonEncorTermns2==null)
		listDesProdDuneCmdNonEncorTermns2=new ArrayList<SelectItem>();
	else
		listDesProdDuneCmdNonEncorTermns2.clear();
	
	listDesProdDuneCmdNonEncorTermns2.add(new SelectItem(0,""));
	System.out.println("00");
	System.out.println("00");	
	
	if((this.idComd4!=0)&&(this.idCli4!=0))//SI ON A SELECTIONNE UN CLIENT
	{
	System.out.println("11");
	System.out.println("11");
	//res=Connecteur.Extrairedonnees("select fig.Idfigurer,pro.Type,fig.Titre from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation !='TERMINEE'");
	//res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd4+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer not in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd4+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation ='TERMINEE')");
	res=Connecteur.Extrairedonnees("select figu.Idfigurer,prod.Type,figu.Titre from commande coma,figurer figu,produits prod where coma.Idcmd="+this.idComd4+" and coma.Idcmd=figu.Idcmd and figu.Idprod=prod.Idprod and figu.Idfigurer not in(select fig.Idfigurer from commande com,figurer fig,produits pro,historique_etapes hist where com.Idcmd="+this.idComd4+" and com.Idcmd=fig.Idcmd and fig.Idprod=pro.Idprod and fig.Idfigurer=hist.Idfigure and hist.Designation ='TERMINEE')");
	
	
	try {
		while(res.next())
		{	
				
			listDesProdDuneCmdNonEncorTermns2.add(new SelectItem(res.getInt("Idfigurer"),res.getString("Type")+" : "+res.getString("Titre")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	return listDesProdDuneCmdNonEncorTermns2;
}

public void setListDesProdDuneCmdNonEncorTermns2(
		List<SelectItem> listDesProdDuneCmdNonEncorTermns2) {
	this.listDesProdDuneCmdNonEncorTermns2 = listDesProdDuneCmdNonEncorTermns2;
}


public void saveBon(){


	if(this.idCli4==0)
	{	message="VOUS N'AVEZ PAS SELECTONNER UN CLIENT!!";
		return;
		}

	if(this.idComd4==0)
	{message="VOUS N'AVEZ PAS SELECTIONNE DE COMMANDE!!";
	return;
		}

	if(this.files1.size()<1)
		{message="LA LISTE DES BONS DE COMMANDE EST VIDE!!";
		return;
		}
	int n=-1;
	//for(int j=0;j<this.files1.size();j++)
	//{
	int j=0;
	System.out.println("+this.files1.get(j).getName()"+this.files1.get(j).getName());
	String s=this.files1.get(j).getName();
	System.out.println("s"+s);
	n=Connecteur.Insererdonnees("update commande set bonCmd='"+s+"' where Idcmd="+this.idComd4+"");
	//}
	if(n!=-1)
		message="OPERATION REUSSIE!!";
	else
		message="OPERATION ECHOUEE!!";
	
	
	//SI IL Y A SUR CETTE COMMANDE AU MOINS UN PRODUIT AVEC L'ETAT COMMANDE
	//CE BON EST UN GARANTI DE PAYEMENT POUR TOUS LES PRODUITS DE CETTE COMMANDE
	ResultSet re=null;
	re=Connecteur.Extrairedonnees("select * from figurer where Idcmd="+this.idComd4+" and etat='COMMANDE'");
	try {
		if(re.next())
		{
		int ne=-1;
		ne=Connecteur.Insererdonnees("update figurer set etat='EN ATTENTE DE TRAITEMENT' where Idcmd="+this.idComd4+"");
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//
	
	
		
	this.files1.clear();
			
	
	}

private ArrayList<File> files1 = new ArrayList<File>();
public ArrayList<File> getFiles1() {
	return files1;
}

public void setFiles1(ArrayList<File> files1) {
	this.files1 = files1;
}
public int getSize1() {
    if (getFiles1().size()>0){
        return getFiles1().size();
    }else 
    {
        return 0;
    }
}


public void listener1(UploadEvent event) throws Exception{

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
	files1.add(file);


	//============================================
	//FAUT COPIER LE FICHIER CHARGE DANS LE REPERT
	//============================================

	FileOutputStream fos=null;

	try{
		
	fos = new FileOutputStream(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\bons\\"+nomDuFic));
	fos.write(item.getData());
	fos.close();

	} catch (IOException e) {
	e.printStackTrace();
	}
	}

private List<Commande> listDuBon;
public List<Commande> getListDuBon() {
	if(this.listDuBon==null)
		this.listDuBon=new ArrayList<Commande>();
	else
		this.listDuBon.clear();
	
	if(this.idComd4!=0)
	{
		ResultSet res=null;
		res=Connecteur.Extrairedonnees("select * from commande where Idcmd="+this.idComd4+"");
		try {
			if(res.next())
			{
				String b=null;
				b=res.getString("bonCmd");
				if(b!=null)
				{
				Commande c=new Commande();
				c.setIdcmd(res.getInt("Idcmd"));
				c.setBon(res.getString("bonCmd"));
				listDuBon.add(c);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	return listDuBon;
}
public void setListDuBon(List<Commande> listDuBon) {
	this.listDuBon = listDuBon;
}

private Commande selectedcmd=null;
public Commande getSelectedcmd() {
	return selectedcmd;
}
public void setSelectedcmd(Commande selectedcmd) {
	this.selectedcmd = selectedcmd;
}

public void enleverBon(ActionEvent e)
{
if(this.selectedcmd==null)//CAS IMPOSSIBLE
{message="CAS IMPOSSIBLE!!";
return;
	}

int n=-1;
n=Connecteur.Insererdonnees("update commande set bonCmd=null where Idcmd="+this.selectedcmd.getIdcmd()+"");
if(n==-1)
	message="MISE A JOUR ECHOUEE!!";
else
	message="MISE A JOUR REUSSIE!!";

this.selectedcmd=null;
	}

public void visualiseBon(ActionEvent ev)
{if(this.selectedcmd==null)//CAS IMPOSSIBLE
{message="CAS IMPOSSIBLE!!";
return;
	}

//int idm=0;
//idm=m.getIdMaql();

String nomBon=null;
nomBon=this.selectedcmd.getBon();

Desktop desk = Desktop.getDesktop();
try {
	desk.open(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\bons\\"+nomBon));
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


this.selectedcmd=null;

	}

}
