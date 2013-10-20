package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;

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
	private ArrayList<String> memoryAllocations;
	
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
	public ArrayList<String> getMemoryAllocations() {
		return memoryAllocations;
	}
	public void setMemoryAllocations(ArrayList<String> memoryAllocations) {
		this.memoryAllocations = memoryAllocations;
	}
}
