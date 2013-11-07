package org.raspberrypi.cecil.model.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.raspberrypi.cecil.model.CecilLexer;
import org.raspberrypi.cecil.model.MemoryModel;
import org.raspberrypi.cecil.model.CecilParser;
import org.raspberrypi.cecil.model.Runner;
import org.raspberrypi.cecil.model.Compiler;

public class TestingStepThrough {

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

		input.add("");
		input.add("stop");
		input.add("");

		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(".d1");
		input.add("insert");
		input.add("65");

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

		Compiler compiler = new Compiler(samplefile.getAbsolutePath());
		Runner runner = new Runner(compiler, compiler.getMemoryModel());
		runner.stepthrough(0);
		runner.stepthrough(1);
		runner.stepthrough(2);
		runner.stepthrough(3);
		
		for(String s: runner.getMemoryModel().getOutput())
			System.out.println("step through result " +s);
		
	}
}
