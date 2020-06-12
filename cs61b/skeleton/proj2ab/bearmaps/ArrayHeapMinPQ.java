package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class PriorityNode<T> implements Comparable<ArrayHeapMinPQ.PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(ArrayHeapMinPQ.PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((ArrayHeapMinPQ.PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    private class ArrayHeap<T> {

        private ArrayHeapMinPQ.PriorityNode[] nodes = new ArrayHeapMinPQ.PriorityNode[100];
        private HashMap <T, Integer> itemIndexMap = new HashMap<>();
        private int size;

        private void throwErrorIfEmpty() {
            if (this.size() == 0) {
                throw new NoSuchElementException();
            }
        }

        private int parentsIndex(int i) {
            return (i - 1) / 2;
        }

        private int leftChildIndex(int i) {
            return i * 2 + 1;
        }

        private int rightChildIndex(int i) {
            return (i + 1) * 2;
        }

        private int swimUp(int cIndex) {
            int pIndex = parentsIndex(cIndex);
            while (nodes[pIndex].getPriority() > nodes[cIndex].getPriority()) {
                PriorityNode pNode = nodes[pIndex];
                nodes[pIndex] = nodes[cIndex];
                nodes[cIndex] = pNode;
                itemIndexMap.put((T)nodes[cIndex].getItem(), cIndex);
                cIndex = pIndex;
                pIndex = parentsIndex(cIndex);
            }

            return cIndex;
        }

        private int minChildIndex(int pIndex) {
            int lIndex = leftChildIndex(pIndex);
            int rIndex = rightChildIndex(pIndex);
            if (lIndex >= size) {
                return -1;
            }
            if (rIndex >= size) {
                return lIndex;
            }
            if (nodes[lIndex].getPriority() < nodes[rIndex].getPriority()) {
                return lIndex;
            }
            return rIndex;
        }
        private int swimDown(int pIndex) {

            PriorityNode pNode = nodes[pIndex];
            int childIndex = minChildIndex(pIndex);
            while (childIndex > 0) {
                if (pNode.getPriority() > nodes[childIndex].getPriority()) {
                    nodes[pIndex] = nodes[childIndex];
                    nodes[childIndex] = pNode;
                    itemIndexMap.put((T)nodes[pIndex].getItem(), pIndex);
                    pIndex = childIndex;
                    childIndex = minChildIndex(pIndex);
                } else {
                    break;
                }
            }

            return pIndex;
        }

        private void deleteLast() {
            T t = (T) nodes[size - 1].getItem();
            itemIndexMap.remove(t);
            nodes[size - 1] = null;
        }

        private void resize(int capacity) {
            ArrayHeapMinPQ.PriorityNode[] newNodes = new ArrayHeapMinPQ.PriorityNode[capacity];
            for (int i = 0; i < size; i++) {
                newNodes[i] = this.nodes[i];
            }

            this.nodes = newNodes;
        }

        public void add(ArrayHeapMinPQ.PriorityNode p) {
            if (itemIndexMap.containsKey(p.item)) {
                throw new IllegalArgumentException("item is already present");
            }
            if (size == this.nodes.length) {
                resize(this.nodes.length * 2);
            }

            nodes[size] = p;
            int i = swimUp(size);
            size += 1;
            itemIndexMap.put((T)p.getItem(), i);
        }

        /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
        public T getSmallest() {
            this.throwErrorIfEmpty();
            PriorityNode<T> n = nodes[0];
            return n.getItem();
        }

        /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
        public T removeSmallest() {
            this.throwErrorIfEmpty();
            PriorityNode<T> n = nodes[0];
            nodes[0] = nodes[size - 1];
            nodes[size - 1] = n;
            deleteLast();
            size -= 1;
            swimDown(0);
            if (size * 4 < this.nodes.length) {
                this.resize(this.nodes.length / 2);
            }
            return n.getItem();
        }

        /* Returns the number of items in the PQ. */
        public int size() {
            return size;
        }

        public boolean contains(T item) {
            return itemIndexMap.containsKey(item);
        }

        /* Changes the priority of the given item. Throws NoSuchElementException if the item
         * doesn't exist. */
        public void changePriority(T item, double priority) {
            if (itemIndexMap.containsKey(item) == false) {
                throw new NoSuchElementException("item doesn't exist");
            }
            int index = itemIndexMap.get(item);
            int pIndex = this.parentsIndex(index);
            this.nodes[index].setPriority(priority);
            if (this.nodes[index].getPriority() < this.nodes[pIndex].getPriority()) {
                index = swimUp(index);
            } else {
                index = swimDown(index);
            }

            itemIndexMap.put(item, index);
        }
    }

    private ArrayHeap<T> heap;

    public ArrayHeapMinPQ() {
        heap = new ArrayHeap<>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        heap.add(new ArrayHeapMinPQ.PriorityNode(item, priority));
    }

    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return heap.contains(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        return (T)heap.getSmallest();
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        return (T)heap.removeSmallest();
    }

    /* Returns the number of items in the PQ. */
    public int size() {
        return heap.size();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        heap.changePriority(item, priority);
    }
}
