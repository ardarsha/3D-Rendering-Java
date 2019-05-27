package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.List;

/**
 * An implementation of the triangle class for 3D graphic rendering purposes.
 * @author Ariel Darshan
 */
public class Triangle implements FlatGeometry{
    /*----------------VARIABLES---------------------*/
    private Point3D _p1;
    private Point3D _p2;
    private Point3D _p3;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for the Triangle class, for 3D graphic rendering.
     * @param a A point in 3D space, representing a node of the triangle.
     * @param b A point in 3D space, representing a node of the triangle.
     * @param c A point in 3D space, representing a node of the triangle.
     */
    public Triangle(Point3D a, Point3D b, Point3D c) {
        _p1 = new Point3D(a);
        _p2 = new Point3D(b);
        _p3 = new Point3D(c);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     * An interface for another class to get the points that assemble the triangle
     * @param choice    The number of node to be returned.
     * @return  choice = 1: the 1st node. choice = 2: the 2nd node. choice = 3: 3rd node.
     * @throws IllegalArgumentException If the choice isn't between 1 and 3.
     */
    public Point3D getPoint(int choice) throws IllegalArgumentException{
        switch (choice) {
            case 0:
                return new Point3D(_p1);
            case 1:
                return new Point3D(_p2);
            case 2:
                return new Point3D(_p3);
            default:
                throw new IllegalArgumentException("only 0, 1 or 2 are valid!");
        }
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /**
     *
     * @param p  with triangle call the function as follows: getNormal(null)
     * @return normal vector to the triangle in 3D space.
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {
        return new Plane(_p1, _p2, _p3).getNormal(null);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return Intersectable.EmptyList;
    }

    public boolean contains(Point3D p) throws ZeroVectorException
    {
        return new Plane(_p1, _p2, _p3).contains(p) &&
                p.distance(_p1) < _p1.distance(_p2) &&
                p.distance(_p1) < p.distance(_p3) &&
                p.distance(_p2) < _p2.distance(_p1) &&
                p.distance(_p2) < p.distance(_p3) &&
                p.distance(_p3) < _p3.distance(_p1) &&
                p.distance(_p3) < _p3.distance(_p2);
    }
    /*----------------END OPERATIONS----------------*/
}
