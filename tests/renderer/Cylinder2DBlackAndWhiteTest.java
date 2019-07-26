package renderer;

import elements.AmbientLight;
import elements.Camera;
import geometries.Cylinder;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Line;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class Cylinder2DBlackAndWhiteTest {
    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    Cylinder2DBlackAndWhiteTest(){
        File file = new File(dir);
        file.mkdirs();
    }

    @Test
    void RenderImage(){
        try {
            Scene scene = new Scene("renderImage");
            scene.setAmbient(new AmbientLight(new Color(Color.WHITE), 1));
            scene.setBackgroundColor(new Color(Color.BLACK));
            Cylinder cylinder = new Cylinder(new Line(new Point3D(25,0,-149), new Vector(-3,-1,0)), 50,50);
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

            scene.addGeometries(cylinder, triangle1, triangle2, triangle3, triangle4);

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
