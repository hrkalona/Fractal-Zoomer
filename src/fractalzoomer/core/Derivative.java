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
package fractalzoomer.core;

/**
 *
 * @author hrkalona
 */
public class Derivative {
    public static final int DISABLED = 0;
    public static final int NUMERICAL = 1;
    public static final int NUMERICAL_SYMMETRICAL = 2;

    public static final Complex DZ = new Complex(1e-4, 1e-4);
    public static final Complex DZ_2 = DZ.times(2);
    public static final Complex INV_DZ = DZ.r_divide(1);
    public static final Complex INV_DZ_2 = DZ_2.r_divide(1);
    public static final Complex INV_DZ_SQUARED = DZ.square().r_divide(1);
    public static int DERIVATIVE_METHOD;

    public static Complex numericalDerivativeFirstOrder(Complex fz, Complex fzdz) {

        return fzdz.sub(fz).times_mutable(INV_DZ);

    }

    public static Complex numericalDerivativeSymmetricFirstOrder(Complex fzdz, Complex fzmdz) {

        return fzdz.sub(fzmdz).times_mutable(INV_DZ_2);

    }

    public static Complex numericalDerivativeSecondOrder(Complex fz, Complex fzdz, Complex fz2dz) {

        return fz.sub(fzdz.times(2)).plus_mutable(fz2dz).times_mutable(INV_DZ_SQUARED);

    }

    public static Complex numericalDerivativeSymmetricSecondOrder(Complex fz, Complex fzdz, Complex fzmdz) {

        return fzdz.sub(fz.times(2)).plus_mutable(fzmdz).times_mutable(INV_DZ_SQUARED);

    }
}
