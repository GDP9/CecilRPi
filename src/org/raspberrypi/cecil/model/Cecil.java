/**
 * 
 */
package org.raspberrypi.cecil.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.raspberrypi.cecil.pojo.CecilInstruction;
import org.raspberrypi.cecil.pojo.CecilProgram;
import org.raspberrypi.cecil.pojo.CecilResult;

/**
 * @author Shreeprabha Carolina
 *
 */
public class Cecil implements CecilModelInterface {

	protected CecilCompiler compiler;
	protected CecilRunner runner;
	private static int ctr;

	public Cecil() {
		ctr = 0;
	}

	@Override
	public CecilResult run() {
		CecilRunner runner = new CecilRunner(compiler.parser, compiler.getMemoryModel());

		ArrayList<String> results = runner.run(ctr);
		CecilResult r = new CecilResult(results, compiler.getMemoryModel());

		return r;
	}

	@Override
	public CecilResult stepThrough() {
		CecilRunner runner = new CecilRunner(compiler.parser, compiler.getMemoryModel());
		return new CecilResult(runner.stepthrough(ctr), compiler.getMemoryModel());
	}

	@Override
	public ArrayList<CecilInstruction> getInstructions() {
		ArrayList<CecilInstruction> instructions = new ArrayList<CecilInstruction>();
		
		/*Unary instructions*/
		instructions.add(getCecilInstructionData("stop", "CECIL stops executing the program"));
		instructions.add(getCecilInstructionData("print", "Prints numerical contents of Accumulator register"));
		instructions.add(getCecilInstructionData("printch", "Prints the contents of Accumulator in ASCII format"));
		instructions.add(getCecilInstructionData("printb", "Prints the contents of Accumulator in binary format"));
		instructions.add(getCecilInstructionData("cclear", "Sets the carry flag to 0"));
		instructions.add(getCecilInstructionData("cset", "Sets the carry flag to 1"));
		instructions.add(getCecilInstructionData("lshift", "Moves all the bits of accumulator on place to the left"));
		instructions.add(getCecilInstructionData("rshift", "Moves all the bits of accumulator on place to the right"));
		instructions.add(getCecilInstructionData("pull", "Pulls a copy of the number on the top of the stack into the Accumulator"));
		instructions.add(getCecilInstructionData("xinc", "Adds 1 to the current value of the X-register"));
		instructions.add(getCecilInstructionData("xdec", "Subtracts 1 from the current value of the X-register"));
		instructions.add(getCecilInstructionData("xpush", "Pushes onto the top of the stack the current value of the X-register"));
		instructions.add(getCecilInstructionData("xpull", "Pulls the value of the top of the stack into the X-register"));
		instructions.add(getCecilInstructionData("push", "Places a copy of the value of the given address onto the top of the stack"));
		instructions.add(getCecilInstructionData("ypush", "Pushes a copy of the number in the Y-register onto the top of the stack"));
		instructions.add(getCecilInstructionData("ypull", "Pulls a copy of the number on  the top of the stack into the Y-register"));
		instructions.add(getCecilInstructionData("yinc", "Adds 1 to the current value of the Y-register"));
		instructions.add(getCecilInstructionData("ydec", "Subtracts 1 from the current value of the Y-register"));
		
		/*Binary instructions*/
		instructions.add(getCecilInstructionData("load", "Replaces contents of the Accumlator with the integer stored at the labelled address"));
		instructions.add(getCecilInstructionData("store", "Stores a copy of the current value in the Accumulator at the labelled address"));
		instructions.add(getCecilInstructionData("add", "Adds the contents of the Accumulator with the integer stored at the labelled address"));
		instructions.add(getCecilInstructionData("sub", "Subtracts the labelled address from the current value of the Accumulator"));
		instructions.add(getCecilInstructionData("and", "Performs a logical AND between the current value of the Accumulator and the number at the given address label"));
		instructions.add(getCecilInstructionData("or", "Performs a logical OR between the current value of the Accumulator and the number at the given address label"));
		instructions.add(getCecilInstructionData("xor", "Performs a logical EXCLUSIVE OR between the current value of the Accumulator and the number at the given address label"));
		instructions.add(getCecilInstructionData("jump", "Passes control to the instructions beginning at the labelled address"));
		instructions.add(getCecilInstructionData("comp", "Compares the value at the labelled address with the current value of the Accumulator"));
		instructions.add(getCecilInstructionData("jineg", "Jumps to a different part of the program, if the negative flag is set"));
		instructions.add(getCecilInstructionData("jipos", "Jumps to a different part of the program, if neither the carry or negative flags are set"));
		instructions.add(getCecilInstructionData("jizero", "Jumps to a different part of the program, if the zero flag is set"));
		instructions.add(getCecilInstructionData("jmptosr", "Jumps to the subroutuive starting at the given labelled address"));
		instructions.add(getCecilInstructionData("jicarry", "Jumps to a different part of the program, if the carry flag is set"));
		instructions.add(getCecilInstructionData("xload", "Copies the labelled address value into the X-register"));
		instructions.add(getCecilInstructionData("xstore", "Copies the current value of the X-register into the labelled address"));
		instructions.add(getCecilInstructionData("loadmx", "Replaces contents of the Accumulator with the current value of the X-register"));
		instructions.add(getCecilInstructionData("xcomp", "Compares the value at the labelled address with the current value of the X-register"));
		instructions.add(getCecilInstructionData("yload", "Copies the labelled address value into the Y-register"));
		instructions.add(getCecilInstructionData("ystore", "Copies the current value of the Y-register into the labelled address"));
		instructions.add(getCecilInstructionData("insert", "Stores the given integer as a 10-bit word in memory"));
		instructions.add(getCecilInstructionData("ycomp", "Compares the value at the labelled address with the current value of the Y-register"));
		
		return instructions;
	}
	
	private CecilInstruction getCecilInstructionData(String name, String description){
		CecilInstruction instruction = new CecilInstruction();
		instruction.setInstructionName(name);
		instruction.setDescription(description);
		return instruction;
	}
	



	@Override
	public CecilResult compile(CecilProgram program) {
		String sample = new String();
		for(ArrayList<String> a: program.getProgramStatements())
			for(int i = 0; i < a.size(); i++) {
				sample += a.get(i) + " ";
			}

		File samplefile = new File("sample.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(samplefile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sample);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		compiler = new CecilCompiler(samplefile.getAbsolutePath());
		CecilResult result = new CecilResult(compiler.getCompilationErrors(), compiler.getMemoryModel());

		return result;
	}

}