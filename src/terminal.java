import java.awt.Color;

import javax.swing.JTextArea;

public class terminal {
	/**
	 * @param args
	 */

	static	String		versionDate="11-May-2007";
	static 	String		stars="\n******************************";
	static	String		output="Welcome to the SIM30!\nVersion: "+versionDate+"\n";
	static	JTextArea 	termWin=new JTextArea(output, 12,25);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public static JTextArea create() {
		return(termWin);
	}
	public static void add(String addText) {
		termWin.setText(termWin.getText()+addText);
	}
	public static void addLine(String addText) {
		termWin.setText(termWin.getText()+addText+"\n");
	}
	public static void addStars() {
		terminal.addLine(stars);
	}
	public static void send(int item) {
		terminal.add("!");
	}
	/*public static void setForeground(Color newColour)
	//Idea was to set parts of text to different colours
	//Nice idea, but it changes the colour of the entire text... :-(  
	{	termWin.setForeground(newColour);
		return;
	}*/
}
