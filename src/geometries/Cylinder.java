package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    /*----------------VARIABLES---------------------*/
    private double height;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Cylinder(Tube direction, double high) {
        super(direction.getAxis(), direction.radios());
        height = high;
    }

    public Cylinder(Ray axis, double radios, double high)
    {
        this(new Tube(axis, radios), high);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public double getHeight() {
        return height;
    }

    @Override
    public Ray getAxis() {
        return super.getAxis();
    }

    @Override
    public double radios() {
        return super.radios();
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}
