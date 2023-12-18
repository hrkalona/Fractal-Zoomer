package fractalzoomer.core;

public class Waypoint {
    public int iteration;
    public Complex z;
    public MantExpComplex mz;

    public int index;
    public boolean rebase;

    public Waypoint() {

    }

    public Waypoint(int iterationIn, int indexIn) {
        iteration = iterationIn;
        index = indexIn;
    }

    public Waypoint(Complex zIn, int iterationIn, int indexIn) {
        z = zIn;
        iteration = iterationIn;
        index = indexIn;
    }

    public Waypoint(Complex zIn, int iterationIn) {
        z = zIn;
        iteration = iterationIn;
    }

    public Waypoint(Complex zIn, int iterationIn, boolean rebase) {
        z = zIn;
        iteration = iterationIn;
        this.rebase = rebase;
    }

    public Waypoint(MantExpComplex zIn, int iterationIn) {
        mz = zIn;
        iteration = iterationIn;
    }

    public Waypoint(MantExpComplex zIn, int iterationIn, boolean rebase) {
        mz = zIn;
        iteration = iterationIn;
        this.rebase = rebase;
    }

    public Waypoint(MantExpComplex zIn, int iterationIn, int indexIn) {
        mz = zIn;
        iteration = iterationIn;
        index = indexIn;
    }
}
