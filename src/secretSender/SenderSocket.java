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
	 * Variabili per la gestione della comunicazione attraverso le socket
	 */
	private static DatagramSocket socket;
	private DatagramPacket pacchettoDaRicezione;
	private DatagramPacket pacchettoDaRicezioneSecondario;
	private DatagramPacket pacchettoDaRicezioneTerziario;
	private String risposta;
	private InetAddress ip;
	private int porta = 0;
	private String msg;
	private DatagramPacket dp;
	private int portaPropria = 50001;

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
		} catch (IOException e) {
			Logger.getLogger(SenderSocket.class.getName()).log(Level.SEVERE, null, e);			
		}
	}
}
