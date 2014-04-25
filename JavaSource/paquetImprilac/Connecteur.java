package paquetImprilac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;







public class Connecteur {

	/**
	 * @param args
	 */
	
	
	private static Connection con=null;
	private static String state;
	private static String resultofinsert;
	private static String resultofExtrairedonnees;
	
	
	

	
	public static void etablirconnection()
	{  
		//ClassBean.listes();
		state=null;
		try {System.out.println("a1a1a1a1a1");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("a2a2a2a2a2a2");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/imprilac", "root", "");
			 state="CONNEXION ETABLIE";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			state="PILOTE INTROUVABLE";
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			state="PAS DE CONNECTION";

			e.printStackTrace();
		}
		
	}		
		
		
		
		
		public static int Insererdonnees(String requete)
		{
			Statement stmt=null;
		    int i=-1;
			resultofinsert=null;
			//LA METHODE DEMANDE LA CONNECTION AVANT D'INSERER
			System.out.println("aaaaaaa");
			etablirconnection();
			System.out.println("bbbbbbb");
			if(state.equals("CONNEXION ETABLIE"))
			{	System.out.println("ccccccccc");
				try {
					stmt=con.createStatement();
					i=stmt.executeUpdate(requete);
					resultofinsert="INSERTION REUSSIE";
					System.out.println("dddddddddd");
				    } catch (SQLException e) {
					// TODO Auto-generated catch block
			
					resultofinsert="INSERTION ECHOUEE";
					e.printStackTrace();
				}

			}
			else resultofinsert="pas de connection";
			
			System.out.println("eeeeeeeeeee");
			
			return i;
			
		}
		
		
		
		
		public static ResultSet Extrairedonnees(String requete ){
			
			ResultSet result=null;
			resultofExtrairedonnees=null;
			Statement stmt=null;
			
			//CETTE METHODE APPEL LA METHODE QUI CREE LA CONNECTION
            etablirconnection();
			if(state.equals("CONNEXION ETABLIE"))
				
			{try {
				stmt=con.createStatement();
				result=stmt.executeQuery(requete);
				resultofExtrairedonnees="operation de lecture reussie";
			} catch (SQLException e) {
				
				System.out.println("oui");
				// TODO Auto-generated catch block
				resultofExtrairedonnees="operation de lecture echouee";	
				e.printStackTrace();
			}
				}else resultofExtrairedonnees="probleme de connexion";
			
			return result;
			
}
		
		
	
		
	    public static String getState() {
			return state;
		}

        public static String getResultofinsert() {
			return resultofinsert;
		}
        public static String getResultofExtrairedonnees() {
			return resultofExtrairedonnees;
		}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		state=null;
String req1=null,req2=null;
		req1="insert into personne (Idpersonne,Nompersonne,Prenompersonne) values (3,'KANA','Je')";
		req2="Select * from personne";
		Connecteur.etablirconnection();
		System.out.println("Valeur de state :"+state);
		state=null;
		Connecteur.Insererdonnees(req1);
		System.out.println("Valeur de state :"+resultofinsert);
		resultofExtrairedonnees=null;
		Connecteur.Extrairedonnees(req2);
		System.out.println("Valeur de state :"+resultofExtrairedonnees);
*/		
	}

}
