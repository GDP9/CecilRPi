package org.raspberrypi.cecil.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JFrame;

public class UserManual extends JFrame {
	private static final long serialVersionUID = 1L;
	
	
	public UserManual() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.DARK_GRAY);
		setSize(960, 540);
		setTitle("User Manual");
		setLayout(new BorderLayout());

        //file you want to play
              //create the media player with the media url
        Player mediaPlayer;
		try {
			//mediaPlayer = Manager.createRealizedPlayer(new MediaLocator(getClass().getResource("/resources/creating a program.mov")));
			mediaPlayer = Manager.createRealizedPlayer(new MediaLocator(getClass().getResource("/resources/lunarphases.mov")));
			Component video = mediaPlayer.getVisualComponent();
	        Component controls = mediaPlayer.getControlPanelComponent();
	        add(video,BorderLayout.CENTER);
	        add(controls,BorderLayout.SOUTH);
		} catch (NoPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //get components for video and playback controls
 catch (CannotRealizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
		
		
	}
}
