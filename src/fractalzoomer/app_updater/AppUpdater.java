

package fractalzoomer.app_updater;

import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;


/**
 *
 * @author hrkalona2
 */
public class AppUpdater {

    private static final String VERSION_URL = "https://raw.githubusercontent.com/hrkalona/Fractal-Zoomer/master/README.md";
    private static final String VERSION_URL2 = "https://raw.githubusercontent.com/hrkalona/Fractal-Zoomer/master/README.txt";
    private static final String DOWNLOAD_URL = "https://sourceforge.net/projects/fractalzoomer/";
    //private static final int DELAY_MILLIS = 50;

    private static int getLatestVersion() {

        int version = -1;
        try {
            version = getLatestVersionInternal(VERSION_URL);
        }
        catch (FileNotFoundException ex) {
            try {
                version = getLatestVersionInternal(VERSION_URL2);
            }
            catch (Exception ex2) {
                return -1;
            }
        }
        catch (Exception ex) {
            return -1;
        }

        return version;

    }

    private static int getLatestVersionInternal(String urlStr) throws IOException {
        int version = -1;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            is = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String temp = br.readLine();

            StringTokenizer tokenizer = new StringTokenizer(temp);

            if (tokenizer.countTokens() == 3) {
                String token1 = tokenizer.nextToken();
                String token2 = tokenizer.nextToken();

                if (token1.equals("Fractal") && token2.equals("Zoomer")) {
                    version = Integer.parseInt(tokenizer.nextToken().replace(".", ""));
                }
            }
        }
        catch (Exception ex) {
            if(is != null) {
                is.close();
            }
            throw ex;
        }

        is.close();

        return version;
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
