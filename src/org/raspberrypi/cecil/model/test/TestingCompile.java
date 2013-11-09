package org.raspberrypi.cecil.model.test;

import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.model.Simulator;
import org.raspberrypi.cecil.pojo.Program;


public class TestingCompile {

	public static void main(String args[]) {

		TestingCompile c = new TestingCompile();
		//c.first();
		c.jr();
	}
	
	/* lacks stop instruction : result should be an error*/
	public void jr() {
		Program program = new Program((new TestInput()).getIncorrectInput());
		Model c = new Model();
		
		c.compile(program);
		Simulator m = c.getSimulator();

		for(int i = 0; i < 10; i++)
			System.out.println(" loc "+i+"  "+m.memory[i]);
		
		for(String s: m.getOutput())
			System.out.println(s);
		
	}
	
	/**
	 * Correct input
	 */
	public void first() {
		Program program = new Program((new TestInput()).getCorrectInput());
		Model c = new Model();
		
		c.compile(program);
		Simulator m = c.getSimulator();

		for(int i = 0; i < 10; i++)
			System.out.println(" loc "+i+"  "+m.memory[i]);
		
		for(String s: m.getOutput())
			System.out.println(s);
	}
}
