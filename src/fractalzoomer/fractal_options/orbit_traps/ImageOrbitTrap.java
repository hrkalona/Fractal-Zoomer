/*
 * Copyright (C) 2019 hrkalona
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
package fractalzoomer.fractal_options.orbit_traps;

import fractalzoomer.core.Complex;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona
 */
public class ImageOrbitTrap extends OrbitTrap {
    private int color;
    public static BufferedImage image;

    public ImageOrbitTrap(double pointRe, double pointIm, double trapLength, double trapWidth) {

        super(pointRe, pointIm, trapLength, trapWidth);

    }

    @Override
    public void check(Complex val, int iteration) {

        if (trapped) {
            return;
        }

        if (iteration > 0) {
            Complex diff = val.sub(point);

            if (diff.getAbsRe() <= trapWidth * 0.5 && diff.getAbsIm() <= trapLength * 0.5) {

                if (image != null) {
                    int j = (int) ((1 - (diff.getIm() + trapLength * 0.5) / trapLength) * (image.getHeight() - 1) + 0.5);
                    int i = (int) (((diff.getRe() + trapWidth * 0.5) / trapWidth) * (image.getWidth() - 1) + 0.5);

                    color = image.getRGB(i, j);
                
                }
                setTrappedData(val, iteration);
            }
        }

    }

    @Override
    public void initialize(Complex pixel) {

        super.initialize(pixel);

        color = 0;

    }

    @Override
    public double getMaxValue() {
        return 0;
    }

    @Override
    public int getColor() {

        return color;

    }

    @Override
    public boolean hasColor() {

        return true;

    }

}
