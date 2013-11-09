/**
 * 
 */
package org.raspberrypi.cecil.model.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.Cecil;
import org.raspberrypi.cecil.pojo.Program;

/**
 * @author Shreeprabha
 *
 */
public class TestingSavingFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ArrayList<ArrayList<String>> userinput = new ArrayList<ArrayList<String>>();
		ArrayList<String> input = new ArrayList<String>();

		input.add(" ");
		input.add("load");
		input.add("d1");
		input.add(";This is a sample comment");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("print");
		input.add(" ");
		input.add(";This is a sample comment");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("printch");
		input.add(" ");
		input.add(";This is a sample comment");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(" ");
		input.add("stop");
		input.add(" ");
		input.add(" ");
		userinput.add(input);
		input  = new ArrayList<String>();

		input.add(".d1");
		input.add("insert");
		input.add("65");
		input.add(" ");
		userinput.add(input);

		Program program = new Program(userinput);

		Cecil model = new Cecil();
		File file = model.programToFile(program, "test.cecil");

		try {
			String s;
			BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
			while((s = reader.readLine()) != null) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Program inception = model.fileToProgram(file);
		for(ArrayList<String> a: inception.getProgramStatements()){
			for(int i = 0; i < a.size(); i++) {
				System.out.print(a.get(i)+" ");
			}
			System.out.println();
		}
	}
}