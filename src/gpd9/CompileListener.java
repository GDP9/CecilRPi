package gpd9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class CompileListener implements ActionListener {

	private String input;
	private JTextArea console;

	public CompileListener(String input, JTextArea console) {
		this.input = input;
		this.console = console; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.console.setText(input);
		System.out.println(input);
		if(this.parse())
			this.compile();
	}

	private void compile() {

	}

	private boolean parse() {
		return false;

	}

}