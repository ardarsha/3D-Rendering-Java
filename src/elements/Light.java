package elements;

import primitives.Color;


/**
 *
 * @author Ariel Darshan
 */
abstract public class Light implements LightSource{

    /*----------------VARIABLES---------------------*/
    protected Color _color;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Light(){
        this._color = new Color(Color.white);
    }

    public Light(Color _color) {
        this._color = new Color(_color);
    }

    public Light(Light other){
        this._color = new Color(other._color);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    public Color getIntensity(){
        return new Color(_color);
    }
    /*----------------END OPERATIONS----------------*/
}
