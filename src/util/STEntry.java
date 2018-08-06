package util;

import type.Type;

public class STEntry {

	private int nestingLevel;
	private Type type;
	private int offset;

	public STEntry(int nestingLevel, int os) {
		this.nestingLevel = nestingLevel;
		offset = os;
	}

	public STEntry(int nestingLevel, Type t, int os) {
		this.nestingLevel = nestingLevel;
		type = t;
		offset = os;
	}

	public void setType(Type t) {
		type = t;
	}

	public Type getType() {
		return type;
	}

	public void setOffset(int o) {
		offset = o;
	}

	public int getOffset() {
		return offset;
	}

	public int getNestLevel() {
		return nestingLevel;
	}

	public String toPrint(String s) {
		return s + "STEntry: nestlev " + Integer.toString(nestingLevel) 
				+ "\n" + s + "STEntry: type\n" + type.toPrint(s + "  ")
				+ s + "STEntry: offset " + Integer.toString(offset) + "\n";
	}
}