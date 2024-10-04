package fractalzoomer.core.reference;

import fractalzoomer.core.Complex;
import fractalzoomer.core.Waypoint;

import java.util.ArrayList;
import java.util.Arrays;

public class CompressedDoubleReference extends DoubleReference {
    private ArrayList<Waypoint> wayPointsList;
    private ArrayList<Integer> rebasesList;

    private int[] rebases;

    private boolean[] rebase;
    private double[] wayPointRe;
    private double[] wayPointIm;
    private int[] wayPointIteration;

    private boolean compressed_extended;

    public CompressedDoubleReference(int length) {
        super();
        wayPointsList = new ArrayList<>();
        rebasesList = new ArrayList<>();
        wayPointRe = new double[0];
        wayPointIm = new double[0];
        wayPointIteration = new int[0];
        rebases = new int[0];
        rebase = new boolean[0];
        this.length = length;
        compressed = true;
        compressed_extended = false;
    }

    public CompressedDoubleReference(int length, int lengthOverride) {
        super();
        wayPointsList = new ArrayList<>();
        rebasesList = new ArrayList<>();
        wayPointRe = new double[0];
        wayPointIm = new double[0];
        wayPointIteration = new int[0];
        rebases = new int[0];
        rebase = new boolean[0];
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

    public void addRebase(int index) {
        rebasesList.add(index);
    }

    public void setRebaseAt(int index, int value) {
        rebasesList.set(index, value);
    }

    public void compact() {
        if(wayPointIteration.length == 0 && !wayPointsList.isEmpty()) {
            int length = wayPointsList.size();
            wayPointRe = new double[length];
            wayPointIm = new double[length];
            wayPointIteration = new int[length];
            for(int i = 0; i < length; i++) {
                Waypoint w = wayPointsList.get(i);
                Complex z = w.z;
                wayPointRe[i] = z.getRe();
                wayPointIm[i] = z.getIm();
                wayPointIteration[i] = w.iteration;
            }
        }
        else if (!wayPointsList.isEmpty()){
            int oldLength = wayPointIteration.length;
            int newLength = oldLength + wayPointsList.size();
            wayPointRe = Arrays.copyOf(wayPointRe, newLength);
            wayPointIm = Arrays.copyOf(wayPointIm, newLength);
            wayPointIteration = Arrays.copyOf(wayPointIteration, newLength);

            for(int i = 0, j = oldLength; i < wayPointsList.size(); i++, j++) {
                Waypoint w = wayPointsList.get(i);
                Complex z = w.z;
                wayPointRe[j] = z.getRe();
                wayPointIm[j] = z.getIm();
                wayPointIteration[j] = w.iteration;
            }
        }

        wayPointsList.clear();
    }


    public void compactExtended() {

        if(wayPointIteration.length == 0 && !wayPointsList.isEmpty()) {
            int length = wayPointsList.size();
            wayPointRe = new double[length];
            wayPointIm = new double[length];
            wayPointIteration = new int[length];
            rebase = new boolean[length];
            for(int i = 0; i < length; i++) {
                Waypoint w = wayPointsList.get(i);
                Complex z = w.z;
                wayPointRe[i] = z.getRe();
                wayPointIm[i] = z.getIm();
                wayPointIteration[i] = w.iteration;
                rebase[i] = w.rebase;
            }
        }
        else if (!wayPointsList.isEmpty()){
            int oldLength = wayPointIteration.length;
            int newLength = oldLength + wayPointsList.size();
            wayPointRe = Arrays.copyOf(wayPointRe, newLength);
            wayPointIm = Arrays.copyOf(wayPointIm, newLength);
            wayPointIteration = Arrays.copyOf(wayPointIteration, newLength);
            rebase = Arrays.copyOf(rebase, newLength);

            for(int i = 0, j = oldLength; i < wayPointsList.size(); i++, j++) {
                Waypoint w = wayPointsList.get(i);
                Complex z = w.z;
                wayPointRe[j] = z.getRe();
                wayPointIm[j] = z.getIm();
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

    public int compressedListLength() {
        return wayPointsList.size();
    }

    public int getWaypointListIteration(int compressed_index) {
        return wayPointsList.get(compressed_index).iteration;
    }

    public boolean useWaypoint(int compressed_index, int iteration) {
        return compressed_index < wayPointIteration.length && wayPointIteration[compressed_index] == iteration;
    }

    public Complex getWaypointData(int compressed_index) {
        return new Complex(wayPointRe[compressed_index], wayPointIm[compressed_index]);
    }

    public Waypoint getWaypoint(int compressed_index) {
        return new Waypoint(new Complex(wayPointRe[compressed_index], wayPointIm[compressed_index]), wayPointIteration[compressed_index]);
    }

    public Waypoint getWaypointExtended(int compressed_index) {
        return new Waypoint(new Complex(wayPointRe[compressed_index], wayPointIm[compressed_index]), wayPointIteration[compressed_index], rebase[compressed_index]);
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
                return new Waypoint(new Complex(wayPointRe[0], wayPointIm[0]), iteration, 0);
            }
            return new Waypoint(0, -1);
        }

        int left = 0;
        int right = wayPointIteration.length - 1;

        while (left <= right) {
            int mid = (int)(((long)left + right) >>> 1);

            if (wayPointIteration[mid] == iteration) {
                return new Waypoint(new Complex(wayPointRe[mid], wayPointIm[mid]), iteration, mid);
            } else if (wayPointIteration[mid] < iteration) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if(right < 0) {
            return new Waypoint(0, -1);
        }

        return new Waypoint(new Complex(wayPointRe[right], wayPointIm[right]), wayPointIteration[right], right);

    }

    public Waypoint findWaypoint(int iteration) {

        int left = 0;
        int right = wayPointIteration.length - 1;

        while (left <= right) {
            int mid = (int)(((long)left + right) >>> 1);

            if (wayPointIteration[mid] == iteration) {
                return new Waypoint(new Complex(wayPointRe[mid], wayPointIm[mid]), iteration, mid);
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

}
