package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int[] xArray;
    private int n;
    private int t;
    private double mean;
    private double stddev;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should greater than 0.");
        }

        n = N;
        t = T;
        xArray = new int[t];
        for (int i = 0; i < t; i ++) {
            Percolation p = pf.make(n);
            while (p.percolates() == false) {
                int r = StdRandom.uniform(n);
                int c = StdRandom.uniform(n);
                while (p.isOpen(r, c) == true) {
                    r = StdRandom.uniform(n);
                    c = StdRandom.uniform(n);
                }
            }
            xArray[i] = p.numberOfOpenSites();
        }

        mean = StdStats.mean(xArray);
        stddev = StdStats.stddev(xArray);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean - 1.96 * stddev / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean + 1.96 * stddev / Math.sqrt(t);
    }
}
