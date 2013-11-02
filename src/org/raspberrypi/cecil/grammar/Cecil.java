/**
 * 
 */
package org.raspberrypi.cecil.grammar;

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
	public String compile(CecilProgram program) {
		
		return null;
	}
	
	@Override
	public CecilResult run() {
		return null;
	}
	
	@Override
	public CecilResult stepThrough(int lineNo) {
		return null;
	}
 
}
