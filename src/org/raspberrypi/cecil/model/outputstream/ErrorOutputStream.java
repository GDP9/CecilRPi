package org.raspberrypi.cecil.model.outputstream;

import java.util.ArrayList;

/**
 * <p>CECIL Application ErrorOutputStream</p>
 * <p>Constitutes the stream of error messages collected by the Compiler, Runner, Lexer and Parser</p>
 * <p>Supplies an ArrayList<OutputError> errors</p>
 * 
 * @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Southampton University, United Kingdom
 * @version 1.2
 * 
 */
public class ErrorOutputStream {
	
	private ArrayList<OutputError> errors;
	
	/**
	 * ErrorOutputStream constructor
	 * Initializes the global variable ArrayList<OutputError> errors
	 */
	public ErrorOutputStream() {
		this.errors = new ArrayList<OutputError>();
	}

	/**
	 * Gets the ArrayList<OutputError> errors
	 * @return ArrayList<OutputError> errors
	 */
	public ArrayList<OutputError> getErrors() {
		return errors;
	}

	/**
	 * Sets the ArrayList<OutputError> errors
	 * @param ArrayList<OutputError> errors
	 */
	public void setErrors(ArrayList<OutputError> errors) {
		this.errors = errors;
	}
}