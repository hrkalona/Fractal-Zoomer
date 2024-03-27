package fractalzoomer.core.bla;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static fractalzoomer.main.Constants.BLA_CALCULATION_STR;

public class BLAS {
    public int M;
    public int L;
    private int LM2;//Level -1 is not attainable due to Zero R
    public BLA[][] b;
    public BLADeep[][] bdeep;
    public boolean isValid;
    private Fractal fractal;

    private int[] elementsPerLevel;

    private int firstLevel;
    private boolean returnL1;

    private long finalTotal;

    private ReferenceDecompressor[] referenceDecompressors;
    private ReferenceDecompressor referenceDecompressor;

    public BLAS(Fractal fractal) {
        this.fractal = fractal;
    }

    private BLADeep createOneStep(DeepReference Ref, int m, MantExp epsilon, ReferenceDecompressor referenceDecompressor) {
        MantExpComplex Z = fractal.getArrayDeepValue(referenceDecompressor, Ref, m);
        MantExpComplex A = fractal.getBlaA(Z);

        MantExp mA = A.hypot();

        MantExp r = mA.multiply(epsilon);

        MantExp r2 = r.square();

        A.Normalize();
        r2.Normalize();

        return BLADeep1Step.create(r2, A);
    }

    private void initLStep(int level, int m, DoubleReference Ref, double blaSize, double epsilon, ReferenceDecompressor referenceDecompressor) {

        b[level][m - 1] = createLStep(level, m, Ref, blaSize, epsilon, referenceDecompressor);

    }

    private void initLStep(int level, int m, DeepReference Ref, MantExp blaSize, MantExp epsilon, ReferenceDecompressor referenceDecompressor) {

        bdeep[level][m - 1] = createLStep(level, m, Ref, blaSize, epsilon, referenceDecompressor);

    }

    private BLA mergeTwoBlas(BLA x, BLA y, double blaSize) {
        int l = x.getL() + y.getL();
        // A = y.A * x.A
        Complex A = BLA.getNewA(x, y);
        // B = y.A * x.B + y.B
        Complex B = BLA.getNewB(x, y);
        double xA = x.hypotA();
        double xB = x.hypotB();
        double r = Math.min(Math.sqrt(x.r2), Math.max(0, (Math.sqrt(y.r2) - xB * blaSize) / xA));
        double r2 = r * r;
        return new BLALStep(r2, A, B) {
            @Override
            public int getL() {
                return l;
            }
        };
    }

    private BLADeep mergeTwoBlas(BLADeep x, BLADeep y, MantExp blaSize) {
        int l = x.getL() + y.getL();
        // A = y.A * x.A
        MantExpComplex A = BLADeep.getNewA(x, y);
        // B = y.A * x.B + y.B
        MantExpComplex B = BLADeep.getNewB(x, y);
        MantExp xA = x.hypotA();
        MantExp xB = x.hypotB();

        MantExp r = MantExp.min(x.getR2().sqrt_mutable(), MantExp.max(MantExp.ZERO, y.getR2().sqrt_mutable().subtract_mutable(xB.multiply(blaSize)).divide_mutable(xA)));
        MantExp r2 = r.square();

        A.Normalize();
        B.Normalize();
        r2.Normalize();

        return BLADeepLStep.create(r2, A, B, l);
    }

    private BLA createLStep(int level, int m, DoubleReference Ref, double blaSize, double epsilon, ReferenceDecompressor referenceDecompressor) {

        if(level == 0) {
            return createOneStep(Ref, m, epsilon, referenceDecompressor);
        }

        int m2 = m << 1;
        int mx = m2 - 1;
        int my = m2;
        int levelm1 = level - 1;
        if (my <= elementsPerLevel[levelm1]) {

            BLA x = createLStep(levelm1, mx, Ref, blaSize, epsilon, referenceDecompressor);

            BLA y = createLStep(levelm1, my, Ref, blaSize, epsilon, referenceDecompressor);

            return mergeTwoBlas(x, y, blaSize);
        } else {
            return createLStep(levelm1, mx, Ref, blaSize, epsilon, referenceDecompressor);
        }
    }


    private BLADeep createLStep(int level, int m, DeepReference Ref, MantExp blaSize, MantExp epsilon, ReferenceDecompressor referenceDecompressor) {

        if(level == 0) {
            return createOneStep(Ref, m, epsilon, referenceDecompressor);
        }

        int m2 = m << 1;
        int mx = m2 - 1;
        int my = m2;
        int levelm1 = level - 1;
        if (my <= elementsPerLevel[levelm1]) {
            BLADeep x = createLStep(levelm1, mx, Ref, blaSize, epsilon, referenceDecompressor);

            BLADeep y = createLStep(levelm1, my, Ref, blaSize, epsilon, referenceDecompressor);

            return mergeTwoBlas(x, y, blaSize);
        } else {
            return createLStep(levelm1, mx, Ref, blaSize, epsilon, referenceDecompressor);
        }
    }

    private BLA createOneStep(DoubleReference Ref, int m, double epsilon, ReferenceDecompressor referenceDecompressor) {
        Complex Z = fractal.getArrayValue(referenceDecompressor, Ref, m);
        Complex A = fractal.getBlaA(Z);

        double mA = A.hypot();

        double r = mA * epsilon;

        double r2 = r * r;

        return new BLA1Step(r2, A);
    }

    private long done;
    private long old_chunk;
    private void init(DoubleReference Ref, double blaSize, double epsilon, JProgressBar progress, long divisor) {

        int elements = elementsPerLevel[firstLevel] + 1;
        if(TaskDraw.USE_THREADS_FOR_BLA) {
            done = 0; //we dont care fore race condition

            ArrayList<Future<?>> futures = new ArrayList<>();
            final int ThreadCount = TaskDraw.la_thread_executor.getCorePoolSize();
            Runnable[] Tasks = new Runnable[ThreadCount];

            long expectedVal = done + elements;
            old_chunk = 0;

            try {
                for (int i = 0; i < Tasks.length; i++) {
                    final int k = i;
                    Tasks[i] = new Runnable() {
                        int ThreadID = k;
                        @Override
                        public void run() {
                            int Start = (int)((long)elements * ThreadID / ThreadCount);
                            int End = (int)((long)elements * (ThreadID + 1) / ThreadCount);

                            Start = ThreadID == 0 ? Start + 1 : Start;

                            ReferenceDecompressor referenceDecompressor = referenceDecompressors[ThreadID];

                            for(int m = Start; m < End; m++) {
                                initLStep(firstLevel, m, Ref, blaSize, epsilon, referenceDecompressor);

                                if (progress != null) {
                                    if (ThreadID == 0) {
                                        done++;
                                        long val = done;

                                        long new_chunk = val / 1000;

                                        if(old_chunk != new_chunk) {
                                            setProgress(progress, val, divisor);
                                            old_chunk = new_chunk;
                                        }
                                    } else {
                                        done++;
                                    }
                                }
                            }

                        }
                    };
                }

                for (int i = 0; i < Tasks.length; i++) {
                    futures.add(TaskDraw.la_thread_executor.submit(Tasks[i]));
                }

                for (int i = 0; i < futures.size(); i++) {
                    futures.get(i).get();
                }
            }
            catch (Exception ex) {}

            //Fix for thread race condition
            if (progress != null && expectedVal > done) {
                setProgress(progress, expectedVal, divisor);
                done = expectedVal;
                old_chunk = done / 1000;
            }
        }
        else {
            done = 0;
            for(int m = 1; m < elements; m++) {
                initLStep(firstLevel, m, Ref, blaSize, epsilon, referenceDecompressor);
                if (progress != null) {
                    done++;
                    long val = done;

                    if(val % 1000 == 0) {
                        setProgress(progress, val, divisor);
                    }
                }
            }
        }

        if (progress != null) {
            long val = done;
            setProgress(progress, val, divisor);
        }


    }

    private void setProgress(JProgressBar progress, long val, long divisor) {
        int progres_val = (int)(val / divisor);
        progress.setValue(progres_val);
        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (progres_val) / progress.getMaximum() * 100)) + "%");
    }

    private void init(DeepReference Ref, MantExp blaSize, MantExp epsilon, JProgressBar progress, long divisor) {

        int elements = elementsPerLevel[firstLevel] + 1;
        if(TaskDraw.USE_THREADS_FOR_BLA) {

            done = 0; //we dont care for race conditions

            ArrayList<Future<?>> futures = new ArrayList<>();
            final int ThreadCount = TaskDraw.la_thread_executor.getCorePoolSize();
            Runnable[] Tasks = new Runnable[ThreadCount];

            long expectedVal = done + elements;
            old_chunk = 0;

            try {
                for (int i = 0; i < Tasks.length; i++) {
                    final int k = i;
                    Tasks[i] = new Runnable() {
                        int ThreadID = k;
                        @Override
                        public void run() {
                            int Start = (int)((long)elements * ThreadID / ThreadCount);
                            int End = (int)((long)elements * (ThreadID + 1) / ThreadCount);
                            ReferenceDecompressor referenceDecompressor = referenceDecompressors[ThreadID];

                            Start = ThreadID == 0 ? Start + 1 : Start;

                            for(int m = Start; m < End; m++) {
                                initLStep(firstLevel, m , Ref, blaSize, epsilon, referenceDecompressor);
                                if (progress != null) {
                                    if (ThreadID == 0) {
                                        done++;
                                        long val = done;
                                        long new_chunk = val / 1000;

                                        if(old_chunk != new_chunk) {
                                            setProgress(progress, val, divisor);
                                            old_chunk = new_chunk;
                                        }
                                    } else {
                                        done++;
                                    }
                                }
                            }

                        }
                    };
                }

                for (int i = 0; i < Tasks.length; i++) {
                    futures.add(TaskDraw.la_thread_executor.submit(Tasks[i]));
                }

                for (int i = 0; i < futures.size(); i++) {
                    futures.get(i).get();
                }
            }
            catch (Exception ex) {}

            //Fix for thread race condition
            if (progress != null && expectedVal > done) {
                setProgress(progress, expectedVal, divisor);
                done = expectedVal;
                old_chunk = done / 1000;
            }
        }
        else {
            done = 0;
            for(int m = 1; m < elements; m++) {
                initLStep(firstLevel, m , Ref, blaSize, epsilon, referenceDecompressor);
                if (progress != null) {
                    done++;
                    long val = done;

                    if(val % 1000 == 0) {
                        setProgress(progress, val, divisor);
                    }
                }
            }
        }

        if (progress != null) {
            long val = done;
            setProgress(progress, val, divisor);
        }

    }

    private void mergeOneStep(int m, int elementsSrc, int src, int dest, double blaSize) {
        int mx = m << 1;
        int my = mx + 1;
        if (my < elementsSrc) {
            BLA x = b[src][mx];
            BLA y = b[src][my];

            b[dest][m] = mergeTwoBlas(x, y, blaSize);
        } else {
            b[dest][m] = b[src][mx];
        }
    }

    private void mergeOneStep(int m, int elementsSrc, int src, int dest, MantExp blaSize) {
        int mx = m << 1;
        int my = mx + 1;
        if (my < elementsSrc) {
            BLADeep x = bdeep[src][mx];
            BLADeep y = bdeep[src][my];

            bdeep[dest][m] = mergeTwoBlas(x, y, blaSize);
        } else {
            bdeep[dest][m] = bdeep[src][mx];
        }
    }

    private void merge(double blaSize, JProgressBar progress, long divisor) {

        boolean useThreadsForBla = TaskDraw.USE_THREADS_FOR_BLA;

        int elementsDst = 0;
        int src = firstLevel;
        int maxLevel = elementsPerLevel.length - 1;
        for (int elementsSrc = elementsPerLevel[src]; src < maxLevel && elementsSrc > 1; src++) {

            int srcp1 = src + 1;
            elementsDst = elementsPerLevel[srcp1];
            int dst = srcp1;

            final int elementsSrcFinal = elementsSrc;
            final int srcFinal = src;
            final int destFinal = dst;

            if(useThreadsForBla) {

                ArrayList<Future<?>> futures = new ArrayList<>();
                final int ThreadCount = TaskDraw.la_thread_executor.getCorePoolSize();
                Runnable[] Tasks = new Runnable[ThreadCount];

                long expectedVal = done + elementsDst;

                try {
                    for (int i = 0; i < Tasks.length; i++) {
                        final int k = i;
                        final int elementsDstFinal = elementsDst;
                        Tasks[i] = new Runnable() {
                            int ThreadID = k;
                            @Override
                            public void run() {
                                int Start = (int)((long)elementsDstFinal * ThreadID / ThreadCount);
                                int End = (int)((long)elementsDstFinal * (ThreadID + 1) / ThreadCount);

                                for(int m = Start; m < End; m++) {
                                    mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                                    if (progress != null) {
                                        if (ThreadID == 0) {
                                            done++;
                                            long val = done;
                                            long new_chunk = val / 1000;

                                            if(old_chunk != new_chunk) {
                                                setProgress(progress, val, divisor);
                                                old_chunk = new_chunk;
                                            }
                                        } else {
                                            done++;
                                        }
                                    }
                                }

                            }
                        };
                    }

                    for (int i = 0; i < Tasks.length; i++) {
                        futures.add(TaskDraw.la_thread_executor.submit(Tasks[i]));
                    }

                    for (int i = 0; i < futures.size(); i++) {
                        futures.get(i).get();
                    }
                }
                catch (Exception ex) {}

                //Fix for thread race condition
                if (progress != null && expectedVal > done) {
                    setProgress(progress, expectedVal, divisor);
                    done = expectedVal;
                    old_chunk = done / 1000;
                }
            }
            else {
               for(int m = 0; m < elementsDst; m++) {
                   mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                   if (progress != null) {
                       done++;
                       long val = done;

                       if(val % 1000 == 0) {
                           setProgress(progress, val, divisor);
                       }
                   }
                }
            }

            elementsSrc = elementsDst;
        }

        if (progress != null) {
            long val = done;
            setProgress(progress, val, divisor);
        }

    }

    private void merge(MantExp blaSize, JProgressBar progress, long divisor) {

        boolean useThreadsForBla = TaskDraw.USE_THREADS_FOR_BLA;

        int elementsDst = 0;
        int src = firstLevel;
        int maxLevel = elementsPerLevel.length - 1;
        for (int elementsSrc = elementsPerLevel[src]; src < maxLevel && elementsSrc > 1; src++) {

            int srcp1 = src + 1;
            elementsDst = elementsPerLevel[srcp1];
            int dst = srcp1;

            final int elementsSrcFinal = elementsSrc;
            final int srcFinal = src;
            final int destFinal = dst;

            if(useThreadsForBla) {

                ArrayList<Future<?>> futures = new ArrayList<>();
                final int ThreadCount = TaskDraw.la_thread_executor.getCorePoolSize();
                Runnable[] Tasks = new Runnable[ThreadCount];

                long expectedVal = done + elementsDst;

                try {
                    for (int i = 0; i < Tasks.length; i++) {
                        final int k = i;
                        final int elementsDstFinal = elementsDst;
                        Tasks[i] = new Runnable() {
                            int ThreadID = k;
                            @Override
                            public void run() {
                                int Start = (int)((long)elementsDstFinal * ThreadID / ThreadCount);
                                int End = (int)((long)elementsDstFinal * (ThreadID + 1) / ThreadCount);

                                for(int m = Start; m < End; m++) {
                                    mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                                    if (progress != null) {
                                        if (ThreadID == 0) {
                                            done++;
                                            long val = done;
                                            long new_chunk = val / 1000;

                                            if(old_chunk != new_chunk) {
                                                setProgress(progress, val, divisor);
                                                old_chunk = new_chunk;
                                            }
                                        } else {
                                            done++;
                                        }
                                    }
                                }

                            }
                        };
                    }

                    for (int i = 0; i < Tasks.length; i++) {
                        futures.add(TaskDraw.la_thread_executor.submit(Tasks[i]));
                    }

                    for (int i = 0; i < futures.size(); i++) {
                        futures.get(i).get();
                    }
                }
                catch (Exception ex) {}

                //Fix for thread race condition
                if (progress != null && expectedVal > done) {
                    setProgress(progress, expectedVal, divisor);
                    done = expectedVal;
                    old_chunk = done / 1000;
                }

            }
            else {
                for(int m = 0; m < elementsDst; m++) {
                    mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                    if (progress != null) {
                        done++;
                        long val = done;

                        if(val % 1000 == 0) {
                            setProgress(progress, val, divisor);
                        }
                    }
                }
            }

            elementsSrc = elementsDst;
        }

        if (progress != null) {
            long val = done;
            setProgress(progress, val, divisor);
        }

    }

    public void init(int M, DoubleReference Ref, double blaSize, JProgressBar progress) {
        double precision = 1 / ((double)(1L << TaskDraw.BLA_BITS));
        firstLevel = TaskDraw.BLA_STARTING_LEVEL - 1;
        isValid = false;

        this.M = M;

        int m = M - 1;

        if(m <= 0){
            return;
        }

        ArrayList<Integer> elemPerLevel = new ArrayList<>();

        long total = 1;
        int count = 1;
        for (; m > 1; m = (m + 1) >>> 1) {
            elemPerLevel.add(m);
            total += m;
            count++;
        }

        elemPerLevel.add(m);

        elementsPerLevel = elemPerLevel.stream().mapToInt(i -> i).toArray();

        finalTotal = total;
        long removedTotal = 0;

        if(firstLevel > 0) {
            for(int i = 0; i < firstLevel && i < elementsPerLevel.length; i++) {
                removedTotal += elementsPerLevel[i];
            }
            finalTotal -= removedTotal;
        }

        returnL1 = firstLevel == 0;


        long divisor = finalTotal > Constants.MAX_PROGRESS_VALUE ? finalTotal / 100 : 1;
        if(progress != null) {
            progress.setMaximum((int)(finalTotal > Constants.MAX_PROGRESS_VALUE ? 100 : finalTotal));
        }

        L = count;
        b = new BLA[count][];
        LM2 = L - 2;

        if(firstLevel >= elementsPerLevel.length){
            return;
        }

        for(int l = firstLevel; l < b.length; l++) {
            b[l] = new BLA[elementsPerLevel[l]];
        }

        if(Ref.compressed) {
            referenceDecompressor = fractal.getReferenceDecompressors()[Ref.id];
        }

        if(TaskDraw.USE_THREADS_FOR_BLA) {
            referenceDecompressors = new ReferenceDecompressor[TaskDraw.la_thread_executor.getCorePoolSize()];
            if (referenceDecompressor != null) {
                for (int i = 0; i < referenceDecompressors.length; i++) {
                    referenceDecompressors[i] = new ReferenceDecompressor(referenceDecompressor);
                }
            }
        }

        init(Ref, blaSize, precision, progress, divisor);

        merge(blaSize, progress, divisor);


//        for (int i = 0; i < L; ++i) {
//            if(b[i] != null) {
//                System.out.println(b[i][0].getL() + "\t" + Math.sqrt(b[i][0].r2));
//            }
//        }
        isValid = true;
    }

    public void init(int M, DeepReference Ref, MantExp blaSize, JProgressBar progress) {
        MantExp precision = new MantExp(1 / ((double)(1L << TaskDraw.BLA_BITS)));
        firstLevel = TaskDraw.BLA_STARTING_LEVEL - 1;
        isValid = false;

        this.M = M;

        int m = M - 1;

        if(m <= 0){
            return;
        }

        ArrayList<Integer> elemPerLevel = new ArrayList<>();

        long total = 1;
        int count = 1;
        for (; m > 1; m = (m + 1) >>> 1) {
            elemPerLevel.add(m);
            total += m;
            count++;
        }

        elemPerLevel.add(m);

        elementsPerLevel = elemPerLevel.stream().mapToInt(i -> i).toArray();

        finalTotal = total;
        long removedTotal = 0;

        if(firstLevel > 0) {
            for(int i = 0; i < firstLevel && i < elementsPerLevel.length; i++) {
                removedTotal += elementsPerLevel[i];
            }
            finalTotal -= removedTotal;
        }

        returnL1 = firstLevel == 0;

        long divisor = finalTotal > Constants.MAX_PROGRESS_VALUE ? finalTotal / 100 : 1;
        if(progress != null) {
            progress.setMaximum((int)(finalTotal > Constants.MAX_PROGRESS_VALUE ? 100 : finalTotal));
        }

        L = count;
        bdeep = new BLADeep[count][];
        LM2 = L - 2;

        if(firstLevel >= elementsPerLevel.length){
            return;
        }

        for(int l = firstLevel; l < bdeep.length; l++) {
            bdeep[l] = new BLADeep[elementsPerLevel[l]];
        }

        if(Ref.compressed) {
            referenceDecompressor = fractal.getReferenceDecompressors()[Ref.id];
        }

        if(TaskDraw.USE_THREADS_FOR_BLA) {
            referenceDecompressors = new ReferenceDecompressor[TaskDraw.la_thread_executor.getCorePoolSize()];
            if (referenceDecompressor != null) {
                for (int i = 0; i < referenceDecompressors.length; i++) {
                    referenceDecompressors[i] = new ReferenceDecompressor(referenceDecompressor);
                }
            }
        }

        init(Ref, blaSize, precision, progress, divisor);

        merge(blaSize, progress, divisor);

//        for (int i = 0; i < L; ++i) {
//            if(bdeep[i] != null) {
//                System.out.println(bdeep[i][0].getL() + "\t" + bdeep[i][0].getR2().sqrt_mutable());
//            }
//        }
        isValid = true;
    }

    public long getTotalElements() {
        return finalTotal;
    }

    public BLA lookup(int m, double z2, int iterations, int max_iterations) {
        if (m == 0) {
            return null;
        }

        BLA B = null;
        BLA tempB;
        int ix = (m - 1) >>> firstLevel;
        for (int level = firstLevel; level < L; ++level) {
            int ixm = (ix << level) + 1;
            if (m == ixm && z2 < (tempB = b[level][ix]).r2) {
                if(iterations + tempB.getL() <= max_iterations) {
                    B = tempB;
                }
            } else {
                break;
            }
            ix = ix >>> 1;
        }
        return B;
    }

    public BLA lookupBackwards(int m, double z2, int iterations, int max_iterations) {

        if (m == 0) {
            return null;
        }

        BLA tempB;

        int k = m - 1;

        if((k & 1) == 1) { // m - 1 is odd
            if (returnL1 && z2 < (tempB = b[0][k]).r2) {
                return tempB;
            }
            return null;
        }

        int zeros;
        int ix;
        if(k == 0) {
            if (z2 >= b[firstLevel][0].r2) { //k >>> firstLevel, This could be done for all K values, but it was shown through statistics that most effort is done on k == 0
                return null;
            }
            zeros = 32;
            ix = 0;
        }
        else {
            float v = (k & -k);
            zeros = (Float.floatToRawIntBits(v) >>> 23) - 0x7f;
            ix = k >>> zeros;
        }

        int startLevel = zeros <= LM2 ? zeros : LM2;
        for (int level = startLevel; level >= firstLevel; --level) {
            if (z2 < (tempB = b[level][ix]).r2) {
                if(iterations + tempB.getL() <= max_iterations) {
                    return tempB;
                }
            }
            ix = ix << 1;
        }
        return null;
    }

    public BLADeep lookup(int m, MantExp z2, int iterations, int max_iterations) {
        if (m == 0) {
            return null;
        }

        //z2 and and r2 are already reduced
        BLADeep B = null;
        BLADeep tempB;
        int ix = (m - 1) >>> firstLevel;
        for (int level = firstLevel; level < L; ++level) {
            int ixm = (ix << level) + 1;
            if (m == ixm && z2.compareToBothPositiveReduced((tempB = bdeep[level][ix]).getR2()) < 0) {
                if(iterations + tempB.getL() <= max_iterations) {
                    B = tempB;
                }
            } else {
                break;
            }
            ix = ix >>> 1;
        }
        return B;
    }

    public BLADeep lookupBackwards(int m, MantExp z2, int iterations, int max_iterations) {

        if (m == 0) {
            return null;
        }

        BLADeep tempB;

        int k = m - 1;

        if((k & 1) == 1) { // m - 1 is odd
            if (returnL1 && z2.compareToBothPositiveReduced((tempB = bdeep[0][k]).getR2()) < 0) {
                return tempB;
            }
            return null;
        }

        int zeros;
        int ix;
        if(k == 0) {
            if(z2.compareToBothPositiveReduced(bdeep[firstLevel][0].getR2()) >= 0) { //k >>> firstLevel, This could be done for all K values, but it was shown through statistics that most effort is done on k == 0
                return null;
            }

            zeros = 32;
            ix = 0;
        }
        else {
            float v = (k & -k);
            zeros = (Float.floatToRawIntBits(v) >>> 23) - 0x7f;
            ix = k >>> zeros;
        }

        int startLevel = zeros <= LM2 ? zeros : LM2;
        for (int level = startLevel; level >= firstLevel; --level) {
            if (z2.compareToBothPositiveReduced((tempB = bdeep[level][ix]).getR2()) < 0) {
                if(iterations + tempB.getL() <= max_iterations) {
                    return tempB;
                }
            }
            ix = ix << 1;
        }
        return null;
    }
}


