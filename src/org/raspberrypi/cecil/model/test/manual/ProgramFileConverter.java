package org.raspberrypi.cecil.model.test.manual;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.raspberrypi.cecil.controller.Controller;
import org.raspberrypi.cecil.model.Model;
import org.raspberrypi.cecil.pojo.Program;


/**
*
* <p>CECIL ProgramFileConverter model manual tests</p>
* <p>Tests to the Program File Converter functionality</p>

* @author Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10), Southampton University, United Kingdom
* @version 1.2
*
*/
public class ProgramFileConverter {

	/**
	 * <p>Main method to execute manual test</p>
	 * <p>Takes in incorrect and correct program input from TestInput.java</p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Program program = new Program((new TestInput()).getCorrectInput());

		Controller controller = new Controller();
		Model model = new Model(controller);
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