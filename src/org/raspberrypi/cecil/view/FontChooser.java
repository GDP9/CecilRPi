package org.raspberrypi.cecil.view;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.colorchooser.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.peer.FontPeer;

public class FontChooser extends JDialog {

	Color panelcolour;
	JComboBox fontName;
	JComboBox fontSize;
	JLabel sampleText;
	String newFont;
	String newColor;
	JTextArea image;
	JComboBox colourName;
	JPanel fontPanel;
	JPanel fontPreview;
	JPanel colourPreview;
	JPanel controlPanel;
	JRadioButton small;
	JRadioButton medium;
	JRadioButton large;
	Frame frame;
	LineBorder border;

	public FontChooser(Frame frame) {
		this.frame = frame;
		panelcolour = frame.ORANGE_THEME[0];
		// super(parent, "Font Chooser", true);
		setSize(350, 350);
		setFrame();
		setColour();
		// setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancelandclose();
			}
		});
	}

	private void setFrame() {
		getContentPane().setLayout(new GridBagLayout());
		getContentPane().setBackground(panelcolour);
		setTitle("Preferences");
		
		fontPanel = new JPanel();
		 ActionListener fontsize = new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	  	updateFont(((JRadioButton) e.getSource()).getText());
			    			    }
			  };
		 ButtonGroup bg = new ButtonGroup();
		small = new JRadioButton("Small");
		small.setFont(new Font("Arial", Font.PLAIN, 12));
		small.addActionListener(fontsize);
		medium = new JRadioButton("Medium");
		medium.setSelected(true);
		medium.setFont(new Font("Arial", Font.PLAIN, 18));
		medium.addActionListener(fontsize);
		large = new JRadioButton("Large");
		large.setFont(new Font("Arial", Font.PLAIN, 24));
		large.addActionListener(fontsize);
		bg.add(small);
		bg.add(medium);
		bg.add(large);
		fontPanel.setBackground(new Color(255, 148, 82));		
		fontPanel.add(small);
		fontPanel.add(medium);
		fontPanel.add(large);
		
		fontPreview = new JPanel(new GridBagLayout());
		fontPreview.setBackground(panelcolour);
		sampleText = new JLabel("The Text looks like this");
		sampleText.setFont(new Font("Courier", Font.PLAIN, 12));
		GridBagConstraints gbc_fontPanel = new GridBagConstraints();
		gbc_fontPanel.fill = GridBagConstraints.BOTH;
		gbc_fontPanel.gridx = 0;
		gbc_fontPanel.gridy = 0;
		gbc_fontPanel.weightx = 1;
		gbc_fontPanel.weighty = 0;
		fontPreview.add(fontPanel, gbc_fontPanel);

		GridBagConstraints gbc_fontlabel = new GridBagConstraints();
		gbc_fontlabel.fill = GridBagConstraints.RELATIVE;
		gbc_fontlabel.gridx = 0;
		gbc_fontlabel.gridy = 1;
		gbc_fontlabel.weightx = 1;
		gbc_fontlabel.weighty = 0;
		fontPreview.add(sampleText, gbc_fontlabel);
		border = new LineBorder(frame.ORANGE_THEME[0], 3, true);
		fontPreview.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder(null, "Font", TitledBorder.LEADING, TitledBorder.TOP, null, null)));

		colourName = new JComboBox(new String[] { "Green", "Orange", "Blue",
				"Default" });
		colourName.setSelectedIndex(1);
		colourName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				updateColour();
			}
		});
		colourPreview = new JPanel(new BorderLayout());
		image = new JTextArea();
		image.setBackground(frame.ORANGE_THEME[2]);
		image.setEditable(false);
		colourPreview.add(colourName, BorderLayout.NORTH);
		colourPreview.add(image, BorderLayout.CENTER);
		colourPreview.setOpaque(false);
		colourPreview.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0, 10, 10, 10), new TitledBorder(null, "Colour", TitledBorder.LEADING, TitledBorder.TOP, null, null)), new EmptyBorder(10, 10, 10, 10)));

		GridBagConstraints gbc_font = new GridBagConstraints();
		gbc_font.fill = GridBagConstraints.BOTH;
		gbc_font.gridx = 0;
		gbc_font.gridy = 0;
		gbc_font.weightx = 1;
		gbc_font.weighty = 0.2;
		getContentPane().add(fontPreview, gbc_font);

		// colourPreview.add(image);

		GridBagConstraints gbc_colour = new GridBagConstraints();
		gbc_colour.fill = GridBagConstraints.BOTH;
		gbc_colour.gridx = 0;
		gbc_colour.gridy = 1;
		gbc_colour.weightx = 0;
		gbc_colour.weighty = 0.3;
		getContentPane().add(colourPreview, gbc_colour);

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				saveandclose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cancelandclose();
			}
		});

		controlPanel = new JPanel();
		controlPanel.add(okButton);
		controlPanel.add(cancelButton);
		controlPanel.setBackground(panelcolour);
		GridBagConstraints gbc_buttons = new GridBagConstraints();
		gbc_buttons.fill = GridBagConstraints.REMAINDER;
		gbc_buttons.gridx = 0;
		gbc_buttons.gridy = 2;
		gbc_buttons.weightx = 1;
		gbc_buttons.weighty = 0;
		getContentPane().add(controlPanel, gbc_buttons);

	}

	protected void setColour() {
		getContentPane().setBackground(panelcolour);
		fontPanel.setBackground(panelcolour);
		fontPreview.setBackground(panelcolour);
		controlPanel.setBackground(panelcolour);
		small.setBackground(panelcolour);
		medium.setBackground(panelcolour);
		large.setBackground(panelcolour);
		repaint();
	}

	/* updates the sample text to the new font */
	protected void updateFont(String font) {

		int size = 12;
		if (font == "Small") {
			size = 12;
			newFont = "Small";
			sampleText.setFont(new Font("Courier", Font.PLAIN, 12));
			frame.tblMemory.setRowHeight(90);
		} 
		
		else if (font == "Medium") {
			size = 18;
			newFont = "Medium";
			sampleText.setFont(new Font("Courier", Font.PLAIN, 18));
			frame.tblMemory.setRowHeight(30);
		}
		
		else if (font == "Large") {
			size = 24;
			newFont = "Large";
			sampleText.setFont(new Font("Courier", Font.PLAIN, 24));
			frame.tblMemory.setRowHeight(90);
			
		}
		
	
	}

	protected void updateColour() {
		// Image will be shown here.
		if (colourName.getSelectedIndex() == 1) {
//			image.setBackground(new Color(255, 230, 214));
//			panelcolour = new Color(255, 148, 82);
//			newColor = "Orange";
			
//			LineBorder lineBorder = new LineBorder(frame.ORANGE_THEME[1], 2, true);
			image.setBackground(frame.ORANGE_THEME[2]);
			panelcolour = frame.ORANGE_THEME[0];
			UIManager.put("TitledBorder.border", new LineBorder(frame.ORANGE_THEME[1], 3, true));
			newColor = "Orange";
		} else if (colourName.getSelectedIndex() == 0) {
//			image.setBackground(new Color(191, 252, 172));
//			panelcolour = new Color(6, 209, 46);
			
			image.setBackground(frame.GREEN_THEME[2]);
			panelcolour = frame.GREEN_THEME[0];
			UIManager.put("TitledBorder.border", new LineBorder(frame.GREEN_THEME[1], 3, true));
			newColor = "Green";
		} else if (colourName.getSelectedIndex() == 2) {
//			image.setBackground(new Color(152, 221, 255));
//			panelcolour = new Color(67, 178, 233);
			
			image.setBackground(frame.BLUE_THEME[2]);
			panelcolour = frame.BLUE_THEME[0];
			UIManager.put("TitledBorder.border", new LineBorder(frame.BLUE_THEME[1], 3, true));
			newColor = "Blue";
		} else if (colourName.getSelectedIndex() == 3) {
//			image.setBackground(new Color(224, 224, 224));
//			panelcolour = new Color(224, 224, 224);
			
			image.setBackground(Color.WHITE);
			panelcolour = UIManager.getColor("Panel.background");
			UIManager.put("TitledBorder.border", new EtchedBorder());
			newColor = "Default";
		}
		setColour();

	}

	public void saveandclose() {
		// Save font & color information
		frame.setNewFont(newFont);
		frame.setNewColour(newColor);
		setVisible(false);
		// System.out.println("Test in FontChooser?"+new Frame().test);

	}

	public void cancelandclose() {
		// Erase any font information and then close the window
		// newFont = null;
		newColor = null;
		setVisible(false);
	}
	/*
	 * public static void main(String[] args) { Frame frame = new Frame(); (new
	 * FontChooser(frame)).setVisible(true); }
	 */
}
