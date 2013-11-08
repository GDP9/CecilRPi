package org.raspberrypi.cecil.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.pojo.CecilInstruction;

/**
 * View interface methods.
 * 
 * MIT Open Source License
 * @author Cathy Jin (cj8g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 07/11/2013
 *
 */
public interface CecilViewInterface {
	/**
	 * Set the instructions to be displayed in the instruction dropdown box and tooltip descriptions.
	 * This method should be called after the view is created.
	 * 
	 * @param instructions ArrayList of instructions.
	 */
	public void setInstructionList(ArrayList<CecilInstruction> instructions);
	
	//All stack values are ordered oldest to newest (last in array is the "current" value)
	public void setAccStack(ArrayList<String> values);
	public void setXStack(ArrayList<String> values);
	public void setYStack(ArrayList<String> values);
	public void setCarryFlag(boolean flagIsOn);
	public void setZeroFlag(boolean flagIsOn);
	public void setNegativeFlag(boolean flagIsOn);
	public void setConsoleText(ArrayList<String> text);
	public void setMemoryAllocation(HashMap<String, String> values);
	
	public void setButtonsEnabled(boolean enabled);
}
