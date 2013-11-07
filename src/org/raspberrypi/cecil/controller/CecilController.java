package org.raspberrypi.cecil.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.model.Cecil;
import org.raspberrypi.cecil.model.CecilMemoryModel;
import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;
import org.raspberrypi.cecil.view.Frame;

public class CecilController implements CecilControllerInterface {
	private Frame view;
	private Cecil model;

	public CecilController() {
		view = new Frame(this);
		model = new Cecil();
	}

	@Override
	public void compileClicked(ArrayList<ArrayList<String>> code) {
		CecilProgram program = new CecilProgram();
		program.setProgramStatements(code);
		model.compile(program);
		CecilMemoryModel result = model.getResult();

		if (result.getAcc() != null && result.getAcc().length > 0) {
			ArrayList<String> accStack = new ArrayList<String>();
			for (int i = 0; i < result.getAcc().length; i++) {
				accStack.add(Integer.toString(result.getAcc()[i]));
			}
			view.setAccStack(accStack);
		} else {
			view.setAccStack(new ArrayList<String>());
		}

		if (result.getxReg() != null && result.getxReg().length > 0) {
			ArrayList<String> xStack = new ArrayList<String>();
			for (int i = 0; i < result.getxReg().length; i++) {
				xStack.add(Integer.toString(result.getxReg()[i]));
			}
			view.setXStack(xStack);
		} else {
			view.setXStack(new ArrayList<String>());
		}

		if (result.getyReg() != null && result.getyReg().length > 0) {
			ArrayList<String> yStack = new ArrayList<String>();
			for (int i = 0; i < result.getyReg().length; i++) {
				yStack.add(Integer.toString(result.getyReg()[i]));
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
		// TODO Auto-generated method stub

	}

	@Override
	public void stepThroughClicked() {
		// TODO Auto-generated method stub

	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new CecilController();
	}
}
