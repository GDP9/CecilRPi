package org.raspberrypi.cecil.model;

import java.io.IOException;
import java.util.HashMap;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.raspberrypi.cecil.model.grammar.CecilLexer;
import org.raspberrypi.cecil.model.grammar.CecilParser;
import org.raspberrypi.cecil.model.outputstream.OutputError;
import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;
import org.raspberrypi.cecil.pojo.Program;

/**
 * CECIL assembly language Compiler
 * Compiles CECIL Language user input programs
 * Handles compilation errors and adds them to StreamOutputError list
 * If successful compilation - sends a standard output message to the view
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

public class Compiler {

	private CecilParser parser;
	private Simulator sim40;
	private StandardOutputStream stdStream;
	private ErrorOutputStream errorStream;

	/**
	 * Compiler parametric constructor
	 * Calls in the parser and lexer to act upon user input
	 * Checks if all data fields have corresponding label fields
	 * Sending standard output on successful compilation
	 * @param filepath of user input
	 * @param Program object
	 */
	public Compiler(String filepath, Program program) {
		try {
			this.parser = new CecilParser(new CommonTokenStream(new CecilLexer(new ANTLRFileStream(filepath))));
			this.parser.initialise();
			this.sim40 = parser.getSimulator();

			/* Parsing */
			this.parser.program();
			this.sim40.setLineNumbers(program);
			this.stdStream = new StandardOutputStream();
			this.errorStream = this.parser.getErrorStream();

			/* type checking for labels */
			for(int i: parser.getDatafield().keySet()){
				if(parser.getLabelfield().keySet().contains(parser.getDatafield().get(i))) 
					this.sim40.memory[i] = this.parser.getLabelfield().get(this.parser.getDatafield().get(i));

				else { 
					this.errorStream.getErrors().add(new OutputError(program.getDataLine(parser.getDatafield().get(i)), "Data " + parser.getDatafield().get(i) + " has no labelfield"));
					this.sim40.setSuccessCompile(false);
				}
			}

			/* Writing successful compilation to StandardOutputStream */
			if(this.sim40.isCompileSuccess()) {
				this.stdStream.getOutput().add("Program has successfully compiled"); 
				this.sim40.setSuccessCompile(true);
			}

		} catch (IOException | RecognitionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets instruction HashMap <int memory adress, String instruction name> from parser
	 * @return HashMap<int, String> instructions
	 */
	public HashMap<Integer, String> getInstructionField() {
		return parser.getInstructionfield();
	}

	/**
	 * Gets Simulator object
	 * @return Simulator object
	 */
	public Simulator getSimulator() {
		return this.sim40;
	}

	/**
	 * gets Parser object
	 * @return Parser object
	 */
	public CecilParser getParser() {
		return parser;
	}


	/**
	 * Looks up all related instructions recursively.
	 * Not in use at the moment.
	 * @param int[] memory
	 * @param int key in memory of instruction, data field
	 * @return memory address related to argument
	 */
	public int recursive(int[] memory, int key) {
		int i = memory[key];
		while(i == -1){
			memory[key] = recursive(memory, key + 2);
			i = memory[key];
		}
		return i;
	}

	/**
	 * gets StandardOutputStream object
	 * @return StandardOutputStream object
	 */
	public StandardOutputStream getStdStream() {
		return stdStream;
	}

	/**
	 * gets the ErrorOutputStream object
	 * @return ErrorOutputStream object
	 */
	public ErrorOutputStream getErrorStream() {
		return errorStream;
	}
}