package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.List;

/**
 * A plane for 3D graphics rendering
 * @author Ariel Darshan
 */
public class Plane implements FlatGeometry{
    /*----------------VARIABLES---------------------*/
    private Point3D _point;
    private Vector _normal;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for plane in algebraic notation
     * @param p A point on the plane.
     * @param norm  The vector that creates a 90 degree angle with the plane (the normal vector).
     */
    public Plane(Point3D p, Vector norm) throws ZeroVectorException {
        _point = new Point3D(p);
        _normal = new Vector(norm).normalised();
    }

    /**
     * Constructor for plane based on 3 points in 3D space.
     * @param p1    A point on the plane.
     * @param p2    A point on the plane.
     * @param p3    A point on the plane.
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) throws ZeroVectorException
    {
        this._point = new Point3D(p1);
        this._normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalised();
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     *
     * @return  A point on the plane.
     */
    public Point3D getPoint() {
        return new Point3D(_point);
    }

    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     *
     * @param p  for plane we use this function should be called as follows: getNormal(null)
     * @return normal vector to the plane.
     */
    @Override
     public Vector getNormal(Point3D p) throws ZeroVectorException {
        return  _normal;
    }

    /**
     * Find the intersections of a certain ray with this plane.
     * @param ray   The ray to find intersections with.
     * @return  A list of points which the ray intersects this at.
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return Intersectable.EmptyList;
    }

    /**
     * A function to check if a point is on the surface of the plane.
     * @param p The point to be checked.
     * @return  true: the point is on the plane. false: the point is not on the plane.
     */
    public boolean contains(Point3D p)
    {
        try {
            return p.equals(_point) || p.subtract(_point).dotProduct(_normal) == 0;
        }
        catch (ZeroVectorException ex)
        {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    /*----------------END OPERATIONS----------------*/
}