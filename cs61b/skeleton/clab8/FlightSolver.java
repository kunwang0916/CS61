import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    ArrayList<Flight> flights;

    public FlightSolver(ArrayList<Flight> flights) {
        this.flights = flights;
    }

    public int solve() {
        Comparator<Flight> startComparator = (Flight o1, Flight o2) -> {
            return o1.startTime - o2.startTime;
        };

        Comparator<Flight> endComparator = (Flight o1, Flight o2) -> {
            return o1.endTime - o2.endTime;
        };

        this.flights.sort(startComparator);
        PriorityQueue<Flight> pq = new PriorityQueue<>(this.flights.size(), endComparator);

        int result = 0;
        int sum = 0;
        for (Flight f : this.flights) {
            Flight firstFlight = pq.peek();
            while (firstFlight != null && firstFlight.endTime < f.startTime) {
                firstFlight = pq.poll();
                sum -= firstFlight.passengers;
                firstFlight = pq.peek();
            }
            sum += f.passengers;
            pq.add(f);
            result = Math.max(result, sum);
        }

        return result;
    }

}
