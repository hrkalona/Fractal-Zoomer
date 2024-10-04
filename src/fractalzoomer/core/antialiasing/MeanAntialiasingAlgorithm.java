package fractalzoomer.core.antialiasing;

import fractalzoomer.utils.ColorCorrection;

public class MeanAntialiasingAlgorithm extends AntialiasingAlgorithm {
    private double sumA;
    private double sumB;
    private double sumC;

    private double sinHue;
    private double cosHue;

    //private boolean hasHue;

    public MeanAntialiasingAlgorithm(int totalSamples,  int colorSpace) {
        super(totalSamples, colorSpace);
        sumA = 0;
        sumB = 0;
        sumC = 0;
        //sinHue = 0;
        //cosHue = 0;

        //hasHue = colorSpace == 5|| colorSpace == 6 || colorSpace == 7 || colorSpace == 8;
    }

    @Override
    public void initialize(int color) {
        double[] result = getColorChannels(color);

//        if(hasHue) {
//            if (colorSpace == 5 || colorSpace == 6) {
//                double angle = Math.toRadians(result[0] * 360);
//                sinHue = Math.sin(angle);
//                cosHue = Math.cos(angle);
//                sumB = result[1];
//                sumC = result[2];
//            } else {
//                double angle = Math.toRadians(result[2]);
//                sinHue = Math.sin(angle);
//                cosHue = Math.cos(angle);
//                sumB = result[0];
//                sumC = result[1];
//            }
//        }
//        else {
            sumA = result[0];
            sumB = result[1];
            sumC = result[2];
        //}
        addedSamples = 1;
    }

    @Override
    public boolean addSample(int color) {
        double[] result = getColorChannels(color);

//        if(hasHue) {
//            if(colorSpace == 5 || colorSpace == 6) {
//                double angle = Math.toRadians(result[0] * 360);
//                sinHue += Math.sin(angle);
//                cosHue += Math.cos(angle);
//                sumB += result[1];
//                sumC += result[2];
//            }
//            else  {
//                double angle = Math.toRadians(result[2]);
//                sinHue += Math.sin(angle);
//                cosHue += Math.cos(angle);
//                sumB += result[0];
//                sumC += result[1];
//            }
//        }
//        else {
            sumA += result[0];
            sumB += result[1];
            sumC += result[2];
            addedSamples++;
        //}
        return true;
    }

    @Override
    public int getColor() {
        if(addedSamples != totalSamples) {
            return 0xff000000;
        }

        int[] result = getColorChannels(sumA * totalSamplesReciprocal, sumB * totalSamplesReciprocal, sumC * totalSamplesReciprocal);
        return ColorCorrection.linearToGamma(result[0], result[1], result[2]);
    }
}
