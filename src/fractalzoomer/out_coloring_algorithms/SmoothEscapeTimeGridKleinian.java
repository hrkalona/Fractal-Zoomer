package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;

public class SmoothEscapeTimeGridKleinian extends OutColorAlgorithm {
    private double u;
    private double log_bailout_squared;
    private double pi2;

    public SmoothEscapeTimeGridKleinian(double u, double log_bailout_squared) {

        super();
        this.u = u * 0.5;
        pi2 = Math.PI * 2;
        this.log_bailout_squared = log_bailout_squared;
        OutNotUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = Math.log(((Complex)object[1]).norm_squared());

        double zabs = temp2 / log_bailout_squared - 1.0f;
        double zarg = (((Complex)object[1]).arg() / (pi2) + 1.0f) % 1.0;

        double k = Math.pow(0.5, 0.5 - zabs);

        double grid_weight = 0.05;

        boolean grid = grid_weight < zabs && zabs < (1.0 - grid_weight) && (grid_weight * k) < zarg && zarg < (1.0 - grid_weight * k);

        double temp = (Integer)object[0] + 1 + Math.log(((Complex)object[1]).sub_i(u).norm());

        return grid ? temp : -(temp + INCREMENT);

    }
}
