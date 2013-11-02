package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * All the values to be displayed in the "output" views when run or step through is completed.
 */
public class CecilResult {

	private ArrayList<String> xStack;
	private ArrayList<String> yStack;
	private ArrayList<String> accStack;
	private boolean carryFlag;
	private boolean zeroFlag;
	private boolean negativeFlag;
	private HashMap<Integer, Integer> memoryAllocations;
	private ArrayList<String> results;
	private ArrayList<String> errors;
	
	public ArrayList<String> getxStack() {
		return xStack;
	}
	public void setxStack(ArrayList<String> xStack) {
		this.xStack = xStack;
	}
	public ArrayList<String> getyStack() {
		return yStack;
	}
	public void setyStack(ArrayList<String> yStack) {
		this.yStack = yStack;
	}
	public ArrayList<String> getAccStack() {
		return accStack;
	}
	public void setAccStack(ArrayList<String> accStack) {
		this.accStack = accStack;
	}
	public boolean isCarryFlag() {
		return carryFlag;
	}
	public void setCarryFlag(boolean carryFlag) {
		this.carryFlag = carryFlag;
	}
	public boolean isZeroFlag() {
		return zeroFlag;
	}
	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
	}
	public boolean isNegativeFlag() {
		return negativeFlag;
	}
	public void setNegativeFlag(boolean negativeFlag) {
		this.negativeFlag = negativeFlag;
	}
	public HashMap<Integer, Integer> getMemoryAllocations() {
		return memoryAllocations;
	}
	public void setMemoryAllocations(HashMap<Integer, Integer> memoryAllocations) {
		this.memoryAllocations = memoryAllocations;
	}
	public ArrayList<String> getResults() {
		return results;
	}
	public void setResults(ArrayList<String> results) {
		this.results = results;
	}
	public ArrayList<String> getErrors() {
		return errors;
	}
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}
}
