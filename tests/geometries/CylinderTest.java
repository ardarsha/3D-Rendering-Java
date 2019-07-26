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

class CylinderTest {

    @Test
    void getNormal() {
        try {
            Cylinder test = new Cylinder(new Line(new Point3D(0, 0, -1), new Vector(0, 0, 1)), 1, 2);
            // Test 1: on surface
            assertEquals(new Vector(1, 0, 0), test.getNormal(new Point3D(1, 0, 0.5)));

            // Test 2: on top
            assertEquals(new Vector(0, 0, 1), test.getNormal(new Point3D(0.5, 0, 1)));

            // Test 3:
            assertEquals(new Vector(0, 0, -1), test.getNormal(new Point3D(0.5, 0, -1)));
        } catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
    }

    @Test
    void findIntersections() {
        try {
            Cylinder test = new Cylinder(new Line(new Point3D(0, 0, -1), new Vector(0, 0, 1)), 1, 2);
            List<Point3D> expected = new ArrayList<>();

            // Test 1: pass in null as an argument
            assertThrows(NullPointerException.class, () -> test.findIntersections(null));

            // Test 2: doesn't intersect tube
            assertEquals(Intersectable.EmptyList, test.findIntersections(new Ray(new Point3D(3,0,0), new Vector(0,1,1))));

            // Test 3: intersects tube, but not cylinder
            assertEquals(Intersectable.EmptyList, test.findIntersections(new Ray(new Point3D(-1,0,2), new Vector(1,1,1))));


            // Test 4: intersects the top and bottom
            expected.clear();
            expected.add(new Point3D(0.5,0,1));
            expected.add(new Point3D(-0.5,0,-1));
            assertEquals(new TreeSet<>(expected), new TreeSet<>(test.findIntersections(new Ray(new Point3D(0.5,0,1), new Vector(-1,0,-2)))));

            // Test 5: intersects the top and surface
            expected.clear();
            expected.add(new Point3D(0.5,0,1));
            expected.add(new Point3D(1,0,-0.5));
            assertEquals(new TreeSet<>(expected), new TreeSet<>(test.findIntersections(new Ray(new Point3D(0.5,0,1), new Vector(-0.5,0,1.5)))));

            //Test 6: intersects the bottom and surface
            expected.clear();
            expected.add(new Point3D(1,0,0));
            expected.add(new Point3D(0.5,0,-1));
            assertEquals(new TreeSet<>(expected), new TreeSet<>(test.findIntersections(new Ray(new Point3D(1,0,0), new Vector(-1,0,-2)))));

            // Test 7: intersects the surface once, and nothing else
            expected.clear();
            expected.add(new Point3D(0,1,0.5));
            assertEquals(expected, test.findIntersections(new Ray(new Point3D(1,1,0), new Vector(-2,0,1))));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}