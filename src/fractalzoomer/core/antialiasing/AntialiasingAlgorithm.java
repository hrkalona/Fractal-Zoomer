package fractalzoomer.core.antialiasing;

import java.util.Random;

public abstract class AntialiasingAlgorithm {
    protected int totalSamples;

    public AntialiasingAlgorithm(int totalSamples) {
        this.totalSamples = totalSamples;
    }

    public abstract void initialize(int color);
    public abstract void addSample(int color);
    public abstract int getColor();

    public static AntialiasingAlgorithm getAntialiasingAlgorithm(int totalSamples, int method, boolean avgWithMean) {

        //int colorspace = 0;
        if(method == 0) {
            //if(colorspace == 0) {
                return new MeanAntialiasingAlgorithmRGB(totalSamples);
//            }
//            else {
//                return new MeanAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 1){
            //if(colorspace == 0) {
                return new MedianAntialiasingAlgorithmRGB(totalSamples, avgWithMean);
//            }
//            else {
//                return new MedianAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 2) {
           // if(colorspace == 0) {
                return new MidPointAntialiasingAlgorithmRGB(totalSamples, avgWithMean);
//            }
//            else {
//                return new MidPointAntialiasingAlgorithmLAB(totalSamples);
//            }
        }
        else if (method == 3) {
            return new ClosestToMeanAntialiasingAlgorithmRGB(totalSamples, avgWithMean);
        }
        else {
            return new ClosestToMidPointAntialiasingAlgorithmRGB(totalSamples, avgWithMean);
        }
    }
}
