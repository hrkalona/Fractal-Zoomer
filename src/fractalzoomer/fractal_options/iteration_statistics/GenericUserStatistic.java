package fractalzoomer.fractal_options.iteration_statistics;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.main.MainWindow;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

public abstract class GenericUserStatistic extends GenericStatistic {
    protected Complex val;
    protected Complex val2;
    protected int statisticIteration;
    protected ExpressionNode expr;
    protected Parser parser;
    protected Complex[] globalVars;
    protected int reductionFunction;
    protected boolean useIterations;
    private PlanePointOption init_val;

    public GenericUserStatistic(double statistic_intensity, String user_statistic_formula, double xCenter, double yCenter, int max_iterations, double size, double[] point, Complex[] globalVars, boolean useAverage, String user_statistic_init_value, int reductionFunction, boolean useIterations, boolean useSmoothing, int lastXItems) {
        super(statistic_intensity, useSmoothing, useAverage, lastXItems);
        val = new Complex();
        val2 = new Complex();

        this.useAverage = useAverage;
        this.reductionFunction = reductionFunction;
        this.useIterations = useIterations;

        this.globalVars = globalVars;

        parser = new Parser();
        expr = parser.parse(user_statistic_formula);

        if (parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser.foundCenter()) {
            parser.setCentervalue(new Complex(xCenter, yCenter));
        }

        if (parser.foundSize()) {
            parser.setSizevalue(new Complex(size, 0));
        }

        if (parser.foundISize()) {
            parser.setISizevalue(new Complex(Math.min(TaskRender.WIDTH, TaskRender.HEIGHT), 0));
        }

        if (parser.foundWidth()) {
            parser.setWidthvalue(new Complex(TaskRender.WIDTH, 0));
        }

        if (parser.foundHeight()) {
            parser.setHeightvalue(new Complex(TaskRender.HEIGHT, 0));
        }

        if (parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }

        init_val = new VariableInitialValue(user_statistic_init_value, xCenter, yCenter, size, max_iterations, point, globalVars);
    }

    @Override
    public void insert(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        super.insert(z, zold, zold2, iterations, c, start, c0);

        if(keepLastXItems) {
            return;
        }

        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        if (parser.foundZ()) {
            parser.setZvalue(z);
        }

        if (parser.foundC()) {
            parser.setCvalue(c);
        }

        if (parser.foundS()) {
            parser.setSvalue(start);
        }

        if (parser.foundC0()) {
            parser.setC0value(c0);
        }

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if(parser.foundStat()) {
            parser.setStatvalue(val);
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        switch (reductionFunction) {
            case MainWindow.REDUCTION_SUM:
                val2.assign(val);
                val.plus_mutable(expr.getValue());
                break;
            case MainWindow.REDUCTION_MIN:
                Complex newVal = expr.getValue();
                int res = newVal.compare(val);

                if (res == 1) { //newVal < val
                    val.assign(newVal);
                    statisticIteration = iterations;
                }
                break;
            case MainWindow.REDUCTION_MAX:
                newVal = expr.getValue();
                res = newVal.compare(val);

                if (res == -1) { //newVal > val
                    val.assign(newVal);
                    statisticIteration = iterations;
                }
                break;
            case MainWindow.REDUCTION_ASSIGN:
                val2.assign(val);
                val.assign(expr.getValue());
                break;
            case MainWindow.REDUCTION_SUB:
                val2.assign(val);
                val.sub_mutable(expr.getValue());
                break;
            case MainWindow.REDUCTION_MULT:
                val2.assign(val);
                val.times_mutable(expr.getValue());
                break;
        }

        samples++;

    }

    @Override
    public void initialize(Complex pixel, Complex untransformedPixel) {
        super.initialize(pixel, untransformedPixel);
        val = new Complex(init_val.getValue(pixel));
        val2 = new Complex(val);
        samples = 0;
        statisticIteration = 0;

        if (parser.foundPixel()) {
            parser.setPixelvalue(pixel);
        }
    }

    protected void addSample(StatisticSample sam, Complex val, int[] samples) {

        int iterations = sam.iterations;
        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        if (parser.foundZ()) {
            parser.setZvalue(sam.z_val);
        }

        if (parser.foundC()) {
            parser.setCvalue(sam.c_val);
        }

        if (parser.foundS()) {
            parser.setSvalue(sam.start_val);
        }

        if (parser.foundC0()) {
            parser.setC0value(sam.c0_val);
        }

        if (parser.foundP()) {
            parser.setPvalue(sam.zold_val);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(sam.zold2_val);
        }

        if(parser.foundStat()) {
            parser.setStatvalue(val);
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        switch (reductionFunction) {
            case MainWindow.REDUCTION_SUM:
                val.plus_mutable(expr.getValue());
                break;
            case MainWindow.REDUCTION_MIN:
                Complex newVal = expr.getValue();
                int res = newVal.compare(val);

                if (res == 1) { //newVal < val
                    val.assign(newVal);
                    statisticIteration = iterations;
                }
                break;
            case MainWindow.REDUCTION_MAX:
                newVal = expr.getValue();
                res = newVal.compare(val);

                if (res == -1) { //newVal > val
                    val.assign(newVal);
                    statisticIteration = iterations;
                }
                break;
            case MainWindow.REDUCTION_ASSIGN:
                val.assign(expr.getValue());
                break;
            case MainWindow.REDUCTION_SUB:
                val.sub_mutable(expr.getValue());
                break;
            case MainWindow.REDUCTION_MULT:
                val.times_mutable(expr.getValue());
                break;
        }

        samples[0]++;
    }

    protected int[] sampleLastX() {

        int length = sampleItems.length;

        int[] samples1 = new int[1];
        int[] samples2 = new int[1];

        if(reductionFunction != MainWindow.REDUCTION_MIN && reductionFunction != MainWindow.REDUCTION_MAX) {
            int start = sampleItem % sampleItems.length;
            for(int i = start, count = 1; count < length; i++, count++) {
                StatisticSample sam = sampleItems[i % sampleItems.length];

                if(sam != null) {
                    addSample(sam, val2, samples2);
                }
            }

            start = (sampleItem + 1) % sampleItems.length;
            for(int i = start, count = 1; count < length; i++, count++) {
                StatisticSample sam = sampleItems[i % sampleItems.length];

                if(sam != null) {
                    addSample(sam, val, samples1);
                }
            }

            samples = Math.min(samples1[0], samples2[0]);

            return new int [] {samples1[0], samples2[0]};
        }
        else {
            int start = sampleItem >= sampleItems.length ? sampleItem % sampleItems.length : 0;
            for(int i = start, count = 0; count < sampleItems.length; i++, count++) {
                StatisticSample sam = sampleItems[i % sampleItems.length];

                if(sam != null) {
                    addSample(sam, val, samples1);
                }
            }

            samples = samples1[0];

            return new int [] {samples};
        }

    }

    protected abstract double getSmoothing();

    @Override
    protected double getValue() {
        double sumRe;
        double sum2Re;

        if(keepLastXItems) {
            int[] sample_vals = sampleLastX();

            if (reductionFunction == MainWindow.REDUCTION_MAX || reductionFunction == MainWindow.REDUCTION_MIN) {
                return useIterations ? statisticIteration * statistic_intensity : val.getRe() * statistic_intensity;
            }

            if (samples < 1) {
                return 0;
            }

            sumRe = val.getRe();
            sum2Re = val2.getRe();

            if(useAverage) {
                sumRe = sumRe / sample_vals[0];
                sum2Re = sum2Re / sample_vals[1];
            }
        }
        else {
            if (reductionFunction == MainWindow.REDUCTION_MAX || reductionFunction == MainWindow.REDUCTION_MIN) {
                return useIterations ? statisticIteration * statistic_intensity : val.getRe() * statistic_intensity;
            }

            if (samples < 1) {
                return 0;
            }

            sumRe = val.getRe();
            sum2Re = val2.getRe();

            if (useAverage) {
                sumRe = sumRe / samples;
                sum2Re = samples < 2 ? 0 : sum2Re / (samples - 1);
            }
        }


        if(!useSmoothing) {
            return sumRe * statistic_intensity;
        }

        double smoothing = getSmoothing();

        return method.interpolate(sumRe, sum2Re, smoothing) * statistic_intensity;
    }

}
