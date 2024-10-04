package fractalzoomer.main.app_settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.main.Constants;
import org.apfloat.Apfloat;

public class ZoomSequenceSettings {
    public double rotation_adjusting_value;
    public int color_cycling_adjusting_value;

    public int gradient_color_cycling_adjusting_value;

    public double light_direction_adjusting_value;
    public double slopes_direction_adjusting_value;
    public double bump_direction_adjusting_value;
    public int zooming_mode;

    public int zoom_every_n_frame;

    public double zoom_factor;

    public boolean flipSequenceIndexing;
    public long startAtSequenceIndex;
    public long sequenceIndexOffset;
    public  String sizeStr;
    public  String endSizeStr;

    public String file_name_pattern;

    public long stop_after_n_steps;

    public int override_max_iterations;
    public String overrideMaxIterationsSizeLimitStr;
    public boolean saveSettingsOnEachStep;

    @JsonIgnore
    public Apfloat startSize;

    @JsonIgnore
    public Apfloat endSize;

    @JsonIgnore
    public Apfloat overrideMaxIterationsSizeLimit;


    public ZoomSequenceSettings() {
        startSize = Constants.DEFAULT_MAGNIFICATION;
        sizeStr = startSize.toString();

        zoom_factor = 2.0;
        flipSequenceIndexing = false;
        startAtSequenceIndex = 0;

        saveSettingsOnEachStep = false;

        rotation_adjusting_value = 0;
        color_cycling_adjusting_value = 0;

        gradient_color_cycling_adjusting_value = 0;

        light_direction_adjusting_value = 0;
        slopes_direction_adjusting_value = 0;
        bump_direction_adjusting_value = 0;

        zooming_mode = 0;

        zoom_every_n_frame = 1;

        file_name_pattern = "";

        stop_after_n_steps = 0;
        override_max_iterations = 0;
        overrideMaxIterationsSizeLimitStr = "0";
        overrideMaxIterationsSizeLimit = new MyApfloat(0);
        sequenceIndexOffset = 0;
    }

    public void setStartSize(Apfloat startSize) {
        this.startSize = startSize;
        sizeStr = startSize.toString();
    }

    public void setEndSize(Apfloat endSize) {
        this.endSize = endSize;
        endSizeStr = endSize.toString();
    }

    public void setOverrideMaxIterationsSizeLimit(Apfloat overrideMaxIterationsSizeLimit) {
        this.overrideMaxIterationsSizeLimit = overrideMaxIterationsSizeLimit;
        overrideMaxIterationsSizeLimitStr = overrideMaxIterationsSizeLimit.toString();
    }
}
