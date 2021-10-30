/*
 * Classe con la funzione di poter limitare i caratteri in un JTextPane
 */
package secretSender;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class JTextPaneLimit extends JTextPane implements TableCellRenderer {
	private static final long serialVersionUID = 1917L;
	
	/*
	 * La dimensione massima di caratteri inseribili nel JTextPane è di 512 in quanto numero massimo che può
	 * contenere il messaggio
	 */
	private final int maxNumberOfCharacters = 512;

	public JTextPaneLimit() {
		setStyledDocument(new DefaultStyledDocument() {
			private static final long serialVersionUID = 1917L;

			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if ((getLength() + str.length()) <= maxNumberOfCharacters) {
					super.insertString(offs, str, a);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return null;
	}


}