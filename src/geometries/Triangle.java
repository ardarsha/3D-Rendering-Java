package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the triangle class for 3D graphic rendering purposes.
 *
 * @author Ariel Darshan
 */
public class Triangle extends FlatGeometry {
    /*----------------VARIABLES---------------------*/
    private Point3D _p1;
    private Point3D _p2;
    private Point3D _p3;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for the Triangle class, for 3D graphic rendering.
     *
     * @param a A point in 3D space, representing a node of the triangle.
     * @param b A point in 3D space, representing a node of the triangle.
     * @param c A point in 3D space, representing a node of the triangle.
     */
    public Triangle(Point3D a, Point3D b, Point3D c) {
        _p1 = new Point3D(a);
        _p2 = new Point3D(b);
        _p3 = new Point3D(c);
    }

    public Triangle(Triangle other) {
        super(other);
        this._p1 = new Point3D(other._p1);
        this._p2 = new Point3D(other._p2);
        this._p3 = new Point3D(other._p3);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     * An interface for another class to get the points that assemble the triangle
     *
     * @param choice The number of node to be returned.
     * @return choice = 1: the 1st node. choice = 2: the 2nd node. choice = 3: 3rd node.
     * @throws IllegalArgumentException If the choice isn't between 1 and 3.
     */
    public Point3D getPoint(int choice) throws IllegalArgumentException {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return _p1.equals(triangle._p1) &&
                _p1.equals(triangle._p1) &&
                _p1.equals(triangle._p1);
    }

    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     * @param p with triangle call the function as follows: getNormal(null)
     * @return normal vector to the triangle in 3D space.
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {
        return new Plane(_p1, _p2, _p3).getNormal(null);
    }

    public List<Point3D> findIntersectionsPoint3D(Ray ray) throws ZeroVectorException {
        if (null == ray)
            throw new NullPointerException();
        Plane plane = new Plane(this);
        List<Point3D> planeRet = plane.findIntersectionsPoint3D(ray);

        if (planeRet.equals(new ArrayList<>()))
            return new ArrayList<>();

        List<Point3D> ret = new ArrayList<>();
        if (this.contains(planeRet.get(0)))
            ret.add(planeRet.get(0));
        return ret;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
        List<GeoPoint> ret = new ArrayList<>();
        for (Point3D point : this.findIntersectionsPoint3D(ray)){
            ret.add(new GeoPoint(new Triangle(this), point));
        }
        return ret;
    }

    /**
     * Check if a point is inside the triangle
     *
     * @param p a point in 3D space
     * @return true: point is inside the triangle and on the same plane. false: otherwise.
     * @throws ZeroVectorException In case the triangle is all on one line (Shouldn't be possible).
     */
    public boolean contains(Point3D p) throws ZeroVectorException {
        Vector AB = _p1.subtract(_p2),
                AC = _p1.subtract(_p3),
                BC = _p2.subtract(_p3),
                AP = p.subtract(_p1),
                BP = p.subtract(_p2);

        double areaABC = AB.crossProduct(AC).length() / 2,
                areaABP = AB.crossProduct(AP).length() / 2,
                areaACP = AC.crossProduct(AP).length() / 2,
                areaBCP = BC.crossProduct(BP).length() / 2;

        return new Coordinate(areaABP + areaACP + areaBCP).equals(new Coordinate(areaABC));
    }
    /*----------------END OPERATIONS----------------*/
}
