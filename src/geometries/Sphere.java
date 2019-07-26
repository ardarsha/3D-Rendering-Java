package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for representing a sphere for 3D graphic rendering.
 *
 * @author Ariel Darshan
 */
public class Sphere extends RadialGeometry {

    /*----------------VARIABLES---------------------*/
    private Point3D _center;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for Sphere implementation, made for 3D graphic rendering. The center will be the Default 3D point of the class Point3D
     *
     * @param radios The radios of the Sphere.
     * @see Point3D
     */
    public Sphere(double radios) {
        super(radios);
        _center = new Point3D();
    }

    /**
     * Constructor for Sphere implementation, made for 3D graphic rendering.
     *
     * @param radios The radios of the sphere.
     * @param center   The center of the sphere.
     */
    public Sphere(double radios, Point3D center) {
        super(radios);
        _center = center;
    }

    public Sphere(Sphere sphere){
        super(sphere);
        this._center = new Point3D(sphere._center);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     * @return The radios of the sphere.
     */
    @Override
    public double radios() {
        return super.radios();
    }

    /**
     * @return The center 3D point of the sphere.
     */
    public Point3D getCenter() {
        return new Point3D(_center);
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     * @param p A point in 3D space.
     * @return The normal vector to the sphere in the direction of p
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {
        return p.subtract(_center).normalised();
    }

    /**
     * Find the intersections of a certain ray with this sphere.
     *
     * @param ray The ray to find intersections with.
     * @return A list of points which the ray intersects this at.
     */
    public List<Point3D> findIntersectionsPoint3D(Ray ray) throws ZeroVectorException {
        if (null == ray)
            throw new NullPointerException();

        Point3D middlePoint = ray.getClosestPoint(_center);
        double tMiddle = ray.getOrigin().distance(middlePoint);
        double sqDis = _center.squareDistance(middlePoint);

        if (sqDis > radios()* radios())
            return new ArrayList<>();

        if (middlePoint.subtract(ray.getOrigin()).dotProduct(ray.getDirection()) < 0)
            return new ArrayList<>();

        double delta = Math.sqrt(radios() * radios() - sqDis);
        double t1 = tMiddle - delta,
                t2 = tMiddle + delta;

        List<Point3D> ret = new ArrayList<>();
        if (t1 >= 0)
            ret.add(ray.getOrigin().add(ray.getDirection().scale(t1)));
        if (t2 >= 0)
            ret.add(ray.getOrigin().add(ray.getDirection().scale(t2)));

        return ret;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
        List<GeoPoint> ret = new ArrayList<>();
        for (Point3D point : this.findIntersectionsPoint3D(ray)){
            ret.add(new GeoPoint(new Sphere(this), point));
        }
        return ret;
    }

    /**
     * @param p A point in 3D space.
     * @return true: p is on the surface of the sphere. false: p is not on the surface of the sphere.
     */
    public boolean contains(Point3D p) {
        return new Coordinate(_center.distance(p)).equals(radios());
    }
    /*----------------END OPERATIONS----------------*/
}
