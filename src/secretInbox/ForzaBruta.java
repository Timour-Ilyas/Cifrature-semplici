package secretInbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ForzaBruta extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1917L;
		
	/*
	 * Variabili di gestione finestra
	 */
	private int x = 300, y = 10, larghezza = 1000, lunghezza = 800;
	
	private JLabel titolo;
	private JComboBox<String> metodoDiCifratura;
	private JButton avvia;
	private JButton annulla;
	
	public ForzaBruta() {
		super("Forza Bruta");
		initComponents();
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(x, y, larghezza, lunghezza);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initComponents() {
		titolo = new JLabel();
		metodoDiCifratura = new JComboBox<>();
		avvia = new JButton();
		annulla = new JButton();
		
		avvia.addActionListener(this);
		annulla.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(avvia == e.getSource()) {
			
		}
		
		if(annulla == e.getSource()) {
			
		}
	}//Chiusura metodo actionPerformed
}
