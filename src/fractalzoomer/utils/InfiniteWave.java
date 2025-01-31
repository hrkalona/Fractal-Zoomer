package fractalzoomer.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InfiniteWave {
    public static InfiniteColorWaveParams[] empty = new InfiniteColorWaveParams[] {new InfiniteColorWaveParams(), new InfiniteColorWaveParams()};
    public static InfiniteColorWaveParams[] defaultParams = new InfiniteColorWaveParams[] {
            new InfiniteColorWaveParams(WaveType.SATURATION, 100.0),
            new InfiniteColorWaveParams(WaveType.HUE, 250.0),
            new InfiniteColorWaveParams(WaveType.BRIGHTNESS, 200.0)};

    public static InfiniteColorWaveParams[] user_params_out = empty;
    public static InfiniteColorWaveParams[] user_params_in = empty;

    public enum WaveType {
        HUE,
        SATURATION,
        BRIGHTNESS
    }

    public static class InfiniteColorWaveParams {

        public InfiniteColorWaveParams() {

        }

        public InfiniteColorWaveParams(WaveType type, Double period) {
            this.type = type;
            this.period = period;
        }

        public WaveType getType() {
            return type;
        }

        public void setType(WaveType type) {
            this.type = type;
        }

        public Double getPeriod() {
            return period;
        }

        public void setPeriod(Double period) {
            this.period = period;
        }

        @JsonProperty
        WaveType type;

        @JsonProperty
        Double period;

        @JsonIgnore
        public void validate() throws Exception {
            if(period == null) {
                throw new Exception("The period must be defined.");
            }
            if(type == null) {
                throw new Exception("The type must be defined.");
            }
        }

        @JsonIgnore
        public static int get(InfiniteColorWaveParams[] params, double n) {
            double nH = 0, nS = 0, nB = 0;
            int nDR = 0, nDG = 0, nDB = 0;
            int i;
            for (i = 0; i< params.length; i++){
                double nPeriod;
                nPeriod = params[i].period;
                /*
                if (m_bTrans)
					g = sin((pi*iter) / nPeriod) / 2 + .5;
				else
					g = sin((pi*((int)iter)) / nPeriod) / 2 + .5;
                 */


                double g = (Math.sin((Math.PI * n) / nPeriod) + 1) * 0.5;

                if (nPeriod<0)
                    g = -(double)nPeriod / (double)100;
                if (params[i].type == WaveType.HUE){
                    nH += g;
                    nDR++;
                }
                else if (params[i].type == WaveType.SATURATION){
                    nS += g;
                    nDG++;
                }
                else if (params[i].type == WaveType.BRIGHTNESS){
                    nB += g;
                    nDB++;
                }
            }
            if (nDR > 0) {
                nH /= nDR;
            }
            if (nDG > 0) {
                nS /= nDG;
            }
            if (nDB > 0) {
                nB /= nDB;
            }
            int[] rgb = hsv2rgb(nH, nS, nB);

            return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
        }
    }

    public static int get_color(double n, InfiniteColorWaveParams[] params) throws Exception {
        for(int i = 0; i < params.length; i++) {
            params[i].validate();
        }
        return InfiniteColorWaveParams.get(params, n);
    }

    static int[] hsv2rgb(double h, double s, double v)
    {
        double hue = h * 6.0;
        double sat = s;
        double bri = v;
        int i = (int) Math.floor(hue);
        double f = hue - i;
        if ((i & 1) == 0) {
            f = 1.0 - f;
        }
        double m = bri * (1.0 - sat);
        double n = bri * (1.0 - sat * f);
        double r = 0, g = 0, b = 0;
        switch (i)
        {
            case 6:
            case 0:
                b = bri;
                g = n;
                r = m;
                break;
            case 1:
                b = n;
                g = bri;
                r = m;
                break;
            case 2:
                b = m;
                g = bri;
                r = n;
                break;
            case 3:
                b = m;
                g = n;
                r = bri;
                break;
            case 4:
                b = n;
                g = m;
                r = bri;
                break;
            case 5:
                b = bri;
                g = m;
                r = n;
        }
        //KF is BGR
        return new int[] {ColorSpaceConverter.clamp((int)(255 * b + 0.5)), ColorSpaceConverter.clamp((int)(255 * g + 0.5)), ColorSpaceConverter.clamp((int)(255 * r + 0.5))};
    }

    public static String paramsToJson(InfiniteColorWaveParams[] params, boolean includeNulls) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if(!includeNulls) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        String text = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        return text.replace("\r\n", "\n");
    }

    public static InfiniteColorWaveParams[] jsonToParams(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        return objectMapper.readValue(json, InfiniteColorWaveParams[].class);
    }

}
