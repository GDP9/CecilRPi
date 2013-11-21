package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class About extends JDialog  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public About() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.DARK_GRAY);
		setSize(610, 400);
		JPanel logo = new JPanel();
		logo.setLayout(new BorderLayout());
		logo.setBorder(new LineBorder(Color.GRAY, 2));
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(getClass().getResource("/resources/cecil_title.png"));;
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			logo.add(picLabel, BorderLayout.CENTER);
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBorder(new LineBorder(Color.GRAY, 2));
		JTextArea info = new JTextArea("Version: SIM40\r\n"
				+ "Copyright (c) 2013 Southampton University group GDP9\r\n" + 
				" \r\n" + 
				"This product was developed by Carolina Ferreira, \r\nShreeprabha Aggarwal, "
				+ "Cathy Jin and Karishma Nune\r\n"
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"Cecil is designed to be the assembly language for a \r\nvirtual processor "
				+ "called SIM."
				+ "Its virtual machine has \r\nits own memory and I/O ports. The virtual processor \r\nSIM has three registers,"
				+ "three status flags and an \r\ninstruction set. This instruction set comprises simple \r\ninstructions like load,"
				+ "add, sub, insert. These can be \r\nused to create more complicated sub-routines like \r\nloops and if-else statements."
				+ "This is particularly \r\nuseful to understand how the machine breaks down a \r\nseemingly atomic for-loop statement into multiple \r\noperations. ");
		info.setBackground(Color.WHITE);
		info.setEditable(false);
		content.add(info, BorderLayout.CENTER);
		getContentPane().add(logo, BorderLayout.WEST);
		getContentPane().add(content, BorderLayout.CENTER);
		
	}
}
