public class Palindrome {
    /*
    给定一个String，把这个字符串拆成一个个字符，然后放进一个队列里。比如把"apple"拆成'a','p','p'...
    */
    public Deque<Character> wordToDeque(String word) {
        int length = word.length();
        LinkedListDeque<Character> listDeque = new LinkedListDeque<>();
        for (int i = 0; i < length; i++) {
            listDeque.addLast(word.charAt(i));
        }
        return listDeque;
    }

    //如果给定的word是Palindrome(回文)，返回true，否则返回false
    public boolean isPalindrome(String word) {
        Palindrome p = new Palindrome();
        Deque<Character> deque = p.wordToDeque(word);
        int j = deque.size() - 1;
        for (int i = 0; i < deque.size(); i++) {
            if (Character.toLowerCase(deque.get(i)) != Character.toLowerCase(deque.get(j))) {
                return false;
            } else {
                j--;
                continue;
            }
        }
        return true;
    }

    //另一种实现
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            if (!cc.equalChars(Character.toLowerCase(d.removeFirst()), Character.toLowerCase(d.removeLast()))) {
                return false;
            }
        }
        return true;

    }

}
