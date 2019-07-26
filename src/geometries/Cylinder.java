package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of a cylinder for rendering purposes. A finite version of Tube.
 *
 * @author Ariel Darshan
 */
public class Cylinder extends Tube {

    /*----------------VARIABLES---------------------*/
    private double _height;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor based on a Tube and the _height.
     *
     * @param tube The cylinders environment: the orientation, radios and location in 3D space.
     * @param high The _height of the cylinder from the tubes point of origin in the direction of the tube.
     */
    public Cylinder(Tube tube, double high) throws ZeroVectorException {
        super(tube.getAxis(), tube.radios());
        _height = high;
    }

    /**
     * Constructor based on axis, radios and the _height.
     *
     * @param axis   Orientation and location in 3D space
     * @param radios The radios of the cylinder
     * @param high   The _height of the cylinder from the axis' point of origin in the direction of the axis.
     */
    public Cylinder(Line axis, double radios, double high) throws ZeroVectorException {
        this(new Tube(axis, radios), high);
    }

    public Cylinder(Cylinder other) throws ZeroVectorException {
        super(other);
        this._height = other._height;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     * @return The _height of the cylinder.
     */
    public double getHeight() {
        return _height;
    }

    /**
     * @return The axis on which the cylinder is based on.
     */
    @Override
    public Line getAxis() throws ZeroVectorException {
        return super.getAxis();
    }

    /**
     * Get the plane of the top of the cylinder
     *
     * @return A plane which the top of the cylinder resides on
     * @throws ZeroVectorException Shouldn't ever happen. This must mean a result of a much deeper error
     */
    public Plane getTop() throws ZeroVectorException {
        Plane plane = new Plane(this.getAxis().getOrigin().add(this.getAxis().getDirection().scale(_height)), this.getAxis().getDirection());
        plane.setEmission(this.getEmission());
        return plane;
    }

    /**
     * Get the plane of the bottom of the cylinder
     *
     * @return A plane which the bottom of the cylinder resides on
     * @throws ZeroVectorException Shouldn't ever happen. This must mean a result of a much deeper error
     */
    public Plane getBottom() throws ZeroVectorException {
        Plane plane = new Plane(this.getAxis().getOrigin(), this.getAxis().getDirection());
        plane.setEmission(this.getEmission());
        return plane;
    }

    /**
     * @return The radios of the cylinder.
     */
    @Override
    public double radios() {
        return super.radios();
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    /**
     * @param p The 3D point of view to get the normal to.
     * @return A vector with the direction of the point, and normal to the cylinder surface.
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {
        if (onSurface(p))
            return p.subtract(getAxis().getClosestPoint(p)).normalised();
        if (onBottom(p))
            return getAxis().getDirection().scale(-1).normalised();
        return getAxis().getDirection().normalised();
    }

    /**
     * @param ray
     * @return
     * @throws ZeroVectorException
     */
    public List<Point3D> findIntersectionsPoint3D(Ray ray) throws ZeroVectorException {
        if (null == ray)
            throw new NullPointerException();

        List<Point3D> tubeRet = super.findLineIntersections(new Line(ray));
        if (tubeRet.size() == 0) {
            if (ray.getDirection().crossProduct(this.getAxis().getDirection()).equals(Vector.ZERO)) {
                if (ray.getOrigin().squareDistance(this.getAxis().getClosestPoint(ray.getOrigin())) < radios() * radios()) {
                    Geometries caps = new Geometries(this.getBottom(), this.getTop());
                    return caps.findIntersectionsPoint3D(ray);
                }
            }
            return new ArrayList<>();
        }
        if (tubeRet.size() == 1) {
            if (this.contains(tubeRet.get(0))) {
                return tubeRet;
            }
            return new ArrayList<>();
        }

        if (this.contains(tubeRet.get(0)) && this.contains(tubeRet.get(1)))
            return super.findIntersectionsPoint3D(ray);

        if (!this.contains(tubeRet.get(0)) && !this.contains(tubeRet.get(1))) {
            Geometries caps = new Geometries(this.getBottom(), this.getTop());
            List<Point3D> capsIntersection = caps.findIntersectionsPoint3D(ray);
            if(capsIntersection.size()!= 0 && this.contains(capsIntersection.get(0))){
                return capsIntersection;
            }
            return new ArrayList<>();
        }

        List<Point3D> ret = new ArrayList<>(2);
        if (this.contains(tubeRet.get(0))) {
            if (super.findIntersectionsPoint3D(ray).contains(tubeRet.get(0)))
                ret.add(tubeRet.get(0));
        } else {
            if (super.findIntersectionsPoint3D(ray).contains(tubeRet.get(1)))
                ret.add(tubeRet.get(1));
        }
        if (getTop().findIntersections(ray).size() != 0 && this.contains(getTop().findIntersections(ray).get(0).point)) {
            ret.add(getTop().findIntersectionsPoint3D(ray).get(0));
        } else if (getBottom().findIntersectionsPoint3D(ray).size() != 0){
            ret.add(getBottom().findIntersectionsPoint3D(ray).get(0));
        }
        return ret;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
        List<GeoPoint> ret = new ArrayList<>();
        for (Point3D point : this.findIntersectionsPoint3D(ray)){
            ret.add(new GeoPoint(new Cylinder(this), point));
        }
        return ret;
    }

    //    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
//        List<GeoPoint> tubeRet = super.findIntersections(ray);
//        List<GeoPoint> ret = new ArrayList<>();
//        switch (tubeRet.size()){
//            case 0:
//                if (!this.getAxis().getDirection().crossProduct(ray.getDirection()).equals(Vector.ZERO))
//                    return Intersectable.EmptyList;
//                Geometries caps = new Geometries(getTop(), getBottom());
//                return caps.findIntersections(ray);
//            case 1:
//                ret.add(new GeoPoint(new Cylinder(this), tubeRet.get(0).point));
//                return ret;
//            case 2:
//
//                break;
//            default:
//                return Intersectable.EmptyList;
//        }
//        List<GeoPoint> tubeRet = super.findIntersections(ray);
//        List<GeoPoint> ret = new ArrayList<>();
//        for (GeoPoint gp : tubeRet){
//            if (this.contains(gp.point)){
//                ret.add(gp);
//            }
//        }
//        return ret;
//    }

    /**
     * A function in order to check if a given point is on the surface of the cylinder.
     *
     * @param p A point in 3D space.
     * @return true: p is on the surface of the cylinder. false: p is not on the surface of the cylinder.
     */
    public boolean contains(Point3D p) throws ZeroVectorException {
        return onSurface(p) || onBottom(p) || onTop(p);
    }

    /**
     * A function in order to check if a given point is on the shell (round part) of the cylinder.
     *
     * @param p A point in 3D space.
     * @return true: p is on the surface of the cylinder. false: p is not on the surface of the cylinder.
     */
    public boolean onSurface(Point3D p) throws ZeroVectorException {
            Point3D closest = getAxis().getClosestPoint(p);
            if (this.getAxis().getOrigin().equals(closest))
                return false;
            return super.contains(p) &&
                    getAxis().getOrigin().distance(closest) <= _height &&
                    closest.subtract(getAxis().getOrigin()).dotProduct(getAxis().getDirection()) > 0;
    }

    /**
     * A function in order to check if a given point is on the bottom of the cylinder.
     *
     * @param p A point in 3D space.
     * @return true: p is on the bottom of the cylinder. false: p is not on the bottom of the cylinder.
     */
    public boolean onBottom(Point3D p) throws ZeroVectorException {
        Point3D closest = getAxis().getClosestPoint(p);
        return closest.equals(getAxis().getOrigin()) && p.distance(getAxis().getOrigin()) <= radios();
    }

    /**
     * A function in order to check if a given point is on the top of the cylinder.
     *
     * @param p A point in 3D space.
     * @return true: p is on the top of the cylinder. false: p is not on the top of the cylinder.
     */
    public boolean onTop(Point3D p) throws ZeroVectorException {
        Point3D closest = getAxis().getClosestPoint(p);
        return closest.equals(getAxis().getOrigin().add(getAxis().getDirection().normalised().scale(_height))) && p.distance(closest) <= radios();
    }
    /*----------------END OPERATIONS----------------*/
}
