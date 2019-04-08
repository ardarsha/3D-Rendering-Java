package geometries;

import primitives.Point3D;

public class Sphere extends RadialGeometry {

    /*----------------VARIABLES---------------------*/
    private Point3D center;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Sphere(double radios) {
        super(radios);
        center = new Point3D();
    }

    public Sphere(double radios, Point3D cent) {
        super(radios);
        center = cent;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    @Override
    public double radios() {
        return super.radios();
    }

    public Point3D getCenter() {
        return new Point3D(center);
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}
