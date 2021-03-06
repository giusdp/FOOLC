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

public class ProgClassNode implements Node {

    private ArrayList<ClassNode> classList;
    private ArrayList<Node> decList;
    private ArrayList<Node> contextBody;

    public ProgClassNode(ArrayList<ClassNode> classes,
                         ArrayList<Node> decs,
                         ArrayList<Node> body) {
        classList = classes;
        decList = decs;
        contextBody = body;
    }

    @Override
    public String toPrint(String indent) {
        return FOOLlib.printProgNode(indent, classList, decList, contextBody);
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        env.incNestLevel(); // porto il nesting level a 0

        env.setOffset(-1); // Bisogna settare il primo offset a -1 così quando si accede ad una variabile prendendo
        // l'offset, si inizia da 9999 invece che da MEMSIZE=10000, dato che l'array memory va da 0 a 9999
        env.setClassOffset(-1);
        env.setMethodOffset(-1);
        env.setFunctionOffset(-1);

        // Creo una nuova hashmap e la aggiugno alla symbol table
        HashMap<String, STEntry> hm = new HashMap<>();
        env.getST().add(hm);// Nuovo scope (della classe)

        ArrayList<SemanticError> res = new ArrayList<>();

        //int initialClassOffset = env.getClassOffset();
        for (ClassNode cn : classList) {
            Environment.getClassMap().put(cn.getId(), cn);
        }

        // Controlla la semantica per ogni dichiarazione di classe
        for (ClassNode n : classList) {
            res.addAll(n.checkSemantics(env));
        }

        // Se ci sono lets
        res.addAll(FOOLlib.processCheckSemanticsDecs(this.decList, env));

        // Siccome le funzioni in svm sono dichiarate all'inizio, il class offset non puo' iniziare da -1, ma da functionOffset + 1
        // Quindi vengono aggiornati i valori degli offset alle classi
        for (ClassNode c : classList) {
            c.stEntry.setOffset(c.stEntry.getOffset() + env.getFunctionOffset() + 1);
        }

        decList.forEach(d -> {
            if (d instanceof VarNode) {
                ((VarNode) d).updateEntryOffset(env.getFunctionOffset() + 1 );
            }
        });

        env.setOffset(env.getOffset() + env.getFunctionOffset() + 1);

        // Controlla l'espressione fuori
        for (Node instruction : contextBody) {
            res.addAll(instruction.checkSemantics(env));
        }

        // Lascia lo scope
        env.getST().remove(env.decNestLevel());
        //env.decNestLevel();

        return res;
    }

    @Override
    public Type typeCheck() {
        Type type = new VoidType(); // Default value

        for (ClassNode cl : classList) {
            type = cl.typeCheck();
            if (type instanceof ErrorType) return type;
        }
        for (Node declaration : decList) {
            type = declaration.typeCheck();
            if (type instanceof ErrorType) return type;
        }
        for (Node instruction : contextBody) {
            type = instruction.typeCheck();
            if (type instanceof ErrorType) return type;
        }

        return type; // The type of the program is the type of the last instruction (the returned expression)
    }

    @Override
    public String codeGeneration() {

        for (ClassNode c : classList) {
            c.codeGeneration();  // La code generation delle classi ritorna stringa vuota quindi non serve.
            // Piuttosto popola la stringa in FOOLlib con le dispatch tables.
        }
        // Se è solo un file di dichiarazioni di classi salta la code generation del let in
        if (!decList.isEmpty()) {
            StringBuilder declCode = new StringBuilder();

            decList.forEach(d -> {
                if (d instanceof FunNode) declCode.append(d.codeGeneration());
            });

            /*decList.forEach(d -> {
                if (d instanceof VarNode)
                    if (((VarNode) d).getExp() instanceof ConstructorNode)
                        declCode.append(d.codeGeneration());
            });*/


            decList.forEach(d -> {
                if (!(d instanceof FunNode))
                    declCode.append(d.codeGeneration());
                /*&& !((d instanceof VarNode) && ((VarNode) d).getExp() instanceof ConstructorNode))*/
            });

            StringBuilder bodyCode = new StringBuilder();
            for (Node instr : contextBody)
                bodyCode.append(instr.codeGeneration());


            return "## LET\n\n" + declCode.toString() + "\n## IN\n\n" + bodyCode.toString() + "halt\n\n" + "## Functions code and Dispatch Table\n" +
                    FOOLlib.getCode();
        }

        return "halt\n" + FOOLlib.getCode();

    }


}
