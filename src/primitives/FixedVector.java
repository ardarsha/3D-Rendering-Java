package primitives;


/**
 * @author Ariel Darshan
 */
abstract class FixedVector {

    /*----------------VARIABLES---------------------*/
    private Vector _direction;
    private Point3D _source;

    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public FixedVector(Vector dir) throws ZeroVectorException {
        _direction = dir.normalised();
        _source = new Point3D();
    }

    public FixedVector(Vector dir, Point3D src) throws ZeroVectorException {
        _direction = dir.normalised();
        _source = new Point3D(src);
    }

    public FixedVector(Point3D src, Vector dir) throws ZeroVectorException {
        this(dir, src);
    }

    public FixedVector(FixedVector other) throws ZeroVectorException {
        _direction = new Vector(other._direction);
        _source = new Point3D(other._source);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getSource() {
        return new Point3D(_source);
    }

    public Vector getDirection() throws ZeroVectorException {
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
        return _direction.equals(rhs._direction) && _source.equals(rhs._source);

    }

    @Override
    public String toString() {
        return _source.toString() + " + t" + _direction.toString();
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    public Point3D getClosestPoint(Point3D p) {
        try {
            if (this.contains(p))
                return new Point3D(p);
            Vector spVector = p.subtract(_source);

            // The closest point is _source if the direction and the vector between _source and p are perpendicular
            if (spVector.dotProduct(_direction) == 0)
                return new Point3D(_source);

            double t = spVector.dotProduct(_direction) / _direction.length();
            return new Point3D(_source.add(_direction.scale(t)));
        } catch (ZeroVectorException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }


    public boolean contains(Point3D p) {
        if (p.equals(_source))
            return true;
        try {
            return p.subtract(_source).crossProduct(_direction).length() == 0;
        } catch (ZeroVectorException ex) {
            System.err.println("something isn't possible here!");
            return false;
        }
    }
    /*----------------END OPERATIONS----------------*/
}