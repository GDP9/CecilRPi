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

import org.raspberrypi.cecil.model.interfaces.ModelInterface;
import org.raspberrypi.cecil.pojo.Instruction;
import org.raspberrypi.cecil.pojo.InstructionList;
import org.raspberrypi.cecil.pojo.Program;

/**
 * @author Shreeprabha Carolina
 *
 */
public class Model implements ModelInterface {

	private Simulator sim40;
	private Compiler compiler;
	private Runner runner;
	private static int ptr;
	
	/**
	 * Default model constructor
	 */
	public Model() {
		ptr = 0;
	}

	/**
	 * Method to instantiate a complete set of all the available machine instructions
	 * 
	 *  @return ArrayList<Instruction>
	 */
	@Override
	public ArrayList<Instruction> getInstructions() {
		return (new InstructionList()).getInstructions();
	}

	@Override
	public Simulator getSimulator() {
		return this.sim40;
	}

	@Override
	public void run() {
		sim40 = runner.getSimulator();
		runner.run(0);
	}

	@Override
	public void stepThrough() {
		runner.stepthrough(ptr);
	}

	@Override
	public void compile(Program program) {	
		File file = programToFile(program, "temp.cecil");
		
		compiler = new Compiler(file.getAbsolutePath());
		sim40 = compiler.getSimulator();
		runner = new Runner(compiler, compiler.getSimulator());
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
		for(int line = 0; line < program.getProgramStatements().size(); line++) {
			ArrayList<String> a = program.getProgramStatements().get(line);
			
			/* Generate the input file string */
			input += a.get(0) + " " + a.get(1) + " " + a.get(2) + " " + a.get(3) + "\n";
			
			/* Save the instruction corresponding to the line number */
			sim40.getInstructionLineNumber().put(line, a.get(1));
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
}