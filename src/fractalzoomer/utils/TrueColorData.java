package fractalzoomer.utils;

import fractalzoomer.core.Complex;

public class TrueColorData {
    public Complex z;
    public Complex zold;
    public Complex zold2;
    public int iterations;
    public Complex c;
    public Complex start;
    public Complex c0;
    public Complex pixel;
    public double statValue;
    public double trapValue;
    public boolean escaped;
    public double fractionalPart;

    public void setData(Complex zIn, Complex zoldIn, Complex zold2In, int iterationsIn, Complex cIn, Complex startIn, Complex c0In, Complex pixelIn, double statIn, double trapIn, boolean escapedIn, double fractionalPartIn) {
        z = zIn;
        zold = zoldIn;
        zold2 = zold2In;
        iterations = iterationsIn;
        c = cIn;
        start = startIn;
        c0 = c0In;
        pixel = pixelIn;
        statValue = statIn;
        trapValue = trapIn;
        escaped = escapedIn;
        fractionalPart = fractionalPartIn;
    }
}
