package ast;

import type.BoolType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.SemanticError;

import java.util.ArrayList;
import java.util.List;

public class IfStmsNode implements Node {

    private Node condition;
    private List<Node> thenBranch, elseBranch;

    public IfStmsNode(Node condition, List<Node> thenBranch, List<Node> elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder thenString = new StringBuilder();
        StringBuilder elseString = new StringBuilder();
        thenBranch.forEach(s -> thenString.append(s.toPrint(indent+"  ")));
        elseBranch.forEach(s -> elseString .append(s.toPrint(indent+"  ")));
        return indent + "IF Stms: " + condition.toPrint(indent+"  ") + thenString + elseString;

    }

    @Override
    public Type typeCheck() {
        ErrorType error = new ErrorType();

        Type condType = condition.typeCheck();

        if (condType instanceof ErrorType){
            return error;
        }

        if (!(FOOLlib.isSubtype(condType, new BoolType()))) {
            error.addErrorMessage("Non boolean condition in if.");
            return error;
        }

        Type t = null;
        Type e = null;
        for (Node s : thenBranch){
            t = s.typeCheck();
            if (t instanceof ErrorType) return t; // It will be void type
        }

        for (Node s : elseBranch){
            e = s.typeCheck();
            if (e instanceof ErrorType) return e;
        }

        if (t == null || e == null) { // Non può mai accadere per via della sintassi di FOOL
            // ma mi fa sentire meglio controllare
            error.addErrorMessage("Then or Else branch is missing.");
        }
        // Controllo il sotto tipaggio solo dell'ultima istruzione nel then e else branch
        // Che sarebbero le istruzioni di ritorno
        if (FOOLlib.isSubtype(t, e)) return e;
        if (FOOLlib.isSubtype(e, t)) return t;

        error.addErrorMessage("Incompatible types in then else branches.");
        return error;
    }

    @Override
    public String codeGeneration() {

        StringBuilder thenCodeString = new StringBuilder();
        StringBuilder elseCodeString = new StringBuilder();

        thenBranch.forEach(s -> thenCodeString.append(s.codeGeneration()));
        elseBranch.forEach(s -> elseCodeString.append(s.codeGeneration()));

        String l1 = FOOLlib.freshLabel();
        String l2 = FOOLlib.freshLabel();
        return condition.codeGeneration() + "push 1\n" + "beq " + l1 + "\n" + elseCodeString +
                "b " + l2 + "\n" + l1 + ":\n" + thenCodeString + l2 + ":\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        //check semantics in the condition
        ArrayList<SemanticError> res = new ArrayList<>(condition.checkSemantics(env));

        //check semantics in then and in else exp
        thenBranch.forEach(s -> res.addAll(s.checkSemantics(env)));
        elseBranch.forEach(s -> res.addAll(s.checkSemantics(env)));

        return res;
    }
}
