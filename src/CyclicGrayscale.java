


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
public class CyclicGrayscale extends ThreadDraw {
  private Palette palette;
  private double color_intensity;

    public CyclicGrayscale(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter,  double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
        palette = palette.makeDefaultPalette("CyclicGrayscale");
        this.color_intensity = color_intensity;

    }

    public CyclicGrayscale(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
        palette = palette.makeDefaultPalette("CyclicGrayscale");
        this.color_intensity = color_intensity;

    }
    
    public CyclicGrayscale(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        palette = palette.makeDefaultPalette("CyclicGrayscale");
        this.color_intensity = color_intensity;

    }

    public CyclicGrayscale(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
        palette = palette.makeDefaultPalette("CyclicGrayscale");
        this.color_intensity = color_intensity;

    }

    public CyclicGrayscale(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, BufferedImage image, int out_coloring_algorithm, double color_intensity, int color_cycling_location) {
        
        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);
        palette = palette.makeDefaultPalette("CyclicGrayscale");
        this.color_intensity = color_intensity;
        
    }

    public CyclicGrayscale(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int out_coloring_algorithm, double color_intensity, int color_cycling_location, boolean[] filters) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, filters);
        palette = palette.makeDefaultPalette("CyclicGrayscale");
        this.color_intensity = color_intensity;

    }

    @Override
    protected Color getDrawingColor(double result) {

        return palette.getColor(((result * color_intensity) % 55) / 55);

    }

    @Override
    protected Color getDrawingColorSmooth(double result) {

        return palette.getColor((result >= 0 ? result : -result) * color_intensity % 1);

    }

}
