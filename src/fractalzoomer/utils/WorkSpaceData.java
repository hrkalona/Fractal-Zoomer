package fractalzoomer.utils;

import fractalzoomer.core.MpfrBigNumComplex;
import fractalzoomer.core.MpirBigNumComplex;
import fractalzoomer.core.NumericLibrary;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.mandelbrot.MandelbarCubed;
import fractalzoomer.functions.mandelbrot.MandelbrotCubed;

public class WorkSpaceData {
    //This is currently implemented for some fractals
    public MpfrBigNum temp1;
    public MpfrBigNum temp2;
    public MpfrBigNum temp3;
    public MpfrBigNum temp4;
    public MpfrBigNum tempPvar;
    public MpfrBigNum tempPvar2;

    public MpfrBigNumComplex z;
    public MpfrBigNumComplex c;
    public MpfrBigNumComplex zold;
    public MpfrBigNumComplex zold2;
    public MpfrBigNumComplex start;
    public MpfrBigNumComplex c0;
    public MpfrBigNumComplex seed;
    public MpfrBigNum root;


    public MpirBigNum temp1p;
    public MpirBigNum temp2p;
    public MpirBigNum temp3p;
    public MpirBigNum temp4p;
    public MpirBigNum tempPvarp;
    public MpirBigNum tempPvar2p;

    public MpirBigNumComplex zp;
    public MpirBigNumComplex cp;
    public MpirBigNumComplex zoldp;
    public MpirBigNumComplex zold2p;
    public MpirBigNumComplex startp;
    public MpirBigNumComplex c0p;
    public MpirBigNumComplex seedp;
    public MpirBigNum rootp;


    public WorkSpaceData(Fractal f) {

        if(NumericLibrary.allocateMPFR(f)) {
            temp1 = new MpfrBigNum();
            temp2 = new MpfrBigNum();
            temp3 = new MpfrBigNum();

            if(f.supportsPeriod() && TaskRender.DETECT_PERIOD) {
                tempPvar = new MpfrBigNum();
                tempPvar2 = new MpfrBigNum();
            }
            if(f instanceof MandelbrotCubed || f instanceof MandelbarCubed) {
                temp4 = new MpfrBigNum();
            }

            if(TaskRender.HIGH_PRECISION_CALCULATION) {
                z = new MpfrBigNumComplex();
                c = new MpfrBigNumComplex();
                zold = new MpfrBigNumComplex();
                zold2 = new MpfrBigNumComplex();
                start = new MpfrBigNumComplex();
                c0 = new MpfrBigNumComplex();
                seed = new MpfrBigNumComplex();
                root = new MpfrBigNum();
            }
        } else if (NumericLibrary.allocateMPIR(f)) {
            temp1p = new MpirBigNum();
            temp2p = new MpirBigNum();
            temp3p = new MpirBigNum();

            if(f.supportsPeriod() && TaskRender.DETECT_PERIOD) {
                tempPvarp = new MpirBigNum();
                tempPvar2p = new MpirBigNum();
            }
            if(f instanceof MandelbrotCubed || f instanceof MandelbarCubed) {
                temp4p = new MpirBigNum();
            }

            if(TaskRender.HIGH_PRECISION_CALCULATION) {
                zp = new MpirBigNumComplex();
                cp = new MpirBigNumComplex();
                zoldp = new MpirBigNumComplex();
                zold2p = new MpirBigNumComplex();
                startp = new MpirBigNumComplex();
                c0p = new MpirBigNumComplex();
                seedp = new MpirBigNumComplex();
                rootp = new MpirBigNum();
            }
        }

    }
}
