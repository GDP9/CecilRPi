package org.raspberrypi.cecil.model.outputstream;

/**
 * A cecil error is associated with a line number of the input editor and the error message
 *  
 * @author sa10g10
 */
public class OutputError {

	/**
	 * Private Variables
	 */
	private int line;
	private String message;
	
	public OutputError(int line, String message){
		this.line=line;
		this.message = message;
	}
	
	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
