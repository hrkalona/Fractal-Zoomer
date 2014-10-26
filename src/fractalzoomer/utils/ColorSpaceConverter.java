package fractalzoomer.utils;

import java.awt.Color;

// --------------------------------------------------------
// convert an image to a different color space
// --------------------------------------------------------


  /**
   * ColorSpaceConverter
   * @author dvs, hlp
   * Created Jan 15, 2004
   * Version 3 posted on ImageJ Mar 12, 2006 by Duane Schwartzwald
   *    vonschwartzwalder at mac.com
   * Version 4 created Feb. 27, 2007
   *   by Harry Parker, harrylparker at yahoo dot com,
   *   corrects RGB to XYZ (and LAB) conversion.
   */
  public class ColorSpaceConverter {

    /**
     * reference white in XYZ coordinates
     */
    public double[] D50 = {96.4212, 100.0, 82.5188};
    public double[] D55 = {95.6797, 100.0, 92.1481};
    public double[] D65 = {95.0429, 100.0, 108.8900};
    public double[] D75 = {94.9722, 100.0, 122.6394};
    public double[] whitePoint = D65;

    /**
     * reference white in xyY coordinates
     */
    public double[] chromaD50 = {0.3457, 0.3585, 100.0};
    public double[] chromaD55 = {0.3324, 0.3474, 100.0};
    public double[] chromaD65 = {0.3127, 0.3290, 100.0};
    public double[] chromaD75 = {0.2990, 0.3149, 100.0};
    public double[] chromaWhitePoint = chromaD65;

    /**
     * sRGB to XYZ conversion matrix
     */
    public double[][] M   = {{0.4124, 0.3576,  0.1805},
                             {0.2126, 0.7152,  0.0722},
                             {0.0193, 0.1192,  0.9505}};

    /**
     * XYZ to sRGB conversion matrix
     */
    public double[][] Mi  = {{ 3.2406, -1.5372, -0.4986},
                             {-0.9689,  1.8758,  0.0415},
                             { 0.0557, -0.2040,  1.0570}};

    /**
     * default constructor, uses D65 for the white point
     */
    public ColorSpaceConverter() {
      whitePoint = D65;
      chromaWhitePoint = chromaD65;
    }

    /**
     * constructor for setting a non-default white point
     * @param white String specifying the white point to use
     */
    public ColorSpaceConverter(String white) {
      whitePoint = D65;
      chromaWhitePoint = chromaD65;
      if (white.equalsIgnoreCase("d50")) {
        whitePoint = D50;
        chromaWhitePoint = chromaD50;
      }
      else if (white.equalsIgnoreCase("d55")) {
        whitePoint = D55;
        chromaWhitePoint = chromaD55;
      }
      else if (white.equalsIgnoreCase("d65")) {
        whitePoint = D65;
        chromaWhitePoint = chromaD65;
      }
      else if (white.equalsIgnoreCase("d75")) {
        whitePoint = D75;
        chromaWhitePoint = chromaD75;
      }
    }

    /**
     * @param H Hue angle/360 (0..1)
     * @param S Saturation (0..1)
     * @param B Value (0..1)
     * @return RGB values
     */
    public int[] HSBtoRGB(double H, double S, double B) {
      int[] result = new int[3];
      int rgb = Color.HSBtoRGB((float) H, (float) S, (float) B);
      result[0] = (rgb >> 16) & 0xff;
      result[1] = (rgb >> 8) & 0xff;
      result[2] = (rgb >> 0) & 0xff;
      return result;
    }

    public int[] HSBtoRGB(double[] HSB) {
      return HSBtoRGB(HSB[0], HSB[1], HSB[2]);
    }

    /**
     * Convert LAB to RGB.
     * @param L
     * @param a
     * @param b
     * @return RGB values
     */
    public int[] LABtoRGB(double L, double a, double b) {
      return XYZtoRGB(LABtoXYZ(L, a, b));
    }

    /**
     * @param Lab
     * @return RGB values
     */
    public int[] LABtoRGB(double[] Lab) {
      return XYZtoRGB(LABtoXYZ(Lab));
    }

    /**
     * Convert LAB to XYZ.
     * @param L
     * @param a
     * @param b
     * @return XYZ values
     */
    public double[] LABtoXYZ(double L, double a, double b) {
      double[] result = new double[3];

      double y = (L + 16.0) / 116.0;
      double y3 = Math.pow(y, 3.0);
      double x = (a / 500.0) + y;
      double x3 = Math.pow(x, 3.0);
      double z = y - (b / 200.0);
      double z3 = Math.pow(z, 3.0);

      if (y3 > 0.008856) {
        y = y3;
      }
      else {
        y = (y - (16.0 / 116.0)) / 7.787;
      }
      if (x3 > 0.008856) {
        x = x3;
      }
      else {
        x = (x - (16.0 / 116.0)) / 7.787;
      }
      if (z3 > 0.008856) {
        z = z3;
      }
      else {
        z = (z - (16.0 / 116.0)) / 7.787;
      }

      result[0] = x * whitePoint[0];
      result[1] = y * whitePoint[1];
      result[2] = z * whitePoint[2];

      return result;
    }

    /**
     * Convert LAB to XYZ.
     * @param Lab
     * @return XYZ values
     */
    public double[] LABtoXYZ(double[] Lab) {
      return LABtoXYZ(Lab[0], Lab[1], Lab[2]);
    }

    /**
     * @param R Red in range 0..255
     * @param G Green in range 0..255
     * @param B Blue in range 0..255
     * @return HSB values: H is 0..360 degrees / 360 (0..1), S is 0..1, B is 0..1
     */
    public double[] RGBtoHSB(int R, int G, int B) {
      double[] result = new double[3];
      float[] hsb = new float[3];
      Color.RGBtoHSB(R, G, B, hsb);
      result[0] = hsb[0];
      result[1] = hsb[1];
      result[2] = hsb[2];
      return result;
    }

    public double[] RGBtoHSB(int[] RGB) {
      return RGBtoHSB(RGB[0], RGB[1], RGB[2]);
    }

    /**
     * @param R
     * @param G
     * @param B
     * @return Lab values
     */
    public double[] RGBtoLAB(int R, int G, int B) {
      return XYZtoLAB(RGBtoXYZ(R, G, B));
    }

    /**
     * @param RGB
     * @return Lab values
     * 
     * D65
     * L : 0, 100
     * a : -86.17, 98.26
     * b : -107.85, 94.48
     */
    public double[] RGBtoLAB(int[] RGB) {
      return XYZtoLAB(RGBtoXYZ(RGB));
    }

    /**
     * Convert RGB to XYZ
     * @param R
     * @param G
     * @param B
     * @return XYZ in double array.
     * 
     * D65
     * X : 0, 95.05
     * Y : 0, 100
     * Z : 0, 108.8
     */
    public double[] RGBtoXYZ(int R, int G, int B) {
      double[] result = new double[3];

      // convert 0..255 into 0..1
      double r = R / 255.0;
      double g = G / 255.0;
      double b = B / 255.0;

      // assume sRGB
      if (r <= 0.04045) {
        r = r / 12.92;
      }
      else {
        r = Math.pow(((r + 0.055) / 1.055), 2.4);
      }
      if (g <= 0.04045) {
        g = g / 12.92;
      }
      else {
        g = Math.pow(((g + 0.055) / 1.055), 2.4);
      }
      if (b <= 0.04045) {
        b = b / 12.92;
      }
      else {
        b = Math.pow(((b + 0.055) / 1.055), 2.4);
      }

      r *= 100.0;
      g *= 100.0;
      b *= 100.0;

      // [X Y Z] = [r g b][M]
      result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
      result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
      result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

      return result;
    }

    /**
     * Convert RGB to XYZ
     * @param RGB
     * @return XYZ in double array.
     */
    public double[] RGBtoXYZ(int[] RGB) {
      return RGBtoXYZ(RGB[0], RGB[1], RGB[2]);
    }

    /**
     * @param x
     * @param y
     * @param Y
     * @return XYZ values
     */
    public double[] xyYtoXYZ(double x, double y, double Y) {
      double[] result = new double[3];
      if (y == 0) {
        result[0] = 0;
        result[1] = 0;
        result[2] = 0;
      }
      else {
        result[0] = (x * Y) / y;
        result[1] = Y;
        result[2] = ((1 - x - y) * Y) / y;
      }
      return result;
    }

    /**
     * @param xyY
     * @return XYZ values
     */
    public double[] xyYtoXYZ(double[] xyY) {
      return xyYtoXYZ(xyY[0], xyY[1], xyY[2]);
    }

    /**
     * Convert XYZ to LAB.
     * @param X
     * @param Y
     * @param Z
     * @return Lab values
     */
    public double[] XYZtoLAB(double X, double Y, double Z) {

      double x = X / whitePoint[0];
      double y = Y / whitePoint[1];
      double z = Z / whitePoint[2];

      if (x > 0.008856) {
        x = Math.pow(x, 1.0 / 3.0);
      }
      else {
        x = (7.787 * x) + (16.0 / 116.0);
      }
      if (y > 0.008856) {
        y = Math.pow(y, 1.0 / 3.0);
      }
      else {
        y = (7.787 * y) + (16.0 / 116.0);
      }
      if (z > 0.008856) {
        z = Math.pow(z, 1.0 / 3.0);
      }
      else {
        z = (7.787 * z) + (16.0 / 116.0);
      }

      double[] result = new double[3];

      result[0] = (116.0 * y) - 16.0;
      result[1] = 500.0 * (x - y);
      result[2] = 200.0 * (y - z);

      return result;
    }

    /**
     * Convert XYZ to LAB.
     * @param XYZ
     * @return Lab values
     */
    public double[] XYZtoLAB(double[] XYZ) {
      return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
    }

    /**
     * Convert XYZ to RGB.
     * @param X
     * @param Y
     * @param Z
     * @return RGB in int array.
     */
    public int[] XYZtoRGB(double X, double Y, double Z) {
      int[] result = new int[3];

      double x = X / 100.0;
      double y = Y / 100.0;
      double z = Z / 100.0;

      // [r g b] = [X Y Z][Mi]
      double r = (x * Mi[0][0]) + (y * Mi[0][1]) + (z * Mi[0][2]);
      double g = (x * Mi[1][0]) + (y * Mi[1][1]) + (z * Mi[1][2]);
      double b = (x * Mi[2][0]) + (y * Mi[2][1]) + (z * Mi[2][2]);

      // assume sRGB
      if (r > 0.0031308) {
        r = ((1.055 * Math.pow(r, 1.0 / 2.4)) - 0.055);
      }
      else {
        r = (r * 12.92);
      }
      if (g > 0.0031308) {
        g = ((1.055 * Math.pow(g, 1.0 / 2.4)) - 0.055);
      }
      else {
        g = (g * 12.92);
      }
      if (b > 0.0031308) {
        b = ((1.055 * Math.pow(b, 1.0 / 2.4)) - 0.055);
      }
      else {
        b = (b * 12.92);
      }

      r = (r < 0) ? 0 : r;
      g = (g < 0) ? 0 : g;
      b = (b < 0) ? 0 : b;

      // convert 0..1 into 0..255
      result[0] = (int) Math.round(r * 255);
      result[1] = (int) Math.round(g * 255);
      result[2] = (int) Math.round(b * 255);

      return result;
    }

    /**
     * Convert XYZ to RGB
     * @param XYZ in a double array.
     * @return RGB in int array.
     */
    public int[] XYZtoRGB(double[] XYZ) {
      return XYZtoRGB(XYZ[0], XYZ[1], XYZ[2]);
    }

    /**
     * @param X
     * @param Y
     * @param Z
     * @return xyY values
     */
    public double[] XYZtoxyY(double X, double Y, double Z) {
      double[] result = new double[3];
      if ((X + Y + Z) == 0) {
        result[0] = chromaWhitePoint[0];
        result[1] = chromaWhitePoint[1];
        result[2] = chromaWhitePoint[2];
      }
      else {
        result[0] = X / (X + Y + Z);
        result[1] = Y / (X + Y + Z);
        result[2] = Y;
      }
      return result;
    }

    /**
     * @param XYZ
     * @return xyY values
     */
    public double[] XYZtoxyY(double[] XYZ) {
      return XYZtoxyY(XYZ[0], XYZ[1], XYZ[2]);
    }
    
    public double[] RGBtoLCH(int[] RGB) {
        
        return RGBtoLCH(RGB[0], RGB[1], RGB[2]);
  
    }
    
    /*
     * D65
     * L : 0, 100
     * C : 0, 133.81
     * H : 0, 360
     */
    public double[] RGBtoLCH(int R, int G, int B) {
        
        double[] temp = RGBtoLAB(R, G, B);
        
        double L = temp[0];
        double a = temp[1];
        double b = temp[2];
        
        double h = Math.atan2(b, a);
        // convert from radians to degrees
        if (h > 0) {
            h = (h / Math.PI) * 180.0;
        }
        else {
            h = 360 - (Math.abs(h) / Math.PI) * 180.0;
        }
        if (h < 0) {
             h += 360.0;
        }
        else if (h >= 360) {
            h -= 360.0;
        }
        
        double[] result = new double[3];
        
        result[0] = L;
        result[1] = Math.sqrt(a * a + b * b);
        result[2] = h;
        
        return result;

    }
    
    public int[] LCHtoRGB(double[] LCH) {
        
        return LCHtoRGB(LCH[0], LCH[1], LCH[2]);
  
    }
    
    public int[] LCHtoRGB(double L, double C, double H) {
        
        double hRadians = H * Math.PI / 180.0;
        
        double a = Math.cos(hRadians) * C;
        double b = Math.sin(hRadians) * C;

        return LABtoRGB(L, a, b);
    }
    
    public double[] RGBtoRYB(int[] RGB) {
        
        return RGBtoRYB(RGB[0], RGB[1], RGB[2]);
        
    }
    /**
    * Convert a red-green-blue system to a red-yellow-blue system.
     * R : 0, 1
     * Y : 0, 1
     * B : 0, 1
    */
    public double[] RGBtoRYB(int R, int G, int B) {
        
        double r = R / 255.0, g = G / 255.0, b = B / 255.0;
        // Remove the whiteness from the color.
        double w = Math.min(r, g);
        w = Math.min(w, b);
        r -= w;
        g -= w;
        b -= w;
        double mg = Math.max(r, g);
        mg = Math.max(mg, b);
        // Get the yellow out of the red+green.
        double y = Math.min(r, g);
        r -= y;
        g -= y;
        // If this unfortunate conversion combines blue and green, then cut each in
        // half to preserve the value's maximum range.
        if (b > 0 && g > 0) {
            b /= 2.0;
            g /= 2.0;
        }
        // Redistribute the remaining green.
        y += g;
        b += g;
        // Normalize to values.
        double my = Math.max(r, y);
        my = Math.max(my, b);
        if (my > 0) {
            double n = mg / my;
            r *= n;
            y *= n;
            b *= n;
        }
        // Add the white back in.
        r += w;
        y += w;
        b += w;
        // And return back the ryb typed accordingly.

        double[] result = new double[3];
        result[0] = r;
        result[1] = y;
        result[2] = b;
        
        return result;
    }
    
    public int[] RYBtoRGB(double[] RYB) {
        
        return RYBtoRGB(RYB[0], RYB[1], RYB[2]);
        
    }
    
    /**
    * Convert a red-yellow-blue system to a red-green-blue system.
    */
    public int[] RYBtoRGB(double r, double y, double b) {

        // Remove the whiteness from the color.
        double w = Math.min(r, y);
        w = Math.min(w, b);
        r -= w;
        y -= w;
        b -= w;
        double my = Math.max(r, y);
        my = Math.max(my, b);
        // Get the green out of the yellow and blue
        double g = Math.min(y, b);
        y -= g;
        b -= g;
        if (b > 0 && g > 0) {
            b *= 2.0;
            g *= 2.0;
        }
        // Redistribute the remaining yellow.
        r += y;
        g += y;
        // Normalize to values.
        double mg = Math.max(r, g);
        mg = Math.max(mg, b);
        if (mg > 0) {
            double n = my / mg;
            r *= n;
            g *= n;
            b *= n;
        }
        // Add the white back in.
        r += w;
        g += w;
        b += w;
        
        if(r > 1.0f) {
            r = 1;
        }
        else if(r < 0) {
            r = 0;
        }
        
        if(g > 1.0f) {
            g = 1;
        }
        else if(g < 0) {
            g = 0;
        }
        
        if(b > 1.0f) {
            b = 1;
        }
        else if(b < 0) {
            b = 0;
        }
        
        r *= 255;
        g *= 255;
        b *= 255;
        // And return back the ryb typed accordingly.
        int[] result = new int[3];
        result[0] = (int)r;
        result[1] = (int)g;
        result[2] = (int)b;
        
        return result;
    } 
    
    public static int clamp(int c) {
        
        if(c < 0) {
            return 0;
        }
	if(c > 255) {
            return 255;
        }
	return c;
                
    }

  }

