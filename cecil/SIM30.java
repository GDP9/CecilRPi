import javax.swing.JTextArea;
import java.lang.Math.*;

/**
 * @author 	David
 * @date	08.09.06
 * @version	0.1
 */

/*
 *  The SIM30 Class simulates the SIM30.
 *  It defines the memory structure and how the various machine codes are
 *  implemented, for example.
 *  It knows how to set and report memory locations, and how to
 *  run a stored program, amongst other things.
 */


public class SIM30 {

	/**
	 * @param args
	 */
/*-------------------------Constants----------------------------------*/
	/*The following constants define the SIM30 memory locations*/
	static final int userBot      = 0;
	static final int userTop 		= 964;
	static final int	stackBot    = 965;
    static final int	stackTop    = 996;
    static final int	stackPtr	= 997;
    static final int	spare1		= 998;
    static final int	spare2		= 999;
    static final int	timerLo     = 1000;
    static final int	timerHi     = 1001;
    static final int randReg     	= 1002;
    static final int spareIO1		= 1003;
    static final int spareIO2		= 1004;
    static final int spareIO3		= 1005;
    static final int soundPitch  	= 1006;
    static final int soundDur    	= 1007;
    static final int keybInport  	= 1008;
    static final int keybFlags   	= 1009;
    static final int vidOutport  	= 1010;
    static int	vidFlags    = 1011;
    static int	anOutport	= 1012;
    static int	anInport	= 1013;
    static int	anFlags		= 1014;
    static int	serOutport  = 1015;
    static int	serInport   = 1016;
    static int	serFlags    = 1017;
    static int	parOutport  = 1018;
    static int	parInport   = 1019;
    static int parFlags    	= 1020;
    static int intV        	= 1021;
    static int intEnable   	= 1022;
    static int startV		= 1023;

  /* The next few constants relate to "pseudo-locations" which can be displayed
   * by using "memdump"
   */
    static int progCtr  	= 1024;
    static int status 		= 1025;
    static int acc 			= 1026;
    static int xreg 		= 1027;
    static int yreg 		= 1028;
    static int memTop 		= 1028;
    
  /* The next constant defines the maximum size of static integer the AIM20 can handle */
    static int maxNum 		= 1023;
    
  /* These define the bit numbers for flags in status byte */
    static int zFlag  		= 1;
    static int nFlag   		= 2;
    static int cFlag   		= 4;
    
  /* These define the bit numbers for flags in static interrupt byte */
    static int intEn    	= 1;
    static int KeybIn  		= 2;
    static int SerIn   		= 4;
    static int ParIn    	= 8;
    static int SndClr  		= 16;
    static int SerOut    	= 32;
    static int ParOut 		= 64;
    static int VidOut   	= 128;
    static int timeOut     	= 256;
    
  /* And now... the SIM30 main memory */
    static int[] SIMmemory = new int[1029];
    
  // Now the command list for the SIM30
  // The various mnemonics all need to be defined
  // The first lot all require a data field
    static int maxCommand = 52;
    static int takesData = 22;
    static String[] CommandList = new String[maxCommand+1];
     static {
    	CommandList[0]  = "stop";
    	CommandList[1]  = "load";
        CommandList[2]  = "store";
        CommandList[3]  = "add";
        CommandList[4]  = "sub";
        CommandList[5]  = "and";
        CommandList[6]  = "or";
        CommandList[7]  = "eor";
        CommandList[8]  = "jump";
        CommandList[9]  = "comp";
        CommandList[10] = "jineg";
        CommandList[11] = "jipos";
        CommandList[12] = "jizero";
        CommandList[13] = "jmptosr";
        CommandList[14] = "jicarry";
        CommandList[15] = "xload";
        CommandList[16] = "xstore";
        CommandList[17] = "loadmx";
        CommandList[18] = "xcomp";
        CommandList[19] = "yload";
        CommandList[20] = "ystore";
        CommandList[21] = "pause";
        CommandList[22] = "printd";
    // From here on, no data field is required}
        CommandList[23] = "return";
        CommandList[24] = "push";
        CommandList[25] = "pull";
        CommandList[26] = "xpush";
        CommandList[27] = "xpull";
        CommandList[28] = "xinc";
        CommandList[29] = "xdec";
        CommandList[30] = "lshift";
        CommandList[31] = "rshift";
        CommandList[32] = "cset";
        CommandList[33] = "cclear";
        CommandList[34] = "getkey";
        CommandList[35] = "wait";
        CommandList[36] = "retfint";
        CommandList[37] = "printb";
        CommandList[38] = "print";
        CommandList[39] = "printch";
        CommandList[40] = "ypush";
        CommandList[41] = "ypull";
        CommandList[42] = "yinc";
        CommandList[43] = "ydec";
        CommandList[44] = "swapax";
        CommandList[45] = "swapay";
        CommandList[46] = "swapxy";
        CommandList[47] = "swapas";
        CommandList[48] = "intenable";
        CommandList[49] = "intdisable";
        CommandList[50] = "nop";
        CommandList[51] = "insert";	//This one is quirky - it's not an instruction!
        CommandList[52] = "halt";
     }
     //We also need a run error variable
     static boolean	runErr;
/*------------------------------Code--------------------------------------------*/
	/* Now for the publicly-available interface */
    public static void main() {
		// TODO Auto-generated method stub

	}
	/* This function allows writes to the SIM30 memory.
	 * Invalid attempts return false, otherwise true is returned.
	 */
    public static int lookUpCommand(String command)
    {	int value;
    	
    	value = 0;
    	while((value <= maxCommand) && (command.compareTo(CommandList[value])!=0)) value++;
    	if(value>maxCommand)value=-1;
    	return(value);
    }
    public static boolean needsData(int token)
    {	boolean	temp = false;
    	if(token<=takesData) temp = true;
    	if(token==0) temp = false; //stop is an exception
    	return temp;
    }
    public static boolean writeToMemory(int value, int loc) {
		boolean temp = false;
		
		if ((loc >= 0) && (loc <= memTop) && (value >= 0) && (value <= maxNum)) {
			SIMmemory[loc] = value;
			temp = true;
		}
		    else {
		    	temp = false;
		    	terminal.add("!!Invalid attempt to write to memory\n");
		    	terminal.add("-Trying to write: "+String.valueOf(value)+" to loc: "+String.valueOf(loc)+"\n");
		    }
		return temp;
	}
	public static int readMemory(int loc) {
		float val1 = (float) Math.random();
		float val2 = val1 * 1023;
		int		value = -1;
		
		if ((loc >= 0) && (loc <= memTop)) {
			if (loc == randReg) {
				value = (int)Math.ceil(val2);
			}
				else value = SIMmemory[loc];
		}
			else {
				value = -1;
				terminal.add("\nAttempt to read illegal address");
			}
		
		return value;
	}
	public static void MemDump(int start, int end) {
		int loc;
		if ((start < 0) || (end > memTop)) {
			terminal.add("\nMemory Dump parameters out of range\n");
		}
		else {
			terminal.add("\nning memory dump\n");		
			for (loc=start; loc<(end+1); loc++) {
				terminal.add("\nlocation: "+loc+"\tvalue: "+SIMmemory[loc]);
			}
			terminal.add("\nEnd of memory dump\n");
		}
		return;
	}
	static int fetch() {
	/*Fetches next instruction & increments prog ctr*/
		int instr, loc;
		loc=readMemory(progCtr);
		instr=readMemory(loc);
		loc++;
		writeToMemory(loc, progCtr);
		return(instr);
	}
	static boolean readStatus(int flag){
		boolean value;
		if((readMemory(status) & flag)>0) value=true;
			else value=false;
		return value;
	}

	static void setStatusFlag(int flag) {
		writeToMemory((readMemory(status) | flag), status);
		return;
	}
	
	static void clearStatusFlag(int flag) {
		writeToMemory((readMemory(status) & (maxNum ^ flag)), status);
		return;
	}
	
	static int setFlags(int result){
		if(result==0) setStatusFlag(zFlag);
			else clearStatusFlag(zFlag);
		if(result<0) {
			setStatusFlag(nFlag);
			result=result+maxNum+1;
		}
			else clearStatusFlag(nFlag);
		if(result>maxNum) {
			setStatusFlag(cFlag);
			result=result-maxNum-1;
		}
			else clearStatusFlag(cFlag);
		return(result);
	}

	static int negSetFlags(int result){
		if(result==(maxNum+1)) {
			setStatusFlag(zFlag);
			result=result-maxNum-1;
		}
			else clearStatusFlag(zFlag);
		if(result<(maxNum+1)) setStatusFlag(nFlag);
			else clearStatusFlag(nFlag);
		if(result>(maxNum+1)) {
			setStatusFlag(cFlag);
			result=result-maxNum-1;
		}
			else clearStatusFlag(cFlag);
		return(result);
	}

	static void stackPush(int value) {
		if(readMemory(stackPtr)<stackBot) runError("stack overflow");
		else
		{	writeToMemory(value, readMemory(stackPtr));
			writeToMemory((readMemory(stackPtr)-1), stackPtr); //decrement stack pointer (stack writes downwards)
		}
		return;
	}
	
	static int stackPull() {
		int value;
		//increment stack pointer (stack writes downwards) - do this first!!
		writeToMemory(readMemory(stackPtr)+1, stackPtr);
		if(readMemory(stackPtr)>stackTop) runError("stack underflow");
		value = readMemory(readMemory(stackPtr));
		return (value);
	}

	static void runError(String Message) {
		runErr = true;
		terminal.addLine("Run error: "+Message);
		return;
	}
	
	public static void Run() {
		String		errMess="";
		int			mCode=0;
		int			temp=0;
		String		tString;
		boolean		tBool;
		/*Note: need to check whether the stop button has been pressed*/
		
		/*Initialise program counter & stack pointer*/
		writeToMemory(readMemory(startV), progCtr);
		writeToMemory(stackTop, stackPtr);
		
		/*get next machine code*/
		mCode=fetch();
		/*Now do mindless loop through memory, doing instructions*/
		while(mCode!=0 && !runErr) {
/*trace*/			terminal.add("processing instr: "+ mCode+"\n");
			switch(mCode) {
				case 0: /*stop*/ 	break;
				case 1: /*load*/ {
					/*temp=fetch();
					terminal.add(":"+String.valueOf(temp)+".");
					terminal.add(String.valueOf(readMemory(temp))+":");*/
					writeToMemory(readMemory(fetch()), acc);
					break;
				}
				case 2: /*store*/	{
					temp=fetch();
					writeToMemory(readMemory(acc), temp);
					/*Some locs need special treatment!*/
					switch(temp){
						case vidOutport:	terminal.send(temp); break;
						default: break;  
					}
					break; 
				}
				case 3: /*add*/ {
					temp = readMemory(acc) + readMemory(fetch());
					if (readStatus(cFlag)) temp++;
					temp = setFlags(temp);
					writeToMemory(temp, acc);
					break;
				}
				case 4: /*sub*/ {
					temp = readMemory(acc) + (readMemory(fetch()) ^ maxNum);
					if (readStatus(cFlag)) temp++;
					temp = negSetFlags(temp);
					writeToMemory(temp, acc);					
					break;
				}
				case 5: /*and*/ {
					temp = readMemory(acc) & readMemory(fetch());
					temp = setFlags(temp);
					writeToMemory(temp, acc);					
					break;
				}
				case 6: /*or*/ {
					temp = readMemory(acc) | readMemory(fetch());
	                temp = setFlags(temp);
	                writeToMemory(temp, acc);
	                break;
				}
				case 7: /*xor*/ {
					temp = readMemory(acc) ^ readMemory(fetch());
	                temp = setFlags(temp);
	                writeToMemory(temp, acc);
	                break;
				}
				case 8: /*jump*/ {
	               writeToMemory(fetch(), progCtr);
	               break;
				}
				case 9: /*comp*/ {
	               temp = readMemory(acc) - readMemory(fetch());
	               setFlags(temp);
	               break;
				}
				case 10: /*jineg*/ {
	               temp = fetch(); /*Note: MUST get next loc regardless - this
	                                    has been a bug in previous versions...!*/
	               if(readStatus(nFlag)) writeToMemory(temp, progCtr);
	               break;
				}
				case 11: /*jipos*/ {
	               temp = fetch(); /*Note: MUST get next loc regardless - this
	                                    has been a bug in previous versions...!*/
	               if(!readStatus(nFlag)) writeToMemory(temp, progCtr);
	               break;
				}
				case 12: /*jizero*/ {
	               temp = fetch(); /*Note: MUST get next loc regardless - this
	                                    has been a bug in previous versions...!*/
	               if(readStatus(zFlag)) writeToMemory(temp, progCtr);
	               break;
				}
				case 13: /*jmptosr*/ {
	              /*push prog cntr onto stack, allowing for next instruction fetch*/
	              stackPush(readMemory(progCtr) + 1);
	              /*set PC for new address*/
	              writeToMemory(fetch(), progCtr);
	              break;
				}
				case 14: /*jicarry*/ {
	               temp = fetch(); /*Note: MUST get next loc regardless - this
	                                    has been a bug in previous versions...!*/
	               if(readStatus(cFlag)) writeToMemory(temp, progCtr);
	               break;
				}
				case 15: /*xload*/ {
	               writeToMemory(readMemory(fetch()), xreg);
	               break;
				}
				case 16: /*xstore*/ {
	               writeToMemory(readMemory(xreg), fetch());
	               break;
				}
				case 17: /*loadmx*/ {
	               writeToMemory(readMemory(fetch()+readMemory(xreg)), acc);
	               break;
				}
				case 18: /*xcomp*/ {
	               temp = readMemory(xreg) - readMemory(fetch());
	               setFlags(temp);
	               break;
				}
				case 19: /*yload*/ {
	               writeToMemory(readMemory(fetch()), yreg);
	               break;
				}
				case 20: /*ystore*/ {
	               writeToMemory(readMemory(yreg), fetch());
	               break;
				}
				case 21: /*pause - for time specified in data field in 20ths of sec*/ {
	               temp = readMemory(fetch()) * 50;
	                 /*Endtime := Time + EncodeTime(0,0,temp div 1000,temp mod 1000);
	                 while Time < Endtime do Application.ProcessMessages;*/
	               //for now...
	               terminal.addLine("pausing for "+String.valueOf(temp)+" 100ths sec");
	               break;
				}
				case 22: /*printd*/ {
	               temp = fetch();
	               tString = String.valueOf((readMemory(temp)) + (readMemory(temp+1)*(maxNum+1)));
	               terminal.add(tString);
	               break;
				}
	//From here on, no data field is required
				case 23: /*return*/ {
	               //Pull prog cntr off stack
	               writeToMemory(stackPull(), progCtr);
	               break;
				}
				case 24: /*push*/ {
	               stackPush(readMemory(acc));
	               break;
				}
				case 25: /*pull*/ {
	               writeToMemory(stackPull(), acc);
	               break;
				}
	            case 26: /*xpush*/ {
	               stackPush(readMemory(xreg));
	               break;
	            }
				case 27: /*xpull*/ {
	               writeToMemory(stackPull(), xreg);
	               break;
				}
				case 28: /*xinc*/ {
	               temp = readMemory(xreg)+1;
	               setFlags(temp);
	               writeToMemory(temp, xreg);
	               break;
				}
				case 29: /*xdec*/ {
	               temp = readMemory(xreg)-1;
	               setFlags(temp);
	               writeToMemory(temp, xreg);
	               break;
				}
				case 30: /*lshift*/ {
	               temp = (readMemory(acc) << 1);
	               if (readStatus(cFlag)) temp++;
	               temp = setFlags(temp);
	               writeToMemory(temp, acc);
	               break;
				}
				case 31: /*rshift*/ {
	               if((readMemory(acc) & 1) > 0) tBool = true;
	               else tBool = false;
	               temp = (readMemory(acc) >>> 1); // Java's >>> makes sure that top bit is not set
	               if (readStatus(cFlag)) temp = temp + ((maxNum+1) / 2);
	               setFlags(temp);
	               if(tBool) setStatusFlag(cFlag);
	               else clearStatusFlag(cFlag);
	               writeToMemory(temp, acc);
	               break;
				}
				case 32: /*cset*/ {
	                  setStatusFlag(cFlag);
	                  break;
				}
				case 33: /*cclear*/ {
	                  clearStatusFlag(cFlag);
	                  break;
				}
				case 34: /*getkey*/ {
                /*OutputText.SetFocus;
                OutputText.SelStart := (OutputLineIndex*39) + OutputCharIndex;
                while (ReadMemory(keybInport) = 0) and (not RunError) do Application.ProcessMessages;{make sure we have the focus or we're in trouble!}
                WriteToMemory((ReadMemory(keybInport) and 255), acc); {get the key}
                WriteToMemory(0, keybInport); {clear the buffer}
                WriteToMemory((ReadMemory(keybFlags) and (512+255)), keybFlags); {clear the interrupt flag}
                */
					break;
				}
				case 35: /*wait - for time specified in acc in 20ths of sec*/ {
					temp = readMemory(acc) * 50;
					//Endtime = Time + EncodeTime(0,0,temp div 1000,temp mod 1000);
					//while Time < Endtime do Application.ProcessMessages;
					break;
				}
				case 36: /*retfint*/ {
					//Reset interrupt flag
					writeToMemory(readMemory(intEnable) | intEn, intEnable);
					//Pull prog cntr off stack
					writeToMemory(stackPull(), progCtr);
					break;
				}
				case 37: /*printb*/ {
                    terminal.add(Integer.toBinaryString(readMemory(acc)));
                	break;
				}
	 			case 38: /*print*/ {
					tString = String.valueOf(readMemory(acc));
					terminal.add(tString);//remember to sort this out!					
					break;
				}
				case 39: /*printch*/{
					char c = (char)(readMemory(acc) & 255);
					terminal.add(String.valueOf(c));
                	break;
				}
				case 40: /*ypush*/ {
					stackPush(readMemory(yreg));
					break;
				}
				case 41: /*ypull*/ {
					writeToMemory(stackPull(), yreg);
					break;
				}
				case 42: /*yinc*/ {
					temp = readMemory(yreg)+1;
					setFlags(temp);
					writeToMemory(temp, yreg);
					break;
				}
				case 43: /*ydec*/ {
					temp = readMemory(yreg)-1;
					setFlags(temp);
					writeToMemory(temp, yreg);
					break;
				}
				case 44: /*swapax*/ {
					temp = readMemory(acc);
					writeToMemory(readMemory(xreg), acc);
					writeToMemory(temp, xreg);
					break;
				}
				case 45: /*swapay*/ {
					temp = readMemory(acc);
					writeToMemory(readMemory(yreg), acc);
					writeToMemory(temp, yreg);
					break;
				}
				case 46: /*swapxy*/ {
					temp = readMemory(xreg);
					writeToMemory(readMemory(yreg), xreg);
					writeToMemory(temp, yreg);
					break;
				}
				case 47: /*swapas*/ {
					temp = readMemory(acc);
					writeToMemory(readMemory(status), acc);
					writeToMemory(temp, status);
					break;
				}
				case 48: /*intenable*/ {
					writeToMemory(readMemory(intEnable) | intEn, intEnable);
					break;
				}
				case 49: /*intdisable*/ {
					writeToMemory(readMemory(intEnable) & (maxNum ^ intEn), intEnable);
					break;
				}
				case 50: /*nop*/ break;
				case 51: /*nop*/ break;
				case 52: /*end*/ break;
				default: /*error!*/ runError("Unrecognised instruction"); break;
			}
			if((mCode!=0) && (mCode!=52))mCode=fetch();
			else if(mCode==52)mCode=0;
		}
		if(runErr) terminal.add("\nRun error, run terminated\n");
			else terminal.add("\nProgram run completed\n");
		return;
	}


}
