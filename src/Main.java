import primitives.*;

public class Main {
    public static void main(String args[])
    {
        Coordinate c[] = new Coordinate[3];
        c[0] = new Coordinate(1);
        c[1] = new Coordinate(-1.5);
        c[2] = new Coordinate(0);
        Point3D p1 = new Point3D(c[0], c[1], c[2]), p2 = new Point3D(c[0], c[0], c[1]);
        Vector v1 = new Vector(p1), v2 = new Vector(p2);
        System.out.println(v1.dotProduct(v2.crossProduct(v2)));
        System.out.println();
        System.out.println(p1);
        System.out.println(p2);
    }
}
