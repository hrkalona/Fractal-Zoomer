/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
public class Square {
    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;
    public final int iteration;
    
    public Square(int x1, int y1, int x2, int y2, int iteration) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.iteration = iteration;
    }
    
    @Override
    public String toString() {
        return "X1: " + x1 + " Y1: " + y1 + " X2: " + x2 + " Y2: " + y2;
    }
}
