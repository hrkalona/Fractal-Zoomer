package fractalzoomer.core.la;

import fractalzoomer.core.*;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.app_settings.ApproximationDefaultSettings;

import java.util.Arrays;

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
};

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
}

public class LAReference {
    public static boolean CONVERT_TO_DOUBLE_WHEN_POSSIBLE = false;

    private static final int lowBound = 64;
    private static final double log16 = Math.log(16);

    private static final MantExp doubleRadiusLimit = new MantExp(0x1.0p-896);
    public static MantExp doubleThresholdLimit = new MantExp(ApproximationDefaultSettings.DoubleThresholdLimit);
    public boolean UseAT;

    public ATInfo AT;

    public int LAStageCount;

    public boolean isValid;

    public boolean performDoublePrecisionSimplePerturbation;

    public boolean calculatedForDeep;

    private static final int MaxLAStages = 512;
    private static final int DEFAULT_SIZE = 131072;

    private LAData[] LAs;
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

    private int LAsize() {
        return LAcurrentIndex;
    }

    private void popLA() {
        if(LAcurrentIndex > 0) {
            LAcurrentIndex--;
            LAs[LAcurrentIndex] = null;
        }
    }

    private boolean CreateLAFromOrbit(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom) throws Exception {

        init(deepZoom);

        int Period = 0;

        GenericLAInfo LA;

        if(deepZoom) {
            GenericLAInfo.refExpRe = refDeep.exps;
            GenericLAInfo.refExpIm = refDeep.expsIm;
            GenericLAInfo.refMantsRe = refDeep.mantsRe;
            GenericLAInfo.refMantsIm = refDeep.mantsIm;
        }
        else {
            GenericLAInfo.refRe = ref.re;
            GenericLAInfo.refIm = ref.im;
        }

        LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0).Step(1);

        LAInfoI LAI = new LAInfoI();
        LAI.NextStageLAIndex = 0;

        if(LA.isZCoeffZero()) {
            return false;
        }

        int i;
        for (i = 2; i < maxRefIteration; i++) {

            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            boolean PeriodDetected = LA.Step(NewLA, i);

            if (PeriodDetected) {
                Period = i;
                LAI.StepLength = Period;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                LAI.NextStageLAIndex = i;

                if (i + 1 < maxRefIteration) {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i).Step(i + 1);
                    i += 2;
                } else {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i);
                    i += 1;
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
                LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0).Step(1);
                LAI.NextStageLAIndex = 0;
                i = 2;

                double NthRoot = Math.round(Math.log(maxRefIteration) / log16);
                Period = (int)Math.round(Math.pow(maxRefIteration, 1.0 / NthRoot));

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);
                addToLA(laData);

                laData = new LAData();
                laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration);
                laData.invalidateInfo();
                addToLA(laData);

                LAStages[0].MacroItCount = 1;

                return false;
            }
        } else if (Period > lowBound) {
            popLA();

            LA = GenericLAInfo.create(maxRefIteration, deepZoom, 0).Step(1);
            LAI.NextStageLAIndex = 0;
            i = 2;

            double NthRoot = Math.round(Math.log(Period) / log16);
            Period = (int)Math.round(Math.pow(Period, 1.0 / NthRoot));

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        for (; i < maxRefIteration; i++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);
            boolean PeriodDetected = LA.Step(NewLA, i);

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
                    detected = NewLA.DetectPeriod(Fractal.getArrayDeepValue(refDeep, ip1));
                }
                else {
                    detected = NewLA.DetectPeriod(Fractal.getArrayValue(ref, ip1));
                }

                if (detected || ip1 >= maxRefIteration) {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i);
                } else {
                    LA = GenericLAInfo.create(maxRefIteration, deepZoom, i).Step(ip1);
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
        laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration);

        addToLA(laData);

        return true;
    }


    private boolean CreateNewLAStage(int maxRefIteration, boolean deepZoom) throws Exception {
        GenericLAInfo LA;
        LAInfoI LAI = new LAInfoI();
        int i;
        int PeriodBegin;
        int PeriodEnd;

        int PrevStage = LAStageCount - 1;
        int CurrentStage = LAStageCount;
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

        LA = PrevStageLA.Composite(PrevStageLAp1);
        LAI.NextStageLAIndex = 0;
        i = prevLaData.StepLength + prevLaDataP1.StepLength;
        int j;

        for (j = 2; j < PrevStageMacroItCount; j++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            int PrevStageLAIndexj = PrevStageLAIndex + j;
            LAData prevLaDataj = LAs[PrevStageLAIndexj];
            GenericLAInfo PrevStageLAj = prevLaDataj.la;
            boolean PeriodDetected = LA.Composite(NewLA, PrevStageLAj);

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

                if (NewLA.DetectPeriod(PrevStageLAjp1.getRef()) || j + 1 >= PrevStageMacroItCount) {
                    LA = PrevStageLAj;
                    i += prevLaDataj.StepLength;
                    j++;
                } else {
                    LA = PrevStageLAj.Composite(PrevStageLAjp1);
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
                LA = PrevStageLA.Composite(PrevStageLAp1);
                i = prevLaData.StepLength + prevLaDataP1.StepLength;
                LAI.NextStageLAIndex = 0;

                j = 2;

                double Ratio = ((double)(maxRefIteration)) / prevLaData.StepLength;
                double NthRoot = Math.round(Math.log(Ratio) / log16); // log16
                Period = prevLaData.StepLength * (int)Math.round(Math.pow(Ratio, 1.0 / NthRoot));

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                LAData laData = new LAData();
                laData.la = LA;
                laData.setInfo(LAI);

                addToLA(laData);

                laData = new LAData();
                laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration);

                laData.invalidateInfo();
                addToLA(laData);

                LAStages[CurrentStage].MacroItCount = 1;

                return false;
            }
        }
        else if (Period > prevLaData.StepLength * lowBound) {
            popLA();

            LA = PrevStageLA.Composite(PrevStageLAp1);
            i = prevLaData.StepLength + prevLaDataP1.StepLength;
            LAI.NextStageLAIndex = 0;

            j = 2;

            double Ratio = ((double)(Period)) / prevLaData.StepLength;

            double NthRoot = Math.round(Math.log(Ratio) / log16);
            Period = prevLaData.StepLength * ((int)Math.round(Math.pow(Ratio, 1.0 / NthRoot)));

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        for (; j < PrevStageMacroItCount; j++) {
            GenericLAInfo NewLA = GenericLAInfo.create(maxRefIteration, deepZoom);

            int PrevStageLAIndexj = PrevStageLAIndex + j;

            LAData prevLaDataj = LAs[PrevStageLAIndexj];
            GenericLAInfo PrevStageLAj = prevLaDataj.la;
            boolean PeriodDetected = LA.Composite(NewLA, PrevStageLAj);

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

                if (NewLA.DetectPeriod(PrevStageLAjp1.getRef()) || j + 1 >= PrevStageMacroItCount) {
                    LA = PrevStageLAj;
                } else {
                    LA = PrevStageLAj.Composite(PrevStageLAjp1);
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
        laData.la = GenericLAInfo.create(maxRefIteration, deepZoom, maxRefIteration);

        addToLA(laData);
        return true;
    }

    private boolean DoubleUsableAtPrevStage(int stage, MantExp radius) {

        LAStageInfo lasi = LAStages[stage];
        if(lasi != null) {
            int LAIndex = lasi.LAIndex;
            GenericLAInfo LA = LAs[LAIndex].la;
            return radius.compareToBothPositiveReduced(doubleRadiusLimit) > 0
                    || (LA.getLAThreshold().compareToBothPositiveReduced(doubleThresholdLimit) > 0
                    && LA.getLAThresholdC().compareToBothPositiveReduced(doubleThresholdLimit) > 0);
        }

        return radius.compareToBothPositiveReduced(doubleRadiusLimit) > 0;

    }

    public void GenerateApproximationData(MantExp radius, ReferenceData refData, ReferenceDeepData refDeepData, int maxRefIteration, boolean deepZoom, Fractal f) {

        try {
            if(maxRefIteration == 0) {
                isValid = false;
                return;
            }

            boolean PeriodDetected;
            try {
                PeriodDetected = CreateLAFromOrbit(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom);
            }
            catch (InvalidCalculationException ex) {
                PeriodDetected = false;
            }

            if (!PeriodDetected) return;

            if (deepZoom && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(0, radius)) {
                performDoublePrecisionSimplePerturbation = true;
            }

            boolean convertedToDouble = false;

            while (true) {
                int PrevStage = LAStageCount - 1;
                int CurrentStage = LAStageCount;

                try {
                    PeriodDetected = CreateNewLAStage(maxRefIteration, deepZoom);

                }
                catch (InvalidCalculationException ex) {
                    PeriodDetected = false;
                }
                catch (Exception ex) {
                    PeriodDetected = false;
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

            if (deepZoom && (!f.useFullFloatExp() || CONVERT_TO_DOUBLE_WHEN_POSSIBLE) && DoubleUsableAtPrevStage(LAStageCount, radius)) {
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

                GenericLAInfo.refRe = refData.Reference.re;
                GenericLAInfo.refIm = refData.Reference.im;
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
        for (int i = LAIndex; i <= length; i++) {
            LAs[i].la = LAs[i].la.toDouble();
        }
    }

    void MinimizeStage(int Stage) {
        int LAIndex = LAStages[Stage].LAIndex;
        int MacroItCount = LAStages[Stage].MacroItCount;

        int length = LAIndex + MacroItCount;
        for (int i = LAIndex; i <= length; i++) {
            LAs[i].la = LAs[i].la.minimize();
        }
    }


    public boolean isLAStageInvalid(int LAIndex, Complex dc) {
        return (dc.chebychevNorm() >= LAs[LAIndex].la.dgetLAThresholdC());
    }

    public boolean isLAStageInvalid(int LAIndex, double dcChebychevNorm) {
        return (dcChebychevNorm >= LAs[LAIndex].la.dgetLAThresholdC());
    }

    public boolean isLAStageInvalid(int LAIndex, MantExpComplex dc) {
        return (dc.chebychevNorm().compareToBothPositiveReduced((LAs[LAIndex].la).getLAThresholdC()) >= 0);
    }

    public boolean isLAStageInvalid(int LAIndex, MantExp dcChebychevNorm) {
        return (dcChebychevNorm.compareToBothPositiveReduced((LAs[LAIndex].la).getLAThresholdC()) >= 0);
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

    public LAstep getLA(int LAIndex, Complex dz, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAData laData = LAs[LAIndexj];

        LAstep las;

        int l = laData.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            GenericLAInfo LAj = laData.la;
            las = LAj.Prepare(dz);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndexj + 1].la.getRef();
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

    public LAstep getLA(int LAIndex, double dre, double dim, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAData laData = LAs[LAIndexj];

        LAstep las;

        int l = laData.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            GenericLAInfo LAj = laData.la;
            las = LAj.Prepare(dre, dim);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndexj + 1].la.getRef();
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

    public LAstep getLA(int LAIndex, MantExpComplex dz, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAData laData = LAs[LAIndexj];

        LAstep las;

        int l = laData.StepLength;
        boolean usuable = iterations + l <= max_iterations;

        if(usuable) {
            GenericLAInfo LAj = laData.la;
            las = LAj.Prepare(dz);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1Deep = (MantExpComplex) LAs[LAIndexj + 1].la.getRef();
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
