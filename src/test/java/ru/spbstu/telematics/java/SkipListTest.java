package ru.spbstu.telematics.java;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.TreeSet;
import java.util.Iterator;

public class SkipListTest {

    @Test
    public void testAddAndContains() {
        SkipList<Integer> skipList = new SkipList<>();
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i = 1; i <= 10; i++) {
            skipList.add(i);
            treeSet.add(i);
        }

        for (int i = 1; i <= 10; i++) {
            assertTrue(skipList.contains(i));
            assertTrue(treeSet.contains(i));
        }

        assertEquals(treeSet.size(), skipList.size());
    }

    @Test
    public void testRemove() {
        SkipList<Integer> skipList = new SkipList<>();
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i = 1; i <= 10; i++) {
            skipList.add(i);
            treeSet.add(i);
        }

        skipList.remove(5);
        treeSet.remove(5);

        assertFalse(skipList.contains(5));
        assertFalse(treeSet.contains(5));
        assertEquals(treeSet.size(), skipList.size());
    }

    @Test
    public void testIteration() {
        SkipList<Integer> skipList = new SkipList<>();
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i = 1; i <= 5; i++) {
            skipList.add(i);
            treeSet.add(i);
        }

        Iterator<Integer> skipListIterator = skipList.iterator();
        Iterator<Integer> treeSetIterator = treeSet.iterator();

        while (skipListIterator.hasNext() && treeSetIterator.hasNext()) {
            assertEquals(skipListIterator.next(), treeSetIterator.next());
        }
    }
}

