package ru.spbstu.telematics.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SkipList<T extends Comparable<T>> implements Iterable<T> {

    private static class Node<T> {
        T value;
        Node<T>[] forward;

        @SuppressWarnings("unchecked")
        Node(int level, T value) {
            forward = (Node<T>[]) new Node[level + 1];
            this.value = value;
        }
    }

    private static final int MAX_LEVEL = 16;
    private final Node<T> head = new Node<>(MAX_LEVEL, null);
    private int level = 0;
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean contains(T value) {
        Node<T> current = head;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
        }
        current = current.forward[0];
        return current != null && current.value.equals(value);
    }

    public boolean add(T value) {
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];
        if (current != null && current.value.equals(value)) {
            return false; // Duplicate, no insertion
        }

        int nodeLevel = randomLevel();
        if (nodeLevel > level) {
            for (int i = level + 1; i <= nodeLevel; i++) {
                update[i] = head;
            }
            level = nodeLevel;
        }

        Node<T> newNode = new Node<>(nodeLevel, value);
        for (int i = 0; i <= nodeLevel; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }
        size++;
        return true;
    }

    public boolean remove(T value) {
        Node<T>[] update = new Node[MAX_LEVEL + 1];
        Node<T> current = head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];
        if (current == null || !current.value.equals(value)) {
            return false;
        }

        for (int i = 0; i <= level; i++) {
            if (update[i].forward[i] != current) {
                break;
            }
            update[i].forward[i] = current.forward[i];
        }

        while (level > 0 && head.forward[level] == null) {
            level--;
        }
        size--;
        return true;
    }

    private int randomLevel() {
        int level = 0;
        while (Math.random() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head.forward[0];

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T value = current.value;
                current = current.forward[0];
                return value;
            }
        };
    }
}

