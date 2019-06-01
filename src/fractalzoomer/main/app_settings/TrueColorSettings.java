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
package fractalzoomer.main.app_settings;

import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class TrueColorSettings {
    public boolean trueColorOut;
    public boolean trueColorIn;
    public int trueColorOutMode;
    public int trueColorInMode;
    public int trueColorOutPreset;
    public int trueColorInPreset;
    public String outTcComponent1;
    public String outTcComponent2;
    public String outTcComponent3;
    public String inTcComponent1;
    public String inTcComponent2;
    public String inTcComponent3;
    public int outTcColorSpace;
    public int inTcColorSpace;
    
    public TrueColorSettings() {
        trueColorOut = false;
        trueColorIn = false;
        
        trueColorOutMode = 0;
        trueColorInMode = 0;
        
        trueColorOutPreset = 0;
        trueColorInPreset = 0;

        outTcComponent1 = "(arg(s) + pi) / (2*pi)";
        outTcComponent2 = "1";
        outTcComponent3 = "1";
        
        inTcComponent1 = "1 - abs(2 * ((arg(s) + pi) / (2*pi)) - 1)";
        inTcComponent2 = "1";
        inTcComponent3 = "(arg(s) + pi) / (2*pi)";
        
        outTcColorSpace = ColorSpaceConverter.HSB;
        inTcColorSpace = ColorSpaceConverter.LCH;
    }
    
}
