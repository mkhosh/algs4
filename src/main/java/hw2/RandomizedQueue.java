package hw2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by mkhoshneshin on 2/8/17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }


    // return the number of items on the queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        // double size of array if necessary and recopy to front of array
        if (n == q.length) resize(2 * q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randIdx = (first + StdRandom.uniform(n)) % q.length;
        Item item = q[randIdx];
        q[randIdx] = q[first];
        q[first] = null;                            // to avoid loitering
        n--;
        first++;
        if (first == q.length) first = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == q.length / 4) resize(q.length / 2);
        return item;
    }


    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randIdx = (first + StdRandom.uniform(n)) % q.length;
        return q[randIdx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int idx = 0;
        private int[] randIds = StdRandom.permutation(n);

        public boolean hasNext() {
            return idx < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(randIds[idx] + first) % q.length];
            idx++;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }
}
