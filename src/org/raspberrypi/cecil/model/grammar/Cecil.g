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
  import org.raspberrypi.cecil.model.grammar.AssignmentException;
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
}


@rulecatch {
  /**
  * Rulecatch implicitly invoked by the parser. The error is appended to the StreamOutputError list. 
  */
     catch (RecognitionException e) {  
             String msg = getErrorMessage(e, tokenNames);
             this.stream.getErrors().add(new OutputError(e.line, msg));    
           
             System.out.println(e.line + " : " + msg);  
     } 
}

/**
 * <p>CECIL sim40 LL(k) grammar</p>
 * <p>Lexer : Tokens (Reserved Keywords) => all-instructions</p>
 * <p>A valid program 
 *  <ul>
 *    <li>may begin with an optional '.start' label</li>
 *    <li>is constituted by multiple statements followed by optional assigments</li>
 *  </ul>
 * </p>
 */
program 
  : '.start'? mnemonicdata statement* assignment*
  ;

/**
 * <p>A valid statement 
 *  <ul>
 *    <li>may begin with an optional label</li>
 *    <li>is constituted by multiple mnemonicdata</li>
 *  </ul>
 * </p>
 */
statement 
  : ('.' labelfield  
  { /* filling labelfield hashmap */
    if(labelfield.containsKey($labelfield.text)) throw new RecognitionException();
    else labelfield.put(($labelfield.text),pointer);
  }
  )? mnemonicdata
  ;  

/**
 * <p>A labelfield is a valid Name which has to begin with ay alphabet followed by any number of alphanumeric characters </p>
 */
labelfield 
  :  NAME
  ;

/**
 * <p>A valid assignment 
 *  <ul>
 *    <li>may begin with an optional label</li>
 *    <li>is constituted by multiple 'insert' instructions followed by integer data values</li>
 *  </ul>
 * </p>
 */
assignment
  :('.' labelfield  
  { /* filling labelfield hashmap */
    if(labelfield.containsKey($labelfield.text)) throw new RecognitionException();
    else labelfield.put(($labelfield.text),pointer);
  }
  )? 'insert' value {
        instructionfield.put(pointer, "insert");
        sim40.memory[pointer++] = Integer.parseInt($value.text);
    
  }
  ;
 
/**
 * <p>A valid mnemonicdata is
 *  <ul>
 *   <li>a binary instruction followed by a datafield</li>
 *   <li> or a unary instruction</li>
 *  </ul>
 * </p>
 */  
mnemonicdata 
  : (binaryinstruction datafield {
      /* Refer to corresponding instruction and add the value to memory (from memory address)
       * Filling in instruction hashmap with binary instructions
       */  
          if(instructionList.instructionToMnemonic($binaryinstruction.text) == -1)
            throw new RecognitionException();
          
          else {
            instructionfield.put(pointer, $binaryinstruction.text);
            sim40.memory[pointer++] = instructionList.instructionToMnemonic($binaryinstruction.text); 
            datafield.put(pointer++,$datafield.text);
          }
     }) 
       
  | unaryinstruction 
      {/* Filling  instruction hashmap with unary instructions */
        instructionfield.put(pointer, $unaryinstruction.text);
        sim40.memory[pointer++] = instructionList.instructionToMnemonic($unaryinstruction.text);
      }
  ;
  
/**
 * <p>A Binary Instruction is followed by an instruction</p>
 */  
unaryinstruction
  : ('stop'| 'print'| 'printch' | 'printb' | 'cclear' | 'cset' | 'xdec' | 'xinc' | 'ydec' | 'yinc')
  ;
  
/**
 * <p>A Binary Instruction is followed by a datafield</p>
 */  
binaryinstruction
  : ('add' | 'sub' | 'and' | 'xor' | 'or' | 'comp' | 'jineg' | 'jicarry' | 'jipos' | 'jizero' | 'jump'
  | 'load' | 'xload' | 'yload' | 'xcomp' | 'ycomp')
  ;
  
 /**
  * <p>A datafield is a valid name that cannot hold an integer value</p>
  */ 
datafield 
  : NAME
  ;

/**
  * <p>A value is a non negative integer</p>
  */ 
value
  :DIGIT+
  ;
/**
 * <p> A name begins with any case alphabet followed by any number of alphanumeric characters</p>
 */
NAME : ('a'..'z' | 'A'..'Z') ('a'..'z' |'A'..'Z'| '0'	..'9')*;

/**
 * <p>A digit is a non-negaative integer</p>
 */
DIGIT : '0'.. '9';

/**
 * <p>Single line comments beginning with semicolon are supported</p>
 */
COMMENT : ';' .* '\n'  {$channel=HIDDEN;} ;

/**
 * <p>White spaces are ignored</p>
 */
WS : (' '|'\t'|'\r'|'\n')+  {$channel=HIDDEN;} ;