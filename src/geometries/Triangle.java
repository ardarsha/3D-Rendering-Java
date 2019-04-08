package geometries;

import primitives.Point3D;

public class Triangle {
    /*----------------VARIABLES---------------------*/
    private Point3D p1;
    private Point3D p2;
    private Point3D p3;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Triangle(Point3D a, Point3D b, Point3D c) {
        p1 = new Point3D(a);
        p2 = new Point3D(b);
        p3 = new Point3D(c);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getPoint(int choice) {
        switch (choice) {
            case 0:
                return new Point3D(p1);
            case 1:
                return new Point3D(p2);
            case 2:
                return new Point3D(p3);
            default:
                throw new IllegalArgumentException("only 0, 1 or 2 are valid!");
        }
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}
