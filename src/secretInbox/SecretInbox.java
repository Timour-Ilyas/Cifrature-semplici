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

 ** Osservazioni: Il programma contiene 5 classi
				  Dato che il programma riceve il messaggio utilizzando socket UDP,
				  dopo aver ricevuto il messaggio, invia un accertamento
 */
package secretInbox;
import java.net.SocketException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SecretInbox {
	public static void main(String[] args) throws SocketException {
		InboxWindow obj = new InboxWindow();
		obj.setVisible(true);
		System.out.println("SecretInbox Attivato");

		/*
		 * Dichiarazione e inizializzazione delle variabili di decifratura e connessione socket
		 */
		Decifratore macchinaDecifratrice = new Decifratore();

		InboxSocket s = new InboxSocket();
		
		/*
		 * Impostazione dell'icona della notifica
		 */
		ImageIcon iconaAgenteSegreto = new ImageIcon("src/secretInbox/Immagini/SmallSecretAgentIcon.png");
		
		//Si attende che arrivi un messaggio
		for(;;){
			s.riceviMessaggio();
			/*
			 * Messaggio arrivato
			 * Parte il timer di 2 secondi, arriva il suono e compare la notifica che dice che è arrivato un messaggio
			 */
			NotificaMessaggio.clessidra(3).start();
			NotificaMessaggio.suonoNotifica("src/secretInbox/Suoni/NotificationSoundEffect.wav");
			JOptionPane.showMessageDialog(null, "È appena arrivato un messaggio", "Nuova notifica", JOptionPane.INFORMATION_MESSAGE, iconaAgenteSegreto);
			
			System.out.println("È appena arrivato un messaggio");
			
			
		}//Fine del ciclo infinito	
	}

	

}
