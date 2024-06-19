
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public class StatisticsSettings implements Constants {
    public static final int[] defRootColors = {0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0xFFA500, 0xff00ff, 0x00ffff, 0x00FF7F, 0x9400D3, 0xFF7F50};
    public boolean statistic;
    public int statistic_type;
    public double statistic_intensity;
    public double stripeAvgStripeDensity;
    public double cosArgStripeDensity;
    public double cosArgInvStripeDensity;
    public double StripeDenominatorFactor;
    public int statisticGroup;
    public String user_statistic_formula;
    public boolean useAverage;
    public int statistic_escape_type;
    public boolean statisticIncludeEscaped;
    public boolean statisticIncludeNotEscaped;
    public String user_statistic_init_value;
    public boolean showAtomDomains;
    public int reductionFunction;
    public boolean useIterations;
    public boolean useSmoothing;
    public double lagrangianPower;
    public double equicontinuityDenominatorFactor;
    public int equicontinuityColorMethod;
    public int equicontinuityMixingMethod;
    public double equicontinuityBlending;
    public double equicontinuitySatChroma;
    public int equicontinuityArgValue;
    public boolean equicontinuityInvertFactor;
    public boolean equicontinuityOverrideColoring;
    public double equicontinuityDelta;

    public boolean useNormalMap;
    public int normalMapColorMode;
    public boolean normalMapOverrideColoring;
    public double normalMapLightFactor;
    public double normalMapBlending;
    public double normalMapHeight;
    public double normalMapAngle;
    public boolean normalMapUseSecondDerivative;
    public boolean normalMapUseDE;
    public double normalMapDEfactor;
    public boolean normalMapInvertDE;
    public int normalMapDeFadeAlgorithm;
    public double normalMapDEUpperLimitFactor;
    public int normalMapColoring;
    public boolean normalMapDEAAEffect;
    public double normalMapDistanceEstimatorfactor;
    public int normalMapDEOffset;
    public double normalMapDEOffsetFactor;
    public boolean normalMapDEUseColorPerDepth;
    public boolean normalMapCombineWithOtherStatistics;

    public double rootIterationsScaling;
    public boolean rootShading;
    public int rootContourColorMethod;
    public double rootBlending;
    public int rootShadingFunction;
    public boolean revertRootShading;
    public boolean highlightRoots;
    public boolean rootSmooting;
    public int[] rootColors;

    public int twlFunction;
    public double[] twlPoint;

    public int langNormType;
    public double langNNorm;
    public double langNormA;
    public double langNormB;

    public int atomNormType;
    public double atomNNorm;

    public Color unmmapedRootColor;
    public Color rootShadingColor;

    public int lastXItems;

    public double patternScale;
    public int checkerNormType;
    public double checkerNormValue;

    public double atomDomainNormA;
    public double atomDomainNormB;

    public double checkerNormA;
    public double checkerNormB;

    public boolean rootScalingCapto1;
    
    public StatisticsSettings(StatisticsSettings copy) {
        statistic = copy.statistic;
        statistic_type = copy.statistic_type;
        statistic_intensity = copy.statistic_intensity;
        stripeAvgStripeDensity = copy.stripeAvgStripeDensity;
        cosArgStripeDensity = copy.cosArgStripeDensity;
        cosArgInvStripeDensity = copy.cosArgInvStripeDensity;
        StripeDenominatorFactor = copy.StripeDenominatorFactor;
        statisticGroup = copy.statisticGroup;
        user_statistic_formula = copy.user_statistic_formula;
        useAverage = copy.useAverage;
        statistic_escape_type = copy.statistic_escape_type;
        statisticIncludeNotEscaped = copy.statisticIncludeNotEscaped;  
        statisticIncludeEscaped = copy.statisticIncludeEscaped;
        user_statistic_init_value = copy.user_statistic_init_value;
        showAtomDomains = copy.showAtomDomains;
        reductionFunction = copy.reductionFunction;
        useIterations = copy.useIterations;
        useSmoothing = copy.useSmoothing;
        lagrangianPower = copy.lagrangianPower;
        equicontinuityDenominatorFactor = copy.equicontinuityDenominatorFactor;
        equicontinuityColorMethod = copy.equicontinuityColorMethod;
        equicontinuityMixingMethod = copy.equicontinuityMixingMethod;
        equicontinuityBlending = copy.equicontinuityBlending;
        equicontinuitySatChroma = copy.equicontinuitySatChroma;
        equicontinuityArgValue = copy.equicontinuityArgValue;
        equicontinuityInvertFactor = copy.equicontinuityInvertFactor;
        equicontinuityOverrideColoring = copy.equicontinuityOverrideColoring;
        equicontinuityDelta = copy.equicontinuityDelta;

        normalMapColorMode = copy.normalMapColorMode;
        normalMapOverrideColoring = copy.normalMapOverrideColoring;
        normalMapLightFactor = copy.normalMapLightFactor;
        normalMapBlending = copy.normalMapBlending;
        normalMapHeight = copy.normalMapHeight;
        normalMapAngle = copy.normalMapAngle;
        normalMapUseSecondDerivative = copy.normalMapUseSecondDerivative;
        normalMapDEfactor = copy.normalMapDEfactor;
        normalMapUseDE = copy.normalMapUseDE;
        normalMapInvertDE = copy.normalMapInvertDE;
        normalMapColoring = copy.normalMapColoring;
        useNormalMap = copy.useNormalMap;
        normalMapDeFadeAlgorithm = copy.normalMapDeFadeAlgorithm;
        normalMapDistanceEstimatorfactor = copy.normalMapDistanceEstimatorfactor;
        normalMapDEOffset = copy.normalMapDEOffset;
        normalMapDEOffsetFactor = copy.normalMapDEOffsetFactor;
        normalMapDEUseColorPerDepth = copy.normalMapDEUseColorPerDepth;
        normalMapCombineWithOtherStatistics = copy.normalMapCombineWithOtherStatistics;

        rootIterationsScaling = copy.rootIterationsScaling;
        rootContourColorMethod = copy.rootContourColorMethod;
        rootBlending = copy.rootBlending;
        rootShading = copy.rootShading;
        rootShadingFunction = copy.rootShadingFunction;
        revertRootShading = copy.revertRootShading;
        highlightRoots = copy.highlightRoots;
        rootSmooting = copy.rootSmooting;

        rootColors = new int[copy.rootColors.length];
        for(int i = 0; i < rootColors.length; i++) {
            rootColors[i] = copy.rootColors[i];
        }

        twlFunction = copy.twlFunction;
        twlPoint = copy.twlPoint;

        langNormType = copy.langNormType;
        langNNorm = copy.langNNorm;

        atomNormType = copy.atomNormType;
        atomNNorm = copy.atomNNorm;
        unmmapedRootColor = copy.unmmapedRootColor;
        rootShadingColor = copy.rootShadingColor;
        normalMapDEUpperLimitFactor = copy.normalMapDEUpperLimitFactor;
        normalMapDEAAEffect = copy.normalMapDEAAEffect;

        lastXItems = copy.lastXItems;

        patternScale = copy.patternScale;
        checkerNormType = copy.checkerNormType;
        checkerNormValue = copy.checkerNormValue;

        atomDomainNormA = copy.atomDomainNormA;
        atomDomainNormB = copy.atomDomainNormB;
        checkerNormA = copy.checkerNormA;
        checkerNormB = copy.checkerNormB;
        langNormA = copy.langNormA;
        langNormB = copy.langNormB;
        rootScalingCapto1 = copy.rootScalingCapto1;

    }
    
    public StatisticsSettings() {
        statistic = false;
        statistic_type = STRIPE_AVERAGE;
        statistic_intensity = 1;
        stripeAvgStripeDensity = 2.5;
        cosArgStripeDensity = 12;
        cosArgInvStripeDensity = 6;
        StripeDenominatorFactor = 12;
        statisticGroup = 0;
        user_statistic_formula = "(0.5 * cos(12 * arg(z)) + 0.5) / norm(z)";
        user_statistic_init_value = "0.0";
        useAverage = true;
        statistic_escape_type = ESCAPING;
        statisticIncludeNotEscaped = false;  
        statisticIncludeEscaped = true;
        showAtomDomains = false;
        reductionFunction = REDUCTION_SUM;
        useIterations = false;
        useSmoothing = true;
        lagrangianPower = 0.25;

        equicontinuityDenominatorFactor = 1000;
        equicontinuityColorMethod = 0;
        equicontinuityMixingMethod = 0;
        equicontinuityBlending = 0;
        equicontinuitySatChroma = 0.5;
        equicontinuityArgValue = 0;
        equicontinuityInvertFactor = false;
        equicontinuityOverrideColoring = true;
        equicontinuityDelta = 1e-4;

        normalMapColorMode = 0;
        normalMapOverrideColoring = true;
        normalMapLightFactor = 0.5;
        normalMapBlending = 0.5;
        normalMapHeight = 1.5;
        normalMapAngle = 45;
        normalMapUseSecondDerivative = false;
        normalMapDEfactor = 1;
        normalMapDEUpperLimitFactor = 0;
        normalMapUseDE = false;
        normalMapInvertDE = false;
        normalMapColoring = 0;
        useNormalMap = true;
        normalMapDEAAEffect = true;
        normalMapDeFadeAlgorithm = 0;
        normalMapDistanceEstimatorfactor = 1;
        normalMapDEOffset = 0;
        normalMapDEOffsetFactor = 1;
        normalMapDEUseColorPerDepth = false;
        normalMapCombineWithOtherStatistics = false;

        rootIterationsScaling = 30;
        rootContourColorMethod = 0;
        rootBlending = 0.5;
        rootShadingFunction = 0;
        revertRootShading = false;
        rootShading = true;
        highlightRoots = true;
        rootColors = new int[defRootColors.length];
        for(int i = 0; i < rootColors.length; i++) {
            rootColors[i] = defRootColors[i];
        }
        rootSmooting = true;

        twlFunction = 0;
        twlPoint = new double[2];
        twlPoint[0] = 2.81395;
        twlPoint[1] = -1.88372;

        langNormType = 0;
        langNNorm = 2;

        atomNormType = 0;
        atomNNorm = 2;

        unmmapedRootColor = Color.BLACK;
        rootShadingColor = Color.BLACK;
        lastXItems = 20;

        patternScale = 5;
        checkerNormType = 0;
        checkerNormValue = 2;

        atomDomainNormA = 1;
        atomDomainNormB = 1;
        checkerNormA = 1;
        checkerNormB = 1;
        langNormA = 1;
        langNormB = 1;
        rootScalingCapto1 = true;
    }
}
