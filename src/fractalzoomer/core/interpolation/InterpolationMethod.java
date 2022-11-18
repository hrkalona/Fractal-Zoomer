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
package fractalzoomer.core.interpolation;

import fractalzoomer.main.MainWindow;

import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public abstract class InterpolationMethod {
    
    protected InterpolationMethod() {
        
    }

    public static InterpolationMethod create(int method) {
        switch(method) {
            case MainWindow.INTERPOLATION_LINEAR:
                return new LinearInterpolation();
            case MainWindow.INTERPOLATION_COSINE:
                return new CosineInterpolation();
            case MainWindow.INTERPOLATION_ACCELERATION:
                return new AccelerationInterpolation();
            case MainWindow.INTERPOLATION_DECELERATION:
                return new DecelerationInterpolation();
            case MainWindow.INTERPOLATION_EXPONENTIAL:
                return new ExponentialInterpolation();
            case MainWindow.INTERPOLATION_CATMULLROM:
                return new CatmullRomInterpolation();
            case MainWindow.INTERPOLATION_CATMULLROM2:
                return new CatmullRom2Interpolation();
            case MainWindow.INTERPOLATION_SIGMOID:
                return new SigmoidInterpolation();
            case MainWindow.INTERPOLATION_QUADRATIC:
                return new QuadraticInterpolation();
            case MainWindow.INTERPOLATION_CUBIC:
                return new CubicInterpolation();
            case MainWindow.INTERPOLATION_SQRT_VALUES:
                return new SqrtValuesInterpolation();
            case MainWindow.INTERPOLATION_SINE:
                return new SineInterpolation();
            case MainWindow.INTERPOLATION_SQRT:
                return new SqrtInterpolation();
            case MainWindow.INTERPOLATION_THIRD_POLY:
                return new ThirdPolynomialInterpolation();
            case MainWindow.INTERPOLATION_FIFTH_POLY:
                return new FifthPolynomialInterpolation();
            case MainWindow.INTERPOLATION_EXPONENTIAL_2:
                return new Exponential2Interpolation();
            case MainWindow.INTERPOLATION_CUBE_ROOT:
                return new CbrtInterpolation();
            case MainWindow.INTERPOLATION_FOURTH_ROOT:
                return new FrthrootInterpolation();
            case MainWindow.INTERPOLATION_SMOOTH_TRANSITION_STEP:
                return new SmoothTransitionFunctionInterpolation();
        }

        return null;
    }
    
    public abstract int interpolate(int r1, int g1, int b1, int r2, int g2, int b2, double coef);
    public abstract double interpolate(double a, double b, double coef);
    public abstract int interpolate(int a, int b, double coef);


    public Color interpolate(Color c1, Color c2, double coef) {

        return new Color(interpolate(c1.getRed(), c1.getGreen(), c1.getBlue(), c2.getRed(), c2.getGreen(), c2.getBlue(), coef));

    }

    public abstract double getCoef(double coef);
}
