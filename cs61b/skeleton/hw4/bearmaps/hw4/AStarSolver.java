package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution = new ArrayList<>();
    private double timeSpent;
    private HashMap<Vertex, Double> distanceMap = new HashMap<>();
    private HashMap<Vertex, Double> weightMap = new HashMap<>();
    private HashSet<Vertex> visited = new HashSet<>();
    private int numStatesExplored = 0;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        ExtrinsicMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();
        pq.add(start, 0);
        distanceMap.put(start, 0.0);
        weightMap.put(start, 0.0);
        while (pq.size() > 0) {
            Vertex v = pq.removeSmallest();
            numStatesExplored += 1;
            solution.add(v);
            solutionWeight += weightMap.get(v);
            timeSpent = sw.elapsedTime();
            if (v.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                return;
            }
            visited.add(v);
            if (timeSpent > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }

            List<WeightedEdge<Vertex>> edges = input.neighbors(v);
            for (WeightedEdge<Vertex> e: edges) {
                // relax
                Vertex p = e.from();
                Vertex q = e.to();
                double w = e.weight();
                double distToQ = distanceMap.getOrDefault(q, Double.MAX_VALUE);
                double distToP = distanceMap.get(p);
                if (distToP + w < distToQ) {
                    distToQ = distToP + w;
                    weightMap.put(q, w);
                    distanceMap.put(q, distToQ);
                    double est = input.estimatedDistanceToGoal(q, end);
                    double newPriority = distToQ + est;
                    if (pq.contains(q)) {
                        pq.changePriority(q, newPriority);
                    } else {
                        pq.add(q, newPriority);
                    }
                }
            }
        }

        outcome = SolverOutcome.UNSOLVABLE;
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return numStatesExplored - 1;
    }

    public double explorationTime() {
        return timeSpent;
    }
}
