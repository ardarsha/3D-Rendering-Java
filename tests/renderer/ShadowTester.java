package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;
import scene.Scene;

import java.io.File;

public class ShadowTester {


	private final String dir = "out/testImages/" + this.getClass().getName() + '/';

	public ShadowTester(){
		File file = new File(dir);
		file.mkdirs();
	}

	@Test
	public void testShadow() throws ZeroVectorException {
		
		Scene scene = new Scene();
		scene.setCamera(
				new Camera(
						new Point3D(0,0,0),
						new Vector(0,0,-1),
						new Vector(0,1,0)),
				200
		);
		scene.setAmbient(new AmbientLight(new Color(Color.black)));
		
		Sphere sphere = new Sphere (500, new Point3D(0,0,-1000));
		sphere.setEmission(new Color(0,0,100));

		sphere.setMaterial(new Material(1,1,20));
		scene.addGeometries(sphere);
		
		Triangle triangle = new Triangle(
										 new Point3D(-125, -225, -260),
										 new Point3D(-225, -125, -260),
										 new Point3D(-225, -225, -270)
									);
		triangle.setEmission(new Color(0,0,100));

		triangle.setMaterial(new Material(3,3,4));
		scene.addGeometries(triangle);
	
		scene.addLights(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
					  0.1, 0.00001, 0.000005,   new Vector(2, 2, -3)));
	

		ImageWriter imageWriter = new ImageWriter(dir+"Shadow Test", 500, 500, 500, 500);
		
		Render render = new Render(scene, imageWriter);
		
		render.renderImage();
		//render.printGrid(50);
		render.writeToImage();
	}
}
