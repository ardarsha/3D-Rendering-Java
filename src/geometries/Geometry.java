package geometries;

import primitives.*;


/**
 * An interface for geometries in a 3D graphics rendering world
 * @author Ariel Darshan
 */
public abstract class Geometry implements Intersectable{

    final static double EPSILON = 0.01;

    private Color _emission;
    private Material _material;

    public Geometry(){
        this._emission = new Color(Color.black);
        this._material = new Material(0,0,0);
    }

    public Geometry(Material material, Color emission){
        this._material = new Material(material);
        this._emission = new Color(emission);
    }

    public Geometry(Geometry other){
        this._emission = new Color(other._emission);
        this._material = new Material(other._material);
    }

    public Color getEmission() {
        return _emission;
    }

    public void setEmission(Color _emission) {
        this._emission = new Color(_emission.getRGB());
    }

    public Material getMaterial() {
        return new Material(_material);
    }

    public void setMaterial(Material _material) {
        this._material = new Material(_material);
    }

    /**
     *  An interface for getting the normal to a geometry
     * @param p A point in 3D space.
     * @return  A vector in the direction of p (from the geometry), and normal to the geometry itself.
     */
    public abstract Vector getNormal(Point3D p) throws ZeroVectorException;
//    /**
//     * Find the intersections of a certain ray with a geometry.
//     * @param ray   The ray to find intersections with.
//     * @return  A list of points which the ray intersects this at.
//     */
//    @Override
//    List<Point3D> findIntersections(Ray ray) throws ZeroVectorException;
}
