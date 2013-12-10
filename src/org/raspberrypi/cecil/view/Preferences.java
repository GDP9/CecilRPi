package org.raspberrypi.cecil.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;

/**
 * Preferences to change the font size and colour settings.
 * 
 * MIT Open Source License
 * @author Karishma Nune (kkn1g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 07/11/2013
 *
 */
public class Preferences extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color panelcolour;
	JComboBox fontName;
	JComboBox fontSize;
	JLabel sampleText;
	Font newFont;
	Color[] newColor;
	JTextArea inputarea;
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

	public Preferences(Frame frame) {
		this.frame = frame;
		newFont = frame.FONT_MEDIUM;
		panelcolour = frame.ORANGE_THEME[0];
		newColor = frame.ORANGE_THEME;
		setSize(350, 350);
		setFrame();
		setColour();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancelandclose();
			}
		});
		
		/**
		 * Focus is on medium by default
		 */
		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				medium.requestFocusInWindow();
			}
		});

	}
	/**
	 * Sets all the panels, buttons, combo boxes along with the Key listeners and action listeners.
	 * A theme consists of three colours; background, highlight, and inner panel colours.
	 */

	private void setFrame() {
		getContentPane().setLayout(new GridBagLayout());
		getContentPane().setBackground(panelcolour);
		setTitle("Preferences");
		fontPanel = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		small = new JRadioButton("Small");
		small.setFont(frame.FONT_SMALL);
		small.setFocusable(true);
		small.getAccessibleContext().setAccessibleDescription("Font size of 12");
		small.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(small.isFocusOwner()&& arg0.getKeyCode()== KeyEvent.VK_ENTER){
					small.setSelected(true);	
					sampleText.setFont(frame.FONT_SMALL);
					newFont = frame.FONT_SMALL;
					updateFont(newFont);

				}

			}
		});

		small.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sampleText.setFont(frame.FONT_SMALL);
				newFont = frame.FONT_SMALL;
				updateFont(newFont);

			}
		});
		medium = new JRadioButton("Medium");
		medium.setSelected(true);
		medium.setFont(frame.FONT_MEDIUM);
		medium.setFocusable(true);
		medium.getAccessibleContext().setAccessibleDescription("Font size of 16");
		medium.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(medium.isFocusOwner()&& arg0.getKeyCode()== KeyEvent.VK_ENTER){
					medium.setSelected(true);	
					sampleText.setFont(frame.FONT_MEDIUM);
					newFont = frame.FONT_MEDIUM;
					updateFont(newFont);

				}

			}
		});
		medium.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sampleText.setFont(frame.FONT_MEDIUM);
				newFont = frame.FONT_MEDIUM;
				updateFont(newFont);
			}
		});
		large = new JRadioButton("Large");
		large.setFont(frame.FONT_LARGE);
		large.setFocusable(true);
		large.getAccessibleContext().setAccessibleDescription("Font size of 24");
		large.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(large.isFocusOwner()&& arg0.getKeyCode()== KeyEvent.VK_ENTER){
					large.setSelected(true);	
					sampleText.setFont(frame.FONT_LARGE);
					newFont = frame.FONT_LARGE;
					updateFont(newFont);

				}

			}
		});
		large.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				sampleText.setFont(frame.FONT_LARGE);
				newFont = frame.FONT_LARGE;
				updateFont(newFont);
			}
		});
		bg.add(small);
		bg.add(medium);
		bg.add(large);
		fontPanel.setBackground(frame.ORANGE_THEME[0]);		
		fontPanel.add(small);
		fontPanel.add(medium);
		fontPanel.add(large);

		fontPreview = new JPanel(new GridBagLayout());
		fontPreview.setBackground(panelcolour);
		sampleText = new JLabel("The Text looks like this");
		sampleText.setFont(frame.FONT_MEDIUM);
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
		fontPreview.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createCompoundBorder(new TitledBorder(null, "Font", TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(5, 5, 5, 5))));
		colourName = new JComboBox(new String[] { "Green", "Orange", "Blue","Default" });
		colourName.setFont(newFont);
		colourName.setSelectedIndex(1);
		colourName.setFocusable(true);
		colourName.getAccessibleContext().setAccessibleDescription("You can select different colour themes for the application");
		colourName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				updateColour();
			}
		});
		colourPreview = new JPanel(new BorderLayout());
		inputarea = new JTextArea();
		inputarea.setBackground(frame.ORANGE_THEME[2]);
		inputarea.setEditable(false);
		colourPreview.add(colourName, BorderLayout.NORTH);
		colourPreview.add(inputarea, BorderLayout.CENTER);
		colourPreview.setOpaque(false);
		colourPreview.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createCompoundBorder(new TitledBorder(null, "Colour", TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(5, 5, 5, 5))));
		GridBagConstraints gbc_font = new GridBagConstraints();
		gbc_font.fill = GridBagConstraints.BOTH;
		gbc_font.gridx = 0;
		gbc_font.gridy = 0;
		gbc_font.weightx = 1;
		gbc_font.weighty = 0.2;
		getContentPane().add(fontPreview, gbc_font);
		

		GridBagConstraints gbc_colour = new GridBagConstraints();
		gbc_colour.fill = GridBagConstraints.BOTH;
		gbc_colour.gridx = 0;
		gbc_colour.gridy = 1;
		gbc_colour.weightx = 0;
		gbc_colour.weighty = 0.3;
		getContentPane().add(colourPreview, gbc_colour);

		JButton okButton = new JButton("Ok");
		okButton.setFocusable(true);
		okButton.getAccessibleContext().setAccessibleDescription("Updates the application with the selected font and colour");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				saveandclose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFocusable(true);
		cancelButton.getAccessibleContext().setAccessibleDescription("Cancel the preferences and go back the main application");
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
	/**
	 * Sets the colours of the preferences.
	 */
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
	/**
	 * Sets the font of the preferences.
	 */
	protected void updateFont(Font font) {
		((TitledBorder)((CompoundBorder)((CompoundBorder)fontPreview.getBorder()).getInsideBorder()).getOutsideBorder()).setTitleFont(font);
		((TitledBorder)((CompoundBorder)((CompoundBorder)colourPreview.getBorder()).getInsideBorder()).getOutsideBorder()).setTitleFont(font);
		colourName.setFont(font);
	}

	/**
	 * Set the colours of the Preferences to the current theme and send the selected option to the frame.
	 * A theme consists of three colours; background, highlight, and inner panel colours.
	 */

	protected void updateColour() {
		if (colourName.getSelectedIndex() == 1) {
			inputarea.setBackground(frame.ORANGE_THEME[2]);
			panelcolour = frame.ORANGE_THEME[0];
			UIManager.put("TitledBorder.border", new LineBorder(frame.ORANGE_THEME[1], 3, true));
			newColor = frame.ORANGE_THEME;
		} else if (colourName.getSelectedIndex() == 0) {
			inputarea.setBackground(frame.GREEN_THEME[2]);
			panelcolour = frame.GREEN_THEME[0];
			UIManager.put("TitledBorder.border", new LineBorder(frame.GREEN_THEME[1], 3, true));
			newColor = frame.GREEN_THEME;
		} else if (colourName.getSelectedIndex() == 2) {
			inputarea.setBackground(frame.BLUE_THEME[2]);
			panelcolour = frame.BLUE_THEME[0];
			UIManager.put("TitledBorder.border", new LineBorder(frame.BLUE_THEME[1], 3, true));
			newColor = frame.BLUE_THEME;
		} else if (colourName.getSelectedIndex() == 3) {
			inputarea.setBackground(Color.WHITE);
			panelcolour = UIManager.getColor("Panel.background");
			UIManager.put("TitledBorder.border", new EtchedBorder());
			newColor = frame.DEFAULT_THEME;
		}
		setColour();

	}
	/**
	 * Send the selected options to the frame.
	 */

	public void saveandclose() {
		frame.setNewFont(newFont);
		frame.setNewColour(newColor);
		setVisible(false);		
	}

	public void cancelandclose() {
		setVisible(false);
	}
}
