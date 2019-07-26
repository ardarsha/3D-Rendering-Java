package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.ZeroVectorException;

import java.util.Objects;

public class Camera {

    /*----------------VARIABLES---------------------*/
    private Point3D _position;
    private Vector _up;
    private Vector _to;
    private Vector _right;
    /*----------------END VARIABLES-----------------*/


    /*----------------CONSTRUCTORS------------------*/
    public Camera(Point3D position, Vector to, Vector up) throws ZeroVectorException {
        if (to.dotProduct(up) != 0)
            throw new IllegalArgumentException("up and to must be perpendicular");
        _position = new Point3D(position);
        _to = to.normalised();
        _up = up.normalised();
        _right = to.crossProduct(up).normalised();
    }

    public Camera(Camera other) {
        this._position = new Point3D(other._position);
        this._right = new Vector(other._right);
        this._to = new Vector(other._to);
        this._up = new Vector(other._up);
    }
    /*----------------END CONSTRUCTORS--------------*/


    /*----------------GETTERS/SETTERS---------------*/
    public Point3D getPosition() {
        return _position;
    }

    public Vector getRight() {
        return _right;
    }

    public Vector getTo() {
        return _to;
    }

    public Vector getUp() {
        return _up;
    }
    /*----------------END GETTERS/SETTERS-----------*/


    /*----------------ADMINISTRATION----------------*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camera camera = (Camera) o;
        return Objects.equals(_position, camera._position) &&
                Objects.equals(_up, camera._up) &&
                Objects.equals(_to, camera._to) &&
                Objects.equals(_right, camera._right);
    }
    /*----------------END ADMINISTRATION------------*/

    /*----------------OPERATIONS--------------------*/

    public Ray constructRayThroughPixel(int Nx, int Ny, double x, double y, double screenDistance, double screenWidth, double screenHeight, double pixelX, double pixelY) throws ZeroVectorException {
        if (pixelX == 0)
            pixelX = 0.000001;
        if (pixelX == 1)
            pixelX = 0.999999;
        if (pixelY == 0)
            pixelY = 0.000001;
        if (pixelY == 1)
            pixelY = 0.999999;

        Point3D screenCenter = this._position.add(this._to.scale(screenDistance));
        double pixelWidth = screenWidth / Nx,
                pixelHeight = screenHeight / Ny;
        Point3D pixelCenter = screenCenter.add(_right.scale((x - Nx / 2.0) * pixelWidth + pixelWidth * pixelX).subtract(_up.scale((y - Ny / 2.0) * pixelHeight + pixelHeight * pixelY)));

        return new Ray(_position, pixelCenter.subtract(this._position).normalised());
    }


        /**
         * construct the rays the create an image from the point of view of the camera, relative to the screen specified in the parameters
         *
         * @param Nx             Number of pixels in the x direction
         * @param Ny             Number of pixels in the y direction
         * @param x              pixel index in the x direction
         * @param y              pixel index in the y direction
         * @param screenDistance the distance between the screen and the camera
         * @param screenWidth    the width of the screen
         * @param screenHeight   the height of the screen
         * @return a ray from the camera to the center of the specified pixel, normalised
         */
    public Ray constructRayThroughPixel(int Nx, int Ny, double x, double y, double screenDistance, double screenWidth, double screenHeight) throws ZeroVectorException {
        return constructRayThroughPixel(Nx, Ny, x, y, screenDistance, screenWidth, screenHeight, 0.5, 0.5);
    }
    /*----------------END OPERATIONS----------------*/
}
