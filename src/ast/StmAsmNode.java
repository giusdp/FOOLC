package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ClassType;
import type.ErrorType;
import type.Type;
import type.VoidType;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class StmAsmNode implements Node {

    private Node asmBody;
    private Type asmType;
    private String variableID;

    private STEntry variableEntry;
    private int nestinglevel;

    StmAsmNode(String variableID, Node asmBody) {
        this.variableID = variableID;
        this.asmBody = asmBody;
        // settata in CheckSemantics():
        this.asmType = null;
    }

    @Override
    public String toPrint(String indent) {

        // TODO: Double check formatting of statement print.
        String bodyString = this.asmBody.toPrint("");
        return indent + "Assign:" + this.variableID + " = " + bodyString;
    }

    @Override
    public Type typeCheck() {

        Type bodyType = asmBody.typeCheck();

        if (bodyType instanceof ErrorType) {
            return bodyType;
        }

        // Verifica: type(body) <: type(var).
        if (!FOOLlib.isSubtype(bodyType, this.asmType)) {
            ErrorType errorMessage = new ErrorType();
            errorMessage.addErrorMessage("Type mismatch in assignment to " + this.variableID + ".");
            return errorMessage;
        }

        if (bodyType instanceof ClassType) ((ClassType) variableEntry.getType()).setInstantiated(true);

        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        String asmString = "";

        StringBuilder getAR = new StringBuilder();
        for (int i = 0; i < nestinglevel - this.variableEntry.getNestLevel(); i++) {
            getAR.append("lw\n");
        }

        asmString += this.asmBody.codeGeneration();
        // Address of variable = framePointer + offset:

        asmString += "lfp\n" + getAR +
                "push " + this.variableEntry.getOffset() + "\n" + "add\n";
        // Address to overwrite indicated by current top of stack.
        asmString += "sw\n";
        return asmString;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        ArrayList<SemanticError> res = new ArrayList<>();

        // Preso da IDNode. Controlla se la variabile è nello scope corrente e poi sale di scope in scope se non la trova.
        int j = env.getNestLevel();
        STEntry tmp = null;
        while (j >= 0 && tmp == null) {
            tmp = (env.getST().get(j)).get(variableID);
            j = j - 1;
        }

        // Verifica: la variabile e` dichiarata.
        if (tmp == null) {
            res.add(new SemanticError("Variable " + variableID + " not declared"));
            return res;
        } else {
            variableEntry = tmp;
            nestinglevel = env.getNestLevel();
        }

        this.asmType = variableEntry.getType();


        res.addAll(this.asmBody.checkSemantics(env));

        return res;
    }

}
