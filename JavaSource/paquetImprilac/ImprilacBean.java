package paquetImprilac;



import java.awt.Event;
import java.awt.Image;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.ServiceLoader;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import javax.swing.FocusManager;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.sun.servicetag.ServiceTag;






public class ImprilacBean {


//FacesContext context= FacesContext.getCurrentInstance();


	
	
	
	
	
//CETTE VARIABLE CONTIENT L'ID DE LA PERSONNE QUI A PROVOQUE
//L'INSTANCIATION DE CETTE BEAN
private int idPersonneConnecte;
public int getIdPersonneConnecte() {
	return idPersonneConnecte;
}
public void setIdPersonneConnecte(int idPersonneConnecte) {
	this.idPersonneConnecte = idPersonneConnecte;
}


private List<SelectItem> listPersonneConnecte=new ArrayList<SelectItem>();

public List<SelectItem> getListPersonneConnecte() {
	listPersonneConnecte.clear();
	listPersonneConnecte.add(new SelectItem(this.idPersonneConnecte,""+this.idPersonneConnecte+""));
	return listPersonneConnecte;
}

public void setListPersonneConnecte(List<SelectItem> listPersonneConnecte) {
	this.listPersonneConnecte = listPersonneConnecte;
}




//Debut proprietes des personnes
private String nomPersonne=null;
private String prenomPersonne=null;
private Date dateNaissance=null;
private int idPersonn=0;
private int idcategoriePersonne;
private String diplome=null;
private String bureau=null;
private String tel=null;
private String profil=null;
private int idPerso;

private boolean showDiplome=false;
private boolean showBureau=false;
private boolean showTel=false;
private boolean modifier=false;
private boolean modifierComptel1=true;
private boolean modifierCompte=false;
private boolean showdesignation;
private boolean showhistorisation;
private boolean showNumeroLigne;

private int numeroLigne=0;
private int idCompte;
private int numLigneUtiliseSurPageCompte=0;
private int idHistorisation;
private int numero;
private int qtiteMatEnStock;

private String designation=null;
private String historisation=null;
private String message;

private static List<SelectItem> listper;
private static List<SelectItem> listper2;
private static List<SelectItem> listDesPersoAyantLogEtPassW;
private static ArrayList listCopieLogin=null;

private String type=null;
private String nouveauLogin=null;
private String nouveauPassWord=null;


private static List<SelectItem> listMat;
private static List<SelectItem> listProduit;
private static List<SelectItem> listMaterielProduit;
private static List<SelectItem> listTypes;
private static List<SelectItem> listhistorisation;

private static List<ImprilacBean> listDesPersoAyantLogEtPw;
private static List<ImprilacBean> listMaterielProduits;

private ImprilacBean selected=null;







public int getQtiteMatEnStock() {
	return qtiteMatEnStock;
}
public void setQtiteMatEnStock(int qtiteMatEnStock) {
	this.qtiteMatEnStock = qtiteMatEnStock;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getNouveauLogin() {
	return nouveauLogin;
}
public void setNouveauLogin(String nouveauLogin) {
	this.nouveauLogin = nouveauLogin;
}
public String getNouveauPassWord() {
	return nouveauPassWord;
}
public void setNouveauPassWord(String nouveauPassWord) {
	this.nouveauPassWord = nouveauPassWord;
}
public ImprilacBean getSelected() {
	return selected;
}
public void setSelected(ImprilacBean selected) {
	this.selected = selected;
}
public int getNumero() {
	return numero;
}
public void setNumero(int numero) {
	this.numero = numero;
}

public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getHistorisation() {
	return historisation;
}
public void setHistorisation(String historisation) {
	this.historisation = historisation;
}
public boolean isShowNumeroLigne() {
	return showNumeroLigne;
}
public void setShowNumeroLigne(boolean showNumeroLigne) {
	this.showNumeroLigne = showNumeroLigne;
}
public List<SelectItem> getListMaterielProduit() {
	return listMaterielProduit;
}
public void setListMaterielProduit(List<SelectItem> listMaterielProduit) {
	ImprilacBean.listMaterielProduit = listMaterielProduit;
}
public  List<SelectItem> getListProduit() {
	return listProduit;
}
public static void setListProduit(List<SelectItem> listProduit) {
	ImprilacBean.listProduit = listProduit;
}
public  List<SelectItem> getListMat() {
	
	ResultSet res=null;
	
	if(listMat==null)
		listMat=new ArrayList<SelectItem>();
	else
		listMat.clear();
	
	listMat.add(new SelectItem(0,""));
	res=Connecteur.Extrairedonnees("select * from materiel where supprime=0");

	try {
		while(res.next())
		{listMat.add(new SelectItem(res.getInt("Idmateriel"),res.getString("Designation")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listMat;
}
public static void setListMat(List<SelectItem>listMat) {
	ImprilacBean.listMat = listMat;
}

public int getNumLigneUtiliseSurPageCompte() {
	return numLigneUtiliseSurPageCompte;
}
public void setNumLigneUtiliseSurPageCompte(int numLigneUtiliseSurPageCompte) {
	this.numLigneUtiliseSurPageCompte = numLigneUtiliseSurPageCompte;
}
public boolean isModifierComptel1() {
	return modifierComptel1;
}
public void setModifierComptel1(boolean modifierComptel1) {
	this.modifierComptel1 = modifierComptel1;
}
public List<SelectItem> getListper2() {
	 
	ResultSet res=null;


	if(listper2==null)
		listper2=new ArrayList<SelectItem>();
	else
		listper2.clear();

	listper2.add(new SelectItem(0,"    "));

	System.out.println("JE SUIS DANS ECOUTEMODIFIER!!");


	if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
	{System.out.println("JE SUIS DANS ECOUTEMODIFIER!!");
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod and c.supprimee=0 order by Nompersonne");

	try {
		while(res.next())
		{
		listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	if(idcategoriePersonne==2)//ON A CHOISIE LE PROFIL DU GERANT
	{	System.out.println("JE SUIS DANS ECOUTEMODIFIER!!");
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g where p.Idpersonne=g.Idgerant and g.supprimee=0 order by Nompersonne");

	try {
		while(res.next())
		{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	if(idcategoriePersonne==3)//ON A CHOISIE LE PROFIL GESTIONNAIRE
	{
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g where p.Idpersonne=g.Idgestion and g.supprimee=0 order by Nompersonne");

		try {
			while(res.next())
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	if(idcategoriePersonne==4)//ON A CHOISIE LE PROFIL CLIENT
	{
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and c.supprimee=0 order by Nompersonne");

		try {
			while(res.next())
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	if(idcategoriePersonne==5)//ON A CHOISIE LE PROFIL PRODUCTEUR
	{
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro where p.Idpersonne=pro.Idproduct and pro.supprimee=0 order by Nompersonne");

		try {
			while(res.next())
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"  "+res.getInt("Idpersonne")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	if(idcategoriePersonne==6)//ON A CHOISIE LE PROFIL DU CAISSIER
	{
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c where p.Idpersonne=c.Idcaissier and c.supprimee=0 order by Nompersonne");

		try {
			while(res.next())
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"   "+res.getInt("Idpersonne")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	return listper2;
}
public static void setListper2(List<SelectItem> listper2) {
	ImprilacBean.listper2 = listper2;
}
public int getIdCompte() {
	return idCompte;
}
public void setIdCompte(int idCompte) {
	this.idCompte = idCompte;
}

public List<SelectItem> getListDesPersoAyantLogEtPassW() {
	numeroLigne=0;
	ResultSet res=null;
	//ImprilacBean chef=null;
	
	if(listDesPersoAyantLogEtPassW==null)
		listDesPersoAyantLogEtPassW=new ArrayList<SelectItem>();
	else
		listDesPersoAyantLogEtPassW.clear();
	
	if(listCopieLogin==null)
		listCopieLogin=new ArrayList();
	else
		listCopieLogin.clear();
	
	if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
	{	numeroLigne=0;
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c,compte co where p.Idpersonne=c.Idcheprod and p.Idpersonne=co.Idpersonne order by p.Nompersonne");
		
	try {
		while(res.next())
		{listCopieLogin.add(res.getString("Login").toString());
		listDesPersoAyantLogEtPassW.add(new SelectItem(numeroLigne,numeroLigne+"."+res.getString("Login")));	
		numeroLigne++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="CHEFS DE PRODUCTION";
	}
	if(idcategoriePersonne==2)//ON A CHOISIE LE PROFIL DU GERANT
	{	numeroLigne=0;
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g,compte co where p.Idpersonne=g.Idgerant and p.Idpersonne=co.Idpersonne order by p.Nompersonne");
		
	try {
		while(res.next())
		{listCopieLogin.add(res.getString("Login").toString());
		listDesPersoAyantLogEtPassW.add(new SelectItem(numeroLigne,numeroLigne+"."+res.getString("Login")));	
		numeroLigne++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="DES GERANT";
	}
	if(idcategoriePersonne==3)//ON A CHOISIE LE PROFIL GESTIONNAIRE
	{	numeroLigne=0;
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g,compte co where p.Idpersonne=g.Idgestion and p.Idpersonne=co.Idpersonne order by p.Nompersonne");
		try {
			while(res.next())
			{listCopieLogin.add(res.getString("Login").toString());
			listDesPersoAyantLogEtPassW.add(new SelectItem(numeroLigne,numeroLigne+"."+res.getString("Login")));	
			numeroLigne++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	profil="GESTIONNAIRES";
	}
	if(idcategoriePersonne==4)//ON A CHOISIE LE PROFIL CLIENT
	{	numeroLigne=0;
		res=Connecteur.Extrairedonnees("select * from personne p,client c,compte co where p.Idpersonne=c.Idclient and p.Idpersonne=co.Idpersonne order by p.Nompersonne");
		try {
			while(res.next())
			{listCopieLogin.add(res.getString("Login").toString());
			listDesPersoAyantLogEtPassW.add(new SelectItem(numeroLigne,numeroLigne+"."+res.getString("Login")));	
			numeroLigne++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CLIENTS";
	}
	if(idcategoriePersonne==5)//ON A CHOISIE LE PROFIL PRODUCTEUR
	{	numeroLigne=0;
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro,compte co where p.Idpersonne=pro.Idproduct and p.Idpersonne=co.Idpersonne order by p.Nompersonne");
		try {
			while(res.next())
			{listCopieLogin.add(res.getString("Login").toString());
			listDesPersoAyantLogEtPassW.add(new SelectItem(numeroLigne,numeroLigne+"."+res.getString("Login")));	
			numeroLigne++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		profil="PRODUCTEURS";
	}
	if(idcategoriePersonne==6)//ON A CHOISIE LE PROFIL DU CAISSIER
	{	numeroLigne=0;
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c,compte co where p.Idpersonne=c.Idcaissier and p.Idpersonne=co.Idpersonne order by p.Nompersonne");
		try {
			while(res.next())
			{listCopieLogin.add(res.getString("Login").toString());
			listDesPersoAyantLogEtPassW.add(new SelectItem(numeroLigne,numeroLigne+"."+res.getString("Login")));	
			numeroLigne++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CAISSIERS";
	}
	
	this.nomPersonne=null;
	this.prenomPersonne=null;
	this.bureau=null;
	this.diplome=null;
	this.tel=null;
	this.login=null;
	this.passWord=null;
	
	return listDesPersoAyantLogEtPassW;
}


public static void setListDesPersoAyantLogEtPassW(
		List<SelectItem> listDesPersoAyantLogEtPassW) {
	ImprilacBean.listDesPersoAyantLogEtPassW = listDesPersoAyantLogEtPassW;
}
public int getNumeroLigne() {
	return numeroLigne;
}
public void setNumeroLigne(int numeroLigne) {
	this.numeroLigne = numeroLigne;
}
public boolean isModifierCompte() {
	return modifierCompte;
}
public void setModifierCompte(boolean modifierCompte) {
	this.modifierCompte = modifierCompte;
}
public void setListDesPersoAyantLogEtPw(
		List<ImprilacBean> listDesPersoAyantLogEtPw) {
	ImprilacBean.listDesPersoAyantLogEtPw = listDesPersoAyantLogEtPw;
}
public List<ImprilacBean> getListDesPersoAyantLogEtPw() {
	
	ResultSet res=null;
	ImprilacBean chef=null;
	
	if(listDesPersoAyantLogEtPw==null)
		listDesPersoAyantLogEtPw=new ArrayList<ImprilacBean>();
	else
		listDesPersoAyantLogEtPw.clear();
	
	if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
	{
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c,compte co where p.Idpersonne=c.Idcheprod and p.Idpersonne=co.Idpersonne and c.supprimee=0 and co.Profil='CHEF DE PRODUCTION' order by p.Nompersonne");

	try {this.numLigneUtiliseSurPageCompte=0;
		while(res.next())
		{chef=new ImprilacBean();

		chef.numLigneUtiliseSurPageCompte=this.numLigneUtiliseSurPageCompte;
		 chef.nomPersonne=res.getString("Nompersonne");
		 chef.prenomPersonne=res.getString("Prenompersonne");
		 chef.diplome=res.getString("Diplome");
		 chef.dateNaissance=res.getDate("Datenaissance");
		 chef.bureau=res.getString("Bureau");
		 chef.login=res.getString("Login");
		 chef.passWord=res.getString("Password");
		 chef.idPersonne=res.getInt("Idpersonne");
		 listDesPersoAyantLogEtPw.add(chef);
		
		this.numLigneUtiliseSurPageCompte++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="CHEFS DE PRODUCTION";
	}
	if(idcategoriePersonne==2)//ON A CHOISIE LE PROFIL DU GERANT
	{	
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g,compte co where p.Idpersonne=g.Idgerant and p.Idpersonne=co.Idpersonne and g.supprimee=0 and co.Profil='GERANT' order by p.Nompersonne");

	try {this.numLigneUtiliseSurPageCompte=0;
		while(res.next())
		{chef=new ImprilacBean();

		chef.numLigneUtiliseSurPageCompte=this.numLigneUtiliseSurPageCompte;
		 chef.nomPersonne=res.getString("Nompersonne");
		 chef.prenomPersonne=res.getString("Prenompersonne");
		 chef.diplome=res.getString("Diplome");
		 chef.dateNaissance=res.getDate("Datenaissance");
		 chef.bureau=res.getString("Bureau");
		 chef.login=res.getString("Login");
		 chef.passWord=res.getString("Password");
		 chef.idPersonne=res.getInt("Idpersonne");
		 listDesPersoAyantLogEtPw.add(chef);
		this.numLigneUtiliseSurPageCompte++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="DES GERANT";
	}
	if(idcategoriePersonne==3)//ON A CHOISIE LE PROFIL GESTIONNAIRE
	{
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g,compte co where p.Idpersonne=g.Idgestion and p.Idpersonne=co.Idpersonne and g.supprimee=0 and co.Profil='GESTIONNAIRE' order by p.Nompersonne");

		try {this.numLigneUtiliseSurPageCompte=0;
			while(res.next())
			{chef=new ImprilacBean();
			
			chef.numLigneUtiliseSurPageCompte=this.numLigneUtiliseSurPageCompte;
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");
			 chef.login=res.getString("Login");
			 chef.passWord=res.getString("Password");
			 chef.idPersonne=res.getInt("Idpersonne");
			 listDesPersoAyantLogEtPw.add(chef);
			 this.numLigneUtiliseSurPageCompte++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	profil="GESTIONNAIRES";
	}
	if(idcategoriePersonne==4)//ON A CHOISIE LE PROFIL CLIENT
	{
		res=Connecteur.Extrairedonnees("select * from personne p,client c,compte co where p.Idpersonne=c.Idclient and p.Idpersonne=co.Idpersonne and c.supprimee=0 order by p.Nompersonne");

		try {this.numLigneUtiliseSurPageCompte=0;
			while(res.next())
			{chef=new ImprilacBean();
			
			chef.numLigneUtiliseSurPageCompte=this.numLigneUtiliseSurPageCompte;
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.tel=res.getString("tel");
			 chef.dateNaissance=res.getDate("Datenaissance");
			 chef.login=res.getString("Login");
			 chef.passWord=res.getString("Password");
			 chef.idPersonne=res.getInt("Idpersonne");
			 listDesPersoAyantLogEtPw.add(chef);
			 this.numLigneUtiliseSurPageCompte++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CLIENTS";
	}
	if(idcategoriePersonne==5)//ON A CHOISIE LE PROFIL PRODUCTEUR
	{
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro,compte co where p.Idpersonne=pro.Idproduct and p.Idpersonne=co.Idpersonne and pro.supprimee=0 and co.Profil='PRODUCTEUR' order by p.Nompersonne");

		try {this.numLigneUtiliseSurPageCompte=0;
			while(res.next())
			{chef=new ImprilacBean();
			
			chef.numLigneUtiliseSurPageCompte=this.numLigneUtiliseSurPageCompte;
			chef.idPersonne=res.getInt("Idpersonne");
			chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");
			 chef.bureau=res.getString("Bureau");
			 chef.login=res.getString("Login");
			 chef.passWord=res.getString("Password");
			 listDesPersoAyantLogEtPw.add(chef);
			 this.numLigneUtiliseSurPageCompte++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		profil="PRODUCTEURS";
	}
	if(idcategoriePersonne==6)//ON A CHOISIE LE PROFIL DU CAISSIER
	{
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c,compte co where p.Idpersonne=c.Idcaissier and p.Idpersonne=co.Idpersonne and c.supprimee=0 and co.Profil='CAISSIER' order by p.Nompersonne");

		try {this.numLigneUtiliseSurPageCompte=0;
			while(res.next())
			{chef=new ImprilacBean();
			
			chef.numLigneUtiliseSurPageCompte=this.numLigneUtiliseSurPageCompte;
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");
			 chef.login=res.getString("Login");
			 chef.passWord=res.getString("Password");
			 chef.idPersonne=res.getInt("Idpersonne");
			 listDesPersoAyantLogEtPw.add(chef);
			 this.numLigneUtiliseSurPageCompte++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CAISSIERS";
	}
	
	this.nomPersonne=null;
	this.prenomPersonne=null;
	this.bureau=null;
	this.diplome=null;
	this.tel=null;
	this.login=null;
	this.passWord=null;

	return listDesPersoAyantLogEtPw;
}


public int getIdPerso() {
	return idPerso;
}
public void setIdPerso(int idPerso) {
	this.idPerso = idPerso;
}
public List<SelectItem> getListper() {
	if(listper==null)
		{listper=new ArrayList<SelectItem>();
		//listper.add(new SelectItem())
		
		}

	return listper;
}
public void setListper(List<SelectItem> listper) {
	ImprilacBean.listper = listper;
}
public boolean isModifier() {
	return modifier;
}
public void setModifier(boolean modifier) {
	this.modifier = modifier;
}
public String getProfil() {
	return profil;
}
public void setProfil(String profil) {
	this.profil = profil;
}
public boolean isShowDiplome() {
	return showDiplome;
}
public void setShowDiplome(boolean showDiplome) {
	this.showDiplome = showDiplome;
}
public boolean isShowBureau() {
	return showBureau;
}
public void setShowBureau(boolean showBureau) {
	this.showBureau = showBureau;
}
public boolean isShowTel() {
	return showTel;
}
public void setShowTel(boolean showTel) {
	this.showTel = showTel;
}
public String getNomPersonne() {
	return nomPersonne;
}
public void setNomPersonne(String nomPersonne) {
	this.nomPersonne = nomPersonne;
}
public String getPrenomPersonne() {
	return prenomPersonne;
}
public void setPrenomPersonne(String prenomPersonne) {
	this.prenomPersonne = prenomPersonne;
}
public Date getDateNaissance() {
	return dateNaissance;
}
public void setDateNaissance(Date dateNaissance) {
	this.dateNaissance = dateNaissance;
}

public int getIdcategoriePersonne() {
	return idcategoriePersonne;
}
public void setIdcategoriePersonne(int idcategoriePersonne) {
	this.idcategoriePersonne = idcategoriePersonne;
}
public String getDiplome() {
	return diplome;
}
public void setDiplome(String diplome) {
	this.diplome = diplome;
}
public String getBureau() {
	return bureau;
}
public void setBureau(String bureau) {
	this.bureau = bureau;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
//Fin proprietes des personnes

//DEBUT DES LISTES DES PERSONNES
private static List<ImprilacBean> listPesonnes;
private List<ImprilacBean> listPesonnesClient;

public void ecouteModMatProd()
{
if(this.modifier==false)
	this.modifier=true;
else
	this.modifier=false;

ResultSet res=null;

if(listMaterielProduit==null)
	listMaterielProduit=new ArrayList<SelectItem>();
else
	listMaterielProduit.clear();

listMaterielProduit.add(new SelectItem(0,"    "));


if(idType==1)//ON MODIFIE LES MATERIELS
{
	res=Connecteur.Extrairedonnees("select * from materiel ");

	try {
		while(res.next())
		{listMaterielProduit.add(new SelectItem(res.getInt("Idmateriel"),res.getInt("Idmateriel")+" "+res.getString("Designation")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
if(idType==2)//ON MODIFIE LES PRODUITS
{
	res=Connecteur.Extrairedonnees("select * from produits ");

	try {
		while(res.next())
		{listMaterielProduit.add(new SelectItem(res.getInt("Idprod"),res.getInt("Idprod")+" "+res.getString("Type")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

	}



public void buildListDesPers()//DEBUT DE LA FONCTION QUI NOUS PERMET DE PASSER AU MODE MODIFIER ET DE CONSTRUIRE LA LISTE DES PERSONNES D'UNE CATEGORIE CHOISIE
{this.modifier=true;
 
ResultSet res=null;


if(listper==null)
	listper=new ArrayList<SelectItem>();
else
	listper.clear();

listper.add(new SelectItem(0,"    "));

/*if(listMaterielProduit==null)
	listMaterielProduit=new ArrayList<SelectItem>();
else
	listMaterielProduit.clear();

listMaterielProduit.add(new SelectItem(0,"    "));*/


System.out.println("JE SUIS DANS ECOUTEMODIFIER!!");


if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
{System.out.println("JE SUIS DANS ECOUTEMODIFIER!!");
	res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod");

try {
	while(res.next())
	{
listper.add(new SelectItem(res.getInt("Idpersonne"),res.getInt("Idpersonne")+". "+res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
if(idcategoriePersonne==2)//ON A CHOISIE LE PROFIL DU GERANT
{	System.out.println("JE SUIS DANS ECOUTEMODIFIER!!");
	res=Connecteur.Extrairedonnees("select * from personne p,gerant g where p.Idpersonne=g.Idgerant");

try {
	while(res.next())
	{listper.add(new SelectItem(res.getInt("Idpersonne"),res.getInt("Idpersonne")+". "+res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}
if(idcategoriePersonne==3)//ON A CHOISIE LE PROFIL GESTIONNAIRE
{
	res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g where p.Idpersonne=g.Idgestion");

	try {
		while(res.next())
		{listper.add(new SelectItem(res.getInt("Idpersonne"),res.getInt("Idpersonne")+". "+res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
if(idcategoriePersonne==4)//ON A CHOISIE LE PROFIL CLIENT
{
	res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient");

	try {
		while(res.next())
		{listper.add(new SelectItem(res.getInt("Idpersonne"),res.getInt("Idpersonne")+". "+res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
if(idcategoriePersonne==5)//ON A CHOISIE LE PROFIL PRODUCTEUR
{
	res=Connecteur.Extrairedonnees("select * from personne p,producteur pro where p.Idpersonne=pro.Idproduct");

	try {
		while(res.next())
		{listper.add(new SelectItem(res.getInt("Idpersonne"),res.getInt("Idpersonne")+". "+res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

}
if(idcategoriePersonne==6)//ON A CHOISIE LE PROFIL DU CAISSIER
{
	res=Connecteur.Extrairedonnees("select * from personne p,caissier c where p.Idpersonne=c.Idcaissier");

	try {
		while(res.next())
		{listper.add(new SelectItem(res.getInt("Idpersonne"),res.getInt("Idpersonne")+". "+res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


/*if(idType==1)//ON MODIFIE LES MATERIELS
{
	res=Connecteur.Extrairedonnees("select * from materiel ");

	try {
		while(res.next())
		{listMaterielProduit.add(new SelectItem(res.getInt("Idmateriel"),res.getInt("Idmateriel")+" "+res.getString("Designation")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
if(idType==2)//ON MODIFIE LES PRODUITS
{
	res=Connecteur.Extrairedonnees("select * from produits ");

	try {
		while(res.next())
		{listMaterielProduit.add(new SelectItem(res.getInt("Idprod"),res.getInt("Idprod")+" "+res.getString("Type")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}*/
	
	}//FIN DE LA FONCTION QUI CONSTRUIT LA LISTE DES PERSONNES


public  List<ImprilacBean> getListMaterielProduits() {
	
	
	ResultSet res=null;
	ImprilacBean Matpro=null;
	
	if(listMaterielProduits==null)
		listMaterielProduits=new ArrayList<ImprilacBean>();
	else
		listMaterielProduits.clear();
	
	if(idType==1)//ON A CHOISIE LE MATERIEL
	{
		res=Connecteur.Extrairedonnees("select * from materiel where supprime=0");

	try {
		while(res.next())
		{Matpro=new ImprilacBean();
		Matpro.numero=res.getInt("Idmateriel");
		Matpro.designation=res.getString("Designation");
		Matpro.historisation=res.getString("Historisation");
		Matpro.qtiteMatEnStock=res.getInt("quantiteEnStocks");

		listMaterielProduits.add(Matpro);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	type="DES MATERIEL";
	}
	if(idType==2)//ON A CHOISIE LE PRODUIT
	{	
		res=Connecteur.Extrairedonnees("select * from produits where supprime=0 ");

	try {
		while(res.next())
		{Matpro=new ImprilacBean();
		Matpro.numero=res.getInt("Idprod");
		Matpro.designation=res.getString("Type");
		 
		listMaterielProduits.add(Matpro);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		type="DES PRODUITS";
	}

	return listMaterielProduits;
}

public static void setListMaterielProduits(
		List<ImprilacBean> listMaterielProduits) {
	ImprilacBean.listMaterielProduits = listMaterielProduits;
}

//



//public List<ImprilacBean> getListPesonnes() {
/*

	
	ResultSet res=null;
	ImprilacBean chef=null;
	
	if(listPesonnes==null)
		listPesonnes=new ArrayList<ImprilacBean>();
	else
		listPesonnes.clear();
	
	int n=1;
	
	if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
	{
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod and c.supprimee=0 order by Nompersonne");
	if(listPesonnes==null)
		listPesonnes=new ArrayList<ImprilacBean>();
	else
		listPesonnes.clear();
	try {
		n=1;
		while(res.next())
		{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
		 chef.idPersonn=res.getInt("Idpersonne");
		 chef.nomPersonne=res.getString("Nompersonne");
		 chef.prenomPersonne=res.getString("Prenompersonne");
		 chef.diplome=res.getString("Diplome");
		 chef.dateNaissance=res.getDate("Datenaissance");
		 chef.bureau=res.getString("Bureau");

		 listPesonnes.add(chef);
		 
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="CHEFS DE PRODUCTION";
	}
	if(idcategoriePersonne==2)//ON A CHOISIE LE PROFIL DU GERANT
	{	
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g where p.Idpersonne=g.Idgerant and g.supprimee=0 order by Nompersonne");
	try {
		n=1;
		while(res.next())
		{chef=new ImprilacBean();
		chef.idPersonne=n;
		n++;
		 chef.idPersonn=res.getInt("Idpersonne");
		 chef.nomPersonne=res.getString("Nompersonne");
		 chef.prenomPersonne=res.getString("Prenompersonne");
		 chef.diplome=res.getString("Diplome");
		 chef.dateNaissance=res.getDate("Datenaissance");
		 chef.bureau=res.getString("Bureau");

		 listPesonnes.add(chef);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="DES GERANT";
	}
	if(idcategoriePersonne==3)//ON A CHOISIE LE PROFIL GESTIONNAIRE
	{
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g where p.Idpersonne=g.Idgestion and g.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	profil="GESTIONNAIRES";
	}
	if(idcategoriePersonne==4)//ON A CHOISIE LE PROFIL CLIENT
	{
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and c.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.tel=res.getString("tel");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CLIENTS";
	}
	if(idcategoriePersonne==5)//ON A CHOISIE LE PROFIL PRODUCTEUR
	{
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro where p.Idpersonne=pro.Idproduct and pro.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");
			 chef.bureau=res.getString("Bureau");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		profil="PRODUCTEURS";
	}
	if(idcategoriePersonne==6)//ON A CHOISIE LE PROFIL DU CAISSIER
	{
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c where p.Idpersonne=c.Idcaissier and c.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CAISSIERS";
	}
	
	this.nomPersonne=null;
	this.prenomPersonne=null;
	this.bureau=null;
	this.diplome=null;
	this.tel=null;
	this.login=null;
	this.passWord=null;
	return listPesonnes;*/
	//}



/*public List<ImprilacBean> getListPesonnesClient() {
	


	
	ResultSet res=null;
	ImprilacBean chef=null;
	
	if(listPesonnesClient==null)
		listPesonnesClient=new ArrayList<ImprilacBean>();
	else
		listPesonnesClient.clear();
	
	int n=1;
	
	

	
	
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and c.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.tel=res.getString("tel");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnesClient.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CLIENTS";
	
	
	
	
	this.nomPersonne=null;
	this.prenomPersonne=null;
	//this.bureau=null;
	//this.diplome=null;
	this.tel=null;
	this.login=null;
	this.passWord=null;

	return listPesonnesClient;
}*/

/*public void setListPesonnesClient(List<ImprilacBean> listPesonnesClient) {
	listPesonnesClient = listPesonnesClient;
}*/


//

public List<ImprilacBean> getListPesonnes() {

	
	ResultSet res=null;
	ImprilacBean chef=null;
	
	if(listPesonnes==null)
		listPesonnes=new ArrayList<ImprilacBean>();
	else
		listPesonnes.clear();
	
	int n=1;
	
	if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
	{
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod and c.supprimee=0 order by Nompersonne");
/*	if(listPesonnes==null)
		listPesonnes=new ArrayList<ImprilacBean>();
	else
		listPesonnes.clear();*/
	try {
		n=1;
		while(res.next())
		{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
		 chef.idPersonn=res.getInt("Idpersonne");
		 chef.nomPersonne=res.getString("Nompersonne");
		 chef.prenomPersonne=res.getString("Prenompersonne");
		 chef.diplome=res.getString("Diplome");
		 chef.dateNaissance=res.getDate("Datenaissance");
		 chef.bureau=res.getString("Bureau");

		 listPesonnes.add(chef);
		 
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="CHEFS DE PRODUCTION";
	}
	if(idcategoriePersonne==2)//ON A CHOISIE LE PROFIL DU GERANT
	{	
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g where p.Idpersonne=g.Idgerant and g.supprimee=0 order by Nompersonne");
	try {
		n=1;
		while(res.next())
		{chef=new ImprilacBean();
		chef.idPersonne=n;
		n++;
		 chef.idPersonn=res.getInt("Idpersonne");
		 chef.nomPersonne=res.getString("Nompersonne");
		 chef.prenomPersonne=res.getString("Prenompersonne");
		 chef.diplome=res.getString("Diplome");
		 chef.dateNaissance=res.getDate("Datenaissance");
		 chef.bureau=res.getString("Bureau");

		 listPesonnes.add(chef);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		profil="DES GERANT";
	}
	if(idcategoriePersonne==3)//ON A CHOISIE LE PROFIL GESTIONNAIRE
	{
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g where p.Idpersonne=g.Idgestion and g.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	profil="GESTIONNAIRES";
	}
	if(idcategoriePersonne==4)//ON A CHOISIE LE PROFIL CLIENT
	{
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and c.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.tel=res.getString("tel");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CLIENTS";
	}
	if(idcategoriePersonne==5)//ON A CHOISIE LE PROFIL PRODUCTEUR
	{
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro where p.Idpersonne=pro.Idproduct and pro.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");
			 chef.bureau=res.getString("Bureau");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		profil="PRODUCTEURS";
	}
	if(idcategoriePersonne==6)//ON A CHOISIE LE PROFIL DU CAISSIER
	{
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c where p.Idpersonne=c.Idcaissier and c.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.diplome=res.getString("Diplome");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnes.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CAISSIERS";
	}
	
	this.nomPersonne=null;
	this.prenomPersonne=null;
	this.bureau=null;
	this.diplome=null;
	this.tel=null;
	this.login=null;
	this.passWord=null;
	return listPesonnes;
}




public List<ImprilacBean> getListPesonnesClient() {
	
	
	
	ResultSet res=null;
	ImprilacBean chef=null;
	
	if(listPesonnesClient==null)
		listPesonnesClient=new ArrayList<ImprilacBean>();
	else
		listPesonnesClient.clear();
	
	int n=1;

	
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and c.supprimee=0 order by Nompersonne");

		try {
			n=1;
			while(res.next())
			{chef=new ImprilacBean();
			chef.idPersonne=n;
			n++;
			 chef.idPersonn=res.getInt("Idpersonne");
			 chef.nomPersonne=res.getString("Nompersonne");
			 chef.prenomPersonne=res.getString("Prenompersonne");
			 chef.tel=res.getString("tel");
			 chef.dateNaissance=res.getDate("Datenaissance");

			 listPesonnesClient.add(chef);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profil="CLIENTS";
	
	
	
	
	this.nomPersonne=null;
	this.prenomPersonne=null;
	//this.bureau=null;
	//this.diplome=null;
	this.tel=null;
	this.login=null;
	this.passWord=null;
	
	
	return listPesonnesClient;
}
public void setListPesonnesClient(List<ImprilacBean> listPesonnesClient) {
	this.listPesonnesClient = listPesonnesClient;
}
public void setListPesonnes(List<ImprilacBean> listPesonnes) {
	ImprilacBean.listPesonnes = listPesonnes;
}


public int getIdPersonn() {
	return idPersonn;
}
public void setIdPersonn(int idPersonn) {
	this.idPersonn = idPersonn;
}

//Debut des proprietes pour l'authentification
private String login=null;
private String passWord;
private int idProfil;
private int idPersonne;

public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getPassWord() {
	return passWord;
}
public void setPassWord(String passWord) {
	this.passWord = passWord;
}
public int getIdProfil() {
	return idProfil;
}
public void setIdProfil(int idProfil) {
	this.idProfil = idProfil;
}
public int getIdPersonne() {
	return idPersonne;
}
public void setIdPersonne(int idPersonne) {
	this.idPersonne = idPersonne;
}

//Fin des proprietes pour l'authentification




									
//Debut de la partie pour les materiaux et produits
private int idType;


public int getIdType() {
	return idType;
}
public void setIdType(int idType) {
	this.idType = idType;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public int getIdHistorisation() {
	return idHistorisation;
}
public void setIdHistorisation(int idHistorisation) {
	this.idHistorisation = idHistorisation;
}
public List<SelectItem> getListTypes() {
	if(listTypes==null)
		listTypes=new ArrayList<SelectItem>();
	else 
		listTypes.clear();
	listTypes.add(new SelectItem(0,""));
	listTypes.add(new SelectItem(1,"MATERIEL"));
	listTypes.add(new SelectItem(2,"PRODUIT"));
	return listTypes;
}
public void setListTypes(List<SelectItem> listTypes) {
	ImprilacBean.listTypes = listTypes;
}
public List<SelectItem> getListhistorisation() {
	if(listhistorisation==null)
		listhistorisation=new ArrayList<SelectItem>();
	else 
	listhistorisation.clear();
	listhistorisation.add(new SelectItem(""));
	listhistorisation.add(new SelectItem("OUI"));
	listhistorisation.add(new SelectItem("NON"));
	return listhistorisation;
}
public void setListhistorisation(List<SelectItem> listhistorisation) {
	ImprilacBean.listhistorisation = listhistorisation;
}


public void enregistreMaterielProd()
{
	int n=-1;
	

	if(this.modifier!=true)//ON FAIT L'INSERTION PAS LA MODIFICATION
	{	
	 if(this.idType==0)
	   {
	    message="CHOISIR LE TYPE S'IL VOUS PLAIT!";
	    return;
	   } 
	 ResultSet res=null;
	 
	if(this.idType==1)//ON INSERT UN MATERIEL
	  {  
		if(this.designation==null||this.designation=="")
	      { 
		   message="TAPEZ LE NOM DU MATERIEL S'IL VOUS PLAIT!";
		   return; 
	      }
	      if(this.historisation==null||this.historisation=="")
	      {
	      message="INDIQUER S'IL EST NECESSAIRE D'ETRE HISTORISE OU PAS!";
	      return;
	      }  
	     // this.designation=this.designation.toUpperCase();
	      res=Connecteur.Extrairedonnees("select * from materiel where Designation='"+this.designation+"' and supprime=0");
	      try {
			if(res.next())
			  {message="CE MATERIEL EXISTE DEJA!!";
				return; 
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		
	      res=Connecteur.Extrairedonnees("select * from materiel where Designation='"+this.designation+"' and supprime=1");
	      try {
	    	  System.out.println("00");
			if(res.next())
			  {n=-1;
			  System.out.println("11");
				n=Connecteur.Insererdonnees("update materiel set supprime=0 where Idmateriel="+res.getInt("Idmateriel")+"");
				if(n!=-1)
					message="INSERTION REUSSIE!!";
				else
					message="INSERTION ECHOUEE!!";
				
				 System.out.println("22");
				
				return; 
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("33");
		n=-1;
		   n=Connecteur.Insererdonnees("insert into materiel(Designation,Historisation,quantiteEnStocks,supprime) values ('"+this.designation+"','"+this.historisation+"',0,0)");
		   if(n!=-1)
			  message="INSERTION REUSSIE!";
		 else
			 message="ECHEC D'INSERTION!";
			System.out.println("idType :"+idType);
			System.out.println("designation :"+designation);
			System.out.println("historisation :"+historisation);
	  }
	
	
	if(this.idType==2)//ON INSERT UN PRODUIT
	  {n=-1;
	  //ResultSet res=null;
		if(this.designation==null||this.designation=="")
	    {  
		message="TAPEZ LE NOM DU PRODUIT S'IL VOUS PLAIT!";
		return;
	    }
		//this.designation=this.designation.toUpperCase();
		res=Connecteur.Extrairedonnees("select * from produits where Type='"+this.designation+"' and supprime=0");
		try {
			if(res.next())
			{message="CE PRODUIT EST DEJA ENREGISTRE!!";
			return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	      res=Connecteur.Extrairedonnees("select * from produits where Type='"+this.designation+"' and supprime=1");
	      try {
			if(res.next())
			  {n=-1;
				n=Connecteur.Insererdonnees("update produits set supprime=0 where Idprod="+res.getInt("Idprod")+"");
				if(n!=-1)
					message="INSERTION REUSSIE!!";
				else
					message="INSERTION ECHOUEE!!";
				return; 
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		n=-1;
		
		
		
		n=Connecteur.Insererdonnees("insert into produits (Type,supprime) values ('"+this.designation+"',0)");
		 if(n!=-1)
			  message="INSERTION REUSSIE!";
		 else
			 message="ECHEC D'INSERTION!";
			System.out.println("idType :"+idType);
			System.out.println("designation :"+designation);
	  }
	  }
	
	
	
	
	else//ON FAIT DES MODIFICATION
	{	this.modifier=false;
		if(this.idType==0)
		{System.out.println("1111111");
			message="CHOISISSER LE TYPE S'IL VOUS PLAIT!!!";
			return;
		}
		
		ResultSet res=null;
		
		if(this.idType==1)
		{	
			if((this.designation.length()<=0)&&(this.historisation.length()<=0))
			{  
			message="VOUS N'AVEZ RIEN MODIFIE!";
			
			return;
			}
			this.designation=this.designation.toUpperCase();
		      res=Connecteur.Extrairedonnees("select * from materiel where Designation='"+this.designation+"'");
		      try {
				if(res.next())
				  {this.designation=null;
				  this.historisation=null;
					message="CE MATERIEL EXISTE DEJA!!";
					return; 
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(this.designation.length()>0)
			{
			n=-1;
			n=Connecteur.Insererdonnees("update materiel set Designation='"+this.designation+"' where Idmateriel="+idPerso);
			if(n==-1)
			{message="ECHEC DE MISE A JOUR!";
			return;
			}
		    else
		    message="MISE A JOUR REUSSIE!";
			}
		
			if(this.historisation.length()>0)
			{n=-1;
			n=Connecteur.Insererdonnees("update materiel set Historisation='"+this.historisation+"' where Idmateriel="+idPerso);
		   if(n==-1)
			{message="ECHEC DE MISE A JOUR!";
			return;
			}
		    else
			message="MISE A JOUR REUSSIE!";
			}
			System.out.println("444444444444");	
		}
		
		if(this.idType==2)
		{
			res=Connecteur.Extrairedonnees("select * from produits where Type='"+this.designation+"'");
			try {
				if(res.next())
				{this.designation=null;
				this.historisation=null;
				message="CE PRODUIT EST DEJA ENREGISTRE!!";
				return;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		if(this.designation.length()>0)
		{n=-1;
			n=Connecteur.Insererdonnees("update produits set Type ='"+this.designation+"' where Idprod="+idPerso);
		   if(n==-1)
			{message="ECHEC DE MISE A JOUR!";
			}
		    else
		    	message="MISE A JOUR REUSSIE!";
		}
		else{
			message="VOUS N'AVEZ RIEN MODIFIER!!";
			return;
		}
	
		}
	
	}
	
	
	//Fin de la partie pour les materiaux et produits
this.designation=null;
this.historisation=null;

	}


public void supprimerMatProd(ActionEvent e)
{
	int n=-1;


	System.out.println("this.selected.getNumero() "+this.selected.getNumero());
	System.out.println("this.selected.getNumero() "+this.selected.getNumero());
	
	if(this.idType==1)//ON SUPPRIME UN MATERIEL
	{System.out.println("this.selected.getNumero()1 "+this.selected.getNumero());
		n=Connecteur.Insererdonnees("update materiel set supprime=1 where Idmateriel="+this.selected.getNumero()+"");
	}
	if(this.idType==2)//ON SUPPRIME UN PRODUIT
	{System.out.println("this.selected.getNumero()2 "+this.selected.getNumero());
		n=Connecteur.Insererdonnees("update produits set supprime=1 where Idprod="+this.selected.getNumero()+"");	
	}
	
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";	
	}
	else
		message="SUPPRESSION REUSSIE!";
	
this.selected=null;
	}


//Fin de la partie pour les materiaux et produits



//Categories des personnes
private static List<SelectItem> listcategoriespersonnes=new ArrayList<SelectItem>();

public List<SelectItem> getListcategoriespersonnes() {

	if(listcategoriespersonnes==null)
		listcategoriespersonnes=new ArrayList<SelectItem>();
	else
		listcategoriespersonnes.clear();
	listcategoriespersonnes.add(new SelectItem(0,""));
	listcategoriespersonnes.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes.add(new SelectItem(3,"GESTIONNAIRE"));
	//listcategoriespersonnes.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes.add(new SelectItem(6,"CAISSIER"));
	return listcategoriespersonnes;
	
}
public void setListcategoriespersonnes(
		List<SelectItem> listcategoriespersonnes) {
	ImprilacBean.listcategoriespersonnes = listcategoriespersonnes;
}

//

//DEBUT DE LA FONCTION QUI DETERMINE LES CASES A AFFICHER SELON LA CATEGORIE DE PERSONNES CHOISIE
public void displayFields(ActionEvent e)
{	System.out.println("111111111");
System.out.println(showBureau);
System.out.println(showDiplome);
System.out.println(showTel);
if((idcategoriePersonne==1)||(idcategoriePersonne==2)||(idcategoriePersonne==5))
	{showBureau=true;
	showDiplome=true;
	showTel=false;
	//return;
	}
if((idcategoriePersonne==3)||(idcategoriePersonne==6))
	{showBureau=false;
	showDiplome=true;
	showTel=false;
	//return;
	}
if((idcategoriePersonne==4))
	{showBureau=false;
	showDiplome=false;
	showTel=true;
	//return;
	}
//QUAND AUCUNE CATEGORIE N'EST CHOISIE
if((idcategoriePersonne!=1)&&(idcategoriePersonne!=2)&&(idcategoriePersonne!=3)&&(idcategoriePersonne!=4)&&(idcategoriePersonne!=5)&&(idcategoriePersonne!=6))
{showBureau=false;
showDiplome=false;
showTel=false;
}


if((idType==1))//POUR INSERER UN MATERIEL
{	
showdesignation=true;
showhistorisation=true;
showNumeroLigne=true;
return;
}
if((idType==2))//POUR INSERER UN PRODUIT
{	
showdesignation=true;
showhistorisation=false;
showNumeroLigne=true;
return;
}

buildListDesPers();
this.modifier=false;

}
//FIN DE LA FONCTION QUI AFFICHE LES CASES QU'IL FAUT


public boolean isShowdesignation() {
	return showdesignation;
}
public void setShowdesignation(boolean showdesignation) {
	this.showdesignation = showdesignation;
}
public boolean isShowhistorisation() {
	return showhistorisation;
}
public void setShowhistorisation(boolean showhistorisation) {
	this.showhistorisation = showhistorisation;
}

//DEBUT DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE PERSONNE
public int recuperID()
{
int i=-1;
ResultSet r=null;
r=Connecteur.Extrairedonnees("SELECT Idpersonne FROM personne order by Idpersonne desc limit 1");
if(r!=null)
	try {
		if(r.next()){
			i=r.getInt("Idpersonne");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
return i;
	}
//FIN DE LA FONCTION QUI RECUPERE LE ID DU DERNIER ENREGISTREMENT DE LA TABLE PERSONNE



//DEBUT DE LA FONCTION QUI FAIT L'INSERTION DANS LA TABLE PERSONNE
public void insertPersonne()
{

if(this.modifier!=true)//ON FAIT L'INSERTION PAS LA MODIFICATION
{	
	if(this.nomPersonne==null||this.nomPersonne=="")
		{message="TAPEZ LE NOM S'IL VOUS PLAIT!";
		return;
		}
    this.nomPersonne=this.nomPersonne.toUpperCase();
	if(this.prenomPersonne==null||this.prenomPersonne=="")
    	{message="TAPEZ LE PRENOM S'IL VOUS PLAIT!";
        return;
        }
	if(this.idcategoriePersonne==0)
		{message="CHOISISSER LA CATEGORIE S'IL VOUS PLAIT!";
		return;
		}

	int n=-1,id;


	//DEBUT D'INSERTION DANS LA TABLE PERSONNE
	if(this.dateNaissance!=null)
	{	if(this.dateNaissance.getYear()>=new java.util.Date().getYear()-15)
		{message="CETTE PERSONNE EST TRES JEUNE!!";
		return;
		}
		n=Connecteur.Insererdonnees("insert into personne(Nompersonne,Prenompersonne,Datenaissance)values('"+this.nomPersonne+"','"+this.prenomPersonne+"','"+changeDateFormat(dateNaissance)+"')");
	}
	else
		n=Connecteur.Insererdonnees("insert into personne(Nompersonne,Prenompersonne)values('"+this.nomPersonne+"','"+this.prenomPersonne+"')");
	//FIN D'INSERTION DANS LA TABLE PERSONNE


	if(n==-1)//ECHEC D'INSERTION DANS LA TABLE PERSONNE
	{message="ECHEC D'INSERTION!";
	return;
	}
	

	this.nomPersonne=null;
	this.prenomPersonne=null;
	this.dateNaissance=null;
	
	if(n!=-1)//ON INSERT DANS L'UNE DES AUTRES TABLES HERITANT LA TABLE PERSONNE
	{
		n=-1;
		id=recuperID();
		if(this.idcategoriePersonne==1)//ON INSERT UN CHEF DE PRODUCTION
		{
			n=Connecteur.Insererdonnees("insert into chef_production(Idcheprod,Bureau,Diplome,supprimee) values ("+id+",'"+this.bureau+"','"+this.diplome+"',0)");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
			
		}
		
		if(this.idcategoriePersonne==2)//ON INSERT LE GERANT
		{
			n=Connecteur.Insererdonnees("insert into gerant(Idgerant,Bureau,Diplome,supprimee) values ("+id+",'"+this.bureau+"','"+this.diplome+"',0)");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
		}
		
		if(this.idcategoriePersonne==3)//ON INSERT LE GESTIONNAIRE
		{
			n=Connecteur.Insererdonnees("insert into gestionnaire(Idgestion,Diplome,supprimee) values ("+id+",'"+this.diplome+"',0)");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
	 
		}
		
		if(this.idcategoriePersonne==4)//ON INSERT LE CLIENT
		{
			
			n=Connecteur.Insererdonnees("insert into client(Idclient,tel,supprimee) values ("+id+",'"+this.tel+"',0)");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!"; 
		}
 
		if(this.idcategoriePersonne==5)//ON INSERT LE PRODUCTEUR
		{
			n=Connecteur.Insererdonnees("insert into producteur(Idproduct,Bureau,Diplome,Etatproducteur,supprimee) values ("+id+",'"+this.bureau+"','"+this.diplome+"','DISPONIBLE',0)");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!"; 
		}
		
		if(this.idcategoriePersonne==6)//ON INSERT LE CAISSIER
		{
			n=Connecteur.Insererdonnees("insert into caissier(Idcaissier,Diplome,supprimee) values ("+id+",'"+this.diplome+"',0)");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
		}
this.diplome=null;
this.bureau=null;
this.tel=null;
	}
	
}//FIN DE LA PARTIE D'INSERTION

}
//FIN DE LA FONCTION UTILISEE POUR INSERRER DANS LES TABLES DES PERSONNES


//DEBUT DE LA FONCTION UTILISEE POUR INSERER UN CLIENT
public void insertClient()
{	
	if(this.nomPersonne==null||this.nomPersonne=="")
	{message="TAPEZ LE NOM S'IL VOUS PLAIT!";
	return;
	}
this.nomPersonne=this.nomPersonne.toUpperCase();
if(this.prenomPersonne==null||this.prenomPersonne=="")
	{message="TAPEZ LE PRENOM S'IL VOUS PLAIT!";
    return;
    }
/*if(this.idcategoriePersonne==0)
	{message="CHOISISSER LA CATEGORIE S'IL VOUS PLAIT!";
	return;
	}*/

int n=-1,id;


//DEBUT D'INSERTION DANS LA TABLE PERSONNE
if(this.dateNaissance!=null)
{	if(this.dateNaissance.getYear()>=new java.util.Date().getYear()-15)
	{message="CETTE PERSONNE EST TRES JEUNE!!";
	return;
	}
	n=Connecteur.Insererdonnees("insert into personne(Nompersonne,Prenompersonne,Datenaissance)values('"+this.nomPersonne+"','"+this.prenomPersonne+"','"+changeDateFormat(dateNaissance)+"')");
}
else
	n=Connecteur.Insererdonnees("insert into personne(Nompersonne,Prenompersonne)values('"+this.nomPersonne+"','"+this.prenomPersonne+"')");
//FIN D'INSERTION DANS LA TABLE PERSONNE


if(n==-1)//ECHEC D'INSERTION DANS LA TABLE PERSONNE
{message="ECHEC D'INSERTION!";
return;
}


this.nomPersonne=null;
this.prenomPersonne=null;
this.dateNaissance=null;

if(n!=-1)//ON INSERT DANS L'UNE DES AUTRES TABLES HERITANT LA TABLE PERSONNE
{
	n=-1;
	id=recuperID();

		
		n=Connecteur.Insererdonnees("insert into client(Idclient,tel,supprimee) values ("+id+",'"+this.tel+"',0)");
		if(n!=-1)
			message="INSERTION REUSSIE!";
		else
			message="ECHEC D'INSERTION!"; 
this.bureau=null;
this.tel=null;
}


	}
//FIN DE LA FONCTION UTILISEE POUR INSERER UN CLIENT

public void modifierPersonne()
{int n;
boolean nomP;

Controleur c=new Controleur();
nomP=c.isStringOfLettersAndNumbersOnly(this.selected.nomPersonne);
if(nomP)
{message="LE NOM DOIT ETRE COMPOSE DE LETTRES UNIQUEMENT";
return;
	}


System.out.println(changeDateFormat(this.selected.dateNaissance));
System.out.println(changeDateFormat(this.selected.dateNaissance));

n=-1;
	

 if(this.selected.dateNaissance!=null)
 {if(this.selected.dateNaissance.getYear()>=new java.util.Date().getYear()-15)
	{message="LA DATE DE NAISSANCE N'EST PAS VALIDE!!";
	return;
	}
	n=Connecteur.Insererdonnees("update personne set Nompersonne='"+this.selected.nomPersonne+"',Prenompersonne='"+this.selected.prenomPersonne+"',Datenaissance='"+changeDateFormat(this.selected.dateNaissance)+"' where Idpersonne="+selected.idPersonn);
 } 
else
	n=Connecteur.Insererdonnees("update personne set Nompersonne='"+this.selected.nomPersonne+"',Prenompersonne='"+this.selected.prenomPersonne+"',Datenaissance=NULL where Idpersonne="+selected.idPersonn);
	 
 if(n==-1)
	{
		message="ECHEC DE MISE A JOUR!";
		return;
	}


	if(this.idcategoriePersonne==1)//ON FAIT LA MISE A JOUR DES CHAMPS DU CHEF DE PRODUCTION
	{	
		n=-1;
		n=Connecteur.Insererdonnees("update chef_production set Diplome='"+this.selected.diplome+"',Bureau='"+this.selected.bureau+"' where Idcheprod="+selected.idPersonn);
		
		if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";
			
		}
		else
			message="MISE A JOUR REUSSIE!";
		
	}
	
	if(this.idcategoriePersonne==2)//ON FAIT LA MISE A JOUR DU GERANT
	{
		n=-1;
		n=Connecteur.Insererdonnees("update gerant set Diplome='"+this.selected.diplome+"',Bureau='"+this.selected.bureau+"' where Idgerant="+this.selected.idPersonn);
		
		if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";
			
		}
		else
			message="MISE A JOUR REUSSIE!";
		
		
	}
	if(this.idcategoriePersonne==3)//ON FAIT LA MISE A JOUR DU GESTIONNAIRE
	{
		n=-1;
		n=Connecteur.Insererdonnees("update gestionnaire set Diplome='"+this.selected.diplome+"' where Idgestion="+this.selected.idPersonn);
		
		if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";
			
		}
		else
			message="MISE A JOUR REUSSIE!";
			
	}
	if(this.idcategoriePersonne==4)//ON FAIT LA MISE A JOUR DU CLIENT
	{
		n=-1;
		n=Connecteur.Insererdonnees("update client set tel='"+this.selected.tel+"' where Idclient="+this.selected.idPersonn);
		if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";	
		}
		else
			message="MISE A JOUR REUSSIE!";
	}
	if(this.idcategoriePersonne==5)//ON FAIT LA MISE A JOUR DU PRODUCTEUR
	{
		n=-1;
		n=Connecteur.Insererdonnees("update producteur set Bureau='"+this.selected.bureau+"',Diplome='"+this.selected.diplome+"' where Idproduct="+this.selected.idPersonn);
		if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";	
		}
		else
			message="MISE A JOUR REUSSIE!";
			
	}
	if(this.idcategoriePersonne==6)//ON FAIT LA MISE A JOUR DU CAISSIER
	{
		n=-1;
		n=Connecteur.Insererdonnees("update caissier set Diplome='"+this.selected.diplome+"' where Idcaissier="+this.selected.idPersonn);
		if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";	
		}
		else
			message="MISE A JOUR REUSSIE!";
			
			
	}
	this.selected=null;
}


public void modifierClient()
{

	int n;
	boolean nomP;

	Controleur c=new Controleur();
	nomP=c.isStringOfLettersAndNumbersOnly(this.selected.nomPersonne);
	if(nomP)
	{message="LE NOM DOIT ETRE COMPOSE DE LETTRES UNIQUEMENT";
	return;
		}


	System.out.println(changeDateFormat(this.selected.dateNaissance));
	System.out.println(changeDateFormat(this.selected.dateNaissance));

	n=-1;
		

	 if(this.selected.dateNaissance!=null)
	 {if(this.selected.dateNaissance.getYear()>=new java.util.Date().getYear()-15)
		{message="LA DATE DE NAISSANCE N'EST PAS VALIDE!!";
		return;
		}
		n=Connecteur.Insererdonnees("update personne set Nompersonne='"+this.selected.nomPersonne+"',Prenompersonne='"+this.selected.prenomPersonne+"',Datenaissance='"+changeDateFormat(this.selected.dateNaissance)+"' where Idpersonne="+selected.idPersonn);
	 } 
	else
		n=Connecteur.Insererdonnees("update personne set Nompersonne='"+this.selected.nomPersonne+"',Prenompersonne='"+this.selected.prenomPersonne+"',Datenaissance=NULL where Idpersonne="+selected.idPersonn);
		 
	 if(n==-1)
		{
			message="ECHEC DE MISE A JOUR!";
			return;
		}



			n=-1;
			n=Connecteur.Insererdonnees("update client set tel='"+this.selected.tel+"' where Idclient="+this.selected.idPersonn);
			if(n==-1)
			{
				message="ECHEC DE MISE A JOUR!";	
			}
			else
				message="MISE A JOUR REUSSIE!";
		
		this.selected=null;

	
	}



public void supprimerClient(ActionEvent e)
{int n=-1;

n=-1;
n=Connecteur.Insererdonnees("update client set supprimee=1 where Idclient="+this.selected.idPersonn);
if(n==-1)
{
	message="ECHEC DE SUPPRESSION!";	
}
else
	message="SUPPRESSION REUSSIE!";

this.selected=null;
	}


public void supprimerCompte(ActionEvent e)
{
	int n=-1;

	n=-1;
	n=Connecteur.Insererdonnees("delete from compte where Login='"+this.selected.login+"'");
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";	
	}
	else
		message="SUPPRESSION REUSSIE!";

	this.selected=null;

	}



public void retirerProfilAKelk1(ActionEvent e)
{
int n=-1;
if(this.idcategoriePersonne==1)//ON SUPPRIME UN CHEF DE PRODUCTION
{	
	n=-1;
	n=Connecteur.Insererdonnees("update chef_production set supprimee=1 where Idcheprod="+selected.idPersonn);
	
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";
		
	}
	else
		message="OPERATION DE SUPPRESSION REUSSIE!";
	
}

if(this.idcategoriePersonne==2)//ON SUPPRIME UN GERANT
{
	n=-1;
	n=Connecteur.Insererdonnees("update gerant set supprimee=1 where Idgerant="+this.selected.idPersonn);
	
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";
		
	}
	else
		message="SUPPRESSION REUSSIE!";
	
	
}
if(this.idcategoriePersonne==3)//ON FAIT LA SUPPRESSION DU GESTIONNAIRE
{
	n=-1;
	n=Connecteur.Insererdonnees("update gestionnaire set supprimee=1 where Idgestion="+this.selected.idPersonn);
	
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";
		
	}
	else
		message="SUPPRESSION REUSSIE!";
		
}
if(this.idcategoriePersonne==4)//ON FAIT LA SUPPRESSION DU CLIENT
{
	n=-1;
	n=Connecteur.Insererdonnees("update client set supprimee=1 where Idclient="+this.selected.idPersonn);
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";	
	}
	else
		message="SUPPRESSION REUSSIE!";
}
if(this.idcategoriePersonne==5)//ON FAIT LA SUPPRESSION DU PRODUCTEUR
{
	n=-1;
	n=Connecteur.Insererdonnees("update producteur set supprimee=1 where Idproduct="+this.selected.idPersonn);
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";	
	}
	else
		message="SUPPRESSION REUSSIE!";
		
}
if(this.idcategoriePersonne==6)//ON FAIT LA SUPPRESSION DU CAISSIER
{
	n=-1;
	n=Connecteur.Insererdonnees("update caissier set supprimee=1 where Idcaissier="+this.selected.idPersonn);
	if(n==-1)
	{
		message="ECHEC DE SUPPRESSION!";	
	}
	else
		message="SUPPRESSION REUSSIE!";
		
		
}
this.selected=null;
	
	}





public void modifieCompte()
{int n;
	if((this.selected.nouveauLogin.length()>0)&&(this.selected.nouveauLogin.length()<4))
	{message="SAISISSER UN LOGIN D'AU MOINS 4 CARACTERES S'IL VOUS PLAIT!!";
	return;
	}

	if((this.selected.nouveauPassWord.length()>0)&&(this.selected.nouveauPassWord.length()<4))
	{message="SAISISSER UN MOT DE PASSE D'AU MOINS 4 CARACTERES S'IL VOUS PLAIT!!";
	return;
	}

	if(new Controleur().isStringOfCharAndNumbers(this.selected.nouveauLogin)==true)
	{
		message="LE LOGIN SAISI CONTIENT DES CARACTERES INVALIDES!!";
		return;
	}
	
	boolean miseAJourHappened=false;
	
	
	if(this.selected.nouveauPassWord.length()>0)
	{
	
	n=-1;
	n=Connecteur.Insererdonnees("UPDATE COMPTE SET Password='"+this.selected.nouveauPassWord+"' where Login='"+this.selected.login+"'");
	
	if(n==-1)
		{message="MISE A JOUR ECHOUEE!!";
		return;
		}
	else
	{
		message="MISE A JOUR REUSSIE!!";
		miseAJourHappened=true;
	}
	
	}
	
	if(this.selected.nouveauLogin.length()>0)
	{
	n=-1;
	n=Connecteur.Insererdonnees("UPDATE COMPTE SET Login='"+this.selected.nouveauLogin+"' where Login='"+this.selected.login+"'");
	if(n==-1)
	{
		message="MISE A JOUR ECHOUEE!!";
		return;
	}
	else
	{
		message="MISE A JOUR REUSSIE!!";
		miseAJourHappened=true;
	}
		
	}

if(miseAJourHappened==false)
	message="RIEN N'A ETE MIS A JOUR!!";

}


public String changeDateFormat(Date d)
{if(d==null)
	return "";
 SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
  return f.format(d);
}

//DEBUT DE LA FONCTION QUI FAIT L'INSERTION ET LA MODIFICATION DANS LA TABLE COMPTE

public void insertDansCompte()
{int n=-1;
ResultSet r=null;
ResultSet res=null;
String pkCompte=null;
if(this.modifierCompte==false)//ON FAIT L'INSERTION DANS LA TABLE COMPTE
{
	if(this.login.length()<4)
	{message="SAISISSER UN LOGIN D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
	return;
	}

	if(this.passWord.length()<4)
	{message="SAISISSER UN MOT DE PASSE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
	return;
	}

	if(this.idcategoriePersonne==0)
	{message="CHOISISSER LA CATEGORIE S'IL VOUS PLAIT!!";
	return;
	}
	
	if(this.showmessage1==true)
	{message="CE LOGIN N'EST PAS VALIDE!!";
		return;
	}
	
	r=Connecteur.Extrairedonnees("select * from compte where Login='"+this.login+"'");
	

	
	try {
	if(r.next())
	{message="CHOISISSER UN AUTRE LOGIN S'IL VOUS PLAIT!!";
	System.out.println("eeeeeeeeeeeeeeeeeeee");
	return;
		}
	} catch (SQLException e) {
	// TODO Auto-generated catch block
		System.out.println("ffffffffffffffffffff");
	e.printStackTrace();	
	}

	if(this.idPerso==0)
	{message="CHOISISSER LA PERSONNE A QUI APPARTIENDRA LE COMPTE S'IL VOUS PLAIT!!";
	return;
	}
	
	if(this.idcategoriePersonne==0)//CAS IMPOSSIBLE
	{
		message="SELECTIONNER LA CATEGORIE S'IL VOUS PLAIT!!";
		return;
	}
	
	
	
	System.out.println("ggggggggggggggggggggggg");
	
	n=-1;
	System.out.println("AVANT INSERRER");
	
	if(this.idcategoriePersonne==1)//ON CREE UN COMPTE DU CHEF DE PRODUCTION
	n=Connecteur.Insererdonnees("insert into compte (Idpersonne,Login,Password,Profil) values ("+this.idPerso+",'"+this.login+"','"+this.passWord+"','CHEF DE PRODUCTION')");
	
	if(this.idcategoriePersonne==2)//ON CREE UN COMPTE DU GERANT
	n=Connecteur.Insererdonnees("insert into compte (Idpersonne,Login,Password,Profil) values ("+this.idPerso+",'"+this.login+"','"+this.passWord+"','GERANT')");
	
	if(this.idcategoriePersonne==3)//ON CREE UN COMPTE DU GESTIONNAIRE
		n=Connecteur.Insererdonnees("insert into compte (Idpersonne,Login,Password,Profil) values ("+this.idPerso+",'"+this.login+"','"+this.passWord+"','GESTIONNAIRE')");
		
	if(this.idcategoriePersonne==5)//ON CREE UN COMPTE DU PRODUCTEUR
		n=Connecteur.Insererdonnees("insert into compte (Idpersonne,Login,Password,Profil) values ("+this.idPerso+",'"+this.login+"','"+this.passWord+"','PRODUCTEUR')");
		
	if(this.idcategoriePersonne==6)//ON CREE UN COMPTE DU CAISSIER
		n=Connecteur.Insererdonnees("insert into compte (Idpersonne,Login,Password,Profil) values ("+this.idPerso+",'"+this.login+"','"+this.passWord+"','CAISSIER')");
	
	
	System.out.println("APRES INSERRER");
	if(n!=-1)
	{	System.out.println("hhhhhhhhhhhhhhhhhhhh");
		message="CREATION DU COMPTE REUSSIE";
	}
	else
	{
		message="CREATION DU COMPTE ECHOUEE";
		System.out.println("iiiiiiiiiiiiiiiiiiiiiii");
	}
	this.login=null;
	this.passWord=null;
	
	}//FIN DE LA PARTIE POUR L'INSERTION








	else//DEBUT DE LA PARTIE DE MISE A JOUR
	{/*this.modifierCompte=false;//ON DESACTIVE LE MODE MODIFICATION
	this.modifierComptel1=true;//ON ACTIVE LE MODE SAISI
		if((this.login.length()>0)&&(this.login.length()<4))
		{message="SAISISSER UNE CHAINE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
		return;
		}
		if((this.passWord.length()>0)&&(this.passWord.length()<4))
		{message="SAISISSER UNE CHAINE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
		return;
		}
		if(this.idcategoriePersonne==0)
		{message="CHOISISSER LA CATEGORIE S'IL VOUS PLAIT!!";
		return;
		}
		
		
		
//ON RECCUPERE LE LOGIN SELECTIONNE
		pkCompte=listCopieLogin.get(idCompte).toString();
		

		//FIN DE LA PARTIE DE RECUPERATION DE LA CLE PRIMAIRE DE LA TABLE COMPTE
		System.out.println("pkCompte AVANT LOGIN"+pkCompte);
		System.out.println("pkCompte AVANT LOGIN"+pkCompte);
		
		if(this.passWord.length()>0)//ON MET A JOUR LE MOT DE PASSE
		{n=-1;
		n=Connecteur.Insererdonnees("UPDATE COMPTE SET Password='"+this.passWord+"' where Login='"+pkCompte+"'");
					
		}
		
		if(this.login.length()>0)//ON MET A JOUR LE LOGIN
		{n=-1;
		n=Connecteur.Insererdonnees("UPDATE COMPTE SET Login='"+this.login+"' where Login='"+pkCompte+"'");
		
		}
		

		
		if(n==-1)
		message="ECHEC DE MISE A JOUR!!";
		else
		message="MISE A JOUR REUSSIE!!";
		
	this.modifierCompte=false;
	this.modifierComptel1=true;
	*/}//FIN DE LA PARTIE DE MODIFICATION DES COMPTES
this.login=null;
this.passWord=null;
}
//FIN DE LA FONCTION QUI PERMET L'INSERTION ET LA MODIFICATION DANS LA TABLE COMPTE

public void ecouteModifierCompte()
{this.modifierCompte=true;
this.modifierComptel1=false;
	}

private boolean afficheMsgDeNonReconnaissance=false;

public boolean isAfficheMsgDeNonReconnaissance() {
	return afficheMsgDeNonReconnaissance;
}
public void setAfficheMsgDeNonReconnaissance(
		boolean afficheMsgDeNonReconnaissance) {
	this.afficheMsgDeNonReconnaissance = afficheMsgDeNonReconnaissance;
}
public String identification()
{ 	ResultSet r=null;

	boolean connu=false;

	if(this.login.length()==0)
	{this.showmessage2=true;
	return null;
	}
	if(this.passWord.length()==0)
	{this.showPassWord1=true;
	return null;
	}
	if(this.showmessage1==true)
		return null;
	
FacesContext context=FacesContext.getCurrentInstance();
HttpSession session=(HttpSession) context.getExternalContext().getSession(true);
	
	
   //DEBUT DU TEST QUE LA PERSONNE EST UN CHEF DE PRODUCTION
	r=Connecteur.Extrairedonnees("select * from personne p,chef_production c,compte co where p.Idpersonne=c.Idcheprod and p.Idpersonne=co.Idpersonne and co.Profil='CHEF DE PRODUCTION' and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME CHEF DE PRODUCTION!!!";
			session.setAttribute("idPersonneConnectee", r.getInt("p.Idpersonne"));
			session.setAttribute("legal","legal");
			
			System.out.println("+session.getAttribute('idPersonneConnectee') "+session.getAttribute("idPersonneConnectee"));
			System.out.println("+session.getAttribute('idPersonneConnectee').getClass() "+session.getAttribute("idPersonneConnectee").getClass());
			
			connu=true;
			this.login=null;
			this.passWord=null;
			return "chef";
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST UN CHEF DE PRODUCTION
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN LE GERANT
	r=Connecteur.Extrairedonnees("select * from personne p,gerant g,compte co where p.Idpersonne=g.Idgerant and p.Idpersonne=co.Idpersonne and co.Profil='GERANT' and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME GERANT!!!";
			session.setAttribute("idPersonneConnectee", r.getInt("p.Idpersonne"));
			session.setAttribute("legal","legal");
			this.idPersonneConnecte=r.getInt("p.Idpersonne");
			
			
			System.out.println("+session.getAttribute('idPersonneConnectee') "+session.getAttribute("idPersonneConnectee"));
			System.out.println("+session.getAttribute('idPersonneConnectee').getClass() "+session.getAttribute("idPersonneConnectee").getClass());
			
			
			connu=true;
			this.login=null;
			this.passWord=null;
			return "gera";
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST LE GERANT

	//DEBUT DU TEST QUE LA PERSONNE EST UN LE GESTIONNAIRE
	r=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g,compte co where p.Idpersonne=g.Idgestion and p.Idpersonne=co.Idpersonne and co.Profil='GESTIONNAIRE' and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME GESTIONNAIRE!!!";
			session.setAttribute("idPersonneConnectee", r.getInt("p.Idpersonne"));
			session.setAttribute("legal","legal");
			this.idPersonneConnecte=r.getInt("p.Idpersonne");
			
			
			System.out.println("+session.getAttribute('idPersonneConnectee') "+session.getAttribute("idPersonneConnectee"));
			System.out.println("+session.getAttribute('idPersonneConnectee').getClass() "+session.getAttribute("idPersonneConnectee").getClass());
			
			
			connu=true;
			this.login=null;
			this.passWord=null;
			return "gest";
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST UN LE GESTIONNAIRE
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN PRODUCTEUR
	r=Connecteur.Extrairedonnees("select * from personne p,producteur pro,compte co where p.Idpersonne=pro.Idproduct and p.Idpersonne=co.Idpersonne and co.Profil='PRODUCTEUR' and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME PRODUCTEUR!!!";
			session.setAttribute("idPersonneConnectee", r.getInt("p.Idpersonne"));
			session.setAttribute("legal","legal");
			this.idPersonneConnecte=r.getInt("p.Idpersonne");
		
			System.out.println("+session.getAttribute('idPersonneConnectee') "+session.getAttribute("idPersonneConnectee"));
			System.out.println("+session.getAttribute('idPersonneConnectee').getClass() "+session.getAttribute("idPersonneConnectee").getClass());
			
			connu=true;
			this.login=null;
			this.passWord=null;
			return "prod";
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST UN PRODUCTEUR
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN CAISSIER
	r=Connecteur.Extrairedonnees("select * from personne p,caissier c,compte co where p.Idpersonne=c.Idcaissier and p.Idpersonne=co.Idpersonne and co.Profil='CAISSIER' and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME CAISSIER!!!";
			session.setAttribute("idPersonneConnectee", r.getInt("p.Idpersonne"));
			session.setAttribute("legal","legal");
			this.idPersonneConnecte=r.getInt("p.Idpersonne");
			
			System.out.println("+session.getAttribute('idPersonneConnectee') "+session.getAttribute("idPersonneConnectee"));
			System.out.println("+session.getAttribute('idPersonneConnectee').getClass() "+session.getAttribute("idPersonneConnectee").getClass());
			
			
			connu=true;
			this.login=null;
			this.passWord=null;
			return "caisse";
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	//FIN DU TEST QUE LA PERSONNE EST UN CAISSIER
	
	message="VOUS N'ETES PAS RECONNU PAR LE SYSTEME!!!";
	if(connu==false)
		this.afficheMsgDeNonReconnaissance=true;
	
	return null;
}

public ImprilacBean()
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

public String deconnection()
{	
	FacesContext context=FacesContext.getCurrentInstance();

	HttpSession session=(HttpSession) context.getExternalContext().getSession(true);
	
	session.invalidate();
	
	this.login=null;
	this.passWord=null;
	this.idPersonneConnecte=0;
	
	return "deconnection";
}

//--------------------------------------------------------------------
private boolean showmessage1=false;
private boolean showmessage2=false;
private boolean showPassWord1=false;



public boolean isShowPassWord1() {
	return showPassWord1;
}
public void setShowPassWord1(boolean showPassWord1) {
	this.showPassWord1 = showPassWord1;
}
public boolean getShowmessage1() {
	return showmessage1;
}
public void setShowmessage1(boolean showmessage1) {
	this.showmessage1 = showmessage1;
}
public boolean isShowmessage2() {
	return showmessage2;
}
public void setShowmessage2(boolean showmessage2) {
	this.showmessage2 = showmessage2;
}

public void listernInPutLogin(ActionEvent e)
{Controleur c=new Controleur();
this.showmessage1=c.isStringOfCharAndNumbers(this.login);
this.showmessage2=false;
this.afficheMsgDeNonReconnaissance=false;
	}
public void listernInPutPassWord(ActionEvent e){
	this.showPassWord1=false;
	this.afficheMsgDeNonReconnaissance=false;
}
//---------------------------------------------------------
private boolean showMessageErrorForPersoName=false;
private boolean showMessageErrorForPersoPrenom=false;
private boolean showMessageErrorForDiplo=false;


public boolean isShowMessageErrorForDiplo() {
	return showMessageErrorForDiplo;
}
public void setShowMessageErrorForDiplo(boolean showMessageErrorForDiplo) {
	this.showMessageErrorForDiplo = showMessageErrorForDiplo;
}
public boolean isShowMessageErrorForPersoPrenom() {
	return showMessageErrorForPersoPrenom;
}
public void setShowMessageErrorForPersoPrenom(
		boolean showMessageErrorForPersoPrenom) {
	this.showMessageErrorForPersoPrenom = showMessageErrorForPersoPrenom;
}
public boolean isShowMessageErrorForPersoName() {
	return showMessageErrorForPersoName;
}
public void setShowMessageErrorForPersoName(boolean showMessageErrorForPersoName) {
	this.showMessageErrorForPersoName = showMessageErrorForPersoName;
}
public void listernInputPersoName(ActionEvent e)
{Controleur c=new Controleur();
this.showMessageErrorForPersoName=c.isStringOfLettersAndNumbersOnly(this.nomPersonne);
//this.showDiplome=true;

}

public void listernInputPersoPrenom(ActionEvent e)
{
	}

public void listernInputDiplome(ActionEvent e)
{Controleur c=new Controleur();
this.showMessageErrorForDiplo=c.isStringOfCharAndNumbers(this.diplome);
	}


//------------------------------DEBUT CREATION DES CHEMINS ET DES ETAPES-------------
private int idEtCh;
private static List<SelectItem> listEtCh;
private String designa;
private String types;



public String getTypes() {
	return types;
}
public void setTypes(String types) {
	this.types = types;
}
public int getIdEtCh() {
	return idEtCh;
}
public void setIdEtCh(int idEtCh) {
	this.idEtCh = idEtCh;
}
public List<SelectItem> getListEtCh() {
	if(listEtCh==null)
		listEtCh=new ArrayList<SelectItem>();
	else 
		listEtCh.clear();
	listEtCh.add(new SelectItem(0,""));
	listEtCh.add(new SelectItem(1,"CHEMIN"));
	listEtCh.add(new SelectItem(2,"ETAPE"));
	//listEtCh.add(new SelectItem(3,"CHARGE"));
	return listEtCh;
}
public void setListEtCh(List<SelectItem> listEtCh) {
	ImprilacBean.listEtCh = listEtCh;
}
public String getDesigna() {
	return designa;
}
public void setDesigna(String designa) {
	this.designa = designa;
}

//====================
private boolean showCout=false;
private float cout;

public boolean isShowCout() {
	return showCout;
}
public void setShowCout(boolean showCout) {
	this.showCout = showCout;
}
public float getCout() {
	return cout;
}
public void setCout(float cout) {
	this.cout = cout;
}
//====================
public void enregistrerChemEtap()
{	int n=-1;
	 if(this.idEtCh==0)
	   {
	    message="CHOISIR LE TYPE S'IL VOUS PLAIT!";
	    return;
	   } 
	 ResultSet res=null;
	 
	if(this.idEtCh==1)//ON INSERT UN CHEMIN
	  {  
		if(this.designa==null||this.designa=="")
	      { 
		   message="TAPEZ LE NOM DU CHEMIN S'IL VOUS PLAIT!";
		   return; 
	      } 
/*		if(this.cout==0)
			{message="INDIQUER LE COUT DE CHEMIN S'IL VOUS PLAIT!!";
			return;
			}*/
	      //this.designa=this.designa.toUpperCase();
	      res=Connecteur.Extrairedonnees("select * from chemin where Designation='"+this.designa+"' and supprime=0");
	      try {
			if(res.next())
			  {message="CE CHEMIN EXISTE DEJA!!";
				return;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		res=null;
		n=-1;
	     res=Connecteur.Extrairedonnees("select * from chemin where Designation='"+this.designa+"' and supprime=1");
	      try {
			if(res.next())
			  {
			n=Connecteur.Insererdonnees("update chemin set cout="+this.cout+", supprime=0 where Idchemin="+res.getInt("Idchemin")+"");	
			if(n!=-1)
			message="OPERATION REUSSIE!!";
			else
			message="OPERATION ECHOUEE!!";
				return;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	      
		   n=Connecteur.Insererdonnees("insert into chemin(Designation,cout,supprime) values ('"+this.designa+"',"+this.cout+",0)");
		   if(n!=-1)
			  message="INSERTION REUSSIE!";
		 else
			 message="ECHEC D'INSERTION!";
		 this.cout=0;
	  }
	
	
	if(this.idEtCh==2)//ON INSERT UNE ETAPE
	  {n=-1;
	  //ResultSet res=null;
		if(this.designa==null||this.designa=="")
	    {  
		message="TAPEZ LE NOM DE L'ETAPE S'IL VOUS PLAIT!";
		return;
	    }
		//this.designa=this.designa.toUpperCase();
		res=Connecteur.Extrairedonnees("select * from etapes where Designation='"+this.designa+"' and supprime=0");
		try {
			if(res.next())
			{message="CETTE ETAPE EST DEJA ENREGISTRE!!";
			return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		res=null;
		n=-1;
	     res=Connecteur.Extrairedonnees("select * from etapes where Designation='"+this.designa+"' and supprime=1");
	      try {
			if(res.next())
			  {
			n=Connecteur.Insererdonnees("update etapes set supprime=0 where Idetape="+res.getInt("Idetape")+"");	
			if(n!=-1)
			message="OPERATION REUSSIE!!";
			else
			message="OPERATION ECHOUEE!!";
				return;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		n=Connecteur.Insererdonnees("insert into etapes (Designation,supprime) values ('"+this.designa+"',0)");
		 if(n!=-1)
			  message="INSERTION REUSSIE!";
		 else
			 message="ECHEC D'INSERTION!";

	  }
	
/*	if(this.idEtCh==3)//ON INSERT UNE CHARGE
	  {  
		if(this.designa==null||this.designa=="")
	      { 
		   message="TAPEZ LE NOM DU CHEMIN S'IL VOUS PLAIT!";
		   return; 
	      } 
		if(this.prixUni==0)
	      { 
		   message="TAPEZ LE PRIX UNITAIRE S'IL VOUS PLAIT!";
		   return; 
	      } 
	      this.designa=this.designa.toUpperCase();
	      res=Connecteur.Extrairedonnees("select * from charges where Designation='"+this.designa+"'");
	      try {
			if(res.next())
			  {message="CETTE CHARGE EXISTE DEJA!!";
				return; 
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		   n=Connecteur.Insererdonnees("insert into charges(Designation,PU) values ('"+this.designa+"',"+this.prixUni+")");
		   if(n!=-1)
			  message="INSERTION REUSSIE!";
		 else
			 message="ECHEC D'INSERTION!";

	  }*/
	
	
	  this.designa=null;
	  //this.idEtCh=0;
	  this.prixUni=0;
	}



//DEBUT DE LA FONCTION QUI PERMET D'INSERRER UNE CHARGE

private int idTypeCharge=0;


public int getIdTypeCharge() {
	return idTypeCharge;
}
public void setIdTypeCharge(int idTypeCharge) {
	this.idTypeCharge = idTypeCharge;
}


private List<SelectItem> listDesTypeDeCharges;


public List<SelectItem> getListDesTypeDeCharges() 
{

	if(listDesTypeDeCharges==null)
		listDesTypeDeCharges=new ArrayList<SelectItem>();
	else 
		listDesTypeDeCharges.clear();
	listDesTypeDeCharges.add(new SelectItem(0,""));
	listDesTypeDeCharges.add(new SelectItem(1,"SIMPLE CHARGE"));
	listDesTypeDeCharges.add(new SelectItem(2,"CHARGE MATERIEL"));
	
	return listDesTypeDeCharges;
}
public void setListDesTypeDeCharges(List<SelectItem> listDesTypeDeCharges) {
	this.listDesTypeDeCharges = listDesTypeDeCharges;
}

private String nomCharg=null;
private int idCharg=0;

public String getNomCharg() {
	return nomCharg;
}
public void setNomCharg(String nomCharg) {
	this.nomCharg = nomCharg;
}
private boolean showZoneSaisie=false;
private boolean showSelect=false;
private boolean showLabelDesi=false;


public boolean isShowLabelDesi() {
	return showLabelDesi;
}
public void setShowLabelDesi(boolean showLabelDesi) {
	this.showLabelDesi = showLabelDesi;
}
public boolean isShowZoneSaisie() {
	return showZoneSaisie;
}
public void setShowZoneSaisie(boolean showZoneSaisie) {
	this.showZoneSaisie = showZoneSaisie;
}
public boolean isShowSelect() {
	return showSelect;
}
public void setShowSelect(boolean showSelect) {
	this.showSelect = showSelect;
}
public int getIdCharg() {
	return idCharg;
}
public void setIdCharg(int idCharg) {
	this.idCharg = idCharg;
}
public void ecouteChangeTypeCharge(ActionEvent e)
{System.out.println("idTypeCharge "+idTypeCharge);

if(this.idTypeCharge==0)
{this.showZoneSaisie=false;
this.showSelect=false;
this.showLabelDesi=false;
	}

if(this.idTypeCharge==1)
{this.showZoneSaisie=true;
this.showSelect=false;
this.showLabelDesi=true;
	}

if(this.idTypeCharge==2)
{this.showZoneSaisie=false;
this.showSelect=true;
this.showLabelDesi=true;
	}

	}

private float prixUnit=0;


public float getPrixUnit() {
	return prixUnit;
}
public void setPrixUnit(float prixUnit) {
	this.prixUnit = prixUnit;
}


private List<SelectItem> listDesMateriaux;
public List<SelectItem> getListDesMateriaux() {
	
	ResultSet res=null;
	
	if(listDesMateriaux==null)
		listDesMateriaux=new ArrayList<SelectItem>();
	else
		listDesMateriaux.clear();
	
	listDesMateriaux.add(new SelectItem(0,""));
	res=Connecteur.Extrairedonnees("select * from materiel where supprime=0");

	try {
		while(res.next())
		{listDesMateriaux.add(new SelectItem(res.getInt("Idmateriel"),res.getString("Designation")));

		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesMateriaux;
}
public void setListDesMateriaux(List<SelectItem> listDesMateriaux) {
	this.listDesMateriaux = listDesMateriaux;
}


public void saveCharges()
{
if(this.idTypeCharge==0)
{message="SELECTIONNER LE TYPE DE LA CHARGE S'IL VOUS PLAIT!!";
return;
	}

if((this.showZoneSaisie)&&((this.nomCharg==null)||(this.nomCharg.length()<1)))
{message="SAISISSER LE NOM DE LA CHARGE S'IL VOUS PLAIT!!";
return;
	}

if((this.showSelect)&&(this.idCharg==0))
{message="SELECTIONNER UNE CHARGE S'IL VOUS PLAIT!!";
return;
	}
	
if(this.prixUnit==0)
{ 
 message="TAPEZ LE PRIX UNITAIRE S'IL VOUS PLAIT!";
 return; 
}  





ResultSet res=null;

/*if(this.nomCharg!=null)
{
this.nomCharg=this.nomCharg.toUpperCase();
res=Connecteur.Extrairedonnees("select * from charges where Designation='"+this.nomCharg+"' and supprimee=0");
try {
	if(res.next())
	  {message="CETTE CHARGE EXISTE DEJA 1!!";
		return; 
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}



res=null;
res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and m.Designation='"+this.nomCharg+"' and c.supprimee=0");
try {
	if(res.next())
	  {message="CETTE CHARGE EXISTE DEJA 2!!";
		return; 
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


}*/


/*res=null;
res=Connecteur.Extrairedonnees("select * from charges where Designation='"+this.nomCharg+"' and supprimee=1");
try {
	if(res.next())
	  {int n=-1;
	  n=Connecteur.Insererdonnees("update charges set supprimee=0 where Idcharge="+res.getInt("Idcharge")+"");
	  if(n!=-1)
		  message="OPERATION REUSSIE!!";
	  else
		  message="OPERATION ECHOUEE!!";
	
		return; 
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}*/



res=null;
//res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and m.Designation='"+this.nomCharg+"' and c.supprimee=1");

/*if(this.idCharg!=0)
{
res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and c.Idmat="+this.idCharg+" and c.supprimee=1");

try {
	if(res.next())
	  {
		int n=-1;
		  n=Connecteur.Insererdonnees("update charges set supprimee=0 where Idcharge="+res.getInt("Idcharge")+"");
		  if(n!=-1)
			  message="OPERATION REUSSIE!!";
		  else
			  message="OPERATION ECHOUEE!!";
		
		return; 
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}*/








if(this.showZoneSaisie)//QUAND LA CHARGE EST DE TYPE 'SIMPLE CHARGE'
{this.nomCharg=this.nomCharg.toUpperCase();

System.out.println("1");

	//if(this.nomCharg!=null)
//	{
	
	res=Connecteur.Extrairedonnees("select * from charges where Designation='"+this.nomCharg+"' and supprimee=0");
	try {
		if(res.next())
		  {message="CETTE CHARGE EXISTE DEJA 1!!";
		  
		  this.idTypeCharge=0;
		  this.nomCharg=null;
		  this.idCharg=0;
		  this.prixUnit=0;
		  this.showLabelDesi=false;
		  this.showSelect=false;
		  this.showZoneSaisie=false;
		  
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	System.out.println("2");

	res=null;
	res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and m.Designation='"+this.nomCharg+"' and c.supprimee=0");
	try {
		if(res.next())
		  {message="CETTE CHARGE EXISTE DEJA 2!!";
		  this.idTypeCharge=0;
		  this.nomCharg=null;
		  this.idCharg=0;
		  this.prixUnit=0;
		  this.showLabelDesi=false;
		  this.showSelect=false;
		  this.showZoneSaisie=false;
		  
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	System.out.println("3");
	//}	
	res=Connecteur.Extrairedonnees("select * from materiel where Designation='"+this.nomCharg+"'");
	try {
	if(res.next())
	  {message="SELECTIONNER L'AUTRE TYPE DE CHARGES!!";
	  this.idTypeCharge=0;
	  this.nomCharg=null;
	  this.idCharg=0;
	  this.prixUnit=0;
	  this.showLabelDesi=false;
	  this.showSelect=false;
	  this.showZoneSaisie=false;
	  
		return; 
	  }
	} catch (SQLException e) {
	// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	System.out.println("4");
	
	res=null;
	//ResultSet res1=null;
	
	res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and m.Designation='"+this.nomCharg+"' and supprimee=1");
	//res1=Connecteur.Extrairedonnees("select * from c where Designation='"+this.nomCharg+"' and supprimee=1");
	
	try {
		if(res.next())
		  {int n=-1;
		  n=Connecteur.Insererdonnees("update charges set supprimee=0 where Idcharge="+res.getInt("Idcharge")+"");
		  if(n!=-1)
			  message="OPERATION REUSSIE!!";
		  else
			  message="OPERATION ECHOUEE!!";
		  
		  this.idTypeCharge=0;
		  this.nomCharg=null;
		  this.idCharg=0;
		  this.prixUnit=0;
		  this.showLabelDesi=false;
		  this.showSelect=false;
		  this.showZoneSaisie=false;
		
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	System.out.println("5");
	
	res=null;
	//ResultSet res1=null;
	
	res=Connecteur.Extrairedonnees("select * from charges where Designation='"+this.nomCharg+"' and supprimee=1");
	//res1=Connecteur.Extrairedonnees("select * from c where Designation='"+this.nomCharg+"' and supprimee=1");
	
	try {
		if(res.next())
		  {int n=-1;
		  n=Connecteur.Insererdonnees("update charges set supprimee=0 where Idcharge="+res.getInt("Idcharge")+"");
		  if(n!=-1)
			  message="OPERATION REUSSIE!!";
		  else
			  message="OPERATION ECHOUEE!!";
		  
		  this.idTypeCharge=0;
		  this.nomCharg=null;
		  this.idCharg=0;
		  this.prixUnit=0;
		  this.showLabelDesi=false;
		  this.showSelect=false;
		  this.showZoneSaisie=false;
		
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	System.out.println("6");
int n=-1;
n=Connecteur.Insererdonnees("insert into charges(Designation,PU,supprimee) values ('"+this.nomCharg+"',"+this.prixUnit+",0)");
if(n!=-1)
	  message="INSERTION REUSSIE!";
else
	 message="ECHEC D'INSERTION!";
	
this.idTypeCharge=0;
this.nomCharg=null;
this.idCharg=0;
this.prixUnit=0;
this.showLabelDesi=false;
this.showSelect=false;
this.showZoneSaisie=false;

}
   








if(this.showSelect)//QUAND LA CHARGE EST DE TYPE 'CHARGE MATERIEL'
{
	
	if(this.idCharg!=0)
	{
	res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and c.Idmat="+this.idCharg+" and c.supprimee=1");

	try {
		if(res.next())
		  {
			int n=-1;
			  n=Connecteur.Insererdonnees("update charges set supprimee=0 where Idcharge="+res.getInt("Idcharge")+"");
			  if(n!=-1)
				  message="OPERATION REUSSIE!!";
			  else
				  message="OPERATION ECHOUEE!!";
			  
			  this.idTypeCharge=0;
			  this.nomCharg=null;
			  this.idCharg=0;
			  this.prixUnit=0;
			  this.showLabelDesi=false;
			  this.showSelect=false;
			  this.showZoneSaisie=false;
			
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	res=null;
	if(this.idCharg!=0)
	{
	res=Connecteur.Extrairedonnees("select * from charges c,materiel m where c.Idmat=m.Idmateriel and c.Idmat="+this.idCharg+" and c.supprimee=0");

	try {
		if(res.next())
		  {
/*			int n=-1;
			  n=Connecteur.Insererdonnees("update charges set supprimee=0 where Idcharge="+res.getInt("Idcharge")+"");
			  if(n!=-1)
				  message="OPERATION REUSSIE!!";
			  else
				  message="OPERATION ECHOUEE!!";*/
			message="CE MATERIEL EXISTE DEJA!!";
			  this.idTypeCharge=0;
			  this.nomCharg=null;
			  this.idCharg=0;
			  this.prixUnit=0;
			  this.showLabelDesi=false;
			  this.showSelect=false;
			  this.showZoneSaisie=false;
			
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	}
	
	
	
	res=Connecteur.Extrairedonnees("select * from charges where Idmat='"+this.idCharg+"'");
	try {
		if(res.next())
		  {message="CETTE CHARGE EST DEJA ENREGISTREE!!";
		  
		  this.idTypeCharge=0;
		  this.nomCharg=null;
		  this.idCharg=0;
		  this.prixUnit=0;
		  this.showLabelDesi=false;
		  this.showSelect=false;
		  this.showZoneSaisie=false;
		  
			return; 
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	int n=-1;
	n=Connecteur.Insererdonnees("insert into charges(Idmat,PU,supprimee) values ('"+this.idCharg+"',"+this.prixUnit+",0)");
	if(n!=-1)
		  message="INSERTION REUSSIE!";
	else
		 message="ECHEC D'INSERTION!";
	
}
      
this.idTypeCharge=0;
this.nomCharg=null;
this.idCharg=0;
this.prixUnit=0;
this.showLabelDesi=false;
this.showSelect=false;
this.showZoneSaisie=false;
}
	
	}


private List<Charge> listDesCharges;

public List<Charge> getListDesCharges() {
	
	if(listDesCharges==null)
		listDesCharges=new ArrayList<Charge>();
	else
		listDesCharges.clear();
	
	ResultSet res1=null,res2=null;

	res1=Connecteur.Extrairedonnees("select * from charges where supprimee=0");
	int numero=1;
	try {
		while(res1.next())
		{Charge c=new Charge();
			
		c.setNum(numero);
		c.setIdCharge(res1.getInt("Idcharge"));
		c.setPrixUni(res1.getFloat("PU"));
		
		if(res1.getString("Designation")!=null)//CHARGE DE TYPE 'SIMPLE CHARGE'	
			c.setDesignation(res1.getString("Designation"));
				
			
		if(res1.getInt("Idmat")!=0)//CHARGE DE TYPE 'CHARGE MATERIEL'
		{ int idMat=res1.getInt("Idmat");
			res2=Connecteur.Extrairedonnees("select * from materiel where Idmateriel="+idMat+" and supprime=0");	
			if(res2.next())
				c.setDesignation(res2.getString("Designation"));
			else
				break;
		}
			
			numero++;
			listDesCharges.add(c);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return listDesCharges;
}
public void setListDesCharges(List<Charge> listDesCharges) {
	this.listDesCharges = listDesCharges;
}


private Charge selec;
public Charge getSelec() {
	return selec;
}
public void setSelec(Charge selec) {
	this.selec = selec;
}


public void modifierCoutCharge()
{
if(this.selec==null)//CAS IMPOSSIBLE
{message="CAS IMPOSSIBLE!!";
return;
	}

if(this.selec.getPrixUni()!=0)
{
/*boolean existIntru=false;
String chaineSaisie=new String();

int IntOfValSaisie;

IntOfValSaisie=(int)(this.selec.getPrixUni());
chaineSaisie=chaineSaisie.valueOf(IntOfValSaisie);*/

/*Controleur contro=new Controleur();
existIntru=contro.isNumber(chaineSaisie);

System.out.println("IntOfValSaisie         "+IntOfValSaisie);
System.out.println("chaineSaisie         "+chaineSaisie);

if(existIntru)
{message="LE PRIX UNITAIRE NE DOIT ETRE COMPOSE QUE PAR DES NOMBRES!!";
return;
	}*/

ResultSet r=null;
r=Connecteur.Extrairedonnees("select * from charges where Idcharge="+this.selec.getIdCharge()+"");
try {
	if(r.next())
	{
	if(this.selec.getPrixUni()==r.getFloat("PU"))
	{
		message="VOUS DEVEZ SAISIR UN NOMBRE DIFFERENT DE L'EXISTANT!!";
		return;
	}
		}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}


int n=-1;
n=Connecteur.Insererdonnees("update charges set PU="+this.selec.getPrixUni()+" where Idcharge="+this.selec.getIdCharge()+"");


if(n!=-1)
{message="OPERATION DE MISE A JOUR REUSSIE!!";
return;
	}
else
message="OPERATION DE MISE A JOUR ECHOUEE!!";
}

	}

private Charge c1=null;
public Charge getC1() {
	return c1;
}
public void setC1(Charge c1) {
	this.c1 = c1;
}
public void supprimerCharge(ActionEvent e)
{
if(c1==null)//CAS IMPOSSIBLE
{message="CAS IMPOSSIBLE!!";
return;
	}

int n=-1;
n=Connecteur.Insererdonnees("update charges set supprimee=1 where Idcharge="+this.c1.getIdCharge()+"");

if(n!=-1)
message="SUPPRESSION REUSSIE!!";
else
message="SUPPRESSION ECHOUEE!!";
}


//FIN DE LA PARTIE POUR LES CHARGES






private List<CheminOuEtape> listCheminEtape;
public List<CheminOuEtape> getListCheminEtape() {
	ResultSet res=null;
	int num=1;
	if(listCheminEtape==null)
		listCheminEtape=new ArrayList<CheminOuEtape>();
	else 
		listCheminEtape.clear();
	
	if(this.idEtCh==1)
	{
		res=Connecteur.Extrairedonnees("select * from chemin where supprime=0");
		this.types="CHEMINS";
	}
	if(this.idEtCh==2)
	{
		res=Connecteur.Extrairedonnees("select * from etapes where supprime=0");
		this.types="ETAPES";
	}
	if(this.idEtCh==3)
	{
		res=Connecteur.Extrairedonnees("select * from charges");
		this.types="CHARGES";
	}
	if(this.idEtCh==1)
	{try {
		while(res.next())
		{CheminOuEtape p=new CheminOuEtape();
		p.setIdChemOuEtap(num);
		//if(this.idEtCh==1)//ON CONSTRUIT LA LISTE DES CHEMINS
		p.setId(res.getInt("Idchemin"));
		//else//ON CONSTRUIT LA LISTE DES ETAPES
		//p.setId(res.getInt("Idetape"));
		p.setDesignation(res.getString("Designation"));
		p.setCout(res.getFloat("cout"));
		this.listCheminEtape.add(p);
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	if(this.idEtCh==2)
	{try {
		while(res.next())
		{CheminOuEtape p=new CheminOuEtape();
		p.setIdChemOuEtap(num);
		if(this.idEtCh==1)//ON CONSTRUIT LA LISTE DES CHEMINS
		p.setId(res.getInt("Idchemin"));
		else//ON CONSTRUIT LA LISTE DES ETAPES
		p.setId(res.getInt("Idetape"));
		p.setDesignation(res.getString("Designation"));
		this.listCheminEtape.add(p);
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	if(this.idEtCh==3)
	{try {
		while(res.next())
		{CheminOuEtape p=new CheminOuEtape();
		p.setIdChemOuEtap(num);
		p.setId(res.getInt("Idcharge"));
		p.setDesignation(res.getString("Designation"));
		p.setPrixUni(res.getFloat("PU"));
		this.listCheminEtape.add(p);
		num++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	return listCheminEtape;
}

public void setListCheminEtape(List<CheminOuEtape> listCheminEtape) {
	this.listCheminEtape = listCheminEtape;
}


public void supprimerCheEta(ActionEvent e)
{int n=-1;
	if(this.idEtCh==1)//ON SUPPRIME UN CHEMIN
	{
	n=Connecteur.Insererdonnees("update chemin set supprime=1 where Idchemin="+this.sele.getId()+"");	
		
	}
	if(this.idEtCh==2)//ON SUPPRIME UNE ETAPE
	{
	n=Connecteur.Insererdonnees("update etapes set supprime=1 where Idetape="+this.sele.getId()+"");		
		
	}
if(n!=-1)
	message="SUPRESSION REUSSIE!!";
else
	message="SUPRESSION ECHOUEE!!";
this.sele=null;	
	}


private boolean showPrixU=false;
public boolean isShowPrixU() {
	return showPrixU;
}
public void setShowPrixU(boolean showPrixU) {
	this.showPrixU = showPrixU;
}
private float prixUni=0;
public float getPrixUni() {
	return prixUni;
}
public void setPrixUni(float prixUni) {
	this.prixUni = prixUni;
}
public void ecouteChangeType(ActionEvent e)
{if(this.idEtCh==3)
	this.showPrixU=true;
else
	this.showPrixU=false;

if(this.idEtCh==1)
	this.showCout=true;
else
	this.showCout=false;
	}

private CheminOuEtape sele=null;
public CheminOuEtape getSele() {
	return sele;
}
public void setSele(CheminOuEtape sele) {
	this.sele = sele;
}

public void modifierCheEta()
{int n=-1;
 if(this.idEtCh==1)//ON MODIFIE UN CHEMIN
	{
	 if(((this.sele.getDesignation()==null)||(this.sele.getDesignation()==""))&&(this.sele.getCout()==0))
		 {message="VOUS N'AVEZ RIEN MODIFIER!!";
		 return;
		 
		 }
	 if((this.sele.getDesignation().length()>0))
	 {
		 n=-1;
		 String chaine=this.sele.getDesignation().toUpperCase();
		 
		 ResultSet res=null;
		 res=Connecteur.Extrairedonnees("select * from chemin where Designation='"+chaine+"'");
		 try {
			if(res.next())
			 {
				 message="CE CHEMIN EST DEJA ENREGISTRE!!";
				 return;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 this.sele.setDesignation(this.sele.getDesignation().toUpperCase());
		 n=Connecteur.Insererdonnees("update chemin set Designation='"+this.sele.getDesignation()+"' where Idchemin="+this.sele.getId()+"");
		 if(n!=-1)
			 message="MISE A JOUR REUSSIE";
		 else
			 message="ECHEC DE MISE A JOUR!!";
	 }
	 if(this.sele.getCout()!=0)
	 {
		 n=-1;
		 n=Connecteur.Insererdonnees("update chemin set cout='"+this.sele.getCout()+"' where Idchemin="+this.sele.getId()+"");
		 if(n!=-1)
			 message="MISE A JOUR REUSSIE!!";
		 else
			 message="ECHEC DE MISE A JOUR!!";
	 }
	 
	}
 if(this.idEtCh==2)//ON MODIFIE UNE ETAPE
 	{
	 if((this.sele.getDesignation()==null)||(this.sele.getDesignation()==""))
		 {message="VOUS N'AVEZ RIEN MODIFIER!!";
		 return;
		 }
	 
	 String chaineEt=null;
	 chaineEt=this.sele.getDesignation().toUpperCase();
	 ResultSet r=null;
	 r=Connecteur.Extrairedonnees("select * from etapes where Designation='"+chaineEt+"'");
	 try {
		if(r.next())
		 {message="CETTE ETAPE EST DEJA ENREGISTREE!!";
			return;
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	// else
	 //{
		 n=-1;
		 this.sele.setDesignation(this.sele.getDesignation().toUpperCase());
		 n=Connecteur.Insererdonnees("update etapes set Designation='"+this.sele.getDesignation()+"' where Idetape="+this.sele.getId()+"");
		 message="MISE A JOUR REUSSIE";
	// }
 	}
 
 
 
 
 if(this.idEtCh==3)//ON MODIFIE UNE CHARGE
	{boolean mod=false;

	 if(((this.sele.getDesignation()==null)||(this.sele.getDesignation()==""))&&(this.sele.getPrixUni()==0))
	 {
		 message="VOUS N'AVEZ RIEN MODIFIER!!";
		 return;
	 }
  System.out.println("this.sele.getDesignation() "+this.sele.getDesignation());
	 if((this.sele.getDesignation().length()>0))//ON MODIFIE LA DESIGNATION
	 {
		 n=-1;
		 this.sele.setDesignation(this.sele.getDesignation().toUpperCase());
		 n=Connecteur.Insererdonnees("update charges set Designation='"+this.sele.getDesignation()+"' where Idcharge="+this.sele.getId()+"");
		 mod=true;
		 if(n!=-1)
		 message="MISE A JOUR REUSSIE";
		 else
		 message="MISE A JOUR ECHOUEE";	 
	 }
	 
	 
	 if(this.sele.getPrixUni()!=0)//ON MODIFIE LE PRIX
	 { n=-1;
	 n=Connecteur.Insererdonnees("update charges set PU='"+this.sele.getPrixUni()+"' where Idcharge="+this.sele.getId()+"");
	 mod=true;
	 if(n!=-1)
	 message="MISE A JOUR REUSSIE";
	 else
	 message="MISE A JOUR ECHOUEE";	 
	 }
		 
	}
}

//------------------------------FIN CREATION DES CHEMINS ET DES ETAPES---------------


//DEBUT D'AJOUT DES CATEGORIES A UNE PERSONNE



private List<SelectItem> listcategoriespersonnes0=new ArrayList<SelectItem>();
public List<SelectItem> getListcategoriespersonnes0() {
	
	
	if(listcategoriespersonnes0==null)
		listcategoriespersonnes0=new ArrayList<SelectItem>();
	else
		listcategoriespersonnes0.clear();
	
	
	listcategoriespersonnes0.add(new SelectItem(0,""));
	listcategoriespersonnes0.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes0.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes0.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes0.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes0.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes0.add(new SelectItem(6,"CAISSIER"));
	
	
	
	return listcategoriespersonnes0;
}

public void setListcategoriespersonnes0(
		List<SelectItem> listcategoriespersonnes0) {
	this.listcategoriespersonnes0 = listcategoriespersonnes0;
}









private List<SelectItem> listcategoriespersonnes1=new ArrayList<SelectItem>();
public List<SelectItem> getListcategoriespersonnes1() {
	
	
	if(listcategoriespersonnes1==null)
		listcategoriespersonnes1=new ArrayList<SelectItem>();
	else
		listcategoriespersonnes1.clear();
	
	
	listcategoriespersonnes1.add(new SelectItem(0,""));
	listcategoriespersonnes1.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes1.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes1.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes1.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes1.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes1.add(new SelectItem(6,"CAISSIER"));
	
	
	
	return listcategoriespersonnes1;
}

public void setListcategoriespersonnes1(
		List<SelectItem> listcategoriespersonnes1) {
	this.listcategoriespersonnes1 = listcategoriespersonnes1;
}

private int idCat1;
private int idP;
private int idCat2;
public int getIdCat1() {
	return idCat1;
}
public void setIdCat1(int idCat1) {
	this.idCat1 = idCat1;
}
public int getIdP() {
	return idP;
}
public void setIdP(int idP) {
	this.idP = idP;
}
public int getIdCat2() {
	return idCat2;
}
public void setIdCat2(int idCat2) {
	this.idCat2 = idCat2;
}

private List<SelectItem> listDesPersonnes1;
public List<SelectItem> getListDesPersonnes1() {
	
	if(this.listDesPersonnes1==null)
		this.listDesPersonnes1=new ArrayList<SelectItem>();
	else
		this.listDesPersonnes1.clear();
	
	ResultSet res=null;
	
	
	if(this.idCat1==0)
		res=Connecteur.Extrairedonnees("select * from personne order by Nompersonne");
	if(this.idCat1==1)
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod and supprimee=0 order by Nompersonne");
	if(this.idCat1==2)
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g where p.Idpersonne=g.Idgerant and supprimee=0 order by Nompersonne");
	if(this.idCat1==3)
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g where p.Idpersonne=g.Idgestion and supprimee=0 order by Nompersonne");
	if(this.idCat1==4)
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient and supprimee=0 order by Nompersonne");
	if(this.idCat1==5)
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pr where p.Idpersonne=pr.Idproduct and supprimee=0 order by Nompersonne");
	if(this.idCat1==6)
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c where p.Idpersonne=c.Idcaissier and supprimee=0 order by Nompersonne");
	
	
	this.listDesPersonnes1.add(new SelectItem(0,""));
	try {
		while(res.next())
		{
	this.listDesPersonnes1.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")+"  "+res.getInt("Idpersonne")));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return listDesPersonnes1;
}
public void setListDesPersonnes1(List<SelectItem> listDesPersonnes1) {
	this.listDesPersonnes1 = listDesPersonnes1;
}


private List<SelectItem> listcategoriespersonnes2=new ArrayList<SelectItem>();
public List<SelectItem> getListcategoriespersonnes2() {
	
	if(listcategoriespersonnes2==null)
		listcategoriespersonnes2=new ArrayList<SelectItem>();
	else
		listcategoriespersonnes2.clear();
	
	if(this.idCat1==0)
	{
		
		listcategoriespersonnes2.add(new SelectItem(0,""));
		listcategoriespersonnes2.add(new SelectItem(1,"CHEF DE PRODUCTION"));
		listcategoriespersonnes2.add(new SelectItem(2,"GERANT"));
		listcategoriespersonnes2.add(new SelectItem(3,"GESTIONNAIRE"));
		listcategoriespersonnes2.add(new SelectItem(4,"CLIENT"));
		listcategoriespersonnes2.add(new SelectItem(5,"PRODUCTEUR"));
		listcategoriespersonnes2.add(new SelectItem(6,"CAISSIER"));
		
	}
	if(this.idCat1==1)
	{listcategoriespersonnes2.add(new SelectItem(0,""));
	listcategoriespersonnes2.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes2.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes2.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes2.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes2.add(new SelectItem(6,"CAISSIER"));
	}
	
	if(this.idCat1==2)
	{listcategoriespersonnes2.add(new SelectItem(0,""));
	listcategoriespersonnes2.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes2.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes2.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes2.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes2.add(new SelectItem(6,"CAISSIER"));
	}
	
	
	if(this.idCat1==3)
	{
	listcategoriespersonnes2.add(new SelectItem(0,""));
	listcategoriespersonnes2.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes2.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes2.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes2.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes2.add(new SelectItem(6,"CAISSIER"));
	}
	
	
	if(this.idCat1==4)
	{
	listcategoriespersonnes2.add(new SelectItem(0,""));
	listcategoriespersonnes2.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes2.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes2.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes2.add(new SelectItem(5,"PRODUCTEUR"));
	listcategoriespersonnes2.add(new SelectItem(6,"CAISSIER"));
	}
	
	
	if(this.idCat1==5)
	{
	listcategoriespersonnes2.add(new SelectItem(0,""));
	listcategoriespersonnes2.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes2.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes2.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes2.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes2.add(new SelectItem(6,"CAISSIER"));
	}
	
	
	if(this.idCat1==6)
	{
	listcategoriespersonnes2.add(new SelectItem(0,""));
	listcategoriespersonnes2.add(new SelectItem(1,"CHEF DE PRODUCTION"));
	listcategoriespersonnes2.add(new SelectItem(2,"GERANT"));
	listcategoriespersonnes2.add(new SelectItem(3,"GESTIONNAIRE"));
	listcategoriespersonnes2.add(new SelectItem(4,"CLIENT"));
	listcategoriespersonnes2.add(new SelectItem(5,"PRODUCTEUR"));
	}
	
	return listcategoriespersonnes2;
}
public void setListcategoriespersonnes2(
		List<SelectItem> listcategoriespersonnes2) {
	this.listcategoriespersonnes2 = listcategoriespersonnes2;
}



public void ajouterProfil()
{
if(this.idP==0)
{message="SELECTIONNER UNE PERSONNE S'IL VOUS PLAIT!!";
return;
	}
if(this.idCat2==0)
{message="VOUS N'AVEZ PAS INDIQUE LE NOUVEAU PROFIL!!";
	return;
	}
ResultSet res=null;
int n=-1;

if(this.idCat2==1)//ON VEUT LUI DONNER LE PROFIL DU CHEF DE PRODUCTION
{
res=Connecteur.Extrairedonnees("select * from chef_production where Idcheprod="+this.idP+" and supprimee=1");
try {
	if(res.next())
	{n=-1;
	n=Connecteur.Insererdonnees("update chef_production set supprimee=0 where Idcheprod="+this.idP+"");	
	}
	else
	{
		n=-1;
		n=Connecteur.Insererdonnees("insert into chef_production(Idcheprod,supprimee) values ("+this.idP+",0)");
	}
	
	if(n==-1)
		message="OPERATION ECHOUEE!!";
	else
		message="OPERATION REUSSIE!!";
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
if(this.idCat2==2)//ON VEUT LUI DONNER LE PROFIL DU GERANT
{

	res=Connecteur.Extrairedonnees("select * from gerant where Idgerant="+this.idP+" and supprimee=1");
	try {
		if(res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update gerant set supprimee=0 where Idgerant="+this.idP+"");	
		}
		else
		{
			n=-1;
			n=Connecteur.Insererdonnees("insert into gerant(Idgerant,supprimee) values ("+this.idP+",0)");
		}
		
		if(n==-1)
			message="OPERATION ECHOUEE!!";
		else
			message="OPERATION REUSSIE!!";
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
if(this.idCat2==3)//ON VEUT LUI DONNER LE PROFIL DU GESTIONNAIRE
{

	res=Connecteur.Extrairedonnees("select * from gestionnaire where Idgestion="+this.idP+" and supprimee=1");
	try {
		if(res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update gestionnaire set supprimee=0 where Idgestion="+this.idP+"");	
		}
		else
		{
			n=-1;
			n=Connecteur.Insererdonnees("insert into gestionnaire(Idgestion,supprimee) values ("+this.idP+",0)");
		}
		
		if(n==-1)
			message="OPERATION ECHOUEE!!";
		else
			message="OPERATION REUSSIE!!";
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
if(this.idCat2==4)//ON VEUT LUI DONNER LE PROFIL DU CLIENT
{

	res=Connecteur.Extrairedonnees("select * from client where Idclient="+this.idP+" and supprimee=1");
	try {
		if(res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update client set supprimee=0 where Idclient="+this.idP+"");	
		}
		else
		{
			n=-1;
			n=Connecteur.Insererdonnees("insert into client(Idclient,supprimee) values ("+this.idP+",0)");
		}
		
		if(n==-1)
			message="OPERATION ECHOUEE!!";
		else
			message="OPERATION REUSSIE!!";
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
if(this.idCat2==5)//ON VEUT LUI DONNER LE PROFIL DU PRODUCTEUR
{

	res=Connecteur.Extrairedonnees("select * from producteur where Idproduct="+this.idP+" and supprimee=1");
	try {
		if(res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update producteur set supprimee=0 where Idproduct="+this.idP+"");	
		}
		else
		{
			n=-1;
			n=Connecteur.Insererdonnees("insert into producteur(Idproduct,supprimee) values ("+this.idP+",0)");
		}
		
		if(n==-1)
			message="OPERATION ECHOUEE!!";
		else
			message="OPERATION REUSSIE!!";
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
if(this.idCat2==6)//ON VEUT LUI DONNER LE PROFIL DU CAISSIER
{

	res=Connecteur.Extrairedonnees("select * from caissier where Idcaissier="+this.idP+" and supprimee=1");
	try {
		if(res.next())
		{n=-1;
		n=Connecteur.Insererdonnees("update caissier set supprimee=0 where Idcaissier="+this.idP+"");	
		}
		else
		{
			n=-1;
			n=Connecteur.Insererdonnees("insert into caissier(Idcaissier,supprimee) values ("+this.idP+",0)");
		}
		
		if(n==-1)
			message="OPERATION ECHOUEE!!";
		else
			message="OPERATION REUSSIE!!";
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

this.idCat1=0;
this.idP=0;
this.idCat2=0;
	}


//FIN D'AJOUT DES CATEGORIES A UNE PERSONNE

}
