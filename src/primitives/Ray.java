package primitives;

public class Ray extends FixedVector {

    public Ray(Vector dir) throws ZeroVectorException
    {
        super(dir);
    }

    public Ray(Vector dir, Point3D src) throws ZeroVectorException{
        super(dir, src);
    }

    public Ray(Point3D src, Vector dir) throws ZeroVectorException
    {
        super(src, dir);
    }

    public Ray(FixedVector other) {
        super(other);
    }
}
