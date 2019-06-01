/*
 * Copyright (C) 2019 hrkalona2
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

import fractalzoomer.core.Complex;
import static fractalzoomer.utils.ColorAlgorithm.INCREMENT;
import static fractalzoomer.utils.ColorAlgorithm.OutNotUsingIncrement;

/**
 *
 * @author hrkalona2
 */
public class BinaryDecomposition2MagneticPendulum extends OutColorAlgorithm {

    public BinaryDecomposition2MagneticPendulum() {

        super();
        OutNotUsingIncrement = false;
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Complex)object[1]).getRe() < 0 ? -(((Complex)object[7]).getRe() + INCREMENT) : ((Complex)object[7]).getRe();

    }
    
}