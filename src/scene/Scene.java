package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.Light;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.*;


public class Scene {

    /*----------------VARIABLES---------------------*/
    private String _name;
    private Color _backgroundColor;
    private AmbientLight _ambient;
    private List<Light> _lights;
    private Geometries _geometries;
    private Camera _camera;
    private double _cameraDistance;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Scene() throws ZeroVectorException {
        this._backgroundColor = new Color(0,0,0);
        this._cameraDistance = 100;
        this._geometries = new Geometries();
        this._ambient = new AmbientLight();
        this._lights = new ArrayList<>();
        this._camera = new Camera(new Point3D(0,0,0), new Vector(-1,0,0), new Vector(0,0,1));
        this._name = "AnonymousScene";
    }

    public Scene(String name) throws ZeroVectorException {
        this();
        _name = name;
    }

    public Scene(String _name, Color _backgroundColor, AmbientLight _ambient, List<Light> lights, Geometries _geometries, Camera _camera, double _cameraDistance) {
        this._name = _name;
        this._backgroundColor = new Color(_backgroundColor.getRGB());
        this._ambient = new AmbientLight(_ambient);
        this._lights.addAll(lights);
        this._geometries = new Geometries(_geometries);
        this._camera = new Camera(_camera);
        this._cameraDistance = _cameraDistance;
    }

    public Scene(Scene other) {
        this._cameraDistance = other._cameraDistance;
        this._camera = new Camera(other._camera);
        this._ambient = new AmbientLight(other._ambient);
        this._lights = new ArrayList<>(other._lights);
        this._backgroundColor = new Color(other._backgroundColor.getRGB());
        this._geometries = new Geometries(other._geometries);
        this._name = other._name;
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public AmbientLight getAmbient() {
        return _ambient;
    }

    public void setAmbient(AmbientLight ambient) {
        this._ambient = new AmbientLight(ambient);
    }

    public Color getBackgroundColor() {
        return _backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this._backgroundColor = new Color(backgroundColor.getRGB());
    }

    public double getCameraDistance() {
        return _cameraDistance;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public String getName() {
        return _name;
    }

    public Camera getCamera() {
        return _camera;
    }

    public void setCamera(Camera _camera, double cameraDistance) {
        this._camera = new Camera(_camera);
        this._cameraDistance = cameraDistance;
    }

    public void setGeometries(Geometries _geometries) {
        this._geometries = new Geometries(_geometries);
    }

    public List<Light> getLights(){
        return new ArrayList<>(this._lights);
    }

    public Iterator<Light> getLightIterator(){
        return _lights.iterator();
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public String toString() {
        return "Scene{" +
                "_name='" + _name + '\'' +
                ", _backgroundColor=" + _backgroundColor +
                ", _ambient=" + _ambient +
                ", _geometries=" + _geometries +
                ", _camera=" + _camera +
                ", _cameraDistance=" + _cameraDistance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scene scene = (Scene) o;
        return Double.compare(scene._cameraDistance, _cameraDistance) == 0 &&
                _name.equals(scene._name) &&
                _backgroundColor.equals(scene._backgroundColor) &&
                _ambient.equals(scene._ambient) &&
                _geometries.equals(scene._geometries) &&
                _camera.equals(scene._camera);
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/
    public void addGeometries(Geometry... geometries) {
        this._geometries.add(geometries);
    }

    public void addGeometries(Geometries geometries, Geometry... others){
        this._geometries.add(geometries);
        this.addGeometries(others);
    }

    public void addLights(Light... lights){
        this._lights.addAll(Arrays.asList(lights));
    }
    /*----------------END OPERATIONS----------------*/
}
