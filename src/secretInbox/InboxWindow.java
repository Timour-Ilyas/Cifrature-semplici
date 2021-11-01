/*
 * Classe finestra della SecretInbox
 */
package secretInbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.BorderLayout;

public class InboxWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1917L;

	/*
	 * Variabili di gestione finestra
	 */
	private int x = 300, y = 10, larghezza = 1000, lunghezza = 800;
	private JPanel superioreTitolo;
	private JPanel pannelloSubInferiore;
	private JLabel titolo;

	/*
	 * Costruttore in cui 
	 * 		Si cambia il nome della finestra
	 * 		Si imposta la finestra 
	 */
	public InboxWindow() {
		super("SecretInbox");
		initComponents();
	}

	private void initComponents() {
		superioreTitolo = new  JPanel();
		pannelloSubInferiore = new JPanel();
		titolo = new JLabel();

		setBounds(x, y, larghezza, lunghezza);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		titolo.setText("SecretInbox");
		titolo.setFont(new Font("Bold",70,70));

		superioreTitolo.add(titolo);

		getContentPane().add(superioreTitolo, BorderLayout.NORTH);
		getContentPane().add(pannelloSubInferiore, BorderLayout.CENTER);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
