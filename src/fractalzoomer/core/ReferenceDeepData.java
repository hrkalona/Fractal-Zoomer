package fractalzoomer.core;

import fractalzoomer.functions.Fractal;

import java.util.stream.IntStream;

import static fractalzoomer.core.ReferenceData.MAX_PRECALCULATED_TERMS;

public class ReferenceDeepData {

    public DeepReference Reference;
    public DeepReference ReferenceSubCp;
    public DeepReference[] PrecalculatedTerms;
    public DeepReference[] ReferenceSubCps;


    public ReferenceDeepData() {
        PrecalculatedTerms = new DeepReference[MAX_PRECALCULATED_TERMS];
    }

    public void clear() {

        deallocate();

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
        Fractal.referenceDeep = null;
    }

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int precalCount) {
        create(max_iterations, needsRefSubCp, precalCount);
        Fractal.referenceDeep = Reference;
    }

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int[] indexes) {
        create(max_iterations, needsRefSubCp, indexes);
        Fractal.referenceDeep = Reference;
    }

    public void createAndSetShortcut(int max_iterations, int cps, int precalCount) {
        create(max_iterations, cps, precalCount);
        Fractal.referenceDeep = Reference;
    }

    public void createAndSetShortcut(int max_iterations, int cps, int[] indexes) {
        create(max_iterations, cps, indexes);
        Fractal.referenceDeep = Reference;
    }

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

    //Use this on julia ref
    public void create(int max_iterations, boolean needsRefSubCp, int[] indexes) {
        if(Reference == null || Reference.shouldCreateNew(max_iterations)) {
            Reference = new DeepReference(max_iterations);
        }
        else {
            Reference.reset();
        }

        if(needsRefSubCp) {
            if(ReferenceSubCp == null || ReferenceSubCp.shouldCreateNew(max_iterations)) {
                ReferenceSubCp = new DeepReference(max_iterations);
            }
            else {
                ReferenceSubCp.reset();
            }
        }

        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            if(index < PrecalculatedTerms.length) {
                if( PrecalculatedTerms[index] == null ||  PrecalculatedTerms[index].shouldCreateNew(max_iterations)) {
                    PrecalculatedTerms[index] = new DeepReference(max_iterations);
                }
                else {
                    PrecalculatedTerms[index].reset();
                }
            }
        }
    }

    public void create(int max_iterations, int cpsCount, int[] indexes) {
        if(Reference == null || Reference.shouldCreateNew(max_iterations)) {
            Reference = new DeepReference(max_iterations);
        }
        else {
            Reference.reset();
        }

        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            if(index < PrecalculatedTerms.length) {
                if( PrecalculatedTerms[index] == null ||  PrecalculatedTerms[index].shouldCreateNew(max_iterations)) {
                    PrecalculatedTerms[index] = new DeepReference(max_iterations);
                }
                else {
                    PrecalculatedTerms[index].reset();
                }
            }
        }

        if(ReferenceSubCps == null || cpsCount != ReferenceSubCps.length) {
            ReferenceSubCps = new DeepReference[cpsCount];

            for(int i = 0; i < ReferenceSubCps.length; i++) {
                ReferenceSubCps[i] = new DeepReference(max_iterations);
            }
        }
        else {
            for(int i = 0; i < ReferenceSubCps.length; i++) {
                if( ReferenceSubCps[i] == null ||  ReferenceSubCps[i].shouldCreateNew(max_iterations)) {
                    ReferenceSubCps[i] = new DeepReference(max_iterations);
                }
                else {
                    ReferenceSubCps[i].reset();
                }
            }
        }
    }

    public void setReference(DeepReference Reference) {
        this.Reference = Reference;
        Fractal.referenceDeep = Reference;
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
