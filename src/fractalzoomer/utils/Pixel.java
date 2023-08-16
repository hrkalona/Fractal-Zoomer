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
    public static boolean REVERT;
    public static double n;
    public static double nreciprocal;

    public final int x;
    public final int y;

    public static double SPACING;

    public static boolean REPEAT;
    
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Pixel o) {

        double aX = x + (useCustom ? customX : midX);
        double aY = y + (useCustom ? customY : midY);

        double bX = o.x + (useCustom ? customX : midX);
        double bY = o.y + (useCustom ? customY : midY);

        if(REPEAT) {
            aX = Math.sin(aX * SPACING) + 1;
            aY = Math.sin(aY * SPACING) + 1;
            bX = Math.sin(bX * SPACING) + 1;
            bY = Math.sin(bY * SPACING) + 1;
        }

        if(COMPARE_ALG == 0) {
            return !REVERT ? Double.compare(aX * aX + aY * aY, bX * bX + bY * bY) : Double.compare( bX * bX + bY * bY, aX * aX + aY * aY);
        }
        else if(COMPARE_ALG == 1) {
            return !REVERT ? Double.compare(Math.max(Math.abs(aX), Math.abs(aY)), Math.max(Math.abs(bX), Math.abs(bY))) : Double.compare(Math.max(Math.abs(bX), Math.abs(bY)), Math.max(Math.abs(aX), Math.abs(aY)));
        }
        else if(COMPARE_ALG == 2) {
            return !REVERT ? Double.compare(Math.abs(aX) + Math.abs(aY), Math.abs(bX) + Math.abs(bY)) : Double.compare(Math.abs(bX) + Math.abs(bY), Math.abs(aX) + Math.abs(aY));
        }
        else if (COMPARE_ALG == 3) {
            return !REVERT ? Double.compare(Math.pow(Math.pow(Math.abs(aX), n) + Math.pow(Math.abs(aY), n), nreciprocal),
                    Math.pow(Math.pow(Math.abs(bX), n) + Math.pow(Math.abs(bY), n), nreciprocal)
                    ) :
                    Double.compare(Math.pow(Math.pow(Math.abs(bX), n) + Math.pow(Math.abs(bY), n), nreciprocal), Math.pow(Math.pow(Math.abs(aX), n) + Math.pow(Math.abs(aY), n), nreciprocal));
        }
        //COMPARE_ALG : 4 is shuffle
        else if (COMPARE_ALG == 5) {
            return !REVERT ? Double.compare(Math.abs(aX), Math.abs(bX)) : Double.compare(Math.abs(bX), Math.abs(aX));
        }
        else if (COMPARE_ALG == 6) {
            return !REVERT ? Double.compare(Math.abs(aY), Math.abs(bY)) : Double.compare(Math.abs(bY), Math.abs(aY));
        }
        else if (COMPARE_ALG == 7) {
            return !REVERT ? Double.compare(Math.atan2(aY, aX), Math.atan2(bY, bX)) : Double.compare(Math.atan2(bY, bX), Math.atan2(aY, aX));
        }
        else if (COMPARE_ALG == 8) {
            return !REVERT ? Double.compare(Math.abs(Math.atan2(aY, aX)), Math.abs(Math.atan2(bY, bX))) : Double.compare(Math.abs(Math.atan2(bY, bX)), Math.abs(Math.atan2(aY, aX)));
        }
        else if (COMPARE_ALG == 9) {
            return !REVERT ? Double.compare(aX, bX) : Double.compare(bX, aX);
        }
        else if (COMPARE_ALG == 10) {
            return !REVERT ? Double.compare(aY, bY) : Double.compare(bY, aY);
        }
        else if (COMPARE_ALG == 11) {
            return !REVERT ? Double.compare(aX + aY, bX + bY) : Double.compare(bX + bY, aX + aY);
        }
        else if (COMPARE_ALG == 12) {
            return !REVERT ? Double.compare(aX - aY, bX - bY) : Double.compare(bX - bY, aX - aY);
        }
        else if (COMPARE_ALG == 13) {
            return !REVERT ? Double.compare(aX * aY, bX * bY) : Double.compare(bX * bY, aX * aY);
        }
        else if (COMPARE_ALG == 14) {
            return !REVERT ? Double.compare(Math.abs(aX) * Math.abs(aY), Math.abs(bX) * Math.abs(bY)) : Double.compare(Math.abs(bX) * Math.abs(bY), Math.abs(aX) * Math.abs(aY));
        }
        else {
            return !REVERT ? Double.compare(Math.min(Math.abs(aX), Math.abs(aY)), Math.min(Math.abs(bX), Math.abs(bY))) : Double.compare(Math.min(Math.abs(bX), Math.abs(bY)), Math.min(Math.abs(aX), Math.abs(aY)));
        }

    }
}
