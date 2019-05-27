package primitives;

public class Line extends Ray{

    public Line(Vector dir) throws ZeroVectorException
    {
        super(dir);
    }

    public Line(Vector dir, Point3D src) throws ZeroVectorException {
        super(dir, src);
    }

    public Line(Point3D src, Vector dir) throws ZeroVectorException
    {
        super(src, dir);
    }

    public Line(Ray other) throws ZeroVectorException {
        super(other);
    }
}
