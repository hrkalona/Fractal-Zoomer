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
    public String[] lyapunovFinalExpression;
    public boolean useLyapunovExponent;
    
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
