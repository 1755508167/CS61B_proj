import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class digitTest {
    @Test
    public void testDigit(){
        String[] strings = new String[]{"ab","abe", "cf", "ad", "da", "ce"};
        assertEquals(0, RadixSort.digit("ab",0,3));
        assertEquals('a',RadixSort.digit("ab",2,3));
        System.out.println(RadixSort.digit("ab",2,3));
    }
}
