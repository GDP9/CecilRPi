package org.raspberrypi.cecil.model;

import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;

public interface CecilModelInterface {
	public String compile(CecilProgram program); //A string for the result? Or a result object needed?
	public CecilResult run(); //Run the program that was previously compiled, return a CecilResult object containing all values to be displayed(?)
	public CecilResult stepThrough(int lineNo);
}
