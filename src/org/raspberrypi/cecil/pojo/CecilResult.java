package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;
import java.util.List;

import org.raspberrypi.cecil.model.CecilMemoryModel;

/*
 * All the values to be displayed in the "output" views when run or step through is completed.
 */
public class CecilResult {

	private int[] xStack;
	private int[] yStack;
	private int[] accStack;
	private boolean carryFlag;
	private boolean zeroFlag;
	private boolean negativeFlag;
	private CecilMemoryModel malloc;
	private ArrayList<String> results;
	private boolean success;
	
	/**
	 * 
	 * @param errors
	 * @param malloc
	 */
	public CecilResult(List<String> errors, CecilMemoryModel malloc) {
		this.results = (ArrayList<String>) errors;
		this.malloc = malloc;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public CecilResult(String stepthrough, CecilMemoryModel memoryModel) {
		this.results = new ArrayList<String>();
		this.results.add(stepthrough);
		this.malloc = memoryModel;		
	}

	public int[] getxStack() {
		xStack[0] = malloc.memory[1027];
		return xStack;
	}
	
	public int[] getyStack() {
		yStack[0] = malloc.memory[1028];
		return yStack;
	}
	
	public int[] getAccStack() {
		accStack[0] = malloc.memory[1026];
		return accStack;
	}
	
	public boolean isCarryFlag() {
		if((malloc.memory[1025] << 2) == 1)
			carryFlag = true;
		else carryFlag = false;
		
		return carryFlag;
	}
	
	public boolean isZeroFlag() {
		if((malloc.memory[1025] << 0) == 1)
			zeroFlag = true;
		else zeroFlag = false;
		
		return zeroFlag;
	}
	
	public boolean isNegativeFlag() {
		if((malloc.memory[1025] << 1) == 1)
			negativeFlag = true;
		else negativeFlag = false;
		
		return negativeFlag;
	}
	
	public int[] getMemoryAllocations() {
		return malloc.memory ;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getResults() {
		return results;
	}
}