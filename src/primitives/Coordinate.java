package primitives;

import static primitives.Util.*;

/**
 * Coordinate for rendering graphics. uses Util for accuracy
 * @author Ezra Tzur Elishai
 */
public final class Coordinate {
    //private static final double EPSILON = 0.0000001;
    protected double _coord;

    public static Coordinate ZERO = new Coordinate(0.0);

    /*--------- Constructors ----------*/
    /**
     * Constructor for the coordinate. The value is aligned to an accuracy of a certain degree.
     * @param coord The value of the coordinate
     */
    public Coordinate(double coord) {
        // if it too close to zero make it zero
        _coord = alignZero(coord);
    }

    /**
     * Copy constructor
     * @param other The coordinate to create a deep copy of.
     */
    public Coordinate(Coordinate other) {
        _coord = other._coord;
    }

    /*------------- Getters/Setters ------*/

    /**
     *
     * @return  The value of the coordinate as a double
     */
    public double get() {
        return _coord;
    }

    /*-------------- Admin ----------------*/

    /**
     * Checks if this coordinate is equal to an object.
     * @param obj   The object to check if equal to.
     * @return  true: The coordinate is equal to obj. false: The coordinate is not equal to obj.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        return usubtract(_coord, ((Coordinate)obj)._coord) == 0.0;
    }

    /**
     *
     * @return  A string representation of the coordinate
     */
    @Override
    public String toString() {
        return "" + _coord;
    }

    /*------------- Operations --------------*/

    /**
     * Subtracts a coordinate from this.
     * @param other The coordinate to subtract.
     * @return  A coordinate with the value of this coordinate minus the value of the other coordinate.
     */
    public Coordinate subtract(Coordinate other) {
        return new Coordinate(usubtract(_coord, other._coord));
    }

    /**
     * Adds a coordinate to this.
     * @param other The coordinate to add.
     * @return  A coordinate with the value of this coordinate plus the value of the other coordinate.
     */
    public Coordinate add(Coordinate other) {
        return new Coordinate(uadd(_coord, other._coord));
    }

    /**
     * Scales this coordinate by a real number.
     * @param num The number to scale by.
     * @return  A coordinate with the value of this coordinate scaled by the number supplied in the argument.
     */
    public Coordinate scale(double num) {
        return new Coordinate(uscale(_coord, num));
    }

    /**
     * Multiplies this by another coordinate.
     * @param other The coordinate to multiply by.
     * @return  A coordinate with the value of this coordinate multiplied by the value of the other coordinate.
     */
    public Coordinate multiply(Coordinate other) {
        return new Coordinate(uscale(_coord, other._coord));
    }

}
