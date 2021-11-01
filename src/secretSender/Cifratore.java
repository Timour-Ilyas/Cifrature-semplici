/*
 * Classe dedita al calcolo della cifratura con l'algoritmo di riferimento scelto
 */
package secretSender;
import java.util.LinkedList;

public class Cifratore {
	/*
	 * Variabile Stringa che contiene il contenuto completo da cifrare
	 * 		Esso contiene il codice dell'agente segreto e il messaggio da inviare
	 */
	private String messaggioDaCifrare;
	/*
	 * Varibaile lista che ha il contenuto equivalente del messaggio da cifrare
	 * La funzione della lista è quella di poter lavorare facilmente con il
	 * contenuto del singolo nodo della lista.
	 * Ogni nodo della lista contiene un singolo carattere del messaggio
	 */
	private LinkedList<Character> listaDaCifrare = new LinkedList<Character>();
	/*
	 * Variabile Stringa che contiene il messaggio cifrato e pronto alla spedizione
	 */
	private String messaggioCifrato;
	/*
	 * L'algoritmo è stato ottimizzato per lavorare unicamente nel range di codice ASCII
	 * tra la 'A' e la 'Z', cioè le lettere maiuscole, quindi è stata implentata questa
	 * variabile booleana che serve a salvare l'informazione in merito al fatto che la
	 * lettera sia minuscola o maiuscola
	 */
	private Boolean upperCase;
	/*
	 * Variabile di appoggio che salva in codice ASCII la chiave,
	 * successivamente si va a sommare questo valore alla lettera della stringa,
	 * ciò comporta la cifratura della lettera
	 */
	private int numeroSommatoreAscii;

	/*
	 * Getter e setter
	 */
	public String getMessaggioDaCifrare() {
		return messaggioDaCifrare;
	}
	public void setMessaggioDaCifrare(String messaggioDaCifrare) {
		this.messaggioDaCifrare = messaggioDaCifrare;
	}
	public String getMessaggioCifrato() {
		return messaggioCifrato;
	}
	public void setMessaggioCifrato(String messaggioCifrato) {
		this.messaggioCifrato = messaggioCifrato;
	}

	/*
	 * Primo metodo di cifratura
	 * Cifratura di Cesare
	 */
	public String cifraturaDiCesare(String testo, String codiceNumerico, int chiave) {
		/*
		 * Preparazione del messaggio da cifrare
		 * Non è necessario eseguire controlli, sono stati già eseguiti dalla
		 * classe "SenderWindow"
		 */
		messaggioDaCifrare = codiceNumerico + ": " + testo;
		/*
		 * Stampa del messaggio prima di attuare la cifratura
		 * Ciò serve anche per controllare che l'input inserito
		 * sia stato salvato nella maniera corretta
		 */
		System.out.println("Messaggio da cifrare: " + messaggioDaCifrare);

		/*
		 * La chiave di cifratura è una variabile che può contenere un qualunque
		 * numero naturale, quindi nel caso il numero
		 * Non avviene il controllo in merito alla positività del numero perché
		 * è stata già eseguita dalla classe "SenderWIndow", esso impone che il
		 * numero debba essere strettamente maggiore di 1
		 */
		if(chiave > 26) {
			chiave %= 26;
			chiave++;
		}

		/*
		 * Inserimento del messaggio da cifrare, da una stringa ad una lista
		 * ciò semplicifica le operazioni di cifratura 
		 */
		for(int i = 0; i < messaggioDaCifrare.length(); i++) {
			listaDaCifrare.add(i, messaggioDaCifrare.charAt(i));
		}

		/*
		 * Inizio della cifratura
		 * Il ciclo for dura fintantoché  ogni lettera verrà cifrata
		 */
		for(int i = 0; i < listaDaCifrare.size(); i++) {
			/*
			 * Fase di controllo sulla caratteristica della lettera di essere
			 * maiuscola o minuscola. All'inizio viene salvato tale dato
			 * successivamente la lettera viene resa momentaneamente maiuscola
			 */
			upperCase = Character.isUpperCase(listaDaCifrare.get(i));

			listaDaCifrare.set(i, Character.toUpperCase(listaDaCifrare.get(i)));
			
			/*
			 * Viene salvato il valore ASCII della lettera in cui la lettera deve essere trasformata
			 */
			numeroSommatoreAscii = (int) listaDaCifrare.get(i) + chiave;
			
			/*
			 * Se il nuovo valore ASCII supera 90 (valore ASCII della "Z") significa che deve ripartire
			 * dall'inizio dell'alfabeto, facendo quindi un "-26"
			 */
			if(numeroSommatoreAscii > 90)
				numeroSommatoreAscii -= 26;
			
			/*
			 * Conversione del valore ASCII in carattere
			 */
			listaDaCifrare.set(i,(char) numeroSommatoreAscii);
			
			/*
			 * Se la lettera in origine era minuscola, la lettera convertita diventa minuscola
			 */
			if(upperCase) {
				Character.toLowerCase(listaDaCifrare.get(i));
			}
		}
		
		/*
		 * La lista di caratteri viene trasformata in una Stringa
		 */
		messaggioCifrato = "";
		for(int i = 0; i < listaDaCifrare.size(); i++) {
			messaggioCifrato = messaggioCifrato + listaDaCifrare.get(i);
		}
		
		//Stampa del messaggio cifrato
		System.out.println("Messaggio cifrato: " + messaggioCifrato);

		return messaggioCifrato;
	}

	/*
	 * Secondo metodo di cifratura
	 * Cifratura di Vigenère
	 */
	public String cifraturaDiVigenère(String testo, String codiceNumerico, int chiave) {
		/*
		 * Preparazione del messaggio da cifrare
		 * Non è necessario eseguire controlli, sono stati già eseguiti dalla
		 * classe "SenderWindow"
		 */
		messaggioDaCifrare = codiceNumerico + ": " + testo;
		/*
		 * Stampa del messaggio prima di attuare la cifratura
		 * Ciò serve anche per controllare che l'input inserito
		 * sia stato salvato nella maniera corretta
		 */
		System.out.println("Messaggio da cifrare: " + messaggioDaCifrare);
		
		/*
		 * Inserimento del messaggio da cifrare, da una stringa ad una lista
		 * ciò semplicifica le operazioni di cifratura 
		 */
		for(int i = 0; i < messaggioDaCifrare.length(); i++) {
			listaDaCifrare.add(i, messaggioDaCifrare.charAt(i));
		}
		



		return messaggioCifrato;
	}
}
