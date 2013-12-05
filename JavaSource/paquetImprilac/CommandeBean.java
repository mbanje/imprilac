package paquetImprilac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

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
			titre="LISTE DES ETAPES SE TROUVANT SUR LE CHEMIN "+res.getString("c.Designation").toUpperCase()+"";
		
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



if((this.activenif==true)||(this.activesociete==true))
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

//=================================================PRODUIT========================
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
{if(this.com==null)
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
		while((j<this.com.getListProd().size())&&!((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre().equalsIgnoreCase(this.titreProd))))		
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
System.out.println("this.titreProduit "+this.titreProduit);
System.out.println("this.titreProduit.length() "+this.titreProduit.length());


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

if(this.com==null)
{message="VOUS DEVEZ D'ABORD CREER UNE COMMANDE!!";
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
	if(this.com.getListProd().get(i).getListCharges()==null)
		{this.com.getListProd().get(i).setListCharges(new ArrayList<Charge>());
		System.out.println("--------JE CREE UNE NOUVELLE LISTE DES DES CHARGES------");
		}
	
	//------------------------AUTRE MOYEN-------------------------
	if(this.com.getListProd().get(i).getCharge()==null)
		this.com.getListProd().get(i).setCharge(c);
	
	else//IL Y A UN OU PLUSIEURS CHARGES
	{Charge j=null;
		j=this.com.getListProd().get(i).getCharge();
		while(j.getCharge()!=null)
		{j=j.getCharge();	
		}
		
		if(j.getCharge()==null)
			j.setCharge(c);
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

System.out.println("FIN DE public void ajouterChargesSurProduit()");
}


private List<Charge> listDesChargePr1Prod=null;
public List<Charge> getListDesChargePr1Prod() {
	
	if(listDesChargePr1Prod==null)
		listDesChargePr1Prod=new ArrayList<Charge>();
	else
		listDesChargePr1Prod.clear();
	
	System.out.println("this.titreProduit "+this.titreProduit);
	System.out.println("this.titreProduit "+this.titreProduit);
	//if(this.titreProd!=null)
	//{	
	if((this.com!=null)&&(this.com.getListProd()!=null)&&(this.com.getListProd().size()>0)&&(this.titreProduit!=null)&&(this.titreProduit.length()>0))
	{
	System.out.println("this.com.getListProd().get(0).getTitre()"+this.com.getListProd().get(0).getTitre());
		
		int i=0;
		while((i<this.com.getListProd().size())&&!(this.titreProduit.equalsIgnoreCase(this.com.getListProd().get(i).getTitre())))
		{System.out.println("JE PASSE DANS LA BOUCLE i= "+i);
			i++;
			}
	//	System.out.println("this.com.getListProd().get(i).getListCharges().size() "+this.com.getListProd().get(i).getListCharges().size());
/*		if(this.com.getListProd().get(i).getListCharges()==null)
		{System.out.println(",,,,,,,,,,");
			this.com.getListProd().get(i).setListCharges(new ArrayList<Charge>());
		}*/
		//if((this.com.getListProd().get(i).getListCharges()!=null)&&(this.com.getListProd().get(i).getListCharges().size()>0))
/*	if(this.com.getListProd().get(i).getListCharges()!=null)
		{System.out.println("this.com.getListProd().get(i).getListCharges().size() avant prise de la liste "+this.com.getListProd().get(i).getListCharges().size());
			listDesChargePr1Prod=this.com.getListProd().get(i).getListCharges();
		System.out.println("this.com.getListProd().get(i).getListCharges().size() apres prise de la liste "+this.com.getListProd().get(i).getListCharges().size());
		}*/
	
//=======DANS LA ZONE DE LA LISTE DE RECOURS========
	if(this.com.getListProd().get(i).getCharge()!=null)
	{Charge c;
		this.listDesChargePr1Prod.add(this.com.getListProd().get(i).getCharge());
		c=this.com.getListProd().get(i).getCharge();
		
		while(c.getCharge()!=null)
		{this.listDesChargePr1Prod.add(c);
		c=c.getCharge();
		}
		
	}
//===============
	
}
	//}
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

}
