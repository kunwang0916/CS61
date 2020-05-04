public class LinkedListDeque<T> {

    public class ListNode {
        public T item;
        ListNode next;
        ListNode pre;

        public ListNode(T i, ListNode n, ListNode p) {
            item = i;
            next = n;
            pre = p;
        }

        public ListNode(T i) {
            item = i;
            next = null;
            pre = null;
        }
    }

    private int size;
    private ListNode sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new ListNode(null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
    }

    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new ListNode(null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;

        for (int i = 0; i < other.size(); i ++) {
            addLast((T)other.get(i));
        }
    }

    public void addFirst(T item) {
        size += 1;
        ListNode node = new ListNode(item, sentinel.next, sentinel.next.pre);
        sentinel.next.pre = node;
        sentinel.next = node;
        sentinel.pre.next = node;
    }

    public void addLast(T item) {
        size += 1;
        ListNode node = new ListNode(item, sentinel.next, sentinel.next.pre);
        sentinel.pre.next = node;
        sentinel.pre = node;
        sentinel.next.pre = node;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        ListNode p = sentinel.next;
        for (int i = 0; i < size; i ++) {
            System.out.println(p.item);
            p = p.next;
        }
    }

    private T removeNode(ListNode node) {
        if (node == null) {
            return null;
        }
        node.pre.next = node.next;
        node.next.pre = node.pre;
        node.next = null;
        node.pre = null;

        size -= 1;
        return node.item;
    }

    public T removeFirst() {
        return removeNode(sentinel.next);
    }

    public T removeLast() {
        return removeNode(sentinel.pre);
    }

    public T get(int index) {
        if (index > size) {
            return null;
        }
        ListNode p = sentinel.next;
        for (int i = 0; i < index; i ++) {
            p = p.next;
        }
        return p.item;
    }

    private T getRecursive(ListNode node, int index) {
        if (index == 0) {
            return (T)node.item;
        }
        return getRecursive(node.next, index - 1);
    }

    public T getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }
}
