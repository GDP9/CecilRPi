package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;

/*
 * Object for encapsulating the programs written by the user
 */
public class CecilProgram {

	private ArrayList<ArrayList<String>> programStatements;
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<String>> getProgramStatements() {
		return programStatements;
	}
	
	/**
	 * 
	 * @param programStatements
	 */
	public void setProgramStatements(ArrayList<ArrayList<String>> programStatements) {
		this.programStatements = programStatements;
	}
}
