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


	@Override
	public CecilResult run() {
		CecilRunner runner = new CecilRunner(compiler.parser, compiler.getMemoryModel());
		
		ArrayList<String> results = runner.run();
		CecilResult r = new CecilResult(results, compiler.getMemoryModel());
		
		return r;
	}

	@Override
	public CecilResult stepThrough(int lineNo) {
		
		
		return null;
	}

	@Override
	public ArrayList<CecilInstruction> getInstructions() {
		// TODO Auto-generated method stub
		return null;
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