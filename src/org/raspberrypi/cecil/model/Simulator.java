package org.raspberrypi.cecil.model;

import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.InstructionList;
import org.raspberrypi.cecil.pojo.Program;

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

	/*
	 * View output variables
	 */
	private ArrayList<Integer> xReg;
	private ArrayList<Integer> yReg;
	private ArrayList<Integer> acc;
	private boolean carryFlag;
	private boolean zeroFlag;
	private boolean negativeFlag;
	private boolean successCompile;
	/*
	 * Model variables
	 */
	static final int PROGRAM_COUNTER = 1024;
	static final int STATUS_ADDRESS = 1025;
	static final int ACCUMULATOR_ADDRESS = 1026;
	static final int XREG_ADDRESS = 1027;
	static final int YREG_ADDRESS = 1028;
	static final int MEMORY_LENGTH = 1029;
	static final int STACK_BEGIN = 908;
	static final int STACK_END = 1006;
	static final int STACK_PTR = 1007;

	public int[] memory = new int[MEMORY_LENGTH]; 
	public int[] lines = new int[MEMORY_LENGTH];

	/**
	 * Constuctor: initialse memory to store -1
	 */
	public Simulator() {
		for(int i = 0 ; i < MEMORY_LENGTH; i++)
			memory[i] = -1;

		memory[STACK_PTR] = 908;
		memory[STATUS_ADDRESS] = 0;

		xReg = new ArrayList<Integer>();
		yReg = new ArrayList<Integer>();
		acc = new ArrayList<Integer>();

		successCompile = true;
		zeroFlag = carryFlag = negativeFlag = false;
	}

	/**
	 * Method to update the view variables
	 */
	public void updateViewVars() {
		if(memory[XREG_ADDRESS] != -1)
			xReg.add(memory[XREG_ADDRESS]);
		if(memory[YREG_ADDRESS] != -1)
			yReg.add(memory[YREG_ADDRESS]);
		if(memory[ACCUMULATOR_ADDRESS] != -1) {
			acc.add(memory[ACCUMULATOR_ADDRESS]);
		}
		
		if( (memory[STATUS_ADDRESS] << 0) == 0)
			zeroFlag = false;
		else zeroFlag = true;

		if( (memory[STATUS_ADDRESS] << 1) == 0)
			negativeFlag = false;
		else negativeFlag = true;

		if( (memory[STATUS_ADDRESS] << 2) == 0){
			carryFlag = false;
		}
		else carryFlag = true;

		}

	public void setLineNumbers(Program program) {
		for(int line = 0, ctr = 1; line < program.getProgramStatements().size(); line++) {
			for(int j = 1; j < 2; j++) {
				if(program.getProgramStatements().get(line).get(j) != null 
						&& !program.getProgramStatements().get(line).get(j).equals(" ")
						&& InstructionList.instructionToMnemonic(program.getProgramStatements().get(line).get(j)) == memory[ctr])
					lines[ctr++] = line;
			}
		}
	}

	/**
	 * @return the xReg
	 */
	public ArrayList<Integer> getXReg() {
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
	public ArrayList<Integer> getYReg() {
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

	/**
	 * @return the carryFlag
	 */
	public boolean isCarryFlag() {
		return carryFlag;
	}

	/**
	 * @param carryFlag the carryFlag to set
	 */
	public void setCarryFlag(boolean carryFlag) {
		this.carryFlag = carryFlag;
	}

	/**
	 * @return the zeroFlag
	 */
	public boolean isZeroFlag() {
		return zeroFlag;
	}

	/**
	 * @param zeroFlag the zeroFlag to set
	 */
	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
	}

	/**
	 * @return the negativeFlag
	 */
	public boolean isNegativeFlag() {
		return negativeFlag;
	}

	/**
	 * @param negativeFlag the negativeFlag to set
	 */
	public void setNegativeFlag(boolean negativeFlag) {
		this.negativeFlag = negativeFlag;
	}

	/**
	 * @return the successCompile
	 */
	public boolean isCompileSuccess() {
		return successCompile;
	}

	/**
	 * @param successCompile the successCompile to set
	 */
	public void setSuccessCompile(boolean successCompile) {
		this.successCompile = successCompile;
	}

	/**
	 * @return the memory
	 */
	public int[] getMemory() {
		return memory;
	}
	
	/**
	 * 
	 * @return stack pointer
	 */
	public static int getStackPtr() {
		return STACK_PTR;
	}
	
}