package org.raspberrypi.cecil.model;

import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.CecilInstruction;
import org.raspberrypi.cecil.pojo.CecilProgram;

public interface CecilModelInterface {
	public void compile(CecilProgram program); //Just use errors if needed
	public void run(); //Run the program that was previously compiled, return a CecilResult object containing all values to be displayed(?)
	public void stepThrough();
	
	public CecilMemoryModel getResult();
	public ArrayList<CecilInstruction> getInstructions();
}