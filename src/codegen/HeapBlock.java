package codegen;

public class HeapBlock {

	HeapBlock next; // contiene il prossimo blocco libero

    private int index; //l'indice di questo blocco

    HeapBlock(int index, HeapBlock nextHeapBlock) {
        this.index = index;
        this.next = nextHeapBlock;
    }

    int getIndex() {
        return index;
    }
}
