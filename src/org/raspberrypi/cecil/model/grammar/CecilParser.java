// $ANTLR 3.5.1 C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g 2013-11-12 19:53:35
 
  package org.raspberrypi.cecil.model.grammar;
  import java.util.HashMap;
  import java.util.ArrayList;
  import org.raspberrypi.cecil.model.*;
  import org.raspberrypi.cecil.pojo.*;
  import org.raspberrypi.cecil.model.outputstream.OutputError;
  import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * CECIL assembly language grammar definition
 * This grammar constitutes all the lexer rules employed by CECIL.
 * It is a version of the CECIL Language originally developed by David Argles.
 * it contains 39 simple instructions and user input datafields and labelfields.
 * 
 * MIT Open Source License
 * @authors Shreeprabha Aggarwal (sa10g10), Carolina Ferreira (cf4g09)
 * Southampton University, United Kingdom
 * @version 1.1
 * 
 * @date 01/11/2013
 */
@SuppressWarnings("all")
public class CecilParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "DIGIT", "NAME", "WS", 
		"'.'", "'.start'", "'add'", "'and'", "'cclear'", "'comp'", "'cset'", "'insert'", 
		"'jicarry'", "'jineg'", "'jipos'", "'jizero'", "'jump'", "'load'", "'loadmx'", 
		"'or'", "'print'", "'printb'", "'printch'", "'stop'", "'store'", "'sub'", 
		"'xcomp'", "'xdec'", "'xinc'", "'xload'", "'xor'", "'xstore'", "'ycomp'", 
		"'ydec'", "'yinc'", "'yload'", "'ystore'"
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
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
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
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g"; }

	 
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
	    
	    /*
	    * Method to be invoked before call to .program method
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
	   
	    /**
	    * Implicitly invoked by the parser. The error is appended in the output console. 
	    */
	    @Override
	    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
	          String hdr = getErrorHeader(e);
	          String msg = getErrorMessage(e, tokenNames);
	          this.stream.getErrors().add(new OutputError(e.line, msg));
	    }



	// $ANTLR start "program"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:100:1: program : '.start' mnemonicdata ( instruction )* stop ;
	public final void program() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:101:3: ( '.start' mnemonicdata ( instruction )* stop )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:101:5: '.start' mnemonicdata ( instruction )* stop
			{
			match(input,9,FOLLOW_9_in_program62); 
			pushFollow(FOLLOW_mnemonicdata_in_program64);
			mnemonicdata();
			state._fsp--;

			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:101:27: ( instruction )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==27) ) {
					int LA1_1 = input.LA(2);
					if ( (LA1_1==8||(LA1_1 >= 10 && LA1_1 <= 40)) ) {
						alt1=1;
					}

				}
				else if ( (LA1_0==8||(LA1_0 >= 10 && LA1_0 <= 26)||(LA1_0 >= 28 && LA1_0 <= 40)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:101:27: instruction
					{
					pushFollow(FOLLOW_instruction_in_program66);
					instruction();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			pushFollow(FOLLOW_stop_in_program69);
			stop();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "program"



	// $ANTLR start "instruction"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:104:1: instruction : ( '.' labelfield )? mnemonicdata ;
	public final void instruction() throws RecognitionException {
		ParserRuleReturnScope labelfield1 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:105:3: ( ( '.' labelfield )? mnemonicdata )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:105:5: ( '.' labelfield )? mnemonicdata
			{
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:105:5: ( '.' labelfield )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==8) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:105:6: '.' labelfield
					{
					match(input,8,FOLLOW_8_in_instruction84); 
					pushFollow(FOLLOW_labelfield_in_instruction86);
					labelfield1=labelfield();
					state._fsp--;

					 /* if label already does not exist or isn't start then add to the hash */
					    if(labelfield.containsKey((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null))) throw new RecognitionException();
					    else labelfield.put(((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null)),pointer);
					  
					}
					break;

			}

			pushFollow(FOLLOW_mnemonicdata_in_instruction99);
			mnemonicdata();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "instruction"


	public static class labelfield_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "labelfield"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:115:1: labelfield : NAME ;
	public final CecilParser.labelfield_return labelfield() throws RecognitionException {
		CecilParser.labelfield_return retval = new CecilParser.labelfield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:116:3: ( NAME )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:116:6: NAME
			{
			match(input,NAME,FOLLOW_NAME_in_labelfield124); 
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "labelfield"



	// $ANTLR start "mnemonicdata"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:119:1: mnemonicdata : ( ( binaryinstruction datafield ) | unaryinstruction );
	public final void mnemonicdata() throws RecognitionException {
		ParserRuleReturnScope binaryinstruction2 =null;
		ParserRuleReturnScope datafield3 =null;
		ParserRuleReturnScope unaryinstruction4 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:120:3: ( ( binaryinstruction datafield ) | unaryinstruction )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( ((LA3_0 >= 10 && LA3_0 <= 11)||LA3_0==13||(LA3_0 >= 15 && LA3_0 <= 23)||(LA3_0 >= 28 && LA3_0 <= 30)||(LA3_0 >= 33 && LA3_0 <= 36)||(LA3_0 >= 39 && LA3_0 <= 40)) ) {
				alt3=1;
			}
			else if ( (LA3_0==12||LA3_0==14||(LA3_0 >= 24 && LA3_0 <= 27)||(LA3_0 >= 31 && LA3_0 <= 32)||(LA3_0 >= 37 && LA3_0 <= 38)) ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:120:5: ( binaryinstruction datafield )
					{
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:120:5: ( binaryinstruction datafield )
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:120:6: binaryinstruction datafield
					{
					pushFollow(FOLLOW_binaryinstruction_in_mnemonicdata139);
					binaryinstruction2=binaryinstruction();
					state._fsp--;

					pushFollow(FOLLOW_datafield_in_mnemonicdata141);
					datafield3=datafield();
					state._fsp--;


					      /* if instruction is insert and data is integer then add value to memory */
					        
					         if(((binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null)).equals("insert")) {
					          if(((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null)).matches("^[0-9]+$")) {
					            instructionfield.put(pointer, "insert");
					            sim40.memory[pointer++] = Integer.parseInt((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null));
					          }
					          else throw new RecognitionException();
					        }
					      /* else reference instruction */  
					        else {
					          instructionfield.put(pointer, (binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null));
					          
					          if(instructionList.instructionToMnemonic((binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null)) == -1)
					            throw new RecognitionException();
					          
					          sim40.memory[pointer++] = instructionList.instructionToMnemonic((binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null)); 
					          
					          if(((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null)).matches("^[0-9]+$"))
					            sim40.memory[pointer++] = Integer.parseInt((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null));
					          
					          else 
					            datafield.put(pointer++,(datafield3!=null?input.toString(datafield3.start,datafield3.stop):null));
					         } 
					     
					}

					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:147:5: unaryinstruction
					{
					pushFollow(FOLLOW_unaryinstruction_in_mnemonicdata159);
					unaryinstruction4=unaryinstruction();
					state._fsp--;


					        instructionfield.put(pointer, (unaryinstruction4!=null?input.toString(unaryinstruction4.start,unaryinstruction4.stop):null));
					        sim40.memory[pointer++] = instructionList.instructionToMnemonic((unaryinstruction4!=null?input.toString(unaryinstruction4.start,unaryinstruction4.stop):null));
					      
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "mnemonicdata"


	public static class unaryinstruction_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "unaryinstruction"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:154:1: unaryinstruction : ( stop | 'print' | 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc' ) ;
	public final CecilParser.unaryinstruction_return unaryinstruction() throws RecognitionException {
		CecilParser.unaryinstruction_return retval = new CecilParser.unaryinstruction_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:3: ( ( stop | 'print' | 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc' ) )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:5: ( stop | 'print' | 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc' )
			{
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:5: ( stop | 'print' | 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc' )
			int alt4=10;
			switch ( input.LA(1) ) {
			case 27:
				{
				alt4=1;
				}
				break;
			case 24:
				{
				alt4=2;
				}
				break;
			case 26:
				{
				alt4=3;
				}
				break;
			case 25:
				{
				alt4=4;
				}
				break;
			case 12:
				{
				alt4=5;
				}
				break;
			case 14:
				{
				alt4=6;
				}
				break;
			case 31:
				{
				alt4=7;
				}
				break;
			case 32:
				{
				alt4=8;
				}
				break;
			case 37:
				{
				alt4=9;
				}
				break;
			case 38:
				{
				alt4=10;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:6: stop
					{
					pushFollow(FOLLOW_stop_in_unaryinstruction182);
					stop();
					state._fsp--;

					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:11: 'print'
					{
					match(input,24,FOLLOW_24_in_unaryinstruction184); 
					}
					break;
				case 3 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:19: 'printch'
					{
					match(input,26,FOLLOW_26_in_unaryinstruction186); 
					}
					break;
				case 4 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:29: 'printb'
					{
					match(input,25,FOLLOW_25_in_unaryinstruction188); 
					}
					break;
				case 5 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:38: 'cclear'
					{
					match(input,12,FOLLOW_12_in_unaryinstruction190); 
					}
					break;
				case 6 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:47: 'cset'
					{
					match(input,14,FOLLOW_14_in_unaryinstruction192); 
					}
					break;
				case 7 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:54: 'xdec'
					{
					match(input,31,FOLLOW_31_in_unaryinstruction194); 
					}
					break;
				case 8 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:61: 'xinc'
					{
					match(input,32,FOLLOW_32_in_unaryinstruction196); 
					}
					break;
				case 9 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:68: 'ydec'
					{
					match(input,37,FOLLOW_37_in_unaryinstruction198); 
					}
					break;
				case 10 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:155:75: 'yinc'
					{
					match(input,38,FOLLOW_38_in_unaryinstruction200); 
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
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
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:158:1: binaryinstruction : ( 'add' | 'sub' | 'and' | 'comp' | 'xor' | 'or' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jump' | 'load' | 'xload' | 'yload' | 'xstore' | 'ystore' | 'loadmx' | 'store' | 'xcomp' | 'insert' | 'ycomp' ) ;
	public final CecilParser.binaryinstruction_return binaryinstruction() throws RecognitionException {
		CecilParser.binaryinstruction_return retval = new CecilParser.binaryinstruction_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:159:3: ( ( 'add' | 'sub' | 'and' | 'comp' | 'xor' | 'or' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jump' | 'load' | 'xload' | 'yload' | 'xstore' | 'ystore' | 'loadmx' | 'store' | 'xcomp' | 'insert' | 'ycomp' ) )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:
			{
			if ( (input.LA(1) >= 10 && input.LA(1) <= 11)||input.LA(1)==13||(input.LA(1) >= 15 && input.LA(1) <= 23)||(input.LA(1) >= 28 && input.LA(1) <= 30)||(input.LA(1) >= 33 && input.LA(1) <= 36)||(input.LA(1) >= 39 && input.LA(1) <= 40) ) {
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "binaryinstruction"



	// $ANTLR start "stop"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:163:1: stop : 'stop' ;
	public final void stop() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:164:3: ( 'stop' )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:164:4: 'stop'
			{
			match(input,27,FOLLOW_27_in_stop275); 

			    instructionfield.put(pointer, "stop");
			    sim40.memory[pointer++] = 0;
			  
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "stop"


	public static class datafield_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "datafield"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:172:1: datafield : ( NAME | ( DIGIT )+ );
	public final CecilParser.datafield_return datafield() throws RecognitionException {
		CecilParser.datafield_return retval = new CecilParser.datafield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:173:3: ( NAME | ( DIGIT )+ )
			int alt6=2;
			int LA6_0 = input.LA(1);
			if ( (LA6_0==NAME) ) {
				alt6=1;
			}
			else if ( (LA6_0==DIGIT) ) {
				alt6=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}

			switch (alt6) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:173:5: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_datafield298); 
					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:174:5: ( DIGIT )+
					{
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:174:5: ( DIGIT )+
					int cnt5=0;
					loop5:
					while (true) {
						int alt5=2;
						int LA5_0 = input.LA(1);
						if ( (LA5_0==DIGIT) ) {
							alt5=1;
						}

						switch (alt5) {
						case 1 :
							// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\grammar\\Cecil.g:174:5: DIGIT
							{
							match(input,DIGIT,FOLLOW_DIGIT_in_datafield304); 
							}
							break;

						default :
							if ( cnt5 >= 1 ) break loop5;
							EarlyExitException eee = new EarlyExitException(5, input);
							throw eee;
						}
						cnt5++;
					}

					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "datafield"

	// Delegated rules



	public static final BitSet FOLLOW_9_in_program62 = new BitSet(new long[]{0x000001FFFFFFFC00L});
	public static final BitSet FOLLOW_mnemonicdata_in_program64 = new BitSet(new long[]{0x000001FFFFFFFD00L});
	public static final BitSet FOLLOW_instruction_in_program66 = new BitSet(new long[]{0x000001FFFFFFFD00L});
	public static final BitSet FOLLOW_stop_in_program69 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_8_in_instruction84 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_labelfield_in_instruction86 = new BitSet(new long[]{0x000001FFFFFFFC00L});
	public static final BitSet FOLLOW_mnemonicdata_in_instruction99 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_labelfield124 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryinstruction_in_mnemonicdata139 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_datafield_in_mnemonicdata141 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryinstruction_in_mnemonicdata159 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_stop_in_unaryinstruction182 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_24_in_unaryinstruction184 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_unaryinstruction186 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_25_in_unaryinstruction188 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_12_in_unaryinstruction190 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_14_in_unaryinstruction192 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_31_in_unaryinstruction194 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_unaryinstruction196 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_37_in_unaryinstruction198 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_38_in_unaryinstruction200 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_stop275 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_datafield298 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIGIT_in_datafield304 = new BitSet(new long[]{0x0000000000000022L});
}
