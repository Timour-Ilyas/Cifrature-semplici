/*
 * Classe finestra della SecretWindow
 */
package secretSender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.SocketException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class SenderWindow extends JFrame implements ActionListener{
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

	private JTextPaneLimit testoDaCifraCampo;

	private JLabel contatoreCaratteri;

	private JLabel scrittaPerCodiceNumerico;

	private JTextField codiceNumericoCampo;

	private JLabel scrittaPerChiave;

	private JTextField chiaveCampo;

	private JComboBox<String> metodoDiCifratura;

	private JButton pulsanteDiConnessione;

	private JButton invioMessaggio;

	private IpPortWindow piccolaFinestraDiInput;

	private Cifratore macchinaCifratrice  = new Cifratore();

	private SenderSocket s;

	public SenderWindow() throws SocketException {
		super("SecretSender");
		initComponents();

		s = new SenderSocket();
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
		testoDaCifraCampo = new JTextPaneLimit();
		scrittaPerCodiceNumerico = new JLabel();
		codiceNumericoCampo = new JTextField(3);
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
		contatoreCaratteri.setForeground(Color.GREEN);

		pulsanteDiConnessione.setText("Impostazioni connessione");
		invioMessaggio.setText("Invio messaggio");

		scrittaPerCodiceNumerico.setText("Codice agente ");
		codiceNumericoCampo.setDocument(new JTextLimit(4));

		scrittaPerChiave.setText("Chiave ");

		metodoDiCifratura.setModel(new DefaultComboBoxModel<>(new String[] { "Cifrario di Cesare", "Cifrario di Vigenère"}));

		/*
		 * Inserimento componenti nel JFrame
		 */
		superioreTitolo.add(titolo);

		testoDaCifraCampo.setPreferredSize(new Dimension(400, 200));

		pannelloFrattoTre.add(testoDaCifraCampo, BorderLayout.CENTER);
		pannelloFrattoTre.add(contatoreCaratteri, BorderLayout.CENTER);

		pannelloPerTestoDaCifrare.add(pannelloFrattoTre);

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
		pulsanteDiConnessione.addActionListener(this);
		invioMessaggio.addActionListener(this);
	}

	/*
	 * Metodo utilizzato per il controllo del codice dell'agente per assicurarsi che contenga solo cifre
	 */
	private static boolean osservatoreSpeciale(String stringa) 
	{ 
		try 
		{  
			Double.parseDouble(stringa);  
			return true;
		} catch(NumberFormatException e)
		{  
			return false;  
		}  
	}

	@Override
	public void actionPerformed(ActionEvent listener) {

		/*
		 * Pulsante necessario per impostare i dati in modo da inviare il messaggio al destinatario
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
		 * 		La condizione della dimensione di massimo 512 caratteri viene gestita dal
		 * 		limite massimo di caratteri inseribili nel JTextPane (se ne occupa la classe: JTextPaneLimit)
		 * 2: Il codice dell'agente deve essere solo ed esclusivamente di 4 cifre numeriche
		 * 		(ciò viene permesso dal limite di caratteri che sono possibili inserire nel JTextField
		 * 		 grazie alla classe: JTextLimit)
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

			//Se il testo non è vuoto)
			if(!testoDaCifraCampo.getText().isBlank()){//Se il testo non è vuoto
				if(Integer.parseInt(chiaveCampo.getText()) > 1){//Se la chiave è maggiore di 1
					if(osservatoreSpeciale(codiceNumericoCampo.getText())){//Se la stringa contiene solo cifre								
						/*
						 * Cifratura del messaggio
						 */
						macchinaCifratrice.cifraturaDiCesare(testoDaCifraCampo.getText(), codiceNumericoCampo.getText(), Integer.parseInt(chiaveCampo.getText()));

						System.out.println("Cifratura del messaggio completata, metodo di Cesare");

						s.setMsg(testoDaCifraCampo.getText());

						try{
							s.setIp(piccolaFinestraDiInput.restituisci(s.getIp()));
							s.setPorta(piccolaFinestraDiInput.restituisci(s.getPorta()));
						}catch(NullPointerException e) {
							JOptionPane.showMessageDialog(null, "Devi inserire un indirizzo ip e una porta", "Errore", JOptionPane.ERROR_MESSAGE);	
						}

						if(s.getIp() != null || s.getPorta() != 0) {
							/*
							 * GOOD ENDING
							 * Tutte le condizioni sono andate a buon fine, adesso si procede per l'invio
							 * del messaggio
							 */
							s.invioMessaggio();
							System.out.println("Invio del messaggio al destinatario specificato");
						}
					}else {
						JOptionPane.showMessageDialog(null, "La stringa deve contenere solo cifre", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "La chiave deve essere maggiore di 1", "Errore", JOptionPane.ERROR_MESSAGE);
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
