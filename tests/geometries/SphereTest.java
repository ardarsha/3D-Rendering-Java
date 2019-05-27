package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        try {
            Sphere sphere = new Sphere(3, new Point3D(0, 0, 1));
            assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point3D(3, 0, 1)));
        }
        catch(Exception ex)
        {
            fail(ex.getMessage());
        }
    }

    @Test
    void findIntersections()
    {
        try {
            Sphere sphere = new Sphere(3);
            List<Point3D> expected = new ArrayList<>(2);
            expected.add(new Point3D(-2, 1, -2));
            expected.add(new Point3D(0, 3, 0));

            List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(1, 4, 1), new Vector(1, 1, 1)));

            // Test 1: two intersection points
            assertEquals(new TreeSet<>(expected), new TreeSet<>(result));

            expected = new ArrayList<>(1);
            expected.add(new Point3D(0, 3, 0));

            // Test 2: one intersection point
            assertEquals(expected, sphere.findIntersections(new Ray(new Point3D(0, 4, 1), new Vector(0, 1, 1))));


            // Test 3: no intersection points
            assertEquals(Intersectable.EmptyList, sphere.findIntersections(new Ray(new Point3D(1, 2, 5), new Vector(1, 1, 0))));

            // Test 4: ray is null
            assertThrows(NullPointerException.class, () -> sphere.findIntersections(null));

            // Test 5: ray has a zero vector as its direction
            assertThrows(ZeroVectorException.class, () -> sphere.findIntersections(new Ray(new Point3D(1, 2, 3), Vector.ZERO)));
        }
        catch (Exception ex)
        {
            fail(ex.getMessage());
        }
    }
}