/**
 * 
 */
package org.raspberrypi.cecil.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.antlr.grammar.v3.LeftRecursiveRuleWalker.outerAlternative_return;
import org.antlr.gunit.swingui.parsers.StGUnitParser.output_return;
import org.raspberrypi.cecil.model.interfaces.ModelInterface;
import org.raspberrypi.cecil.model.interfaces.SimulatorInterface;
import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.OutputError;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;
import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.InstructionList;
import org.raspberrypi.cecil.pojo.Program;

/**
 * CECIL Application Model
 * Implements two interfaces: ModelInterface and SimulatorInterface
 * Calls on the Compiler and Runner for functionality of the application
 * Gets all standard output and error messages from Compiler and Runner
 * 
 * The MIT License (MIT)
 * Copyright (c) 2013 Southampton University group GDP9
 * 
 * @authors Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
 * Southampton University, United Kingdom
 * @version 1.2
 * 
 * @date 14/11/2013
 * 
 */
public class Model implements ModelInterface, SimulatorInterface {

	private StandardOutputStream stdStream;
	private ErrorOutputStream errorStream;
	private Simulator sim40;
	private Compiler compiler;
	private Runner runner;
	private boolean compileClicked = false;

	private static int ptr;

	/**
	 * Default model constructor
	 * Sets the starting location (addressable memory location) for running and stepping through the program to 0
	 */
	public Model() {
		ptr = 0;
	}

	/**
	 * Instantiates a complete set of all the available machine instructions (InstructionList object)
	 *  @return ArrayList<Instruction>
	 */
	@Override
	public ArrayList<Instruction> getInstructions() {
		return (new InstructionList().getInstructions());
	}

	/**
	 * Returns the machine code of the given instruction.
	 * @param instruction name
	 * @return instruction mnemonic
	 */
	public int instructionToMnemonic(String name) {
		for(Instruction i: getInstructions()) {
			if(i.getName().equals(name))
				return i.getMnemonic();
		}
		return -1;
	}

	/**
	 * Checks if a certain instruction is binary
	 * @param instruction name
	 * @return boolean
	 */
	public boolean isBinaryInstruction(String name){
		if(instructionToMnemonic(name) >= 18)
			return true;

		return false;
	}

	/**
	 * Checks if an instruction is valid
	 * @param instruction given name
	 * @return boolean
	 */
	public boolean isInstruction(String name) {
		if(instructionToMnemonic(name) == -1)
			return false;

		return true;
	}

	/**
	 * Calls in the method run in Runner
	 * Runs the user input program and updates the view accordingly
	 */
	@Override
	public void run() {

		if(ptr==0){
			this.runner = new Runner(this.compiler);
			if(isCompileClicked()){
				setCompileClicked(false);
			}
			else{
				refreshOutputs();
			}
			this.setViewToDefault();
		}
		else
			this.runner= new Runner(this.compiler,this.sim40);

		this.runner.run(ptr);
		this.sim40 = this.runner.getSimulator();
		this.errorStream = this.runner.getErrorStream();
		this.stdStream = this.runner.getStdStream();
		ptr = 0;
	}

	/**
	 * Calls in the method stepthrough in Runner
	 * Steps through the user input program and updates the view accordingly
	 */
	@Override
	public int stepThrough() {

		if(ptr==0){
			if(isCompileClicked()){
				setCompileClicked(false);
				this.setViewToDefault();
			}
			else{
				refreshOutputs();
				this.setViewToDefault();
			}
			this.runner = new Runner(this.compiler);
		}
		else{
			this.runner = new Runner(this.compiler, this.sim40);
		}

		ptr = this.runner.stepthrough(ptr);
		if(ptr == -1) ptr = 0;
		this.sim40 = this.runner.getSimulator();
		this.errorStream = this.runner.getErrorStream();
		this.stdStream = this.runner.getStdStream();
		return this.sim40.lines[ptr];
	}

	/**
	 * Calls in the method compile in Compiler
	 * Compiles the user input program and updates the view accordingly
	 */
	@Override
	public void compile(Program program) {	
		this.setCompileClicked(true);
		ptr = 0;
		File file = programToFile(program, "temp.cecil");
		this.compiler = new Compiler(file.getAbsolutePath(), program);
		this.sim40 = this.compiler.getSimulator();
		this.errorStream = this.compiler.getErrorStream();
		this.stdStream = this.compiler.getStdStream();	
	}

	/**
	 * sets the global boolean variable compileClicked according to if the last user action was compiling their input program
	 * @param boolean compile clicking has occurred
	 */
	private void setCompileClicked (boolean hasOccurred){
		this.compileClicked = hasOccurred;
	}

	/**
	 * gets the global boolean according to if the last user action was compiling their input program 
	 * @return boolean compile clicking has occurred
	 */
	private boolean isCompileClicked (){
		return this.compileClicked;
	}

	/**
	 * Refreshes ErrorOutputStream and StandardOutputStream
	 */
	private void refreshOutputs(){
		if(errorStream !=null)
			errorStream.setErrors(new ArrayList<OutputError>());
		if(stdStream !=null)
			stdStream.setOutput(new ArrayList<String>());
	}

	/**
	 * Saves the input editor program into a .cecil file
	 * @param input : Program statements from the input editor
	 * @param fileName : name of file (assuming a .cecil extension)
	 */
	public File programToFile(Program program, String fileName) {
		/* Formatted program input to be saved */
		String input = new String("");
		File file = new File(fileName);

		/* Formatting input */
		for(int line = 0; line < program.getProgramStatements().size(); line++) {
			ArrayList<String> a = program.getProgramStatements().get(line);

			/* Generate the input file string */
			input += a.get(0) + " " + a.get(1) + " " + a.get(2) + " " + a.get(3) + "\n";
		}

		/* Writing to file */
		try {	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(input);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Sets the Simulator to initiation values
	 */
	public void setViewToDefault(){
		this.sim40 = new Simulator();
	}

	/**
	 * Converts the program input from a .cecil file to Program object
	 * @param user input file
	 * @return Program object
	 */
	public Program fileToProgram(File file) {
		ArrayList<ArrayList<String>> program = new ArrayList<ArrayList<String>>();
		try {
			String s;
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));

			while((s = reader.readLine()) != null) {
				s += "\n";
				ArrayList<String> line = new ArrayList<String>();
				String word = new String("");

				for(int i = 0; s.charAt(i) != '\n'; i++) {
					/*
					 * One space    => second or third column word or fourth column is empty
					 * Two spaces   => first column is empty or fourth column is empty
					 * Three spaces => second or third column is empty
					 */
					if(s.charAt(i) == ' ') {
						if(s.charAt(i+1) == ' ') {
							if(s.charAt(i+2) == ' ') {
								line.add(word);
								word = "";
								line.add(" ");
								i++;
							}

							else if(s.charAt(i+2) == '\n') {
								line.add(word);
								line.add(" ");
								break;
							}

							else
								line.add(" ");
							i++;
						}

						else if(s.charAt(i) == '\n') {
							line.add(word);
							line.add(" ");
							break;
						}

						else {
							line.add(word);
							word = "";
						}
					}

					else if(s.charAt(i) == ';') {
						line.add(s.substring(i, s.length()-1));
						break;
					}

					else	word += s.charAt(i);
				}

				program.add(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Program(program);
	}

	/**
	 * gets boolean isCarryFlag
	 * @return boolean
	 */
	@Override
	public boolean isCarryFlag() {
		return this.sim40.isCarryFlag();
	}

	/**
	 * gets boolean isZeroFlag
	 * @return boolean
	 */
	@Override
	public boolean isZeroFlag() {
		return this.sim40.isZeroFlag();
	}

	/**
	 * gets boolean isNegativeFlag
	 * @return boolean
	 */
	@Override
	public boolean isNegativeFlag() {
		return this.sim40.isNegativeFlag();
	}

	/**
	 * gets boolean isCompileSuccess
	 * @return boolean
	 */
	@Override
	public boolean isCompileSuccess() {
		return this.sim40.isCompileSuccess();
	}

	/**
	 * gets the stack for the X register 
	 * @return ArrayList<Integer> X register values
	 */
	@Override
	public ArrayList<Integer> getXReg() {
		return this.sim40.getXReg();
	}

	/**
	 * gets the stack for the Y register 
	 * @return ArrayList<Integer> Y register values
	 */
	@Override
	public ArrayList<Integer> getYReg() {
		return this.sim40.getYReg();
	}

	/**
	 * gets the stack for the Accumulator register 
	 * @return ArrayList<Integer> Accumulator register values
	 */
	@Override
	public ArrayList<Integer> getAcc() {
		return this.sim40.getAcc();
	}

	/**
	 * gets the CECIL memory manipulated by the user input program
	 * @return int[] memory
	 */
	@Override
	public int[] getMemory() {
		return this.sim40.getMemory();
	}

	/**
	 * sets the ErrorOutpurStream object
	 * @param ErrorOutputStream object
	 */
	public void setErrorStream(ErrorOutputStream stream){
		this.errorStream = stream;
	}

	/**
	 * gets the ErrorOutputStream object
	 * @return ErrorOutputStream object
	 */
	@Override
	public ErrorOutputStream getErrorStream() {
		if(this.errorStream==null)
			return new ErrorOutputStream();
		return this.errorStream;
	}

	/**
	 * gets the StandardOutputStream object
	 * @return StandardOutputStream object
	 */
	@Override
	public StandardOutputStream getStdStream() {
		if(this.stdStream==null)
			return new StandardOutputStream();
		return this.stdStream;
	}
}