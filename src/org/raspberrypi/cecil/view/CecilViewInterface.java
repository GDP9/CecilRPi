package org.raspberrypi.cecil.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.pojo.CecilInstruction;

public interface CecilViewInterface {
	//To be given at the start and displayed in the instruction drop down boxes
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
