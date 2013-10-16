// $ANTLR 3.5 C:\\Users\\Shreeprabha\\grammar\\Hello.g 2013-10-16 06:19:28

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.debug.*;
import java.io.IOException;
@SuppressWarnings("all")
public class HelloParser extends DebugParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "DIGIT", "INSTRUCTIONFIELD", 
		"NAME", "WS", "'.'", "'start'"
	};
	public static final int EOF=-1;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int COMMENT=4;
	public static final int DIGIT=5;
	public static final int INSTRUCTIONFIELD=6;
	public static final int NAME=7;
	public static final int WS=8;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public static final String[] ruleNames = new String[] {
		"invalidRule", "program", "datafield", "labelfield", "instruction"
	};

	public static final boolean[] decisionCanBacktrack = new boolean[] {
		false, // invalid decision
		false, false, false, false
	};

 
	public int ruleLevel = 0;
	public int getRuleLevel() { return ruleLevel; }
	public void incRuleLevel() { ruleLevel++; }
	public void decRuleLevel() { ruleLevel--; }
	public HelloParser(TokenStream input) {
		this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
	}
	public HelloParser(TokenStream input, int port, RecognizerSharedState state) {
		super(input, state);
		DebugEventSocketProxy proxy =
			new DebugEventSocketProxy(this, port, null);

		setDebugListener(proxy);
		try {
			proxy.handshake();
		}
		catch (IOException ioe) {
			reportError(ioe);
		}
	}

	public HelloParser(TokenStream input, DebugEventListener dbg) {
		super(input, dbg, new RecognizerSharedState());
	}

	protected boolean evalPredicate(boolean result, String predicate) {
		dbg.semanticPredicate(result, predicate);
		return result;
	}

	@Override public String[] getTokenNames() { return HelloParser.tokenNames; }
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\grammar\\Hello.g"; }



	// $ANTLR start "program"
	// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:1: program : ( instruction )* ;
	public final void program() throws  {
		try { dbg.enterRule(getGrammarFileName(), "program");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(3, 0);

		try {
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:9: ( ( instruction )* )
			dbg.enterAlt(1);

			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:11: ( instruction )*
			{
			dbg.location(3,11);
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:11: ( instruction )*
			try { dbg.enterSubRule(1);

			loop1:
			while (true) {
				int alt1=2;
				try { dbg.enterDecision(1, decisionCanBacktrack[1]);

				int LA1_0 = input.LA(1);
				if ( (LA1_0==INSTRUCTIONFIELD||LA1_0==9) ) {
					alt1=1;
				}

				} finally {dbg.exitDecision(1);}

				switch (alt1) {
				case 1 :
					dbg.enterAlt(1);

					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:11: instruction
					{
					dbg.location(3,11);
					pushFollow(FOLLOW_instruction_in_program10);
					instruction();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}
			} finally {dbg.exitSubRule(1);}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		dbg.location(3, 22);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "program");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "program"



	// $ANTLR start "instruction"
	// C:\\Users\\Shreeprabha\\grammar\\Hello.g:7:1: instruction : ( labelfield )? INSTRUCTIONFIELD datafield ;
	public final void instruction() throws  {
		try { dbg.enterRule(getGrammarFileName(), "instruction");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(7, 0);

		try {
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:7:13: ( ( labelfield )? INSTRUCTIONFIELD datafield )
			dbg.enterAlt(1);

			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:7:15: ( labelfield )? INSTRUCTIONFIELD datafield
			{
			dbg.location(7,15);
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:7:15: ( labelfield )?
			int alt2=2;
			try { dbg.enterSubRule(2);
			try { dbg.enterDecision(2, decisionCanBacktrack[2]);

			int LA2_0 = input.LA(1);
			if ( (LA2_0==9) ) {
				alt2=1;
			}
			} finally {dbg.exitDecision(2);}

			switch (alt2) {
				case 1 :
					dbg.enterAlt(1);

					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:7:15: labelfield
					{
					dbg.location(7,15);
					pushFollow(FOLLOW_labelfield_in_instruction21);
					labelfield();
					state._fsp--;

					}
					break;

			}
			} finally {dbg.exitSubRule(2);}
			dbg.location(7,27);
			match(input,INSTRUCTIONFIELD,FOLLOW_INSTRUCTIONFIELD_in_instruction24); dbg.location(7,44);
			pushFollow(FOLLOW_datafield_in_instruction26);
			datafield();
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
		dbg.location(7, 52);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "instruction");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "instruction"



	// $ANTLR start "datafield"
	// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:1: datafield : ( ( DIGIT ( DIGIT )? ) | NAME );
	public final void datafield() throws  {
		try { dbg.enterRule(getGrammarFileName(), "datafield");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(10, 0);

		try {
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:11: ( ( DIGIT ( DIGIT )? ) | NAME )
			int alt4=2;
			try { dbg.enterDecision(4, decisionCanBacktrack[4]);

			int LA4_0 = input.LA(1);
			if ( (LA4_0==DIGIT) ) {
				alt4=1;
			}
			else if ( (LA4_0==NAME) ) {
				alt4=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				dbg.recognitionException(nvae);
				throw nvae;
			}

			} finally {dbg.exitDecision(4);}

			switch (alt4) {
				case 1 :
					dbg.enterAlt(1);

					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:13: ( DIGIT ( DIGIT )? )
					{
					dbg.location(10,13);
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:13: ( DIGIT ( DIGIT )? )
					dbg.enterAlt(1);

					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:14: DIGIT ( DIGIT )?
					{
					dbg.location(10,14);
					match(input,DIGIT,FOLLOW_DIGIT_in_datafield62); dbg.location(10,20);
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:20: ( DIGIT )?
					int alt3=2;
					try { dbg.enterSubRule(3);
					try { dbg.enterDecision(3, decisionCanBacktrack[3]);

					int LA3_0 = input.LA(1);
					if ( (LA3_0==DIGIT) ) {
						alt3=1;
					}
					} finally {dbg.exitDecision(3);}

					switch (alt3) {
						case 1 :
							dbg.enterAlt(1);

							// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:21: DIGIT
							{
							dbg.location(10,21);
							match(input,DIGIT,FOLLOW_DIGIT_in_datafield65); dbg.location(10,26);
							3
							}
							break;

					}
					} finally {dbg.exitSubRule(3);}

					}

					}
					break;
				case 2 :
					dbg.enterAlt(2);

					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:10:35: NAME
					{
					dbg.location(10,35);
					match(input,NAME,FOLLOW_NAME_in_datafield73); 
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
		dbg.location(10, 38);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "datafield");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "datafield"



	// $ANTLR start "labelfield"
	// C:\\Users\\Shreeprabha\\grammar\\Hello.g:11:1: labelfield : '.' ( 'start' | NAME ) ;
	public final void labelfield() throws  {
		try { dbg.enterRule(getGrammarFileName(), "labelfield");
		if ( getRuleLevel()==0 ) {dbg.commence();}
		incRuleLevel();
		dbg.location(11, 0);

		try {
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:11:12: ( '.' ( 'start' | NAME ) )
			dbg.enterAlt(1);

			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:11:14: '.' ( 'start' | NAME )
			{
			dbg.location(11,14);
			match(input,9,FOLLOW_9_in_labelfield80); dbg.location(11,18);
			if ( input.LA(1)==NAME||input.LA(1)==10 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				dbg.recognitionException(mse);
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
		dbg.location(11, 33);

		}
		finally {
			dbg.exitRule(getGrammarFileName(), "labelfield");
			decRuleLevel();
			if ( getRuleLevel()==0 ) {dbg.terminate();}
		}

	}
	// $ANTLR end "labelfield"

	// Delegated rules



	public static final BitSet FOLLOW_instruction_in_program10 = new BitSet(new long[]{0x0000000000000242L});
	public static final BitSet FOLLOW_labelfield_in_instruction21 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_INSTRUCTIONFIELD_in_instruction24 = new BitSet(new long[]{0x00000000000000A0L});
	public static final BitSet FOLLOW_datafield_in_instruction26 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_DIGIT_in_datafield62 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_DIGIT_in_datafield65 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NAME_in_datafield73 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_9_in_labelfield80 = new BitSet(new long[]{0x0000000000000480L});
	public static final BitSet FOLLOW_set_in_labelfield82 = new BitSet(new long[]{0x0000000000000002L});
}
