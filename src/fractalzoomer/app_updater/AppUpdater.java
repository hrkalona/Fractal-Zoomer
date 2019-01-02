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

package fractalzoomer.app_updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

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
/**
 *
 * @author hrkalona2
 */
public class AppUpdater {

    private static final String VERSION_URL = "https://raw.githubusercontent.com/hrkalona/Fractal-Zoomer/master/README.txt";
    private static final String DOWNLOAD_URL = "https://sourceforge.net/projects/fractalzoomer/";
    //private static final int DELAY_MILLIS = 50;

    private static int getLatestVersion() {
        InputStream is = null;
        BufferedReader br;
        String version = null;

        try {
            //Thread.sleep(DELAY_MILLIS);

            URL url = new URL(VERSION_URL);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            String temp = br.readLine();
            
            StringTokenizer tokenizer = new StringTokenizer(temp);

            if(tokenizer.countTokens() == 3) {
                String token1 = tokenizer.nextToken();
                String token2 = tokenizer.nextToken();

                if(token1.equals("Fractal") && token2.equals("Zoomer")) {
                    version = tokenizer.nextToken().replace(".", "");
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }

        }
        catch(Exception e) {
            //e.printStackTrace();
            return -1;
        }
        finally {

            try {
                if(is != null) {
                    is.close();
                }
            }
            catch(IOException e) {
            }
        }

        try {
            return Integer.parseInt(version);
        }
        catch(Exception ex) {
            return -1;
        }

    }

    public static String[] checkVersion(int currentVersion) {
        int latestVersion = getLatestVersion();

        String[] res = new String[2];

        if(latestVersion != -1) {
            if(currentVersion < latestVersion) {
                res[0] = "Version " + convertVersion(latestVersion) + " is available! Click here to download.";
                res[1] = DOWNLOAD_URL;

                return res;
            }
            else {
                res[0] = "Your software is up to date!";
                res[1] = "up to date";
                
                return res;
            }
        }

        res[0] = "An error occurred while checking for an updated version of the software.";
        res[1] = "error";

        return res;
    }
    
    public static String convertVersion(int version) {
       
        String temp2 = "" + version;
        String versionStr = "";

        int i;
        for(i = 0; i < temp2.length() - 1; i++) {
            versionStr += temp2.charAt(i) + ".";
        }
        versionStr += temp2.charAt(i);
        
        return versionStr;
        
    }
}
