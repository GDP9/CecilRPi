package org.raspberrypi.cecil.model.outputstream;

/**
 * <p>CECIL Application OutputError
 * <p>Constitutes CECIL error associated with<ul>
 * 	<li>line number of the input editor</li>
 * 	<li>error message collected from Compiler, Runner, Lexer and Parser</li><ul>
 * </p>
 *  
 * @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Southampton University, United Kingdom
 * @version 1.2
 *  
 */
public class OutputError {

	private int line;
	private String message;
	
	/**
	 * OutputError parametric constructor
	 * @param int line number of input editor
	 * @param String error message
	 */
	public OutputError(int line, String message){
		this.line=line;
		this.message = message;
	}
	
	/**
	 * Gets the line number in the user input program
	 * @return int line number
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * Sets the line number in the input user program
	 * @param int line number
	 */
	public void setLine(int line) {
		this.line = line;
	}
	
	/**
	 * Gets the error message collected from the Compiler, Runner, Lexer and Parser
	 * @return String error message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the error message 
	 * @param String error message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}