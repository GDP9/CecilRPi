/**
 * 
 */
package org.raspberrypi.cecil.model;

import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.CecilInstruction;
import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;

/**
 * @author Shreeprabha Carolina
 *
 */
public class Cecil implements CecilModelInterface {
	
	protected CecilCompiler compiler;
	protected CecilRunner runner;
	protected CecilMemoryModel sim40;
	
	
	@Override
	public CecilResult run() {
		return null;
	}
	
	@Override
	public CecilResult stepThrough(int lineNo) {
		return null;
	}

	@Override
	public ArrayList<CecilInstruction> getInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CecilResult compile(CecilProgram program) {
		// TODO Auto-generated method stub
		return null;
	}
 
}
