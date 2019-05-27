package primitives;

public class Vector {

    final static public Vector ZERO = new Vector(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO, 0);
    /*----------------VARIABLES---------------------*/
    private Point3D _Head;
    /*----------------END CONSTANTS-----------------*/

    /*----------------CONSTANTS---------------------*/
    private Vector(Coordinate x, Coordinate y, Coordinate z, int dummy) {
        _Head = new Point3D(x, y, z);
    }
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Vector(Coordinate x, Coordinate y, Coordinate z) throws ZeroVectorException {
        this(new Point3D(x, y, z));
    }

    public Vector(Point3D p) throws ZeroVectorException {
        _Head = new Point3D(p);
        if (this.equals(ZERO))
            throw new ZeroVectorException("Zero vector passed in as a parameter to CTOR of vector");
    }

    public Vector(double x, double y, double z) throws ZeroVectorException {
        this(new Point3D(x, y, z));
    }

    public Vector(Vector other) throws ZeroVectorException {
        this(other._Head);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getHead() {
        return new Point3D(_Head);
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (null == obj)
            return false;
        if (!(obj instanceof Vector))
            return false;
        Vector rhs = (Vector) obj;
        return _Head.equals(rhs._Head);
    }

    @Override
    public String toString() {
        return "[" + _Head.toString() + ']';
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    //public:
    public Vector add(Vector rhs) throws ZeroVectorException {
        Vector vector = new Vector(_Head.add(rhs));
        if (vector.equals(ZERO))
            throw new ZeroVectorException("Addition results in zero vector.\n\t\tv1 = " + this.toString() + "\n\t\tv2 = " + rhs.toString());
        return vector;
    }

    public Vector subtract(Vector rhs) throws ZeroVectorException {
        Vector vector = new Vector(_Head.subtract(rhs._Head));
        if (vector.equals(ZERO))
            throw new ZeroVectorException("Subtraction results in zero vector.\n\t\tv1 = " + this.toString() + "\n\t\tv2 = " + rhs.toString());
        return vector;
    }

    public Vector scale(double scalar) throws ZeroVectorException {
        return new Vector(_Head.getX().scale(scalar), _Head.getY().scale(scalar), _Head.getZ().scale(scalar));
    }

    public double dotProduct(Vector rhs) throws ZeroVectorException {
        if (0 == rhs.length())
            throw new ZeroVectorException("Right hand side is zero.\n Hash: " + rhs.hashCode());

        if (0 == this.length())
            throw new ZeroVectorException("Left hand side is zero\n Hash: " + rhs.hashCode());
        return this._Head.getX().multiply(rhs._Head.getX()).add(
                this._Head.getY().multiply(rhs._Head.getY())).add(
                this._Head.getZ().multiply(rhs._Head.getZ())).get();
    }

    public Vector crossProduct(Vector rhs) throws ZeroVectorException {
        Coordinate x = new Coordinate(_Head.getY().multiply(rhs._Head.getZ()).subtract(_Head.getZ().multiply(rhs._Head.getY())));
        Coordinate y = new Coordinate(_Head.getZ().multiply(rhs._Head.getX()).subtract(_Head.getX().multiply(rhs._Head.getZ())));
        Coordinate z = new Coordinate(_Head.getX().multiply(rhs._Head.getY()).subtract(_Head.getY().multiply(rhs._Head.getX())));
        return new Vector(new Point3D(x, y, z));
    }

    public double length() {
        return _Head.distance(new Point3D(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO));
    }

    public Vector normalised() throws ZeroVectorException {
        return new Vector(this.scale(1 / length()));
    }
    /*----------------END OPERATIONS----------------*/
}
