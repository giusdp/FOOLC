package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Environment {

	private ArrayList<HashMap<String, STEntry>> symTable = 
			new ArrayList<HashMap<String, STEntry>>();
	
	private int nestingLevel;
	private int offset;

	// Tiene traccia dei methodi dichiarati nelle classi. 
	//Utilizzato per la check semantics delle invocazione ai metodi
	private HashMap<String, Set<String>> classMethods = new HashMap<>();
	
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


	public HashMap<String, Set<String>> getClassMethods() {
		return classMethods;
	}
	
	public void addMethod(String c, String m) {
		classMethods.get(c).add(m);
	}

	//livello ambiente con dichiarazioni piu' esterno è 0 (prima posizione ArrayList) 
	//invece che 1 (slides)
	//il "fronte" della lista di tabelle è symTable.get(nestingLevel)
}
