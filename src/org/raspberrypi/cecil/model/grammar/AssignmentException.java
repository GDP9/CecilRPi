/**
 * 
 */
package org.raspberrypi.cecil.model.grammar;

/**
 * @author Shreeprabha
 *
 */
public class AssignmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private int line;
	
	public AssignmentException(String msg, int line) {
		super();
		this.msg = msg;
		this.line = line;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * @return the line
	 */
	public int getLine() {
		return this.line;
	}

	
}
