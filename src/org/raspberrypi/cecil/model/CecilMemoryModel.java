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
public class CecilMemoryModel {
	
	static private int[] xReg;
	static private int[] yReg;
	static private int[] acc;
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
	
	/**
	 * Constuctor: initialse memory to store -1
	 */
	public CecilMemoryModel() {
		for(int i = 0 ; i < MEMORY_LENGTH; i++)
			memory[i] = -1;
		
		memory[STACK_PTR] = 908;
		output = new ArrayList<String>();
	}

	public static int[] getxReg() {
		return xReg;
	}

	public static void setxReg(int[] xReg) {
		CecilMemoryModel.xReg = xReg;
	}

	public static int[] getyReg() {
		return yReg;
	}

	public static void setyReg(int[] yReg) {
		CecilMemoryModel.yReg = yReg;
	}

	public static int[] getAcc() {
		return acc;
	}

	public static void setAcc(int[] acc) {
		CecilMemoryModel.acc = acc;
	}

	public static boolean isCarryFlag() {
		return carryFlag;
	}

	public static void setCarryFlag(boolean carryFlag) {
		CecilMemoryModel.carryFlag = carryFlag;
	}

	public static boolean isZeroFlag() {
		return zeroFlag;
	}

	public static void setZeroFlag(boolean zeroFlag) {
		CecilMemoryModel.zeroFlag = zeroFlag;
	}

	public static boolean isNegativeFlag() {
		return negativeFlag;
	}

	public static void setNegativeFlag(boolean negativeFlag) {
		CecilMemoryModel.negativeFlag = negativeFlag;
	}

	public static boolean isSuccessCompile() {
		return successCompile;
	}

	public static void setSuccessCompile(boolean successCompile) {
		CecilMemoryModel.successCompile = successCompile;
	}

	public static ArrayList<String> getOutput() {
		return output;
	}

	public static void setOutput(ArrayList<String> output) {
		CecilMemoryModel.output = output;
	}
	
	
}
