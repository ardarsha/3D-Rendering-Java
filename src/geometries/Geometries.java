package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.ZeroVectorException;

import java.util.*;

/**
 * A class for representing a collection of geometries
 *
 * @author Ariel Darshan
 */
public class Geometries implements Intersectable {

    private List<Geometry> _list;

    /**
     * Constructor for Geometries
     *
     * @param geometries A list of 0 or more Intersectables
     */
    public Geometries(Geometry... geometries) {
        _list = new ArrayList<>(geometries.length);
        _list.addAll(Arrays.asList(geometries));
    }

    public Geometries(Geometries other){
        this._list = new ArrayList<>(other._list);
    }

    public Iterator<Geometry> getGeometriesIterator(){
        return this._list.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geometries that = (Geometries) o;
        return Objects.equals(_list, that._list);
    }

    /**
     * Add geometries to collection
     *
     * @param geometries A list of 0 or more Intersectables
     */
    public void add(Geometry... geometries) {
        _list.addAll(Arrays.asList(geometries));
    }

    public void add(Geometries geometries){
        _list.addAll(geometries._list);
    }

    /**
     * Find the intersections of a certain ray with an object
     *
     * @param ray The ray to find intersections with.
     * @return A list of points which the ray intersects this at.
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws ZeroVectorException {
        List<GeoPoint> ret = new ArrayList<>();
        for (Intersectable geometry : _list) {
            ret.addAll(geometry.findIntersections(ray));
        }
        return ret;
    }

    public List<Point3D> findIntersectionsPoint3D(Ray ray) throws ZeroVectorException {
        List<Point3D> ret = new ArrayList<>();
        for (GeoPoint geoPoint : this.findIntersections(ray)){
            ret.add(geoPoint.point);
        }
        return ret;
    }
}
