package util;

import java.util.ArrayList;

import ast.ClassNode;
import ast.FunNode;
import ast.Node;
import type.BoolType;
import type.ClassType;
import type.IntType;
import type.Type;
import type.VoidType;

public class FOOLlib {

    private static int labCount = 0;

    private static int funLabCount = 0;

    private static String funCode = "";

    //valuta se il tipo "a" è <: del tipo "b"
    public static boolean isSubtype(Type a, Type b) {

        // Se a e b sono tipi primitivi (int, bool o void) si controlla solo se sono uguali.
        if (a instanceof BoolType || a instanceof IntType || a instanceof VoidType) {
            return a.getClass().equals(b.getClass());
        }
        // Se a e' un ClassType allora si controlla il subtyping fra classi
        else if (a instanceof ClassType) {

            if (!(b instanceof ClassType)) return false; // se b non e' una classe, non puo' esserci subtyping

            ClassType typeA = (ClassType) a;
            ClassType typeB = (ClassType) b;

            if ((typeA).getId().equals(typeB.getId())) return true; // sono la stess tipo di classe (stesso ID)
            else {

                // Altrimenti controlliamo la classe padre di a se ha lo stesso ID di b
                // Per controllare la classe padre serve accedere alla classMap in env

                String parent = Environment.getClassMap().get(typeA.getId()).getSuperClassName();

                while (parent != null) { // sali di livello nell'albero della ereditarieta'
                    if ((parent).equals(typeB.getId()))
                        return true; // se la classe parent e' la stessa classe di b, c'e' subtyping
                    else
                        parent = Environment.getClassMap().get(parent).getSuperClassName(); // altrimenti accedi alla classe padre di a
                }
            }
        }

        return false;
    }

    //Provides a standardised formatted print for a ProgNode's Let declarations and body.
    public static String printProgNode(String indent,
                                       ArrayList<ClassNode> classList,
                                       ArrayList<Node> declarationList,
                                       ArrayList<Node> contextBody) {

        String progType = (classList.isEmpty()) ? "ProgLetIn" : "ProgClass";
        String let = "Let Declarations\n", in = "IN\n";
        StringBuilder classString = new StringBuilder();
        StringBuilder declarationString = new StringBuilder();
        StringBuilder bodyString = new StringBuilder();

        // Popula le stringhe con informazioni sulle classi, dichiarazioni e il corpo.
        for (ClassNode c : classList) classString.append(c.toPrint(indent + "    "));
        for (Node d : declarationList) declarationString.append(d.toPrint(indent + "    "));
        for (Node b : contextBody) bodyString.append(b.toPrint(indent + "    "));

        if (classString.length() > 0) {
            classString.insert(0, "Classes\n");
        }

        return indent + progType + "\n" +
                "  " + classString +
                "  " + let + declarationString +
                "  " + in +
                bodyString;
    }

    public static String freshLabel() {
        return "label" + (labCount++);
    }

    public static String freshFunLabel() {
        return "function" + (funLabCount++);
    }

    public static void putCode(String c) {
        funCode += "\n" + c; //aggiunge una linea vuota di separazione prima di funzione
    }

    public static String getCode() {
        return funCode;
    }

    public static ArrayList<SemanticError> processCheckSemanticsDecs(ArrayList<Node> decList, Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        //check semantics in the dec list
        ArrayList<FunNode> funDecs = new ArrayList<>();
        //if there are children then check semantics for every child and save the results
        for (Node dec : decList) {
            if (dec instanceof FunNode) {
                // Separo il caso in cui è una fun node così da poter gestire chiamate di funzioni dichiarate dopo.
                // Inserisco la funzione con una stentry provvisoria per posticipare la valutazione vera della funzione.
                env.getST().get(env.getNestLevel()).put(((FunNode) dec).getId(), new STEntry());
                Environment.getDeclaredFunctions().add((FunNode) dec);
                funDecs.add((FunNode) dec);
            } else {
                res.addAll(dec.checkSemantics(env));
            }
        }

        funDecs.forEach(f -> res.addAll(f.checkSemantics(env)));

        return res;
    }


}