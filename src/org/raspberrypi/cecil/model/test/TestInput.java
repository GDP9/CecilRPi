/**
 * 
 */
package org.raspberrypi.cecil.model.test;

import java.util.ArrayList;

/**
 * @author Shreeprabha
 *
 */
public class TestInput {
	
	private ArrayList<ArrayList<String>> correctInput;
	private ArrayList<ArrayList<String>> incorrectInput;
	
	public TestInput() {
		incorrectInput = new ArrayList<ArrayList<String>>();
		correctInput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();
		
		input.add(" ");
		input.add("load");
		input.add("d1");
		input.add(";This is a sample comment");
		correctInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("print");
		input.add(" ");
		input.add(";This is a sample comment");
		correctInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("printch");
		input.add(" ");
		input.add(";This is a sample comment");
		correctInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("stop");
		input.add(" ");
		input.add(" ");
		correctInput.add(input);
		input  = new ArrayList<String>();

		input.add(".d1");
		input.add("insert");
		input.add("65");
		input.add(" ");
		correctInput.add(input);
		
		/**
		 * Incorrect input initialisation
		 */
		input.add(" ");
		input.add("load");
		input.add("d1");
		input.add(";This is a sample comment");
		incorrectInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("print");
		input.add(" ");
		input.add(";This is a sample comment");
		incorrectInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("stop");
		input.add(" ");
		input.add(" ");
		incorrectInput.add(input);
		input  = new ArrayList<String>();

		input.add(".d1");
		input.add("add");
		input.add("65");
		input.add(" ");
		incorrectInput.add(input);
	}
	
	public ArrayList<ArrayList<String>> getCorrectInput() {
		return this.correctInput;
	}
	
	public ArrayList<ArrayList<String>> getIncorrectInput() {
		return this.incorrectInput;
	}
}
