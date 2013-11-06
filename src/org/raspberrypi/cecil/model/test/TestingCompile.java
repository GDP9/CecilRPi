package org.raspberrypi.cecil.model.test;

import java.util.ArrayList;

import org.raspberrypi.cecil.model.Cecil;
import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;

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


		CecilProgram program = new CecilProgram();
		program.setProgramStatements(userinput);


		Cecil c = new Cecil();
		CecilResult r = c.compile(program);
		int m[] = r.getMemoryAllocations();

		for(int i = 0; i < 10; i++)
			System.out.println(" loc "+i+"  "+m[i]);
		
		for(String s: r.getResults())
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


		CecilProgram program = new CecilProgram();
		program.setProgramStatements(userinput);


		Cecil c = new Cecil();
		CecilResult r = c.compile(program);
		int m[] = r.getMemoryAllocations();

		for(int i = 0; i < 10; i++)
			System.out.println(" loc "+i+"  "+m[i]);
		
		for(String s: r.getResults())
			System.out.println(s);
	}
}
