/**
 * CECIL assembly language grammar definition
 * 
 * Authors Shreeprabha Aggarwal, Carolina Ferreira 
 * Version 1.0
 * Date 19/10/2013
 */
grammar Cecil;

options {
    language = Java;
}

/* package and import declaration for parser */
@header { 
  package org.raspberrypi.cecil.grammar;
  import java.util.HashMap;
  import java.util.ArrayList;
}

/* package and import declaration for lexer */
@lexer::header {
  package org.raspberrypi.cecil.grammar;
}

/* Member function and field declaration */
@members { 
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
}

/**
 * Rules
 * TODO: getkey, wait, pause, retfint, swapax, swapay, swapxy, swapas, intenable, intdisable, nop, halt
 * Reserved Keywords: all-instructions
 */
program 
  : instruction*
  ;

instruction 
  : ('.' labelfield  
  { /* if label already does not exist or isn't start then add to the hash */
    if(labelfield.containsKey($labelfield.text)) throw new RecognitionException();
    else labelfield.put(($labelfield.text),pointer);
  }
  )? instructiondata
  
  
  ;  
  
labelfield 
  :  'start' | NAME
  ;

instructiondata 
  : (binaryinstruction datafield {
      /* if instruction is insert and data is integer then add value to memory */
        if(($binaryinstruction.text).equals("insert")) {
          if(($datafield.text).matches("^[0-9]+$")) {
            instructionfield.put(pointer, "insert");
            memorymodel.memory[pointer++] = Integer.parseInt($datafield.text);
          }
          else throw new RecognitionException();
        }
      /* else reference instruction */  
        else {
          instructionfield.put(pointer, $binaryinstruction.text);
          memorymodel.memory[pointer++] = CommandList.get($binaryinstruction.text); 
          if(($datafield.text).matches("^[0-9]+$"))
            memorymodel.memory[pointer++] = Integer.parseInt($datafield.text);
          else 
            datafield.put($datafield.text,pointer++);
         } 
     }) 
       
  | unaryinstruction 
      {
        instructionfield.put(pointer, $unaryinstruction.text);
        memorymodel.memory[pointer++] = CommandList.get($unaryinstruction.text);
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
 * Only single line comments beginning with semicolon are supported
 */
COMMENT : ';' .* '\n'  {$channel=HIDDEN;} ;
WS : (' '|'\t'|'\r'|'\n')+  {$channel=HIDDEN;} ;