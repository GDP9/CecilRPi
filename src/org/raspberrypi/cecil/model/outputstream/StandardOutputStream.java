/**
 * 
 */
package org.raspberrypi.cecil.model.outputstream;

import java.util.ArrayList;

/**
 * @author sa10g10
 *
 */
public class StandardOutputStream {

	private ArrayList<String> output;
	
	/**
	 * @param output
	 */
	public StandardOutputStream() {
		this.output = new ArrayList<String>();
	}

	/**
	 * @return the output
	 */
	public ArrayList<String> getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(ArrayList<String> output) {
		this.output = output;
	}
}
