package org.raspberrypi.cecil.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.raspberrypi.cecil.model.Cecil;
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
		CecilResult result = model.compile(program);
		
		if (result.getAccStack() != null && result.getAccStack().length > 0) {
			ArrayList<String> accStack = new ArrayList<String>();
			for (int i = 0; i < result.getAccStack().length; i++) {
				accStack.add(Integer.toString(result.getAccStack()[i]));
			}
			view.setAccStack(accStack);
		} else {
			view.setAccStack(new ArrayList<String>());
		}
		
		if (result.getxStack() != null && result.getxStack().length > 0) {
			ArrayList<String> xStack = new ArrayList<String>();
			for (int i = 0; i < result.getxStack().length; i++) {
				xStack.add(Integer.toString(result.getxStack()[i]));
			}
			view.setXStack(xStack);
		} else {
			view.setXStack(new ArrayList<String>());
		}
		
		if (result.getyStack() != null && result.getyStack().length > 0) {
			ArrayList<String> yStack = new ArrayList<String>();
			for (int i = 0; i < result.getyStack().length; i++) {
				yStack.add(Integer.toString(result.getyStack()[i]));
			}
			view.setYStack(yStack);
		} else {
			view.setYStack(new ArrayList<String>());
		}
		
		view.setCarryFlag(result.isCarryFlag());
		view.setZeroFlag(result.isZeroFlag());
		view.setNegativeFlag(result.isNegativeFlag());
		
		if (result.getMemoryAllocations() != null && result.getMemoryAllocations().length > 0) {
			HashMap<String, String> memoryValues = new HashMap<String, String>();
			for (int i = 0; i < result.getMemoryAllocations().length; i++) {
				if (result.getMemoryAllocations()[i] >= 0) {
					memoryValues.put(Integer.toString(i), Integer.toString(result.getMemoryAllocations()[i]));
				} else {
					memoryValues.put(Integer.toString(i), "");
				}
			}
			view.setMemoryAllocation(memoryValues);
		} else {
			view.setMemoryAllocation(new HashMap<String, String>());
		}
		
		view.setConsoleText(result.getResults());
		view.setButtonsEnabled(result.isSuccess());
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
