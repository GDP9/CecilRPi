package org.raspberrypi.cecil.model;

import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.CecilInstruction;
import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;

public interface CecilModelInterface {
	public CecilResult compile(CecilProgram program); //Just use errors if needed
	public CecilResult run(); //Run the program that was previously compiled, return a CecilResult object containing all values to be displayed(?)
	public CecilResult stepThrough(int lineNo);
	
	public ArrayList<CecilInstruction> getInstructions();
}