package fractalzoomer.core.interpolation;

public class QuadraticInterpolation extends InterpolationMethod {
    private double power = 2;

    public QuadraticInterpolation() {
        super();
    }

    @Override
    protected int interpolateColorsInternal(int r1, int g1, int b1, int r2, int g2, int b2, double coef) {

        double tempr1 = Math.pow(r1, power);
        double tempg1 = Math.pow(g1, power);
        double tempb1 = Math.pow(b1, power);

        int red = (int)(Math.pow(tempr1 + (Math.pow(r2, power) - tempr1) * coef, 1 / power) + 0.5);
        int green = (int)(Math.pow(tempg1 + (Math.pow(g2, power) - tempg1) * coef, 1 / power) + 0.5);
        int blue = (int)(Math.pow(tempb1 + (Math.pow(b2, power) - tempb1) * coef, 1 / power) + 0.5);

        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }

    @Override
    public double interpolate(double a, double b, double coef) {

        double tempa = Math.pow(a, power);
        return Math.pow(tempa + (Math.pow(b, power) - tempa) * coef, 1 / power);

    }

    @Override
    public int interpolate(int a, int b, double coef) {

        double tempa = Math.pow(a, power);
        return (int)(Math.pow(tempa + (Math.pow(b, power) - tempa) * coef, 1 / power) + 0.5);

    }

    @Override
    public double getCoef(double coef) {

        return coef;

    }
}
