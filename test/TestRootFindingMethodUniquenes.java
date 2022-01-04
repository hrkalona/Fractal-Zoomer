import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.abbasbandy.AbbasbandyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.halley.HalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder3.Householder3RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.jaratt.JarattRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.jaratt2.Jaratt2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.midpoint.MidpointRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.muller.MullerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton.NewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton_hines.NewtonHinesRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.secant.SecantRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.stirling.StirlingRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.super_halley.SuperHalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.third_order_newton.ThirdOrderNewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.traub_ostrowski.TraubOstrowskiRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.weerakoon_fernando.WeerakoonFernandoRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker.WhittakerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker_double_convex.WhittakerDoubleConvexRootFindingMethod;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class TestRootFindingMethodUniquenes {
    private static final double epsilon = 1e-4;

    public static Complex getNextIterationForMethod(Complex z, int method, Complex zold, Complex zold2)
    {

        Complex fz = z.fourth().sub(1);
        Complex dfz = z.cube().times(4);
        Complex ddfz = z.square().times(12);
        Complex dddfz = z.times(24);

        switch (method) {
            case 0: //Newton
                return NewtonRootFindingMethod.newtonMethod(z, fz, dfz, new Complex(1, 0));
            case 1: //Halley
                return HalleyRootFindingMethod.halleyMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 2: //Householder
                return HouseholderRootFindingMethod.householderMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 3: //Schroder
                return SchroderRootFindingMethod.schroderMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 4: //Parhalley
                return ParhalleyRootFindingMethod.parhalleyMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 5: // Laguerrre
                return LaguerreRootFindingMethod.laguerreMethod(z, fz, dfz, ddfz, new Complex(4, 0), new Complex(1, 0));
            case 6: //Newton-hines
                return NewtonHinesRootFindingMethod.newtonHinesMethod(z, fz, dfz, new Complex(-0.5, 0), new Complex(1, 0));
            case 7: //Secant
                Complex fz1 = zold.fourth().sub(1);
                return SecantRootFindingMethod.secantMethod(z, fz, zold,fz1, new Complex(1, 0));
            case 8: //Whittaker
                return WhittakerRootFindingMethod.whittakerMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 9: //Whittaker-Dbl
                return WhittakerDoubleConvexRootFindingMethod.whittakerDoubleConvexMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 10: //Jaratt
                Complex df_combined = JarattRootFindingMethod.getDerivativeArgument(z, fz, dfz);
                df_combined = df_combined.cube().times(4);
                return JarattRootFindingMethod.jarattMethod(z, fz, dfz, df_combined, new Complex(1, 0));
            case 11: //Jaratt2
                df_combined = Jaratt2RootFindingMethod.getDerivativeArgument(z, fz, dfz);
                df_combined = df_combined.cube().times(4);
                return Jaratt2RootFindingMethod.jaratt2Method(z, fz, dfz, df_combined, new Complex(1, 0));
            case 12: // Super-halley
                return SuperHalleyRootFindingMethod.superHalleyMethod(z, fz, dfz, ddfz, new Complex(1, 0));
            case 13: //Weerakoon-F
                df_combined = WeerakoonFernandoRootFindingMethod.getDerivativeArgument(z, fz, dfz);
                df_combined = df_combined.cube().times(4);
                return WeerakoonFernandoRootFindingMethod.weerakoonFernandoMethod(z, fz, dfz, df_combined, new Complex(1, 0));
            case 14: //Midpoint
                df_combined = MidpointRootFindingMethod.getDerivativeArgument(z, fz, dfz);
                df_combined = df_combined.cube().times(4);
                return MidpointRootFindingMethod.midpointMethod(z, fz, df_combined, new Complex(1, 0));
            case 15: //Stirling
                df_combined = StirlingRootFindingMethod.getDerivativeArgument(z, fz);
                df_combined = df_combined.cube().times(4);
                return StirlingRootFindingMethod.stirlingMethod(z, fz, df_combined, new Complex(1, 0));
            case 16: //ThirdOrderNewton
                Complex ffz = ThirdOrderNewtonRootFindingMethod.getFunctionArgument(z, fz, dfz);
                ffz = ffz.fourth().sub(1);
                return ThirdOrderNewtonRootFindingMethod.thirdOrderNewtonMethod(z, fz, dfz, ffz, new Complex(1, 0));
            case 17: //TraubOstrowski
                ffz = TraubOstrowskiRootFindingMethod.getFunctionArgument(z, fz, dfz);
                ffz = ffz.fourth().sub(1);
                return TraubOstrowskiRootFindingMethod.traubOstrowskiMethod(z, fz, dfz, ffz, new Complex(1, 0));
            case 18: //Steffensen
                ffz = SteffensenRootFindingMethod.getFunctionArgument(z, fz);
                ffz = ffz.fourth().sub(1);
                return SteffensenRootFindingMethod.steffensenMethod(z, fz, ffz, new Complex(1, 0));
            case 19: //Muller
                return MullerRootFindingMethod.mullerMethod(z, zold, zold2, fz, zold.fourth().sub(1), zold2.fourth().sub(1), new Complex(1, 0));
                //return MullerRootFindingMethod.mullerMethod(z, new Complex(), new Complex(1e-10, 0), fz, new Complex().fourth().sub(1), new Complex(1e-10, 0).fourth().sub(1), new Complex(1, 0));
            case 20: //Abbasbandy
                return AbbasbandyRootFindingMethod.abbasbandyMethod(z, fz, dfz, ddfz, dddfz, new Complex(1, 0));
            case 21: //Householder3
                return Householder3RootFindingMethod.householder3Method(z, fz, dfz, ddfz, dddfz, new Complex(1, 0));
        }

        return null;
    }

    public static void main(String[] args) {

        Complex[] vals = new Complex[100000];
        Random rand = new Random(123);

        int[] methods = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};

        for(int i = 0; i < vals.length; i++) {
            double signRe = rand.nextDouble() >= 0.5 ? -1 : 1;
            double signIm = rand.nextDouble() >= 0.5 ? -1 : 1;
            vals[i] = new Complex(rand.nextDouble() * signRe * 3, rand.nextDouble() * signIm * 3);
        }

        Complex[][] resultsPerMethod = new Complex[methods.length][vals.length];
        for(int j = 0; j < methods.length; j++) {
            for (int i = 0; i < vals.length; i++) {
                resultsPerMethod[j][i] = getNextIterationForMethod(new Complex(vals[i]), j, new Complex(1e-10, 0), new Complex());
            }
        }

        int[][] methodSimilarities = new int[methods.length][methods.length];
        for (int i = 0; i < vals.length; i++) {
            for(int j = 0; j < methods.length; j++) {
                for(int k = j + 1; k < methods.length; k++) {

                    if(resultsPerMethod[j][i].isNaN() || resultsPerMethod[j][i].isInfinite())
                    {
                        //System.out.println("Skipping: " + resultsPerMethod[j][i]);
                        continue;
                    }

                    if(resultsPerMethod[k][i].isNaN() || resultsPerMethod[k][i].isInfinite())
                    {
                        //System.out.println("Skipping: " + resultsPerMethod[k][i]);
                        continue;
                    }

                    if(resultsPerMethod[j][i].distance(resultsPerMethod[k][i]) < epsilon) {
                        //System.out.println("Found similarity for method " + j + " and method " + k + " for value " + resultsPerMethod[j][i]);
                        methodSimilarities[j][k] = methodSimilarities[k][j] = methodSimilarities[k][j] + 1;
                    }
                }
            }
        }

        for(int i = 0; i < methodSimilarities.length; i++) {
            for(int j = i + 1; j < methodSimilarities.length; j++) {
                double percentage = (double)methodSimilarities[i][j] / vals.length * 100;
                if(percentage > 2) {
                    System.out.println("Similarity of " + String.format("%3d", i) + " with " + String.format("%3d", j) +": " + String.format("%6.2f", percentage) + "%");
                }
            }
        }

        double size = 6;
        double height_ratio = 1;
        int image_size = 600;

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;
        double xCenter = 0;
        double yCenter = 0;

        double temp_xcenter_size = xCenter - size_2_x;
        double temp_ycenter_size = yCenter + size_2_y;

        int[] default_palette = {-16774636, -16510682, -16246472, -15982517, -15718307, -15454353, -15190398, -14860652, -14596442, -14332487, -14068277, -13804323, -13474576, -13673506, -13806644, -14005318, -14138456, -14337130, -14470267, -14668941, -14802079, -15000753, -15133891, -15332565, -15465702, -14350567, -13169639, -12054248, -10873320, -9758185, -8642793, -7461866, -6280938, -5165803, -3984875, -2869484, -1688556, -2869485, -3984622, -5099759, -6214896, -7330033, -8445170, -9560563, -10675700, -11790837, -12905974, -14021111, -15136247, -14018808, -12901369, -11783674, -10666234, -9548795, -8431356, -7313661, -6196221, -5078526, -3961087, -2843648, -1660416, -2842880, -3959807, -5142270, -6259197, -7441660, -8558332, -9740795, -10857722, -12040185, -13157112, -14339575, -15456246, -15586039, -15715832, -15780089, -15909882, -16039675, -16103931, -16233724, -16363517, -16427774, -16557567, -16687360, -16751616, -16753664, -16755455, -16691966, -16693757, -16630268, -16632060, -16634107, -16570362, -16572409, -16508664, -16510711, -16446966, -15331573, -14216179, -13100785, -11985392, -10869998, -9754604, -8639467, -7523817, -6408423, -5293030, -4177636, -2996706, -3653601, -4310495, -4967389, -5624284, -6281178, -6937816, -7594711, -8251605, -8908499, -9565394, -10287824, -10878926, -10418377, -9892035, -9431485, -8905143, -8444593, -7918507, -7457702, -6931360, -6470810, -5944468, -5483918, -4957576, -5942159, -6926742, -7911324, -8895907, -9880490, -10864816, -11849399, -12833982, -13818564, -14803147, -15787730, -16772056, -16639947, -16442302, -16310192, -16112547, -15980438, -15848073, -15650427, -15452782, -15320672, -15123027, -14990918, -14793016, -14990919, -15123286, -15321189, -15453556, -15651459, -15783826, -15981729, -16114096, -16311999, -16444366, -16642525};

        for(int j = 0; j < methods.length; j++) {

            BufferedImage image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            for(int y = 0; y < image_size; y++) {
                for (int x = 0, loc = y * image_size + x; x < image_size; x++, loc++) {
                    boolean escaped = false;
                    Complex zold = new Complex();
                    Complex c = new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y);
                    Complex z_1 = new Complex(1e-10, 0);
                    Complex z_2 = new Complex();

                    int iteration = 0;
                    for(iteration = 0; iteration < 500; iteration++) {

                        if(c.distance_squared(zold) < 1e-10 && iteration > 0) {
                            escaped = true;
                            break;
                        }
                        zold.assign(c);
                        c = getNextIterationForMethod(c, j, z_1, z_2);

                    }

                    if(escaped) {
                        image.setRGB(x, y, default_palette[iteration % default_palette.length]);
                    }
                    else {
                        image.setRGB(x, y, Color.black.getRGB());
                    }
                }
            }

            File outputfile = new File("rootfinding-images/method" + j + ".png");
            try {
                ImageIO.write(image, "png", outputfile);
            }
            catch (Exception ex) {}
        }
    }
}
