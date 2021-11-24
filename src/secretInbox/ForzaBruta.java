/*
 * Classe che esegue la forza bruta
 * 	In primis viene scelto il tipo di cifratura con il JComboBox
 * 	Successivamente viene avviato il pulsante "avvia"
 */
package secretInbox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class ForzaBruta extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili di gestione finestra
	 */
	private int x = 300, y = 10, larghezza = 500, lunghezza = 500;

	private JLabel titolo;
	private JComboBox<String> metodoDiCifratura;
	private JButton avvia;
	private JButton annulla;
	private JTextArea areaOriginali;
	private JScrollPane scrollinoArea;
	//Componenti per Cesare
	private JLabel scrittaChiaveSottoArea = new JLabel();
	//Componenti per Vigenère
	private JScrollPane scrollinoMessaggi;
	private JList<String> listaGraficaMessaggi;

	private JPanel pannelloSuperiore;
	private JPanel pannelloCentrale;
	private JPanel pannelloInferiore;

	/*
	 * Variabili per decifratura del messaggio ricevuto
	 */
	private String testoDelMessaggio;
	private Decifratore decifratorePersonale = new Decifratore();

	private String candidatoTemporaneo = "";
	private LinkedList<String> candidatiChiave;
	private String quintoCarattere;
	private int messaggioSelezionato;

	public ForzaBruta(String testo) {
		super("Forza Bruta");
		initComponents();
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(x, y, larghezza, lunghezza);
		testoDelMessaggio = testo;

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener( new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				InboxWindow.accertamentoSullaFinestra = true;
				dispose();
			}
		});
	}

	private void initComponents() {
		titolo = new JLabel();
		metodoDiCifratura = new JComboBox<>();
		avvia = new JButton();
		annulla = new JButton();
		areaOriginali = new JTextArea();
		scrollinoArea = new JScrollPane();
		scrollinoMessaggi = new JScrollPane();
		listaGraficaMessaggi = new JList<>();
		pannelloSuperiore = new JPanel();
		pannelloCentrale = new JPanel();
		pannelloInferiore = new JPanel();

		titolo.setText("Forza bruta");
		titolo.setBounds(320, 620, 210, 30);
		titolo.setFont(new java.awt.Font("Tahoma", 1, 40));

		avvia.setText("Avvia");
		avvia.setBounds(320, 620, 210, 30);
		avvia.setFont(new java.awt.Font("Tahoma", 0, 14));
		avvia.setPreferredSize(new java.awt.Dimension(100, 30));

		annulla.setText("Annulla");
		annulla.setBounds(320, 620, 210, 30);
		annulla.setFont(new java.awt.Font("Tahoma", 0, 14));
		annulla.setPreferredSize(new java.awt.Dimension(100, 30));

		metodoDiCifratura.setBounds(400, 560, 300, 100);
		metodoDiCifratura.setModel(new DefaultComboBoxModel<>(new String[] { "Cifrario di Cesare", "Cifrario di Vigenère"}));

		areaOriginali.setColumns(40);
		areaOriginali.setRows(10);
		areaOriginali.setEditable(false);
		scrollinoArea.setViewportView(areaOriginali);
		scrollinoArea.setBounds(40, 260, 430, 180);

		scrollinoMessaggi.setViewportView(listaGraficaMessaggi);
		scrollinoMessaggi.setBounds(165, 270, 165, 75);
		scrollinoMessaggi.setVisible(false);

		pannelloSuperiore.add(titolo);
		pannelloCentrale.add(metodoDiCifratura);
		pannelloCentrale.add(scrollinoArea);

		pannelloCentrale.add(scrittaChiaveSottoArea);

		pannelloCentrale.add(scrollinoMessaggi);

		pannelloInferiore.add(avvia);
		pannelloInferiore.add(annulla);

		getContentPane().add(pannelloSuperiore, BorderLayout.NORTH);
		getContentPane().add(pannelloCentrale, BorderLayout.CENTER);
		getContentPane().add(pannelloInferiore, BorderLayout.SOUTH);

		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
				try {
					areaOriginali.setText(candidatiChiave.get(messaggioSelezionato));
				}catch(IndexOutOfBoundsException ee) {}
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
				areaOriginali.setText(candidatiChiave.get(messaggioSelezionato));
			}
		});

		aMap.put(keyDown, new AbstractAction() {
			private static final long serialVersionUID = 1917L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listaGraficaMessaggi.setSelectedIndex(listaGraficaMessaggi.getSelectedIndex() + 1);
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
				areaOriginali.setText(candidatiChiave.get(messaggioSelezionato));
			}
		});

		avvia.addActionListener(this);
		annulla.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(avvia == e.getSource() && metodoDiCifratura.getSelectedIndex() == 0) {
			//Il limite di ricerca è "99999" perché il numero massimo che può essere inserito è di 5 cifre
			for(int i = 0; i <= 99999; i++) {
				//Cerchiamo il 2 punti
				if(decifratorePersonale.decifraturaDiCesare(String.valueOf(testoDelMessaggio.charAt(4)), i).equals(":")) {
					areaOriginali.setText(decifratorePersonale.decifraturaDiCesare(testoDelMessaggio, i));

					scrittaChiaveSottoArea.setText("La chiave trovata è: " + i);
					scrittaChiaveSottoArea.setBounds(320, 620, 210, 30);
					scrittaChiaveSottoArea.setFont(new java.awt.Font("Tahoma", 1, 25));
					scrittaChiaveSottoArea.setForeground(Color.RED);
					//Nel caso sia stata fatta precedentemente una cifratura di Vigenère toglie la JList
					scrollinoMessaggi.setVisible(false);
					System.out.println("Brute Force andato a buon fine!\nLa chiave corretta è: " + i);
					i = 99999+1;//Limite della condizione massima + 1, in modo che il ciclo for termini
				}
			}
		}//Chiusura avvio Brute Force con Cesare

		if(avvia == e.getSource() && metodoDiCifratura.getSelectedIndex() == 1) {
			//Inizializzazione della lista dei candidati ad essere la chiave corretta
			candidatiChiave = new LinkedList<String>();

			/*
			 * Ricerca della prima cifra della chiave
			 */
			candidatoTemporaneo = "";
			for(int i = 0; i <= 99999; i++) {
				//Cerchiamo il 2 punti
				if(decifratorePersonale.decifraturaDiCesare(String.valueOf(testoDelMessaggio.charAt(5)), i).equals(" ")) {
					candidatoTemporaneo +=  String.valueOf(i);
					i = 99999+1;//Limite della condizione massima + 1, in modo che il ciclo for termini
				}
			}

			/*
			 * Ricerca della quinta cifra della chiave
			 */
			for(int i = 0; i <= 99999; i++) {
				//Cerchiamo il 2 punti
				if(decifratorePersonale.decifraturaDiCesare(String.valueOf(testoDelMessaggio.charAt(4)), i).equals(":")) {
					quintoCarattere =  String.valueOf(i);
					i = 99999+1;//Limite della condizione massima + 1, in modo che il ciclo for termini
				}
			}

			/*
			 * Trovate le 2 lettere, ora mancano la seconda, terza e quarta lettera della stringa
			 * 	Si vanno a cercare gli zeri delle chiavi
			 */

			//Seconda posizione della chiave
			int differenza = 0;
			int secondoNumeroDiPassoSuccessiviAlloZero = 0;
			while(testoDelMessaggio.charAt(1) - secondoNumeroDiPassoSuccessiviAlloZero != 48) {
				secondoNumeroDiPassoSuccessiviAlloZero = testoDelMessaggio.charAt(1) - differenza;
				differenza++;
			}

			//Terza posizione della guerra
			differenza = 0;
			int terzoNumeroDiPassoSuccessiviAlloZero = 0;
			while(testoDelMessaggio.charAt(2) - terzoNumeroDiPassoSuccessiviAlloZero != 48) {
				terzoNumeroDiPassoSuccessiviAlloZero = testoDelMessaggio.charAt(1) - differenza;
				differenza++;
			}

			//Quarta posizione della guerra
			differenza = 0;
			int quartoNumeroDiPassoSuccessiviAlloZero = 0;
			while(testoDelMessaggio.charAt(3) - quartoNumeroDiPassoSuccessiviAlloZero != 48) {
				quartoNumeroDiPassoSuccessiviAlloZero = testoDelMessaggio.charAt(1) - differenza;
				differenza++;
			}

			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					for(int k = 0; k < 10; k++) {
						//Calcolo e unione dei primi 4 caratteri della chiave
						candidatoTemporaneo += String.valueOf(secondoNumeroDiPassoSuccessiviAlloZero - i);
						candidatoTemporaneo += String.valueOf(terzoNumeroDiPassoSuccessiviAlloZero - j);
						candidatoTemporaneo += String.valueOf(quartoNumeroDiPassoSuccessiviAlloZero - k);

						//Unione delle prime quattro cifre con l'ultimo carattere delal chiave
						candidatoTemporaneo += quintoCarattere;

						candidatoTemporaneo = candidatoTemporaneo.substring(candidatoTemporaneo.length()-5, candidatoTemporaneo.length()-1);
						//La chiave calcolata aggiunta alla lista che le va a contenere tutte
						candidatiChiave.add(decifratorePersonale.decifraturaDiVigenère(testoDelMessaggio, candidatoTemporaneo));

						aggiungiMessaggioInLista();
					}
				}
			}
			/*
			 * Sono state appena calcolate le 1000 chiavi possibili
			 */
			scrollinoMessaggi.setVisible(true);
		}//Chiusura avvio Brute Force con Vigenère

		if(annulla == e.getSource()) {
			InboxWindow.accertamentoSullaFinestra = true;
			dispose();
		}
	}//Chiusura metodo actionPerformed

	private void aggiungiMessaggioInLista() {
		String[] strings = new String[candidatiChiave.size()];//Reset della stringa da inserire

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
		for(int i = 0; i < candidatiChiave.size(); i++) {
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
		//Si toglie il testo dalla scritta nel caso sia stato usato precedentemente il metodo di Cesare		

		scrittaChiaveSottoArea.setText("'");
		scrittaChiaveSottoArea.setText("");
		scrollinoMessaggi.setVisible(true);
	}//Chiusura metodo aggiungi messaggio in Lista
}
