package ast;
import java.util.ArrayList;
import java.util.HashMap;

import type.ErrorType;
import type.Type;
import type.VoidType;
import util.FOOLlib;
import util.Environment;
import util.STEntry;
import util.SemanticError;

/* Class representing a Let in instruction node */
public class ProgLetInNode implements Node {

	private ArrayList<Node> declist;
	private ArrayList<Node> exps; // E' null se il corpo sono statements
	private ArrayList<Node> statements; // Questa è la lista di statements nel caso in cui
	// il corpo sono statements. 
	
	private ArrayList<Node> fullBody = null;
	
	/* takes the list of declarations, the expressions and the statements*/
	public ProgLetInNode(ArrayList<Node> d, ArrayList<Node> exps, ArrayList<Node> stms, ArrayList<Node> fullBody) {
		this.declist = d;
		this.exps = exps;
		this.statements= stms;
		this.fullBody = fullBody;
	}

	public String toPrint(String indent) {

		// TODO: bodyString can replace expString and stmsString:
		// prints the body in the correct order, not sorted by exp/stms.
		String bodyString = "";
		for (Node stm : fullBody)
			bodyString += stm.toPrint(indent + "    ");
//		return bodyString;
		
		String declString="";
		for (Node dec:declist)
			declString+=dec.toPrint(indent+"    ");
		
//		String expString="";
//		for (Node e : exps) {
//			expString += e.toPrint(indent + "    ");
//		}
//
//		String stmString="";
//		for (Node stm: statements)
//			stmString+=stm.toPrint(indent+"    ");
		
		String let = "Let Declarations\n", in = "IN\n";
		
//		String ex = " Expressions\n  ";
//		if (expString.equals("")) ex = "";
//		
//		String st = " Statements\n  ";
//		if (stmString.equals("")) st = "";
		
		return indent+"ProgLetIn\n  " + let 
			   + declString
			   + "  " + in + "  "
			   + bodyString; 
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		env.incNestLevel();
		HashMap<String,STEntry> hm = new HashMap<String,STEntry> ();
		env.getST().add(hm);
		//System.out.println("Lista di Hashmaps size entrando:" + env.getST().size());

		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		env.setOffset(-2); // Code generation stuff


		//check semantics in the dec list
		//if there are children then check semantics for every child and save the results
		for(Node n : declist) {
			res.addAll(n.checkSemantics(env));
		}
		
		//check semantics in the exp body or stms body
		for (Node e : exps) {
			res.addAll(e.checkSemantics(env));
		}
		
		for(Node s : statements)
			res.addAll(s.checkSemantics(env));
		
		//clean the scope, we are leaving a let scope
		env.getST().remove(env.decNestLevel());

		//System.out.println("Lista di Hashmaps size uscendo:" + env.getST().size());


		//return the result
		return res;
	}

	public Type typeCheck () {
		Type type;
		
		for (Node dec:declist) {
			type = dec.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		for (Node e : exps) {
			type = e.typeCheck();
			if (type instanceof ErrorType) return type;			
		}
		for(Node s : statements) {
			type = s.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		
		return new VoidType();
	}

	public String codeGeneration() {
		
		// TODO: Edit the below to incorporate this.fullBody.
		// This will generate ccode according to correct exp/stm order.
		String declCode = "";
		for (Node dec:declist) {
			declCode += dec.codeGeneration();
		}
		
		String expsCode = "";
		for (Node e : exps) {
			expsCode += e.codeGeneration();
		}

		String stmsCode = "";
		for (Node s : statements) {
			stmsCode += s.codeGeneration();
		}
		return  declCode + expsCode + stmsCode + "halt\n" + FOOLlib.getCode();
	} 



}  