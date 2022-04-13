package fractalzoomer.core.bla;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static fractalzoomer.main.Constants.BLA_CALCULATION_STR;

public class BLAS {
    public int M;
    public int L;
    private int LM1;
    public BLA[][] b;
    public BLADeep[][] bdeep;
    private Fractal fractal;

    private int[] elementsPerLevel;
    public static boolean MEMORY_PACKING = false;
    private static final int MEM_PACKING_LIMIT = 100000000;

    private int firstLevel;
    private boolean returnL1;

    public BLAS(Fractal fractal) {
        this.fractal = fractal;
    }

    private BLADeep createOneStep(DeepReference Ref, int m, MantExp blaSize, MantExp epsilon) {
        MantExpComplex Z = Fractal.getArrayDeepValue(Ref, m);
        MantExpComplex A = fractal.getBlaA(Z);
        //MantExpComplex B = new MantExpComplex(1, 0);

        MantExp mA = A.hypot();
        //MantExp mB = B.hypot(); //assume that B is 1 + 0i

        MantExp r_nonliner = mA.multiply(epsilon).subtract_mutable(blaSize.divide(mA));
        MantExp r = MantExp.max(MantExp.ZERO, r_nonliner);
        MantExp r2 = r.square();

        A.Reduce();
        r2.Reduce();

        return new BLADeep1Step(r2, A);
    }

    private void initLStep(int level, int m, double[] Ref, double blaSize, double epsilon) {

        b[level][m - 1] = createLStep(level, m, Ref, blaSize, epsilon);

    }

    private void initLStep(int level, int m, DeepReference Ref, MantExp blaSize, MantExp epsilon) {

        bdeep[level][m - 1] = createLStep(level, m, Ref, blaSize, epsilon);

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
        return BLAGenericStep.getGenericStep(r2, A, B, l);
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

        A.Reduce();
        B.Reduce();
        r2.Reduce();

        return BLADeepGenericStep.getGenericStep(r2, A, B, l);
    }

    private BLA createLStep(int level, int m, double[] Ref, double blaSize, double epsilon) {

        if(level == 0) {
            return createOneStep(Ref, m, blaSize, epsilon);
        }

        int m2 = m << 1;
        int mx = m2 - 1;
        int my = m2;
        int levelm1 = level - 1;
        if (my <= elementsPerLevel[levelm1]) {

            BLA x = createLStep(levelm1, mx, Ref, blaSize, epsilon);

            BLA y = createLStep(levelm1, my, Ref, blaSize, epsilon);

            return mergeTwoBlas(x, y, blaSize);
        } else {
            return createLStep(levelm1, mx, Ref, blaSize, epsilon);
        }
    }


    private BLADeep createLStep(int level, int m, DeepReference Ref, MantExp blaSize, MantExp epsilon) {

        if(level == 0) {
            return createOneStep(Ref, m, blaSize, epsilon);
        }

        int m2 = m << 1;
        int mx = m2 - 1;
        int my = m2;
        int levelm1 = level - 1;
        if (my <= elementsPerLevel[levelm1]) {
            BLADeep x = createLStep(levelm1, mx, Ref, blaSize, epsilon);

            BLADeep y = createLStep(levelm1, my, Ref, blaSize, epsilon);

            return mergeTwoBlas(x, y, blaSize);
        } else {
            return createLStep(levelm1, mx, Ref, blaSize, epsilon);
        }
    }

    private BLA createOneStep(double[] Ref, int m, double blaSize, double epsilon) {
        Complex Z = Fractal.getArrayValue(Ref, m);
        Complex A = fractal.getBlaA(Z);
        //Complex B = new Complex(1, 0);

        double mA = A.hypot();
        //double mB = B.hypot(); //assume that B is 1 + 0i


        //epsilon * mA - (mB  * blaSize) / mA;
        double r_nonliner = mA * epsilon - blaSize / mA;

        double r = Math.max(0, r_nonliner);

        double r2 = r * r;

        return new BLA1Step(r2, A);
    }

    private int done;
    private int old_chunk;
    private void init(double[] Ref, double blaSize, double epsilon, JProgressBar progress) {

        int elements = elementsPerLevel[firstLevel] + 1;
        if(ThreadDraw.USE_THREADS_FOR_BLA) {
            done = 0; //we dont care fore race condition
            long mainId = Thread.currentThread().getId();

            int expectedVal = done + elements;

            old_chunk = 0;
            IntStream.range(1, elements).
                    parallel().forEach(m -> {
                        initLStep(firstLevel, m, Ref, blaSize, epsilon);
                        if (progress != null) {
                            if (Thread.currentThread().getId() == mainId) {
                                done++;
                                int val = done;

                                int new_chunk = val / 1000;

                                if(old_chunk != new_chunk) {
                                    progress.setValue(val);
                                    progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                                    old_chunk = new_chunk;
                                }
                            } else {
                                done++;
                            }
                        }
                    });

            //Fix for thread race condition
            if (progress != null && expectedVal > done) {
                progress.setValue(expectedVal);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (expectedVal) / progress.getMaximum() * 100)) + "%");
                done = expectedVal;
                old_chunk = done / 1000;
            }
        }
        else {
            done = 0;
            for(int m = 1; m < elements; m++) {
                initLStep(firstLevel, m, Ref, blaSize, epsilon);
                if (progress != null) {
                    done++;
                    int val = done;

                    if(val % 1000 == 0) {
                        progress.setValue(val);
                        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                    }
                }
            }
        }

        if (progress != null) {
            int val = done;
            progress.setValue(val);
            progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
        }



    }

    private void init(DeepReference Ref, MantExp blaSize, MantExp epsilon, JProgressBar progress) {

        int elements = elementsPerLevel[firstLevel] + 1;
        if(ThreadDraw.USE_THREADS_FOR_BLA) {

            done = 0; //we dont care for race conditions
            long mainId = Thread.currentThread().getId();

            old_chunk = 0;

            int expectedVal = done + elements;

            IntStream.range(1, elements).
                    parallel().forEach(m -> {
                        initLStep(firstLevel, m , Ref, blaSize, epsilon);
                        if (progress != null) {
                            if (Thread.currentThread().getId() == mainId) {
                                done++;
                                int val = done;
                                int new_chunk = val / 1000;

                                if(old_chunk != new_chunk) {
                                    progress.setValue(val);
                                    progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                                    old_chunk = new_chunk;
                                }
                            } else {
                                done++;
                            }
                        }
                        }
                    );

            //Fix for thread race condition
            if (progress != null && expectedVal > done) {
                progress.setValue(expectedVal);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (expectedVal) / progress.getMaximum() * 100)) + "%");
                done = expectedVal;
                old_chunk = done / 1000;
            }
        }
        else {
            done = 0;
            for(int m = 1; m < elements; m++) {
                initLStep(firstLevel, m , Ref, blaSize, epsilon);
                if (progress != null) {
                    done++;
                    int val = done;

                    if(val % 1000 == 0) {
                        progress.setValue(val);
                        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                    }
                }
            }
        }

        if (progress != null) {
            int val = done;
            progress.setValue(val);
            progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
        }

    }

    private void mergeOneStep(int m, int elementsSrc, int src, int dest, double blaSize) {
        int mx = m << 1;
        int my = mx + 1;
        if (my < elementsSrc) {
            BLA x = b[src][mx];
            BLA y = b[src][my];

            b[dest][m] = mergeTwoBlas(x, y, blaSize);;
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

    private void merge(double blaSize, JProgressBar progress) {

        boolean useThreadsForBla = ThreadDraw.USE_THREADS_FOR_BLA;

        long mainId = Thread.currentThread().getId();
        if(progress != null) {
            done = progress.getValue();
            old_chunk = done / 1000;
        }

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

                int expectedVal = done + elementsDst;

                IntStream.range(0, elementsDst).
                    parallel().forEach(m -> {
                        mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                            if (progress != null) {
                                if (Thread.currentThread().getId() == mainId) {
                                    done++;
                                    int val = done;
                                    int new_chunk = val / 1000;

                                    if(old_chunk != new_chunk) {
                                        progress.setValue(val);
                                        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                                        old_chunk = new_chunk;
                                    }
                                } else {
                                    done++;
                                }
                            }
                    }
                );

                //Fix for thread race condition
                if (progress != null && expectedVal > done) {
                    progress.setValue(expectedVal);
                    progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (expectedVal) / progress.getMaximum() * 100)) + "%");
                    done = expectedVal;
                    old_chunk = done / 1000;
                }
            }
            else {
               for(int m = 0; m < elementsDst; m++) {
                   mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                   if (progress != null) {
                       done++;
                       int val = done;

                       if(val % 1000 == 0) {
                           progress.setValue(val);
                           progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                       }
                   }
                }
            }

            elementsSrc = elementsDst;
        }

        if (progress != null) {
            int val = done;
            progress.setValue(val);
            progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
        }

    }

    private void merge(MantExp blaSize, JProgressBar progress) {

        boolean useThreadsForBla = ThreadDraw.USE_THREADS_FOR_BLA;

        long mainId = Thread.currentThread().getId();
        if(progress != null) {
            done = progress.getValue();
            old_chunk = done / 1000;
        }

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

                int expectedVal = done + elementsDst;

                IntStream.range(0, elementsDst).
                    parallel().forEach(m -> {
                            mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                            if (progress != null) {
                                if (Thread.currentThread().getId() == mainId) {
                                    done++;
                                    int val = done;
                                    int new_chunk = val / 1000;

                                    if(old_chunk != new_chunk) {
                                        progress.setValue(val);
                                        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                                        old_chunk = new_chunk;
                                    }
                                } else {
                                    done++;
                                }
                            }
                    }
                );

                //Fix for thread race condition
                if (progress != null && expectedVal > done) {
                    progress.setValue(expectedVal);
                    progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (expectedVal) / progress.getMaximum() * 100)) + "%");
                    done = expectedVal;
                    old_chunk = done / 1000;
                }

            }
            else {
                for(int m = 0; m < elementsDst; m++) {
                    mergeOneStep(m, elementsSrcFinal, srcFinal, destFinal, blaSize);

                    if (progress != null) {
                        done++;
                        int val = done;

                        if(val % 1000 == 0) {
                            progress.setValue(val);
                            progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                        }
                    }
                }
            }

            elementsSrc = elementsDst;
        }

        if (progress != null) {
            int val = done;
            progress.setValue(val);
            progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
        }

    }

    public void init(int M, double[] Ref, double blaSize, JProgressBar progress) {
        double precision = 1 / ((double)(1L << ThreadDraw.BLA_BITS));
        firstLevel = ThreadDraw.BLA_STARTING_LEVEL - 1;

        if(M >= MEM_PACKING_LIMIT) {
            MEMORY_PACKING = true;
        }
        else {
            MEMORY_PACKING = false;
        }

        this.M = M;

        int m = M - 1;

        if(m <= 0){
            return;
        }

        ArrayList<Integer> elemPerLevel = new ArrayList<>();

        int total = 1;
        int count = 1;
        for (; m > 1; m = (m + 1) >>> 1) {
            elemPerLevel.add(m);
            total += m;
            count++;
        }

        elemPerLevel.add(m);

        elementsPerLevel = elemPerLevel.stream().mapToInt(i -> i).toArray();

        int finalTotal = total;
        int removedTotal = 0;

        if(firstLevel > 0) {
            for(int i = 0; i < firstLevel && i < elementsPerLevel.length; i++) {
                removedTotal += elementsPerLevel[i];
            }
            finalTotal -= removedTotal;
        }

        returnL1 = firstLevel == 0;

        if(progress != null) {
            progress.setMaximum(finalTotal);
        }

        L = count;
        b = new BLA[count][];
        LM1 = L - 1;

        if(firstLevel >= elementsPerLevel.length){
            return;
        }

        for(int l = firstLevel; l < b.length; l++) {
            b[l] = new BLA[elementsPerLevel[l]];
        }

        init(Ref, blaSize, precision, progress);

        merge(blaSize, progress);


//        for (int i = 0; i < L; ++i) {
//            if(b[i] != null) {
//                System.out.println(b[i][0].getL() + "\t" + Math.sqrt(b[i][0].r2));
//            }
//        }
    }

    public void init(int M, DeepReference Ref, MantExp blaSize, JProgressBar progress) {
        MantExp precision = new MantExp(1 / ((double)(1L << ThreadDraw.BLA_BITS)));
        firstLevel = ThreadDraw.BLA_STARTING_LEVEL - 1;

        if(M >= MEM_PACKING_LIMIT) {
            MEMORY_PACKING = true;
        }
        else {
            MEMORY_PACKING = false;
        }

        this.M = M;

        int m = M - 1;

        if(m <= 0){
            return;
        }

        ArrayList<Integer> elemPerLevel = new ArrayList<>();

        int total = 1;
        int count = 1;
        for (; m > 1; m = (m + 1) >>> 1) {
            elemPerLevel.add(m);
            total += m;
            count++;
        }

        elemPerLevel.add(m);

        elementsPerLevel = elemPerLevel.stream().mapToInt(i -> i).toArray();

        int finalTotal = total;
        int removedTotal = 0;

        if(firstLevel > 0) {
            for(int i = 0; i < firstLevel && i < elementsPerLevel.length; i++) {
                removedTotal += elementsPerLevel[i];
            }
            finalTotal -= removedTotal;
        }

        returnL1 = firstLevel == 0;

        if(progress != null) {
            progress.setMaximum(finalTotal);
        }

        L = count;
        bdeep = new BLADeep[count][];
        LM1 = L - 1;

        if(firstLevel >= elementsPerLevel.length){
            return;
        }

        for(int l = firstLevel; l < bdeep.length; l++) {
            bdeep[l] = new BLADeep[elementsPerLevel[l]];
        }

        init(Ref, blaSize, precision, progress);

        merge(blaSize, progress);

//        for (int i = 0; i < L; ++i) {
//            if(bdeep[i] != null) {
//                System.out.println(bdeep[i][0].getL() + "\t" + bdeep[i][0].getR2().sqrt_mutable());
//            }
//        }
    }

    public BLA lookup(int m, double z2) {
        if (m == 0) {
            return null;
        }

        BLA B = null;
        BLA tempB;
        int ix = (m - 1) >>> firstLevel;
        for (int level = firstLevel; level < L; ++level) {
            int ixm = (ix << level) + 1;
            if (m == ixm && z2 < (tempB = b[level][ix]).r2) {
                B = tempB;
            } else {
                break;
            }
            ix = ix >>> 1;
        }
        return B;
    }

    public BLA lookupBackwards(int m, double z2) {

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
        if(k == 0) {
            zeros = 32;
        }
        else {
            float v = (k & -k);
            zeros = (Float.floatToRawIntBits(v) >>> 23) - 0x7f;
        }

        int ix = k >>> zeros;

        int startLevel = zeros <= LM1 ? zeros : LM1;
        for (int level = startLevel; level >= firstLevel; --level) {
            if (z2 < (tempB = b[level][ix]).r2) {
                return tempB;
            }
            ix = ix << 1;
        }
        return null;
    }

    public BLADeep lookup(int m, MantExp z2) {
        if (m == 0) {
            return null;
        }

        //z2 and and r2 are already reduced
        BLADeep B = null;
        BLADeep tempB;
        int ix = (m - 1) >>> firstLevel;
        for (int level = firstLevel; level < L; ++level) {
            int ixm = (ix << level) + 1;
            if (m == ixm && z2.compareToReduced((tempB = bdeep[level][ix]).getR2()) < 0) {
                B = tempB;
            } else {
                break;
            }
            ix = ix >>> 1;
        }
        return B;
    }

    public BLADeep lookupBackwards(int m, MantExp z2) {

        if (m == 0) {
            return null;
        }

        BLADeep tempB;

        int k = m - 1;

        if((k & 1) == 1) { // m - 1 is odd
            if (returnL1 && z2.compareToReduced((tempB = bdeep[0][k]).getR2()) < 0) {
                return tempB;
            }
            return null;
        }

        int zeros;
        if(k == 0) {
            zeros = 32;
        }
        else {
            float v = (k & -k);
            zeros = (Float.floatToRawIntBits(v) >>> 23) - 0x7f;
        }

        int ix = k >>> zeros;

        int startLevel = zeros <= LM1 ? zeros : LM1;
        for (int level = startLevel; level >= firstLevel; --level) {
            if (z2.compareToReduced((tempB = bdeep[level][ix]).getR2()) < 0) {
                return tempB;
            }
            ix = ix << 1;
        }
        return null;
    }
}


