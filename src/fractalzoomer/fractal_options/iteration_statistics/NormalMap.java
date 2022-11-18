package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.*;
import fractalzoomer.core.bla.BLA;
import fractalzoomer.core.bla.BLADeep;
import fractalzoomer.core.interpolation.*;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.ColorAlgorithm;
import org.apfloat.Apfloat;

public class NormalMap extends GenericStatistic {
    private Complex derivative;
    private Complex derivative2;

    private MantExpComplex derivative_m;
    private MantExpComplex derivative2_m;
    private boolean useSecondDerivative;
    private double power;
    private double h2;
    private Complex v;
    private double DELimit;
    private MantExp DELimit_m;

    private double DEUpperLimit;
    private MantExp DEUpperLimit_m;
    private boolean isJulia;
    private boolean isJuliter;
    private int juliterIterations;
    private boolean normalMapUseDE;
    private boolean normalMapInvertDE;
    private int normalMapColoring;
    private boolean useNormalMap;
    private int function;

    private boolean normalMapDEAAEffect;

    private double normalMapDEfactor;

    private boolean normalMapOverrideColoring;

    private boolean supportsDeepCalc;

    private int deFadeAlgorithm = 0;


    public NormalMap(double statistic_intensity, double power, double height, double angle, boolean useSecondDerivative, double size, double normalMapDEfactor, boolean isJulia, boolean normalMapUseDE, boolean normalMapInvertDE, int normalMapColoring, boolean useNormalMap, double DEUpperLimit, boolean normalMapDEAAEffect, boolean normalMapOverrideColoring, int deFadeAlgorithm) {
        super(statistic_intensity, false, false);
        this.power = power;
        h2 = height;
        v = new Complex(0, angle*2*Math.PI/360).exp();
        this.useSecondDerivative = useSecondDerivative;
        DELimit = (size / ThreadDraw.IMAGE_SIZE) * normalMapDEfactor;
        this.normalMapDEfactor = normalMapDEfactor;
        this.isJulia = isJulia;
        this.normalMapUseDE = normalMapUseDE;
        this.normalMapInvertDE = normalMapInvertDE;
        this.normalMapColoring = normalMapColoring;
        this.useNormalMap = useNormalMap;
        this.DEUpperLimit = DEUpperLimit;
        DEUpperLimit_m = new MantExp(DEUpperLimit);
        this.normalMapDEAAEffect = normalMapDEAAEffect;
        isJuliter = false;
        juliterIterations = 0;
        this.normalMapOverrideColoring = normalMapOverrideColoring;
        this.deFadeAlgorithm = deFadeAlgorithm;
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
                derivative_m = new MantExpComplex(derivative);
            }
            else if ((function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTNTH)){
                if (zold.compare(new Complex()) != 0) {
                    derivative = new Complex(1, 0);
                    derivative_m = new MantExpComplex(derivative);
                }
            }
        }

        MantExpComplex zold_m = null;
        MantExpComplex c_val_m = null;

        boolean supportsDeepCalculations = supportsDeepCalculations();

        if(supportsDeepCalculations) {
            zold_m = new MantExpComplex(zold);
            c_val_m = new MantExpComplex(c_val);
        }

        if(useSecondDerivative) {
            if(function == Constants.MANDELBROT) {
                if(supportsDeepCalculations) {
                    derivative2_m = (derivative2_m.times(zold_m).plus_mutable(derivative_m.square())).times_mutable(power); // 2ddc * z + 2dc^2
                }
                else {
                    derivative2 = (derivative2.times(zold).plus_mutable(derivative.square())).times_mutable(power); // 2ddc * z + 2dc^2
                }
            }
            else if(function == Constants.MANDELBROTCUBED) {
                //Hopefully my math are correct
                //ddc = 3*ddc*z^2 + 6*z*dc^2
                if(supportsDeepCalculations) {
                    derivative2_m = derivative2_m.times(power).times_mutable(zold_m.square()).plus_mutable(derivative_m.square().times_mutable(power * (power - 1)).times_mutable(zold_m));
                }
                else {
                    derivative2 = derivative2.times(power).times_mutable(zold.square()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold));
                }
            }
            else if(function == Constants.MANDELBROTFOURTH) {
                if(supportsDeepCalculations) {
                    derivative2_m = derivative2_m.times(power).times_mutable(zold_m.cube()).plus_mutable(derivative_m.square().times_mutable(power * (power - 1)).times_mutable(zold_m.square()));
                }
                else {
                    derivative2 = derivative2.times(power).times_mutable(zold.cube()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.square()));
                }
            }
            else if(function == Constants.MANDELBROTFIFTH) {
                if(supportsDeepCalculations) {
                    derivative2_m = derivative2_m.times(power).times_mutable(zold_m.fourth()).plus_mutable(derivative_m.square().times_mutable(power * (power - 1)).times_mutable(zold_m.cube()));
                }
                else {
                    derivative2 = derivative2.times(power).times_mutable(zold.fourth()).plus_mutable(derivative.square().times_mutable(power * (power - 1)).times_mutable(zold.cube()));
                }
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
                if (supportsDeepCalculations) {
                    derivative2_m = (derivative2_m.times(new MantExpComplex(1, 0).sub_mutable(zold_m.times2())).sub_mutable(derivative_m.square().times2_mutable())).times_mutable(c_val_m);
                } else {
                    derivative2 = (derivative2.times(zold.times(2).r_sub_mutable(1)).sub_mutable(derivative.square().times_mutable(2))).times_mutable(c_val);
                }
            }

            if(function == Constants.LAMBDA && (!isJulia || (isJulia && isJuliter && iterations < juliterIterations))) {
                if (supportsDeepCalculations) {
                    derivative2_m.plus_mutable((new MantExpComplex(2, 0).sub_mutable(zold_m.times4())).times_mutable(derivative_m));
                }
                else {
                    derivative2.plus_mutable((zold.times(4).r_sub_mutable(2)).times_mutable(derivative));
                }
            }
        }

        if(function == Constants.MANDELBROT) {
            if(supportsDeepCalculations) {
                derivative_m = derivative_m.times(power).times_mutable(zold_m);
            }
            else {
                derivative = derivative.times(power).times_mutable(zold);
            }
        }
        else if(function == Constants.MANDELBROTCUBED) {
            if(supportsDeepCalculations) {
                derivative_m = derivative_m.times(power).times_mutable(zold_m.square());
            }
            else {
                derivative = derivative.times(power).times_mutable(zold.square());
            }
        }
        else if(function == Constants.MANDELBROTFOURTH) {
            if(supportsDeepCalculations) {
                derivative_m = derivative_m.times(power).times_mutable(zold_m.cube());
            }
            else {
                derivative = derivative.times(power).times_mutable(zold.cube());
            }
        }
        else if(function == Constants.MANDELBROTFIFTH) {
            if(supportsDeepCalculations) {
                derivative_m = derivative_m.times(power).times_mutable(zold_m.fourth());
            }
            else {
                derivative = derivative.times(power).times_mutable(zold.fourth());
            }
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
            if(supportsDeepCalculations) {
                derivative_m = derivative_m.times(new MantExpComplex(1, 0).sub_mutable(zold_m.times2())).times_mutable(c_val_m);
            }
            else {
                derivative = derivative.times(zold.times(2).r_sub_mutable(1)).times_mutable(c_val);
            }
        }

        if((function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTNTH) && (!isJulia || (isJulia && isJuliter && iterations < juliterIterations))) {

            if(supportsDeepCalculations) {
                derivative_m.plus_mutable(MantExp.ONE);
            }
            else {
                derivative.plus_mutable(1);
            }
        }
        else if(function == Constants.LAMBDA && (!isJulia || (isJulia && isJuliter && iterations < juliterIterations))) {
            if(supportsDeepCalculations) {
                derivative_m.sub_mutable(zold_m.square()).plus_mutable(zold_m);
            }
            else {
                derivative.sub_mutable(zold.square()).plus_mutable(zold);
            }
        }

        if(supportsDeepCalculations) {
            if(useSecondDerivative) {
                derivative2_m.Reduce();
            }
            derivative_m.Reduce();
        }

        samples++;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, BLA bla) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(power == 0) {
            return;
        }

        if(iterations == 0) {

            if ((function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTNTH)){
                if (zold.compare(new Complex()) != 0) {
                    derivative = new Complex(1, 0);
                    derivative_m = new MantExpComplex(derivative);
                }
            }

        }

        boolean supportsDeepCalculations = supportsDeepCalculations();

        if(useSecondDerivative) {
            if(supportsDeepCalculations) {
                derivative2_m = new MantExpComplex(bla.getValue(derivative2_m.toComplex()));
                derivative2_m.Reduce();
            }
            else {
                derivative2 = bla.getValue(derivative2);
            }
        }

        if(supportsDeepCalculations) {
            derivative_m = new MantExpComplex(bla.getValue(derivative_m.toComplex(), 1));
            derivative_m.Reduce();
        }
        else {
            derivative = bla.getValue(derivative, 1);
        }

        samples++;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, BLADeep bla) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(power == 0) {
            return;
        }

        if(iterations == 0) {

            if ((function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTNTH)){
                if (zold.compare(new Complex()) != 0) {
                    derivative = new Complex(1, 0);
                    derivative_m = new MantExpComplex(derivative);
                }
            }

        }

        if(useSecondDerivative) {
            derivative2_m = bla.getValue(derivative2_m);
            derivative2_m.Reduce();
        }

        derivative_m = bla.getValue(derivative_m, MantExp.ONE);
        derivative_m.Reduce();

        samples++;
    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        samples = 0;
        derivative = new Complex();
        derivative2 = new Complex();
        derivative_m = new MantExpComplex();
        derivative2_m = new MantExpComplex();
    }

    private double getValueInternal() {
        if(power == 0) {
            return 0;
        }

        Complex u = null;

        if(supportsDeepCalculations()) {
            MantExpComplex u_m = null;
            if(useSecondDerivative) {
                double lo = 0.5 * Math.log(z_val.hypot());
                MantExpComplex z_val_m = new MantExpComplex(z_val);
                u_m = z_val_m.times(derivative_m).times_mutable(derivative_m.square().conjugate_mutable().times_mutable(1 + lo).sub_mutable(z_val_m.times(derivative2_m).conjugate_mutable().times_mutable(lo)));
            }
            else {
                MantExpComplex z_val_m = new MantExpComplex(z_val);
                u_m = z_val_m.divide(derivative_m);

            }
            u = u_m.divide(u_m.norm()).toComplex();  // normal vector: (u.re,u.im,1)
        }
        else {
            if(useSecondDerivative) {
                double lo = 0.5 * Math.log(z_val.norm());
                u = z_val.times(derivative).times_mutable(derivative.square().conjugate_mutable().times_mutable(1 + lo).sub_mutable(z_val.times(derivative2).conjugate_mutable().times_mutable(lo)));
            }
            else {
                u = z_val.divide(derivative);
            }
            u = u.divide(u.norm());  // normal vector: (u.re,u.im,1)
        }

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

        if(normalMapUseDE && !normalMapDEAAEffect) {

            if(supportsDeepCalculations()) {
                double temp2 = z_val.hypot();
                double temp3 = Math.log(temp2);

                MantExp left = new MantExp(temp2).multiply(new MantExp(temp3));
                MantExp right = (derivative_m.hypot().multiply(DELimit_m));

                if(normalMapInvertDE) {
                    if (left.compareTo(right) > 0) {
                        if(DEUpperLimit == 0) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }

                        if(left.compareTo(right.multiply(DEUpperLimit_m)) < 0) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                        else {
                            return ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                    }
                }
                else {
                    if (left.compareTo(right) <= 0 ) {

                        if(DEUpperLimit == 0) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }

                        if(left.compareTo(right.divide(DEUpperLimit_m)) > 0) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                        else {
                            return ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                    }
                }
            }
            else {
                double temp2 = z_val.hypot();
                double temp3 = Math.log(temp2);

                double left = temp2 * temp3;
                double right = (derivative.hypot() * DELimit);

                if(normalMapInvertDE) {
                    if (!Double.isNaN(left) && !Double.isNaN(right) && left > right) {
                        if(DEUpperLimit == 0) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }

                        if(left < right * DEUpperLimit) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                        else {
                            return ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                    }
                }
                else {
                    if (!Double.isNaN(left) && !Double.isNaN(right) && left <= right ) {

                        if(DEUpperLimit == 0) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }

                        if(left > right / DEUpperLimit) {
                            return -ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                        else {
                            return ColorAlgorithm.MAXIMUM_ITERATIONS;
                        }
                    }
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

        if(!normalMapOverrideColoring) {
            return 0;
        }

        if(normalMapColoring == 1) {

            if(supportsDeepCalculations()) {

                double norm_z = z_val.hypot();
                MantExpComplex u = new MantExpComplex(z_val).divide(derivative_m);
                MantExpComplex temp = u.times(Math.log(norm_z)).times(MantExp.TWO);
                return ((1 + temp.toComplex().arg() / (2 * Math.PI)) % 1.0);//Todo: Bug here

            }
            else {
                double norm_z = z_val.hypot();
                Complex u = z_val.divide(derivative);

                Complex temp = u.times(Math.log(norm_z)).times(2);

                return ((1 + temp.arg() / (2 * Math.PI)) % 1.0);
            }

        }
        else if(normalMapColoring == 2 || normalMapColoring == 3){
            //double norm_z = z_val.norm();
            //Complex temp = z_val.times(Math.log(norm_z)).times(2).divide(derivative);
            //return 1 / Math.tanh(temp.norm());

            if(supportsDeepCalculations()) {
                double temp2 = z_val.hypot();
                double temp3 = Math.log(temp2);
                MantExp temp4 = new MantExp(temp2).divide(derivative_m.hypot());
                double temp = -4.0 * new MantExp(temp3).multiply(temp4).multiply_mutable(MantExp.TWO).log();

                return temp < 0 ? 0 : temp;
            }
            else {
                double temp2 = z_val.hypot();
                double temp3 = Math.log(temp2);
                double temp4 = temp2 / derivative.hypot();
                double temp = -4.0 * Math.log(2 * temp3 * temp4);

                return temp < 0 ? 0 : temp;
            }

        }

        return 0;

    }

    public double getDeCoefficient() {

        double t;
        if(supportsDeepCalculations()) {
            double temp2 = z_val.hypot();
            double temp3 = Math.log(temp2);

            MantExp d = new MantExp(temp2).multiply(new MantExp(temp3)).divide_mutable(derivative_m.hypot());
            MantExp t_m = d.divide(DELimit_m);
            t = t_m.toDouble();
        }
        else {
            double temp2 = z_val.hypot();
            double temp3 = Math.log(temp2);

            double d = temp2 * temp3 / derivative.hypot();
            t = d / DELimit;
        }

        if(normalMapInvertDE) {
            t = 1 - (t > 1 ? 1 : t);
        }
        else {
            t = t > 1 ? 1 : t;
        }

        switch (deFadeAlgorithm) {
            case 1:
                return SqrtInterpolation.getCoefficient(t);
            case 2:
                return CbrtInterpolation.getCoefficient(t);
            case 3:
                return FrthrootInterpolation.getCoefficient(t);
            case 4:
                return CosineInterpolation.getCoefficient(t);
            case 5:
                return AccelerationInterpolation.getCoefficient(t);
            case 6:
                return SineInterpolation.getCoefficient(t);
            case 7:
                return DecelerationInterpolation.getCoefficient(t);
            case 8:
                return ThirdPolynomialInterpolation.getCoefficient(t);
            case 9:
                return FifthPolynomialInterpolation.getCoefficient(t);
            case 10:
                return Exponential2Interpolation.getCoefficient(t);
            case 11:
                return SmoothTransitionFunctionInterpolation.getCoefficient(t);
            default:
                return t;
        }


    }

    public boolean supportsDeepCalculations() {
        return supportsDeepCalc;
    }

    @Override
    //Todo: Find a way to fix polar variable size
    public void setSize(Apfloat size) {
        super.setSize(size);
        DELimit_m = new MantExp(MyApfloat.fp.multiply(MyApfloat.fp.divide(size, new MyApfloat(ThreadDraw.IMAGE_SIZE)), new MyApfloat(normalMapDEfactor)));
        supportsDeepCalc = (function >= MainWindow.MANDELBROT && function <= MainWindow.MANDELBROTFIFTH  || function == MainWindow.LAMBDA) && ThreadDraw.PERTURBATION_THEORY;
    }

    public void initializeApproximationDerivatives(MantExpComplex dz, MantExpComplex ddz) {
        derivative = dz.toComplex();
        derivative_m = dz;
        derivative2 = ddz.toComplex();
        derivative2_m = ddz;
    }

    public boolean usesSecondDerivative() {
        return useSecondDerivative;
    }
}
