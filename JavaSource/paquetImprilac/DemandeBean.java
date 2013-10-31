package paquetImprilac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class DemandeBean {
	
	private String message;

	private int idMateriel=0;
	private int quantiteMateriel=0;
	private int quantiteDmd=0;
	private int quantiteMatAccord=0;
	private int idDmd;
	private int qtiteEnlist;
	private int nombreDeMatDejaDansunites=0;
	private int idUniteDmd;
	
	private String designationMat;
	private List<DemandeBean> listDesProdDmd;
	private List<DemandeBean> listDesMateriaux;
	private List<SelectItem> listDesDmdNonEtudiees;
	private List<SelectItem> listAutorisation;
	private List<SelectItem> listDmdEnCourtEtEtudi;
	private List<SelectItem> listMatAccordees;
	private List<SelectItem> listDmdPourHisto;
	private List<SelectItem> listDMatPourHisto;
	private List<SelectItem> listDesEtatsDesMat;
	private List<SelectItem> listDesEtatsDesMat1;
	private List<DemandeBean> listAHistoriser=new ArrayList<DemandeBean>();
	private List<DemandeBean> listMatHistoriser;
	//Liste des materiaux non encore evaluees(par le gerant)
	//se trouvant sur une demande.
	private List<SelectItem> listDesMatNonEvalueSeTrouvSurUneDmd;
	private String nom;
	private String prenom;
	private String autorisation=null;
	private String motivation;
	private int etatMat;
	private String etatMatString;
	
	
	private int idUnites;
	private String nomMateriel;
	private Date dateDrnMod=null;
	private DemandeBean selected;
	//private boolean ajoute=true;
	
	
	
	
	
	
	
	
	
	
	
	


	public DemandeBean getSelected() {
		return selected;
	}
	public void setSelected(DemandeBean selected) {
		this.selected = selected;
	}
	public int getNombreDeMatDejaDansunites() {
		return nombreDeMatDejaDansunites;
	}
	public void setNombreDeMatDejaDansunites(int nombreDeMatDejaDansunites) {
		this.nombreDeMatDejaDansunites = nombreDeMatDejaDansunites;
	}
	public List<DemandeBean> getListMatHistoriser() {
		
		ResultSet res=null;
        DemandeBean Mat=null;	
		if(listMatHistoriser==null)
			listMatHistoriser=new ArrayList<DemandeBean>();
		else
			listMatHistoriser.clear();
		
		
			res=Connecteur.Extrairedonnees("SELECT * FROM unites, materiel, unite_dmde WHERE unites.Idunitedmd = unite_dmde.Idunitedmd AND materiel.Idmateriel = unite_dmde.Idmateriel ");

		try {
			while(res.next())
			{Mat=new DemandeBean();
			Mat.idUnites=res.getInt("Idunite");
			Mat.nomMateriel=res.getString("Designation");
			Mat.etatMatString=res.getString("etat");
			Mat.dateDrnMod=res.getDate("DateDernMod");
		

			listMatHistoriser.add(Mat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listMatHistoriser;
	}
	public void setListMatHistoriser(List<DemandeBean> listMatHistoriser) {
		this.listMatHistoriser = listMatHistoriser;
	}
	public int getIdUnites() {
		return idUnites;
	}
	public void setIdUnites(int idUnites) {
		this.idUnites = idUnites;
	}
	public String getNomMateriel() {
		return nomMateriel;
	}
	public void setNomMateriel(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}
	public Date getDateDrnMod() {
		return dateDrnMod;
	}
	public void setDateDrnMod(Date dateDrnMod) {
		this.dateDrnMod = dateDrnMod;
	}
	public void setIdUniteDmd(int idUniteDmd) {
		this.idUniteDmd = idUniteDmd;
	}
	public int getQtiteEnlist() {
		return qtiteEnlist;
	}
	public void setQtiteEnlist(int qtiteEnlist) {
		this.qtiteEnlist = qtiteEnlist;
	}
	public String getEtatMatString() {
		return etatMatString;
	}
	public void setEtatMatString(String etatMatString) {
		this.etatMatString = etatMatString;
	}
	public List<DemandeBean> getListAHistoriser() {
		
		
		return listAHistoriser;
	}
	public void setListAHistoriser(List<DemandeBean> listAHistoriser) {
		this.listAHistoriser = listAHistoriser;
	}



	public int getEtatMat() {
		return etatMat;
	}
	public void setEtatMat(int etatMat) {
		this.etatMat = etatMat;
	}
	
	public int getIdUniteDmd() {
		return idUniteDmd;
	}
	public void setListDesEtatsDesMat1(List<SelectItem> listDesEtatsDesMat1) {
		this.listDesEtatsDesMat1 = listDesEtatsDesMat1;
	}
	public List<SelectItem> getListDesEtatsDesMat1() {
		if(listDesEtatsDesMat1==null)
			listDesEtatsDesMat1=new ArrayList<SelectItem>();
		else 
			listDesEtatsDesMat1.clear();
		
		listDesEtatsDesMat1.add(new SelectItem(0,""));
		listDesEtatsDesMat1.add(new SelectItem(1,"BON ETAT"));
		listDesEtatsDesMat1.add(new SelectItem(2,"EN PANNE"));
		listDesEtatsDesMat1.add(new SelectItem(3,"ENDOMAGE"));
		return listDesEtatsDesMat1;
	}
	
	public List<SelectItem> getListDesEtatsDesMat() {
		if(listDesEtatsDesMat==null)
			listDesEtatsDesMat=new ArrayList<SelectItem>();
		else 
			listDesEtatsDesMat.clear();
		
		listDesEtatsDesMat.add(new SelectItem(0,""));
		listDesEtatsDesMat.add(new SelectItem(1,"BON ETAT"));
		listDesEtatsDesMat.add(new SelectItem(2,"EN PANNE"));
		
		return listDesEtatsDesMat;
	}


	public void setListDesEtatsDesMat(List<SelectItem> listDesEtatsDesMat) {
		this.listDesEtatsDesMat = listDesEtatsDesMat;
	}


	public List<SelectItem> getListDMatPourHisto() {
		
		ResultSet res=null;
		if(listDMatPourHisto==null)
			listDMatPourHisto=new ArrayList<SelectItem>();
		else
			listDMatPourHisto.clear();
		
		res=Connecteur.Extrairedonnees("select m.Idmateriel,m.Designation from unite_dmde uni,materiel m where uni.Iddmd="+this.idDmd+" and uni.Historise='NON' and uni.Idgestion is not null and uni.Idmateriel=m.Idmateriel and m.Historisation='OUI'");
		
		listDMatPourHisto.add(new SelectItem(0," "));
		try {
			while(res.next())
			{
		listDMatPourHisto.add(new SelectItem(res.getInt("Idmateriel"),res.getString("Designation")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listDMatPourHisto;
	}


	public void setListDMatPourHisto(List<SelectItem> listDMatPourHisto) {
		this.listDMatPourHisto = listDMatPourHisto;
	}


	public List<SelectItem> getListDmdPourHisto() {
		
		ResultSet res=null;
		if(listDmdPourHisto==null)
			listDmdPourHisto=new ArrayList<SelectItem>();
		else
			listDmdPourHisto.clear();
		
		//res=Connecteur.Extrairedonnees("select Iddemande from demande where Iddemande in (Select d.Iddemande from demande d,unite_dmde u where d.Iddemande=u.Iddmd and d.Iddemande in ( select uni.Iddmd from unite_dmde uni,materiel m where uni.Idgestion is not null and uni.Idmateriel=m.Idmateriel and m.Historisation='OUI'))");
		res=Connecteur.Extrairedonnees("select Iddemande from demande where Iddemande in (select uni.Iddmd from unite_dmde uni,materiel m where uni.Idgestion is not null and uni.Historise='NON' and uni.Idmateriel=m.Idmateriel and m.Historisation='OUI')");
		listDmdPourHisto.add(new SelectItem(0," "));
		try {
			while(res.next())
			{
				listDmdPourHisto.add(new SelectItem(res.getInt("Iddemande"),""+res.getInt("Iddemande")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listDmdPourHisto;
	}


	public void setListDmdPourHisto(List<SelectItem> listDmdPourHisto) {
		this.listDmdPourHisto = listDmdPourHisto;
	}


	public List<SelectItem> getListMatAccordees() {
		
		ResultSet res=null;
		if(listMatAccordees==null)
			listMatAccordees=new ArrayList<SelectItem>();
		else
			listMatAccordees.clear();
		
		res=Connecteur.Extrairedonnees("Select m.Idmateriel,m.Designation from unite_dmde u,materiel m where u.Iddmd="+this.idDmd+" and u.Idmateriel=m.Idmateriel and u.Idgestion is null and Autorisation='OUI'");
		
		listMatAccordees.add(new SelectItem(0," "));
		try {
			while(res.next())
			{
				listMatAccordees.add(new SelectItem(res.getInt("Idmateriel"),res.getString("Designation")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMatAccordees;
	}
	
	
	public void setListMatAccordees(List<SelectItem> listMatAccordees) {
		this.listMatAccordees = listMatAccordees;
	}
	
	public List<SelectItem> getListDmdEnCourtEtEtudi() {
		ResultSet res=null;
		if(listDmdEnCourtEtEtudi==null)
			listDmdEnCourtEtEtudi=new ArrayList<SelectItem>();
		else
			listDmdEnCourtEtEtudi.clear();
		
		res=Connecteur.Extrairedonnees("Select * from demande where Etatdmd='ETUDE EN COURT' or Etatdmd='ETUDIEE' and Iddemande in (select Iddmd from unite_dmde u where u.Idgestion is null and u.Autorisation='OUI')");
		
		listDmdEnCourtEtEtudi.add(new SelectItem(0," "));
		
		try {
			while(res.next())
			{listDmdEnCourtEtEtudi.add(new SelectItem(res.getInt("Iddemande"),""+res.getInt("Iddemande")+""));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listDmdEnCourtEtEtudi;
	}
	
	public void setListDmdEnCourtEtEtudi(List<SelectItem> listDmdEnCourtEtEtudi) {
		this.listDmdEnCourtEtEtudi = listDmdEnCourtEtEtudi;
	}
	public String getMotivation() {
		return motivation;
	}
	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}
	public String getAutorisation() {
		return autorisation;
	}
	public void setAutorisation(String autorisation) {
		this.autorisation = autorisation;
	}
	
	public List<SelectItem> getListAutorisation() {
		if(listAutorisation==null)
			listAutorisation=new ArrayList<SelectItem>();
		else
			listAutorisation.clear();
		
		listAutorisation.add(new SelectItem(""));
		listAutorisation.add(new SelectItem("OUI"));
		listAutorisation.add(new SelectItem("NON"));
		
		return listAutorisation;
	}
	
	public void setListAutorisation(List<SelectItem> listAutorisation) {
		this.listAutorisation = listAutorisation;
	}
	public int getQuantiteMatAccord() {
		return quantiteMatAccord;
	}
	public void setQuantiteMatAccord(int quantiteMatAccord) {
		this.quantiteMatAccord = quantiteMatAccord;
	}
	public int getQuantiteDmd() {
		return quantiteDmd;
	}
	public void setQuantiteDmd(int quantiteDmd) {
		this.quantiteDmd = quantiteDmd;
	}
	public List<DemandeBean> getListDesMateriaux() {
		ResultSet res=null;
		res=Connecteur.Extrairedonnees("Select * from materiel");
		if(listDesMateriaux==null)
			listDesMateriaux=new ArrayList<DemandeBean>();
		else
			listDesMateriaux.clear();
		
		try {
			while(res.next())
			{DemandeBean m=new DemandeBean();
			m.designationMat=res.getString("Designation");
			m.quantiteMateriel=res.getInt("quantiteEnStocks");
			listDesMateriaux.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return listDesMateriaux;
	}
	public void setListDesMateriaux(List<DemandeBean> listDesMateriaux) {
		this.listDesMateriaux = listDesMateriaux;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getIdDmd() {
		return idDmd;
	}
	public void setIdDmd(int idDmd) {
		this.idDmd = idDmd;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<DemandeBean> getListDesProdDmd() {
		return listDesProdDmd;
	}
	public void setListDesProdDmd(List<DemandeBean> listDesProdDmd) {
		this.listDesProdDmd = listDesProdDmd;
	}
	public int getIdMateriel() {
		return idMateriel;
	}
	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}
	public int getQuantiteMateriel() {
		return quantiteMateriel;
	}
	public void setQuantiteMateriel(int quantiteMateriel) {
		this.quantiteMateriel = quantiteMateriel;
	}

	public String getDesignationMat() {
		return designationMat;
	}
	public void setDesignationMat(String designationMat) {
		this.designationMat = designationMat;
	}
	public List<SelectItem> getListDesMatNonEvalueSeTrouvSurUneDmd() {
		ResultSet res=null;
		if(listDesMatNonEvalueSeTrouvSurUneDmd==null)
			listDesMatNonEvalueSeTrouvSurUneDmd=new ArrayList<SelectItem>();
		else
			listDesMatNonEvalueSeTrouvSurUneDmd.clear();
		
		res=Connecteur.Extrairedonnees("Select m.Idmateriel,m.Designation from unite_dmde u,materiel m where u.Iddmd="+this.idDmd+" and u.Idmateriel=m.Idmateriel and u.Idgerant is null");
		
		listDesMatNonEvalueSeTrouvSurUneDmd.add(new SelectItem(0," "));
		try {
			while(res.next())
			{
	listDesMatNonEvalueSeTrouvSurUneDmd.add(new SelectItem(res.getInt("Idmateriel"),res.getString("Designation")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listDesMatNonEvalueSeTrouvSurUneDmd;
	}
	
	
	
	public void setListDesMatNonEvalueSeTrouvSurUneDmd(
			List<SelectItem> listDesMatNonEvalueSeTrouvSurUneDmd) {
		this.listDesMatNonEvalueSeTrouvSurUneDmd = listDesMatNonEvalueSeTrouvSurUneDmd;
	}
	public List<SelectItem> getListDesDmdNonEtudiees() {
		ResultSet res=null;
		if(listDesDmdNonEtudiees==null)
			listDesDmdNonEtudiees=new ArrayList<SelectItem>();
		else
			listDesDmdNonEtudiees.clear();
		
		res=Connecteur.Extrairedonnees("Select * from demande where Etatdmd='NON ETUDIEE' or Etatdmd='ETUDE EN COURT'");
		
		listDesDmdNonEtudiees.add(new SelectItem(0," "));
		
		try {
			while(res.next())
			{listDesDmdNonEtudiees.add(new SelectItem(res.getInt("Iddemande"),""+res.getInt("Iddemande")+""));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listDesDmdNonEtudiees;
	}
	public void setListDesDmdNonEtudiees(List<SelectItem> listDesDmdNonEtudiees) {
		this.listDesDmdNonEtudiees = listDesDmdNonEtudiees;
	}
	
	
	//DEBUT DE LA FONCTION QUI AJOUTE DU MATERIEL SUR UNE COMMANDE
	public void ajouteMatSurCmd()
	{int j=0;

	System.out.println("Dans idMateriel "+idMateriel);
	System.out.println("Dans idMateriel "+idMateriel);
	if(this.idMateriel==0)
		{message="CHOISISSER LE MATERIEL S'IL VOUS PLAIT!!!";
		System.out.println("Dans idMateriel==0 "+idMateriel);
		return;
		}
	if(this.quantiteMateriel==0)
		{message="SAISISSER LA QUANTITE S'IL VOUS PLAIT!!!";
		System.out.println("LA QUANTITE EST 0");
		return;
		}	
	if(listDesProdDmd==null)
		listDesProdDmd=new ArrayList<DemandeBean>();
	System.out.println("Size  :"+listDesProdDmd.size());
	while((j<listDesProdDmd.size())&&(listDesProdDmd.get(j).idMateriel!=this.idMateriel))
	{j++;
		}

	if(j<listDesProdDmd.size())
	{listDesProdDmd.get(j).quantiteMateriel+=this.quantiteMateriel;
	return;
	}
	System.out.println("Size  :"+listDesProdDmd.size());

	ResultSet r=Connecteur.Extrairedonnees("Select * from materiel where Idmateriel="+this.idMateriel+"");
	DemandeBean i=new DemandeBean();

	i.idMateriel=this.idMateriel;
	i.quantiteMateriel=this.quantiteMateriel;

	try {
		r.next();
		i.designationMat=r.getString("Designation");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	listDesProdDmd.add(i);

	System.out.println("Size  :"+listDesProdDmd.size());
	}
	//FIN DE LA FONCTION QUI AJOUTE DU MATERIEL SUR UNE COMMANDE

	
	
	//DEBUT DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE COMMANDE
	public int recuperIDCmd()
	{
	int i=-1;
	ResultSet r=null;
	r=Connecteur.Extrairedonnees("SELECT Iddemande FROM demande order by Iddemande desc limit 1");
	if(r!=null)
		try {
			if(r.next()){
				i=r.getInt("Iddemande");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return i;
		}
	//FIN DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE PERSONNE


//DEBUT DE LA FONCTION QUI CREE UNE DEMANDE ET ENREGISTRE LES PRODUITS
//SE TROUVANT SUR CETTE COMMANDE.
	public void creerDmdMateriel()
	{int idDmd;
	if(listDesProdDmd.size()==0)	
		{message="LA COMMANDE EST VIDE!!!";
		return;
		}
	int i=-1;
	i=Connecteur.Insererdonnees("insert into demande (Idchefprod,Datedmd,Etatdmd) values (3,now(),'NON ETUDIEE')");

	if(i==-1)
	{message="CREATION DEMANDE ECHOUEE!!!";
	return;
		}
	idDmd=recuperIDCmd();
	int k;
	for(k=0;k<listDesProdDmd.size();k++)
	{System.out.println("Avant");
	i=Connecteur.Insererdonnees("insert into unite_dmde(Iddmd,Idmateriel,Qtitedmd,Autorisation) values ("+idDmd+","+listDesProdDmd.get(k).idMateriel+","+listDesProdDmd.get(k).quantiteMateriel+",'NON')");
	System.out.println("Apres");
	}

	if(i==-1)
	message="OPERATION DE DEMANDE DU MATERIEL ECHOUEE!!!";
	else
	message="OPERATION DE DEMANDE DU MATERIEL REUSSIE!!!";

	listDesProdDmd.clear();
	}
//FIN DE LA FONCTION QUI CREE UNE DEMANDE ET ENREGISTRE LES PRODUITS
//SE TROUVANT SUR CETTE COMMANDE.
	
	
	public void detailPersoPourUneDmd()
	{
		if(this.idDmd==0)
		{
			this.nom="";
			this.prenom="";
			return;
		}
		ResultSet res=null;
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c,demande d where d.Iddemande="+this.idDmd+" and d.Idchefprod=c.Idcheprod and c.Idcheprod=p.Idpersonne");
		
		try {
			if(res.next())
			{this.nom=res.getString("Nompersonne");
			this.prenom=res.getString("Prenompersonne");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.quantiteMateriel=0;
	}

	
	//DEBUT DE LA FONCTION QUI PERMET D'AUGMENTER LA QUANTITE D'UN 
	//MATERIEL DONNE EN STOCK.
	public void ajouteQuantiteMat()
	{int n=-1,quantit=0;
	ResultSet res=null;
	
	if(this.idMateriel==0)
	{message="SELECTIONNER LE MATERIEL S'IL VOUS PLAIT!!";
	return;
	}
	if(this.quantiteMateriel==0)
	{message="SAISISSER LA QUANTITE S'IL VOUS PLAIT!!";
	return;
	}
	
	res=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+this.idMateriel+"");
	
	try {
		res.next();
	quantit=res.getInt("quantiteEnStocks");

	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.quantiteMateriel+=quantit;
	n=Connecteur.Insererdonnees("update materiel set quantiteEnStocks="+this.quantiteMateriel+" where Idmateriel="+this.idMateriel+"");
	
	this.quantiteMateriel=0;
	if(n==-1)
		message="ECHEC D'INSERTION!!";
	else
		message="INSERTION REUSSIE!!";
	}
	//FIN DE LA FONCTION QUI PERMET D'AUGMENTER LA QUANTITE D'UN 
	//MATERIEL DONNE EN STOCK.
	
	
	
	public void detailsMaterielSurDmd()
	{ResultSet res=null;
	res=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+this.idMateriel+"");
	try {
		if(res.next())
		{
			this.quantiteMateriel=res.getInt("quantiteEnStocks");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	res=Connecteur.Extrairedonnees("select * from unite_dmde where Iddmd="+this.idDmd+" and Idmateriel="+this.idMateriel+"");
	try {
		res.next();
		this.quantiteDmd=res.getInt("Qtitedmd");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
//DEBUT DE LA FONCTION QUI DIMINUE LA QUANTITE DU MATERIEL SE TROUVANT EN STOCK
//ELLE RETOURNE -1 EN CAS D'ECHEC
	public int diminueQteMatDansStock(int idMat,int qte)
	{ResultSet res=null;
	int qTotal=0,n=-1;
	res=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+idMat+"");
	try {
		res.next();
		qTotal=res.getInt("quantiteEnStocks");
		qTotal-=qte;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	n=Connecteur.Insererdonnees("update materiel set quantiteEnStocks="+qTotal+" where Idmateriel="+idMat+"");
	return n;
	}
//FIN DE LA FONCTION QUI DIMINUE LA QUANTITE DU MATERIEL SE TROUVANT EN STOCK
	
	
	public void saveDecisionOnAskedMat()
	{int n=-1;
	ResultSet res=null;
	System.out.println("iiiiiiiii");
		if(this.idDmd==0)
		{System.out.println("i0i0i0i0i0i0i0i0i0");
			message="SELECTIONNER UNE DEMANDE S'IL VOUS PLAIT!!";
			return;
		}
		System.out.println("jjjjjjjjjj");
		if(this.idMateriel==0)
		{
			message="SELECTIONNER LE MATERIEL S'IL VOUS PLAIT!!";
			return;
		}
		
		System.out.println("this.autorisation"+this.autorisation);
		System.out.println("this.quantiteMatAccord"+this.quantiteMatAccord);
		
		if((this.quantiteMatAccord==0)&&(this.autorisation.equalsIgnoreCase("OUI")))
		{
			message="SAISISSER LA QUANTITE ACCORDEE S'IL VOUS PLAIT!!";
			return;
		}
		System.out.println("lllllllll");
		if(this.quantiteMateriel<this.quantiteMatAccord)
		{
			message="LA QUANTITE ACCORDEE N'EST PAS DISPONNIBLE!!";
			return;
		}
		System.out.println("mmmmmmmmm");
		if(this.autorisation.length()==0)
		{
			message="VOUS N'AVEZ NI AUTORISE,NI REFUSE LA SORTIE DE CE PRODUIT!!";
			return;
		}
		if((this.autorisation.equalsIgnoreCase("NON"))&&(this.motivation.length()==0))
		{
			message="JUSTIFIER LA DECISION S'IL VOUS PLAIT!!";
			return;
		}
		System.out.println("nnnnnnnnnn");
	n=Connecteur.Insererdonnees("update unite_dmde set Idgerant=9,Autorisation='"+this.autorisation+"',Motivation='"+this.motivation+"',Dateanalyse=now(),Qtiteaccorde="+this.quantiteMatAccord+" where Iddmd="+this.idDmd+" and Idmateriel="+this.idMateriel+"");
	System.out.println("ooooooooooo");
	if(n==-1)
	{message="INSERTION ECHOUEE!!";
	return;
	}

	res=Connecteur.Extrairedonnees("select * from demande where Etatdmd='NON ETUDIEE'");
	try {
		if(res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update demande set Etatdmd='ETUDE EN COURT' where Iddemande="+this.idDmd+"");
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	n=diminueQteMatDansStock(this.idMateriel,this.quantiteMatAccord);
	if(n==-1)
	{message="INSERTION ECHOUEE!!";
	return;
	}
	
	res=Connecteur.Extrairedonnees("select * from unite_dmde where Iddmd="+this.idDmd+" and Idgerant is null");
	try {
		if(!res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update demande set Etatdmd='ETUDIEE' where Iddemande="+this.idDmd+"");	
		}
			
		message="OPERATION REUSSIE!!";
		
		this.autorisation="";
		this.quantiteMatAccord=0;
		this.motivation=null;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	//DEBUT DE LA FONCTION QUI RECCUPERE LA QUANTITE D'UN MATERIEL
	//ACCORDEE PAR LE GERANT
	public void extraireQteAccordee()
	{
		ResultSet res=null;
		res=Connecteur.Extrairedonnees("select * from unite_dmde where Iddmd="+this.idDmd+" and Idmateriel="+this.idMateriel+"");
		try {
			if(res.next())
			this.quantiteMatAccord=res.getInt("Qtiteaccorde");
			else
			this.quantiteMatAccord=0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//FIN DE LA FONCTION QUI RECCUPERE LA QUANTITE D'UN MATERIEL
	//ACCORDEE PAR LE GERANT
	
	//DEBUT DE LA FONCTION QUI ECOUTE LE CHANGEMENT DE LA DEMANDE
	public void ecouteChangeDmd()
	{
		this.quantiteMatAccord=0;
	}
	//FIN DE LA FONCTION QUI ECOUTE LE CHANGEMENT DE LA DEMANDE	
	
	
	
	//DEBUT DE LA FONCTION QUI CHERCHE ET RENVOIT LA CLE PRIMAIRE DE LA
	//TABLE unite_dmde SACHANT LE NUMERO DE LA DEMANDE ET DU MATERIEL
	
	public int chercheIdUnite_dmde(int d,int m)
	{int id=-1;
	ResultSet res=Connecteur.Extrairedonnees("select Idunitedmd from unite_dmde where Iddmd="+d+" and Idmateriel="+m+"");
	try {
		if(res.next())
		id=res.getInt("Idunitedmd");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return id;
	}
	
	//FIN DE LA FONCTION QUI CHERCHE ET RENVOIT LA CLE PRIMAIRE DE LA
	//TABLE unite_dmde SACHANT LE NUMERO DE LA DEMANDE ET DU MATERIEL
	
	
	
	//DEBUT DE LA FONCTION QUI MARQUE LA SORTIE DES MATERIAUX
	//DEMANDES
	public void marquerSortieMat()
	{	ResultSet res=null;
		int n=-1,m=-1;
		//ON MARQUE LA SORTIE DU MATERIEL.TOUT CE MATERIEL EST SORTI
		//D'UN COUP
		n=Connecteur.Insererdonnees("update unite_dmde set Idgestion=15,Historise='NON' where Iddmd="+this.idDmd+" and Idmateriel="+this.idMateriel+"");
   //ON VERIFIE S'IL Y AURAIT ENCORE DU MATERIEL A SORTIR SUR CETTE DEMANDE
		res=Connecteur.Extrairedonnees("select * from unite_dmde where Iddmd="+this.idDmd+" and Autorisation='OUI' and Idgestion is null");	
try {//SI PLUS DU MATERIEL A SORTIR SUR CETTE DEMANDE
	if(!res.next())
		{//ON VERIFIE S'IL N'Y AURAIT PAS DU MATERIEL NON ENCORE ETUDIE
		//PAR LE GERANT SUR CETTE DEMANDE
		res=Connecteur.Extrairedonnees("select * from unite_dmde where Iddmd="+this.idDmd+" and Idgerant is null");
		//S'IL N'Y EN A PAS, LA COMMANDE PASSE A L'ETAT 'TERMINEE'
		if(!res.next())
			m=Connecteur.Insererdonnees("update demande set Etatdmd='TERMINEE' where Iddemande="+this.idDmd+"");
		}
	
res=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+this.idMateriel+" and Historisation='OUI'");
if(res.next())
{
//(DEBUT)ON CREE DES ENREGISTREMENT DANS unites EN NOMBRE EGAL A LA QUANTITE
//SORTIE
	
//ON RECUPERE LA CLE PRIMAIRE DE LA TABLE unite_dmde
m=chercheIdUnite_dmde(this.idDmd,this.idMateriel);

for(int i=1;i<=this.quantiteMatAccord;i++)
{int k=-1;
k=Connecteur.Insererdonnees("insert into unites (Idunitedmd) values ("+m+")");
	}
	
//(FIN)ON CREE DES ENREGISTREMENT DANS unites EN NOMBRE EGAL A LA QUANTITE
//SORTIE
}
	this.quantiteMatAccord=0;
		if(n==-1)
			message="OPERATION ECHOUEE!!";
		else
			message="OPERATION REUSSIE!!";
			
			
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
	//FIN DE LA FONCTION QUI MARQUE LA SORTIE DES MATERIAUX
	//DEMANDES
	
	
public void ecouteAjouteHisto()
{System.out.println("000");
	ResultSet res=null;
	
if(this.idDmd==0)
{message="CHOISISSER LA DEMANDE S'IL VOUS PLAIT!!";
return;
	}

if(this.idMateriel==0)
{message="CHOISISSER LE MATERIEL S'IL VOUS PLAIT!!";
return;
	}

System.out.println("this.etatMat"+this.etatMat);

System.out.println("111");
System.out.println("this.etatMat"+this.etatMat);
if(this.etatMat==0)
{message="INDIQUER L'ETAT DU MATERIEL S'IL VOUS PLAIT!!";
System.out.println("222");
return;
	}


//IL FAUT QUE JE CHERCHE LE NOMBRE DE CE MATERIEL DEJA ENREGISTRE DANS
//unites
res=Connecteur.Extrairedonnees("select Idunitedmd from unite_dmde where Iddmd="+this.idDmd+" and Idmateriel="+this.idMateriel+"");
try {
	res.next();
	this.idUniteDmd=res.getInt("Idunitedmd");
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}


this.nombreDeMatDejaDansunites=0;

res=Connecteur.Extrairedonnees("select * from unites where Idunitedmd="+this.idUniteDmd+" and etat is not null");
try {
	while(res.next())
	{
		this.nombreDeMatDejaDansunites++;
	}

} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

//SI TOUT CE MATERIEL A DEJA ETE ENREGISTRE
if(this.nombreDeMatDejaDansunites>=this.quantiteMatAccord)
{message="TOUT CE MATERIEL EST DEJA ENREGISTRE";
return;
	}


int i=1;
//listAHistoriser=new ArrayList<DemandeBean>();
//System.out.println("LA TAILLE D'UNE LISTE VIDE EST "+listAHistoriser.size());
System.out.println("-1-1-1");
System.out.println("listAHistoriser.size() avant while"+listAHistoriser.size());
while(i<=listAHistoriser.size())
{System.out.println("-2-2-2");
System.out.println("listAHistoriser.size() dans while"+listAHistoriser.size());

	if(listAHistoriser.get(i-1).idUniteDmd==this.idUniteDmd)
		{System.out.println("-3-3-3");
		this.nombreDeMatDejaDansunites++;
		System.out.println("-4-4-4");
		}
	System.out.println("-5-5-5");
i++;
}


if(this.nombreDeMatDejaDansunites>=this.quantiteMatAccord)
{message="TOUT CE MATERIEL EST DEJA ENREGISTRE";
return;
	}

//ON BLOCK L'AJOUT DU MATERIEL SUR LA LISTE ALORS QU'IL N'Y A PLUS
//CE MATERIEL

if(this.etatMat==1)
	this.etatMatString="BON ETAT";
if(this.etatMat==2)
	this.etatMatString="EN PANNE";

res=Connecteur.Extrairedonnees("select Designation from materiel where Idmateriel="+this.idMateriel+"");
try {
	res.next();
	System.out.println("555");
	this.designationMat=res.getString("Designation");
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
if(listAHistoriser==null)
	listAHistoriser=new ArrayList<DemandeBean>();
System.out.println("666");
DemandeBean d=new DemandeBean();
d.idUniteDmd=this.idUniteDmd;
d.idDmd=this.idDmd;
d.idMateriel=this.idMateriel;
d.designationMat=this.designationMat;
d.etatMatString=this.etatMatString;
listAHistoriser.add(d);
System.out.println("777");
message="FAIT!";
	}


public void updateUnite()
{
int j,n=-1,idUni;
ResultSet res=null;
for(j=0;j<this.listAHistoriser.size();j++)
{n=-1;
res=Connecteur.Extrairedonnees("select * from unites where etat is null and Idunitedmd="+this.listAHistoriser.get(j).idUniteDmd+"");
try {
	if(res.next())
	{	//ON RECCUPERE L'id DU PREMIER
		idUni=res.getInt("Idunite");
		n=Connecteur.Insererdonnees("update unites set etat='"+this.listAHistoriser.get(j).etatMatString+"', DateDernMod=now() where Idunitedmd="+this.listAHistoriser.get(j).idUniteDmd+" and Idunite="+idUni+"");

	}
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}



//ON VERIFIT S'IL Y A ENCORE UN MATERIEL DE CE TYPE QUI N'EST PAS HISTORISE
res=Connecteur.Extrairedonnees("select * from unites where Idunitedmd="+this.listAHistoriser.get(j).idUniteDmd+" and etat is null");
try {//SI TOUT CE MATERIEL EST DEJA HISTORISE
	if(!res.next())
	{n=-1;
	n=Connecteur.Insererdonnees("update unite_dmde set Historise='OUI' where Idunitedmd="+this.listAHistoriser.get(j).idUniteDmd+"");
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
this.listAHistoriser.clear();
	}


public void modifierEtatMat()
{int n=-1;
if(this.selected.etatMat==0)
	{System.out.println("this.selected.etatMat"+this.selected.etatMat);
	message="VOUS N'AVEZ RIEN CHANGE!!";
	return;
	}
if(this.selected.etatMat==1)
	this.selected.etatMatString="BON ETAT";
if(this.selected.etatMat==2)
	this.selected.etatMatString="EN PANNE";
if(this.selected.etatMat==3)
	this.selected.etatMatString="ENDOMAGE";
System.out.println("this.selected.etatMatString"+this.selected.etatMatString);
n=Connecteur.Insererdonnees("update unites set etat='"+this.selected.etatMatString+"',DateDernMod=now() where Idunite="+this.selected.idUnites+"");
if(n==-1)
message="ECHEC DE MISE A JOUR!!";
else
message="MISE A JOUR REUSSIE!!";
}

}
