public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> deque = new ArrayDeque<>();
        for (Character c : word.toCharArray() ) {
            deque.addLast(c);
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        while (d.size() > 1) {
            if (d.removeFirst() != d.removeLast()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() < 2) {
            return true;
        }
        int l = 0;
        int r = word.length() - 1;
        while (l < r) {
            if (cc.equalChars(word.charAt(l), word.charAt(r)) == false) {
                return false;
            }
            l ++;
            r --;
        }
        return true;
    }
}