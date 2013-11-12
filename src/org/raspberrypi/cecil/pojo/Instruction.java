package org.raspberrypi.cecil.pojo;

/**
*
* CECIL assembly language instructions object.
* CECIL instructions objects are created through this class.
* These objects constitute data on the name and description of CECIL instructions.
* Supplies the necessary information for the instructions tool tips employed by the interface. 
*
* The MIT License (MIT)
* Copyright (c) 2013 Southampton University group GDP9
* 
* @authors Cathy Jin (cj8g10), Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
* Southampton University, United Kingdom
* @version 1.1
* 
* @date 09/11/2013
*  
*
* Instruction language reference:
* 
* 	Instruction "stop"
* 		Machine code: 0
* 		Full description: This simple command tells CECIL to stop executing the program.
* 						  Note that a program may have more than one stop instruction, although good programming practice recommends each program (and subroutine) has only a single entry point, and a single exit point.
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "print" 
* 		Machine code: 1
* 		Full description: This instruction causes the contents of the ACCUMULATOR to be printed in the output window — in the form of a decimal integer.
*		Affected by flags: none
*		Affects flags: none
* 	
* 	Instruction "printch" 
* 		Machine code: 2
* 		Full description: This instruction displays in the output window the character whose ASCII code is currently in the ACCUMULATOR (so if “65” were in the ACCUMULATOR then an upper case “A” would be displayed in the output window).
*		Affected by flags: none
*		Affects flags: none
* 	
* 	Instruction "printb"
* 		Machine code: 3
* 		Full description: This instruction causes the contents of the ACCUMULATOR to be printed in the output window — in the form of a BINARY number. The ACCUMULATOR is unchanged.	
* 		Affected by flags: none
* 		Affects flags: none
* 
*	Instruction "cclear"
* 		Machine code: 4
* 		Full description: This simply operation sets the carry flag to “0” (whatever its previous value).
* 						  Since the carry flag is referred to by the “add” and “sub” operations, it is important to set or clear the flag appropriately before executing those operations.
* 						  Always zero the carry flag before an “add”  statement.
* 		Affected by flags: none 
* 		Affects flags: carry flag is set to 0
* 
*	Instruction "cset"
* 		Machine code: 5
* 		Full description: This simply operation sets the carry flag to “1” (whatever its previous value).
* 						  Since the carry flag is referred to by the “add” and “sub” operations, it is important to set or clear the flag appropriately before executing those operations.
* 						  Always set the carry flag before a “sub”  statement.
* 		Affected by flags: none
* 		Affects flags: carry flag is set to 1 
* 
*	Instruction "lshift"
* 		Machine code: 6
* 		Full description: This instruction works on the value in the ACCUMULATOR as a 10-bit binary number. 
* 						  The instruction moves all the bits along one place to the left — the leftmost (most significant) bit being placed into the carry  flag, and the old value of the carry flag being added as the new rightmost (least significant) bit. 
* 						  The resultant 10-bit word replaces the previous value of the ACCUMULATOR.
* 		Affected by flags: the old value of the carry flag becomes the right-most bit
* 		Affects flags: carry flag set if the most significant bit was set before the “lshift” 
* 
*	Instruction "rshift"
* 		Machine code: 7
* 		Full description: This instruction works on the value in the ACCUMULATOR as a 10-bit binary number. 
* 						  The instruction moves all the bits along one place to the right — the rightmost (least significant) bit being placed into the carry-flag, and the old value of the carry flag being added as the new leftmost (most significant) bit. 
* 						  The resultant 10-bit word replaces the previous value of the ACCUMULATOR.
* 						  Note, this instruction can also be though of as integer division by two (i.e. result is the number of times 2 goes into the value of the ACCUMULATOR), except when the carry flag was 1.
* 		Affected by flags: old value of carry flag becomes new left-most bit
* 		Affects flags: zero flag set if result is zero
* 					   carry flag set to previous right-most bit
* 
* 	Instruction "pull"
* 		Machine code: 8
* 		Full description: Places into the ACCUMULATOR the value at the top of the STACK (and remove that value from the top of the stack).
* 		Affected by flags: none	
* 		Affects flags: none
* 
* 	Instruction "xinc"
* 		Machine code: 9
* 		Full description: This command adds 1 to the current value of the X-register.
* 		Affected by flags: none
* 		Affects flags: sets carry flag if result would have been greater than 1023
* 
* 	Instruction "xdec"
* 		Machine code: 10
* 		Full description: This command subtracts 1 from the current value of the X-register. 
* 						  If this results in a zero or negative result, the appropriate flag is set.
* 		Affected by flags: none
* 		Affects flags: sets zero flag if new X-register value is zero
* 					   sets negative flag if new X-register value is negative
* 
*	 Instruction "xpush"
* 		Machine code: 11
* 		Full description: A copy of the current value of the X register is pushed (copied) onto the top of the stack (leaving the X register unchanged).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "xpull"
* 		Machine code: 12
* 		Full description: The value on the top of the stack is placed into the X-register (and removed from the top of the stack).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "push"
* 		Machine code: 13
* 		Full description: Places a copy of the value of the given address onto the top of the STACK (leaving the value at the address unchanged).	
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "ypush"
* 		Machine code: 14
* 		Full description: Pushes a copy of the number in the Y-register onto the top of the stack.
* 		Affected by flags: none 
* 		Affects flags: none
* 
* 	Instruction "ypull"
* 		Machine code: 15
* 		Full description: pull a copy of the number on  the top of the stack into the Y-register (and remove that number from the stack).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "yinc"
* 		Machine code: 16
*		Full description: This command adds 1 to the current value of the Y-register.
* 		Affected by flags: none
* 		Affects flags: sets carry flag if result would have been greater than 1023
* 
* 	Instruction "ydec"
* 		Machine code: 17
* 		Full description: This command subtracts 1 from the current value of the Y-register. 
* 						  If this results in a zero or negative result, the appropriate flag is set.
* 		Affected by flags: none
* 		Affects flags: sets zero flag if new Y-register value is zero
* 					   sets negative flag if new Y-register value is negative
* 
* 	Instruction "load"
* 		Machine code: 18
* 		Full description: This instruction replaces the contents of the ACCUMULATOR with the integer stored at the labelled address.
* 		Affected by flags: none
* 		Affects flags: none
* 
* 
* 	Instruction "store"
* 		Machine code: 19
* 		Full description: This command will place a copy of the current value in the accumulator at the labelled address (replacing any existing value there).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "add"
* 		Machine code: 20
* 		Full description: This adds the contents of the number at the data label 'Data', the current value of the accumulator (it also adds to this the current value of the carry flag).
* 		Affected by flags: will add the contents of the carry flag (1 or 0) to the result
* 		Affects flags: carry flag set if total of addition is greater than 1023	
* 					   zero flag set if result of addition is zero
* 
* 	Instruction "sub"
* 		Machine code: 21
* 		Full description: The number at the labelled address is subtracted from the current value of the accumulator, leaving the result in the accumulator.
* 		Affected by flags: the current value of the carry flag, this is subtracted as well as the number stored at the labelled address
* 		Affects flags: zero flag set if result is zero 
* 					   negative flag set if result is negative
* 
* 	Instruction "and"
* 		Machine code: 22
* 		Full description: A logical AND is performed between the current value of the ACCUMULATOR and the number at the given address label. The result of the AND is placed in the ACCUMULATOR, and the zero flag set accordingly.	
*						  I.e. for each of the 10 bits in the ACCUMULATOR and the labelled address an AND is performed, and where both bits are 1, the new bit for the ACCUMULATOR is 1. If either or both bits are 0, then the new bit is 0. 
* 		Affected by flags: none
* 		Affects flags: zero flag set if result of AND is zero for all 10 bits
* 
* 	Instruction "or"
* 		Machine code: 23
* 		Full description: A logical OR is performed between the current value of the ACCUMULATOR and the number at the given address label. The result of the AND is placed in the ACCUMULATOR, and the zero flag set accordingly.	
*						  I.e. for each of the 10 bits in the ACCUMULATOR and the labelled address an OR is performed, and where both bits are 1, the new bit for the ACCUMULATOR is 1. If either or both bits are 0, then the new bit is 0. 
* 		Affected by flags: none
* 		Affects flags: zero flag set if result of “OR” is zero for all 10 bits
* 
* 	Instruction "xor"
* 		Machine code: 24
* 		Full description: This instruction performs a logical "EXCLUSIVE OR" operation between the bits of the current value of the ACCUMULATOR, and the integer stored at the labelled address.	
* 						  The result of the “EOR” is stored in the ACCUMULATOR (overwriting the previous value).
* 						  A logical "EOR" compares each bit of two binary numbers in turn, the result of each comparison is a new bit, a '1' if one of the bits were '1', a '0' otherwise (i.e. 0 if both 0 or both 1). 
* 		Affected by flags: none
* 		Affects flags: zero flag set if result of “OR” is zero
* 
* 	Instruction "jump"
* 		Machine code: 25
* 		Full description: Pass control to the instructions beginning at the labelled address.	
*						  (i.e. change the program counter to the labelled address).
* 		Affected by flags: none
* 		Affects flags: none (but changes program counter)
* 
* 	Instruction "comp"
* 		Machine code: 26
* 		Full description: Compares the value at the labelled address with the current value of the Accumulator
* 		Affected by flags: none
* 		Affects flags: zero flag set if numbers are the same
* 					   negative flag set if number at labelled address is larger than value in Accumulator
* 
* 	Instruction "jineg"
* 		Machine code: 27
* 		Full description: If the negative flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
* 		Affected by flags: only executed if negative flag is set
* 		Affects flags: none (but changes program counter)
* 
* 	Instruction "jipos"
* 		Machine code: 28
* 		Full description: If neither the zero or negative flags are set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
* 		Affected by flags: only executed if neither zero flag or negative flag is set
* 		Affects flags: none (but changes program counter)
* 
* 	Instruction "jizero"
* 		Machine code: 29
* 		Full description: If the zero flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
* 		Affected by flags: only executed if zero flag is set	
* 		Affects flags: none (but changes program counter)
* 
* 	Instruction "jmptosr"
* 		Machine code: 30
* 		Full description: Pass control to the instructions beginning at the labelled address, however when the next “return” instruction is executed, program control will return to the instruction immediately following the “jmptosr” instruction.
* 		Affected by flags: none
* 		Affects flags: none (but changes program counter)
* 
* 	Instruction "jicarry"
* 		Machine code: 31
* 		Full description: If the carry flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
* 		Affected by flags: only executed if carry flag is set	
* 		Affects flags: none (but changes program counter)
* 
* 	Instruction "xload"
* 		Machine code: 32
* 		Full description: A copy of the number stored in the labelled address is copied into the X-register.
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "xstore"
* 		Machine code: 33
* 		Full description: The current value of the X-register is copied into the labelled address (the X-register is unchanged).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "loadmx"
* 		Machine code: 34
* 		Full description: This instruction is similar to "load", but includes the current value of the X-register when working out from which memory address to copy the data into the accumulator. 	
* 						  i.e.
*							(1) loadmx first adds the value in the X-register to the memory address of the data label "data"	
*							(2) the result is a address reference (offset from "label" by the number in the X-register)
*							(3) it is the value in this offset address that is loaded into the accumulator
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "xcomp"
* 		Machine code: 35
* 		Full description: The value at the labelled address is compared with the current value of the X-register (rather than with the ACCUMULATOR for the “comp” instruction).
* 		Affected by flags: none
* 		Affects flags: zero flag set if numbers are the same
* 					   negative flag set if number at labelled address is larger than value in X-register
* 
* 	Instruction "yload"
* 		Machine code: 36
* 		Full description: A copy of the number stored in the labelled address is copied into the Y-register.
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "ystore"
* 		Machine code: 37
* 		Full description: The current value of the Y-register is copied into the labelled address (the Y-register is unchanged).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "insert"
* 		Machine code: 38
* 		Full description: Instructs the compiler to store the given integer as a 10-bit word in memory — an address label is optional (but usually present).
* 		Affected by flags: none
* 		Affects flags: none
* 
* 	Instruction "ycomp"
* 		Machine code: 39
* 		Full description: The value at the labelled address is compared with the current value of the Y-register (rather than with the ACCUMULATOR for the “comp” instruction).
* 		Affected by flags: none
* 		Affects flags: zero flag set if numbers are the same
* 					   negative flag set if number at labelled address is larger than value in Y-register
* 
*/
public class Instruction {

	/**
	 * name, tool-tip description, machine code mnemonic fields
	 */
	private String name;
	private String description;
	private int mnemonic;
	
	/**
	 * Parametric Constructor
	 * 
	 * @param name
	 * @param description
	 * @param mnemonic
	 */
	public Instruction(String name, String description, int mnemonic) {
		this.name = name;
		this.description = description;
		this.mnemonic = mnemonic;
	}
	
	/**
	 * Getter method for this instruction's name
	 * 
	 * @return name: String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter method for this instruction's tool-tip description
	 * 
	 * @return description: String
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Getter method for this instruction's machine mnemonic code
	 * 
	 * @return mnemonic: int
	 */
	public int getMnemonic() {
		return this.mnemonic;
	}
}