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
		 * Inizio della cifratura
		 * Il ciclo for dura fintantoché  ogni lettera verrà cifrata
		 */
		int momentum;
		for (int i = 0; i < arrayDiByteDaCifrare.length; i++) {
			momentum = (int) arrayDiByteDaCifrare[i] + chiave;
			if(arrayDiByteDaCifrare[i] == ' ')
				arrayDiByteCifrato[i] = (byte) 33;
			else
				arrayDiByteCifrato[i] = (byte) momentum;
		}

		messaggioCifrato = new String(arrayDiByteCifrato, 0, arrayDiByteCifrato.length).trim();

		//Stampa del messaggio cifrato
		System.out.println("Messaggio cifrato: " + messaggioCifrato);

		return messaggioCifrato;
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
