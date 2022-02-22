/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8_puzzle_ai_project;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
* the implementation of queue
 */
public class MyQueue<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int N;               // number of elements on queue

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty queue.
     */
    public MyQueue() {
        first = null;
        last = null;
        N = 0;
    }

    public void clear() {
        first = null;
        last = null;
         N= 0;
    }

   public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this queue.
     */
    public int size() {
        return N;
    }

    /**
     * Returns the item least recently added to this queue.
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }

    public void enqueue(Item item) {

        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        N++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }


    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public void addQueue(MyQueue<Item> queue) {
        if (!queue.isEmpty()) {


            Node<Item> oldFirst = first;

            if (isEmpty()) {
                first = queue.first;
                last = queue.last;
            } else {
                first = queue.first;
                queue.last.next = oldFirst;
            }

            N = N + queue.size();
        }

    }

}
