package org.raspberrypi.cecil.model.interfaces;

import java.io.File;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;
import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.Program;

public interface ModelInterface {
	/**
	 * Compiler + Runner
	 */
	public void compile(Program program); 
	public void run(); 
	public void stepThrough();
	
	/**
	 * Memory + View Output 
	 */
	public ErrorOutputStream getErrorStream();
	public StandardOutputStream getStdStream();
	public ArrayList<Instruction> getInstructions();
	
	/**
	 * Input editor converter
	 */
	public File programToFile(Program program, String fileName);
	public Program fileToProgram(File fileName);
}