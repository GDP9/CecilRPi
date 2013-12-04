package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * User manual frame for listing and opening the CECIL tutorial videos.
 * Videos must be located in the same folder as the .jar file.
 * The videos have not been included in this project due to performance issues.
 * 
 * @author Cathy Jin (cj8g10)
 * @version 1.3
 * 
 * @date 04/12/2013
 * 
 */
public class UserManual extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension SIZE = new Dimension(400, 500);
	private JScrollPane listScroller;
	private JButton btnOpen;
	private JList<String> list;
	
	/**
	 * Creates the UserManual frame with default colour theme and font.
	 */
	public UserManual() {		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().getAccessibleContext().setAccessibleName("User manual resources");
		
		setSize(SIZE);
		setTitle("User Manual");
		setResizable(false);
		
		String[] data = {"1. Accessibility and usability functionalities", "2. Creating a program", "3. Compiling a program", "4. Running a program", "5. Stepping through a program", "6. Interactive LED functionality"};
		list = new JList<String>(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED));
		list.getAccessibleContext().setAccessibleName("Video tutorials");
		list.getAccessibleContext().setAccessibleDescription("Contains links to all video tutorials for using CECIL");
		list.setToolTipText("Choose an accompanying video resource to view. (Note: The videos must be in the same folder as the CECIL application)");
		
		//Tutorials list gets default focus
		addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e) {
				list.requestFocusInWindow();
			}
		});
		
		listScroller = new JScrollPane(list);
		listScroller.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createCompoundBorder(new TitledBorder(null, "Tutorial videos", TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(5, 5, 5, 5))));
		listScroller.setOpaque(false);
		
		JPanel bottomPanel = new JPanel();
		btnOpen = new JButton("Open");
		btnOpen.getAccessibleContext().setAccessibleName("Open");
		btnOpen.getAccessibleContext().setAccessibleDescription("Opens the video");
		btnOpen.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					btnOpen.doClick();
				}
			}
		});
		btnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (list.getSelectedIndex()){
				case 0:
					try {
						Desktop.getDesktop().open(new File("accessibility_and_usability_functionalities.mp4"));
					} catch (IOException e) {
						System.out.println("ERROR: Could not find the accessibility_and_usability_functionalities.mp4 video file");
					}
					break;
				case 1:
					try {
						Desktop.getDesktop().open(new File("creating_a_program.mp4"));
					} catch (IOException e) {
						System.out.println("ERROR: Could not find the creating_a_program.mp4 video file.");
					}
					break;
				case 2:
					try {
						Desktop.getDesktop().open(new File("compiling_program.mp4"));
					} catch (IOException e) {
						System.out.println("ERROR: Could not find the compiling_program.mp4 video file.");
					}
					break;
				case 3:
					try {
						Desktop.getDesktop().open(new File("running_program.mp4"));
					} catch (IOException e) {
						System.out.println("ERROR: Could not find the running_program.mp4 video file.");
					}
					break;
				case 4:
					try {
						Desktop.getDesktop().open(new File("step_through_program.mp4"));
					} catch (IOException e) {
						System.out.println("ERROR: Could not find the step_through_program.mp4 video file.");
					}
					break;
				case 5:
					try {
						Desktop.getDesktop().open(new File("interactive_LED_functionality.mp4"));
					} catch (IOException e) {
						System.out.println("ERROR: Could not find the interactive_LED_functionality.mp4 video file.");
					}
					break;
				}
			}
		});
		
		bottomPanel.add(btnOpen);
		bottomPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		bottomPanel.setOpaque(false);
		
		getContentPane().add(listScroller, BorderLayout.CENTER);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);	
	}
	
	/**
	 * Sets the colour theme of the window using an array of three Colors (see Frame).
	 * 
	 * @param colourTheme New colour theme to change to.
	 */
	public void setColours(Color[] colourTheme) {
		if (colourTheme != null) {
			getContentPane().setBackground(colourTheme[0]);
		} else {
			getContentPane().setBackground(UIManager.getColor("Panel.background"));
		}
		repaint();
	}
	
	/**
	 * Sets the font of the components.
	 * 
	 * @param font New font to change to.
	 */
	public void setFonts(Font font){
		((TitledBorder)((CompoundBorder)((CompoundBorder)listScroller.getBorder()).getInsideBorder()).getOutsideBorder()).setTitleFont(font);
		btnOpen.setFont(font);
		list.setFont(font);
	}
}
