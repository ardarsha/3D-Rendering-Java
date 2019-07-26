package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import primitives.ZeroVectorException;


public class PointLight extends Light {
    /*----------------VARIABLES---------------------*/
    private Point3D _position;
    private double _Kc;
    private double _Kl;
    private double _Kq;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public PointLight(Point3D _position, double _Kc, double _Kl, double _Kq) {
        super();
        this._position = new Point3D(_position);
        this._Kc = _Kc;
        this._Kl = _Kl;
        this._Kq = _Kq;
    }

    public PointLight(Color _color, Point3D _position, double _Kc, double _Kl, double _Kq) {
        super(_color);
        this._position = new Point3D(_position);
        this._Kc = _Kc;
        this._Kl = _Kl;
        this._Kq = _Kq;
    }

    public PointLight(PointLight other){
        super(other._color);
        this._position = new Point3D(other._position);
        this._Kc = other._Kc;
        this._Kl = other._Kl;
        this._Kq = other._Kq;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    public Point3D getPosition() {
        return _position;
    }

    public double getKc() {
        return _Kc;
    }

    public double getKl() {
        return _Kl;
    }

    public double getKq() {
        return _Kq;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    @Override
    public Color getIntensity(Point3D p) throws ZeroVectorException {
        double d = p.distance(_position);
        double k = (_Kc + _Kl * d + _Kq * d * d);

        k = k > 1 ? 1 : k;

        return _color.scale(k);
    }

    @Override
    public Vector getL(Point3D p) throws ZeroVectorException {
        return p.subtract(_position).normalised();
    }
    /*----------------END OPERATIONS----------------*/
}
