package fractalzoomer.core.la;

import fractalzoomer.core.*;
import fractalzoomer.functions.Fractal;

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
    public LAInfoI(LAInfoI other) {
        StepLength = other.StepLength;
        NextStageLAIndex = other.NextStageLAIndex;
    }
};
public class LAReference {

    private static final int lowBound = 64;
    private static final double log16 = Math.log(16);

    private static final MantExp doubleRadiusLimit = new MantExp(0x1.0p-896);
    private static final MantExp doubleThresholdLimit = new MantExp(0x1.0p-768);
    public boolean UseAT;

    public ATInfo AT;

    public int LAStageCount;

    public boolean isValid;

    public boolean DoublePrecisionPT;

    public boolean calculatedForDeep;

    private static final int MaxLAStages = 512;
    private static final int DEFAULT_SIZE = 10000;
    private GenericLAInfo[] LAs;
    private int LAcurrentIndex;
    private int LAIcurrentIndex;
    private LAInfoI[] LAIs;

    private LAStageInfo[] LAStages;

    private void init(boolean deepZoom) {

        calculatedForDeep = deepZoom;
        isValid = false;
        LAStages = new LAStageInfo[MaxLAStages];
        LAcurrentIndex = 0;
        LAIcurrentIndex = 0;

        LAs = new GenericLAInfo[DEFAULT_SIZE];
        LAIs = new LAInfoI[DEFAULT_SIZE];

        UseAT = false;
        LAStageCount = 0;

        for(int i = 0; i < LAStages.length; i++) {
            LAStages[i] = new LAStageInfo();
        }

        LAStages[0].UseDoublePrecision = !deepZoom;

        LAStages[0].LAIndex = 0;
    }

    private void addToLA(GenericLAInfo la) {
        LAs[LAcurrentIndex] = la;
        LAcurrentIndex++;
        if (LAcurrentIndex >= LAs.length) {
            LAs = Arrays.copyOf(LAs, LAs.length << 1);
        }
    }

    private int LAsize() {
        return LAcurrentIndex;
    }

    private void addToLAI(LAInfoI lai) {
        LAIs[LAIcurrentIndex] = lai;
        LAIcurrentIndex++;
        if (LAIcurrentIndex >= LAIs.length) {
            LAIs = Arrays.copyOf(LAIs, LAIs.length << 1);
        }
    }

    private void popLA() {
        if(LAcurrentIndex > 0) {
            LAcurrentIndex--;
            LAs[LAcurrentIndex] = null;
        }
    }

    private void popLAI() {
        if(LAIcurrentIndex > 0) {
            LAIcurrentIndex--;
            LAIs[LAIcurrentIndex] = null;
        }
    }


    private boolean CreateLAFromOrbit(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom) throws Exception {

        init(deepZoom);

        int Period = 0;

        GenericLAInfo LA;

        if(deepZoom) {
            LA = new LAInfoDeep(new MantExpComplex());
        }
        else {
            LA = new LAInfo(new Complex());
        }

        if(deepZoom) {
            LA = LA.Step(Fractal.getArrayDeepValue(refDeep, 1));
        }
        else {
            LA = LA.Step(Fractal.getArrayValue(ref, 1));
        }

        LAInfoI LAI = new LAInfoI();
        LAI.NextStageLAIndex = 0;

        if(LA.isZCoeffZero()) {
            return false;
        }

        int i;
        for (i = 2; i < maxRefIteration; i++) {

            GenericLAInfo NewLA;
            boolean PeriodDetected;

            if(deepZoom) {
                NewLA = new LAInfoDeep();
                PeriodDetected = LA.Step(NewLA, Fractal.getArrayDeepValue(refDeep, i));
            }
            else {
                NewLA = new LAInfo();
                PeriodDetected = LA.Step(NewLA, Fractal.getArrayValue(ref, i));
            }

            if (PeriodDetected) {
                Period = i;
                LAI.StepLength = Period;

                addToLA(LA);
                addToLAI(new LAInfoI(LAI));

                LAI.NextStageLAIndex = i;

                if (i + 1 < maxRefIteration) {
                    if(deepZoom) {
                        LA = new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, i)).Step(Fractal.getArrayDeepValue(refDeep, i + 1));
                    }
                    else {
                        LA = new LAInfo(Fractal.getArrayValue(ref, i)).Step(Fractal.getArrayValue(ref, i + 1));
                    }
                    i += 2;
                } else {
                    if(deepZoom) {
                        LA = new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, i));
                    }
                    else {
                        LA = new LAInfo(Fractal.getArrayValue(ref, i));
                    }
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
                if(deepZoom) {
                    LA = new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, 0)).Step(Fractal.getArrayDeepValue(refDeep, 1));
                }
                else {
                    LA = new LAInfo(Fractal.getArrayValue(ref, 0)).Step(Fractal.getArrayValue(ref, 1));
                }
                LAI.NextStageLAIndex = 0;
                i = 2;

                double NthRoot = Math.round(Math.log(maxRefIteration) / log16);
                Period = (int)Math.round(Math.pow(maxRefIteration, 1.0 / NthRoot));

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                addToLA(LA);
                addToLAI(new LAInfoI(LAI));

                if(deepZoom) {
                    addToLA(new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, maxRefIteration)));
                }
                else {
                    addToLA(new LAInfo(Fractal.getArrayValue(ref, maxRefIteration)));
                }

                LAStages[0].MacroItCount = 1;

                return false;
            }
        } else if (Period > lowBound) {
            popLA();
            popLAI();

            if(deepZoom) {
                LA = new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, 0)).Step(Fractal.getArrayDeepValue(refDeep, 1));
            }
            else {
                LA = new LAInfo(Fractal.getArrayValue(ref, 0)).Step(Fractal.getArrayValue(ref, 1));
            }
            LAI.NextStageLAIndex = 0;
            i = 2;

            double NthRoot = Math.round(Math.log(Period) / log16);
            Period = (int)Math.round(Math.pow(Period, 1.0 / NthRoot));

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        for (; i < maxRefIteration; i++) {
            GenericLAInfo NewLA;
            boolean PeriodDetected;

            if(deepZoom) {
                NewLA = new LAInfoDeep();
                PeriodDetected = LA.Step(NewLA, Fractal.getArrayDeepValue(refDeep, i));
            }
            else {
                NewLA = new LAInfo();
                PeriodDetected = LA.Step(NewLA, Fractal.getArrayValue(ref, i));
            }

            if (PeriodDetected || i >= PeriodEnd) {
                LAI.StepLength = i - PeriodBegin;

                addToLA(LA);
                addToLAI(new LAInfoI(LAI));

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
                    if(deepZoom) {
                        LA = new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, i));
                    }
                    else {
                        LA = new LAInfo(Fractal.getArrayValue(ref, i));
                    }
                } else {
                    if(deepZoom) {
                        LA = new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, i)).Step(Fractal.getArrayDeepValue(refDeep, ip1));
                    }
                    else {
                        LA = new LAInfo(Fractal.getArrayValue(ref, i)).Step(Fractal.getArrayValue(ref, ip1));
                    }
                    i++;
                }
            } else {
                LA = NewLA;
            }
        }

        LAI.StepLength = i - PeriodBegin;

        addToLA(LA);
        addToLAI(new LAInfoI(LAI));

        LAStages[0].MacroItCount = LAsize();

        if(deepZoom) {
            addToLA(new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, maxRefIteration)));
        }
        else {
            addToLA(new LAInfo(Fractal.getArrayValue(ref, maxRefIteration)));
        }

        addToLAI(new LAInfoI());

        return true;
    }


    private boolean CreateNewLAStage(DoubleReference ref, DeepReference refDeep, int maxRefIteration, boolean deepZoom) throws Exception {
        GenericLAInfo LA;
        LAInfoI LAI = new LAInfoI();
        int i;
        int PeriodBegin;
        int PeriodEnd;

        int PrevStage = LAStageCount - 1;
        int CurrentStage = LAStageCount;
        int PrevStageLAIndex = LAStages[PrevStage].LAIndex;
        int PrevStageMacroItCount = LAStages[PrevStage].MacroItCount;
        GenericLAInfo PrevStageLA = LAs[PrevStageLAIndex];
        LAInfoI PrevStageLAI = LAIs[PrevStageLAIndex];

        GenericLAInfo PrevStageLAp1 = LAs[PrevStageLAIndex + 1];
        LAInfoI PrevStageLAIp1 = LAIs[PrevStageLAIndex + 1];

        int Period = 0;

        if (CurrentStage >= MaxLAStages) throw new Exception("Too many stages");

        LAStages[CurrentStage].UseDoublePrecision = !deepZoom;
        LAStages[CurrentStage].LAIndex = LAsize();

        LA = PrevStageLA.Composite(PrevStageLAp1);
        LAI.NextStageLAIndex = 0;
        i = PrevStageLAI.StepLength + PrevStageLAIp1.StepLength;
        int j;

        for (j = 2; j < PrevStageMacroItCount; j++) {
            GenericLAInfo NewLA;

            if(deepZoom) {
                NewLA = new LAInfoDeep();
            }
            else {
                NewLA = new LAInfo();
            }

            int PrevStageLAIndexj = PrevStageLAIndex + j;
            GenericLAInfo PrevStageLAj = LAs[PrevStageLAIndexj];
            LAInfoI PrevStageLAIj = LAIs[PrevStageLAIndexj];
            boolean PeriodDetected = LA.Composite(NewLA, PrevStageLAj);

            if (PeriodDetected) {
                if (PrevStageLAj.isLAThresholdZero()) break;
                Period = i;

                LAI.StepLength = Period;

                addToLA(LA);
                addToLAI(new LAInfoI(LAI));

                LAI.NextStageLAIndex = j;

                int PrevStageLAIndexjp1 = PrevStageLAIndexj + 1;
                GenericLAInfo PrevStageLAjp1 = LAs[PrevStageLAIndexjp1];
                LAInfoI PrevStageLAIjp1 = LAIs[PrevStageLAIndexjp1];

                if (NewLA.DetectPeriod(PrevStageLAjp1.getRef()) || j + 1 >= PrevStageMacroItCount) {
                    LA = PrevStageLAj;
                    i += PrevStageLAIj.StepLength;
                    j++;
                } else {
                    LA = PrevStageLAj.Composite(PrevStageLAjp1);
                    i += PrevStageLAIj.StepLength + PrevStageLAIjp1.StepLength;
                    j += 2;
                }
                break;
            }
            LA = NewLA;
            PrevStageLAIj = LAIs[PrevStageLAIndex + j];
            i += PrevStageLAIj.StepLength;
        }
        LAStageCount++;
        if (LAStageCount > MaxLAStages) throw new Exception("Too many stages");

        PeriodBegin = Period;
        PeriodEnd = PeriodBegin + Period;

        if (Period == 0) {
            if (maxRefIteration > PrevStageLAI.StepLength * lowBound) {
                LA = PrevStageLA.Composite(PrevStageLAp1);
                i = PrevStageLAI.StepLength + PrevStageLAIp1.StepLength;
                LAI.NextStageLAIndex = 0;

                j = 2;

                double Ratio = ((double)(maxRefIteration)) / PrevStageLAI.StepLength;
                double NthRoot = Math.round(Math.log(Ratio) / log16); // log16
                Period = PrevStageLAI.StepLength * (int)Math.round(Math.pow(Ratio, 1.0 / NthRoot));

                PeriodBegin = 0;
                PeriodEnd = Period;
            } else {
                LAI.StepLength = maxRefIteration;

                addToLA(LA);
                addToLAI(new LAInfoI(LAI));


                if(deepZoom) {
                    addToLA(new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, maxRefIteration)));
                }
                else {
                    addToLA(new LAInfo(Fractal.getArrayValue(ref, maxRefIteration)));
                }

                LAStages[CurrentStage].MacroItCount = 1;

                return false;
            }
        }
        else if (Period > PrevStageLAI.StepLength * lowBound) {
            popLA();
            popLAI();

            LA = PrevStageLA.Composite(PrevStageLAp1);
            i = PrevStageLAI.StepLength + PrevStageLAIp1.StepLength;
            LAI.NextStageLAIndex = 0;

            j = 2;

            double Ratio = ((double)(Period)) / PrevStageLAI.StepLength;

            double NthRoot = Math.round(Math.log(Ratio) / log16);
            Period = PrevStageLAI.StepLength * ((int)Math.round(Math.pow(Ratio, 1.0 / NthRoot)));

            PeriodBegin = 0;
            PeriodEnd = Period;
        }

        for (; j < PrevStageMacroItCount; j++) {
            GenericLAInfo NewLA;

            if(deepZoom) {
                NewLA = new LAInfoDeep();
            }
            else {
                NewLA = new LAInfo();
            }

            int PrevStageLAIndexj = PrevStageLAIndex + j;

            GenericLAInfo PrevStageLAj = LAs[PrevStageLAIndexj];
            LAInfoI PrevStageLAIj = LAIs[PrevStageLAIndexj];
            boolean PeriodDetected = LA.Composite(NewLA, PrevStageLAj);

            if (PeriodDetected || i >= PeriodEnd) {
                LAI.StepLength = i - PeriodBegin;

                addToLA(LA);
                addToLAI(new LAInfoI(LAI));

                LAI.NextStageLAIndex = j;
                PeriodBegin = i;
                PeriodEnd = PeriodBegin + Period;

                GenericLAInfo PrevStageLAjp1 = LAs[PrevStageLAIndexj + 1];

                if (NewLA.DetectPeriod(PrevStageLAjp1.getRef()) || j + 1 >= PrevStageMacroItCount) {
                    LA = PrevStageLAj;
                } else {
                    LA = PrevStageLAj.Composite(PrevStageLAjp1);
                    i += PrevStageLAIj.StepLength;
                    j++;
                }
            } else {
                LA = NewLA;
            }
            PrevStageLAIj = LAIs[PrevStageLAIndex + j];
            i += PrevStageLAIj.StepLength;
        }

        LAI.StepLength = i - PeriodBegin;

        addToLA(LA);
        addToLAI(new LAInfoI(LAI));

        LAStages[CurrentStage].MacroItCount = LAsize() - LAStages[CurrentStage].LAIndex;

        if(deepZoom) {
            addToLA(new LAInfoDeep(Fractal.getArrayDeepValue(refDeep, maxRefIteration)));
        }
        else {
            addToLA(new LAInfo(Fractal.getArrayValue(ref, maxRefIteration)));
        }

        addToLAI(new LAInfoI());
        return true;
    }

    private boolean DoubleUsableAtPrevStage(int stage, MantExp radius) {
            int LAIndex = LAStages[stage].LAIndex;
            GenericLAInfo LA = LAs[LAIndex];
            return radius.compareToBothPositiveReduced(doubleRadiusLimit) > 0
                    || (LA.getLAThreshold().compareToBothPositiveReduced(doubleThresholdLimit) > 0
                         && LA.getLAThresholdC().compareToBothPositiveReduced(doubleThresholdLimit) > 0);

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

            if (deepZoom && !f.useFullFloatExp()) {
                if (DoubleUsableAtPrevStage(0, radius)) {
                    DoublePrecisionPT = true;
                }
            }

            boolean convertedToDouble = false;

            while (true) {
                int PrevStage = LAStageCount - 1;
                int CurrentStage = LAStageCount;

                try {
                    PeriodDetected = CreateNewLAStage(refData.Reference, refDeepData.Reference, maxRefIteration, deepZoom);

                }
                catch (InvalidCalculationException ex) {
                    PeriodDetected = false;
                }

                if (deepZoom && !f.useFullFloatExp()) {
                    if (DoubleUsableAtPrevStage(CurrentStage, radius)) {
                        ConvertStageToDouble(PrevStage);
                        convertedToDouble = true;
                    }
                }

                if (!PeriodDetected) break;
            }

            CreateATFromLA(radius);

            if (deepZoom && !f.useFullFloatExp()) {
                if (radius.compareToBothPositiveReduced(doubleRadiusLimit) > 0) {
                    ConvertStageToDouble(LAStageCount - 1);
                    convertedToDouble = true;
                }
            }

            //Recreate data to save memory
            if (deepZoom && refData.Reference == null && (convertedToDouble || DoublePrecisionPT)) {
                f.createLowPrecisionOrbit(maxRefIteration, refData, refDeepData);
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
        SqrRadius.Reduce();

        for (int Stage = LAStageCount; Stage > 0; ) {
            Stage--;
            int LAIndex = LAStages[Stage].LAIndex;
            AT = LAs[LAIndex].CreateAT(LAs[LAIndex + 1]);
            AT.StepLength = LAIs[LAIndex].StepLength;
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

        for (int i = 0; i <= MacroItCount; i++) {
            LAs[LAIndex + i] = new LAInfo((LAInfoDeep)LAs[LAIndex + i]);
        }
    }


    public boolean isLAStageInvalid(int LAIndex, Complex dc) {
        return (dc.chebychevNorm() >= ((LAInfo)LAs[LAIndex]).LAThresholdC);
    }

    public boolean isLAStageInvalid(int LAIndex, double dcChebychevNorm) {
        return (dcChebychevNorm >= ((LAInfo)LAs[LAIndex]).LAThresholdC);
    }

    public boolean isLAStageInvalid(int LAIndex, MantExpComplex dc) {
        return (dc.chebychevNorm().compareToBothPositiveReduced((LAs[LAIndex]).getLAThresholdC()) >= 0);
    }

    public boolean isLAStageInvalid(int LAIndex, MantExp dcChebychevNorm) {
        return (dcChebychevNorm.compareToBothPositiveReduced((LAs[LAIndex]).getLAThresholdC()) >= 0);
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

    public LAstep getLA(int LAIndex, Complex dz, Complex dc, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAInfoI LAIj = LAIs[LAIndexj];

        LAstep las;

        int l = LAIj.StepLength;
        boolean usuable  = iterations + l <= max_iterations;

        if(usuable) {
            LAInfo LAj = (LAInfo) LAs[LAIndexj];
            las = LAj.Prepare(dz, dc);

            if(!las.unusable) {
                las.LAj = LAj;
                las.Refp1 = (Complex) LAs[LAIndexj + 1].getRef();
                las.step = LAIj.StepLength;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.nextStageLAindex = LAIj.NextStageLAIndex;

        return las;

    }

    public LAstep getLA(int LAIndex, MantExpComplex dz, MantExpComplex dc, int j, int iterations, int max_iterations) {

        int LAIndexj = LAIndex + j;
        LAInfoI LAIj = LAIs[LAIndexj];

        LAstep las;

        int l = LAIj.StepLength;
        boolean usuable = iterations + l <= max_iterations;

        if(usuable) {
            LAInfoDeep LAj = (LAInfoDeep) LAs[LAIndexj];

            las = LAj.Prepare(dz, dc);

            if(!las.unusable) {
                las.LAjdeep = LAj;
                las.Refp1Deep = (MantExpComplex) LAs[LAIndexj + 1].getRef();
                las.step = LAIj.StepLength;
            }
        }
        else {
            las = new LAstep();
            las.unusable = true;
        }

        las.nextStageLAindex = LAIj.NextStageLAIndex;

        return las;

    }

}
