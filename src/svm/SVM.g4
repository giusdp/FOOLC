grammar SVM;

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
assembly: (instruction)*;
instruction: 
      PUSH n=NUMBER 			#pushNumber
	  | PUSH l=LABEL     		#pushLabel
	  | POP						#pop
	  | ADD						#add
	  | SUB						#sub
	  | MULT					#mult
	  | DIV						#div
	  | STOREW					#storeW
	  | LOADW					#loadW
	  | l=LABEL COL				#label
	  | l=LABEL                 #dispatchLabel
	  | BRANCH l=LABEL			#branch
	  | BRANCHEQ l=LABEL		#branchEqual
	  | BRANCHLESSEQ l=LABEL	#branchLessEqual
	  | BRANCHLESS l=LABEL		#branchLess
	  | BRANCHGREATEREQ l=LABEL	#branchGreaterEqual
	  | BRANCHGREATER l=LABEL	#branchGreater
	  | AND l=LABEL				#branchAnd
	  | OR l=LABEL				#branchOr
	  | JS						#js
	  | LOADRA          		#loadRA
	  | STORERA         		#storeRa
	  | LOADRV      			#loadRV
	  | STORERV        			#storeRV
	  | LOADFP          		#loadFP
	  | STOREFP        			#storeFP
	  | COPYFP        			#copyFP
	  | LOADHP        			#loadHP
	  | STOREHP         		#storeHP
	  | PRINT           		#print
	  | HALT           			#halt
	  | NEW 					#new
	  | LOADMETHOD				#loadMethod
	  | DUPLICATETOP			#duplicateTop
	  ;
 	 
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
 
PUSH : 'push' ; 	// pushes constant in the stack
POP	 : 'pop' ; 	// pops from stack
ADD	 : 'add' ;  	// add two values from the stack
SUB	 : 'sub' ;	// add two values from the stack
MULT	 : 'mult' ;  	// add two values from the stack
DIV	 : 'div' ;	// add two values from the stack
STOREW	 : 'sw' ; 	// store in the memory cell pointed by top the value next
LOADW	 : 'lw' ;	// load a value from the memory cell pointed by top
BRANCH	 : 'b' ;	// jump to label
BRANCHEQ : 'beq' ;	// jump to label if top == next
BRANCHLESSEQ:'bleq' ;	// jump to label if top <= next
BRANCHLESS:'bl' ;	// jump to label if top < next
BRANCHGREATEREQ:'bgeq' ;	// jump to label if top >= next
BRANCHGREATER:'bg' ;	// jump to label if top > next
AND: 'and' ; // jump to label if top && next
OR: 'or' ;  // jump to label if top or next
JS	 : 'js' ;	// jump to instruction pointed by top of stack and store next instruction in ra
LOADRA	 : 'lra' ;	// load from ra
STORERA  : 'sra' ;	// store top into ra	 
LOADRV	 : 'lrv' ;	// load from rv
STORERV  : 'srv' ;	// store top into rv	 
LOADFP	 : 'lfp' ;	// load frame pointer in the stack
STOREFP	 : 'sfp' ;	// store top into frame pointer
COPYFP   : 'cfp' ;      // copy stack pointer into frame pointer
LOADHP	 : 'lhp' ;	// load heap pointer in the stack
STOREHP	 : 'shp' ;	// store top into heap pointer
PRINT	 : 'print' ;	// print top of stack
HALT	 : 'halt' ;	// stop execution

// Object stuff
NEW      : 'new' ;   // alloca un'area di memoria nello heap
// Methods stuff
LOADMETHOD: 'lm' ; // Carica sullo stack l'indirizzo del primo metodo 
					// della classe, il cui indirizzo è al top dello stack
DUPLICATETOP: 'cts'; // Fa una copia del top dello stack e lo inserisce. Serve per accedere alla dispatch table
						// perché per accedere ad un metodo di un oggetto si usa l'indirizzo nella dispatch table
						// e c'è bisogno di duplicarlo cosi poi si può usare per continuare ad accedere all'oggetto (e.g. per usare i campi nel metodo)
         

COL	 : ':' ;
LABEL	 : ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9' | '_')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;
LINECOMENTS    : '#' (~('\n'|'\r'))* -> skip;

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);



