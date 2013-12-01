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


	
}
