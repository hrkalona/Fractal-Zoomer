package fractalzoomer.core.mipla;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.Future;

import static fractalzoomer.main.Constants.BLA_CALCULATION_STR;

public class MipLA {
    private MipLAStep[][] LAData;
    private MipLADeepStep[][] LADataDeep;
    private int referenceLength;

    private long finalTotal;

    private int[] elementsPerLevel;

    public boolean valid;
    private int L;

    private int firstLevel;

    private ReferenceDecompressor[] referenceDecompressors;
    private ReferenceDecompressor referenceDecompressor;

    public void create(int referenceLength, DoubleReference Ref, Fractal f, JProgressBar progress) {

        firstLevel = TaskRender.BLA3_STARTING_LEVEL - 1;

        ArrayList<Integer> elemPerLevel = new ArrayList<>();

        int current = referenceLength - 1;
        elemPerLevel.add(current);
        long total = current;
        while(current > 1) {
            current = (current + 1) >>> 1;
            total += current;
            elemPerLevel.add(current);
        }

        elementsPerLevel = elemPerLevel.stream().mapToInt(i -> i).toArray();

        finalTotal = total;
        long removedTotal = 0;

        if(firstLevel > 0) {
            for(int i = 0; i < firstLevel && i < elementsPerLevel.length; i++) {
                removedTotal += elementsPerLevel[i];
            }
            finalTotal -= removedTotal;
        }

        if(finalTotal == 0) {
            return;
        }

        if(Ref.compressed) {
            referenceDecompressor = f.getReferenceDecompressors()[Ref.id];
        }

        if(TaskRender.USE_THREADS_FOR_BLA3) {
            referenceDecompressors = new ReferenceDecompressor[TaskRender.approximation_thread_executor.getCorePoolSize()];
            if (referenceDecompressor != null) {
                for (int i = 0; i < referenceDecompressors.length; i++) {
                    referenceDecompressors[i] = new ReferenceDecompressor(referenceDecompressor);
                }
            }
        }

        LAData = new MipLAStep[elementsPerLevel.length][];

        for(int i = 0; i < LAData.length; i++) {
            LAData[i] = new MipLAStep[elementsPerLevel[i]];
        }

        long divisor = finalTotal > Constants.MAX_PROGRESS_VALUE ? finalTotal / Constants.PROGRESS_SCALE : 1;
        if(progress != null) {
            progress.setMaximum((int)(finalTotal > Constants.MAX_PROGRESS_VALUE ? Constants.PROGRESS_SCALE : finalTotal));
        }

        CreateLAFromOrbit(referenceLength, Ref, null, f, progress, divisor);

        for(int i = firstLevel + 1; i < LAData.length; i++) {
            CreateNewLALevel(progress, divisor, i, false);
        }

        L = LAData.length;
        valid = true;
    }

    public void create(int referenceLength, DeepReference Ref, Fractal f, JProgressBar progress) {

        firstLevel = TaskRender.BLA3_STARTING_LEVEL - 1;

        ArrayList<Integer> elemPerLevel = new ArrayList<>();

        int current = referenceLength - 1;
        elemPerLevel.add(current);
        long total = current;
        while(current > 1) {
            current = (current + 1) >>> 1;
            total += current;
            elemPerLevel.add(current);
        }

        elementsPerLevel = elemPerLevel.stream().mapToInt(i -> i).toArray();

        finalTotal = total;
        long removedTotal = 0;

        if(firstLevel > 0) {
            for(int i = 0; i < firstLevel && i < elementsPerLevel.length; i++) {
                removedTotal += elementsPerLevel[i];
            }
            finalTotal -= removedTotal;
        }

        if(finalTotal == 0) {
            return;
        }

        if(Ref.compressed) {
            referenceDecompressor = f.getReferenceDecompressors()[Ref.id];
        }

        if(TaskRender.USE_THREADS_FOR_BLA3) {
            referenceDecompressors = new ReferenceDecompressor[TaskRender.approximation_thread_executor.getCorePoolSize()];
            if (referenceDecompressor != null) {
                for (int i = 0; i < referenceDecompressors.length; i++) {
                    referenceDecompressors[i] = new ReferenceDecompressor(referenceDecompressor);
                }
            }
        }

        if(firstLevel >= elementsPerLevel.length){
            return;
        }

        LADataDeep = new MipLADeepStep[elementsPerLevel.length][];

        for(int i = firstLevel; i < LADataDeep.length; i++) {
            LADataDeep[i] = new MipLADeepStep[elementsPerLevel[i]];
        }

        long divisor = finalTotal > Constants.MAX_PROGRESS_VALUE ? finalTotal / Constants.PROGRESS_SCALE : 1;
        if(progress != null) {
            progress.setMaximum((int)(finalTotal > Constants.MAX_PROGRESS_VALUE ? Constants.PROGRESS_SCALE : finalTotal));
        }

        CreateLAFromOrbit(referenceLength, null, Ref, f, progress, divisor);

        for(int i = firstLevel + 1; i < LADataDeep.length; i++) {
            CreateNewLALevel(progress, divisor, i, true);
        }

        L = LADataDeep.length;
        valid = true;
    }

    private long done;
    private long old_chunk;

    private MipLADeepStep createLStep(int level, Fractal f, ReferenceDecompressor referenceDecompressor, DeepReference Ref, int m) {

        if(level == 0) {
            return MipLADeep1Step.create(f.getArrayDeepValue(referenceDecompressor, Ref, m), f);
        }

        int m2 = m << 1;
        int mx = m2 - 1;
        int my = m2;
        int levelm1 = level - 1;
        if (my <= elementsPerLevel[levelm1]) {
            MipLADeepStep x = createLStep(levelm1, f, referenceDecompressor, Ref, mx);

            MipLADeepStep y = createLStep(levelm1, f, referenceDecompressor, Ref, my);

            return x.Composite(y);
        } else {
            return createLStep(levelm1, f, referenceDecompressor, Ref, mx);
        }

    }

    private MipLAStep createLStep(int level, Fractal f, ReferenceDecompressor referenceDecompressor, DoubleReference Ref, int m) {

        if(level == 0) {
            return new MipLA1Step(f.getArrayValue(referenceDecompressor, Ref, m), f);
        }

        int m2 = m << 1;
        int mx = m2 - 1;
        int my = m2;
        int levelm1 = level - 1;
        if (my <= elementsPerLevel[levelm1]) {
            MipLAStep x = createLStep(levelm1, f, referenceDecompressor, Ref, mx);

            MipLAStep y = createLStep(levelm1, f, referenceDecompressor, Ref, my);

            return x.Composite(y);
        } else {
            return createLStep(levelm1, f, referenceDecompressor, Ref, mx);
        }

    }

    private void CreateLAFromOrbit(int referenceLength, DoubleReference Ref, DeepReference refDeep, Fractal f, JProgressBar progress, long divisor) {

        this.referenceLength = referenceLength;

        boolean isDeep;
        int currentLevelLength;
        if(LAData != null) {
            CurrentLevel = LAData[firstLevel];
            isDeep = false;
            currentLevelLength = CurrentLevel.length;
        }
        else {
            CurrentLevelDeep = LADataDeep[firstLevel];
            isDeep = true;
            currentLevelLength = CurrentLevelDeep.length;
        }


        if(TaskRender.USE_THREADS_FOR_BLA3) {
            done = 0; //we dont care fore race condition

            ArrayList<Future<?>> futures = new ArrayList<>();
            final int ThreadCount = TaskRender.approximation_thread_executor.getCorePoolSize();
            Runnable[] Tasks = new Runnable[ThreadCount];

            long expectedVal = done + currentLevelLength;
            old_chunk = 0;

            try {
                for (int i = 0; i < Tasks.length; i++) {
                    final int k = i;
                    Tasks[i] = new Runnable() {
                        int ThreadID = k;
                        @Override
                        public void run() {
                            int Start = (int)((long)currentLevelLength * ThreadID / ThreadCount);
                            int End = (int)((long)currentLevelLength * (ThreadID + 1) / ThreadCount);

                            ReferenceDecompressor referenceDecompressor = referenceDecompressors[ThreadID];

                            for(int m = Start; m < End; m++) {

                                if(isDeep) {
                                    CurrentLevelDeep[m] = createLStep(firstLevel, f, referenceDecompressor, refDeep, m + 1);
                                }
                                else {
                                    CurrentLevel[m] = createLStep(firstLevel, f, referenceDecompressor, Ref, m + 1);
                                }

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
                    futures.add(TaskRender.approximation_thread_executor.submit(Tasks[i]));
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
            for (int i = 0; i < currentLevelLength; i++) {
                if(isDeep) {
                    CurrentLevelDeep[i] = createLStep(firstLevel, f, referenceDecompressor, refDeep, i + 1);
                }
                else {
                    CurrentLevel[i] = createLStep(firstLevel, f, referenceDecompressor, Ref, i + 1);
                }

                if (progress != null) {
                    done++;
                    long val = done;

                    if (val % 1000 == 0) {
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

    MipLAStep[] PreviousLevel = null;
    MipLAStep[] CurrentLevel = null;

    MipLADeepStep[] PreviousLevelDeep = null;
    MipLADeepStep[] CurrentLevelDeep = null;
    private void CreateNewLALevel(JProgressBar progress, long divisor, int currentLevelIndex, boolean isDeep) {

        int previousLength;
        int currentLength;

        if(isDeep) {
            PreviousLevelDeep = LADataDeep[currentLevelIndex - 1];
            previousLength = PreviousLevelDeep.length;
            CurrentLevelDeep = LADataDeep[currentLevelIndex];
            currentLength = CurrentLevelDeep.length;
        }
        else {
            PreviousLevel = LAData[currentLevelIndex - 1];
            previousLength = PreviousLevel.length;
            CurrentLevel = LAData[currentLevelIndex];
            currentLength = CurrentLevel.length;
        }

        if(TaskRender.USE_THREADS_FOR_BLA3) {
            ArrayList<Future<?>> futures = new ArrayList<>();
            final int ThreadCount = TaskRender.approximation_thread_executor.getCorePoolSize();
            Runnable[] Tasks = new Runnable[ThreadCount];

            long expectedVal = done + currentLength;

            try {
                for (int i = 0; i < Tasks.length; i++) {
                    final int k = i;
                    Tasks[i] = new Runnable() {
                        int ThreadID = k;
                        @Override
                        public void run() {
                            int Start = (int)((long)currentLength * ThreadID / ThreadCount);
                            int End = (int)((long)currentLength * (ThreadID + 1) / ThreadCount);

                            for(int m = Start; m < End; m++) {

                                int index1 = m << 1;
                                int index2 = index1 + 1;
                                if(index1 < previousLength && index2 < previousLength) {
                                    if(isDeep) {
                                        CurrentLevelDeep[m] = PreviousLevelDeep[index1].Composite(PreviousLevelDeep[index2]);
                                    }
                                    else {
                                        CurrentLevel[m] = PreviousLevel[index1].Composite(PreviousLevel[index2]);
                                    }
                                }
                                else {
                                    if(isDeep) {
                                        CurrentLevelDeep[m] = PreviousLevelDeep[index1];
                                    }
                                    else {
                                        CurrentLevel[m] = PreviousLevel[index1];
                                    }
                                }

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
                    futures.add(TaskRender.approximation_thread_executor.submit(Tasks[i]));
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
            for (int m = 0; m < currentLength; m++) {
                int index1 = m << 1;
                int index2 = index1 + 1;
                if(index1 < previousLength && index2 < previousLength) {
                    if(isDeep) {
                        CurrentLevelDeep[m] = PreviousLevelDeep[index1].Composite(PreviousLevelDeep[index2]);
                    }
                    else {
                        CurrentLevel[m] = PreviousLevel[index1].Composite(PreviousLevel[index2]);
                    }
                }
                else {
                    if(isDeep) {
                        CurrentLevelDeep[m] = PreviousLevelDeep[index1];
                    }
                    else {
                        CurrentLevel[m] = PreviousLevel[index1];
                    }
                }

                if (progress != null) {
                    done++;
                    long val = done;

                    if (val % 1000 == 0) {
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

    public MipLAPair Lookup(int i, double norm_dz, double norm_dc) {

        MipLAPair result = new MipLAPair();

        if (i == 0 || i >= referenceLength) return result;

        int index = i - 1, length = 1;

        if (firstLevel > 0) {
            if((index & ((1 << firstLevel) - 1)) != 0){
                return result;
            }

            length <<= firstLevel;
            index >>= firstLevel;
        }

        for (int k = firstLevel; k < L; k++) {
            MipLAStep current = LAData[k][index];
            if (norm_dz > current.ValidRadius || norm_dc > current.ValidRadiusC) break;
            result.step = current;
            result.length = length;

            if ((index & 1) == 1) {
                break;
            }
            index >>= 1;
            length <<= 1;
        }

        result.length = Math.min(result.length, referenceLength - i);
        return result;
    }

    public MipLAPair Lookup(int i, MantExp norm_dz, MantExp norm_dc) {

        MipLAPair result = new MipLAPair();

        if (i == 0 || i >= referenceLength) return result;

        int index = i - 1, length = 1;

        if (firstLevel > 0) {
            if((index & ((1 << firstLevel) - 1)) != 0){
                return result;
            }

            length <<= firstLevel;
            index >>= firstLevel;
        }

        for (int k = firstLevel; k < L; k++) {
            MipLADeepStep current = LADataDeep[k][index];
            if (norm_dz.compareToBothPositiveReduced(current.ValidRadiusExp, current.ValidRadius) > 0 || norm_dc.compareToBothPositiveReduced(current.ValidRadiusCExp, current.ValidRadiusC) > 0) break;
            result.stepDeep = current;
            result.length = length;

            if ((index & 1) == 1) {
                break;
            }
            index >>= 1;
            length <<= 1;
        }

        result.length = Math.min(result.length, referenceLength - i);
        return result;
    }

    /*public MipLADeepStep Lookup2(int i, MantExp norm_dz, MantExp norm_dc) {

        if (i == 0 || i >= referenceLength) return null;

        int index = i - 1;

        if (firstLevel > 0) {
            if((index & ((1 << firstLevel) - 1)) != 0){
                return null;
            }
            index >>= firstLevel;
        }

        MipLADeepStep result = null;

        for (int k = firstLevel; k < L; k++) {
            MipLADeepStep current = LADataDeep[k][index];
            if (norm_dz.compareToBothPositiveReduced(current.ValidRadiusExp, current.ValidRadius) > 0 || norm_dc.compareToBothPositiveReduced(current.ValidRadiusCExp, current.ValidRadiusC) > 0) break;
            result = current;

            if ((index & 1) == 1) {
                break;
            }
            index >>= 1;
        }

        return result;
    }*/

    public long getTotalElements() {
        return finalTotal;
    }
}
