package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.ColorAlgorithm;

public class NormalMap extends GenericStatistic {
    private Complex derivative;
    private Complex derivative2;
    private boolean useSecondDerivative;
    private double power;
    private double h2;
    private Complex v;
    private double DELimit;
    private boolean isJulia;
    private boolean isJuliter;
    private int juliterIterations;
    private boolean normalMapUseDE;
    private boolean normalMapInvertDE;
    private int normalMapColoring;
    private boolean useNormalMap;
    private int function;


    public NormalMap(double statistic_intensity, double power, double height, double angle, boolean useSecondDerivative, double size, double normalMapDEfactor, boolean isJulia, boolean normalMapUseDE, boolean normalMapInvertDE, int normalMapColoring, boolean useNormalMap) {
        super(statistic_intensity, false, false);
        this.power = power;
        h2 = height;
        v = new Complex(0, angle*2*Math.PI/360).exp();
        this.useSecondDerivative = useSecondDerivative;
        DELimit = (size / ThreadDraw.IMAGE_SIZE) * normalMapDEfactor;
        DELimit = DELimit * DELimit;
        this.isJulia = isJulia;
        this.normalMapUseDE = normalMapUseDE;
        this.normalMapInvertDE = normalMapInvertDE;
        this.normalMapColoring = normalMapColoring;
        this.useNormalMap = useNormalMap;
        isJuliter = false;
        juliterIterations = 0;
    }

    public void setJuliterOptions(boolean isJuliter, int juliterIterations) {
        this.isJuliter = isJuliter;
        this.juliterIterations = juliterIterations;
    }

    public void setFunctionId(int function) {
        this.function = function;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(power == 0) {
            return;
        }

        if(iterations == 0) {

            if(function == Constants.LAMBDA && isJulia && !isJuliter) {
                derivative = new Complex(0.25, 0);
            }
            else if ((function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTNTH)){
                if (zold.compare(new Complex()) != 0) {
                    derivative = new Complex(1, 0);
                }
            }
        }

        if(useSecondDerivative) {
            if(function == Constants.MANDELBROT) {
                derivative2 = (derivative2.times(zold).plus_mutable(derivative.square())).times_mutable(power); // 2ddc * z + 2dc^2
            }
            else if(function == Constants.MANDELBROTCUBED) {
                //Hopefully my math are correct
                //ddc = 3*ddc*z^2 + 6*z*dc^2
                derivative2 = derivative2.times(power).times_mutable(zold.square()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold));
            }
            else if(function == Constants.MANDELBROTFOURTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.cube()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.square()));
            }
            else if(function == Constants.MANDELBROTFIFTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.fourth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.cube()));
            }
            else if(function == Constants.MANDELBROTSIXTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.fifth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.fourth()));
            }
            else if(function == Constants.MANDELBROTSEVENTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.sixth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.fifth()));
            }
            else if(function == Constants.MANDELBROTEIGHTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.seventh()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.sixth()));
            }
            else if(function == Constants.MANDELBROTNINTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.eighth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.seventh()));
            }
            else if(function == Constants.MANDELBROTTENTH) {
                derivative2 = derivative2.times(power).times_mutable(zold.ninth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.eighth()));
            }
            else if(function == Constants.MANDELBROTNTH) {
                if(power == 11) {
                    derivative2 = derivative2.times(power).times_mutable(zold.tenth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.ninth()));
                }
                else {
                    derivative2 = derivative2.times(power).times_mutable(zold.pow(power - 1)).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.pow(power - 2)));
                }
            }
            else if(function == Constants.LAMBDA) {
                derivative2 = (derivative2.times(zold.times(2).r_sub_mutable(1)).sub_mutable(derivative.square().times_mutable(2))).times_mutable(c_val);
            }

            if(function == Constants.LAMBDA && (!isJulia || (isJulia && isJuliter && iterations < juliterIterations))) {
                derivative2.plus_mutable((zold.times(4).r_sub_mutable(2)).times_mutable(derivative));
            }
        }

        if(function == Constants.MANDELBROT) {
            derivative = derivative.times(power).times_mutable(zold);
        }
        else if(function == Constants.MANDELBROTCUBED) {
            derivative = derivative.times(power).times_mutable(zold.square());
        }
        else if(function == Constants.MANDELBROTFOURTH) {
            derivative = derivative.times(power).times_mutable(zold.cube());
        }
        else if(function == Constants.MANDELBROTFIFTH) {
            derivative = derivative.times(power).times_mutable(zold.fourth());
        }
        else if(function == Constants.MANDELBROTSIXTH) {
            derivative = derivative.times(power).times_mutable(zold.fifth());
        }
        else if(function == Constants.MANDELBROTSEVENTH) {
            derivative = derivative.times(power).times_mutable(zold.sixth());
        }
        else if(function == Constants.MANDELBROTEIGHTH) {
            derivative = derivative.times(power).times_mutable(zold.seventh());
        }
        else if(function == Constants.MANDELBROTNINTH) {
            derivative = derivative.times(power).times_mutable(zold.eighth());
        }
        else if(function == Constants.MANDELBROTTENTH) {
            derivative = derivative.times(power).times_mutable(zold.ninth());
        }
        else if(function == Constants.MANDELBROTNTH) {
            if(power == 11) {
                derivative = derivative.times(power).times_mutable(zold.tenth());
            }
            else {
                derivative = derivative.times(power).times_mutable(zold.pow(power - 1));
            }
        }
        else if (function == Constants.LAMBDA) {
            derivative = derivative.times(zold.times(2).r_sub_mutable(1)).times_mutable(c_val);
        }

        if((function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTNTH) && (!isJulia || (isJulia && isJuliter && iterations < juliterIterations))) {
            derivative.plus_mutable(1);
        }
        else if(function == Constants.LAMBDA && (!isJulia || (isJulia && isJuliter && iterations < juliterIterations))) {
            derivative.sub_mutable(zold.square()).plus_mutable(zold);
        }

        samples++;
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        samples = 0;
        derivative = new Complex();
        derivative2 = new Complex();
    }

    private double getValueInternal() {
        if(power == 0) {
            return 0;
        }

        Complex u = null;
        if(useSecondDerivative) {
            double lo = 0.5 * Math.log(z_val.norm());
            u = z_val.times(derivative).times(derivative.square().conjugate().times(1 + lo).sub(z_val.times(derivative2).conjugate().times(lo)));
        }
        else {
            u = z_val.divide(derivative);

        }

        u = u.divide(u.norm());  // normal vector: (u.re,u.im,1)
        double reflection = u.getRe() * v.getRe() + u.getIm() * v.getIm() + h2;  // dot product with the incoming light
        reflection = reflection / (1 + h2);  // rescale so that t does not get bigger than 1
        if (reflection < 0.0) reflection = 0.0;
        if (reflection > 1) reflection = 1;

        return reflection;
    }

    @Override
    public double getValueNotEscaped() {
        boolean oldnormalMapUseDE = normalMapUseDE;
        normalMapUseDE = false;
        double val = getValue();
        normalMapUseDE = oldnormalMapUseDE;
        return val;
    }

    @Override
    public double getValue() {

        if(normalMapUseDE) {
            double temp2 = z_val.norm_squared();
            double temp3 = Math.log(temp2);

            double left = temp2 * temp3 * temp3;
            double right = (derivative.norm_squared() * DELimit);

            if(normalMapInvertDE) {
                if (!Double.isNaN(left) && !Double.isNaN(right) && left > right) {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }
            else {
                if (!Double.isNaN(left) && !Double.isNaN(right) && left <= right) {
                    return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                }
            }
        }

        double value = 0;

        if(useNormalMap) {
            value = getValueInternal();
        }

        if(Double.isNaN(value)) {
            value =  0;
        }

        double value2 = getExtraValue();

        if(Double.isNaN(value2)) {
            value2 =  0;
        }

        return (value + value2) * statistic_intensity;
    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }

    @Override
    public double getValueForColoring() {
        return getValueInternal();
    }

    @Override
    public double getExtraValue() {

        if(normalMapColoring == 1) {
            double norm_z = z_val.norm();
            Complex temp = z_val.times(Math.log(norm_z)).times(2).divide(derivative);
            return ((1 + temp.arg() / (2 * Math.PI)) % 1.0);
        }
        else if(normalMapColoring == 2 || normalMapColoring == 3){
            //double norm_z = z_val.norm();
            //Complex temp = z_val.times(Math.log(norm_z)).times(2).divide(derivative);
            //return 1 / Math.tanh(temp.norm());

            double temp2 = z_val.norm_squared();
            double temp3 = Math.log(temp2);
            double temp = -2.0 * Math.log(temp3 * temp3 * temp2 / derivative.norm_squared());

            return temp < 0 ? 0 : temp;
        }

        return 0;

    }
}
