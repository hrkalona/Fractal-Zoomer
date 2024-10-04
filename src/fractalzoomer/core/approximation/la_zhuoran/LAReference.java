package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.core.*;
import fractalzoomer.core.approximation.la_zhuoran.impl.LAInfo;
import fractalzoomer.core.reference.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.ApproximationDefaultSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

class LAStageInfo {
    int Begin;
    int End;
    boolean UseDoublePrecision;
}

public class LAReference {
    public static boolean CONVERT_TO_DOUBLE_WHEN_POSSIBLE = false;
    public static boolean CREATE_AT = true;

    public static int fakePeriodLimit = ApproximationDefaultSettings.fakePeriodLimit;
    public static double rootDivisor = ApproximationDefaultSettings.RootDivisor;

    private static final MantExp doubleRadiusLimit = new MantExp(0x1.0p-896);
    public static MantExp doubleThresholdLimit = new MantExp(ApproximationDefaultSettings.DoubleThresholdLimit);
    public static int NthRootOption = ApproximationDefaultSettings.NthRootOption;
    public boolean UseAT;

    public static Fractal f;
    public static ReferenceDecompressor referenceDecompressor;

    public ATInfo AT;

    public int LAStageCount;

    public boolean isValid;

    public boolean performDoublePrecisionSimplePerturbation;

    public boolean calculatedForDeep;

    private static final int MaxLAStages = 512;
    private static final int DEFAULT_SIZE = 131072;
    private static final int DEFAULT_SIZE_THREAD = 4096;

    private GenericLAInfo[] LAs;
    private GenericLAInfo[][] LAsPerThread;
    private GenericLAInfo[] LastLAPerThread;
    private int[] LAcurrentIndexPerThread;
    private int LAcurrentIndex;
    private LAStageInfo[] LAStages;
    private ATInfo[] ATs;

    private void init(boolean deepZoom) {

        calculatedForDeep = deepZoom;
        isValid = false;
        LAStages = new LAStageInfo[MaxLAStages];
        LAcurrentIndex = 0;

        LAs = new GenericLAInfo[DEFAULT_SIZE];

        UseAT = false;
        LAStageCount = 0;

        LAStages[0] = new LAStageInfo();

        LAStages[0].UseDoublePrecision = !deepZoom;

        LAStages[0].Begin = 0;
        ATs = null;

    }

    private void addToLAS(GenericLAInfo la) throws Exception {
        LAs[LAcurrentIndex] = la;
        LAcurrentIndex++;
        if (LAcurrentIndex >= LAs.length) {
            long newLen  = ((long) LAs.length) << 1;
            if(newLen > (long)Integer.MAX_VALUE) {
                throw new Exception("No space");
            }
            LAs = Arrays.copyOf(LAs, (int)newLen);
        }
    }

    private void addToLAS(GenericLAInfo la, int threadId) throws Exception {
        int currentLaIndex = LAcurrentIndexPerThread[threadId];
        LAsPerThread[threadId][currentLaIndex] = la;
        currentLaIndex++;
        LAcurrentIndexPerThread[threadId] = currentLaIndex;

        if (currentLaIndex >= LAsPerThread[threadId].length) {
            long newLen  = ((long) LAsPerThread[threadId].length) << 1;
            if(newLen > Integer.MAX_VALUE) {
                throw new Exception("No space");
            }
            LAsPerThread[threadId] = Arrays.copyOf(LAsPerThread[threadId], (int)newLen);
        }
    }

    private void popLA(int threadId) {
        int currentLaIndex = LAcurrentIndexPerThread[threadId];
        if(currentLaIndex > 0) {
            currentLaIndex--;
            LAcurrentIndexPerThread[threadId] = currentLaIndex;
            LAsPerThread[threadId][currentLaIndex] = null;
        }
    }

    public int LAsize() {
        return LAcurrentIndex;
    }

    private void popLA() {
        if(LAcurrentIndex > 0) {
            LAcurrentIndex--;
            LAs[LAcurrentIndex] = null;
        }
    }

    private int getNthRoot(double val) {
        if(NthRootOption == 0) {//Original
            double NthRoot = Math.round(log2(val) / 4.0);
            NthRoot = NthRoot < 1 ? 1 : NthRoot;
            return  (int)Math.round(Math.pow(val, 1.0 / NthRoot));
        }

        double NthRoot = log2(val) / rootDivisor;
        NthRoot = NthRoot < 1 ? 1 : NthRoot;
        return  (int)Math.round(Math.pow(val, 1.0 / NthRoot));
    }

    private boolean CreateLAFromOrbitMagnitudeBased(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom, Fractal f) throws Exception {
        LAReference.f = f;

        if((deepZoom && refDeep.compressed) || (!deepZoom && ref.compressed)) {
            if (deepZoom) {
                referenceDecompressor = f.getReferenceDecompressors()[refDeep.id];
            } else {
                referenceDecompressor = f.getReferenceDecompressors()[ref.id];
            }
        }

        init(deepZoom);

        int i = 0;

        GenericLAInfo step = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);

        i++;

        int Period = 0;
        MagnitudeDetectionBase minMagnitude = MagnitudeDetectionBase.create(deepZoom, i, referenceDecompressor);
        MagnitudeDetectionBase prevMinMagnitude = minMagnitude;

        for (;i < maxRefIteration; i++) {
            MagnitudeDetectionBase magnitudeZ = MagnitudeDetectionBase.create(deepZoom, i, referenceDecompressor);

            if (magnitudeZ.lessThan(minMagnitude)) {
                prevMinMagnitude = minMagnitude;
                minMagnitude = magnitudeZ;

                if (minMagnitude.lessThanWithThresholdStage0(prevMinMagnitude)) {
                    Period = i;
                    break;
                }
            }

            step = step.Step(i, referenceDecompressor);
        }

        addToLAS(step);

        LAStageCount = 1;

        if (Period == 0) {
            if (maxRefIteration > fakePeriodLimit) {
                popLA();
                Period = getNthRoot(maxRefIteration);
                i = 0;
            } else {
                LAStages[0].End = LAStages[0].Begin + 1;

                GenericLAInfo laEnd = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
                laEnd.setNextStageLAIndex(0);
                addToLAS(laEnd);

                return false;
            }
        } else if (Period > fakePeriodLimit) {
            popLA();
            Period = getNthRoot(Period);
            i = 0;
        }

        MagnitudeDetectionBase threshold = MagnitudeDetectionBase.getThreshold(prevMinMagnitude, minMagnitude);

        step = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);
        i++;

        for (; i < maxRefIteration; i++) {
            if (MagnitudeDetectionBase.create(deepZoom, i, referenceDecompressor).lessThan(threshold) || step.StepLength >= Period) {
                addToLAS(step);
                step = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);
            } else {
                step = step.Step(i, referenceDecompressor);
            }
        }

        addToLAS(step);

        LAStages[0].End = LAsize();

        GenericLAInfo endLa = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
        endLa.setNextStageLAIndex(0);
        addToLAS(endLa);

        return true;
    }

    private boolean CreateLAFromOrbit(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom, Fractal f) throws Exception {

        LAReference.f = f;

        if((deepZoom && refDeep.compressed) || (!deepZoom && ref.compressed)) {
            if (deepZoom) {
                referenceDecompressor = f.getReferenceDecompressors()[refDeep.id];
            } else {
                referenceDecompressor = f.getReferenceDecompressors()[ref.id];
            }
        }

        init(deepZoom);

        int Period = 0;

        GenericLAInfo LA;

        LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);

        if(LA.isZCoeffZero()) {
            return false;
        }

        int i;
        for (i = 2; i < maxRefIteration; i++) {

            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            boolean dipDetected = LA.Step(NewLA, i, referenceDecompressor);

            if (dipDetected) {
                Period = i;
                addToLAS(LA);

                int ip1 = i + 1;

                if (ip1 >= maxRefIteration) {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);
                    i++;
                } else {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor).Step(ip1, referenceDecompressor);
                    i += 2;
                }
                break;
            }
            LA = NewLA;
        }

        LAStageCount = 1;

        if (Period == 0) {
            if (maxRefIteration > fakePeriodLimit) {
                LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
                i = 2;

                Period = getNthRoot(maxRefIteration);
            } else {
                addToLAS(LA);

                GenericLAInfo laEnd = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
                laEnd.invalidateInfo();
                addToLAS(laEnd);

                LAStages[0].End = LAStages[0].Begin + 1;

                return false;
            }
        } else if (Period > fakePeriodLimit) {
            popLA();

            LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
            i = 2;

            Period = getNthRoot(Period);
        }

        for (; i < maxRefIteration; i++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
            boolean dipDetected = LA.Step(NewLA, i, referenceDecompressor);

            if (dipDetected || LA.StepLength >= Period) {
                addToLAS(LA);

                int ip1 = i + 1;

                boolean detected;

                GenericLAInfo LAi = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);

                if(deepZoom) {
                    detected = NewLA.DetectDip(f.getArrayDeepValue(referenceDecompressor, refDeep, ip1));
                }
                else {
                    detected = NewLA.DetectDip(f.getArrayValue(referenceDecompressor, ref, ip1));
                }

                if (detected || ip1 >= maxRefIteration) {
                    LA = LAi;
                } else {
                    LA = LAi.Step(ip1, referenceDecompressor);
                    i++;
                }
            } else {
                LA = NewLA;
            }
        }

        addToLAS(LA);

        LAStages[0].End = LAsize();

        GenericLAInfo endLa = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
        endLa.clearInfo();
        addToLAS(endLa);

        return true;
    }

    private int i;
    private GenericLAInfo LA;

    private int Period;
    private volatile Exception[] exceptions;
    private CompletableFuture<Integer>[] StartIndex;

    private volatile int[] FinishIndex;
    private int  ThreadCount;

    private boolean CreateLAFromOrbit_MT(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom, Fractal f) throws Exception {

        int WorkThreshholdForThreads = 50000;
        int MaxThreadCount = TaskRender.approximation_thread_executor.getCorePoolSize();

        ThreadCount = maxRefIteration / WorkThreshholdForThreads;

        if (ThreadCount > MaxThreadCount) {
            ThreadCount = MaxThreadCount;
        }
        else if (ThreadCount == 0) {
            ThreadCount = 1;
        }

        if(ThreadCount == 1) {
            return CreateLAFromOrbit(ref, refDeep, maxRefIteration, deepZoom , f);
        }

        LAReference.f = f;

        if((deepZoom && refDeep.compressed) || (!deepZoom && ref.compressed)) {
            if (deepZoom) {
                referenceDecompressor = f.getReferenceDecompressors()[refDeep.id];
            } else {
                referenceDecompressor = f.getReferenceDecompressors()[ref.id];
            }
        }

        init(deepZoom);

        Period = 0;


        LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);

        if(LA.isZCoeffZero()) {
            return false;
        }

        for (i = 2; i < maxRefIteration; i++) {

            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            boolean dipDetected = LA.Step(NewLA, i, referenceDecompressor);

            if (dipDetected) {
                Period = i;
                addToLAS(LA);

                int ip1 = i + 1;

                if (ip1 >= maxRefIteration) {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);
                    i++;
                } else {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor).Step(ip1, referenceDecompressor);
                    i += 2;
                }
                break;
            }
            LA = NewLA;
        }

        LAStageCount = 1;

        if (Period == 0) {
            if (maxRefIteration > fakePeriodLimit) {
                LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
                i = 2;

                Period = getNthRoot(maxRefIteration);
            } else {
                addToLAS(LA);

                GenericLAInfo endLa = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
                endLa.invalidateInfo();
                addToLAS(endLa);

                LAStages[0].End = LAStages[0].Begin + 1;

                return false;
            }
        } else if (Period > fakePeriodLimit) {
            popLA();

            LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
            i = 2;

            Period = getNthRoot(Period);
        }

        Runnable[] Tasks = new Runnable[ThreadCount];
        StartIndex = new CompletableFuture[ThreadCount];

        exceptions = new Exception[ThreadCount];
        LAsPerThread = new GenericLAInfo[ThreadCount][DEFAULT_SIZE_THREAD];
        LastLAPerThread = new GenericLAInfo[ThreadCount];
        LAcurrentIndexPerThread = new int[ThreadCount];
        FinishIndex = new int[ThreadCount];

        for(int i = 0; i < ThreadCount; i++) {
            StartIndex[i] = new CompletableFuture<>();
        }

        ArrayList<Future<?>> futures = new ArrayList<>();

        ReferenceDecompressor[] referenceDecompressors = new ReferenceDecompressor[ThreadCount];
        if(referenceDecompressor != null) {
            for (int i = 0; i < referenceDecompressors.length; i++) {
                referenceDecompressors[i] = new ReferenceDecompressor(referenceDecompressor);
            }
        }

        Tasks[0] = new Runnable() {
            int ThreadID = 0;
            @Override
            public void run() {
                try {
                    int threadBoundary = maxRefIteration / ThreadCount;
                    int nextThread = ThreadID + 1;
                    ReferenceDecompressor referenceDecompressor = referenceDecompressors[ThreadID];

                    for (; i < maxRefIteration; i++) {
                        GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
                        boolean dipDetected = LA.Step(NewLA, i, referenceDecompressor);

                        if (dipDetected || LA.StepLength >= Period) {
                            addToLAS(LA);

                            int ip1 = i + 1;

                            boolean detected;

                            GenericLAInfo LAi = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);

                            if (deepZoom) {
                                detected = NewLA.DetectDip(f.getArrayDeepValue(referenceDecompressor, refDeep, ip1));
                            } else {
                                detected = NewLA.DetectDip(f.getArrayValue(referenceDecompressor, ref, ip1));
                            }

                            if (detected || ip1 >= maxRefIteration) {
                                LA = LAi;
                            } else {
                                LA = LAi.Step(ip1, referenceDecompressor);
                                i++;
                            }

                            if (i > threadBoundary) {

                                if(i >= maxRefIteration) {
                                    break;
                                }

                                if(nextThread < StartIndex.length) {
                                    int nextStart = StartIndex[nextThread].get();

                                    if (nextStart < 0) { //The next tread had an exception
                                        return;
                                    }

                                    if (i == nextStart - 1) {
                                        i++;
                                        break;
                                    } else if (i >= nextStart) {
                                        //throw new Exception("Thread( " + ThreadID + " ) error " + i + " >= " + nextStart);
//                                        if(prevStart != nextStart) {
//                                            System.out.println("Thread( " + ThreadID + " ) error " + i + " >= " + nextStart);
//                                        }
//                                        prevStart = nextStart;
                                        nextThread++;
                                    }
                                }
                            }
                        } else {
                            LA = NewLA;
                        }
                    }

                    FinishIndex[ThreadID] = i;

                    LastLAPerThread[ThreadID] = LA;
                }
                catch (Exception ex) {
                    exceptions[ThreadID] = ex;
                    StartIndex[ThreadID].complete(-1);
                    FinishIndex[ThreadID] = -1;
                }
            }
        };

        for(int i = 1; i < Tasks.length; i++) {
            final int id = i;
            Tasks[i] = new Runnable() {
                int ThreadID = id;
                @Override
                public void run() {
                    try {
                        ReferenceDecompressor referenceDecompressor = referenceDecompressors[ThreadID];
                        int LastThread = ThreadCount - 1;
                        int nextThread = ThreadID + 1;
                        int Begin = (int)((long)maxRefIteration * ThreadID / ThreadCount);
                        int End = (int)((long)maxRefIteration * (ThreadID + 1) / ThreadCount);

                        int j = Begin;

                        GenericLAInfo LA_2 = GenericLAInfo.create(maxRefIteration, deepZoom, j, referenceDecompressor);
                        LA_2 = LA_2.Step(j + 1, referenceDecompressor);
                        int j2 = j + 2;

                        GenericLAInfo LA_ = GenericLAInfo.create(maxRefIteration, deepZoom, j - 1, referenceDecompressor);
                        LA_ = LA_.Step(j, referenceDecompressor);
                        int j1 = j + 1;


                        boolean dipDetected2 = false;
                        boolean dipDetected = false;

                        for (; j2 < maxRefIteration || j1 < maxRefIteration; j1++, j2++) {
                            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
                            dipDetected = LA_.Step(NewLA, j1, referenceDecompressor);

                            if(dipDetected) {
                                int jp1 = j1 + 1;

                                if (jp1 >= maxRefIteration) {
                                    LA_ = GenericLAInfo.create(maxRefIteration, deepZoom, j1, referenceDecompressor);
                                    j1++;
                                } else {
                                    LA_ = GenericLAInfo.create(maxRefIteration, deepZoom, j1, referenceDecompressor).Step(jp1, referenceDecompressor);
                                    j1 += 2;
                                }
                                break;
                            }

                            LA_ = NewLA;

                            if(j2 < maxRefIteration) {
                                GenericLAInfo NewLA2 = GenericLAInfo.create(maxRefIteration, deepZoom);
                                dipDetected2 = LA_2.Step(NewLA2, j2, referenceDecompressor);

                                if (dipDetected2) {
                                    int jp1 = j2 + 1;

                                    if (jp1 >= maxRefIteration) {
                                        LA_2 = GenericLAInfo.create(maxRefIteration, deepZoom, j2, referenceDecompressor);
                                        j2++;
                                    } else {
                                        LA_2 = GenericLAInfo.create(maxRefIteration, deepZoom, j2, referenceDecompressor).Step(jp1, referenceDecompressor);
                                        j2 += 2;
                                    }
                                    break;
                                }

                                LA_2 = NewLA2;
                            }

                        }

                        if(dipDetected2) {
                            LA_ = LA_2;
                            j = j2;
                        }
                        else if(dipDetected) {
                            j = j1;
                        }
                        else {
                            j = maxRefIteration;
                        }

                        //Just for protection
                        if(ThreadID == LastThread || (j >= Begin && j < End)) {
                            StartIndex[ThreadID].complete(j);
                        }
                        else {
                            int nextStart = StartIndex[nextThread].get();

                            if(nextStart < 0) {
                                return;
                            }

                            StartIndex[ThreadID].complete(nextStart); //Abort the current thread and leave its task to the previous
                            FinishIndex[ThreadID] = -1;
                            return;
                        }

                        for (; j < maxRefIteration; j++) {
                            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
                            dipDetected = LA_.Step(NewLA, j, referenceDecompressor);

                            if(dipDetected || LA_.StepLength >= Period) {
                                addToLAS(LA_, ThreadID);

                                int jp1 = j + 1;

                                boolean detected;

                                GenericLAInfo LAj = GenericLAInfo.create(maxRefIteration, deepZoom, j, referenceDecompressor);

                                if(deepZoom) {
                                    detected = NewLA.DetectDip(f.getArrayDeepValue(referenceDecompressor, refDeep, jp1));
                                }
                                else {
                                    detected = NewLA.DetectDip(f.getArrayValue(referenceDecompressor, ref, jp1));
                                }

                                if (detected || jp1 >= maxRefIteration) {
                                    LA_ = LAj;
                                } else {
                                    LA_ = LAj.Step(jp1, referenceDecompressor);
                                    j++;
                                }

                                if (j > End) {
                                    if (ThreadID == LastThread) throw new Exception("Last Thread cannot reach this");

                                    if(j >= maxRefIteration) {
                                        break;
                                    }

                                    if(nextThread < StartIndex.length) {
                                        int nextStart = StartIndex[nextThread].get();

                                        if (nextStart < 0) { //The next tread had an exception
                                            return;
                                        }

                                        if (j == nextStart - 1) {
                                            j++;
                                            break;
                                        } else if (j >= nextStart) {
                                            //throw new Exception("Thread( " +ThreadID + " ) error " + j + " >= " + nextStart);
//                                            if(prevStart != nextStart) {
//                                                System.out.println("Thread( " + ThreadID + " ) error " + j + " >= " + nextStart);
//                                            }
//                                            prevStart = nextStart;
                                            nextThread++;
                                        }
                                    }
                                }
                            }
                            else {
                                LA_ = NewLA;
                            }
                        }

                        FinishIndex[ThreadID] = j;

                        LastLAPerThread[ThreadID] = LA_;
                    }
                    catch (Exception ex) {
                        exceptions[ThreadID] = ex;
                        StartIndex[ThreadID].complete(-1);
                        FinishIndex[ThreadID] = -1;
                    }
                }
            };
        }

        for(int i = 0; i < Tasks.length; i++) {
            futures.add(TaskRender.approximation_thread_executor.submit(Tasks[i]));
        }

        for(int i = 0; i < futures.size(); i++) {
            futures.get(i).get();
        }

        for(int i = 0; i < Tasks.length; i++) {
            if(exceptions[i] != null) {
                throw exceptions[i];
            }
        }


        {
            int lastThreadToAdd = 0;

            int i = 0;
            int j = i;
            while (i < Tasks.length - 1 && FinishIndex[j] > StartIndex[i + 1].get()) {
                i++; //Skip, if there is a missalignment
            }
            i++;

            for (; i < Tasks.length; i++) {
                int length = LAcurrentIndexPerThread[i];
                GenericLAInfo[] threadData = LAsPerThread[i];
                for (int k = 0; k < length; k++) {
                    addToLAS(threadData[k]);
                }

                if(FinishIndex[i] > StartIndex[i].get()) { //If this thread managed to search
                    lastThreadToAdd = i;
                }

                j = i;
                while (i < Tasks.length - 1 && FinishIndex[j] > StartIndex[i + 1].get()) {
                    i++; //Skip, if there is a missalignment
                }
            }

            addToLAS(LastLAPerThread[lastThreadToAdd]);
        }

        LAsPerThread = null;
        LAcurrentIndexPerThread = null;
        LastLAPerThread = null;

        LAStages[0].End = LAsize();


        GenericLAInfo endLa = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
        endLa.clearInfo();
        addToLAS(endLa);

        return true;
    }

    private boolean CreateNewLAStageMagnitudeBased(int maxRefIteration, boolean deepZoom, int PrevStage, int CurrentStage) throws Exception {

        LAStages[CurrentStage] = new LAStageInfo();
        LAStages[CurrentStage].UseDoublePrecision = !deepZoom;
        LAStages[CurrentStage].Begin = LAsize();

        int Period = 0;
        int PrevStageBegin = LAStages[PrevStage].Begin;
        int PrevStageEnd = LAStages[PrevStage].End;
        int i = PrevStageBegin;
        int PrevStageStepLength = LAs[PrevStageBegin].StepLength;

        GenericLAInfo step = GenericLAInfo.copy(LAs[i]);
        step.setNextStageLAIndex(i);
        i++;

        MagnitudeDetectionBase minMagnitude = MagnitudeDetectionBase.create(LAs[i].getRef(f));
        MagnitudeDetectionBase prevMinMagnitude = minMagnitude;

        for (; i < PrevStageEnd; i++) {
            MagnitudeDetectionBase magnitudeZ = MagnitudeDetectionBase.create(LAs[i].getRef(f));

            if (magnitudeZ.lessThan(minMagnitude)) {
                prevMinMagnitude = minMagnitude;
                minMagnitude = magnitudeZ;

                if (minMagnitude.lessThanWithThreshold(prevMinMagnitude)) {
                    Period = step.StepLength;
                    break;
                }
            }

            step = step.Composite(LAs[i], referenceDecompressor);
        }

        addToLAS(step);

        LAStageCount++;
        if (LAStageCount > MaxLAStages) throw new Exception("Too many stages");

        if (Period == 0) {
            if (maxRefIteration > PrevStageStepLength * fakePeriodLimit) {
                popLA();
                i = PrevStageBegin;
                double Ratio = ((double)(maxRefIteration)) / PrevStageStepLength;
                Period = PrevStageStepLength * getNthRoot(Ratio);
            } else {
                LAStages[CurrentStage].End = LAStages[CurrentStage].Begin + 1;
                addToLAS(LAs[PrevStageEnd]);
                return false;
            }
        }
        else if (Period > LAs[PrevStageBegin].StepLength * fakePeriodLimit) {
            popLA();
            i = PrevStageBegin;
            double Ratio = ((double)(Period)) / PrevStageStepLength;
            Period = PrevStageStepLength * getNthRoot(Ratio);
        }

        MagnitudeDetectionBase threshold = MagnitudeDetectionBase.getThreshold(prevMinMagnitude, minMagnitude);

        step = GenericLAInfo.copy(LAs[i]);
        step.setNextStageLAIndex(i);
        i++;

        for (; i < PrevStageEnd; i++) {
            MagnitudeDetectionBase magnitudeZ = MagnitudeDetectionBase.create(LAs[i].getRef(f));
            if (magnitudeZ.lessThan(threshold) || step.StepLength >= Period) {
                addToLAS(step);

                step = GenericLAInfo.copy(LAs[i]);
                step.setNextStageLAIndex(i);
            } else {
                step = step.Composite(LAs[i], referenceDecompressor);
            }
        }

        addToLAS(step);
        LAStages[CurrentStage].End = LAsize();
        addToLAS(LAs[PrevStageEnd]);

        return true;
    }


    private boolean CreateNewLAStage(int maxRefIteration, boolean deepZoom, int PrevStage, int CurrentStage) throws Exception {
        GenericLAInfo LA;

        int PrevStageBegin = LAStages[PrevStage].Begin;
        int PrevStageEnd = LAStages[PrevStage].End;

        int j = PrevStageBegin;

        GenericLAInfo PrevStageLA = LAs[j];
        GenericLAInfo PrevStageLAp1 = LAs[j + 1];

        int Period = 0;

        LAStages[CurrentStage] = new LAStageInfo();
        LAStages[CurrentStage].UseDoublePrecision = !deepZoom;
        LAStages[CurrentStage].Begin = LAsize();

        LA = PrevStageLA.Composite(PrevStageLAp1, referenceDecompressor);
        LA.setNextStageLAIndex(j);
        j += 2;

        for (; j < PrevStageEnd; j++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            GenericLAInfo LAj = LAs[j];
            boolean dipDetected = LA.Composite(NewLA, LAj, referenceDecompressor);

            if (dipDetected) {
                if (LAj.isLAThresholdZero()) break;
                Period = LA.StepLength;

                addToLAS(LA);

                GenericLAInfo LAjp1 = LAs[j + 1];

                if (NewLA.DetectDip(LAjp1.getRef(f)) || j + 1 >= PrevStageEnd) {
                    LA = GenericLAInfo.copy(LAj);
                    LA.setNextStageLAIndex(j);
                    j++;
                } else {
                    LA = LAj.Composite(LAjp1, referenceDecompressor);
                    LA.setNextStageLAIndex(j);
                    j += 2;
                }
                break;
            }
            LA = NewLA;
        }
        LAStageCount++;
        if (LAStageCount > MaxLAStages) throw new Exception("Too many stages");

        if (Period == 0) {
            if (maxRefIteration > PrevStageLA.StepLength * fakePeriodLimit) {
                j = PrevStageBegin;
                LA = PrevStageLA.Composite(PrevStageLAp1, referenceDecompressor);
                LA.setNextStageLAIndex(j);
                j += 2;

                double Ratio = ((double)(maxRefIteration)) / PrevStageLA.StepLength;
                Period = PrevStageLA.StepLength * getNthRoot(Ratio);
            } else {
                addToLAS(LA);

                GenericLAInfo endLA = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
                endLA.invalidateInfo();

                addToLAS(endLA);

                LAStages[CurrentStage].End = LAStages[CurrentStage].Begin + 1;

                return false;
            }
        }
        else if (Period > PrevStageLA.StepLength * fakePeriodLimit) {
            popLA();

            j = PrevStageBegin;
            LA = PrevStageLA.Composite(PrevStageLAp1, referenceDecompressor);
            LA.setNextStageLAIndex(j);
            j += 2;

            double Ratio = ((double)(Period)) / PrevStageLA.StepLength;
            Period = PrevStageLA.StepLength * getNthRoot(Ratio);
        }

        for (; j < PrevStageEnd; j++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            GenericLAInfo LAj = LAs[j];
            boolean dipDetected = LA.Composite(NewLA, LAj, referenceDecompressor);

            if (dipDetected || LA.StepLength >= Period) {
                addToLAS(LA);

                GenericLAInfo LAjp1 = LAs[j + 1];

                if (NewLA.DetectDip(LAjp1.getRef(f)) || j + 1 >= PrevStageEnd) {
                    LA = GenericLAInfo.copy(LAj);
                    LA.setNextStageLAIndex(j);
                } else {
                    LA = LAj.Composite(LAjp1, referenceDecompressor);
                    LA.setNextStageLAIndex(j);
                    j++;
                }
            } else {
                LA = NewLA;
            }
        }

        addToLAS(LA);

        LAStages[CurrentStage].End = LAsize();

        GenericLAInfo endLa = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
        endLa.clearInfo();

        addToLAS(endLa);
        return true;
    }

    private boolean DoubleUsableAtPrevStage(int stage, MantExp radius) {

        LAStageInfo lasi;
        if(stage < LAStages.length && (lasi = LAStages[stage]) != null) {
            int LAIndex = lasi.Begin;

            GenericLAInfo LA = LAs[LAIndex];

            if(LA == null) {
                return false;
            }

            return radius.compareToBothPositiveReduced(doubleRadiusLimit) > 0
                    || (LA.getLAThreshold().compareToBothPositiveReduced(doubleThresholdLimit) > 0
                    && LA.getLAThresholdC().compareToBothPositiveReduced(doubleThresholdLimit) > 0);
        }

        return radius.compareToBothPositiveReduced(doubleRadiusLimit) > 0;

    }

    private void DismissStage(int Stage) {
        int LAIndex = LAStages[Stage].Begin;
        int length = LAsize() - LAIndex;

        for(int i = 0; i < length; i++) {
            popLA();
        }

        LAStages[Stage] = null;
        if(LAStageCount == Stage + 1) {
            LAStageCount--;
        }
    }

    public void GenerateApproximationData(MantExp radius, ReferenceData refData, ReferenceDeepData refDeepData, int maxRefIteration, boolean deepZoom, Fractal f) {

        try {
            if(maxRefIteration == 0) {
                isValid = false;
                return;
            }

            boolean sucessful;
            try {
                if(LAInfo.DETECTION_METHOD == 2) {
                    sucessful = CreateLAFromOrbitMagnitudeBased(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom, f);
                } else {
                    if(TaskRender.USE_THREADS_FOR_BLA2) {
                        sucessful = CreateLAFromOrbit_MT(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom, f);
                    }
                    else {
                        sucessful = CreateLAFromOrbit(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom, f);
                    }
                }
            }
            catch (InvalidCalculationException ex) {
                DismissStage(0);
                return;
            }

            if (!sucessful) return;

            if (deepZoom && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(0, radius)) {
                performDoublePrecisionSimplePerturbation = true;
            }

            boolean convertedToDouble = false;
            boolean dismissedStage = false;

            while (true) {
                int PrevStage = LAStageCount - 1;
                int CurrentStage = LAStageCount;

                try {
                    if (LAInfo.DETECTION_METHOD == 2) {
                        sucessful = CreateNewLAStageMagnitudeBased(maxRefIteration, deepZoom, PrevStage, CurrentStage);
                    } else {
                        sucessful = CreateNewLAStage(maxRefIteration, deepZoom, PrevStage, CurrentStage);
                    }
                }
                catch (InvalidCalculationException ex) {
                    DismissStage(CurrentStage);
                    dismissedStage = true;
                    break;
                }

                if (deepZoom && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(CurrentStage, radius)) {
                    ConvertStageToDouble(PrevStage);
                    convertedToDouble = true;
                }
                else if(LAInfo.DETECTION_METHOD == 1) {
                    MinimizeStage(PrevStage);
                }

                if (!sucessful) break;
            }

            if (deepZoom && !dismissedStage && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(LAStageCount, radius)) {
                ConvertStageToDouble(LAStageCount - 1);
                convertedToDouble = true;
            }
            else if(LAInfo.DETECTION_METHOD == 1) {
                MinimizeStage(LAStageCount - 1);
            }

            if(CREATE_AT) {
                CreateATFromLA(radius);
            }
            else {
                UseAT = false;
            }

            //Recreate data to save memory
            if (deepZoom && (refData.Reference == null || refData.Reference.dataLength() != maxRefIteration + 1) && (convertedToDouble || performDoublePrecisionSimplePerturbation)) {
                f.createLowPrecisionOrbit(maxRefIteration + 1, refData, refDeepData);
            }
        }
        catch (Exception ex) {
            isValid = false;
            ex.printStackTrace();
            return;
        }

        isValid = true;
    }

    public void CreateATFromLA(MantExp radius) {

        if(ATs == null) {
            ATs = new ATInfo[LAStageCount];
        }

        MantExp SqrRadius = radius.square();
        SqrRadius.Normalize();

        for (int Stage = LAStageCount; Stage > 0; ) {
            Stage--;

            AT = ATs[Stage];
            if(AT == null) {
                int LAIndex = LAStages[Stage].Begin;
                GenericLAInfo la = LAs[LAIndex];
                GenericLAInfo lap1 = LAs[LAIndex + 1];
                if(la != null && lap1 != null) {
                    ATs[Stage] = AT = la.CreateAT(lap1);
                    AT.StepLength = la.StepLength;
                }
            }

            if (AT != null && AT.StepLength > 0 && AT.Usable(SqrRadius)) {
                UseAT = true;
                return;
            }
        }
        UseAT = false;
    }

    void ConvertStageToDouble(int Stage) {
        int Begin = LAStages[Stage].Begin;
        int End = LAStages[Stage].End;

        LAStages[Stage].UseDoublePrecision = true;

        if(TaskRender.USE_THREADS_FOR_BLA2) {
            IntStream.range(Begin, End + 1).
                    parallel().forEach(i -> LAs[i] = LAs[i].toDouble());
        }
        else {
            for (int i = Begin; i <= End; i++) {
                LAs[i] = LAs[i].toDouble();
            }
        }
    }

    void MinimizeStage(int Stage) {
        int Begin = LAStages[Stage].Begin;
        int End = LAStages[Stage].End;

        if(TaskRender.USE_THREADS_FOR_BLA2) {
            IntStream.range(Begin, End + 1).
                    parallel().forEach(i -> LAs[i] = LAs[i].minimize());
        }
        else {
            for (int i = Begin; i <= End; i++) {
                LAs[i] = LAs[i].minimize();
            }
        }
    }


    public boolean isLAStageInvalid(int LAIndex, Complex dc) {
        return (dc.chebyshevNorm() >= LAs[LAIndex].dgetLAThresholdC());
    }

    public boolean isLAStageInvalid(int LAIndex, double dcChebyshevNorm) {
        return (dcChebyshevNorm >= LAs[LAIndex].dgetLAThresholdC());
    }

    public boolean isLAStageInvalid(int LAIndex, MantExpComplex dc) {
        return (dc.chebyshevNorm().compareToBothPositiveReduced(LAs[LAIndex].getLAThresholdC()) >= 0);
    }

    public boolean isLAStageInvalid(int LAIndex, MantExp dcChebyshevNorm) {
        return (dcChebyshevNorm.compareToBothPositiveReduced(LAs[LAIndex].getLAThresholdC()) >= 0);
    }

    public int getNextStageLAIndex(int LAIndex) {
        return LAs[LAIndex].NextStageLAIndex;
    }

    public boolean useDoublePrecisionAtStage(int stage) {
        return LAStages[stage].UseDoublePrecision;
    }

    public int getBegin(int CurrentLAStage) {
        return CurrentLAStage < 0 ? 0 : LAStages[CurrentLAStage].Begin;
    }

    public int getEnd(int CurrentLAStage) {
        return LAStages[CurrentLAStage].End;
    }
    public LAstep getLA(Fractal f, int LAIndex, Complex dz, int iterations, int max_iterations) {

        GenericLAInfo LAj = LAs[LAIndex];

        LAstep las;

        int l = LAj.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            las = LAj.Prepare(f, dz);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndex + 1].getRef(f);
                las.step = l;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.NextStageLAIndex = LAj.NextStageLAIndex;

        return las;

    }

    public LAstep getLA(Fractal f, int LAIndex, double dre, double dim, int iterations, int max_iterations) {

        GenericLAInfo LAj = LAs[LAIndex];

        LAstep las;

        int l = LAj.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            las = LAj.Prepare(f, dre, dim);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndex + 1].getRef(f);
                las.step = l;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.NextStageLAIndex = LAj.NextStageLAIndex;

        return las;

    }

    public LAstep getLA(Fractal f, int LAIndex, MantExpComplex dz, int iterations, int max_iterations) {

        GenericLAInfo LAj = LAs[LAIndex];

        LAstep las;

        int l = LAj.StepLength;
        boolean usuable = iterations + l <= max_iterations;

        if(usuable) {
            las = LAj.Prepare(f, dz);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1Deep = (MantExpComplex) LAs[LAIndex + 1].getRef(f);
                las.step = l;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.NextStageLAIndex = LAj.NextStageLAIndex;

        return las;

    }

    private static double log2 = Math.log(2);

    private double log2(double x) {
        return Math.log(x) / log2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for(int i = 0; i < LAcurrentIndex; i++) {
            hash = 31 * hash + LAs[i].toString().hashCode();
        }
        return hash;
    }

    public static void main(String[] args) {

        long mem;
        int length = 20000000;
//        mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//
//        LAInfo[] temp = new LAInfo[length];
//        LAInfoI[] temp2 = new LAInfoI[length];
//
//        for(int i = 0; i < length; i++) {
//            temp[i] = new LAInfo();
//            temp2[i] = new LAInfoI();
//        }
       // System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - mem);

//        mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        LAData[] temp3 = new LAData[length];
//
//        for(int i = 0; i < length; i++) {
//            temp3[i] = new LAData();
//            temp3[i].la = new LAInfo();
//        }
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - mem);

//        mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        LAData2[] temp3 = new LAData2[length];
//
//        for(int i = 0; i < length; i++) {
//            int j = i;
//            temp3[i] = new LAData2() {
//                @Override
//                public int getStepLength() {
//                    return j;
//                }
//
//                @Override
//                public int getNextStageLAIndex() {
//                    return -j;
//                }
//            };
//            temp3[i].la = new LAInfo();
//        }
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - mem);
    }

}
