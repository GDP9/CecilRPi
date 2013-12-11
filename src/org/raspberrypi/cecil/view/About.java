package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
	JScrollPane scroll;
	public About() {
		getContentPane().setLayout(new BorderLayout());		
		setSize(610, 600);
		setTitle("About");
		logo = new JPanel();
		logo.setLayout(new BorderLayout());
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
		info = new JTextArea("Version: SIM40\r\n"
				+ "Copyright (c) 2013 Southampton University group GDP9" + 
				" \r\n" + 
				"This product was developed by Carolina Ferreira, \r\nShreeprabha Aggarwal, "
				+ "Cathy Jin and Karishma Nune\r\n"
				+"\r\n"
				+"\r\n"
				+"System requirements - JRE 7"
				+"\r\n"
				+"This application was tested on Windows 7, Windows 8, Ubuntu Precise Pangolin, OS X Mavericks, and Raspbian Wheezy."
				+"\r\n"
				+"\r\n"
				+"Cecil is designed to be the assembly language for a virtual processor "
				+ "called SIM40."
				+ "Its virtual machine has its own memory and I/O ports. The virtual processor SIM40 has three registers,"
				+ "three status flags and an instruction set. This instruction set comprises simple instructions like load,"
				+ "add, sub, insert. These can be used to create more complicated sub-routines like loops and if-else statements."
				+ "This is particularly useful to understand how the machine breaks down a seemingly atomic for-loop statement into multiple operations.");
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setBackground(Color.WHITE);
		info.setEditable(false);
		scroll = new JScrollPane(info);
		info.setFocusable(true);
		info.getAccessibleContext().setAccessibleDescription("Version: SIM40\r\n"
				+ "Copyright (c) 2013 Southampton University group GDP9" + 
				" \r\n" + 
				"This product was developed by Carolina Ferreira, \r\nShreeprabha Aggarwal, "
				+ "Cathy Jin and Karishma Nune\r\n"
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"System requirements - JRE 7"
				+"\r\n"
				+"This application was tested on Windows 7, Windows 8, Ubuntu Precise Pangolin, OS X Mavericks, and Raspbian Wheezy."
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"\r\n"
				+"Cecil is designed to be the assembly language for a virtual processor "
				+ "called SIM40."
				+ "Its virtual machine has its own memory and I/O ports. The virtual processor SIM40 has three registers,"
				+ "three status flags and an instruction set. This instruction set comprises simple instructions like load,"
				+ "add, sub, insert. These can be used to create more complicated sub-routines like loops and if-else statements."
				+ "This is particularly useful to understand how the machine breaks down a seemingly atomic for-loop statement into multiple operations.");
		content.add(scroll, BorderLayout.CENTER);
		getContentPane().add(logo, BorderLayout.WEST);
		getContentPane().add(content, BorderLayout.CENTER);
		getContentPane().setFocusable(true);
		
		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				info.requestFocusInWindow();
			}
		});
	}
	/**
	 * Sets the colour theme of the window using an array of three Colors (see Frame).
	 * 
	 * @param colourTheme New colour theme to change to.
	 */
	public void setColours(Color[] colourTheme) {
		if (colourTheme != null) {
			logo.setBackground(colourTheme[0]);
			logo.setBorder(new LineBorder(colourTheme[1], 2));
			content.setBorder(new LineBorder(colourTheme[1], 2));	
		} else {
			logo.setBackground(UIManager.getColor("Panel.background"));			
		}
		repaint();
	}
	/**
	 * Sets the font of the components.
	 * 
	 * @param font New font to change to.
	 */
	public void setFonts(Font font){
		info.setFont(font);		
	}
	
}
