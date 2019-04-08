package primitives;

public class Ray {

    /*----------------VARIABLES---------------------*/
    private Vector direction;
    private Point3D source;

    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Ray(Vector dir, Point3D src) {
        direction = new Vector(dir);
        source = new Point3D(src);
    }

    public Ray(Ray other) {
        direction = new Vector(other.direction);
        source = new Point3D(other.source);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
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
        Ray rhs = (Ray) obj;
        return direction.equals(rhs.direction) && source.equals(rhs.source);

    }

    @Override
    public String toString() {
        return source.toString() + " + t" + direction.toString();
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}