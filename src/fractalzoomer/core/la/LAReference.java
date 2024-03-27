package fractalzoomer.core.la;

import fractalzoomer.core.*;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.ApproximationDefaultSettings;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

class LAStageInfo {
    int LAIndex;
    int MacroItCount;
    boolean UseDoublePrecision;
};

class  LAInfoI {
    int StepLength, NextStageLAIndex;

    public LAInfoI() {
        StepLength = 0;
        NextStageLAIndex = 0;
    }
}

class LAData {
    public GenericLAInfo la;
    int StepLength, NextStageLAIndex; //Info

    public LAData() {
        StepLength = 0;
        NextStageLAIndex = 0;
    }

    public void setInfo(LAInfoI other) {
        StepLength = other.StepLength;
        NextStageLAIndex = other.NextStageLAIndex;
    }

    void invalidateInfo() {
        StepLength = -1;
        NextStageLAIndex = -1;
    }

    @Override
    public String toString() {
        String res = "";
        if(la != null) {
            res += la.toString();
        }
        res += StepLength + "\n";
        res += NextStageLAIndex + "\n";
        return res;
    }
}

public class LAReference {
    public static boolean CONVERT_TO_DOUBLE_WHEN_POSSIBLE = false;

    private static final int lowBound = ApproximationDefaultSettings.lowBound;
    public static double periodDivisor = ApproximationDefaultSettings.PeriodDivisor;

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

    private LAData[] LAs;
    private LAData[][] LAsPerThread;
    private LAData[] LastLAPerThread;
    private int[] LAcurrentIndexPerThread;
    private int LAcurrentIndex;
    private LAStageInfo[] LAStages;

    private void init(boolean deepZoom) {

        calculatedForDeep = deepZoom;
        isValid = false;
        LAStages = new LAStageInfo[MaxLAStages];
        LAcurrentIndex = 0;

        LAs = new LAData[DEFAULT_SIZE];

        UseAT = false;
        LAStageCount = 0;

        LAStages[0] = new LAStageInfo();

        LAStages[0].UseDoublePrecision = !deepZoom;

        LAStages[0].LAIndex = 0;

    }

    private void addToLA(LAData laData) throws Exception {
        LAs[LAcurrentIndex] = laData;
        LAcurrentIndex++;
        if (LAcurrentIndex >= LAs.length) {
            long newLen  = ((long) LAs.length) << 1;
            if(newLen > (long)Integer.MAX_VALUE) {
                throw new Exception("No space");
            }
            LAs = Arrays.copyOf(LAs, (int)newLen);
        }
    }

    private void addToLA(LAData laData, int threadId) throws Exception {
        int currentLaIndex = LAcurrentIndexPerThread[threadId];
        LAsPerThread[threadId][currentLaIndex] = laData;
        currentLaIndex++;
        LAcurrentIndexPerThread[threadId] = currentLaIndex;

        if (currentLaIndex >= LAsPerThread[threadId].length) {
            long newLen  = ((long) LAsPerThread[threadId].length) << 1;
            if(newLen > (long)Integer.MAX_VALUE) {
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

        double NthRoot = log2(val) / periodDivisor;
        NthRoot = NthRoot < 1 ? 1 : NthRoot;
        return  (int)Math.round(Math.pow(val, 1.0 / NthRoot));
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

        LAInfoI LAI = new LAInfoI();
        LAI.NextStageLAIndex = 0;

        if(LA.isZCoeffZero()) {
            return false;
        }

        int i;
        for (i = 2; i < maxRefIteration; i++) {

            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            boolean PeriodDetected = LA.Step(NewLA, i, referenceDecompressor);

            if (PeriodDetected) {
                Period = i;
                LAI.StepLength = Period;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                LAI.NextStageLAIndex = i;

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

        int PeriodBegin = Period;
        int PeriodEnd = PeriodBegin + Period;

        if (Period == 0) {
            if (maxRefIteration > lowBound) {
                LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
                LAI.NextStageLAIndex = 0;
                i = 2;

                Period = getNthRoot(maxRefIteration);

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);
                addToLA(laData);

                laData = new LAData();
                laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
                laData.invalidateInfo();
                addToLA(laData);

                LAStages[0].MacroItCount = 1;

                return false;
            }
        } else if (Period > lowBound) {
            popLA();

            LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
            LAI.NextStageLAIndex = 0;
            i = 2;

            Period = getNthRoot(Period);

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        for (; i < maxRefIteration; i++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
            boolean PeriodDetected = LA.Step(NewLA, i, referenceDecompressor);

            if (PeriodDetected || i >= PeriodEnd) {
                LAI.StepLength = i - PeriodBegin;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                LAI.NextStageLAIndex = i;
                PeriodBegin = i;
                PeriodEnd = PeriodBegin + Period;

                int ip1 = i + 1;

                boolean detected;

                if(deepZoom) {
                    detected = NewLA.DetectPeriod(f.getArrayDeepValue(referenceDecompressor, refDeep, ip1));
                }
                else {
                    detected = NewLA.DetectPeriod(f.getArrayValue(referenceDecompressor, ref, ip1));
                }

                if (detected || ip1 >= maxRefIteration) {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);
                } else {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor).Step(ip1, referenceDecompressor);
                    i++;
                }
            } else {
                LA = NewLA;
            }
        }

        LAI.StepLength = i - PeriodBegin;

        LAData laData = new LAData();
        laData.la = LA;
        laData.setInfo(LAI);
        addToLA(laData);

        LAStages[0].MacroItCount = LAsize();

        laData = new LAData();
        laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);

        addToLA(laData);

        return true;
    }

    private int i;
    private int PeriodBegin;
    private int PeriodEnd;
    private GenericLAInfo LA;

    private int Period;
    private volatile Exception[] exceptions;
    private CompletableFuture<Integer>[] StartIndex;

    private volatile int[] FinishIndex;
    private int  ThreadCount;

    private boolean CreateLAFromOrbit_MT(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom, Fractal f) throws Exception {

        int WorkThreshholdForThreads = 50000;
        int MaxThreadCount = TaskDraw.la_thread_executor.getCorePoolSize();

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

        LAInfoI LAI = new LAInfoI();
        LAI.NextStageLAIndex = 0;

        if(LA.isZCoeffZero()) {
            return false;
        }

        for (i = 2; i < maxRefIteration; i++) {

            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            boolean PeriodDetected = LA.Step(NewLA, i, referenceDecompressor);

            if (PeriodDetected) {
                Period = i;
                LAI.StepLength = Period;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                LAI.NextStageLAIndex = i;

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

        PeriodBegin = Period;
        PeriodEnd = PeriodBegin + Period;

        if (Period == 0) {
            if (maxRefIteration > lowBound) {
                LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
                LAI.NextStageLAIndex = 0;
                i = 2;

                Period = getNthRoot(maxRefIteration);

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);
                addToLA(laData);

                laData = new LAData();
                laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);
                laData.invalidateInfo();
                addToLA(laData);

                LAStages[0].MacroItCount = 1;

                return false;
            }
        } else if (Period > lowBound) {
            popLA();

            LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0, referenceDecompressor).Step(1, referenceDecompressor);
            LAI.NextStageLAIndex = 0;
            i = 2;

            Period = getNthRoot(Period);

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        Runnable[] Tasks = new Runnable[ThreadCount];
        StartIndex = new CompletableFuture[ThreadCount];

        exceptions = new Exception[ThreadCount];
        LAsPerThread = new LAData[ThreadCount][DEFAULT_SIZE_THREAD];
        LastLAPerThread = new LAData[ThreadCount];
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
                    int prevStart = 0;
                    ReferenceDecompressor referenceDecompressor = referenceDecompressors[ThreadID];

                    for (; i < maxRefIteration; i++) {
                        GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
                        boolean PeriodDetected = LA.Step(NewLA, i, referenceDecompressor);

                        if (PeriodDetected || i >= PeriodEnd) {
                            LAI.StepLength = i - PeriodBegin;

                            LAData laData = new LAData();
                            laData.la = LA;
                            laData.setInfo(LAI);

                            addToLA(laData);

                            LAI.NextStageLAIndex = i;
                            PeriodBegin = i;
                            PeriodEnd = PeriodBegin + Period;

                            int ip1 = i + 1;

                            boolean detected;

                            if (deepZoom) {
                                detected = NewLA.DetectPeriod(f.getArrayDeepValue(referenceDecompressor, refDeep, ip1));
                            } else {
                                detected = NewLA.DetectPeriod(f.getArrayValue(referenceDecompressor, ref, ip1));
                            }

                            if (detected || ip1 >= maxRefIteration) {
                                LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor);
                            } else {
                                LA = GenericLAInfo.create(maxRefIteration, deepZoom, i, referenceDecompressor).Step(ip1, referenceDecompressor);
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

                    LAI.StepLength = i - PeriodBegin;

                    LAData laData = new LAData();
                    laData.la = LA;
                    laData.setInfo(LAI);

                    LastLAPerThread[ThreadID] = laData;
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
                        int prevStart = 0;

                        int j = Begin;

                        LAInfoI LAI_ = new LAInfoI();
                        LAI_.NextStageLAIndex = j;

                        GenericLAInfo LA_2 = GenericLAInfo.create(maxRefIteration, deepZoom, j, referenceDecompressor);
                        LA_2 = LA_2.Step(j + 1, referenceDecompressor);
                        int j2 = j + 2;

                        GenericLAInfo LA_ = GenericLAInfo.create(maxRefIteration, deepZoom, j - 1, referenceDecompressor);
                        LA_ = LA_.Step(j, referenceDecompressor);
                        int j1 = j + 1;


                        int PeriodBegin = 0;
                        int PeriodEnd = 0;
                        boolean PeriodDetected2 = false;
                        boolean PeriodDetected = false;

                        for (; j2 < maxRefIteration || j1 < maxRefIteration; j1++, j2++) {
                            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
                            PeriodDetected = LA_.Step(NewLA, j1, referenceDecompressor);

                            if(PeriodDetected) {
                                LAI_.NextStageLAIndex = j1;
                                PeriodBegin = j1;
                                PeriodEnd = PeriodBegin + Period;

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
                                PeriodDetected2 = LA_2.Step(NewLA2, j2, referenceDecompressor);

                                if (PeriodDetected2) {
                                    LAI_.NextStageLAIndex = j2;
                                    PeriodBegin = j2;
                                    PeriodEnd = PeriodBegin + Period;

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

                        if(PeriodDetected2) {
                            LA_ = LA_2;
                            j = j2;
                        }
                        else if(PeriodDetected) {
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
                            PeriodDetected = LA_.Step(NewLA, j, referenceDecompressor);

                            if(PeriodDetected || j >= PeriodEnd) {
                                LAI_.StepLength = j - PeriodBegin;

                                LAData laData = new LAData();
                                laData.la = LA_;
                                laData.setInfo(LAI_);
                                addToLA(laData, ThreadID);

                                LAI_.NextStageLAIndex = j;
                                PeriodBegin = j;
                                PeriodEnd = PeriodBegin + Period;

                                int jp1 = j + 1;

                                boolean detected;

                                if(deepZoom) {
                                    detected = NewLA.DetectPeriod(f.getArrayDeepValue(referenceDecompressor, refDeep, jp1));
                                }
                                else {
                                    detected = NewLA.DetectPeriod(f.getArrayValue(referenceDecompressor, ref, jp1));
                                }

                                if (detected || jp1 >= maxRefIteration) {
                                    LA_ = GenericLAInfo.create(maxRefIteration, deepZoom, j, referenceDecompressor);
                                } else {
                                    LA_ = GenericLAInfo.create(maxRefIteration, deepZoom, j, referenceDecompressor).Step(jp1, referenceDecompressor);
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

                        LAI_.StepLength = j - PeriodBegin;

                        LAData laData = new LAData();
                        laData.la = LA_;
                        laData.setInfo(LAI_);

                        LastLAPerThread[ThreadID] = laData;
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
            futures.add(TaskDraw.la_thread_executor.submit(Tasks[i]));
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
                LAData[] threadData = LAsPerThread[i];
                for (int k = 0; k < length; k++) {
                    addToLA(threadData[k]);
                }

                if(FinishIndex[i] > StartIndex[i].get()) { //If this thread managed to search
                    lastThreadToAdd = i;
                }

                j = i;
                while (i < Tasks.length - 1 && FinishIndex[j] > StartIndex[i + 1].get()) {
                    i++; //Skip, if there is a missalignment
                }
            }

            addToLA(LastLAPerThread[lastThreadToAdd]);
        }

        LAsPerThread = null;
        LAcurrentIndexPerThread = null;
        LastLAPerThread = null;

        LAStages[0].MacroItCount = LAsize();

        LAData laData = new LAData();
        laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);

        addToLA(laData);

        return true;
    }


    private boolean CreateNewLAStage(int maxRefIteration, boolean deepZoom, int PrevStage, int CurrentStage) throws Exception {
        GenericLAInfo LA;
        LAInfoI LAI = new LAInfoI();
        int i;
        int PeriodBegin;
        int PeriodEnd;

        int PrevStageLAIndex = LAStages[PrevStage].LAIndex;
        int PrevStageMacroItCount = LAStages[PrevStage].MacroItCount;

        LAData prevLaData = LAs[PrevStageLAIndex];
        GenericLAInfo PrevStageLA = prevLaData.la;

        LAData prevLaDataP1 = LAs[PrevStageLAIndex + 1];
        GenericLAInfo PrevStageLAp1 = prevLaDataP1.la;

        int Period = 0;

        if (CurrentStage >= MaxLAStages) throw new Exception("Too many stages");

        LAStages[CurrentStage] = new LAStageInfo();
        LAStages[CurrentStage].UseDoublePrecision = !deepZoom;
        LAStages[CurrentStage].LAIndex = LAsize();

        LA = PrevStageLA.Composite(PrevStageLAp1, referenceDecompressor);
        LAI.NextStageLAIndex = 0;
        i = prevLaData.StepLength + prevLaDataP1.StepLength;
        int j;

        for (j = 2; j < PrevStageMacroItCount; j++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            int PrevStageLAIndexj = PrevStageLAIndex + j;
            LAData prevLaDataj = LAs[PrevStageLAIndexj];
            GenericLAInfo PrevStageLAj = prevLaDataj.la;
            boolean PeriodDetected = LA.Composite(NewLA, PrevStageLAj, referenceDecompressor);

            if (PeriodDetected) {
                if (PrevStageLAj.isLAThresholdZero()) break;
                Period = i;

                LAI.StepLength = Period;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                LAI.NextStageLAIndex = j;

                int PrevStageLAIndexjp1 = PrevStageLAIndexj + 1;
                LAData prevLaDatajp1 = LAs[PrevStageLAIndexjp1];
                GenericLAInfo PrevStageLAjp1 = prevLaDatajp1.la;

                if (NewLA.DetectPeriod(PrevStageLAjp1.getRef(f)) || j + 1 >= PrevStageMacroItCount) {
                    LA = PrevStageLAj;
                    i += prevLaDataj.StepLength;
                    j++;
                } else {
                    LA = PrevStageLAj.Composite(PrevStageLAjp1, referenceDecompressor);
                    i += prevLaDataj.StepLength + prevLaDatajp1.StepLength;
                    j += 2;
                }
                break;
            }
            LA = NewLA;
            i += LAs[PrevStageLAIndex + j].StepLength;
        }
        LAStageCount++;
        if (LAStageCount > MaxLAStages) throw new Exception("Too many stages");

        PeriodBegin = Period;
        PeriodEnd = PeriodBegin + Period;

        if (Period == 0) {
            if (maxRefIteration > prevLaData.StepLength * lowBound) {
                LA = PrevStageLA.Composite(PrevStageLAp1, referenceDecompressor);
                i = prevLaData.StepLength + prevLaDataP1.StepLength;
                LAI.NextStageLAIndex = 0;

                j = 2;

                double Ratio = ((double)(maxRefIteration)) / prevLaData.StepLength;
                Period = prevLaData.StepLength * getNthRoot(Ratio);

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                laData = new LAData();
                laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);

                laData.invalidateInfo();
                addToLA(laData);

                LAStages[CurrentStage].MacroItCount = 1;

                return false;
            }
        }
        else if (Period > prevLaData.StepLength * lowBound) {
            popLA();

            LA = PrevStageLA.Composite(PrevStageLAp1, referenceDecompressor);
            i = prevLaData.StepLength + prevLaDataP1.StepLength;
            LAI.NextStageLAIndex = 0;

            j = 2;

            double Ratio = ((double)(Period)) / prevLaData.StepLength;
            Period = prevLaData.StepLength * getNthRoot(Ratio);

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        for (; j < PrevStageMacroItCount; j++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            int PrevStageLAIndexj = PrevStageLAIndex + j;

            LAData prevLaDataj = LAs[PrevStageLAIndexj];
            GenericLAInfo PrevStageLAj = prevLaDataj.la;
            boolean PeriodDetected = LA.Composite(NewLA, PrevStageLAj, referenceDecompressor);

            if (PeriodDetected || i >= PeriodEnd) {
                LAI.StepLength = i - PeriodBegin;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);
                addToLA(laData);

                LAI.NextStageLAIndex = j;
                PeriodBegin = i;
                PeriodEnd = PeriodBegin + Period;

                LAData prevLaDatajP1 = LAs[PrevStageLAIndexj + 1];
                GenericLAInfo PrevStageLAjp1 = prevLaDatajP1.la;

                if (NewLA.DetectPeriod(PrevStageLAjp1.getRef(f)) || j + 1 >= PrevStageMacroItCount) {
                    LA = PrevStageLAj;
                } else {
                    LA = PrevStageLAj.Composite(PrevStageLAjp1, referenceDecompressor);
                    i += prevLaDataj.StepLength;
                    j++;
                }
            } else {
                LA = NewLA;
            }
            i += LAs[PrevStageLAIndex + j].StepLength;
        }

        LAI.StepLength = i - PeriodBegin;

        LAData laData = new LAData();
        laData.la = LA;
        laData.setInfo(LAI);

        addToLA(laData);

        LAStages[CurrentStage].MacroItCount = LAsize() - LAStages[CurrentStage].LAIndex;

        laData = new LAData();
        laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration, referenceDecompressor);

        addToLA(laData);
        return true;
    }

    private boolean DoubleUsableAtPrevStage(int stage, MantExp radius) {

        LAStageInfo lasi;
        if(stage < LAStages.length && (lasi = LAStages[stage]) != null) {
            int LAIndex = lasi.LAIndex;

            LAData laData = LAs[LAIndex];

            if(laData == null) {
                return false;
            }
            GenericLAInfo LA = laData.la;

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
        int LAIndex = LAStages[Stage].LAIndex;
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

            boolean PeriodDetected;
            try {
                if(TaskDraw.USE_THREADS_FOR_BLA2) {
                    PeriodDetected = CreateLAFromOrbit_MT(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom, f);
                }
                else {
                    PeriodDetected = CreateLAFromOrbit(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom, f);
                }
            }
            catch (InvalidCalculationException ex) {
                DismissStage(0);
                return;
            }

            if (!PeriodDetected) return;

            if (deepZoom && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(0, radius)) {
                performDoublePrecisionSimplePerturbation = true;
            }

            boolean convertedToDouble = false;
            boolean dismissedStage = false;

            while (true) {
                int PrevStage = LAStageCount - 1;
                int CurrentStage = LAStageCount;

                try {
                    PeriodDetected = CreateNewLAStage(maxRefIteration, deepZoom, PrevStage, CurrentStage);
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

                if (!PeriodDetected) break;
            }

            if (deepZoom && !dismissedStage && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(LAStageCount, radius)) {
                ConvertStageToDouble(LAStageCount - 1);
                convertedToDouble = true;
            }
            else if(LAInfo.DETECTION_METHOD == 1) {
                MinimizeStage(LAStageCount - 1);
            }

            CreateATFromLA(radius);

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

        MantExp SqrRadius = radius.square();
        SqrRadius.Normalize();

        for (int Stage = LAStageCount; Stage > 0; ) {
            Stage--;
            int LAIndex = LAStages[Stage].LAIndex;
            LAData laData = LAs[LAIndex];
            AT = laData.la.CreateAT(LAs[LAIndex + 1].la);
            AT.StepLength = laData.StepLength;
            if (AT.StepLength > 0 && AT.Usable(SqrRadius)) {
                UseAT = true;
                return;
            }
        }
        UseAT = false;
    }

    void ConvertStageToDouble(int Stage) {
        int LAIndex = LAStages[Stage].LAIndex;
        int MacroItCount = LAStages[Stage].MacroItCount;

        LAStages[Stage].UseDoublePrecision = true;

        int length = LAIndex + MacroItCount;

        if(TaskDraw.USE_THREADS_FOR_BLA2) {
            IntStream.range(LAIndex, length + 1).
                    parallel().forEach(i -> LAs[i].la = LAs[i].la.toDouble());
        }
        else {
            for (int i = LAIndex; i <= length; i++) {
                LAs[i].la = LAs[i].la.toDouble();
            }
        }
    }

    void MinimizeStage(int Stage) {
        int LAIndex = LAStages[Stage].LAIndex;
        int MacroItCount = LAStages[Stage].MacroItCount;

        int length = LAIndex + MacroItCount;

        if(TaskDraw.USE_THREADS_FOR_BLA2) {
            IntStream.range(LAIndex, length + 1).
                    parallel().forEach(i -> LAs[i].la = LAs[i].la.minimize());
        }
        else {
            for (int i = LAIndex; i <= length; i++) {
                LAs[i].la = LAs[i].la.minimize();
            }
        }
    }


    public boolean isLAStageInvalid(int LAIndex, Complex dc) {
        return (dc.chebyshevNorm() >= LAs[LAIndex].la.dgetLAThresholdC());
    }

    public boolean isLAStageInvalid(int LAIndex, double dcChebyshevNorm) {
        return (dcChebyshevNorm >= LAs[LAIndex].la.dgetLAThresholdC());
    }

    public boolean isLAStageInvalid(int LAIndex, MantExpComplex dc) {
        return (dc.chebyshevNorm().compareToBothPositiveReduced((LAs[LAIndex].la).getLAThresholdC()) >= 0);
    }

    public boolean isLAStageInvalid(int LAIndex, MantExp dcChebyshevNorm) {
        return (dcChebyshevNorm.compareToBothPositiveReduced((LAs[LAIndex].la).getLAThresholdC()) >= 0);
    }

    public boolean useDoublePrecisionAtStage(int stage) {
        return LAStages[stage].UseDoublePrecision;
    }

    public int getLAIndex(int CurrentLAStage) {
        return LAStages[CurrentLAStage].LAIndex;
    }

    public int getMacroItCount(int CurrentLAStage) {
        return LAStages[CurrentLAStage].MacroItCount;
    }

    public LAstep getLA(Fractal f, int LAIndex, Complex dz, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAData laData = LAs[LAIndexj];

        LAstep las;

        int l = laData.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            GenericLAInfo LAj = laData.la;
            las = LAj.Prepare(f, dz);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndexj + 1].la.getRef(f);
                las.step = l;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.nextStageLAindex = laData.NextStageLAIndex;

        return las;

    }

    public LAstep getLA(Fractal f, int LAIndex, double dre, double dim, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAData laData = LAs[LAIndexj];

        LAstep las;

        int l = laData.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            GenericLAInfo LAj = laData.la;
            las = LAj.Prepare(f, dre, dim);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndexj + 1].la.getRef(f);
                las.step = l;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.nextStageLAindex = laData.NextStageLAIndex;

        return las;

    }

    public LAstep getLA(Fractal f, int LAIndex, MantExpComplex dz, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAData laData = LAs[LAIndexj];

        LAstep las;

        int l = laData.StepLength;
        boolean usuable = iterations + l <= max_iterations;

        if(usuable) {
            GenericLAInfo LAj = laData.la;
            las = LAj.Prepare(f, dz);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1Deep = (MantExpComplex) LAs[LAIndexj + 1].la.getRef(f);
                las.step = l;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.nextStageLAindex = laData.NextStageLAIndex;

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
