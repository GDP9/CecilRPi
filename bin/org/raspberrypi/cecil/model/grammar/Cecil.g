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
 
grammar Cecil;

options {
    language = Java;
}

/* package and import declaration for parser */
@header { 
  package org.raspberrypi.cecil.model.grammar;
  import java.util.HashMap;
  import java.util.ArrayList;
  import org.raspberrypi.cecil.model.*;
  import org.raspberrypi.cecil.pojo.*;
  import org.raspberrypi.cecil.model.outputstream.OutputError;
  import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
}

/* package and import declaration for lexer */
@lexer::header {
  package org.raspberrypi.cecil.model.grammar;
}

/* Member function and field declaration */

@members { 
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
}


@rulecatch {
	catch (RecognitionException e) {
		String hdr = getErrorHeader(e);
		String msg = getErrorMessage(e, tokenNames);
		System.out.println(e.line + msg);
    
		this.stream.getErrors().add(new OutputError(e.line, msg));
		
	}
	
	catch (NullPointerException e1) {
    //String hdr = getErrorHeader(e1);
    //String msg = getErrorMessage(e1, tokenNames);
    //throw new RecognitionException();
    
    //this.stream.getErrors().add(new OutputError(e1.line, msg));
    
  }
}

/**
 * Rules
 * TODO: getkey, wait, pause, retfint, swapax, swapay, swapxy, swapas, intenable, intdisable, nop, halt, ypull, ypush, push, 
 *       lshift, rshift, pull, printd, xpull, xpush, jmptosr, return
 * Reserved Keywords: all-instructions
 */
program 
  : '.start' mnemonicdata instruction* stop
  ;

instruction 
  : ('.' labelfield  
  { /* if label already does not exist or isn't start then add to the hash */
    if(labelfield.containsKey($labelfield.text)) throw new RecognitionException();
    else labelfield.put(($labelfield.text),pointer);
  }
  )? mnemonicdata
  
  
  ;  
  
labelfield 
  :  NAME
  ;

mnemonicdata 
  : (binaryinstruction datafield {
      /* if instruction is insert and data is integer then add value to memory */
         if(($binaryinstruction.text).equals("insert")) {
          if(($datafield.text).matches("^[0-9]+$")) {
            instructionfield.put(pointer, "insert");
            sim40.memory[pointer++] = Integer.parseInt($datafield.text);
          }
          else throw new RecognitionException();
        }
      /* else reference instruction */  
        else {
          instructionfield.put(pointer, $binaryinstruction.text);
          
          if(instructionList.instructionToMnemonic($binaryinstruction.text) == -1)
            throw new RecognitionException();
          
          sim40.memory[pointer++] = instructionList.instructionToMnemonic($binaryinstruction.text); 
          
          if(($datafield.text).matches("^[0-9]+$"))
            sim40.memory[pointer++] = Integer.parseInt($datafield.text);
          
          else 
            datafield.put(pointer++,$datafield.text);
         } 
     }) 
       
  | unaryinstruction 
      {
        instructionfield.put(pointer, $unaryinstruction.text);
        sim40.memory[pointer++] = instructionList.instructionToMnemonic($unaryinstruction.text);
      }
  ;

unaryinstruction
  : (stop|'print'|'printch'|'printb'|'cclear'|'cset'|'xdec'|'xinc'|'ydec'|'yinc')
  ;
  
binaryinstruction
  : ('add'|'sub'|'and'|'comp'|'xor'|'or'|'jineg'|'jicarry'|'jipos'|'jizero'|'jump'
  |'load'|'xload'|'yload'|'xstore'|'ystore'|'loadmx'|'store'|'xcomp'|'insert'|'ycomp')
  ;
  
stop
  :'stop'
  {
    instructionfield.put(pointer, "stop");
    sim40.memory[pointer++] = 0;
  }
  
  ;
  
datafield 
  : NAME
  | DIGIT+
  ;

NAME : ('a'..'z' | 'A'..'Z') ('a'..'z' |'A'..'Z'| '0'	..'9' | '_' )*;

DIGIT : '0'.. '9';

/* 
 * Only single line comments beginning with semicolon are supported
 */
COMMENT : ';' .* '\n'  {$channel=HIDDEN;} ;
WS : (' '|'\t'|'\r'|'\n')+  {$channel=HIDDEN;} ;