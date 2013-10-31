/**
 * 
 */
package org.raspberrypi.cecil.grammar;

/**
 * @author Shreeprabha
 *
 */
public class CecilCompiler {
	
	public int recursive(int[] memory, int key) {
		int i = memory[key];
		while(i == -1){
			memory[key] = recursive(memory, key + 2);
			i = memory[key];
		}
		return i;
	}

}
