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
* @version 1.2
* 
* @date 14/11/2013
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
		instructions.add(new Instruction("stop", "Opcode:0 CECIL stops executing the program", 0));
		instructions.add(new Instruction("print", "Opcode:1 Prints numerical contents of Accumulator register", 1));
		instructions.add(new Instruction("printch", "Opcode:2 Prints the contents of Accumulator in ASCII format", 2));
		instructions.add(new Instruction("printb", "Opcode:3 Prints the contents of Accumulator in binary format", 3));
		instructions.add(new Instruction("cclear", "Opcode:4 Sets the carry flag to 0", 4));
		instructions.add(new Instruction("cset", "Opcode:5 Sets the carry flag to 1", 5));
//		instructions.add(new Instruction("lshift", "Moves all the bits of accumulator on place to the left", 6));
//		instructions.add(new Instruction("rshift", "Moves all the bits of accumulator on place to the right", 7));
//		instructions.add(new Instruction("pull", "Pulls a copy of the number on the top of the stack into the Accumulator", 8));
		instructions.add(new Instruction("xinc", "Opcode:9 Adds 1 to the current value of the X-register", 9));
		instructions.add(new Instruction("xdec", "Opcode:10 Subtracts 1 from the current value of the X-register", 10));
//		instructions.add(new Instruction("xpush", "Pushes onto the top of the stack the current value of the X-register", 11));
//		instructions.add(new Instruction("xpull", "Pulls the value of the top of the stack into the X-register", 12));
//		instructions.add(new Instruction("push", "Places a copy of the value of the given address onto the top of the stack", 13));
//		instructions.add(new Instruction("ypush", "Pushes a copy of the number in the Y-register onto the top of the stack", 14));
//		instructions.add(new Instruction("ypull", "Pulls a copy of the number on  the top of the stack into the Y-register", 15));
		instructions.add(new Instruction("yinc", "Opcode:16 Adds 1 to the current value of the Y-register", 16));
		instructions.add(new Instruction("ydec", "Opcode:17  Subtracts 1 from the current value of the Y-register", 17));
		
		/*
		 * Binary instructions
		 */
		instructions.add(new Instruction("load", "Opcode:18 Replaces contents of the Accumlator with the integer stored at the labelled address", 18));
		//instructions.add(new Instruction("store", "Opcode:19 Stores a copy of the current value in the Accumulator at the labelled address", 19));
		instructions.add(new Instruction("add", "Opcode:20 Adds the contents of the Accumulator with the integer stored at the labelled address", 20));
		instructions.add(new Instruction("sub", "Opcode:21 Subtracts the labelled address from the current value of the Accumulator", 21));
		instructions.add(new Instruction("and", "Opcode:22 Performs a logical AND between the current value of the Accumulator and the number at the given address label", 22));
		instructions.add(new Instruction("or", "Opcode:23 Performs a logical OR between the current value of the Accumulator and the number at the given address label", 23));
		instructions.add(new Instruction("xor", "Opcode:24 Performs a logical EXCLUSIVE OR between the current value of the Accumulator and the number at the given address label", 24));
		instructions.add(new Instruction("jump", "Opcode:25 Passes control to the instructions beginning at the labelled address", 25));
		instructions.add(new Instruction("comp", "Opcode:26 Compares the value at the labelled address with the current value of the Accumulator", 26));
		instructions.add(new Instruction("jineg", "Opcode:27 Jumps to a different part of the program, if the negative flag is set", 27));
		instructions.add(new Instruction("jipos", "Opcode:28 Jumps to a different part of the program, if neither the zero or negative flags are set", 28));
		instructions.add(new Instruction("jizero", "Opcode:29 Jumps to a different part of the program, if the zero flag is set", 29));
		//instructions.add(new Instruction("jmptosr", "Jumps to the subroutuive starting at the given labelled address", 30));
		instructions.add(new Instruction("jicarry", "Opcode:31 Jumps to a different part of the program, if the carry flag is set", 31));
		instructions.add(new Instruction("xload", "Opcode:32 Copies the labelled address value into the X-register", 32));
		//instructions.add(new Instruction("xstore", "Opcode:33 Copies the current value of the X-register into the labelled address", 33));
		//instructions.add(new Instruction("loadmx", "Opcode:34 Replaces contents of the Accumulator with the current value of the X-register", 34));
		instructions.add(new Instruction("xcomp", "Opcode:35 Compares the value at the labelled address with the current value of the X-register", 35));
		instructions.add(new Instruction("yload", "Opcode:36 Copies the labelled address value into the Y-register", 36));
		//instructions.add(new Instruction("ystore", "Copies the current value of the Y-register into the labelled address", 37));
		instructions.add(new Instruction("insert", "Opcode:38 Stores the given integer as a 10-bit word in memory", 38));
		instructions.add(new Instruction("ycomp", "Opcode:39 Compares the value at the labelled address with the current value of the Y-register", 39));
		
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