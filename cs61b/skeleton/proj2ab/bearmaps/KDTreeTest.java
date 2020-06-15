package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private Random r = new Random(1000);
    @Test
    public void testSimpleCase() {
        List<Point> l = new ArrayList<>();
        l.add(new Point(2, 3));
        l.add(new Point(1, 5));
        l.add(new Point(4, 2));
        l.add(new Point(4, 5));
        l.add(new Point(3, 3));
        l.add(new Point(4, 4));

        KDTree kt = new KDTree(l);
        NaivePointSet nps = new NaivePointSet(l);
        Point goal = new Point(0, 7);

        Point ktResult = kt.nearest(goal.getX(), goal.getY());
        Point npsResult = nps.nearest(goal.getX(), goal.getY());

        assertEquals(npsResult, ktResult);
    }

    private final int timeTestSize = 1000;
    private final int timeTestSearchSize = timeTestSize / 10;

    private Point randomPoint() {
        return new Point(r.nextDouble(), r.nextDouble());
    }

    private List<Point> randomPoints(int N) {
        java.util.List<Point> points = new ArrayList<Point>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    @Test
    public void randTest() {
        List<Point> l = randomPoints(timeTestSize);

        KDTree kt = new KDTree(l);
        NaivePointSet nps = new NaivePointSet(l);

        List<Point> s = randomPoints(timeTestSearchSize);
        for (Point p : s) {
            double x = p.getX();
            double y = p.getY();
            assertEquals(kt.nearest(x, y), nps.nearest(x, y));
        }
    }

    @Test
    public void timeTest() {

        List<Point> l = randomPoints(timeTestSize);

        KDTree kt = new KDTree(l);
        NaivePointSet nps = new NaivePointSet(l);
        List<Point> s = randomPoints(timeTestSearchSize);

        Stopwatch sw = new Stopwatch();
        for (Point p : s) {
            double x = p.getX();
            double y = p.getY();
            kt.nearest(x, y);
        }
        System.out.println(sw.elapsedTime());

        sw = new Stopwatch();
        for (Point p : s) {
            double x = p.getX();
            double y = p.getY();
            nps.nearest(x, y);
        }
        System.out.println(sw.elapsedTime());
    }
}
