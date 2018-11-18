package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import ast.ClassNode;
import ast.FunNode;

public class Environment {

	private ArrayList<HashMap<String, STEntry>> symTable = new ArrayList<>();
	
	private int nestingLevel;
	private int offset;
	private int classOffset; // Offset dedicato per le classi
    private int methodOffset;
    private int functionOffset;


	private HashMap<String, ClassNode> classMap = new HashMap<>();
	private static ArrayList<FunNode> declaredFunctions = new ArrayList<>();

	public Environment() {
		nestingLevel = -1;
		offset = 0;
	}

	public ArrayList<HashMap<String, STEntry>> getST() {
		return symTable;
	}

	public int getNestLevel() {
		return nestingLevel;
	}

	public int incNestLevel() {
		return nestingLevel++;
	}

	public int decNestLevel() {
		return nestingLevel--;
	}

	public int getOffset() {
		return offset;
	}

	public int incOffset() {
		return offset++;
	}

	public int decOffset() {
		return offset--;
	}

	public void setOffset(int n) {
		offset = n;
	}

	public HashMap<String, ClassNode> getClassMap() {
		return classMap;
	}

	public int getClassOffset(){ return classOffset; }

	public int decClassOffset() {
		return classOffset--;
	}

	public void setClassOffset(int classOffset) {
		this.classOffset = classOffset;
	}

    public int decMethodOffset() {
        return methodOffset--;
    }

    public void setMethodOffset(int methodOffset) {
        this.methodOffset = methodOffset;
    }

    public int decFunctionffset() {
        return functionOffset--;
    }

    public void setFunctionOffset(int functionOffset) {
        this.functionOffset = functionOffset;
    }

    public int getFunctionOffset() {
        return functionOffset;
    }

    public static ArrayList<FunNode> getDeclaredFunctions() {
        return declaredFunctions;
    }

    public static FunNode getFunctionById(String id, int nl){
        // Reversed for each on the declared functions. It's reversed to respect the scopes, the function in the most
        // nested scope is the last one inserted in the list, so it should be the first one returned.
        // The first functions inserted is the one in the most external scope, so it should be the last one returned.
        for (int i = declaredFunctions.size() - 1; i >= 0; --i){
        	FunNode f = declaredFunctions.get(i);
            if (f.getId().equals(id) && f.getEntry().getNestLevel() <= nl){
                return f;
            }
        }

        return null;
    }

    //livello ambiente con dichiarazioni piu' esterno è 0 (prima posizione ArrayList)
	//invece che 1 (slides)
	//il "fronte" della lista di tabelle è symTable.get(nestingLevel)
}
