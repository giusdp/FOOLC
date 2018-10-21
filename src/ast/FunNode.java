package ast;
import java.util.ArrayList;
import java.util.HashMap;

import type.ArrowType;
import type.ErrorType;
import type.Type;
import util.FOOLlib;
import util.FOOLlib.RuleName;
import util.STEntry;
import util.Environment;
import util.SemanticError;

public class FunNode implements Node {

	private String id;
	private Type returnType;
	private RuleName returningRule;
	private ArrayList<Node> parlist;
	private ArrayList<Node> declist; 
	private ArrayList<Node> expsBody;
	private ArrayList<Node> stmsBody;
	private ArrowType functionType;

	public FunNode (String i,
					Type t,
					RuleName rule,
					ArrayList<Node> parameters,
					ArrayList<Node> decls,
					ArrayList<Node> exps,
					ArrayList<Node> stms) {
		id=i;
		returnType=t;
		parlist = parameters;
		expsBody = exps;
		stmsBody = stms;
		declist = decls;
		returningRule = rule;
		
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
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//env.offset = -2;
		HashMap<String,STEntry> currentScope = env.getST().get(env.getNestLevel());
		STEntry entry = new STEntry(env.getNestLevel(),env.decOffset()); 
		//separo introducendo "entry"

		if ( currentScope.put(id,entry) != null )
			res.add(new SemanticError("Function "+ id +" already declared"));
		else{
			//creare una nuova hashmap per la symTable
			env.incNestLevel();
			HashMap<String,STEntry> nuovoScope = new HashMap<String,STEntry> ();
			env.getST().add(nuovoScope);

			ArrayList<Type> parTypes = new ArrayList<Type>();
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
				env.setOffset(-2);
				System.out.println("DEC SIZE > 0");
				//if there are children then check semantics for every child and save the results
				for(Node n : declist)
					res.addAll(n.checkSemantics(env));
			}

			// Check semantics for function statements and expressions.
			for(Node s : stmsBody) {
				res.addAll(s.checkSemantics(env));
			}
			for (Node funExp : expsBody) {
				res.addAll(funExp.checkSemantics(env));	
			}
			// Close scope.
			env.getST().remove(env.decNestLevel());

		}

		return res;
	}

	public String toPrint(String indent) {
		String parlstr="";
		
		for (Node par:parlist)
			parlstr+=par.toPrint(indent+"  ");
		
		String declstr="";
		if (declist!=null) 
			for (Node dec:declist)
				declstr+=dec.toPrint(indent+"  ");
		
		String expString="";
		if (!expsBody.isEmpty()) 
			for (Node e:expsBody)
				expString+=e.toPrint(indent+"  ");
		
		String stmsString="";
		if (!stmsBody.isEmpty()) 
			for (Node s:stmsBody)
				stmsString+=s.toPrint(indent+"  ");
		
		return indent + "Fun: "+ id + " of type " +functionType.toPrint("") +
				"\n" + parlstr + declstr + expString + stmsString;
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
		for(Node stm : stmsBody) {
			type = stm.typeCheck();
			if(type instanceof ErrorType) return type;
		}
		for (Node exp : expsBody) {
			type = exp.typeCheck();
			if(type instanceof ErrorType) return type;
		}
		
		// Controllo del returnNode (in realta' viene gia' fatto quando si controlla il corpo
		// Pero' ora ci serve proprio il tipo per vedere se e' subtype di return type
		
		Node finalNode = null;
		finalNode = (returningRule == RuleName.EXP)	? expsBody.get(expsBody.size() - 1)
													: stmsBody.get(stmsBody.size() - 1);
			
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

		String declCode="";
		if (declist!=null) 
			for (Node dec:declist)
				declCode+=dec.codeGeneration();
		
		String expsCode = "";
		for (Node exp : expsBody) {
			expsCode += exp.codeGeneration();
		}
		
		// TODO stms and exps codegen (careful with the order)

		String popDecl="";
		if (declist!=null) 
			for (Node dec:declist)
				popDecl+="pop\n";

		String popParl="";
		for (Node dec:parlist)
			popParl+="pop\n";

		String funLabel=FOOLlib.freshFunLabel(); 
		FOOLlib.putCode(funLabel+":\n"+
				"cfp\n"+ //setta $fp a $sp				
				"lra\n"+ //inserimento return address
				declCode+ //inserimento dichiarazioni locali
				expsCode+
				"srv\n"+ //pop del return value
				popDecl+
				"sra\n"+ // pop del return address
				"pop\n"+ // pop di AL
				popParl+
				"sfp\n"+  // setto $fp a valore del CL
				"lrv\n"+ // risultato della funzione sullo stack
				"lra\n"+"js\n" // salta a $ra
				);

		return /*"push "+ */ funLabel +"\n";
	}

}  