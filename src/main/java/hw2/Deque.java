package hw2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by mkhoshneshin on 2/7/17.
 */
public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        private Node(Item it, Node pr, Node ne) {
            item = it;
            next = ne;
            prev = pr;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
        assert check();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item==null) throw new NullPointerException("Item is null");
        first = new Node(item, null, first);
        if (last == null) last = first;
        else first.next.prev = first;
        n++;
        assert check();
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item==null) throw new NullPointerException("Item is null");
        last = new Node(item, last, null);
        if (first == null) first = last;
        else last.prev.next = last;
        n++;
        assert check();
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        else first.prev = null;
        n--;
        assert check();
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.prev;
        if (isEmpty()) first = null;
        else last.next = null;
        n--;
        assert check();
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;


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

    // check internal invariants
    private boolean check() {
        if (first.prev != null || last.next != null) return false;
        if (n < 0) {
            return false;
        } else if (n == 0) {
            if (first != null) return false;
            if (last != null) return false;
        } else if (n == 1) {
            if (first == null || last == null) return false;
            if (first != last) return false;
            if (first.next != null) return false;
            if (last.prev != null) return false;
        } else {
            if (first == null || last == null) return false;
            if (first == last) return false;
            if (first.next == null) return false;
            if (last.next != null) return false;

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            numberOfNodes = 0;
            for (Node x = last; x != null; x = x.prev) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;

            Node firstNode = last;
            while (firstNode.prev != null) {
                firstNode = firstNode.prev;
            }
            if (first != firstNode) return false;
        }

        return true;
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }
}
