package fractalzoomer.core.interpolation;

public class SmoothTransitionFunctionInterpolation extends InterpolationMethod {

    public SmoothTransitionFunctionInterpolation() {
        super();
    }

    @Override
    public int interpolate(int r1, int g1, int b1, int r2, int g2, int b2, double coef) {

        coef = getCoefficient(coef);

        int red = (int)(r1 + (r2 - r1) * coef + 0.5);
        int green = (int)(g1 + (g2 - g1) * coef + 0.5);
        int blue = (int)(b1 + (b2 - b1) * coef + 0.5);

        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }

    @Override
    public double interpolate(double a, double b, double coef) {

        coef = getCoefficient(coef);
        return a + (b - a) * coef;

    }

    @Override
    public int interpolate(int a, int b, double coef) {

        coef = getCoefficient(coef);
        return (int)(a + (b - a) * coef + 0.5);

    }

    @Override
    public double getCoef(double coef) {

        return getCoefficient(coef);

    }

    public static double getCoefficient(double coef) {
        return phi(coef);
    }

    private static double psi(double x) {

        if(x == 0) return 0;

        return Math.exp(-1/x);

    }

    private static double phi(double x) {
        double temp = psi(x);
        return temp / (temp + psi(1 - x));
    }
}
