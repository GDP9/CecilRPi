grammar Hello;

program : header instruction*;

instruction : labelfield? INSTRUCTIONFIELD datafield?;

INSTRUCTIONFIELD : ('stop'| 'insert' | 'load'| 'store'| 'add'| 'sub'| 'print'| 'printch');
datafield : DIGIT+ | NAME;
labelfield : '.' ('start' | NAME);

header 	: 'program' NAME+ 'author' NAME+ 'date' dateformat;

dateformat : DAY '/' MONTH '/' DIGIT DIGIT;
MONTH : ('0' DIGIT | '1' '1' | '1' |'0' | '1' '2'); 
DAY : ('0' DIGIT | '1' DIGIT | '2' DIGIT | '3' ('0' | '1'));

NAME : 'a'..'z' ('a'..'z' | '0'	..'9' | '_' )*;
DIGIT : '0'.. '9';

COMMENT : ';' .* '\n'  {skip();} ;
WS : (' '|'\t'|'\r'|'\n')+  {skip();} ;