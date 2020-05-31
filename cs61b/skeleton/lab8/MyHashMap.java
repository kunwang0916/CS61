import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    class Bucket<K, V> {
        BucketNode<K, V> first;
    }

    class BucketNode<K, V> {
        K key;
        V value;
        BucketNode next;

        BucketNode(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

    private static int defaultInitialSize = 16;
    private static double defaultLoadFactor = 0.75;
    private Bucket[] buckets;
    private Set<K> keySet;
    private int capacity;
    private double factor;

    public MyHashMap() {
        this(defaultInitialSize,defaultLoadFactor);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, defaultLoadFactor);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new Bucket[initialSize];
        keySet = new HashSet<>();
        capacity = initialSize;
        factor = loadFactor;
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        keySet.clear();
    }

    private int keyToIndex(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    private Bucket bucketForKey(K key) {
        Bucket b = buckets[this.keyToIndex(key)];
        if (b == null) {
            b = new Bucket();
            buckets[this.keyToIndex(key)] = b;
        }

        return b;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (this.containsKey(key) == false) return null;

        Bucket b = bucketForKey(key);
        BucketNode n = b.first;
        while (n != null) {
            if (n.key.equals(key)) {
                return (V)n.value;
            }
            n = n.next;
        }

        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return keySet.size();
    }

    private void resizeTo(int newCapacity) {
        MyHashMap nhm = new MyHashMap(newCapacity, factor);
        for (K k: this.keySet()) {
            nhm.put(k, this.get(k));
        }
        this.buckets = nhm.buckets;
        this.capacity = newCapacity;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        boolean replace = keySet.contains(key);
        if (((double)this.size()) / capacity >= this.factor) {
            this.resizeTo(this.size() * 2);
        }
        keySet.add(key);
        Bucket b = bucketForKey(key);
        if (b.first == null) {
            b.first = new BucketNode(key, value);
            return;
        }
        BucketNode n = b.first;
        if (replace) {
            while (n != null) {
                if (n.key.equals(key)) {
                    n.value = value;
                    return;
                }
                n = n.next;
            }
        } else {
            while (n.next != null) {
                n = n.next;
            }
            n.next = new BucketNode(key, value);
        }
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        return this.keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
