package fractalzoomer.utils;

public class ColorCorrection {
    static double gamma = 1;
    static double inv_gamma = 1 / gamma;
    static double intensity_exponent = 1;
    static double interpolation_exponent = 1;

    public static void set(double g, double intensity_e, double interpolation_e) {
        gamma = g;
        inv_gamma = 1 / gamma;
        intensity_exponent = intensity_e;
        interpolation_exponent = interpolation_e;
    }

    public static int gammaToLinear(int v) {
        if(gamma == 1) {
            return v;
        }
        return (int)(Math.pow(v / 255.0, gamma) * 255 + 0.5);
    }

    public static int linearToGamma(int color) {
        if(gamma == 1) {
            return color;
        }
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        return 0xff000000 | ((int)(Math.pow(r / 255.0, inv_gamma) * 255 + 0.5) << 16) | ((int)(Math.pow(g / 255.0, inv_gamma) * 255 + 0.5) << 8) | ((int)(Math.pow(b / 255.0, inv_gamma) * 255 + 0.5));
    }

    public static int linearToGamma(int r, int g, int b) {
        if(gamma == 1) {
            return 0xff000000 | (r << 16) | (g << 8) | b;
        }
        return 0xff000000 | ((int)(Math.pow(r / 255.0, inv_gamma) * 255 + 0.5) << 16) | ((int)(Math.pow(g / 255.0, inv_gamma) * 255 + 0.5) << 8) | ((int)(Math.pow(b / 255.0, inv_gamma) * 255 + 0.5));
    }

    public static int linearToGamma(double r, double g, double b) {
        if(gamma == 1) {
            return 0xff000000 | (((int)(r + 0.5)) << 16) | (((int)(g + 0.5)) << 8) | ((int)(b + 0.5));
        }
        return 0xff000000 | ((int)(Math.pow(r / 255.0, inv_gamma) * 255 + 0.5) << 16) | ((int)(Math.pow(g / 255.0, inv_gamma) * 255 + 0.5) << 8) | ((int)(Math.pow(b / 255.0, inv_gamma) * 255 + 0.5));
    }

    public static double modifyIntensityCurve(double v) {
        if(intensity_exponent == 1) {
            return v;
        }
        return Math.pow(v, intensity_exponent);
    }

    public static double modifyInterpolationCurve(double v) {
        if(interpolation_exponent == 1) {
            return v;
        }
        return Math.pow(v, interpolation_exponent);
    }
}
