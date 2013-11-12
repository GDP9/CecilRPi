/**
 * 
 */
package org.raspberrypi.cecil.model.outputstream;

import java.util.ArrayList;

/**
 * @author sa10g10
 *
 */
public class ErrorOutputStream {
	
	private ArrayList<OutputError> errors;
	
	public ErrorOutputStream() {
		this.errors = new ArrayList<OutputError>();
	}

	/**
	 * @return the errors
	 */
	public ArrayList<OutputError> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(ArrayList<OutputError> errors) {
		this.errors = errors;
	}
}
