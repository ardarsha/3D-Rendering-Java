package renderer;

import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

class RenderColor2DTest {
    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    RenderColor2DTest() {
        File file = new File(dir);
        file.mkdirs();
    }


    @Test
    void renderImage() {
        try {
            Scene scene = new Scene("renderImage");
            scene.setAmbient(new AmbientLight(new Color(Color.black), 1));
            scene.setBackgroundColor(new Color(Color.green));
            Sphere sphere = new Sphere(50, new Point3D(0, 0, -150));
            Triangle triangle1 = new Triangle(
                    new Point3D(100, 0, -149),
                    new Point3D(0, 100, -149),
                    new Point3D(100, 100, -149)
            );
            Triangle triangle2 = new Triangle(
                    new Point3D(100, 0, -149),
                    new Point3D(0, -100, -149),
                    new Point3D(100, -100, -149)
            );
            Triangle triangle3 = new Triangle(
                    new Point3D(-100, 0, -149),
                    new Point3D(0, 100, -149),
                    new Point3D(-100, 100, -149)
            );
            Triangle triangle4 = new Triangle(
                    new Point3D(-100, 0, -149),
                    new Point3D(0, -100, -149),
                    new Point3D(-100, -100, -149)
            );
            sphere.setEmission(new Color(Color.blue));
            triangle1.setEmission(new Color(Color.red));
            triangle2.setEmission(new Color(Color.cyan));
            triangle3.setEmission(new Color(Color.yellow));
            triangle4.setEmission(new Color(Color.black));

            scene.addGeometries(sphere, triangle1, triangle2, triangle3, triangle4);

            scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)), 150);
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