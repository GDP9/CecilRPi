/**
 * This is the memory model layout of CECIL application
 */
package org.raspberrypi.cecil.model;

import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.InstructionList;
import org.raspberrypi.cecil.pojo.Program;

/**
 * <p>CECIL assembly language Simulator</p>
 * <p>Contains static final memory allocation variables (e.g. Accumulator address)</p>
 * <p>Sets all variables used in the view with values retrieved from the model (e.g. Accumulator stack)</p>
 * <p>Memory Model :
 *  <ul>
 *  	<li>Accumulator address : 1026</li>
 *  	<li>X-reg address: 1027</li>
 *  	<li>Y-reg address: 1028</li>
 *  	<li>Status address (flags): 1025</li>
 *  	<li>Memory length address: 1029</li>
 *  	<li>Stack pointers : 908,1006,1007</li>
 *  	<li>Program counter (used to track user input program): 1024 </li>
 *  </ul>
 * </p>
 * 
 * @author Carolina Ferreira (cf4g09)
 * @author Shreeprabha Aggarwal (sa10g10)
 * @author Southampton University, United Kingdom
 * 
 * @version 1.2
 * 
 * @date 14/11/2013
 *
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

	/*
	 * Public for ease of use
	 * Memory allocation addresses and user input program lines
	 */ 
	public int[] memory = new int[MEMORY_LENGTH]; 
	public int[] lines = new int[MEMORY_LENGTH];

	/**
	 * <p>Simulator constructor</p>
	 * <p>Initializes all variables to default values and memory to store -1</p>
	 */
	public Simulator() {
		for(int i = 0 ; i < MEMORY_LENGTH; i++)
			memory[i] = -1;

		memory[STACK_PTR] = 908;
		memory[STATUS_ADDRESS] = 0;
		memory[ACCUMULATOR_ADDRESS] = 0;
		memory[XREG_ADDRESS] = 0;
		memory[YREG_ADDRESS] = 0;

		successCompile = true;
		zeroFlag = carryFlag = negativeFlag = false;

		xReg = new ArrayList<Integer>();
		yReg = new ArrayList<Integer>();
		acc = new ArrayList<Integer>();

		xReg.add(0);
		yReg.add(0);
		acc.add(0);

	}

	/**
	 * <p>Updates flag variables used in the view:
	 * 	<ul>
	 * 		<li>Carry flag (2nd bit in STATUS_ADDRESS) - boolean carryFlag</li>
	 *  	<li>Negative flag (1st bit in STATUS_ADDRESS) - boolean negativeFlag</li>
	 *  	<li>Zero flag (0th bit in STATUS_ADDRESS) - boolean zeroFlag</li>
	 *  </ul>
	 * </p>
	 */
	public void updateViewFlags() {

		if( (memory[STATUS_ADDRESS] & (1<< 0)) == 0)
			zeroFlag = false;
		else zeroFlag = true;

		if( (memory[STATUS_ADDRESS] & (1<< 1)) == 0)
			negativeFlag = false;
		else negativeFlag = true;

		if( (memory[STATUS_ADDRESS]& (1<< 2)) == 0){
			carryFlag = false;
		}
		else carryFlag = true;

	}

	/**
	 * Updates register variables used in view 
	 */
	public void updateViewRegisters() {
		if(memory[XREG_ADDRESS] != xReg.get(xReg.size() - 1))
			xReg.add(memory[XREG_ADDRESS]);

		if(memory[YREG_ADDRESS] != yReg.get(yReg.size() - 1))
			yReg.add(memory[YREG_ADDRESS]);

		if(memory[ACCUMULATOR_ADDRESS]!= acc.get(acc.size() - 1)) 
			acc.add(memory[ACCUMULATOR_ADDRESS]);

	}

	/**
	 * Updates all the view variables
	 */
	public void updateViewVars() {
		this.updateViewFlags();
		this.updateViewRegisters();
	}

	/**
	 * Gets the line numbers of user input program and stores them in lines[]
	 * 
	 * @param Program object
	 */
	public void setLineNumbers(Program program) {
		for(int line = 0, ctr = 0; line < program.getProgramStatements().size(); line++) {
			for(int j = 1; j <= 2; j++) {
				if(program.getProgramStatements().get(line).get(j) != null 
						&& !program.getProgramStatements().get(line).get(j).equals(" ")
						&& InstructionList.instructionToMnemonic(program.getProgramStatements().get(line).get(j)) == memory[ctr]) {
		
					lines[ctr++] = line+1;
				}
			}
		}
		
		
	}

	/**
	 * Gets xReg stack
	 * @return ArrayList<Integer> xReg
	 */
	public ArrayList<Integer> getXReg() {
		return this.xReg;
	}

	/**
	 * Sets the xReg stack
	 * @param ArrayList<Integer> xReg
	 */
	public void setxReg(ArrayList<Integer> xReg) {
		this.xReg = xReg;
	}

	/**
	 * Gets yReg stack
	 * @return ArrayList<Integer> yReg
	 */
	public ArrayList<Integer> getYReg() {
		return this.yReg;
	}

	/**
	 * Sets the yReg stack
	 * @param ArrayList<Integer> yReg
	 */
	public void setyReg(ArrayList<Integer> yReg) {
		this.yReg = yReg;
	}

	/**
	 * Gets accumulator stack
	 * @return ArrayList<Integer> acc
	 */
	public ArrayList<Integer> getAcc() {
		return this.acc;
	}

	/**
	 * Sets the accumulator stack
	 * @param ArrayList<Integer> acc
	 */
	public void setAcc(ArrayList<Integer> acc) {
		this.acc = acc;
	}

	/**
	 * Gets the carryFlag boolean variable
	 * @return boolean carryFlag
	 */
	public boolean isCarryFlag() {
		return carryFlag;
	}

	/**
	 * Sets the carryFlag boolean variable
	 * @param boolean carryFlag 
	 */
	public void setCarryFlag(boolean carryFlag) {
		this.carryFlag = carryFlag;
	}

	/**
	 * Gets the zeroFlag boolean variable
	 * @return boolean zeroFlag
	 */
	public boolean isZeroFlag() {
		return zeroFlag;
	}

	/**
	 * Sets the zeroFlag boolean variable
	 * @param boolean zeroFlag 
	 */
	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
	}

	/**
	 * Gets the negativeFlag boolean variable
	 * @return boolean zeroFlag
	 */
	public boolean isNegativeFlag() {
		return negativeFlag;
	}

	/**
	 * Sets the negativeFlag boolean variable
	 * @param boolean zeroFlag 
	 */
	public void setNegativeFlag(boolean negativeFlag) {
		this.negativeFlag = negativeFlag;
	}

	/**
	 * Gets the isCompileSuccess boolean variable
	 * @return boolean successCompile
	 */
	public boolean isCompileSuccess() {
		return successCompile;
	}

	/**
	 * Sets the successCompile boolean variable
	 * @param boolean successCompile 
	 */
	public void setSuccessCompile(boolean successCompile) {
		this.successCompile = successCompile;
	}

	/**
	 * Gets the memory variable
	 * @return int[] memory
	 */
	public int[] getMemory() {
		return memory;
	}

	/**
	 * Gets the STACK_PTR variable
	 * @return in STAKC_PTR
	 */
	public static int getStackPtr() {
		return STACK_PTR;
	}

}