/**
 * 
 */
package org.raspberrypi.cecil.grammar;

import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

/**
 * @author Shreeprabha
 *
 */
public class CecilCompiler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CommonTokenStream tokens  =  new CommonTokenStream(new CecilLexer( new ANTLRFileStream("src/org/raspberrypi/cecil/grammar/sampleinput")));
			CecilParser parser = new CecilParser(tokens);
			CecilCompiler c = new CecilCompiler();
			parser.program();
			for(int i = 0; i < 15; i++) System.out.println("loc "+i+"  val "+parser.memory[i]);
			System.out.println();
			for(String key : parser.datafield.keySet()) {
				if(parser.labelfield.keySet().contains(key)) 
					parser.memory[parser.datafield.get(key)] = c.recursive(parser.memory,parser.labelfield.get(key));
					
				else System.out.println("Error, Label not found");
			}
			for(int i = 0; i < 15; i++) System.out.println("loc "+i+"  val "+parser.memory[i]);
		} catch (IOException | RecognitionException e) {
			e.printStackTrace();
		}

	}
	
	public int recursive(int[] memory, int key) {
		Integer i = memory[key];
		while(i == null){
			memory[key] = recursive(memory, key + 2);
			i = memory[key];
		}
		return i;
	}

}
