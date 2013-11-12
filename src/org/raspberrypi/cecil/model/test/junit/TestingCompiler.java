package org.raspberrypi.cecil.model.test.junit;

import java.io.File;
import java.util.ArrayList;
import org.raspberrypi.cecil.model.Compiler;
import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.pojo.Program;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
*
* CECIL Compiler JUnit tests.
* Performs JUnit assertion tests to Compiler.
* Takes correct and incorrect input.
* Uses JUnit4.
*
* The MIT License (MIT)
* Copyright (c) 2013 Southampton University group GDP9
*
* @authors Carolina Ferreira (cf4g09)
* Southampton University, United Kingdom
* @version 1.1
* 
* @date 11/11/2013
*
*/
@RunWith (JUnit4.class)
public class TestingCompiler {

	/**
	 * Set of tests which compile correctly.
	 */
	@Test
	public void compiles(){
		Compiler c = getCorrect();
		org.junit.Assert.assertEquals(c.getSimulator().isCompileSuccess(), true);
		//org.junit.Assert.assertEquals(c.getInstructionField().containsValue("stop"), true);
		for(int key : c.getParser().getDatafield().keySet()){
			org.junit.Assert.assertEquals(c.getParser().getLabelfield().keySet().contains(c.getParser().getDatafield().get(key)),true);
			org.junit.Assert.assertEquals((int)c.getSimulator().memory[key],(int)c.getParser().getLabelfield().get(c.getParser().getDatafield().get(key)));
		}

	}

//	/**
//	 * Set of tests which do not compile.
//	 */
//	@Test
//	public void doesntCompile(){
//		Compiler c = getIncorrect();
//		org.junit.Assert.assertEquals(c.getSimulator().isCompileSuccess(), false);
//		org.junit.Assert.assertEquals(c.getInstructionField().containsValue("stop"), false);
//		for(int key : c.getParser().getDatafield().keySet()){
//			org.junit.Assert.assertEquals(c.getParser().getLabelfield().keySet().contains(c.getParser().getDatafield().get(key)),false);
//			org.junit.Assert.assertEquals(c.getSimulator().memory[key], -1);
//			org.junit.Assert.assertNull(c.getParser().getLabelfield().get(c.getParser().getDatafield().get(key)));
//		}
//	}

	/**
	 * Incorrect input used in compiler test.
	 * @return Compiler object using incorrect input
	 */
	private Compiler getIncorrect(){
		ArrayList<ArrayList<String>> userinput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();

		input.add("");
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
//		input  = new ArrayList<String>();
//
//		input.add(".d1");
//		input.add("insert");
//		input.add("65");
//		input.add(" ");
//		userinput.add(input);

		Program program = new Program(userinput);
		Model m = new Model();

		File sample  = m.programToFile(program, "sample.cecil");
		Compiler c = new Compiler(sample.getAbsolutePath(), program);

		return c;

	}

	/**
	 * Correct input used in compiler test.
	 * @return Compiler object using correct input
	 */
	private Compiler getCorrect(){
		ArrayList<ArrayList<String>> userinput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();

	/*	
		input.add(".start");
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
		userinput.add(input);*/
		
		input.add(".start");
		input.add("load");
		input.add("num1");
		input.add(";");
		userinput.add(input);
		
		input = new ArrayList<String>();
		input.add(" ");
		input.add("add");
		input.add("num2");
		input.add(";");
		userinput.add(input);
		
//		input = new ArrayList<String>();
//		input.add(" ");
//		input.add("stop");
//		input.add(" ");
//		input.add(";");
//		userinput.add(input);
//		
		input = new ArrayList<String>();
		input.add(" ");
		input.add("printch");
		input.add(" ");
		input.add(";");
		userinput.add(input);
		
//		input = new ArrayList<String>();
//		input.add(" ");
//		input.add("stop");
//		input.add(" ");
//		input.add(";");
//		userinput.add(input);
//		
		input = new ArrayList<String>();
		input.add(".num1");
		input.add("insert");
		input.add("63");
		input.add(";");
		userinput.add(input);
		
		input = new ArrayList<String>();
		input.add(".num2");
		input.add("insert");
		input.add("2");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(" ");
		input.add("stop");
		input.add(" ");
		input.add(";");
		userinput.add(input);

		
		Program program = new Program(userinput);
		Model m = new Model();

		File sample  = m.programToFile(program, "sample.cecil");
		Compiler c = new Compiler(sample.getAbsolutePath(), program);

		return c;
	}

}