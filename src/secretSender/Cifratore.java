/*
 * Classe dedita al calcolo della cifratura con l'algoritmo di riferimento scelto
 */
package secretSender;

public class Cifratore {
	/*
	 * Variabile Stringa che contiene il messaggio cifrato e pronto alla spedizione
	 */
	private String messaggioCifrato;

	/*
	 * Getter e setter
	 */
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
		 * Stampa del messaggio prima di attuare la cifratura
		 * Ciò serve anche per controllare che l'input inserito
		 * sia stato salvato nella maniera corretta
		 */
		System.out.println("Messaggio da cifrare: " + ": " + testo);

		//Stringa -> Vettore di caratteri
		//Vettore di caratteri -> Vettore di Byte
		/*
		 * Preparazione del messaggio da cifrare
		 * Non è necessario eseguire controlli, sono stati già eseguiti dalla
		 * classe "SenderWindow"
		 */
		char[] arrayDiCharDaCifrare = (codiceNumerico + ": " + testo).toCharArray();
		byte[] arrayDiByteDaCifrare = new byte[arrayDiCharDaCifrare.length];

		for(int i = 0; i < arrayDiCharDaCifrare.length;i++) 
			arrayDiByteDaCifrare[i] = (byte) arrayDiCharDaCifrare[i];

		byte[] arrayDiByteCifrato = new byte[arrayDiCharDaCifrare.length];

		/*
		 * INIZIO DELLA CIFRATURA
		 * Il ciclo for dura fintantoché  ogni lettera verrà cifrata
		 */
		int momentum;
		for (int i = 0; i < arrayDiByteDaCifrare.length; i++) {
			momentum = (int) arrayDiByteDaCifrare[i] + (chiave % 256);
			momentum %= 256;

			//Controllo degli spazi
			if(arrayDiByteDaCifrare[i] == ' ')//Spazio diventa: !
				arrayDiByteCifrato[i] = (byte) 33; //33 = !
			else
				arrayDiByteCifrato[i] = (byte) momentum;
		}

		//Trasformiamo l'array di byte in una stringa
		messaggioCifrato = new String(arrayDiByteCifrato, 0, arrayDiByteCifrato.length).trim();
		//Trim toglie spazi ulteriori che danno errori

		//Stampa del messaggio cifrato
		System.out.println("Messaggio cifrato: " + messaggioCifrato);

		return messaggioCifrato;//Returniamo una stringa
	}

	/*
	 * Secondo metodo di cifratura
	 * Cifratura di Vigenère
	 */
	public String cifraturaDiVigenère(String testo, String codiceNumerico, String chiave) {
		/*
		 * Stampa del messaggio prima di attuare la cifratura
		 * Ciò serve anche per controllare che l'input inserito
		 * sia stato salvato nella maniera corretta
		 */
		System.out.println("Messaggio da cifrare: " + codiceNumerico + ": " + testo);

		/*
		 * Preparazione del messaggio da cifrare
		 * Non è necessario eseguire controlli, sono stati già eseguiti dalla
		 * classe "SenderWindow"
		 */
		char[] arrayDiCharDaCifrare = (codiceNumerico + ": " + testo).toCharArray();
		if(chiave.length() > 2)
			chiave = chiave.substring(0, 2);
		byte[] arrayDiByteDaCifrare = new byte[arrayDiCharDaCifrare.length];

		for(int i = 0; i < arrayDiCharDaCifrare.length;i++) 
			arrayDiByteDaCifrare[i] = (byte) arrayDiCharDaCifrare[i];

		char[] chiaveInChar = chiave.toCharArray();
		byte[] chiaveInByte = new byte[chiaveInChar.length];

		for(int i = 0; i < chiaveInChar.length;i++)
			chiaveInByte[i] = (byte) chiaveInChar[i];

		byte[] arrayDiByteCifrato = new byte[arrayDiCharDaCifrare.length];

		/*
		 * Inizio della cifratura
		 * Il ciclo for dura fintantoché  ogni lettera verrà cifrata
		 */
		int momentum;
		for (int i = 0; i < arrayDiByteDaCifrare.length; i++) { 
			momentum = (int) arrayDiByteDaCifrare[i] + (int) chiaveInByte[i % chiaveInByte.length];

			if(arrayDiByteDaCifrare[i] == ' ')
				arrayDiByteCifrato[i] = (byte) 33;
			else
				arrayDiByteCifrato[i] = (byte) momentum;
		}

		messaggioCifrato = new String(arrayDiByteCifrato, 0, arrayDiByteCifrato.length).trim();

		System.out.println("Messaggio cifrato: " + messaggioCifrato);

		return messaggioCifrato;
	}
}
