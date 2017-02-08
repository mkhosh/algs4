package hw2;

import java.util.Iterator;

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

        private Node(Item it, Node ne, Node pr) {
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
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {

    }

    // add the item to the end
    public void addLast(Item item) {

    }

    // remove and return the item from the front
    public Item removeFirst() {

    }

    // remove and return the item from the end
    public Item removeLast() {

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {

    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

    // check internal invariants
    private boolean check() {
        if (n < 0) {
            return false;
        } else if (n == 0) {
            if (first != null) return false;
            if (last != null) return false;
        } else if (n == 1) {
            if (first == null || last == null) return false;
            if (first != last) return false;
            if (first.next != null) return false;
        } else {
            if (first == null || last == null) return false;
            if (first == last) return false;
            if (first.next == null) return false;
            if (last.next != null) return false;

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }

        return true;
    }
}
