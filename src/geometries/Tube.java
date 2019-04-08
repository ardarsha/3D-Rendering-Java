package geometries;

import primitives.Ray;

public class Tube extends RadialGeometry {

    /*----------------VARIABLES---------------------*/
    private Ray axis;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Tube(Ray ray, double rad) {
        super(rad);
        axis = new Ray(ray);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Ray getAxis() {
        return new Ray(axis);
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