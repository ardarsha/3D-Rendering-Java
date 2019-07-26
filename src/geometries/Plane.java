package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.ArrayList;
import java.util.List;

/**
 * A plane for 3D graphics rendering
 *
 * @author Ariel Darshan
 */
public class Plane extends FlatGeometry {
    /*----------------VARIABLES---------------------*/
    private Point3D _point;
    private Vector _normal;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for plane in algebraic notation
     *
     * @param p    A point on the plane.
     * @param norm The vector that creates a 90 degree angle with the plane (the normal vector).
     */
    public Plane(Point3D p, Vector norm) throws ZeroVectorException {
        _point = new Point3D(p);
        _normal = new Vector(norm).normalised();
    }

    /**
     * Constructor for plane based on 3 points in 3D space.
     *
     * @param p1 A point on the plane.
     * @param p2 A point on the plane.
     * @param p3 A point on the plane.
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) throws ZeroVectorException {
        this._point = new Point3D(p1);
        this._normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalised();
    }

    /**
     * Constructor for plane based on triangle.
     *
     * @param triangle The triangle that represents the plane.
     * @throws ZeroVectorException In case the triangle doesn't form a plane (points on one line)
     */
    public Plane(Triangle triangle) throws ZeroVectorException {
        this(triangle.getPoint(0), triangle.getPoint(1), triangle.getPoint(2));
    }

    public Plane(Plane plane) {
        super(plane);
        this._normal = new Vector(plane._normal);
        this._point = new Point3D(plane._point);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     * @return A point on the plane.
     */
    public Point3D getPoint() {
        return new Point3D(_point);
    }

    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     * @param p for plane we use this function should be called as follows: getNormal(null)
     * @return normal vector to the plane.
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {
        return _normal;
    }

    public List<Point3D> findIntersectionsPoint3D(Ray ray) throws ZeroVectorException {
        if (_normal.dotProduct(ray.getDirection()) == 0)
            return new ArrayList<>();

        double t = _point.subtract(ray.getOrigin()).dotProduct(_normal) / _normal.dotProduct(ray.getDirection());
        List<Point3D> ret = new ArrayList<>();
        if (t >= 0)
            ret.add(ray.getOrigin().add(ray.getDirection().scale(t)));
        return ret;
    }

    /**
     * Find the intersections of a certain ray with this plane.
     *
     * @param ray The ray to find intersections with.
     * @return A list of points which the ray intersects this at.
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
        List<GeoPoint> ret = new ArrayList<>();
        for (Point3D point : this.findIntersectionsPoint3D(ray)) {
            ret.add(new GeoPoint(new Plane(this), point));
        }
        return ret;
    }

    /**
     * A function to check if a point is on the surface of the plane.
     *
     * @param p The point to be checked.
     * @return true: the point is on the plane. false: the point is not on the plane.
     */
    public boolean contains(Point3D p) {
        try {
            return p.equals(_point) || p.subtract(_point).dotProduct(_normal) == 0;
        } catch (ZeroVectorException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }
    /*----------------END OPERATIONS----------------*/
}