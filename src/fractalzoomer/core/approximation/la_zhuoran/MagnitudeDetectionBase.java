package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.reference.ReferenceDecompressor;
import fractalzoomer.functions.Fractal;

public abstract class MagnitudeDetectionBase {
    public static MagnitudeDetectionBase create(boolean deepZoom, int index, ReferenceDecompressor referenceDecompressor) {
        if(deepZoom) {
            MantExpComplex v = LAReference.f.getArrayDeepValue(referenceDecompressor, Fractal.referenceDeep, index);
            return new MagnitudeDetectionDeep(v.chebyshevNorm());
        } else {
            Complex v = LAReference.f.getArrayValue(referenceDecompressor, Fractal.reference, index);
            return new MagnitudeDetection(v.chebyshevNorm());
        }
    }

    public static MagnitudeDetectionBase create(GenericComplex c) {
        if(c instanceof MantExpComplex) {
            return new MagnitudeDetectionDeep(((MantExpComplex)c).chebyshevNorm());
        } else {
            return new MagnitudeDetection(((Complex)c).chebyshevNorm());
        }
    }
    public abstract boolean lessThan(MagnitudeDetectionBase other);
    public abstract boolean lessThanWithThresholdStage0(MagnitudeDetectionBase other);
    public abstract boolean lessThanWithThreshold(MagnitudeDetectionBase other);

    public static MagnitudeDetectionBase getThreshold(MagnitudeDetectionBase prevMinMagnitude, MagnitudeDetectionBase minMagnitude) {
        if(prevMinMagnitude instanceof MagnitudeDetection) {
            return new MagnitudeDetection(((MagnitudeDetection)prevMinMagnitude).magnitude * Math.sqrt(((MagnitudeDetection)minMagnitude).magnitude / ((MagnitudeDetection)prevMinMagnitude).magnitude));
        } else {
            return new MagnitudeDetectionDeep(((MagnitudeDetectionDeep)prevMinMagnitude).magnitude.multiply(((MagnitudeDetectionDeep)minMagnitude).magnitude.divide(((MagnitudeDetectionDeep)prevMinMagnitude).magnitude).sqrt()));
        }
    }
}
