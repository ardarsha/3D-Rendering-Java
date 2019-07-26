package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import primitives.ZeroVectorException;

public interface LightSource {

    Color getIntensity(Point3D p) throws ZeroVectorException;

    Vector getL(Point3D p) throws ZeroVectorException;
}
