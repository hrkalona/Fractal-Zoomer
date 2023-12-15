package fractalzoomer.core.la;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class ATInfo {
    private static final MantExp factor = new MantExp(0x1.0p32);
    private static final double ESCAPE_RADIUS = 4;

    protected int StepLength;
    public MantExp ThresholdC;
    public double SqrEscapeRadius;
    public MantExpComplex RefC;
    public MantExpComplex ZCoeff, CCoeff, InvZCoeff;
    public MantExpComplex CCoeffSqrInvZCoeff;
    public MantExpComplex CCoeffInvZCoeff;

    protected boolean Usable(MantExp SqrRadius) {
       return CCoeff.norm_squared().multiply(SqrRadius).multiply_mutable(factor)
                .compareToBothPositive(RefC.norm_squared()) > 0 && SqrEscapeRadius > ESCAPE_RADIUS;
    }

    public boolean isValid(Complex DeltaSub0) {
        return DeltaSub0.chebychevNorm() <= ThresholdC.toDouble();
    }

    public boolean isValid(MantExpComplex DeltaSub0) {
        return DeltaSub0.chebychevNorm().compareToBothPositiveReduced(ThresholdC) <= 0;
    }

    public Complex getC(Complex dc) {
        MantExpComplex temp = MantExpComplex.create(dc).times(CCoeff).plus_mutable(RefC);
        temp.Normalize();
        return temp.toComplex();
    }

    public MantExpComplex getDZ(Complex z) {
        MantExpComplex temp = MantExpComplex.create(z).times(InvZCoeff);
        temp.Normalize();
        return temp;
    }

    public MantExpComplex getDZDC(Complex dzdc) {
        MantExpComplex temp = MantExpComplex.create(dzdc).times(CCoeffInvZCoeff);
        temp.Normalize();
        return temp;
    }

    public MantExpComplex getDZDC2(Complex dzdc2) {
        MantExpComplex temp = MantExpComplex.create(dzdc2).times(CCoeffSqrInvZCoeff);
        temp.Normalize();
        return temp;
    }

    public Complex getC(MantExpComplex dc) {
        MantExpComplex temp = dc.times(CCoeff).plus_mutable(RefC);
        temp.Normalize();
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

        double norm_squared = 0;
        double zre_sqr = 0;
        double zim_sqr = 0;
        double zre = 0;
        double zim = 0;
        double cre = c.getRe();
        double cim = c.getIm();
        double temp;

        int i;
        for(i = 0; i < ATMaxIt; i++) {

            //zre = z.getRe();
            zre_sqr = zre * zre;
            //zim = z.getIm();
            zim_sqr = zim * zim;
            norm_squared = zre_sqr + zim_sqr;

            if(norm_squared > SqrEscapeRadius) {
                break;
            }

            if(derivatives > 1) {
                dzdc2 = dzdc2.times(z).plus_mutable(dzdc.square()).times2_mutable();
            }
            if(derivatives > 0) {
                dzdc = dzdc.times2().times_mutable(z).plus_mutable(1);
            }

            //z = z.square_mutable_plus_c_mutable(c, zre_sqr, zim_sqr, norm_squared);

            temp = zre + zim;
            zre = zre_sqr - zim_sqr + cre;
            zim = temp * temp - norm_squared + cim;
            z.assign(zre, zim);
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
