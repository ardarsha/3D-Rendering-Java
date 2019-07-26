package primitives;

public class Material {
    /*----------------VARIABLES---------------------*/
    private double _Kd;
    private double _Ks;
    private int _nShininess;
    private double _Kr;
    private double _Kt;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Material(double _Kd, double _Ks, int _nShininess) {
        this._Kd = _Kd;
        this._Ks = _Ks;
        this._nShininess = _nShininess;
        this._Kt = 0;
        this._Kr = 0;
    }

    public Material(double _Kd, double _Ks, int _nShininess, double _Kr, double _Kt){
        this(_Kd, _Ks, _nShininess);
        this._Kt = _Kt;
        this._Kr = _Kr;
    }


    public Material(Material other){
        this._Kd = other._Kd;
        this._Ks = other._Ks;
        this._nShininess = other._nShininess;
        this._Kr = other._Kr;
        this._Kt = other._Kt;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/

    public double getKd() {
        return _Kd;
    }

    public double getKs() {
        return _Ks;
    }

    public double getKr() {
        return _Kr;
    }

    public double getKt() {
        return _Kt;
    }

    public int getNShininess() {
        return _nShininess;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/

    @Override
    public String toString() {
        return "diffusion coefficient: " + _Kd + '\n' +
                "specular coefficient: " + _Ks + '\n' +
                "refraction level (shininess): " + _nShininess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Double.compare(material._Kd, _Kd) == 0 &&
                Double.compare(material._Ks, _Ks) == 0 &&
                _nShininess == material._nShininess;
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    /*----------------END OPERATIONS----------------*/
}
