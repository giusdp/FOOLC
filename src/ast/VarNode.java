package ast;

import java.util.ArrayList;
import java.util.HashMap;

import type.ClassType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.STEntry;
import util.SemanticError;
import util.FOOLlib;

public class VarNode implements Node {

    private String id;
    private Type type;
    private Node exp;
    private STEntry entry;
    private boolean isClass;

    public VarNode(String i, Type t, Node v) {
        id = i;
        type = t;
        exp = v;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        // Il nesting level viene incrementato ogni volta in cui si entra in uno scope,
        // quindi non sarà mai -1, perché il let in, le funzioni e le classi creano nuovi scope
        // e le variabili non possono essere dichiarate al di fuori di quei comandi.
        HashMap<String, STEntry> hm = env.getST().get(env.getNestLevel());

        isClass = false;
        if (type instanceof ClassType) {
            isClass = true;

            int j = env.getNestLevel();
            STEntry tmp = null;

            while (j >= 0 && tmp == null) {
                tmp = (env.getST().get(j--)).get(((ClassType) type).getId());
                // Il nome della classe equivale al tipo della variabile
            }

            if (tmp == null) {
                res.add(new SemanticError("Class " + ((ClassType) type).getId() + " has not been defined."));
                return res;
            }

            // Se è nullnode si mette false perché è ancora da instanziare
            ((ClassType) type).setInstantiated((!(exp instanceof NullNode)));

        }
        entry = new STEntry(env.getNestLevel(), type, env.decOffset());

        if (hm.put(id, entry) != null) {
            if (!isClass) res.add(new SemanticError("Id for variable " + id + " is already declared."));
            else res.add(new SemanticError("Id " + id + " already used as class name. Can't use exact name for var."));
            return res;
        }
        res.addAll(exp.checkSemantics(env));
        return res;
    }

    public String toPrint(String indent) {
        return indent + "Var:" + id + "\n" + type.toPrint(indent + "  ") + exp.toPrint(indent + "  ");
    }

    //valore di ritorno non utilizzato
    public Type typeCheck() {
        Type expType = exp.typeCheck();
        ErrorType error = new ErrorType();
        error.addErrorMessage("Incompatible value for variable: " + id);

        if (expType instanceof ErrorType) return expType;

        if (!isClass) { // Se non è una classe vedi il tipaggio. Se sotto tipo ok altrimenti errore
            if (!(FOOLlib.isSubtype(exp.typeCheck(), type))) {
                return error;
            }
        }
        else { // Altrimenti se è una classe ed è sotto tipo: ok
            if (FOOLlib.isSubtype(exp.typeCheck(), type)){
                return type;
            }
            else if (exp instanceof NullNode) { // oppure se non è sottotipo deve essere null
                ((NullNode) exp).setId(((ClassType) entry.getType()).getId());
                return type;
            }
            else{ // se non è manco null allora errore
                error.addErrorMessage("Incompatible value for variable: " + id);
                return error;
            }
        }

        return type;
    }

    public String codeGeneration() {
        return exp.codeGeneration();
    }


    void updateEntryOffset(int diff) {
        this.entry.setOffset(this.entry.getOffset() + diff);
    }

    public Node getExp() {
        return exp;
    }
}  