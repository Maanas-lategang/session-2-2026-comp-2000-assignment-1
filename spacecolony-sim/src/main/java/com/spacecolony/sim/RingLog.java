package com.spacecolony.sim;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Fixed-capacity circular buffer used to keep a rolling log of recent
 * simulation events without unbounded memory growth.
 */
public class RingLog<T> implements Iterable<T> {

    private final Object[] elements;
    private int start;
    private int size;

    public RingLog(int capacity) {
        this.elements = new Object[capacity];
        this.start = 0;
        this.size = 0;
    }

    public void add(T value) {
        int writeIndex = (start + size) % elements.length;
        if (size < elements.length) {
            elements[writeIndex] = value;
            size++;
        } else {
            elements[start] = value;
            start = (start + 1) % elements.length;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[(start + index) % elements.length];
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(cursor++);
            }
        };
    }
}
