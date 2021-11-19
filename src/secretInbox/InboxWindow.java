/*
 * Classe finestra della SecretInbox
 */
package secretInbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
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
import java.awt.Font;

public class InboxWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili di gestione finestra
	 */
	private int x = 300, y = 10, larghezza = 1000, lunghezza = 800;

	private JButton tastoForzaBrutale;
	private JButton tastoConverti;
	private JButton tastoArchivioDati;
	private JLabel sfondo;
	private JLabel scrittaChiave;
	private JLabel titolo;
	private JList<String> listaGraficaMessaggi;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JTextArea areaConvertiti;
	private JTextArea areaOriginali;
	private JTextField areaChiave;
	private JLabel scrittaDelCampoSinisto;
	private JLabel scrittaDelCampoDestro;
	private JComboBox<String> metodoDiCifratura;

	private int messaggioSelezionato = -1;

	/*
	 * Dichiarazione e inizializzazione della variabile di decifrazione messaggio
	 */
	private Decifratore macchinaDecifratrice = new Decifratore();

	/*
	 * Lista che contiene tutti i messaggi inviati dall'utente
	 */
	private LinkedList<String> listaDeiMessaggi = new LinkedList<String>();
	
	/*
	 * Variabile per non aprire molteplici volte la finestra di ip o di archivio
	 */
	public static boolean accertamentoSullaFinestra = true;
	
	/*
	 * Costruttore in cui 
	 * 		Si cambia il nome della finestra
	 * 		Si imposta la finestra 
	 */
	public InboxWindow() {
		super("SecretInbox");
		initComponents();

		/*
		 * Fare in modo che quando il programma termina viene chiusa la socket
		 */
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				InboxSocket.terminaSocket();
				dispose();
			}
		});
	}

	private void initComponents() {
		/*
		 * Inizializzazione dei componenti
		 */
		titolo = new JLabel();
		areaChiave = new JTextField();
		scrittaChiave = new JLabel();
		jScrollPane1 = new JScrollPane();
		areaConvertiti = new JTextArea();
		jScrollPane2 = new JScrollPane();
		areaOriginali = new JTextArea();
		tastoConverti = new JButton();
		tastoForzaBrutale = new JButton();
		jScrollPane3 = new JScrollPane();
		listaGraficaMessaggi = new JList<>();
		metodoDiCifratura = new JComboBox<>();
		tastoArchivioDati = new JButton();
		scrittaDelCampoSinisto = new JLabel();
		scrittaDelCampoDestro = new JLabel();
		sfondo = new JLabel();

		setBounds(x, y, larghezza, lunghezza);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		titolo.setFont(new Font("Tahoma", 1, 70)); 
		titolo.setText("SecretInbox");
		titolo.setToolTipText("");
		titolo.setPreferredSize(new java.awt.Dimension(80, 20));
		getContentPane().add(titolo);
		titolo.setBounds(270, 0, 480, 110);

		getContentPane().add(areaChiave);
		areaChiave.setBounds(480, 480, 210, 30);

		scrittaChiave.setFont(new Font("Tahoma", 0, 17)); 
		scrittaChiave.setText("Chiave:");
		getContentPane().add(scrittaChiave);
		scrittaChiave.setBounds(420, 480, 90, 30);

		areaConvertiti.setColumns(20);
		areaConvertiti.setRows(5);
		jScrollPane1.setViewportView(areaConvertiti);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(520, 260, 430, 180);

		areaOriginali.setColumns(20);
		areaOriginali.setRows(5);
		jScrollPane2.setViewportView(areaOriginali);

		getContentPane().add(jScrollPane2);
		jScrollPane2.setBounds(40, 260, 430, 180);

		tastoConverti.setFont(new Font("Tahoma", 0, 14));
		tastoConverti.setText("Converti");

		getContentPane().add(tastoConverti);
		tastoConverti.setBounds(420, 650, 150, 30);

		tastoForzaBrutale.setText("Forza Brutale");

		getContentPane().add(tastoForzaBrutale);
		tastoForzaBrutale.setBounds(40, 680, 150, 30);

		listaGraficaMessaggi.setModel(new javax.swing.AbstractListModel<String>() {
			private static final long serialVersionUID = 1917L;
			private String[] strings = { "Nessun messaggio arrivato" };
			public int getSize() { return strings.length; }
			public String getElementAt(int i) { return strings[i]; }
		});

		jScrollPane3.setViewportView(listaGraficaMessaggi);

		getContentPane().add(jScrollPane3);
		jScrollPane3.setBounds(410, 150, 165, 75);

		tastoArchivioDati.setText("Archivio Dati");

		getContentPane().add(tastoArchivioDati);
		tastoArchivioDati.setBounds(860, 680, 110, 30);

		scrittaDelCampoSinisto.setText("Messaggio cifrato");
		getContentPane().add(scrittaDelCampoSinisto);
		scrittaDelCampoSinisto.setBounds(50, 240, 120, 14);

		scrittaDelCampoDestro.setText("Messaggio decifrato");
		getContentPane().add(scrittaDelCampoDestro);
		scrittaDelCampoDestro.setBounds(820, 240, 120, 14);

		metodoDiCifratura.setModel(new DefaultComboBoxModel<>(new String[] { "Cifrario di Cesare", "Cifrario di Vigenère"}));
		getContentPane().add(metodoDiCifratura);
		metodoDiCifratura.setBounds(400, 560, 210, 40);

		sfondo.setIcon(new javax.swing.ImageIcon("src/secretInbox/Immagini/SfondoInbox.png"));
		getContentPane().add(sfondo);
		sfondo.setBounds(0, 0, 1000, 800);

		listaGraficaMessaggi.setEnabled(false);
		tastoForzaBrutale.setEnabled(false);
		tastoConverti.setEnabled(false);
		areaConvertiti.setEditable(false);
		areaOriginali.setEditable(false);
		areaChiave.setEditable(false);
		metodoDiCifratura.setEnabled(false);

		/*
		 * Attivazione dei:
		 * 	MouseListener e KeyListener per la lista dei messaggi
		 * 	ActionListener per i 3 pulsanti
		 */
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
				try {
					areaOriginali.setText(listaDeiMessaggi.get(messaggioSelezionato));
				}catch(IndexOutOfBoundsException ee) {}
				areaConvertiti.setText("");
			}
		};
		listaGraficaMessaggi.addMouseListener(mouseListener);

		int condition = JComponent.WHEN_FOCUSED;
		InputMap iMap = listaGraficaMessaggi.getInputMap(condition);
		ActionMap aMap = listaGraficaMessaggi.getActionMap();

		String keyUp = "SOPRA";
		String keyDown = "SOTTO";
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), keyUp);
		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), keyDown);

		aMap.put(keyUp, new AbstractAction() {
			private static final long serialVersionUID = 1917L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listaGraficaMessaggi.setSelectedIndex(listaGraficaMessaggi.getSelectedIndex() - 1);
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
				areaOriginali.setText(listaDeiMessaggi.get(messaggioSelezionato));
				areaConvertiti.setText("");
			}
		});

		aMap.put(keyDown, new AbstractAction() {
			private static final long serialVersionUID = 1917L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listaGraficaMessaggi.setSelectedIndex(listaGraficaMessaggi.getSelectedIndex() + 1);
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
				areaOriginali.setText(listaDeiMessaggi.get(messaggioSelezionato));
				areaConvertiti.setText("");
			}
		});

		tastoForzaBrutale.addActionListener(this);
		tastoConverti.addActionListener(this);
		tastoArchivioDati.addActionListener(this);
	}

	/*
	 * Metodo utilizzato per il controllo della chiave nella cifratura di cesare
	 * Per assicurarsi che contenga solo cifre
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(tastoConverti == e.getSource()) {
			if(messaggioSelezionato != -1) {//Se è stato scelto un messaggio
				if(!areaChiave.getText().isBlank()) {//Se è stata inserita una chiave
					/*
					 * Scelta della decifratura di Cesare
					 */
					if(metodoDiCifratura.getSelectedIndex() == 0) {
						if(osservatoreSpeciale(areaChiave.getText())) {
							/*
							 * Se è stato scelto un messaggio e se è stata inserita una corretta chiave per decifrare
							 * avviene il GOOD ENDING
							 */
							String msgConvertito = macchinaDecifratrice.decifraturaDiCesare(listaDeiMessaggi.get(messaggioSelezionato).trim(), Integer.parseInt(areaChiave.getText()));
							areaConvertiti.setText(msgConvertito);
						}else {
							JOptionPane.showMessageDialog(null, "Con il metodo scelto devi inserire un numero", "Errore", JOptionPane.ERROR_MESSAGE);	
						}
					}else {	
						/*
						 * Scelta della decifratura di Vigènere
						 */
						if(osservatoreMegaSpeciale(areaChiave.getText())){
							/*
							 * Se è stato scelto un messaggio e se è stata inserita una corretta chiave per decifrare
							 * avviene il GOOD ENDING
							 */
							String msgConvertito = macchinaDecifratrice.decifraturaDiVigenère(listaDeiMessaggi.get(messaggioSelezionato), areaChiave.getText());
							areaConvertiti.setText(msgConvertito);
						}else {
							JOptionPane.showMessageDialog(null, "Con il metodo scelto puoi inserire solo caratteri", "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Inserisci una chiave", "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(null, "Non hai scelto alcun messaggio", "Errore", JOptionPane.ERROR_MESSAGE);
			}
		}//Parentesi chiusa dell'evento pressione tasto

		if(tastoArchivioDati == e.getSource()) {

		}//Parentesi chiusa dell'evento pressione tasto

		if(tastoForzaBrutale == e.getSource()) {

		}//Parentesi chiusa dell'evento pressione tasto
	}//Chiusura ActionListener

	public void aggiungiMessaggioInLista(String pacchetto) {
		/*
		 * Estrazione del messaggio dal pacchetto inviato dall'utente
		 */
		String messaggio = "";
		for(int i = 6; i < pacchetto.length(); i++) {
			if(pacchetto.charAt(i) == '') {
				messaggio += System.getProperty("line.separator");
			}
			else messaggio += pacchetto.charAt(i);
		}

		listaDeiMessaggi.add(messaggio);//Aggiunta alla lista dei messaggi quello appena arrivato
		String[] strings = new String[listaDeiMessaggi.size()];//Reset della stringa da inserire

		/*
		 * Azzeramento di ogni cella
		 */
		for(int i = 0; i < strings.length; i++) {
			strings[i] = "";
		}

		/*
		 * Il vettore di stringhe contiene il numero di messaggi che sono arrivati
		 * in modo da stamparne il numero sulla JList
		 */
		for(int i = 0; i < listaDeiMessaggi.size();i++) {
			int j = i+1;
			strings[i] = ("Messaggio " + j);
		}

		/*
		 * Impostazione della JList
		 */
		listaGraficaMessaggi.setModel(new javax.swing.AbstractListModel<String>() {
			private static final long serialVersionUID = 1917L;

			public int getSize() { return strings.length; }
			public String getElementAt(int i) { return strings[i]; }
		});

		listaGraficaMessaggi.setEnabled(true);
		tastoForzaBrutale.setEnabled(true);
		tastoConverti.setEnabled(true);
		areaChiave.setEditable(true);
		metodoDiCifratura.setEnabled(true);
	}//Chiusura metodo aggiungi messaggio in Lista
}
