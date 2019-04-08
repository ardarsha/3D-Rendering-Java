package primitives;

/**
 * Implementation of 3D point for rendering graphics
 */
public class Point3D extends Point2D {

    /*----------------VARIABLES---------------------*/
    private Coordinate z;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Point3D() {
        super();
        z = new Coordinate(Coordinate.ZERO);
    }

    public Point3D(Point2D p, Coordinate c) {
        super(p);
        z = new Coordinate(c);
    }

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        super(x, y);
        this.z = new Coordinate(z);
    }

    public Point3D(Point3D other) {
        super(other);
        this.z = new Coordinate(other.z);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    @Override
    public Coordinate getX() {
        return super.getX();
    }

    @Override
    public Coordinate getY() {
        return super.getY();
    }

    public Coordinate getZ() {
        return z;
    }

    private void setZ(Coordinate z) {
        this.z = z;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + z + ')';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (null == obj) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D rhs = (Point3D) obj;
        return z.equals(rhs.z) && super.getX().equals(rhs.getX()) && super.getY().equals(rhs.getY());
    }

    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    //private:
    private Point3D add(Point3D rhs) {
        return new Point3D(super.add(new Point2D(rhs.getX(), rhs.getY())), z.add(rhs.z));
    }

    private Point3D subtract(Point3D rhs) {
        return new Point3D(super.subtract(new Point2D(rhs.getX(), rhs.getY())), z.subtract(rhs.z));
    }

    //public:
    public double squareDistance(Point3D rhs) {
        Coordinate zDistance = z.subtract(rhs.z);
        return super.squareDistance(rhs) + zDistance.scale(zDistance.get()).get();
    }

    public double distance(Point3D rhs) {
        return Math.sqrt(squareDistance(rhs));
    }

    public Point3D subtract(Vector rhs) {
        return subtract(rhs.getHead());
    }

    public Point3D add(Vector rhs) {
        return this.add(rhs.getHead());
    }

    public Point3D scale(double factor) {
        return new Point3D(super.scale(factor), z.scale(factor));
    }
    /*----------------END OPERATIONS----------------*/
}