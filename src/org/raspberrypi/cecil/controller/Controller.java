package org.raspberrypi.cecil.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.model.Simulator;
import org.raspberrypi.cecil.pojo.Program;
import org.raspberrypi.cecil.view.Frame;

/**
 * Controller class for interfacing between the model and view.
 * This class creates an instance of the view (Frame) and model (Cecil) classes.
 * This class contains the main method.
 * 
 * MIT Open Source License
 * @author Cathy Jin (cj8g10)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 07/11/2013
 *
 */
public class Controller implements ControllerInterface {
	private Frame view;
	private Model model;

	/**
	 * Constructor which creates a view (Frame) and model (Cecil) object.
	 */
	public Controller() {
		ArrayList<ArrayList<String>> program = new ArrayList<ArrayList<String>>();
		ArrayList<String> line = new ArrayList<String>();
		line.add(".start");
		line.add("load");
		line.add("num1");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add("");
		line.add("add");
		line.add("num2");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add("");
		line.add("print");
		line.add("");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add("");
		line.add("printch");
		line.add("");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add("");
		line.add("stop");
		line.add("");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(".num1");
		line.add("insert");
		line.add("63");
		program.add(line);
		
		line = new ArrayList<String>();
		line.add(".num2");
		line.add("insert");
		line.add("2");
		program.add(line);
		
		view = new Frame(this);		
		model = new Model();
		view.setInstructionList(model.getInstructions());
		view.setProgramCode(program);
	}

	@Override
	public void compileClicked(ArrayList<ArrayList<String>> code) {
		Program program = new Program(code);
		
		model.compile(program);
		Simulator result = model.getSimulator();

		if (result.getAcc() != null) {
			ArrayList<String> accStack = new ArrayList<String>();
			for (int i = 0; i < result.getAcc().size(); i++) {
				accStack.add(Integer.toString(result.getAcc().get(i)));
			}
			view.setAccStack(accStack);
		} else {
			view.setAccStack(new ArrayList<String>());
		}

		if (result.getxReg() != null && result.getxReg().size() > 0) {
			ArrayList<String> xStack = new ArrayList<String>();
			for (int i = 0; i < result.getxReg().size(); i++) {
				xStack.add(Integer.toString(result.getxReg().get(i)));
			}
			view.setXStack(xStack);
		} else {
			view.setXStack(new ArrayList<String>());
		}

		if (result.getyReg() != null && result.getyReg().size() > 0) {
			ArrayList<String> yStack = new ArrayList<String>();
			for (int i = 0; i < result.getyReg().size(); i++) {
				yStack.add(Integer.toString(result.getyReg().get(i)));
			}
			view.setYStack(yStack);
		} else {
			view.setYStack(new ArrayList<String>());
		}

		view.setCarryFlag(result.isCarryFlag());
		view.setZeroFlag(result.isZeroFlag());
		view.setNegativeFlag(result.isNegativeFlag());

		if (result.memory[0] !=  -1) {
			HashMap<String, String> memoryValues = new HashMap<String, String>();
			int i = 0;
			while(result.memory[i] != -1) {
				memoryValues.put(Integer.toString(i), Integer.toString(result.memory[i]));
				i++;
			}

			view.setMemoryAllocation(memoryValues);
		} 

		else {
			view.setMemoryAllocation(new HashMap<String, String>());
		}

		view.setConsoleText(result.getOutput());
		view.setButtonsEnabled(result.isSuccessCompile());
	}

	@Override
	public void runClicked() {

		model.run();
		
		Simulator result = model.getSimulator();

		if (result.getAcc() != null) {
			ArrayList<String> accStack = new ArrayList<String>();
			for (int i = 0; i < result.getAcc().size(); i++) {
				accStack.add(Integer.toString(result.getAcc().get(i)));
			}
			view.setAccStack(accStack);
		} else {
			view.setAccStack(new ArrayList<String>());
		}

		if (result.getxReg() != null && result.getxReg().size() > 0) {
			ArrayList<String> xStack = new ArrayList<String>();
			for (int i = 0; i < result.getxReg().size(); i++) {
				xStack.add(Integer.toString(result.getxReg().get(i)));
			}
			view.setXStack(xStack);
		} else {
			view.setXStack(new ArrayList<String>());
		}

		if (result.getyReg() != null && result.getyReg().size() > 0) {
			ArrayList<String> yStack = new ArrayList<String>();
			for (int i = 0; i < result.getyReg().size(); i++) {
				yStack.add(Integer.toString(result.getyReg().get(i)));
			}
			view.setYStack(yStack);
		} else {
			view.setYStack(new ArrayList<String>());
		}

		view.setCarryFlag(result.isCarryFlag());
		view.setZeroFlag(result.isZeroFlag());
		view.setNegativeFlag(result.isNegativeFlag());

		if (result.memory[0] !=  -1) {
			HashMap<String, String> memoryValues = new HashMap<String, String>();
			int i = 0;
			while(result.memory[i] != -1) {
				memoryValues.put(Integer.toString(i), Integer.toString(result.memory[i]));
				i++;
			}

			view.setMemoryAllocation(memoryValues);
		} 

		else {
			view.setMemoryAllocation(new HashMap<String, String>());
		}

		view.setConsoleText(result.getOutput());
		view.setButtonsEnabled(result.isSuccessCompile());
	}


	@Override
	public void stepThroughClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fileOpened(File file) {
		Program program = model.fileToProgram(file);
		if (program != null && program.getProgramStatements() != null) {
			view.setProgramCode(program.getProgramStatements());
		}
	}
	
	@Override
	public void saveToFile(ArrayList<ArrayList<String>> code, String filename) {
		if (code != null && filename != null) {
			Program program = new Program(code);
			File file = model.programToFile(program, filename);
		}
	}
	
	/**
	 * Launches the application by creating a new CecilController.
	 */
	public static void main(String[] args) {
		new Controller();
	}
}
