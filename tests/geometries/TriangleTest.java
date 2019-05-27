package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void getNormal() {
        try {
            Triangle test = new Triangle(new Point3D(1, 2, 3), new Point3D(2, 3, 4), new Point3D(3, 4, 6));
            assertEquals(new Vector(1,-1,0).normalised(), test.getNormal(null));
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
            Triangle test = new Triangle(new Point3D(1, 2, 3), new Point3D(2, 3, 4), new Point3D(3, 4, 6));

            // Test 1: pass in null as the argument
            assertThrows(NullPointerException.class, () -> test.findIntersections(null));

            // Test 2: parallel to plane
            assertEquals(Intersectable.EmptyList, test.findIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(1, 1, 1))));

            // Test 3: intersects plane, but not triangle
            assertEquals(Intersectable.EmptyList, test.findIntersections(new Ray(new Point3D(0,0,0), new Vector(1,-1,0))));

            // Test 4: intersects triangle
            List<Point3D> result = new ArrayList<>(1);
            result.add(new Point3D(2,3,4.2));
            assertEquals(result, test.findIntersections(new Ray(new Point3D(1,4,-2), new Vector(1,-1,6.2))));
        }
        catch (Exception ex)
        {
            fail(ex.getMessage());
        }
    }
}