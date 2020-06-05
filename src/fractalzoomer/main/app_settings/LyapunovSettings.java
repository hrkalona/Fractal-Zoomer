/*
 * Copyright (C) 2020 hrkalona2
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

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author hrkalona2
 */
public class LyapunovSettings {
    public String lyapunovA;
    public String lyapunovB;
    public String lyapunovC;
    public String lyapunovD;
    public String lyapunovExpression;
    public String lyapunovFunction;
    public String lyapunovExponentFunction;
    public String lyapunovInitialValue;
    public String[] lyapunovFinalExpression;
    public boolean useLyapunovExponent;
    public int lyapunovVariableId;
    public int lyapunovInitializationIteratons;
    public boolean lyapunovskipBailoutCheck;
    public static final String DEFAULT_LYAPUNOV_FUNCTION = "r * z * (1 - z)";
    public static final String DEFAULT_LYAPUNOV_EXPONENT_FUNCTION = "r * (1 - 2 * z)";
    public static final String DEFAULT_LYAPUNOV_FUNCTION_TRIMMED = DEFAULT_LYAPUNOV_FUNCTION.replaceAll("\\s+", "");
    public static final String DEFAULT_LYAPUNOV_EXPONENT_FUNCTION_TRIMMED = DEFAULT_LYAPUNOV_EXPONENT_FUNCTION.replaceAll("\\s+", "");
    
    public LyapunovSettings() {
        lyapunovA = "re(c)";
        lyapunovB = "im(c)";
        lyapunovC = "0";
        lyapunovD = "0";
        lyapunovExpression = "$A; $B";
        lyapunovFinalExpression = new String[2];
        lyapunovFinalExpression[0] = lyapunovA;
        lyapunovFinalExpression[1] = lyapunovB;
        useLyapunovExponent = true;
        lyapunovFunction = DEFAULT_LYAPUNOV_FUNCTION;
        lyapunovExponentFunction = DEFAULT_LYAPUNOV_EXPONENT_FUNCTION;
        lyapunovVariableId = 0;
        lyapunovInitialValue = "0.5";
        lyapunovInitializationIteratons = 0;
        lyapunovskipBailoutCheck = false;
    }
    
    public static String[] getTokens(String input) {
        
        StringTokenizer st = new StringTokenizer(input, ";");
        
        ArrayList<String> tokens = new ArrayList<>();
        while(st.hasMoreTokens()) {
            String temp = st.nextToken();
            temp = temp.trim();          
            tokens.add(temp);
        }
          
        if(!tokens.isEmpty()) {
            return tokens.toArray(new String[tokens.size()]);
        }
        
        return null;
        
    }
    
    public static String[] flatten(String[] input, String source, String destination) {
        
        for(int i = 0; i < input.length; i++) {
            input[i] = input[i].replace(source.toUpperCase(), destination);
            input[i] = input[i].replace(source.toLowerCase(), destination);
        }
        
        return input;
        
    }
    
}
