package secretSender;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private int x = 300, y = 10, larghezza = 1000, lunghezza = 800;

	public IpPortWindow() {
		super("Dati del Quartier Generale");
		initComponentsIpPorta();
		setResizable(false);
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

		x = 300; y = 10; larghezza = 400; lunghezza = 200;

		setBounds(x, y, larghezza, lunghezza);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		scrittaInformativa.setText("Inserisci l'IP e la Porta del Quartier Generale");
		scrittaIpVicino.setText("Indirizzo Ip: ");
		scrittaPortaVicino.setText("Porta: ");
		pulsantePositivo.setText("Ok");
		pulsanteNegativo.setText("Annulla");

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
	 * Devo rendere inuscibile dalla finestra e titolo rosso
	 */
	@Override
	public void actionPerformed(ActionEvent listener) {
		if(pulsantePositivo == listener.getSource()) {

		}

		if(pulsanteNegativo == listener.getSource()) {

		}
	}
}
