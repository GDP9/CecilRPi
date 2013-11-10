package org.raspberrypi.cecil.controller;

import java.io.File;
import java.util.ArrayList;

/**
 * Controller interface methods.
 * These methods are used by the view to notify the controller of user driven events.
 * 
 * MIT Open Source License
 * @author Cathy Jin (cj8g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 07/11/2013
 *
 */
public interface ControllerInterface {
	
	/**
	 * Notify the controller that the compile button has been clicked.
	 * 
	 * @param code An ArrayList of instruction lines. Each instruction is an ArrayList composed of three Strings.
	 */
	public void compileClicked(ArrayList<ArrayList<String>> code);

	/**
	 * Notify the controller that the run button has been clicked.
	 */
	public void runClicked();
	
	/**
	 * Notify the controller that the step through button has been clicked.
	 */
	public void stepThroughClicked();
	
	/**
	 * Notify the controller that a file has been opened.
	 */
	public void fileOpened(File file);
	
	/**
	 * Notify the controller that the save button has been clicked.
	 * 
	 * @param code An ArrayList of instruction lines. Each instruction is an ArrayList composed of three Strings.
	 */
	public void saveToFile(ArrayList<ArrayList<String>> code, String filename);
}
