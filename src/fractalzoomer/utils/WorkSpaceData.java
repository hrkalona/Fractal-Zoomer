package fractalzoomer.utils;

import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.mandelbrot.MandelbrotCubed;;

public class WorkSpaceData {
    //This is currently implemented for some fractals
    public MpfrBigNum temp1;
    public MpfrBigNum temp2;
    public MpfrBigNum temp3;
    public MpfrBigNum temp4;

    public MpfrBigNumComplex z;
    public MpfrBigNumComplex c;
    public MpfrBigNumComplex zold;
    public MpfrBigNumComplex zold2;
    public MpfrBigNumComplex start;
    public MpfrBigNumComplex c0;
    public MpfrBigNumComplex seed;
    public MpfrBigNum root;


    public WorkSpaceData(Fractal f) {

        if(LibMpfr.LOAD_ERROR == null) {
            temp1 = new MpfrBigNum();
            temp2 = new MpfrBigNum();
            if(f instanceof MandelbrotCubed) {
                temp3 = new MpfrBigNum();
                temp4 = new MpfrBigNum();
            }

            if(ThreadDraw.HIGH_PRECISION_CALCULATION) {
                z = new MpfrBigNumComplex();
                c = new MpfrBigNumComplex();
                zold = new MpfrBigNumComplex();
                zold2 = new MpfrBigNumComplex();
                start = new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex();
                seed = new MpfrBigNumComplex();
                root = new MpfrBigNum();
            }
        }
    }
}
