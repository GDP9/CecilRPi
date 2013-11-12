package org.raspberrypi.cecil.model;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;
import org.raspberrypi.cecil.model.outputstream.Error;

/**
 * 
 * CECIL assembly language Runner
 * 
 * MIT Open Source License
 * @authors Shreeprabha Aggarwal (sa10g10), Carolina Ferreira (cf4g09)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 01/11/2013
 *
 * NEED TO SEND m.getOutput() AND INSERT CHARACTERS AND STRINGS
 *
 */
public class Runner {

	private Compiler compiler;
	private Simulator sim40;
	private ErrorOutputStream errorStream;
	private StandardOutputStream stdStream;

	/**
	 * Constructor
	 */
	public Runner(Compiler compiler) {
		this.compiler = compiler;
		this.sim40 = compiler.getSimulator();
		this.errorStream = compiler.getErrorStream();
		this.stdStream = compiler.getStdStream();
	}

	public Simulator getSimulator() {
		return this.sim40;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public void stepthrough(int i) {
		switch(i) {

		case 0: return;

		case 1: case 2: case 3: 
			this.stdStream.getOutput().add(result(i));
			++i;
			break;

		default:
			i = execute(i);
		}


		checkStatusFlags();
		sim40.updateViewVars();
		sim40.memory[Simulator.PROGRAM_COUNTER] = i;
	}

	/**
	 * 
	 * @return
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
				i = execute(i);
			}

			checkStatusFlags();
			sim40.updateViewVars();
			sim40.memory[Simulator.PROGRAM_COUNTER] = i;
		}

	}


	/**
	 * 
	 * @param i
	 * @return
	 */
	private String result(int i) {
		switch(i){

		case 1: /* print */
			return (""+sim40.memory[sim40.memory[Simulator.ACCUMULATOR_ADDRESS]]);

		case 2: /* printch */
			return (""+(char)sim40.memory[sim40.memory[Simulator.ACCUMULATOR_ADDRESS]]);

		case 3: /* printb */
			return (Integer.toBinaryString(sim40.memory[sim40.memory[Simulator.ACCUMULATOR_ADDRESS]]));
		}

		return "";
	}

	/**
	 * Executing each instruction
	 * @param i
	 * @return
	 */
	private int execute(int i) {
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
		case 6: /* lshift */
			sim40.memory[Simulator.ACCUMULATOR_ADDRESS] <<= 1;
			i++;
			break;
		case 7: /* rshift */
			sim40.memory[Simulator.ACCUMULATOR_ADDRESS] >>= 1;
			i++;
			break;
		case 8: /* pull */
			if(sim40.memory[Simulator.STACK_PTR] != Simulator.STACK_BEGIN) {
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] = sim40.memory[Simulator.STACK_PTR]--;
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[i],"Cannot pull because of underflow"));
			
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
		
		case 11: /* xpull */
			if(sim40.memory[Simulator.STACK_PTR] != Simulator.STACK_BEGIN) {
				sim40.memory[Simulator.XREG_ADDRESS] = sim40.memory[Simulator.STACK_PTR]--;
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[i],"Cannot pull because of underflow"));
			
			i++;
			break;
		
		case 12: /* xpush */
			if(sim40.memory[Simulator.STACK_PTR] != Simulator.STACK_END) {
				sim40.memory[++sim40.memory[Simulator.STACK_PTR]] = sim40.memory[Simulator.XREG_ADDRESS];
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[i],"Cannot push because of overflow"));
			
			i++;
			break;
		
		case 13: /* push */
			if(sim40.memory[Simulator.STACK_PTR] != Simulator.STACK_END) {
				sim40.memory[++sim40.memory[Simulator.STACK_PTR]] = sim40.memory[Simulator.ACCUMULATOR_ADDRESS];
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[i],"Cannot push because of overflow"));
			
			i++;
			break;
		
		case 14: /* ypush */
			if(sim40.memory[Simulator.STACK_PTR] != Simulator.STACK_END) {
				sim40.memory[++sim40.memory[Simulator.STACK_PTR]] = sim40.memory[Simulator.YREG_ADDRESS];
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[i],"Cannot push because of overflow"));
			i++;
			break;
		
		case 15: /* ypull */
			if(sim40.memory[Simulator.STACK_PTR] != Simulator.STACK_BEGIN) {
				sim40.memory[Simulator.YREG_ADDRESS] = sim40.memory[Simulator.STACK_PTR]--;
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[i],"Cannot pull because of underflow"));
			
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
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] = sim40.memory[++i];
				i++;
			}

			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));

			break;
			
		case 19: /* store */
			if(checkInsert(i))	
				sim40.memory[sim40.memory[++i]] = sim40.memory[Simulator.ACCUMULATOR_ADDRESS];
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		
		case 20: /* add */
			if(checkInsert(i))	{
				sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] += sim40.memory[sim40.memory[++i]];
			}
			
			else
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		
		case 21: /* sub */
			if(checkInsert(i)) {	
				sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] -= sim40.memory[sim40.memory[++i]];
			}
			else
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		case 22: /* and */
			if(checkInsert(i)) {	
				sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] &= sim40.memory[sim40.memory[++i]];
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		
		case 23: /* or */
			if(checkInsert(i)) {	
				sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] |= sim40.memory[sim40.memory[++i]];
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		
		case 24: /* xor */
			if(checkInsert(i)) {	
				sim40.memory[sim40.memory[i+1]] = sim40.memory[sim40.memory[i+1] + 1];
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] ^= sim40.memory[sim40.memory[++i]];
			}
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		
		case 25: /* jump */
			i = sim40.memory[i+1];
			break;
		
		case 26: /* comp */
			zeroflagstatus(Simulator.ACCUMULATOR_ADDRESS);
			negativeflagstatus(Simulator.ACCUMULATOR_ADDRESS, sim40.memory[++i]);
			break;
		
		case 27: /* jineg */
			/* Description If the negative flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.*/
			if(isBitSet(sim40.memory[Simulator.STATUS_ADDRESS], 1))
				i = sim40.memory[i+1];
			break;
		
		case 28 : /* jipos */
			/*Description If neither the carry or negative flags are set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.*/
			if(!isBitSet(sim40.memory[Simulator.STATUS_ADDRESS], 1) &&  !isBitSet(sim40.memory[Simulator.STATUS_ADDRESS], 2))
				i = sim40.memory[i+1];
			break;
		
		case 29: /* jizero */
			if(!isBitSet(sim40.memory[Simulator.STATUS_ADDRESS], 0))
				i = sim40.memory[i+1];
			break;
		
		case 30: /* jmptosr */
			sim40.memory[sim40.memory[Simulator.STACK_PTR]] = sim40.memory[i];
			i = sim40.memory[i+1];
			break;
		
		case 31: /* jicarry */
			if(!isBitSet(sim40.memory[Simulator.STATUS_ADDRESS], 2))
				i = sim40.memory[i+1];
			break;
		
		case 32: /* xload */
			if(checkInsert(i))	
				sim40.memory[Simulator.XREG_ADDRESS] = sim40.memory[++i];
			
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			break;
		
		case 33: /* xstore */
			if(checkInsert(i))	
				sim40.memory[sim40.memory[++i]] = sim40.memory[Simulator.XREG_ADDRESS];
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			break;
		
		case 34: /* loadmx */
			if(checkInsert(i))	
				sim40.memory[Simulator.ACCUMULATOR_ADDRESS] = sim40.memory[++i] + sim40.memory[Simulator.XREG_ADDRESS];
			else
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			break;
			
		case 35: /* xcomp */
			zeroflagstatus(Simulator.XREG_ADDRESS);		
			negativeflagstatus(Simulator.XREG_ADDRESS, sim40.memory[++i]);
			break;
		
		case 36: /* yload */
			if(checkInsert(i))	
				sim40.memory[Simulator.YREG_ADDRESS] = sim40.memory[++i];
			else 
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));
			
			break;
		
		case 37: /* ystore */
			if(checkInsert(i))	
				sim40.memory[sim40.memory[++i]] = sim40.memory[Simulator.YREG_ADDRESS];
			else
				this.errorStream.getErrors().add(new Error(this.sim40.lines[sim40.memory[i+1]],"Instruction has to be insert"));

			break;
		
		case 38: /* insert */
			sim40.memory[i] = sim40.memory[++i];
			break;
		
		case 39: /* ycomp */
			zeroflagstatus(Simulator.YREG_ADDRESS);		
			negativeflagstatus(Simulator.YREG_ADDRESS, sim40.memory[++i]);
			break;
		}

		return i;
	}
	/**
	 * 
	 */
	private void checkStatusFlags() {
		/* checking carry flag status */

		if((sim40.memory[Simulator.ACCUMULATOR_ADDRESS] != -1 && sim40.memory[sim40.memory[Simulator.ACCUMULATOR_ADDRESS]] >= 1024)
				|| (sim40.memory[Simulator.YREG_ADDRESS] != -1 && sim40.memory[sim40.memory[Simulator.YREG_ADDRESS]] >= 1024)
				|| (sim40.memory[Simulator.XREG_ADDRESS] != -1 && sim40.memory[sim40.memory[Simulator.XREG_ADDRESS]] >= 1024)) {
			if(sim40.memory[sim40.memory[Simulator.ACCUMULATOR_ADDRESS]] >= 1024)
				setRegToMax(Simulator.ACCUMULATOR_ADDRESS);

			if( sim40.memory[sim40.memory[Simulator.XREG_ADDRESS]] >= 1024) 
				setRegToMax(Simulator.XREG_ADDRESS);

			if(sim40.memory[sim40.memory[Simulator.YREG_ADDRESS]] >= 1024)
				setRegToMax(Simulator.YREG_ADDRESS);
		}

		/* checking negative flag status */
		else if((sim40.memory[Simulator.ACCUMULATOR_ADDRESS] != -1 && sim40.memory[sim40.memory[Simulator.ACCUMULATOR_ADDRESS]] == 0)
				|| (sim40.memory[Simulator.YREG_ADDRESS] != -1 && sim40.memory[sim40.memory[Simulator.YREG_ADDRESS]] == 0)
				|| (sim40.memory[Simulator.XREG_ADDRESS] != -1 && sim40.memory[sim40.memory[Simulator.XREG_ADDRESS]] == 0)) {
			sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<1);
			sim40.memory[Simulator.STATUS_ADDRESS] |= 1;
		}

	}

	/**
	 * 
	 * @param register
	 */
	private void setRegToMax(int register) {
		sim40.memory[sim40.memory[register]] = 1024;
		sim40.memory[Simulator.STATUS_ADDRESS] |= (1<<2);
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	private boolean checkInsert(int i) {
		return (compiler.getInstructionField().containsKey(sim40.memory[i+1]) && compiler.getInstructionField().get(sim40.memory[i+1]).equals("insert"));
	}

	/**
	 * 
	 */
	private void zeroflagstatus(int register){
		if(sim40.memory[register] == 0){
			sim40.memory[Simulator.STATUS_ADDRESS] |= 1;
		}
		else {
			sim40.memory[Simulator.STATUS_ADDRESS] &= 0;
		}
	}

	/**
	 * 
	 * @param register
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
	 * 
	 * @param b
	 * @param pos
	 * @return
	 */
	private boolean isBitSet(int b, int pos)
	{
		return (b & (1 << pos)) != 0;
	}

	/**
	 * @return the errorStream
	 */
	public ErrorOutputStream getErrorStream() {
		return errorStream;
	}

	/**
	 * @return the stdStream
	 */
	public StandardOutputStream getStdStream() {
		return stdStream;
	}
}