package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import static edu.princeton.cs.algs4.StdOut.println;

/**
 * Created by mkhoshneshin on 1/26/17.
 */

public class PercolationStats {
    private int N, NT;

    private double mu, std;

    public PercolationStats(int n, int trials) {   // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        N = n;
        NT = trials;
        runSimulation();
    }

    private double oneRun() {
        Percolation perc = new Percolation(N);
        for (int idx : StdRandom.permutation(N * N)) {
            int row = idx / N + 1;
            int col = idx - (row - 1) * N + 1;
            perc.open(row, col);
            if (perc.percolates()) break;
        }
        return ((double) perc.numberOfOpenSites()) / ((double) N * N);
    }

    private void runSimulation() {
        double[] ratios = new double[NT];
        for (int i = 0; i < NT; i++) {
            ratios[i] = oneRun();
        }
        mu = StdStats.mean(ratios);
        std = StdStats.stddev(ratios);
    }

    public double mean() {                       // sample mean of percolation threshold
        return mu;
    }

    public double stddev() {                      // sample standard deviation of percolation threshold
        return std;
    }

    public double confidenceLo() {                 // low  endpoint of 95% confidence interval
        return mu - 1.96 * std / Math.sqrt(NT);
    }

    public double confidenceHi() {                // high endpoint of 95% confidence interval
        return mu + 1.96 * std / Math.sqrt(NT);
    }

    public static void main(String[] args) {       // test client (described below)
        int n = 200;
        int t = 100;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats stats = new PercolationStats(n, t);
        println("mean = " + stats.mean());
        println("std = " + stats.stddev());
        println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}