package secretInbox;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ForzaBruta extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili di gestione finestra
	 */
	private int x = 300, y = 10, larghezza = 500, lunghezza = 500;

	private JLabel titolo;
	private JLabel scrittaChiaveSottoArea = new JLabel();
	private JComboBox<String> metodoDiCifratura;
	private JButton avvia;
	private JButton annulla;
	private JTextArea areaOriginali;
	private JScrollPane scrollinoArea;
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
	private LinkedList<String> candidati;

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
		scrollinoMessaggi.setBounds(410, 150, 165, 75);
		scrollinoMessaggi.setVisible(false);

		pannelloSuperiore.add(titolo);
		pannelloCentrale.add(metodoDiCifratura);
		pannelloCentrale.add(scrollinoArea);
		pannelloCentrale.add(scrollinoMessaggi);
		pannelloInferiore.add(avvia);
		pannelloInferiore.add(annulla);

		pannelloCentrale.add(scrittaChiaveSottoArea);

		getContentPane().add(pannelloSuperiore, BorderLayout.NORTH);
		getContentPane().add(pannelloCentrale, BorderLayout.CENTER);
		getContentPane().add(pannelloInferiore, BorderLayout.SOUTH);

		avvia.addActionListener(this);
		annulla.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(avvia == e.getSource() && metodoDiCifratura.getSelectedIndex() == 0) {
			//Il limite di ricerca è "99999" perché il numero massimo che può essere inserito è di 5 cifre
			for(int i = 0; i <= 99999; i++) {
				if(decifratorePersonale.decifraturaDiCesare(String.valueOf(testoDelMessaggio.charAt(4)), i).equals(";")) {
					i++;
					areaOriginali.setText(decifratorePersonale.decifraturaDiCesare(testoDelMessaggio, i));

					scrittaChiaveSottoArea.setText("La chiave trovata è: " + i);
					scrittaChiaveSottoArea.setBounds(320, 620, 210, 30);
					scrittaChiaveSottoArea.setFont(new java.awt.Font("Tahoma", 1, 25));
					scrittaChiaveSottoArea.setForeground(Color.RED);
					System.out.println("Brute Force andato a buon fine!\nLa chiave corretta è: " + i);
					i = 99999+1;//Limite della condizione massima + 1, in modo che il ciclo for termini
				}
			}
		}//Chiusura avvio Brute Force con Cesare

		if(avvia == e.getSource() && metodoDiCifratura.getSelectedIndex() == 1) {
			//Inizializzazione della lista dei candidati ad essere la chiave corretta
			candidati = new LinkedList<String>();
			/*
			 * 6 perchè nella stringa del messaggio si hanno informazioni certe sui primi 6 caratteri
			 * I primi 4 caratteri sono necessariamente numeri e il quinto e sesto caratteri sono costanti: ": "
			 */
			for(int i = 0; i < 6; i++) {
				if(i < 4) {//Analisi dei primi 4 caratteri
					/*
					 * 255 perché è il numero massimo di caratteri ascii esistenti
					 */
					for(int j = 0; j <= 255; j++) {
						/*
						 * Se il carattere di riferimento (carattere in posizione i) cifrato con chiave j
						 * dà come risultato un numero rappresentato da k
						 * Può essere un candidato ad essere la chiave corretta
						 */
						for(int k = 0; k <= 100; k++) {
							System.out.println(String.valueOf(testoDelMessaggio.charAt(i)) + " " + j + " " + k);
							if(decifratorePersonale.decifraturaDiVigenère(String.valueOf(testoDelMessaggio.charAt(i)), j).equals(Integer.toString(k))) {
								candidatoTemporaneo += String.valueOf(j);
								k = 100;
								j = 256;
							}
						}
					}
				}else {//Analisi del quinto e sesto carattere
					//Il limite di ricerca è "99999" perché il numero massimo che può essere inserito è di 5 cifre
					for(int j = 0; j < 99999; j++) {
						if(decifratorePersonale.decifraturaDiVigenère(String.valueOf(testoDelMessaggio.charAt(4)), j).equals(";")) {
							candidatoTemporaneo += String.valueOf(j);
							for(int k = 0; k < 99999; k++) {
								if(decifratorePersonale.decifraturaDiVigenère(String.valueOf(testoDelMessaggio.charAt(4)), j).equals(" ")) {
									candidatoTemporaneo += String.valueOf(k);
									candidati.add(candidatoTemporaneo);
									k = 99999+1;//Limite della condizione massima + 1, in modo che il ciclo for termini
								}
							}
							j = 99999+1;//Limite della condizione massima + 1, in modo che il ciclo for termini
						}
					}
				}//Chiusura analisi del quinto e sesto carattere
				System.out.println(candidatoTemporaneo);
			}//Chiusura ciclo for per analisi dei 6 caratteri
		}//Chiusura avvio Brute Force con Vigenère

		if(annulla == e.getSource()) {
			InboxWindow.accertamentoSullaFinestra = true;
			dispose();
		}
	}//Chiusura metodo actionPerformed
}
