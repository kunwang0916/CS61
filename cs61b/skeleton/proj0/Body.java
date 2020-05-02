class Body {
    /** Its current x position */
    public double xxPos;
    /** Its current y position */
    public double yyPos;
    /** Its current velocity in the x direction */
    public double xxVel;
    /** Its current velocity in the x direction */
    public double yyVel;
    /** Its mass */
    public double mass;
    /** The name of the file that corresponds to the image that depicts the body */
    public String imgFileName;

    static final double G = 6.67e-11;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
    }

    public double calcDistance(Body b) {
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public double calcForceExertedBy(Body b) {
        double r = calcDistance(b);
        return G * b.mass * this.mass / Math.pow(r, 2);
    }

    public double calcForceExertedByX(Body b) {
        double dx = b.xxPos - this.xxPos;
        return calcForceExertedBy(b) * dx / calcDistance(b);
    }

    public double calcForceExertedByY(Body b) {
        double dy = b.yyPos - this.yyPos;
        return calcForceExertedBy(b) * dy / calcDistance(b);
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        double f = 0;
        for (Body b : bodies) {
            if (b.equals(this)) {
                continue;
            }
            f += calcForceExertedByX(b);
        }
        return f;
    }
    
    public double calcNetForceExertedByY(Body[] bodies) {
        double f = 0;
        for (Body b : bodies) {
            if (b.equals(this)) {
                continue;
            }
            f += calcForceExertedByY(b);
        }
        return f;
    }

    public void update(double dt, double fX, double fY) {
        this.xxVel += dt * (fX / this.mass);
        this.yyVel += dt * (fY / this.mass);
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}