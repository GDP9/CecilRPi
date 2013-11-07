package org.raspberrypi.cecil.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

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
 
public class CecilCompiler {
	
	private CecilParser parser;
	private CecilMemoryModel m;
	
	public CecilCompiler(String filepath) {
		try {
			CommonTokenStream tokens  =  new CommonTokenStream(new CecilLexer(new ANTLRFileStream(filepath)));
			
			parser = new CecilParser(tokens);
			parser.initialseCommandList();
			
			m = parser.getMemoryModel();

			/* Parsing!!! */
			parser.program();
			
			/* type checking for labels */
			for(String key : parser.getDatafield().keySet()) {
				if(parser.getLabelfield().keySet().contains(key)) 
					m.memory[parser.getDatafield().get(key)] = parser.getLabelfield().get(key);
			
				else m.getOutput().add("Error, Label not found");
			}
			
			/* checking for stop instruction */
			if(!parser.getInstructionfield().containsValue("stop")) {
				m.getOutput().add("Program needs a stop instruction!");
			}
			
			if(m.getOutput().isEmpty())
				m.getOutput().add("Successful Compilation");
			
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
	public CecilMemoryModel getMemoryModel() {
		return m;
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