package util;

import java.util.ArrayList;
import java.util.HashMap;

import ast.ClassNode;

public class Environment {

	private ArrayList<HashMap<String, STEntry>> symTable = 
			new ArrayList<HashMap<String, STEntry>>();
	
	private int nestingLevel;
	private int offset;
	private int classOffset; // Offset dedicato per le classi e i loro metodi

	private HashMap<String, ClassNode> classMap = new HashMap<>();
	
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

	public int incrClassOffset() {
		return classOffset++;
	}
	
	public int decClassOffset() {
		return classOffset--;
	}

	public void setClassOffset(int classOffset) {
		this.classOffset = classOffset;
	}
	
	
	

	//livello ambiente con dichiarazioni piu' esterno è 0 (prima posizione ArrayList) 
	//invece che 1 (slides)
	//il "fronte" della lista di tabelle è symTable.get(nestingLevel)
}
