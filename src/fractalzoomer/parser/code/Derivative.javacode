/*
 * Copyright (C) 2020 hrkalona
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
    public static final int NUMERICAL_FORWARD = 1;
    public static final int NUMERICAL_CENTRAL = 2;
    public static final int NUMERICAL_BACKWARD = 3;

    public static Complex DZ = new Complex(1e-3 * 0.5, 1e-3 * 0.5);
    public static Complex DZ_2;
    public static Complex DZ_3;

    public static Complex INV_DZ;
    public static Complex INV_DZ_2;
    public static Complex INV_DZ_SQUARED;
    public static Complex INV_DZ_CUBED;

    static {
        calculateConstants();
    }

    public static void calculateConstants() {
        DZ_2 = DZ.times(2);
        DZ_3 = DZ.times(3);

        INV_DZ = DZ.r_divide(1);
        INV_DZ_2 = DZ_2.r_divide(1);
        INV_DZ_SQUARED = DZ.square().r_divide(1);
        INV_DZ_CUBED = DZ.cube().r_divide(1);
    }

    public static int DERIVATIVE_METHOD;

    //Parameters: f(z), f(z + dz)
    public static Complex numericalForwardDerivativeFirstOrder(Complex fz, Complex fzdz) {

        return fzdz.sub(fz).times_mutable(INV_DZ);

    }

    //Parameters: f(z + dz), f(z - dz)
    public static Complex numericalCentralDerivativeFirstOrder(Complex fzdz, Complex fzmdz) {

        return fzdz.sub(fzmdz).times_mutable(INV_DZ_2);

    }

    //Parameters: f(z), f(z - dz)
    public static Complex numericalBackwardDerivativeFirstOrder(Complex fz, Complex fzmdz) {

        return fz.sub(fzmdz).times_mutable(INV_DZ);

    }

    //Parameters: f(z), f(z + dz), f(z + 2 * dz)
    public static Complex numericalForwardDerivativeSecondOrder(Complex fz, Complex fzdz, Complex fz2dz) {

        return fz.sub(fzdz.times(2)).plus_mutable(fz2dz).times_mutable(INV_DZ_SQUARED);

    }

    //Parameters: f(z), f(z + dz), f(z - dz)
    public static Complex numericalCentralDerivativeSecondOrder(Complex fz, Complex fzdz, Complex fzmdz) {

        return fzdz.sub(fz.times(2)).plus_mutable(fzmdz).times_mutable(INV_DZ_SQUARED);

    }

    //Parameters: f(z), f(z - dz), f(z - 2 * dz)
    public static Complex numericalBackwardDerivativeSecondOrder(Complex fz, Complex fzmdz, Complex fzm2dz) {

        return fz.sub(fzmdz.times(2)).plus_mutable(fzm2dz).times_mutable(INV_DZ_SQUARED);

    }

    //Parameters: f(z), f(z + dz), f(z + 2 * dz), f(z + 3 * dz)
    public static Complex numericalForwardDerivativeThirdOrder(Complex fz, Complex fzdz, Complex fz2dz, Complex fz3dz) {

        return fz3dz.sub(fz2dz.times(3)).plus_mutable(fzdz.times(3)).sub_mutable(fz).times_mutable(INV_DZ_CUBED);

    }

    //Parameters: f(z), f(z - dz), f(z - 2 * dz), f(z - 3 * dz)
    public static Complex numericalBackwardDerivativeThirdOrder(Complex fz, Complex fzmdz, Complex fzm2dz, Complex fzm3dz) {

        return fz.sub(fzmdz.times(3)).plus_mutable(fzm2dz.times(3)).sub_mutable(fzm3dz).times_mutable(INV_DZ_CUBED);

    }

    //Parameters: f(z + dz), f(z + 2 * dz), f(z - dz), f(z - 2 * dz)
    public static Complex numericalCentralDerivativeThirdOrder(Complex fzdz, Complex fz2dz, Complex fzmdz, Complex fzm2dz) {

        return fzmdz.sub(fzm2dz.times(0.5)).plus_mutable(fz2dz.times(0.5)).sub_mutable(fzdz).times_mutable(INV_DZ_CUBED);

    }
}
