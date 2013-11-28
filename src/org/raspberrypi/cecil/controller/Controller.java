/**
 * This is the main Controller of the CECIL application
 */
package org.raspberrypi.cecil.controller;

import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;


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
 * <p>
 * Controller class for interfacing between the model and view.
 * </p>
 * <p>
 * This class creates an instance of the view (Frame) and model (Cecil) classes.
 * </p>
 * <p>
 * This class contains the main method.
 * </p>
 * 
 * @author Cathy Jin (cj8g10)
 * @author Shreeprabha Aggarwal (sa10g10)
 * @author Southampton University, United Kingdom
 * @version 1.3
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

	Synthesizer synthesizer;
	MidiChannel m;

	/**
	 * Launches the application by creating a new CecilController.
	 * @param String[] args : command line user input has not been allowed
	 */
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Controller();
				//new WelcomeMusic().music();
			}
		});
	}


	/**
	 * Constructor which creates a view (Frame) and model (Cecil) object.
	 */
	public Controller() {
		model = new Model(this);
		view = new Frame(this);		

		view.setInstructionList(model.getInstructions());
		view.setProgramCode(this.getDefaultInput());

		sendToIO = false;
		usingRPi = System.getProperty("os.name").toLowerCase().equals("linux") 
				&& System.getProperty("os.arch").toLowerCase().equals("arm");
		view.setIOEnabled(usingRPi);

		/* Code for making sounds */
		try {
			synthesizer = MidiSystem.getSynthesizer();
			if (synthesizer == null) {
				System.out.println("getSynthesizer() failed!");
				return;

			} 
			synthesizer.open();
		}

		catch (Exception ex) {
			ex.printStackTrace(); 
			return; 
		}

		m = synthesizer.getChannels()[0];
		synthesizer.loadInstrument(synthesizer.getDefaultSoundbank().getInstruments()[0]);
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

	/**
	 * Event listener for handling the compile button click event
	 * @param Program consisting of statements
	 */
	@Override
	public void compileClicked(ArrayList<ArrayList<String>> code) {
		if(checkCorrectInput(code)) {
			model.compile(new Program(code));
			
			this.setViewOutput();
			
			if(model.isCompileSuccess()){
				view.setButtonsEnabled(model.isCompileSuccess());
				if(sendToIO)
					for(int i = 0; i < 10; i++) 
						opin.get(i).low();

			}

			else { 
				model.setViewToDefault();
				this.setViewOutput();
				view.setConsoleError(model.getErrorStream().getErrors());
			}
		}
	}

	/**
	 * Event listener for handling the run button click event
	 */
	@Override
	public void runClicked() {
		model.run();
		this.setViewOutput();

		if (sendToIO) {
			sendOutputToGPIO();
		}
	}

	/**
	 * Event listener for handling the step-through button click event
	 * @param int line : remembers the current line of program execution
	 */
	@Override
	public void stepThroughClicked(int line) {
		view.highlightStepThrough(model.stepThrough());
		model.updateLine();
		this.setViewOutput();

		if (sendToIO) {
			sendOutputToGPIO();
		}
	}
	
	
	/**
	 * Setter method to enable or disable the run and step-through button
	 * @param boolean isEnabled
	 */
	public void setButtonsEnabled(boolean isEnabled) {
		view.setButtonsEnabled(isEnabled);
	}

	/**
	 * <p>
	 * <b>GPIO</b> hook that enables the <b>interactive LED Display</b> by transmitting each bit of the
	 * accumulator value as a binary sequence to the physical GPIO ports of the Raspberry Pi
	 * </p>
	 * <p>The ports in use are 1 to 10 on RPi
	 */
	@Override
	public void sendOutputToGPIO() {

		for(int i = 0; i < 10; i++) {
			if((model.getAcc().get(model.getAcc().size() - 1) & (1 << i)) == Math.pow(2, i))	{	
				opin.get(i).high();
				m.noteOn(88+(i*2), 64);
				System.out.println(i+"switched on");
			}

			else { 
				opin.get(i).low();
				m.noteOn(88+(i*2), 64);
				System.out.println(i+"switched off");
			}
		}
	}

	/**
	 * Intialises the GPIO ports to be linked to fixed GPIO pins
	 */
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
	 * User input validation method before sending the input to the compiler to parse
	 * 
	 * @param Program : Cecil program in where each line is in form of ArrayList of Strings(Columns)
	 * @return boolean : Whether or not the user input is partially syntactically correct
	 * 
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
	 * Adds the generated error to the Error OutputStream
	 *  
	 * @param line : line number on the input editor where the OutputError occurred
	 * @param msg : error message
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
		/* Accumulator */
		if (model.getAcc() != null) {
			ArrayList<String> accStack = new ArrayList<String>();
			for (int i = 0; i < model.getAcc().size(); i++) {
				accStack.add(Integer.toString(model.getAcc().get(i)));
			}
			view.setAccStack(accStack);
		} 
		
		else {
			view.setAccStack(new ArrayList<String>());
		}

		/* X-Register */
		if (model.getXReg() != null && model.getXReg().size() > 0) {
			ArrayList<String> xStack = new ArrayList<String>();
			for (int i = 0; i < model.getXReg().size(); i++) {
				xStack.add(Integer.toString(model.getXReg().get(i)));
			}
			view.setXStack(xStack);
		} 
		
		else {
			view.setXStack(new ArrayList<String>());
		}

		/* Y-Register */
		if (model.getYReg() != null && model.getYReg().size() > 0) {
			ArrayList<String> yStack = new ArrayList<String>();
			for (int i = 0; i < model.getYReg().size(); i++) {
				yStack.add(Integer.toString(model.getYReg().get(i)));
			}
			view.setYStack(yStack);
		} 
		
		else {
			view.setYStack(new ArrayList<String>());
		}

		/* Status Flags */
		view.setCarryFlag(model.isCarryFlag());
		view.setZeroFlag(model.isZeroFlag());
		view.setNegativeFlag(model.isNegativeFlag());

		/* Memory Model */
		view.setMemoryAllocation(model.getMemory());

		/* Toggle between StandardOutput and OutputError Streams in console */
		if (!model.isCompileSuccess() && model.getErrorStream() != null && model.getErrorStream().getErrors() != null) {
			view.setConsoleError(model.getErrorStream().getErrors());
		} 
		
		else {
			view.setConsoleResult(model.getStdStream().getOutput());

		}
	}

	/**
	 * Loads the file into the input editor
	 * 
	 * @param File file : file to be opended in the input editor
	 */
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

	/**
	 * Saves the Cecil Program in the file
	 * 
	 * @param Program : Cecil Program c
	 * @param String filename : Cecil program in where each line is in form of ArrayList of Strings(Columns)
	 */
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

	/**
	 * <p>Checks for 
	 * 	<ul>
	 * 		<li>Hardware platform is Raspberry Pi</li>
	 * 		<li>GPIO ports display checkbox is ticked</li>
	 * 	</ul>
	 * <p>
	 * @param booleam ioPortsEnabled : state of the checkbox
	 */
	@Override
	public void ioCheckClicked(boolean ioPortsEnabled) {
		if (ioPortsEnabled && usingRPi) {
			sendToIO = ioPortsEnabled;

			/* Initialse the GPIO ports only once */
			if (gpio == null) {
				gpio  = GpioFactory.getInstance();
				piInitialseGPIO();

			}
		}
	}
}