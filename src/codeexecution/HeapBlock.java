package codeexecution;
/**
 * @author Giuseppe
 *
 */
public class HeapBlock {

	HeapBlock next; // contiene il prossimo blocco libero

    private int position; // l'indice di questo blocco

    HeapBlock(int position, HeapBlock nextHeapBlock) {
        this.position = position;
        this.next = nextHeapBlock;
    }

    int getPointedPosition() {
        return position;
    }
}
