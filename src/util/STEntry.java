package util;

import type.Type;

public class STEntry {

	private int nestingLevel;
	private Type type;
	private int offset;
	private boolean toBeEvaluated = false;

	public STEntry(int nestingLevel, int os) {
		this.nestingLevel = nestingLevel;
		offset = os;
	}

	public STEntry(int nestingLevel, Type t, int os) {
		this(nestingLevel, os);
        type = t;
	}

	public STEntry(){
	    toBeEvaluated = true;
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
				+ "\n" + s + "STEntry: type " + type.toPrint("  ")
				+ s + "STEntry: offset " + Integer.toString(offset) + "\n";
	}

    public boolean isToBeEvaluated() {
        return toBeEvaluated;
    }

    public void setToBeEvaluated(boolean toBeEvaluated) {
        this.toBeEvaluated = toBeEvaluated;
    }
}