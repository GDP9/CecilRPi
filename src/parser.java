
public class parser {

	/**
	 * @param args
	 */
			static	String	progText;
			static	String	currentLine;
	public	static	String	restOfLine;
	public	static	String	currentWord;
	public	static	String	nextWord="";
			static	char	terminator = ' ';
			static	char	tabChar = '\t';
			static	String	spcStr = " ";
			static	char	newline = '\n';
	
	static	void replaceTabs() 
	/*this replaces tabs with spaces within program*/
	{
 		progText = progText.replace(tabChar, terminator);
 		return;
 	}

	public static	void replaceNewLines() 
	/*this replaces new line chars with spaces within program*/
	{
 		progText = progText.replace(newline, terminator);
 		return;
 	}
	
	public static void moveToNextWord()
	//Gets the next word in the program
	//i.e. sets "currentWord" to the next word in the text,
	//sets "nextWord" to show what will be coming next,
	//and updates the progText
	{
		currentWord=getNextWord();
		progText=progText.substring(Math.min(progText.indexOf(" "), progText.indexOf("\n")), progText.length()-1);
		while(currentWord.length()>0 && currentWord.substring(0,1).equals(";"))
		{	//it's a comment - ignore the rest of the line
			terminal.addLine("Ignoring comment: >"+currentWord+"<");
			getRestOfLine();
			currentWord=getNextWord();
			progText=progText.substring(Math.min(progText.indexOf(" "), progText.indexOf("\n")), progText.length()-1);
		}
		if(!currentWord.equals(""))nextWord=getNextWord();
		return;
	}

 	static String getNextWord()
 	//Gets the next word without moving on in the program text
	{	String	result;
		int		endPtr;
		
		progText = progText.trim()+" \n";
		endPtr = Math.min(progText.indexOf(" "), progText.indexOf("\n"));
		result=progText.substring(0, endPtr);
		return(result);
	}
	
 	public static String getRestOfLine () 
	//This is used to get the rest of the line for the headers
	//At this stage, nls are needed to end the text
 	{	String result="";
 		
 		progText = progText.trim()+"\n";
 		if(progText.indexOf("\n")>0)
 		{	result=progText.substring(0, progText.indexOf("\n"));
 			progText=progText.substring(progText.indexOf("\n"), progText.length()-1);
 		}
 		else result="<parse error!>";
		restOfLine=result;
		return(result);
 	}
	
 	public static int getData()
 	{
 		int		value=-1;
 		Integer	I;
 		I=Integer.valueOf(currentWord);
 		value = I.intValue();
 		return (value);
 	}
 	
 	public static void init()
	//Sets everything up at the start of the compilation phase
	{	//Set up progText
		progText = editor.getText();
		//Change tabs for spaces throughout prog
		replaceTabs();
		//Set up currentWord
		moveToNextWord();
		//And get restOfLine
		restOfLine = getRestOfLine();
		return;
	}
}