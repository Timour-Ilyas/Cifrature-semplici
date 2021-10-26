/*
 * Classe finestra della SecretWindow
 */
package secretSender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.net.SocketException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class SenderWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili per l'intefaccia grafica
	 */
	private int x = 300, y = 10, larghezza = 1000, lunghezza = 800;
	private JPanel superioreTitolo;
	private JPanel pannelloPerTestoDaCifrare;
	private JPanel pannelloFrattoTre;
	private JPanel inferioreInput;
	private JPanel pannelloSubInferiore;

	private JLabel titolo;

	private JTextPane testoDaCifraCampo;

	private JLabel contatoreCaratteri;

	private JLabel scrittaPerCodiceNumerico;

	private JTextField codiceNumericoCampo;

	private JLabel scrittaPerChiave;

	private JTextField chiaveCampo;

	private JComboBox<String> metodoDiCifratura;


	private JButton pulsanteDiConnessione;

	private JButton invioMessaggio;

	private IpPortWindow piccolaFinestraDiInput;

	SenderSocket s;

	public SenderWindow() {
		super("SecretSender");
		initComponents();
	}

	private void initComponents() {
		/*
		 * Inizializzazione dei componenti
		 */
		superioreTitolo = new JPanel();
		pannelloPerTestoDaCifrare = new JPanel();
		pannelloFrattoTre = new JPanel();
		inferioreInput = new JPanel();
		pannelloSubInferiore = new JPanel();
		titolo = new JLabel();
		testoDaCifraCampo = new JTextPane();
		scrittaPerCodiceNumerico = new JLabel();
		codiceNumericoCampo = new JTextField(30);
		scrittaPerChiave = new JLabel();
		chiaveCampo = new JTextField();
		metodoDiCifratura = new JComboBox<String>();
		contatoreCaratteri = new JLabel();
		pulsanteDiConnessione = new JButton();
		invioMessaggio = new JButton();

		setBounds(x, y, larghezza, lunghezza);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		titolo.setText("SecretSender");
		titolo.setFont(new Font("Bold",70,70));
		contatoreCaratteri.setText("512");

		pulsanteDiConnessione.setText("Impostazioni connessione");
		invioMessaggio.setText("Invio messaggio");

		superioreTitolo.add(titolo);

		testoDaCifraCampo.setPreferredSize(new Dimension(400, 200));

		pannelloFrattoTre.add(testoDaCifraCampo, BorderLayout.CENTER);
		pannelloFrattoTre.add(contatoreCaratteri, BorderLayout.CENTER);

		pannelloPerTestoDaCifrare.add(pannelloFrattoTre);

		scrittaPerCodiceNumerico.setText("Codice agente ");
		scrittaPerChiave.setText("Chiave ");

		metodoDiCifratura.setModel(new DefaultComboBoxModel<>(new String[] { "Cifrario di Cesare", "Cifrario di Vigenère"}));

		codiceNumericoCampo.setPreferredSize(new Dimension(20, 50));
		chiaveCampo.setPreferredSize(new Dimension(200, 50));
		metodoDiCifratura.setPreferredSize(new Dimension(300, 50));

		inferioreInput.add(scrittaPerCodiceNumerico);
		inferioreInput.add(codiceNumericoCampo);
		inferioreInput.add(scrittaPerChiave);
		inferioreInput.add(chiaveCampo);
		inferioreInput.add(metodoDiCifratura);
		inferioreInput.add(pulsanteDiConnessione);
		inferioreInput.add(invioMessaggio);	

		pannelloSubInferiore.setLayout(new BoxLayout(pannelloSubInferiore, BoxLayout.PAGE_AXIS));
		pannelloSubInferiore.add(pannelloPerTestoDaCifrare);
		pannelloSubInferiore.add(inferioreInput);

		getContentPane().add(superioreTitolo, BorderLayout.NORTH);
		getContentPane().add(pannelloSubInferiore, BorderLayout.CENTER);

		/*
		 * Attivazione ActionListener
		 */





		int condition = JComponent.WHEN_FOCUSED;
		InputMap iMap = testoDaCifraCampo.getInputMap(condition);
		ActionMap aMap = testoDaCifraCampo.getActionMap();

		String enter = "enter";
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);
		aMap.put(enter, new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("enter pressed");
			}
		});

		testoDaCifraCampo.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent eListener) {

			}
			public void focusLost(FocusEvent eListener) {}
		});




		pulsanteDiConnessione.addActionListener(this);
		invioMessaggio.addActionListener(this);
	}

	private void keyTyped(java.awt.event.KeyEvent evt) {
		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			contatoreCaratteri.setText(Integer.toString(512 - testoDaCifraCampo.getText().length()));					}
	}

	@Override
	public void actionPerformed(ActionEvent listener) {
		testoDaCifraCampo.setEditable(false);
		codiceNumericoCampo.setEditable(false);
		chiaveCampo.setEditable(false);
		metodoDiCifratura.setEnabled(false);
		pulsanteDiConnessione.setEnabled(false);
		invioMessaggio.setEnabled(false);

		if(pulsanteDiConnessione == listener.getSource()) {
			piccolaFinestraDiInput = new IpPortWindow();
			piccolaFinestraDiInput.setVisible(true);
		}

		if(invioMessaggio == listener.getSource() && metodoDiCifratura.getSelectedIndex() == 0) {
			if(!testoDaCifraCampo.getText().isBlank() || //Se il testo non è vuoto
					!codiceNumericoCampo.getText().isBlank() || //Se il numero dell'agente non è vuoto
					Integer.parseInt(chiaveCampo.getText()) > 0 //Se la chiave è maggiore di 0
					) {
				Cifratore macchinaCifratrice = new Cifratore();

				/*
				 * Cifratura del messaggio
				 */
				macchinaCifratrice.cifraturaDiCesare(testoDaCifraCampo.getText(), codiceNumericoCampo.getText(), Integer.parseInt(chiaveCampo.getText()));

				System.out.println("Cifratura del messaggio completata, metodo di Cesare");
			}
			else {

			}

			/*
			 * Condizioni di non invio del messaggio
			 * Ip uguale a null (cioè ancora non inserito)
			 * Porta uguale a 0 (la porta non può essere 0)

			if(s.getIp() == null || s.getPorta() == 0) {

			}
			else {
				try {
					s = new SenderSocket();
				} catch (SocketException e) {
					e.printStackTrace();
				}

				System.out.println("Invio del messaggio al destinatario specificato");

			}*/
			testoDaCifraCampo.setEditable(true);
			codiceNumericoCampo.setEditable(true);
			chiaveCampo.setEditable(true);
			metodoDiCifratura.setEnabled(true);
			pulsanteDiConnessione.setEnabled(true);
			invioMessaggio.setEnabled(true);
		}
		if(invioMessaggio == listener.getSource() && metodoDiCifratura.getSelectedIndex() == 1) {
			testoDaCifraCampo.setEditable(false);
			codiceNumericoCampo.setEditable(false);
			chiaveCampo.setEditable(false);
			metodoDiCifratura.setEnabled(false);
			pulsanteDiConnessione.setEnabled(false);
			invioMessaggio.setEnabled(false);

			System.out.println("Cifratura del messaggio completata, metodo di Vigenère");

		}
	}
}
