package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;

public class LAstep {
    public int step;
    public int nextStageLAindex;
    protected GenericLAInfo LAj;
    protected Complex Refp1;
    protected MantExpComplex Refp1Deep;

    public Complex newDz;
    public MantExpComplex newDzDeep;
    public boolean unusable;

    public double newdre;
    public double newdim;

    public double outdre;
    public double outdim;

    public Complex Evaluate(Complex DeltaSub0) {
        return LAj.Evaluate(newDz, DeltaSub0);
    }

    public Complex EvaluateWithZeroD0() {
        return LAj.Evaluate(newDz);
    }

//    public Complex EvaluateWithZeroD0Fast() {
//        return LAj.Evaluate(newdre, newdim);
//    }

    public void EvaluateWithZeroD0Fast() {
        LAInfoBase LAjb = ((LAInfoBase)LAj);
        double ZCoeffRe = LAjb.ZCoeffRe;
        double ZCoeffIm = LAjb.ZCoeffIm;
        outdre = newdre * ZCoeffRe - newdim * ZCoeffIm;
        outdim = newdre * ZCoeffIm + newdim * ZCoeffRe;
    }

//    public Complex Evaluate(double d0re, double d0im) {
//        return LAj.Evaluate(newdre, newdim, d0re, d0im);
//    }

    public void Evaluate(double d0re, double d0im) {
        LAInfoBase LAjb = ((LAInfoBase)LAj);
        double ZCoeffRe = LAjb.ZCoeffRe;
        double ZCoeffIm = LAjb.ZCoeffIm;
        double CCoeffRe = LAjb.CCoeffRe;
        double CCoeffIm = LAjb.CCoeffIm;
        outdre = newdre * ZCoeffRe - newdim * ZCoeffIm + d0re * CCoeffRe - d0im * CCoeffIm;
        outdim = newdre * ZCoeffIm + newdim * ZCoeffRe + d0re * CCoeffIm + d0im * CCoeffRe;
    }

    public Complex getZ(Complex DeltaSubN) { return Refp1.plus(DeltaSubN);}

    public Complex getRefp1() {
        return Refp1;
    }

    public MantExpComplex Evaluate(MantExpComplex DeltaSub0) {
        return LAj.Evaluate(newDzDeep, DeltaSub0);
    }

    public MantExpComplex EvaluateDzdc(MantExpComplex z, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc(z, dzdc);
    }

    public MantExpComplex EvaluateDzdc2(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc2(z, dzdc2, dzdc);
    }

    public MantExpComplex EvaluateDzdcDeep(MantExpComplex z, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc(z, dzdc);
    }

    public MantExpComplex EvaluateDzdc2Deep(MantExpComplex z, MantExpComplex dzdc2, MantExpComplex dzdc) {
        return LAj.EvaluateDzdc2(z, dzdc2, dzdc);
    }

    public MantExpComplex getZ(MantExpComplex DeltaSubN) { return Refp1Deep.plus(DeltaSubN);}

}
