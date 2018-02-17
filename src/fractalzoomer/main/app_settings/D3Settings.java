/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.main.app_settings;

/**
 *
 * @author kaloch
 */
public class D3Settings {
    public boolean d3;
    public int detail;
    public boolean gaussian_scaling;
    public double gaussian_weight;
    public int gaussian_kernel;
    public double fiX;
    public double fiY;
    public double color_3d_blending;
    public double d3_size_scale;
    public int max_range;
    public int min_range;
    public int max_scaling;
    public double d3_height_scale;
    public int height_algorithm;
    public boolean shade_height;
    public int shade_choice;
    public int shade_algorithm;
    public boolean shade_invert;
    
    public D3Settings() {
        
        fiX = 0.64;
        fiY = 0.82;

        d3_height_scale = 1;
        height_algorithm = 0;
        d3_size_scale = 1;

        color_3d_blending = 0.84;

        gaussian_scaling = false;
        gaussian_weight = 2;
        gaussian_kernel = 1;

        shade_height = false;
        shade_choice = 0;
        shade_algorithm = 0;
        shade_invert = false;

        max_range = 100;
        min_range = 0;
        max_scaling = 100;

        d3 = false;
        detail = 400;
        
    }
}
