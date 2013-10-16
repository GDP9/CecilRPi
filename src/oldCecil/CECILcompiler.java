package oldCecil;
import javax.swing.JTextArea;

import java.awt.Color;
import java.text.*;
import java.util.*;

public class CECILcompiler {

	/**
	 * @param args
	 */
	static boolean	compileError = false;
	static boolean	labelsNotFound = false;
	static String	progName = "";
	static String	author = "";
	static String	progDate = "";
	static int		progPos;
	static int		progTop;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void errMess(String message) 
	{	terminal.addLine("Compiler error: " + message);
		compileError = true;
		return;
	}
	public static boolean headerOK() {
		//String		nextWord;
		Date		today;
		DateFormat	myDateFormatter;
		
		/*Check for program text*/
		if (parser.currentWord=="") errMess("No program to compile!");
		else{
			terminal.addLine("Checking header...\n");
			/*Check for keyword <program>*/
			if (!parser.currentWord.equals("program")) errMess("Expected <program> but found <"+parser.currentWord+">");
			else {
				progName = parser.restOfLine;
				terminal.addLine("Program name:\t"+progName);
			}
			parser.moveToNextWord();
			/*Check for keyword <author>*/
			if (!parser.currentWord.equals("author")) errMess("Expected <author> but found <"+parser.currentWord+">");
			else {
				author = parser.getRestOfLine();
				terminal.addLine("Author:\t\t"+author);
			}
			parser.moveToNextWord();
			/*Check for keyword <date>*/
			if (!parser.currentWord.equals("date")) errMess("Expected <date> but found <"+parser.currentWord+">");
			else {
				progDate = parser.getRestOfLine();
				terminal.addLine("Program date:\t"+progDate);
			}
			/*Display current date & time*/
			myDateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			today = new Date();
			terminal.addLine("Today & time:\t" + myDateFormatter.format(today));
		}
		terminal.addStars();
		//can't do next line - need nls in for comment lines
		//parser.replaceNewLines();
		return (!compileError);
	}
	public static void processText() 
	{	int		token;
		int		t;
		
		progPos = 0;
		progTop = 0;
		//terminal.addLine(parser.currentWord);
		
	    /*Process text looking for optional label, mandatory command and possible
	         dependent data fields*/
		while(!parser.currentWord.equals(""))
		{	/*trace*/ terminal.addLine("examining <"+parser.currentWord+">");
			//look for label
			if(parser.currentWord.length()>1 && parser.currentWord.substring(0,1).equals("."))
			{	//got one - insert it in the label table
				if(!labeller.addLabel(parser.currentWord.substring(1, parser.currentWord.length()), progPos))errMess("label not assigned");
				parser.moveToNextWord();
			}
			//should now have a command
			token = SIM30.lookUpCommand(parser.currentWord);
			if(token>=0)terminal.addLine("command found: <"+parser.currentWord+">, token is: "+String.valueOf(token));
			else
			{	errMess("Illegal Command <"+parser.currentWord+">");
				token=1023;
			}
			SIM30.writeToMemory(token, progPos);
			progPos++;
			//Is it "insert"?
			if(parser.currentWord.equals("insert"))
			{
				progPos--; //need to overwrite insert code with actual data
				parser.moveToNextWord();
				t=parser.getData();
				if(t>=0)
				{
					SIM30.writeToMemory(t, progPos);
					terminal.addLine("Writing data: "+String.valueOf(t)+" to loc: "+String.valueOf(progPos));
				}
				else 
				{	errMess("Data field not evaluated correctly\n");
					SIM30.writeToMemory(1023,progPos);
				}
				progPos++;
			}
			//Does it take a data field?
			if(SIM30.needsData(token)) 
				{	parser.moveToNextWord();
					t=labeller.getAddress(parser.currentWord); 
					if(t>=0)SIM30.writeToMemory(t, progPos);
					else SIM30.writeToMemory(1023,progPos);
					terminal.addLine("Data field found, adding: "+String.valueOf(t)+" to loc: "+String.valueOf(progPos));
					progPos++;
				}
			parser.moveToNextWord();
		}
		terminal.addLine("Finished!");
		progTop=progPos-1;

		return;
	}
	
	public static boolean compile()
	{
		boolean compiled = false;

		compileError = false;
		terminal.addStars();
		terminal.addLine("Starting compiler");
		labeller.initialise();
		terminal.addLine("Initialising label table");
		if (editor.getText().length()==0) errMess("There's nothing to compile!");
		else 
			{
				parser.init();
				if (headerOK()) 
				{	terminal.addLine("Header OK, compiling program...");
					parser.moveToNextWord();
					processText();
					SIM30.MemDump(0, progTop);
					labeller.printLabelTable();
				}
				if (labelsNotFound) 
				{
					compileError = false;
					labelsNotFound = false;
					terminal.addStars();
					terminal.addLine("Some labels not found");
					terminal.addLine("Checking program second time");
					terminal.addStars();
					parser.init();
					if (headerOK())
					{
						terminal.addLine("Header still OK, compiling program...");
						parser.moveToNextWord();
						//terminal.addLine("noOfLocs: "+String.valueOf(labeller.noOfLocs));
						processText();
					}
				}
				if (labelsNotFound) errMess("!!Some labels still not found");
				terminal.addStars();
				SIM30.MemDump(0, progTop);
				labeller.printLabelTable();
				terminal.addStars();
				if (compileError) 
				{
					terminal.add("!!Program failed to compile!!");
					compiled = false;
				}
				else 
				{
					terminal.add("Program compiled OK");
					compiled = true;
				}
			}
		terminal.addStars();

		return (compiled);
	}
}
