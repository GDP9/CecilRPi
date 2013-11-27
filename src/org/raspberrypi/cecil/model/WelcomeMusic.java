/**
 * 
 */
package org.raspberrypi.cecil.model;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * @author Shreeprabha
 *
 */
public class WelcomeMusic {


	public void music() {
		File soundFile = new File("tada.wav");
		AudioInputStream audioInputStream = null;
		try
		{
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		AudioFormat	audioFormat = audioInputStream.getFormat();
		SourceDataLine	line = null;
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);

			/*
			  The line is there, but it is not yet ready to
			  receive audio data. We have to open the line.
			 */
			line.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		line.start();
		int	nBytesRead = 0;
		byte[]	abData = new byte[128000];
		while (nBytesRead != -1)
		{
			try
			{
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (nBytesRead >= 0)
			{
				int	nBytesWritten = line.write(abData, 0, nBytesRead);
			}
		}
		line.drain();
		line.close();

		/* Welcome note */
		//		m.noteOn(62, 64);
		//		Thread.sleep(1000);
		//		m.noteOn(64, 64);
		//		Thread.sleep(500);
		//		m.noteOn(62, 64);
		//		Thread.sleep(1000);
		//		m.noteOn(64, 64);
		//		Thread.sleep(500);
		//		m.noteOn(62, 64);
		//		Thread.sleep(1000);
		//		m.noteOn(64, 64);
	}

}
