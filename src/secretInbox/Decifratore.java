/*
 * Classe dedita al calcolo della decifratura con l'algoritmo di riferimento scelto
 */
package secretInbox;

public class Decifratore {
	/*
	 * Variabile Stringa che contiene il messaggio decifrato
	 */
	private String messaggioDecifrato;

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
		//System.out.println("Messaggio da decifrare: " + testo);

		/*
		 * Preparazione del messaggio da decifrare
		 * Non è necessario eseguire controlli, sono stati già eseguiti dalla
		 * classe "InboxWindow"
		 */
		char[] arrayDiCharDaDecifrare = testo.toCharArray();
		byte[] arrayDiByteDaDecifrare = new byte[arrayDiCharDaDecifrare.length];

		for(int i = 0; i < arrayDiCharDaDecifrare.length;i++) 
			arrayDiByteDaDecifrare[i] = (byte) arrayDiCharDaDecifrare[i];

		byte[] arrayDiByteDecifrato = new byte[arrayDiCharDaDecifrare.length];

		/*
		 * Inizio della cifratura
		 * Il ciclo for dura fintantoché  ogni lettera verrà cifrata
		 */
		int momentum;
		for (int i = 0; i < arrayDiByteDaDecifrare.length; i++) {
			momentum = (int) arrayDiByteDaDecifrare[i] - chiave;
			if(arrayDiByteDaDecifrare[i] == '!')
				arrayDiByteDecifrato[i] = (byte) 32;
			else
				arrayDiByteDecifrato[i] = (byte) momentum;
		}

		messaggioDecifrato = new String(arrayDiByteDecifrato, 0, arrayDiByteDecifrato.length).trim();

		//Stampa del messaggio cifrato
		//System.out.println("Messaggio decifrato: " + messaggioDecifrato);

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
		//System.out.println("Messaggio da decifrare: " + testo);

		/*
		 * Preparazione del messaggio da cifrare
		 * Non è necessario eseguire controlli, sono stati già eseguiti dalla
		 * classe "InboxWindow"
		 */
		char[] arrayDiCharDaDecifrare = testo.toCharArray();
		byte[] arrayDiByteDaDecifrare = new byte[arrayDiCharDaDecifrare.length];

		for(int i = 0; i < arrayDiCharDaDecifrare.length;i++) 
			arrayDiByteDaDecifrare[i] = (byte) arrayDiCharDaDecifrare[i];

		char[] chiaveInChar = chiave.toCharArray();
		byte[] chiaveInByte = new byte[chiaveInChar.length];

		for(int i = 0; i < chiaveInChar.length;i++)
			chiaveInByte[i] = (byte) chiaveInChar[i];

		byte[] arrayDiByteDecifrato = new byte[arrayDiCharDaDecifrare.length];

		/*
		 * Inizio della decifratura
		 * Il ciclo for dura fintantoché  ogni lettera verrà cifrata
		 */
		int momentum;
		for (int i = 0; i < arrayDiByteDaDecifrare.length; i++) {
			momentum = (int) arrayDiByteDaDecifrare[i] - (int) chiaveInByte[i % chiaveInByte.length];
			if(arrayDiByteDaDecifrare[i] == '!') 
				arrayDiByteDecifrato[i] = (byte) 32;
			else
				arrayDiByteDecifrato[i] = (byte) momentum;
		}

		messaggioDecifrato = new String(arrayDiByteDecifrato, 0, arrayDiByteDecifrato.length).trim();

		//System.out.println("Messaggio decifrato: " + messaggioDecifrato);

		return messaggioDecifrato;
	}
	
	/*
	 * Metodo di Vigenère con parametro chiave int
	 */
	public String decifraturaDiVigenère(String testo, int chiave) {
		/*
		 * Se come chiave viene passato un intero
		 * Trasforma la chiave in una stringa e chiama il metodo principale che esegue il calcolo
		 */
		String chiaveInStringa = String.valueOf(chiave);
		return decifraturaDiVigenère(testo, chiaveInStringa);
	}
}
