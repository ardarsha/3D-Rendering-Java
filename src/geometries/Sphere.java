package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.List;

/**
 * A class for representing a sphere for 3D graphic rendering.
 * @author Ariel Darshan
 */
public class Sphere extends RadialGeometry {

    /*----------------VARIABLES---------------------*/
    private Point3D _center;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for Sphere implementation, made for 3D graphic rendering. The center will be the Default 3D point of the class Point3D
     * @see Point3D
     * @param radios    The radios of the Sphere.
     */
    public Sphere(double radios) {
        super(radios);
        _center = new Point3D();
    }

    /**
     * Constructor for Sphere implementation, made for 3D graphic rendering.
     * @param radios    The radios of the sphere.
     * @param cent  The center of the sphere.
     */
    public Sphere(double radios, Point3D cent) {
        super(radios);
        _center = cent;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     *
     * @return  The radios of the sphere.
     */
    @Override
    public double radios() {
        return super.radios();
    }

    /**
     *
     * @return  The center 3D point of the sphere.
     */
    public Point3D getCenter() {
        return new Point3D(_center);
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     *
     * @param p A point in 3D space.
     * @return  The normal vector to the sphere in the direction of p
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {
        if (this.contains(p))
            return p.subtract(_center).normalised();
        return null;
    }

    /**
     * Find the intersections of a certain ray with this sphere.
     *
     * @param ray The ray to find intersections with.
     * @return A list of points which the ray intersects this at.
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return Intersectable.EmptyList;
    }

    /**
     *
     * @param p A point in 3D space.
     * @return  true: p is on the surface of the sphere. false: p is not on the surface of the sphere.
     */
    public boolean contains(Point3D p)
    {
        return _center.distance(p) == radios();
    }
    /*----------------END OPERATIONS----------------*/
}
