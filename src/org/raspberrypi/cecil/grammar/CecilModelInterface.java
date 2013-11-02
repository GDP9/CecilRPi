<<<<<<< HEAD:src/org/raspberrypi/cecil/grammar/CecilModelInterface.java
package org.raspberrypi.cecil.grammar;

import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;

public interface CecilModelInterface {
	public String compile(CecilProgram program); //A string for the result? Or a result object needed?
	public CecilResult run(); //Run the program that was previously compiled, return a CecilResult object containing all values to be displayed(?)
	public CecilResult stepThrough(int lineNo);
}
=======
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
>>>>>>> 2a498168c01f0d68c306615ad7179c68302d49e6:src/org/raspberrypi/cecil/model/CecilModelInterface.java
