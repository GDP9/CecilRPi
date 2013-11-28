// $ANTLR 3.5.1 C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g 2013-11-28 01:20:55
 
  package org.raspberrypi.cecil.model.grammar;
  import java.util.HashMap;
  import java.util.ArrayList;
  import org.raspberrypi.cecil.model.*;
  import org.raspberrypi.cecil.pojo.*;
  import org.raspberrypi.cecil.model.outputstream.OutputError;
  import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
  import org.raspberrypi.cecil.model.grammar.AssignmentException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * CECIL assembly language grammar definition
 * This grammar constitutes all the lexer rules employed by CECIL and corresponding parsing rules.
 * It is a version of the CECIL Language originally developed by David Argles.
 * it contains 39 simple instructions and user input datafields and labelfields.
 * It catches the relevant errors thrown by the lexer and parser.
 * The language used is Java.
 * 
 * The MIT License (MIT)
 * Copyright (c) 2013 Southampton University group GDP9
 * 
 * @authors Carolina Ferreira (cf4g09), Shreeprabha Aggarwal (sa10g10)
 * Southampton University, United Kingdom
 * @version 1.2
 * 
 * @date 14/11/2013
 *
 */
@SuppressWarnings("all")
public class CecilParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "DIGIT", "NAME", "WS", 
		"'.'", "'.start'", "'add'", "'and'", "'cclear'", "'comp'", "'cset'", "'insert'", 
		"'jicarry'", "'jineg'", "'jipos'", "'jizero'", "'jump'", "'load'", "'or'", 
		"'print'", "'printb'", "'printch'", "'stop'", "'sub'", "'xcomp'", "'xdec'", 
		"'xinc'", "'xload'", "'xor'", "'ycomp'", "'ydec'", "'yinc'", "'yload'"
	};
	public static final int EOF=-1;
	public static final int T__8=8;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int COMMENT=4;
	public static final int DIGIT=5;
	public static final int NAME=6;
	public static final int WS=7;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public CecilParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public CecilParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return CecilParser.tokenNames; }
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g"; }

	 
	    /* Simulator */
	    private Simulator sim40;
	    
	    /* Machine Instructions */
	    private InstructionList instructionList;
	    
	    /* Local variables */
	    private static int pointer;
	    private HashMap<Integer, String> datafield;
	    private HashMap<String, Integer> labelfield;
	    private HashMap<Integer, String> instructionfield;    
	    private ErrorOutputStream stream;
	    
	    /**
	     * This method is invoked before calling the .program method
	     */
	    public void initialise() {
	      pointer = 0;
	      
	      sim40 = new Simulator();
	      
	      datafield = new HashMap<Integer, String>();
	      labelfield = new HashMap<String, Integer>();
	      instructionfield = new HashMap<Integer, String>();
	      
	      stream = new ErrorOutputStream();
	      instructionList = new InstructionList();
	    }
	    
	    /* Getter for Simulator Model */
	    public Simulator getSimulator() {
	      return this.sim40;
	    }
	    
	    /* Getter for ErrorOutputStream stream */
	    public ErrorOutputStream getErrorStream() {
	      return this.stream;
	    }
	    
	    /* Getters for fields */
	    public HashMap<Integer, String> getDatafield () { return datafield; }
	    public HashMap<String, Integer> getLabelfield () { return labelfield; }
	    public HashMap<Integer, String> getInstructionfield () { return instructionfield; }  

	 @Override
	  public void reportError(RecognitionException e) {
	        String msg = getErrorMessage(e, tokenNames);
	        this.stream.getErrors().add(new OutputError(e.line, msg));    
	             
	  }
	  
	  @Override
	  public void displayRecognitionError(String[] tokenNames, RecognitionException e)  {
	        String msg = getErrorMessage(e, tokenNames);
	        this.stream.getErrors().add(new OutputError(e.line, msg));     
	  }



	// $ANTLR start "program"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:127:1: program : ( '.start' )? mnemonicdata ( statement )* ( assignment )* ;
	public final void program() throws RecognitionException{
		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:3: ( ( '.start' )? mnemonicdata ( statement )* ( assignment )* )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:5: ( '.start' )? mnemonicdata ( statement )* ( assignment )*
			{
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:5: ( '.start' )?
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0==9) ) {
				alt1=1;
			}
			switch (alt1) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:5: '.start'
					{
					match(input,9,FOLLOW_9_in_program73); 
					}
					break;

			}

			pushFollow(FOLLOW_mnemonicdata_in_program76);
			mnemonicdata();
			state._fsp--;

			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:28: ( statement )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==8) ) {
					int LA2_1 = input.LA(2);
					if ( (LA2_1==NAME) ) {
						int LA2_4 = input.LA(3);
						if ( ((LA2_4 >= 10 && LA2_4 <= 14)||(LA2_4 >= 16 && LA2_4 <= 36)) ) {
							alt2=1;
						}

					}

				}
				else if ( ((LA2_0 >= 10 && LA2_0 <= 14)||(LA2_0 >= 16 && LA2_0 <= 36)) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:28: statement
					{
					pushFollow(FOLLOW_statement_in_program78);
					statement();
					state._fsp--;

					}
					break;

				default :
					break loop2;
				}
			}

			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:39: ( assignment )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==8||LA3_0==15) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:128:39: assignment
					{
					pushFollow(FOLLOW_assignment_in_program81);
					assignment();
					state._fsp--;

					}
					break;

				default :
					this.stream.getErrors().add(new OutputError(input.get(input.index()).getLine(),
							"Syntactic Error: insert instructions cannot be followed by any other instruction"));
				
					break loop3;
				}
			}

			}

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "program"



	// $ANTLR start "statement"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:139:1: statement : ( '.' labelfield )? mnemonicdata ;
	public final void statement() throws RecognitionException {
		ParserRuleReturnScope labelfield1 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:140:3: ( ( '.' labelfield )? mnemonicdata )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:140:5: ( '.' labelfield )? mnemonicdata
			{
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:140:5: ( '.' labelfield )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==8) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:140:6: '.' labelfield
					{
					match(input,8,FOLLOW_8_in_statement99); 
					pushFollow(FOLLOW_labelfield_in_statement101);
					labelfield1=labelfield();
					state._fsp--;

					 /* filling labelfield hashmap */
					    if(labelfield.containsKey((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null))) throw new RecognitionException();
					    else labelfield.put(((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null)),pointer);
					  
					}
					break;

			}

			pushFollow(FOLLOW_mnemonicdata_in_statement114);
			mnemonicdata();
			state._fsp--;

			}

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "statement"


	public static class labelfield_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "labelfield"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:151:1: labelfield : NAME ;
	public final CecilParser.labelfield_return labelfield() throws RecognitionException {
		CecilParser.labelfield_return retval = new CecilParser.labelfield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:152:3: ( NAME )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:152:6: NAME
			{
			match(input,NAME,FOLLOW_NAME_in_labelfield133); 
			}

			retval.stop = input.LT(-1);

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "labelfield"



	// $ANTLR start "assignment"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:163:1: assignment : ( '.' labelfield )? 'insert' value ;
	public final void assignment() throws RecognitionException {
		ParserRuleReturnScope labelfield2 =null;
		ParserRuleReturnScope value3 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:164:3: ( ( '.' labelfield )? 'insert' value )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:164:4: ( '.' labelfield )? 'insert' value
			{
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:164:4: ( '.' labelfield )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==8) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:164:5: '.' labelfield
					{
					match(input,8,FOLLOW_8_in_assignment148); 
					pushFollow(FOLLOW_labelfield_in_assignment150);
					labelfield2=labelfield();
					state._fsp--;

					 /* filling labelfield hashmap */
					    if(labelfield.containsKey((labelfield2!=null?input.toString(labelfield2.start,labelfield2.stop):null))) throw new RecognitionException();
					    else labelfield.put(((labelfield2!=null?input.toString(labelfield2.start,labelfield2.stop):null)),pointer);
					  
					}
					break;

			}

			match(input,15,FOLLOW_15_in_assignment163); 
			pushFollow(FOLLOW_value_in_assignment165);
			value3=value();
			state._fsp--;


			        instructionfield.put(pointer, "insert");
			        sim40.memory[pointer++] = Integer.parseInt((value3!=null?input.toString(value3.start,value3.stop):null));
			    
			  
			}

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "assignment"



	// $ANTLR start "mnemonicdata"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:184:1: mnemonicdata : ( ( binaryinstruction datafield ) | unaryinstruction );
	public final void mnemonicdata() throws RecognitionException {
		ParserRuleReturnScope binaryinstruction4 =null;
		ParserRuleReturnScope datafield5 =null;
		ParserRuleReturnScope unaryinstruction6 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:185:3: ( ( binaryinstruction datafield ) | unaryinstruction )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( ((LA6_0 >= 10 && LA6_0 <= 11)||LA6_0==13||(LA6_0 >= 16 && LA6_0 <= 22)||(LA6_0 >= 27 && LA6_0 <= 28)||(LA6_0 >= 31 && LA6_0 <= 33)||LA6_0==36) ) {
				alt6=1;
			}
			else if ( (LA6_0==12||LA6_0==14||(LA6_0 >= 23 && LA6_0 <= 26)||(LA6_0 >= 29 && LA6_0 <= 30)||(LA6_0 >= 34 && LA6_0 <= 35)) ) {
				alt6=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:185:5: ( binaryinstruction datafield )
					{
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:185:5: ( binaryinstruction datafield )
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:185:6: binaryinstruction datafield
					{
					pushFollow(FOLLOW_binaryinstruction_in_mnemonicdata187);
					binaryinstruction4=binaryinstruction();
					state._fsp--;

					pushFollow(FOLLOW_datafield_in_mnemonicdata189);
					datafield5=datafield();
					state._fsp--;


					      /* Refer to corresponding instruction and add the value to memory (from memory address)
					       * Filling in instruction hashmap with binary instructions
					       */  
					          if(instructionList.instructionToMnemonic((binaryinstruction4!=null?input.toString(binaryinstruction4.start,binaryinstruction4.stop):null)) == -1)
					            throw new RecognitionException();
					          
					          else {
					            instructionfield.put(pointer, (binaryinstruction4!=null?input.toString(binaryinstruction4.start,binaryinstruction4.stop):null));
					            sim40.memory[pointer++] = instructionList.instructionToMnemonic((binaryinstruction4!=null?input.toString(binaryinstruction4.start,binaryinstruction4.stop):null)); 
					            datafield.put(pointer++,(datafield5!=null?input.toString(datafield5.start,datafield5.stop):null));
					          }
					     
					}

					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:199:5: unaryinstruction
					{
					pushFollow(FOLLOW_unaryinstruction_in_mnemonicdata207);
					unaryinstruction6=unaryinstruction();
					state._fsp--;

					/* Filling  instruction hashmap with unary instructions */
					        instructionfield.put(pointer, (unaryinstruction6!=null?input.toString(unaryinstruction6.start,unaryinstruction6.stop):null));
					        sim40.memory[pointer++] = instructionList.instructionToMnemonic((unaryinstruction6!=null?input.toString(unaryinstruction6.start,unaryinstruction6.stop):null));
					      
					}
					break;

			}
		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "mnemonicdata"


	public static class unaryinstruction_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "unaryinstruction"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:209:1: unaryinstruction : ( 'stop' | 'print' | 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc' ) ;
	public final CecilParser.unaryinstruction_return unaryinstruction() throws RecognitionException {
		CecilParser.unaryinstruction_return retval = new CecilParser.unaryinstruction_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:210:3: ( ( 'stop' | 'print' | 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc' ) )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:
			{
			if ( input.LA(1)==12||input.LA(1)==14||(input.LA(1) >= 23 && input.LA(1) <= 26)||(input.LA(1) >= 29 && input.LA(1) <= 30)||(input.LA(1) >= 34 && input.LA(1) <= 35) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "unaryinstruction"


	public static class binaryinstruction_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "binaryinstruction"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:216:1: binaryinstruction : ( 'add' | 'sub' | 'and' | 'xor' | 'or' | 'comp' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jump' | 'load' | 'xload' | 'yload' | 'xcomp' | 'ycomp' ) ;
	public final CecilParser.binaryinstruction_return binaryinstruction() throws RecognitionException {
		CecilParser.binaryinstruction_return retval = new CecilParser.binaryinstruction_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:217:3: ( ( 'add' | 'sub' | 'and' | 'xor' | 'or' | 'comp' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jump' | 'load' | 'xload' | 'yload' | 'xcomp' | 'ycomp' ) )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:
			{
			if ( (input.LA(1) >= 10 && input.LA(1) <= 11)||input.LA(1)==13||(input.LA(1) >= 16 && input.LA(1) <= 22)||(input.LA(1) >= 27 && input.LA(1) <= 28)||(input.LA(1) >= 31 && input.LA(1) <= 33)||input.LA(1)==36 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			}

			retval.stop = input.LT(-1);

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "binaryinstruction"


	public static class datafield_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "datafield"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:224:1: datafield : NAME ;
	public final CecilParser.datafield_return datafield() throws RecognitionException {
		CecilParser.datafield_return retval = new CecilParser.datafield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:225:3: ( NAME )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:225:5: NAME
			{
			match(input,NAME,FOLLOW_NAME_in_datafield374); 
			}

			retval.stop = input.LT(-1);

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "datafield"


	public static class value_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "value"
	// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:231:1: value : ( DIGIT )+ ;
	public final CecilParser.value_return value() throws RecognitionException {
		CecilParser.value_return retval = new CecilParser.value_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:232:3: ( ( DIGIT )+ )
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:232:4: ( DIGIT )+
			{
			// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:232:4: ( DIGIT )+
			int cnt7=0;
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==DIGIT) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:232:4: DIGIT
					{
					match(input,DIGIT,FOLLOW_DIGIT_in_value389); 
					}
					break;

				default :
					if ( cnt7 >= 1 ) break loop7;
					EarlyExitException eee = new EarlyExitException(7, input);
					throw eee;
				}
				cnt7++;
			}

			}

			retval.stop = input.LT(-1);

		}

		  /**
		  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
		  */
		     catch (RecognitionException e) {  
		             String msg = getErrorMessage(e, tokenNames);
		             this.stream.getErrors().add(new OutputError(e.line, msg));    
		           
		             System.out.println(e.line + " : " + msg);  
		     } 

		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "value"

	// Delegated rules



	public static final BitSet FOLLOW_9_in_program73 = new BitSet(new long[]{0x0000001FFFFF7C00L});
	public static final BitSet FOLLOW_mnemonicdata_in_program76 = new BitSet(new long[]{0x0000001FFFFFFD02L});
	public static final BitSet FOLLOW_statement_in_program78 = new BitSet(new long[]{0x0000001FFFFFFD02L});
	public static final BitSet FOLLOW_assignment_in_program81 = new BitSet(new long[]{0x0000000000008102L});
	public static final BitSet FOLLOW_8_in_statement99 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_labelfield_in_statement101 = new BitSet(new long[]{0x0000001FFFFF7C00L});
	public static final BitSet FOLLOW_mnemonicdata_in_statement114 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_labelfield133 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_8_in_assignment148 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_labelfield_in_assignment150 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_15_in_assignment163 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_value_in_assignment165 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryinstruction_in_mnemonicdata187 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_datafield_in_mnemonicdata189 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryinstruction_in_mnemonicdata207 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_datafield374 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIGIT_in_value389 = new BitSet(new long[]{0x0000000000000022L});
}
