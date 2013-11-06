package org.raspberrypi.cecil.pojo;

/*
 * Object for encapsulating the instruction information
 */
public class CecilInstruction {

	private String instructionName;
	private String description;
	
	public String getInstructionName() {
		return instructionName;
	}
	public void setInstructionName(String instructionName) {
		this.instructionName = instructionName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
