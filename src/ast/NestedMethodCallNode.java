package ast;

import type.ArrowType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

import java.util.ArrayList;

public class NestedMethodCallNode extends MethodCallNode {

    private String id;
    private ArrayList<Node> parList;

    private String ownerClass;

    private int dtOffset;
    private int nestingLevel;

    private Type methodType;

    private int nestedCallNestingLevel = 3; // La chiamata in un metodo sarà sempre a nl = 3.
                                            // Class -> nl=1. Method -> nl=2. Call-> nl=3

    public NestedMethodCallNode(String className, String m, ArrayList<Node> args) {
        id = m;
        parList = args;
        ownerClass = className;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>();

        ClassNode ownerClassNode = env.getClassMap().get(ownerClass);


        // Dopo i controlli preliminari sulla variabile usata.
        // Si cerca la definizione del metodo nell'hashmap dei metodi
        // Siccome  metodo di una classe, la variabile dovrebbe essere una classe
        // Se non lo è si intercetta l'eccezione e si da un Semantic Error

            //Non può succedere in realtà perché quando si va ad instanziare la classe, se non è stata
            // definita già è errore, quindi molto prima di questo controllo.
            if (ownerClassNode == null){
                res.add(new SemanticError("Class "+ ownerClass + " not defined."));
                return res;
            }

            // Verificare che il metodo 'id' esiste in classe 'ownerClass':
            boolean methodDeclared = false;
            for (Node fn : ownerClassNode.getMethodList()) {
                FunNode function = (FunNode) fn;
                if (function.getId().equals(this.id)) {
                    methodType = (ArrowType) function.getType();
                    methodDeclared = true;
                    break;
                }
            }
            // Se il metodo non è dichiarato nella 'ownerClass' e la 'ownerClass' estende
            // una classe, bisogna controllare che il metodo sia della 'superClass'
            if (!methodDeclared) {
                while (ownerClassNode.getSuperClass() != null  && !methodDeclared) {
                    for (Node fn : ownerClassNode.getSuperClass().getMethodList()) {
                        FunNode function = (FunNode) fn;
                        if (function.getId().equals(this.id)) {
                            // if method declared in subclass is polymorphic, store type for TypeChecking.
                            methodType = function.getType();
                            methodDeclared = true;
                            break;
                        }
                    }
                    ownerClassNode = ownerClassNode.getSuperClass();
                }
            }

            if (!methodDeclared) {
                res.add(new SemanticError("NestedMethodCall: Method "+ id + " in class " + ownerClass + " is not defined."));
                return res;
            }

            nestingLevel = env.getNestLevel(); // Otteniamo il nesting level "a tempo di invocazione"
            dtOffset = ownerClassNode.getMethodDTOffset(this.id);

        return res;
    }


    @Override
    public String toPrint(String indent) {

        StringBuilder parlstr = new StringBuilder();

        for (Node par : parList)
            parlstr.append(par.toPrint(indent + "  "));

        // TODO: methodEntry is null at runtime. Causes NullPtrException.
        // methodEntry is never instantiated here.
        return indent + "Method Call:" + id + "\n" + indent + "    from class " + ownerClass + /*methodEntry.toPrint(indent + "  ") +*/ parlstr;
    }

    @Override
    public Type typeCheck() {
        ErrorType error = new ErrorType();

        // Siccome il polimorfismo e' stato gia' controllato in classnode, per tutti i metodi
        // Ora bisogna fare un semplice controllo sui parametri. Questo controllo ora e' identico a CallNode
        // TODO: classe padre per callnode, methodcallnode, constructornode per evitare questo codice ripetuto 3 volte???
        if (methodType instanceof ArrowType) {
            ArrowType funType = (ArrowType) methodType;
            ArrayList<Type> parTypes = funType.getParList();

            // si controllano numero parametri con quelli passati in input
            if ( parTypes.size() != parList.size() ) {
                error.addErrorMessage("Wrong number of parameters in the invocation of the method: "+id + " inside  class" + ownerClass+
                        "\nExpected " + parTypes.size() + " but found " + parList.size());
                return error;
            }
            // si controllano tipi parametri con quelli passati in input
            for (int i = 0; i < parList.size(); i++)
                if ( !(FOOLlib.isSubtype( (parList.get(i)).typeCheck(), parTypes.get(i)) ) ) {
                    error.addErrorMessage("Wrong type for the "+(i+1)+"-th parameter in the invocation of method: "+id + " inside  class" + ownerClass);
                    return error;
                }
            return funType.getReturn();
        }

        // ALTRIMENTI se non e' ne' funzione, allora errore
        error.addErrorMessage("Invocation of a non-function " + id);
        return error;
    }

    @Override
    public String codeGeneration() {
        StringBuilder parametersCodeString = new StringBuilder();
        for (int i = parList.size() - 1; i >= 0; i--) {
            parametersCodeString.append(parList.get(i).codeGeneration());
        }

        StringBuilder getAR= new StringBuilder();
        for (int i=0; i< nestingLevel - nestedCallNestingLevel; i++) {
            getAR.append("lw\n");
        }

        return "lfp\n" + //CL
                parametersCodeString +
                "push " + 0 + "\n" + //metto offset sullo stack. E' 0 perché non dobbiamo spostarci via dalla classe
                "lfp\n" +
                getAR +
                "add\n" +
                "lw\n"  + //carico sullo stack il valore all'indirizzo ottenuto (della classe all'heap)
                "cts\n"+ // Duplicando ora il top, duplico l'indirizzo della classe che punta all'heap.
                //cosi' sara' il top dello stack all'esecuzione del metodo
                "lw\n" +
                "push " + dtOffset + "\n"+
                "add\n"+
                "lm\n" +
                "js\n";
    }

}
