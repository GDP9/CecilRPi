package org.raspberrypi.cecil.model;

import java.util.ArrayList;

/**
 * CECIL assembly language Memory Model - Static final variables declaration
 * 
 * MIT Open Source License
 * @authors Shreeprabha Aggarwal (sa10g10), Carolina Ferreira (cf4g09)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 01/11/2013
 *
 *  Memory Model :
 *
 *  accumulator : 1026
 *  x-reg : 1027
 *  y-reg : 1028
 */
public class MemoryModel {
	
	static private ArrayList<Integer> xReg;
	static private ArrayList<Integer> yReg;
	static private ArrayList<Integer> acc;
	static private boolean carryFlag;
	static private boolean zeroFlag;
	static private boolean negativeFlag;
	static private ArrayList<String> output;
	static private boolean successCompile;
	
	static final int STATUS_ADDRESS = 1025;
	static final int PROGRAM_COUNTER = 1024;
	static final int ACCUMULATOR_ADDRESS = 1026;
	static final int XREG_ADDRESS = 1027;
	static final int YREG_ADDRESS = 1028;
	static final int MEMORY_LENGTH = 1029;
	static final int STACK_BEGIN = 908;
	static final int STACK_END = 1006;
	static final int STACK_PTR = 1007;
	
	public static int[] memory = new int[MEMORY_LENGTH];
	
	public void updateViewVars() {
		
		if(memory[XREG_ADDRESS] != -1)
			xReg.add(memory[memory[XREG_ADDRESS]]);
		if(memory[YREG_ADDRESS] != -1)
			yReg.add(memory[memory[YREG_ADDRESS]]);
		if(memory[ACCUMULATOR_ADDRESS] != -1)
			acc.add(memory[memory[ACCUMULATOR_ADDRESS]]);
		
		if( (memory[STATUS_ADDRESS] << 0) == 0)
			zeroFlag = false;
		else zeroFlag = true;
		
		if( (memory[STATUS_ADDRESS] << 1) == 0)
			negativeFlag = false;
		else negativeFlag = true;
		
		if( (memory[STATUS_ADDRESS] << 2) == 0)
			carryFlag = false;
		else carryFlag = true;

	}
	
	/**
	 * Constuctor: initialse memory to store -1
	 */
	public MemoryModel() {
		for(int i = 0 ; i < MEMORY_LENGTH; i++)
			memory[i] = -1;
		
		memory[STACK_PTR] = 908;
		output = new ArrayList<String>();
		
		xReg = new ArrayList<Integer>();
		yReg = new ArrayList<Integer>();
		acc = new ArrayList<Integer>();
		
		zeroFlag = carryFlag = negativeFlag = false;
	}

	public static boolean isCarryFlag() {
		return carryFlag;
	}

	public static void setCarryFlag(boolean carryFlag) {
		MemoryModel.carryFlag = carryFlag;
	}

	public static boolean isZeroFlag() {
		return zeroFlag;
	}

	public static void setZeroFlag(boolean zeroFlag) {
		MemoryModel.zeroFlag = zeroFlag;
	}

	public static boolean isNegativeFlag() {
		return negativeFlag;
	}

	public static void setNegativeFlag(boolean negativeFlag) {
		MemoryModel.negativeFlag = negativeFlag;
	}

	public static boolean isSuccessCompile() {
		return successCompile;
	}

	public static void setSuccessCompile(boolean successCompile) {
		MemoryModel.successCompile = successCompile;
	}

	public static ArrayList<String> getOutput() {
		return output;
	}

	public static void setOutput(ArrayList<String> output) {
		MemoryModel.output = output;
	}

	/**
	 * @return the xReg
	 */
	public static ArrayList<Integer> getxReg() {
		return xReg;
	}

	/**
	 * @param xReg the xReg to set
	 */
	public static void setxReg(ArrayList<Integer> xReg) {
		MemoryModel.xReg = xReg;
	}

	/**
	 * @return the yReg
	 */
	public static ArrayList<Integer> getyReg() {
		return yReg;
	}

	/**
	 * @param yReg the yReg to set
	 */
	public static void setyReg(ArrayList<Integer> yReg) {
		MemoryModel.yReg = yReg;
	}

	/**
	 * @return the acc
	 */
	public static ArrayList<Integer> getAcc() {
		return acc;
	}

	/**
	 * @param acc the acc to set
	 */
	public static void setAcc(ArrayList<Integer> acc) {
		MemoryModel.acc = acc;
	}	
}
