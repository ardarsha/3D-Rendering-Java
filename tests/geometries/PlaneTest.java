package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void getNormal() {
        try {
            assertEquals(new Vector(3, 4, 6).normalised() , new Plane(new Point3D(0, 1, 2), new Vector(3, 4, 6)).getNormal(null));
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
            Plane plane = new Plane(new Point3D(0, 0, 0), new Vector(1, 0, 0));
            List<Point3D> result = new ArrayList<>();

            // Test 1: ray parallel to plane
            assertEquals(Intersectable.EmptyList, plane.findIntersections(new Ray(new Vector(0, 2, -7), new Point3D(0, 0, 1))));

            // Test 2: ray perpendicular to plane.
            result.add(new Point3D(0, 1, 0));
            assertEquals(result, plane.findIntersections(new Ray(new Vector(1, 0, 0), new Point3D(1, 1, 0))));

            // Test 3: ray in an angle other than 90 or 180 deg.
            result.clear();
            result.add(new Point3D(0, 0.5, -1.5));
            assertEquals(result, plane.findIntersections(new Ray(new Vector(2, 1, 2), new Point3D(1, 1, -0.5))));

            // Test 4: ray is null
            assertThrows(NullPointerException.class, () -> plane.findIntersections(null));

            // Test 5: ray has zero vector as its direction
            assertThrows(ZeroVectorException.class, () -> plane.findIntersections(new Ray(Vector.ZERO)));
        }
        catch (Exception ex)
        {
            fail(ex.getMessage());
        }

    }
}