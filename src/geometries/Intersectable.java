package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.ZeroVectorException;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for object that can be intersected by a line in 3D space.
 * @author Ariel Darshan
 */
public interface Intersectable {
    class GeoPoint{
        public Geometry geometry;
        public Point3D point;

        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = new Point3D(point);
        }

        public GeoPoint(GeoPoint other){
            this.geometry = other.geometry;
            this.point = new Point3D(other.point);
        }
    }
    List<GeoPoint> EmptyList = new ArrayList<>();
    /**
     * Find the intersections of a certain ray with an object
     * @param ray   The ray to find intersections with.
     * @return  A list of points which the ray intersects this at.
     */
    List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException;
}