// $ANTLR 3.5.1 C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g 2013-11-02 14:24:07
 
  package org.raspberrypi.cecil.model;
  import java.util.HashMap;
  import java.util.ArrayList;


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
		"'.'", "'add'", "'and'", "'cclear'", "'comp'", "'cset'", "'insert'", "'jicarry'", 
		"'jineg'", "'jipos'", "'jizero'", "'jmptosr'", "'jump'", "'load'", "'loadmx'", 
		"'lshift'", "'or'", "'print'", "'printb'", "'printch'", "'printd'", "'pull'", 
		"'push'", "'return'", "'rshift'", "'start'", "'stop'", "'store'", "'sub'", 
		"'xcomp'", "'xdec'", "'xinc'", "'xload'", "'xor'", "'xpull'", "'xpush'", 
		"'xstore'", "'ycomp'", "'ydec'", "'yinc'", "'yload'", "'ypull'", "'ypush'", 
		"'ystore'"
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
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
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
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g"; }

	 
	    /* Memory Model */
	    private CecilMemoryModel memorymodel = new CecilMemoryModel();
	    /* Local variables */
	    private static int pointer = 0;
	    private HashMap<String, Integer> datafield = new HashMap<String, Integer>();
	    private HashMap<String, Integer> labelfield = new HashMap<String, Integer>();
	    private HashMap<Integer, String> instructionfield = new HashMap<Integer, String>();
	    
	    private static int maxCommand = 53;
	    private static HashMap<String, Integer> CommandList = new HashMap<String, Integer>(maxCommand);
	    
	    
	    private String parserError = "";
	    
	    
	    /* Getter for Memory Model */
	    public CecilMemoryModel getMemoryModel() {
	      return memorymodel;
	    }
	    
	    public HashMap<String, Integer> getDatafield () { return datafield; }
	    public HashMap<String, Integer> getLabelfield () { return labelfield; }
	    public HashMap<Integer, String> getInstructionfield () { return instructionfield; }
	  
	    /* Initialse the CommandList */
	    public static void initialseCommandList() {
	        CommandList.put("stop", 0);
	        CommandList.put("print", 1);
	        CommandList.put("printch", 2);
	        CommandList.put("printb", 3);
	        //CommandList.put("printd", 4);
	        CommandList.put("cclear", 4);
	        CommandList.put("cset", 5);
	        CommandList.put("lshift", 6);
	        CommandList.put("rshift", 7);
	        CommandList.put("pull", 8);
	        CommandList.put("xinc", 9);
	        CommandList.put("xdec", 10);
	        CommandList.put("xpush", 11);
	        CommandList.put("xpull", 12);
	        CommandList.put("push", 13);
	        CommandList.put("ypush", 14);
	        CommandList.put("ypull", 15);
	        CommandList.put("yinc", 16);
	        CommandList.put("ydec", 17);
	        
	        /* Unary ends, binary starts from here onwards; data field is required */
	        
	        CommandList.put("load", 18);
	        CommandList.put("store", 19);
	        CommandList.put("add", 20);
	        CommandList.put("sub", 21);
	        CommandList.put("and", 22);
	        CommandList.put("or", 23);
	        CommandList.put("xor", 24);
	        CommandList.put("jump", 25);
	        CommandList.put("comp", 26);
	        CommandList.put("jineg", 27);
	        CommandList.put("jipos", 28);
	        CommandList.put("jizero", 29);
	        CommandList.put("jmptosr", 30);
	        CommandList.put("jicarry", 31);
	        CommandList.put("xload", 32);
	        CommandList.put("xstore", 33);
	        CommandList.put("loadmx", 34);
	        CommandList.put("xcomp", 35);
	        CommandList.put("yload", 36);
	        CommandList.put("ystore", 37);    
	        CommandList.put("insert", 38); //This one is quirky - it's not an instruction!
	        CommandList.put("ycomp", 39);
	       }
	       
	    private List<String> errors = new ArrayList<String>();
	    
	    public void displayRecognitionError(String[] tokenNames,
	                                        RecognitionException e) {
	        String hdr = getErrorHeader(e);
	        String msg = getErrorMessage(e, tokenNames);
	        errors.add(hdr + " " + msg);
	    }
	    
	    public List<String> getErrors() {
	        return errors;
	    }



	// $ANTLR start "program"
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:126:1: program : ( instruction )* ;
	public final void program() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:127:3: ( ( instruction )* )
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:127:5: ( instruction )*
			{
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:127:5: ( instruction )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( ((LA1_0 >= 8 && LA1_0 <= 32)||(LA1_0 >= 34 && LA1_0 <= 51)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:127:5: instruction
					{
					pushFollow(FOLLOW_instruction_in_program62);
					instruction();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

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
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:130:1: instruction : ( '.' labelfield )? instructiondata ;
	public final void instruction() throws RecognitionException {
		ParserRuleReturnScope labelfield1 =null;

		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:131:3: ( ( '.' labelfield )? instructiondata )
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:131:5: ( '.' labelfield )? instructiondata
			{
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:131:5: ( '.' labelfield )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==8) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:131:6: '.' labelfield
					{
					match(input,8,FOLLOW_8_in_instruction78); 
					pushFollow(FOLLOW_labelfield_in_instruction80);
					labelfield1=labelfield();
					state._fsp--;

					 /* if label already does not exist or isn't start then add to the hash */
					    if(labelfield.containsKey((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null))) throw new RecognitionException();
					    else labelfield.put(((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null)),pointer);
					  
					}
					break;

			}

			pushFollow(FOLLOW_instructiondata_in_instruction93);
			instructiondata();
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
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:141:1: labelfield : ( 'start' | NAME );
	public final CecilParser.labelfield_return labelfield() throws RecognitionException {
		CecilParser.labelfield_return retval = new CecilParser.labelfield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:142:3: ( 'start' | NAME )
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:
			{
			if ( input.LA(1)==NAME||input.LA(1)==33 ) {
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
	// $ANTLR end "labelfield"



	// $ANTLR start "instructiondata"
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:145:1: instructiondata : ( ( binaryinstruction datafield ) | unaryinstruction );
	public final void instructiondata() throws RecognitionException {
		ParserRuleReturnScope binaryinstruction2 =null;
		ParserRuleReturnScope datafield3 =null;
		ParserRuleReturnScope unaryinstruction4 =null;

		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:146:3: ( ( binaryinstruction datafield ) | unaryinstruction )
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( ((LA3_0 >= 9 && LA3_0 <= 10)||LA3_0==12||(LA3_0 >= 14 && LA3_0 <= 22)||LA3_0==24||LA3_0==31||(LA3_0 >= 35 && LA3_0 <= 37)||(LA3_0 >= 40 && LA3_0 <= 41)||(LA3_0 >= 44 && LA3_0 <= 45)||LA3_0==48||LA3_0==51) ) {
				alt3=1;
			}
			else if ( (LA3_0==11||LA3_0==13||LA3_0==23||(LA3_0 >= 25 && LA3_0 <= 30)||LA3_0==32||LA3_0==34||(LA3_0 >= 38 && LA3_0 <= 39)||(LA3_0 >= 42 && LA3_0 <= 43)||(LA3_0 >= 46 && LA3_0 <= 47)||(LA3_0 >= 49 && LA3_0 <= 50)) ) {
				alt3=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:146:5: ( binaryinstruction datafield )
					{
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:146:5: ( binaryinstruction datafield )
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:146:6: binaryinstruction datafield
					{
					pushFollow(FOLLOW_binaryinstruction_in_instructiondata137);
					binaryinstruction2=binaryinstruction();
					state._fsp--;

					pushFollow(FOLLOW_datafield_in_instructiondata139);
					datafield3=datafield();
					state._fsp--;


					      /* if instruction is insert and data is integer then add value to memory */
					        if(((binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null)).equals("insert")) {
					          if(((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null)).matches("^[0-9]+$")) {
					            instructionfield.put(pointer, "insert");
					            memorymodel.memory[pointer++] = Integer.parseInt((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null));
					          }
					          else throw new RecognitionException();
					        }
					      /* else reference instruction */  
					        else {
					          instructionfield.put(pointer, (binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null));
					          memorymodel.memory[pointer++] = CommandList.get((binaryinstruction2!=null?input.toString(binaryinstruction2.start,binaryinstruction2.stop):null)); 
					          if(((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null)).matches("^[0-9]+$"))
					            memorymodel.memory[pointer++] = Integer.parseInt((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null));
					          else 
					            datafield.put((datafield3!=null?input.toString(datafield3.start,datafield3.stop):null),pointer++);
					         } 
					     
					}

					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:166:5: unaryinstruction
					{
					pushFollow(FOLLOW_unaryinstruction_in_instructiondata157);
					unaryinstruction4=unaryinstruction();
					state._fsp--;


					        instructionfield.put(pointer, (unaryinstruction4!=null?input.toString(unaryinstruction4.start,unaryinstruction4.stop):null));
					        memorymodel.memory[pointer++] = CommandList.get((unaryinstruction4!=null?input.toString(unaryinstruction4.start,unaryinstruction4.stop):null));
					      
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
	// $ANTLR end "instructiondata"


	public static class unaryinstruction_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "unaryinstruction"
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:173:1: unaryinstruction : ( 'stop' | 'print' | 'printch' | 'printb' | 'printd' | 'cclear' | 'cset' | 'lshift' | 'rshift' | 'pull' | 'xdec' | 'xinc' | 'xpull' | 'xpush' | 'ydec' | 'yinc' | 'ypull' | 'ypush' | 'push' ) ;
	public final CecilParser.unaryinstruction_return unaryinstruction() throws RecognitionException {
		CecilParser.unaryinstruction_return retval = new CecilParser.unaryinstruction_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:174:3: ( ( 'stop' | 'print' | 'printch' | 'printb' | 'printd' | 'cclear' | 'cset' | 'lshift' | 'rshift' | 'pull' | 'xdec' | 'xinc' | 'xpull' | 'xpush' | 'ydec' | 'yinc' | 'ypull' | 'ypush' | 'push' ) )
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:
			{
			if ( input.LA(1)==11||input.LA(1)==13||input.LA(1)==23||(input.LA(1) >= 25 && input.LA(1) <= 30)||input.LA(1)==32||input.LA(1)==34||(input.LA(1) >= 38 && input.LA(1) <= 39)||(input.LA(1) >= 42 && input.LA(1) <= 43)||(input.LA(1) >= 46 && input.LA(1) <= 47)||(input.LA(1) >= 49 && input.LA(1) <= 50) ) {
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
	// $ANTLR end "unaryinstruction"


	public static class binaryinstruction_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "binaryinstruction"
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:178:1: binaryinstruction : ( 'add' | 'sub' | 'and' | 'comp' | 'xor' | 'or' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jmptosr' | 'jump' | 'load' | 'xload' | 'yload' | 'xstore' | 'ystore' | 'loadmx' | 'store' | 'xcomp' | 'insert' | 'return' | 'ycomp' ) ;
	public final CecilParser.binaryinstruction_return binaryinstruction() throws RecognitionException {
		CecilParser.binaryinstruction_return retval = new CecilParser.binaryinstruction_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:179:3: ( ( 'add' | 'sub' | 'and' | 'comp' | 'xor' | 'or' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jmptosr' | 'jump' | 'load' | 'xload' | 'yload' | 'xstore' | 'ystore' | 'loadmx' | 'store' | 'xcomp' | 'insert' | 'return' | 'ycomp' ) )
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:
			{
			if ( (input.LA(1) >= 9 && input.LA(1) <= 10)||input.LA(1)==12||(input.LA(1) >= 14 && input.LA(1) <= 22)||input.LA(1)==24||input.LA(1)==31||(input.LA(1) >= 35 && input.LA(1) <= 37)||(input.LA(1) >= 40 && input.LA(1) <= 41)||(input.LA(1) >= 44 && input.LA(1) <= 45)||input.LA(1)==48||input.LA(1)==51 ) {
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


	public static class datafield_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "datafield"
	// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:184:1: datafield : ( NAME | ( DIGIT )+ );
	public final CecilParser.datafield_return datafield() throws RecognitionException {
		CecilParser.datafield_return retval = new CecilParser.datafield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:185:3: ( NAME | ( DIGIT )+ )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==NAME) ) {
				alt5=1;
			}
			else if ( (LA5_0==DIGIT) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:185:5: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_datafield309); 
					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:186:5: ( DIGIT )+
					{
					// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:186:5: ( DIGIT )+
					int cnt4=0;
					loop4:
					while (true) {
						int alt4=2;
						int LA4_0 = input.LA(1);
						if ( (LA4_0==DIGIT) ) {
							alt4=1;
						}

						switch (alt4) {
						case 1 :
							// C:\\Users\\Shreeprabha\\Dropbox\\GDP\\CecilRPi\\src\\org\\raspberrypi\\cecil\\model\\Cecil.g:186:5: DIGIT
							{
							match(input,DIGIT,FOLLOW_DIGIT_in_datafield315); 
							}
							break;

						default :
							if ( cnt4 >= 1 ) break loop4;
							EarlyExitException eee = new EarlyExitException(4, input);
							throw eee;
						}
						cnt4++;
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



	public static final BitSet FOLLOW_instruction_in_program62 = new BitSet(new long[]{0x000FFFFDFFFFFF02L});
	public static final BitSet FOLLOW_8_in_instruction78 = new BitSet(new long[]{0x0000000200000040L});
	public static final BitSet FOLLOW_labelfield_in_instruction80 = new BitSet(new long[]{0x000FFFFDFFFFFE00L});
	public static final BitSet FOLLOW_instructiondata_in_instruction93 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryinstruction_in_instructiondata137 = new BitSet(new long[]{0x0000000000000060L});
	public static final BitSet FOLLOW_datafield_in_instructiondata139 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryinstruction_in_instructiondata157 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_datafield309 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIGIT_in_datafield315 = new BitSet(new long[]{0x0000000000000022L});
}
