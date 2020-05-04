public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int firstIndex;

    final private double DOWN_SIZE_RATIO = 0.25;
    final private int UP_SIZE_FACTOR = 2;
    final private int DEFAULT_SIZE = 8;

    public ArrayDeque() {
        items = (Item [])new Object[DEFAULT_SIZE];
        size = 0;
        firstIndex = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (Item [])new Object[other.size()];
        size = other.size;
        for (int i = 0; i < other.size(); i++) {
            addLast((Item) other.get(i));
        }
    }

    private void resizeTo(int capacity) {
        Item[] newItems = (Item [])new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(firstIndex + i) % items.length];
        }
        firstIndex = 0;
        items = newItems;
    }

    private int nextLastIndex() {
        return (firstIndex + size) % items.length;
    }

    private int currentLastIndex() {
        return (firstIndex + size - 1) % items.length;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Item i) {
        if (size == items.length) {
            resizeTo(size * UP_SIZE_FACTOR);
        }
        items[nextLastIndex()] = i;
        size++;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public Item removeLast() {
        if (size < items.length * DOWN_SIZE_RATIO
                && items.length >= DEFAULT_SIZE * 2) {
            resizeTo(items.length / 2);
        }
        Item x = items[currentLastIndex()];
        items[currentLastIndex()] = null;
        size -= 1;
        return x;
    }

    public void addFirst(Item i) {
        if (size == items.length) {
            resizeTo(size * UP_SIZE_FACTOR);
        }
        if (size > 0) {
            firstIndex = (firstIndex + items.length - 1) % items.length;
        }
        items[firstIndex] = i;
        size++;
    }

    public Item removeFirst() {
        if (size < items.length * DOWN_SIZE_RATIO
                && items.length >= DEFAULT_SIZE * 2) {
            resizeTo(items.length / 2);
        }
        Item x = items[firstIndex];
        items[firstIndex] = null;
        firstIndex = (firstIndex + 1) % items.length;
        size -= 1;
        return x;
    }

    /** Returns the item from the back of the list. */
    public Item getLast() {
        if (size == 0) {
            return null;
        }
        return items[size];
    }

    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        int index = (firstIndex + i) % items.length;
        return items[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i ++) {
            int index = (firstIndex + i) % items.length;
            System.out.println(items[index]);
        }
    }
}
