package project;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Cylinder;
import geometries.Geometries;
import geometries.Plane;
import geometries.Tube;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.io.File;

public class Project {
    private final String dir = "out/testImages/" + this.getClass().getName() + '/';

    public Project() {
        File file = new File(dir);
        file.mkdirs();
    }

    @Test
    public void recursiveTest1() throws ZeroVectorException {
        Scene scene = new Scene();
        scene.setAmbient(new AmbientLight(new Color(0.1735f, 0.1735f, 0.1735f)));
        scene.setBackgroundColor(new Color(0.1735f,0.1735f,0.1735f));


        scene.setCamera(
                new Camera(
                        new Point3D(7.57971, -7.30042, 4.5194),
                        new Vector(-7.57971, 7.57971, -4.5194).normalised(),
                        new Vector(-4.5194, 4.5194, 15.15942).normalised()
                ),
                1000/1
        );

        //floor
        Plane floor = new Plane(new Point3D(0, 0, 0), new Vector(0, 0, 1));
        floor.setMaterial(new Material(1, 0, 20, 0.5, 0.1));
        floor.setEmission(new Color(Color.white).scale(0.35));
        scene.addGeometries(floor);

        //table
        Geometries table = new Geometries();

        // legs
        for (int i = 0; i < 4; ++i) {
            double x = i == 0 || i == 1 ? 1.1 : -1.1;
            double y = i == 0 || i == 2 ? 1.2 : -1.2;
            Cylinder cylinder = new Cylinder(
                    new Tube(
                            new Line(
                                    new Point3D(x, y, 0),
                                    new Vector(0, 0, 1)
                            ),
                            0.2
                    ),
                    2
            );
            cylinder.setMaterial(new Material(0.8, 0.5, 20, 0, 0));
            cylinder.setEmission(new Color(0.799f, 0.122f, 0.117f).scale(1.0));
            table.add(cylinder);
        }

        Cylinder surface =
                new Cylinder(
                        new Tube(
                                new Line(
                                        new Point3D(0, 0, 2),
                                        new Vector(0, 0, 1)),
                                3
                        ),
                        0.1
                );
        surface.setMaterial(new Material(0.8, 0.5, 20, 0.1, 0.75));
        surface.setEmission(new Color(0.052f, 0.8f, 0.755f).scale(0.7));
        table.add(surface);

        scene.addGeometries(table);

        // checkers
        Geometries checkers = new Geometries();

        // TODO: black piece on light square
        // TODO: white piece on light square
        // TODO: black piece on dark square
        // TODO: white piece on dark square

        // board
        Geometries board = new Geometries();
        //TODO

        //lighting
        scene.addLights(
                new PointLight(
                        new Color(Color.white).scale(0.8),
                        new Point3D(4.14935, 0.672964, 5.49982),
                        0,
                        0.0 / 30,
                        31.5 / 900
                ),
                new PointLight(
                        new Color(Color.white).scale(0.7),
                        new Point3D(5.71677, -6.63823, 3.46249),
                        0,
                        0.0 / 30,
                        31.5 / 900
                ),
                new SpotLight(
                        new Color(Color.white).scale(0.4),
                        new Point3D(7.57971, -7.30042, 4.5194),
                        0,
                        0.0 / 30,
                        31.5 / 900,
                        new Vector(-7.0, 7.0, -4.0).normalised()
                )/*,
                new SpotLight(
                        new Color(Color.white).scale(0.7),
                        new Point3D(-7.57971, 7.30042, 4.5194),
                        0,
                        0.0 / 30,
                        31.5 / 900,
                        new Vector(7.0, -7.0, -4.0).normalised()
                )*/
        );
        // location1 : (4.14935, 0.672964, 5.49982)
        // location2 : (5.71677, -6.63823, 3.46249)


        ImageWriter imageWriter = new ImageWriter(dir + "Project1", 960/1, 540/1, 960/1, 540/1);

        Render render = new Render(scene, imageWriter);

        //render.renderImage();
        //render.renderSuperSampledImage(10);
        render.renderSoftShadowImage(4);
        //render.renderSoftShadowSuperSampledImage(10, 4);
        //render.renderSoftShadowSuperSampledImage(30, 20); // overkill almost 6h run time
        //render.printGrid(50);
        render.writeToImage();
    }
}
