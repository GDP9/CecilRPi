package org.raspberrypi.cecil.grammar.test;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.raspberrypi.cecil.grammar.CecilLexer;
import org.raspberrypi.cecil.grammar.CecilMemoryModel;
import org.raspberrypi.cecil.grammar.CecilParser;
import org.raspberrypi.cecil.grammar.CecilRunner;

public class Testing {

	public static void main(String args[]) {
		ArrayList<ArrayList<String>> userinput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();
		
		input.add(".start");
		input.add("load");
		input.add("d1");
		
		userinput.add(input);
		input  = new ArrayList<String>();
		
		input.add("");
		input.add("print");
		input.add("");
		
		userinput.add(input);
		input  = new ArrayList<String>();
		
		input.add("");
		input.add("printch");
		input.add("");
		
		userinput.add(input);
		input  = new ArrayList<String>();
		
		input.add(".d1");
		input.add("insert");
		input.add("3");
		
		userinput.add(input);
		
		String sample = new String();
		for(ArrayList a: userinput)
			for(int i=0; i<a.size();i++) {
				sample += a.get(i) + " ";
			}
		
		System.out.println(sample);
		
		File samplefile = new File("sample.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(samplefile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sample);
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		CommonTokenStream tokens;
		try {
			tokens = new CommonTokenStream(new CecilLexer( new ANTLRFileStream(samplefile.getAbsolutePath())));
			CecilParser parser = new CecilParser(tokens);
            parser.initialseCommandList();
			
			CecilMemoryModel m = parser.getMemoryModel();

			/* Parsing!!! */
			parser.program();
			
			/* type checking for labels */
			for(String key : parser.getDatafield().keySet()) {
				if(parser.getLabelfield().keySet().contains(key)) 
					m.memory[parser.getDatafield().get(key)] = parser.getLabelfield().get(key);
			
				else parser.getErrors().add("Error, Label not found");
			}
			
			int i = 0;
			while(m.memory[i] != -1)
				System.out.println(i+" "+m.memory[i++]);
			
			CecilRunner runner = new CecilRunner(parser, m);
			for()			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
