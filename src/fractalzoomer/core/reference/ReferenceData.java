package fractalzoomer.core.reference;

import fractalzoomer.functions.Fractal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.core.reference.ReferenceOrbit.DATA_LENGTH;
import static fractalzoomer.core.reference.ReferenceOrbit.MAX_PRECALCULATED_TERMS;

public class ReferenceData implements Serializable {
    private static final long serialVersionUID = -6978567595222259L;

    public DoubleReference Reference;
    public DoubleReference ReferenceSubCp;
    public DoubleReference[] PrecalculatedTerms;
    //public DoubleReference[] ReferenceSubCps;

    public int id;

    public ReferenceData(int id) {
        PrecalculatedTerms = new DoubleReference[MAX_PRECALCULATED_TERMS];
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
        Fractal.reference = null;
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

    /*public void create(int max_iterations, int cps, int precalCount, boolean compression) {
        int[] indexes = new int[0];
        if(precalCount > 0) {
            indexes = IntStream.range(0, precalCount).toArray();
        }
        create(max_iterations, cps, indexes, compression);
    }*/

    public void create(int max_iterations, boolean needsRefSubCp, int[] indexes, boolean compression) {
        if(compression) {
            Reference = new CompressedDoubleReference(max_iterations, ReferenceType.NORMAL);
            Reference.id = id * DATA_LENGTH;
        }
        else {
            if (Reference == null || Reference.shouldCreateNew(max_iterations)) {
                Reference = new DoubleReference(max_iterations, ReferenceType.NORMAL);
                Reference.id = id * DATA_LENGTH;
            } else {
                Reference.reset();
            }
        }

        if(needsRefSubCp) {
            if(compression) {
                ReferenceSubCp = new CompressedDoubleReference(max_iterations, ReferenceType.CP);
                ReferenceSubCp.id = id * DATA_LENGTH + 1;
            }
            else {
                if (ReferenceSubCp == null || ReferenceSubCp.shouldCreateNew(max_iterations)) {
                    ReferenceSubCp = new DoubleReference(max_iterations, ReferenceType.CP);
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
                    PrecalculatedTerms[index] = new CompressedDoubleReference(max_iterations, ReferenceType.EXPRESSION);
                    PrecalculatedTerms[index].id = id * DATA_LENGTH + (index + 2);
                }
                else {
                    if (PrecalculatedTerms[index] == null || PrecalculatedTerms[index].shouldCreateNew(max_iterations)) {
                        PrecalculatedTerms[index] = new DoubleReference(max_iterations, ReferenceType.EXPRESSION);
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
