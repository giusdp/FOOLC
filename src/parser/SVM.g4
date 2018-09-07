grammar SVM;

@header {
import java.util.HashMap;
import parser.ExecuteVM;
import java.util.ArrayList;
}

@lexer::members {
//public int lexicalErrors=0;
 public ArrayList<String> errors = new ArrayList<>();
}

@parser::members {
      
   	private ArrayList<Integer> code = new ArrayList<Integer>();
    private HashMap<String,Integer> labelAdd = new HashMap<String,Integer>();
    private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();
        
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
  
assembly: 
    (	PUSH n=NUMBER		{	code.add(PUSH); 
			          			code.add(Integer.parseInt($n.text));}
	  |	PUSH l=LABEL 		{	code.add(PUSH);
	  							labelRef.put(code.size(), $l.text);
	  							code.add(0);} 	

	  | POP					{	code.add(POP);}	
	  | ADD			    	{	code.add(ADD);}
	  | SUB		    		{	code.add(SUB);}
	  | MULT	    		{	code.add(MULT);}
	  | DIV					{	code.add(DIV);}
	  | STOREW				{	code.add(STOREW);}
	  | LOADW           	{	code.add(LOADW);}
	  | l=LABEL COL     	{  	labelAdd.put($l.text,code.size());}
	  | BRANCH l=LABEL  	{	code.add(BRANCH);
	  							labelRef.put(code.size(),$l.text);
                       			code.add(0);}
	  | BRANCHEQ l=LABEL 	{	code.add(BRANCHEQ);
		  						labelRef.put(code.size(),$l.text);
		  						code.add(0);}
	  | BRANCHLESSEQ l=LABEL {	code.add(BRANCHLESSEQ);
	  							labelRef.put(code.size(),$l.text);
	  							code.add(0);}
	  /* | BRANCHGREATEQ l=LABEL {	code.add(BRANCHGREATEQ);
	  							labelRef.put(code.size(),$l.text);
	  							code.add(0);}
	  | BRANCHLESS l=LABEL {	code.add(BRANCHLESS);
	  							labelRef.put(code.size(),$l.text);
	  							code.add(0);}
	  | BRANCHGREAT l=LABEL {	code.add(BRANCHGREAT);
			  					labelRef.put(code.size(),$l.text);
			  					code.add(0);} */
	  | JS 					{	code.add(JS);}		     
	  | LOADRA 				{	code.add(LOADRA);}    
	  | STORERA 			{	code.add(STORERA);}  
	  | LOADRV 				{	code.add(LOADRV);}   
	  | STORERV 			{	code.add(STORERV);}  
	  | LOADFP 				{	code.add(LOADFP);}   
	  | STOREFP  			{	code.add(STOREFP);}  
	  | COPYFP 				{	code.add(COPYFP);}   
	  | LOADHP 				{	code.add(LOADHP);}
	  | STOREHP 			{	code.add(STOREHP);}
	  | PRINT 				{	code.addPRINT);}
	  | HALT 				{	code.add(HALT);}

	  // AGGIUNTI PER IL PROGETTO
	  | NEW 				{ 	code.add(NEW);	}
	  )* { for (Integer refAdd: labelRef.keySet()) {
	              code[refAdd]=labelAdd.get(labelRef.get(refAdd));
		     } 
		   } ;
 	 
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

// AGGIUNTI PER IL PROGETTO
BRANCHGREATEQ:'bgeq' ; // jump to label if top >= next
BRANCHLESS:'bl' ; // jump to label if top < next
BRANCHGREAT:'bg' ; // jump to label if top > next

// -------------

PUSH 		: 'push' ; 	// pushes constant in the stack
POP	 		: 'pop' ; 	// pops from stack
ADD	 		: 'add' ;  	// add two values from the stack
SUB	 		: 'sub' ;	// add two values from the stack
MULT	 	: 'mult' ;  	// add two values from the stack
DIV	 		: 'div' ;	// add two values from the stack
STOREW		: 'sw' ; 	// store in the memory cell pointed by top the value next
LOADW	 	: 'lw' ;	// load a value from the memory cell pointed by top
BRANCH	 	: 'b' ;	// jump to label
BRANCHEQ 	: 'beq' ;	// jump to label if top == next
BRANCHLESSEQ:'bleq' ;	// jump to label if top <= next
JS	 		: 'js' ;	// jump to instruction pointed by top of stack and store next instruction in ra
LOADRA	 	: 'lra' ;	// load from ra
STORERA  	: 'sra' ;	// store top into ra	 
LOADRV	 	: 'lrv' ;	// load from rv
STORERV  	: 'srv' ;	// store top into rv	 
LOADFP	 	: 'lfp' ;	// load frame pointer in the stack
STOREFP	 	: 'sfp' ;	// store top into frame pointer
COPYFP   	: 'cfp' ;      // copy stack pointer into frame pointer
LOADHP	 	: 'lhp' ;	// load heap pointer in the stack
STOREHP	 	: 'shp' ;	// store top into heap pointer
PRINT	 	: 'print' ;	// print top of stack
HALT	 	: 'halt' ;	// stop execution

// AGGIUNTI PER IL PROGETTO
NEW 		: 'new'	;

COL	 		: ':' ;
LABEL	 	: ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 	: '0' | ('-')?(('1'..'9')('0'..'9')*) ;

WHITESP  	: ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);

ERR 		: . { errors.add("Invalid char: " + getText());} -> channel(HIDDEN);

/*ERR   		 : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); */
