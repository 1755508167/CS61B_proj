package synthesizer;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(false,arb.isEmpty());
        int item=arb.dequeue();
        assertEquals(1,item);
        int peek=arb.peek();
        //System.out.println(arb.peek());
        //assertEquals(2,peek);

    }

    @Test
    public void testForLoop() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        for (int i =0;i<7;i++){
            arb.enqueue(i);
        }
        for (int i=0;i<4;i++){
            arb.dequeue();
        }
        for(int x:arb){
            System.out.println(x);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
