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

public class UpdatedShlav10Tester{
	private final String dir = "out/testImages/" + this.getClass().getName() + '/';

	public UpdatedShlav10Tester(){
		File file = new File(dir);
		file.mkdirs();
	}

	@Test
	public void recursiveTest1() throws ZeroVectorException {

		Scene scene = new Scene();
		scene.setAmbient(new AmbientLight(new Color(Color.black)));

		scene.setCamera(
				new Camera(
						new Point3D(0,0,0),
						new Vector(0,0,-1),
						new Vector(0,1,0)
				),
				200
		);
		
		Sphere sphere = new Sphere( 500, new Point3D(0.0, 0.0, -1000));
		sphere.setEmission(new Color(0,0, 100));

		sphere.setMaterial(new Material(1,1,20,0,1));
		scene.addGeometries(sphere);
		
		Sphere sphere2 = new Sphere( 250, new Point3D(0.0, 0.0, -1000));
		sphere2.setEmission(new Color (100, 20, 20));
		sphere2.setMaterial(new Material(1,1,20,0,0));
		scene.addGeometries(sphere2);

		scene.addLights(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
							   0.1, 0.00001, 0.000005,  new Vector(2, 2, -3))); // NOW
				
		ImageWriter imageWriter = new ImageWriter(dir + "Recursive Test1", 500, 500, 500, 500);
		
		Render render = new Render( scene, imageWriter);
		
		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
		
	}
	
	
	
	@Test
	public void recursiveTest2() throws ZeroVectorException {

		Scene scene = new Scene();
		scene.setAmbient(new AmbientLight(new Color(Color.black)));

		scene.setCamera(
				new Camera(
						new Point3D(0,0,0),
						new Vector(0,0,-1),
						new Vector(0,1,0)
				),
				200
		);

		Sphere sphere = new Sphere( 500, new Point3D(0.0, 0.0, -1000));
		sphere.setEmission(new Color(Color.RED));
		sphere.setMaterial(new Material(1,1,20,0,0.5));
		scene.addGeometries(sphere);

		Sphere sphere2 = new Sphere( 250, new Point3D(0.0, 0.0, -1000));
		sphere2.setEmission(new Color(Color.BLUE));
		sphere2.setMaterial(new Material(1,1,20,0,0));
		scene.addGeometries(sphere2);

		scene.addLights(new SpotLight(new Color(255, 100, 100), new Point3D(-200, -200, -150),
					    0.1, 0.00001, 0.000005, new Vector(2, 2, -3)));

		ImageWriter imageWriter = new ImageWriter(dir + "Recursive Test2", 500, 500, 500, 500);

		Render render = new Render(scene, imageWriter);

		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
	}

	@Test
	public void recursiveTest3() throws ZeroVectorException {

		Scene scene = new Scene();
		scene.setAmbient(new AmbientLight(new Color(Color.black)));

		scene.setCamera(
				new Camera(
						new Point3D(0,0,0),
						new Vector(0,0,-1),
						new Vector(0,1,0)
				),
				200
		);
		Sphere sphere = new Sphere( 300, new Point3D(-550, -500, -1000));
		sphere.setEmission(new Color(0, 0, 100));
		sphere.setMaterial(new Material(1,1,20,0,0.5));
		scene.addGeometries(sphere);

		Sphere sphere2 = new Sphere( 150, new Point3D(-550, -500, -1000));
		sphere2.setEmission(new Color(100, 20, 20));
		sphere2.setMaterial(new Material(1,1,20,0,0));
		scene.addGeometries(sphere2);

		Triangle triangle = new Triangle( new Point3D(  1500, -1500, -1500),
				 new Point3D( -1500,  1500, -1500),
				 new Point3D(  200,  200, -375));
		triangle.setEmission(new Color(20, 20, 20));

		Triangle triangle2 = new Triangle( new Point3D(  1500, -1500, -1500),
				  new Point3D( -1500,  1500, -1500),
				  new Point3D( -1500, -1500, -1500));

		triangle2.setEmission(new Color(20, 20, 20));

		triangle.setMaterial(new Material(0,0,0,1,0));

		triangle2.setMaterial(new Material(0,0,0,1,0));

		scene.addGeometries(triangle, triangle2);

		scene.addLights(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),
				   0.1, 0.00001, 0.000005,  new Vector(-2, -2, -3)));

		sphere = new Sphere( 30, new Point3D(200, 200, -150));
		sphere.setEmission(new Color(100, 0, 0));
		sphere.setMaterial(new Material(1,1,0,1,1));
		scene.addGeometries(sphere);

		ImageWriter imageWriter = new ImageWriter(dir + "Recursive Test3", 500, 500, 500, 500);

		Render render = new Render( scene, imageWriter);
		render.renderImage();
		render.printGrid(50);
		render.writeToImage();
	}

	@Test
	public void recursiveTestSuperSample() throws ZeroVectorException {

		Scene scene = new Scene();
		scene.setAmbient(new AmbientLight(new Color(Color.black)));

		scene.setCamera(
				new Camera(
						new Point3D(0,0,0),
						new Vector(0,0,-1),
						new Vector(0,1,0)
				),
				200
		);
		Sphere sphere = new Sphere( 300, new Point3D(-550, -500, -1000));
		sphere.setEmission(new Color(0, 0, 100));
		sphere.setMaterial(new Material(1,1,20,0,0.5));
		scene.addGeometries(sphere);

		Sphere sphere2 = new Sphere( 150, new Point3D(-550, -500, -1000));
		sphere2.setEmission(new Color(100, 20, 20));
		sphere2.setMaterial(new Material(1,1,20,0,0));
		scene.addGeometries(sphere2);

		Triangle triangle = new Triangle( new Point3D(  1500, -1500, -1500),
				new Point3D( -1500,  1500, -1500),
				new Point3D(  200,  200, -375));
		triangle.setEmission(new Color(20, 20, 20));

		Triangle triangle2 = new Triangle( new Point3D(  1500, -1500, -1500),
				new Point3D( -1500,  1500, -1500),
				new Point3D( -1500, -1500, -1500));

		triangle2.setEmission(new Color(20, 20, 20));

		triangle.setMaterial(new Material(0,0,0,1,1));

		triangle2.setMaterial(new Material(0,0,0,1,0));

		scene.addGeometries(triangle, triangle2);


		scene.addLights(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),
				0.1, 0.00001, 0.000005,  new Vector(-2, -2, -3)));

		ImageWriter imageWriter = new ImageWriter(dir + "Super Sample", 500, 500, 500, 500);

		Render render = new Render( scene, imageWriter);
		//render.renderImage();
		render.renderSuperSampledImage(30);
		//render.renderSuperSampledThreadedImage(30);
		//render.printGrid(50);
		render.writeToImage();
	}

	@Test
	public void recursiveTestSuperSampleMultiThreaded() throws ZeroVectorException {

		//System.out.println(Thread.currentThread().getId());
		Scene scene = new Scene();
		scene.setAmbient(new AmbientLight(new Color(Color.black)));

		scene.setCamera(
				new Camera(
						new Point3D(0,0,0),
						new Vector(0,0,-1),
						new Vector(0,1,0)
				),
				200
		);
		Sphere sphere = new Sphere( 300, new Point3D(-550, -500, -1000));
		sphere.setEmission(new Color(0, 0, 100));
		sphere.setMaterial(new Material(1,1,20,0,0.5));
		scene.addGeometries(sphere);

		Sphere sphere2 = new Sphere( 150, new Point3D(-550, -500, -1000));
		sphere2.setEmission(new Color(100, 20, 20));
		sphere2.setMaterial(new Material(1,1,20,0,0));
		scene.addGeometries(sphere2);

		Triangle triangle = new Triangle( new Point3D(  1500, -1500, -1500),
				new Point3D( -1500,  1500, -1500),
				new Point3D(  200,  200, -375));
		triangle.setEmission(new Color(20, 20, 20));

		Triangle triangle2 = new Triangle( new Point3D(  1500, -1500, -1500),
				new Point3D( -1500,  1500, -1500),
				new Point3D( -1500, -1500, -1500));

		triangle2.setEmission(new Color(20, 20, 20));

		triangle.setMaterial(new Material(0,0,0,1,1));

		triangle2.setMaterial(new Material(0,0,0,1,0));

		scene.addGeometries(triangle, triangle2);


		scene.addLights(new SpotLight(new Color(255, 100, 100),  new Point3D(200, 200, -150),
				0.1, 0.00001, 0.000005,  new Vector(-2, -2, -3)));

		ImageWriter imageWriter = new ImageWriter(dir + "Super Multithreaded", 500, 500, 500, 500);

		Render render = new Render( scene, imageWriter);
		//render.renderImage();
		//render.renderSuperSampledImage(30);
		//render.renderSuperSampledThreadedImage(30);
		//render.printGrid(50);
		render.writeToImage();
	}
}
