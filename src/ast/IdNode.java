package ast;

import java.util.ArrayList;

import type.ArrowType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.SemanticError;
import util.STEntry;

public class IdNode implements Node {

    private String id;
    private STEntry entry;
    private int nestinglevel;

    public IdNode(String i) {
        id = i;
    }

    public String getId() {
        return id;
    }

    public String toPrint(String indent) {
        //System.out.println(entry);
        return indent + "Id:" + id + " at nestlev " + nestinglevel + "\n" +
                entry.toPrint(indent + "  ");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        int j = env.getNestLevel();
        STEntry tmp = null;
        while (j >= 0 && tmp == null) {
            tmp = (env.getST().get(j)).get(id);
            j = j - 1;
        }

        if (tmp == null) {
            res.add(new SemanticError("Variable " + id + " not declared"));
        } else {
            entry = tmp;
            nestinglevel = env.getNestLevel();
        }

        return res;
    }

    public Type typeCheck() {
        if (entry.getType() instanceof ArrowType) {
            ErrorType error = new ErrorType();
            error.addErrorMessage("Wrong usage of function identifier: " + id);
            return error;
        }
        return entry.getType();
    }

    public String codeGeneration() {
        StringBuilder getAR = new StringBuilder();
        for (int i = 0; i < nestinglevel - entry.getNestLevel(); i++)
            getAR.append("lw\n");

        return "push " + entry.getOffset() + "\n" + //metto offset sullo stack
                "lfp\n" + getAR + //risalgo la catena statica
                "add\n" +
                "lw\n"; //carico sullo stack il valore all'indirizzo ottenuto

    }

    public Type getType() {
        return entry.getType();
    }

    public STEntry getEntry() {
        return entry;
    }
}