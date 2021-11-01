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

public class SenderSocket {
	
	/*
	 * La variabile socket che comunicherą con il programma Inbox
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
	 * Variabile DatagramPacket che conterrą tutti i dati soprastanti necessari alla comunicazione
	 */
	private DatagramPacket dp;
	/*
	 * Variabili utilizzate per ricevere il messaggio di risposta dal destinatario
	 */
	private byte buf[] = new byte[64];
	private DatagramPacket pacchettoDaRicezione  = new DatagramPacket(buf,buf.length);
	private String risposta;


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
		try 
		{
			dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,ip,porta);

			socket.send(dp);
			
			socket.receive(pacchettoDaRicezione);
			
			risposta = new String(pacchettoDaRicezione.getData(), 0, pacchettoDaRicezione.getData().length);
			
		} catch (IOException e) {
			Logger.getLogger(SenderSocket.class.getName()).log(Level.SEVERE, null, e);			
		}
		
	}
}
