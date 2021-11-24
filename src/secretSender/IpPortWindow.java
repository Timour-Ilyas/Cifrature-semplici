/*
 * Classe che serve per creare una piccola finestra che verr� chiamata dal SenderWindow
 * Questa classe serve per impostare l'indirizzo ip e la porta della Socket a cui ci si
 * vuole connettere
 */
package secretSender;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IpPortWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili per l'intefaccia grafica
	 */
	private JPanel superioreTitolo;
	private JPanel pannelloCentrale;
	private JPanel pannelloIp;
	private JPanel pannelloPorta;
	private JPanel pannelloPulsanti;
	private JLabel scrittaInformativa;
	private JLabel scrittaIpVicino;
	private JLabel scrittaPortaVicino;
	private JTextField ipCampo;
	private JTextField portaCampo;
	private JButton pulsantePositivo;
	private JButton pulsanteNegativo;

	private int x = 300, y = 50, larghezza = 1000, lunghezza = 800;

	/*
	 * Variabili provvisorie di scambio
	 */
	private InetAddress ip;
	private Integer porta;

	public IpPortWindow() {
		super("Dati del Quartier Generale");
		initComponentsIpPorta();
		setResizable(false);
		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				SenderWindow.accertamentoSullaFinestra = true;
				dispose();
			}
		});
	}

	private void initComponentsIpPorta() {
		/*
		 * Inizializzazione dei componenti
		 */
		superioreTitolo = new JPanel();
		pannelloCentrale = new JPanel();
		pannelloIp = new JPanel();
		pannelloPorta = new JPanel();
		pannelloPulsanti = new JPanel();
		scrittaInformativa = new JLabel();
		scrittaIpVicino = new JLabel();
		scrittaPortaVicino = new JLabel();
		ipCampo = new JTextField();
		portaCampo = new JTextField();
		pulsantePositivo = new JButton();
		pulsanteNegativo = new JButton();

		x = 300; y = 10; larghezza = 450; lunghezza = 150;

		setBounds(x, y, larghezza, lunghezza);

		scrittaInformativa.setText("Inserisci l'IP e la Porta del Quartier Generale");
		scrittaInformativa.setFont(new Font("Arial",17, 17));
		scrittaInformativa.setForeground(Color.RED);

		scrittaIpVicino.setText("Indirizzo Ip: ");
		scrittaPortaVicino.setText("Porta: ");
		pulsantePositivo.setText("Ok");
		pulsanteNegativo.setText("Annulla");

		/*
		 * La dimensione dei campi � stata ottimizzata per l'inserimento di un indirizzo IpV4
		 * dato che si possono facilmente vedere tutti e 15 i caratteri, ma non per questo pu�
		 * non pu� essere inserito l'indirizzo Ipv6, il programma lo permette
		 */
		ipCampo.setPreferredSize(new Dimension(100, 25));
		portaCampo.setPreferredSize(new Dimension(40, 25));

		/*
		 * Viene imposto un limite di caratteri che si possono inserire in nella casella di testo
		 * 		Nella casella dell'ip � 39 perch� gli indirizzi IpV6 sono composti al massimo da 39 cifre
		 * 		nel caso ad essere inserito fosse l'indirizzo IpV4 sarebbero necessarie solo 15 caratteri
		 */
		ipCampo.setDocument(new JTextLimit(39));
		portaCampo.setDocument(new JTextLimit(5));

		superioreTitolo.add(scrittaInformativa);

		pannelloIp.add(scrittaIpVicino);
		pannelloIp.add(ipCampo);

		pannelloPorta.add(scrittaPortaVicino);
		pannelloPorta.add(portaCampo);

		pannelloCentrale.add(pannelloIp);
		pannelloCentrale.add(pannelloPorta);

		pannelloPulsanti.add(pulsantePositivo);
		pannelloPulsanti.add(pulsanteNegativo);

		getContentPane().add(superioreTitolo, BorderLayout.NORTH);
		getContentPane().add(pannelloCentrale, BorderLayout.CENTER);
		getContentPane().add(pannelloPulsanti, BorderLayout.SOUTH);

		/*
		 * Attivazione ActionListener
		 */
		pulsantePositivo.addActionListener(this);
		pulsanteNegativo.addActionListener(this);
	}

	/*
	 * Con il metodo restituisci possiamo estrapolare un dato inserito dall'utente in base alla richiesta che forniamo
	 * L'overloading ci permette di fare ci� con un singolo metodo
	 * In base al parametro di entrata si avranno gli output richiesti
	 */
	public InetAddress restituisci(InetAddress ip) {
		return this.ip;
	}

	public int restituisci(int porta) {
		return this.porta;
	}

	@Override
	public void actionPerformed(ActionEvent listener) {
		/*
		 * Il pulsante ok permette di salvare l'indirizzo ip e la porta inseriti dall'utente
		 */
		if(pulsantePositivo == listener.getSource()) {

			if(ipCampo.getText().length() > 0) {//Se nel campo dell'ip c'� scritto qualcosa
				if(portaCampo.getText().length() >0) {//Se nel campo della porta c'� scritto qualcosa
					/*
					 * Inseriamo i valori dei campi nelle variabili provvisorie
					 */
					try {
						ip = InetAddress.getByName(ipCampo.getText());
					} catch (UnknownHostException e) {//Se l'host non viene riconosciuto d� un messaggio di errore
						JOptionPane.showMessageDialog(null, "Host non riconosciuto", "Errore", JOptionPane.ERROR_MESSAGE);
						System.out.println("Errore: Host non riconosciuto");
					};
					try{
						if(Integer.parseInt(portaCampo.getText()) <= 65535){
							porta = Integer.parseInt(portaCampo.getText());
							/*
							 * GOOD ENDING, i dati sono stati inseriti correttamente
							 * La funzione di questa classe termina qui, la finestra si chiuse
							 */
							System.out.println("Ip: " + ip + "\nPorta: " + porta);
							dispose();
						}else {
							porta = null;
							JOptionPane.showMessageDialog(null, "La porta pu� essere al massimo 65535", "Errore", JOptionPane.ERROR_MESSAGE);
							System.out.println("Errore: La porta pu� essere al massimo 65535");
						}
					}catch(NumberFormatException evtExp) {
						JOptionPane.showMessageDialog(null, "La porta deve essere un numero", "Errore", JOptionPane.ERROR_MESSAGE);
						System.out.println("La porta deve essere un numero");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Inserisci una porta", "Errore", JOptionPane.ERROR_MESSAGE);
				}//Chiusura dell'if con condizione: porta inserita o meno
			}else {
				JOptionPane.showMessageDialog(null, "Inserisci un indirizzo Ip", "Errore", JOptionPane.ERROR_MESSAGE);
			}//Chiusura dell'if con condizione: ip inserito o meno
		}//Chiusura del metodo del pulsante

		/*
		 * Il pulsante annulla esce dalla finestra di impostazione Ip e porta senza salvare nulla
		 */
		if(pulsanteNegativo == listener.getSource()) {
			SenderWindow.accertamentoSullaFinestra = true;
			ip = null;
			porta = null;
			dispose();
		}
	}
}
