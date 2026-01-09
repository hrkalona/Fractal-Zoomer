package fractalzoomer.utils;

import fractalzoomer.core.Complex;

public class OutColorData {
    public int iterations;
    public Complex z;
    public Complex zold;
    public Complex zold2;
    public Complex c;
    public Complex start;
    public Complex c0;
    public Complex pixel;
    public Complex dc;
    public boolean escaped;
    public Complex pendulumLen;

    public void setData(int iterationsIn, Complex zIn, Complex zoldIn, Complex zold2In, Complex cIn, Complex startIn, Complex c0In, Complex pixelIn) {
        iterations = iterationsIn;
        z = zIn;
        zold = zoldIn;
        zold2 = zold2In;
        c = cIn;
        start = startIn;
        c0 = c0In;
        pixel = pixelIn;
        dc = null;
        escaped = false;
        pendulumLen = null;
    }

    //EOC
    public void setData(int iterationsIn, Complex zIn, Complex zoldIn, Complex zold2In, Complex cIn, Complex startIn, Complex c0In, Complex pixelIn, boolean escapedIn) {
        iterations = iterationsIn;
        z = zIn;
        zold = zoldIn;
        zold2 = zold2In;
        c = cIn;
        start = startIn;
        c0 = c0In;
        pixel = pixelIn;
        dc = null;
        escaped = escapedIn;
        pendulumLen = null;
    }

    //Mandelbrot DEM
    public void setData(int iterationsIn, Complex zIn, Complex dcIn) {
        iterations = iterationsIn;
        z = zIn;
        zold = null;
        zold2 = null;
        c = null;
        start = null;
        c0 = null;
        pixel = null;
        dc = dcIn;
        escaped = false;
        pendulumLen = null;
    }

    //Magnetic pendulum
    public void setData(int iterationsIn, Complex zIn, Complex zoldIn, Complex zold2In, Complex cIn, Complex startIn, Complex c0In, Complex pixelIn, Complex pendulumLenIn) {
        iterations = iterationsIn;
        z = zIn;
        zold = zoldIn;
        zold2 = zold2In;
        c = cIn;
        start = startIn;
        c0 = c0In;
        pixel = pixelIn;
        dc = null;
        escaped = false;
        pendulumLen = pendulumLenIn;
    }

}
