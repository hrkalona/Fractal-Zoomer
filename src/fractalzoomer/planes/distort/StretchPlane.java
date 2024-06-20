package fractalzoomer.planes.distort;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

public class StretchPlane extends Plane {
    private double r;
    private double r_reciprocal;
    private double v1, v2, v3, v4;
    private double xcenterd;
    private double ycenterd;
    private boolean use_center;

    public StretchPlane(double stretchAngle, double strechAmount, Apfloat[] center) {
        r = Math.pow(2, strechAmount);
        double rad_angle = Math.toRadians(stretchAngle);
        double cosanagle = Math.cos(rad_angle);
        double sinangle = Math.sin(rad_angle);
        r_reciprocal = 1 / r;
        double cossqr = cosanagle * cosanagle;
        double sinsqr = sinangle * sinangle;
        v1 = r * cossqr + r_reciprocal * sinsqr;
        v2 = cosanagle * sinangle * (r_reciprocal - r);
        v3 = v2;
        v4 = r * sinsqr + r_reciprocal * cossqr;

        use_center = center[0].compareTo(Apfloat.ZERO) != 0 && center[1].compareTo(Apcomplex.ZERO) != 0;

        if(use_center) {
            xcenterd = center[0].doubleValue();
            ycenterd = center[1].doubleValue();
        }
    }

    @Override
    public Complex transform(Complex pixel) {

        double x = pixel.getRe();
        double y = pixel.getIm();
        if(use_center) {
           x -= xcenterd;
           y -= ycenterd;
        }

        double xprime = x * v1 + y * v2;
        double yprime = x * v3 + y * v4;

        if(use_center) {
            xprime += xcenterd;
            yprime += ycenterd;
        }

        return new Complex(xprime, yprime);
    }
}
