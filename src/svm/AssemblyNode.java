package svm;

public class AssemblyNode {

	// TODO Un assembly node rappresenta una istruzione svm
	private int instructionID; // int representing an instruction, from antlr

	private int labelAddress; // If it's a push or some instruction with labels we store it here
	private int number; // if it's a push number we store it here

	private int codeIndex;

	private boolean withLabel = false;

	public AssemblyNode(int instr, int codeIndex) {
		this.instructionID = instr;
		this.codeIndex = codeIndex;
	}

	public int getInstructionID() {
		return instructionID;
	}

	public int getLabelAddress() {
		return labelAddress;
	}

	// Se si puo' fare jumping a questa istruzione, allora si setta l'indirizzo
	// della label per fare jumping
	public void setLabelAddress(int label) {
		withLabel = true;
		this.labelAddress = label;
	}

	public boolean isWithLabel() {
		return withLabel;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCodeIndex() {
		return codeIndex;
	}

	public void setCodeIndex(int codeIndex) {
		this.codeIndex = codeIndex;
	}

}
