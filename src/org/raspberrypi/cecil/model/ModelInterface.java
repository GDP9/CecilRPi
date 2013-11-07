package org.raspberrypi.cecil.model;

import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.CecilInstruction;
import org.raspberrypi.cecil.pojo.CecilProgram;

public interface ModelInterface {
	public void compile(CecilProgram program); //Just use errors if needed
	public void run(); //Run the program that was previously compiled, return a CecilResult object containing all values to be displayed(?)
	public void stepThrough();
	
	public Compiler getCompiler();
	public Runner getRunner();
	public ArrayList<CecilInstruction> getInstructions();
}