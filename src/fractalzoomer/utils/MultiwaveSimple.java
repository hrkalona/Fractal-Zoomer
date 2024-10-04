package fractalzoomer.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MultiwaveSimple {
    private static double TWO_PI = Math.PI * 2;
    public static MultiwaveSimpleColorParams[] empty = new MultiwaveSimpleColorParams[] {new MultiwaveSimpleColorParams(), new MultiwaveSimpleColorParams()};
    public static MultiwaveSimpleColorParams[] defaultParams = new MultiwaveSimpleColorParams[] {
            new MultiwaveSimpleColorParams(100.0, 1.0, 0.0, WaveType.R),
            new MultiwaveSimpleColorParams(50.0, 0.5, 0.0, WaveType.G),
            new MultiwaveSimpleColorParams(210.0, 0.8, 0.0, WaveType.B),
            new MultiwaveSimpleColorParams(100.0, 0.4, 10.0, WaveType.R),
    };
    public static MultiwaveSimpleColorParams[] user_params_out = empty;
    public static MultiwaveSimpleColorParams[] user_params_in = empty;

    enum WaveType {
        R,
        G,
        B
    }
    public static class MultiwaveSimpleColorParams {
        public MultiwaveSimpleColorParams() {

        }

        public MultiwaveSimpleColorParams(Double wavelength, Double amplitude, Double phase, WaveType type) {
            this.wavelength = wavelength;
            this.amplitude = amplitude;
            this.phase = phase;
            this.type = type;
        }

        @JsonIgnore
        public void validate() throws Exception {
            if(wavelength == null) {
                throw new Exception("The wavelength must be defined.");
            }
            if(amplitude == null) {
                throw new Exception("The amplitude must be defined.");
            }
            if(phase == null) {
                throw new Exception("The phase must be defined.");
            }
            if(type == null) {
                throw new Exception("The type must be defined.");
            }
            if(amplitude < 0 || amplitude > 1) {
                throw new Exception("The amplitude must be in range of [0, 1].");
            }
            if(wavelength <= 0) {
                throw new Exception("The wavelength must be greater than 0.");
            }
        }

        @JsonProperty
        private Double wavelength;
        @JsonProperty
        private Double phase;
        @JsonProperty
        private Double amplitude;
        @JsonProperty
        private WaveType type;

        public Double getPhase() {
            return phase;
        }

        public void setPhase(Double phase) {
            this.phase = phase;
        }

        public Double getWavelength() {
            return wavelength;
        }

        public void setWavelength(Double wavelength) {
            this.wavelength = wavelength;
        }

        public Double getAmplitude() {
            return amplitude;
        }

        public void setAmplitude(Double amplitude) {
            this.amplitude = amplitude;
        }

        public WaveType getType() {
            return type;
        }

        public void setType(WaveType type) {
            this.type = type;
        }

        static double velocity_add(double a, double b)
        {
            double a1 = a - 0.5, b1 = b - 0.5;
            return (((a1 + b1) / (((a1 * b1) * 4) + 1)) + 0.5);
        }

       static double wave_eval(double wavelength, double amplitude, double phase, double position) {
            return (((Math.sin((position % wavelength) * (TWO_PI / wavelength) + phase) * amplitude) + 1) * 0.5);
       }

        @JsonIgnore
        public static int get(MultiwaveSimpleColorParams[] params, double n) {
            double red_val = 0;
            boolean first_red = true;
            double green_val = 0;
            boolean first_green = true;
            double blue_val = 0;
            boolean first_blue = true;
            for(int i = 0; i < params.length; i++) {
                double current_val = wave_eval(params[i].wavelength, params[i].amplitude, params[i].phase, n);
                if(params[i].type == WaveType.R) {
                    if(first_red) {
                        red_val = current_val;
                        first_red = false;
                    }
                    else {
                        red_val = velocity_add(red_val, current_val);
                    }
                }
                else if(params[i].type == WaveType.G) {
                    if(first_green) {
                        green_val = current_val;
                        first_green = false;
                    }
                    else {
                        green_val = velocity_add(green_val, current_val);
                    }
                }
                else {
                    if(first_blue) {
                        blue_val = current_val;
                        first_blue = false;
                    }
                    else {
                        blue_val = velocity_add(blue_val, current_val);
                    }
                }
            }

            return 0xff000000 | (ColorSpaceConverter.clamp((int)(255 * red_val + 0.5)) << 16) | (ColorSpaceConverter.clamp((int)(255 * green_val + 0.5)) << 8) | ColorSpaceConverter.clamp((int)(255 * blue_val + 0.5));
        }
    }

    public static int get_color(double n, MultiwaveSimpleColorParams[] params) throws Exception {
        for(int i = 0; i < params.length; i++) {
            params[i].validate();
        }
        return MultiwaveSimpleColorParams.get(params, n);
    }

    public static String paramsToJson(MultiwaveSimpleColorParams[] params, boolean includeNulls) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if(!includeNulls) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        String text = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        return text.replace("\r\n", "\n");
    }

    public static MultiwaveSimpleColorParams[] jsonToParams(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        return objectMapper.readValue(json, MultiwaveSimpleColorParams[].class);
    }
}
