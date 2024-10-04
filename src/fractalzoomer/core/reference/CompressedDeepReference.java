package fractalzoomer.core.reference;

import fractalzoomer.core.*;

import java.util.ArrayList;
import java.util.Arrays;

public class CompressedDeepReference extends DeepReference {
    private ArrayList<Waypoint> wayPointsList;

    private ArrayList<Integer> rebasesList;

    private int[] rebases;
    private boolean[] rebase;
    private double[] wayPointMantissaRe;
    private double[] wayPointMantissaIm;

    private long[] wayPointExpRe;
    private long[] wayPointExpIm;
    private int[] wayPointIteration;

    private boolean compressed_extended;

    public CompressedDeepReference(int length) {
        super();
        wayPointsList = new ArrayList<>();
        rebasesList = new ArrayList<>();
        rebases = new int[0];
        rebase = new boolean[0];
        wayPointMantissaRe = new double[0];
        wayPointMantissaIm = new double[0];
        wayPointExpRe = new long[0];
        wayPointExpIm = new long[0];
        wayPointIteration = new int[0];
        this.length = length;
        compressed = true;
        compressed_extended = false;
    }

    public CompressedDeepReference(int length, int lengthOverride) {
        wayPointsList = new ArrayList<>();
        rebasesList = new ArrayList<>();
        rebases = new int[0];
        rebase = new boolean[0];
        wayPointMantissaRe = new double[0];
        wayPointMantissaIm = new double[0];
        wayPointExpRe = new long[0];
        wayPointExpIm = new long[0];
        wayPointIteration = new int[0];
        this.length = length;
        this.lengthOverride = lengthOverride;
        compressed = true;
        compressed_extended = false;
    }

    public void setCompressedExtended(boolean value) {
        compressed_extended = value;
    }

    public boolean isCompressedExtended() {
        return compressed_extended;
    }

    public void addWaypoint(Waypoint w) {
        wayPointsList.add(w);
    }

    public void compact() {
        if(wayPointIteration.length == 0 && !wayPointsList.isEmpty()) {
            int length = wayPointsList.size();
            wayPointMantissaRe = new double[length];
            wayPointMantissaIm = new double[length];
            wayPointExpRe = new long[length];
            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                wayPointExpIm = new long[length];
            }
            wayPointIteration = new int[length];
            for(int i = 0; i < length; i++) {
                Waypoint w = wayPointsList.get(i);
                MantExpComplex mz = w.mz;
                wayPointMantissaRe[i] = mz.getMantissaReal();
                wayPointMantissaIm[i] = mz.getMantissaImag();
                wayPointExpRe[i] = mz.getExp();
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    wayPointExpIm[i] = mz.getExpImag();
                }
                wayPointIteration[i] = w.iteration;
            }
        }
        else if (!wayPointsList.isEmpty()){
            int oldLength = wayPointIteration.length;
            int newLength = oldLength + wayPointsList.size();
            wayPointMantissaRe = Arrays.copyOf(wayPointMantissaRe, newLength);
            wayPointMantissaIm = Arrays.copyOf(wayPointMantissaIm, newLength);
            wayPointExpRe = Arrays.copyOf(wayPointExpRe, newLength);
            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                wayPointExpIm = Arrays.copyOf(wayPointExpIm, newLength);
            }
            wayPointIteration = Arrays.copyOf(wayPointIteration, newLength);

            for(int i = 0, j = oldLength; i < wayPointsList.size(); i++, j++) {
                Waypoint w = wayPointsList.get(i);
                MantExpComplex mz = w.mz;
                wayPointMantissaRe[j] = mz.getMantissaReal();
                wayPointMantissaIm[j] = mz.getMantissaImag();
                wayPointExpRe[j] = mz.getExp();
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    wayPointExpIm[j] = mz.getExpImag();
                }
                wayPointIteration[j] = w.iteration;
            }
        }

        wayPointsList.clear();
    }

    public void compactExtended() {
        if(wayPointIteration.length == 0 && !wayPointsList.isEmpty()) {
            int length = wayPointsList.size();
            wayPointMantissaRe = new double[length];
            wayPointMantissaIm = new double[length];
            wayPointExpRe = new long[length];
            rebase = new boolean[length];
            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                wayPointExpIm = new long[length];
            }
            wayPointIteration = new int[length];
            for(int i = 0; i < length; i++) {
                Waypoint w = wayPointsList.get(i);
                MantExpComplex mz = w.mz;
                wayPointMantissaRe[i] = mz.getMantissaReal();
                wayPointMantissaIm[i] = mz.getMantissaImag();
                wayPointExpRe[i] = mz.getExp();
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    wayPointExpIm[i] = mz.getExpImag();
                }
                wayPointIteration[i] = w.iteration;
                rebase[i] = w.rebase;
            }
        }
        else if (!wayPointsList.isEmpty()){
            int oldLength = wayPointIteration.length;
            int newLength = oldLength + wayPointsList.size();
            wayPointMantissaRe = Arrays.copyOf(wayPointMantissaRe, newLength);
            wayPointMantissaIm = Arrays.copyOf(wayPointMantissaIm, newLength);
            wayPointExpRe = Arrays.copyOf(wayPointExpRe, newLength);
            rebase = Arrays.copyOf(rebase, newLength);
            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                wayPointExpIm = Arrays.copyOf(wayPointExpIm, newLength);
            }
            wayPointIteration = Arrays.copyOf(wayPointIteration, newLength);

            for(int i = 0, j = oldLength; i < wayPointsList.size(); i++, j++) {
                Waypoint w = wayPointsList.get(i);
                MantExpComplex mz = w.mz;
                wayPointMantissaRe[j] = mz.getMantissaReal();
                wayPointMantissaIm[j] = mz.getMantissaImag();
                wayPointExpRe[j] = mz.getExp();
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    wayPointExpIm[j] = mz.getExpImag();
                }
                wayPointIteration[j] = w.iteration;
                rebase[j] = w.rebase;
            }
        }

        wayPointsList.clear();

        if(rebases.length == 0 && !rebasesList.isEmpty()) {
            int length = rebasesList.size();
            rebases = new int[length];

            for(int i = 0; i < length; i++) {
                rebases[i] = rebasesList.get(i);
            }
        }
        else if (!rebasesList.isEmpty()){
            int oldLength = rebases.length;
            int newLength = oldLength + rebasesList.size();
            rebases = Arrays.copyOf(rebases, newLength);

            for(int i = 0, j = oldLength; i < rebasesList.size(); i++, j++) {
                rebases[j] = rebasesList.get(i);
            }
        }

        rebasesList.clear();
    }

    public int compressedLength() {
        return wayPointIteration.length;
    }

    public int getWaypointIteration(int compressed_index) {
        return wayPointIteration[compressed_index];
    }

    public boolean useWaypoint(int compressed_index, int iteration) {
        return compressed_index < wayPointIteration.length && wayPointIteration[compressed_index] == iteration;
    }

    public MantExpComplex getWaypointData(int compressed_index) {
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new MantExpComplexFull(wayPointExpRe[compressed_index], wayPointExpIm[compressed_index], wayPointMantissaRe[compressed_index], wayPointMantissaIm[compressed_index]);
        }
        return new MantExpComplex(wayPointExpRe[compressed_index], wayPointMantissaRe[compressed_index], wayPointMantissaIm[compressed_index]);
    }

    public Waypoint getWaypoint(int compressed_index) {

        MantExpComplex z;
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            z = new MantExpComplexFull(wayPointExpRe[compressed_index], wayPointExpIm[compressed_index], wayPointMantissaRe[compressed_index], wayPointMantissaIm[compressed_index]);
        }
        else {
           z = new MantExpComplex(wayPointExpRe[compressed_index], wayPointMantissaRe[compressed_index], wayPointMantissaIm[compressed_index]);
        }
        return new Waypoint(z, wayPointIteration[compressed_index]);

    }

    public Waypoint getWaypointExtended(int compressed_index) {
        MantExpComplex z;
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            z = new MantExpComplexFull(wayPointExpRe[compressed_index], wayPointExpIm[compressed_index], wayPointMantissaRe[compressed_index], wayPointMantissaIm[compressed_index]);
        }
        else {
            z = new MantExpComplex(wayPointExpRe[compressed_index], wayPointMantissaRe[compressed_index], wayPointMantissaIm[compressed_index]);
        }
        return new Waypoint(z, wayPointIteration[compressed_index], rebase[compressed_index]);
    }

    @Override
    public void reset() {

    }

    @Override
    public void resize(int length) {

    }

    public Waypoint getClosestWaypoint(int iteration) {

        if(iteration == 0) {
            if(wayPointIteration.length > 0 && wayPointIteration[0] == iteration) {
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    return new Waypoint(new MantExpComplexFull(wayPointExpRe[0], wayPointExpIm[0], wayPointMantissaRe[0], wayPointMantissaIm[0]), wayPointIteration[0], 0);
                }
                return new Waypoint(new MantExpComplex(wayPointExpRe[0], wayPointMantissaRe[0], wayPointMantissaIm[0]), wayPointIteration[0], 0);

            }
            return new Waypoint(0, -1);
        }

        int left = 0;
        int right = wayPointIteration.length - 1;

        while (left <= right) {
            int mid = (int)(((long)left + right) >>> 1);

            if (wayPointIteration[mid] == iteration) {
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    return new Waypoint(new MantExpComplexFull(wayPointExpRe[mid], wayPointExpIm[mid], wayPointMantissaRe[mid], wayPointMantissaIm[mid]), iteration, mid);
                }
                return new Waypoint(new MantExpComplex(wayPointExpRe[mid], wayPointMantissaRe[mid], wayPointMantissaIm[mid]), iteration, mid);
            } else if (wayPointIteration[mid] < iteration) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if(right < 0) {
            return new Waypoint(0, -1);
        }

        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            return new Waypoint(new MantExpComplexFull(wayPointExpRe[right], wayPointExpIm[right], wayPointMantissaRe[right], wayPointMantissaIm[right]), wayPointIteration[right], right);
        }
        return new Waypoint(new MantExpComplex(wayPointExpRe[right], wayPointMantissaRe[right], wayPointMantissaIm[right]), wayPointIteration[right], right);

    }

    public Waypoint findWaypoint(int iteration) {

        int left = 0;
        int right = wayPointIteration.length - 1;

        while (left <= right) {
            int mid = (int)(((long)left + right) >>> 1);

            if (wayPointIteration[mid] == iteration) {
                if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                    return new Waypoint(new MantExpComplexFull(wayPointExpRe[mid], wayPointExpIm[mid], wayPointMantissaRe[mid], wayPointMantissaIm[mid]), iteration, mid);
                }
                return new Waypoint(new MantExpComplex(wayPointExpRe[mid], wayPointMantissaRe[mid], wayPointMantissaIm[mid]), iteration, mid);
            } else if (wayPointIteration[mid] < iteration) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    public int rebasesListLength() {
        return rebasesList.size();
    }

    public int getRebaseFromListAtIndex(int index) {
        return rebasesList.get(index);
    }

    public int rebasesLength() {
        return rebases.length;
    }

    public int getRebaseAtIndex(int index) {
        return rebases[index];
    }

    public void addRebase(int index) {
        rebasesList.add(index);
    }

    public void setRebaseAt(int index, int value) {
        rebasesList.set(index, value);
    }

    public int compressedListLength() {
        return wayPointsList.size();
    }

    public int getWaypointListIteration(int compressed_index) {
        return wayPointsList.get(compressed_index).iteration;
    }
}
