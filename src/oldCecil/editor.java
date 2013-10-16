package oldCecil;
import javax.swing.JTextArea;

public class editor {

	/**
	 * @param args
	 */
	/*This is the default program which displays in the programming window*/
	static	String		demoProg="program\tTest\nauthor\tDavid\ndate\t11 May 07\n;---program starts here---\n.start\tload\tdata1\n\tcclear\n\tadd\tdata2\n\tprint\n\tstop\n;---data follows---\n.data1\tinsert\t12\n.data2\tinsert\t23\n";
	static	JTextArea 	editWin=new JTextArea(demoProg, 15,25);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static JTextArea create() {
		return(editWin);
	}
	public static String getText() {
		return (editWin.getText());
	}
}
