package fractalzoomer.utils;

import fractalzoomer.core.Complex;

public class InColorData {
    public Complex z;
    public Complex zold;
    public Complex zold2;
    public Complex c;
    public Complex start;
    public Complex c0;
    public Complex pixel;

    public void setData(Complex zIn, Complex zoldIn, Complex zold2In, Complex cIn, Complex startIn, Complex c0In, Complex pixelIn) {
        z = zIn;
        zold = zoldIn;
        zold2 = zold2In;
        c = cIn;
        start = startIn;
        c0 = c0In;
        pixel = pixelIn;
    }
}
