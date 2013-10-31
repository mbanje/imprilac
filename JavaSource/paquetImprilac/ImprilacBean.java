package paquetImprilac;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;






public class ImprilacBean {

	


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
	res=Connecteur.Extrairedonnees("select * from materiel ");

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
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod");

	try {
		while(res.next())
		{
		listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));
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
		{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

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
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

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
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));
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
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

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
			{listper2.add(new SelectItem(res.getInt("Idpersonne"),res.getString("Nompersonne")+"  "+res.getString("Prenompersonne")));

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
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c,compte co where p.Idpersonne=c.Idcheprod and p.Idpersonne=co.Idpersonne order by p.Nompersonne");

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
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g,compte co where p.Idpersonne=g.Idgerant and p.Idpersonne=co.Idpersonne order by p.Nompersonne");

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
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g,compte co where p.Idpersonne=g.Idgestion and p.Idpersonne=co.Idpersonne order by p.Nompersonne");

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
		res=Connecteur.Extrairedonnees("select * from personne p,client c,compte co where p.Idpersonne=c.Idclient and p.Idpersonne=co.Idpersonne order by p.Nompersonne");

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
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro,compte co where p.Idpersonne=pro.Idproduct and p.Idpersonne=co.Idpersonne order by p.Nompersonne");

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
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c,compte co where p.Idpersonne=c.Idcaissier and p.Idpersonne=co.Idpersonne order by p.Nompersonne");

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



public void buildListDesPers()//DEBUT DE LA FONCTION QUI NOUS PERMET DE PASSER AU MODE MODIFIER ET DE CONSTRUIRE LA LISTE DES PERSONNES D'UNE CATEGORIE CHOISIE
{this.modifier=true;
 
ResultSet res=null;


if(listper==null)
	listper=new ArrayList<SelectItem>();
else
	listper.clear();

listper.add(new SelectItem(0,"    "));

if(listMaterielProduit==null)
	listMaterielProduit=new ArrayList<SelectItem>();
else
	listMaterielProduit.clear();

listMaterielProduit.add(new SelectItem(0,"    "));


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
		res=Connecteur.Extrairedonnees("select * from materiel ");

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
		res=Connecteur.Extrairedonnees("select * from produits ");

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


public List<ImprilacBean> getListPesonnes() {

	
	ResultSet res=null;
	ImprilacBean chef=null;
	
	if(listPesonnes==null)
		listPesonnes=new ArrayList<ImprilacBean>();
	else
		listPesonnes.clear();
	
	if(idcategoriePersonne==1)//ON A CHOISIE LE PROFIL CHEF DE PRODUCTION
	{
		res=Connecteur.Extrairedonnees("select * from personne p,chef_production c where p.Idpersonne=c.Idcheprod");
/*	if(listPesonnes==null)
		listPesonnes=new ArrayList<ImprilacBean>();
	else
		listPesonnes.clear();*/
	try {
		while(res.next())
		{chef=new ImprilacBean();
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
		res=Connecteur.Extrairedonnees("select * from personne p,gerant g where p.Idpersonne=g.Idgerant");
	try {
		while(res.next())
		{chef=new ImprilacBean();
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
		res=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g where p.Idpersonne=g.Idgestion");

		try {
			while(res.next())
			{chef=new ImprilacBean();
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
		res=Connecteur.Extrairedonnees("select * from personne p,client c where p.Idpersonne=c.Idclient");

		try {
			while(res.next())
			{chef=new ImprilacBean();
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
		res=Connecteur.Extrairedonnees("select * from personne p,producteur pro where p.Idpersonne=pro.Idproduct");

		try {
			while(res.next())
			{chef=new ImprilacBean();
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
		res=Connecteur.Extrairedonnees("select * from personne p,caissier c where p.Idpersonne=c.Idcaissier");

		try {
			while(res.next())
			{chef=new ImprilacBean();
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


public int getIdPersonn() {
	return idPersonn;
}
public void setIdPersonn(int idPersonn) {
	this.idPersonn = idPersonn;
}
public void setListPesonnes(List<ImprilacBean> listPesonnes) {
	ImprilacBean.listPesonnes = listPesonnes;
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
	
	 
	if(this.idType==1)//ON INSERT UN MATERIEL
	  {  ResultSet res=null;
		if(this.designation==null||this.designation=="")
	      { 
		   message="TAPEZ LE NOM DU MATERIEL S'IL VOUS PLAIT!";
		   return; 
	      }
	      if(this.historisation==null||this.historisation=="")
	      {
	      message="DIRER S'IL NECESSAIRE D'HISTORISER OU PAS SVP!";
	      return;
	      }  
	      
	      res=Connecteur.Extrairedonnees("select * from materiel where Designation='"+this.designation+"'");
	      try {
			if(res.next())
			  {message="CE MATERIEL EXISTE DEJA!!";
				return; 
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		   n=Connecteur.Insererdonnees("insert into materiel(Designation,Historisation,quantiteEnStocks) values ('"+this.designation+"','"+this.historisation+"',0)");
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
	  ResultSet res=null;
		if(this.designation==null||this.designation=="")
	    {  
		message="TAPEZ LE NOM DU PRODUIT S'IL VOUS PLAIT!";
		return;
	    }
		
		res=Connecteur.Extrairedonnees("select * from produits where Type='"+this.designation+"'");
		try {
			if(res.next())
			{message="CE PRODUIT EST DEJA ENREGISTRE!!";
			return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		n=Connecteur.Insererdonnees("insert into produits (Type) values ('"+this.designation+"')");
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
		
		
		
		if(this.idType==1)
		{	
			if((this.designation.length()<=0)&&(this.historisation.length()<=0))
			{  
			message="VOUS N'AVEZ RIEN MODIFIE!";
			
			return;
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
	listcategoriespersonnes.add(new SelectItem(4,"CLIENT"));
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



//DEBUT DE LA FONCTION QUI FAIT L'INSERTION ET LA MODIFICATION DANS LA TABLE PERSONNE
public void insertPersonne()
{

if(this.modifier!=true)//ON FAIT L'INSERTION PAS LA MODIFICATION
{	
	if(this.nomPersonne==null||this.nomPersonne=="")
		{message="TAPEZ LE NOM S'IL VOUS PLAIT!";
		return;
		}
    
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
		n=Connecteur.Insererdonnees("insert into personne(Nompersonne,Prenompersonne,Datenaissance)values('"+this.nomPersonne+"','"+this.prenomPersonne+"','"+changeDateFormat(dateNaissance)+"')");
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
			n=Connecteur.Insererdonnees("insert into chef_production(Idcheprod,Bureau,Diplome) values ("+id+",'"+this.bureau+"','"+this.diplome+"')");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
		}
		
		if(this.idcategoriePersonne==2)//ON INSERT LE GERANT
		{
			n=Connecteur.Insererdonnees("insert into gerant(Idgerant,Bureau,Diplome) values ("+id+",'"+this.bureau+"','"+this.diplome+"')");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
		}
		
		if(this.idcategoriePersonne==3)//ON INSERT LE GESTIONNAIRE
		{
			n=Connecteur.Insererdonnees("insert into gestionnaire(Idgestion,Diplome) values ("+id+",'"+this.diplome+"')");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!";
	 
		}
		
		if(this.idcategoriePersonne==4)//ON INSERT LE CLIENT
		{
			
			n=Connecteur.Insererdonnees("insert into client(Idclient,tel) values ("+id+",'"+this.tel+"')");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!"; 
		}
 
		if(this.idcategoriePersonne==5)//ON INSERT LE PRODUCTEUR
		{
			n=Connecteur.Insererdonnees("insert into producteur(Idproduct,Bureau,Diplome) values ("+id+",'"+this.bureau+"','"+this.diplome+"')");
			if(n!=-1)
				message="INSERTION REUSSIE!";
			else
				message="ECHEC D'INSERTION!"; 
		}
		
		if(this.idcategoriePersonne==6)//ON INSERT LE CAISSIER
		{
			n=Connecteur.Insererdonnees("insert into caissier(Idcaissier,Diplome) values ("+id+",'"+this.diplome+"')");
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


public void modifierPersonne()
{int n;
System.out.println("momomomo");

n=-1;

	n=Connecteur.Insererdonnees("update personne set Nompersonne='"+this.selected.nomPersonne+"',Prenompersonne='"+this.selected.prenomPersonne+"',Datenaissance='"+changeDateFormat(this.selected.dateNaissance)+"' where Idpersonne="+selected.idPersonn);
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
	
}



public void modifieCompte()
{int n;
	if((this.selected.nouveauLogin.length()>0)&&(this.selected.nouveauLogin.length()<4))
	{message="SAISISSER UNE CHAINE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
	return;
	}

	if((this.selected.nouveauPassWord.length()>0)&&(this.selected.nouveauPassWord.length()<4))
	{message="SAISISSER UNE CHAINE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
	return;
	}

	if(this.selected.nouveauPassWord.length()>0)
	{
	
	n=-1;
	n=Connecteur.Insererdonnees("UPDATE COMPTE SET Password='"+this.selected.nouveauPassWord+"' where Login='"+this.selected.login+"'");
	
	if(n==-1)
		{message="MISE A JOUR ECHOUEE!!";
		return;
		}
	else
		message="MISE A JOUR REUSSIE!!";
	
	}
	
	if(this.selected.nouveauLogin.length()>0)
	{
	n=-1;
	n=Connecteur.Insererdonnees("UPDATE COMPTE SET Login='"+this.selected.nouveauLogin+"' where Login='"+this.selected.login+"'");
	if(n==-1)
		message="MISE A JOUR ECHOUEE!!";
	else
		message="MISE A JOUR REUSSIE!!";
		
	}
	

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
	{message="SAISISSER UNE CHAINE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
	return;
	}

	if(this.passWord.length()<4)
	{message="SAISISSER UNE CHAINE D'AU MOINS 4 CARACTERE S'IL VOUS PLAIT!!";
	return;
	}

	if(this.idcategoriePersonne==0)
	{message="CHOISISSER LA CATEGORIE S'IL VOUS PLAIT!!";
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
	
	System.out.println("ggggggggggggggggggggggg");
	
	n=-1;
	System.out.println("AVANT INSERRER");
	n=Connecteur.Insererdonnees("insert into compte (Idpersonne,Login,Password) values ("+this.idPerso+",'"+this.login+"','"+this.passWord+"')");
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
	{this.modifierCompte=false;//ON DESACTIVE LE MODE MODIFICATION
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
	}//FIN DE LA PARTIE DE MODIFICATION DES COMPTES
this.login=null;
this.passWord=null;
}
//FIN DE LA FONCTION QUI PERMET L'INSERTION ET LA MODIFICATION DANS LA TABLE COMPTE

public void ecouteModifierCompte()
{this.modifierCompte=true;
this.modifierComptel1=false;
	}

public void identification()
{ 	ResultSet r=null;

	if(this.login.length()==0)
	{message="SAISISSER LE LOGIN S'IL VOUS PLAIT!!!";
	return;
	}
	if(this.passWord.length()==0)
	{message="SAISISSER LE MOT DE PASSE S'IL VOUS PLAIT!!!";
	return;
	}
	
   //DEBUT DU TEST QUE LA PERSONNE EST UN CHEF DE PRODUCTION
	r=Connecteur.Extrairedonnees("select * from personne p,chef_production c,compte co where p.Idpersonne=c.Idcheprod and p.Idpersonne=co.Idpersonne and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME CHEF DE PRODUCTION!!!";
			return;
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST UN CHEF DE PRODUCTION
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN LE GERANT
	r=Connecteur.Extrairedonnees("select * from personne p,gerant g,compte co where p.Idpersonne=g.Idgerant and p.Idpersonne=co.Idpersonne and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME GERANT!!!";
			return;
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST LE GERANT
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN LE GESTIONNAIRE
	r=Connecteur.Extrairedonnees("select * from personne p,gestionnaire g,compte co where p.Idpersonne=g.Idgestion and p.Idpersonne=co.Idpersonne and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME GESTIONNAIRE!!!";
			return;
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST UN LE GESTIONNAIRE
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN PRODUCTEUR
	r=Connecteur.Extrairedonnees("select * from personne p,producteur pro,compte co where p.Idpersonne=pro.Idproduct and p.Idpersonne=co.Idpersonne and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME PRODUCTEUR!!!";
			return;
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//FIN DU TEST QUE LA PERSONNE EST UN PRODUCTEUR
	
	//DEBUT DU TEST QUE LA PERSONNE EST UN CAISSIER
	r=Connecteur.Extrairedonnees("select * from personne p,caissier c,compte co where p.Idpersonne=c.Idcaissier and p.Idpersonne=co.Idpersonne and co.Login='"+this.login+"' and co.Password='"+this.passWord+"'");
	try {
		if(r.next())
			{message="VOUS ETES RECONNU COMME CAISSIER!!!";
			return;
			}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	//FIN DU TEST QUE LA PERSONNE EST UN CAISSIER
	
	message="VOUS N'ETES PAS RECONNU PAR LE SYSTEME!!!";
	
}




}
