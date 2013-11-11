package org.raspberrypi.cecil.model.test.manual;

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

		input.add(" ");
		input.add("load");
		input.add("d1");
		input.add(";This is a sample comment");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("print");
		input.add(" ");
		input.add(";This is a sample comment");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("printch");
		input.add(" ");
		input.add(";This is a sample comment");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("stop");
		input.add(" ");
		input.add(" ");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(".d1");
		input.add("insert");
		input.add("65");
		input.add(" ");
		userinput.add(input);

		String sample = new String();
		for(ArrayList<String> a: userinput)
			for(int i=0; i<a.size();i++) {
				sample += a.get(i) + " ";
			}

		//System.out.println(sample);

		File samplefile = new File("sample.cecil");
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

		Compiler compiler = new Compiler(samplefile.getAbsolutePath());
		
		for(int j = 0; j < 10; j++)
			System.out.println(" loc "+j+"  "+compiler.getSimulator().memory[j]);
		
		Runner runner = new Runner(compiler, compiler.getSimulator());
		runner.run(0);
		
		for(int h:runner.getSimulator().getAcc())
			System.out.println("fa "+h);

		for(String s: runner.getSimulator().getOutput())
			System.out.println(" result " + s);
		
		for(int k=0; k <1029; k++)
			System.out.println(runner.getSimulator().memory[k]);

	} 
}