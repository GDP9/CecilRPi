package org.raspberrypi.cecil.pojo;

/**
 *
 * <p>CECIL assembly language instructions object.</p>
 * <p>CECIL instructions objects are created through this class.</p>
 * <p>These objects constitute data on the name and description of CECIL instructions.</p>
 * <p>Supplies the necessary information for the instructions tool tips employed by the interface. </p>
 *
 * <p>Instruction language reference:
 * <ul>
 *
 * 	<li>
 * 	<p>Instruction "stop"</p>
 * 	<p>Machine code: 0</p>
 * 	<p>Full description: This simple command tells CECIL to stop executing the program.
 * 						  Note that a program may have more than one stop instruction, although good programming practice recommends each program (and subroutine) has only a single entry point, and a single exit point.
 * 	</p><p>Affected by flags: none</p>
 * 	<p>Affects flags: none</p>
 * 	</li>
 *
 * <li>
 * 	<p>Instruction "print" </p>
 * 	<p>Machine code: 1</p>
 * 	<p>Full description: This instruction causes the contents of the ACCUMULATOR to be printed in the output window — in the form of a decimal integer.
 *	</p><p>Affected by flags: none</p>
 *	<p>Affects flags: none</p>
 *</li>
 * 	
 *<li>
 *	<p>Instruction "printch" </p>
 * 		<p>Machine code: 2</p>
 * 		<p>Full description: This instruction displays in the output window the character whose ASCII code is currently in the ACCUMULATOR (so if “65” were in the ACCUMULATOR then an upper case “A” would be displayed in the output window).
 *		</p><p>Affected by flags: none</p>
 *		<p>Affects flags: none</p>
 * </li>
 * <li>
 * 	<p>Instruction "printb"</p>
 * 	<p>	Machine code: 3</p>
 * 		<p>Full description: This instruction causes the contents of the ACCUMULATOR to be printed in the output window — in the form of a BINARY number. The ACCUMULATOR is unchanged.	
 * 		</p><p>Affected by flags: none</p>
 * 		<p>Affects flags: none</p>
 * </li>
 * <li>
 *	<p>Instruction "cclear"</p>
 * 	<p>	Machine code: 4</p>
 * 		<p>Full description: This simply operation sets the carry flag to “0” (whatever its previous value).
 * 						  Since the carry flag is referred to by the “add” and “sub” operations, it is important to set or clear the flag appropriately before executing those operations.
 * 						  Always zero the carry flag before an “add”  statement.
 * 		</p><p>Affected by flags: none </p>
 * 		<p>Affects flags: carry flag is set to 0</p>
 * </li>
 * <li>
 *	<p>Instruction "cset"</p>
 * 	<p>	Machine code: 5</p>
 * 		<p>Full description: This simply operation sets the carry flag to “1” (whatever its previous value).
 * 						  Since the carry flag is referred to by the “add” and “sub” operations, it is important to set or clear the flag appropriately before executing those operations.
 * 						  Always set the carry flag before a “sub”  statement.
 * 		</p><p>Affected by flags: none</p>
 * 		<p>Affects flags: carry flag is set to 1 </p>
 * </li>
 * <li>
 * 	<p>Instruction "xinc"
 * 	</p><p>	Machine code: 6
 * 	</p><p>	Full description: This command adds 1 to the current value of the X-register.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: sets carry flag if result would have been greater than 1023
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "xdec"
 * 	</p><p>	Machine code: 7
 * 	</p><p>	Full description: This command subtracts 1 from the current value of the X-register. 
 * 						  If this results in a zero or negative result, the appropriate flag is set.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: sets zero flag if new X-register value is zero
 * 					   sets negative flag if new X-register value is negative
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "yinc"
 * 	</p><p>	Machine code: 8
 *	</p><p>	Full description: This command adds 1 to the current value of the Y-register.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: sets carry flag if result would have been greater than 1023
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "ydec"
 * 	</p><p>	Machine code: 9
 * 	</p><p>	Full description: This command subtracts 1 from the current value of the Y-register. 
 * 						  If this results in a zero or negative result, the appropriate flag is set.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: sets zero flag if new Y-register value is zero
 * 					   sets negative flag if new Y-register value is negative
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>	Instruction "insert"
 * 	</p><p>	Machine code: 10
 * 	</p><p>	Full description: Instructs the compiler to store the given integer as a 10-bit word in memory — an address label is optional (but usually present).
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: none
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "load"
 * 	</p><p>	Machine code: 11
 * 	</p><p>	Full description: This instruction replaces the contents of the ACCUMULATOR with the integer stored at the labelled address.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: none
 * 
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "xload"
 * 	</p><p>	Machine code: 12
 * 	</p><p>	Full description: A copy of the number stored in the labelled address is copied into the X-register.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: none
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "yload"
 * 	</p><p>	Machine code: 13
 * 	</p><p>	Full description: A copy of the number stored in the labelled address is copied into the Y-register.
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: none
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "add"
 * 	</p><p>	Machine code: 14
 * 	</p><p>	Full description: This adds the contents of the number at the data label 'Data', the current value of the accumulator (it also adds to this the current value of the carry flag).
 * 	</p><p>	Affected by flags: will add the contents of the carry flag (1 or 0) to the result
 * 	</p><p>	Affects flags: carry flag set if total of addition is greater than 1023	
 * 					   zero flag set if result of addition is zero
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "sub"
 * 	</p><p>	Machine code: 15
 * 	</p><p>	Full description: The number at the labelled address is subtracted from the current value of the accumulator, leaving the result in the accumulator.
 * 	</p><p>	Affected by flags: the current value of the carry flag, this is subtracted as well as the number stored at the labelled address
 * 	</p><p>	Affects flags: zero flag set if result is zero 
 * 					   negative flag set if result is negative
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "and"
 * 	</p><p>	Machine code: 16
 * 	</p><p>	Full description: A logical AND is performed between the current value of the ACCUMULATOR and the number at the given address label. The result of the AND is placed in the ACCUMULATOR, and the zero flag set accordingly.	
 *						  I.e. for each of the 10 bits in the ACCUMULATOR and the labelled address an AND is performed, and where both bits are 1, the new bit for the ACCUMULATOR is 1. If either or both bits are 0, then the new bit is 0. 
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: zero flag set if result of AND is zero for all 10 bits
 * 
 * 	</p></li>
 * <li>
 * <p>Instruction "or"
 * 	</p><p>	Machine code: 17
 * 	</p><p>	Full description: A logical OR is performed between the current value of the ACCUMULATOR and the number at the given address label. The result of the AND is placed in the ACCUMULATOR, and the zero flag set accordingly.	
 *						  I.e. for each of the 10 bits in the ACCUMULATOR and the labelled address an OR is performed, and where both bits are 1, the new bit for the ACCUMULATOR is 1. If either or both bits are 0, then the new bit is 0. 
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: zero flag set if result of “OR” is zero for all 10 bits
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "xor"
 * 	</p><p>	Machine code: 18
 * 	</p><p>	Full description: This instruction performs a logical "EXCLUSIVE OR" operation between the bits of the current value of the ACCUMULATOR, and the integer stored at the labelled address.	
 * 						  The result of the “EOR” is stored in the ACCUMULATOR (overwriting the previous value).
 * 						  A logical "EOR" compares each bit of two binary numbers in turn, the result of each comparison is a new bit, a '1' if one of the bits were '1', a '0' otherwise (i.e. 0 if both 0 or both 1). 
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: zero flag set if result of “OR” is zero
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "jump"
 * 	</p><p>	Machine code: 19
 * 	</p><p>	Full description: Pass control to the instructions beginning at the labelled address.	
 *						  (i.e. change the program counter to the labelled address).
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: none (but changes program counter)
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "comp"
 * 	</p><p>	Machine code: 20
 * 	</p><p>	Full description: Compares the value at the labelled address with the current value of the Accumulator
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: zero flag set if numbers are the same
 * 					   negative flag set if number at labelled address is larger than value in Accumulator
 *
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "xcomp"
 * 	</p><p>	Machine code: 21
 * 	</p><p>	Full description: The value at the labelled address is compared with the current value of the X-register (rather than with the ACCUMULATOR for the “comp” instruction).
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: zero flag set if numbers are the same

 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "ycomp"
 * 	</p><p>	Machine code: 22
 * 	</p><p>	Full description: The value at the labelled address is compared with the current value of the Y-register (rather than with the ACCUMULATOR for the “comp” instruction).
 * 	</p><p>	Affected by flags: none
 * 	</p><p>	Affects flags: zero flag set if numbers are the same
 * 					   negative flag set if number at labelled address is larger than value in Y-register
 *
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "jineg"
 * 	</p><p>Machine code: 23
 * 		</p><p>Full description: If the negative flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
 * 	</p><p>	Affected by flags: only executed if negative flag is set
 * 	</p><p>Affects flags: none (but changes program counter)
 * 
 * 	</p><p>Instruction "jipos"
 * 	</p><p>	Machine code: 24
 * 	</p><p>	Full description: If neither the zero or negative flags are set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
 * 	</p><p>Affected by flags: only executed if neither zero flag or negative flag is set
 * 	</p><p>Affects flags: none (but changes program counter)
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "jizero"
 * 	</p><p>	Machine code: 25
 * </p><p>	Full description: If the zero flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
 * 	</p><p>	Affected by flags: only executed if zero flag is set	
 * 	</p><p>	Affects flags: none (but changes program counter)
 * 
 * 	</p>
 * </li>
 * <li>
 * <p>Instruction "jicarry"
 * 	</p><p>	Machine code: 26
 * 	</p><p>	Full description: If the carry flag is set, THEN jump to a different part of the program (change the program counter to the labelled address), otherwise do nothing.
 * 	</p><p>	Affected by flags: only executed if carry flag is set	
 * 	</p><p>	Affects flags: none (but changes program counter)
 * </p>
 *</li>
 * </ul>
 * </p>
 * 
 * @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Cathy Jin (cj8g10), Southampton University, United Kingdom
 * @version 1.2
 * 
 */
public class Instruction {

	/*
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
	 * Gets instruction name
	 * @return String name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets an instruction's tool-tip description
	 * @return String description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets an instruction's machine mnemonic code
	 * @return int mnemonic
	 */
	public int getMnemonic() {
		return this.mnemonic;
	}
}