package org.raspberrypi.cecil.model;

import java.io.IOException;
import java.util.HashMap;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.raspberrypi.cecil.model.grammar.CecilLexer;
import org.raspberrypi.cecil.model.grammar.CecilParser;

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
	
	/**
	 * Parametric Constructor
	 * @param filepath
	 */
	public Compiler(String filepath) {
		try {
			CommonTokenStream tokens  =  new CommonTokenStream(new CecilLexer(new ANTLRFileStream(filepath)));
			
			this.parser = new CecilParser(tokens);
			this.parser.initialse();
			
			this.sim40 = parser.getSimulator();

			/* Parsing */
			parser.program();
			
			/* type checking for labels */
			for(String key : parser.getDatafield().keySet()) {
				if(parser.getLabelfield().keySet().contains(key)) 
					sim40.memory[parser.getDatafield().get(key)] = parser.getLabelfield().get(key);
			
				else sim40.getOutput().add("Error, Label not found");
			}
			
			/* checking for stop instruction */
			if(!parser.getInstructionfield().containsValue("stop")) {
				sim40.getOutput().add("Program needs at least one stop instruction");
			}
			
			if(sim40.getOutput().isEmpty()) {
				sim40.getOutput().add("Successful Compilation"); 
				sim40.setSuccessCompile(true);
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
}