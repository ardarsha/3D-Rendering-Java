package primitives;

/**
 * 2D point for graphic rendering
 * @author Ariel Darshan
 */
public class Point2D {

    /*----------------VARIABLES---------------------*/
    private Coordinate _x;
    private Coordinate _y;
    /*----------------END VARIABLES-----------------*/

    
    /*----------------CONSTRUCTORS------------------*/

    /**
     * Default Constructor for Point2D. initialized to (0,0).
     *
     */
    public Point2D() {
        _x = new Coordinate(Coordinate.ZERO);
        _y = new Coordinate(Coordinate.ZERO);
    }

    /**
     * Constructor for 2D point class
     * @param x     the x coordinate of the point
     * @param y     the y coordinate of the point
     */

    /**
     * Constructor to initialize the point with supplied values.
     * @param x The value of the first coordinate.
     * @param y The value of the second coordinate.
     */
    public Point2D(double x, double y)
    {
        this(new Coordinate(x), new Coordinate(y));
    }

    /**
     * Constructor to initialize the point with supplied Coordinates.
     * @param x The first coordinate.
     * @param y The second coordinate.
     */
    public Point2D(Coordinate x, Coordinate y) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
    }

    /**
     * Copy constructor for Point2D. Does a deep copy.
     * @param other The Point2D to make a deep copy of.
     */
    public Point2D(Point2D other) {
        this._x = new Coordinate(other._x);
        this._y = new Coordinate(other._y);
    }
    /*----------------END CONSTRUCTORS--------------*/

    
    /*----------------GETTERS/SETTERS---------------*/

    /**
     *
     * @return  A coordinate with the value of x.
     */
    public Coordinate getX() {
        return new Coordinate(_x);
    }

    /**
     *
     * @return A coordinate with the value of y.
     */
    public Coordinate getY() {
        return new Coordinate(_y);
    }
    /*----------------END GETTERS/SETTERS-----------*/

    
    /*----------------ADMINISTRATION----------------*/

    /**
     *
     * @return  A string representing the 2D point.
     */
    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ')';
    }

    /**
     * Checks if this is equal to another object.
     * @param obj   The other object to check if equal to.
     * @return  true: if the object is a 2D point, and has the same values.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point2D)) return false;
        Point2D rhs = (Point2D) obj;
        return this._x.equals(rhs._x) && this._y.equals(rhs._y);
    }
    /*----------------END ADMINISTRATION------------*/

    
    /*----------------OPERATIONS--------------------*/

    /**
     *
     * @param rhs
     * @return
     */
    public double distance(Point2D rhs) {
        return new Coordinate(Math.sqrt(this.squareDistance(rhs))).get();
    }

    /**
     *
     * @param rhs
     * @return
     */
    public double squareDistance(Point2D rhs) {
        Point2D sub = this.subtract(rhs);
        return new Coordinate(sub._x.scale(sub._x.get()).get() + sub._y.scale(sub._y.get()).get()).get();
    }

    /**
     *
     * @param rhs
     * @return
     */
    public Point2D subtract(Point2D rhs) {
        return new Point2D(rhs._x.subtract(this._x), rhs._y.subtract(this._y));
    }

    /**
     *
     * @param rhs
     * @return
     */
    public Point2D add(Point2D rhs) {
        return new Point2D(rhs._x.add(this._x), rhs._y.add(this._y));
    }

    /**
     *
     * @param factor
     * @return
     */
    public Point2D scale(double factor) {
        return new Point2D(this._x.scale(factor), this._y.scale(factor));
    }
    /*----------------END OPERATIONS----------------*/
}
