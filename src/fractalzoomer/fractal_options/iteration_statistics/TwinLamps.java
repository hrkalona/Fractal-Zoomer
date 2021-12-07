package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;

public class TwinLamps extends GenericStatistic {
    private double min;
    private double max;
    private double sum;
    private Complex point;
    private int twlFunction;

    public TwinLamps(double statistic_intensity, int twlFunction, double[] twlPoint) {
        super(statistic_intensity, false, false);
        point = new Complex(twlPoint[0], twlPoint[1]);
        this.twlFunction = twlFunction;
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {
        super.insert(z, zold, zold2, iterations, c, start, c0);

        double norm = z.plus(point).norm();


        double ln = Math.exp(-norm);

        if(ln < min) {
            min = ln;
        }
        if(ln > max) {
            max = ln;
        }

        sum = sum + ln;

        samples++;

    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        min = Double.MAX_VALUE;
        max = -1;
        samples = 0;
        sum = 0;
    }

    @Override
    public double getValue() {
        if(samples < 1) {
            return 0;
        }

        if(twlFunction == 1) {
            sum = Math.sqrt(sum);
        }

        return statistic_intensity * sum / (1 + max - min);

    }

    @Override
    public int getType() {
        return MainWindow.ESCAPING;
    }
}
