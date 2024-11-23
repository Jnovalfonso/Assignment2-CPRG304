package implementations;

import utilities.Iterator;
import utilities.QueueADT;

import java.util.NoSuchElementException;

public class MyQueue<E> implements QueueADT<E> {

    // Attributes
    private MyDLL<E> q;

    // Constructor
    public MyQueue() {
        q = new MyDLL<>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot enqueue a null element.");
        }
        q.add(toAdd);
    }

    @Override
    public E dequeue() throws NoSuchElementException {
        if (q.isEmpty()) {
            throw new NoSuchElementException("Queue is empty. Cannot dequeue.");
        }
        return q.remove(0); 
    }

    @Override
    public E peek() throws NoSuchElementException {
        if (q.isEmpty()) {
            return null; 
        }
        return q.get(0); 
    }

    @Override
    public void dequeueAll() {
        q.clear(); 
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty(); 
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        return q.contains(toFind); 
    }

    @Override
    public int search(E toFind) {
        for (int i = 0; i < q.size(); i++) {
            if (q.get(i).equals(toFind)) {
                return i + 1; 
            }
        }
        return -1; 
    }

    @Override
    public Iterator<E> iterator() {
        return q.iterator(); 
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null || this.size() != that.size()) {
            return false;
        }
        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

        while (thisIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return q.toArray(); 
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        return q.toArray(holder); 
    }

    @Override
    public boolean isFull() {
        
        return false;
    }

    @Override
    public int size() {
        return q.size(); 
    }
}

