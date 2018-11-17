package ast;

import java.util.ArrayList;

import type.*;
import util.Environment;
import util.SemanticError;
import util.STEntry;

public class IdNode implements Node {

    private String id;
    private STEntry entry;
    private int nestinglevel;

    private boolean minus = false, not = false;


    public IdNode(String i) {
        id = i;
    }

    public IdNode(String i, boolean minus, boolean not) {
        id = i;
        this.minus = minus;
        this.not = not;
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
        ErrorType error = new ErrorType();
        if (entry.getType() instanceof ArrowType) {
            error.addErrorMessage("Wrong usage of function identifier: " + id);
            return error;
        }

        if (minus && !(entry.getType() instanceof IntType)){
            error.addErrorMessage("Wrong usage of minus symbol. Can't negate variable: "+
                    id+" of type "+entry.getType().toPrint(""));
            return error;
        }

        if (not && !(entry.getType() instanceof BoolType)){
            error.addErrorMessage("Wrong usage of not symbol. Can't negate variable: "+
                    id+" of type "+entry.getType().toPrint(""));
            return error;
        }

        return entry.getType();
    }

    public String codeGeneration() {
        StringBuilder getAR = new StringBuilder();
        for (int i = 0; i < nestinglevel - entry.getNestLevel(); i++)
            getAR.append("lw\n");

        String code =  "push " + entry.getOffset() + "\n" + //metto offset sullo stack
                "lfp\n" + getAR + //risalgo la catena statica
                "add\n" +
                "lw\n"; //carico sullo stack il valore all'indirizzo ottenuto
        if (minus){
            code += "push -1\n" + "mult\n";
        }
        else if (not){
            String neg = "push 1\n";
            code = neg + code + "sub\n";
        }

        return code;
    }

    public Type getType() {
        return entry.getType();
    }

    public STEntry getEntry() {
        return entry;
    }

}