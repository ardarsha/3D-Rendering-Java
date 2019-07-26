package primitives;

/**
 * Implementation of 3D point for rendering graphics.
 * @author Ariel Darshan
 */
public class Point3D extends Point2D implements Comparable<Point3D>{

    /*----------------VARIABLES---------------------*/
    private Coordinate _z;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     *
     */
    public Point3D() {
        super();
        _z = new Coordinate(Coordinate.ZERO);
    }

    /**
     *
     * @param p
     * @param c
     */
    public Point3D(Point2D p, Coordinate c) {
        super(p);
        _z = new Coordinate(c);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        super(x, y);
        this._z = new Coordinate(z);
    }

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Point3D(double x, double y, double z)
    {
        super(x,y);
        _z = new Coordinate(z);
    }

    /**
     *
     * @param other
     */
    public Point3D(Point3D other) {
        super(other);
        this._z = new Coordinate(other._z);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    /**
     *
     * @return
     */
    @Override
    public Coordinate getX() {
        return super.getX();
    }

    /**
     *
     * @return
     */
    @Override
    public Coordinate getY() {
        return super.getY();
    }

    /**
     *
     * @return
     */
    public Coordinate getZ() {
        return _z;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + _z + ')';
    }

    /**
     *
     * @param obj   The other object to check if equal to.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D rhs = (Point3D) obj;
        return _z.equals(rhs._z) && super.getX().equals(rhs.getX()) && super.getY().equals(rhs.getY());
    }



    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    //private:
    private Point3D add(Point3D rhs) {
        if (null == rhs)
            return new Point3D(this);
        return new Point3D(super.add(new Point2D(rhs.getX(), rhs.getY())), _z.add(rhs._z));
    }

    private Point3D subtractHelp(Point3D rhs) {
        if (null == rhs)
            return new Point3D(this);
        return new Point3D(this.getX().subtract(rhs.getX()), this.getY().subtract(rhs.getY()), this._z.subtract(rhs._z));
    }

    //public:

    /**
     * Calculates the distance between this point and another point, squared.
     * @param rhs   The point to calculate the distance from.
     * @return  The distance between this point and the point given in the parameter, squared. -1 if rhs is null.
     */
    public double squareDistance(Point3D rhs) {
        if (null == rhs)
            return -1;
        Coordinate zDistance = _z.subtract(rhs._z);
        return new Coordinate(super.squareDistance(rhs)).add(zDistance.multiply(zDistance)).get();
    }

    /**
     * Calculates the distance between this point and another point.
     * @param rhs   The point to calculate the distance from.
     * @return  The distance between this point and the point given in the parameter. -1 if rhs is null.
     */
    public double distance(Point3D rhs) {
        if (null == rhs)
            return -1;
        return new Coordinate(Math.sqrt(squareDistance(rhs))).get();
    }

    /**
     * Calculates the values of a vector from a point given in a parameter to this point.
     * @param rhs   The point to subtract.
     * @return  A vector from rhs to this point.
     */
    public Vector subtract(Point3D rhs) {
        return new Vector(subtractHelp(rhs));
    }

    /**
     *
     * @param rhs
     * @return
     */
    public Point3D add(Vector rhs) {
        return this.add(rhs.getHead());
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Point3D o) throws NullPointerException, ClassCastException{
        if (this.equals(o))
            return 0;

        if (null == o)
            throw new NullPointerException();

        if (this.getX().get() < o.getX().get())
            return -1;
        if (this.getX().get() > o.getX().get())
            return 1;

        if (this.getY().get() < o.getY().get())
            return -1;
        if (this.getY().get() > o.getY().get())
            return 1;

        if (this._z.get() < o._z.get())
            return -1;
        if (this._z.get() > o._z.get())
            return 1;

        // something went wrong in the equals method
        throw new ClassCastException();
    }

    //public Point3D scale(double factor) {
    //    return new Point3D(super.scale(factor), _z.scale(factor));
    //}
    /*----------------END OPERATIONS----------------*/
}