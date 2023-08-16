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
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class UserTrueColorAlgorithm extends TrueColorAlgorithm {

    private ExpressionNode expr1;
    private Parser parser1;
    private ExpressionNode expr2;
    private Parser parser2;
    private ExpressionNode expr3;
    private Parser parser3;
    private Complex[] globalVars;
    private int space;

    public UserTrueColorAlgorithm(String c1, String c2, String c3, int space, double genericBail, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars) {
        super();

        this.space = space;

        parser1 = new Parser();
        expr1 = parser1.parse(c1);

        parser2 = new Parser();
        expr2 = parser2.parse(c2);

        parser3 = new Parser();
        expr3 = parser3.parse(c3);

        Complex cbound = new Complex(genericBail, 0);

        if (parser1.foundBail()) {
            parser1.setBailvalue(cbound);
        }

        if (parser2.foundBail()) {
            parser2.setBailvalue(cbound);
        }

        if (parser3.foundBail()) {
            parser3.setBailvalue(cbound);
        }

        Complex ccbail = new Complex(genericBail, 0);

        if (parser1.foundCbail()) {
            parser1.setCbailvalue(ccbail);
        }

        if (parser2.foundCbail()) {
            parser2.setCbailvalue(ccbail);
        }

        if (parser3.foundCbail()) {
            parser3.setCbailvalue(ccbail);
        }

        Complex c_max_iterations = new Complex(max_iterations, 0);

        if (parser1.foundMaxn()) {
            parser1.setMaxnvalue(c_max_iterations);
        }

        if (parser2.foundMaxn()) {
            parser2.setMaxnvalue(c_max_iterations);
        }

        if (parser3.foundMaxn()) {
            parser3.setMaxnvalue(c_max_iterations);
        }

        Complex c_center = new Complex(xCenter, yCenter);

        if (parser1.foundCenter()) {
            parser1.setCentervalue(c_center);
        }

        if (parser2.foundCenter()) {
            parser2.setCentervalue(c_center);
        }

        if (parser3.foundCenter()) {
            parser3.setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);

        if (parser1.foundSize()) {
            parser1.setSizevalue(c_size);
        }

        if (parser2.foundSize()) {
            parser2.setSizevalue(c_size);
        }

        if (parser3.foundSize()) {
            parser3.setSizevalue(c_size);
        }

        Complex c_isize = new Complex(TaskDraw.IMAGE_SIZE, 0);
        if (parser1.foundISize()) {
            parser1.setISizevalue(c_isize);
        }

        if (parser2.foundISize()) {
            parser2.setISizevalue(c_isize);
        }

        if (parser3.foundISize()) {
            parser3.setISizevalue(c_isize);
        }

        Complex c_point = new Complex(point[0], point[1]);

        if (parser1.foundPoint()) {
            parser1.setPointvalue(c_point);
        }

        if (parser2.foundPoint()) {
            parser2.setPointvalue(c_point);
        }

        if (parser3.foundPoint()) {
            parser3.setPointvalue(c_point);
        }

    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped) {

        /**
         * * C1 ***
         */
        if (parser1.foundN()) {
            parser1.setNvalue(new Complex(iterations, 0));
        }

        if (parser1.foundZ()) {
            parser1.setZvalue(z);
        }

        if (parser1.foundC()) {
            parser1.setCvalue(c);
        }

        if (parser1.foundS()) {
            parser1.setSvalue(start);
        }

        if (parser1.foundPixel()) {
            parser1.setPixelvalue(pixel);
        }

        if (parser1.foundC0()) {
            parser1.setSvalue(c0);
        }

        if (parser1.foundP()) {
            parser1.setPvalue(zold);
        }

        if (parser1.foundPP()) {
            parser1.setPPvalue(zold2);
        }

        if (parser1.foundStat()) {
            parser1.setStatvalue(new Complex(stat, 0));
        }

        if (parser1.foundTrap()) {
            parser1.setTrapvalue(new Complex(trap, 0));
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser1.foundVar(i)) {
                parser1.setVarsvalue(i, globalVars[i]);
            }
        }

        /**
         * *************************
         */
        if (space != ColorSpaceConverter.DIRECT && space != ColorSpaceConverter.PALETTE && space != ColorSpaceConverter.GRADIENT) {
            /**
             * * C2 ***
             */
            if (parser2.foundN()) {
                parser2.setNvalue(new Complex(iterations, 0));
            }

            if (parser2.foundZ()) {
                parser2.setZvalue(z);
            }

            if (parser2.foundC()) {
                parser2.setCvalue(c);
            }

            if (parser2.foundS()) {
                parser2.setSvalue(start);
            }

            if (parser2.foundPixel()) {
                parser2.setPixelvalue(pixel);
            }

            if (parser2.foundC0()) {
                parser2.setSvalue(c0);
            }

            if (parser2.foundP()) {
                parser2.setPvalue(zold);
            }

            if (parser2.foundPP()) {
                parser2.setPPvalue(zold2);
            }

            if (parser2.foundStat()) {
                parser2.setStatvalue(new Complex(stat, 0));
            }

            if (parser2.foundTrap()) {
                parser2.setTrapvalue(new Complex(trap, 0));
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2.foundVar(i)) {
                    parser2.setVarsvalue(i, globalVars[i]);
                }
            }

            /**
             * *************************
             */
            /**
             * * C3 ***
             */
            if (parser3.foundN()) {
                parser3.setNvalue(new Complex(iterations, 0));
            }

            if (parser3.foundZ()) {
                parser3.setZvalue(z);
            }

            if (parser3.foundC()) {
                parser3.setCvalue(c);
            }

            if (parser3.foundS()) {
                parser3.setSvalue(start);
            }

            if (parser3.foundPixel()) {
                parser3.setPixelvalue(pixel);
            }

            if (parser3.foundC0()) {
                parser3.setSvalue(c0);
            }

            if (parser3.foundP()) {
                parser3.setPvalue(zold);
            }

            if (parser3.foundPP()) {
                parser3.setPPvalue(zold2);
            }

            if (parser3.foundStat()) {
                parser3.setStatvalue(new Complex(stat, 0));
            }

            if (parser3.foundTrap()) {
                parser3.setTrapvalue(new Complex(trap, 0));
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser3.foundVar(i)) {
                    parser3.setVarsvalue(i, globalVars[i]);
                }
            }

            /**
             * *************************
             */
        }

        double c1 = 0, c2 = 0, c3 = 0;

        c1 = expr1.getValue().getRe();

        if (space != ColorSpaceConverter.DIRECT && space != ColorSpaceConverter.PALETTE && space != ColorSpaceConverter.GRADIENT) {
            c2 = expr2.getValue().getRe();
            c3 = expr3.getValue().getRe();
        }

        return getColor(c1, c2, c3, space, escaped);

    }

    private static int getColor(double c1, double c2, double c3, int space, boolean escaped) {

        if (space != ColorSpaceConverter.DIRECT && space != ColorSpaceConverter.PALETTE && space != ColorSpaceConverter.GRADIENT) {
            c1 = ColorSpaceConverter.clamp(c1);
            c2 = ColorSpaceConverter.clamp(c2);
            c3 = ColorSpaceConverter.clamp(c3);
        }

        switch (space) {
            case ColorSpaceConverter.RGB:
                return 0xFF000000 | ((int) (255 * c1 + 0.5)) << 16 | ((int) (255 * c2 + 0.5)) << 8 | ((int) (255 * c3 + 0.5));
            case ColorSpaceConverter.XYZ:
                int[] res = ColorSpaceConverter.XYZtoRGB(c1 * 100, c2 * 100, c3 * 100);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.HSB:
                res = ColorSpaceConverter.HSBtoRGB(c1, c2, c3);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.HWB:
                res = ColorSpaceConverter.HWBtoRGB(c1, c2, c3);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.HSL:
                res = ColorSpaceConverter.HSLtoRGB(c1, c2, c3);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.RYB:
                res = ColorSpaceConverter.RYBtoRGB(c1, c2, c3);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.LAB:
                res = ColorSpaceConverter.LABtoRGB(c1 * 100, (2 * c2 - 1) * 100, (2 * c3 - 1) * 100);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.LCH_ab:
                res = ColorSpaceConverter.LCH_abtoRGB(c1 * 100, c2 * 140, c3 * 360);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.HSL_uv:
                res = ColorSpaceConverter.HSL_uvtoRGB(c1 * 360, c2 * 128, c3 * 100);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.LCH_uv:
                res = ColorSpaceConverter.LCH_uvtoRGB(c1 * 100, c2 * 179.08, c3 * 360);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.LCH_oklab:
                res = ColorSpaceConverter.LCH_oklabtoRGB(c1, c2 * 0.3224, c3 * 360);
                return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
            case ColorSpaceConverter.DIRECT:
                return 0xFF000000 | (0x00FFFFFF & (int) c1);
            case ColorSpaceConverter.PALETTE:
                c1 = Math.abs(c1);

                if (escaped || !usePaletteForInColoring) {
                    double transfered_result = color_transfer_outcoloring.transfer(c1);
                    return palette_outcoloring.getPaletteColor(transfered_result + color_cycling_location_outcoloring);
                } else {
                    double transfered_result = color_transfer_incoloring.transfer(c1);
                    return palette_incoloring.getPaletteColor(transfered_result + color_cycling_location_incoloring);
                }

            case ColorSpaceConverter.GRADIENT:
                c1 = Math.abs(c1);               
                return gradient[(int)(c1 + gradient_offset + 0.5) % gradient.length];               
            default:
                return 0;
        }
    }

}
