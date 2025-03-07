import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayDequeTest {

    private ArrayDeque<Integer> deque;

    @BeforeEach
    public void setUp() {
        deque = new ArrayDeque<>();
    }

    @Test
    public void testAddFirstAndRemoveFirst() {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(3, deque.size());
        assertEquals(3, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(1, deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddLastAndRemoveLast() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(3, deque.size());
        assertEquals(3, deque.removeLast());
        assertEquals(2, deque.removeLast());
        assertEquals(1, deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddFirstAndRemoveLast() {
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        assertEquals(3, deque.size());
        assertEquals(1, deque.removeLast());
        assertEquals(2, deque.removeLast());
        assertEquals(3, deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddLastAndRemoveFirst() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(3, deque.size());
        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.removeFirst());
        assertEquals(3, deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testGet() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        assertEquals(3, deque.size());
        assertEquals(1, deque.get(0));
        assertEquals(2, deque.get(1));
        assertEquals(3, deque.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> deque.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> deque.get(3));
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }
        assertEquals(8, deque.size());
        //assertEquals(8, deque.capacity);
        deque.addLast(8);
        assertEquals(9, deque.size());
        //assertEquals(16, deque.capacity);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(deque.isEmpty());
        deque.addLast(1);
        assertFalse(deque.isEmpty());
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, deque.size());
        deque.addLast(1);
        assertEquals(1, deque.size());
        deque.addLast(2);
        assertEquals(2, deque.size());
        deque.removeFirst();
        assertEquals(1, deque.size());
    }

    @Test
    public void testPrintDeque() {
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        String expectedOutput = "1 2 3";
        StringBuilder output = new StringBuilder();
        deque.printDeque();
        // 这里假设你能够捕获控制台输出，将其存储到 output 中
        assertEquals(expectedOutput, output.toString().trim());
    }
}