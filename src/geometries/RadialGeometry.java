package geometries;

public abstract class RadialGeometry {
    /*----------------VARIABLES---------------------*/
    private double radios;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public RadialGeometry(double rad)
    {
        radios = rad;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public double radios()
    {
        return radios;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}