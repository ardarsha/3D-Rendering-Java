package renderer;


import elements.Light;
import geometries.FlatGeometry;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static geometries.Intersectable.GeoPoint;

public class Render {
    /*----------------VARIABLES---------------------*/
    private final static int RECURSION_LEVEL = 4;
    private final static double EPSILON = 0.001;

    private Scene _scene;
    private ImageWriter _imageWriter;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Render(Scene scene, ImageWriter imageWriter) {
        _imageWriter = new ImageWriter(imageWriter);
        _scene = new Scene(scene);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Scene getScene() {
        return _scene;
    }

    public ImageWriter getImageWriter() {
        return _imageWriter;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Render render = (Render) o;
        return Objects.equals(_scene, render._scene) &&
                Objects.equals(_imageWriter, render._imageWriter);
    }
    /*----------------END ADMINISTRATION------------*/


    /*----------------OPERATIONS--------------------*/

    private double occludedValue(Light light, GeoPoint geoPoint) throws ZeroVectorException {
        Vector lightDirection = light.getL(geoPoint.point).scale(-1);
        Vector epsVector = geoPoint.geometry.getNormal(geoPoint.point);
        Ray lightRay = new Ray(geoPoint.point.add(epsVector.scale(Render.EPSILON)), lightDirection);

        List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(lightRay);

        float ret = 1.0f; // multiplier of remaining light
        for (GeoPoint geoPoint1 : intersectionPoints) {
            if (geoPoint.geometry instanceof FlatGeometry && geoPoint1.geometry.equals(geoPoint.geometry))
                continue;
            double Kt = geoPoint1.geometry.getMaterial().getKt();
            ret -= (1 - Kt) * ret;
        }

        return ret > 0 ? ret : 0.0f;
    }

    @Deprecated
    private boolean occluded(Light light, GeoPoint geoPoint) throws ZeroVectorException {
        Vector lightDirection = light.getL(geoPoint.point).scale(-1);
        Vector epsVector = geoPoint.geometry.getNormal(geoPoint.point);
        Ray lightRay = new Ray(geoPoint.point.add(epsVector.scale(Render.EPSILON)), lightDirection);

        List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(lightRay);

        for (GeoPoint geoPoint1 : intersectionPoints) {
            if (geoPoint.geometry instanceof FlatGeometry && geoPoint1.geometry.equals(geoPoint.geometry))
                continue;
            if (geoPoint1.geometry.getMaterial().getKt() == 0)
                return true;
        }

        return false;
    }


    private double softShadowOccludedValue(Light light, GeoPoint geoPoint, int nRays) throws ZeroVectorException {
        Vector lightDirection = light.getL(geoPoint.point).scale(-1);
        Vector epsVector = geoPoint.geometry.getNormal(geoPoint.point);

        double ret = 0.0f;
        for (int i = 0; i < nRays; ++i) {
            Vector A;
            do {
                A = lightDirection.crossProduct(lightDirection.scale(2,3,5));
            }while(A.equals(Vector.ZERO));
            Vector B = A.crossProduct(lightDirection);
            A = A.scale((Math.random() *2 -1) * i*EPSILON);
            B = B.scale((Math.random() *2 -1) * i*EPSILON);
            Vector D = A.add(B);
            Ray lightRay = new Ray(geoPoint.point.add(epsVector.scale(Render.EPSILON)), lightDirection.add(D));
            List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(lightRay);

            float rem = 1.0f; // multiplier of remaining light
            for (GeoPoint geoPoint1 : intersectionPoints) {
                if (geoPoint.geometry instanceof FlatGeometry && geoPoint1.geometry.equals(geoPoint.geometry))
                    continue;
                double Kt = geoPoint1.geometry.getMaterial().getKt();
                rem -= (1 - Kt) * rem;
            }
            ret += rem;
        }

        if (nRays != 0)
            ret /= nRays;
        return ret > 0 ? ret : 0.0f;
    }

    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {

        double distance = Double.MAX_VALUE;
        Point3D P0 = _scene.getCamera().getPosition();
        GeoPoint minDistancePoint = null;

        for (GeoPoint geoPoint : intersectionPoints) {
            if (P0.distance(geoPoint.point) < distance) {
                minDistancePoint = new GeoPoint(geoPoint);
                distance = P0.distance(geoPoint.point);
            }
        }
        return minDistancePoint;
    }

    private GeoPoint findClosestIntersection(Ray ray) throws ZeroVectorException {
//        return getClosestPoint(_scene.getGeometries().findIntersections(ray));
        List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(ray);
        double distance = Double.MAX_VALUE;
        Point3D P0 = ray.getOrigin();
        GeoPoint minDistancePoint = null;

        for (GeoPoint geoPoint : intersectionPoints) {
            if (P0.distance(geoPoint.point) < distance) {
                minDistancePoint = new GeoPoint(geoPoint);
                distance = P0.distance(geoPoint.point);
            }
        }
        return minDistancePoint;
    }

    private Color calcDiffuseComp(double kd, Vector normal, Vector l, Color intensity) throws ZeroVectorException {
        return intensity.scale(kd * Math.max(normal.dotProduct(l.scale(-1)), 0));
    }

    private Color calcSpecularComp(double ks, Vector v, Vector normal, Vector l, double shininess, Color lightIntensity) throws ZeroVectorException {
        Vector R = l.add(normal.scale(-2 * l.dotProduct(normal))).normalised();

        double k = ks * Math.pow(Math.max(0, v.dotProduct(R)), shininess);
        return lightIntensity.scale(k);
    }

    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray) throws ZeroVectorException {
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        Vector R = ray.getDirection().add(normal.scale(-2 * ray.getDirection().dotProduct(normal))).normalised();

        return new Ray(geoPoint.point.add(R.scale(Render.EPSILON)), R);
    }

    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) throws ZeroVectorException {
        return new Ray(geoPoint.point.add(ray.getDirection().scale(Render.EPSILON)), ray.getDirection());
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray, int recursionLevel) throws ZeroVectorException {
        if (recursionLevel == RECURSION_LEVEL)
            return new Color(Color.black);
        Color ambientLight = _scene.getAmbient().getIntensity(geoPoint.point);
        Color emissionLight = geoPoint.geometry.getEmission();
        Color specularLight = new Color(Color.BLACK);
        Color diffuseLight = new Color(Color.BLACK);

        Vector v = geoPoint.point.subtract(_scene.getCamera().getPosition()).normalised();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // Specular and Diffuse calculation
        for (Light light : _scene.getLights()) {
            Vector l = light.getL(geoPoint.point);
            double occludedValue = this.occludedValue(light, geoPoint);
            if (occludedValue != 0 && n.dotProduct(l) * n.dotProduct(v) > 0) {
                diffuseLight = diffuseLight.add(
                        calcDiffuseComp(
                                geoPoint.geometry.getMaterial().getKd(),
                                geoPoint.geometry.getNormal(geoPoint.point),
                                light.getL(geoPoint.point),
                                light.getIntensity(geoPoint.point)
                        ).scale(occludedValue)
                );

                specularLight = specularLight.add(
                        calcSpecularComp(
                                geoPoint.geometry.getMaterial().getKs(),
                                _scene.getCamera().getPosition().subtract(geoPoint.point).normalised(),
                                geoPoint.geometry.getNormal(geoPoint.point),
                                light.getL(geoPoint.point),
                                geoPoint.geometry.getMaterial().getNShininess(),
                                light.getIntensity(geoPoint.point)
                        ).scale(occludedValue)
                );

            }
        }

        Ray reflectedRay = constructReflectedRay(geoPoint, ray);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        Color reflectedLight = new Color(Color.black);
        double Kr = geoPoint.geometry.getMaterial().getKr();
        if (null != reflectedPoint && 0 != Kr) {
            reflectedLight = calcColor(
                    reflectedPoint,
                    reflectedRay,
                    recursionLevel + 1
            ).scale(Kr);
        }

        Ray refractedRay = constructRefractedRay(geoPoint, ray);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        Color refractedLight = new Color(Color.black);
        double Kt = geoPoint.geometry.getMaterial().getKt();
        if (null != refractedPoint && 0 != Kt) {
            refractedLight = calcColor(
                    refractedPoint,
                    refractedRay,
                    recursionLevel + 1
            ).scale(geoPoint.geometry.getMaterial().getKt());
        }

        double rLight = ambientLight.getRed() +
                diffuseLight.getRed() +
                specularLight.getRed();

        double gLight = ambientLight.getRed() +
                diffuseLight.getGreen() +
                specularLight.getGreen();

        double bLight = ambientLight.getRed() +
                diffuseLight.getBlue() +
                specularLight.getBlue();

        rLight /= 255;
        gLight /= 255;
        bLight /= 255;

        return emissionLight.scale(rLight, gLight, bLight).add(reflectedLight).add(refractedLight);
        //return ambientLight.add(emissionLight, diffuseLight, specularLight, reflectedLight, refractedLight);
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray) throws ZeroVectorException {
        return calcColor(geoPoint, ray, 0);
    }

    private Color calcSoftShadowColor(GeoPoint geoPoint, Ray ray, int recursionLevel, int nRays) throws ZeroVectorException {
        if (recursionLevel == RECURSION_LEVEL)
            return new Color(Color.black);
        Color ambientLight = _scene.getAmbient().getIntensity(geoPoint.point);
        Color emissionLight = geoPoint.geometry.getEmission();
        Color specularLight = new Color(Color.BLACK);
        Color diffuseLight = new Color(Color.BLACK);

        Vector v = geoPoint.point.subtract(_scene.getCamera().getPosition()).normalised();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // Specular and Diffuse calculation
        for (Light light : _scene.getLights()) {
            Vector l = light.getL(geoPoint.point);
            double occludedValue = this.softShadowOccludedValue(light, geoPoint, nRays);
            if (occludedValue != 0 && n.dotProduct(l) * n.dotProduct(v) > 0) {
                diffuseLight = diffuseLight.add(
                        calcDiffuseComp(
                                geoPoint.geometry.getMaterial().getKd(),
                                geoPoint.geometry.getNormal(geoPoint.point),
                                light.getL(geoPoint.point),
                                light.getIntensity(geoPoint.point)
                        ).scale(occludedValue)
                );

                specularLight = specularLight.add(
                        calcSpecularComp(
                                geoPoint.geometry.getMaterial().getKs(),
                                _scene.getCamera().getPosition().subtract(geoPoint.point).normalised(),
                                geoPoint.geometry.getNormal(geoPoint.point),
                                light.getL(geoPoint.point),
                                geoPoint.geometry.getMaterial().getNShininess(),
                                light.getIntensity(geoPoint.point)
                        ).scale(occludedValue)
                );

            }
        }

        Ray reflectedRay = constructReflectedRay(geoPoint, ray);
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        Color reflectedLight = new Color(Color.black);
        double Kr = geoPoint.geometry.getMaterial().getKr();
        if (null != reflectedPoint && 0 != Kr) {
            reflectedLight = calcSoftShadowColor(
                    reflectedPoint,
                    reflectedRay,
                    recursionLevel + 1,
                    nRays
            ).scale(Kr);
        }

        Ray refractedRay = constructRefractedRay(geoPoint, ray);
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        Color refractedLight = new Color(Color.black);
        double Kt = geoPoint.geometry.getMaterial().getKt();
        if (null != refractedPoint && 0 != Kt) {
            refractedLight = calcSoftShadowColor(
                    refractedPoint,
                    refractedRay,
                    recursionLevel + 1,
                    nRays
            ).scale(geoPoint.geometry.getMaterial().getKt());
        }

        double rLight = ambientLight.getRed() +
                diffuseLight.getRed() +
                specularLight.getRed();

        double gLight = ambientLight.getRed() +
                diffuseLight.getGreen() +
                specularLight.getGreen();

        double bLight = ambientLight.getRed() +
                diffuseLight.getBlue() +
                specularLight.getBlue();

        rLight /= 255;
        gLight /= 255;
        bLight /= 255;

        return emissionLight.scale(rLight, gLight, bLight).add(reflectedLight).add(refractedLight);
        //return ambientLight.add(emissionLight, diffuseLight, specularLight, reflectedLight, refractedLight);
    }

    private Color calcSoftShadowColor(GeoPoint geoPoint, Ray ray, int nRays) throws ZeroVectorException {
        return calcSoftShadowColor(geoPoint, ray, 0, nRays);
    }

    public void printGrid(int interval) {
        for (int i = 0; i < _imageWriter.getHeight(); ++i) {
            for (int j = 0; j < _imageWriter.getWidth(); ++j) {
                if ((i % interval == 0 && i != 0) || (j % interval == 0 && j != 0)) {
                    _imageWriter.writePixel(j, i, new Color(Color.white));
                }
            }
        }
    }

    public void renderImage() throws ZeroVectorException {
        for (int i = 0; i < _imageWriter.getHeight(); i++) {
            for (int j = 0; j < _imageWriter.getWidth(); j++) {
                Ray ray = _scene.getCamera().constructRayThroughPixel(
                        _imageWriter.getNx(),
                        _imageWriter.getNy(),
                        j,
                        i,
                        _scene.getCameraDistance(),
                        _imageWriter.getWidth(),
                        _imageWriter.getHeight()
                );

                GeoPoint intersection = findClosestIntersection(ray);

                if (null == intersection) {
                    _imageWriter.writePixel(j, i, _scene.getBackgroundColor());
                } else {
                    Color pixelColor = calcColor(intersection, ray);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }
        }
    }

    public void renderSuperSampledImage(int nRays) throws ZeroVectorException {
        for (int i = 0; i < _imageWriter.getHeight(); ++i) {
            for (int j = 0; j < _imageWriter.getWidth(); ++j) {
                List<Ray> rays = new ArrayList<>();
                for (int k = 0; k < nRays - 1; ++k) {
                    rays.add(_scene.getCamera().constructRayThroughPixel(
                            _imageWriter.getNx(),
                            _imageWriter.getNy(),
                            j,
                            i,
                            _scene.getCameraDistance(),
                            _imageWriter.getWidth(),
                            _imageWriter.getHeight(),
                            Math.random(),
                            Math.random()
                            )
                    );
                }

                // pixel center
                rays.add(_scene.getCamera().constructRayThroughPixel(
                        _imageWriter.getNx(),
                        _imageWriter.getNy(),
                        j,
                        i,
                        _scene.getCameraDistance(),
                        _imageWriter.getWidth(),
                        _imageWriter.getHeight()
                ));

                List<Color> colors = new ArrayList<>();
                for (Ray ray : rays) {
                    GeoPoint intersection = findClosestIntersection(ray);
                    if (null != intersection) {
                        colors.add(calcColor(intersection, ray));
                    } else {
                        colors.add(_scene.getBackgroundColor());
                    }
                }
                if (colors.isEmpty())
                    _imageWriter.writePixel(j, i, _scene.getBackgroundColor());
                else {
                    Color pixelColor = Color.mean(colors.toArray(Color[]::new));
                    _imageWriter.writePixel(j, i, pixelColor);
                }


            }
        }
    }

    public void renderSoftShadowImage(int nRays) throws ZeroVectorException {
        for (int i = 0; i < _imageWriter.getHeight(); i++) {
            for (int j = 0; j < _imageWriter.getWidth(); j++) {
                Ray ray = _scene.getCamera().constructRayThroughPixel(
                        _imageWriter.getNx(),
                        _imageWriter.getNy(),
                        j,
                        i,
                        _scene.getCameraDistance(),
                        _imageWriter.getWidth(),
                        _imageWriter.getHeight()
                );

                GeoPoint intersection = findClosestIntersection(ray);

                if (null == intersection) {
                    _imageWriter.writePixel(j, i, _scene.getBackgroundColor());
                } else {
                    Color pixelColor = calcSoftShadowColor(intersection, ray, nRays);
                    _imageWriter.writePixel(j, i, pixelColor);
                }
            }
        }
    }

    public void renderSoftShadowSuperSampledImage(int nSuperRays, int nSoftRays) throws ZeroVectorException{
        for (int i = 0; i < _imageWriter.getHeight(); ++i) {
            System.out.println(i);
            for (int j = 0; j < _imageWriter.getWidth(); ++j) {
                List<Ray> rays = new ArrayList<>();
                for (int k = 0; k < nSuperRays - 1; ++k) {
                    rays.add(_scene.getCamera().constructRayThroughPixel(
                            _imageWriter.getNx(),
                            _imageWriter.getNy(),
                            j,
                            i,
                            _scene.getCameraDistance(),
                            _imageWriter.getWidth(),
                            _imageWriter.getHeight(),
                            Math.random(),
                            Math.random()
                            )
                    );
                }

                // pixel center
                rays.add(_scene.getCamera().constructRayThroughPixel(
                        _imageWriter.getNx(),
                        _imageWriter.getNy(),
                        j,
                        i,
                        _scene.getCameraDistance(),
                        _imageWriter.getWidth(),
                        _imageWriter.getHeight()
                ));

                List<Color> colors = new ArrayList<>();
                for (Ray ray : rays) {
                    GeoPoint intersection = findClosestIntersection(ray);
                    if (null != intersection) {
                        colors.add(calcSoftShadowColor(intersection, ray, nSoftRays));
                    } else {
                        colors.add(_scene.getBackgroundColor());
                    }
                }
                if (colors.isEmpty())
                    _imageWriter.writePixel(j, i, _scene.getBackgroundColor());
                else {
                    Color pixelColor = Color.mean(colors.toArray(Color[]::new));
                    _imageWriter.writePixel(j, i, pixelColor);
                }


            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToimage();
    }
    /*----------------END OPERATIONS----------------*/
}
