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

import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.Program;

/**
 * @author Shreeprabha Carolina
 *
 */
public class Cecil implements ModelInterface {

	protected Compiler compiler;
	protected Runner runner;
	private static int ptr;

	public Cecil() {
		ptr = 0;
	}

	@Override
	public void run() {
		runner.run(0);
	}

	@Override
	public void stepThrough() {
		runner.stepthrough(ptr);
	}

	
	@Override
	public ArrayList<Instruction> getInstructions() {
		ArrayList<Instruction> instructions = new ArrayList<Instruction>();
		
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
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @return
	 */
	private Instruction getCecilInstructionData(String name, String description){
		Instruction instruction = new Instruction();
		instruction.setInstructionName(name);
		instruction.setDescription(description);
		return instruction;
	}

	/**
	 * Method to Save a the input editor program into a .cecil file
	 * 
	 * @param input : Program statements from the input editor
	 * @param fileName : name of file (assuming a .cecil extension)
	 */
	public File programToFile(Program program, String fileName) {
		/* Formatted program input to be saved */
		String input = new String("");
		File file = new File(fileName);
		
		/* Formatting input */
		for(ArrayList<String> a: program.getProgramStatements()) {
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
	 * Method to convert the program input from a .cecil file to Program object
	 * 
	 * @param file
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
	
	@Override
	public void compile(Program program) {
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

		System.out.println("Reading file");
		try {
			String s;
			BufferedReader reader = new BufferedReader(new FileReader(samplefile.getAbsolutePath()));
			while((s = reader.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		compiler = new Compiler(samplefile.getAbsolutePath());
		
		System.out.println("Instruction field");
		for(int i: compiler.getInstructionField().keySet()) {
			System.out.println(compiler.getInstructionField().get(i));
		}
		
		runner = new Runner(compiler, compiler.getMemoryModel());
	}

	@Override
	public Compiler getCompiler() {
		return compiler;
	}
	
	@Override
	public Runner getRunner() {
		return runner;
	}
}