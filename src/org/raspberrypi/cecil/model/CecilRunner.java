package org.raspberrypi.cecil.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

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
 * NEED TO SEND ERRORS AND INSERT CHARACTERS AND STRINGS
 *
 */
public class CecilRunner {

	private CecilParser parser;
	private CecilMemoryModel m;
	private List errors;

	/**
	 * Constructor
	 */
	public CecilRunner(CecilParser parser, CecilMemoryModel m) {
		this.parser = parser;
		this.m = m;
	}

	public ArrayList<String> run() {
		int i = 0;
		ArrayList<String> result = new ArrayList<String>();

		while(m.memory[i] != -1) {
			switch(m.memory[i]) {

			/* Unary Instructions */
			case 0 : /* stop */
				return result;  // -1 means successful program exit
			case 1: /* print */
				result.add(""+m.memory[m.ACCUMULATOR_ADDRESS]);
			case 2: /* printch */
				result.add(""+(char) m.memory[m.ACCUMULATOR_ADDRESS]);
			case 3: /* printb */
				result.add(Integer.toBinaryString(m.memory[m.ACCUMULATOR_ADDRESS]));
			case 4: /* cclear */
				m.memory[m.STATUS_ADDRESS] &= (0<<2);
				break;
			case 5: /* cset */
				m.memory[m.STATUS_ADDRESS] |= (1<<2);
				break;
			case 6: /* lshift */
				m.memory[m.ACCUMULATOR_ADDRESS] <<= 1;
				break;
			case 7: /* rshift */
				m.memory[m.ACCUMULATOR_ADDRESS] >>= 1;
				break;
			case 8: /* pull */
				if(m.memory[m.STACK_PTR] != m.STACK_BEGIN) {
					m.memory[m.ACCUMULATOR_ADDRESS] = m.memory[m.STACK_PTR]--;
				}
				else errors.add("Cannot pull because of underflow");
				break;
			case 9: /* xinc */
				m.memory[m.XREG_ADDRESS]++;
				break;
			case 10: /* xdec */
				m.memory[m.XREG_ADDRESS]--;
				break;
			case 11: /* xpull */
				if(m.memory[m.STACK_PTR] != m.STACK_BEGIN) {
					m.memory[m.XREG_ADDRESS] = m.memory[m.STACK_PTR]--;
				}
				else errors.add("Cannot pull because of underflow");
				break;
			case 12: /* xpush */
				if(m.memory[m.STACK_PTR] != m.STACK_END) {
					m.memory[++m.memory[m.STACK_PTR]] = m.memory[m.XREG_ADDRESS];
				}
				else errors.add("Cannot pull because of overflow");
				break;
			case 13: /* push */
				if(m.memory[m.STACK_PTR] != m.STACK_END) {
					m.memory[++m.memory[m.STACK_PTR]] = m.memory[m.ACCUMULATOR_ADDRESS];
				}
				else errors.add("Cannot pull because of overflow");
				break;
			case 14: /* ypush */
				if(m.memory[m.STACK_PTR] != m.STACK_END) {
					m.memory[++m.memory[m.STACK_PTR]] = m.memory[m.YREG_ADDRESS];
				}
				else errors.add("Cannot pull because of overflow");
				break;
			case 15: /* ypull */
				if(m.memory[m.STACK_PTR] != m.STACK_BEGIN) {
					m.memory[m.YREG_ADDRESS] = m.memory[m.STACK_PTR]--;
				}
				else errors.add("Cannot pull because of underflow");
				break;
			case 16: /* yinc */
				m.memory[m.YREG_ADDRESS]++;
				break;
			case 17: /* ydec */
				m.memory[m.YREG_ADDRESS]--;
				break;

				/* Binary Instructions */	
			case 18: /* load */
				//System.out.println(checkInsert(i));
				
				if(checkInsert(i+1))	
					m.memory[m.ACCUMULATOR_ADDRESS] = m.memory[++i];
				else errors.add("Instruction has to be insert");
				break;
			case 19: /* store */
				if(checkInsert(i))	
					m.memory[m.memory[++i]] = m.memory[m.ACCUMULATOR_ADDRESS];
				else errors.add("Instruction has to be insert");
				break;
			case 20: /* add */
				if(checkInsert(i))	{
					m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
					m.memory[m.ACCUMULATOR_ADDRESS] += m.memory[m.memory[++i]];
				}
				else errors.add("Instruction has to be insert");
				break;
			case 21: /* sub */
				if(checkInsert(i)) {	
					m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
					m.memory[m.ACCUMULATOR_ADDRESS] -= m.memory[m.memory[++i]];
				}
				else errors.add("Instruction has to be insert");
				break;
			case 22: /* and */
				if(checkInsert(i)) {	
					m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
					m.memory[m.ACCUMULATOR_ADDRESS] &= m.memory[m.memory[++i]];
				}
				else errors.add("Instruction has to be insert");
				break;
			case 23: /* or */
				if(checkInsert(i)) {	
					m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
					m.memory[m.ACCUMULATOR_ADDRESS] |= m.memory[m.memory[++i]];
				}
				else errors.add("Instruction has to be insert");
				break;
			case 24: /* xor */
				if(checkInsert(i)) {	
					m.memory[m.memory[i+1]] = m.memory[m.memory[i+1] + 1];
					m.memory[m.ACCUMULATOR_ADDRESS] ^= m.memory[m.memory[++i]];
				}
				else errors.add("Instruction has to be insert");
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
				else errors.add("Instruction has to be insert");
				break;
			case 33: /* xstore */
				if(checkInsert(i))	
					m.memory[m.memory[++i]] = m.memory[m.XREG_ADDRESS];
				else errors.add("Instruction has to be insert");
				break;
			case 34: /* loadmx */
				if(checkInsert(i))	
					m.memory[m.ACCUMULATOR_ADDRESS] = m.memory[++i] + m.memory[m.XREG_ADDRESS];
				else errors.add("Instruction has to be insert");
				break;
			case 35: /* xcomp */
				zeroflagstatus(m.XREG_ADDRESS);		
				negativeflagstatus(m.XREG_ADDRESS, m.memory[++i]);
				break;
			case 36: /* yload */
				if(checkInsert(i))	
					m.memory[m.YREG_ADDRESS] = m.memory[++i];
				else errors.add("Instruction has to be insert");
				break;
			case 37: /* ystore */
				if(checkInsert(i))	
					m.memory[m.memory[++i]] = m.memory[m.YREG_ADDRESS];
				else errors.add("Instruction has to be insert");
				break;
			case 38: /* insert */
				m.memory[i] = m.memory[++i];
				break;
			case 39: /* ycomp */
				zeroflagstatus(m.YREG_ADDRESS);		
				negativeflagstatus(m.YREG_ADDRESS, m.memory[++i]);
				break;
			}

			checkStatusFlags();
			m.memory[m.PROGRAM_COUNTER] = i;
		}
		
		return result;
	}

	/**
	 * 
	 */
	private void checkStatusFlags() {
		/* checking carry flag status */
		if(m.memory[m.ACCUMULATOR_ADDRESS] >= 1024 || m.memory[m.YREG_ADDRESS] >= 1024 || m.memory[m.XREG_ADDRESS] >= 1024) {
			if(m.memory[m.ACCUMULATOR_ADDRESS] >= 1024)
				setRegToMax(m.ACCUMULATOR_ADDRESS);

			if( m.memory[m.XREG_ADDRESS] >= 1024) 
				setRegToMax(m.XREG_ADDRESS);

			if(m.memory[m.YREG_ADDRESS] >= 1024)
				setRegToMax(m.YREG_ADDRESS);
		}

		/* checking negative flag status */
		else if(m.memory[m.ACCUMULATOR_ADDRESS] == 0 || m.memory[m.YREG_ADDRESS] == 0 || m.memory[m.XREG_ADDRESS] == 0) {
			m.memory[m.STATUS_ADDRESS] |= (1<<1);
			m.memory[m.STATUS_ADDRESS] |= 1;
		}
			
	}

	/**
	 * 
	 * @param register
	 */
	private void setRegToMax(int register) {
		m.memory[register] = 1024;
		m.memory[m.STATUS_ADDRESS] |= (1<<2);
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	private boolean checkInsert(int i) {
		//System.out.println(parser.getInstructionfield().get(m.memory[m.memory[i+1]]));
		return (parser.getInstructionfield().containsKey(m.memory[m.memory[i+1]]) && parser.getInstructionfield().get(m.memory[m.memory[i+1]]).equals("insert"));
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