/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
package fractalzoomer.core.blending;

import fractalzoomer.core.interpolation.AccelerationInterpolation;
import fractalzoomer.core.interpolation.CatmullRom2Interpolation;
import fractalzoomer.core.interpolation.CatmullRomInterpolation;
import fractalzoomer.core.interpolation.CosineInterpolation;
import fractalzoomer.core.interpolation.DecelerationInterpolation;
import fractalzoomer.core.interpolation.ExponentialInterpolation;
import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.core.interpolation.LinearInterpolation;
import fractalzoomer.core.interpolation.SigmoidInterpolation;
import fractalzoomer.main.MainWindow;

/**
 *
 * @author hrkalona2
 */
public abstract class Blending {
    protected InterpolationMethod method;
    
    public Blending(int color_interpolation) {
        
        switch (color_interpolation) {
            case MainWindow.INTERPOLATION_LINEAR:
                method = new LinearInterpolation();
                break;
            case MainWindow.INTERPOLATION_COSINE:
                method = new CosineInterpolation();
                break;
            case MainWindow.INTERPOLATION_ACCELERATION:
                method = new AccelerationInterpolation();
                break;
            case MainWindow.INTERPOLATION_DECELERATION:
                method = new DecelerationInterpolation();
                break;
            case MainWindow.INTERPOLATION_EXPONENTIAL:
                method = new ExponentialInterpolation();
                break;
            case MainWindow.INTERPOLATION_CATMULLROM:
                method = new CatmullRomInterpolation();
                break;
            case MainWindow.INTERPOLATION_CATMULLROM2:
                method = new CatmullRom2Interpolation();
                break;
            case MainWindow.INTERPOLATION_SIGMOID:
                method = new SigmoidInterpolation();
                break;
        }
    }
    
    public abstract int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef);
}
