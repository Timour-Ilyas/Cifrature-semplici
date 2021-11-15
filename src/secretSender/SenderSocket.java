/*

 * La classe gestisce la socket del SecretSender
 */
package secretSender;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SenderSocket extends Thread{

	/*
	 * La variabile socket che comunicherà con il programma Inbox
	 */
	private static DatagramSocket socket;
	/*
	 * La porta della propria socket aperta, la variabile si usa unicamente nel costruttore
	 */
	private int portaPropria = 50001;
	/*
	 * Dati necessari alla comunicazione, il messaggio da inviare, l'ip e la porta
	 */
	private String msg;
	private InetAddress ip;
	private int porta = 0;
	/*
	 * Variabile DatagramPacket che conterrà tutti i dati soprastanti necessari alla comunicazione
	 */
	private DatagramPacket dp;
	/*
	 * Variabili utilizzate per ricevere il messaggio di risposta dal destinatario
	 */
	private byte buf[] = new byte[64];
	private DatagramPacket pacchettoDaRicezione;
	private String risposta = "null";

	private boolean ripetizione = true;

	public SenderSocket() throws SocketException {
		socket = new DatagramSocket(portaPropria);
	}

	/*
	 * Getter e setter
	 */
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/*
	 * Metodo che invia il messaggio al destinatario specificato dai dati inseriti
	 * Se non sono stati impostati i dati, non viene utilizzato il metodo
	 */
	public void invioMessaggio() {
		try {
			dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,ip,porta);
			socket.send(dp);
		} catch (IOException e) {
			Logger.getLogger(SenderSocket.class.getName()).log(Level.SEVERE, null, e);			
		}
	}
	
	/*
	 * Viene avviato un thread che rimane sempre attivo in ascolto ad attendere l'arrivo di un messaggio
	 * La socket attiva che ascolta viene chiusua dall'interno in modo che alla chiusura del programma la socket non si ancora aperta
	 */
	public void run() {
		while(ripetizione) {
			try {
				for(int k = 0; k < buf.length; k++) buf[k] = 0;
				pacchettoDaRicezione  = new DatagramPacket(buf,buf.length);
				socket.receive(pacchettoDaRicezione);
				risposta = new String(pacchettoDaRicezione.getData(), 0, pacchettoDaRicezione.getData().length).trim();
				
				if(!risposta.contains("termina")){
					System.out.println("Il messaggio è arrivato al destinatario");
				}
				if(risposta.contains("termina")){
					socket.close();
					System.out.println("Termine programma");
					System.exit(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}//Chiusura Try Catch
		}//Chiusura ciclo while
	}//Chiusura metodo run
	
	/*
	 * Metodo che chiude il thread della socket che rimane in ascolto
	 */
	public void terminaSocket() {
		try {
			String termina = "termina";
			dp = new DatagramPacket(termina.getBytes(),termina.getBytes().length,InetAddress.getByName("Localhost") , socket.getLocalPort());
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ripetizione = false;
	}//Chiusura metodo di terminazione socket
}