package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class ATInfo {
    private static final MantExp factor = new MantExp(0x1.0p32);

    protected int StepLength;
    protected MantExp ThresholdC;
    protected double SqrEscapeRadius;
    protected MantExpComplex RefC;
    protected MantExpComplex ZCoeff, CCoeff, InvZCoeff;
    protected MantExpComplex CCoeffSqrInvZCoeff;
    protected MantExpComplex CCoeffInvZCoeff;

    protected MantExp CCoeffNormSqr;
    protected MantExp RefCNormSqr;

    protected boolean Usable(MantExp SqrRadius) {
       return CCoeffNormSqr.multiply(SqrRadius).multiply_mutable(factor)
                .compareToBothPositive(RefCNormSqr) > 0 && SqrEscapeRadius > 4.0;
    }

    public boolean isValid(Complex DeltaSub0) {
        return DeltaSub0.chebychevNorm() <= ThresholdC.toDouble();
    }

    public boolean isValid(MantExpComplex DeltaSub0) {
        return DeltaSub0.chebychevNorm().compareToBothPositiveReduced(ThresholdC) <= 0;
    }

    public Complex getC(Complex dc) {
        MantExpComplex temp = new MantExpComplex(dc).times(CCoeff).plus_mutable(RefC);
        temp.Reduce();
        return temp.toComplex();
    }

    public MantExpComplex getDZ(Complex z) {
        MantExpComplex temp = new MantExpComplex(z).times(InvZCoeff);
        temp.Reduce();
        return temp;
    }

    public MantExpComplex getDZDC(Complex dzdc) {
        MantExpComplex temp = new MantExpComplex(dzdc).times(CCoeffInvZCoeff);
        temp.Reduce();
        return temp;
    }

    public MantExpComplex getDZDC2(Complex dzdc2) {
        MantExpComplex temp = new MantExpComplex(dzdc2).times(CCoeffSqrInvZCoeff);
        temp.Reduce();
        return temp;
    }

    public Complex getC(MantExpComplex dc) {
        MantExpComplex temp = dc.times(CCoeff).plus_mutable(RefC);
        temp.Reduce();
        return temp.toComplex();
    }

    public ATResult PerformAT(int max_iterations, GenericComplex DeltaSub0, int derivatives) {
        //int ATMaxIt = (max_iterations - 1) / StepLength + 1;
        int ATMaxIt = max_iterations / StepLength;

        Complex c;
        if(DeltaSub0 instanceof Complex) {
            c = getC((Complex) DeltaSub0);
        }
        else {
            c = getC((MantExpComplex) DeltaSub0);
        }

        Complex z = new Complex();
        Complex dzdc = new Complex();
        Complex dzdc2 = new Complex();

        int i;
        for(i = 0; i < ATMaxIt; i++) {

            if(z.norm_squared() > SqrEscapeRadius) {
                break;
            }

            if(derivatives > 1) {
                dzdc2 = dzdc2.times(z).plus_mutable(dzdc.square()).times2_mutable();
            }
            if(derivatives > 0) {
                dzdc = dzdc.times2().times_mutable(z).plus_mutable(1);
            }

            z = z.square_mutable_plus_c_mutable(c);
        }

        ATResult res = new ATResult();
        res.dz = getDZ(z);

        if(derivatives > 1) {
            res.dzdc2 = getDZDC2(dzdc2);
        }

        if(derivatives > 0) {
            res.dzdc = getDZDC(dzdc);
        }

        res.bla_iterations = i * StepLength;
        res.bla_steps = i;

        return res;
    }
}
