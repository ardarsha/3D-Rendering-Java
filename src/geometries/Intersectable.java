package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for object that can be intersected by a line in 3D space.
 * @author Ariel Darshan
 */
public interface Intersectable {

    List<Point3D> EmptyList = new ArrayList<>();
    /**
     * Find the intersections of a certain ray with an object
     * @param ray   The ray to find intersections with.
     * @return  A list of points which the ray intersects this at.
     */
    List<Point3D> findIntersections(Ray ray);
}
