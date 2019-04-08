package geometries;

import primitives.*;

public class Plane {
    /*----------------VARIABLES---------------------*/
    private Point3D point;
    private Vector normal;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Plane(Point3D p, Vector norm) {
        point = new Point3D(p);
        normal = new Vector(norm);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getPoint() {
        return new Point3D(point);
    }

    public Vector getNormal() {
        return new Vector(normal);
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}