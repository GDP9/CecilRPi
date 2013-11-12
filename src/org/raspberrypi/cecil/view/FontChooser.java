package org.raspberrypi.cecil.view;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

public class FontChooser extends JDialog{


JComboBox fontName;
JComboBox fontSize;
JLabel sampleText;
String newFont;
String newColor;
JTextArea image;
JComboBox colourName;
Frame frame;

public FontChooser(Frame frame) {
	this.frame= frame;
 //super(parent, "Font Chooser", true);
 setSize(350, 350);
 //setResizable(false);


 addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e) {
	   cancelandclose();
   }
 });

 Container c = getContentPane();
 c.setLayout(new GridBagLayout());
 c.setBackground(new Color(255,230,214));
 
 JPanel fontPanel = new JPanel();
 fontName = new JComboBox(new String[] {"TimesRoman", 
                                        "Helvetica", "Courier"});
 fontName.setSelectedIndex(2);
 fontName.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent ae) {
		    updateFont();
		   }
		 });
 fontSize = new JComboBox(new String[] {"Small", 
	       "Medium", "Large"});
 fontSize.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent ae) {
		    updateFont();
		   }
		 });
 
//Color(255,230,214)
fontPanel.setBackground(new Color(255,148,82));				 
 fontPanel.add(fontName);
 fontPanel.add(new JLabel(" Size: "));
 fontPanel.add(fontSize);

 JPanel fontPreview = new JPanel(new GridBagLayout());
 fontPreview.setBackground(new Color(255,148,82));
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


colourName = new JComboBox(new String[] {"Pink", 
       "Orange", "Green", "Default"});
colourName.setSelectedIndex(1);
colourName.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent ae) {
		      updateColour();
		   }
		 });
JPanel colourPreview = new JPanel(new BorderLayout());
image = new JTextArea();
image.setBackground(Color.ORANGE);
colourPreview.add(colourName, BorderLayout.NORTH);
colourPreview.add(image, BorderLayout.CENTER);


GridBagConstraints gbc_font = new GridBagConstraints();
gbc_font.fill = GridBagConstraints.BOTH;
gbc_font.gridx = 0;
gbc_font.gridy = 0;
gbc_font.weightx = 1;
gbc_font.weighty = 0.2;
c.add(fontPreview, gbc_font);


 //colourPreview.add(image);
 
 GridBagConstraints gbc_colour = new GridBagConstraints();
 gbc_colour.fill = GridBagConstraints.BOTH;
 gbc_colour.gridx = 0;
 gbc_colour.gridy = 1;
 gbc_colour.weightx = 0;
 gbc_colour.weighty = 0.3;
  c.add(colourPreview, gbc_colour);


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

 JPanel controlPanel = new JPanel();
 controlPanel.add(okButton);
 controlPanel.add(cancelButton);
 controlPanel.setBackground(new Color(255,230,214));
 GridBagConstraints gbc_buttons = new GridBagConstraints();
 gbc_buttons.fill = GridBagConstraints.REMAINDER;
 gbc_buttons.gridx = 0;
 gbc_buttons.gridy = 2;
 gbc_buttons.weightx = 1;
 gbc_buttons.weighty = 0;
 c.add(controlPanel, gbc_buttons);
 
 }

/*updates the sample text to the new font*/
protected void updateFont() {
	
 String name = fontName.getSelectedItem().toString();
 int size = 12;
 if(fontSize.getSelectedItem().toString()=="Small"){
 size = 12;
 newFont = "Small";
 }
 else if(fontSize.getSelectedItem().toString()=="Medium"){
	 size = 18;
	 newFont = "Medium";
	 }
 else if(fontSize.getSelectedItem().toString()=="Large"){
	 size = 24;
	 newFont = "Large";
	 }
 Font f = new Font(name, Font.PLAIN, size);
 sampleText.setFont(f);
 }

protected void updateColour() {
	  //Image will be shown here.
	if(colourName.getSelectedIndex()==1){
	   image.setBackground(Color.ORANGE);
	   newColor = "Orange";
	}
	else if(colourName.getSelectedIndex()==0){
		image.setBackground(Color.PINK);
	}
	else if(colourName.getSelectedIndex()==2){
		image.setBackground(Color.GREEN);
	}
	else if(colourName.getSelectedIndex()==3){
		image.setBackground(Color.GRAY);
		newColor = "Default";
	}
	
	}



public void saveandclose() {
 // Save font & color information
 frame.setNewFont(newFont);
 frame.setNewColour(newColor);
setVisible(false);
//System.out.println("Test in FontChooser?"+new Frame().test);

}


public void cancelandclose() {
 // Erase any font information and then close the window
 //newFont = null;
 newColor = null;
 setVisible(false);
}
/*public static void main(String[] args) {
	Frame frame = new Frame();
    (new FontChooser(frame)).setVisible(true);
 }*/
}
