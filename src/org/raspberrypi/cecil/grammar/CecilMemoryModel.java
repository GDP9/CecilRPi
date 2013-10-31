package org.raspberrypi.cecil.grammar;

/**
 * 
 * @authors Shreeprabha, Carolina
 *
 */
public class CecilMemoryModel {
	static final int STATUS_ADDRESS = 1025;
	static final int PROGRAM_COUNTER = 1024;
	static final int ACCUMULATOR_ADDRESS = 1026;
	static final int XREG_ADDRESS = 1027;
	static final int YREG_ADDRESS = 1028;
	static final int MEMORY_LENGTH = 1029;
	static final int STACK_BEGIN = 908;
	static final int STACK_END = 1006;
	static final int STACK_PTR = 1007;
	
	static int[] memory = new int[MEMORY_LENGTH];
	
	/**
	 * Constuctor: initialse memory to store -1
	 */
	public CecilMemoryModel() {
		for(int i = 0 ; i < MEMORY_LENGTH; i++)
			memory[i] = -1;
		
		memory[STACK_PTR] = 908;
	}
}
