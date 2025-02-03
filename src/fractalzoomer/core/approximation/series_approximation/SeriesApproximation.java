package fractalzoomer.core.approximation.series_approximation;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.MantExpComplex;
import fractalzoomer.core.numerics.MantExpComplexFull;
import fractalzoomer.core.reference.DeepReference;
import fractalzoomer.core.reference.ReferenceOrbit;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

import javax.swing.*;

public abstract class SeriesApproximation {
    public int SAskippedIterations;
    public int SATerms;
    public long SASize;
    protected static final int skippedThreshold = 6;
    protected static final int max_data = skippedThreshold * 2;
    public static DeepReference coefficients;

    public abstract void calculateApproximation(Apfloat dsize, boolean deepZoom, Location loc, ReferenceOrbit refOrbit, JProgressBar progress, Fractal f);

    public MantExpComplex getSACoefficient(int term, int i) {

        int dataIndex = i % max_data;
        int index = dataIndex * SATerms + term;

        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new MantExpComplexFull(coefficients.exps[index], coefficients.expsIm[index], coefficients.mantsRe[index], coefficients.mantsIm[index]);
        }
        return new MantExpComplex(coefficients.exps[index], coefficients.mantsRe[index], coefficients.mantsIm[index]);

    }

    protected void setSACoefficient(int term, int i, MantExpComplex val) {

        int dataIndex = i % max_data;
        int index = dataIndex * SATerms + term;

        if(coefficients.saveMemory) {
            coefficients.checkAllocation(index);
        }

        coefficients.exps[index] = val.getExp();
        coefficients.mantsRe[index] = val.getMantissaReal();
        coefficients.mantsIm[index] = val.getMantissaImag();

        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            coefficients.expsIm[index] = val.getExpImag();
        }

    }

    protected long calculateSAmagnitude(long clog, long logwToThe) {

        if(clog == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        return clog + logwToThe;

    }

    /*protected boolean isLastTermNotNegligible(MantExpComplex[][] coefs, MantExpComplex[] delta, MantExp limit, int i, int terms) {
        int lastIndex = terms - 1;
        MantExp magLast = coefs[lastIndex][i].norm_squared().multiply_mutable(limit);

        for(int k=0; k<lastIndex; k++) {
            //|Ak*d^k| * THRESHOLD < |Am*d^m|
            //|Ak*d^k|^2 * THRESHOLD^2 < |Am*d^m|^2
            //|Ak*d^k|^2 < |Am*d^m|^2 * 1/THRESHOLD^2
            //|Ak*d^k|^2 < |Am|^2 * (|d^m| * 1/THRESHOLD^2)
            //|Ak*d^k|^2 < |Am|^2 * limit
            if (coefs[k][i].times(delta[k + 1]).norm_squared().compareTo(magLast) < 0) return true;
        }
        return false;
    }*/

    protected boolean isLastTermNotNegligible(long[] magCoeff, long magDiffThreshold, int lastIndex) {
        long magLast = magCoeff[lastIndex];

        if(magLast == Long.MIN_VALUE) {
            return false;
        }

        for (int k = 0; k < lastIndex; k++) {
            if(magCoeff[k] == Long.MIN_VALUE) {
                return false;
            }

            if (magCoeff[k] - magLast < magDiffThreshold) return true;
        }
        return false;
    }
}
