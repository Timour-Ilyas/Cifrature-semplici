/*
 * Classe che si occupa della gestione del tempo con cui scade la 
 * visualizzazione della notifica e il suono nel momento del suo arrivo
 */
package secretInbox;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class NotificaMessaggio {
	public static Timer clessidra(int seconds) {
		ActionListener close = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Window[] windows = Window.getWindows();
				for (Window window : windows) {
					if (window instanceof JDialog) {
						JDialog dialog = (JDialog) window;
						if (dialog.getContentPane().getComponentCount() == 1
								&& dialog.getContentPane().getComponent(0) instanceof JOptionPane){
							dialog.dispose();
						}
					}
				}

			}

		};
		Timer t = new Timer(seconds * 1000, close);
		t.setRepeats(false);
		return t;
	}

	public static void suonoNotifica(String soundFile) {
		File f = new File("./" + soundFile);
		AudioInputStream audioIn = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clip.start();
	}
}
