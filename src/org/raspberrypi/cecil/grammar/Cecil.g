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
}

/* package and import declaration for lexer */
@lexer::header {
  package org.raspberrypi.cecil.grammar;
}

/* Member function and field declaration */
@members { 
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
        static String parserError = "";
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
    if(labelfield.containsKey($labelfield.text)) parserError = "label already exists!";
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
        if(($binaryinstruction.text).equals("insert") && ($datafield.text).matches("^[0-9]+$"))
          memory[pointer++] = Integer.parseInt($datafield.text);
      /* else reference instruction */  
        else {
          memory[pointer++] = CommandList.get($binaryinstruction.text); 
          if(($datafield.text).matches("^[0-9]+$"))
            memory[pointer++] = Integer.parseInt($datafield.text);
          else 
            datafield.put($datafield.text,pointer++);
         } 
     }) 
       
  | unaryinstruction {memory[pointer++] = CommandList.get($unaryinstruction.text);}
  ;

unaryinstruction
  : ('stop'| 'print'| 'printch'|'printb'|'printd'|'cclear'|'cset'|'lshift'|'rshift'|'pull'|'return'
  |'xdec'|'xinc'|'xpull'|'xpush'|'ydec'|'yinc'|'ypull'|'ypush')
  ;
  
binaryinstruction
  : ('add'|'sub'|'and'|'comp'|'eor'|'or'|'jineg'|'jicarry'|'jipos'|'jizero'|'jmptosr'|'jump'
  |'load'|'xload'|'yload'|'xstore'|'ystore'|'loadmx'|'push'|'store'|'xcomp' | 'insert')
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