package org.raspberrypi.cecil.model;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.OutputError;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;

/**
 * 
 * CECIL assembly language Runner
 * runs CECIL Language user input programs
 * Handles running errors and adds them to StreamOutputError list
 * Only takes place when compilation has been previously successful
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
public class Runner {

	private Compiler compiler;
	private Simulator sim40;
	private ErrorOutputStream errorStream;
	private StandardOutputStream stdStream;
	private boolean isProgramTerminated;

	/**
	 * Runner Parametric constructor
	 * Initalises Simulator, ErrorOutputStream and StandardOutputStream
	 * @param Compiler object
	 */
	public Runner(Compiler compiler) {
		this.compiler = compiler;
		this.sim40 = new Simulator();
		this.sim40.memory = compiler.getSimulator().getMemory();
		this.sim40.lines = compiler.getSimulator().lines;
		this.errorStream = new ErrorOutputStream();
		this.stdStream = new StandardOutputStream();
		this.isProgramTerminated = false;
	}
	
	/**
	 * Gets boolean isProgramTerminated
	 * @return boolean isProgramTerminated
	 */
	public boolean isProgramTerminated() {
		return this.isProgramTerminated;
	}

	/**
	 * Sets boolean isProgramTerminated
	 * @param boolean isEnabled
	 */
	public void setIsProgramTerminated(boolean isEnabled) {
		this.isProgramTerminated = isEnabled;
	}
	
	/**
	 * Runner Parametric constructor
	 * Initalises Simulator, ErrorOutputStream and StandardOutputStream
	 * @param Simulator object
	 */
	public Runner(Compiler compiler , Simulator sim40, ErrorOutputStream errorStream, StandardOutputStream stdStream) {
		this.compiler = compiler;
		this.sim40 = sim40;
		this.errorStream = errorStream;
		this.stdStream = stdStream;
		this.isProgramTerminated = false;
	}


	/**
	 * gets Simulator object
	 * @return Simulator object
	 */
	public Simulator getSimulator() {
		return this.sim40;
	}

	/**
	 * Executes "step through" of user input program
	 * This execution goes according to the instructions rules
	 * @param line number
	 * @return next line number
	 */
	public int stepthrough(int i) {
		//System.out.println("into runner step "+i);
		switch(sim40.memory[i]) {

		case 0: this.isProgramTerminated = true; //System.out.println("val from runner "+i);
		return i;

		case 1: case 2: case 3: 
			this.stdStream.getOutput().add(result(i));
			++i;
			break;

		default:
			if(sim40.memory[i] >=25 && sim40.memory[i]<=31)
				i = compareExecute(i);
			
			else if(sim40.memory[i] == 4 || sim40.memory[i] == 5) {
				i = restExecute(i);
				checkStatusFlags();
				sim40.updateViewFlags();
			}
			
			else {
				i = restExecute(i);
				checkStatusFlags();
				sim40.updateViewVars();
			}
		}
		
		sim40.memory[Simulator.PROGRAM_COUNTER] = i;
		
		//System.out.println("val from runner "+i);
		return i;
	}

	/**
	 * Executes "run" of user input program
	 * This execution goes according to the instructions rules
	 * @param line number
	 * @return next line number
	 */
	public void run(int i) {
		while(sim40.memory[i] != -1) {	
			switch(sim40.memory[i]) {

			case 0: return;

			case 1: case 2: case 3: 
				this.stdStream.getOutput().add(result(i));
				++i;
				break;

			default:
				if(sim40.memory[i] >=25 && sim40.memory[i]<=31)
					i = compareExecute(i);
				
				else if(sim40.memory[i] == 4 || sim40.memory[i] == 5) {
					i = restExecute(i);
					checkStatusFlags();
					sim40.updateViewFlags();
				}
				
				else {
					i = restExecute(i);
					checkStatusFlags();
					sim40.updateViewVars();
				}
			}

			sim40.memory[Simulator.PROGRAM_COUNTER] = i;
		}

	}


	/**
	 * Executes instructions "print", "printch" and "printb" existent in user input programs
	 * This execution goes according to the instructions rules
	 * @param addressable memory location
	 * @return String instruction output
	 */
	private String result(int i) {
		switch(sim40.memory[i]){

		case 1: /* print */
			return (""+sim40.memory[Simulator.ACCUMULATOR_ADDRESS]);

		case 2: /* printch */
			return (""+(char)sim40.memory[Simulator.ACCUMULATOR_ADDRESS]);

		case 3: /* printb */
			return (Integer.toBinaryString(sim40.memory[Simulator.ACCUMULATOR_ADDRESS]));
		}

		return "";
	}

	/**
	 * Executes comparison instructions "jump", "comp", "jineg", "jipos", "jizero" and "jicarry" existent in user input programs
	 * This execution goes according to the instructions rules
	 * @param addressable memory location
	 * @return int next addressable memory location
	 */
	private int compareExecute(int i) {
		switch(sim40.memory[i]) {

		case 25: /* jump */
			i = sim40.memory[i+1];
			break;

		case 26: /* comp */
			zeroflagstatus(Simulator.ACCUMULATOR_ADDRESS, sim40.memory[i+1]);
			negativeflagstatus(Simulator.ACCUMULATOR_ADDRESS, sim40.memory[++i]);
			break;

		case 27: /* jineg */
			if((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<1)) == 2) {
				i = sim40.memory[i+1];
				
			}
			else 
				i = i+2;
			break;

		case 28 : /* jipos */
			if(((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<1)) == 0) && ((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<1)) == 0))	
				i = sim40.memory[i+1];
			else 
				i = i+2;
			break;

		case 29: /* jizero */
			if(((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<0)) == 1))
				i = sim40.memory[i+1];
			else 
				i = i+2;
			break;

		case 31: /* jicarry */
			if(((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<2)) == 4))
				i = sim40.memory[i+1];
			else 
				i = i+2;
			break;
		}
		return i;
	}

	/**
	 * Executes other instructions "cclear", "cset", "(y)xinc", "(y)xdec","(x,y)load", "(x,y)store", "add", "sub", "and", "or", "xor", "loadmx" and "insert" existent in user input programs
	 * This execution goes according to the instructions rules
	 * @param addressable memory location
	 * @return int next addressable memory location
	 */
	private int restExecute(int i) {
		switch(sim40.memory[i]) {

		/* Unary Instructions */

		case 4: /* cclear */
			sim40.memory[Simulator.STATUS_ADDRESS] &= (0<<2);
			i++;
			break;

		case 5: /* cset */
			sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<2);
			i++;
			break;

		case 9: /* xinc */
			sim40.memory[Simulator.XREG_ADDRESS]++;
			i++;
			break;

		case 10: /* xdec */
			sim40.memory[Simulator.XREG_ADDRESS]--;
			i++;
			break;

		case 16: /* yinc */
			sim40.memory[Simulator.YREG_ADDRESS]++;
			i++;
			break;

		case 17: /* ydec */
			sim40.memory[Simulator.YREG_ADDRESS]--;
			i++;
			break;

			/* Binary Instructions */	
		case 18: /* load */
			if(checkInsert(i))	{
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] = sim40.memory[sim40.memory[++i]];
				i++;
			}

			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;

		case 19: /* store */
			if(checkInsert(i))	{
				sim40.memory[sim40.memory[++i]] = sim40.memory[Simulator.ACCUMULATOR_ADDRESS];
				i++;
			}
			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;

		case 20: /* add */
			if(checkInsert(i))	{
				//sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] += sim40.memory[sim40.memory[++i]];
				i++;
			}

			else
			{
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));
			}

			break;

		case 21: /* sub */
			if(checkInsert(i)) {	
				//sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] -= sim40.memory[sim40.memory[++i]];
				i++;
			}
			else
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;
		case 22: /* and */
			if(checkInsert(i)) {	
				//sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] &= sim40.memory[sim40.memory[++i]];
				i++;
			}

			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;

		case 23: /* or */
			if(checkInsert(i)) {	
				//sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] |= sim40.memory[sim40.memory[++i]];
				i++;
			}

			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;

		case 24: /* xor */
			if(checkInsert(i)) {	
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] ^= sim40.memory[sim40.memory[++i]];
				i++;
			}

			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;
		case 32: /* xload */
			
			if(checkInsert(i))	{
				sim40.memory[Simulator.XREG_ADDRESS] = sim40.memory[sim40.memory[++i]];
				i++;
			}
			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));
			break;

		case 33: /* xstore */
			if(checkInsert(i))	
				sim40.memory[sim40.memory[++i]] = sim40.memory[Simulator.XREG_ADDRESS];
			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));
			break;

		case 34: /* loadmx */
			if(checkInsert(i))	
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] = sim40.memory[sim40.memory[++i]] + sim40.memory[Simulator.XREG_ADDRESS];
			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));
			break;

		case 35: /* xcomp */
			zeroflagstatus(Simulator.XREG_ADDRESS, sim40.memory[i+1]);		
			negativeflagstatus(Simulator.XREG_ADDRESS, sim40.memory[++i]);
			break;

		case 36: /* yload */
			if(checkInsert(i))	{
				sim40.memory[Simulator.YREG_ADDRESS] = sim40.memory[sim40.memory[++i]]; 
				i++;
			}
			else 
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;

		case 37: /* ystore */
			if(checkInsert(i))	
				sim40.memory[sim40.memory[++i]] = sim40.memory[Simulator.YREG_ADDRESS];
			else
				this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

			break;

		case 38: /* insert */
			sim40.memory[i] = sim40.memory[++i];
			break;

		case 39: /* ycomp */
			zeroflagstatus(Simulator.YREG_ADDRESS, sim40.memory[i+1]);		
			negativeflagstatus(Simulator.YREG_ADDRESS, sim40.memory[++i]);
			break;
		}
		return i;
	}

	/**
	 * Modifies status flags according to register values
	 * Modifies carry, negative and zero flags
	 */
	private void checkStatusFlags() {

		this.checkFlags(Simulator.ACCUMULATOR_ADDRESS);
		this.checkFlags(Simulator.XREG_ADDRESS);
		this.checkFlags(Simulator.YREG_ADDRESS);
	}


	/**
	 * Modifies status flags and register values according to register values
	 * @param register
	 */
	private void checkFlags(int register) {
		/*register values greater than 1024*/
		if(sim40.memory[register] >= 1024) 
			setRegToMax(register);


		/*register values smaller than or equal to 0*/
		else if(sim40.memory[register] <= 0) {
			setRegToMin(register);
		}
	}

	/**
	 * Sets the register value to max 1024
	 * Sets the carry flag to true
	 * @param register
	 */
	private void setRegToMax(int register) {
		sim40.memory[register] = 1024;
		sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<2);
	}

	/**
	 * Sets the register value to min 0
	 * Sets the zero and negative flag accordingly 
	 * @param register
	 */
	private void setRegToMin(int register) {
		if(sim40.memory[register] < 0)
			sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<1);

		sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<0);
		sim40.memory[register] = 0;
	}

	/**
	 * Checks if insert instruction exists for a given load instruction.
	 * All load instructions must have a corresponding "insert" instruction so the data field is not null
	 * @param addressable memory location
	 * @return boolean
	 */
	private boolean checkInsert(int i) {
		return (compiler.getInstructionField().containsKey(sim40.memory[i+1]) && compiler.getInstructionField().get(sim40.memory[i+1]).equals("insert"));
	}

	/**
	 * Checks the registers values and sets the zero flag accordingly.
	 * @param int register
	 * @param int addressable memory location
	 */
	private void zeroflagstatus(int register, int address){
		if(sim40.memory[register] == sim40.memory[address]){
			sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<0);
		}
		else {
			sim40.memory[Simulator.STATUS_ADDRESS] &= (0<<0);
		}
	}

	/**
	 * Checks the registers values and sets the negative flag accordingly.
	 * @param int register
	 * @param int addressable memory location
	 */
	private void negativeflagstatus(int register, int address){
		if(sim40.memory[register] <= sim40.memory[address]){
			sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<1);
		}
		else {
			sim40.memory[Simulator.STATUS_ADDRESS] &= (0<<1);
		}
	}

	/**
	 * gets ErrorOutputStream object
	 * @return ErrorOutputStream object
	 */
	public ErrorOutputStream getErrorStream() {
		return errorStream;
	}

	/**
	 * gets StandardOutputStream object
	 * @return StandardOutputStream object
	 */
	public StandardOutputStream getStdStream() {
		return stdStream;
	}
}