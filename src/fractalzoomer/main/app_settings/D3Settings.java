/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    public boolean bilateral_scaling;
    public double sigma_s;
    public double sigma_r;
    public int gaussian_kernel;
    public boolean remove_outliers_pre;
    public boolean remove_outliers_post;
    public int outliers_method;
    public double fiX;
    public double fiY;
    public int d3_color_type;
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
    public boolean preHeightScaling;
    public int fractionalTransfer;
    public int fractionalSmoothing;
    public int fractionalTransferMode;
    public double fractionalTransferScale;
    public boolean height_invert;
    
    public D3Settings() {
        
        fiX = 0.64;
        fiY = 0.82;

        d3_height_scale = 1;
        height_algorithm = 0;
        d3_size_scale = 1;
        
        d3_color_type = 3;

        color_3d_blending = 0.84;

        gaussian_scaling = false;
        bilateral_scaling = false;
        sigma_s = 2;
        sigma_r = 2;
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

        remove_outliers_pre = true;
        remove_outliers_post = false;

        preHeightScaling = false;

        outliers_method = 0;

        fractionalTransfer = 0;
        fractionalSmoothing = 0;
        fractionalTransferMode = 1;
        fractionalTransferScale = 10;

        height_invert = false;
        
    }
}
