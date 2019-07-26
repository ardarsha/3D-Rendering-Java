package primitives;

public class Vector {
    /*----------------CONSTANTS---------------------*/
    final static public Vector ZERO = new Vector(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO);
    /*----------------END CONSTANTS-----------------*/

    /*----------------VARIABLES---------------------*/
    private Point3D _Head;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

    public Vector(Point3D p) {
        _Head = new Point3D(p);
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    public Vector(Vector other) {
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
        return _Head.toString().replace('(', '[').replace(')', ']');
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    //public:
    public Vector add(Vector rhs) {
        return new Vector(_Head.add(rhs));
    }

    public Vector subtract(Vector rhs) {

        return new Vector(_Head.subtract(rhs._Head));
    }

    public Vector scale(double sX, double sY, double sZ) {
        if (sX == 0 && sY == 0 && sZ == 0)
            return ZERO;
        return new Vector(_Head.getX().scale(sX), _Head.getY().scale(sY), _Head.getZ().scale(sZ));
    }

    public Vector scale(double scalar) {
        return this.scale(scalar, scalar, scalar);
    }

    public double dotProduct(Vector rhs) throws ZeroVectorException {
        if (null == rhs)
            throw new NullPointerException();
        if (this.equals(ZERO) || rhs.equals(ZERO))
            throw new ZeroVectorException();

        return this._Head.getX().multiply(rhs._Head.getX()).add(
                this._Head.getY().multiply(rhs._Head.getY())).add(
                this._Head.getZ().multiply(rhs._Head.getZ())).get();
    }

    public Vector crossProduct(Vector rhs) throws ZeroVectorException {
        if (null == rhs)
            throw new NullPointerException();
        if (this.equals(ZERO) || rhs.equals(ZERO))
            throw new ZeroVectorException();

        Coordinate x = new Coordinate(_Head.getY().multiply(rhs._Head.getZ()).subtract(_Head.getZ().multiply(rhs._Head.getY())));
        Coordinate y = new Coordinate(_Head.getZ().multiply(rhs._Head.getX()).subtract(_Head.getX().multiply(rhs._Head.getZ())));
        Coordinate z = new Coordinate(_Head.getX().multiply(rhs._Head.getY()).subtract(_Head.getY().multiply(rhs._Head.getX())));

        if (x.equals(Coordinate.ZERO) && y.equals(Coordinate.ZERO) && z.equals(Coordinate.ZERO))
            return ZERO;
        return new Vector(new Point3D(x, y, z));
    }

    public double squared() throws ZeroVectorException {
        if (this.equals(ZERO))
            return 0;
        return this.dotProduct(this);
    }

    public double length() throws ZeroVectorException {
        return Math.sqrt(this.squared());
    }

    public Vector normalised() throws ZeroVectorException {
        if (this.equals(ZERO))
            throw new ZeroVectorException();
        return new Vector(this.scale(1 / length()));
    }
    /*----------------END OPERATIONS----------------*/
}
