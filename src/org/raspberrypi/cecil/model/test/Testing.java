package org.raspberrypi.cecil.model.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.Compiler;
import org.raspberrypi.cecil.model.Runner;

public class Testing {

	public static void main(String args[]) {
		ArrayList<ArrayList<String>> userinput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();

		input.add("");
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

		//System.out.println(sample);

		File samplefile = new File("sample.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(samplefile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sample);
			bw.close();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}

		int i = 0;

		Compiler compiler = new Compiler(samplefile.getAbsolutePath());
		
		for(int j = 0; j < 10; j++)
			System.out.println(" loc "+j+"  "+compiler.getMemoryModel().memory[j]);
		
		
		Runner runner = new Runner(compiler, compiler.getMemoryModel());

		runner.run(0);
		
		for(int h:runner.getMemoryModel().getAcc())
			System.out.println("fasfasf "+h);
//
//		for(String s: runner.getMemoryModel().getOutput())
//			System.out.println(" result " + s);
//		
//		for(int k=0; k <1029; k++)
//			System.out.println(runner.getMemoryModel().memory[k]);

	} 
}