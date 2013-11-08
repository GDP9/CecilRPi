package org.raspberrypi.cecil.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.model.Cecil;
import org.raspberrypi.cecil.model.MemoryModel;
import org.raspberrypi.cecil.pojo.CecilInstruction;
import org.raspberrypi.cecil.pojo.CecilProgram;
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
public class CecilController implements CecilControllerInterface {
	private Frame view;
	private Cecil model;

	/**
	 * Constructor which creates a view (Frame) and model (Cecil) object.
	 */
	public CecilController() {
		view = new Frame(this);		
		model = new Cecil();
		view.setInstructionList(model.getInstructions());
	}

	@Override
	public void compileClicked(ArrayList<ArrayList<String>> code) {
		CecilProgram program = new CecilProgram();
		program.setProgramStatements(code);
		
		model.compile(program);
		MemoryModel result = model.getCompiler().getMemoryModel();

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
		
		MemoryModel result = model.getRunner().getMemoryModel();

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


	/**
	 * Launches the application by creating a new CecilController.
	 */
	public static void main(String[] args) {
		new CecilController();
	}
}
