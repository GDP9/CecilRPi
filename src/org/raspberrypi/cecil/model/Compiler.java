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
 * 
 * MIT Open Source License
 * @authors Shreeprabha Aggarwal (sa10g10), Carolina Ferreira (cf4g09)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 01/11/2013
 */

public class Compiler {

	private CecilParser parser;
	private Simulator sim40;
	private StandardOutputStream stdStream;
	private ErrorOutputStream errorStream;

	/**
	 * Parametric Constructor
	 * @param filepath
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
					this.errorStream.getErrors().add(new OutputError(program.getDataLine(parser.getDatafield().get(i))+1, "Data " + parser.getDatafield().get(i) + " has no labelfield"));
					this.sim40.setSuccessCompile(false);
				}
			}
			/* checking for stop instruction */
//			if(!parser.getInstructionfield().containsValue("stop")) {
//				this.errorStream.getErrors().add(new OutputError(program.getProgramStatements().size(), "Program needs at least one stop instruction"));
//				this.sim40.setSuccessCompile(false);
//			}

			/* Writing successful compilation to std stream */
			if(this.sim40.isCompileSuccess()) {
				this.stdStream.getOutput().add("Program has successfully compiled"); 
				this.sim40.setSuccessCompile(true);
			}
			
		} catch (IOException | RecognitionException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, String> getInstructionField() {
		return parser.getInstructionfield();
	}


	/**
	 * 
	 * @return
	 */
	public Simulator getSimulator() {
		return this.sim40;
	}

	/**
	 * 
	 * @return
	 */
	public CecilParser getParser() {
		return parser;
	}


	/**
	 * 
	 * @param memory
	 * @param key
	 * @return
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
	 * @return the stdStream
	 */
	public StandardOutputStream getStdStream() {
		return stdStream;
	}

	/**
	 * @return the errorStream
	 */
	public ErrorOutputStream getErrorStream() {
		return errorStream;
	}
}