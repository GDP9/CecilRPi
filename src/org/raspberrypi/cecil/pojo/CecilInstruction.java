package org.raspberrypi.cecil.pojo;

import java.util.ArrayList;

/*
 * Object for encapsulating the instruction information
 */
public class CecilInstruction {

	private String instructionName;
	private String description;
	private String tooltipDescription; //If different
	private ArrayList<String> effectedByFlags;
	private ArrayList<String> affectsFlags;
	private ArrayList<String> examples;
	private ArrayList<String> warnings;
	private ArrayList<String> relatedInstructions;
	
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
	public String getTooltipDescription() {
		return tooltipDescription;
	}
	public void setTooltipDescription(String tooltipDescription) {
		this.tooltipDescription = tooltipDescription;
	}
	public ArrayList<String> getEffectedByFlags() {
		return effectedByFlags;
	}
	public void setEffectedByFlags(ArrayList<String> effectedByFlags) {
		this.effectedByFlags = effectedByFlags;
	}
	public ArrayList<String> getAffectsFlags() {
		return affectsFlags;
	}
	public void setAffectsFlags(ArrayList<String> affectsFlags) {
		this.affectsFlags = affectsFlags;
	}
	public ArrayList<String> getExamples() {
		return examples;
	}
	public void setExamples(ArrayList<String> examples) {
		this.examples = examples;
	}
	public ArrayList<String> getWarnings() {
		return warnings;
	}
	public void setWarnings(ArrayList<String> warnings) {
		this.warnings = warnings;
	}
	public ArrayList<String> getRelatedInstructions() {
		return relatedInstructions;
	}
	public void setRelatedInstructions(ArrayList<String> relatedInstructions) {
		this.relatedInstructions = relatedInstructions;
	}
}
