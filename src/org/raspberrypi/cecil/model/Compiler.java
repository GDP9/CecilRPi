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
 * <p>CECIL assembly language Compiler</p>
 * <p>Compiles CECIL Language user input programs</p>
 * <p>Handles compilation errors and adds them to OutputErrorStream list</p>
 * <p>If successful compilation - sends a standard output message to the view</p>
 * 
 * @author Carolina Ferreira (cf4g09)
 * @author Shreeprabha Aggarwal (sa10g10)
 * @author Southampton University, United Kingdom
 * @version 1.3
 * 
 * @date 28/11/2013
 * 
 */
public class Compiler {

	private CecilParser parser;
	private Simulator sim40;
	private StandardOutputStream stdStream;
	private ErrorOutputStream errorStream;

	/**
	 * <p>Compiler's parametric constructor</p>
	 * <p>Syntactic checking: Calls in the CecilParser and CeciLexer to act upon user input</p>
	 * <p>Semantic checking: Checks if all data fields have corresponding label fields</p>
	 * <p>Sending standard output on successful compilation</p>
	 * <p>Meaningful Error Handling</p>
	 * 
	 * @param filepath of user input temp.cecil file
	 * @param Program : Cecil program in where each line is in form of ArrayList of Strings(Columns)
	 */
	public Compiler(String filepath, Program program) {

		try {
			this.parser = new CecilParser(new CommonTokenStream(new CecilLexer(new ANTLRFileStream(filepath))));

			this.parser.initialise();
			this.sim40 = parser.getSimulator();

			/* Parsing */
			try {
				this.parser.program();
				this.sim40.setLineNumbers(program);
				this.stdStream = new StandardOutputStream();
				this.errorStream = this.parser.getErrorStream();
			}

			catch(RecognitionException e) {
				this.errorStream.getErrors().add(new OutputError(e.line, e.getLocalizedMessage()));    
			}

			/* If no parser errors exist, then proceed for type checking */
			if(this.errorStream.getErrors().size() == 0) {
				/* type checking for labels */
				for(int i: parser.getDatafield().keySet()){
					if(parser.getLabelfield().keySet().contains(parser.getDatafield().get(i))) 
						this.sim40.memory[i] = this.parser.getLabelfield().get(this.parser.getDatafield().get(i));

					else { 
						this.errorStream.getErrors().add(new OutputError(program.getDataLine(parser.getDatafield().get(i)), "Data " + parser.getDatafield().get(i) + " has no labelfield"));
						this.sim40.setSuccessCompile(false);
					}
				}
			}

			else {
				this.sim40.setSuccessCompile(false);
			}

			/* Writing successful compilation to StandardOutputStream */
			if(this.sim40.isCompileSuccess()) {
				this.stdStream.getOutput().add("Program has successfully compiled"); 
				this.sim40.setSuccessCompile(true);
			}

		} 
		
		catch (IOException e) {
			this.errorStream.getErrors().add(new OutputError(1, "IOException: cannot read user input"));			
		}
	}

	/**
	 * Gets instruction HashMap <int memory address, String instruction name> from CecilParser
	 * @return HashMap<int, String> instructions
	 */
	public HashMap<Integer, String> getInstructionField() {
		return this.parser.getInstructionfield();
	}

	/**
	 * Gets Simulator object
	 * @return Simulator object
	 */
	public Simulator getSimulator() {
		return this.sim40;
	}

	/**
	 * Gets Parser object
	 * @return Parser object
	 */
	public CecilParser getParser() {
		return this.parser;
	}


	/**
	 * <p>Looks up all related instructions recursively</p>
	 * <p>Not in use at the moment</p>
	 * 
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
	 * Gets StandardOutputStream object
	 * @return StandardOutputStream object
	 */
	public StandardOutputStream getStdStream() {
		return this.stdStream;
	}

	/**
	 * Gets the ErrorOutputStream object
	 * @return ErrorOutputStream object
	 */
	public ErrorOutputStream getErrorStream() {
		return this.errorStream;
	}
}