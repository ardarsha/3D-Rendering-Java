package primitives;


/**
 * @author Ariel Darshan
 */
abstract class FixedVector {

    /*----------------VARIABLES---------------------*/
    private Vector _direction;
    private Point3D _origin;

    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public FixedVector(Vector dir) throws ZeroVectorException {
        _direction = dir.normalised();
        _origin = new Point3D();
    }

    public FixedVector(Vector dir, Point3D src) throws ZeroVectorException {
        _direction = dir.normalised();
        _origin = new Point3D(src);
    }

    public FixedVector(Point3D src, Vector dir) throws ZeroVectorException {
        this(dir, src);
    }

    public FixedVector(FixedVector other) {
        _direction = new Vector(other._direction);
        _origin = new Point3D(other._origin);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getOrigin() {
        return new Point3D(_origin);
    }

    public Vector getDirection() {
        return new Vector(_direction);
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (!(obj instanceof Ray))
            return false;
        FixedVector rhs = (FixedVector) obj;
        return _direction.equals(rhs._direction) && _origin.equals(rhs._origin);

    }

    @Override
    public String toString() {
        return _origin.toString() + " + t" + _direction.toString();
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     * Find the point on the fixed vector that is closest to a given point
     * @param p A point in 3D space
     * @return the point on the fixed vector that is closest to p
     */
    public Point3D getClosestPoint(Point3D p) {
        try {
            if (this.contains(p))
                return new Point3D(p);
            Vector spVector = p.subtract(_origin);

            // The closest point is _origin if the direction and the vector between _origin and p are perpendicular
            if (spVector.dotProduct(_direction) == 0)
                return new Point3D(_origin);

            double t = spVector.dotProduct(_direction) / _direction.length();
            return new Point3D(_origin.add(_direction.scale(t)));
        } catch (ZeroVectorException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Find the shortest distance from a given point to the fixed vector, squared
     * @param p A point in 3D space
     * @return The shortest distance from p to this, squared
     */
    public double squareDistance(Point3D p)
    {
        return this.getClosestPoint(p).squareDistance(p);
    }

    /**
     * Find the shortest distance from a given point to the fixed vector
     * @param p A point in 3D space
     * @return The shortest distance from p to this
     */
    public double distance(Point3D p)
    {
        return this.getClosestPoint(p).distance(p);
    }

    /**
     * Check if a point is on the line the fixed vector creates
     * @param p A point in 3D space
     * @return true: the point is on the line the fixed vector constructs. false: otherwise.
     */
    public boolean contains(Point3D p) {
        if (p.equals(_origin))
            return true;
        try {
            return p.subtract(_origin).crossProduct(_direction).length() == 0;
        } catch (ZeroVectorException ex) {
            System.err.println("something isn't possible here!");
            return false;
        }
    }
    /*----------------END OPERATIONS----------------*/
}