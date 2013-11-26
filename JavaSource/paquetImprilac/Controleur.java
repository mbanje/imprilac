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

	
	//This function checks if "chaine" is a String composed by only
	//Letters and/or numbers only
	//It returns: 
	//-false if in "chaine" there is only Letters and/or numbers
	//-true if there an other character(not letter or number or "_")
	public boolean isStringOfLettersAndNumbersOnly(String chaine)
	{boolean conclusion=false;
	String motif="[^a-zA-Z]";
	pattern1 = Pattern.compile(motif);
	matcher1 = pattern1.matcher(chaine);
	conclusion=matcher1.find();
	return conclusion;
	}
	
	public boolean isStringForPernom(String chaine)
	{boolean b1,b2,conclusion=false;
	String motif1="[\\S&&\\W]";
	String motif2="\\W";
	pattern1 = Pattern.compile(motif1);
	matcher1 = pattern1.matcher(chaine);
	b1=matcher1.find();
/*	pattern1 = Pattern.compile(motif2);
	matcher1 = pattern1.matcher(chaine);
	b2=matcher1.find();
	if(b1 && b2)
		conclusion=true;
	else
		conclusion=false;*/
	return conclusion;
	}
	
	
	public boolean isNumber(String chaine)
	{boolean conclusion=false;
	String motif="[^0-9]";
	pattern1 = Pattern.compile(motif);
	matcher1 = pattern1.matcher(chaine);
	conclusion=matcher1.find();
		return conclusion;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controleur  c=new Controleur();
		boolean b;

		b=c.isNumber("Verna-ck23hgy");
		System.out.println(b);
		b=c.isNumber("ç 23 $");
		System.out.println(b);	
		b=c.isNumber("V)j23dhd$");
		System.out.println(b);
		b=c.isNumber("323");
		System.out.println(b);
		b=c.isNumber("Gtw");
		System.out.println(b);
		b=c.isNumber("Gt_w");
		System.out.println(b);
		b=c.isNumber("");
		System.out.println(b);
	}

}
