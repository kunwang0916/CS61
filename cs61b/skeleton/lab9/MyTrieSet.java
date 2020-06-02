import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61B {
    private Node root;

    private static class Node {
        private boolean isKey;
        private HashMap<Character, Node> children;

        private Node(boolean isKey) {
            this.isKey = isKey;
            this.children = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node(false);
    }

    /** Clears all items out of Trie */
    public void clear() {
        root = new Node(false);
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        Node n = this.root;
        for (int i = 0; i < key.length(); i++) {
            Character c = key.charAt(i);
            n = n.children.get(c);
            if (n == null) {
                return false;
            }
        }

        return n.isKey;
    }

    /** Inserts string KEY into Trie */
    public void add(String key) {
        Node n = this.root;
        for (int i = 0; i < key.length(); i++) {
            Character c = key.charAt(i);
            if (n.children.containsKey(c) == false) {
                n.children.put(c, new Node(false));
            }
            n = n.children.get(c);
        }
        n.isKey = true;
    }

    private void addKeysFromNode(Node node, String str, ArrayList<String> result) {
        if (node == null) {
            return;
        }

        if (node.isKey) {
            result.add(str);
        }

        for (Character c : node.children.keySet()) {
            Node cNode = node.children.get(c);
            String cString = str + c;
            addKeysFromNode(cNode, cString, result);
        }
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> result = new ArrayList<>();
        Node n = this.root;
        for (int i = 0; i < prefix.length(); i++) {
            Character c = prefix.charAt(i);
            n = n.children.get(c);
            if (n == null) {
                return result;
            }
        }

        addKeysFromNode(n, prefix, result);
        return result;
    }

    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
