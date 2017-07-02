/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
package fractalzoomer.palettes.interpolation;

/**
 *
 * @author hrkalona2
 */
public class LinearInterpolation extends SmoothInterpolationMethod {

    public LinearInterpolation() {
        super();
    }
    
    @Override
    public int interpolate(int r1, int g1, int b1, int r2, int g2, int b2, double coef) {
        
        int red = (int)(r1 + (r2 - r1) * coef + 0.5);
        int green = (int)(g1 + (g2 - g1) * coef + 0.5);
        int blue = (int)(b1 + (b2 - b1) * coef + 0.5);

        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }
    
}
