package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;
import scene.Scene;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class Shlav8Tester {
    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    public Shlav8Tester() {
        File file = new File(dir);
        file.mkdirs();
    }


    @Test
    public void pointLightTest1() throws ZeroVectorException {

        Scene scene = new Scene();
        scene.setCamera(
                new Camera(
                        new Point3D(0, 0, 0),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0)
                ),
                100
        );
        scene.setAmbient(new AmbientLight(new Color(Color.black)));
        Sphere sphere = new Sphere(800, new Point3D(0, 0, -1000));
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setMaterial(new Material(5, 5, 20));
        scene.addGeometries(sphere);
        scene.addLights(new PointLight(new Color(255, 100, 100), new Point3D(-200, -200, -100),//-200, -200, -100),
                0, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter(dir + "Point Test1", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        //render.printGrid(50);
        render.writeToImage();

    }


    @Test
    public void pointLightTest2() throws ZeroVectorException {
        Scene scene = new Scene();
        scene.setCamera(
                new Camera(
                        new Point3D(0, 0, 0),
                        new Vector(0, 0, -1),
                        new Vector(0, 1, 0)
                ),
                100
        );

        scene.setAmbient(new AmbientLight(new Color(Color.black), 1));

        Sphere sphere = new Sphere(800, new Point3D(0, 0, -1000));
        sphere.setEmission(new Color(0, 0, 100));

        sphere.setMaterial(new Material(10, 10, 20));


        Triangle triangle = new Triangle(new Point3D(3500, 3500, -2000),
                new Point3D(-3500, -3500, -1000), new Point3D(3500, -3500, -2000));
        Triangle triangle2 = new Triangle(new Point3D(3500, 3500, -2000),
                new Point3D(-3500, 3500, -1000), new Point3D(-3500, -3500, -1000));

        triangle.setEmission(new Color(Color.BLACK));
        triangle2.setEmission(new Color(Color.black));

        triangle.setMaterial(new Material(1,1,20));
        triangle2.setMaterial(new Material(1,1,20));

        scene.addGeometries(triangle, triangle2);//, sphere);

        scene.addLights(
                new PointLight(
                        new Color(255, 100, 100),
                        new Point3D(-100, 200, -100),
                        0,
                        0.000001,
                        0.0000005
                )
        );


        ImageWriter imageWriter = new ImageWriter(dir + "Point Test2", 500, 500, 500, 500);
        Render render = new Render(scene, imageWriter);

        render.renderImage();
        //render.printGrid(50);
        render.writeToImage();
    }


    @Test
    public void spotLightTest1() throws ZeroVectorException {

        Scene scene = new Scene();
        scene.setAmbient(new AmbientLight(new Color(Color.black)));

        scene.setCamera(
                new Camera(
                        new Point3D(0,0,0),
                        new Vector(0,0,-1),
                        new Vector(0,1,0)
                ),
                100
        );
        Sphere sphere = new Sphere( 800, new Point3D(0, 0, -1000));
        sphere.setEmission(new Color(0, 0, 100));

        sphere.setMaterial(new Material(1,1,20));
        scene.addGeometries(sphere);

        scene.addLights(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -100),
                0, 0.00001, 0.000005, new Vector(2, 2, -3)));

        ImageWriter imageWriter = new ImageWriter(dir + "Spot Test1", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        //render.printGrid(50);
        render.writeToImage();
    }

    @Test
    public void spotLightTest2() throws ZeroVectorException {

        Scene scene = new Scene();
        scene.setAmbient(new AmbientLight(new Color(Color.black)));

        scene.setCamera(
                new Camera(
                        new Point3D(0,0,0),
                        new Vector(0,0,-1),
                        new Vector(0,1,0)
                ),
                100
        );

        Sphere sphere = new Sphere( 500, new Point3D(0, 0, -1000));
        sphere.setEmission(new Color(0, 0, 100));
        sphere.setMaterial(new Material(1,1,20));

        scene.addGeometries(sphere);

        Triangle triangle = new Triangle(
                new Point3D(-125, -225, -260),
                new Point3D(-225, -125, -260),
                new Point3D(-225, -225, -270)
        );

        triangle.setEmission(new Color(0, 0, 100));

        triangle.setMaterial(new Material(5,1,4));
        scene.addGeometries(triangle);

        scene.addLights(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
                0.1, 0.00001, 0.000005, new Vector(2, 2, -3)));

        ImageWriter imageWriter = new ImageWriter(dir + "Spot Test2", 500, 500, 500, 500);

        Render render = new Render(scene, imageWriter);

        render.renderImage();
        //render.printGrid(50);
        render.writeToImage();
    }

    @Test
    public void spotLightTest3() throws ZeroVectorException {
        Render render;
        try {
            render = new Render(new Scene(), new ImageWriter("error", 1,1,1,1));
        }
        catch (Exception ex)
        {
            throw ex;
        }
        try {

            Scene scene = new Scene();
            scene.setAmbient(new AmbientLight(new Color(Color.black), 1));

            scene.setCamera(
                    new Camera(
                            new Point3D(0, 0, 0),
                            new Vector(0, 0, -1),
                            new Vector(0, 1, 0)
                    ),
                    100
            );

            Triangle triangle = new Triangle(
                    new Point3D(3500, 3500, -2000),
                    new Point3D(-3500, -3500, -1000),
                    new Point3D(3500, -3500, -2000)
            );


            Triangle triangle2 = new Triangle(
                    new Point3D(3500, 3500, -2000),
                    new Point3D(-3500, 3500, -1000),
                    new Point3D(-3500, -3500, -1000)
            );

            triangle.setEmission(new Color(Color.black));
            triangle2.setEmission(new Color(Color.black));

            triangle.setMaterial(new Material(1, 1, 20));
            triangle2.setMaterial(new Material(1, 1, 20));

            scene.addGeometries(triangle, triangle2);

            scene.addLights(new SpotLight(new Color(255, 100, 100), new Point3D(200, 200, -100),
                    0, 0.000001, 0.0000005, new Vector(-2, -2, -3)));


            ImageWriter imageWriter = new ImageWriter(dir + "Spot Test3", 500, 500, 500, 500);

            render = new Render(scene, imageWriter);

            render.renderImage();
            //	render.printGrid(50);
            render.writeToImage();
        }
        catch (Exception ex){
            render.writeToImage();
            fail(ex.getMessage());
        }

    }


}
