import javafx.scene.transform.Scale;

class NBody {
    static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        double r = in.readDouble();
        return r;
    }

    static Body[] readBodies(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        in.readDouble();
        Body[] bodies = new Body[n];
        for (int i = 0; i < n; i++) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = "images/" + in.readString();
            bodies[i] = new Body(xP, yP, xV, yV, m, img);
        }

        return bodies;
    }

    static void drawBodies(Body[] bodies) {
        for (Body b : bodies) {
            b.draw();
        }
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];
        Body[] bodies = readBodies(fileName);
        int N = bodies.length;
        double radius = readRadius(fileName);

        /**
         * Enables double buffering. A animation technique where all drawing takes place
         * on the offscreen canvas. Only when you call show() does your drawing get
         * copied from the offscreen canvas to the onscreen canvas, where it is
         * displayed in the standard drawing window.
         */
        StdDraw.enableDoubleBuffering();

        /**
         * Sets up the universe so it goes from -100, -100 up to 100, 100
         */
        StdDraw.setScale(-radius, radius);

        double[] xForces = new double[N];
        double[] yForces = new double[N];
        for (int t = 0; t < T; t += dt) {
            /* Clears the drawing window. */
            StdDraw.clear();

            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (int i = 0; i < N; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            for (int i = 0; i < N; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            drawBodies(bodies);

            /* Shows the drawing to the screen, and waits 2000 milliseconds. */
            StdDraw.show();

            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", bodies[i].xxPos, bodies[i].yyPos,
                    bodies[i].xxVel, bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}