package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Abstract class for the flat geometries in the rendering options.
 * @author Ariel Darshan
 */
public interface FlatGeometry extends Geometry {
    @Override
    List<Point3D> findIntersections(Ray ray);
}
