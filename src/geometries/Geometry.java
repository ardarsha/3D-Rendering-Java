package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.List;

/**
 * An interface for geometries in a 3D graphics rendering world
 * @author Ariel Darshan
 */
public interface Geometry extends Intersectable{
    /**
     *  An interface for getting the normal to a geometry
     * @param p A point in 3D space.
     * @return  A vector in the direction of p (from the geometry), and normal to the geometry itself.
     */
    Vector getNormal(Point3D p) throws ZeroVectorException;

    /**
     * Find the intersections of a certain ray with a geometry.
     * @param ray   The ray to find intersections with.
     * @return  A list of points which the ray intersects this at.
     */
    @Override
    List<Point3D> findIntersections(Ray ray);
}
