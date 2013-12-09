package org.raspberrypi.cecil.model.outputstream;

import java.util.ArrayList;

/**
 * <p>CECIL Application StandardOutputStream</p>
 * <p>Constitutes the stream of standard messages collected by the Compiler and Runner</p>
 * <p>Supplies an ArrayList<string> output</p>
 *
 * @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Southampton University, United Kingdom
 * @version 1.2
 * 
 */
public class StandardOutputStream {

	private ArrayList<String> output;
	
	/**
	 * StandardOutputStream constructor
	 * Initializes the global variable ArrayList<String> output
	 */
	public StandardOutputStream() {
		this.output = new ArrayList<String>();
	}

	/**
	 * Gets the ArrayList<String> output
	 * @return ArrayList<String> output
	 */
	public ArrayList<String> getOutput() {
		return output;
	}

	/**
	 * Sets the ArrayList<String> output
	 * @param ArrayList<String> output
	 */
	public void setOutput(ArrayList<String> output) {
		this.output = output;
	}
}