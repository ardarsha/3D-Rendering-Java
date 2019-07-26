package renderer;

import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

class RenderTest {
    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    RenderTest() {
        File file = new File(dir);
        file.mkdirs();
    }

    @Test
    void printGrid() {
        try {
            Render render = new Render(
                    new Scene("printGrid"),
                    new ImageWriter(dir + "printGrid", 500, 500, 500, 500)
            );
            render.printGrid(50);
            render.writeToImage();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    void renderImage() {
        try {
            Scene scene = new Scene("renderImage");
            scene.addGeometries(
                    new Sphere(50, new Point3D(0,0,-150)),
                    new Triangle(
                            new Point3D(100,0,-149),
                            new Point3D(0,100,-149),
                            new Point3D(100,100,-149)
                    ),
                    new Triangle(
                            new Point3D(100,0,-149),
                            new Point3D(0,-100,-149),
                            new Point3D(100,-100,-149)
                    ),
                    new Triangle(
                            new Point3D(-100,0,-149),
                            new Point3D(0,100,-149),
                            new Point3D(-100,100,-149)
                    ),
                    new Triangle(
                            new Point3D(-100,0,-149),
                            new Point3D(0,-100,-149),
                            new Point3D(-100,-100,-149)
                    )
            );
            scene.setCamera(new Camera(new Point3D(0,0,0), new Vector(0,0,-1), new Vector(0,1,0)), 150);
            Render render = new Render(
                    scene,
                    new ImageWriter(dir + "renderImage", 500, 500, 500, 500)
            );

            render.renderImage();
            render.printGrid(50);
            render.writeToImage();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}