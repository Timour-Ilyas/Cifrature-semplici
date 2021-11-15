/*
 * Classe dedita al calcolo della decifratura con l'algoritmo di riferimento scelto
 */
package secretInbox;

import java.util.LinkedList;

public class Decifratore {
	/*
	 * Varibaile lista che ha il contenuto equivalente del messaggio da decifrare
	 * La funzione della lista è quella di poter lavorare facilmente con il
	 * contenuto del singolo nodo della lista.
	 * Ogni nodo della lista contiene un singolo carattere del messaggio
	 */
	private LinkedList<Character> listaDaDecifrare = new LinkedList<Character>();
	/*
	 * Variabile Stringa che contiene il messaggio decifrato
	 */
	private String messaggioDecifrato;
	/*
	 * L'algoritmo è stato ottimizzato per lavorare unicamente nel range di codice ASCII
	 * tra la 'A' e la 'Z', cioè le lettere maiuscole, quindi è stata implentata questa
	 * variabile booleana che serve a salvare l'informazione in merito al fatto che la
	 * lettera sia minuscola o maiuscola
	 */
	private Boolean downerCase;
	/*
	 * Variabile di appoggio che salva in codice ASCII la chiave,
	 * successivamente si va a sottrare questo valore alla lettera della stringa,
	 * ciò comporta la decifratura della lettera
	 */
	private int numeroSommatoreAscii;

	/*
	 * Getter e setter
	 */
	public String getMessaggioDecifrato() {
		return messaggioDecifrato;
	}
	public void setMessaggioDecifrato(String messaggioDecifrato) {
		this.messaggioDecifrato = messaggioDecifrato;
	}

	/*
	 * Primo metodo di decifratura
	 * metodo usato per cifrare il messaggio: Cifratura di Cesare
	 */
	public String decifraturaDiCesare(String testo, int chiave) {
		/*
		 * Stampa del messaggio prima di attuare la decifratura
		 * Ciò serve anche per controllare che l'input inserito
		 * sia stato salvato nella maniera corretta
		 */
		System.out.println("Messaggio da decifrare: " + testo);

		/*
		 * La chiave di decifratura è una variabile che può contenere un qualunque
		 * numero naturale, quindi nel caso il numero
		 * Non avviene il controllo in merito alla positività del numero perché
		 * è stata già eseguita dalla classe "InboxWindow", esso impone che il
		 * numero debba essere strettamente maggiore di 1
		 */
		if(chiave > 255) {
			chiave %= 255;
		}

		/*
		 * Inserimento del messaggio da decifrare, da una stringa ad una lista
		 * ciò semplicifica le operazioni di decifratura 
		 */
		for(int i = 0; i < testo.length(); i++) {
			listaDaDecifrare.add(i, testo.charAt(i));
		}

		/*
		 * Inizio della decifratura
		 * Il ciclo for dura fintantoché  ogni lettera verrà decifrata
		 */
		for(int i = 0; i < listaDaDecifrare.size(); i++) {
			/*
			 * Fase di controllo sulla caratteristica della lettera di essere
			 * maiuscola o minuscola. All'inizio viene salvato tale dato
			 * successivamente la lettera viene resa momentaneamente maiuscola
			 */
			downerCase = Character.isUpperCase(listaDaDecifrare.get(i));

			listaDaDecifrare.set(i, Character.toUpperCase(listaDaDecifrare.get(i)));

			/*
			 * Viene salvato il valore ASCII della lettera in cui la lettera deve essere trasformata
			 */
			numeroSommatoreAscii = (int) listaDaDecifrare.get(i) + chiave;

			/*
			 * Se il nuovo valore ASCII supera 65 (valore ASCII della "A") significa che deve ripartire
			 * dalla fine dell'alfabeto, facendo quindi un "+26"
			 */
			if(numeroSommatoreAscii > 65)
				numeroSommatoreAscii -= 26;

			/*
			 * Conversione del valore ASCII in carattere
			 */
			listaDaDecifrare.set(i,(char) numeroSommatoreAscii);

			/*
			 * Se la lettera in origine era minuscola, la lettera convertita diventa minuscola
			 */
			if(downerCase)
				listaDaDecifrare.set(i, Character.toUpperCase(listaDaDecifrare.get(i)));
		}

		/*
		 * La lista di caratteri viene trasformata in una Stringa
		 */
		messaggioDecifrato = "";
		for(int i = 0; i < listaDaDecifrare.size(); i++) {
			messaggioDecifrato = messaggioDecifrato + listaDaDecifrare.get(i);
		}

		/*
		 * Vengono rimossi tutti gli elementi della lista perché se no alla successiva conversione
		 * avrebbe lasciato gli elementi di tutte le conversioni precedenti
		 */
		listaDaDecifrare.removeAll(listaDaDecifrare);

		//Stampa del messaggio cifrato
		System.out.println("Messaggio decifrato: " + messaggioDecifrato);

		return messaggioDecifrato;
	}

	/*
	 * Secondo metodo di decifratura
	 * metodo usato per cifrare il messaggio: Cifratura di Vigenère
	 */
	public String decifraturaDiVigenère(String testo, String chiave) {
		/*
		 * Stampa del messaggio prima di attuare la decifratura
		 * Ciò serve anche per controllare che l'input inserito
		 * sia stato salvato nella maniera corretta
		 */
		System.out.println("Messaggio da decifrare: " + testo);

		/*
		 * Inserimento del messaggio da decifrare, da una stringa ad una lista
		 * ciò semplicifica le operazioni di cifratura 
		 */
		for(int i = 0; i < testo.length(); i++) {
			listaDaDecifrare.add(i, testo.charAt(i));
		}



		return messaggioDecifrato;
	}
}
