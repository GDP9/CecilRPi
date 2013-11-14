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
 
grammar Cecil;

options {
    language = Java;
}

/*Header with package and import declaration for parser*/
@header { 
  package org.raspberrypi.cecil.model.grammar;
  import java.util.HashMap;
  import java.util.ArrayList;
  import org.raspberrypi.cecil.model.*;
  import org.raspberrypi.cecil.pojo.*;
  import org.raspberrypi.cecil.model.outputstream.OutputError;
  import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
}

/*Header with package and import declaration for lexer*/
@lexer::header {
  package org.raspberrypi.cecil.model.grammar;
}

/* Member function and field declaration */
@parser::members { 
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
}


  
@rulecatch {
  /**
  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
  */
     catch (RecognitionException e) {
             String hdr = getErrorHeader(e);
             String msg = getErrorMessage(e, tokenNames);
             System.out.println(e.line + " : " + msg);
             this.stream.getErrors().add(new OutputError(e.line, msg));      
     }
     catch (Exception e) {
     System.out.println("Other exception : " + e.getMessage());
     } 
      
}

/**
 * CECIL sim30 lexer rules
 * Not added to grammar: getkey, wait, pause, retfint, swapax, swapay, swapxy, swapas, intenable, intdisable, nop, halt
 * Reserved Keywords: all-instructions
 * a program contitutes labelfields, instructions (binary and unary), datafields and comments
 */
program 
  : '.start'? mnemonicdata instruction*;

instruction 
  : ('.' labelfield  
  { /* filling labelfield hashmap */
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
      /* If an instruction is 'insert' and data is integer then add the value directly to memory */
         if(($binaryinstruction.text).equals("insert")) {
          if(($datafield.text).matches("^[0-9]+$")) {
            instructionfield.put(pointer, "insert");
            sim40.memory[pointer++] = Integer.parseInt($datafield.text);
          }
          else throw new RecognitionException();
        }
      /* Else if instruction is not 'insert', reference to corresponding instruction and add the value to memory (from memory address)
       * Filling in instruction hashmap with binary instructions
       */  
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
      {/* Filling  instruction hashmap with unary instructions */
        instructionfield.put(pointer, $unaryinstruction.text);
        sim40.memory[pointer++] = instructionList.instructionToMnemonic($unaryinstruction.text);
      }
  ;

unaryinstruction
  : ('stop'| 'print'| 'printch'|'printb'|'printd'|'cclear'|'cset'|'lshift'|'rshift'|'pull'
  |'xdec'|'xinc'|'xpull'|'xpush'|'ydec'|'yinc'|'ypull'|'ypush'|'push')
  ;
  
binaryinstruction
  : ('add'|'sub'|'and'|'comp'|'xor'|'or'|'jineg'|'jicarry'|'jipos'|'jizero'|'jmptosr'|'jump'
  |'load'|'xload'|'yload'|'xstore'|'ystore'|'loadmx'|'store'|'xcomp' | 'insert' | 'return'|'ycomp')
  ;
  
  
datafield 
  : NAME
  | DIGIT+
  ;

NAME : ('a'..'z' | 'A'..'Z') ('a'..'z' |'A'..'Z'| '0'	..'9' | '_' )*;

DIGIT : '0'.. '9';

/* 
 * Single line comments beginning with semicolon are supported
 */
COMMENT : ';' .* '\n'  {$channel=HIDDEN;} ;
WS : (' '|'\t'|'\r'|'\n')+  {$channel=HIDDEN;} ;