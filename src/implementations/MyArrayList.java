package implementations;

import java.util.Iterator;
import java.util.NoSuchElementException;

import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> {
    private E[] elements;
    private int size;

    public MyArrayList() {
        elements = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        elements = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Element cannot be null");
        }
        if (size == elements.length) {
            resize();
        }
        elements[size++] = toAdd;
        return true;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Element cannot be null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if (size == elements.length) {
            resize();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Collection cannot be null");
        }
        utilities.Iterator<? extends E> iterator = toAdd.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        return elements[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        E removedElement = elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Element cannot be null");
        }
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(toRemove)) {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Element cannot be null");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }
        E oldElement = elements[index];
        elements[index] = toChange;
        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Element cannot be null");
        }
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array cannot be null");
        }
        if (toHold.length < size) {
            return (E[]) java.util.Arrays.copyOf(elements, size, toHold.getClass());
        }
        System.arraycopy(elements, 0, toHold, 0, size);
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    @Override
    public Object[] toArray() {
        return java.util.Arrays.copyOf(elements, size);
    }

    @Override
    public utilities.Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    private void resize() {
        E[] newElements = (E[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    private class MyArrayListIterator implements Iterator<E>, utilities.Iterator<E> {
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < size;
        }

        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return elements[currentIndex++];
        }
    }
}
