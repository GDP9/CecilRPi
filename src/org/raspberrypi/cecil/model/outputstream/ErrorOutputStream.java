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
	
	private ArrayList<Error> errors;
	
	public ErrorOutputStream() {
		this.errors = new ArrayList<Error>();
	}

	/**
	 * @return the errors
	 */
	public ArrayList<Error> getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(ArrayList<Error> errors) {
		this.errors = errors;
	}
}
