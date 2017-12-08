package au.edu.uq.itee.comp3506.assn2.entities;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Implementation of a list, which designed to store call record items.
 * 
 * Memory efficiency: O(n)
 * 
 * @author Wayne
 */
public class ValueList<T> implements Iterable<T> {
	
	int N; // Size of a list
	Node<T> first;
	
	/**
	 * Node class of list.
	 */
	public static class Node<T> {
	    T item;
	    Node<T> next;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 */
	public ValueList() {
		first = null;
        N = 0;
	}
	
	/**
	 * Runtime efficiency: O(1)
	 */
	public boolean isEmpty() {
        return first == null;
    }
	
	/**
	 * Runtime efficiency: O(1)
	 */
    public int size() {
        return N;
    }
    
    /**
	 * Runtime efficiency: O(1)
	 */
    public void add(T item) {
        Node<T> oldfirst = first;
        first = new Node<T>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }
    
    /**
	 * Runtime efficiency: O(1)
	 */
    public Iterator<T> iterator() {
        return new ListIterator<T>(first);
    }
    
    /**
     * Implement iterator.
     */
	@SuppressWarnings("hiding")
	private class ListIterator<T> implements Iterator<T> {
        private Node<T> current;
        
        /**
    	 * Runtime efficiency: O(1)
    	 */
        public ListIterator(Node<T> first) {
            current = first;
        }
        
        /**
    	 * Runtime efficiency: O(1)
    	 */
        public boolean hasNext() {
        	return current != null;
        }
        
        /**
    	 * Runtime efficiency: O(1)
    	 */
        public void remove() {
        	throw new UnsupportedOperationException();
        }
        
        /**
    	 * Runtime efficiency: O(1)
    	 */
        public T next() {
            if(!hasNext()) {
            	throw new NoSuchElementException();
            }
            T t = current.item;
            current = current.next;
            return t;
        }
    }
}
