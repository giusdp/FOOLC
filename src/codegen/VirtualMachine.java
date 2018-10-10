package codegen;

import java.util.ArrayList;
import java.util.HashSet;

public class VirtualMachine {
	
	public static final int START_ADDRESS = 1234;   //indirizzo di partenza
    private static final int MEMSIZE = 1000;        //dimensione totale della memoria
    
    private ArrayList<String> output = new ArrayList<>();   //contiene l'esito della print o gli errori

    private int[] memory = new int[MEMSIZE];    //memoria
    private int[] code;                         //codice da eseguire

    private int hp = START_ADDRESS;             //heap pointer
    private int ip = 0;                         //istruction pointer
    private int sp = START_ADDRESS + MEMSIZE;   //stack pointer
    private int fp = START_ADDRESS + MEMSIZE;   //frame pointer all'inizio punta alla stessa locazione dello stack pointer
    private int ra;
    private int rv;

    private Heap heap = new Heap(MEMSIZE);
    private HashSet<HeapBlock> heapInUso = new HashSet<>();
    
}
