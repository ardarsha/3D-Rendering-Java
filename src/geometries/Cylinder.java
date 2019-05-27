package geometries;

import primitives.*;

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
        if (!this.contains(p))
            return null;
        if (onSurface(p))
            return p.subtract(getAxis().getClosestPoint(p)).normalised();
        if (onBottom(p))
            return getAxis().getDirection().scale(-1).normalised();
        return getAxis().getDirection().normalised();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return Intersectable.EmptyList;
    }

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
     * @return true: p is on the shell of the cylinder. false: p is not on the shell of the cylinder.
     */
    public boolean onSurface(Point3D p) {
        try {
            Point3D closest = getAxis().getClosestPoint(p);
            return super.contains(p) &&
                    getAxis().getSource().distance(closest) <= _height &&
                    closest.subtract(getAxis().getSource()).dotProduct(getAxis().getDirection()) > 0;
        } catch (ZeroVectorException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    /**
     * A function in order to check if a given point is on the bottom of the cylinder.
     *
     * @param p A point in 3D space.
     * @return true: p is on the bottom of the cylinder. false: p is not on the bottom of the cylinder.
     */
    public boolean onBottom(Point3D p) throws ZeroVectorException {
        Point3D closest = getAxis().getClosestPoint(p);
        return closest.equals(getAxis().getSource()) && p.distance(getAxis().getSource()) <= radios();
    }

    /**
     * A function in order to check if a given point is on the top of the cylinder.
     *
     * @param p A point in 3D space.
     * @return true: p is on the top of the cylinder. false: p is not on the top of the cylinder.
     */
    public boolean onTop(Point3D p) throws ZeroVectorException {
        Point3D closest = getAxis().getClosestPoint(p);
        return closest.equals(getAxis().getSource().add(getAxis().getDirection().normalised().scale(_height))) && p.distance(closest) <= radios();
    }
    /*----------------END OPERATIONS----------------*/
}
