package hw2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by mkhoshneshin on 2/8/17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = 0;
        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        int idx = 0;
        for (String s : queue) {
            if (idx++ >= k) break;
            StdOut.println(s);
        }
    }

}
