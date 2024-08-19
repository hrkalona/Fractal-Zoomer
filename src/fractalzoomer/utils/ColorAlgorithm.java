
package fractalzoomer.utils;

/**
 *
 * @author hrkalona2
 */
public class ColorAlgorithm {
    public static final double MAXIMUM_ITERATIONS = Double.MAX_VALUE;
    public static double MAXIMUM_ITERATIONS_DE;
    public static final int INCREMENT = 50;
    public static final int MAGNET_INCREMENT_DEFAULT = 106;
    public static int MAGNET_INCREMENT = MAGNET_INCREMENT_DEFAULT;
    public static boolean OutUsingIncrement = false;
    public static boolean InUsingIncrement = false;
    public static boolean GlobalUsingIncrement = false;
    public static boolean DomainColoringBypass = false;

    static {
        long bits = Double.doubleToRawLongBits(MAXIMUM_ITERATIONS);
        long mantissa = bits & 0x000fffffffffffffL;
        mantissa--;
        bits = (bits & 0xfff0000000000000L) | mantissa;

        MAXIMUM_ITERATIONS_DE = Double.longBitsToDouble(bits); //This is the previous max number
    }

    private static double getResultWithoutIncrement(double result) {


        if((!OutUsingIncrement && !InUsingIncrement && !GlobalUsingIncrement) || DomainColoringBypass) {
            return result;
            //return Math.abs(result); //Todo keep an eye for issues
        }
        
        if(result <= -INCREMENT) {
            return Math.abs(result + INCREMENT);
        }

        //return Math.abs(result); //Todo keep an eye for issues
        return result;

    }
    
    public static double transformResultToHeight(double result, int max_iterations) {

        double res = Math.abs(result);
        if(res == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return result < 0 ? -max_iterations : max_iterations;
        }
        else if(res == ColorAlgorithm.MAXIMUM_ITERATIONS_DE) {
            return result < 0 ? -max_iterations : max_iterations;
        }
        return getResultWithoutIncrement(result);
        
    }
}
