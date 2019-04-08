package primitives;


public class Point2D {

    /*----------------VARIABLES---------------------*/
    private Coordinate x;
    private Coordinate y;
    /*----------------END VARIABLES-----------------*/

    
    /*----------------CONSTRUCTORS------------------*/
    public Point2D() {
        x = new Coordinate(Coordinate.ZERO);
        y = new Coordinate(Coordinate.ZERO);
    }

    public Point2D(Coordinate x, Coordinate y) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
    }

    public Point2D(Point2D other) {
        this.x = new Coordinate(other.x);
        this.y = new Coordinate(other.y);
    }
    /*----------------END CONSTRUCTORS--------------*/

    
    /*----------------GETTERS/SETTERS---------------*/
    public Coordinate getX() {
        return x;
    }

    public Coordinate getY() {
        return y;
    }

    private void setX(Coordinate x) {
        this.x = new Coordinate(x);
    }

    private void setY(Coordinate y) {
        this.y = new Coordinate(y);
    }
    /*----------------END GETTERS/SETTERS-----------*/

    
    /*----------------ADMINISTRATION----------------*/
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point2D)) return false;
        Point2D rhs = (Point2D) obj;
        return this.x.equals(rhs.x) && this.y.equals(rhs.y);
    }
    /*----------------END ADMINISTRATION------------*/

    
    /*----------------OPERATIONS--------------------*/
    public double distance(Point2D rhs) {
        return Math.sqrt(this.squareDistance(rhs));
    }

    public double squareDistance(Point2D rhs) {
        Point2D sub = this.subtract(rhs);
        return sub.x.scale(sub.x.get()).get() + sub.y.scale(sub.y.get()).get();
    }

    public Point2D subtract(Point2D rhs) {
        return new Point2D(rhs.x.subtract(this.x), rhs.y.subtract(this.y));
    }

    public Point2D add(Point2D rhs) {
        return new Point2D(rhs.x.add(this.x), rhs.y.add(this.y));
    }

    public Point2D scale(double factor) {
        return new Point2D(this.x.scale(factor), this.y.scale(factor));
    }
    /*----------------END OPERATIONS----------------*/
}
