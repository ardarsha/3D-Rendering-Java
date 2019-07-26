package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class LightsTest {
    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    LightsTest(){
        File file = new File(dir);
        file.mkdirs();
    }

    @Test
    void renderImage(){
        Render render;
        try {
             render = new Render(
                    new Scene(
                            "name"
                    ),
                    new ImageWriter(dir + "error", 500,500,500,500)
            );
        }
        catch (Exception ex){
            fail(ex.getMessage());
            return;
        }
        try {
            Scene scene = new Scene("renderImage");
            scene.setAmbient(new AmbientLight(new Color(Color.WHITE), 0.1));
            scene.setBackgroundColor(new Color(Color.BLACK));

            scene.addLights(new PointLight(new Color(223,57,156).scale(0.12), new Point3D(-50,0,-60), 1,1,1));
            scene.addLights(new DirectionalLight(new Color(1,2,3), new Vector(-1,-0.5,-1.5)));
            //scene.addLights(new SpotLight(new Color(100,100,0).scale(0.01), new Point3D(1000,100,1000), 1,1,3,new Vector(100,-10,-30).scale(-1).add(new Vector(0,0,-150))));

            Triangle triangle1 = new Triangle(new Point3D(-100,0,-150), new Point3D(0,100,-150), new Point3D(100,0,-150));
            Triangle triangle2 = new Triangle(new Point3D(-100,0,-150), new Point3D(0,-100,-150), new Point3D(100,0,-150));
            triangle1.setEmission(new Color(Color.black));
            triangle2.setEmission(new Color(Color.black));
            triangle1.setMaterial(new Material(2,10,2));
            triangle2.setMaterial(new Material(2,10,2));
            scene.addGeometries(triangle1, triangle2);

            Sphere sphere = new Sphere(30, new Point3D(0,0,-120));
            sphere.setMaterial(new Material(9,1,0));
            sphere.setEmission(new Color(Color.blue).scale(0.3));
            scene.addGeometries(sphere);

//            Cylinder cylinder = new Cylinder(new Line(new Point3D(-70,-20,-120), new Vector(1,2,3)),30, 50);
//            cylinder.setMaterial(new Material(5,5,0));
//            cylinder.setEmission(new Color(Color.blue).scale(0.5));
//            scene.addGeometries(cylinder);

            sphere = new Sphere(30, new Point3D(0,60,-120));
            sphere.setMaterial(new Material(9,9,20));
            sphere.setEmission(new Color(Color.blue).scale(0.3));
            scene.addGeometries(sphere);

            scene.setCamera(new Camera(new Point3D(-200, 0, -100), new Vector(1, 0, 0), new Vector(0, 0, 1)), 200);
            render = new Render(
                    scene,
                    new ImageWriter(dir + "renderImage", 500, 500, 500, 500)
            );

            render.renderImage();
            //render.printGrid(170);
            render.writeToImage();
        } catch (Exception ex) {
            render.writeToImage();
            fail(ex.getClass().getName() +  ' ' + ex.getMessage());
        }
    }
}
