/*
 * Classe finestra della SecretWindow
 */
package secretSender;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

public class SenderWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili per l'intefaccia grafica
	 */
	private int x = 300, y = 10, larghezza = 1000, lunghezza = 800;

	private JLabel titolo;
	private JScrollPane paneScroller;
	private JTextPaneLimit testoDaCifraCampo;
	private JLabel contatoreCaratteri;
	private JLabel scrittaPerCodiceNumerico;
	private JTextField codiceNumericoCampo;
	private JLabel scrittaPerChiave;
	private JTextField chiaveCampo;
	private JComboBox<String> metodoDiCifratura;
	private JButton pulsanteDiConnessione;
	private JButton invioMessaggio;
	private JLabel sfondo;
	private JButton pulsanteArchivio;

	/*
	 * Variabili di cifratura e connessione socket
	 */
	private Cifratore macchinaCifratrice  = new Cifratore();

	private SenderSocket s;

	/*
	 * Variabile per non aprire molteplici volte la finestra di ip o di archivio
	 */
	public static boolean accertamentoSullaFinestra = true;

	/*
	 * Piccole finestre secondarie
	 */
	private IpPortWindow piccolaFinestraDiInput;
	private ArchivioSender as = new ArchivioSender();

	/*
	 * Costruttore in cui 
	 * 		Si cambia il nome della finestra
	 * 		Si imposta la finestra 
	 * 		Si inizializza la socket
	 */
	public SenderWindow() throws SocketException {
		super("SecretSender");
		initComponents();

		/*
		 * Chiamata costruttore della Socket
		 * 	Start del Thread di ascolto
		 */
		s = new SenderSocket();
		s.start();

		/*
		 * Fare in modo che quando il programma termina viene chiusa la socket
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				s.terminaSocket();
				dispose();
			}
		});
	}

	/*
	 * Metodo per inserire i componenti nella finestra
	 */
	private void initComponents() {
		/*
		 * Inizializzazione dei componenti
		 */
		titolo = new JLabel();
		scrittaPerCodiceNumerico = new JLabel();
		codiceNumericoCampo = new JTextField(3);
		scrittaPerChiave = new JLabel();
		chiaveCampo = new JTextField();
		metodoDiCifratura = new JComboBox<String>();
		contatoreCaratteri = new JLabel();
		pulsanteDiConnessione = new JButton();
		invioMessaggio = new JButton();
		sfondo = new JLabel();
		pulsanteArchivio = new JButton();
		paneScroller = new JScrollPane();
		testoDaCifraCampo = new JTextPaneLimit();

		setBounds(x, y, larghezza, lunghezza);

		getContentPane().setLayout(null);

		pulsanteArchivio.setText("Archivio Dati");

		getContentPane().add(pulsanteArchivio);
		pulsanteArchivio.setBounds(860, 680, 110, 30);

		pulsanteDiConnessione.setFont(new java.awt.Font("Tahoma", 0, 14));
		pulsanteDiConnessione.setText("Impostazioni connessione");

		getContentPane().add(pulsanteDiConnessione);
		pulsanteDiConnessione.setBounds(320, 620, 210, 30);

		invioMessaggio.setFont(new java.awt.Font("Tahoma", 0, 14));
		invioMessaggio.setText("Invio messaggio");

		getContentPane().add(invioMessaggio);
		invioMessaggio.setBounds(580, 620, 150, 30);

		paneScroller.setViewportView(testoDaCifraCampo);

		getContentPane().add(paneScroller);
		paneScroller.setBounds(300, 140, 430, 180);

		getContentPane().add(codiceNumericoCampo);
		codiceNumericoCampo.setBounds(530, 370, 40, 30);
		codiceNumericoCampo.setDocument(new JTextLimit(4));
		getContentPane().add(chiaveCampo);
		chiaveCampo.setBounds(470, 440, 100, 30);
		chiaveCampo.setDocument(new JTextLimit(5));

		scrittaPerChiave.setFont(new java.awt.Font("Tahoma", 0, 17));
		scrittaPerChiave.setText("Chiave:");
		getContentPane().add(scrittaPerChiave);
		scrittaPerChiave.setBounds(410, 440, 90, 30);

		scrittaPerCodiceNumerico.setFont(new java.awt.Font("Tahoma", 0, 17));
		scrittaPerCodiceNumerico.setText("Codice agente:");
		getContentPane().add(scrittaPerCodiceNumerico);
		scrittaPerCodiceNumerico.setBounds(410, 370, 120, 30);

		getContentPane().add(metodoDiCifratura);
		metodoDiCifratura.setBounds(440, 510, 210, 40);
		metodoDiCifratura.setModel(new DefaultComboBoxModel<>(new String[] { "Cifrario di Cesare", "Cifrario di Vigenère"}));

		titolo.setFont(new java.awt.Font("Tahoma", 1, 70));
		titolo.setText("SecretSender");
		titolo.setPreferredSize(new java.awt.Dimension(80, 20));
		getContentPane().add(titolo);
		titolo.setBounds(270, 0, 480, 110);

		contatoreCaratteri.setFont(new java.awt.Font("Tahoma", 0, 13));
		contatoreCaratteri.setText("512");
		contatoreCaratteri.setForeground(Color.BLUE);
		getContentPane().add(contatoreCaratteri);
		contatoreCaratteri.setBounds(740, 300, 40, 20);

		sfondo.setIcon(new javax.swing.ImageIcon("src/secretSender/Immagini/SfondoSender.png"));
		sfondo.setText("jLabel3");
		getContentPane().add(sfondo);
		sfondo.setBounds(0, 0, 1000, 800);

		/*
		 * Attivazione KeyListener e ActionListener
		 */
		int condition = JComponent.WHEN_FOCUSED;
		InputMap iMap = testoDaCifraCampo.getInputMap(condition);
		ActionMap aMap = testoDaCifraCampo.getActionMap();

		String key = "";
		for(int i = 32; i <= 127; i++) {
			iMap.put(KeyStroke.getKeyStroke(i, 0), key);
		}
		/*
		 * La cancellazione e l'incollatura non funzionano nonostante siano passati come parametri
		 */
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_CANCEL, 0), key);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PASTE, 0), key);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.CHAR_UNDEFINED, 0), key);

		aMap.put(key, new AbstractAction() {
			private static final long serialVersionUID = 1917L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int numeroDimostrativo= 511 - testoDaCifraCampo.getText().length();
				if(numeroDimostrativo == -1)
					numeroDimostrativo = 0;
				contatoreCaratteri.setText(Integer.toString(numeroDimostrativo));
				if(numeroDimostrativo > 200)
					contatoreCaratteri.setForeground(Color.BLUE);
				else if(numeroDimostrativo >= 50)
					contatoreCaratteri.setForeground(Color.YELLOW);
				else
					contatoreCaratteri.setForeground(Color.RED);

			}
		});
		pulsanteDiConnessione.addActionListener(this);
		invioMessaggio.addActionListener(this);
		pulsanteArchivio.addActionListener(this);
	}
	
	/*
	 * Parte per far funzionare i pulsanti
	 */
	@Override
	public void actionPerformed(ActionEvent listener) {
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
		 * 			la chiave non deve essere un numero negativo
		 * 
		 * 			Se il metodo di cifratura è quello di Vigenère
		 * 			la chiave deve essere una stringa di caratteri
		 * 4: L'indirizzo ip e la porta del destinatario siano stati inseriti
		 * 	  		Verificare la correttezza dell'indirizzo IP
		 * 			Verifica che la porta sia un valore compreso tra 1024 e 65535
		 */
		if(invioMessaggio == listener.getSource() && metodoDiCifratura.getSelectedIndex() == 0) {	
			//Viene messo tutto a false per non generare errori
			testoDaCifraCampo.setEditable(false);
			codiceNumericoCampo.setEditable(false);
			chiaveCampo.setEditable(false);
			metodoDiCifratura.setEnabled(false);
			pulsanteDiConnessione.setEnabled(false);
			invioMessaggio.setEnabled(false);

			if(!testoDaCifraCampo.getText().isEmpty()){//Se il testo non è vuoto
				if(!codiceNumericoCampo.getText().isEmpty()){//Se il codice agente non è vuoto
					if(osservatoreSpeciale(codiceNumericoCampo.getText())){//Se il codice agente contiene solo cifre
						if(codiceNumericoCampo.getText().trim().length() == 4){//Se il codice agente è composta da 4 cifre
							if(!chiaveCampo.getText().isEmpty()){//Se la chiave non è vuota
								if(osservatoreSpeciale(chiaveCampo.getText())){//Se la chiave contiene solo cifre
									if(Integer.parseInt(chiaveCampo.getText()) > -1){//Se la chiave non è negativa
										/*
										 * Cifratura del messaggio
										 */
										s.setMsg(macchinaCifratrice.cifraturaDiCesare(testoDaCifraCampo.getText(), codiceNumericoCampo.getText(), Integer.parseInt(chiaveCampo.getText())));

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

											/*
											 * Attesa di 2 secondi per osservare se il messaggio arriva al destinatario
											 */
											try {
												TimeUnit.SECONDS.sleep(2);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}

											if(s.getRisposta() == "null") {
												JOptionPane.showMessageDialog(null, "L'utente non esiste o non è attualmente raggiungibile", "Problema di connessione", JOptionPane.WARNING_MESSAGE);
												System.out.println("Non è possibile raggiungere il destinatario");
												s.setRisposta("null");
											}
										}

										/*
										 * Dopo che è stato effettuata la cifrata e l'invio del messaggio
										 * Qualunque sia l'esisto:
										 * 		Viene salvato il messaggio nell'archivio
										 */
										as.aggiungiMessaggioArchivio(codiceNumericoCampo.getText() + ": " + testoDaCifraCampo.getText());
									}else {
										JOptionPane.showMessageDialog(null, "Inserisci una chiave maggiore di 0", "Errore", JOptionPane.ERROR_MESSAGE);
									}
								}else {
									JOptionPane.showMessageDialog(null, "La chiave con il metodo di Cesare deve essere un numero", "Errore", JOptionPane.ERROR_MESSAGE);
								}
							}else {
								JOptionPane.showMessageDialog(null, "Non inserito una chiave", "Errore", JOptionPane.ERROR_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Il codice agente deve essere composto da 4 cifre", "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Il codice agente deve contenere solo cifre", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Non hai inserito il tuo codice agente", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Non hai inserito alcun messaggio", "Errore", JOptionPane.ERROR_MESSAGE);
			}
			
			//I componenti si riattivano
			testoDaCifraCampo.setEditable(true);
			codiceNumericoCampo.setEditable(true);
			chiaveCampo.setEditable(true);
			metodoDiCifratura.setEnabled(true);
			pulsanteDiConnessione.setEnabled(true);
			invioMessaggio.setEnabled(true);
		}//Chiusura ActionListener del tasto invio (metodo di Cesare)

		/*
		 * Metodo di cifratura vigenère
		 * Le condizioni necessarie sono scritte nel commento precedente alla cifratura di Cesare
		 */
		if(invioMessaggio == listener.getSource() && metodoDiCifratura.getSelectedIndex() == 1) {
			testoDaCifraCampo.setEditable(false);
			codiceNumericoCampo.setEditable(false);
			chiaveCampo.setEditable(false);
			metodoDiCifratura.setEnabled(false);
			pulsanteDiConnessione.setEnabled(false);
			invioMessaggio.setEnabled(false);

			if(!testoDaCifraCampo.getText().isEmpty()){//Se il testo non è vuoto
				if(!codiceNumericoCampo.getText().isEmpty()){//Se il codice agente non è vuoto
					if(osservatoreSpeciale(codiceNumericoCampo.getText())){//Se il codice agente contiene solo cifre
						if(codiceNumericoCampo.getText().trim().length() == 4){//Se il codice agente è composta da 4 cifre
							if(!chiaveCampo.getText().isEmpty()){//Se la chiave non è vuota
								if(osservatoreMegaSpeciale(chiaveCampo.getText())){//Se la chiave contiene solo lettere
									/*
									 * Cifratura del messaggio
									 */
									s.setMsg(macchinaCifratrice.cifraturaDiVigenère(testoDaCifraCampo.getText(), codiceNumericoCampo.getText(), chiaveCampo.getText()));

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

										/*
										 * Attesa di 2 secondi per osservare se il messaggio arriva al destinatario
										 */
										try {
											TimeUnit.SECONDS.sleep(2);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}

										if(s.getRisposta() == "null") {
											JOptionPane.showMessageDialog(null, "L'utente non esiste o non è attualmente raggiungibile", "Problema di connessione", JOptionPane.WARNING_MESSAGE);
											System.out.println("Non è possibile raggiungere il destinatario");
											s.setRisposta("null");
										}
									}

									/*
									 * Dopo che è stato effettuata la cifrata e l'invio del messaggio
									 * Qualunque sia l'esisto:
									 * 		Viene salvato il messaggio nell'archivio
									 */
									as.aggiungiMessaggioArchivio(codiceNumericoCampo.getText() + ": " + testoDaCifraCampo.getText());
								}else {
									JOptionPane.showMessageDialog(null, "La chiave con il metodo di Vigenère deve contenere solo cifre", "Errore", JOptionPane.ERROR_MESSAGE);
								}
							}else {
								JOptionPane.showMessageDialog(null, "Non inserito una chiave", "Errore", JOptionPane.ERROR_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Il codice agente deve essere composto da 4 cifre", "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(null, "Il codice agente deve contenere solo cifre", "Errore", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Non hai inserito il tuo codice agente", "Errore", JOptionPane.ERROR_MESSAGE);
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
		}//Chiusura ActionListener del tasto invio (metodo di Vigenère)

		/*
		 * Pulsante necessario per impostare i dati in modo da inviare il messaggio al destinatario
		 */
		if(pulsanteDiConnessione == listener.getSource()) {
			piccolaFinestraDiInput = new IpPortWindow();
			if(accertamentoSullaFinestra) {
				piccolaFinestraDiInput.setVisible(true);
				accertamentoSullaFinestra = false;
			}
		}

		if(pulsanteArchivio == listener.getSource()) {	
			/*
			 * Se la finestra è stata già aperta non apre un'altra finestra ma mette il focus su quella già aperta
			 */
			if(accertamentoSullaFinestra) {
				as.setVisible(true);
				accertamentoSullaFinestra = false;
			}
		}
	}//Chiusura metodo degli ActionListener

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

	/*
	 * Metodo utilizzato per il controllo della chiave nella cifratura di Vigenère
	 * Per assicurarsi che contenga solo lettere
	 */
	private static boolean osservatoreMegaSpeciale(String stringa) {
		for(int i = 0; i < stringa.length(); i++) {
			for(int j = 0; j < 9; j++) {
				if(stringa.charAt(i) == Integer.toString(j).charAt(0)) {
					return false;
				}
			}
		}

		return true;
	}
}
