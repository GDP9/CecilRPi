package org.raspberrypi.cecil.pojo;
import java.util.ArrayList;

/**
*
* <p>CECIL assembly language instructions list.</p>
* <p>CECIL instructions list is created in this class.</p>
* <p>This list constitutes data on the name, description of CECIL instructions and their respective machine code (mnemonic).</p>
* <p>Supplies the necessary information for the instructions tool tips employed by the interface.</p>
* 
* @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Southampton University, United Kingdom
* @version 1.2
*  
*/
public class InstructionList {

	private static ArrayList<Instruction> instructions;
	
	/**
	 * <p>InstructionList Constructor.</p>
	 * <p>Initiates the Instruction list and adds the necessary data:
	 * 	<ul>
	 *    <li>instruction name</li>
	 *    <li>instruction description</li>
	 * 	  <li>instruction machine code (mnemonic)</li>
	 *  </ul>
	 * </p>
	 */
	public InstructionList() {
		instructions = new ArrayList<Instruction>();
		
		/*
		 * Unary instructions
		 */
		instructions.add(new Instruction("stop", "[Opcode:0] CECIL stops executing the program", 0));
		instructions.add(new Instruction("print", "[Opcode:1] Prints numerical contents of Accumulator register", 1));
		instructions.add(new Instruction("printch", "[Opcode:2] Prints the contents of Accumulator in ASCII format", 2));
		instructions.add(new Instruction("printb", "[Opcode:3] Prints the contents of Accumulator in binary format", 3));
		instructions.add(new Instruction("cclear", "[Opcode:4] Sets the carry flag to 0", 4));
		instructions.add(new Instruction("cset", "[Opcode:5] Sets the carry flag to 1", 5));
		instructions.add(new Instruction("xinc", "[Opcode:6] Adds 1 to the current value of the X-register", 6));
		instructions.add(new Instruction("xdec", "[Opcode:7] Subtracts 1 from the current value of the X-register", 7));
		instructions.add(new Instruction("yinc", "[Opcode:8] Adds 1 to the current value of the Y-register", 8));
		instructions.add(new Instruction("ydec", "[Opcode:9]  Subtracts 1 from the current value of the Y-register", 9));
		
		/*
		 * Binary instructions
		 */
		instructions.add(new Instruction("insert", "[Opcode:10] Stores the given integer as a 10-bit word in memory", 10));
		instructions.add(new Instruction("load", "[Opcode:11] Replaces the contents of the Accumulator with the integer stored at the labelled address", 11));
		instructions.add(new Instruction("xload", "[Opcode:12] Copies the labelled address value into the X-register", 12));
		instructions.add(new Instruction("yload", "[Opcode:13] Copies the labelled address value into the Y-register", 13));
		instructions.add(new Instruction("add", "[Opcode:14] Adds the integer stored at the labelled address to the Accumulator value", 14));
		instructions.add(new Instruction("sub", "[Opcode:15] Subtracts the value stored at the labelled address from the current Accumulator value", 15));
		instructions.add(new Instruction("and", "[Opcode:16] Performs a logical AND between the current value of the Accumulator and the number at the given address label", 16));
		instructions.add(new Instruction("or", "[Opcode:17] Performs a logical OR between the current value of the Accumulator and the number at the given address label", 17));
		instructions.add(new Instruction("xor", "[Opcode:18] Performs a logical EXCLUSIVE OR between the current value of the Accumulator and the number at the given address label", 18));
		instructions.add(new Instruction("jump", "[Opcode:19] Passes control to the instruction beginning at the labelled address", 19));
		instructions.add(new Instruction("comp", "[Opcode:20] Compares the value at the labelled address with the current value of the Accumulator", 20));
		instructions.add(new Instruction("xcomp", "[Opcode:21] Compares the value at the labelled address with the current value of the X-register", 21));
		instructions.add(new Instruction("ycomp", "[Opcode:22] Compares the value at the labelled address with the current value of the Y-register", 22));
		instructions.add(new Instruction("jineg", "[Opcode:23] Jumps to the instruction at the labelled address if the negative flag is set", 23));
		instructions.add(new Instruction("jipos", "[Opcode:24] Jumps to the instruction at the labelled address if neither the zero or negative flags are set", 24));
		instructions.add(new Instruction("jizero", "[Opcode:25] Jumps to the instruction at the labelled address if the zero flag is set", 25));
		instructions.add(new Instruction("jicarry", "[Opcode:26] Jumps to the instruction at the labelled address if the carry flag is set", 26));
			
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