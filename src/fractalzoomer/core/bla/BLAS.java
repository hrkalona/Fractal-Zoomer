package fractalzoomer.core.bla;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;

import javax.swing.*;
import java.util.stream.IntStream;

import static fractalzoomer.main.Constants.BLA_CALCULATION_STR;

public class BLAS {
    public int M;
    public int L;
    public BLA[][] b;
    public BLADeep[][] bdeep;
    private Fractal fractal;

    public BLAS(Fractal fractal) {
        this.fractal = fractal;
    }

    private void initOneStep(DeepReference Ref, int m, MantExp limitFactor, MantExp blaSize, MantExp step_count) {
        MantExpComplex Z = Fractal.getArrayDeepValue(Ref, m);
        MantExpComplex A = fractal.getBlaA(Z);
        //MantExpComplex B = new MantExpComplex(1, 0);

        //double mZ = (burning_ship ? Math.min(z.getRe(), z.getIm()) : z.hypot()) * 0.5;
        MantExp mZ = Z.hypot().multiply(limitFactor);
        MantExp mA = A.hypot();
        //MantExp mB = B.hypot(); //assume that B is 1 + 0i


        //MantExp r = MantExp.max(MantExp.ZERO, mZ.subtract(mB.multiply(blaSize)).divide_mutable(mA.add_mutable(MantExp.ONE))).multiply(step_count);
        MantExp r = MantExp.max(MantExp.ZERO, mZ.subtract(blaSize).divide_mutable(mA.add_mutable(MantExp.ONE))).multiply(step_count);
        MantExp r2 = r.square();

        A.Reduce();
        r2.Reduce();

        BLADeep bla = new BLADeep1Step(r2, A);
        bdeep[0][m - 1] = bla;

    }

    private void initOneStep(double[] Ref, int m, double limitFactor, double blaSize, double step_count) {
        Complex Z = Fractal.getArrayValue(Ref, m);
        Complex A = fractal.getBlaA(Z);
        //Complex B = new Complex(1, 0);

        //double mZ = (burning_ship ? Math.min(z.getRe(), z.getIm()) : z.hypot()) * 0.5;
        double mZ = Z.hypot() * limitFactor;
        double mA = A.hypot();
        //double mB = B.hypot(); //assume that B is 1 + 0i

        //double r = Math.max(0, (mZ - mB * blaSize) / (mA + 1)) * step_count;
        double r = Math.max(0, (mZ - blaSize) / (mA + 1)) * step_count;
        double r2 = r * r;
        BLA bla = new BLA1Step(r2, A);
        b[0][m - 1] = bla;
    }

    private int done;
    private int old_chunk;
    private void init(double[] Ref, double blaSize, double step_count, boolean burning_ship, JProgressBar progress) {

        double limitFactor = ThreadDraw.BLA_LIMIT_FACTOR;

        if(ThreadDraw.USE_THREADS_FOR_BLA) {
            done = 0; //we dont care fore race condition
            long mainId = Thread.currentThread().getId();

            old_chunk = 0;
            IntStream.range(1, M).
                    parallel().forEach(m -> {
                        initOneStep(Ref, m, limitFactor, blaSize, step_count);
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

            if (progress != null) {
                int val = done;
                progress.setValue(val);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }
        else {
            done = 0;
            for(int m = 1; m < M; m++) {
                initOneStep(Ref, m, limitFactor, blaSize, step_count);
                if (progress != null) {
                    done++;
                    int val = done;

                    if(val % 1000 == 0) {
                        progress.setValue(val);
                        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                    }
                }
            }

            if (progress != null) {
                int val = done;
                progress.setValue(val);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }

        }

    }

    private void init(DeepReference Ref, MantExp blaSize, MantExp step_count, boolean burning_ship, JProgressBar progress) {

        MantExp limitFactor = new MantExp(ThreadDraw.BLA_LIMIT_FACTOR);

        if(ThreadDraw.USE_THREADS_FOR_BLA) {

            done = 0; //we dont care for race conditions
            long mainId = Thread.currentThread().getId();

            old_chunk = 0;
            IntStream.range(1, M).
                    parallel().forEach(m -> {
                        initOneStep(Ref, m, limitFactor, blaSize, step_count);
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

            if (progress != null) {
                int val = done;
                progress.setValue(val);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }
        else {
            done = 0;
            for(int m = 1; m < M; m++) {
                initOneStep(Ref, m, limitFactor, blaSize, step_count);
                if (progress != null) {
                    done++;
                    int val = done;

                    if(val % 1000 == 0) {
                        progress.setValue(val);
                        progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
                    }
                }
            }

            if (progress != null) {
                int val = done;
                progress.setValue(val);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", (int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }

    }

    private void mergeOneStep(int m, int msrc, int srcOffset, int destOffset, double blaSize) {
        int mx = m << 1;
        int my = mx + 1;
        if (my < msrc) {
            BLA x = b[0][mx + srcOffset];
            BLA y = b[0][my + srcOffset];
            int l = x.getL() + y.getL();
            // A = y.A * x.A
            Complex A = BLA.getNewA(x, y);
            // B = y.A * x.B + y.B
            Complex B = BLA.getNewB(x, y);
            double xA = x.hypotA();
            double xB = x.hypotB();
            double r = Math.min(Math.sqrt(x.r2), Math.max(0, (Math.sqrt(y.r2) - xB * blaSize) / xA));
            double r2 = r * r;
            BLA bla = new BLALStep(r2, A, B, l);
            b[0][m + destOffset] = bla;
        } else {
            b[0][m + destOffset] = b[0][mx + srcOffset];
        }
    }

    private void mergeOneStep(int m, int msrc, int srcOffset, int destOffset, MantExp blaSize) {
        int mx = m << 1;
        int my = mx + 1;
        if (my < msrc) {
            BLADeep x = bdeep[0][mx + srcOffset];
            BLADeep y = bdeep[0][my + srcOffset];
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

            BLADeep bla = new BLADeepLStep(r2, A, B, l);
            bdeep[0][m + destOffset] = bla;
        } else {
            bdeep[0][m + destOffset] = bdeep[0][mx + srcOffset];
        }
    }

    private void merge(double blaSize, JProgressBar progress) {

        int destOffset = 0;
        int srcOffset = 0;

        boolean useThreadsForBla = ThreadDraw.USE_THREADS_FOR_BLA;

        for (int msrc = M - 1; msrc > 1; msrc = (msrc + 1) >>> 1) {
            int mdst = (msrc + 1) >>> 1;
            destOffset += msrc;

            final int msrcFinal = msrc;
            final int srcOffsetFinal = srcOffset;
            final int destOffsetFinal = destOffset;

            if(useThreadsForBla) {
                IntStream.range(0, mdst).
                    parallel().forEach(m -> {
                        mergeOneStep(m, msrcFinal, srcOffsetFinal, destOffsetFinal, blaSize);
                    }
                );
            }
            else {
               for(int m = 0; m < mdst; m++) {
                   mergeOneStep(m, msrc, srcOffset, destOffset, blaSize);
                }
            }
            srcOffset += msrc;

            if(progress != null) {
                int val = progress.getValue() + mdst;
                progress.setValue(val);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }

    }

    private void merge(MantExp blaSize, JProgressBar progress) {

        boolean useThreadsForBla = ThreadDraw.USE_THREADS_FOR_BLA;

        int destOffset = 0;
        int srcOffset = 0;

        for (int msrc = M - 1; msrc > 1; msrc = (msrc + 1) >>> 1) {
            int mdst = (msrc + 1) >>> 1;
            destOffset += msrc;

            final int msrcFinal = msrc;
            final int srcOffsetFinal = srcOffset;
            final int destOffsetFinal = destOffset;

            if(useThreadsForBla) {
                IntStream.range(0, mdst).
                    parallel().forEach(m -> {
                        mergeOneStep(m, msrcFinal, srcOffsetFinal, destOffsetFinal, blaSize);
                    }
                );
            }
            else {
                for(int m = 0; m < mdst; m++) {
                    mergeOneStep(m, msrc, srcOffset, destOffset, blaSize);
                }
            }
            srcOffset += msrc;

            if(progress != null) {
                int val = progress.getValue() + mdst;
                progress.setValue(val);
                progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (val) / progress.getMaximum() * 100)) + "%");
            }
        }

    }

    public void init(int M, double[] Ref, double blaSize, boolean burning_ship, JProgressBar progress) {
        double precision = 1 / ((double)(1L << ThreadDraw.BLA_BITS));

        this.M = M;
        int total = 1;
        int count = 1;
        int m = M - 1;
        for (; m > 1; m = (m + 1) >>> 1) {
            total += m;
            count++;
        }

        if(progress != null) {
            progress.setMaximum(total);
        }

        L = count;
        b = new BLA[count][];
        b[0] = new BLA[total];
        int ix = 1;
        m = M - 1;

        init(Ref, blaSize, precision, burning_ship, progress);
        merge(blaSize, progress);

        int offset = 0;
        int length = total;
        for (; m > 1; m = (m + 1) >>> 1) {
            offset += m;

            length = length >>> 1;

            b[ix] = new BLA[length];

            System.arraycopy(b[0], offset, b[ix], 0, length);

            ix++;
        }

//        for (int i = 0; i < L; ++i) {
//            System.out.println(b[i][0].l + "\t" + Math.sqrt(b[i][0].r2));
//        }
    }

    public void init(int M, DeepReference Ref, MantExp blaSize, boolean burning_ship, JProgressBar progress) {
        MantExp precision = new MantExp(1 / ((double)(1L << ThreadDraw.BLA_BITS)));

        this.M = M;
        int total = 1;
        int count = 1;
        int m = M - 1;
        for (; m > 1; m = (m + 1) >>> 1) {
            total += m;
            count++;
        }

        if(progress != null) {
            progress.setMaximum(total);
        }

        L = count;
        bdeep = new BLADeep[count][];
        bdeep[0] = new BLADeep[total];
        int ix = 1;
        m = M - 1;

        init(Ref, blaSize, precision, burning_ship, progress);
        merge(blaSize, progress);

        int offset = 0;
        int length = total;
        for (; m > 1; m = (m + 1) >>> 1) {
            offset += m;

            length = length >>> 1;

            bdeep[ix] = new BLADeep[length];

            System.arraycopy(bdeep[0], offset, bdeep[ix], 0, length);

            ix++;
        }

//        for (int i = 0; i < L; ++i) {
//            System.out.println(bdeep[i][0].l + "\t" + bdeep[i][0].getR2().sqrt_mutable());
//        }
    }

    public BLA lookup(int m, double z2) {
        if (m == 0) {
            return null;
        }

        BLA B = null;
        BLA tempB;
        int ix = m - 1;
        for (int level = 0; level < L; ++level) {
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

    public BLADeep lookup(int m, MantExp z2) {
        if (m == 0) {
            return null;
        }

        //z2 and and r2 are already reduced
        BLADeep B = null;
        BLADeep tempB;
        int ix = m - 1;
        for (int level = 0; level < L; ++level) {
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

}
