package org.raspberrypi.cecil;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;
import javax.swing.JButton;

public class CRaspberryPi {

	private JFrame frmCecilIde;
	private JTextArea inputEditor;
	private JTextArea console;
	private JButton btnCompile;
	private JButton btnRun;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRaspberryPi window = new CRaspberryPi();
					window.frmCecilIde.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CRaspberryPi() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCecilIde = new JFrame();
		frmCecilIde.setTitle("CECIL IDE");
		frmCecilIde.setBounds(100, 100, 450, 300);
		frmCecilIde.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_inputEditor = new JPanel();
		panel_inputEditor.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Input Editor", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		frmCecilIde.getContentPane().add(panel_inputEditor, BorderLayout.WEST);
		panel_inputEditor.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		String	demoProg="program\tTest\nauthor\tDavid\ndate\t11 May 07\n;---program starts here---\n.start\tload\tdata1\n\tcclear\n\tadd\tdata2\n\tprint\n\tstop\n;---data follows---\n.data1\tinsert\t12\n.data2\tinsert\t23\n";
		inputEditor = new JTextArea(demoProg, 15, 25);
		inputEditor.setToolTipText("This is the input editor for writing machine code.");
		panel_inputEditor.add(inputEditor);
		
		JPanel panel_console = new JPanel();
		panel_console.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Console", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmCecilIde.getContentPane().add(panel_console, BorderLayout.EAST);
		
		console = new JTextArea(15, 25);
		console.setEditable(false);
		console.setToolTipText("Output View");
		panel_console.add(console);
		
		JToolBar toolBar = new JToolBar();
		frmCecilIde.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnCompile = new JButton("Compile");
		btnCompile.addActionListener(new CompileListener(inputEditor.getText(), console));
		toolBar.add(btnCompile);
		
		btnRun = new JButton("Run");
		toolBar.add(btnRun);
	}
}