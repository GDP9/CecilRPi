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
public class Simulator {
	
	private ArrayList<Integer> xReg;
	private ArrayList<Integer> yReg;
	private ArrayList<Integer> acc;
	private boolean carryFlag;
	private boolean zeroFlag;
	private boolean negativeFlag;
	private ArrayList<String> output;
	private boolean successCompile;
	
	static final int STATUS_ADDRESS = 1025;
	static final int PROGRAM_COUNTER = 1024;
	static final int ACCUMULATOR_ADDRESS = 1026;
	static final int XREG_ADDRESS = 1027;
	static final int YREG_ADDRESS = 1028;
	static final int MEMORY_LENGTH = 1029;
	static final int STACK_BEGIN = 908;
	static final int STACK_END = 1006;
	static final int STACK_PTR = 1007;
	
	public int[] memory = new int[MEMORY_LENGTH];
	
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
	public Simulator() {
		for(int i = 0 ; i < MEMORY_LENGTH; i++)
			memory[i] = -1;
		
		memory[STACK_PTR] = 908;
		output = new ArrayList<String>();
		
		xReg = new ArrayList<Integer>();
		yReg = new ArrayList<Integer>();
		acc = new ArrayList<Integer>();
		
		zeroFlag = carryFlag = negativeFlag = false;
	}

	public boolean isCarryFlag() {
		return carryFlag;
	}

	public void setCarryFlag(boolean carryFlag) {
		this.carryFlag = carryFlag;
	}

	public boolean isZeroFlag() {
		return zeroFlag;
	}

	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
	}

	public boolean isNegativeFlag() {
		return negativeFlag;
	}

	public void setNegativeFlag(boolean negativeFlag) {
		this.negativeFlag = negativeFlag;
	}

	public boolean isSuccessCompile() {
		return this.successCompile;
	}

	public void setSuccessCompile(boolean successCompile) {
		this.successCompile = successCompile;
	}

	public ArrayList<String> getOutput() {
		return this.output;
	}

	public void setOutput(ArrayList<String> output) {
		this.output = output;
	}

	/**
	 * @return the xReg
	 */
	public ArrayList<Integer> getxReg() {
		return this.xReg;
	}

	/**
	 * @param xReg the xReg to set
	 */
	public void setxReg(ArrayList<Integer> xReg) {
		this.xReg = xReg;
	}

	/**
	 * @return the yReg
	 */
	public ArrayList<Integer> getyReg() {
		return this.yReg;
	}

	/**
	 * @param yReg the yReg to set
	 */
	public void setyReg(ArrayList<Integer> yReg) {
		this.yReg = yReg;
	}

	/**
	 * @return the acc
	 */
	public ArrayList<Integer> getAcc() {
		return this.acc;
	}

	/**
	 * @param acc the acc to set
	 */
	public void setAcc(ArrayList<Integer> acc) {
		this.acc = acc;
	}	
}