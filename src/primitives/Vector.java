package primitives;

public class Vector {

    /*----------------VARIABLES---------------------*/
    private Point3D Head;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Vector() {
        Head = new Point3D();
    }

    public Vector(Point3D p) {
        Head = new Point3D(p);
    }

    public Vector(Vector other) {
        this.Head = new Point3D(other.Head);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getHead() {
        return new Point3D(Head);
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
        return Head.equals(rhs.Head);
    }

    @Override
    public String toString() {
        return "[" + Head.toString() + ']';
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    //public:
    public Vector add(Vector rhs) {
        return new Vector(Head.add(rhs));
    }

    public Vector subtract(Vector rhs) {
        return new Vector(Head.subtract(rhs));
    }

    public Vector scale(double scalar) {
        return new Vector(Head.scale(scalar));
    }

    public double dotProduct(Vector rhs) {
        return this.Head.getX().scale(rhs.Head.getX().get()).get() +
                this.Head.getY().scale(rhs.Head.getY().get()).get() +
                this.Head.getZ().scale(rhs.Head.getZ().get()).get();
    }

    public Vector crossProduct(Vector rhs) {
        Coordinate x = new Coordinate(Head.getY().scale(rhs.Head.getZ().get()).subtract(Head.getZ().scale(rhs.Head.getY().get())));
        Coordinate y = new Coordinate(Head.getZ().scale(rhs.Head.getX().get()).subtract(Head.getX().scale(rhs.Head.getZ().get())));
        Coordinate z = new Coordinate(Head.getX().scale(rhs.Head.getY().get()).subtract(Head.getY().scale(rhs.Head.getX().get())));
        return new Vector(new Point3D(x, y, z));
    }

    public double length() {
        return Head.distance(new Point3D(Coordinate.ZERO, Coordinate.ZERO, Coordinate.ZERO));
    }

    public Vector normilized() {
        return new Vector(Head.scale(1 / length()));
    }
    /*----------------END OPERATIONS----------------*/
}
