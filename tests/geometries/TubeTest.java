package geometries;

import org.junit.jupiter.api.Test;
import primitives.Line;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
        try {
            Tube test = new Tube(new Line(new Point3D(0, 0, -1), new Vector(0, 0, 1)), 1);
            // Test 1: on surface
            assertEquals(new Vector(1, 0, 0), test.getNormal(new Point3D(1, 0, 0.5)));
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
            final Tube tube = new Tube(new Line(new Vector(0, 0, 1)), 1);
            List<Point3D> expected = new ArrayList<>(1);

            // Test 1: pass in null as an argument
            assertThrows(NullPointerException.class, () -> tube.findIntersections(null));

            // Test 2: parallel, but not on the tube itself
            assertEquals(Intersectable.EmptyList, tube.findIntersections(new Ray(new Vector(0, 0, 1))));

            // Test 3: parallel, on the tube
            expected.add(new Point3D(1, 0, 0));
            assertEquals(expected, tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1))));

            // Test 4: 2 intersection points
            expected.clear();
            expected.add(new Point3D(-1,0,1));
            expected.add(new Point3D(0,1,2));
            assertEquals(new TreeSet<>(expected), new TreeSet<>(tube.findIntersections(new Ray(new Point3D(-1,0,1), new Vector(1,1,1)))));

            // Test 5: 1 intersection point
            expected.clear();
            expected.add(new Point3D(0,1,0.5));
            assertEquals(expected, tube.findIntersections(new Ray(new Point3D(1,1,0), new Vector(-2,0,1))));

            // Test 6: something that didn't work
            expected.clear();
            //expected.add(new Point3D(1,0,2));
            expected.add(new Point3D(-1,0,-2));
            assertEquals(new TreeSet<>(expected), new TreeSet<>(tube.findIntersections(new Ray(new Point3D(0.5,0,1), new Vector(-1,0,-2)))));
        }
        catch (Exception ex)
        {
            fail(ex.getMessage());
        }
    }
}