
package fractalzoomer.core.interpolation;


/**
 *
 * @author hrkalona2
 */
public class CatmullRomInterpolation extends InterpolationMethod {

    public CatmullRomInterpolation() {
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
    
    public static double catmullrom(double t, double p0, double p1, double p2, double p3) {
        return 0.5 * ((2 * p1)
                + (-p0 + p2) * t
                + (2 * p0 - 5 * p1 + 4 * p2 - p3) * t * t
                + (-p0 + 3 * p1 - 3 * p2 + p3) * t * t * t);
    }

    public static double getCoefficient(double coef) {
        return catmullrom(coef, 0.5, 0, 1, 0.5);
    }
}
