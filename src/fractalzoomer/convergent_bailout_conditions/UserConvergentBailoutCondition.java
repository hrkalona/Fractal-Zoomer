package fractalzoomer.convergent_bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.main.MainWindow;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import org.apfloat.Apfloat;

public class UserConvergentBailoutCondition extends ConvergentBailoutCondition {
    private ExpressionNode[] expr;
    private Parser[] parser;
    private int convergent_bailout_test_comparison;
    private Complex[] globalVars;

    public UserConvergentBailoutCondition(double convergent_bailout, String convergent_bailout_test_user_formula, String convergent_bailout_test_user_formula2, int convergent_bailout_test_comparison, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars) {
        super(convergent_bailout);

        this.globalVars = globalVars;

        parser = new Parser[2];
        expr = new ExpressionNode[2];

        parser[0] = new Parser();
        parser[1] = new Parser();

        expr[0] = parser[0].parse(convergent_bailout_test_user_formula);
        expr[1] = parser[1].parse(convergent_bailout_test_user_formula2);

        this.convergent_bailout_test_comparison = convergent_bailout_test_comparison;

        Complex cbailbound = new Complex(convergent_bailout, 0);

        if(parser[0].foundCbail()) {
            parser[0].setCbailvalue(cbailbound);
        }

        if(parser[1].foundCbail()) {
            parser[1].setCbailvalue(cbailbound);
        }

        Complex c_max_iterations = new Complex(max_iterations, 0);

        if(parser[0].foundMaxn()) {
            parser[0].setMaxnvalue(c_max_iterations);
        }

        if(parser[1].foundMaxn()) {
            parser[1].setMaxnvalue(c_max_iterations);
        }

        Complex c_center = new Complex(xCenter, yCenter);

        if(parser[0].foundCenter()) {
            parser[0].setCentervalue(c_center);
        }

        if(parser[1].foundCenter()) {
            parser[1].setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);

        if(parser[0].foundSize()) {
            parser[0].setSizevalue(c_size);
        }

        if(parser[1].foundSize()) {
            parser[1].setSizevalue(c_size);
        }

        Complex c_isize = new Complex(Math.min(TaskRender.WIDTH, TaskRender.HEIGHT), 0);
        if (parser[0].foundISize()) {
            parser[0].setISizevalue(c_isize);
        }

        if (parser[1].foundISize()) {
            parser[1].setISizevalue(c_isize);
        }

        Complex c_width = new Complex(TaskRender.WIDTH, 0);

        if (parser[0].foundWidth()) {
            parser[0].setWidthvalue(c_width);
        }

        if (parser[1].foundWidth()) {
            parser[1].setWidthvalue(c_width);
        }

        Complex c_height = new Complex(TaskRender.HEIGHT, 0);

        if (parser[0].foundHeight()) {
            parser[0].setHeightvalue(c_height);
        }

        if (parser[1].foundHeight()) {
            parser[1].setHeightvalue(c_height);
        }

        Complex c_point = new Complex(point[0], point[1]);

        if(parser[0].foundPoint()) {
            parser[0].setPointvalue(c_point);
        }

        if(parser[1].foundPoint()) {
            parser[1].setPointvalue(c_point);
        }
    }

    @Override
    public boolean converged(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        /*LEFT*/
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex(iterations, 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(z);
        }

        if(parser[0].foundC()) {
            parser[0].setCvalue(c);
        }

        if(parser[0].foundS()) {
            parser[0].setSvalue(start);
        }

        if(parser[0].foundPixel()) {
            parser[0].setPixelvalue(pixel);
        }

        if(parser[0].foundC0()) {
            parser[0].setC0value(c0);
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(zold);
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(zold2);
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[0].foundVar(i)) {
                parser[0].setVarsvalue(i, globalVars[i]);
            }
        }

        /*RIGHT*/
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex(iterations, 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(z);
        }

        if(parser[1].foundC()) {
            parser[1].setCvalue(c);
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(start);
        }

        if(parser[1].foundPixel()) {
            parser[1].setPixelvalue(pixel);
        }

        if(parser[1].foundC0()) {
            parser[1].setC0value(c0);
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(zold);
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(zold2);
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[1].foundVar(i)) {
                parser[1].setVarsvalue(i, globalVars[i]);
            }
        }

        int res = expr[0].getValue().compare(expr[1].getValue());


        boolean result = false;
        switch (convergent_bailout_test_comparison) {
            case MainWindow.GREATER: //Escape if a > b
                result =  res == -1;
                break;
            case MainWindow.GREATER_EQUAL: //Escape if a >= b
                result =  (res == -1 || res == 0);
                break;
            case MainWindow.LOWER: //Escape if a < b
                result = res == 1;
                break;
            case MainWindow.LOWER_EQUAL: //Escape if a <= b
                result = (res == 1 || res == 0);
                break;
            case MainWindow.EQUAL: //Escape if a == b
                result =  res == 0;
                break;
            case MainWindow.NOT_EQUAL: //Escape if a != b
                result = res != 0;
                break;
            default:
                result = false;
                break;
        }

        if(calculateDistance && result) {
            distance = z.distance_squared(zold);
        }

        return result;
    }

    @Override
    public boolean converged(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(Complex z, double root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return converged(z, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(DDComplex z, DoubleDouble root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(BigComplex z, Apfloat root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNum root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNum root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNum root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }


    @Override
    public boolean converged(Complex z, Complex root, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {
        return converged(z, zold, zold2, iterations, c, start, c0, pixel);
    }

    @Override
    public boolean converged(BigComplex z, BigComplex root, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, BigComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(BigIntNumComplex z, BigIntNumComplex root, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(MpfrBigNumComplex z, MpfrBigNumComplex root, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(MpirBigNumComplex z, MpirBigNumComplex root, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNumComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }

    @Override
    public boolean converged(DDComplex z, DDComplex root, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DDComplex pixel) {
        return converged(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), pixel.toComplex());
    }
}
