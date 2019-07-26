package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


public class DirectionalLight extends Light {

    /*----------------VARIABLES---------------------*/
    private Vector _direction;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public DirectionalLight(Vector _direction) {
        super();
        this._direction = new Vector(_direction);
    }

    public DirectionalLight(Color color, Vector _direction){
        super(color);
        this._direction = new Vector(_direction);
    }

    public DirectionalLight(DirectionalLight other){
        super(other._color);
        this._direction = new Vector(other._direction);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Vector getDirection() {
        return _direction;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p){
        return getDirection();
    }
    /*----------------END OPERATIONS----------------*/
}
