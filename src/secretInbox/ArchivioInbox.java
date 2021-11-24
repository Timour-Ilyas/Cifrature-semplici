package secretInbox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class ArchivioInbox extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili per l'intefaccia grafica
	 */
	private int x = 300, y = 10, larghezza = 500, lunghezza = 500;

	private JLabel titolo;
	private JButton inserisci;
	private JButton annulla;
	private JPanel pannelloSuperiore;
	private JPanel pannelloCentrale;
	private JPanel pannelloInferiore;

	/*
	 * Varibili per il salvataggio e lettura nei file
	 */
	private File fl = new File("src/Archivio.txt");
	private FileWriter flW;
	private JFileChooser scegliFile;
	private BufferedReader lettore;
	private String riga;
	private boolean presenzaFile;

	private JScrollPane scrollino;
	private JList<String> listaGraficaMessaggi = new JList<>();
	private int messaggioSelezionato = -1;

	public ArchivioInbox() {
		super("Archivio");
		initComponents();
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(x, y, larghezza, lunghezza);

		try {
			flW = new FileWriter(fl);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
		/*
		 * Inizializzazione dei componenti
		 */
		titolo = new JLabel();
		inserisci = new JButton();
		annulla = new JButton();
		pannelloSuperiore = new JPanel();
		pannelloCentrale = new JPanel();
		pannelloInferiore = new JPanel();
		scrollino = new JScrollPane();

		titolo.setText("Archivio Dati");
		titolo.setBounds(320, 620, 210, 30);
		titolo.setFont(new java.awt.Font("Tahoma", 1, 40));

		inserisci.setText("Inserisci");
		inserisci.setBounds(320, 620, 210, 30);
		inserisci.setFont(new java.awt.Font("Tahoma", 0, 14));
		inserisci.setPreferredSize(new java.awt.Dimension(100, 30));

		annulla.setText("Annulla");
		annulla.setBounds(320, 620, 210, 30);
		annulla.setFont(new java.awt.Font("Tahoma", 0, 14));
		annulla.setPreferredSize(new java.awt.Dimension(100, 30));

		pannelloSuperiore.add(titolo);
		
		presenzaFile = situazioneAttuale();

		/*
		 * Se non c'è nessun messaggio salvato allora lo scrive sulla finestra
		 */
		if(!presenzaFile) {
			JLabel a = new JLabel();
			a.setText("Nessun messaggio in archivio");
			a.setBounds(320, 620, 210, 30);
			a.setFont(new java.awt.Font("Tahoma", 1, 20));
			pannelloCentrale.add(a);
			inserisci.setEnabled(false);
		}else {

		}
		
		pannelloInferiore.add(inserisci);
		pannelloInferiore.add(annulla);
		
		getContentPane().add(pannelloSuperiore, BorderLayout.NORTH);
		getContentPane().add(pannelloCentrale, BorderLayout.CENTER);
		getContentPane().add(pannelloInferiore, BorderLayout.SOUTH);

		/*
		 * Attivazione dei:
		 * 	MouseListener e KeyListener per la lista dei messaggi
		 * 	ActionListener per i 3 pulsanti
		 */
		MouseListener mouseListener = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
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
			}
		});

		aMap.put(keyDown, new AbstractAction() {
			private static final long serialVersionUID = 1917L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				listaGraficaMessaggi.setSelectedIndex(listaGraficaMessaggi.getSelectedIndex() + 1);
				messaggioSelezionato = listaGraficaMessaggi.getSelectedIndex();
			}
		});
		inserisci.addActionListener(this);
		annulla.addActionListener(this);
	}

	private boolean situazioneAttuale() {
		try {
			/*
			 * Lettura del file per osservare se ci sono messaggi salvati
			 */
			lettore = new BufferedReader(new FileReader(fl));

			/*
			 * Se c'è un messaggio salvato
			 */
			if((riga = lettore.readLine()) != null) {
				return true;
			}
		} catch (IOException e) {
			System.out.println("Non c'è nessun file nell'archivio");
		}
		return false;
	}

	public void aggiungiMessaggioArchivio(String messaggio) { 
		//Viene aggiunto il messaggio al file
		try {
			lettore = new BufferedReader(new FileReader(fl));
			flW.append(messaggio);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Viene contato il numero di righe che ha il file
		int contatoreRighe = 0;
		try {
			while((riga = lettore.readLine()) != null) {
				lettore.skip(1);
				contatoreRighe++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] strings = new String[contatoreRighe];//Reset della stringa da inserire

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
		for(int i = 0; i < contatoreRighe;i++) {
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

		scrollino.setViewportView(listaGraficaMessaggi);
		situazioneAttuale();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(inserisci == e.getSource()) {	

		}

		if(annulla == e.getSource()) {
			InboxWindow.accertamentoSullaFinestra = true;
			dispose();
		}
	}//Chiusura Metodo ActioPerformed
}

