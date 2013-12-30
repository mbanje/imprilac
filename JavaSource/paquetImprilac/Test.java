package paquetImprilac;


//Packages � importer afin d'utiliser l'objet File
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {/*
		// TODO Auto-generated method stub

		//Nous d�clarons nos objets en dehors du bloc try/catch
		FileInputStream fis;
		FileOutputStream fos;
		BufferedInputStream bis;
		BufferedOutputStream bos;
		
		
		
		
		try {
			System.out.println("1");
			fis = new FileInputStream(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\maquettes\\Projet.ppt"));
			System.out.println("2");
			fos = new FileOutputStream(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\maquettes\\PP.ppt"));
			System.out.println("3");
			
			
			//bis = new BufferedInputStream(new FileInputStream(new File("TTTTT.txt")));
			//bos = new BufferedOutputStream(new FileOutputStream(new File("TTTTT3.txt")));
			byte[] buf = new byte[8];
			//On r�cup�re le temps du syst�me
			long startTime = System.currentTimeMillis();
			while(fis.read(buf) != -1){
			fos.write(buf);
			}
		
		//On affiche le temps d'ex�cution
		System.out.println("Temps de lecture + �criture avec FileInputSt,! ream et FileOutputStream : " + (System.currentTimeMillis() - startTime));
		//On r�initialise
		startTime = System.currentTimeMillis();
		//while(bis.read(buf) != -1){
		//bos.write(buf);
		//}
		//On r�affiche
		System.out.println("Temps de lecture + �criture avec BufferedIn,! putStream et BufferedOutputStream : " + (System.currentTimeMillis() - startTime));
		//On ferme nos flux de donn�es
		fis.close();
		//bis.close();
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		
	*/
		Desktop desk = Desktop.getDesktop();
		try {
			desk.open(new File("C:\\Documents and Settings\\S\\Mes documents\\memoire\\maquettes\\113_11_28_6_17_29_11_Dice.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
