package fractalzoomer.main.app_settings;

import java.awt.image.BufferedImage;

public class TextureSettings {
    public boolean applyTexture;
    public BufferedImage textureImg;
    public double texture_noise_reducing_factor;
    public double texture_blending;
    public int texture_color_blending;
    public boolean texture_reverse_color_blending;
    public double textureScaleX;
    public double textureScaleY;
    public int textureOffset;

    public TextureSettings() {
        applyTexture = false;
        texture_noise_reducing_factor = 1e-10;
        texture_blending = 0.7;
        texture_color_blending = 0;
        texture_reverse_color_blending = false;
        textureScaleX = 5.0;
        textureScaleY = 0.5;
        textureOffset = 0;
    }
}
