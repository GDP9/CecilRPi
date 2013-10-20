package org.raspberrypi.cecil.controller;

import java.util.ArrayList;

public interface CecilControllerInterface {
	public void compileClicked(ArrayList<ArrayList<String>> code); //Store each statement (3 Strings) in an ArrayList, and all instructions in another ArrayList
	public void runClicked();
	public void stepThroughClicked();
	public String instructionHovered(int index);
}
