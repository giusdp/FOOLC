package ast;

import java.util.ArrayList;

import type.ClassType;
import type.ErrorType;
import type.Type;
import type.BoolType;
import util.Environment;
import util.SemanticError;
import util.FOOLlib;

public class IfNode implements Node {

    private Node cond;
    private Node th;
    private Node el;

    public IfNode(Node c, Node t, Node e) {

        cond = c;
        th = t;
        el = e;
    }

    public String toPrint(String indent) {
        return indent + "If\n" + cond.toPrint(indent + "  ") + th.toPrint(indent + "  ") + el.toPrint(indent + "  ");
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create the result
        ArrayList<SemanticError> res = new ArrayList<>();

        //check semantics in the condition
        res.addAll(cond.checkSemantics(env));

        //check semantics in then and in else exp

        res.addAll(th.checkSemantics(env));
        res.addAll(el.checkSemantics(env));

        return res;
    }


    public Type typeCheck() {

        ErrorType error = new ErrorType();

        Type condType = cond.typeCheck();

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
        else if (FOOLlib.isSubtype(e, t))
            return t;

        // Controllo di classe padre comune
        if (t instanceof ClassType && e instanceof ClassType){
            ClassNode thenClass = Environment.getClassMap().get(((ClassType) t).getId());
            ClassNode elseClass = Environment.getClassMap().get(((ClassType) e).getId());

            ClassNode thenParent = thenClass.getSuperClass();
            ClassNode elseParent = elseClass.getSuperClass();

            // Se hanno un padre in comune allora tutto bene
            while (thenParent != null){

                if (FOOLlib.isSubtype(e, thenParent.getClassType())) return thenParent.getClassType();

                thenParent = thenParent.getSuperClass();
            }

            while (elseParent != null){

                if (FOOLlib.isSubtype(t, elseParent.getClassType())) return elseParent.getClassType();

                elseParent = elseParent.getSuperClass();
            }
        }

        // TODO Se t ed e sono di tipo classe, si deve controllare se abbiano una classe base in comune

        error.addErrorMessage("Incompatible types in then else branches.");
        return error;
    }

    public String codeGeneration() {
        String l1 = FOOLlib.freshLabel();
        String l2 = FOOLlib.freshLabel();
        return cond.codeGeneration() + "push 1\n" + "beq " + l1 + "\n" + el.codeGeneration() +
                "b " + l2 + "\n" + l1 + ":\n" + th.codeGeneration() + l2 + ":\n";
    }

}  