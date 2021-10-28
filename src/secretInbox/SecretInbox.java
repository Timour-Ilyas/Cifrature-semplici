/**
 ** Nome programma: SecretInbox
 ** Versione programma: 1.0
 ** Data di inizio: 24/10/21
 ** Autore: Timour Ilyas, Bresci Bernardo, Capecchi Matteo
 ** Problema: Sviluppare un programma che decifra un messaggio arrivato da un SecretSender
			  e stampare i risultati ottenuti

 ** Dati
 ** In input: Messaggio cifrato

 ** in output: Messaggio decifrato

 ** Osservazioni: Il programma contiene 3 classi
				  Dato che il programma riceve il messaggio utilizzando socket UDP,
				  dopo aver ricevuto il messaggio, invia un accertamento
 */
package secretInbox;

public class SecretInbox {
	public static void main(String[] args) {
		InboxWindow obj = new InboxWindow();
		obj.setVisible(true);
		System.out.println("SecretInbox Attivato");
	}
}
