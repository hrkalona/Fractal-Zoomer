package fractalzoomer.core.reference;

import fractalzoomer.core.Complex;
import fractalzoomer.core.GenericComplex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.reference.CompressedDoubleReference;
import fractalzoomer.core.reference.DoubleReference;
import fractalzoomer.functions.Fractal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ReferenceData {
    public static int REFERENCE_DATA_COUNT = 4;
    public static final int MAX_PRECALCULATED_TERMS = 10;
    public static final int SUBEXPRESSION_LENGTH = (1 + MAX_PRECALCULATED_TERMS);

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

    public Complex compressorZ;
    public MantExpComplex compressorZm;

    public Complex period_dzdc;
    public MantExpComplex period_mdzdc;

    public int id;

    public ReferenceData(int id) {
        PrecalculatedTerms = new DoubleReference[MAX_PRECALCULATED_TERMS];
        this.id = id;
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
        period_dzdc = null;
        period_mdzdc = null;
        //minValue = null;

    }

    public void clearWithoutDeallocation() {
        MaxRefIteration = 0;
        secondTolastZValue = null;
        thirdTolastZValue = null;
        lastZValue = null;
        lastRValue = null;
        dzdc = null;
        mdzdc = null;
        period_dzdc = null;
        period_mdzdc = null;
        //minValue = null;

    }

    public void deallocate() {
        Reference = null;
        ReferenceSubCp = null;
        Arrays.fill(PrecalculatedTerms, null);
        if(ReferenceSubCps != null) {
            Arrays.fill(ReferenceSubCps, null);
            ReferenceSubCps = null;
        }
        Fractal.reference = null;
    }

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int precalCount, boolean compression) {
        create(max_iterations, needsRefSubCp, precalCount, compression);
        Fractal.reference = Reference;
    }

    /*public void createAndSetShortcut(int max_iterations, int cps, int precalCount, boolean compression) {
        create(max_iterations, cps, precalCount, compression);
        Fractal.reference = Reference;
    }

    public void createAndSetShortcut(int max_iterations, int cps, int[] indexes, boolean compression) {
        create(max_iterations, cps, indexes, compression);
        Fractal.reference = Reference;
    }*/

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int[] indexes, boolean compression) {
        create(max_iterations, needsRefSubCp, indexes, compression);
        Fractal.reference = Reference;
    }

    //Use this on julia ref
    public void create(int max_iterations, boolean needsRefSubCp, int precalCount, boolean compression) {
        int[] indexes = new int[0];
        if(precalCount > 0) {
            indexes = IntStream.range(0, precalCount).toArray();
        }
        create(max_iterations, needsRefSubCp, indexes, compression);
    }

    /*public void create(int max_iterations, int cps, int precalCount, boolean compression) {
        int[] indexes = new int[0];
        if(precalCount > 0) {
            indexes = IntStream.range(0, precalCount).toArray();
        }
        create(max_iterations, cps, indexes, compression);
    }*/

    public void create(int max_iterations, boolean needsRefSubCp, int[] indexes, boolean compression) {
        if(compression) {
            Reference = new CompressedDoubleReference(max_iterations);
            Reference.id = id;
        }
        else {
            if (Reference == null || Reference.shouldCreateNew(max_iterations)) {
                Reference = new DoubleReference(max_iterations);
                Reference.id = id;
            } else {
                Reference.reset();
            }
        }

        if(needsRefSubCp) {
            if(compression) {
                ReferenceSubCp = new CompressedDoubleReference(max_iterations);
                ReferenceSubCp.id = id * SUBEXPRESSION_LENGTH;
            }
            else {
                if (ReferenceSubCp == null || ReferenceSubCp.shouldCreateNew(max_iterations)) {
                    ReferenceSubCp = new DoubleReference(max_iterations);
                } else {
                    ReferenceSubCp.reset();
                }
            }
        }

        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            if(index < PrecalculatedTerms.length) {
                if(compression) {
                    PrecalculatedTerms[index] = new CompressedDoubleReference(max_iterations);
                    PrecalculatedTerms[index].id = id * SUBEXPRESSION_LENGTH + (index + 1);
                }
                else {
                    if (PrecalculatedTerms[index] == null || PrecalculatedTerms[index].shouldCreateNew(max_iterations)) {
                        PrecalculatedTerms[index] = new DoubleReference(max_iterations);
                    } else {
                        PrecalculatedTerms[index].reset();
                    }
                }
            }
        }
    }

    /*public void create(int max_iterations, int cpsCount, int[] indexes, boolean compression) {
        if(compression) {
            Reference = new CompressedDoubleReference(max_iterations);
            Reference.id = id;
        }
        else {
            if (Reference == null || Reference.shouldCreateNew(max_iterations)) {
                Reference = new DoubleReference(max_iterations);
                Reference.id = id;
            } else {
                Reference.reset();
            }
        }

        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            if(index < PrecalculatedTerms.length) {
                if( PrecalculatedTerms[index] == null ||  PrecalculatedTerms[index].shouldCreateNew(max_iterations)) {
                    PrecalculatedTerms[index] = new DoubleReference(max_iterations);
                }
                else {
                    PrecalculatedTerms[index].reset();
                }
            }
        }

        if(ReferenceSubCps == null || cpsCount != ReferenceSubCps.length) {
            ReferenceSubCps = new DoubleReference[cpsCount];

            for(int i = 0; i < ReferenceSubCps.length; i++) {
                ReferenceSubCps[i] = new DoubleReference(max_iterations);
            }
        }
        else {
            for(int i = 0; i < ReferenceSubCps.length; i++) {
                if( ReferenceSubCps[i] == null ||  ReferenceSubCps[i].shouldCreateNew(max_iterations)) {
                    ReferenceSubCps[i] = new DoubleReference(max_iterations);
                }
                else {
                    ReferenceSubCps[i].reset();
                }
            }
        }

    }*/

    public void setReference(DoubleReference Reference) {
        this.Reference = Reference;
        Reference.id = id;
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

    public ArrayList<Integer> getWaypointsLength() {
        ArrayList<Integer> waypoints = new ArrayList<>();

        if(Reference != null && Reference.compressed) {
            waypoints.add(((CompressedDoubleReference)Reference).compressedLength());
        }

        if(ReferenceSubCp != null && ReferenceSubCp.compressed) {
            waypoints.add(((CompressedDoubleReference)ReferenceSubCp).compressedLength());
        }

        for(int i = 0; i < PrecalculatedTerms.length; i++) {
            if(PrecalculatedTerms[i] != null && PrecalculatedTerms[i].compressed) {
                waypoints.add(((CompressedDoubleReference)PrecalculatedTerms[i]).compressedLength());
            }
        }

        return waypoints;
    }

     public boolean exists() {
        return Reference != null;
     }
}
