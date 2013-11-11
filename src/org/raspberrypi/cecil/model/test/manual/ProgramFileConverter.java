/**
 * 
 */
package org.raspberrypi.cecil.model.test.manual;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.pojo.Program;

/**
 * @author Shreeprabha
 *
 */
public class ProgramFileConverter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Program program = new Program((new TestInput()).getCorrectInput());

		Model model = new Model();
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