import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void test() {
        OffByN offBy5 = new OffByN(5);
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));  // false
    }
}
