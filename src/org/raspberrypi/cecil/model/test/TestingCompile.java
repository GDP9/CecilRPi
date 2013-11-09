package org.raspberrypi.cecil.model.test;

import java.util.ArrayList;

import org.raspberrypi.cecil.model.Cecil;
import org.raspberrypi.cecil.model.MemoryModel;
import org.raspberrypi.cecil.pojo.Program;


public class TestingCompile {

	public static void main(String args[]) {

		TestingCompile c = new TestingCompile();
		//c.first();
		//c.jr();
	}
	
	/* lacks stop instruction : result should be an error*/
	public void jr() {
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
		input.add("65");

		userinput.add(input);


		Program program = new Program(userinput);

		Cecil c = new Cecil();
		c.compile(program);
		MemoryModel m = c.getCompiler().getMemoryModel();

		for(int i = 0; i < 10; i++)
			System.out.println(" loc "+i+"  "+m.memory[i]);
		
		for(String s: m.getOutput())
			System.out.println(s);

	}
	
	public void first() {
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


		Program program = new Program(userinput);

		Cecil c = new Cecil();
		c.compile(program);
		MemoryModel m = c.getCompiler().getMemoryModel();

		for(int i = 0; i < 10; i++)
			System.out.println(" loc "+i+"  "+m.memory[i]);
		
		for(String s: m.getOutput())
			System.out.println(s);
	}
}
