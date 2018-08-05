grammar FOOL;

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
  
prog   : exp SEMIC        #singleExp
       | let exp SEMIC    #letInExp
       ;

let    : LET (dec SEMIC)+ IN ;

vardec : type ID ;

varasm : vardec ASM exp ;

fun    : type ID LPAR ( vardec ( COMMA vardec)* )? RPAR (let)? exp ;

dec    : varasm  #varAssignment
       | fun     #funDeclaration
       ;
   
type   : INT  
       | BOOL 
       ;  
    
exp    : left=term ((PLUS | MINUS) right=exp)?
       ;
   
term   : left=factor ((TIMES | DIVISION) right=term)?
       ;
   
factor : left=value ((EQ|GREATER|LESS|GREATEREQUAL|LESSEQUAL|OR|AND) right=factor)?
       ;

value  : INTEGER          #intVal
       | (NOT)? ( TRUE | FALSE ) #boolVal
       | LPAR exp RPAR    #baseExp
       | IF cond=exp THEN CLPAR thenBranch=exp CRPAR ELSE CLPAR elseBranch=exp CRPAR  #ifExp
       | ID                                      #varExp
       | ID ( LPAR (exp (COMMA exp)* )? RPAR )   #funExp
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

//Numbers
fragment DIGIT : '0'..'9';    
INTEGER        : (MINUS)?DIGIT+;

//IDs
fragment CHAR  : 'a'..'z' |'A'..'Z' ;
ID             : CHAR (CHAR | DIGIT)* ;

//ESCAPED SEQUENCES
WS             : (' '|'\t'|'\n'|'\r')-> skip;
LINECOMMENTS    : '//' (~('\n'|'\r'))* -> skip;
BLOCKCOMMENTS   : '/*'( ~('/'|'*')|'/'~'*'|'*'~'/'|BLOCKCOMMENTS)* '*/' -> skip;
