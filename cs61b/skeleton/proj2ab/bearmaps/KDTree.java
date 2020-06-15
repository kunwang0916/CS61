package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private KNode root;
    private final int K = 2;

    class KNode {
        Point point;
        KNode left;
        KNode right;
        int dimensionToCompare;

        public KNode (Point p, int d) {
            this.point = p;
            this.dimensionToCompare = d;
        }

        int compareToPoint(Point p) {
            if (dimensionToCompare % K == 0) {
                return (int)(point.getX() - p.getX());
            }
            return (int)(point.getY() - p.getY());
        }

        double distance(Point p) {
            return Point.distance(this.point, p);
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            this.insert(p);
        }
    }

    private void insert(Point p) {
        root = insert(root, p, 0);
    }

    private KNode insert(KNode n, Point p, int dimension) {
        if (n == null) {
            return new KNode(p, dimension);
        }

        if (n.point.equals(p)) {
            return n;
        }

        if (n.compareToPoint(p) < 1) {
            n.right = insert(n.right, p, dimension + 1);
        } else {
            n.left = insert(n.left, p, dimension + 1);
        }

        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        if (this.root == null) {
            return null;
        }

        return nearest(root, new Point(x, y), root).point;
    }

    private KNode nearest(KNode n, Point goal, KNode best) {
        if (n == null) {
            return best;
        }
        if (n.distance(goal) < best.distance(goal)) {
            best = n;
        }

        KNode goodSide = n.right;
        KNode badSide = n.left;
        if (n.compareToPoint(goal) > 0) {
            goodSide = n.left;
            badSide = n.right;
        }
        best = nearest(goodSide, goal, best);
        if (this.shouldCheckBadside(n, best, goal)) {
            best = nearest(badSide, goal, best);
        }

        return best;
    }

    private boolean shouldCheckBadside(KNode n, KNode best, Point goal) {
        double bestD = best.distance(goal);
        if (n.dimensionToCompare % K == 0) {
            return Math.pow(n.point.getX() - goal.getX(), 2) < bestD;
        } else {
            return Math.pow(n.point.getY() - goal.getY(), 2) < bestD;
        }
    }
}
