package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import codeexecution.DTEntry;
import codeexecution.DispatchTable;
import type.ArrowType;
import type.ClassType;
import type.ErrorType;
import type.Type;
import util.Environment;
import util.FOOLlib;
import util.STEntry;
import util.SemanticError;

public class ClassNode implements Node {

    private String id;
    private ArrayList<Node> fieldList;
    private ArrayList<Node> methodList;

    public STEntry stEntry;

    private ClassType type;

    // EREDITARIETA
    private String superClassName = null;
    private ClassNode superClass = null;

    // Offset dei metodi nella classe. id metodo -> offset dispatch table
    private Map<String, Integer> methodsDTOffsets = new HashMap<>();

    // COSTRUTTORE
    public ClassNode(String name, ArrayList<Node> fieldList, ArrayList<Node> methodList) {
        this.id = name;
        this.fieldList = fieldList;
        this.methodList = methodList;

        type = new ClassType(id);
        int dtOffset = 1; // Parte da 1, perchè a 0 c'e' l'indirizzo della classe
        // e per accedere alle funzioni si fa indirizzoClasse + dtOffset
        // Esempio:
        /*
         * class_A:		<---- dtOffset = 0
         * function0	<---- dtOffset = 1
         * function1 	<---- dtOffset = 2
         *
         */

        for (Node m : methodList) {
            FunNode f = (FunNode) m;
            methodsDTOffsets.put(f.getId(), dtOffset++);
        }
    }

    @Override
    public String toPrint(String indent) {
        StringBuilder fieldstr = new StringBuilder();
        StringBuilder methodstr = new StringBuilder();
        String superstr = "";

        for (Node n : fieldList) {
            fieldstr.append(n.toPrint(indent + "  "));
        }

        for (Node method : methodList) {
            methodstr.append(method.toPrint(indent + "  "));
        }

        if (superClass != null)
            superstr += indent + "  " + "Extends: " + superClassName + "\n";

        return indent + "Class:" + id + "\n" + superstr + fieldstr + methodstr;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {

        //create result list
        ArrayList<SemanticError> res = new ArrayList<>();

        // Controllo classe già dichiarata
        HashMap<String, STEntry> currentScope = env.getST().get(env.getNestLevel());

        stEntry = new STEntry(env.getNestLevel(), type, env.getClassOffset());

        if (currentScope.put(id, stEntry) != null) {
            res.add(new SemanticError("Class " + id + " is already declared"));
            return res; // Se la classe è già stata dichiarata allora possiamo fermarci
        }
        // ****************

        // Controllo super classe dichiarata

        if (superClassName != null) {
            if (superClassName.equals(id)) {
                res.add(new SemanticError("Class " + id + " cannot have as base class itself."));
                return res;
            }
            if (currentScope.get(superClassName) == null) {
                res.add(new SemanticError("Base class " + superClassName + " is not declared"));
                return res;
            } else {
                setSuperClass(env.getClassMap().get(superClassName));


                ClassNode sc = superClass;
                while (sc != null) {
                    updateDTOffsets(sc);
                    ArrayList<Node> sf = sc.getFieldList();
                    for (int i = sf.size() - 1; i >= 0; --i) {
                        fieldList.add(0, sf.get(i));
                    }
//                    fieldList.addAll(sc.getFieldList());
                    sc = sc.getSuperClass();
                }

            }
        }

        // Altrimenti proseguiamo con la creazione di un nuovo scope
        // Dove inserire i campi e i metodi e fare i controlli dovuti
        env.incNestLevel();
        HashMap<String, STEntry> newScope = new HashMap<>();
        env.getST().add(newScope);

        int paroffset = 1;

        for (Node field : fieldList) {
            ParNode arg = (ParNode) field;

            // se non si setta su true poi nel controllo del corpo della classe se si evoca un metodo
            // da errore che la variabile non è inizializzata
            if (arg.getType() instanceof ClassType)
                ((ClassType) arg.getType()).setInstantiated(true);

            STEntry argSTEntry = new STEntry(env.getNestLevel(), arg.getType(), paroffset++);
            if (newScope.put(arg.getId(), argSTEntry) != null) {
                res.add(new SemanticError("Field " + arg.getId() + " already declared"));
            }
        }

        res.addAll(FOOLlib.processCheckSemanticsDecs(this.methodList, env));


        env.getST().remove(env.decNestLevel());

        return res;
    }

    @Override
    public Type typeCheck() {
        // Ci sarebbe da controllare il tipo di tutti i campi e di tutti i metodi e della superclasse, se c'e',
        // per vedere se ci sono errori, e poi se tutto va bene si ritorna il ClassType.
        // Pero' e' inutile controllare il tipo di Par perche' non dara' mai errori (vedi ParNode per il perche')
        // La superclasse e' anche inutile controllare perche' gia' ProgClassNode si occupa
        // del type checking di tutte le classi, quindi direi di controllare solo i tipi dei metodi.

        // Type-check polymorphic methods.
        ClassNode superclassIterator = this.getSuperClass();

        // Organise return types of class methods into array for comparison:

        // With this map we can keep track of the BASE arrowtype associated with a certain DERIVED function
        // so later we can get the arrowtype of the derived function and check it against the base function type
        HashMap<FunNode, ArrowType> derivedMethodToBaseArrowTypeMap = new HashMap<>();

        while (superclassIterator != null) {

            for (Node myMethods : this.getMethodList()) {
                FunNode derivedMethod = (FunNode) myMethods;
                for (Node baseMethods : superclassIterator.getMethodList()) {
                    FunNode baseMethod = (FunNode) baseMethods;
                    // if method is polymorphic:
                    if (baseMethod.getId().equals(derivedMethod.getId())) {

                        // If the derived method is already present, we update the associated arrowtype of the higher class
                        derivedMethodToBaseArrowTypeMap.put(derivedMethod, (ArrowType) baseMethod.getType());
                    }
                }
            }

            superclassIterator = superclassIterator.getSuperClass();
        }

        // TODO: controllare tipi dei campi (istanceof NullNode?)
        Type methodType;
        for (Node m : methodList) {
            FunNode method = (FunNode) m;
            methodType = method.typeCheck();
            if (methodType instanceof ErrorType) return methodType; // Return ErrorType se c'e' un errore nei metodi
        }

        // Una volta controllato che i metodi vadano bene, bisogna controllare l'overriding dei metodi
        // Se c'e' una super classe e ci sono overriding dei metodi, bisogna controllare che sia stato fatto
        // bene, usando la regola del type checking sull'overriding nelle slides

        // derivedReturn <: baseReturn
        // derivedParameter :> baseParameter

        ErrorType error = new ErrorType();

        for (FunNode derivedMethod : derivedMethodToBaseArrowTypeMap.keySet()) {
            ArrowType derivedMethodType = (ArrowType) derivedMethod.getType();
            ArrowType baseMethodType = derivedMethodToBaseArrowTypeMap.get(derivedMethod);

            // DO the override type checking
            error.addErrorMessage("Derived method " + derivedMethod.getId() + " in class " + this.id + ".\n  ");

            // First check the return type, is derivedReturn <: baseReturn ?
            if (!(FOOLlib.isSubtype(derivedMethodType.getReturn(), baseMethodType.getReturn()))) {
                error.addErrorMessage("Must return same type or subtype of overridden method: " +
                        baseMethodType.getReturn().toPrint(""));
                return error;
            }

            // Second check if the number of parameters is the same
            if (derivedMethodType.getParList().size() != baseMethodType.getParList().size()) {
                error.addErrorMessage("Must have same number of parameters of overridden method: " +
                        baseMethodType.getParList().size());
                return error;
            }

            // Third check each parameter types, is derivedParameter :> baseParameter ?
            for (int i = 0; i < derivedMethodType.getParList().size(); i++) {
                if (!(FOOLlib.isSubtype((baseMethodType.getParList().get(i)), derivedMethodType.getParList().get(i)))) {
                    error.addErrorMessage("The " + (i + 1) + "-th parameter must have same type or super type of the " + (i + 1) +
                            "-th parameter of overridden method: " + baseMethodType.getParList().get(i).toPrint(""));
                    return error;
                }
            }
        }

        return type;
    }

    @Override
    public String codeGeneration() {
        // Bisogna creare una nuova dispatch table per la classe
        // Cioè una entry per DispatchTable.dispatchtables: <classID, List<DTEntry>>

        // abbiamo la className: this.id

        // Creiamo List<DTEntry>
        List<DTEntry> dispatchTable;

        // Se la classe ha superclasse, allora la dispatch table di questa classe è solo
        // una estensione di quella della superclasse, altrimenti è una nuova da zero

        if (superClassName == null) {
            dispatchTable = new ArrayList<>();
        }
        // Altrimenti la copio come base
        else {
            dispatchTable = DispatchTable.getDispatchTableOfClass(superClassName);
        }

        //contiene i metodi della superclasse
        HashMap<String, String> superClassMethods = new HashMap<>();
        //aggiungo i metodi della superclasse alla hashmap
        for (DTEntry d : dispatchTable) {
            superClassMethods.put(d.getMethodID(), d.getMethodLabel());
        }

        //contiene i metodi della classe attuale
        HashMap<String, String> currentClassMethods = new HashMap<>();

        for (Node n : methodList) { //aggiungo i metodi della classe attuale
            FunNode m = (FunNode) n;
            currentClassMethods.put(m.getId(), m.codeGeneration().split(" ", 2)[1]); // Splitto la stringa ritornata togliendo la prima parola
            // Fun node mette un push davanti la label
            // qui tocca toglierlo che non ci deve essere
        }

        // ***** OVERRIDE DEI METODI NELLA DISPATCH TABLE *****
        for (int i = 0; i < dispatchTable.size(); i++) {

            String oldMethodID = dispatchTable.get(i).getMethodID();

            String newMethodCode = currentClassMethods.get(oldMethodID);

            // Sovrascrive versioni vecchie di funzioni polimorfiche quando
            // i nomi corrispondono.
            if (newMethodCode != null) {
                dispatchTable.set(i, new DTEntry(oldMethodID, newMethodCode));
            }
        }

        // Inserire metodi non-ereditati nella dispatch table.
        for (Node n : methodList) {
            FunNode m = (FunNode) n;

            String currentMethodID = m.getId();

            //se la superclasse non ha il metodo che si sta esaminando, lo si aggiunge alla dispatch table.
            if (superClassMethods.get(currentMethodID) == null) {
                dispatchTable.add(new DTEntry(currentMethodID, currentClassMethods.get(currentMethodID)));
            }
        }

        DispatchTable.addDispatchTable(this.id, dispatchTable);

        return "";
    }

    public String getId() {
        return id;
    }

    public ArrayList<Node> getFieldList() {
        return fieldList;
    }

    public ArrayList<Node> getMethodList() {
        return methodList;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public void setSuperClass(ClassNode parent) {
        this.superClass = parent;
    }

    public ClassNode getSuperClass() {
        return superClass;
    }

    public ClassType getClassType() {
        return type;
    }

    public int getMethodDTOffset(String methodID) {
        return methodsDTOffsets.get(methodID);

    }

    private void updateDTOffsets(ClassNode parent) {
        int nOverrideMethods = 0;

        for (String k : methodsDTOffsets.keySet()) {
            for (Node f : parent.getMethodList()) {
                if (((FunNode) f).getId().equals(k)) nOverrideMethods++;
            }
            methodsDTOffsets.put(k, methodsDTOffsets.get(k) + parent.getMethodList().size() - nOverrideMethods);
        }
    }

}
