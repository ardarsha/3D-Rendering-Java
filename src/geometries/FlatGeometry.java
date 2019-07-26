package geometries;

/**
 * Abstract class for the flat geometries in the rendering options.
 * @author Ariel Darshan
 */
public abstract class FlatGeometry extends Geometry {
    public FlatGeometry(){
        super();
    }
    public FlatGeometry(FlatGeometry other){
        super(other);
    }
}
