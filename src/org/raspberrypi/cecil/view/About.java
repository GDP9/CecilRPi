package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.accessibility.Accessible;
/**
 * About CECIL page.
 * 
 * MIT Open Source License
 * @author Karishma Nune (kkn1g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 07/11/2013
 *
 */
public class About extends JDialog implements Accessible  {
	private static final long serialVersionUID = 1L;
	JTextArea info;
	JPanel logo;
	JPanel content;
	BufferedImage myPicture;
	public About() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.DARK_GRAY);
		setSize(610, 600);
		setTitle("About");
		logo = new JPanel();
		logo.setLayout(new BorderLayout());
		logo.setBorder(new LineBorder(Color.GRAY, 2));		
		try {
			myPicture = ImageIO.read(getClass().getResource("/resources/cecil_title.png"));;
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			logo.add(picLabel, BorderLayout.CENTER);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBorder(new LineBorder(Color.GRAY, 2));
				  
		info = new JTextArea("Version: SIM40\r\n"
				+ "Copyright (c) 2013 Southampton University group GDP9" + 
				" \r\n" + 
				"This product was developed by Carolina Ferreira, \r\nShreeprabha Aggarwal, "
				+ "Cathy Jin and Karishma Nune\r\n"
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"System requirements - Java 1.7"
				+"\r\n"
				+"This application works on Windows, Linux, Mac and Raspberry Pi."
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"Cecil is designed to be the assembly language for a virtual processor "
				+ "called SIM."
				+ "Its virtual machine has its own memory and I/O ports. The virtual processor SIM has three registers,"
				+ "three status flags and an instruction set. This instruction set comprises simple instructions like load,"
				+ "add, sub, insert. These can be used to create more complicated sub-routines like loops and if-else statements."
				+ "This is particularly useful to understand how the machine breaks down a seemingly atomic for-loop statement into multiple operations. ");
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setBackground(Color.WHITE);
		info.setEditable(false);
		info.setFocusable(true);
		content.add(info, BorderLayout.CENTER);
		getContentPane().add(logo, BorderLayout.WEST);
		getContentPane().add(content, BorderLayout.CENTER);
		getContentPane().setFocusable(true);
		
		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				info.requestFocusInWindow();
			}
		});
	}
}
