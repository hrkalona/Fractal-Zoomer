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
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author kaloch
 */
public class OrbitTrapSettings {
    public boolean useTraps;
    public double[] trapPoint;
    public double trapLength;
    public double trapWidth;
    public int trapType;
    public double trapBlending;
    public double trapNorm;
    public int lineType;
    public int trapColorMethod;
    public double trapIntensity;
    public double trapMaxDistance;
    public Color trapColor1;
    public Color trapColor2;
    public Color trapColor3;
    public double trapColorInterpolation;
    public boolean trapIncludeNotEscaped;
    public boolean trapIncludeEscaped;
    public int trapColorFillingMethod;
    public BufferedImage trapImage;
    public boolean trapCellularStructure;
    public boolean trapCellularInverseColor;
    public Color trapCellularColor;
    public boolean countTrapIterations;
    public double trapCellularSize;
    public boolean invertTrapHeight;
    public int trapHeightFunction;
    public int checkType;
    public boolean showOnlyTraps;
    public Color background;

    public double sfm1;
    public double sfm2;
    public double sfn1;
    public double sfn2;
    public double sfn3;
    public double sfa;
    public double sfb;
    
    public OrbitTrapSettings() {

        checkType = Constants.TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE;

        trapType = Constants.POINT_TRAP;
        useTraps = false;
        trapPoint = new double[2];
        trapPoint[0] = 0.0;
        trapPoint[1] = 0.0;
        trapLength = 4;
        trapWidth = 0.4;
        trapBlending = 0.5;
        trapNorm = 2;
        lineType = 0;
        trapColorMethod = 3;
        trapIntensity = 0;
        trapMaxDistance = 0;

        trapColor1 = Color.RED;
        trapColor2 = Color.GREEN;
        trapColor3 = Color.BLUE;
        trapColorInterpolation = 0;
        trapIncludeNotEscaped = true;
        trapIncludeEscaped = true;

        trapColorFillingMethod = Constants.TRAP_COLOR_PER_TRAP;
        
        trapCellularStructure = false;
        trapCellularInverseColor = false;
        trapCellularColor = Color.WHITE;
        countTrapIterations = false;
        trapCellularSize = 0.05;
        
        invertTrapHeight = false;
        trapHeightFunction = 1;

        sfm1 = 3;
        sfm2 = 3;
        sfn1 = 4.5;
        sfn2 = 10;
        sfn3 = 10;
        sfa = 0.5;
        sfb = 0.5;

        showOnlyTraps = false;
        background = Color.BLACK;
        
    }
    
}
