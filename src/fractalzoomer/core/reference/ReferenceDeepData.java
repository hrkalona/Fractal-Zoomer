package fractalzoomer.core.reference;

import fractalzoomer.functions.Fractal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.core.reference.ReferenceOrbit.DATA_LENGTH;
import static fractalzoomer.core.reference.ReferenceOrbit.MAX_PRECALCULATED_TERMS;

public class ReferenceDeepData implements Serializable {
    private static final long serialVersionUID = -12345222259L;

    public DeepReference Reference;
    public DeepReference ReferenceSubCp;
    public DeepReference[] PrecalculatedTerms;
    //public DeepReference[] ReferenceSubCps;
    public int id;


    public ReferenceDeepData(int id) {
        PrecalculatedTerms = new DeepReference[MAX_PRECALCULATED_TERMS];
        this.id = id;
    }

    public void deallocate() {
        Reference = null;
        ReferenceSubCp = null;
        Arrays.fill(PrecalculatedTerms, null);
//        if(ReferenceSubCps != null) {
//            Arrays.fill(ReferenceSubCps, null);
//            ReferenceSubCps = null;
//        }
        Fractal.referenceDeep = null;
    }

    public void createAndSetShortcut(int max_iterations, boolean needsRefSubCp, int[] indexes, boolean compress) {
        create(max_iterations, needsRefSubCp, indexes, compress);
        Fractal.referenceDeep = Reference;
    }

    /*public void createAndSetShortcut(int max_iterations, int cps, int precalCount, boolean compression) {
        create(max_iterations, cps, precalCount, compression);
        Fractal.referenceDeep = Reference;
    }

    public void createAndSetShortcut(int max_iterations, int cps, int[] indexes, boolean compression) {
        create(max_iterations, cps, indexes, compression);
        Fractal.referenceDeep = Reference;
    }*/

    /*public void create(int max_iterations, int cps, int precalCount, boolean compression) {
        int[] indexes = new int[0];
        if(precalCount > 0) {
            indexes = IntStream.range(0, precalCount).toArray();
        }
        create(max_iterations, cps, indexes, compression);
    }*/

    //Use this on julia ref
    public void create(int max_iterations, boolean needsRefSubCp, int[] indexes, boolean compression) {

        if(compression) {
            Reference = new CompressedDeepReference(max_iterations, ReferenceType.NORMAL);
            Reference.id = id * DATA_LENGTH;
        }
        else {
            if (Reference == null || Reference.shouldCreateNew(max_iterations)) {
                Reference = new DeepReference(max_iterations, ReferenceType.NORMAL);
                Reference.id = id * DATA_LENGTH;
            } else {
                Reference.reset();
            }
        }

        if(needsRefSubCp) {
            if(compression) {
                ReferenceSubCp = new CompressedDeepReference(max_iterations, ReferenceType.CP);
                ReferenceSubCp.id = id * DATA_LENGTH + 1;
            }
            else {
                if (ReferenceSubCp == null || ReferenceSubCp.shouldCreateNew(max_iterations)) {
                    ReferenceSubCp = new DeepReference(max_iterations, ReferenceType.CP);
                    ReferenceSubCp.id = id * DATA_LENGTH + 1;
                } else {
                    ReferenceSubCp.reset();
                }
            }
        }

        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            if(index < PrecalculatedTerms.length) {
                if(compression) {
                    PrecalculatedTerms[index] = new CompressedDeepReference(max_iterations, ReferenceType.EXPRESSION);
                    PrecalculatedTerms[index].id = id * DATA_LENGTH + (index + 2);
                }
                else {
                    if (PrecalculatedTerms[index] == null || PrecalculatedTerms[index].shouldCreateNew(max_iterations)) {
                        PrecalculatedTerms[index] = new DeepReference(max_iterations, ReferenceType.EXPRESSION);
                        PrecalculatedTerms[index].id = id * DATA_LENGTH + (index + 2);
                    } else {
                        PrecalculatedTerms[index].reset();
                    }
                }
            }
        }
    }

    /*public void create(int max_iterations, int cpsCount, int[] indexes, boolean compression) {
        if(compression) {
            Reference = new CompressedDeepReference(max_iterations);
            Reference.id = id;
        }
        else {
            if (Reference == null || Reference.shouldCreateNew(max_iterations)) {
                Reference = new DeepReference(max_iterations);
                Reference.id = id;
            } else {
                Reference.reset();
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
    }*/

    public void setReference(DeepReference Reference) {
        this.Reference = Reference;
        Reference.id = id;
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

//        if(ReferenceSubCps != null) {
//            for (int i = 0; i < ReferenceSubCps.length; i++) {
//                if (ReferenceSubCps[i] != null) {
//                    ReferenceSubCps[i].resize(max_iterations);
//                }
//            }
//        }
    }

    public ArrayList<Integer> getWaypointsLength() {
        ArrayList<Integer> waypoints = new ArrayList<>();

        if(Reference != null && Reference.compressed) {
            waypoints.add(((CompressedDeepReference)Reference).compressedLength());
        }

        if(ReferenceSubCp != null && ReferenceSubCp.compressed) {
            waypoints.add(((CompressedDeepReference)ReferenceSubCp).compressedLength());
        }

        for(int i = 0; i < PrecalculatedTerms.length; i++) {
            if(PrecalculatedTerms[i] != null && PrecalculatedTerms[i].compressed) {
                waypoints.add(((CompressedDeepReference)PrecalculatedTerms[i]).compressedLength());
            }
        }

        return waypoints;
    }

    public boolean exists() {
        return Reference != null;
    }
}
