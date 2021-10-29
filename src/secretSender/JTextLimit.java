/*
 * Classe con la funzione di poter limitare i caratteri in un campo di testo
 */
package secretSender;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

public class JTextLimit extends PlainDocument {
	private static final long serialVersionUID = 1917L;
	private int limit;

	JTextLimit(int limit) {
		super();
		this.limit = limit;
	}

	public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}	
}