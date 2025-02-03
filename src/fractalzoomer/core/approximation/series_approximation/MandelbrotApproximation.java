package fractalzoomer.core.approximation.series_approximation;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.numerics.MantExpComplex;
import fractalzoomer.core.numerics.MyApfloat;
import fractalzoomer.core.reference.DeepReference;
import fractalzoomer.core.reference.DoubleReference;
import fractalzoomer.core.reference.ReferenceOrbit;
import fractalzoomer.core.reference.ReferenceType;
import fractalzoomer.functions.Fractal;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.stream.IntStream;

import static fractalzoomer.main.Constants.SA_CALCULATION_STR;

public class MandelbrotApproximation extends SeriesApproximation {

    @Override
    public void calculateApproximation(Apfloat dsize, boolean deepZoom, Location loc, ReferenceOrbit refOrbit, JProgressBar progress, Fractal f) {
        SAskippedIterations = 0;

        int numCoefficients = TaskRender.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        DeepReference referenceDeep = refOrbit.referenceDeepData.Reference;
        DoubleReference reference = refOrbit.referenceData.Reference;

        SATerms = numCoefficients;

        /*MantExpComplex[] DeltaSub0ToThe = new MantExpComplex[numCoefficients + 1];*/

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        /*if(deepZoom) {
            DeltaSub0ToThe[1] = new MantExpComplex(dsizeMantExp, dsizeMantExp);
        }
        else {
            //DeltaSub0ToThe[1] = new MantExpComplex(sqrt2 * size, sqrt2 * size);
        }*/

        SASize = loc.getMaxSizeInImage().log2approx();
        logwToThe[1] = SASize;

        for (int i = 2; i <= numCoefficients; i++) {
            //DeltaSub0ToThe[i] = DeltaSub0ToThe[i - 1].times(DeltaSub0ToThe[1]);
            //DeltaSub0ToThe[i].Reduce();
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new DeepReference(numCoefficients * max_data, ReferenceType.SA);

        setSACoefficient(0, 0, MantExpComplex.create(1, 0));

        for(int i = 1; i < numCoefficients; i++){
            setSACoefficient(i, 0, MantExpComplex.create());
        }

        //MantExp limit = DeltaSub0ToThe[numCoefficients].norm_squared().multiply_mutable(new MantExp(MyApfloat.reciprocal(ThreadDraw.SERIES_APPROXIMATION_TOLERANCE.multiply(ThreadDraw.SERIES_APPROXIMATION_TOLERANCE))));

        long oomDiff = TaskRender.SERIES_APPROXIMATION_OOM_DIFFERENCE;
        int SAMaxSkipIter = TaskRender.SERIES_APPROXIMATION_MAX_SKIP_ITER;

        //int length = max_iterations;
        //int dataLength = deepZoom ? ReferenceDeep.length() : (Reference.length >> 1);
        int length = deepZoom ? referenceDeep.length() : reference.length();

        //int batches = 8;
        //int batchSize = numCoefficients / batches;
        //int leftOvers = numCoefficients % batches;
        //int batchLooplastIndex = batchSize * batches - 1;
        int lastIndex = numCoefficients - 1;
        //boolean doExtra = batchSize % 2 == 1;
        //int batchLoopLength = batchSize >> 1;
        boolean useThreads = TaskRender.USE_THREADS_FOR_SA;//numCoefficients > 32;


        int i;
        //int circleIndex = 0;
        for(i = 1; i < length; i++) {

            /*
            int index;
            if(i - 1 > MaxRefIteration) {
                index = circleIndex % dataLength + 1;
                circleIndex++;
                circleIndex = circleIndex % (dataLength - 1);

            }
            else {
                index = (i - 1);
            }*/

            if(i - 1 > refOrbit.MaxRefIteration) {
                SAskippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                return;
            }

            MantExpComplex twoRef = null;

            if(deepZoom) {
                twoRef = f.getReferenceDeepValue(referenceDeep, i - 1).times2_mutable();
            }
            else {
                twoRef = MantExpComplex.create(f.getReferenceValue(reference, i - 1).times2_mutable());
            }

            //MantExpComplex twoAn = null;

            int new_i = i;
            int old_i = (i - 1);

            /*MantExpComplex coef0i = null;
            MantExpComplex coef1i = null;
            MantExpComplex coef2i = null;
            MantExpComplex coef3i = null;
            MantExpComplex coef4i = null;

            if (numCoefficients >= 1) {
                //A
                coef0i = getSACoefficient(0, old_i);
                MantExpComplex temp = coef0i.times(twoRef).plus_mutable(MantExp.ONE); // An+1 = 2XnAn + 1
                temp.Reduce();
                magCoeff[0] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[1]);
                setSACoefficient(0, new_i, temp);
            }
            if (numCoefficients >= 2) {
                //B
                coef1i = getSACoefficient(1, old_i);
                MantExpComplex temp = coef1i.times(twoRef).plus_mutable(coef0i.square()); // Bn+1 = 2XnBn + An^2
                temp.Reduce();
                magCoeff[1] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[2]);
                setSACoefficient(1, new_i, temp);
            }
            if (numCoefficients >= 3) {
                //C
                coef2i = getSACoefficient(2, old_i);
                twoAn = coef0i.times2();
                MantExpComplex temp = coef2i.times(twoRef).plus_mutable(coef1i.times(twoAn)); // Cn+1 = 2XnCn + 2AnBn
                temp.Reduce();
                magCoeff[2] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[3]);
                setSACoefficient(2, new_i, temp);
            }
            if (numCoefficients >= 4) {
                //D
                coef3i = getSACoefficient(3, old_i);
                MantExpComplex temp = coef3i.times(twoRef).plus_mutable(twoAn.times(coef2i)).plus_mutable(coef1i.square()); //Dn+1 = 2XnCn + 2AnCn + Bn^2
                temp.Reduce();
                magCoeff[3] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[4]);
                setSACoefficient(3, new_i, temp);
            }
            if (numCoefficients >= 5) {
                //E
                coef4i = getSACoefficient(4, old_i);
                MantExpComplex temp = coef4i.times(twoRef).plus_mutable(twoAn.times(coef3i)).plus_mutable(coef1i.times(coef2i).times2_mutable()); //En+1 = 2XnEn + 2AnDn + 2BnCn
                temp.Reduce();
                magCoeff[4] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[5]);
                setSACoefficient(4, new_i, temp);
            }*/

            //if(numCoefficients >= 6) {
            /*//k = 5
            for(int k = 0; k < numCoefficients; k++) {
                MantExpComplex sum = k == 0 ? MantExpComplex.create(1, 0) : MantExpComplex.create();

                int calcLength = (k >> 1);

                int j = 0;

                if(calcLength != 0) {
                    for (; j < calcLength; j++) {
                        sum = sum.plus_mutable(getSACoefficient(j, old_i).times(getSACoefficient(k - j - 1, old_i)));
                    }

                    sum = sum.times2_mutable();
                }

                if(k % 2 == 1) {
                    sum = sum.plus_mutable(getSACoefficient(j, old_i).square());
                }

                MantExpComplex temp = getSACoefficient(k, old_i).times(twoRef).plus_mutable(sum);

                temp.Reduce();
                magCoeff[k] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[k + 1]);
                setSACoefficient(k, new_i, temp);
            }*/


            final MantExpComplex twoRefFinal = twoRef;

            if(useThreads) {
                // if(batchSize != 0) {
//                    IntStream.range(0, batches).parallel().forEach(b -> {
//
//                        int offset = b * batchSize;
//                        for (int m = 0, k = offset; m < batchSize; m++, k++) { //Split in segments
//                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
//                        }
//////                        for (int m = 0, k = b; m < batchSize; m++, k += batches) { // Get one every multiple of batch + b
//////                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
//////                        }
////
////                        int k = b;
////                        for (int m = 0; m < batchLoopLength; m++, k += batches) { // Get first-last gauss sum style
////                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
////                            calcCoeffs(batchLooplastIndex - k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
////                        }
////                        if(doExtra) {
////                            calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
////                        }
//                    });


                //}

//                int offset = batches * batchSize;
//                for(int m = 0, k = offset; m < leftOvers; m++, k++) {
//                    calcCoeffs(k, old_i, new_i, twoRefFinal, magCoeff, logwToThe);
//                }

                IntStream.range(0, numCoefficients)
                        .parallel().forEach(k ->
                                calcCoeffs(lastIndex - k, old_i, new_i, twoRefFinal, magCoeff, logwToThe)
                        );

            }
            else {
                for(int k = 0; k < numCoefficients; k++) {
                    calcCoeffs(k, old_i, new_i, twoRef, magCoeff, logwToThe);
                }
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && (i >= SAMaxSkipIter || isLastTermNotNegligible(magCoeff, oomDiff, lastIndex))) {
                //if(i > 1 && isLastTermNotNegligible(coefficients, DeltaSub0ToThe, limit, new_i, numCoefficients)) {
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

    private void calcCoeffs(int k, int old_i, int new_i, MantExpComplex twoRef, long[] magCoeff, long[] logwToThe) {
        MantExpComplex sum = k == 0 ? MantExpComplex.create(1, 0) : MantExpComplex.create();

        int calcLength = (k >> 1);

        int j = 0;

        int tempK1 = k - 1;
        if (calcLength != 0) {
            for (; j < calcLength; j++) {
                sum = sum.plus_mutable(getSACoefficient(j, old_i).times_mutable(getSACoefficient(tempK1 - j, old_i)));
            }

            sum = sum.times2_mutable();
        }

        if (k % 2 == 1) {
            sum = sum.plus_mutable(getSACoefficient(j, old_i).square_mutable());
        }

        MantExpComplex temp = getSACoefficient(k, old_i).times_mutable(twoRef).plus_mutable(sum);

        temp.Normalize();
        magCoeff[k] = calculateSAmagnitude(temp.log2normApprox(), logwToThe[k + 1]);
        setSACoefficient(k, new_i, temp);
    }
}
