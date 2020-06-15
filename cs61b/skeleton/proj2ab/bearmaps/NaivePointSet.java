package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point input = new Point(x, y);
        Double minD = Double.MAX_VALUE;
        Point result = null;
        for (Point p: points) {
            double d = Point.distance(p, input);
            if (d < minD) {
                result = p;
                minD = d;
            }
        }

        return result;
    }
}
