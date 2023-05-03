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
package fractalzoomer.utils;

/**
 *
 * @author hrkalona2
 */
public class Pixel implements Comparable<Pixel> {
    public static int customX;
    public static int customY;

    public static boolean useCustom;

    public static int midX;
    public static int midY;
    public static int COMPARE_ALG;
    public static double n;
    public static double nreciprocal;

    public final int x;
    public final int y;
    
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Pixel o) {

        int aX = x + (useCustom ? customX : midX);
        int aY = y + (useCustom ? customY : midY);

        int bX = o.x + (useCustom ? customX : midX);
        int bY = o.y + (useCustom ? customY : midY);

        if(COMPARE_ALG == 0) {
            return Integer.compare(aX * aX + aY * aY, bX * bX + bY * bY);
        }
        else if(COMPARE_ALG == 1) {
            return Integer.compare(Math.max(Math.abs(aX), Math.abs(aY)), Math.max(Math.abs(bX), Math.abs(bY)));
        }
        else if(COMPARE_ALG == 2) {
            return Integer.compare(Math.abs(aX) + Math.abs(aY), Math.abs(bX) + Math.abs(bY));
        }
        else {
            return Double.compare(Math.pow(Math.pow(Math.abs(aX), n) + Math.pow(Math.abs(aY), n), nreciprocal),
                    Math.pow(Math.pow(Math.abs(bX), n) + Math.pow(Math.abs(bY), n), nreciprocal)
                    );
        }

    }
}
