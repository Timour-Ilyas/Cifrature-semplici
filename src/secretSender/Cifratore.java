package secretSender;
import java.util.LinkedList;

public class Cifratore {
	private String messaggioDaCifrare;
	private LinkedList<Character> listaDaCifrare = new LinkedList<Character>();
	private String messaggioCifrato;
	private Boolean upperCase;
	private int numeroSommatoreAscii;

	/*
	 * Getter e setter
	 */
	public String getMessaggioDaCifrare() {
		return messaggioDaCifrare;
	}
	public void setMessaggioDaCifrare(String messaggioDaCifrare) {
		this.messaggioDaCifrare = messaggioDaCifrare;
	}
	public String getMessaggioCifrato() {
		return messaggioCifrato;
	}
	public void setMessaggioCifrato(String messaggioCifrato) {
		this.messaggioCifrato = messaggioCifrato;
	}

	public String cifraturaDiCesare(String testo, String codiceNumerico, int chiave) {
		messaggioDaCifrare = codiceNumerico + ": " + testo;
		System.out.println(messaggioDaCifrare);

		if(chiave > 26) {
			chiave %= 26;
			chiave++;
		}

		for(int i = 0; i < messaggioDaCifrare.length(); i++) {
			listaDaCifrare.add(i, messaggioDaCifrare.charAt(i));
		}

		for(int i = 0; i < listaDaCifrare.size(); i++) {
			upperCase = Character.isUpperCase(listaDaCifrare.get(i));

			listaDaCifrare.set(i, Character.toUpperCase(listaDaCifrare.get(i)));

			numeroSommatoreAscii = (int) listaDaCifrare.get(i) + chiave;

			if(numeroSommatoreAscii > 90)
				numeroSommatoreAscii -= 26;

			listaDaCifrare.set(i,(char) numeroSommatoreAscii);

			if(upperCase) {
				Character.toLowerCase(listaDaCifrare.get(i));
			}
		}

		messaggioCifrato = "";
		for(int i = 0; i < listaDaCifrare.size(); i++) {
			messaggioCifrato = messaggioCifrato + listaDaCifrare.get(i);
		}

		System.out.println(messaggioCifrato);

		return messaggioCifrato;
	}

	public String cifraturaDiVigenère(LinkedList<Character> listaDaCifrare, String chiave) {

		return messaggioCifrato;
	}
}
