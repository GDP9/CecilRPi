package org.raspberrypi.cecil.controller;

import java.io.File;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.OutputError;
import org.raspberrypi.cecil.pojo.Program;
import org.raspberrypi.cecil.view.Frame;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Controller class for interfacing between the model and view.
 * This class creates an instance of the view (Frame) and model (Cecil) classes.
 * This class contains the main method.
 * 
 * MIT Open Source License
 * @author Cathy Jin (cj8g10), Shreeprabha Aggarwal (sa10g10)
 * Southampton University, United Kingdom
 * @version 1.2
 * 
 * @date 14/11/2013
 *
 */
public class Controller implements ControllerInterface {
	private Frame view;
	private Model model;
	private boolean usingRPi;
	private boolean sendToIO;
	
	GpioController gpio;
	ArrayList<GpioPinDigitalOutput> opin;

	/**
	 * Launches the application by creating a new CecilController.
	 */
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Controller();
			}
		});
	}


	/**
	 * Constructor which creates a view (Frame) and model (Cecil) object.
	 */
	public Controller() {
		view = new Frame(this);		
		model = new Model(this);

		view.setInstructionList(model.getInstructions());
		view.setProgramCode(this.getDefaultInput());

		sendToIO = false;
		usingRPi = System.getProperty("os.name").toLowerCase().equals("linux") && System.getProperty("os.arch").toLowerCase().equals("arm");
		view.setIOEnabled(usingRPi);
		
		if(usingRPi) {
			gpio  = GpioFactory.getInstance();
			piInitialseGPIO();
		}
	}

	/**
	 * Private method to generate default input editor program
	 * @return Program 
	 */
	private ArrayList<ArrayList<String>> getDefaultInput() {
		ArrayList<ArrayList<String>> program = new ArrayList<ArrayList<String>>();
		ArrayList<String> line = new ArrayList<String>();

		/*line.add(".start");
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
		line.add("printch");
		line.add(" ");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("printb");
		line.add(" ");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("xor");
		line.add("num2");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("printb");
		line.add(" ");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("xdec");
		line.add(" ");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("cset");
		line.add(" ");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("jizero");
		line.add("end");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(" ");
		line.add("print");
		line.add(" ");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();
		line.add(".end");
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
		program.add(line);*/

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
		line.add("60");
		line.add(";");
		program.add(line);

		line = new ArrayList<String>();

		line.add(".num2");
		line.add("insert");
		line.add("5");
		line.add(";");
		program.add(line);

		return program;
	}

	@Override
	public void compileClicked(ArrayList<ArrayList<String>> code) {
		if(checkCorrectInput(code)) {
			model.compile(new Program(code));
			this.setViewOutput();
			if(model.isCompileSuccess()){
				view.setButtonsEnabled(model.isCompileSuccess());
			}
		}

		else { 
			model.setViewToDefault();
			this.setViewOutput();
			view.setConsoleError(model.getErrorStream().getErrors());
		}
	}

	@Override
	public void runClicked() {
		//model.setToDefault();
		model.run();
		this.setViewOutput();

		if (sendToIO) {
			sendOutputToGPIO();
		}
	}

	public void setButtonsEnabled(boolean isEnabled) {
		view.setButtonsEnabled(isEnabled);
	}

	@Override
	public void stepThroughClicked(int line) {
		view.highlightStepThrough(model.stepThrough());
		model.updateLine();
		this.setViewOutput();

		if (sendToIO) {
			sendOutputToGPIO();
		}
	}

	//	/**
	//	 * Testing the method functionality
	//	 */
	//	public void sendOutputToGPIO() {
	//		int val = model.getAcc().get(model.getAcc().size() - 1);
	//		for(int i = 0; i < 10; i++) {
	//			if((val & (1 << i)) == Math.pow(2, i))
	//				System.out.println("port "+(i+1)+"  switch turned on");
	//			else
	//				System.out.println("port "+(i+1)+"  switch turned off");
	//		}
	//	}

	/**
	 * GPIO hook
	 */
	public void sendOutputToGPIO() {
		for(int i = 0; i < 10; i++) {
			if((model.getAcc().get(model.getAcc().size() - 1) & (1 << i)) == Math.pow(2, i))	{	
				opin.get(i).high();
				System.out.println(i+"switched on");
			}

			else { 
				opin.get(i).low();
				System.out.println(i+"switched off");
			}
		}
	}

	private void piInitialseGPIO() {
		/* create gpio controller */
		
		opin = new ArrayList<GpioPinDigitalOutput>();
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09, "MyLED", PinState.LOW));
		opin.add(gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "MyLED", PinState.LOW));
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public boolean checkCorrectInput(ArrayList<ArrayList<String>> code){
		for(int i = 0; i<code.size(); i++) {
			ArrayList<String> input = code.get(i);
			String alphanumeric = "[a-zA-Z][a-zA-Z0-9]*";
			/* Checking for alpha-numeric labelfield*/
			if(!input.get(0).equals(" ") && !input.get(0).substring(1).matches(alphanumeric)) {
				addErrorToOutputstream(i+1, "Incorrectly formed label");
				return false;
			}

			/* Checking for alpha-numeric datafield */
			else if(!input.get(2).equals(" ")) {
				if(input.get(1).equals("insert")) {
					if(!input.get(2).matches("[0-9]*")) {
						addErrorToOutputstream(i+1, "Data should be numeric only");
						return false;
					}
					continue;
				}

				else if(!input.get(2).matches(alphanumeric)) {
					addErrorToOutputstream(i+1, "Incorrectly formed data");
					return false;
				}
			}

			/* Checking for empty instruction */
			if(input.get(1).equals(" ")) {
				/* Checking for missing instruction given datafield or labelfield */
				if(!input.get(0).equals(" ") || !input.get(2).equals(" ")) {
					addErrorToOutputstream(i+1, "An instruction must be provided");
					return false;
				}
			}


			/* Checking for valid instruction */
			else if(!model.isInstruction(input.get(1))) {
				System.out.println("gets here");
				addErrorToOutputstream(i+1, "Invalid instruction provided");
				return false;
			}

			else {
				/* Checking for missing datafield following binary instruction */
				if(model.isBinaryInstruction(input.get(1))) {
					if(input.get(2).equals(" ")) {
						addErrorToOutputstream(i+1, "A binary instruction must be succeeded by a datafield");
						return false;
					}
					continue;
				}

				/* Checking for existing datafield following unary instruction */
				else if(!model.isBinaryInstruction(input.get(1))) {
					if(!input.get(2).equals(" ")){
						addErrorToOutputstream(i+1, "A unary instruction must not be succeeded by a datafield");
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 
	 * @param line
	 * @param msg
	 */
	private void addErrorToOutputstream (int line, String msg){
		ErrorOutputStream e = new ErrorOutputStream();
		e.getErrors().add(new OutputError(line, msg));
		model.setErrorStream(e);
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

	}


	@Override
	public void fileOpened(File file) {
		Program program = model.fileToProgram(file);
		if (program != null && program.getProgramStatements() != null) {
			view.clearVisualisations();
			view.setButtonsEnabled(false);
			view.setProgramCode(program.getProgramStatements());
			view.setFilename(file.getName());
		}
	}

	@Override
	public void saveToFile(ArrayList<ArrayList<String>> code, String filename) {
		if (code != null && filename != null) {
			if (!filename.endsWith(".cecil")) {
				filename += ".cecil";
			}
			Program program = new Program(code);
			File file = model.programToFile(program, filename);
			view.setFilename(file.getName());
		}
	}

	@Override
	public void ioCheckClicked(boolean ioPortsEnabled) {
		if (ioPortsEnabled && usingRPi) {
			sendToIO = ioPortsEnabled;
		}
	}
}