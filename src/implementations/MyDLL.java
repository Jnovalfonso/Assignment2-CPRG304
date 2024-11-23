package implementations;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<E> implements ListADT<E> {
	private MyDLLNode<E> head;
	private MyDLLNode<E> tail;
	private int size;
	
	public int size() {
		return size;
	}
	
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}
	
	@Override
	public boolean add (int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		
		if (toAdd == null) {
	        throw new NullPointerException("Cannot add null element to the list.");
	    }
		
		if (index < 0 || index > size) { 
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }
		
		MyDLLNode newNode = new MyDLLNode(toAdd);
		
		if (index == 0) {
			newNode.setNext(head);
			if (head != null) {
	            head.setPrev(newNode);
	        }
	        head = newNode;
	        if (tail == null) {
	        	tail = newNode;
	        }
		} else if (index == size) {
			newNode.setPrev(tail);
			if (tail != null) {
				tail.setNext(newNode);
			}
			tail = newNode;	
		} else {
			MyDLLNode current = head;
			
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
			
			newNode.setNext(current);
			newNode.setPrev(current.getPrev());

			if (current.getPrev() != null) {
			    current.getPrev().setNext(newNode);
			}
			current.setPrev(newNode);
		}
		
		size ++;
		return true;
	}

	@Override
	public boolean add(Object toAdd) throws NullPointerException {
		if (toAdd == null) {
	        throw new NullPointerException("Cannot add null element to the list.");
	    }
	    
	    MyDLLNode newNode = new MyDLLNode(toAdd);

	    if (tail == null) {
	        head = newNode;
	        tail = newNode;
	    } else {
	        tail.setNext(newNode);
	        newNode.setPrev(tail);
	        tail = newNode;
	    }

	    size++;
	    return true;
	}

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if (toAdd == null) {
	        throw new NullPointerException("Cannot add elements from a null collection.");
	    }
		
		for (int i = 0; i < toAdd.size(); i++) {
	        E element = toAdd.get(i); 
	        this.add(element);
	    }
	    return true;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size || this.isEmpty()) { 
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }
		
		MyDLLNode<E> current = head;
		
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		
		
		return current.getElement();
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) { 
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }
	    
	    E element = null;

	    if (index == 0) { 
	        element = head.getElement();
	        
	        if (head == tail) { 
	            head = null;
	            tail = null;
	        } else {
	            head = head.getNext();
	            head.setPrev(null);
	        }
	    }
	    else if (index == size - 1) { 
	        element = tail.getElement();
	        
	        if (head == tail) { 
	            head = null;
	            tail = null;
	        } else {
	            tail = tail.getPrev();
	            tail.setNext(null);
	        }
	    }
	    else {
	        MyDLLNode<E> current = head;
	        
	        for (int i = 0; i < index; i++) {
	            current = current.getNext();
	        }
	        
	        element = current.getElement();

	        MyDLLNode<E> currentPrev = current.getPrev();
	        MyDLLNode<E> currentNext = current.getNext();
	        
	        currentPrev.setNext(currentNext);
	        currentNext.setPrev(currentPrev);
	    }

	    size--;
	    return element;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		if (toRemove == null) {
	        throw new NullPointerException("Cannot add elements from a null collection.");
	    }
		
		MyDLLNode<E> current = head;

	    while (current != null) {
	        if (current.getElement().equals(toRemove)) { 
	            E element = current.getElement();

	            if (current == head) {
	                head = current.getNext();
	                if (head != null) {
	                    head.setPrev(null);
	                } else {
	                    tail = null; 
	                }
	            }
	            else if (current == tail) {
	                tail = current.getPrev();
	                if (tail != null) {
	                    tail.setNext(null);
	                }
	            }
	            else {
	                MyDLLNode<E> currentPrev = current.getPrev();
	                MyDLLNode<E> currentNext = current.getNext();
	                currentPrev.setNext(currentNext);
	                currentNext.setPrev(currentPrev);
	            }

	            size--; 
	            return element; 
	        }

	        current = current.getNext();
	    }
		
		return null;
	}

	@Override
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
	        throw new NullPointerException("Cannot set null as the element.");
	    }

	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	    }

	    MyDLLNode<E> current = head;
	    
	    for (int i = 0; i < index; i++) {
	        current = current.getNext();
	    }

	    E oldElement = current.getElement();
	    current.setElement(toChange);

	    return oldElement;
	}

	@Override
	public boolean isEmpty() {
		return head == null;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if (toFind == null) {
	        throw new NullPointerException("Cannot search for a null element.");
	    }
		
		MyDLLNode<E> current = head;
		
		while (current != null) {
			if (current.getElement().equals(toFind)) {
				return true;
			} else {
				current = current.getNext();
			}
		}
		
		return false;
	}

	@Override
	public E[] toArray(E[] toHold) throws NullPointerException {
	    if (toHold == null) {
	        throw new NullPointerException("The provided array cannot be null.");
	    }

	    int size = this.size;  
	    MyDLLNode<E> current = head;
	    
	    if (toHold.length < size) {
	        toHold = (E[]) Array.newInstance(toHold.getClass().getComponentType(), size);
	    }

	    for (int i = 0; i < size; i++) {
	        toHold[i] = current.getElement();
	        current = current.getNext();
	    }

	    return toHold;
	}

	@Override
	public Object[] toArray() {
	    Object[] result = new Object[size]; 
	    MyDLLNode<E> current = head;
	    
	    for (int i = 0; i < size; i++) {
	        result[i] = current.getElement();
	        current = current.getNext();
	    }

	    return result;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyDLLIterator();		
	}
	
	private class MyDLLIterator implements Iterator<E> {
        private MyDLLNode<E> current;

        public MyDLLIterator() {
            current = head;  
        }

        @Override
        public boolean hasNext() {
            return current != null; 
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list.");
            }

            E element = current.getElement(); 
            current = current.getNext(); 
            return element;
        }
    }
}
