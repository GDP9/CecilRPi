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
}
