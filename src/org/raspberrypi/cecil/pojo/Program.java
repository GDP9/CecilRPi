package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;

/*
 * Object for encapsulating the programs written by the user
 */
public class Program {

	private ArrayList<ArrayList<String>> programStatements;

	/**
	 * Constructor
	 * @param programStatements
	 */
	public Program(ArrayList<ArrayList<String>> programStatements) {
		this.programStatements = programStatements;
	}


	/**
	 * 
	 * @return
	 */
	public ArrayList<ArrayList<String>> getProgramStatements() {
		return programStatements;
	}

	/**
	 * Returns the line number at the first occurrence of the data label
	 * 
	 * @param name
	 * @param col
	 * @return
	 */
	public int getDataLine(String name) {

		for(int line = 0; line < programStatements.size(); line++){
			if(programStatements.get(line).get(2).equals(name))
				return line+1;
		}

		return 0;
	}
}