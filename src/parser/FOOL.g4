grammar FOOL;

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

prog   : (print | exp) SEMIC                                        #singleExp
       | let ( exp SEMIC | print SEMIC | stms )+                    #letInExp
       | (classdec)+ ( let ( exp SEMIC | print SEMIC | stms )+ )?   #classExp
       ;

classdec  : CLASS ID (EXTENDS ID)? (LPAR vardec (COMMA vardec)* RPAR)? (CLPAR (fun)+ CRPAR)? SEMIC;

let    : LET (dec)+ IN ;

letVar : LET (varasm)+ IN ;

vardec : type ID ;

varasm : vardec ASM exp SEMIC ;

fun    : type ID LPAR ( vardec ( COMMA vardec)* )? RPAR (letVar)? CLPAR (exp SEMIC|print SEMIC|stms)+ CRPAR ;

dec    : varasm  #varAssignment
       | fun     #funDeclaration
       ;
   
type   : INT  
       | BOOL 
       | VOID
       | ID
       ;
       
print  : PRINT LPAR (ID | exp) RPAR
       ;
    
exp    : left=term ((PLUS | MINUS) right=exp)?
       ;
   
term   : left=factor ((TIMES | DIVISION) right=term)?
       ;
   
factor : left=value ((EQ|GREATER|LESS|GREATEREQUAL|LESSEQUAL|OR|AND) right=factor)?
       ;

stm    : ID ASM body=exp																#AsmStm
       | IF LPAR cond=exp RPAR THEN CLPAR thenBranch=stms CRPAR ELSE CLPAR elseBranch=stms CRPAR  #ifStm ;
       // if exp then { stms } else { stms } (condizionale) ;

stms   : ( stm SEMIC )+ ;

value  : (MINUS)? INTEGER          #intVal
       | (NOT)? ( TRUE | FALSE ) #boolVal
       | LPAR exp RPAR    #baseExp
       | IF LPAR cond=exp RPAR THEN CLPAR thenBranch=exp SEMIC CRPAR ELSE CLPAR elseBranch=exp SEMIC CRPAR  #ifExp
       | (MINUS|NOT)? ID                                    #varExp
       | ID ( LPAR (exp (COMMA exp)* )? RPAR )              #funExp
       | ID DOT ID ( LPAR (exp (COMMA exp)* )? RPAR )	    #methodExp
       | NEW ID (LPAR (exp (COMMA exp)*)? RPAR)   		    #newExp
       | NULL                                               #nullExp
       ;

/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
SEMIC  : ';' ;
COLON  : ':' ;
COMMA  : ',' ;
EQ     : '==' ;
ASM    : '=' ;
PLUS   : '+' ;
TIMES  : '*' ;
TRUE   : 'true' ;
FALSE  : 'false' ;
LPAR   : '(' ;
RPAR   : ')' ;
CLPAR  : '{' ;
CRPAR  : '}' ;
IF     : 'if' ;
THEN   : 'then' ;
ELSE   : 'else' ;
PRINT  : 'print' ; 
LET    : 'let' ;
IN     : 'in' ;
VAR    : 'var' ;
FUN    : 'fun' ;
INT    : 'int' ;
BOOL   : 'bool' ;

// AGGIUNTI PER IL PROGETTO
MINUS         : '-';
DIVISION      : '/';
GREATER       : '>';
LESS          : '<';
GREATEREQUAL  : '>=';
LESSEQUAL     : '<=';
OR            : '||';
AND           : '&&';
NOT           : 'not';
END           : 'end';

VOID    : 'void';

CLASS   : 'class' ;
THIS    : 'this' ;
NEW     : 'new' ;
DOT     : '.' ;
EXTENDS : 'extends';
NULL    : 'null';


//Numbers
fragment DIGIT : '0'..'9';    
INTEGER        : DIGIT+;

//IDs
fragment CHAR  : 'a'..'z' |'A'..'Z' ;
ID             : CHAR (CHAR | DIGIT)* ;

//ESCAPED SEQUENCES
WS             : (' '|'\t'|'\n'|'\r')-> skip;
LINECOMMENTS    : '//' (~('\n'|'\r'))* -> skip;
BLOCKCOMMENTS   : '/*'( ~('/'|'*')|'/'~'*'|'*'~'/'|BLOCKCOMMENTS)* '*/' -> skip;
