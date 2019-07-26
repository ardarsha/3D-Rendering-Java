package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

/**
 * An implementation of an infinite cylinder in 3D space.
 *
 * @author Ariel Darshan
 */
public class Tube extends RadialGeometry {

    /*----------------VARIABLES---------------------*/
    private Line _axis;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for tube class for rendering purposes
     *
     * @param axis The axis on which the tube is on.
     * @param rad  The radios of the tube.
     */
    public Tube(Line axis, double rad) throws ZeroVectorException {
        super(rad);
        _axis = new Line(axis);
    }

    public Tube(Tube tube) throws ZeroVectorException {
        super(tube);
        this._axis = new Line(tube._axis);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     * @return The axis on which the tube resides.
     */
    public Line getAxis() throws ZeroVectorException {
        return new Line(_axis);
    }

    /**
     * @return The radios of the tube.
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
     * @param p A point in 3D space.
     * @return The Vector that is normal to the tube at the closest point to p on the tube.
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException {

        double t = p.subtract(_axis.getOrigin()).length() * p.subtract(_axis.getOrigin()).length() - radios() * radios();
        return p.subtract(_axis.getOrigin().add(_axis.getDirection().normalised().scale(sqrt(t))));
    }

    // found algorithm on internet
    private List<Coordinate> getIntersectionScalars(Line ray) throws ZeroVectorException {
        if (null == ray)
            throw new NullPointerException();
        if (ray.getDirection().equals(Vector.ZERO))
            throw new ZeroVectorException("This shouldn't be happening");

        if (this._axis.getDirection().crossProduct(ray.getDirection()).equals(Vector.ZERO)) {
            if (this.contains(ray.getOrigin())) {
                List<Coordinate> ret = new ArrayList<>(1);
                ret.add(new Coordinate(0));
                return ret;
            }
            return new ArrayList<>();
        }

        int shift = 0;
        Vector V = ray.getDirection();
        Vector AB = this._axis.getDirection();
        Vector AO = ray.getOrigin().subtract(this._axis.getOrigin());
        while (AO.crossProduct(AB).equals(Vector.ZERO)){
            AO = ray.getOrigin().add(ray.getDirection()).subtract(this._axis.getOrigin());
            ++shift;
        }
        Vector AOxAB = AB.crossProduct(AO);
        Vector VxAB = V.crossProduct(AB);

        double ab2 = AB.squared();
        double A = VxAB.squared(),
                B = -2 * VxAB.dotProduct(AOxAB),
                C = AOxAB.squared() - (radios() * radios() * ab2);
        double delta = B * B - 4 * A * C;
        if (new Coordinate(delta).equals(Coordinate.ZERO)) {
            double t = -B / (2 * A);
            List<Coordinate> ret = new ArrayList<>();
            ret.add(new Coordinate(t));
            return ret;
        } else if (delta < 0)
            return new ArrayList<>();
        else if (delta > 0) {
            double t1 = (-B + Math.sqrt(delta)) / (2 * A),
                    t2 = (-B - Math.sqrt(delta)) / (2 * A);
            List<Coordinate> ret = new ArrayList<>();
            ret.add(new Coordinate(t1 - shift));
            ret.add(new Coordinate(t2 - shift));
            return ret;
        }
        return new ArrayList<>();
    }

    public List<Point3D> findIntersectionsPoint3D(Ray ray) throws ZeroVectorException {
        List<Point3D> ret = new ArrayList<>();
        for (Coordinate t : getIntersectionScalars(new Line(ray)))
        {
            if (t.get() >= 0)
                ret.add(ray.getOrigin().add(ray.getDirection().scale(t.get())));
        }
        return ret;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
        List<GeoPoint> ret = new ArrayList<>();
        for (Point3D point : this.findIntersectionsPoint3D(ray)){
            ret.add(new GeoPoint(new Tube(this), point));
        }
        return ret;
    }

    public List<Point3D> findLineIntersections(Line line) throws ZeroVectorException
    {
        List<Point3D> ret = new ArrayList<>();
        for (Coordinate t : this.getIntersectionScalars(line))
        {
            ret.add(line.getOrigin().add(line.getDirection().scale(t.get())));
        }
        return ret;
    }

    /**
     * @param p A point in 3D space.
     * @return true: p is on the surface of the tube. false: p is not on the surface of the tube.
     */
    public boolean contains(Point3D p) throws ZeroVectorException {
        return new Coordinate(_axis.getClosestPoint(p).distance(p)).subtract(new Coordinate(radios())).get() <= Geometry.EPSILON;
    }
    /*----------------END OPERATIONS----------------*/
}