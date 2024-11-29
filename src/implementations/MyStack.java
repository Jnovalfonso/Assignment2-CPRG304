package implementations;
import java.util.EmptyStackException;
import utilities.StackADT;
import utilities.Iterator;
import java.util.NoSuchElementException;
/**
* This class represents a stack data structure.
* @param <E> the type of elements in the stack
*/
public class MyStack<E> implements StackADT<E> {
   private MyArrayList<E> stack;
   public MyStack() {
       stack = new MyArrayList<>();
   }
  /**
    * Pushes an item onto the stack.
    */
   @Override
   public void push(E element) {
       if (element == null) throw new NullPointerException("Cannot push null element onto stack");
       stack.add(element);
   }
	@Override
	public E pop() {
	   if (isEmpty()) {
	       throw new EmptyStackException();
	   }
	   return stack.remove(stack.size() - 1);
	}
	@Override
	public E peek() {
	   if (isEmpty()) {
	       throw new EmptyStackException();
	   }
	   return stack.get(stack.size() - 1);
	}

   @Override
   public boolean isEmpty() {
       return stack.isEmpty();
   }
   @Override
   public int size() {
       return stack.size();
   }
   @Override
   public void clear() {
       stack.clear();
   }
   @Override
   public boolean equals(StackADT<E> that) {
       if (that == null || this.size() != that.size()) return false;
       Iterator<E> thisIt = this.iterator();
       Iterator<E> thatIt = (Iterator<E>) that.iterator();
       while (thisIt.hasNext()) {
           if (!thisIt.next().equals(thatIt.next())) {
               return false;
           }
       }
       return true;
   }
   @Override
   public boolean contains(E obj) {
       if (obj == null) throw new NullPointerException("Cannot search for null element in stack");
       return stack.contains(obj);
   }
   @Override
   public int search(E obj) {
       if (obj == null) throw new NullPointerException("Cannot search for null element in stack");
       for (int i = stack.size() - 1, pos = 1; i >= 0; i--, pos++) {
           if (obj.equals(stack.get(i))) {
               return pos;
           }
       }
       return -1;
   }
   @Override
   public Iterator<E> iterator() {
       return new Iterator<E>() {
           private int currentIndex = stack.size() - 1;
           @Override
           public boolean hasNext() {
               return currentIndex >= 0;
           }
           @Override
           public E next() {
               if (!hasNext()) {
                   throw new NoSuchElementException();
               }
               return stack.get(currentIndex--);
           }
       };
   }
   @Override
   public Object[] toArray() {
       Object[] result = new Object[stack.size()];
       for (int i = 0; i < stack.size(); i++) {
           result[i] = stack.get(stack.size() - 1 - i);
       }
       return result;
   }
   @Override
   public E[] toArray(E[] copy) {
       if (copy == null) throw new NullPointerException("The provided array is null");
       if (copy.length < stack.size()) {
           copy = (E[]) java.lang.reflect.Array.newInstance(copy.getClass().getComponentType(), stack.size());
       }
       for (int i = 0; i < stack.size(); i++) {
           copy[i] = stack.get(stack.size() - 1 - i);
       }
       return copy;
   }
	@Override
	public boolean stackOverflow() {
		return false;
	}
}