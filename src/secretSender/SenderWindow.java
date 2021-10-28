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
import javax.swing.JOptionPane;
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

	private Cifratore macchinaCifratrice  = new Cifratore();;

	private SenderSocket s;

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

		/*
		 * Pulsante necessario per impostare i dati per inviare il messaggio al
		 * destinatario
		 */
		if(pulsanteDiConnessione == listener.getSource()) {
			piccolaFinestraDiInput = new IpPortWindow();
			piccolaFinestraDiInput.setVisible(true);
		}

		/*
		 * Quando il pulsante d'invio viene cliccato ci sono prima delle condizioni necessarie per
		 * inviare il messaggio al destinatario

		 * Condizioni:
		 * 1: Il messaggio deve essere minimo 1 carattere e massimo 512 caratteri
		 * 2: Il codice dell'agente deve essere solo ed esclusivamente di 4 cifre numeriche
		 * 3: La chiave deve essere coerente con il metodo di cifratura
		 * 	  		Se il metodo di cifratura è quella di Cesare
		 * 			la chiave deve essere un numero maggiore di 1
		 * 
		 * 			Se il metodo di cifratura è quello di Vigenère
		 * 			la chiave deve essere una stringa di caratteri
		 * 4: L'indirizzo ip e la porta del destinatario siano stati inseriti
		 * 	  		Verificare la correttezza dell'indirizzo IP
		 * 			Verifica che la porta sia un valore compreso tra 1024 e 65535
		 */
		if(invioMessaggio == listener.getSource() && metodoDiCifratura.getSelectedIndex() == 0) {			
			testoDaCifraCampo.setEditable(false);
			codiceNumericoCampo.setEditable(false);
			chiaveCampo.setEditable(false);
			metodoDiCifratura.setEnabled(false);
			pulsanteDiConnessione.setEnabled(false);
			invioMessaggio.setEnabled(false);

			/*
			 * Controllo che il codice dell'agente contenga solo cifre
			 */
			boolean correttezzaDellInseritoCodice = true;
			for(int i = 0; i < 10;i++) {
				if(
						!(codiceNumericoCampo.getText().contains(Integer.toString((int) Integer.toString(i).charAt(i))))
						)
				{
					correttezzaDellInseritoCodice = false;
				}
			}

			//Se il testo non è vuoto)
			if(!testoDaCifraCampo.getText().isBlank()){//Se il testo non è vuoto
				if(Integer.parseInt(testoDaCifraCampo.getText()) <= 512){//Se il testo è minore o ugale a 512 caratteri
					if(codiceNumericoCampo.getText().length() == 4){//Se il numero di cifre del codice agente è uguale a 4
						if(Integer.parseInt(chiaveCampo.getText()) > 1){//Se la chiave è maggiore di 1
							if(correttezzaDellInseritoCodice){//Se la stringa contiene solo cifre								
								/*
								 * Cifratura del messaggio
								 */
								macchinaCifratrice.cifraturaDiCesare(testoDaCifraCampo.getText(), codiceNumericoCampo.getText(), Integer.parseInt(chiaveCampo.getText()));

								System.out.println("Cifratura del messaggio completata, metodo di Cesare");

								if(s.getIp() == null || s.getPorta() == 0) {
									try {
										s = new SenderSocket();
									} catch (SocketException e) {
										e.printStackTrace();
									}

									/*
									 * GOOD ENDING
									 * Tutte le condizioni sono andate a buon fine, adesso si procede per l'invio
									 * del messaggio
									 */
									System.out.println("Invio del messaggio al destinatario specificato");
									s.invioMessaggio("");
								}else {									
									JOptionPane.showMessageDialog(null, "Devi inserire un indirizzo ip e una porta", "Errore", JOptionPane.ERROR_MESSAGE);
								}
							}else {
								JOptionPane.showMessageDialog(null, "La stringa deve contenere solo cifre", "Errore", JOptionPane.ERROR_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(null, "La chiave deve essere maggiore di 1", "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Il codice dell'agente può essere solo di 4 cifre", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Il messaggio è troppo lungo", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Non hai inserito alcun messaggio", "Errore", JOptionPane.ERROR_MESSAGE);
			}

			testoDaCifraCampo.setEditable(true);
			codiceNumericoCampo.setEditable(true);
			chiaveCampo.setEditable(true);
			metodoDiCifratura.setEnabled(true);
			pulsanteDiConnessione.setEnabled(true);
			invioMessaggio.setEnabled(true);
		}//Chiusura ActionListener del tasto invio (metodo di Cesare)

		if(invioMessaggio == listener.getSource() && metodoDiCifratura.getSelectedIndex() == 1) {
			testoDaCifraCampo.setEditable(false);
			codiceNumericoCampo.setEditable(false);
			chiaveCampo.setEditable(false);
			metodoDiCifratura.setEnabled(false);
			pulsanteDiConnessione.setEnabled(false);
			invioMessaggio.setEnabled(false);

			System.out.println("Cifratura del messaggio completata, metodo di Vigenère");

		}//Chiusura ActionListener del tasto invio (metodo di Vigenère)
	}//Chiusura metodo degli ActionListener
}
