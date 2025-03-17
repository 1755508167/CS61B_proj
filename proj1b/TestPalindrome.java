import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void isPalindrome() {
        Palindrome p = new Palindrome();
        Deque<Character> deque = p.wordToDeque("lol");
        assertTrue(palindrome.isPalindrome("lol"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("anana"));
        assertTrue(palindrome.isPalindrome("&"));
        assertFalse(palindrome.isPalindrome("Aa"));
        assertFalse(palindrome.isPalindrome("caaaaaaaa"));
    }

    @Test
    public void testisPalindrome() {
        Palindrome p = new Palindrome();
        assertFalse(p.isPalindrome("cat", new OffByOne()));
        assertTrue(p.isPalindrome("flake", new OffByOne()));
        assertTrue(p.isPalindrome("a", new OffByOne()));
        assertTrue(p.isPalindrome("", new OffByOne()));
        assertFalse(p.isPalindrome("Anb",new OffByOne()));
    }

    @Test
    public void testIsPalindromeInOffByN() {
        assertTrue(palindrome.isPalindrome("FLAKE", new OffByN(1)));
        assertTrue(palindrome.isPalindrome("flke", new OffByN(1)));
        assertTrue(palindrome.isPalindrome("fl&a%ke", new OffByN(1)));
        assertFalse(palindrome.isPalindrome("aaaa", new OffByN(1)));
        assertTrue(palindrome.isPalindrome("fFaHh", new OffByN(2)));
        assertTrue(palindrome.isPalindrome("fh", new OffByN(2)));
    }
}
