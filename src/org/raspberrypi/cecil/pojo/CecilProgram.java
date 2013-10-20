package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;
import java.util.Date;

/*
 * Object for encapsulating the programs written by the user
 */
public class CecilProgram {

	private String programName;
	private String programAuthor;
	private Date programDate;
	private ArrayList<ArrayList<String>> programStatements;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramAuthor() {
		return programAuthor;
	}
	public void setProgramAuthor(String programAuthor) {
		this.programAuthor = programAuthor;
	}
	public Date getProgramDate() {
		return programDate;
	}
	public void setProgramDate(Date programDate) {
		this.programDate = programDate;
	}
	public ArrayList<ArrayList<String>> getProgramStatements() {
		return programStatements;
	}
	public void setProgramStatements(ArrayList<ArrayList<String>> programStatements) {
		this.programStatements = programStatements;
	}
}
