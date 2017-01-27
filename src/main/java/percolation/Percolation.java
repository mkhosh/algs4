package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

import static edu.princeton.cs.algs4.StdOut.println;

/**
 * Created by mkhoshneshin on 1/25/17.
 */
public class Percolation {
    private int N;
    private boolean[] openStatus;
    private boolean[] rootReachedBottom;
    private WeightedQuickUnionUF uf;
    private int topSite;
    private int openSiteCount;

    public Percolation(int n) {               // create n-by-n grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        N = n;
        openStatus = new boolean[N * N];
        Arrays.fill(openStatus, false);
        rootReachedBottom = new boolean[N * N + 1];
        Arrays.fill(rootReachedBottom, false);
        uf = new WeightedQuickUnionUF(N * N + 1);
        topSite = N * N;
        openSiteCount = 0;
    }

    private void setRootReachedBottom(int idx, boolean val) {
        rootReachedBottom[uf.find(idx)] = val;
    }

    private boolean getRootReachedbottom(int idx) {
        return rootReachedBottom[uf.find(idx)];
    }

    private int getIdx(int row, int col) {
        return (row - 1) * N + (col - 1);
    }

    private boolean valid(int rc) {
        return rc >= 1 && rc <= N;
    }

    private void validate(int row, int col) {
        if (!valid(row) || !valid(col))
            throw new IndexOutOfBoundsException();
    }

    private boolean addNb(int idx, int row, int col, boolean curReaching) {
        if (valid(row) && valid(col) && isOpen(row, col)) {
            curReaching |= getRootReachedbottom(getIdx(row, col));
            uf.union(idx, getIdx(row, col));
        }
        return curReaching;

    }

    public void open(int row, int col) {   // open site (row, col) if it is not open already
        validate(row, col);
        int idx = getIdx(row, col);
        if (openStatus[idx]) return;
        boolean curReachingToBottom = (row == N) || getRootReachedbottom(idx);
        openStatus[idx] = true;
        curReachingToBottom = addNb(idx, row - 1, col, curReachingToBottom); //top
        curReachingToBottom = addNb(idx, row + 1, col, curReachingToBottom); //bottom
        curReachingToBottom = addNb(idx, row, col + 1, curReachingToBottom); //right
        curReachingToBottom = addNb(idx, row, col - 1, curReachingToBottom); //left
        if (row == 1) {
            curReachingToBottom |= getRootReachedbottom(topSite);
            uf.union(topSite, getIdx(1, col));
        }
        setRootReachedBottom(idx, curReachingToBottom);

        openSiteCount++;
    }

    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        validate(row, col);
        return openStatus[getIdx(row, col)];
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        validate(row, col);
        return uf.connected(topSite, getIdx(row, col));
    }

    public int numberOfOpenSites() {     // number of open sites
        return openSiteCount;
    }

    public boolean percolates() {        // does the system percolate?
        return getRootReachedbottom(topSite);
    }

    public static void main(String[] args) {  // test client (optional)
        println("hello");
    }
}
