package bearmaps;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    public void testSize() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        assertEquals(a.size(), 0);
        a.add(3, 0.2);
        assertEquals(a.size(), 1);
        a.add(4, 0.3);
        assertEquals(a.size(), 2);
        a.removeSmallest();
        assertEquals(a.size(), 1);
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        assertFalse(a.contains(1));
        a.add(3, 0.2);
        assertFalse(a.contains(1));
        assertTrue(a.contains(3));
        a.add(4, 0.1);
        assertFalse(a.contains(1));
        assertTrue(a.contains(4));
        assertTrue(a.contains(3));
        a.removeSmallest();
        assertFalse(a.contains(1));
        assertFalse(a.contains(4));
        assertTrue(a.contains(3));
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(3, 0.2);
        assertEquals(3, a.getSmallest().intValue());
        a.add(4, 0.1);
        assertEquals(4, a.getSmallest().intValue());
        a.removeSmallest();
        assertEquals(3, a.getSmallest().intValue());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(3, 0.2);
        assertEquals(3, a.getSmallest().intValue());
        a.add(4, 0.1);
        assertEquals(4, a.getSmallest().intValue());
        a.removeSmallest();
        assertEquals(3, a.getSmallest().intValue());
        a.add(10, 0.8);
        a.add(5, 0.01);
        assertEquals(5, a.getSmallest().intValue());
        a.removeSmallest();
        assertEquals(3, a.getSmallest().intValue());
        a.removeSmallest();
        assertEquals(10, a.getSmallest().intValue());
    }

    @Test
    public void testResize() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> n = new NaiveMinPQ<>();
        Random rand = new Random();
        int testSize = 1000;
        for (int i = 0; i < testSize; i++) {
            double p = rand.nextDouble();
            a.add(i, p);
            n.add(i, p);
            assertEquals(a.getSmallest(), n.getSmallest());
        }

        while (a.size() > 0) {
            assertEquals(a.getSmallest(), n.getSmallest());
            a.removeSmallest();
            n.removeSmallest();
        }
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> n = new NaiveMinPQ<>();
        Random rand = new Random();
        int testSize = 1000;
        for (int i = 0; i < testSize; i++) {
            double p = rand.nextDouble();
            a.add(i, p);
            n.add(i, p);
            assertEquals(a.getSmallest(), n.getSmallest());
        }

        for (int i = 0; i < testSize; i++) {
            double p = rand.nextDouble();
            a.changePriority(i, p);
            n.changePriority(i, p);
            assertEquals(a.getSmallest(), n.getSmallest());
        }
    }
}
