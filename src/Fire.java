
import java.awt.Color;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Fire extends ThreadPaint {
  private Palette palette;
  private double color_intensity;

    public Fire(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
        palette = palette.makeDefaultPalette("Fire");
        this.color_intensity = color_intensity;

    }

    public Fire(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
        palette = palette.makeDefaultPalette("Fire");
        this.color_intensity = color_intensity;

    }

    public Fire(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, boolean inverse_plane, boolean anti_aliasing, boolean edges, boolean emboss, int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, periodicity_checking, inverse_plane, out_coloring_algorithm, anti_aliasing, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
        palette = palette.makeDefaultPalette("Fire");
        this.color_intensity = color_intensity;

    }

    public Fire(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, Color fractal_color, BufferedImage image, int out_coloring_algorithm, double color_intensity, int color_cycling_location) {
        
        super(FROMx, TOx, FROMy, TOy, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);
        palette = palette.makeDefaultPalette("Fire");
        this.color_intensity = color_intensity;

    }

    public Fire(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int out_coloring_algorithm, double color_intensity, int color_cycling_location, boolean image_buffering, boolean anti_aliasing, boolean edges, boolean emboss) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
        palette = palette.makeDefaultPalette("Fire");
        this.color_intensity = color_intensity;

    }

    @Override
    protected Color getDrawingColor(double result) {

        return palette.getColor(((result * color_intensity) % max_iterations) / max_iterations);

    }

    @Override
    protected Color getDrawingColorSmooth(double result) {

        return palette.getColor((result >= 0 ? result : -result) * color_intensity % 1);

    }

}
