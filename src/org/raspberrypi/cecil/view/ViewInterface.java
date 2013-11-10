package org.raspberrypi.cecil.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.pojo.Instruction;

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
public interface ViewInterface {
	/**
	 * Set the instructions to be displayed in the instruction dropdown box and tooltip descriptions.
	 * This method should be called after the view is created.
	 * 
	 * @param instructions Instruction set with descriptions.
	 */
	public void setInstructionList(ArrayList<Instruction> instructions);
	
	/**
	 * Set the program code to be displayed in the input editor.
	 * 
	 * @param program An ArrayList of instruction lines. Each instruction is an ArrayList composed of three Strings.
	 */
	public void setProgramCode(ArrayList<ArrayList<String>> program);
	
	/**
	 * Set the values to be displayed in the accumulator register visualisation.
	 * Uses an ArrayList to show the history of values from oldest to newest.
	 * 
	 * @param values The values to be displayed.
	 */
	public void setAccStack(ArrayList<String> values);
	
	/**
	 * Set the values to be displayed in the X register visualisation.
	 * Uses an ArrayList to show the history of values from oldest to newest.
	 * 
	 * @param values The values to be displayed.
	 */
	public void setXStack(ArrayList<String> values);
	
	/**
	 * Set the values to be displayed in the Y register visualisation.
	 * Uses an ArrayList to show the history of values from oldest to newest.
	 * 
	 * @param values The values to be displayed.
	 */
	public void setYStack(ArrayList<String> values);
	
	/**
	 * Set the state to be displayed in the carry status flag visualisation.
	 * 
	 * @param flagIsOn The state to be displayed.
	 */
	public void setCarryFlag(boolean flagIsOn);
	
	/**
	 * Set the state to be displayed in the zero status flag visualisation.
	 * 
	 * @param flagIsOn The state to be displayed.
	 */
	public void setZeroFlag(boolean flagIsOn);
	
	/**
	 * Set the state to be displayed in the negative status flag visualisation.
	 * 
	 * @param flagIsOn The state to be displayed.
	 */
	public void setNegativeFlag(boolean flagIsOn);
	
	/**
	 * Set the values to be displayed in the console output visualisation.
	 * If the compile or run is successful it will display a list of results.
	 * If unsuccessful it will display an error.
	 * 
	 * @param text Result or error strings.
	 */
	public void setConsoleText(ArrayList<String> text);
	
	/**
	 * Set the values to be displayed in the memory visualisation.
	 * 
	 * @param values The values to be displayed.
	 */
	public void setMemoryAllocation(HashMap<String, String> values);
	
	/**
	 * Set the state of the run and step through buttons to be enabled or disabled.
	 * The buttons should be disabled until a successful compile has completed.
	 * 
	 * @param isEnabled The state to be displayed.
	 */
	public void setButtonsEnabled(boolean isEnabled);
}