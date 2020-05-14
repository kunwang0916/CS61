package es.datastructur.synthesizer;
//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

import java.util.Arrays;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[])new Object[capacity];
        first = 0;
        last = 0;
    }

    public int capacity() {
        return rb.length;
    }

    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        if (fillCount() == capacity()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        fillCount += 1;
        rb[last] = x;
        last = (last + 1) % capacity();
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        if (fillCount() == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        fillCount -= 1;
        T t = rb[first];
        if (fillCount == 0) {
            first = 0;
            last = 0;
        } else {
            first = (first + 1) % capacity();
        }

        return t;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (fillCount() == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
    private class ARBIterator implements Iterator<T> {
        private int index;
        public ARBIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index != last;
        }

        @Override
        public T next() {
            return rb[index];
        }
    }

    public Iterator<T> iterator() {
        return new ARBIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayRingBuffer<?> that = (ArrayRingBuffer<?>) o;
        return first == that.first &&
                last == that.last &&
                fillCount == that.fillCount &&
                Arrays.equals(rb, that.rb);
    }
}