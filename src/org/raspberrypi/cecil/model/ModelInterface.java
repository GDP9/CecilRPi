package org.raspberrypi.cecil.model;

import java.io.File;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;
import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.Program;

/**
 * <p>CECIL Application ModelInterface</p>
 * <p>Implemented by Model.java</p>
 * <p>Contains methods to be called from Model.java by the Controller</p>
 * 
 * @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Southampton University, United Kingdom
 * @version 1.2
 * 
 */
public interface ModelInterface {
	
	/**
	 * Compile Program code.
	 * @param program
	 */
	public void compile(Program program); 
	
	/**
	 * Run program stored in memory.
	 */
	public void run(); 
	
	/**
	 * Step-through program stored in memory.
	 * @return currently executed line to highlight in input-editor.
	 */
	public int stepThrough();
	
	/**
	 * Getter for Error Stream. 
	 * @return ErrorOutputStream
	 */
	public ErrorOutputStream getErrorStream();
	
	/**
	 * Getter for Standard Stream.
	 * @return StandardOutputStream
	 */
	public StandardOutputStream getStdStream();
	
	/**
	 * Setter for Error Stream. 
	 * @param ErrorOutputStream
	 */
	public void setErrorStream(ErrorOutputStream stream);
	
	/**
	 * Getter for Instruction list.
	 * @param ArrayList<Instruction>
	 */
	public ArrayList<Instruction> getInstructions();
	
	/**
	 * Reset view output variables in sim40 simulator object. 
	 */
	public void setViewToDefault();
	
	/**
	 * Translate instruction to opcode.
	 * @param String instruction name
	 * @return int opcode
	 */
	public int instructionToMnemonic(String name);
	
	/**
	 * Check if instruction is binary.
	 * @param String instruction name
	 * @return boolean
	 */
	public boolean isBinaryInstruction(String name);
	
	/**
	 * Check for valid instruction.
	 * @param String instruction name
	 * @return boolean
	 */
	public boolean isInstruction(String name);
	
	
	/**
	 * Updates the lines array in the sim40 simulator object to store the input editor line number corresponding to each cell in memory.
	 */
	public void updateLine();
	
	/**
	 * Check if &quot;stop&quot; instruction has been executed. 
	 * @return boolean
	 */
	public boolean isProgramTerminated();
	
	/**
	 * Converts user program code to suitable format to be stored in a file 
	 * 
	 * @param program
	 * @param fileName
	 * @return file
	 */
	public File programToFile(Program program, String fileName);
	
	/**
	 * Converts the stored program file to program that is readable by the input editor.
	 * 
	 * @param fileName
	 * @return program
	 */
	public Program fileToProgram(File fileName);
	
	/**
	 * Returns true if the carry flag is set. 
	 * @return boolean.
	 */
	public boolean isCarryFlag();
	
	/**
	 * Returns true if the zero flag is set. 
	 * @return boolean.
	 */
	public boolean isZeroFlag();
	
	/**
	 * Returns true if the negative flag is set. 
	 * @return boolean.
	 */
	public boolean isNegativeFlag();
	
	/**
	 * Returns true if compilation is successful.
	 * @return boolean.
	 */
	public boolean isCompileSuccess();
	
	/**
	 * Gets X Register stack frame for this program execution.
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getXReg();
	
	/**
	 * Gets Y Register stack frame for this program execution.
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getYReg();
	
	/**
	 * Gets Accumulator Register stack frame for this program execution.
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getAcc();
	
	/**
	 * Gets memory of sim40 simulator object.
	 * @return int[]
	 */
	public int[] getMemory();
}