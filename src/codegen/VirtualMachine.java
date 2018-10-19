package codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import svm.AssemblyNode;
import svm.SVMParser;

/**
 * @author Giuseppe
 *
 */
public class VirtualMachine {
	
	
	public static final int CODESIZE = 10000;
    public static final int MEMSIZE = 10000;
    
    private List<AssemblyNode> code;
    //private int[] code;
    private int[] memory = new int[MEMSIZE];
    
    private int ip = 0;
    private int sp = MEMSIZE;
    
    private int hp = 0;       
    private int fp = MEMSIZE; 
    private int ra;           
    private int rv;
    
    private Heap heap = new Heap(MEMSIZE);
    private HashSet<HeapBlock> usedHeapBlocks = new HashSet<>();
    
    
    public VirtualMachine(List<AssemblyNode> code) {
        this.code = code;
      }

    public void cpu() throws Exception {
      while ( true ) {
        AssemblyNode bytecode = code.get(ip); // fetch
        int v1,v2;
        int address;
        switch ( bytecode.getInstruction() ) {
          case SVMParser.PUSH:
            push( code.get(ip++).getNumber() );
            break;
          case SVMParser.POP:
            pop();
            break;
          case SVMParser.ADD :
            v1=pop();
            v2=pop();
            push(v2 + v1);
            break;
          case SVMParser.MULT :
            v1=pop();
            v2=pop();
            push(v2 * v1);
            break;
          case SVMParser.DIV :
            v1=pop();
            v2=pop();
            push(v2 / v1);
            break;
          case SVMParser.SUB :
            v1=pop();
            v2=pop();
            push(v2 - v1);
            break;
          case SVMParser.STOREW : //
            address = pop();
            memory[address] = pop();    
            break;
          case SVMParser.LOADW : //
            push(memory[pop()]);
            break;
          case SVMParser.BRANCH : 
            address = code.get(ip).getNumber();
            ip = address;
            break;
          case SVMParser.BRANCHEQ : //
            address = code.get(ip++).getNumber();
            v1=pop();
            v2=pop();
            if (v2 == v1) ip = address;
            break;
          case SVMParser.BRANCHLESSEQ :
            address = code.get(ip++).getNumber();
            v1=pop();
            v2=pop();
            if (v2 <= v1) ip = address;
            break;
          case SVMParser.JS : //
            address = pop();
            ra = ip;
            ip = address;
            break;
         case SVMParser.STORERA : //
            ra=pop();
            break;
         case SVMParser.LOADRA : //
            push(ra);
            break;
         case SVMParser.STORERV : //
            rv=pop();
            break;
         case SVMParser.LOADRV : //
            push(rv);
            break;
         case SVMParser.LOADFP : //
            push(fp);
            break;
         case SVMParser.STOREFP : //
            fp=pop();
            break;
         case SVMParser.COPYFP : //
            fp=sp;
            break;
         case SVMParser.STOREHP : //
            hp=pop();
            break;
         case SVMParser.LOADHP : //
            push(hp);
            break;
         case SVMParser.PRINT :
            System.out.println((sp<MEMSIZE)?memory[sp]:"Empty stack!");
            break;
           
         case SVMParser.NEW :
        	 // Si assume che nel top dello stack c'è la classe in question (per la dispatch table), il numero di campi e i loro valori
        	 // dall'ultimo al primo.

             int dispatchTableAddress = pop();
             int fieldNumber = pop();
             int[] fieldValues = new int[fieldNumber];
             for (int i = 0; i < fieldNumber; ++i) {
            	 fieldValues[i] = pop();
             }


             HeapBlock allocatedMemory; // Primo blocco della lista di heap blocks "allocati", con next si accede al blocco successivo

             // Bisogna allocare memoria per i campi e l'indirizzo alla dispatch table
             allocatedMemory = heap.alloc(fieldNumber + 1);

             // La memoria allocata viene "taggata" come in uso (essendo nell'insieme degli used heap block),
             // Può essere utile per un eventuale garbage collector, se non lo facciamo togliamo questa roba
             usedHeapBlocks.add(allocatedMemory);

             // Si prende la prima posizione per l'array memory della lista di heap blocks allocati
             int heapMemoryStart = allocatedMemory.getPointedPosition();
             
             // Si inserisce nell'array memory l'indirizzo alla dispatch table alla posizione getPointedPosition() dell'heapblock attuale (il primo)
             // ed avanzo al prossimo heap block
             setMemory(allocatedMemory.getPointedPosition(), dispatchTableAddress);
             allocatedMemory = allocatedMemory.next;

             // Si inseriscono uno alla volta i campi passati in input, spostandoci uno ad uno all'heap block successivo
             for (int i = 0; i < fieldNumber; i++) {
                 setMemory(allocatedMemory.getPointedPosition(), fieldValues[i]);
                 allocatedMemory = allocatedMemory.next;
             }
             // Si fa un push sullo stack della prima posizione puntata all'array memory (da dove parte la classe nell'heap)
             push(heapMemoryStart);

             // Caso in cui l'heap superi lo stack
             if (heap.getHeadIndex() > hp) {
                 hp = heap.getHeadIndex();
             }

             // TODO E se la memoria terminasse? Errore e il programma esce. Oppure garbage collector se lo facciamo
             
        	 break;
            
         case SVMParser.HALT :
            return;
        }
      }
    } 
    
    //setta il valore passato all'indirizzo passato
	private void setMemory(int address, int value) throws Exception {
	    int location = address;
	    if (location < 0 || location >= MEMSIZE) {
	        throw new Exception("Segmentation Fault Error");
	    }
	    memory[location] = value;
	}
    
    private int pop() {
      return memory[sp++];
    }
    
    private void push(int v) {
      memory[--sp] = v;
    }
    
}
