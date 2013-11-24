package paquetImprilac;
import java.util.regex.*;
public class Controleur {

	/**
	 * @param args
	 */
	
	private static Pattern pattern1;
	private static Matcher matcher1;
	
	
	
	
	//This function checks if "chaine" is a String composed by only
	//Letters and/or numbers and or "_"
	//It returns: 
	//-false if in "chaine" there is only Letters and/or numbers and or "_"
	//-true if there an other character(not letter or number or "_")
	public boolean isStringOfCharAndNumbers(String chaine)
	{boolean conclusion=false;
	String motifNomCharDeMot="\\W";
	pattern1 = Pattern.compile(motifNomCharDeMot);
	matcher1 = pattern1.matcher(chaine);
	conclusion=matcher1.find();
	return conclusion;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controleur  c=new Controleur();
		boolean b;

		b=c.isStringOfCharAndNumbers("Verna-ck233hgy");
		System.out.println(b);
		b=c.isStringOfCharAndNumbers("V)j 23 dhd$");
		System.out.println(b);	
		b=c.isStringOfCharAndNumbers("V)j23dhd$");
		System.out.println(b);
		b=c.isStringOfCharAndNumbers("32_3");
		System.out.println(b);
	}

}
