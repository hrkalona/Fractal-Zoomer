package fractalzoomer.core.approximation.series_approximation;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.MantExp;
import fractalzoomer.core.numerics.MantExpComplex;
import fractalzoomer.core.numerics.MyApfloat;
import fractalzoomer.core.reference.DeepReference;
import fractalzoomer.core.reference.DoubleReference;
import fractalzoomer.core.reference.ReferenceOrbit;
import fractalzoomer.core.reference.ReferenceType;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

import javax.swing.*;

import static fractalzoomer.main.Constants.SA_CALCULATION_STR;

public class MandelbrotFourthApproximation extends SeriesApproximation {

    @Override
    public void calculateApproximation(Apfloat dsize, boolean deepZoom, Location loc, ReferenceOrbit refOrbit, JProgressBar progress, Fractal f) {
        SAskippedIterations = 0;

        int numCoefficients = TaskRender.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        if (numCoefficients > 5) {
            numCoefficients = 5;
        }

        DeepReference referenceDeep = refOrbit.referenceDeepData.Reference;
        DoubleReference reference = refOrbit.referenceData.Reference;

        SATerms = numCoefficients;

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        SASize = loc.getMaxSizeInImage().log2approx();
        logwToThe[1] = SASize;

        for (int i = 2; i <= numCoefficients; i++) {
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new DeepReference(numCoefficients * max_data, ReferenceType.SA);

        setSACoefficient(0, 0, MantExpComplex.create(1, 0));

        for(int i = 1; i < numCoefficients; i++){
            setSACoefficient(i, 0, MantExpComplex.create());
        }

        long oomDiff = TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE;
        int SAMaxSkipIter = TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER;

        int length = deepZoom ? referenceDeep.length() : reference.length();

        int lastIndex = numCoefficients - 1;

        int i;
        for(i = 1; i < length; i++) {

            if(i - 1 > refOrbit.MaxRefIteration) {
                SAskippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                return;
            }

            MantExpComplex ref = null;

            if(deepZoom) {
                ref = f.getReferenceDeepValue(referenceDeep, i - 1);
            }
            else {
                ref = MantExpComplex.create(f.getReferenceValue(reference, i - 1));
            }

            MantExpComplex fourRefCubed = ref.cube().times4_mutable();

            MantExpComplex refSquared = ref.square();

            int new_i = i;
            int old_i = (i - 1);

            MantExpComplex coef0i = null;
            MantExpComplex coef1i = null;
            MantExpComplex coef2i = null;
            MantExpComplex coef3i = null;
            MantExpComplex coef4i = null;

            MantExpComplex anSquared = null;
            MantExpComplex sixRefSquared = null;
            MantExpComplex twelveRefSquared = null;
            MantExpComplex fourAnCube = null;
            MantExpComplex bnSquared = null;
            MantExpComplex twelveRef = null;
            MantExpComplex twelveRefSquaredAn = null;

            if (numCoefficients >= 1) {
                //An+1 = P * A * (X^(P-1)) + 1
                coef0i = getSACoefficient(0, old_i);
                MantExpComplex temp = coef0i.times(fourRefCubed).plus_mutable(MantExp.ONE); //4*Z^3*a_1 + 1
                temp.Normalize();
                magCoeff[0] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[1]);
                setSACoefficient(0, new_i, temp);
            }
            if (numCoefficients >= 2) {
                //Bn+1 = P * B * (X^(P-1)) + ((P*(P-1))/2) * (A^2) * (X^(P-2))
                coef1i = getSACoefficient(1, old_i);
                anSquared = coef0i.square();
                sixRefSquared = refSquared.times(MantExp.SIX);
                MantExpComplex temp = coef1i.times(fourRefCubed)
                        .plus_mutable(anSquared.times(sixRefSquared)); //6*Z^2*a_1^2 + 4*Z^3*a_2
                temp.Normalize();
                magCoeff[1] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[2]);
                setSACoefficient(1, new_i, temp);
            }
            if (numCoefficients >= 3) {
                //Cn+1 = P * C * (X^(P-1)) + (P*(P-1)) * A * B * (X^(P-2)) + ((P*(P-1)*(P-2))/6) * (A^3) * (X^(P-3))
                coef2i = getSACoefficient(2, old_i);
                twelveRefSquared = refSquared.times(MantExp.TWELVE);
                twelveRefSquaredAn = twelveRefSquared.times(coef0i);
                fourAnCube = coef0i.cube().times4_mutable();
                MantExpComplex temp = coef2i.times(fourRefCubed)
                        .plus_mutable(twelveRefSquaredAn.times(coef1i))
                        .plus_mutable(fourAnCube.times(ref)); //4*Z*a_1^3 + 12*Z^2*a_1*a_2 + 4*Z^3*a_3
                temp.Normalize();
                magCoeff[2] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[3]);
                setSACoefficient(2, new_i, temp);
            }

            if (numCoefficients >= 4) {
                coef3i = getSACoefficient(3, old_i);
                bnSquared = coef1i.square();
                twelveRef = ref.times(MantExp.TWELVE);
                MantExpComplex temp = coef3i.times(fourRefCubed)
                        .plus_mutable(coef0i.fourth())
                        .plus_mutable(anSquared.times(twelveRef).times_mutable(coef1i))
                        .plus_mutable(sixRefSquared.times(bnSquared))
                        .plus_mutable(twelveRefSquaredAn.times(coef2i)); //a_1^4 + 12*Z*a_1^2*a_2 + 6*Z^2*a_2^2 + 12*Z^2*a_1*a_3 + 4*Z^3*a_4
                temp.Normalize();
                magCoeff[3] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[4]);
                setSACoefficient(3, new_i, temp);
            }

            if (numCoefficients >= 5) {
                coef4i = getSACoefficient(4, old_i);
                MantExpComplex temp = coef4i.times(fourRefCubed)
                        .plus_mutable(fourAnCube.times(coef1i))
                        .plus_mutable(bnSquared.times(coef0i).times_mutable(twelveRef))
                        .plus_mutable(anSquared.times(coef2i).times_mutable(twelveRef))
                        .plus_mutable(coef1i.times(coef2i).times_mutable(twelveRefSquared))
                        .plus_mutable(twelveRefSquaredAn.times(coef3i)); //4*a_1^3*a_2 + 12*Z*a_1*a_2^2 + 12*Z*a_1^2*a_3 + 12*Z^2*a_2*a_3 + 12*Z^2*a_1*a_4 + 4*Z^3*a_5
                temp.Normalize();
                magCoeff[4] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[5]);
                setSACoefficient(4, new_i, temp);
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && (i >= SAMaxSkipIter || isLastTermNotNegligible(magCoeff, oomDiff, lastIndex))) {
                //|Bn+1 * d^2 * tolerance| < |Cn+1 * d^3|
                //When we're breaking here, it means that we've found a point where the approximation no longer works. Returning that would create a messed up image. We should move a little further back to get an approximation that is good.
                SAskippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
                return;
            }

            if(progress != null && i % 1000 == 0) {
                progress.setValue(i);
                progress.setString(SA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (i) / progress.getMaximum() * 100)) + "%");
            }

        }

        i = length - 1;
        SAskippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
    }

}
