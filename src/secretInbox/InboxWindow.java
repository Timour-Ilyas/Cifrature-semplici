/*
 * Classe finestra della SecretInbox
 */
package secretInbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
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
		tastoArchivioDati = new JButton();
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
		tastoConverti.setBounds(420, 600, 150, 30);

		tastoForzaBrutale.setText("Forza Brutale");

		getContentPane().add(tastoForzaBrutale);
		tastoForzaBrutale.setBounds(40, 680, 150, 30);

		listaGraficaMessaggi.setModel(new javax.swing.AbstractListModel<String>() {
			private static final long serialVersionUID = 1917L;
			String[] strings = { "Nessun messaggio arrivato" };
			public int getSize() { return strings.length; }
			public String getElementAt(int i) { return strings[i]; }
		});

		jScrollPane3.setViewportView(listaGraficaMessaggi);

		getContentPane().add(jScrollPane3);
		jScrollPane3.setBounds(410, 150, 165, 70);

		tastoArchivioDati.setText("Archivio Dati");

		getContentPane().add(tastoArchivioDati);
		tastoArchivioDati.setBounds(860, 680, 110, 30);

		sfondo.setIcon(new javax.swing.ImageIcon("src/secretInbox/Immagini/SfondoInbox.png"));
		getContentPane().add(sfondo);
		sfondo.setBounds(0, 0, 1000, 750);

		listaGraficaMessaggi.setEnabled(false);
		tastoForzaBrutale.setEnabled(false);
		tastoConverti.setEnabled(false);
		areaConvertiti.setEditable(false);
		areaOriginali.setEditable(false);
		areaChiave.setEditable(false);

		/*
		 * Attivazione ActionListener
		 */

		tastoForzaBrutale.addActionListener(this);
		tastoConverti.addActionListener(this);
		tastoArchivioDati.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(tastoConverti == e.getSource()) {

		}

		if(tastoArchivioDati == e.getSource()) {

		}

		if(tastoForzaBrutale == e.getSource()) {

		}
	}
}
