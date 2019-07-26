package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import primitives.ZeroVectorException;


public class SpotLight extends PointLight {
    /*----------------VARIABLES---------------------*/
    private Vector _direction;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public SpotLight(Point3D _position, double _Kc, double _Kl, double _Kq, Vector _direction) {
        super(_position, _Kc, _Kl, _Kq);
        this._direction = new Vector(_direction);
    }

    public SpotLight(Color _color, Point3D _position, double _Kc, double _Kl, double _Kq, Vector _direction) throws ZeroVectorException {
        super(_color, _position, _Kc, _Kl, _Kq);
        this._direction = new Vector(_direction).normalised();
    }

    public SpotLight(SpotLight other){
        super(other._color, other.getPosition(), other.getKc(), other.getKl(), other.getKq());
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
    public Color getIntensity(Point3D p) throws ZeroVectorException {
        return super.getIntensity(p).scale(Math.max(0, _direction.dotProduct(getL(p))));
    }

    @Override
    public Vector getL(Point3D p) throws ZeroVectorException {
        return super.getL(p);
    }

    /*----------------END OPERATIONS----------------*/
}
