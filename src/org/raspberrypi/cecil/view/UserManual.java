package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class UserManual extends JFrame {
	private static final long serialVersionUID = 1L;
	private Frame frame;
	private JScrollPane listScroller;
	private JButton btnOpen;
	private JList list;
	
	public UserManual(Frame frame) {
		this.frame = frame;
		
		getContentPane().setLayout(new BorderLayout());
		setSize(400, 500);
		setTitle("User Manual");
		
		Object[] data = {"1. Accessibility and usability functionalities", "2. Creating a program", "3. Compiling a program", "4. Running a program", "5. Stepping through a program", "6. Interactive LED functionality"};
		list = new JList(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		listScroller = new JScrollPane(list);
		listScroller.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), BorderFactory.createCompoundBorder(new TitledBorder(null, "How-To Videos", TitledBorder.LEADING, TitledBorder.TOP, null, null), new EmptyBorder(5, 5, 5, 5))));
		listScroller.setOpaque(false);
		
		JPanel bottomPanel = new JPanel();
		btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (list.getSelectedIndex()){
				case 0:
					try {
						Desktop.getDesktop().open(new File("accessibility_and_usability_functionalities.mp4"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					try {
						Desktop.getDesktop().open(new File("creating_a_program.mp4"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						Desktop.getDesktop().open(new File("compiling_program.mp4"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						Desktop.getDesktop().open(new File("running_program.mp4"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					try {
						Desktop.getDesktop().open(new File("step_through_program.mp4"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					try {
						Desktop.getDesktop().open(new File("interactive_LED_functionality.mp4"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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

//		JButton btnPlay = new JButton("Play");
//		btnPlay.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
////				String fileUri = getWorkingDirectory()+"\\user_manual_resources\\compiling program.mpeg";
//				URL fileUri = getClass().getResource("/resources/compiling_program.mpeg");
//				try {
//					Desktop.getDesktop().open(new File(fileUri.toString()));
//					System.out.println(fileUri);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					System.out.println("failed");
//					e.printStackTrace();
//				}
//			}
//		});
//		getContentPane().add(btnPlay);
		
//        file you want to play
//              create the media player with the media url
//		Player mediaPlayer;
//		try {
//			// mediaPlayer = Manager.createRealizedPlayer(new
//			// MediaLocator(getClass().getResource("/resources/creating a program.mov")));
//			mediaPlayer = Manager
//					.createRealizedPlayer(new MediaLocator(getClass()
//							.getResource("/resources/compiling_program.mpeg")));
//			Component video = mediaPlayer.getVisualComponent();
//			Component controls = mediaPlayer.getControlPanelComponent();
//			add(video, BorderLayout.CENTER);
//			add(controls, BorderLayout.SOUTH);
//		} catch (NoPlayerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// get components for video and playback controls
//		catch (CannotRealizeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
       
		
		
	}
	
	public void setColours(Color[] colourTheme) {
		getContentPane().setBackground(colourTheme[0]);
		repaint();
	}
	
	public void setFonts(Font font){
		((TitledBorder)((CompoundBorder)((CompoundBorder)listScroller.getBorder()).getInsideBorder()).getOutsideBorder()).setTitleFont(font);
		btnOpen.setFont(font);
		list.setFont(font);
	}
	
//	public String getWorkingDirectory() {
//	    String applicationDir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath(); 
//
//	    if (applicationDir.endsWith(".jar"))
//	    {
//	        applicationDir = new File(applicationDir).getParent();
//	    }
//	    else
//	    {
//	        // Add the path to the class files  
//	        applicationDir += getClass().getName().replace('.', '/');
//
//	        // Step one level up as we are only interested in the 
//	        // directory containing the class files
//	        applicationDir = new File(applicationDir).getParent();
//	    }
//
//	    return applicationDir;
//	}
}
