package ast;

import java.util.ArrayList;

import type.*;
import util.Environment;
import util.SemanticError;
import util.FOOLlib;
import util.STEntry;

public class CallNode implements Node {

    private String id;
    private STEntry entry;
    private ArrayList<Node> parList;
    private int nestingLevel;

    public CallNode(String text, ArrayList<Node> args) {
        id = text;
        parList = args;
    }

    public String getId() {
        return id;
    }

    public String toPrint(String indent) {  //
        StringBuilder parlstr = new StringBuilder();
        for (Node par : parList)
            parlstr.append(par.toPrint(indent + "  "));
        return indent + "Call: " + id + " at nestlev " + nestingLevel + "\n"
                + entry.toPrint(indent + "  ")
                + parlstr.toString();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        //create the result
        ArrayList<SemanticError> res = new ArrayList<>();

        int j = env.getNestLevel();
        STEntry tmp = null;

        while (j >= 0 && tmp == null)
            tmp = (env.getST().get(j--)).get(id);

        if (tmp == null) {
            res.add(new SemanticError("Function " + id + " not declared"));
        } else {
            this.entry = tmp;
            this.nestingLevel = env.getNestLevel();

            for (Node arg : parList)
                res.addAll(arg.checkSemantics(env));
        }

        return res;
    }

    public Type typeCheck() {

        ErrorType error = new ErrorType();

        Type entryType;
        if (entry.isToBeEvaluated()) {
            FunNode f = Environment.getFunctionById(id, nestingLevel);
            entry = f != null ? f.getEntry() : null;
            entryType = f != null ? f.getType() : null;
        } else {
            entryType = entry.getType();
        }

        // Se l'invocazione e' di una normale funzione, allora il type sara' un arrowtype e il typechecking si fa su questo
        if (entryType instanceof ArrowType) {
            ArrowType funType = (ArrowType) entryType;
            ArrayList<Type> parTypes = funType.getParList();
            if (!(parTypes.size() == parList.size())) {
                error.addErrorMessage("Wrong number of parameters in the invocation of function: " + id +
                        ". \n Expected " + parTypes.size() + " but found " + parList.size());
                return error;
            }

            for (int i = 0; i < parList.size(); i++) {

                Type inputParType = parList.get(i).typeCheck();
                Type argType = parTypes.get(i);
                if (inputParType instanceof VoidType ||
                        (inputParType instanceof ClassType && !((ClassType) inputParType).isInstantiated())) {
                    error.addErrorMessage("Cannot pass 'null' to function "+id+". Null at "+ (i + 1) +"-th parameter.");
                    return error;
                    //((ClassType) fieldType).setInstantiated(false);
                }
                if (inputParType instanceof ErrorType) return inputParType;
                if (argType instanceof ErrorType) return argType;

                if (!(FOOLlib.isSubtype(inputParType, argType))) {
                    error.addErrorMessage("Wrong type for the " + (i + 1) + "-th parameter in the invocation of function: " + id);
                    return error;
                }
            }

            return funType.getReturn();
        }

        // ALTRIMENTI se non e' ne' funzione, allora errore
        error.addErrorMessage("Invocation of a non-function " + id);
        return error;


    }

    public String codeGeneration() {

        StringBuilder parCode = new StringBuilder();
        for (int i = parList.size() - 1; i >= 0; i--) {
            parCode.append(parList.get(i).codeGeneration());
        }

        StringBuilder getAR = new StringBuilder();
        for (int i = 0; i < nestingLevel - entry.getNestLevel(); i++) {
            getAR.append("lw\n");
        }

        return  "lfp\n" + //CL
                parCode.toString() +
                "lfp\n" + getAR + //setto AL risalendo la catena statica
                // ora recupero l'indirizzo a cui saltare e lo metto sullo stack
                "push " + entry.getOffset() + "\n" + //metto offset sullo stack
                "lfp\n" + getAR + //risalgo la catena statica
                "add\n" +
                "lw\n" + //carico sullo stack il valore all'indirizzo ottenuto
                "js\n";
    }

    public STEntry getEntry() {
        return entry;
    }
}