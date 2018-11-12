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

	private ArrayList<Node> contextBody;
	/* takes the list of declarations, the expressions and the statements*/

	public ProgLetInNode(ArrayList<Node> d, ArrayList<Node> fullBody) {
		this.declist = d;
		this.contextBody = fullBody;
	}
	public String toPrint(String indent) {

		return FOOLlib.printProgNode(indent, new ArrayList<ClassNode>(), declist, contextBody);
	}

	@Override
	public ArrayList<SemanticError> checkSemantics(Environment env) {
		env.incNestLevel();
		HashMap<String,STEntry> hm = new HashMap<>();
		env.getST().add(hm);

		//declare resulting list
		ArrayList<SemanticError> res = new ArrayList<>();

		env.setOffset(-1);
        env.setFunctionOffset(-1);

		//check semantics in the dec list
        res.addAll(FOOLlib.processCheckSemanticsDecs(this.declist, env));

		declist.forEach(d -> {
		    if (d instanceof VarNode) ((VarNode) d).updateEntryOffset(env.getFunctionOffset() + 1);
        });

		env.setOffset(env.getOffset() + env.getFunctionOffset() + 1);

        //check semantics in the exp body or stms body
        for (Node instr : this.contextBody) {
            res.addAll(instr.checkSemantics(env));
        }

		//clean the scope, we are leaving a let scope
		env.getST().remove(env.decNestLevel());

		//return the result
		return res;
	}

	public Type typeCheck () {
		Type type = new VoidType(); // Default value

		for (Node declaration : this.declist) {
			type = declaration.typeCheck();
			if (type instanceof ErrorType) return type;
		}
		for (Node instruction : this.contextBody) {
			type = instruction.typeCheck();
			if (type instanceof ErrorType) return type;
		}

		return type; // The type of the "let in" program is the type of the last instruction (the returned expression)
	}

	public String codeGeneration() {

		// TODO: more rigorous testing needed to ensure codeGen works.

		StringBuilder declCode = new StringBuilder();
        for (Node dec : declist){
            if (dec instanceof FunNode) declCode.append(dec.codeGeneration());
        }

        for (Node dec : declist){
            if (! (dec instanceof FunNode)) declCode.append(dec.codeGeneration());
        }

		StringBuilder bodyCode = new StringBuilder();
		for (Node stm : this.contextBody) {
			bodyCode.append(stm.codeGeneration());
		}

        return "## LET\n\n" + declCode.toString() + "\n## IN\n\n" + bodyCode.toString() + "halt\n" + FOOLlib.getCode();
	}

	public ArrayList<Node> getDeclist() {
		return declist;
	}

	public ArrayList<Node> getContextBody() {
		return contextBody;
	}
}