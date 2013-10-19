// $ANTLR 3.5.1 C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g 2013-10-19 16:20:41
 
  package org.raspberrypi.cecil.grammar;
  import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * CECIL assembly language grammar definition
 * 
 * Authors Shreeprabha Aggarwal, Carolina Ferreira 
 * Version 1.0
 * Date 19/10/2013
 */
@SuppressWarnings("all")
public class CecilParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "DAY", "DIGIT", "MONTH", 
		"NAME", "WS", "'.'", "'/'", "'add'", "'and'", "'author'", "'cclear'", 
		"'comp'", "'cset'", "'date'", "'eor'", "'insert'", "'jicarry'", "'jineg'", 
		"'jipos'", "'jizero'", "'jmptosr'", "'jump'", "'load'", "'loadmx'", "'lshift'", 
		"'or'", "'print'", "'printb'", "'printch'", "'printd'", "'program'", "'pull'", 
		"'push'", "'return'", "'rshift'", "'start'", "'stop'", "'store'", "'sub'", 
		"'xcomp'", "'xdec'", "'xinc'", "'xload'", "'xpull'", "'xpush'", "'xstore'", 
		"'ydec'", "'yinc'", "'yload'", "'ypull'", "'ypush'", "'ystore'"
	};
	public static final int EOF=-1;
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
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int T__56=56;
	public static final int COMMENT=4;
	public static final int DAY=5;
	public static final int DIGIT=6;
	public static final int MONTH=7;
	public static final int NAME=8;
	public static final int WS=9;

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
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g"; }

	 
	    static int[] memory = new int[1029];
	    static int pointer = 0;
	    
	    HashMap<String, Integer> datafield = new HashMap<String, Integer>();
	    HashMap<String, Integer> labelfield = new HashMap<String, Integer>();
	    
	    static int maxCommand = 53;
	    static HashMap<String, Integer> CommandList = new HashMap<String, Integer>(maxCommand);
	     static {
	        CommandList.put("stop", 0);
	        CommandList.put("load", 1);
	        CommandList.put("store", 2);
	        CommandList.put("add", 3);
	        CommandList.put("sub", 4);
	        CommandList.put("and", 5);
	        CommandList.put("or", 6);
	        CommandList.put("eor", 7);
	        CommandList.put("jump", 8);
	        CommandList.put("comp", 9);
	        CommandList.put("jineg", 10);
	        CommandList.put("jipos", 11);
	        CommandList.put("jizero", 12);
	        CommandList.put("jmptosr", 13);
	        CommandList.put("jicarry", 14);
	        CommandList.put("xload", 15);
	        CommandList.put("xstore", 16);
	        CommandList.put("loadmx", 17);
	        CommandList.put("xcomp", 18);
	        CommandList.put("yload", 19);
	        CommandList.put("ystore", 20);
	        CommandList.put("pause", 21);
	        CommandList.put("printd", 22);
	    
	        /* Binary ends, unary starts from here onwards; no data field is required */
	    
	        CommandList.put("return", 23);
	        CommandList.put("push", 24);
	        CommandList.put("pull", 25);
	        CommandList.put("xpush", 26);
	        CommandList.put("xpull", 27);
	        CommandList.put("xinc", 28);
	        CommandList.put("xdec", 29);
	        CommandList.put("lshift", 30);
	        CommandList.put("rshift", 31);
	        CommandList.put("cset", 32);
	        CommandList.put("cclear", 33);
	        CommandList.put("getkey", 34);
	        CommandList.put("wait", 35);
	        CommandList.put("retfint", 36);
	        CommandList.put("printb", 37);
	        CommandList.put("print", 38);
	        CommandList.put("printch", 39);
	        CommandList.put("ypush", 40);
	        CommandList.put("ypull", 41);
	        CommandList.put("yinc", 42);
	        CommandList.put("ydec", 43);
	        CommandList.put("swapax", 44);
	        CommandList.put("swapay", 45);
	        CommandList.put("swapxy", 46);
	        CommandList.put("swapas", 47);
	        CommandList.put("intenable", 48);
	        CommandList.put("intdisable", 49);
	        CommandList.put("nop", 50);
	        CommandList.put("insert", 51); //This one is quirky - it's not an instruction!
	        CommandList.put("halt", 52);
	       }



	// $ANTLR start "program"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:102:1: program : header ( instruction )* ;
	public final void program() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:103:3: ( header ( instruction )* )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:103:5: header ( instruction )*
			{
			pushFollow(FOLLOW_header_in_program60);
			header();
			state._fsp--;

			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:103:12: ( instruction )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==10||(LA1_0 >= 12 && LA1_0 <= 13)||(LA1_0 >= 15 && LA1_0 <= 17)||(LA1_0 >= 19 && LA1_0 <= 34)||(LA1_0 >= 36 && LA1_0 <= 39)||(LA1_0 >= 41 && LA1_0 <= 56)) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:103:12: instruction
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



	// $ANTLR start "header"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:1: header : 'program' ( NAME )+ 'author' ( NAME )+ 'date' dateformat ;
	public final void header() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:9: ( 'program' ( NAME )+ 'author' ( NAME )+ 'date' dateformat )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:11: 'program' ( NAME )+ 'author' ( NAME )+ 'date' dateformat
			{
			match(input,35,FOLLOW_35_in_header75); 
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:21: ( NAME )+
			int cnt2=0;
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==NAME) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:21: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_header77); 
					}
					break;

				default :
					if ( cnt2 >= 1 ) break loop2;
					EarlyExitException eee = new EarlyExitException(2, input);
					throw eee;
				}
				cnt2++;
			}

			match(input,14,FOLLOW_14_in_header80); 
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:36: ( NAME )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==NAME) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:106:36: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_header82); 
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			match(input,18,FOLLOW_18_in_header85); 
			pushFollow(FOLLOW_dateformat_in_header87);
			dateformat();
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
	// $ANTLR end "header"



	// $ANTLR start "dateformat"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:108:1: dateformat : DAY ( '.' | '/' ) MONTH ( '.' | '/' ) DIGIT DIGIT ;
	public final void dateformat() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:109:3: ( DAY ( '.' | '/' ) MONTH ( '.' | '/' ) DIGIT DIGIT )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:109:5: DAY ( '.' | '/' ) MONTH ( '.' | '/' ) DIGIT DIGIT
			{
			match(input,DAY,FOLLOW_DAY_in_dateformat98); 
			if ( (input.LA(1) >= 10 && input.LA(1) <= 11) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			match(input,MONTH,FOLLOW_MONTH_in_dateformat106); 
			if ( (input.LA(1) >= 10 && input.LA(1) <= 11) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			match(input,DIGIT,FOLLOW_DIGIT_in_dateformat114); 
			match(input,DIGIT,FOLLOW_DIGIT_in_dateformat116); 
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
	// $ANTLR end "dateformat"



	// $ANTLR start "instruction"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:120:1: instruction : ( '.' labelfield )? instructiondata ;
	public final void instruction() throws RecognitionException {
		ParserRuleReturnScope labelfield1 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:121:3: ( ( '.' labelfield )? instructiondata )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:121:5: ( '.' labelfield )? instructiondata
			{
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:121:5: ( '.' labelfield )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==10) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:121:6: '.' labelfield
					{
					match(input,10,FOLLOW_10_in_instruction211); 
					pushFollow(FOLLOW_labelfield_in_instruction212);
					labelfield1=labelfield();
					state._fsp--;

					}
					break;

			}

			pushFollow(FOLLOW_instructiondata_in_instruction216);
			instructiondata();
			state._fsp--;


			    if(!((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null)).equals("start") && !labelfield.containsKey((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null)))
			     labelfield.put(((labelfield1!=null?input.toString(labelfield1.start,labelfield1.stop):null)),pointer);
			  
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
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:128:1: labelfield : ( 'start' | NAME );
	public final CecilParser.labelfield_return labelfield() throws RecognitionException {
		CecilParser.labelfield_return retval = new CecilParser.labelfield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:129:3: ( 'start' | NAME )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:
			{
			if ( input.LA(1)==NAME||input.LA(1)==40 ) {
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
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:132:1: instructiondata : ( ( binaryinstruction datafield ) | unaryinstruction ) ;
	public final void instructiondata() throws RecognitionException {
		ParserRuleReturnScope datafield2 =null;

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:133:3: ( ( ( binaryinstruction datafield ) | unaryinstruction ) )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:133:5: ( ( binaryinstruction datafield ) | unaryinstruction )
			{
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:133:5: ( ( binaryinstruction datafield ) | unaryinstruction )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( ((LA5_0 >= 12 && LA5_0 <= 13)||LA5_0==16||(LA5_0 >= 19 && LA5_0 <= 28)||LA5_0==30||LA5_0==37||(LA5_0 >= 42 && LA5_0 <= 44)||LA5_0==47||LA5_0==50||LA5_0==53||LA5_0==56) ) {
				alt5=1;
			}
			else if ( (LA5_0==15||LA5_0==17||LA5_0==29||(LA5_0 >= 31 && LA5_0 <= 34)||LA5_0==36||(LA5_0 >= 38 && LA5_0 <= 39)||LA5_0==41||(LA5_0 >= 45 && LA5_0 <= 46)||(LA5_0 >= 48 && LA5_0 <= 49)||(LA5_0 >= 51 && LA5_0 <= 52)||(LA5_0 >= 54 && LA5_0 <= 55)) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:133:6: ( binaryinstruction datafield )
					{
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:133:6: ( binaryinstruction datafield )
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:133:7: binaryinstruction datafield
					{
					pushFollow(FOLLOW_binaryinstruction_in_instructiondata259);
					binaryinstruction();
					state._fsp--;

					pushFollow(FOLLOW_datafield_in_instructiondata261);
					datafield2=datafield();
					state._fsp--;

					}

					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:134:5: unaryinstruction
					{
					pushFollow(FOLLOW_unaryinstruction_in_instructiondata269);
					unaryinstruction();
					state._fsp--;

					}
					break;

			}


			    datafield.put((datafield2!=null?input.toString(datafield2.start,datafield2.stop):null),pointer++);
			  
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



	// $ANTLR start "unaryinstruction"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:140:1: unaryinstruction : ( 'stop' | 'print' | 'printch' | 'printb' | 'printd' | 'cclear' | 'cset' | 'lshift' | 'rshift' | 'pull' | 'return' | 'xdec' | 'xinc' | 'xpull' | 'xpush' | 'ydec' | 'yinc' | 'ypull' | 'ypush' ) ;
	public final void unaryinstruction() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:141:3: ( ( 'stop' | 'print' | 'printch' | 'printb' | 'printd' | 'cclear' | 'cset' | 'lshift' | 'rshift' | 'pull' | 'return' | 'xdec' | 'xinc' | 'xpull' | 'xpush' | 'ydec' | 'yinc' | 'ypull' | 'ypush' ) )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:
			{
			if ( input.LA(1)==15||input.LA(1)==17||input.LA(1)==29||(input.LA(1) >= 31 && input.LA(1) <= 34)||input.LA(1)==36||(input.LA(1) >= 38 && input.LA(1) <= 39)||input.LA(1)==41||(input.LA(1) >= 45 && input.LA(1) <= 46)||(input.LA(1) >= 48 && input.LA(1) <= 49)||(input.LA(1) >= 51 && input.LA(1) <= 52)||(input.LA(1) >= 54 && input.LA(1) <= 55) ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
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
	// $ANTLR end "unaryinstruction"



	// $ANTLR start "binaryinstruction"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:145:1: binaryinstruction : ( 'add' | 'sub' | 'and' | 'comp' | 'eor' | 'or' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jmptosr' | 'jump' | 'load' | 'xload' | 'yload' | 'xstore' | 'ystore' | 'loadmx' | 'push' | 'store' | 'xcomp' | 'insert' ) ;
	public final void binaryinstruction() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:146:3: ( ( 'add' | 'sub' | 'and' | 'comp' | 'eor' | 'or' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jmptosr' | 'jump' | 'load' | 'xload' | 'yload' | 'xstore' | 'ystore' | 'loadmx' | 'push' | 'store' | 'xcomp' | 'insert' ) )
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:
			{
			if ( (input.LA(1) >= 12 && input.LA(1) <= 13)||input.LA(1)==16||(input.LA(1) >= 19 && input.LA(1) <= 28)||input.LA(1)==30||input.LA(1)==37||(input.LA(1) >= 42 && input.LA(1) <= 44)||input.LA(1)==47||input.LA(1)==50||input.LA(1)==53||input.LA(1)==56 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
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
	// $ANTLR end "binaryinstruction"


	public static class datafield_return extends ParserRuleReturnScope {
	};


	// $ANTLR start "datafield"
	// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:151:1: datafield : ( NAME | ( DIGIT )+ );
	public final CecilParser.datafield_return datafield() throws RecognitionException {
		CecilParser.datafield_return retval = new CecilParser.datafield_return();
		retval.start = input.LT(1);

		try {
			// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:152:3: ( NAME | ( DIGIT )+ )
			int alt7=2;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==NAME) ) {
				alt7=1;
			}
			else if ( (LA7_0==DIGIT) ) {
				alt7=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}

			switch (alt7) {
				case 1 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:152:5: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_datafield414); 
					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:153:5: ( DIGIT )+
					{
					// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:153:5: ( DIGIT )+
					int cnt6=0;
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( (LA6_0==DIGIT) ) {
							alt6=1;
						}

						switch (alt6) {
						case 1 :
							// C:\\Users\\Shreeprabha\\Documents\\GitHub\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:153:5: DIGIT
							{
							match(input,DIGIT,FOLLOW_DIGIT_in_datafield420); 
							}
							break;

						default :
							if ( cnt6 >= 1 ) break loop6;
							EarlyExitException eee = new EarlyExitException(6, input);
							throw eee;
						}
						cnt6++;
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



	public static final BitSet FOLLOW_header_in_program60 = new BitSet(new long[]{0x01FFFEF7FFFBB402L});
	public static final BitSet FOLLOW_instruction_in_program62 = new BitSet(new long[]{0x01FFFEF7FFFBB402L});
	public static final BitSet FOLLOW_35_in_header75 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_NAME_in_header77 = new BitSet(new long[]{0x0000000000004100L});
	public static final BitSet FOLLOW_14_in_header80 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_NAME_in_header82 = new BitSet(new long[]{0x0000000000040100L});
	public static final BitSet FOLLOW_18_in_header85 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_dateformat_in_header87 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DAY_in_dateformat98 = new BitSet(new long[]{0x0000000000000C00L});
	public static final BitSet FOLLOW_set_in_dateformat100 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_MONTH_in_dateformat106 = new BitSet(new long[]{0x0000000000000C00L});
	public static final BitSet FOLLOW_set_in_dateformat108 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_DIGIT_in_dateformat114 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_DIGIT_in_dateformat116 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_10_in_instruction211 = new BitSet(new long[]{0x0000010000000100L});
	public static final BitSet FOLLOW_labelfield_in_instruction212 = new BitSet(new long[]{0x01FFFEF7FFFBB000L});
	public static final BitSet FOLLOW_instructiondata_in_instruction216 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_binaryinstruction_in_instructiondata259 = new BitSet(new long[]{0x0000000000000140L});
	public static final BitSet FOLLOW_datafield_in_instructiondata261 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_unaryinstruction_in_instructiondata269 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_datafield414 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIGIT_in_datafield420 = new BitSet(new long[]{0x0000000000000042L});
}
