// $ANTLR 3.5 C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g 2013-10-16 10:53:56

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class CecilParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "DAY", "DIGIT", "INSTRUCTIONFIELD", 
		"MONTH", "NAME", "WS", "'.'", "'/'", "'author'", "'date'", "'program'", 
		"'start'"
	};
	public static final int EOF=-1;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int COMMENT=4;
	public static final int DAY=5;
	public static final int DIGIT=6;
	public static final int INSTRUCTIONFIELD=7;
	public static final int MONTH=8;
	public static final int NAME=9;
	public static final int WS=10;

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
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g"; }



	// $ANTLR start "program"
	// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:3:1: program : header ( instruction )* ;
	public final void program() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:3:9: ( header ( instruction )* )
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:3:11: header ( instruction )*
			{
			pushFollow(FOLLOW_header_in_program10);
			header();
			state._fsp--;

			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:3:18: ( instruction )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==INSTRUCTIONFIELD||LA1_0==11) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:3:18: instruction
					{
					pushFollow(FOLLOW_instruction_in_program12);
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
	// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:1: instruction : ( labelfield )? INSTRUCTIONFIELD ( datafield )? ;
	public final void instruction() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:13: ( ( labelfield )? INSTRUCTIONFIELD ( datafield )? )
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:15: ( labelfield )? INSTRUCTIONFIELD ( datafield )?
			{
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:15: ( labelfield )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==11) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:15: labelfield
					{
					pushFollow(FOLLOW_labelfield_in_instruction21);
					labelfield();
					state._fsp--;

					}
					break;

			}

			match(input,INSTRUCTIONFIELD,FOLLOW_INSTRUCTIONFIELD_in_instruction24); 
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:44: ( datafield )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==DIGIT||LA3_0==NAME) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:5:44: datafield
					{
					pushFollow(FOLLOW_datafield_in_instruction26);
					datafield();
					state._fsp--;

					}
					break;

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
	// $ANTLR end "instruction"



	// $ANTLR start "datafield"
	// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:8:1: datafield : ( ( DIGIT )+ | NAME );
	public final void datafield() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:8:11: ( ( DIGIT )+ | NAME )
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==DIGIT) ) {
				alt5=1;
			}
			else if ( (LA5_0==NAME) ) {
				alt5=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 5, 0, input);
				throw nvae;
			}

			switch (alt5) {
				case 1 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:8:13: ( DIGIT )+
					{
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:8:13: ( DIGIT )+
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
							// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:8:13: DIGIT
							{
							match(input,DIGIT,FOLLOW_DIGIT_in_datafield66); 
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
				case 2 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:8:22: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_datafield71); 
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
	// $ANTLR end "datafield"



	// $ANTLR start "labelfield"
	// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:9:1: labelfield : '.' ( 'start' | NAME ) ;
	public final void labelfield() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:9:12: ( '.' ( 'start' | NAME ) )
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:9:14: '.' ( 'start' | NAME )
			{
			match(input,11,FOLLOW_11_in_labelfield78); 
			if ( input.LA(1)==NAME||input.LA(1)==16 ) {
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
	// $ANTLR end "labelfield"



	// $ANTLR start "header"
	// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:1: header : 'program' ( NAME )+ 'author' ( NAME )+ 'date' dateformat ;
	public final void header() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:9: ( 'program' ( NAME )+ 'author' ( NAME )+ 'date' dateformat )
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:11: 'program' ( NAME )+ 'author' ( NAME )+ 'date' dateformat
			{
			match(input,15,FOLLOW_15_in_header95); 
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:21: ( NAME )+
			int cnt6=0;
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==NAME) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:21: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_header97); 
					}
					break;

				default :
					if ( cnt6 >= 1 ) break loop6;
					EarlyExitException eee = new EarlyExitException(6, input);
					throw eee;
				}
				cnt6++;
			}

			match(input,13,FOLLOW_13_in_header100); 
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:36: ( NAME )+
			int cnt7=0;
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==NAME) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:11:36: NAME
					{
					match(input,NAME,FOLLOW_NAME_in_header102); 
					}
					break;

				default :
					if ( cnt7 >= 1 ) break loop7;
					EarlyExitException eee = new EarlyExitException(7, input);
					throw eee;
				}
				cnt7++;
			}

			match(input,14,FOLLOW_14_in_header105); 
			pushFollow(FOLLOW_dateformat_in_header107);
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
	// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:13:1: dateformat : DAY '/' MONTH '/' DIGIT DIGIT ;
	public final void dateformat() throws RecognitionException {
		try {
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:13:12: ( DAY '/' MONTH '/' DIGIT DIGIT )
			// C:\\Users\\Shreeprabha\\workspace\\CecilRPi\\src\\org\\raspberrypi\\cecil\\grammar\\Cecil.g:13:14: DAY '/' MONTH '/' DIGIT DIGIT
			{
			match(input,DAY,FOLLOW_DAY_in_dateformat115); 
			match(input,12,FOLLOW_12_in_dateformat117); 
			match(input,MONTH,FOLLOW_MONTH_in_dateformat119); 
			match(input,12,FOLLOW_12_in_dateformat121); 
			match(input,DIGIT,FOLLOW_DIGIT_in_dateformat123); 
			match(input,DIGIT,FOLLOW_DIGIT_in_dateformat125); 
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

	// Delegated rules



	public static final BitSet FOLLOW_header_in_program10 = new BitSet(new long[]{0x0000000000000882L});
	public static final BitSet FOLLOW_instruction_in_program12 = new BitSet(new long[]{0x0000000000000882L});
	public static final BitSet FOLLOW_labelfield_in_instruction21 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_INSTRUCTIONFIELD_in_instruction24 = new BitSet(new long[]{0x0000000000000242L});
	public static final BitSet FOLLOW_datafield_in_instruction26 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIGIT_in_datafield66 = new BitSet(new long[]{0x0000000000000042L});
	public static final BitSet FOLLOW_NAME_in_datafield71 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_11_in_labelfield78 = new BitSet(new long[]{0x0000000000010200L});
	public static final BitSet FOLLOW_set_in_labelfield80 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_header95 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_NAME_in_header97 = new BitSet(new long[]{0x0000000000002200L});
	public static final BitSet FOLLOW_13_in_header100 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_NAME_in_header102 = new BitSet(new long[]{0x0000000000004200L});
	public static final BitSet FOLLOW_14_in_header105 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_dateformat_in_header107 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DAY_in_dateformat115 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_dateformat117 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_MONTH_in_dateformat119 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_12_in_dateformat121 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_DIGIT_in_dateformat123 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_DIGIT_in_dateformat125 = new BitSet(new long[]{0x0000000000000002L});
}
