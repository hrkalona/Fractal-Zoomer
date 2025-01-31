package fractalzoomer.core.blending;

import fractalzoomer.main.MainWindow;

public class BlendingFactory {

    public static Blending blendingFactory(int interpolation, int color_blending, int color_space) {

        switch (color_blending) {
            case MainWindow.NORMAL_BLENDING:
                return new NormalBlending(interpolation, color_space);
            case MainWindow.MULTIPLY_BLENDING:
                return new MultiplyBlending(interpolation, color_space);
            case MainWindow.DIVIDE_BLENDING:
                return new DivideBlending(interpolation, color_space);
            case MainWindow.ADDITION_BLENDING:
                return new AdditionBlending(interpolation, color_space);
            case MainWindow.SUBTRACTION_BLENDING:
                return new SubtractionBlending(interpolation, color_space);
            case MainWindow.DIFFERENCE_BLENDING:
                return new DifferenceBlending(interpolation, color_space);
            case MainWindow.VALUE_BLENDING:
                return new ValueBlending(interpolation, color_space);
            case MainWindow.SOFT_LIGHT_BLENDING:
                return new SoftLightBlending(interpolation, color_space);
            case MainWindow.SCREEN_BLENDING:
                return new ScreenBlending(interpolation, color_space);
            case MainWindow.DODGE_BLENDING:
                return new DodgeBlending(interpolation, color_space);
            case MainWindow.BURN_BLENDING:
                return new BurnBlending(interpolation, color_space);
            case MainWindow.DARKEN_ONLY_BLENDING:
                return new DarkenOnlyBlending(interpolation, color_space);
            case MainWindow.LIGHTEN_ONLY_BLENDING:
                return new LightenOnlyBlending(interpolation, color_space);
            case MainWindow.HARD_LIGHT_BLENDING:
                return new HardLightBlending(interpolation, color_space);
            case MainWindow.GRAIN_EXTRACT_BLENDING:
                return new GrainExtractBlending(interpolation, color_space);
            case MainWindow.GRAIN_MERGE_BLENDING:
                return new GrainMergeBlending(interpolation, color_space);
            case MainWindow.SATURATION_BLENDING:
                return new SaturationBlending(interpolation, color_space);
            case MainWindow.COLOR_BLENDING:
                return new ColorBlending(interpolation, color_space);
            case MainWindow.HUE_BLENDING:
                return new HueBlending(interpolation, color_space);
            case MainWindow.EXCLUSION_BLENDING:
                return new ExclusionBlending(interpolation, color_space);
            case MainWindow.PIN_LIGHT_BLENDING:
                return new PinLightBlending(interpolation, color_space);
            case MainWindow.LINEAR_LIGHT_BLENDING:
                return new LinearLightBlending(interpolation, color_space);
            case MainWindow.VIVID_LIGHT_BLENDING:
                return new VividLightBlending(interpolation, color_space);
            case MainWindow.OVERLAY_BLENDING:
                return new OverlayBlending(interpolation, color_space);
            case MainWindow.LCH_CHROMA_BLENDING:
                return new LCHChromaBlending(interpolation, color_space);
            case MainWindow.LCH_COLOR_BLENDING:
                return new LCHColorBlending(interpolation, color_space);
            case MainWindow.LCH_HUE_BLENDING:
                return new LCHHueBlending(interpolation, color_space);
            case MainWindow.LCH_LIGHTNESS_BLENDING:
                return new LCHLightnessBlending(interpolation, color_space);
            case MainWindow.LUMINANCE_BLENDING:
                return new LuminanceBlending(interpolation, color_space);
            case MainWindow.LINEAR_BURN_BLENDING:
                return new LinearBurnBlending(interpolation, color_space);
        }

        return null;
    }
}
