/*
 * La classe gestisce la socket dell'InboxSender
 */
package secretInbox;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class InboxSocket {

	/*
	 *  La variabile socket che comunicherà con il programma Sender
	 */
	private static DatagramSocket socket;
	/*
	 * La porta della propria socket aperta, la variabile si usa unicamente nel costruttore
	 */
	private int portaPropria = 50000;
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
	private byte buf[] = new byte[512];
	private DatagramPacket pacchettoDaRicezione = new DatagramPacket(buf,buf.length);
	private String messaggioRicevuto;
	
	public static boolean accettazione = true;

	public InboxSocket() throws SocketException {
		socket = new DatagramSocket(portaPropria);
		setMsg("ok");
	}

	/*
	 * Getter e setter
	 */
	public String getMessaggioRicevuto() {
		return messaggioRicevuto;
	}
	public void setMessaggioRicevuto(String messaggioRicevuto) {
		this.messaggioRicevuto = messaggioRicevuto;
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
	 * Metodo che riceve il messaggio dal mittente
	 */
	public void riceviMessaggio() {
		try 
		{
			for(int k = 0; k < buf.length; k++) buf[k] = 0;
			pacchettoDaRicezione  = new DatagramPacket(buf,buf.length);
			socket.receive(pacchettoDaRicezione);

			messaggioRicevuto = new String(pacchettoDaRicezione.getData(), 0, pacchettoDaRicezione.getData().length).trim();

			setIp(pacchettoDaRicezione.getAddress());
			setPorta(pacchettoDaRicezione.getPort());
			
			dp = new DatagramPacket(msg.getBytes(),msg.getBytes().length,ip,porta);

			socket.send(dp);
		} catch (IOException e) {}
	}
	
	/*
	 * Metodo che chiude il thread della socket che rimane in ascolto
	 */
	public static void terminaSocket() {
		socket.close();
		System.out.println("Termine programma");
		accettazione = false;
		System.exit(0);
	}//Chiusura metodo di terminazione socket
}
