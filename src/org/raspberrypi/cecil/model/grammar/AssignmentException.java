/**
 * This is the exception class
 */
package org.raspberrypi.cecil.model.grammar;

/**
 * CECIL Exception class that defines the incorrect assignment syntax exception
 * 
 * @author Shreeprabha Aggarwal (sa10g10)
 * @author Southampton University, United Kingdom
 * 
 * @version 1.1
 * 
 * @date 28/11/2013
 * 
 */
public class AssignmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private int line;

	/**
	 * Default parametric Constructor
	 * @param msg : Error message
	 * @param line: Error line to give enable meaningful error highlighting
	 */
	public AssignmentException(String msg, int line) {
		super();
		this.msg = msg;
		this.line = line;
	}

	/**
	 * Gets the Error Message
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * Gets the Error line number on the input editor
	 * @return the line
	 */
	public int getLine() {
		return this.line;
	}

}