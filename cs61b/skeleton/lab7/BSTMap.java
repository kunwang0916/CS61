import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    public class BST<K extends Comparable<K>, V> {
        private Node root;
        private class Node {
            K key;
            V value;
            private Node left, right;
            private int size;
            public Node(K key, V value, int size) {
                this.key = key;
                this.value = value;
                this.size = size;
            }
        }

        public BST() {}

        public boolean isEmpty() {
            return size() == 0;
        }

        public int size() {
            return size(root);
        }

        private int size(Node n) {
            if (n == null) {
                return 0;
            }
            return n.size;
        }

        public boolean contains(K key) {
            if (key == null)  {
                throw new IllegalArgumentException("calls containss with a null key");
            }
            return get(key) != null;
        }

        public V get(K key) {
            return get(root, key);
        }

        private V get(Node n, K key) {
            if (key == null) {
                throw new IllegalArgumentException("calls get() with a null key");
            }
            if (n == null) {
                return null;
            }
            int cmp = key.compareTo(n.key);
            if (cmp > 0) {
                return get(n.right, key);
            } else if ( cmp < 0) {
                return get(n.left, key);
            } else {
                return n.value;
            }
        }

        public void put(K key, V value) {
            if (key == null) {
                throw new IllegalArgumentException("calls put() with a null key");
            }

            if (value == null) {
                delete(key);
                return;
            }

            root = put(root, key, value);
        }

        private Node put(Node n, K key, V value) {
            if (n == null) {
                return new Node(key, value, 1);
            }
            int cmp = key.compareTo(n.key);
            if (cmp > 0) {
                put(n.right, key, value);
            } else if (cmp < 0) {
                put(n.left, key, value);
            } else {
                n.value = value;
            }
            n.size = 1 + size(n.left) + size(n.right);
            return n;
        }

        public void delete(K key) {
            if (key == null) {
                throw new IllegalArgumentException("calls delete() with a null key");
            }
            root = delete(root, key);
        }

        private Node delete(Node n, K key) {
            if (n == null) {
                return null;
            }

            int cmp = key.compareTo(n.key);
            if (cmp < 0) {
                delete(n.left, key);
            } else if (cmp > 0) {
                delete(n.right, key);
            } else {
                if (n.right == null) {
                    return n.left;
                }
                if (n.left == null) {
                    return n.right;
                }
                Node t = n;
                n = min(t.right);
                n.right = deleteMin(t.right);
                n.left = t.left;
            }

            n.size = size(n.left) + size(n.right) + 1;
            return n;
        }

        public K min() {
            if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null) {
                return x;
            } else {
                return min(x.left);
            }
        }

        public void deleteMin() {
            if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
            root = deleteMin(root);
        }

        private Node deleteMin(Node x) {
            if (x.left == null) return x.right;
            x.left = deleteMin(x.left);
            x.size = size(x.left) + size(x.right) + 1;
            return x;
        }

        public List<K> keysInOrder() {
            return keysInOrder(root);
        }

        private List<K> keysInOrder(Node n) {
            List<K> r = new ArrayList<>();
            if (n == null) {
                return r;
            }

            r.addAll(keysInOrder(n.left));
            r.add(n.key);
            r.addAll(keysInOrder(n.right));

            return r;
        }
    }

    private BST bst;

    public BSTMap() {
        bst = new BST<>();
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        bst = new BST<>();
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return bst.contains(key);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        return (V)bst.get(key);
    }


    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return bst.size();
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        bst.put(key, value);
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException("not support for Lab 7");
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException("not support for Lab 7");
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("not support for Lab 7");
    }

    public Iterator iterator() {
        throw new UnsupportedOperationException("not support for Lab 7");
    }

    void printInOrder() {
        List<K> list = bst.keysInOrder();
        for (K k : list) {
            System.out.print(k);
        }
    }
}
