package org.raspberrypi.cecil.pojo;
import java.util.ArrayList;

/**
*
* CECIL assembly language instructions list.
* CECIL instructions list is created in this class.
* This list constitutes data on the name, description of CECIL instructions and their respective machine code (mnemonic).
* Supplies the necessary information for the instructions tool tips employed by the interface. 
*
* The MIT License (MIT)
* Copyright (c) 2013 Southampton University group GDP9
* 
* @authors Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
* Southampton University, United Kingdom
* @version 1.1
* 
* @date 09/11/2013
*  
*/
public class InstructionList {

	private static ArrayList<Instruction> instructions;
	
	/**
	 * InstructionList Constructor.
	 * Initiates the Instruction list and adds the necessary data:
	 * 	- instruction name
	 *  - instruction description
	 * 	- instruction machine code (mnemonic)
	 */
	public InstructionList() {
		instructions = new ArrayList<Instruction>();
		
		/*
		 * Unary instructions
		 */
		instructions.add(new Instruction("stop", "CECIL stops executing the program", 0));
		instructions.add(new Instruction("print", "Prints numerical contents of Accumulator register", 1));
		instructions.add(new Instruction("printch", "Prints the contents of Accumulator in ASCII format", 2));
		instructions.add(new Instruction("printb", "Prints the contents of Accumulator in binary format", 3));
		instructions.add(new Instruction("cclear", "Sets the carry flag to 0", 4));
		instructions.add(new Instruction("cset", "Sets the carry flag to 1", 5));
//		instructions.add(new Instruction("lshift", "Moves all the bits of accumulator on place to the left", 6));
//		instructions.add(new Instruction("rshift", "Moves all the bits of accumulator on place to the right", 7));
//		instructions.add(new Instruction("pull", "Pulls a copy of the number on the top of the stack into the Accumulator", 8));
		instructions.add(new Instruction("xinc", "Adds 1 to the current value of the X-register", 9));
		instructions.add(new Instruction("xdec", "Subtracts 1 from the current value of the X-register", 10));
//		instructions.add(new Instruction("xpush", "Pushes onto the top of the stack the current value of the X-register", 11));
//		instructions.add(new Instruction("xpull", "Pulls the value of the top of the stack into the X-register", 12));
//		instructions.add(new Instruction("push", "Places a copy of the value of the given address onto the top of the stack", 13));
//		instructions.add(new Instruction("ypush", "Pushes a copy of the number in the Y-register onto the top of the stack", 14));
//		instructions.add(new Instruction("ypull", "Pulls a copy of the number on  the top of the stack into the Y-register", 15));
		instructions.add(new Instruction("yinc", "Adds 1 to the current value of the Y-register", 16));
		instructions.add(new Instruction("ydec", "Subtracts 1 from the current value of the Y-register", 17));
		
		/*
		 * Binary instructions
		 */
		instructions.add(new Instruction("load", "Replaces contents of the Accumlator with the integer stored at the labelled address", 18));
		instructions.add(new Instruction("store", "Stores a copy of the current value in the Accumulator at the labelled address", 19));
		instructions.add(new Instruction("add", "Adds the contents of the Accumulator with the integer stored at the labelled address", 20));
		instructions.add(new Instruction("sub", "Subtracts the labelled address from the current value of the Accumulator", 21));
		instructions.add(new Instruction("and", "Performs a logical AND between the current value of the Accumulator and the number at the given address label", 22));
		instructions.add(new Instruction("or", "Performs a logical OR between the current value of the Accumulator and the number at the given address label", 23));
		instructions.add(new Instruction("xor", "Performs a logical EXCLUSIVE OR between the current value of the Accumulator and the number at the given address label", 24));
		instructions.add(new Instruction("jump", "Passes control to the instructions beginning at the labelled address", 25));
		instructions.add(new Instruction("comp", "Compares the value at the labelled address with the current value of the Accumulator", 26));
		instructions.add(new Instruction("jineg", "Jumps to a different part of the program, if the negative flag is set", 27));
		instructions.add(new Instruction("jipos", "Jumps to a different part of the program, if neither the zero or negative flags are set", 28));
		instructions.add(new Instruction("jizero", "Jumps to a different part of the program, if the zero flag is set", 29));
		instructions.add(new Instruction("jmptosr", "Jumps to the subroutuive starting at the given labelled address", 30));
		instructions.add(new Instruction("jicarry", "Jumps to a different part of the program, if the carry flag is set", 31));
		instructions.add(new Instruction("xload", "Copies the labelled address value into the X-register", 32));
		instructions.add(new Instruction("xstore", "Copies the current value of the X-register into the labelled address", 33));
		instructions.add(new Instruction("loadmx", "Replaces contents of the Accumulator with the current value of the X-register", 34));
		instructions.add(new Instruction("xcomp", "Compares the value at the labelled address with the current value of the X-register", 35));
		instructions.add(new Instruction("yload", "Copies the labelled address value into the Y-register", 36));
		instructions.add(new Instruction("ystore", "Copies the current value of the Y-register into the labelled address", 37));
		instructions.add(new Instruction("insert", "Stores the given integer as a 10-bit word in memory", 38));
		instructions.add(new Instruction("ycomp", "Compares the value at the labelled address with the current value of the Y-register", 39));
		
	}
	
	/**
	 * Returns the instructions object list constructed.
	 * @return ArrayList<Instruction> instructions
	 */
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	
	/**
	 * Returns the machine code of the given instruction.
	 * @param instruction name
	 * @return instruction mnemonic
	 */
	public static int instructionToMnemonic(String name) {
		for(Instruction i: instructions) {
			if(i.getName().equals(name))
				return i.getMnemonic();
		}
		
		return -1;
	}
}