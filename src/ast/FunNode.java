package ast;
import java.util.ArrayList;
import java.util.HashMap;

import type.ArrowType;
import type.Type;
import util.FOOLlib;
import util.STEntry;
import util.Environment;
import util.SemanticError;

public class FunNode implements Node {

	private String id;
	private Type type; 
	private ArrayList<Node> parlist = new ArrayList<>(); 
	private ArrayList<Node> declist; 
	private Node expBody;
	private ArrayList<Node> stmsBody;

	public FunNode (String i, Type t) {
		id=i;
		type=t;
	}

	public void addDecExpBody (ArrayList<Node> d, Node b) {
		declist=d;
		expBody=b;
	}
	
	public void addDecStmsBody (ArrayList<Node> d, ArrayList<Node> b) {
		declist=d;
		stmsBody=b;
	}

	public String getId() {
		return id;
	}

	public Type getType() {
		return type;
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {

		//create result list
		ArrayList<SemanticError> res = new ArrayList<SemanticError>();

		//env.offset = -2;
		HashMap<String,STEntry> currentScope = env.getST().get(env.getNestLevel());
		STEntry entry = new STEntry(env.getNestLevel(),env.decOffset()); 
		//separo introducendo "entry"

		//System.out.println("FUN: " + id + " STENTRY: " + entry.getNestLevel() + 
			//	" offset: " + entry.getOffset());

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
			entry.setType( new ArrowType(parTypes, type) );

			//check semantics in the dec list
			if(declist.size() > 0){
				env.setOffset(-2);
				System.out.println("DEC SIZE > 0");
				//if there are children then check semantics for every child and save the results
				for(Node n : declist)
					res.addAll(n.checkSemantics(env));
			}

			//check body
			if (expBody == null) { // If its stms
				for(Node s : stmsBody) {
					//System.out.println(s.toPrint(""));
					res.addAll(s.checkSemantics(env));
				}
			}
			else {
				res.addAll(expBody.checkSemantics(env));	
			}
			//close scope
			env.getST().remove(env.decNestLevel());

		}

		return res;
	}

	public void addPar (Node p) {
		parlist.add(p);
	}  

	public String toPrint(String indent) {
		String parlstr="";
		for (Node par:parlist)
			parlstr+=par.toPrint(indent+"  ");
		String declstr="";
		if (declist!=null) 
			for (Node dec:declist)
				declstr+=dec.toPrint(indent+"  ");
		return indent+"Fun:" + id +"\n"
		+type.toPrint(indent+"  ")
		+parlstr
		+declstr
		+expBody.toPrint(indent+"  ") ; 
	}

	//valore di ritorno non utilizzato
	public Type typeCheck () {
		if (declist!=null) 
			for (Node dec:declist)
				dec.typeCheck();
		if ( !(FOOLlib.isSubtype(expBody.typeCheck(),type)) ){
			System.out.println("Wrong return type for function "+id);
			System.exit(0);
		}  
		return null;
	}

	public String codeGeneration() {

		String declCode="";
		if (declist!=null) for (Node dec:declist)
			declCode+=dec.codeGeneration();

		String popDecl="";
		if (declist!=null) for (Node dec:declist)
			popDecl+="pop\n";

		String popParl="";
		for (Node dec:parlist)
			popParl+="pop\n";

		String funl=FOOLlib.freshFunLabel(); 
		FOOLlib.putCode(funl+":\n"+
				"cfp\n"+ //setta $fp a $sp				
				"lra\n"+ //inserimento return address
				declCode+ //inserimento dichiarazioni locali
				expBody.codeGeneration()+
				"srv\n"+ //pop del return value
				popDecl+
				"sra\n"+ // pop del return address
				"pop\n"+ // pop di AL
				popParl+
				"sfp\n"+  // setto $fp a valore del CL
				"lrv\n"+ // risultato della funzione sullo stack
				"lra\n"+"js\n" // salta a $ra
				);

		return "push "+ funl +"\n";
	}

}  