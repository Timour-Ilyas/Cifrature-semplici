/**
 ** Nome programma: SecretSender
 ** Versione programma: 1.0
 ** Data di inizio: 24/10/21
 ** Autore: Timour Ilyas, Bresci Bernardo, Capecchi Matteo
 ** Problema: Sviluppare un 

 ** Dati
 ** In input: 

 ** in output: 

 ** Osservazioni:

 */
package secretSender;
import java.util.ArrayList;

public class SecretSender {
	public static void main(String[] args) {
		SenderWindow obj = new SenderWindow();
		obj.setVisible(true);
		System.out.println("SecretSender Attivato");
		
		String testo = "Testo";
		String codiceNumerico = "0001";
		String messaggioDaCifrare = codiceNumerico + ": " + testo;
		System.out.println(messaggioDaCifrare);
		
		ArrayList<Character> listaDaCifrare = new ArrayList<Character>();
		
		for(int i = 0; i < messaggioDaCifrare.length(); i++) {
			listaDaCifrare.add(i, messaggioDaCifrare.charAt(i));
		}
		
		Boolean upperCase;
		for(int i = 0; i < listaDaCifrare.size(); i++) {
			upperCase = Character.isUpperCase(listaDaCifrare.get(i));
			
			
			
		}
		
	}
}
