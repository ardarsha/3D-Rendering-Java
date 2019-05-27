package geometries;

/**
 * An abstract class for representing geometries which require a radios.
 * @author Ariel Darshan
 */
public abstract class RadialGeometry implements Geometry {
    /*----------------VARIABLES---------------------*/
    private double _radios;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/

    /**
     *
     * @param rad Radios.
     */
    public RadialGeometry(double rad)
    {
        _radios = rad;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    /**
     *
     * @return The radios.
     */
    public double radios()
    {
        return _radios;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
//
//    @Override
//    public abstract Vector getNormal(Point3D p) ;

    /*----------------END OPERATIONS----------------*/
}