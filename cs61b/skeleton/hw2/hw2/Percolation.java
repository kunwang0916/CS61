package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF uf;
    private boolean[] openedArray;
    private int virtualBottomIndex;
    private int virtualTopIndex;
    private int openSites;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        // plus 2 for visual
        // last 1 is virtual-bottom
        // second to last is virtual-top
        if (N <= 0) {
            throw new IllegalArgumentException("N should greater than 0");
        }
        n = N;
        int size = N * N;
        uf = new WeightedQuickUnionUF( size + 2);
        openedArray = new boolean[size];
        virtualBottomIndex = size + 1;
        virtualTopIndex = size;
    }

    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col) == false) {
            openSites += 1;
        }
        int index = xyTo1D(row, col);
        openedArray[index] = true;
        if (row == 0) {
            uf.union(index, virtualTopIndex);
        }

        if (row == n - 1) {
            uf.union(index, virtualBottomIndex);
        }

        int[][] neighbors = {{row + 1, col}, {row - 1, col}, {row, col + 1}, {row, col - 1}};
        for (int[] nei : neighbors) {
            if (nei[0] < 0 || nei[0] >= n || nei[1] < 0 || nei[1] >= n) {
                continue;
            }
            int ni = xyTo1D(nei[0], nei[1]);
            if (isOpen(nei[0], nei[1])) {
                uf.union(index, ni);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openedArray[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(xyTo1D(row, col), virtualTopIndex);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualBottomIndex, virtualTopIndex);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {
    }
}
