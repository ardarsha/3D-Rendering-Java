package geometries;

import primitives.*;

import java.util.List;

import static java.lang.Math.sqrt;

/**
 * An implementation of an infinite cylinder in 3D space.
 * @author Ariel Darshan
 */
public class Tube extends RadialGeometry {

    /*----------------VARIABLES---------------------*/
    private Line _axis;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     * Constructor for tube class for rendering purposes
     * @param axis   The axis on which the tube is on.
     * @param rad   The radios of the tube.
     */
    public Tube(Line axis, double rad) throws ZeroVectorException{
        super(rad);
        _axis = new Line(axis);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     *
     * @return  The axis on which the tube resides.
     */
    public Line getAxis() throws ZeroVectorException {
        return new Line(_axis);
    }

    /**
     *
     * @return  The radios of the tube.
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
     *
     * @param p A point in 3D space.
     * @return  The Vector that is normal to the tube at the closest point to p on the tube.
     */
    @Override
    public Vector getNormal(Point3D p) throws ZeroVectorException{

        double t = p.subtract(_axis.getSource()).length() * p.subtract(_axis.getSource()).length() - radios() * radios();
        return p.subtract(_axis.getSource().add(_axis.getDirection().normalised().scale(sqrt(t))));
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return Intersectable.EmptyList;
    }

    /**
     *
     * @param p A point in 3D space.
     * @return  true: p is on the surface of the tube. false: p is not on the surface of the tube.
     */
    public boolean contains(Point3D p) throws ZeroVectorException {
            return _axis.getClosestPoint(p).distance(p) == radios();
    }
    /*----------------END OPERATIONS----------------*/
}