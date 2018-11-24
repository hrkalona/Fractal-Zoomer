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
package fractalzoomer.utils;

/**
 *
 * @author hrkalona2
 */
public class ColorAlgorithm {
    public static final int MAXIMUM_ITERATIONS = 1234567890;
    public static final int INCREMENT = 50;
    public static final int MAGNET_INCREMENT = 106;
    public static boolean OutNotUsingIncrement = true;
    public static boolean InNotUsingIncrement = true;
    public static boolean GlobalIncrementBypass = false;

    public static double getResultWithoutIncrement(double result) {

        if(OutNotUsingIncrement && InNotUsingIncrement && !GlobalIncrementBypass) {
            return Math.abs(result);
        }

        if(result <= -INCREMENT) {
            return Math.abs(result + INCREMENT);
        }

        return Math.abs(result);

    }
    
    public static double transformResultToHeight(double result, int max_iterations) {
        
        return Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS ? max_iterations : getResultWithoutIncrement(result);
        
    }
}
