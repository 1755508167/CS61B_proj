import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestArray2 {
    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(10);
        deque.addFirst(20);
        assertEquals(20, deque.get(0));
        assertEquals(10, deque.get(1));
        assertEquals(2, deque.size());
    }

    @Test
    public void testAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(30);
        deque.addLast(40);
        assertEquals(30, deque.get(0));
        assertEquals(40, deque.get(1));
        assertEquals(2, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(50);
        deque.addLast(60);
        assertEquals(50, deque.removeFirst());
        assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(70);
        deque.addFirst(80);
        assertEquals(70, deque.removeLast());
        assertEquals(1, deque.size());
    }

    @Test
    public void testGet() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(90);
        deque.addLast(100);
        deque.addLast(110);
        assertEquals(100, deque.get(1));
    }

    @Test
    public void testIsEmpty() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        deque.addFirst(120);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testResize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }
        assertEquals(10, deque.size());
        assertEquals(0, deque.get(0));
        assertEquals(9, deque.get(9));
    }
}
