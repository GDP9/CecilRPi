package org.raspberrypi.cecil.model.test.junit;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.raspberrypi.cecil.model.Compiler;
import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.model.Runner;
import org.raspberrypi.cecil.model.Simulator;
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
public class TestingRunner {

	private ArrayList<String> getbitwise_algebraic_operators() {
		ArrayList<String> bitwise_algebraic_Operators = new ArrayList<String>();
		bitwise_algebraic_Operators.add("add");
		bitwise_algebraic_Operators.add("sub");
		bitwise_algebraic_Operators.add("and");
		bitwise_algebraic_Operators.add("or");
		bitwise_algebraic_Operators.add("xor");
		return bitwise_algebraic_Operators;
	}

	private ArrayList<String> getCarryOperators(){
		ArrayList<String> carryOperators = new ArrayList<String>();
		carryOperators.add("cclear");
		carryOperators.add("cset");
		return carryOperators;
	}


	private ArrayList<String> getRegisterOperators(){
		ArrayList<String> accOperators = new ArrayList<String>();
		accOperators.add("xdec");
		accOperators.add("xinc");
		accOperators.add("ydec");
		accOperators.add("yinc");
		return accOperators;
	}

	/**
	 * Set of tests which run correctly.
	 */
	@Test
	public void runs(){
		String resultPrint = "";
		String resulPrintCh = "";
		String resulPrintB = "";
		for(String s : getbitwise_algebraic_operators()){
			Runner r = getCorrect(s, " ", " ");
			r.run(0);
			Simulator sim40 = r.getSimulator();
			switch (s) {
			case "add":
				resultPrint = "65";
				resulPrintCh = Character.toString((char)65);
				resulPrintB = Integer.toBinaryString(65);
				break;

			case "sub":
				resultPrint = "61";
				resulPrintCh= Character.toString((char)61);
				resulPrintB = Integer.toBinaryString(61);
				break;

			case "and":
				resultPrint = "2";
				resulPrintCh= Character.toString((char)2);
				resulPrintB = Integer.toBinaryString(2);
				break;

			case "or":
				resultPrint = "63";
				resulPrintCh= Character.toString((char)63);
				resulPrintB = Integer.toBinaryString(63);
				break;

			case "xor":
				resultPrint = "61";
				resulPrintCh= Character.toString((char)61);
				resulPrintB = Integer.toBinaryString(61);
				break;
			}
			org.junit.Assert.assertEquals(resultPrint, r.getStdStream().getOutput().get(0));
			org.junit.Assert.assertEquals(resulPrintCh, r.getStdStream().getOutput().get(1));
			org.junit.Assert.assertEquals(resulPrintB, r.getStdStream().getOutput().get(2));		
		}

		for(String s: getCarryOperators()){
			Runner r = getCorrect(" ", s, " ");
			r.run(0);
			Simulator sim40 = r.getSimulator();
			if(s.equals("cclear"))
				org.junit.Assert.assertEquals(false, sim40.isCarryFlag());
			else
				org.junit.Assert.assertEquals(true, sim40.isCarryFlag());
		}

		for(String s: getRegisterOperators()){
			Runner r = getCorrect(" ", " ", s);
			r.run(0);
			Simulator sim40 = r.getSimulator();
			boolean carry = false;
			boolean negative = false;
			boolean zero = false;

			if(sim40.getAcc().get(sim40.getAcc().size()-1)<=0){
				carry=false;
				negative=true;
				zero=true;
			}
			else if(sim40.getAcc().get(sim40.getAcc().size()-1)>1023){
				carry=true;
				negative=false;
				zero=false;
			}
			else{
				carry=false;
				negative=false;
				zero=false;
			}
			
			switch (s) {
			case "xdec":
				if((sim40.getXReg().get(sim40.getXReg().size()-1)<=0)){
					carry = false;
					negative = true;
					zero=true;
				}
				else{
					carry = false;
					negative = false;
					zero=false;
				}
				break;

			case "ydec":
				if((sim40.getYReg().get(sim40.getYReg().size()-1)<=0)){
					carry = false;
					negative = true;
					zero=true;
				}
				else{
					carry = false;
					negative = false;
					zero=false;
				}
				break;

			case "xinc":
				if ((sim40.getXReg().get(sim40.getXReg().size()-1)>1023)){
					carry = true;
					negative = false;
					zero=false;
				}
				else{
					carry = false;
					negative = false;
					zero=false;
				}
				break;
				
			case "yinc":
				if ((sim40.getYReg().get(sim40.getYReg().size()-1)>1023)){
					carry = true;
					negative = false;
					zero=false;
				}
				else{
					carry = false;
					negative = false;
					zero=false;
				}
				break;
			}

			System.out.println("helloo   " + (sim40.getXReg().get(sim40.getXReg().size()-1)));
			System.out.println("zerooo  " + zero);
			org.junit.Assert.assertEquals(carry, sim40.isCarryFlag());
			//org.junit.Assert.assertEquals(negative, sim40.isNegativeFlag());
			org.junit.Assert.assertEquals(zero, sim40.isZeroFlag());
		}
		//org.junit.Assert.assertEquals(0, (int)(sim40.getXReg().get(sim40.getXReg().size()-1)));

	}

	/**
	 * Correct input used in compiler test.
	 * @return Compiler object using correct input
	 */
	private Runner getCorrect(String op, String cOp, String regOp){
		ArrayList<ArrayList<String>> userinput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();

		input.add(".start");
		input.add("load");
		input.add("num1");
		input.add(";");
		userinput.add(input);

		if(!op.equals(" ")){
			input = new ArrayList<String>();
			input.add(" ");
			input.add(op);
			input.add("num2");
			input.add(";");
			userinput.add(input);
		}

		input = new ArrayList<String>();
		input.add(" ");
		input.add(cOp);
		input.add(" ");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(" ");
		input.add(regOp);
		input.add(" ");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(" ");
		input.add("print");
		input.add(" ");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(" ");
		input.add("printch");
		input.add(" ");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(" ");
		input.add("printb");
		input.add(" ");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(" ");
		input.add("stop");
		input.add(" ");
		input.add(";");
		userinput.add(input);

		input = new ArrayList<String>();
		input.add(".num1");
		input.add("insert");
		input.add("63");
		input.add(";");
		userinput.add(input);

		if(!op.equals(" ")){
			input = new ArrayList<String>();
			input.add(".num2");
			input.add("insert");
			input.add("2");
			input.add(";");
			userinput.add(input);
		}


		Program program = new Program(userinput);
		Model m = new Model();

		File sample  = m.programToFile(program, "sample.cecil");
		Compiler c = new Compiler(sample.getAbsolutePath(), program);
		Runner r = new Runner(c);

		return r;
	}

}