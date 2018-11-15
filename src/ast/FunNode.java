package ast;
import type.ArrowType;
import type.ErrorType;
import type.Type;
import type.VoidType;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

public class FunNode implements Node {

	private String id;
	private Type returnType;
	private ArrayList<Node> parlist;
	private ArrayList<Node> declist; 
	private ArrayList<Node> body;
	private ArrowType functionType;
	private STEntry entry;
	
	private boolean isMethod = false;

	public FunNode (String i,
					Type t,
					ArrayList<Node> parameters,
					ArrayList<Node> decls,
					ArrayList<Node> body) {
		id=i;
		returnType=t;
		parlist = parameters;
		this.body = body;
		declist = decls;
		
		ArrayList<Type> parTypes = new ArrayList<>();
		for (Node par : parameters) {
			ParNode p = (ParNode) par;
			parTypes.add(p.getType());
		}
		
		functionType = new ArrowType(parTypes, returnType);
		
	}

	public String getId() {
		return id;
	}

	public Type getType() {
		return functionType;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {

		//create result list
		ArrayList<SemanticError> res = new ArrayList<>();

		HashMap<String,STEntry> currentScope = env.getST().get(env.getNestLevel());
		
		// Problema con il decOffset qui. Se questo è un metodo di una classe, l'offset dovrebbe
		// essere inerente alla classe e non all'offset di tutto l'environment. 
		// Quindi è stato introdotto methodOffset
		if (isMethod)  entry = new STEntry(env.getNestLevel(), env.decMethodOffset());
		else entry = new STEntry(env.getNestLevel(), env.decFunctionffset());

		STEntry oldEntry = currentScope.put(id,entry);
		if ( oldEntry != null && !oldEntry.isToBeEvaluated())
			res.add(new SemanticError("Error for " 
					+ (isMethod ? "method " : "function ")+ id
					+ ". ID " +id + " already in use."));
		else{
			//creare una nuova hashmap per la symTable
			env.incNestLevel();
			HashMap<String,STEntry> nuovoScope = new HashMap<>();
			env.getST().add(nuovoScope);

			ArrayList<Type> parTypes = new ArrayList<>();
			int paroffset=1;

			//check args
			for(Node a : parlist){
				ParNode arg = (ParNode) a;
				parTypes.add(arg.getType());
				STEntry parSTEntry = new STEntry(env.getNestLevel(),arg.getType(),paroffset++);
				if (nuovoScope.put(arg.getId(), parSTEntry) != null) {
					res.add(new SemanticError("Parameter "+arg.getId()+" already declared"));
				}
			}

			//set func type
			entry.setType( new ArrowType(parTypes, returnType) );

			//check semantics in the dec list
			if(declist.size() > 0){
                env.setOffset(-2); // reset the offset to the starting value because we are in a new scope
                //if there are children then check semantics for every child and save the results
//				FOOLlib.processCheckSemanticsDecs(declist, env);
                for (Node arg : declist)
                    res.addAll(arg.checkSemantics(env));
			}

			// Check semantics for function statements and expressions.
			for(Node instr : body) {
				res.addAll(instr.checkSemantics(env));
			}
			// Close scope.
			env.getST().remove(env.decNestLevel());

		}

		return res;
	}

	public String toPrint(String indent) {
		StringBuilder parlstr= new StringBuilder();
		
		for (Node par:parlist)
			parlstr.append(par.toPrint(indent + "  "));
		
		StringBuilder declstr= new StringBuilder();
		if (declist!=null) 
			for (Node dec:declist)
				declstr.append(dec.toPrint(indent + "  "));
		
		StringBuilder instrString= new StringBuilder();
		if (!body.isEmpty()) 
			for (Node s:body)
				instrString.append(s.toPrint(indent + "  "));
		
		return indent + "Fun: "+ id + " of type " +functionType.toPrint("") +
				"\n" + parlstr.toString() + declstr.toString() + instrString.toString();
	}

	public Type typeCheck () {
		if (declist!=null) {
			for (Node dec:declist) {
				Type type = dec.typeCheck();
				if (type instanceof ErrorType) return type;
			}
		}
		
		// Dobbiamo controllare che non ci siano errori di tipo anche per tutte
		// le istruzioni nel corpo della funzione
		Type type;
		for (Node exp : body) {
			type = exp.typeCheck();
			if(type instanceof ErrorType) return type;
		}
		
		// Controllo del returnNode in realta' viene gia' fatto quando si controlla il corpo
		// Pero' ora ci serve proprio il tipo per vedere se e' subtype di return type
		
		Node finalNode = body.get(body.size() - 1);
			
		Type returnNodeType = finalNode.typeCheck();
		/* if(returnNodeType instanceof ErrorType) return returnNodeType; */
		// Non serve vedere se e' instanceof ErrorType perche' se cosi' fosse sarebbe gia' uscito prima al 
		// controllo del corpo
		
		if ( !(FOOLlib.isSubtype(returnNodeType, returnType)) ){
			ErrorType error = new ErrorType();
			error.addErrorMessage("Function " + id + " must return: " + returnType.toPrint("") +
					   "Actually returned: " + returnNodeType.toPrint(""));
			return error;
		}  
		
		
		return returnType;
	}

	public String codeGeneration() {

		StringBuilder declCode= new StringBuilder();
        StringBuilder popDecl= new StringBuilder();

        for (Node dec:declist){
            declCode.append(dec.codeGeneration());
            popDecl.append("pop\n");
        }

        StringBuilder bodyCodeString = new StringBuilder();
        for (Node exp : body) {
            bodyCodeString.append(exp.codeGeneration());
        }

		StringBuilder popParl= new StringBuilder();
		for (Node dec:parlist)
			popParl.append("pop\n");

		StringBuilder srvCalls = new StringBuilder();
        if (!(returnType instanceof VoidType))
            srvCalls.append("srv\n"); // 1 di default ci deve essere a meno che il return della funzione sia void

        // Add many pop as there are fun (or methods) invocations unless they return void
         // pop because the only value to save is from the last call invocation which is saved by the first srv
        String cmd = "pop\n";
        for (int i = 0; i < body.size() -1 ;++i){
            Node n = body.get(i);
            if (n instanceof CallNode){
                CallNode c = (CallNode) n;
                ArrowType rt = (ArrowType) c.getEntry().getType();
                if (!(rt.getReturn() instanceof VoidType))
                    srvCalls.append(cmd);
            }
            else if (n instanceof MethodCallNode){
                MethodCallNode c = (MethodCallNode) n;
                ArrowType rt = (ArrowType) c.getMethodType();
                if (!(rt.getReturn() instanceof VoidType)) {
                    srvCalls.append(cmd);
                }
            }
            // The only other cases it would be void type
            else if (! (n instanceof StmAsmNode) && !(n instanceof IfStmsNode)) {
                srvCalls.append(cmd);
            }
        }

		String funLabel=FOOLlib.freshFunLabel(); 
		FOOLlib.putCode(funLabel+":\n"+
				"cfp\n"+ //setta $fp a $sp				
				"lra\n"+ //inserimento return address
				declCode+ //inserimento dichiarazioni locali
				bodyCodeString +
                srvCalls + //pop del return value // ci dovrebbero essere tanti srv quante sono le chiamate
				popDecl+
				"sra\n"+ // pop del return address
				"pop\n"+ // pop di AL
				popParl+
				"sfp\n"+  // setto $fp a valore del CL
                ((returnType instanceof VoidType) ? "" :"lrv\n")+ // risultato della funzione sullo stack
				"lra\n"+"js\n" // salta a $ra
				);

		return "push " + funLabel +"\n";
	}

	public boolean isMethod() {
		return isMethod;
	}

	public void setMethod(boolean isMethod) {
		this.isMethod = isMethod;
	}
	

	public STEntry getEntry(){
	    return entry;
    }

}  