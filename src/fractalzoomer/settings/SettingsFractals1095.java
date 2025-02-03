package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SettingsFractals1095 extends SettingsFractals1094 implements Serializable {
    private static final long serialVersionUID = 92378412313L;
    private double preMobiusA;
    private double preMobiusB;
    private double postMobiusA;
    private double postMobiusB;
    private int aaType;
    private double bls_ambient;
    private double[] bls_colorAmbient;
    private double[] bls_diffuse;
    private double[] bls_specular;
    private double bls_shininess;
    private double bls_polarAngle;
    private double bls_azimuthAngle;
    private double[] bls_color;
    private Double[] bls_materialSpecularColor;
    private double bls_heightTransferFactor;
    private int bls_heightTransfer;
    private int bls_fractionalTransfer;
    private int bls_fractionalSmoothing;
    private int bls_fractionalTransferMode;
    private double bls_fractionalTransferScale;
    private double bls_noise_reducing_factor;
    private boolean bls_lighting;
    private int[] textureImageData;
    private int textureImageWidth;
    private int textureImageHeight;
    private boolean applyTexture;
    private double texture_noise_reducing_factor;
    private double texture_blending;
    private int texture_color_blending;
    private boolean texture_reverse_color_blending;
    private double textureScaleX;
    private double textureScaleY;
    private int textureOffset;


    public SettingsFractals1095(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row, boolean split_into_rectagle_areas, int rectangle_area_split_algorithm, int area_dimension_x, int area_dimension_y) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks, blocks_format, two_step_refinement,  one_chunk_per_row, split_into_rectagle_areas, rectangle_area_split_algorithm, area_dimension_x, area_dimension_y);

        preMobiusA = s.fns.preffs.mobiusA;
        preMobiusB = s.fns.preffs.mobiusB;
        postMobiusA = s.fns.postffs.mobiusA;
        postMobiusB = s.fns.postffs.mobiusB;
        aaType = s.fs.aaType;

        bls_ambient = s.pps.bls.ambient;
        bls_colorAmbient = s.pps.bls.colorAmbient;
        bls_diffuse = s.pps.bls.diffuse;
        bls_specular = s.pps.bls.specular;
        bls_shininess = s.pps.bls.shininess;
        bls_polarAngle = s.pps.bls.polarAngle;
        bls_azimuthAngle = s.pps.bls.azimuthAngle;
        bls_color = s.pps.bls.color;
        bls_materialSpecularColor = s.pps.bls.materialSpecularColor;
        bls_heightTransferFactor = s.pps.bls.heightTransferFactor;
        bls_heightTransfer = s.pps.bls.heightTransfer;
        bls_fractionalTransfer = s.pps.bls.fractionalTransfer;
        bls_fractionalSmoothing = s.pps.bls.fractionalSmoothing;
        bls_fractionalTransferMode = s.pps.bls.fractionalTransferMode;
        bls_fractionalTransferScale = s.pps.bls.fractionalTransferScale;
        bls_noise_reducing_factor = s.pps.bls.bls_noise_reducing_factor;
        bls_lighting = s.pps.bls.lighting;

        applyTexture = s.pps.ts.applyTexture;
        texture_noise_reducing_factor = s.pps.ts.texture_noise_reducing_factor;
        texture_blending = s.pps.ts.texture_blending;
        texture_color_blending = s.pps.ts.texture_color_blending;
        texture_reverse_color_blending = s.pps.ts.texture_reverse_color_blending;
        textureScaleX = s.pps.ts.textureScaleX;
        textureScaleY = s.pps.ts.textureScaleY;
        textureOffset = s.pps.ts.textureOffset;

        if (s.pps.ts.applyTexture && s.pps.ts.textureImg != null) {
            textureImageWidth = s.pps.ts.textureImg.getWidth();
            textureImageHeight = s.pps.ts.textureImg.getHeight();
            textureImageData = s.pps.ts.textureImg.getRGB(0, 0, textureImageWidth, textureImageHeight, null, 0, textureImageWidth);
        }
    }

    @Override
    public int getVersion() {

        return 1095;

    }

    public BufferedImage getTextureImage() {

        if(textureImageWidth == 0 || textureImageHeight == 0 || textureImageData == null) {
            return null;
        }

        BufferedImage img = new BufferedImage(textureImageWidth, textureImageHeight, BufferedImage.TYPE_INT_ARGB);
        img.setRGB(0, 0, textureImageWidth, textureImageHeight, textureImageData, 0, textureImageWidth);
        return img;

    }

    public double getPreMobiusA() {
        return preMobiusA;
    }

    public double getPreMobiusB() {
        return preMobiusB;
    }

    public double getPostMobiusA() {
        return postMobiusA;
    }

    public double getPostMobiusB() {
        return postMobiusB;
    }

    public int getAaType() {
        return aaType;
    }

    public double getBlsAmbient() {
        return bls_ambient;
    }

    public double[] getBlsColorAmbient() {
        return bls_colorAmbient;
    }

    public double[] getBlsDiffuse() {
        return bls_diffuse;
    }

    public double[] getBlsSpecular() {
        return bls_specular;
    }

    public double getBlsShininess() {
        return bls_shininess;
    }

    public double getBlsPolarAngle() {
        return bls_polarAngle;
    }

    public double getBlsAzimuthAngle() {
        return bls_azimuthAngle;
    }

    public double[] getBlsColor() {
        return bls_color;
    }

    public Double[] getBlsMaterialSpecularColor() {
        return bls_materialSpecularColor;
    }

    public double getBlsHeightTransferFactor() {
        return bls_heightTransferFactor;
    }

    public int getBlsHeightTransfer() {
        return bls_heightTransfer;
    }

    public int getBlsFractionalTransfer() {
        return bls_fractionalTransfer;
    }

    public int getBlsFractionalSmoothing() {
        return bls_fractionalSmoothing;
    }

    public int getBlsFractionalTransferMode() {
        return bls_fractionalTransferMode;
    }

    public double getBlsFractionalTransferScale() {
        return bls_fractionalTransferScale;
    }

    public double getBlsNoiseReducingFactor() {
        return bls_noise_reducing_factor;
    }

    public boolean getBlsLighting() {
        return bls_lighting;
    }

    public boolean getApplyTexture() {
        return applyTexture;
    }

    public double getTextureNoiseReducingFactor() {
        return texture_noise_reducing_factor;
    }

    public double getTextureBlending() {
        return texture_blending;
    }

    public int getTextureColorBlending() {
        return texture_color_blending;
    }

    public boolean getTextureReverseColorBlending() {
        return texture_reverse_color_blending;
    }

    public double getTextureScaleX() {
        return textureScaleX;
    }

    public double getTextureScaleY() {
        return textureScaleY;
    }

    public int getTextureOffset() {
        return textureOffset;
    }
}
