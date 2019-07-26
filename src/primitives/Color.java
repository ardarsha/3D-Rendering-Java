package primitives;

import java.awt.color.ColorSpace;

/**
 * @author Ariel Darshan
 */
public class Color extends java.awt.Color {

    // All CTORs are original awt ones except for the copy CTORs
    public Color(int RGB) {
        super(RGB > 0xffffffff ? 0xffffffff : RGB);
    }

    public Color(Color other) {
        super(other.getRGB());
    }

    public Color(java.awt.Color other) {
        this(other.getRGB());
    }

    public Color(int r, int g, int b) {
        super(r > 255 ? 255 : r, g > 255 ? 255 : g, b > 255 ? 255 : b);
    }

    public Color(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public Color(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    public Color(float r, float g, float b) {
        super(r > 1 ? 1 : r, g > 1 ? 1 : g, b > 1 ? 1 : b);
    }

    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Color(ColorSpace cspace, float components[], float alpha) {
        super(cspace, components, alpha);
    }

    public static Color mean(Color... colors) {
        if (colors.length == 0)
            return new Color(black);
        int r = 0, g = 0, b = 0;
        for (Color color : colors) {
            r += color.getRed();
            g += color.getGreen();
            b += color.getBlue();
        }
        return new Color(
                r / colors.length,
                g / colors.length,
                b / colors.length
        );
    }

    public Color add(Color... colors) {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        for (Color color : colors) {
            if (null == color){
                continue;
            }
            r += color.getRed() > 0 ? color.getRed() : 0;
            g += color.getGreen() > 0 ? color.getGreen() : 0;
            b += color.getBlue() > 0 ? color.getBlue() : 0;
        }
        return new Color(r > 255 ? 255 : r, g > 255 ? 255 : g, b > 255 ? 255 : b);
    }

    public Color scale(double scalar) {
        return this.scale(scalar, scalar, scalar);
    }

    public Color scale(double rScalar, double gScalar, double bScalar) {
        int r = (int) (getRed() * rScalar);
        int g = (int) (getGreen() * gScalar);
        int b = (int) (getBlue() * bScalar);
        return new Color(r > 255 ? 255 : r, g > 255 ? 255 : g, b > 255 ? 255 : b);
    }
}
