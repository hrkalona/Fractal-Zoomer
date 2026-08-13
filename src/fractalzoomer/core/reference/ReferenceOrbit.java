package fractalzoomer.core.reference;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.core.numerics.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import org.apfloat.Apfloat;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class ReferenceOrbit implements Serializable {
    private static final long serialVersionUID = -6872978567595222259L;
    public static int REFERENCE_DATA_COUNT = 4;
    public static final int MAX_PRECALCULATED_TERMS = 25;
    public static final int DATA_LENGTH = (2 + MAX_PRECALCULATED_TERMS); //Ref + RefSubCp + Precalc

    public int MaxRefIteration;
    public Complex dzdc;
    public MantExpComplex mdzdc;
    public Complex compressorZ;
    public MantExpComplex compressorZm;
    public Complex period_dzdc;
    public MantExpComplex period_mdzdc;
    public ReferenceData referenceData;
    public ReferenceDeepData referenceDeepData;
    public String RefType = "";
    public int DetectedPeriod;
    public List<Integer> tinyRefPts;

    public transient GenericComplex lastZValue;
    public transient GenericComplex secondTolastZValue;
    public transient GenericComplex thirdTolastZValue;
    public transient GenericComplex refPoint;
    public transient GenericComplex c;
    public transient Apfloat LastCalculationSize;
    private String lastZValueReString;
    private String lastZValueImString;
    private String CValueReString;
    private String CValueImString;
    private String secondTolastZValueReString;
    private String secondTolastZValueImString;
    private String thirdTolastZValueReString;
    private String thirdTolastZValueImString;
    private String refPointReString;
    private String refPointImString;
    private String LastCalculationSizeString;
    private String highPrecisionTypeName;
    private long precision;
    public boolean isJulia;

    public ReferenceOrbit(int id, int idDeep, boolean julia) {
        referenceData = new ReferenceData(id);
        referenceDeepData = new ReferenceDeepData(idDeep);
        isJulia = julia;
    }

    public void clear() {
        clearWithoutDeallocation();
        referenceData.deallocate();
        referenceDeepData.deallocate();
    }

    public void clearWithoutDeallocation() {
        MaxRefIteration = 0;
        secondTolastZValue = null;
        thirdTolastZValue = null;
        lastZValue = null;
        c = null;
        dzdc = null;
        mdzdc = null;
        period_dzdc = null;
        period_mdzdc = null;
        RefType = "";
        refPoint = null;
        DetectedPeriod = 0;
        tinyRefPts = null;
        compressorZ = null;
        compressorZm = null;
        precision = 0;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {

        lastZValueReString = null;
        lastZValueImString = null;
        CValueReString = null;
        CValueImString = null;
        secondTolastZValueReString = null;
        secondTolastZValueImString = null;
        thirdTolastZValueReString = null;
        thirdTolastZValueImString = null;
        refPointImString = null;
        refPointReString = null;
        LastCalculationSizeString = null;
        if(lastZValue != null) {
            highPrecisionTypeName = getHighPrecisionTypeName(lastZValue);
        }

        if(lastZValue != null) {
            BigComplex bc = lastZValue.toBigComplex();
            lastZValueReString = bc.getRe().toString(true);
            lastZValueImString = bc.getIm().toString(true);
        }
        if(c != null) {
            BigComplex bc = c.toBigComplex();
            CValueReString = bc.getRe().toString(true);
            CValueImString = bc.getIm().toString(true);
        }
        if(secondTolastZValue != null) {
            BigComplex bc = secondTolastZValue.toBigComplex();
            secondTolastZValueReString = bc.getRe().toString(true);
            secondTolastZValueImString = bc.getIm().toString(true);
        }
        if(thirdTolastZValue != null) {
            BigComplex bc = thirdTolastZValue.toBigComplex();
            thirdTolastZValueReString = bc.getRe().toString(true);
            thirdTolastZValueImString = bc.getIm().toString(true);
        }
        if(refPoint != null) {
            BigComplex bc = refPoint.toBigComplex();
            refPointReString = bc.getRe().toString(true);
            refPointImString = bc.getIm().toString(true);
        }
        if(LastCalculationSize != null) {
            LastCalculationSizeString = LastCalculationSize.toString();
        }
        precision = MyApfloat.precision;
        oos.defaultWriteObject();
    }

    public void build(int bigNumLib) {
        if(bigNumLib == Constants.BIGNUM_BUILT_IN) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new BigNumComplex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new BigNumComplex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new BigNumComplex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new BigNumComplex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new BigNumComplex(refPointReString, refPointImString);
            }
        } else if(bigNumLib == Constants.BIGNUM_BIGINT) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new BigIntNumComplex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new BigIntNumComplex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new BigIntNumComplex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new BigIntNumComplex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new BigIntNumComplex(refPointReString, refPointImString);
            }
        } else if(bigNumLib == Constants.BIGNUM_MPFR) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new MpfrBigNumComplex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new MpfrBigNumComplex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new MpfrBigNumComplex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new MpfrBigNumComplex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new MpfrBigNumComplex(refPointReString, refPointImString);
            }
        } else if(bigNumLib == Constants.BIGNUM_MPIR) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new MpirBigNumComplex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new MpirBigNumComplex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new MpirBigNumComplex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new MpirBigNumComplex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new MpirBigNumComplex(refPointReString, refPointImString);
            }
        } else if(bigNumLib == Constants.BIGNUM_DOUBLEDOUBLE) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new DDComplex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new DDComplex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new DDComplex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new DDComplex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new DDComplex(refPointReString, refPointImString);
            }
        } else if(bigNumLib == Constants.BIGNUM_APFLOAT) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new BigComplex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new BigComplex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new BigComplex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new BigComplex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new BigComplex(refPointReString, refPointImString);
            }
        } else if(bigNumLib == Constants.BIGNUM_DOUBLE) {
            if (lastZValueReString != null && lastZValueImString != null) {
                lastZValue = new Complex(lastZValueReString, lastZValueImString);
            }
            if (CValueReString != null && CValueImString != null) {
                c = new Complex(CValueReString, CValueImString);
            }
            if (secondTolastZValueReString != null && secondTolastZValueImString != null) {
                secondTolastZValue = new Complex(secondTolastZValueReString, secondTolastZValueImString);
            }
            if (thirdTolastZValueReString != null && thirdTolastZValueImString != null) {
                thirdTolastZValue = new Complex(thirdTolastZValueReString, thirdTolastZValueImString);
            }
            if (refPointReString != null && refPointImString != null) {
                refPoint = new Complex(refPointReString, refPointImString);
            }
        }

        if(LastCalculationSizeString != null) {
            LastCalculationSize = new MyApfloat(LastCalculationSizeString);
        }

        if(period_mdzdc != null && period_dzdc == null) {
            period_dzdc = period_mdzdc.toComplex();
        }
        if(mdzdc != null && dzdc == null) {
            dzdc = mdzdc.toComplex();
        }
    }

    private String getHighPrecisionTypeName(GenericComplex v) {
        String highPrecisionTypeName = v.getClass().getName();
        int index = highPrecisionTypeName.lastIndexOf(".");
        if(index != -1) {
            highPrecisionTypeName = highPrecisionTypeName.substring(index + 1);
        }
        return highPrecisionTypeName;
    }

    public boolean isValid(GenericComplex newRefPoint, Fractal f, boolean deepZoom) {
        if (!f.getRefType().equals(RefType)) {
            return false;
        }
        if (precision != 0 && precision != MyApfloat.precision) {
            return false;
        }
        if(lastZValue == null || refPoint == null || LastCalculationSize == null) {
            return false;
        }
//        if(!highPrecisionTypeName.equals(getHighPrecisionTypeName(newRefPoint))) {
//            return false;
//        }

        BigComplex newBc = newRefPoint.toBigComplex();
        String newRefpointRe = newBc.getRe().toString(true);
        String newRefpointIm = newBc.getIm().toString(true);
        BigComplex bc = refPoint.toBigComplex();
        String refPointRe = bc.getRe().toString(true);
        String refPointIm = bc.getIm().toString(true);
        boolean reMatches = matches(newRefpointRe, refPointRe);
        boolean imMatches = matches(newRefpointIm, refPointIm);
        if(!reMatches || !imMatches) {
            return false;
        }

        boolean detectPeriod = f.detectPeriod();
        boolean compression = f.useCompressedRef();
        boolean usesCircleBail = f.usesCircleBail();
        boolean gatherTinyRefPts = f.gatherTinyRefPts(deepZoom);
        boolean stopDuringRefCalc = f.stopReferenceCalculationOnDetectedPeriod();

        if(!usesCircleBail && (secondTolastZValue == null || thirdTolastZValue == null)) {
            return false;
        }
        if((deepZoom && (referenceDeepData.Reference == null || referenceData.Reference == null)) || (!deepZoom && referenceData.Reference == null)) {
            return false;
        }
        if(detectPeriod
                && ((deepZoom && (mdzdc == null || period_mdzdc == null))
                 || (!deepZoom && (dzdc == null || period_dzdc == null)))) {
            return false;
        }
        if(detectPeriod && deepZoom && TaskRender.MANTEXPCOMPLEX_FORMAT == 1 && (!(mdzdc instanceof MantExpComplexFull) || !(period_mdzdc instanceof MantExpComplexFull))) {
            return false;
        }
        if(detectPeriod && deepZoom && TaskRender.MANTEXPCOMPLEX_FORMAT == 0 && ((mdzdc instanceof MantExpComplexFull) || (period_mdzdc instanceof MantExpComplexFull))) {
            return false;
        }
        if(!detectPeriod && DetectedPeriod != 0) {
            return false;
        }
        int refLegth = deepZoom ? referenceDeepData.Reference.dataLength() : referenceData.Reference.dataLength();
        if (DetectedPeriod > 0 && (DetectedPeriod >= refLegth || DetectedPeriod > MaxRefIteration)) {
            return false;
        }
        if(gatherTinyRefPts && tinyRefPts == null) {
            return false;
        }
        boolean usesCompression = deepZoom ? referenceDeepData.Reference.compressed : referenceData.Reference.compressed;
        if (compression != usesCompression) {
            return false;
        }
        double usedCompressionError = deepZoom ? referenceDeepData.Reference.getCompressionError() : referenceData.Reference.getCompressionError();
        if (compression && usedCompressionError != ReferenceCompressor.CompressionError) {
            return false;
        }
        if (compression && ((deepZoom && compressorZm == null) || (!deepZoom && compressorZ == null))) {
            return false;
        }
        if(compression && deepZoom && TaskRender.MANTEXPCOMPLEX_FORMAT == 1 && !(compressorZm instanceof MantExpComplexFull)) {
            return false;
        }
        if(compression && deepZoom && TaskRender.MANTEXPCOMPLEX_FORMAT == 0 && (compressorZm instanceof MantExpComplexFull)) {
            return false;
        }
        if(deepZoom && TaskRender.MANTEXPCOMPLEX_FORMAT == 1 && !referenceDeepData.Reference.hasTwoExponents()) {
            return false;
        }
        if(deepZoom && TaskRender.MANTEXPCOMPLEX_FORMAT == 0 && referenceDeepData.Reference.hasTwoExponents()) {
            return false;
        }
        boolean usedSaveMemory = deepZoom ? referenceDeepData.Reference.saveMemory : referenceData.Reference.saveMemory;
        boolean saveMemory = stopDuringRefCalc;
        if (!compression && !saveMemory && usedSaveMemory) {
            return false;
        }
        if (detectPeriod && (stopDuringRefCalc && DetectedPeriod != MaxRefIteration)
                || (!stopDuringRefCalc && DetectedPeriod == MaxRefIteration)) {
            return false;
        }

        if(compression && referenceDeepData.ReferenceSubCp != null) {
            if(referenceDeepData.ReferenceSubCp.getFuction() == null) {
                return false;
            }
            try {
                SerializableFunction<MantExpComplex, MantExpComplex> func = (SerializableFunction<MantExpComplex, MantExpComplex>) referenceDeepData.ReferenceSubCp.getFuction();
            } catch (Exception ex) {
                return false;
            }
        }

        if(compression && referenceData.ReferenceSubCp != null) {
            if(referenceData.ReferenceSubCp.getFuction() == null) {
                return false;
            }
            try {
                SerializableFunction<Complex, Complex> func = (SerializableFunction<Complex, Complex>) referenceData.ReferenceSubCp.getFuction();
            } catch (Exception ex) {
                return false;
            }
        }

        if(compression && referenceData.PrecalculatedTerms != null) {
            for (int i = 0; i < referenceData.PrecalculatedTerms.length; i++) {
                if(referenceData.PrecalculatedTerms[i] != null) {
                    if(referenceData.PrecalculatedTerms[i].getFuction() == null) {
                        return false;
                    }
                    try {
                        SerializableFunction<Complex, Complex> func = (SerializableFunction<Complex, Complex>) referenceData.PrecalculatedTerms[i].getFuction();
                    } catch (Exception ex) {
                        return false;
                    }
                }
            }
        }

        if(compression && referenceDeepData.PrecalculatedTerms != null) {
            for (int i = 0; i < referenceDeepData.PrecalculatedTerms.length; i++) {
                if(referenceDeepData.PrecalculatedTerms[i] != null) {
                    if(referenceDeepData.PrecalculatedTerms[i].getFuction() == null) {
                        return false;
                    }
                    try {
                        SerializableFunction<MantExpComplex, MantExpComplex> func = (SerializableFunction<MantExpComplex, MantExpComplex>) referenceDeepData.PrecalculatedTerms[i].getFuction();
                    } catch (Exception ex) {
                        return false;
                    }
                }
            }
        }

        if (referenceData.Reference != null && (referenceData.Reference.type == null || referenceData.Reference.type == ReferenceType.INVALID)) {
            return false;
        }

        if (referenceDeepData.Reference != null && (referenceDeepData.Reference.type == null || referenceDeepData.Reference.type == ReferenceType.INVALID)) {
            return false;
        }

        return true;
    }

    private boolean matches(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());
        int maxLength = Math.max(str1.length(), str2.length());
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        int count = 0;
        for (int i = 0; i < minLength; i++) {
            if (arr1[i] != arr2[i]) {
                break;
            }
            count++;
        }
        return maxLength - count <= 2 && (double)count / maxLength >= 0.9;
    }

}
