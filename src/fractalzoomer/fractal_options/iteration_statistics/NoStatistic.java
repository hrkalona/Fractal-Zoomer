package fractalzoomer.fractal_options.iteration_statistics;

public class NoStatistic extends GenericStatistic {

    public NoStatistic() {
        super(0, false, false, 0);

    }

    @Override
    protected double getValue() {
        return 0;
    }

    @Override
    public int getType() {
        return 0;
    }
}
