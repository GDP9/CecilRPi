package org.raspberrypi.cecil.controller;

import java.io.File;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.pojo.Program;
import org.raspberrypi.cecil.view.Frame;

/**
 * Controller class for interfacing between the model and view.
 * This class creates an instance of the view (Frame) and model (Cecil) classes.
 * This class contains the main method.
 * 
 * MIT Open Source License
 * @author Cathy Jin (cj8g10), Shreeprabha Aggarwal (sa10g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 11/11/2013
 *
 */
public class Controller implements ControllerInterface {
	private Frame view;
	private Model model;

	/**
	 * Launches the application by creating a new CecilController.
	 */
	public static void main(String[] args) {
		new Controller();
	}
	
	
	/**
	 * Constructor which creates a view (Frame) and model (Cecil) object.
	 */
	public Controller() {
		view = new Frame(this);		
		model = new Model();
		
		view.setInstructionList(model.getInstructions());
		view.setProgramCode(this.getDefaultInput());
	}

	/**
	 * Private method to generate default input editor program
	 * @return Program 
	 */
	private ArrayList<ArrayList<String>> getDefaultInput() {
		ArrayList<ArrayList<String>> program = new ArrayList<ArrayList<String>>();
		ArrayList<String> line = new ArrayList<String>();
		
		line.add(".start");
		line.add("load");
		line.add("num1");
		line.add(";");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(" ");
		line.add("add");
		line.add("num2");
		line.add(";");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(" ");
		line.add("print");
		line.add(" ");
		line.add(";");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(" ");
		line.add("printch");
		line.add(" ");
		line.add(";");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(" ");
		line.add("stop");
		line.add(" ");
		line.add(";");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(".num1");
		line.add("insert");
		line.add("63");
		line.add(";");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(".num2");
		line.add("insert");
		line.add("2");
		line.add(";");
		program.add(line);
		
		return program;
	}
	
	@Override
	public void compileClicked(ArrayList<ArrayList<String>> code) {
		model.compile(new Program(code));
		this.setViewOutput();
	}

	@Override
	public void runClicked() {
		model.run();
		this.setViewOutput();
	}

	@Override
	public void stepThroughClicked() {
		model.stepThrough();
		this.setViewOutput();
	}

	/**
	 * Refreshes the View output using the current snapshot of the memory
	 */
	private void setViewOutput() {
		if (model.getAcc() != null) {
			ArrayList<String> accStack = new ArrayList<String>();
			for (int i = 0; i < model.getAcc().size(); i++) {
				accStack.add(Integer.toString(model.getAcc().get(i)));
			}
			view.setAccStack(accStack);
		} else {
			view.setAccStack(new ArrayList<String>());
		}

		if (model.getXReg() != null && model.getXReg().size() > 0) {
			ArrayList<String> xStack = new ArrayList<String>();
			for (int i = 0; i < model.getXReg().size(); i++) {
				xStack.add(Integer.toString(model.getXReg().get(i)));
			}
			view.setXStack(xStack);
		} else {
			view.setXStack(new ArrayList<String>());
		}

		if (model.getYReg() != null && model.getYReg().size() > 0) {
			ArrayList<String> yStack = new ArrayList<String>();
			for (int i = 0; i < model.getYReg().size(); i++) {
				yStack.add(Integer.toString(model.getYReg().get(i)));
			}
			view.setYStack(yStack);
		} else {
			view.setYStack(new ArrayList<String>());
		}

		view.setCarryFlag(model.isCarryFlag());
		view.setZeroFlag(model.isZeroFlag());
		view.setNegativeFlag(model.isNegativeFlag());

		view.setMemoryAllocation(model.getMemory());

		if (!model.isCompileSuccess() && model.getErrorStream() != null && model.getErrorStream().getErrors() != null) {
			view.setConsoleError(model.getErrorStream().getErrors());
		} else {
			view.setConsoleResult(model.getStdStream().getOutput());
		}
		view.setButtonsEnabled(model.isCompileSuccess());
	}
	
	
	@Override
	public void fileOpened(File file) {
		Program program = model.fileToProgram(file);
		if (program != null && program.getProgramStatements() != null) {
			view.clearVisualisations();
			view.setProgramCode(program.getProgramStatements());
			view.setFilename(file.getName());
		}
	}
	
	@Override
	public void saveToFile(ArrayList<ArrayList<String>> code, String filename) {
		if (code != null && filename != null) {
			Program program = new Program(code);
			File file = model.programToFile(program, filename);
			view.setFilename(file.getName());
		}
	}	
}