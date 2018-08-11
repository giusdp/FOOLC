package util;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment {

	private ArrayList<HashMap<String, STEntry>> symTable = 
			new ArrayList<HashMap<String, STEntry>>();
	private int nestingLevel;
	private int offset;

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

	//livello ambiente con dichiarazioni piu' esterno è 0 (prima posizione ArrayList) 
	//invece che 1 (slides)
	//il "fronte" della lista di tabelle è symTable.get(nestingLevel)
}
