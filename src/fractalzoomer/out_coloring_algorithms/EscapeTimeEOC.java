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

package fractalzoomer.out_coloring_algorithms;

public class EscapeTimeEOC extends EscapeTime {

    public EscapeTimeEOC() {
        super();
        OutNotUsingIncrement = true;
    }

    @Override
    public double getResult(Object[] object) {

        return  (Boolean)object[8] ? (Integer)object[0] + MAGNET_INCREMENT  : (Integer)object[0];

    }
    
}
