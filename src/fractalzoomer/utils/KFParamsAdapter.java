package fractalzoomer.utils;

import fractalzoomer.core.TaskRender;
import fractalzoomer.core.interpolation.CosineInterpolation;
import fractalzoomer.core.numerics.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static fractalzoomer.main.Constants.*;
import static fractalzoomer.main.app_settings.GeneratedPaletteSettings.DEFAULT_LARGE_LENGTH;

public class KFParamsAdapter {

    public static boolean parseKFR(String fileName, Settings s, MainWindow ptr) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(fileName));

            String str_line;

            String re = "0";
            String im = "0";
            String magnification = "1";
            String iterations = "200";
            String colors = "";
            String iterDiv = "1";
            String colorOffset = "0";
            String flat = "0";
            String rotateAngle = "0";
            String colorMethod = "0";
            String BailoutRadiusPreset = "1";
            String Differences = "0";
            String Slopes = "0";
            String SlopeAngle = "45";
            String SlopeRatio = "50";
            String SlopePower = "20";
            String InteriorColor = "";
            String Smooth = "0";
            String BailoutRadiusCustom = "2";
            String StretchAngle = "0";
            String StretchAmount = "0";
            String ImagPointsUp = "0";
            String Power = "2";
            String FractalType = "0";
            String ImageWidth = "";
            String ImageHeight = "";
            String SmoothMethod = "0";
            String BailoutNormPreset = "1";
            String BailoutNormCustom = "2";
            String real = "1";
            String imag = "1";
            String JitterSeed = "0";
            String JitterShape = "0";
            String JitterScale = "1";
            String MultiColor = "0";
            String BlendMC = "0";
            String MultiColors = "";

            boolean matchedAny = false;
            while ((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(str_line, " ");

                if (tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();
                    if(token.equalsIgnoreCase("Re:") && tokenizer.countTokens() == 1) {
                        re = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Im:") && tokenizer.countTokens() == 1) {
                        im = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Zoom:") && tokenizer.countTokens() == 1) {
                        magnification = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Iterations:") && tokenizer.countTokens() == 1) {
                        iterations = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("MultiColor:") && tokenizer.countTokens() == 1) {
                        MultiColor = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("BlendMC:") && tokenizer.countTokens() == 1) {
                        BlendMC = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("MultiColors:") && tokenizer.countTokens() >= 1) {
                        int tokens_size = tokenizer.countTokens();
                        for(int i = 0; i < tokens_size; i++) {
                            MultiColors += tokenizer.nextToken();
                        }
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Colors:") && tokenizer.countTokens() >= 1) {

                        if(tokenizer.countTokens() == 1) {
                            colors = tokenizer.nextToken();
                            matchedAny = true;
                        }
                        else {
                            int tokens_size = tokenizer.countTokens();
                            for(int i = 0; i < tokens_size; i++) {
                                colors += tokenizer.nextToken();
                            }
                            matchedAny = true;
                        }
                    }
                    else if(token.equalsIgnoreCase("IterDiv:") && tokenizer.countTokens() == 1) {
                        iterDiv = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("ColorOffset:") && tokenizer.countTokens() == 1) {
                        colorOffset = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Flat:") && tokenizer.countTokens() == 1) {
                        flat = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("RotateAngle:") && tokenizer.countTokens() == 1) {
                        rotateAngle = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("ColorMethod:") && tokenizer.countTokens() == 1) {
                        colorMethod = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("BailoutRadiusPreset:") && tokenizer.countTokens() == 1) {
                        BailoutRadiusPreset = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Differences:") && tokenizer.countTokens() == 1) {
                        Differences = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Slopes:") && tokenizer.countTokens() == 1) {
                        Slopes = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("SlopeAngle:") && tokenizer.countTokens() == 1) {
                        SlopeAngle = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("SlopeRatio:") && tokenizer.countTokens() == 1) {
                        SlopeRatio = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("SlopePower:") && tokenizer.countTokens() == 1) {
                        SlopePower = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("InteriorColor:") && tokenizer.countTokens() == 1) {
                        InteriorColor = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Smooth:") && tokenizer.countTokens() == 1) {
                        Smooth = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("BailoutRadiusCustom:") && tokenizer.countTokens() == 1) {
                        BailoutRadiusCustom = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("StretchAngle:") && tokenizer.countTokens() == 1) {
                        StretchAngle = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("StretchAmount:") && tokenizer.countTokens() == 1) {
                        StretchAmount = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("ImagPointsUp:") && tokenizer.countTokens() == 1) {
                        ImagPointsUp = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Power:") && tokenizer.countTokens() == 1) {
                        Power = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("FractalType:") && tokenizer.countTokens() == 1) {
                        FractalType = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("ImageWidth:") && tokenizer.countTokens() == 1) {
                        ImageWidth = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("ImageHeight:") && tokenizer.countTokens() == 1) {
                        ImageHeight = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("SmoothMethod:") && tokenizer.countTokens() == 1) {
                        SmoothMethod = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("BailoutNormPreset:") && tokenizer.countTokens() == 1) {
                        BailoutNormPreset = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("BailoutNormCustom:") && tokenizer.countTokens() == 1) {
                        BailoutNormCustom = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("real:") && tokenizer.countTokens() == 1) {
                        real = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("imag:") && tokenizer.countTokens() == 1) {
                        imag = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("JitterSeed:") && tokenizer.countTokens() == 1) {
                        JitterSeed = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("JitterScale:") && tokenizer.countTokens() == 1) {
                        JitterScale = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("JitterShape:") && tokenizer.countTokens() == 1) {
                        JitterShape = tokenizer.nextToken();
                        matchedAny = true;
                    }
                }

            }

            br.close();

            if(!matchedAny) {
                JOptionPane.showMessageDialog(ptr, "Unsupported file format.", "Error!", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            s.defaultValues();
            s.applyStaticSettings();
            Fractal.clearReferences(true, true);

            ptr.getSelectionRectangle().clear();
            ptr.getMainPanel().repaint();

            try {
                int power = Integer.parseInt(Power);
                int ftype = Integer.parseInt(FractalType);
                int function = convertToFunction(power, ftype, s);
                if(function == -1) {
                    throw new Exception("");
                }
                s.fns.function = function;
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(ptr, "Unsupported Fractal Function found.", "Error!", JOptionPane.ERROR_MESSAGE);
                return true;
            }

            int flipImaginary = 0;
            try {
                flipImaginary = Integer.parseInt(ImagPointsUp);
            }
            catch (Exception ex) {

            }

            try {

                if(MyApfloat.setAutomaticPrecision) {
                    long precision = MyApfloat.getAutomaticPrecision(new String[]{magnification, re, im}, new boolean[] {true, false, false}, s.fns.function);

                    if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                        Fractal.clearReferences(true, true);
                        MyApfloat.setPrecision(precision, s);
                    }
                }

                s.xCenter = new MyApfloat(re);
                if(flipImaginary == 1) {
                    s.yCenter = new MyApfloat(im);
                    s.flip_imaginary = false;
                }
                else {
                    s.yCenter = new MyApfloat(im).negate(); //Inverted in KF
                    s.flip_imaginary = true;
                }

                s.size = MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, new MyApfloat(magnification));
            } catch (Exception ex) {

            }

            TaskRender.PERTURBATION_THEORY = true;

            try {
                long miter = Long.parseLong(iterations);
                s.max_iterations = miter > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)miter;
            } catch (Exception ex) {

            }

            if(!ImageWidth.isEmpty() && !ImageHeight.isEmpty()) {
                try {
                    int width = Integer.parseInt(ImageWidth);
                    int height = Integer.parseInt(ImageHeight);

                    if (width > 0 && width <= 46500 && height > 0 && height <= 46500) {
                        ptr.updateImageSize(width, height);
                    }
                }
                catch (Exception ex) {

                }
            }

            int maxColors = 1024;

            double iterDivD = 1;
            try {
                iterDivD = Double.parseDouble(iterDiv);
                iterDivD = iterDivD < 0 ? 1 : iterDivD;
            }
            catch (Exception ex) {}

            s.color_space = COLOR_SPACE_RGB;
            s.gamma = 1;
            s.intesity_exponent = 1;
            s.interpolation_exponent = 1;
            s.color_blending.color_blending = Constants.NORMAL_BLENDING;

            if(MultiColor.equals("1")) {
                try {
                    String[] tokens = MultiColors.split(",");

                    ArrayList<InfiniteWave.InfiniteColorWaveParams> params = new ArrayList<>();
                    for (int i = 0; i < tokens.length; i ++) {
                        String[] tokens2 = tokens[i].split("\\s+");

                        if(tokens2.length != 3) {
                            throw new Exception();
                        }

                        InfiniteWave.WaveType type;
                        if(tokens2[2].equals("2")) {
                            type = InfiniteWave.WaveType.BRIGHTNESS;
                        }
                        else if(tokens2[2].equals("1")) {
                            type = InfiniteWave.WaveType.SATURATION;
                        }
                        else {
                            type = InfiniteWave.WaveType.HUE;
                        }

                        params.add(new InfiniteWave.InfiniteColorWaveParams(type, Double.parseDouble(tokens2[0])));
                    }

                    InfiniteWave.InfiniteColorWaveParams[] p = new InfiniteWave.InfiniteColorWaveParams[params.size()];
                    for(int i = 0; i < p.length; i++) {
                        p[i] = params.get(i);
                    }

                    try {
                        s.gps.outcoloring_infinite_wave_user_palette = InfiniteWave.paramsToJson(p, false);
                    }
                    catch (Exception ex) {
                        throw ex;
                    }

                    if(BlendMC.equals("1")) {
                        s.gps.blendNormalPaletteWithGeneratedPaletteOutColoring = true;
                    }
                    s.gps.blendingOutColoring = 0.5;
                    s.gps.useGeneratedPaletteOutColoring = true;
                    s.gps.restartGeneratedOutColoringPaletteAt = DEFAULT_LARGE_LENGTH;
                    s.gps.generatedPaletteOutColoringId = 5;
                }
                catch (Exception ex) {

                }
            }

            try {
                ArrayList<Color> primaryCols = new ArrayList<>();

                try {
                    if (colors.isEmpty()) {
                        throw new Exception();
                    }

                    String[] tokens = colors.split(",");

                    if (tokens.length % 3 != 0) {
                        throw new Exception();
                    }

                    for (int i = 0; i < tokens.length; i += 3) {
                        int blue = Integer.parseInt(tokens[i]);
                        int green = Integer.parseInt(tokens[i + 1]);
                        int red = Integer.parseInt(tokens[i + 2]);
                        red = ColorSpaceConverter.clamp(red);
                        green = ColorSpaceConverter.clamp(green);
                        blue = ColorSpaceConverter.clamp(blue);
                        primaryCols.add(new Color(red, green, blue));
                    }
                }
                catch (Exception ex) {
                    primaryCols.clear();

                    primaryCols.add(new Color(255, 255, 255));
                    primaryCols.add(new Color(64, 0, 128));
                    primaryCols.add(new Color(0, 0, 160));
                    primaryCols.add(new Color(0, 128, 192));
                    primaryCols.add(new Color(0, 128, 64));
                    primaryCols.add(new Color(255, 255, 0));
                    primaryCols.add(new Color(255, 128, 64));
                    primaryCols.add(new Color(255, 0, 0));
                }

                if(!primaryCols.isEmpty()) {
                    ArrayList<Color> finalCols = new ArrayList<>();
                    CosineInterpolation lerp = new CosineInterpolation();
                    int m_nParts = primaryCols.size();
                    int j, p = 0;
                    for (j = 0; j < maxColors; j++) {
                        double temp = (double) j * (double) m_nParts / (double) maxColors;
                        p = (int) temp;
                        int pn = (p + 1) % m_nParts;
                        temp -= p;
                        finalCols.add(lerp.interpolateColors(primaryCols.get(p), primaryCols.get(pn), temp, false));
                    }

                    s.ps.color_choice = DIRECT_PALETTE_ID;
                    s.ps.direct_palette = finalCols.stream().mapToInt(Color::getRGB).toArray();
                }
            }
            catch (Exception ex) {

            }

            int coffset = 0;
            try {
                long c = Long.parseLong(colorOffset);
                coffset = c > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)c;
            }
            catch (Exception ex) {

            }

            if(Smooth.equals("1")) {
                s.fns.smoothing = true;
            }
            else if(Smooth.equals("0")) {
                s.fns.smoothing = false;
            }

            if(flat.equals("1")) {
                s.fns.banded = true;
                s.fns.smoothing = true;
            }
            else if(Smooth.equals("0")) {
                s.fns.banded = false;
            }

            if(Slopes.equals("1")) {
                s.pps.ss.slopes = true;
                s.pps.ss.heightTransferFactor = 100;
                s.pps.ss.colorMode = 4;
                s.pps.ss.applyWidthScaling = true;
            }
            else if(Slopes.equals("0")) {
                s.pps.ss.slopes = false;
            }

            try {
                double sratio = Double.parseDouble(SlopeRatio);
                sratio = sratio < 0 ? 0 : sratio;
                sratio = sratio > 100 ? 100 : sratio;

                s.pps.ss.SlopeRatio = (sratio / 100) / 2;
            }
            catch (Exception ex) {

            }

            try {
                if(!InteriorColor.isEmpty()) {
                    String[] tokens = InteriorColor.split(",");

                    if (tokens.length % 3 == 0) {
                        int blue = Integer.parseInt(tokens[0]);
                        int green = Integer.parseInt(tokens[1]);
                        int red = Integer.parseInt(tokens[2]);
                        red = ColorSpaceConverter.clamp(red);
                        green = ColorSpaceConverter.clamp(green);
                        blue = ColorSpaceConverter.clamp(blue);
                        s.fractal_color = new Color(red, green, blue);
                    }
                }
            }
            catch (Exception ex) {

            }

            try {
                double spower = Double.parseDouble(SlopePower);
                spower = spower < 0 ? 0 : spower;
                spower = spower > 100 ? 100 : spower;

                s.pps.ss.SlopePower = spower / 100;
            }
            catch (Exception ex) {

            }

            try {
                double slopeAngle = Double.parseDouble(SlopeAngle);

                slopeAngle = slopeAngle > 360 ? 360 : slopeAngle;
                slopeAngle = slopeAngle < -360 ? -360 : slopeAngle;

                slopeAngle = -slopeAngle;

                if(slopeAngle < 0) {
                    slopeAngle = 360 + slopeAngle;
                }

                slopeAngle += 180;

                slopeAngle = slopeAngle % 360.0;

                s.pps.ss.SlopeAngle = slopeAngle;
            }
            catch (Exception ex) {

            }

            try {
                double rangle = -Double.parseDouble(rotateAngle); //Inverted in KF

                if(rangle != 0) {
                    s.fns.rotation = rangle;

                    Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
                    s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
                    s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

                    s.fns.rotation_center[0] = s.xCenter;
                    s.fns.rotation_center[1] = s.yCenter;
                }
            }
            catch (Exception ex) {

            }

            int color_method = 0;
            try {
                color_method = Integer.parseInt(colorMethod);

                switch (color_method) {
                    case 0:
                        s.ps.transfer_function = DEFAULT;
                        break;
                    case 1:
                        s.ps.transfer_function = KF_SQUARE_ROOT;
                        break;
                    case 2:
                        s.ps.transfer_function = KF_CUBE_ROOT;
                        break;
                    case 3:
                        s.ps.transfer_function = KF_LOGARITHM;
                        break;
                    case 4:
                        s.pps.hss.histogramColoring = true;
                        s.pps.hss.hmapping = 1;
                        s.pps.hss.use_integer_iterations = true;
                        break;
                    case 5:
                        s.ps.transfer_function = DEFAULT;
                        s.pps.ndes.useNumericalDem = true;
                        s.pps.ndes.distanceFactor = 1;
                        s.fns.banded = false;
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(ptr, "DE+Standard is not supported.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        s.ps.transfer_function = DEFAULT;
                        break;
                    case 7:
                        s.ps.transfer_function = KF_LOGARITHM;
                        s.pps.ndes.useNumericalDem = true;
                        s.pps.ndes.distanceFactor = 1;
                        s.fns.banded = false;
                        break;
                    case 8:
                        s.ps.transfer_function = KF_SQUARE_ROOT;
                        s.pps.ndes.useNumericalDem = true;
                        s.pps.ndes.distanceFactor = 1;
                        s.fns.banded = false;
                        break;
                    case 9:
                        s.ps.transfer_function = KF_LOG_LOG;
                        break;
                    case 10:
                        s.ps.transfer_function = KF_ATAN;
                        break;
                    case 11:
                        s.ps.transfer_function = KF_FOURTH_ROOT;
                        break;
                }
            }
            catch (Exception ex) {

            }

            try {
                int diff = Integer.parseInt(Differences);

                if(diff < 7) {
                    s.pps.ndes.differencesMethod = diff;
                }
                else {
                    JOptionPane.showMessageDialog(ptr, "Analytic Differencing is not supported.", "Warning!", JOptionPane.WARNING_MESSAGE);
                    s.pps.ndes.differencesMethod = 0;
                }
            }
            catch (Exception ex) {

            }

            if (s.pps.ndes.useNumericalDem) {
                s.pps.ndes.applyWidthScaling = true;
            }

            try {
                int smooth = Integer.parseInt(SmoothMethod);

                if(smooth == 1) {
                    s.fns.escaping_smooth_algorithm = 2;
                }
            }
            catch (Exception ex) {

            }

            try {

                int bail_preset = Integer.parseInt(BailoutRadiusPreset);

                switch (bail_preset) {
                    case 0: //High
                        s.fns.bailout = 100; //10000
                        break;
                    case 1: //2
                        s.fns.bailout = 2;
                        break;
                    //2 is not supported
                    case 3:
                        try {
                            double bail = Double.parseDouble(BailoutRadiusCustom);
                            if(bail > 0) {
                                s.fns.bailout = Math.sqrt(bail);
                            }
                        }
                        catch (Exception ex) {

                        }
                        break;
                }
            }
            catch (Exception ex) {

            }

            try {
                double sangle = Double.parseDouble(StretchAngle);
                double samount = Double.parseDouble(StretchAmount);

                if(sangle != 0 || samount != 0) {
                    s.fns.plane_type = STRETCH_PLANE;
                    s.fns.plane_transform_angle = sangle;
                    s.fns.plane_transform_amount = samount;
                    s.fns.plane_transform_center_hp[0] = s.xCenter;
                    s.fns.plane_transform_center_hp[1] = s.yCenter;
                    s.fns.plane_transform_center[0] = s.fns.plane_transform_center_hp[0].doubleValue();
                    s.fns.plane_transform_center[1] = s.fns.plane_transform_center_hp[1].doubleValue();
                }
            }
            catch (Exception ex) {

            }

            double norm_a = 1;
            try {
                norm_a = Double.parseDouble(real);
                s.fns.norm_a = norm_a;
                s.fns.cbs.norm_a = norm_a;
            }
            catch (Exception ex) {

            }

            double norm_b = 1;
            try {
                norm_b = Double.parseDouble(imag);
                s.fns.norm_b = norm_b;
                s.fns.cbs.norm_b = norm_b;
            }
            catch (Exception ex) {

            }

            if(norm_a != 1 || norm_b != 1) {
                BailoutNormPreset = "3";
            }

            if(s.isConvergingType()) {
                try {
                    int normPreset = Integer.parseInt(BailoutNormPreset);
                    switch (normPreset) {
                        case 0:
                            s.fns.cbs.convergent_bailout_test_algorithm = CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF;
                            break;
                        case 2:
                            s.fns.cbs.convergent_bailout_test_algorithm = CONVERGENT_BAILOUT_CONDITION_SQUARE_KF;
                            break;
                        case 3:
                            s.fns.cbs.convergent_bailout_test_algorithm = CONVERGENT_BAILOUT_CONDITION_NNORM_KF;
                            try {
                                s.fns.cbs.convergent_n_norm = Double.parseDouble(BailoutNormCustom);
                            }
                            catch (Exception ex) {
                                s.fns.cbs.convergent_n_norm = 2;
                            }
                            break;
                        case 1:
                        default:
                            s.fns.cbs.convergent_bailout_test_algorithm = CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF;
                            break;
                    }
                }
                catch (Exception ex) {

                }
            }
            else {
                try {
                    int normPreset = Integer.parseInt(BailoutNormPreset);
                    switch (normPreset) {
                        case 0:
                            s.fns.bailout_test_algorithm = BAILOUT_CONDITION_RHOMBUS;
                            s.fns.bailout *= s.fns.bailout;
                            break;
                        case 2:
                            s.fns.bailout_test_algorithm = BAILOUT_CONDITION_SQUARE;
                            s.fns.bailout *= s.fns.bailout;
                            break;
                        case 3:
                            s.fns.bailout_test_algorithm = BAILOUT_CONDITION_NNORM;
                            try {
                                s.fns.n_norm = Double.parseDouble(BailoutNormCustom);
                            }
                            catch (Exception ex) {
                                s.fns.n_norm = 2;
                            }
                            break;
                        case 1:
                        default:
                            s.fns.bailout_test_algorithm = BAILOUT_CONDITION_CIRCLE;
                            break;
                    }
                }
                catch (Exception ex) {

                }
            }

            if(iterDivD < 1) {
                s.fns.smoothing = true;
            }

            s.ps.color_intensity = 1 / iterDivD;
            s.ps.color_cycling_location = 0;

            if(s.isConvergingType()) {
                if(color_method == 0) {
                    int val = (int) (maxColors - 1 / iterDivD);
                    while (val < 0) {
                        val += maxColors;
                    }
                    s.ps.color_cycling_location = val;
                }
            }
            else if(s.fns.smoothing) {
                s.fns.smoothing_color_selection = 1;
            }

            s.ps.color_cycling_location += coffset;

            s.fns.convergent_bailout = 1E-12;

            try {
                int jseed = Integer.parseInt(JitterSeed);

                if(jseed != 0) {
                    s.js.enableJitter = true;
                    s.js.jitterSeed = jseed;
                }

                double jscale = Double.parseDouble(JitterScale);

                if(jscale > 0) {
                    s.js.jitterScale = jscale;
                }

                int jshape = Integer.parseInt(JitterShape);

                if(jshape == 0 || jshape == 1) {
                    s.js.jitterShape = jshape;
                }
            }
            catch (Exception ex) {

            }

            s.applyStaticSettings();

        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static boolean parseKFRLocation(String fileName, Settings s, MainWindow ptr) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(fileName));

            String str_line;

            String re = "0";
            String im = "0";
            String magnification = "1";
            String iterations = "200";
            String rotateAngle = "0";
            String StretchAngle = "0";
            String StretchAmount = "0";
            String ImagPointsUp = "0";

            boolean matchedAny = false;
            while ((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(str_line, " ");

                if (tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();
                    if(token.equalsIgnoreCase("Re:") && tokenizer.countTokens() == 1) {
                        re = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Im:") && tokenizer.countTokens() == 1) {
                        im = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Zoom:") && tokenizer.countTokens() == 1) {
                        magnification = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("Iterations:") && tokenizer.countTokens() == 1) {
                        iterations = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("RotateAngle:") && tokenizer.countTokens() == 1) {
                        rotateAngle = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("StretchAngle:") && tokenizer.countTokens() == 1) {
                        StretchAngle = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("StretchAmount:") && tokenizer.countTokens() == 1) {
                        StretchAmount = tokenizer.nextToken();
                        matchedAny = true;
                    }
                    else if(token.equalsIgnoreCase("ImagPointsUp:") && tokenizer.countTokens() == 1) {
                        ImagPointsUp = tokenizer.nextToken();
                        matchedAny = true;
                    }
                }

            }

            br.close();

            if(!matchedAny) {
                JOptionPane.showMessageDialog(ptr, "Unsupported file format.", "Error!", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            Fractal.clearReferences(true, true);

            ptr.getSelectionRectangle().clear();
            ptr.getMainPanel().repaint();

            int flipImaginary = 0;
            try {
                flipImaginary = Integer.parseInt(ImagPointsUp);
            }
            catch (Exception ex) {

            }

            try {

                if(MyApfloat.setAutomaticPrecision) {
                    long precision = MyApfloat.getAutomaticPrecision(new String[]{magnification, re, im}, new boolean[] {true, false, false}, s.fns.function);

                    if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                        Fractal.clearReferences(true, true);
                        MyApfloat.setPrecision(precision, s);
                    }
                }

                s.xCenter = new MyApfloat(re);
                if(flipImaginary == 1) {
                    s.yCenter = new MyApfloat(im);
                    s.flip_imaginary = false;
                }
                else {
                    s.yCenter = new MyApfloat(im).negate(); //Inverted in KF
                    s.flip_imaginary = true;
                }

                s.size = MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, new MyApfloat(magnification));
            } catch (Exception ex) {

            }

            try {
                long miter = Long.parseLong(iterations);
                s.max_iterations = miter > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)miter;
            } catch (Exception ex) {

            }

            try {
                double rangle = -Double.parseDouble(rotateAngle); //Inverted in KF

                if(rangle != 0) {
                    s.fns.rotation = rangle;

                    Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
                    s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
                    s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

                    s.fns.rotation_center[0] = s.xCenter;
                    s.fns.rotation_center[1] = s.yCenter;
                }
            }
            catch (Exception ex) {

            }

            try {
                double sangle = Double.parseDouble(StretchAngle);
                double samount = Double.parseDouble(StretchAmount);

                if(sangle != 0 || samount != 0) {
                    s.fns.plane_type = STRETCH_PLANE;
                    s.fns.plane_transform_angle = sangle;
                    s.fns.plane_transform_amount = samount;
                    s.fns.plane_transform_center_hp[0] = s.xCenter;
                    s.fns.plane_transform_center_hp[1] = s.yCenter;
                    s.fns.plane_transform_center[0] = s.fns.plane_transform_center_hp[0].doubleValue();
                    s.fns.plane_transform_center[1] = s.fns.plane_transform_center_hp[1].doubleValue();
                }
            }
            catch (Exception ex) {

            }


        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public static void writeBasicKFR(String fileName, Settings s) {
        String im;
        int ImagPointsUp;
        if(s.flip_imaginary) {
            im = s.yCenter.negate().toString(true);
            ImagPointsUp = 0;
        } else {
            im = s.yCenter.toString(true);
            ImagPointsUp = 1;
        }

        String kfr =
                "Re: " + s.xCenter.toString(true) + "\n" +
                        "Im: " + im + "\n" +
                        "Zoom: " + MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, s.size).toString() + "\n" +
                        "Iterations: " + s.max_iterations + "\n" +
                        "ImagPointsUp: " + ImagPointsUp + "\n";

        if(s.fns.plane_type == STRETCH_PLANE) {
            kfr += "StretchAngle: " + s.fns.plane_transform_angle + "\n";
            kfr += "StretchAmount: " + s.fns.plane_transform_amount + "\n";
        }

        if(s.fns.rotation != 0) {
            kfr += "RotateAngle: " + (-s.fns.rotation) + "\n";
        }

        String FractalTypePower = convertToPowerFractalType(s);

        if(!FractalTypePower.isEmpty()) {
            kfr += FractalTypePower;
        }

        kfr = kfr.replace("\n", "\r\n");

        try {
            Files.write(Paths.get(fileName), kfr.getBytes());
        }
        catch (Exception ex) {}
    }

    private static String convertToPowerFractalType(Settings s) {
        switch (s.fns.function) {
            case MANDELBROT:
                return "FractalType: " + (s.fns.burning_ship ? 1 : 0) + "\nPower: 2\n";
            case MANDELBROTCUBED:
                return "FractalType: " + (s.fns.burning_ship ? 1 : 0) + "\nPower: 3\n";
            case MANDELBROTFOURTH:
                return "FractalType: " + (s.fns.burning_ship ? 1 : 0) + "\nPower: 4\n";
            case MANDELBROTFIFTH:
                return "FractalType: " + (s.fns.burning_ship ? 1 : 0) + "\nPower: 5\n";
            case MANDELBROTSIXTH:
                return "FractalType: 0\nPower: 6\n";
            case MANDELBROTSEVENTH:
                return "FractalType: 0\nPower: 7\n";
            case MANDELBROTEIGHTH:
                return "FractalType: 0\nPower: 8\n";
            case MANDELBROTNINTH:
                return "FractalType: 0\nPower: 9\n";
            case MANDELBROTTENTH:
                return "FractalType: 0\nPower: 10\n";
            case BUFFALO_MANDELBROT:
                return "FractalType: 2\nPower: 2\n";
            case CELTIC_MANDELBROT:
                return "FractalType: 3\nPower: 2\n";
            case MANDELBAR:
                return "FractalType: 4\nPower: 2\n";
            case MANDELBARCUBED:
                return "FractalType: 4\nPower: 3\n";
            case PERPENDICULAR_MANDELBROT:
                return "FractalType: 6\nPower: 2\n";
            case PERPENDICULAR_BURNING_SHIP:
                return "FractalType: 7\nPower: 2\n";
            case PERPENDICULAR_CELTIC_MANDELBROT:
                return "FractalType: 8\nPower: 2\n";
            case PERPENDICULAR_BUFFALO_MANDELBROT:
                return "FractalType: 9\nPower: 2\n";
            case FORMULA50:
                return "FractalType: 44\nPower: 3\n";
            case NOVA:
                if(s.fns.relaxation[0] == 1 && s.fns.relaxation[1] == 0 && s.fns.defaultNovaInitialValue) {
                    if (s.fns.nova_method == NOVA_NEWTON && (s.fns.z_exponent_nova[0] == 2 || s.fns.z_exponent_nova[0] == 3 || s.fns.z_exponent_nova[0] == 4 || s.fns.z_exponent_nova[0] == 5) && s.fns.z_exponent_nova[1] == 0) {
                        if (s.fns.z_exponent_nova[0] == 3) {
                            return "FractalType: 97\nPower: " + (int) s.fns.z_exponent_nova[0] + "\n";
                        } else {
                            return "FractalType: 98\nPower: " + (int) s.fns.z_exponent_nova[0] + "\n";
                        }
                    } else if (s.fns.nova_method == NOVA_HALLEY && (s.fns.z_exponent_nova[0] == 2 || s.fns.z_exponent_nova[0] == 3 || s.fns.z_exponent_nova[0] == 4 || s.fns.z_exponent_nova[0] == 5) && s.fns.z_exponent_nova[1] == 0) {
                        return "FractalType: 99\nPower: " + (int) s.fns.z_exponent_nova[0] + "\n";
                    } else if (s.fns.nova_method == NOVA_SCHRODER && (s.fns.z_exponent_nova[0] == 2 || s.fns.z_exponent_nova[0] == 3) && s.fns.z_exponent_nova[1] == 0) {
                        return "FractalType: 100\nPower: " + (int) s.fns.z_exponent_nova[0] + "\n";
                    } else if (s.fns.nova_method == NOVA_HOUSEHOLDER3 && (s.fns.z_exponent_nova[0] == 2 || s.fns.z_exponent_nova[0] == 3) && s.fns.z_exponent_nova[1] == 0) {
                        return "FractalType: 101\nPower: " + (int) s.fns.z_exponent_nova[0] + "\n";
                    } else if (s.fns.nova_method == NOVA_HOUSEHOLDER && s.fns.z_exponent_nova[0] == 3 && s.fns.z_exponent_nova[1] == 0) {
                        return "FractalType: 102\nPower: 3\n";
                    }
                }
                break;
        }
        return "";
    }

    private static int convertToFunction(int Power, int FractalType, Settings s) {

        if (FractalType == 0) { //Multibrot
            switch (Power) {
                case 3:
                    return MANDELBROTCUBED;
                case 4:
                    return MANDELBROTFOURTH;
                case 5:
                    return MANDELBROTFIFTH;
                case 6:
                    return MANDELBROTSIXTH;
                case 7:
                    return MANDELBROTSEVENTH;
                case 8:
                    return MANDELBROTEIGHTH;
                case 9:
                    return MANDELBROTNINTH;
                case 10:
                    return MANDELBROTTENTH;
                case 2:
                default:
                    return MANDELBROT;
            }
        } else if (FractalType == 1) { //Multibrot Burning-Ship
            switch (Power) {
                case 2:
                    s.fns.burning_ship = true;
                    return MANDELBROT;
                case 3:
                    s.fns.burning_ship = true;
                    return MANDELBROTCUBED;
                case 4:
                    s.fns.burning_ship = true;
                    return MANDELBROTFOURTH;
                case 5:
                    s.fns.burning_ship = true;
                    return MANDELBROTFIFTH;
            }
        } else if (FractalType == 2) { //Buffalo
            switch (Power) {
                case 2:
                    return BUFFALO_MANDELBROT;
            }
        } else if (FractalType == 3) { //Celtic
            switch (Power) {
                case 2:
                    return CELTIC_MANDELBROT;
            }
        } else if (FractalType == 4) { //Mandelbar
            switch (Power) {
                case 2:
                    return MANDELBAR;
                case 3:
                    return MANDELBARCUBED;
            }
        } else if (FractalType == 6) { //Perpendicular Mandelbrot
            switch (Power) {
                case 2:
                    return PERPENDICULAR_MANDELBROT;
            }
        } else if (FractalType == 7) { //Perpendicular Burning Ship
            switch (Power) {
                case 2:
                    return PERPENDICULAR_BURNING_SHIP;
            }
        } else if (FractalType == 8) { //Perpendicular Celtic
            switch (Power) {
                case 2:
                    return PERPENDICULAR_CELTIC_MANDELBROT;
            }
        } else if (FractalType == 9) { //Perpendicular Buffalo
            switch (Power) {
                case 2:
                    return PERPENDICULAR_BUFFALO_MANDELBROT;
            }
        } else if (FractalType == 97) { //Nova
            switch (Power) {
                case 3:
                    s.fns.z_exponent_nova[0] = 3;
                    s.fns.z_exponent_nova[1] = 0;
                    s.fns.nova_method = NOVA_NEWTON;
                    return NOVA;
            }
        } else if (FractalType == 44) { //TheRedshiftRider 3
            switch (Power) {
                case 3:
                    return FORMULA50;
            }
        } else if (FractalType == 98) { //Newton Nova Mandelbrot
            s.fns.z_exponent_nova[0] = Power;
            s.fns.z_exponent_nova[1] = 0;
            s.fns.nova_method = NOVA_NEWTON;
            return NOVA;
        } else if (FractalType == 99) { //Halley Nova Mandelbrot
            s.fns.z_exponent_nova[0] = Power;
            s.fns.z_exponent_nova[1] = 0;
            s.fns.nova_method = NOVA_HALLEY;
            return NOVA;
        } else if (FractalType == 100) { //Schroder Nova Mandelbrot
            s.fns.z_exponent_nova[0] = Power;
            s.fns.z_exponent_nova[1] = 0;
            s.fns.nova_method = NOVA_SCHRODER;
            return NOVA;
        } else if (FractalType == 101) { //Householder 3 Nova Mandelbrot
            s.fns.z_exponent_nova[0] = Power;
            s.fns.z_exponent_nova[1] = 0;
            s.fns.nova_method = NOVA_HOUSEHOLDER3;
            return NOVA;
        } else if (FractalType == 102) { //Householder Nova Mandelbrot
            s.fns.z_exponent_nova[0] = Power;
            s.fns.z_exponent_nova[1] = 0;
            s.fns.nova_method = NOVA_HOUSEHOLDER;
            return NOVA;
        }

        return -1;
    }

    public static void saveKFB(String fileName, int image_width, int image_height, Settings s) {
        if (TaskRender.image_iterations == null || TaskRender.image_iterations.length != image_width * image_height) {
            return;
        }

        int[] palette = TaskRender.palette_outcoloring.getPalette(); //Writing only the out palette

        int totalBytes = 3 * Byte.BYTES
                + 2 * Integer.BYTES
                + image_width * image_height * Integer.BYTES
                + 2 * Integer.BYTES
                + palette.length * Byte.BYTES * 3
                + Integer.BYTES
                + image_width * image_height * Float.BYTES;

        ByteBuffer buffer = ByteBuffer.allocate(totalBytes); // Allocate enough space for data
        buffer.order(ByteOrder.LITTLE_ENDIAN); // Set to little-endian

        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            buffer.put((byte)'K');
            buffer.put((byte)'F');
            buffer.put((byte)'B');

            buffer.putInt(image_width);
            buffer.putInt(image_height);

            for(int j = 0; j < image_width; j++) {
                for(int i = 0; i < image_height; i++) {
                    double value = TaskRender.image_iterations[i * image_width + j];
                    if (TaskRender.isMaximumIterations(value)) {
                        buffer.putInt(s.max_iterations);
                    } else {
                        int intValue = (int) Math.abs(value);
                        buffer.putInt(intValue);
                    }
                }
            }

            int iterDiv =  (int)(1 / s.ps.color_intensity); //is probably not used...
            buffer.putInt(iterDiv);
            buffer.putInt(palette.length);

            for(int i = 0; i < palette.length; i++) {
                int color = palette[i];
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;
                buffer.put((byte) red);
                buffer.put((byte) green);
                buffer.put((byte) blue);
            }

            buffer.putInt(s.max_iterations);

            for (int j = 0; j < image_width; j++) {
                for(int i = 0; i < image_height; i++) {
                    double value = TaskRender.image_iterations[i * image_width + j];
                    if (TaskRender.isMaximumIterations(value)) {
                        buffer.putFloat(0.0f);
                    } else {
                        float fract = 1 - (float) MathUtils.fract(Math.abs(value));
                        buffer.putFloat(fract);
                    }
                }
            }
            fos.write(buffer.array(), 0, buffer.array().length);
        } catch (Exception e) {
        }
    }
}
