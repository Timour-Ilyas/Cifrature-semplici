/**
 ** Nome programma: SecretSender
 ** Versione programma: 1.0
 ** Data di inizio: 24/10/21
 ** Autore: Timour Ilyas, Bresci Bernardo, Capecchi Matteo
 ** Problema: Sviluppare un programma che cifra una stringa contenente un messaggio da
			  inviare ad un determinato destinatario, specificato dai dati dell'ip e della
			  porta inseriti dall'utente
 ** Dati
 ** In input: Messaggio da inviare, Codice dell'agente, Chiave, metodo di cifratura

 ** in output: Messaggio cifrato

 ** Osservazioni: Il programma contiene 5 classi
				  Il programma utilizza socket UDP, quindi non è certo che il messaggio
				  arrivi al destinatario, è stato pensato un metodo per accertarsi che
				  il messaggio sia arrivato al destinatario
 */
package secretSender;

public class SecretSender {
	public static void main(String[] args) {
		SenderWindow obj = new SenderWindow();
		obj.setVisible(true);
		System.out.println("SecretSender Attivato");
	}
}
