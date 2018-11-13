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

    Node condition;
    List<Node> thenBranch, elseBranch;

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
        Type t = th.typeCheck();
        Type e = el.typeCheck();

        if (t instanceof ErrorType) return t;
        if (e instanceof ErrorType) return e;

        if (FOOLlib.isSubtype(t, e))
            return e;
        if (FOOLlib.isSubtype(e, t))
            return t;

        error.addErrorMessage("Incompatible types in then else branches.");
        return error;
    }

    @Override
    public String codeGeneration() {
        return null;
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
