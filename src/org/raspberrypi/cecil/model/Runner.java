package org.raspberrypi.cecil.model;

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
	private MemoryModel m;

	/**
	 * Constructor
	 */
	public Runner(Compiler compiler, MemoryModel m) {
		this.compiler = compiler;
		this.m = m;
	}

	public MemoryModel getMemoryModel() {
		return this.m;
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
			m.getOutput().add(result(i));
			++i;
			break;

		default:
			i = execute(i);
		}


		checkStatusFlags();
		m.updateViewVars();
		m.memory[m.PROGRAM_COUNTER] = i;
	}

	/**
	 * 
	 * @return
	 */
	public void run(int i) {

		while(m.memory[i] != -1) {	
			//System.out.println("i "+ i+"   mem[i]  "+m.memory[i]);
			
			switch(m.memory[i]) {

			case 0: return;

			case 1: case 2: case 3: 
				m.getOutput().add(result(i));
				++i;
				break;

			default:
				System.out.println("val of i "+ i);
				i = execute(i);
				System.out.println("val of i "+i);
			}

			checkStatusFlags();
			m.updateViewVars();
			m.memory[m.PROGRAM_COUNTER] = i;
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
			return (""+m.memory[m.memory[m.ACCUMULATOR_ADDRESS]]);

		case 2: /* printch */
			return (""+(char)m.memory[m.memory[m.ACCUMULATOR_ADDRESS]]);

		case 3: /* printb */
			return (Integer.toBinaryString(m.memory[m.memory[m.ACCUMULATOR_ADDRESS]]));
		}

		return "";
	}

	/**
	 * Executing each instruction
	 * @param i
	 * @return
	 */
	private int execute(int i) {
		switch(m.memory[i]) {

		/* Unary Instructions */

		case 4: /* cclear */
			m.memory[m.STATUS_ADDRESS] &= (0<<2);
			i++;
			break;
		case 5: /* cset */
			m.memory[m.STATUS_ADDRESS] |= (1<<2);
			i++;
			break;
		case 6: /* lshift */
			m.memory[m.ACCUMULATOR_ADDRESS] <<= 1;
			i++;
			break;
		case 7: /* rshift */
			m.memory[m.ACCUMULATOR_ADDRESS] >>= 1;
			i++;
			break;
		case 8: /* pull */
			if(m.memory[m.STACK_PTR] != m.STACK_BEGIN) {
				m.memory[m.ACCUMULATOR_ADDRESS] = m.memory[m.STACK_PTR]--;
			}
			else m.getOutput().add("Cannot pull because of underflow");
			i++;
			break;
		case 9: /* xinc */
			m.memory[m.XREG_ADDRESS]++;
			i++;
			break;
		case 10: /* xdec */
			m.memory[m.XREG_ADDRESS]--;
			i++;
			break;
		case 11: /* xpull */
			if(m.memory[m.STACK_PTR] != m.STACK_BEGIN) {
				m.memory[m.XREG_ADDRESS] = m.memory[m.STACK_PTR]--;
			}
			else m.getOutput().add("Cannot pull because of underflow");
			i++;
			break;
		case 12: /* xpush */
			if(m.memory[m.STACK_PTR] != m.STACK_END) {
				m.memory[++m.memory[m.STACK_PTR]] = m.memory[m.XREG_ADDRESS];
			}
			else m.getOutput().add("Cannot pull because of overflow");
			i++;
			break;
		case 13: /* push */
			if(m.memory[m.STACK_PTR] != m.STACK_END) {
				m.memory[++m.memory[m.STACK_PTR]] = m.memory[m.ACCUMULATOR_ADDRESS];
			}
			else m.getOutput().add("Cannot pull because of overflow");
			i++;
			break;
		case 14: /* ypush */
			if(m.memory[m.STACK_PTR] != m.STACK_END) {
				m.memory[++m.memory[m.STACK_PTR]] = m.memory[m.YREG_ADDRESS];
			}
			else m.getOutput().add("Cannot pull because of overflow");
			i++;
			break;
		case 15: /* ypull */
			if(m.memory[m.STACK_PTR] != m.STACK_BEGIN) {
				m.memory[m.YREG_ADDRESS] = m.memory[m.STACK_PTR]--;
			}
			else m.getOutput().add("Cannot pull because of underflow");
			i++;
			break;
		case 16: /* yinc */
			m.memory[m.YREG_ADDRESS]++;
			i++;
			break;
		case 17: /* ydec */
			m.memory[m.YREG_ADDRESS]--;
			i++;
			break;

			/* Binary Instructions */	
		case 18: /* load */
			if(checkInsert(i))	{
				m.memory[m.ACCUMULATOR_ADDRESS] = m.memory[++i];
				i++;
			}

			else {
			
				m.getOutput().add("Instruction has to be insert");

			}

			break;
		case 19: /* store */
			if(checkInsert(i))	
				m.memory[m.memory[++i]] = m.memory[m.ACCUMULATOR_ADDRESS];
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 20: /* add */
			if(checkInsert(i))	{
				m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
				m.memory[m.ACCUMULATOR_ADDRESS] += m.memory[m.memory[++i]];
			}
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 21: /* sub */
			if(checkInsert(i)) {	
				m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
				m.memory[m.ACCUMULATOR_ADDRESS] -= m.memory[m.memory[++i]];
			}
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 22: /* and */
			if(checkInsert(i)) {	
				m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
				m.memory[m.ACCUMULATOR_ADDRESS] &= m.memory[m.memory[++i]];
			}
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 23: /* or */
			if(checkInsert(i)) {	
				m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
				m.memory[m.ACCUMULATOR_ADDRESS] |= m.memory[m.memory[++i]];
			}
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 24: /* xor */
			if(checkInsert(i)) {	
				m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
				m.memory[m.ACCUMULATOR_ADDRESS] ^= m.memory[m.memory[++i]];
			}
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 25: /* jump */
			i = m.memory[i+1];
			break;
		case 26: /* comp */
			zeroflagstatus(m.ACCUMULATOR_ADDRESS);
			negativeflagstatus(m.ACCUMULATOR_ADDRESS, m.memory[++i]);
			break;
		case 27: /* jineg */
			/* Description If the negative flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.*/
			if(isBitSet(m.memory[m.STATUS_ADDRESS], 1))
				i = m.memory[i+1];
			break;
		case 28 : /* jipos */
			/*Description If neither the carry or negative flags are set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.*/
			if(!isBitSet(m.memory[m.STATUS_ADDRESS], 1) &&  !isBitSet(m.memory[m.STATUS_ADDRESS], 2))
				i = m.memory[i+1];
			break;
		case 29: /* jizero */
			if(!isBitSet(m.memory[m.STATUS_ADDRESS], 0))
				i = m.memory[i+1];
			break;
		case 30: /* jmptosr */
			m.memory[m.memory[m.STACK_PTR]] = m.memory[i];
			i = m.memory[i+1];
			break;
		case 31: /* jicarry */
			if(!isBitSet(m.memory[m.STATUS_ADDRESS], 2))
				i = m.memory[i+1];
			break;
		case 32: /* xload */
			if(checkInsert(i))	
				m.memory[m.XREG_ADDRESS] = m.memory[++i];
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 33: /* xstore */
			if(checkInsert(i))	
				m.memory[m.memory[++i]] = m.memory[m.XREG_ADDRESS];
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 34: /* loadmx */
			if(checkInsert(i))	
				m.memory[m.ACCUMULATOR_ADDRESS] = m.memory[++i] + m.memory[m.XREG_ADDRESS];
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 35: /* xcomp */
			zeroflagstatus(m.XREG_ADDRESS);		
			negativeflagstatus(m.XREG_ADDRESS, m.memory[++i]);
			break;
		case 36: /* yload */
			if(checkInsert(i))	
				m.memory[m.YREG_ADDRESS] = m.memory[++i];
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 37: /* ystore */
			if(checkInsert(i))	
				m.memory[m.memory[++i]] = m.memory[m.YREG_ADDRESS];
			else m.getOutput().add("Instruction has to be insert");
			break;
		case 38: /* insert */
			
			m.memory[i] = m.memory[++i];
			break;
		case 39: /* ycomp */
			zeroflagstatus(m.YREG_ADDRESS);		
			negativeflagstatus(m.YREG_ADDRESS, m.memory[++i]);
			break;
		}
	
		return i;
	}
	/**
	 * 
	 */
	private void checkStatusFlags() {
		/* checking carry flag status */
		
		if((m.memory[m.ACCUMULATOR_ADDRESS] != -1 && m.memory[m.memory[m.ACCUMULATOR_ADDRESS]] >= 1024)
				|| (m.memory[m.YREG_ADDRESS] != -1 && m.memory[m.memory[m.YREG_ADDRESS]] >= 1024)
				|| (m.memory[m.XREG_ADDRESS] != -1 && m.memory[m.memory[m.XREG_ADDRESS]] >= 1024)) {
			if(m.memory[m.memory[m.ACCUMULATOR_ADDRESS]] >= 1024)
				setRegToMax(m.ACCUMULATOR_ADDRESS);

			if( m.memory[m.memory[m.XREG_ADDRESS]] >= 1024) 
				setRegToMax(m.XREG_ADDRESS);

			if(m.memory[m.memory[m.YREG_ADDRESS]] >= 1024)
				setRegToMax(m.YREG_ADDRESS);
		}

		/* checking negative flag status */
		else if((m.memory[m.ACCUMULATOR_ADDRESS] != -1 && m.memory[m.memory[m.ACCUMULATOR_ADDRESS]] == 0)
				|| (m.memory[m.YREG_ADDRESS] != -1 && m.memory[m.memory[m.YREG_ADDRESS]] == 0)
				|| (m.memory[m.XREG_ADDRESS] != -1 && m.memory[m.memory[m.XREG_ADDRESS]] == 0)) {
			m.memory[m.STATUS_ADDRESS] |= (1<<1);
			m.memory[m.STATUS_ADDRESS] |= 1;
		}

	}

	/**
	 * 
	 * @param register
	 */
	private void setRegToMax(int register) {
		m.memory[m.memory[register]] = 1024;
		m.memory[m.STATUS_ADDRESS] |= (1<<2);
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	private boolean checkInsert(int i) {
		return (compiler.getInstructionField().containsKey(m.memory[i+1]) && compiler.getInstructionField().get(m.memory[i+1]).equals("insert"));
	}

	/**
	 * 
	 */
	private void zeroflagstatus(int register){
		if(m.memory[register] == 0){
			m.memory[m.STATUS_ADDRESS] |= 1;
		}
		else {
			m.memory[m.STATUS_ADDRESS] &= 0;
		}
	}

	/**
	 * 
	 * @param register
	 */
	private void negativeflagstatus(int register, int address){
		if(m.memory[register] <= m.memory[address]){
			m.memory[m.STATUS_ADDRESS] |= (1<<1);
		}
		else {
			m.memory[m.STATUS_ADDRESS] &= (0<<1);
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
}
