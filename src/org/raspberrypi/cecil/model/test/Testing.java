package org.raspberrypi.cecil.model.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.CecilCompiler;
import org.raspberrypi.cecil.model.CecilRunner;

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

		CecilCompiler compiler = new CecilCompiler(samplefile.getAbsolutePath());
		CecilRunner runner = new CecilRunner(compiler, compiler.getMemoryModel());

		runner.run(0);

		for(String s: runner.getMemoryModel().getOutput())
			System.out.println(" result " + s);
		
		for(int k=0; i <20; k++)
			System.out.println(runner.getMemoryModel().memory[k]);

	} 
}