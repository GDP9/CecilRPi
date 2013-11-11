/**
 * 
 */

package org.raspberrypi.cecil.model.test.junit;

import java.io.File;
import java.util.ArrayList;
import org.raspberrypi.cecil.model.Compiler;
import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.pojo.Program;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith (JUnit4.class)
public class TestingCompiler {

	@Test
	public void compiles(){
		Compiler c = getCorrect();
		org.junit.Assert.assertEquals(c.getInstructionField().containsValue("stop"), true);
		org.junit.Assert.assertEquals(c.getSimulator().isSuccessCompile(), true);
		for(String key : c.getParser().getDatafield().keySet()){
			org.junit.Assert.assertEquals(c.getParser().getLabelfield().keySet().contains(key),true);
			org.junit.Assert.assertEquals((int)c.getSimulator().memory[c.getParser().getDatafield().get(key)],(int)c.getParser().getLabelfield().get(key));
		}

	}

	@Test
	public void doesntCompile(){
		Compiler c = getIncorrect();
		org.junit.Assert.assertEquals(c.getInstructionField().containsValue("stop"), false);
		for(String key : c.getParser().getDatafield().keySet()){
			org.junit.Assert.assertEquals(c.getParser().getLabelfield().keySet().contains(key),false);
			org.junit.Assert.assertEquals(c.getSimulator().memory[c.getParser().getDatafield().get(key)], -1);
			org.junit.Assert.assertNull(c.getParser().getLabelfield().get(key));
		}
	}

	private Compiler getIncorrect(){
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
//		input  = new ArrayList<String>();
//
//		input.add(".d2");
//		input.add("insert");
//		input.add("65");
//		input.add(" ");
//		userinput.add(input);

		Program program = new Program(userinput);
		Model m = new Model();

		File sample  = m.programToFile(program, "sample.cecil");
		Compiler c = new Compiler(sample.getAbsolutePath());

		return c;

	}

	private Compiler getCorrect(){
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

		Program program = new Program(userinput);
		Model m = new Model();

		File sample  = m.programToFile(program, "sample.cecil");
		Compiler c = new Compiler(sample.getAbsolutePath());

		return c;
	}

}