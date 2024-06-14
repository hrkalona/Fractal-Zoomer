package fractalzoomer.core;

import fractalzoomer.functions.Fractal;

import java.util.function.Function;

public class ReferenceCompressor {
    public static double CompressionError = Math.pow(10, -9);
    private static double CompressionErrorInverted = 1 / CompressionError;
    private static MantExp CompressionErrorInvertedm = new MantExp(CompressionErrorInverted);


    private static double limit1 = 0x1.0p-4;
    private static MantExp limit1m = new MantExp(limit1);

    private static double limit2 = 0x1.000001p0;
    private static MantExp limit2m = new MantExp(limit2);

    public static void setCompressionError() {
        CompressionErrorInverted = 1 / CompressionError;
        CompressionErrorInvertedm = new MantExp(CompressionErrorInverted);
    }
    private Complex z;
    private Complex c;

    private Complex initVal;

    private MantExpComplex mz;
    private MantExpComplex mc;

    private MantExpComplex initValm;

    private Function<Complex, Complex> function;
    private Function<MantExpComplex, MantExpComplex> functionm;

    private Fractal f;

    public ReferenceCompressor( Fractal f, Complex z, Complex c, Complex initVal) {
        this.z = z;
        this.c = c;
        this.f = f;
        this.initVal = initVal;
    }

    public ReferenceCompressor( Fractal f, MantExpComplex mz, MantExpComplex mc, MantExpComplex initValm) {
        this.f = f;
        this.mz = mz;
        this.mc = mc;
        this.initValm = initValm;
    }

    public ReferenceCompressor(Function<Complex, Complex> function) {
        this.function = function;
    }

    public ReferenceCompressor(Function<MantExpComplex, MantExpComplex> function, boolean deep) {
        this.functionm = function;
    }

    //maxRefIteration should be +1
    public static CompressedDeepReference compressReferenceWithExpression(CompressedDeepReference reference, DeepReference expressionReference, Function<MantExpComplex, MantExpComplex> function, Fractal f, MantExpComplex z, MantExpComplex c, int maxRefIteration) {

        if(expressionReference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        if(reference.length != expressionReference.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        CompressedDeepReference cref = new CompressedDeepReference(reference.length, reference.lengthOverride);

        MantExpComplex CorrectVal, CalculatedVal;

        //Do the compression

        int length = Math.min(maxRefIteration, reference.length - 1);
        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;


        int compressed_index = 0;
        for (int i = 0; i <= length; i++) {
            if(reference.useWaypoint(compressed_index, i)) {
                z = reference.getWaypointData(compressed_index);
                compressed_index++;
            }
            else if (i > 0) {
                z = f.function(z, c);
            }

            if (twoExponents) {
                CorrectVal = new MantExpComplexFull(expressionReference.exps[i], expressionReference.expsIm[i], expressionReference.mantsRe[i], expressionReference.mantsIm[i]);
            } else {
                CorrectVal = new MantExpComplex(expressionReference.exps[i], expressionReference.mantsRe[i], expressionReference.mantsIm[i]);
            }
            CalculatedVal = function.apply(z);
            CalculatedVal.Normalize();

            if (CalculatedVal.sub(CorrectVal).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(CorrectVal.chebyshevNorm()) > 0) {
                cref.addWaypoint(new Waypoint(MantExpComplex.copy(CorrectVal), i));
            }

        }

        cref.compact();

        return cref;

    }

    @Deprecated
    public static CompressedDeepReference compressReferenceWithExpression(DeepReference reference, DeepReference expressionReference, Function<MantExpComplex, MantExpComplex> function, int maxRefIteration) {

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        if(expressionReference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        if(reference.length != expressionReference.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        CompressedDeepReference cref = new CompressedDeepReference(reference.length, reference.lengthOverride);

        MantExpComplex CorrectVal, CalculatedVal;

        //Do the compression

        int length = Math.min(maxRefIteration, reference.length - 1);
        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        for (int i = 0; i <= length; i++) {

            if (twoExponents) {
                CorrectVal = new MantExpComplexFull(expressionReference.exps[i], expressionReference.expsIm[i], expressionReference.mantsRe[i], expressionReference.mantsIm[i]);
                CalculatedVal = function.apply(new MantExpComplexFull(reference.exps[i], reference.expsIm[i], reference.mantsRe[i], reference.mantsIm[i]));
            } else {
                CorrectVal = new MantExpComplex(expressionReference.exps[i], expressionReference.mantsRe[i], expressionReference.mantsIm[i]);
                CalculatedVal = function.apply(new MantExpComplex(reference.exps[i], reference.mantsRe[i], reference.mantsIm[i]));
            }

            CalculatedVal.Normalize();

            if (CalculatedVal.sub(CorrectVal).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(CorrectVal.chebyshevNorm()) > 0) {
                cref.addWaypoint(new Waypoint(MantExpComplex.copy(CorrectVal), i));
            }

        }

        cref.compact();

        return cref;

    }

    //maxRefIteration should be +1
    public static CompressedDoubleReference compressReferenceWithExpression(CompressedDoubleReference reference, DoubleReference expressionReference, Function<Complex, Complex> function, Fractal f, Complex z, Complex c, int maxRefIteration) {

        if(expressionReference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        if(reference.length != expressionReference.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        CompressedDoubleReference cref = new CompressedDoubleReference(reference.length, reference.lengthOverride);

        Complex CorrectVal, CalculatedVal;

        //Do the compression

        int length = Math.min(maxRefIteration, reference.length - 1);



        int compressed_index = 0;
        for (int i = 0; i <= length; i++) {
            if(reference.useWaypoint(compressed_index, i)) {
                z = reference.getWaypointData(compressed_index);
                compressed_index++;
            }
            else if (i > 0){
                z = f.function(z, c);
            }

            CorrectVal = new Complex(expressionReference.re[i], expressionReference.im[i]);
            CalculatedVal = function.apply(z);

            if (CalculatedVal.sub(CorrectVal).chebyshevNorm() * CompressionErrorInverted > CorrectVal.chebyshevNorm()) {
                cref.addWaypoint(new Waypoint(new Complex(CorrectVal), i));
            }

        }

        cref.compact();

        return cref;

    }

    //maxRefIteration should be +1
    @Deprecated
    public static CompressedDoubleReference compressReferenceWithExpression(DoubleReference reference, DoubleReference expressionReference, Function<Complex, Complex> function, int maxRefIteration) {

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        if(expressionReference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        if(reference.length != expressionReference.length) {
            throw new UnsupportedOperationException("The references must have the same length.");
        }

        CompressedDoubleReference cref = new CompressedDoubleReference(reference.length, reference.lengthOverride);

        Complex CorrectVal, CalculatedVal;

        //Do the compression

        int length = Math.min(maxRefIteration, reference.length - 1);

        for (int i = 0; i <= length; i++) {
            CorrectVal = new Complex(expressionReference.re[i], expressionReference.im[i]);
            CalculatedVal = function.apply(new Complex(reference.re[i], reference.im[i]));

            if (CalculatedVal.sub(CorrectVal).chebyshevNorm() * CompressionErrorInverted > CorrectVal.chebyshevNorm()) {
                cref.addWaypoint(new Waypoint(new Complex(CorrectVal), i));
            }

        }

        cref.compact();

        return cref;

    }


    //maxRefIteration should be +1
    public static CompressedDoubleReference compressReferenceSimple(Fractal f, DoubleReference reference, Complex z, Complex c, int maxRefIteration) {

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        CompressedDoubleReference cref = new CompressedDoubleReference(reference.length, reference.lengthOverride);

        Complex Z;

        int length = Math.min(maxRefIteration, reference.length - 1);

        //Do the compression

        for (int i = 0; i <= length; i++) {
            Z = new Complex(reference.re[i], reference.im[i]);

            if (z.sub(Z).chebyshevNorm() * CompressionErrorInverted > Z.chebyshevNorm()) {
                z = Z;
                cref.addWaypoint(new Waypoint(new Complex(Z), i));
            }

            z = f.function(z, c);
        }

        cref.compact();

        return cref;

    }

    public static CompressedDeepReference compressReferenceExtended(Fractal f, DeepReference reference, MantExpComplex c, int maxRefIteration) {

        if(!f.supportsExtendedReferenceCompression()) {
            throw new UnsupportedOperationException("This compression only works for mandelbrot.");
        }

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        CompressedDeepReference cref = new CompressedDeepReference(reference.length, reference.lengthOverride);
        cref.setCompressedExtended(true);

        MantExpComplex Z;

        int i = 1;
        MantExpComplex z = MantExpComplex.copy(c);

        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        int length = Math.min(maxRefIteration, reference.length - 1);

        for (; i <= length; i++) {
            if(twoExponents) {
                Z = new MantExpComplexFull(reference.exps[i], reference.expsIm[i], reference.mantsRe[i], reference.mantsIm[i]);
            }
            else {
                Z = new MantExpComplex(reference.exps[i], reference.mantsRe[i], reference.mantsIm[i]);
            }

            MantExp norm = Z.chebyshevNorm();

            if(norm.compareToBothPositiveReduced(limit1m) < 0) {
                z = Z;
                cref.addWaypoint(new Waypoint(MantExpComplex.copy(Z), i, true));
                break;
            }
            else if (z.sub(Z).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(norm) > 0) {
                z = Z;
                cref.addWaypoint(new Waypoint(MantExpComplex.copy(Z), i, false));
            }

            z = f.function(z, c);
            z.Normalize();
        }

        MantExpComplex dz = MantExpComplex.copy(z);
        int PrevWayPointIteration = i;
        dz = f.perturbationFunction(dz, reference, 0);
        dz.Normalize();
        i++;
        int j = 1;

        MantExpComplex Zi, Zj;
        for (; i <= length; i++, j++) {

            if(twoExponents) {
                Zj = new MantExpComplexFull(reference.exps[j], reference.expsIm[j], reference.mantsRe[j], reference.mantsIm[j]);
            }
            else {
                Zj = new MantExpComplex(reference.exps[j], reference.mantsRe[j], reference.mantsIm[j]);
            }

            z = dz.plus(Zj);
            z.Normalize();

            if(twoExponents) {
                Zi = new MantExpComplexFull(reference.exps[i], reference.expsIm[i], reference.mantsRe[i], reference.mantsIm[i]);
            }
            else {
                Zi = new MantExpComplex(reference.exps[i], reference.mantsRe[i], reference.mantsIm[i]);
            }

            if(j >= PrevWayPointIteration || z.sub(Zi).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(Zi.chebyshevNorm()) > 0) { // TODO: Use a different threshold for uncorrectable parts of the orbit.
                PrevWayPointIteration = i;
                z = Zi;
                dz = z.sub(Zj);
                dz.Normalize();

                if(z.chebyshevNorm().compareToBothPositiveReduced(dz.chebyshevNorm()) < 0 || (i - j) * 4 < i) {// TODO: Improve second condition
                    dz = z;
                    j = 0;
                    cref.addWaypoint(new Waypoint(MantExpComplex.copy(dz), i, true));
                }
                else {
                    cref.addWaypoint(new Waypoint(MantExpComplex.copy(dz), i, false));
                }
            }
            else if(z.chebyshevNorm().compareToBothPositiveReduced(dz.times(limit2m).chebyshevNorm()) < 0 ) {
                dz = z;
                j = 0;

                int rebasesLength = cref.rebasesListLength();
                if(rebasesLength > 0 && cref.getRebaseFromListAtIndex(rebasesLength - 1) > cref.getWaypointListIteration(cref.compressedListLength() - 1)) {
                    cref.setRebaseAt(rebasesLength - 1, i);
                }
                else {
                    cref.addRebase(i);
                }
            }

            dz = f.perturbationFunction(dz, reference, j);
            dz.Normalize();
        }

        cref.addWaypoint(new Waypoint(MantExpComplex.create(), Integer.MAX_VALUE, false));
        cref.addRebase(Integer.MAX_VALUE);

        cref.compactExtended();

        return cref;

    }

    public static CompressedDoubleReference compressReferenceExtended(Fractal f, DoubleReference reference, Complex c, int maxRefIteration) {

        if(!f.supportsExtendedReferenceCompression()) {
            throw new UnsupportedOperationException("This compression only works for mandelbrot.");
        }

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        CompressedDoubleReference cref = new CompressedDoubleReference(reference.length, reference.lengthOverride);
        cref.setCompressedExtended(true);

        Complex Z;

        int i = 1;
        Complex z = new Complex(c);

        int length = Math.min(maxRefIteration, reference.length - 1);

        for (; i <= length; i++) {
            Z = new Complex(reference.re[i], reference.im[i]);

            double norm = Z.chebyshevNorm();

            if(norm < limit1) {
                z = Z;
                cref.addWaypoint(new Waypoint(new Complex(Z), i, true));
                break;
            }
            else if (z.sub(Z).chebyshevNorm() * CompressionErrorInverted > norm) {
                z = Z;
                cref.addWaypoint(new Waypoint(new Complex(Z), i, false));
            }

            z = f.function(z, c);
        }

        Complex dz = new Complex(z);
        int PrevWayPointIteration = i;
        dz = f.perturbationFunction(dz, reference, 0);
        i++;
        int j = 1;

        Complex Zi, Zj;
        for (; i <= length; i++, j++) {
            Zj = new Complex(reference.re[j], reference.im[j]);
            z = dz.plus(Zj);
            Zi = new Complex(reference.re[i], reference.im[i]);
            if(j >= PrevWayPointIteration || z.sub(Zi).chebyshevNorm() * CompressionErrorInverted > Zi.chebyshevNorm()) { // TODO: Use a different threshold for uncorrectable parts of the orbit.
                PrevWayPointIteration = i;
                z = Zi;
                dz = z.sub(Zj);

                if(z.chebyshevNorm() < dz.chebyshevNorm() || (i - j) * 4 < i) {// TODO: Improve second condition
                    dz = z;
                    j = 0;
                    cref.addWaypoint(new Waypoint(new Complex(dz), i, true));
                }
                else {
                    cref.addWaypoint(new Waypoint(new Complex(dz), i, false));
                }
            }
            else if(z.chebyshevNorm() < dz.chebyshevNorm() * limit2) {
                dz = z;
                j = 0;

                int rebasesLength = cref.rebasesListLength();
                if(rebasesLength > 0 && cref.getRebaseFromListAtIndex(rebasesLength - 1) > cref.getWaypointListIteration(cref.compressedListLength() - 1)) {
                    cref.setRebaseAt(rebasesLength - 1, i);
                }
                else {
                    cref.addRebase(i);
                }
            }

            dz = f.perturbationFunction(dz, reference, j);
        }

        cref.addWaypoint(new Waypoint(new Complex(), Integer.MAX_VALUE, false));
        cref.addRebase(Integer.MAX_VALUE);

        cref.compactExtended();

        return cref;

    }

    public static CompressedDeepReference compressReferenceSimple(Fractal f, DeepReference reference, MantExpComplex z, MantExpComplex c, int maxRefIteration) {

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is already compressed.");
        }

        CompressedDeepReference cref = new CompressedDeepReference(reference.length, reference.lengthOverride);

        MantExpComplex Z;

        //Do the compression

        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        int length = Math.min(maxRefIteration, reference.length - 1);

        for (int i = 0; i <= length; i++) {
            if(twoExponents) {
                Z = new MantExpComplexFull(reference.exps[i], reference.expsIm[i], reference.mantsRe[i], reference.mantsIm[i]);
            }
            else {
                Z = new MantExpComplex(reference.exps[i], reference.mantsRe[i], reference.mantsIm[i]);
            }

            if (z.sub(Z).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(Z.chebyshevNorm()) > 0) {
                z = Z;
                cref.addWaypoint(new Waypoint(MantExpComplex.copy(Z), i));
            }

            z = f.function(z, c);
            z.Normalize();
        }

        cref.compact();

        return cref;

    }

    public Complex setArrayValue(DoubleReference ref, int iteration, Complex Z) {
        CompressedDoubleReference cref = (CompressedDoubleReference) ref;
        if (z.sub(Z).chebyshevNorm() * CompressionErrorInverted > Z.chebyshevNorm()) {
            z.assign(Z);
            cref.addWaypoint(new Waypoint(new Complex(Z), iteration));
        }

        Complex result = new Complex(z);
        z = f.function(z, c);

        return result;
    }

    public MantExpComplex setArrayDeepValue(DeepReference ref, int iteration, MantExpComplex Z) {
        CompressedDeepReference cref = (CompressedDeepReference) ref;
        if (mz.sub(Z).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(Z.chebyshevNorm()) > 0) {
            mz.assign(Z);
            cref.addWaypoint(new Waypoint(MantExpComplex.copy(Z), iteration));
        }

        MantExpComplex result = MantExpComplex.copy(mz);
        mz = f.function(mz, mc);
        mz.Normalize();

        return result;
    }

    public void setArrayValue(DoubleReference ref, int iteration, Complex CorrectVal, Complex refZ) {
        CompressedDoubleReference cref = (CompressedDoubleReference) ref;
        Complex CalculatedVal = function.apply(refZ);
        if (CalculatedVal.sub(CorrectVal).chebyshevNorm() * CompressionErrorInverted > CorrectVal.chebyshevNorm()) {
            cref.addWaypoint(new Waypoint(new Complex(CorrectVal), iteration));
        }
    }

    public void setArrayDeepValue(DeepReference ref, int iteration, MantExpComplex CorrectVal, MantExpComplex refZ) {
        CompressedDeepReference cref = (CompressedDeepReference) ref;

        MantExpComplex CalculatedVal = functionm.apply(refZ);
        CalculatedVal.Normalize();

        if (CalculatedVal.sub(CorrectVal).times_mutable(CompressionErrorInvertedm).chebyshevNorm().compareToBothPositiveReduced(CorrectVal.chebyshevNorm()) > 0) {
            cref.addWaypoint(new Waypoint(MantExpComplex.copy(CorrectVal), iteration));
        }
    }

    public Complex getZ() {
        return z;
    }

    public MantExpComplex getZDeep() {
        return mz;
    }

    public void compact(DoubleReference ref) {
        CompressedDoubleReference cref = (CompressedDoubleReference) ref;
        cref.compact();
    }

    public void compact(DeepReference ref) {
        CompressedDeepReference cref = (CompressedDeepReference) ref;
        cref.compact();
    }

    public static void createLowPrecisionOrbit(CompressedDoubleReference ref, CompressedDeepReference cref) {
        int length = cref.compressedLength();
        for(int i = 0; i < length; i++) {
            Waypoint w = cref.getWaypoint(i);
            ref.addWaypoint(new Waypoint(w.mz.toComplex(), w.iteration));
        }
        ref.compact();
    }

    public void reset() {
        if(initVal != null) {
            z = new Complex(initVal);
        }

        if(initValm != null) {
            mz = MantExpComplex.copy(initValm);
        }
    }

    /*
    if(deepZoom) {
            CompressedDeepReference test = ReferenceCompressor.compressExtended(this, referenceDeep, refPointSmallDeep, referenceData.MaxRefIteration + 1);
            CompressedDeepReference test2 = ReferenceCompressor.compressSimple(this, referenceDeep, MantExpComplex.create(), refPointSmallDeep, referenceData.MaxRefIteration + 1);

            DeepReference test3 = ReferenceDecompressor.uncompressSimple(this, test2, MantExpComplex.create(), refPointSmallDeep, referenceData.MaxRefIteration + 1);
            DeepReference test4 = ReferenceDecompressor.uncompressExtended(this, test, MantExpComplex.create(), refPointSmallDeep, referenceData.MaxRefIteration + 1);

            MantExp maxError1 = new MantExp(-Double.MAX_VALUE);
            int index1 = -1;
            MantExpComplex origRef;
            MantExpComplex newRef;
            boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;
            for (int i = 0; i <= referenceData.MaxRefIteration + 1; i++) {

                if(twoExponents) {
                    origRef = new MantExpComplexFull(referenceDeep.exps[i], referenceDeep.expsIm[i], referenceDeep.mantsRe[i], referenceDeep.mantsIm[i]);
                    newRef = new MantExpComplexFull(test3.exps[i], test3.expsIm[i], test3.mantsRe[i], test3.mantsIm[i]);
                }
                else {
                    origRef = new MantExpComplex(referenceDeep.exps[i], referenceDeep.mantsRe[i], referenceDeep.mantsIm[i]);
                    newRef = new MantExpComplex(test3.exps[i], test3.mantsRe[i], test3.mantsIm[i]);
                }

                MantExp error = origRef.distance(newRef);
                error.Normalize();
                if (error.compareTo(maxError1) > 0) {
                    maxError1 = error;
                    index1 = i;
                }
            }

            MantExp maxError2 = new MantExp(-Double.MAX_VALUE);
            int index2 = -1;
            for (int i = 0; i <= referenceData.MaxRefIteration + 1; i++) {
                if(twoExponents) {
                    origRef = new MantExpComplexFull(referenceDeep.exps[i], referenceDeep.expsIm[i], referenceDeep.mantsRe[i], referenceDeep.mantsIm[i]);
                    newRef = new MantExpComplexFull(test4.exps[i], test4.expsIm[i], test4.mantsRe[i], test4.mantsIm[i]);
                }
                else {
                    origRef = new MantExpComplex(referenceDeep.exps[i], referenceDeep.mantsRe[i], referenceDeep.mantsIm[i]);
                    newRef = new MantExpComplex(test4.exps[i], test4.mantsRe[i], test4.mantsIm[i]);
                }

                MantExp error = origRef.distance(newRef);
                error.Normalize();

                if (error.compareTo(maxError2) > 0) {
                    maxError2 = error;
                    index2 = i;
                }
            }

            System.out.println(maxError1 + " " + index1);
            System.out.println(maxError2 + " " + index2);
        }
     */
}
