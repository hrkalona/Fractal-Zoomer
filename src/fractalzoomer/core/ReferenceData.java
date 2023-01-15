package fractalzoomer.core;

import fractalzoomer.functions.Fractal;

import java.util.stream.IntStream;

public class ReferenceData {
    public static final int MAX_PRECALCULATED_TERMS = 10;

    public DoubleReference Reference;
    public DoubleReference ReferenceSubCp;
    public DoubleReference[] PrecalculatedTerms;
    public DoubleReference[] ReferenceSubCps;
    public int MaxRefIteration;
    public GenericComplex lastZValue;
    public GenericComplex secondTolastZValue;
    public GenericComplex thirdTolastZValue;

    public Object lastRValue;
    //public Object minValue;

    public Complex dzdc;
    public MantExpComplex mdzdc;

    public ReferenceData() {
        PrecalculatedTerms = new DoubleReference[MAX_PRECALCULATED_TERMS];
    }

    public void clear() {
        deallocate();
        MaxRefIteration = 0;
        secondTolastZValue = null;
        thirdTolastZValue = null;
        lastZValue = null;
        lastRValue = null;
        dzdc = null;
        mdzdc = null;
        //minValue = null;

    }

    public void deallocate() {
        Reference = null;
        ReferenceSubCp = null;
        for(int i = 0; i < PrecalculatedTerms.length; i++) {
            PrecalculatedTerms[i] = null;
        }
        if(ReferenceSubCps != null) {
            for (int i = 0; i < ReferenceSubCps.length; i++) {
                ReferenceSubCps[i] = null;
            }
            ReferenceSubCps = null;
        }
        Fractal.reference = null;
    }

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int precalCount) {
        create(max_iterations, needsRefSubCp, precalCount);
        Fractal.reference = Reference;
    }

    public void createAndSetShortcut(int max_iterations, int cps, int precalCount) {
        create(max_iterations, cps, precalCount);
        Fractal.reference = Reference;
    }

    public void createAndSetShortcut(int max_iterations, int cps, int[] indexes) {
        create(max_iterations, cps, indexes);
        Fractal.reference = Reference;
    }

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int[] indexes) {
        create(max_iterations, needsRefSubCp, indexes);
        Fractal.reference = Reference;
    }

    //Use this on julia ref
    public void create(int max_iterations, boolean needsRefSubCp, int precalCount) {
        int[] indexes = new int[0];
        if(precalCount > 0) {
            indexes = IntStream.range(0, precalCount).toArray();
        }
        create(max_iterations, needsRefSubCp, indexes);
    }

    public void create(int max_iterations, int cps, int precalCount) {
        int[] indexes = new int[0];
        if(precalCount > 0) {
            indexes = IntStream.range(0, precalCount).toArray();
        }
        create(max_iterations, cps, indexes);
    }

    public void create(int max_iterations, boolean needsRefSubCp, int[] indexes) {
        Reference = new DoubleReference(max_iterations);

        if(needsRefSubCp) {
            ReferenceSubCp = new DoubleReference(max_iterations);
        }

        for(int i = 0; i < indexes.length; i++) {
            if(indexes[i] < PrecalculatedTerms.length) {
                PrecalculatedTerms[indexes[i]] = new DoubleReference(max_iterations);
            }
        }
    }

    public void create(int max_iterations, int cpsCount, int[] indexes) {
        Reference = new DoubleReference(max_iterations);

        for(int i = 0; i < indexes.length; i++) {
            if(indexes[i] < PrecalculatedTerms.length) {
                PrecalculatedTerms[indexes[i]] = new DoubleReference(max_iterations);
            }
        }

        ReferenceSubCps = new DoubleReference[cpsCount];
        for(int i = 0; i < ReferenceSubCps.length; i++) {
            ReferenceSubCps[i] = new DoubleReference(max_iterations);
        }
    }

    public void setReference(DoubleReference Reference) {
        this.Reference = Reference;
        Fractal.reference = Reference;
    }

    public void resize(int max_iterations) {
        if(Reference != null) {
            Reference.resize(max_iterations);
        }

        if(ReferenceSubCp != null) {
            ReferenceSubCp.resize(max_iterations);
        }

        for(int i = 0; i < PrecalculatedTerms.length; i++) {
            if(PrecalculatedTerms[i] != null) {
                PrecalculatedTerms[i].resize(max_iterations);
            }
        }

        if(ReferenceSubCps != null) {
            for (int i = 0; i < ReferenceSubCps.length; i++) {
                if (ReferenceSubCps[i] != null) {
                    ReferenceSubCps[i].resize(max_iterations);
                }
            }
        }
    }
}
