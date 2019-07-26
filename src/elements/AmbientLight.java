package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


public class AmbientLight extends Light {
    /*----------------VARIABLES---------------------*/
    private double _Ka;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public AmbientLight(Color Ia, double Ka) {
        _color = new Color(Ia.getRGB());
        _Ka = Ka;
    }

    public AmbientLight(Color Ia){
        _color = new Color(Ia.getRGB());
        _Ka = 0.1;
    }

    public AmbientLight(int r, int g, int b){
        this(new Color(r,g,b));
    }

    public AmbientLight(){
        _color = new Color(255,255,255);
        _Ka = 1;
    }

    public AmbientLight(AmbientLight other) {
        this._color = new Color(other._color.getRGB());
        this._Ka = other._Ka;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmbientLight that = (AmbientLight) o;
        return Double.compare(that._Ka, _Ka) == 0 &&
                _color.equals(that._color);
    }

    @Override
    public String toString() {
        return "AmbientLight{" +
                "_color=" + _color +
                ", _Ka=" + _Ka +
                '}';
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    @Override
    public Color getIntensity(Point3D p) {
                return _color.scale(_Ka);
    }

    @Override
    public Vector getL(Point3D p) {
        // dummy vector, no meaning
        return new Vector(1,0,0);
    }

    /*----------------END OPERATIONS----------------*/
}
