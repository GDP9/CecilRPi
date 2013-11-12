package org.raspberrypi.cecil.view;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.peer.FontPeer;

public class FontChooser extends JDialog {

	Color panelcolour = new Color(255, 148, 82);
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
	Frame frame;

	public FontChooser(Frame frame) {
		this.frame = frame;
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

		fontPanel = new JPanel();
		fontName = new JComboBox(new String[] { "TimesRoman", "Helvetica",
				"Courier" });
		fontName.setSelectedIndex(2);
		fontName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				updateFont();
			}
		});
		fontSize = new JComboBox(new String[] { "Small", "Medium", "Large" });
		fontSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				updateFont();
			}
		});

		// Color(255,230,214)
		fontPanel.setBackground(new Color(255, 148, 82));
		fontPanel.add(fontName);
		fontPanel.add(new JLabel(" Size: "));
		fontPanel.add(fontSize);

		fontPreview = new JPanel(new GridBagLayout());
		fontPreview.setBackground(panelcolour);
		sampleText = new JLabel("The Text looks like this");
		sampleText.setFont(new Font("Courier", Font.BOLD, 12));
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
		image.setBackground(new Color(255, 230, 214));
		image.setEditable(false);
		colourPreview.add(colourName, BorderLayout.NORTH);
		colourPreview.add(image, BorderLayout.CENTER);

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

	}

	/* updates the sample text to the new font */
	protected void updateFont() {

		String name = fontName.getSelectedItem().toString();
		int size = 12;
		if (fontSize.getSelectedItem().toString() == "Small") {
			size = 12;
			newFont = "Small";
		} else if (fontSize.getSelectedItem().toString() == "Medium") {
			size = 18;
			newFont = "Medium";
		} else if (fontSize.getSelectedItem().toString() == "Large") {
			size = 24;
			newFont = "Large";
		}
		Font f = new Font(name, Font.PLAIN, size);
		sampleText.setFont(f);
	}

	protected void updateColour() {
		// Image will be shown here.
		if (colourName.getSelectedIndex() == 1) {
			image.setBackground(new Color(255, 230, 214));
			panelcolour = new Color(255, 148, 82);
			newColor = "Orange";
		} else if (colourName.getSelectedIndex() == 0) {
			image.setBackground(new Color(191, 252, 172));
			panelcolour = new Color(6, 209, 46);
			newColor = "Green";
		} else if (colourName.getSelectedIndex() == 2) {
			image.setBackground(new Color(152, 221, 255));
			panelcolour = new Color(67, 178, 233);
			newColor = "Blue";
		} else if (colourName.getSelectedIndex() == 3) {
			image.setBackground(new Color(224, 224, 224));
			panelcolour = new Color(224, 224, 224);
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
