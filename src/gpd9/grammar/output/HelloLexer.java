// $ANTLR 3.5 C:\\Users\\Shreeprabha\\grammar\\Hello.g 2013-10-16 06:19:28

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class HelloLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__9=9;
	public static final int T__10=10;
	public static final int COMMENT=4;
	public static final int DIGIT=5;
	public static final int INSTRUCTIONFIELD=6;
	public static final int NAME=7;
	public static final int WS=8;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public HelloLexer() {} 
	public HelloLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public HelloLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "C:\\Users\\Shreeprabha\\grammar\\Hello.g"; }

	// $ANTLR start "T__9"
	public final void mT__9() throws RecognitionException {
		try {
			int _type = T__9;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:2:6: ( '.' )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:2:8: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__9"

	// $ANTLR start "T__10"
	public final void mT__10() throws RecognitionException {
		try {
			int _type = T__10;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:7: ( 'start' )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:3:9: 'start'
			{
			match("start"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__10"

	// $ANTLR start "INSTRUCTIONFIELD"
	public final void mINSTRUCTIONFIELD() throws RecognitionException {
		try {
			int _type = INSTRUCTIONFIELD;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:18: ( ( 'stop' | 'load' | 'store' | 'add' | 'sub' | 'print' | 'printch' ) )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:20: ( 'stop' | 'load' | 'store' | 'add' | 'sub' | 'print' | 'printch' )
			{
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:20: ( 'stop' | 'load' | 'store' | 'add' | 'sub' | 'print' | 'printch' )
			int alt1=7;
			switch ( input.LA(1) ) {
			case 's':
				{
				int LA1_1 = input.LA(2);
				if ( (LA1_1=='t') ) {
					int LA1_5 = input.LA(3);
					if ( (LA1_5=='o') ) {
						int LA1_8 = input.LA(4);
						if ( (LA1_8=='p') ) {
							alt1=1;
						}
						else if ( (LA1_8=='r') ) {
							alt1=3;
						}

						else {
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 1, 8, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 1, 5, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( (LA1_1=='u') ) {
					alt1=5;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 1, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 'l':
				{
				alt1=2;
				}
				break;
			case 'a':
				{
				alt1=4;
				}
				break;
			case 'p':
				{
				int LA1_4 = input.LA(2);
				if ( (LA1_4=='r') ) {
					int LA1_7 = input.LA(3);
					if ( (LA1_7=='i') ) {
						int LA1_9 = input.LA(4);
						if ( (LA1_9=='n') ) {
							int LA1_12 = input.LA(5);
							if ( (LA1_12=='t') ) {
								int LA1_13 = input.LA(6);
								if ( (LA1_13=='c') ) {
									alt1=7;
								}

								else {
									alt1=6;
								}

							}

							else {
								int nvaeMark = input.mark();
								try {
									for (int nvaeConsume = 0; nvaeConsume < 5 - 1; nvaeConsume++) {
										input.consume();
									}
									NoViableAltException nvae =
										new NoViableAltException("", 1, 12, input);
									throw nvae;
								} finally {
									input.rewind(nvaeMark);
								}
							}

						}

						else {
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 1, 9, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}

					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 1, 7, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 1, 4, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}
			switch (alt1) {
				case 1 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:21: 'stop'
					{
					match("stop"); 

					}
					break;
				case 2 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:29: 'load'
					{
					match("load"); 

					}
					break;
				case 3 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:37: 'store'
					{
					match("store"); 

					}
					break;
				case 4 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:46: 'add'
					{
					match("add"); 

					}
					break;
				case 5 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:53: 'sub'
					{
					match("sub"); 

					}
					break;
				case 6 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:60: 'print'
					{
					match("print"); 

					}
					break;
				case 7 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:9:69: 'printch'
					{
					match("printch"); 

					}
					break;

			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INSTRUCTIONFIELD"

	// $ANTLR start "NAME"
	public final void mNAME() throws RecognitionException {
		try {
			int _type = NAME;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:19:6: ( 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )* )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:19:8: 'a' .. 'z' ( 'a' .. 'z' | '0' .. '9' | '_' )*
			{
			matchRange('a','z'); 
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:19:17: ( 'a' .. 'z' | '0' .. '9' | '_' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NAME"

	// $ANTLR start "DIGIT"
	public final void mDIGIT() throws RecognitionException {
		try {
			int _type = DIGIT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:20:7: ( '0' .. '9' )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "DIGIT"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:23:3: ( ';' ( . )* '\\n' )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:23:3: ';' ( . )* '\\n'
			{
			match(';'); 
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:23:7: ( . )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0=='\n') ) {
					alt3=2;
				}
				else if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\uFFFF')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:23:7: .
					{
					matchAny(); 
					}
					break;

				default :
					break loop3;
				}
			}

			match('\n'); 
			skip();
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:25:4: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:25:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
			{
			// C:\\Users\\Shreeprabha\\grammar\\Hello.g:25:6: ( ' ' | '\\t' | '\\r' | '\\n' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// C:\\Users\\Shreeprabha\\grammar\\Hello.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			skip();
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:8: ( T__9 | T__10 | INSTRUCTIONFIELD | NAME | DIGIT | COMMENT | WS )
		int alt5=7;
		switch ( input.LA(1) ) {
		case '.':
			{
			alt5=1;
			}
			break;
		case 's':
			{
			switch ( input.LA(2) ) {
			case 't':
				{
				switch ( input.LA(3) ) {
				case 'a':
					{
					int LA5_15 = input.LA(4);
					if ( (LA5_15=='r') ) {
						int LA5_21 = input.LA(5);
						if ( (LA5_21=='t') ) {
							int LA5_27 = input.LA(6);
							if ( ((LA5_27 >= '0' && LA5_27 <= '9')||LA5_27=='_'||(LA5_27 >= 'a' && LA5_27 <= 'z')) ) {
								alt5=4;
							}

							else {
								alt5=2;
							}

						}

						else {
							alt5=4;
						}

					}

					else {
						alt5=4;
					}

					}
					break;
				case 'o':
					{
					switch ( input.LA(4) ) {
					case 'p':
						{
						int LA5_22 = input.LA(5);
						if ( ((LA5_22 >= '0' && LA5_22 <= '9')||LA5_22=='_'||(LA5_22 >= 'a' && LA5_22 <= 'z')) ) {
							alt5=4;
						}

						else {
							alt5=3;
						}

						}
						break;
					case 'r':
						{
						int LA5_23 = input.LA(5);
						if ( (LA5_23=='e') ) {
							int LA5_28 = input.LA(6);
							if ( ((LA5_28 >= '0' && LA5_28 <= '9')||LA5_28=='_'||(LA5_28 >= 'a' && LA5_28 <= 'z')) ) {
								alt5=4;
							}

							else {
								alt5=3;
							}

						}

						else {
							alt5=4;
						}

						}
						break;
					default:
						alt5=4;
					}
					}
					break;
				default:
					alt5=4;
				}
				}
				break;
			case 'u':
				{
				int LA5_11 = input.LA(3);
				if ( (LA5_11=='b') ) {
					int LA5_17 = input.LA(4);
					if ( ((LA5_17 >= '0' && LA5_17 <= '9')||LA5_17=='_'||(LA5_17 >= 'a' && LA5_17 <= 'z')) ) {
						alt5=4;
					}

					else {
						alt5=3;
					}

				}

				else {
					alt5=4;
				}

				}
				break;
			default:
				alt5=4;
			}
			}
			break;
		case 'l':
			{
			int LA5_3 = input.LA(2);
			if ( (LA5_3=='o') ) {
				int LA5_12 = input.LA(3);
				if ( (LA5_12=='a') ) {
					int LA5_18 = input.LA(4);
					if ( (LA5_18=='d') ) {
						int LA5_25 = input.LA(5);
						if ( ((LA5_25 >= '0' && LA5_25 <= '9')||LA5_25=='_'||(LA5_25 >= 'a' && LA5_25 <= 'z')) ) {
							alt5=4;
						}

						else {
							alt5=3;
						}

					}

					else {
						alt5=4;
					}

				}

				else {
					alt5=4;
				}

			}

			else {
				alt5=4;
			}

			}
			break;
		case 'a':
			{
			int LA5_4 = input.LA(2);
			if ( (LA5_4=='d') ) {
				int LA5_13 = input.LA(3);
				if ( (LA5_13=='d') ) {
					int LA5_19 = input.LA(4);
					if ( ((LA5_19 >= '0' && LA5_19 <= '9')||LA5_19=='_'||(LA5_19 >= 'a' && LA5_19 <= 'z')) ) {
						alt5=4;
					}

					else {
						alt5=3;
					}

				}

				else {
					alt5=4;
				}

			}

			else {
				alt5=4;
			}

			}
			break;
		case 'p':
			{
			int LA5_5 = input.LA(2);
			if ( (LA5_5=='r') ) {
				int LA5_14 = input.LA(3);
				if ( (LA5_14=='i') ) {
					int LA5_20 = input.LA(4);
					if ( (LA5_20=='n') ) {
						int LA5_26 = input.LA(5);
						if ( (LA5_26=='t') ) {
							switch ( input.LA(6) ) {
							case 'c':
								{
								int LA5_31 = input.LA(7);
								if ( (LA5_31=='h') ) {
									int LA5_32 = input.LA(8);
									if ( ((LA5_32 >= '0' && LA5_32 <= '9')||LA5_32=='_'||(LA5_32 >= 'a' && LA5_32 <= 'z')) ) {
										alt5=4;
									}

									else {
										alt5=3;
									}

								}

								else {
									alt5=4;
								}

								}
								break;
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
							case '_':
							case 'a':
							case 'b':
							case 'd':
							case 'e':
							case 'f':
							case 'g':
							case 'h':
							case 'i':
							case 'j':
							case 'k':
							case 'l':
							case 'm':
							case 'n':
							case 'o':
							case 'p':
							case 'q':
							case 'r':
							case 's':
							case 't':
							case 'u':
							case 'v':
							case 'w':
							case 'x':
							case 'y':
							case 'z':
								{
								alt5=4;
								}
								break;
							default:
								alt5=3;
							}
						}

						else {
							alt5=4;
						}

					}

					else {
						alt5=4;
					}

				}

				else {
					alt5=4;
				}

			}

			else {
				alt5=4;
			}

			}
			break;
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'm':
		case 'n':
		case 'o':
		case 'q':
		case 'r':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt5=4;
			}
			break;
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			{
			alt5=5;
			}
			break;
		case ';':
			{
			alt5=6;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
			{
			alt5=7;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 5, 0, input);
			throw nvae;
		}
		switch (alt5) {
			case 1 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:10: T__9
				{
				mT__9(); 

				}
				break;
			case 2 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:15: T__10
				{
				mT__10(); 

				}
				break;
			case 3 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:21: INSTRUCTIONFIELD
				{
				mINSTRUCTIONFIELD(); 

				}
				break;
			case 4 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:38: NAME
				{
				mNAME(); 

				}
				break;
			case 5 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:43: DIGIT
				{
				mDIGIT(); 

				}
				break;
			case 6 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:49: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 7 :
				// C:\\Users\\Shreeprabha\\grammar\\Hello.g:1:57: WS
				{
				mWS(); 

				}
				break;

		}
	}



}
