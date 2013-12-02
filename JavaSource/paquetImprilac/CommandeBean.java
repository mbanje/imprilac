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

public void instancierCmd()
{System.out.println("!!!!!!");
if(this.idClient==0)
{message="SELECTIONNER LE CLIENT A QUI APPARTIENDRA LA COMMANDE S'IL VOUS PLAIT!!";
System.out.println("???????");
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

message="IL FAUT ENSUITE METTRE LES PRODUITS SUR CETTE COMMANDE!!";
	}
public void supprimerCmd()
{this.com=null;
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
private String titreProd;
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
{if((this.idClient==0)||(this.com==null))
	{message="CREER D'ABORD LA COMMANDE S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("aaa");
if(this.idProd==0)
	{message="SELECTIONNER UN PRODUIT S'IL VOUS PLAIT!!";
	System.out.println("aaabbb");
	return;
	}
System.out.println("bbb");
if(this.titreProd==null)
	{message="DONNER UN TITRE A CE PRODUIT S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("ccc");
if(this.nbrExemplaires==0)
	{message="SAISISSER LE NOMBRE D'EXEMPLAIRES COMMANDES S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("ddd");
if(this.maqPresente==null)
	{message="INDIQUER SI LA MAQUETTE EST PRESENTE OU NON S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("eee");
if(this.idChemin==0)
	{message="INDIQUER LE CHEMIN QUE VA PRENDRE LE PRODUIT S'IL VOUS PLAIT!!";
	return;
	}
System.out.println("fff");

//ON VERIFIE D'ABORD S'IL N'Y AURAIT PAS DEJA SUR CETTE COMMANDE UN PRODUIT
//DU MEME NOM ET DU MEME TITRE.
int j=0;
for(j=0;j<this.com.getListProd().size();j++)
{if((this.com.getListProd().get(j).getIdProduit()==this.idProd)&&(this.com.getListProd().get(j).getTitre()==this.titreProd))
	{message="DONNER UN AUTRE TITRE A CE PRODUIT S'IL VOUS PLAIT!!";
	break;
	}
	}

ResultSet res=null;
if(j>this.com.getListProd().size())//UN PRODUIT DU MEME NOM ET TITRE N'A PAS ETE TROUVE
	{Produit p=new Produit();
	
	//ON INITIALISE UN PRODUIT AVANT DE L'AJOUTER SUR LA LISTE
	p.setIdProduit(this.idProd);
	p.setTitre(this.titreProd);
	p.setNbre_exemplaire_prod(this.nbrExemplaires);
	if(this.maqPresente=="OUI")
		p.setMaq_presentee(true);
	else
		p.setMaq_presentee(false);
	p.setIdChemin(this.idChemin);
	
	
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
	
	j--;
	this.com.getListProd().get(j).setProduit(p);
	
	
	}
	}

private List<SelectItem> listDeroulanteDesProd;
public List<SelectItem> getListDeroulanteDesProd() {
	
	if((this.com==null)||(this.com.getListProd()==null))
	{	this.listDeroulanteDesProd=new ArrayList<SelectItem>();
		listDeroulanteDesProd.add(new SelectItem(0,""));
	}
	else
	{int j=0;
		listDeroulanteDesProd.add(new SelectItem(0,""));
		for(j=0;j<this.com.getListProd().size();j++)
		{
		this.listDeroulanteDesProd.add(new SelectItem(this.com.getListProd().get(j).getIdProduit(), ""+this.com.getListProd().get(j).getTitre()+""));
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


}