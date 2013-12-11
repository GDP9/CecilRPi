/**
 * This is primary program execution of the CECIL application
 */
package org.raspberrypi.cecil.model;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.OutputError;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;

/**
 * <p>CECIL assembly language Runner 
 * 	<ul>
 * 		<li>runs CECIL Language user input programs</li>
 * 		<li>Handles running errors and adds them to StreamOutputError list</li>
 * 		<li>Only takes place when compilation has been previously successful</li>
 * 	</ul>
 * </p>

 * @author Carolina Ferreira (cf4g09)
 * @author Shreeprabha Aggarwal (sa10g10)
 * @author Southampton University, United Kingdom
 * 
 * @version 1.3
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
	 * <p>Runner's Parametric constructor</p>
	 * </p>Initialses Simulator, ErrorOutputStream and StandardOutputStream</p>
	 * 
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
	 * <p>Runner's Parametric constructor</p>
	 * <p>Initialses Simulator, ErrorOutputStream and StandardOutputStream</p>
	 * <p>Primarily invoked during step-through functionality</p>
	 * 
	 * @param Compiler object
	 * @param Simulator object
	 * @param ErrorOutputStream object
	 * @param StandardOutputStream object
	 */
	public Runner(Compiler compiler , Simulator sim40, ErrorOutputStream errorStream, StandardOutputStream stdStream) {
		this.compiler = compiler;
		this.sim40 = sim40;
		this.errorStream = errorStream;
		this.stdStream = stdStream;
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
	 * Gets Simulator object
	 * @return Simulator object
	 */
	public Simulator getSimulator() {
		return this.sim40;
	}

	/**
	 * <p>Executes each line of user input program like an <b>interpreter</b></p>
	 * <p>This execution goes according to the memory model containing the instructions and data</p>
	 *
	 * @param program counter
	 * @return next line number for highlighting in input editor
	 */
	public int stepthrough(int i) {
		switch(sim40.memory[i]) {

		/* stop */
		case 0: this.isProgramTerminated = true; 
		return i;

		/* print, printb, printch */
		case 1: case 2: case 3: 
			this.stdStream.getOutput().add(result(i));
			++i;
			break;

		default:
			/* jump, comp, xcomp, ycomp, jineg, jipos, jizero, jicarry */
			if(sim40.memory[i] >= 19 && sim40.memory[i] <= 26)
				i = compareExecute(i);

			else if(sim40.memory[i] >= 4 && sim40.memory[i] <= 9) 
				i = unaryExecute(i);

			else 
				i = binaryExecute(i);		

		}
		
		checkResetAllRegsAndFlags();
		sim40.updateViewVars();
		sim40.memory[Simulator.PROGRAM_COUNTER] = i;

		return i;
	}

	/**
	 * <p>Executes &quote;run&quote; of user input program</p>
	 * <p>This execution goes according to the instructions rules<p>
	 * 
	 * @param program counter
	 */
	public void run(int i) {
		while(sim40.memory[i] != -1) {	
			switch(sim40.memory[i]) {

			/* stop */
			case 0: return;

			/* print, printb, printch */
			case 1: case 2: case 3: 
				this.stdStream.getOutput().add(result(i));
				++i;
				break;

			default:
				/* jump, comp, xcomp, ycomp, jineg, jipos, jizero, jicarry */
				if(sim40.memory[i] >= 19 && sim40.memory[i] <= 26)
					i = compareExecute(i);

				else if(sim40.memory[i] >= 4 && sim40.memory[i] <= 9) 
					i = unaryExecute(i);

				else 
					i = binaryExecute(i);		

			}
			
			checkResetAllRegsAndFlags();
			sim40.updateViewVars();
			sim40.memory[Simulator.PROGRAM_COUNTER] = i;
		}

	}


	/**
	 * <p>Executes instructions "print", "printch" and "printb" existent in user input programs
	 * </p>
	 * <p>This execution goes according to the instructions rules
	 * </p>
	 * 
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
	 * <p>Executes comparison instructions &quot;jump&quot;, &quot;comp&quot;, &quot;xcomp&quot;, &quot;ycomp&quot;, &quot;jineg&quot;, &quot;jipos&quot;, &quot;jizero&quot; and &quot;jicarry&quot; existent in user input programs
	 * </p>
	 * <p>This execution goes according to the instructions rules
	 * </p>
	 * 
	 * @param addressable memory location
	 * @return int next addressable memory location
	 */
	private int compareExecute(int i) {
		switch(sim40.memory[i]) {

		case 19: /* jump */
			i = sim40.memory[i+1];
			break;

		case 20: /* comp */
			updateZeroFlagOnComp(Simulator.ACCUMULATOR_ADDRESS, sim40.memory[i+1]);
			updateNegativeFlagOnComp(Simulator.ACCUMULATOR_ADDRESS, sim40.memory[++i]);
			break;

		case 21: /* xcomp */
			updateZeroFlagOnComp(Simulator.XREG_ADDRESS, sim40.memory[i+1]);		
			updateNegativeFlagOnComp(Simulator.XREG_ADDRESS, sim40.memory[++i]);
			break;

		case 22: /* ycomp */
			updateZeroFlagOnComp(Simulator.YREG_ADDRESS, sim40.memory[i+1]);		
			updateNegativeFlagOnComp(Simulator.YREG_ADDRESS, sim40.memory[++i]);
			break;

		case 23: /* jineg */
			if((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<1)) == 2) {
				i = sim40.memory[i+1];
			}

			else 
				i = i + 2;
			break;

		case 24 : /* jipos */
			if(((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<0)) == 0) && ((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<1)) == 0))	
				i = sim40.memory[i+1];
			else 
				i = i + 2;
			break;

		case 25: /* jizero */
			if(((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<0)) == 1))
				i = sim40.memory[i+1];
			else 
				i = i + 2;
			break;

		case 26: /* jicarry */
			if(((sim40.memory[Simulator.STATUS_ADDRESS] & (1<<2)) == 4))
				i = sim40.memory[i+1];
			else 
				i = i + 2;
			break;

		}

		return i;
	}

	/**
	 * <p>Executes unary instruction</p>
	 * 
	 * @param i
	 * @return program counter
	 */
	private int unaryExecute(int i) {
		switch(sim40.memory[i]) {

		/* Unary Instructions */
		case 4: /* cclear */
			switchOffFlag(2);
			break;

		case 5: /* cset */
			switchOnFlag(2);
			break;

		case 6: /* xinc */
			sim40.memory[Simulator.XREG_ADDRESS]++;
			checkResetOverflow(Simulator.XREG_ADDRESS);
			break;

		case 7: /* xdec */
			sim40.memory[Simulator.XREG_ADDRESS]--;
			checkResetUnderflow(Simulator.YREG_ADDRESS);
			break;

		case 8: /* yinc */
			sim40.memory[Simulator.YREG_ADDRESS]++;
			checkResetOverflow(Simulator.YREG_ADDRESS);
			break;

		case 9: /* ydec */
			sim40.memory[Simulator.YREG_ADDRESS]--;
			checkResetUnderflow(Simulator.YREG_ADDRESS);
			break;

		}

		return ++i;
	}

	/**
	 * <p>Executes other instructions "(x,y)load", "add", "sub", "and", "or", "xor" existent in user input programs
	 * </p>
	 * <p>This execution goes according to the instructions rules
	 * </p>
	 * 
	 * @param addressable memory location
	 * @return int next addressable memory location
	 */
	private int binaryExecute(int i) {
		if(!(compiler.getInstructionField().containsKey(sim40.memory[i+1]) && compiler.getInstructionField().get(sim40.memory[i+1]).equals("insert")))
			this.errorStream.getErrors().add(new OutputError(this.sim40.lines[sim40.memory[i+1]],"Must have a matching 'insert' instruction"));

		else {		
			switch(sim40.memory[i]) {

			/* insert is 10 but is already performed during compilation */

			/* Binary Instructions */	
			case 11: /* load */
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] = sim40.memory[sim40.memory[++i]];
				break;

			case 12: /* xload */
				sim40.memory[Simulator.XREG_ADDRESS] = sim40.memory[sim40.memory[++i]];
				break;

			case 13: /* yload */
				sim40.memory[Simulator.YREG_ADDRESS] = sim40.memory[sim40.memory[++i]]; 
				break;

			case 14: /* add */
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] += sim40.memory[sim40.memory[++i]];
				break;

			case 15: /* sub */
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] -= sim40.memory[sim40.memory[++i]];
				break;

			case 16: /* and */
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] &= sim40.memory[sim40.memory[++i]];
				break;

			case 17: /* or */
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] |= sim40.memory[sim40.memory[++i]];
				break;

			case 18: /* xor */
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] ^= sim40.memory[sim40.memory[++i]];
				break;
			}
		}

		
		return ++i;
	}

	/**
	 * <p>Checks for buffer overflow and underflow for all registers and resets them and flags accordingly</p>
	 */
	private void checkResetAllRegsAndFlags() {
		//for(int i = 1026; i <= 1028; i++ ) {
		System.out.println("Checking x val"+sim40.memory[Simulator.XREG_ADDRESS]);
		
		checkResetRegisterFlag(Simulator.ACCUMULATOR_ADDRESS);
			checkResetRegisterFlag(Simulator.XREG_ADDRESS);
			checkResetRegisterFlag(Simulator.YREG_ADDRESS);
			
			
		//}
	}

	/**
	 * <p>Modifies status flags and register values according to register values</p>
	 * @param register: accumulator, x or y
	 */
	private void checkResetRegisterFlag(int register) {
		checkResetOverflow(register);
		checkResetUnderflow(register);

	}

	/**
	 * <p>Checks for buffer overflow and resets the register and flags</p>
	 * @param int register: accumulator, x or y
	 */
	private void checkResetOverflow(int register) {
		/* register values greater than 1024*/
		if(sim40.memory[register] >= 1024) 
			setRegToMax(register);
	}

	/**
	 * <p>Checks for buffer underflow and resets the register and flags</p>
	 * @param int register: accumulator, x or y
	 */
	private void checkResetUnderflow(int register) {
		/* register values smaller than or equal to 0*/
		if(sim40.memory[register] <= 0) {
			setRegToMin(register);
		
		}
	}

	/**
	 * <p>Sets the register value to max 1024
	 * </p>
	 * <p>Sets the carry flag to true
	 * </p>
	 * 
	 * @param register: accumulator, x or y
	 */
	private void setRegToMax(int register) {
		sim40.memory[register] = 1024;
		switchOnFlag(2);
	}

	/**
	 * <p>Sets the register value to min 0
	 * </p>
	 * <p>Sets the zero and negative flag accordingly 
	 * </p>
	 * 
	 * @param register: accumulator, x or y
	 */
	private void setRegToMin(int register) {
		if(sim40.memory[register] < 0)
			switchOnFlag(1);

		switchOnFlag(0);
		sim40.memory[register] = 0;
	}

	/**
	 * <p>Turns the flag on</p>
	 * @param flag
	 */
	private void switchOnFlag(int flag) {
		sim40.memory[Simulator.STATUS_ADDRESS]|= (1<<flag);
	}

	/**
	 * <p>Turns the flag off</p>
	 * @param flag
	 */
	private void switchOffFlag(int flag) {
		sim40.memory[Simulator.STATUS_ADDRESS] = ~(~sim40.memory[Simulator.STATUS_ADDRESS] | (1<<flag));
	}


	/**
	 * Checks the registers values and sets the zero flag accordingly.
	 * 
	 * @param int register
	 * @param int addressable memory location
	 */
	private void updateZeroFlagOnComp(int register, int address){
		if(sim40.memory[register] == sim40.memory[address]){
			switchOnFlag(0);
		}

		else {
			switchOffFlag(0);
		}
	}

	/**
	 * Checks the registers values and sets the negative flag accordingly.
	 * 
	 * @param int register
	 * @param int addressable memory location
	 */
	private void updateNegativeFlagOnComp(int register, int address){
		if(sim40.memory[register] <= sim40.memory[address]){
			switchOnFlag(1);
		}

		else {
			switchOffFlag(1);
		}
	}


	/**
	 * Gets ErrorOutputStream object
	 * @return ErrorOutputStream object
	 */
	public ErrorOutputStream getErrorStream() {
		return errorStream;
	}

	/**
	 * Gets StandardOutputStream object
	 * @return StandardOutputStream object
	 */
	public StandardOutputStream getStdStream() {
		return stdStream;
	}
}