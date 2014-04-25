package paquetImprilac;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

public class Afficherbean {
	
	private int idPersonneConnecte;
	private String message;
	

	public Afficherbean()
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

	public int getIdPersonneConnecte() {
		return idPersonneConnecte;
	}

	public void setIdPersonneConnecte(int idPersonneConnecte) {
		this.idPersonneConnecte = idPersonneConnecte;
	}
	
	
	private List<Materiel> listDesMatAuChef;

	public List<Materiel> getListDesMatAuChef() {
		
		
		ResultSet res=null;
		Materiel Mat=null;
		
		if(listDesMatAuChef==null)
			listDesMatAuChef=new ArrayList<Materiel>();
		else
			listDesMatAuChef.clear();
		

			res=Connecteur.Extrairedonnees("select * from materiel ");
			int i=1;
		try {
			while(res.next())
			{Mat=new Materiel();
			Mat.setNum(i);
			Mat.setIdMateriel(res.getInt("Idmateriel"));
			Mat.setDesignation(res.getString("Designation"));
			Mat.setQteAuChef(res.getInt("qtiteRestteOChef"));
			listDesMatAuChef.add(Mat);
			i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listDesMatAuChef;
	}

	public void setListDesMatAuChef(List<Materiel> listDesMatAuChef) {
		this.listDesMatAuChef = listDesMatAuChef;
	}
	
	
	private List<Personne> listDesProducteursDispo;

	public List<Personne> getListDesProducteursDispo() {
		
		
		ResultSet res=null;
		Personne p=null;
		
		if(listDesProducteursDispo==null)
			listDesProducteursDispo=new ArrayList<Personne>();
		else
			listDesProducteursDispo.clear();
		

			res=Connecteur.Extrairedonnees("select * from personne p,producteur pr where p.Idpersonne=pr.Idproduct and Etatproducteur='DISPONIBLE' order by Nompersonne");
			int i=1;
			
		try {
			while(res.next())
			{p=new Personne();
			p.setNumPersonne(i);
			p.setIdPersonne(res.getInt("Idpersonne"));
			p.setNomPersonne(res.getString("Nompersonne"));
			p.setPrenomPersonne(res.getString("Prenompersonne"));
			p.setDiplome(res.getString("Diplome"));
			listDesProducteursDispo.add(p);
			i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listDesProducteursDispo;
	}

	public void setListDesProducteursDispo(List<Personne> listDesProducteursDispo) {
		this.listDesProducteursDispo = listDesProducteursDispo;
	}
	
	private List<Personne> listDeTousLesProducteurs;

	public List<Personne> getListDeTousLesProducteurs() {
		
		
		ResultSet res=null;
		Personne p=null;
		
		if(listDeTousLesProducteurs==null)
			listDeTousLesProducteurs=new ArrayList<Personne>();
		else
			listDeTousLesProducteurs.clear();
		

			res=Connecteur.Extrairedonnees("select * from personne p,producteur pr where p.Idpersonne=pr.Idproduct order by Nompersonne");
			int i=1;
			
		try {
			while(res.next())
			{p=new Personne();
			p.setNumPersonne(i);
			p.setIdPersonne(res.getInt("Idpersonne"));
			p.setNomPersonne(res.getString("Nompersonne"));
			p.setPrenomPersonne(res.getString("Prenompersonne"));
			p.setDiplome(res.getString("Diplome"));
			p.setEtat(res.getString("Etatproducteur"));
			listDeTousLesProducteurs.add(p);
			i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return listDeTousLesProducteurs;
	}

	public void setListDeTousLesProducteurs(List<Personne> listDeTousLesProducteurs) {
		this.listDeTousLesProducteurs = listDeTousLesProducteurs;
	}
	
	
	private Personne psele=null;

	public Personne getPsele() {
		return psele;
	}

	public void setPsele(Personne psele) {
		this.psele = psele;
	}


	private List<SelectItem> listDesEtatsDesEmploy;

	public List<SelectItem> getListDesEtatsDesEmploy() {
		
		
		if(listDesEtatsDesEmploy==null)
			this.listDesEtatsDesEmploy=new ArrayList<SelectItem>();
		else
			this.listDesEtatsDesEmploy.clear();
		
		listDesEtatsDesEmploy.add(new SelectItem(0,""));
		listDesEtatsDesEmploy.add(new SelectItem(1,"DISPONIBLE"));
		listDesEtatsDesEmploy.add(new SelectItem(2,"EN CONGE"));
		listDesEtatsDesEmploy.add(new SelectItem(3,"PARTI"));
		
		
		return listDesEtatsDesEmploy;
	}

	public void setListDesEtatsDesEmploy(List<SelectItem> listDesEtatsDesEmploy) {
		this.listDesEtatsDesEmploy = listDesEtatsDesEmploy;
	}
	
	private int idEtat;
	public int getIdEtat() {
		return idEtat;
	}

	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}
	
	
	
	
	public void changeEtatProduct()
	{int n=-1;

	if(this.idEtat==0)
	{
		message="VOUS N'AVEZ CHOISI AUCUN ETAT!!";
		return;
	}
	
	if(this.idEtat==1)
	{
	n=Connecteur.Insererdonnees("update producteur set Etatproducteur='DISPONIBLE' where Idproduct="+this.psele.getIdPersonne()+"");	
	if(n!=-1)
		message="OPERATION REUSSIE!!";
	else
		message="OPERATION ECHOUEE!!";
	
	return;
	}
	
	if(this.idEtat==2)
	{
		n=Connecteur.Insererdonnees("update producteur set Etatproducteur='EN CONGE' where Idproduct="+this.psele.getIdPersonne()+"");	
		if(n!=-1)
			message="OPERATION REUSSIE!!";
		else
			message="OPERATION ECHOUEE!!";
		
		return;
		
	}
	
	if(this.idEtat==3)
	{
		n=Connecteur.Insererdonnees("update producteur set Etatproducteur='PARTI' where Idproduct="+this.psele.getIdPersonne()+"");	
		if(n!=-1)
			message="OPERATION REUSSIE!!";
		else
			message="OPERATION ECHOUEE!!";
		
		return;

	}
	this.idEtat=0;
	this.listDesEtatsDesEmploy.clear();
	
	}

}
