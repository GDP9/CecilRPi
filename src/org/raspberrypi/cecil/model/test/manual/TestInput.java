package org.raspberrypi.cecil.model.test.manual;

import java.util.ArrayList;

/**
*
* CECIL TestInput model manual tests
* Tests are supplied to ProgramFileConverter.java 
* Produces Incorrect and correct inputs
*
* The MIT License (MIT)
* Copyright (c) 2013 Southampton University group GDP9
* 
* @authors Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
* Southampton University, United Kingdom
* @version 1.2
* 
* @date 14/11/2013
*
*/
public class TestInput {
	
	private ArrayList<ArrayList<String>> correctInput;
	private ArrayList<ArrayList<String>> incorrectInput;
	
	/**
	 * TestInput constructor
	 * Sets incorrect and correct program inputs to be used by ProgramFileConverter.java 
	 */
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
		input  = new ArrayList<String>();
		input.add(" ");
		input.add("load");
		input.add("d1");
		input.add(" ");
		incorrectInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("print");
		input.add(" ");
		input.add(";This is a sample comment");
		incorrectInput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("printch");
		input.add(" ");
		input.add(";This is a sample comment");
		incorrectInput.add(input);
		input  = new ArrayList<String>();

//		input.add(" ");
//		input.add("stop");
//		input.add(" ");
//		input.add(" ");
//		incorrectInput.add(input);
//		input  = new ArrayList<String>();

		input.add(".d1");
		input.add("insert");
		input.add("65");
		input.add(" ");
		incorrectInput.add(input);
	}
	
	/**
	 * Gets the correct program input
	 * @return ArrayList<ArrayList<String>> correctInput
	 */
	public ArrayList<ArrayList<String>> getCorrectInput() {
		return this.correctInput;
	}
	
	/**
	 * Gets the incorrect program input
	 * @return ArrayList<ArrayList<String>> incorrectInput
	 */
	public ArrayList<ArrayList<String>> getIncorrectInput() {
		return this.incorrectInput;
	}
}
