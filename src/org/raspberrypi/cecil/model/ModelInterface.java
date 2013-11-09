package org.raspberrypi.cecil.model;

import java.io.File;
import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.Program;

public interface ModelInterface {
	public void compile(Program program); //Just use errors if needed
	public void run(); //Run the program that was previously compiled, return a CecilResult object containing all values to be displayed(?)
	public void stepThrough();
	
	public Compiler getCompiler();
	public Runner getRunner();
	public ArrayList<Instruction> getInstructions();
	
	/*
	 * Input editor converter
	 */
	public File programToFile(Program program, String fileName);
	public Program fileToProgram(File fileName);
}