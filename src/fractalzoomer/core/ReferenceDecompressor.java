package fractalzoomer.core;

import fractalzoomer.functions.Fractal;

import java.util.function.Function;

public class ReferenceDecompressor {
    private Complex c;
    private MantExpComplex mc;

    private Complex initVal;
    private MantExpComplex initValm;
    private Fractal f;

    private int prevIteration;
    private Complex prevZ;
    private MantExpComplex prevZm;
    private long nextWaypointIteration;
    private int currentWaypointIteration;
    private int nextCompressedIndex;

    private int currentCompressIndex;

    private Waypoint currentWaypoint;

    private Function<Complex, Complex> function;
    private Function<MantExpComplex, MantExpComplex> functionm;

    public ReferenceDecompressor(Function<Complex, Complex> function) {
        this.function = function;
        prevIteration = Integer.MIN_VALUE;
        nextWaypointIteration = Long.MIN_VALUE;
        nextCompressedIndex = Integer.MIN_VALUE;
        currentWaypointIteration = Integer.MIN_VALUE;
        currentCompressIndex = Integer.MIN_VALUE;
        currentWaypoint = null;
        prevZ = new Complex();
    }

    public ReferenceDecompressor(Function<MantExpComplex, MantExpComplex> function, boolean deep) {
        this.functionm = function;
        prevIteration = Integer.MIN_VALUE;
        nextWaypointIteration = Long.MIN_VALUE;
        nextCompressedIndex = Integer.MIN_VALUE;
        currentWaypointIteration = Integer.MIN_VALUE;
        currentCompressIndex = Integer.MIN_VALUE;
        currentWaypoint = null;
        prevZm = MantExpComplex.create();
    }

    public ReferenceDecompressor(ReferenceDecompressor other) {
        if(other.c != null) {
            this.c = new Complex(other.c);
        }
        if(other.initVal != null) {
            this.initVal = new Complex(other.initVal);
        }
        if(other.mc != null) {
            this.mc = MantExpComplex.copy(other.mc);
        }
        if(other.initValm != null) {
            this.initValm = MantExpComplex.copy(other.initValm);
        }
        this.f = other.f;
        this.functionm = other.functionm;
        this.function = other.function;

        prevIteration = Integer.MIN_VALUE;
        nextWaypointIteration = Long.MIN_VALUE;
        nextCompressedIndex = Integer.MIN_VALUE;
        currentWaypointIteration = Integer.MIN_VALUE;
        currentCompressIndex = Integer.MIN_VALUE;
        currentWaypoint = null;
        prevZm = MantExpComplex.create();
        prevZ = new Complex();
    }

    public ReferenceDecompressor(Fractal f, Complex c, Complex initVal) {
        this.c = c;
        this.initVal = initVal;
        this.f = f;
        prevIteration = Integer.MIN_VALUE;
        nextWaypointIteration = Long.MIN_VALUE;
        nextCompressedIndex = Integer.MIN_VALUE;
        currentWaypointIteration = Integer.MIN_VALUE;
        currentCompressIndex = Integer.MIN_VALUE;
        currentWaypoint = null;
        prevZ = new Complex();
    }

    public ReferenceDecompressor(Fractal f, MantExpComplex mc, MantExpComplex initValm) {
        this.f = f;
        this.mc = mc;
        this.initValm = initValm;
        prevIteration = Integer.MIN_VALUE;
        nextWaypointIteration = Long.MIN_VALUE;
        nextCompressedIndex = Integer.MIN_VALUE;
        currentWaypointIteration = Integer.MIN_VALUE;
        currentCompressIndex = Integer.MIN_VALUE;
        currentWaypoint = null;
        prevZm = MantExpComplex.create();
    }

    //maxRefIteration should be +1
    public static DoubleReference uncompressReferenceSimple(Fractal f, CompressedDoubleReference cref, Complex z, Complex c, int maxRefIteration) {

        if(cref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        if(cref.length <= 0) {
            throw new UnsupportedOperationException("Invalid length");
        }

        DoubleReference ref = new DoubleReference(cref.length, cref.lengthOverride);

        ref.re[0] = z.getRe();
        ref.im[0] = z.getIm();

        int compressed_index = 0;

        int length = Math.min(maxRefIteration, ref.length - 1);

        for(int i = 1; i <= length; i++) {
            if(cref.useWaypoint(compressed_index, i)) {
                z = cref.getWaypointData(compressed_index);
                compressed_index++;
            }
            else {
                z = f.function(z, c);
            }

            ref.re[i] = z.getRe();
            ref.im[i] = z.getIm();
        }

        return ref;
    }

    //maxRefIteration should be +1
    @Deprecated
    public static DoubleReference uncompressReferenceWithExpression(DoubleReference reference, CompressedDoubleReference cexprref, Function<Complex, Complex> function, int maxRefIteration) {

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is compressed.");
        }

        if(cexprref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        if(cexprref.length <= 0) {
            throw new UnsupportedOperationException("Invalid length");
        }

        if(reference.length != cexprref.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        DoubleReference output = new DoubleReference(cexprref.length, cexprref.lengthOverride);

        int compressed_index = 0;

        int length = Math.min(maxRefIteration, output.length - 1);

        Complex z;

        for(int i = 0; i <= length; i++) {
            if(cexprref.useWaypoint(compressed_index, i)) {
                z = cexprref.getWaypointData(compressed_index);
                compressed_index++;
            }
            else {
                z = function.apply(new Complex(reference.re[i], reference.im[i]));
            }

            output.re[i] = z.getRe();
            output.im[i] = z.getIm();
        }

        return output;
    }

    public static DoubleReference uncompressReferenceWithExpression(CompressedDoubleReference reference, CompressedDoubleReference cexprref, Function<Complex, Complex> function, Fractal f, Complex z, Complex c, int maxRefIteration) {


        if(reference.isCompressedExtended() || cexprref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        if(cexprref.length <= 0) {
            throw new UnsupportedOperationException("Invalid length");
        }

        if(reference.length != cexprref.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        DoubleReference output = new DoubleReference(cexprref.length, cexprref.lengthOverride);

        int compressed_index = 0;
        int compressed_index2 = 0;

        int length = Math.min(maxRefIteration, output.length - 1);

        Complex exprz;

        for(int i = 0; i <= length; i++) {
            if(reference.useWaypoint(compressed_index2, i)) {
                z = reference.getWaypointData(compressed_index2);
                compressed_index2++;
            }
            else if (i > 0) {
                z = f.function(z, c);
            }

            if(cexprref.useWaypoint(compressed_index, i)) {
                exprz = cexprref.getWaypointData(compressed_index);
                compressed_index++;
            }
            else {
                exprz = function.apply(z);
            }

            output.re[i] = exprz.getRe();
            output.im[i] = exprz.getIm();
        }

        return output;
    }


    //maxRefIteration should be +1
    @Deprecated
    public static DeepReference uncompressReferenceWithExpression(DeepReference reference, CompressedDeepReference cexprref, Function<MantExpComplex, MantExpComplex> function, int maxRefIteration) {

        if(reference.compressed) {
            throw new UnsupportedOperationException("This reference is compressed.");
        }

        if(cexprref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        if(cexprref.length <= 0) {
            throw new UnsupportedOperationException("Invalid length");
        }

        if(reference.length != cexprref.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        DeepReference output = new DeepReference(cexprref.length, cexprref.lengthOverride);

        int compressed_index = 0;

        int length = Math.min(maxRefIteration, output.length - 1);

        MantExpComplex z;

        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        for(int i = 0; i <= length; i++) {
            if(cexprref.useWaypoint(compressed_index, i)) {
                z = cexprref.getWaypointData(compressed_index);
                compressed_index++;
            }
            else {
                if(twoExponents) {
                    z = function.apply(new MantExpComplexFull(reference.exps[i], reference.expsIm[i], reference.mantsRe[i], reference.mantsIm[i]));
                }
                else {
                    z = function.apply(new MantExpComplex(reference.exps[i], reference.mantsRe[i], reference.mantsIm[i]));
                }

                z.Normalize();
            }

            output.exps[i] = z.getExp();
            output.mantsRe[i] = z.getMantissaReal();
            output.mantsIm[i] = z.getMantissaImag();
            if(twoExponents) {
                output.expsIm[i] = z.getExpImag();
            }
        }

        return output;
    }

    public static DeepReference uncompressReferenceWithExpression(CompressedDeepReference reference, CompressedDeepReference cexprref, Function<MantExpComplex, MantExpComplex> function, Fractal f, MantExpComplex z, MantExpComplex c, int maxRefIteration) {

        if(cexprref.isCompressedExtended() || reference.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        if(cexprref.length <= 0) {
            throw new UnsupportedOperationException("Invalid length");
        }

        if(reference.length != cexprref.length) {
            throw new UnsupportedOperationException("Invalid length");
        }

        DeepReference output = new DeepReference(cexprref.length, cexprref.lengthOverride);

        int compressed_index = 0;
        int compressed_index2 = 0;

        int length = Math.min(maxRefIteration, output.length - 1);

        MantExpComplex exprz;

        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        for(int i = 0; i <= length; i++) {
            if(reference.useWaypoint(compressed_index2, i)) {
                z = reference.getWaypointData(compressed_index2);
                compressed_index2++;
            }
            else if(i > 0) {
                z = f.function(z, c);
                z.Normalize();
            }

            if(cexprref.useWaypoint(compressed_index, i)) {
                exprz = cexprref.getWaypointData(compressed_index);
                compressed_index++;
            }
            else {
                exprz = function.apply(z);
                exprz.Normalize();
            }

            output.exps[i] = exprz.getExp();
            output.mantsRe[i] = exprz.getMantissaReal();
            output.mantsIm[i] = exprz.getMantissaImag();
            if(twoExponents) {
                output.expsIm[i] = exprz.getExpImag();
            }
        }

        return output;
    }
    private static double ScalingFactor = 0x1.0p-384;
    private static void CorrectOrbit(DoubleReference ref, int Begin, int End, Complex diff) {

        Complex dzdc = new Complex(ScalingFactor, 0);
        for (int i = End; i > Begin; ) {
            i--;
            dzdc.times_mutable(new Complex(ref.re[i], ref.im[i]).times2_mutable());
            Complex temp = diff.times(dzdc.conjugate().times_mutable(ScalingFactor / dzdc.norm_squared()));
            ref.re[i] += temp.getRe();
            ref.im[i] += temp.getIm();
        }
    }

    private static MantExp ScalingFactorm = new MantExp(ScalingFactor);

    private static void CorrectOrbit(DeepReference ref, int Begin, int End, MantExpComplex diff) {
        MantExpComplex dzdc = MantExpComplex.create(ScalingFactorm, new MantExp());
        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;
        MantExpComplex Z;
        for (int i = End; i > Begin; ) {
            i--;

            if(twoExponents) {
                Z = new MantExpComplexFull(ref.exps[i], ref.expsIm[i], ref.mantsRe[i], ref.mantsIm[i]);
            }
            else {
                Z = new MantExpComplex(ref.exps[i], ref.mantsRe[i], ref.mantsIm[i]);
            }

            dzdc.times_mutable(Z.times2());
            dzdc.Normalize();
            MantExpComplex temp = diff.times(dzdc.conjugate().times_mutable(ScalingFactorm.divide(dzdc.norm_squared())));
            temp.Normalize();
            Z.plus_mutable(temp);
            Z.Normalize();

            ref.mantsRe[i] = Z.getMantissaReal();
            ref.mantsIm[i] = Z.getMantissaImag();
            ref.exps[i] = Z.getExp();
            if(twoExponents) {
                ref.expsIm[i] = Z.getExpImag();
            }
        }
    }

    public static DoubleReference uncompressReferenceExtended(Fractal f, CompressedDoubleReference cref, Complex z, Complex c, int maxRefIteration) {

        if(!f.supportsExtendedReferenceCompression()) {
            throw new UnsupportedOperationException("This compression only works for mandelbrot");
        }

        if(!cref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        DoubleReference ref = new DoubleReference(cref.length, cref.lengthOverride);

        if(cref.compressedLength() == 0) {
            throw new UnsupportedOperationException("Cannot uncompress due to corrupted compressed data.");
        }

        int length = Math.min(maxRefIteration, ref.length - 1);

        int WayPointIndex = 0;
        int RebaseIndex = 0;
        Waypoint NextWayPoint = cref.getWaypointExtended(WayPointIndex);
        int NextRebase = cref.getRebaseAtIndex(RebaseIndex);

        int i = 0;
        int UncorrectedOrbitBegin = 1;
        for (; i <= length; i++)
        {
            if (i == NextWayPoint.iteration) {
                CorrectOrbit(ref, UncorrectedOrbitBegin, i, NextWayPoint.z.sub(z));
                UncorrectedOrbitBegin = i + 1;
                z = NextWayPoint.z;
                boolean Rebase = NextWayPoint.rebase;
                WayPointIndex++;
                NextWayPoint = cref.getWaypointExtended(WayPointIndex);
                if (Rebase) {
                    break;
                }
            }
            ref.re[i] = z.getRe();
            ref.im[i] = z.getIm();

            z = f.function(z, c);
        }

        int j = 0;
        Complex dz = z;
        for (; i <= length; i++, j++) {
            z = dz.plus(new Complex(ref.re[j], ref.im[j]));
            if(i == NextWayPoint.iteration) {
                if (NextWayPoint.rebase) {
                    dz = z;
                    j = 0;
                }
                CorrectOrbit(ref, UncorrectedOrbitBegin, i, NextWayPoint.z.sub(dz));
                UncorrectedOrbitBegin = i + 1;
                dz = NextWayPoint.z;
                z = dz.plus(new Complex(ref.re[j], ref.im[j])); //Zj
                WayPointIndex++;
                NextWayPoint = cref.getWaypointExtended(WayPointIndex);
            }
            else if(i == NextRebase) {
                RebaseIndex++;
                NextRebase = cref.getRebaseAtIndex(RebaseIndex);
                dz = z;
                j = 0;
            }
            else if(z.chebyshevNorm() < dz.chebyshevNorm()) {
                dz = z;
                j = 0;
            }

            ref.re[i] = z.getRe();
            ref.im[i] = z.getIm();

            dz = f.perturbationFunction(dz, ref, j);
        }

        return ref;
    }

    public static DeepReference uncompressReferenceExtended(Fractal f, CompressedDeepReference cref, MantExpComplex z, MantExpComplex c, int maxRefIteration) {

        if(!f.supportsExtendedReferenceCompression()) {
            throw new UnsupportedOperationException("This compression only works for mandelbrot");
        }

        if(!cref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        DeepReference ref = new DeepReference(cref.length, cref.lengthOverride);

        if(cref.compressedLength() == 0) {
            throw new UnsupportedOperationException("Cannot uncompress due to corrupted compressed data.");
        }

        int length = Math.min(maxRefIteration, ref.length - 1);

        int WayPointIndex = 0;
        int RebaseIndex = 0;
        Waypoint NextWayPoint = cref.getWaypointExtended(WayPointIndex);
        int NextRebase = cref.getRebaseAtIndex(RebaseIndex);

        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        int i = 0;
        int UncorrectedOrbitBegin = 1;
        for (; i <= length; i++)
        {
            if (i == NextWayPoint.iteration) {
                CorrectOrbit(ref, UncorrectedOrbitBegin, i, NextWayPoint.mz.sub(z));
                UncorrectedOrbitBegin = i + 1;
                z = NextWayPoint.mz;
                boolean Rebase = NextWayPoint.rebase;
                WayPointIndex++;
                NextWayPoint = cref.getWaypointExtended(WayPointIndex);
                if (Rebase) {
                    break;
                }
            }

            ref.mantsRe[i] = z.getMantissaReal();
            ref.mantsIm[i] = z.getMantissaImag();
            ref.exps[i] = z.getExp();
            if(twoExponents) {
                ref.expsIm[i] = z.getExpImag();
            }

            z = f.function(z, c);
            z.Normalize();
        }

        int j = 0;
        MantExpComplex dz = z;
        MantExpComplex Z;
        for (; i <= length; i++, j++) {
            if(twoExponents) {
                Z = new MantExpComplexFull(ref.exps[j], ref.expsIm[j], ref.mantsRe[j], ref.mantsIm[j]);
            }
            else {
                Z = new MantExpComplex(ref.exps[j], ref.mantsRe[j], ref.mantsIm[j]);
            }

            z = dz.plus(Z);
            z.Normalize();
            if(i == NextWayPoint.iteration) {
                if (NextWayPoint.rebase) {
                    dz = z;
                    j = 0;
                }
                CorrectOrbit(ref, UncorrectedOrbitBegin, i, NextWayPoint.mz.sub(dz));
                UncorrectedOrbitBegin = i + 1;
                dz = NextWayPoint.mz;

                if(twoExponents) {
                    Z = new MantExpComplexFull(ref.exps[j], ref.expsIm[j], ref.mantsRe[j], ref.mantsIm[j]);
                }
                else {
                    Z = new MantExpComplex(ref.exps[j], ref.mantsRe[j], ref.mantsIm[j]);
                }

                z = dz.plus(Z);
                z.Normalize();
                WayPointIndex++;
                NextWayPoint = cref.getWaypointExtended(WayPointIndex);
            }
            else if(i == NextRebase) {
                RebaseIndex++;
                NextRebase = cref.getRebaseAtIndex(RebaseIndex);
                dz = z;
                j = 0;
            }
            else if(z.chebyshevNorm().compareToBothPositiveReduced(dz.chebyshevNorm()) < 0) {
                dz = z;
                j = 0;
            }

            ref.mantsRe[i] = z.getMantissaReal();
            ref.mantsIm[i] = z.getMantissaImag();
            ref.exps[i] = z.getExp();
            if(twoExponents) {
                ref.expsIm[i] = z.getExpImag();
            }

            dz = f.perturbationFunction(dz, ref, j);
            dz.Normalize();
        }

        return ref;
    }

    public static DeepReference uncompressReferenceSimple(Fractal f, CompressedDeepReference cref, MantExpComplex z, MantExpComplex c, int maxRefIteration) {

        if(cref.isCompressedExtended()) {
            throw new UnsupportedOperationException("Cannot uncompress due to different compression algorithm.");
        }

        if(cref.length <= 0) {
            throw new UnsupportedOperationException("Invalid length");
        }

        DeepReference ref = new DeepReference(cref.length, cref.lengthOverride);

        boolean twoExponents = TaskRender.MANTEXPCOMPLEX_FORMAT == 1;

        ref.exps[0] = z.getExp();
        ref.mantsRe[0] = z.getMantissaReal();
        ref.mantsIm[0] = z.getMantissaImag();

        if(twoExponents) {
            ref.expsIm[0] = z.getExpImag();
        }

        int compressed_index = 0;

        int length = Math.min(maxRefIteration, ref.length - 1);

        for(int i = 1; i <= length; i++) {
            if(cref.useWaypoint(compressed_index, i)) {
                z = cref.getWaypointData(compressed_index);
                compressed_index++;
            }
            else {
                z = f.function(z, c);
                z.Normalize();
            }

            ref.exps[i] = z.getExp();
            ref.mantsRe[i] = z.getMantissaReal();
            ref.mantsIm[i] = z.getMantissaImag();

            if(twoExponents) {
                ref.expsIm[i] = z.getExpImag();
            }
        }

        return ref;
    }

    public MantExpComplex getArrayDeepValue(DeepReference array, int iteration) {
        CompressedDeepReference cref = (CompressedDeepReference) array;
        MantExpComplex z;

        if(iteration == prevIteration) {
            z = MantExpComplex.copy(prevZm);
        }
        else if(iteration == nextWaypointIteration) {
            currentCompressIndex = nextCompressedIndex;
            currentWaypoint = cref.getWaypoint(currentCompressIndex);
            nextCompressedIndex++;

            currentWaypointIteration = currentWaypoint.iteration;

            if(nextCompressedIndex < cref.compressedLength()) {
                nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
            }
            else {
                nextWaypointIteration = Long.MAX_VALUE;
            }

            z = MantExpComplex.copy(currentWaypoint.mz);
        }
        else if(prevIteration >= 0 && iteration < nextWaypointIteration && iteration > prevIteration) { //sequential
            int length = iteration - prevIteration;
            z = MantExpComplex.copy(prevZm);
            for(int i = 0; i < length; i++) {
                z = f.function(z, mc);
                z.Normalize();
            }
        }
        else {
           if(iteration < currentWaypointIteration || iteration >= nextWaypointIteration) {
               currentWaypoint = cref.getClosestWaypoint(iteration); //binary search
               currentCompressIndex = currentWaypoint.index;
               nextCompressedIndex = currentCompressIndex + 1;

               currentWaypointIteration = currentWaypoint.iteration;

               if (nextCompressedIndex < cref.compressedLength()) {
                   nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
               } else {
                   nextWaypointIteration = Long.MAX_VALUE;
               }
           }

           if(currentWaypoint.mz == null) {
               z = MantExpComplex.copy(initValm);
           }
           else {
               z = MantExpComplex.copy(currentWaypoint.mz);
           }

           for (int i = currentWaypointIteration; i < iteration; i++) {
               z = f.function(z, mc);
               z.Normalize();
           }
        }

        prevZm.assign(z);
        prevIteration = iteration;

        return z;
    }

    public Complex getArrayValue(DoubleReference array, int iteration) {

        Complex z;
        CompressedDoubleReference cref = (CompressedDoubleReference) array;

        if(iteration == prevIteration) {
            z = new Complex(prevZ);
        }
        else if(iteration == nextWaypointIteration) {
            currentCompressIndex = nextCompressedIndex;
            currentWaypoint = cref.getWaypoint(currentCompressIndex);
            nextCompressedIndex++;

            currentWaypointIteration = currentWaypoint.iteration;

            if (nextCompressedIndex < cref.compressedLength()) {
                nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
            } else {
                nextWaypointIteration = Long.MAX_VALUE;
            }

            z = new Complex(currentWaypoint.z);
        }
        else if(prevIteration >= 0 && iteration < nextWaypointIteration && iteration > prevIteration) { //sequential
            int length = iteration - prevIteration;
            z = new Complex(prevZ);
            for(int i = 0; i < length; i++) {
                z = f.function(z, c);
            }
        }
        else {

            if(iteration < currentWaypointIteration || iteration >= nextWaypointIteration) {
                currentWaypoint = cref.getClosestWaypoint(iteration); //binary search
                currentCompressIndex = currentWaypoint.index;
                nextCompressedIndex = currentCompressIndex + 1;

                currentWaypointIteration = currentWaypoint.iteration;

                if (nextCompressedIndex < cref.compressedLength()) {
                    nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
                } else {
                    nextWaypointIteration = Long.MAX_VALUE;
                }
            }

            if(currentWaypoint.z == null) {
                z = new Complex(initVal);
            }
            else {
                z = new Complex(currentWaypoint.z);
            }

            for (int i = currentWaypointIteration; i < iteration; i++) {
                z = f.function(z, c);
            }
        }

        prevZ.assign(z);
        prevIteration = iteration;

        return z;

    }

    public Complex getArrayValue(DoubleReference array, int iteration, Complex refZ) {

        Complex z;
        CompressedDoubleReference cref = (CompressedDoubleReference) array;

        if(iteration == prevIteration) {
            z = new Complex(prevZ);
        }
        else if(iteration == nextWaypointIteration) {
            currentCompressIndex = nextCompressedIndex;
            currentWaypoint = cref.getWaypoint(currentCompressIndex);
            nextCompressedIndex++;

            currentWaypointIteration = currentWaypoint.iteration;

            if (nextCompressedIndex < cref.compressedLength()) {
                nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
            } else {
                nextWaypointIteration = Long.MAX_VALUE;
            }

            z = new Complex(currentWaypoint.z);
        }
        else {

            if(iteration < currentWaypointIteration || iteration >= nextWaypointIteration) {
                currentWaypoint = cref.getClosestWaypoint(iteration); //binary search
                currentCompressIndex = currentWaypoint.index;
                nextCompressedIndex = currentCompressIndex + 1;

                currentWaypointIteration = currentWaypoint.iteration;

                if (nextCompressedIndex < cref.compressedLength()) {
                    nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
                } else {
                    nextWaypointIteration = Long.MAX_VALUE;
                }
            }

            if(iteration != currentWaypointIteration || currentWaypoint.z == null) {
                z = function.apply(refZ);
            }
            else {
                z = new Complex(currentWaypoint.z);
            }
        }

        prevZ.assign(z);
        prevIteration = iteration;

        return z;

    }

    public MantExpComplex getArrayDeepValue(DeepReference array, int iteration, MantExpComplex refZ) {

        MantExpComplex z;
        CompressedDeepReference cref = (CompressedDeepReference) array;

        if(iteration == prevIteration) {
            z = MantExpComplex.copy(prevZm);
        }
        else if(iteration == nextWaypointIteration) {
            currentCompressIndex = nextCompressedIndex;
            currentWaypoint = cref.getWaypoint(currentCompressIndex);
            nextCompressedIndex++;

            currentWaypointIteration = currentWaypoint.iteration;

            if (nextCompressedIndex < cref.compressedLength()) {
                nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
            } else {
                nextWaypointIteration = Long.MAX_VALUE;
            }

            z = MantExpComplex.copy(currentWaypoint.mz);
        }
        else {

            if(iteration < currentWaypointIteration || iteration >= nextWaypointIteration) {
                currentWaypoint = cref.getClosestWaypoint(iteration); //binary search
                currentCompressIndex = currentWaypoint.index;
                nextCompressedIndex = currentCompressIndex + 1;

                currentWaypointIteration = currentWaypoint.iteration;

                if (nextCompressedIndex < cref.compressedLength()) {
                    nextWaypointIteration = cref.getWaypointIteration(nextCompressedIndex);
                } else {
                    nextWaypointIteration = Long.MAX_VALUE;
                }
            }

            if(iteration != currentWaypointIteration || currentWaypoint.z == null) {
                z = functionm.apply(refZ);
                z.Normalize();
            }
            else {
                z = MantExpComplex.copy(currentWaypoint.mz);
            }
        }

        prevZm.assign(z);
        prevIteration = iteration;

        return z;

    }


    //when using a single decompressor on a multithreaded level
    public MantExpComplex getArrayDeepValueRandomAccess(DeepReference array, int iteration) {
        CompressedDeepReference cref = (CompressedDeepReference) array;
        Waypoint waypoint = cref.getClosestWaypoint(iteration); //binary search
        MantExpComplex z;

        if(waypoint.mz == null) {
            z = MantExpComplex.copy(initValm);
        }
        else {
            z = waypoint.mz;
        }

        for (int i = waypoint.iteration; i < iteration; i++) {
            z = f.function(z, mc);
            z.Normalize();
        }

        return z;
    }

    //when using a single decompressor on a multithreaded level
    public Complex getArrayValueRandomAccess(DoubleReference array, int iteration) {
        CompressedDoubleReference cref = (CompressedDoubleReference) array;
        Waypoint waypoint = cref.getClosestWaypoint(iteration); //binary search
        Complex z;

        if(waypoint.z == null) {
            z = new Complex(initVal);
        }
        else {
            z = waypoint.z;
        }

        for (int i = waypoint.iteration; i < iteration; i++) {
            z = f.function(z, c);
        }

        return z;
    }

    public MantExpComplex getArrayDeepValueRandomAccess(DeepReference array, int iteration, MantExpComplex refZ) {

        CompressedDeepReference cref = (CompressedDeepReference) array;

        Waypoint waypoint = cref.findWaypoint(iteration);

        MantExpComplex z;
        if(waypoint == null) {
            z = functionm.apply(refZ);
        }
        else {
            z = waypoint.mz;
        }

        return z;

    }

    public Complex getArrayValueRandomAccess(DoubleReference array, int iteration, Complex refZ) {

        CompressedDoubleReference cref = (CompressedDoubleReference) array;

        Waypoint waypoint = cref.findWaypoint(iteration);

        Complex z;
        if(waypoint == null) {
            z = function.apply(refZ);
        }
        else {
            z = waypoint.z;
        }

        return z;

    }
}
