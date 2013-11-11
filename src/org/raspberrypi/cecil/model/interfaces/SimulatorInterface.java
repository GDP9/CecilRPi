package org.raspberrypi.cecil.model.interfaces;

import java.util.ArrayList;

/**
 * This class limits the access to the Simulator and allows only the getters to be 
 * invoked from the view/controller
 * 
 * @author sa10g10
 *
 */
public interface SimulatorInterface {

	public boolean isCarryFlag();
	public boolean isZeroFlag();
	public boolean isNegativeFlag();
	public boolean isSuccessCompile();
	public ArrayList<Integer> getxReg();
	public ArrayList<Integer> getyReg();
	public ArrayList<Integer> getAcc();
	public int[] getMemory();
}
