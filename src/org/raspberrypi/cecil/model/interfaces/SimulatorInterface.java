package org.raspberrypi.cecil.model.interfaces;

import java.util.ArrayList;

/**
 * CECIL Application SimulatorInterface
 * Implemented by Model.java
 * Limits the access to the Simulator and allows only the getters to be invoked from the view/controller
 * 
 * The MIT License (MIT)
 * Copyright (c) 2013 Southampton University group GDP9
 * 
 * @authors Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
 * Southampton University, United Kingdom
 * @version 1.2
 * 
 * @date 14/11/2013
 *
 */
public interface SimulatorInterface {

	public boolean isCarryFlag();
	public boolean isZeroFlag();
	public boolean isNegativeFlag();
	public boolean isCompileSuccess();
	public ArrayList<Integer> getXReg();
	public ArrayList<Integer> getYReg();
	public ArrayList<Integer> getAcc();
	public int[] getMemory();
	
}
