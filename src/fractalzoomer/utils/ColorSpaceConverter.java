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

package fractalzoomer.utils;

import java.awt.*;
import java.util.ArrayList;

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
      public static final int RGB = 0;
      public static final int XYZ = 1;
      public static final int HSB = 2;
      public static final int HSL = 3;
      public static final int RYB = 4;
      public static final int LAB = 5;
      public static final int LCH_ab = 6;
      public static final int PALETTE = 7;
      public static final int GRADIENT = 8;
      public static final int DIRECT = 9;
      public static final int HWB = 10;
      /**
       * reference white in XYZ coordinates
       */
      public static int whitePointId = 2;
      private static double[] D50 = {96.4212, 100.0, 82.5188};
      private static double[] D55 = {95.6797, 100.0, 92.1481};
      private static double[] D65 = {95.0429, 100.0, 108.8900};
      private static double[] D75 = {94.9722, 100.0, 122.6394};
      private static double[] whitePoint = D65;

      //For whitepoint

      /*
      Name	    CIE 1931 2°	    CIE 1964 10° CCT (K)	Color	Note
                x2°	    y2°	    x10°    y10°
        A	0.44757	0.40745	0.45117	0.40594	2856		incandescent / tungsten
        B	0.34842	0.35161	0.34980	0.35270	4874		obsolete, direct sunlight at noon
        C	0.31006	0.31616	0.31039	0.31905	6774		obsolete, average / North sky daylight / NTSC 1953[28], PAL-M[29]
        D50	0.34567	0.35850	0.34773	0.35952	5003		horizon light, ICC profile PCS
        D55	0.33242	0.34743	0.33411	0.34877	5503		mid-morning / mid-afternoon daylight
        D65	0.31271	0.32902	0.31382	0.33100	6504		noon daylight: television, sRGB color space
        D75	0.29902	0.31485	0.29968	0.31740	7504		North sky daylight
        D93	0.28315	0.29711	0.28327	0.30043	9305		high-efficiency blue phosphor monitors, BT.2035
        E	0.33333	0.33333	0.33333	0.33333	5454		equal energy
        F1	0.31310	0.33727	0.31811	0.33559	6430		daylight fluorescent
        F2	0.37208	0.37529	0.37925	0.36733	4230		cool white fluorescent
        F3	0.40910	0.39430	0.41761	0.38324	3450		white fluorescent
        F4	0.44018	0.40329	0.44920	0.39074	2940		warm white fluorescent
        F5	0.31379	0.34531	0.31975	0.34246	6350		daylight fluorescent
        F6	0.37790	0.38835	0.38660	0.37847	4150		light white fluorescent
        F7	0.31292	0.32933	0.31569	0.32960	6500		D65 simulator, daylight simulator
        F8	0.34588	0.35875	0.34902	0.35939	5000		D50 simulator, Sylvania F40 Design 50
        F9	0.37417	0.37281	0.37829	0.37045	4150		cool white deluxe fluorescent
        F10	0.34609	0.35986	0.35090	0.35444	5000		Philips TL85, Ultralume 50
        F11	0.38052	0.37713	0.38541	0.37123	4000		Philips TL84, Ultralume 40
        F12	0.43695	0.40441	0.44256	0.39717	3000		Philips TL83, Ultralume 30
        LED-B1	0.4560	0.4078			2733		phosphor-converted blue
        LED-B2	0.4357	0.4012			2998		phosphor-converted blue
        LED-B3	0.3756	0.3723			4103		phosphor-converted blue
        LED-B4	0.3422	0.3502			5109		phosphor-converted blue
        LED-B5	0.3118	0.3236			6598		phosphor-converted blue
        LED-BH1	0.4474	0.4066			2851		mixing of phosphor-converted blue LED and red LED (blue-hybrid)
        LED-RGB1	0.4557	0.4211			2840		mixing of red, green, and blue LEDs
        LED-V1	0.4560	0.4548			2724		phosphor-converted violet
        LED-V2	0.3781	0.3775			4070		phosphor-converted violet
       */
      static double[] xytoXYZ(double x, double y) {
          if (y == 0) {
              return new double[] {0, 0, 0};
          }
          double z = 1.0 - x - y;
          double Y = 100;
          double X = (Y / y) * x;
          double Z = (Y / y) * z;
          return new double[] {X, Y, Z};
      }

      public static void init() {
          if(whitePointId == 0) {
              whitePoint = D50;
          }
          else if(whitePointId == 1) {
              whitePoint = D55;
          } else if (whitePointId == 2) {
              whitePoint = D65;
          }
          else {
              whitePoint = D75;
          }
      }
      

    /**
     * reference white in xyY coordinates
     */
    private static double[] chromaD50 = {0.3457, 0.3585, 100.0};
    private static double[] chromaD55 = {0.3324, 0.3474, 100.0};
    private static double[] chromaD65 = {0.3127, 0.3290, 100.0};
    private static double[] chromaD75 = {0.2990, 0.3149, 100.0};
    private static double[] chromaWhitePoint = chromaD65;

    /**
     * sRGB to XYZ conversion matrix
     */
    private static double[][] M   = {{0.4124, 0.3576,  0.1805},
                             {0.2126, 0.7152,  0.0722},
                             {0.0193, 0.1192,  0.9505}};

    /**
     * XYZ to sRGB conversion matrix
     */
    private static double[][] Mi  = {{ 3.2406, -1.5372, -0.4986},
                             {-0.9689,  1.8758,  0.0415},
                             { 0.0557, -0.2040,  1.0570}};


    /**
     * @param H Hue angle/360 (0..1)
     * @param S Saturation (0..1)
     * @param B Value (0..1)
     * @return RGB values
     */
    public static int[] HSBtoRGB(double H, double S, double B) {
      int[] result = new int[3];
      int rgb = Color.HSBtoRGB((float) H, (float) S, (float) B);
      result[0] = (rgb >> 16) & 0xff;
      result[1] = (rgb >> 8) & 0xff;
      result[2] = rgb & 0xff;
      return result;
    }
    
    public static int[] HSLtoRGB(double H, double S, double L) {
      
        H = H * 360;
        
        double C = (1 -  Math.abs(2 * L  - 1)) * S;
        
        double Hprime =  H / 60;
        
        double X =  C * (1 - Math.abs((Hprime % 2.0) - 1));
        
        double R1 = 0, G1 = 0, B1 = 0;
        
        if(Hprime >= 0 && Hprime <= 1) {
            R1 = C;
            G1 = X;
            B1 = 0;
        }
        else if(Hprime >= 1 && Hprime <= 2) {
            R1 = X;
            G1 = C;
            B1 = 0;
        }
        else if(Hprime >= 2 && Hprime <= 3) {
            R1 = 0;
            G1 = C;
            B1 = X;
        }
        else if(Hprime >= 3 && Hprime <= 4) {
            R1 = 0;
            G1 = X;
            B1 = C;
        }
        else if(Hprime >= 4 && Hprime <= 5) {
            R1 = X;
            G1 = 0;
            B1 = C;
        }
        else if(Hprime >= 5 && Hprime <= 6) {
            R1 = C;
            G1 = 0;
            B1 = X;
        }
        
        double m = L - C / 2.0;
        
        return new int[] {(int)((R1 + m) * 255 + 0.5), (int)((G1 + m) * 255 + 0.5), (int)((B1 + m) * 255 + 0.5)};
        
    }

    public static int[] HSBtoRGB(double[] HSB) {
      return HSBtoRGB(HSB[0], HSB[1], HSB[2]);
    }
    
    public static int[] HSLtoRGB(double[] HSL) {
      return HSLtoRGB(HSL[0], HSL[1], HSL[2]);
    }

    /**
     * Convert LAB to RGB.
     * @param L
     * @param a
     * @param b
     * @return RGB values
     */
    public static int[] LABtoRGB(double L, double a, double b) {
      return XYZtoRGB(LABtoXYZ(L, a, b));
    }

      public static int[] JzAzBztoRGB(double Jz, double az, double bz) {
          return XYZtoRGB(JzAzBztoXYZ(Jz, az, bz));
      }

      public static int[] JzAzBztoRGB(double[] JzAzBz) {
          return XYZtoRGB(JzAzBztoXYZ(JzAzBz[0], JzAzBz[1], JzAzBz[2]));
      }

      /**
       * Convert OKLAB to RGB.
       * @param L
       * @param a
       * @param b
       * @return RGB values
       */
      public static int[] OKLABtoRGB(double L, double a, double b) {
          return LinearRGBtoRGB(OKLABtoLinearRGB(L, a, b));
      }

      public static int[] OKLABtoRGB(double[] Lab) {
          return LinearRGBtoRGB(OKLABtoLinearRGB(Lab));
      }

      /**
       * Convert LUV to RGB.
       * @param L
       * @param u
       * @param v
       * @return RGB values
       */
      public static int[] LUVtoRGB(double L, double u, double v) {
          return XYZtoRGB(LUVtoXYZ(L, u, v));
      }

    /**
     * @param Lab
     * @return RGB values
     */
    public static int[] LABtoRGB(double[] Lab) {
      return XYZtoRGB(LABtoXYZ(Lab));
    }

      /**
       * @param Luv
       * @return RGB values
       */
      public static int[] LUVtoRGB(double[] Luv) {
          return XYZtoRGB(LUVtoXYZ(Luv));
      }

    /**
     * Convert LAB to XYZ.
     * @param L
     * @param a
     * @param b
     * @return XYZ values
     */
    public static double[] LABtoXYZ(double L, double a, double b) {
      double[] result = new double[3];

      double y = (L + 16.0) / 116.0;
      double y3 = y * y * y;
      double x = (a / 500.0) + y;
      double x3 = x * x * x;
      double z = y - (b / 200.0);
      double z3 = z * z * z;

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

    private static double PQ_inv(double X) {
        double XX = Math.pow(X, 7.460772656268214e-03);
        return 1e4 * Math.pow(
                (0.8359375 - XX) / (18.6875*XX - 18.8515625),
                6.277394636015326);
    }


      public static double[] JzAzBztoXYZ(double Jz, double az, double bz) {
          Jz = Jz + 1.6295499532821566e-11;

          double Iz = Jz / (0.44 + 0.56*Jz),
                  L = PQ_inv(Iz + 1.386050432715393e-1*az + 5.804731615611869e-2*bz),
                  M = PQ_inv(Iz - 1.386050432715393e-1*az - 5.804731615611891e-2*bz),
                  S = PQ_inv(Iz - 9.601924202631895e-2*az - 8.118918960560390e-1*bz);
          return new double[] {
          + 100 * (1.661373055774069e+00*L - 9.145230923250668e-01*M + 2.313620767186147e-01*S),
                  100 * (- 3.250758740427037e-01*L + 1.571847038366936e+00*M - 2.182538318672940e-01*S),
                  100 * (- 9.098281098284756e-02*L - 3.127282905230740e-01*M + 1.522766561305260e+00*S)};
      }

      public static double[] OKLABtoLinearRGB(double L, double a, double b) {


          double l_ = L + 0.3963377774 * a + 0.2158037573 * b;
          double m_ = L - 0.1055613458 * a - 0.0638541728 * b;
          double s_ = L - 0.0894841775 * a - 1.2914855480 * b;

          double l = l_*l_*l_;
          double m = m_*m_*m_;
          double s = s_*s_*s_;

          return new double[] {
                  (+4.0767416621 * l - 3.3077115913 * m + 0.2309699292 * s),
                  (-1.2684380046 * l + 2.6097574011 * m - 0.3413193965 * s),
                  (-0.0041960863 * l - 0.7034186147 * m + 1.7076147010 * s),
          };

      }

      /**
       * Convert LUV to XYZ.
       * @param L
       * @param a
       * @param b
       * @return XYZ values
       */
      public static double[] LUVtoXYZ(double L, double u, double v) {

          if(L == 0 && u == 0 && v == 0) {
              return new double[] {0, 0, 0};
          }

          double recipn = 1.0 / (whitePoint[0] + 15 * whitePoint[1] + 3 * whitePoint[2]);
          double undot = (4 * whitePoint[0]) * recipn;
          double vndot = (9 * whitePoint[1]) * recipn;

          double thirteenLRec = 1.0 / (13 * L);
          double udot = u * thirteenLRec + undot;
          double vdot = v * thirteenLRec + vndot;

          double Y;
          if(L <= 8) {
              double temp = 3.0 / 29.0;
              Y = whitePoint[1] * L * temp * temp * temp;
          }
          else {
              double temp = (L + 16) / 116.0;
             Y =  whitePoint[1] * temp * temp * temp;
          }

          double fourVdot = 4 * vdot;
          double X = Y * (9 * udot) / fourVdot;
          double Z = Y * (12 - 3 * udot - 20 * vdot) / fourVdot;

          return new double[] {X, Y, Z};

      }

    /**
     * Convert LAB to XYZ.
     * @param Lab
     * @return XYZ values
     */
    public static double[] LABtoXYZ(double[] Lab) {
      return LABtoXYZ(Lab[0], Lab[1], Lab[2]);
    }

      public static double[] OKLABtoLinearRGB(double[] Lab) {
          return OKLABtoLinearRGB(Lab[0], Lab[1], Lab[2]);
      }

      /**
       * Convert LUV to XYZ.
       * @param Luv
       * @return XYZ values
       */
      public static double[] LUVtoXYZ(double[] Luv) {
          return LUVtoXYZ(Luv[0], Luv[1], Luv[2]);
      }

    /**
     * @param R Red in range 0..255
     * @param G Green in range 0..255
     * @param B Blue in range 0..255
     * @return HSB values: H is 0..360 degrees / 360 (0..1), S is 0..1, B is 0..1
     */
    public static double[] RGBtoHSB(int R, int G, int B) {
      double[] result = new double[3];
      float[] hsb = new float[3];
      Color.RGBtoHSB(R, G, B, hsb);
      result[0] = hsb[0];
      result[1] = hsb[1];
      result[2] = hsb[2];
      return result;
    }
    
    /**
     * @param R Red in range 0..255
     * @param G Green in range 0..255
     * @param B Blue in range 0..255
     * @return HSL values: H is 0..360 degrees / 360 (0..1), S is 0..1, L is 0..1
     */
    public static double[] RGBtoHSL(int R, int G, int B) {

        double rec = 1.0 / 255.0;
        double r = R * rec;
        double g = G * rec;
        double b = B * rec;
        
        double MAX = Math.max(Math.max(r, g), b);
        double MIN = Math.min(Math.min(r, g), b);
        
        double H = 0;
        if(MAX == MIN) {
            H = 0;
        }
        else if(MAX == r) {
            H = 60 * ((g - b) / (MAX - MIN));
        }
        else if(MAX == g) {
            H = 60 * (2 + (b - r) / (MAX - MIN));
        }
        else {//if(MAX == b) {
            H = 60 * (4 + (r - g) / (MAX - MIN));
        }
        
        H = H < 0 ? H + 360 : H;
        
        double S = 0;
        
        if(MAX == 0) {
            S = 0;
        }
        else if(MIN == 1) {
            S = 0;
        }
        else {
            S = (MAX - MIN) / (1 - Math.abs(MAX + MIN - 1));
        }
        
        double L = (MIN + MAX) * 0.5;

        return new double[] {H / 360.0, S, L};
    }

    public static double[] RGBtoHSB(int[] RGB) {
      return RGBtoHSB(RGB[0], RGB[1], RGB[2]);
    }
    
    public static double[] RGBtoHSL(int[] RGB) {
      return RGBtoHSL(RGB[0], RGB[1], RGB[2]);
    }

    /**
     * @param R
     * @param G
     * @param B
     * @return Lab values
     */
    public static double[] RGBtoLAB(int R, int G, int B) {
      return XYZtoLAB(RGBtoXYZ(R, G, B));
    }

      public static double[] RGBtoJzAzBz(int R, int G, int B) {
          return XYZtoJzAzBz(RGBtoXYZ(R, G, B));
      }

      /**
       * @param R
       * @param G
       * @param B
       * @return Lab values
       */
      public static double[] RGBtoOKLAB(int R, int G, int B) {
          return LinearRGBtoOKLAB(RGBtoLinearRGB(R, G, B));
      }

      /**
       * @param R
       * @param G
       * @param B
       * @return Luv values
       */
      public static double[] RGBtoLUV(int R, int G, int B) {
          return XYZtoLUV(RGBtoXYZ(R, G, B));
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
    public static double[] RGBtoLAB(int[] RGB) {
      return XYZtoLAB(RGBtoXYZ(RGB));
    }

      public static double[] RGBtoOKLAB(int[] RGB) {
          return LinearRGBtoOKLAB(RGBtoLinearRGB(RGB));
      }

    /*
        Min L= 0.0 U= -83.06803370763052 V= -134.11398769549862
        Max L= 100.0 U= 175.0601455599254 V= 107.40606240895833
     */
    public static double[] RGBtoLUV(int[] RGB) {
          return XYZtoLUV(RGBtoXYZ(RGB));
      }

      public static int[] HSL_uvtoRGB(double[] HSL) {
          return HSL_uvtoRGB(HSL[0], HSL[1], HSL[2]);
      }

      public static int[] HPL_uvtoRGB(double[] HPL) {
          return HPL_uvtoRGB(HPL[0], HPL[1], HPL[2]);
      }

      public static double[] HSL_uvtoLCH_uv(double[] HSL) {
            return HSL_uvtoLCH_uv(HSL[0], HSL[1], HSL[2]);
      }

      public static double[] LCH_uvtoHSL_uv(double[] LCH) {
            return LCH_uvtoHSL_uv(LCH[0], LCH[1], LCH[2]);
      }

      public static double[] LCH_uvtoHPL_uv(double[] LCH) {
          return LCH_uvtoHPL_uv(LCH[0], LCH[1], LCH[2]);
      }
      public static double[] HSL_uvtoLCH_uv(double H, double S, double L) {

          if (L > 99.9999999) {
              return new double[]{100d, 0, H};
          }

          if (L < 0.00000001) {
              return new double[]{0, 0, H};
          }

          double max = maxChromaForLH(L, H);
          double C = max / 100 * S;

          return new double[]{L, C, H};
      }

      public static double[] HPL_uvtoLCH_uv(double H, double P, double L) {

          if (L > 99.9999999) {
              return new double[]{100, 0, H};
          }

          if (L < 0.00000001) {
              return new double[]{0, 0, H};
          }

          double max = maxSafeChromaForL(L);
          double C = max / 100 * P;

          return new double[]{L, C, H};
      }

      private static class Length {
          final boolean greaterEqualZero;
          final double length;


          private Length(double length) {
              this.greaterEqualZero = length >= 0;
              this.length = length;
          }
      }
      private static double kappa = 903.2962962;
      private static double epsilon = 0.0088564516;

      private static double[][] m = new double[][]
              {
                      new double[]{3.240969941904521, -1.537383177570093, -0.498610760293},
                      new double[]{-0.96924363628087, 1.87596750150772, 0.041555057407175},
                      new double[]{0.055630079696993, -0.20397695888897, 1.056971514242878},
              };
      private static ArrayList<double[]> getBounds(double L) {
          ArrayList<double[]> result = new ArrayList<>();

          double sub1 = Math.pow(L + 16, 3) / 1560896;
          double sub2 = sub1 > epsilon ? sub1 : L / kappa;

          for (int c = 0; c < 3; ++c) {
              double m1 = m[c][0];
              double m2 = m[c][1];
              double m3 = m[c][2];

              for (int t = 0; t < 2; ++t) {
                  double top1 = (284517 * m1 - 94839 * m3) * sub2;
                  double top2 = (838422 * m3 + 769860 * m2 + 731718 * m1) * L * sub2 - 769860 * t * L;
                  double bottom = (632260 * m3 - 126452 * m2) * sub2 + 126452 * t;

                  result.add(new double[]{top1 / bottom, top2 / bottom});
              }
          }

          return result;
      }

      private static Length lengthOfRayUntilIntersect(double theta, double[] line) {
          double length = line[1] / (Math.sin(theta) - line[0] * Math.cos(theta));

          return new Length(length);
      }
      private static double maxChromaForLH(double L, double H) {
          double hrad = H / 360 * Math.PI * 2;

          ArrayList<double[]> bounds = getBounds(L);
          double min = Double.MAX_VALUE;

          for (double[] bound : bounds) {
              Length length = lengthOfRayUntilIntersect(hrad, bound);
              if (length.greaterEqualZero) {
                  min = Math.min(min, length.length);
              }
          }

          return min;
      }
      public static double[] LCH_uvtoHSL_uv(double L, double C, double H) {

          if (L > 99.9999999) {
              return new double[]{H, 0, 100};
          }

          if (L < 0.00000001) {
              return new double[]{H, 0, 0};
          }

          double max = maxChromaForLH(L, H);
          double S = C / max * 100;

          return new double[]{H, S, L};

      }

      private static double intersectLineLine(double[] lineA, double[] lineB) {
          return (lineA[1] - lineB[1]) / (lineB[0] - lineA[0]);
      }

      private static double distanceFromPole(double[] point) {
          return Math.sqrt(Math.pow(point[0], 2) + Math.pow(point[1], 2));
      }

      private static double maxSafeChromaForL(double L) {
          ArrayList<double[]> bounds = getBounds(L);
          double min = Double.MAX_VALUE;

          for (int i = 0; i < 2; ++i) {
              double m1 = bounds.get(i)[0];
              double b1 = bounds.get(i)[1];
              double[] line = new double[]{m1, b1};

              double x = intersectLineLine(line, new double[]{-1 / m1, 0});
              double length = distanceFromPole(new double[]{x, b1 + x * m1});

              min = Math.min(min, length);
          }

          return min;
      }

      public static double[] LCH_uvtoHPL_uv(double L, double C, double H) {

          if (L > 99.9999999) {
              return new double[]{H, 0, 100};
          }

          if (L < 0.00000001) {
              return new double[]{H, 0, 0};
          }

          double max = maxSafeChromaForL(L);
          double P = C / max * 100;

          return new double[]{H, P, L};

      }

      public static int[] HSL_uvtoRGB(double H, double S, double L) {
          return LCH_uvtoRGB(HSL_uvtoLCH_uv(H, S, L));
      }

      public static int[] HPL_uvtoRGB(double H, double P, double L) {
          return LCH_uvtoRGB(HPL_uvtoLCH_uv(H, P, L));
      }

      public static double[] RGBtoHSL_uv(int R, int G, int B) {
          return LCH_uvtoHSL_uv(RGBtoLCH_uv(R, G, B));
      }

      public static double[] RGBtoHSL_uv(int[] RGB) {
         return RGBtoHSL_uv(RGB[0], RGB[1], RGB[2]);
      }

      public static double[] RGBtoHPL_uv(int R, int G, int B) {
          return LCH_uvtoHPL_uv(RGBtoLCH_uv(R, G, B));
      }

      public static double[] RGBtoHPL_uv(int[] RGB) {
          return RGBtoHPL_uv(RGB[0], RGB[1], RGB[2]);
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

    public static double Clinear(double x) {
        if (x <= 0.04045) {
            x = x / 12.92;
        }
        else {
            x = Math.pow(((x + 0.055) / 1.055), 2.4);
        }
        return x;
    }

    public static double[] RGBtoLinearRGB(int[] RGB) {
        return RGBtoLinearRGB(RGB[0], RGB[1], RGB[2]);
    }

    public static double[] RGBtoLinearRGB(int R, int G, int B) {
        double rec = 1.0 / 255.0;
        double r = R * rec;
        double g = G * rec;
        double b = B * rec;

        // assume sRGB
        r = Clinear(r);
        g = Clinear(g);
        b = Clinear(b);

        return new double[] {r, g, b};
    }

    public static int[] LinearRGBtoRGB(double[] RGB) {
        return LinearRGBtoRGB(RGB[0], RGB[1], RGB[2]);
    }

    public static int[] LinearRGBtoRGB(double r, double g, double b) {

        int[] result = new int[3];

        r = Clinear_inv(r);
        g = Clinear_inv(g);
        b = Clinear_inv(b);

        r = (r < 0) ? 0 : r;
        g = (g < 0) ? 0 : g;
        b = (b < 0) ? 0 : b;

        // convert 0..1 into 0..255
        result[0] = (int) (r * 255 + 0.5 );
        result[1] = (int) (g * 255 + 0.5);
        result[2] = (int) (b * 255 + 0.5);

        result[0] = (result[0] > 255) ? 255 : result[0];
        result[1] = (result[1]  > 255) ? 255 : result[1];
        result[2] = (result[2]  > 255) ? 255 : result[2];

        return result;

    }

    public static double[] RGBtoXYZ(int R, int G, int B) {
      double[] result = new double[3];

      // convert 0..255 into 0..1
      double rec = 1.0 / 255.0;
      double r = R * rec;
      double g = G * rec;
      double b = B * rec;

      // assume sRGB
      r = Clinear(r);
      g = Clinear(g);
      b = Clinear(b);

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
    public static double[] RGBtoXYZ(int[] RGB) {
      return RGBtoXYZ(RGB[0], RGB[1], RGB[2]);
    }

    /**
     * @param x
     * @param y
     * @param Y
     * @return XYZ values
     */
    public static double[] xyYtoXYZ(double x, double y, double Y) {
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
    public static double[] xyYtoXYZ(double[] xyY) {
      return xyYtoXYZ(xyY[0], xyY[1], xyY[2]);
    }

    /**
     * Convert XYZ to LAB.
     * @param X
     * @param Y
     * @param Z
     * @return Lab values
     */
    public static double[] XYZtoLAB(double X, double Y, double Z) {

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


    private static double PQ(double X) {
        double XX = Math.pow(X*1e-4, 0.1593017578125);
          return Math.pow(
                  (0.8359375 + 18.8515625*XX) / (1 + 18.6875*XX),
                  134.034375);
    }

      public static double[] XYZtoJzAzBz(double X, double Y, double Z) {


          double rec = 1 / 100.0;
          X = X * rec;
          Y = Y * rec;
          Z = Z * rec;

          double Lp = PQ(0.674207838*X + 0.382799340*Y - 0.047570458*Z),
                  Mp = PQ(0.149284160*X + 0.739628340*Y + 0.083327300*Z),
                  Sp = PQ(0.070941080*X + 0.174768000*Y + 0.670970020*Z),
                  Iz = 0.5 * (Lp + Mp),
                  az = 3.524000*Lp - 4.066708*Mp + 0.542708*Sp,
                  bz = 0.199076*Lp + 1.096799*Mp - 1.295875*Sp,
                  Jz = (0.44 * Iz) / (1 - 0.56*Iz) - 1.6295499532821566e-11;
          return new double[] {Jz, az, bz};

      }

      public static double[] LinearRGBtoOKLAB(double R, double G, double B) {

          double l = 0.4122214708 * R + 0.5363325363 * G + 0.0514459929 * B;
          double m = 0.2119034982 * R + 0.6806995451 * G + 0.1073969566 * B;
          double s = 0.0883024619 * R + 0.2817188376 * G + 0.6299787005 * B;

          double l_ = Math.cbrt(l);
          double m_ = Math.cbrt(m);
          double s_ = Math.cbrt(s);

          return new double[] {
                  0.2104542553*l_ + 0.7936177850*m_ - 0.0040720468*s_,
                  1.9779984951*l_ - 2.4285922050*m_ + 0.4505937099*s_,
                  0.0259040371*l_ + 0.7827717662*m_ - 0.8086757660*s_,
          };
      }

    /**
     * Convert XYZ to LAB.
     * @param XYZ
     * @return Lab values
     */
    public static double[] XYZtoLAB(double[] XYZ) {
      return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
    }

      public static double[] XYZtoJzAzBz(double[] XYZ) {
          return XYZtoJzAzBz(XYZ[0], XYZ[1], XYZ[2]);
      }

      public static double[] LinearRGBtoOKLAB(double[] RGB) {
          return LinearRGBtoOKLAB(RGB[0], RGB[1], RGB[2]);
      }

      /**
       * Convert XYZ to LUV.
       * @param XYZ
       * @return Lab values
       */
      public static double[] XYZtoLUV(double[] XYZ) {
          return XYZtoLUV(XYZ[0], XYZ[1], XYZ[2]);
      }

      /**
       * Convert XYZ to LUV.
       * @param X
       * @param Y
       * @param Z
       * @return Lab values
       */
      public static double[] XYZtoLUV(double X, double Y, double Z) {

          if(X == 0 && Y == 0 && Z == 0) {
              return new double[] {0, 0, 0};
          }

          double recip = 1.0 / (X + 15 * Y + 3 * Z);
          double udot = (4 * X) * recip;
          double vdot = (9 * Y) * recip;

          double recipn = 1.0 / (whitePoint[0] + 15 * whitePoint[1] + 3 * whitePoint[2]);
          double undot = (4 * whitePoint[0]) * recipn;
          double vndot = (9 * whitePoint[1]) * recipn;

          double ydivyn = Y / whitePoint[1];
          double temp = 6.0/29.0;
          double val = temp * temp * temp;

          double L;

          if(ydivyn <= val) {
              temp = 29.0 / 3.0;
              L = temp * temp * temp * ydivyn;
          }
          else {
              L = 116 * Math.pow(ydivyn, 1.0 / 3.0) - 16;
          }

          double thirteenL = 13 * L;
          double u = thirteenL * (udot - undot);
          double v = thirteenL * (vdot - vndot);

          return new double[] {L, u, v};
      }

    /**
     * Convert XYZ to RGB.
     * @param X
     * @param Y
     * @param Z
     * @return RGB in int array.
     */

    public static double Clinear_inv(double x) {

        if (x > 0.0031308) {
            x = ((1.055 * Math.pow(x, 1.0 / 2.4)) - 0.055);
        }
        else {
            x = (x * 12.92);
        }

        return x;

    }
    public static int[] XYZtoRGB(double X, double Y, double Z) {
      int[] result = new int[3];

      double rec = 1.0 / 100.0;
      double x = X * rec;
      double y = Y * rec;
      double z = Z * rec;

      // [r g b] = [X Y Z][Mi]
      double r = (x * Mi[0][0]) + (y * Mi[0][1]) + (z * Mi[0][2]);
      double g = (x * Mi[1][0]) + (y * Mi[1][1]) + (z * Mi[1][2]);
      double b = (x * Mi[2][0]) + (y * Mi[2][1]) + (z * Mi[2][2]);

      // assume sRGB
      r = Clinear_inv(r);
      g = Clinear_inv(g);
      b = Clinear_inv(b);

      r = (r < 0) ? 0 : r;
      g = (g < 0) ? 0 : g;
      b = (b < 0) ? 0 : b;

      // convert 0..1 into 0..255
      result[0] = (int) (r * 255 + 0.5 );
      result[1] = (int) (g * 255 + 0.5);
      result[2] = (int) (b * 255 + 0.5);
     
      result[0] = (result[0] > 255) ? 255 : result[0];
      result[1] = (result[1]  > 255) ? 255 : result[1];
      result[2] = (result[2]  > 255) ? 255 : result[2];
      
      return result;
    }

    /**
     * Convert XYZ to RGB
     * @param XYZ in a double array.
     * @return RGB in int array.
     */
    public static int[] XYZtoRGB(double[] XYZ) {
      return XYZtoRGB(XYZ[0], XYZ[1], XYZ[2]);
    }

    /**
     * @param X
     * @param Y
     * @param Z
     * @return xyY values
     */
    public static double[] XYZtoxyY(double X, double Y, double Z) {
      double[] result = new double[3];
      if ((X + Y + Z) == 0) {
        result[0] = chromaWhitePoint[0];
        result[1] = chromaWhitePoint[1];
        result[2] = chromaWhitePoint[2];
      }
      else {
          double rec = 1.0 / (X + Y + Z);
        result[0] = X * rec;
        result[1] = Y * rec;
        result[2] = Y;
      }
      return result;
    }

    /**
     * @param XYZ
     * @return xyY values
     */
    public static double[] XYZtoxyY(double[] XYZ) {
      return XYZtoxyY(XYZ[0], XYZ[1], XYZ[2]);
    }

    public static double[] RGBtoCMYK(int[] RGB) {
        return RGBtoCMYK(RGB[0], RGB[1], RGB[2]);
    }

      public static int[] CMYKtoRGB(double[] CMYK) {
          return CMYKtoRGB(CMYK[0], CMYK[1], CMYK[2], CMYK[3]);
      }

      public static int[] CMYKtoRGB(double C, double M, double Y, double K) {
          double oneMK = 1 - K;
          int R = (int)(255.0 * (1 - C) * oneMK + 0.5);
          int G = (int)(255.0 * (1 - M) * oneMK + 0.5);
          int B = (int)(255.0 * (1 - Y) * oneMK + 0.5);
          return new int[] {R, G, B};
      }

    public static double[] RGBtoCMYK(int R, int G, int B) {
        double rec = 1.0 / 255.0;
        double rprime = R * rec;
        double gprime = G * rec;
        double bprime = B * rec;

        double K = 1 - Math.max(rprime, Math.max(gprime, bprime));
        double oneMK = 1.0 / (1 - K);
        double C = (1 - rprime - K) * oneMK;
        double M = (1 - gprime - K) * oneMK;
        double Y = (1 - bprime - K) * oneMK;

        return new double[] {C, M, Y, K};
    }
    
    public static double[] RGBtoLCH_ab(int[] RGB) {
        
        return RGBtoLCH_ab(RGB[0], RGB[1], RGB[2]);
  
    }

      public static double[] RGBtoLCH_JzAzBz(int[] RGB) {

          return RGBtoLCH_JzAzBz(RGB[0], RGB[1], RGB[2]);

      }

      public static double[] RGBtoLCH_oklab(int[] RGB) {

          return RGBtoLCH_oklab(RGB[0], RGB[1], RGB[2]);

      }

      public static double[] RGBtoLCH_uv(int[] RGB) {

          return RGBtoLCH_uv(RGB[0], RGB[1], RGB[2]);

      }

      /*

        Min L= 0.0 C= 5.0638463202671015E-5 H= 5.041644380817674E-6
        Max L= 100.0 C= 179.08481341748777 H= 359.9999965782164

       */
      public static double[] RGBtoLCH_uv(int R, int G, int B) {

          double[] temp = RGBtoLUV(R, G, B);

          double L = temp[0];
          double u = temp[1];
          double v = temp[2];

          double h = Math.atan2(v, u);
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
          result[1] = Math.sqrt(u * u + v * v);
          result[2] = h;

          return result;

      }
    
    /*
     * D65
     * L : 0, 100
     * C : 0, 133.81
     * H : 0, 360
     */
    public static double[] RGBtoLCH_ab(int R, int G, int B) {
        
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


      public static double[] RGBtoLCH_JzAzBz(int R, int G, int B) {

          double[] temp = RGBtoJzAzBz(R, G, B);

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


      public static double[] RGBtoLCH_oklab(int R, int G, int B) {

          double[] temp = RGBtoOKLAB(R, G, B);

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
    
    public static int[] LCH_abtoRGB(double[] LCH) {
        
        return LCH_abtoRGB(LCH[0], LCH[1], LCH[2]);
  
    }

      public static int[] LCH_JzAzBztoRGB(double[] LCH) {

          return LCH_JzAzBztoRGB(LCH[0], LCH[1], LCH[2]);

      }

      public static int[] LCH_oklabtoRGB(double[] LCH) {

          return LCH_oklabtoRGB(LCH[0], LCH[1], LCH[2]);

      }


      public static int[] LCH_uvtoRGB(double[] LCH) {

          return LCH_uvtoRGB(LCH[0], LCH[1], LCH[2]);

      }
    
    public static int[] LCH_abtoRGB(double L, double C, double H) {
        
        double hRadians = H * Math.PI / 180.0;
        
        double a = Math.cos(hRadians) * C;
        double b = Math.sin(hRadians) * C;

        return LABtoRGB(L, a, b);
    }

      public static int[] LCH_JzAzBztoRGB(double L, double C, double H) {

          double hRadians = H * Math.PI / 180.0;

          double a = Math.cos(hRadians) * C;
          double b = Math.sin(hRadians) * C;

          return JzAzBztoRGB(L, a, b);
      }

      public static int[] LCH_oklabtoRGB(double L, double C, double H) {

          double hRadians = H * Math.PI / 180.0;

          double a = Math.cos(hRadians) * C;
          double b = Math.sin(hRadians) * C;

          return OKLABtoRGB(L, a, b);
      }

      public static int[] LCH_uvtoRGB(double L, double C, double H) {

          double hRadians = H * Math.PI / 180.0;

          double u = Math.cos(hRadians) * C;
          double v = Math.sin(hRadians) * C;

          return LUVtoRGB(L, u, v);
      }
    
    public static double[] RGBtoRYB(int[] RGB) {
        
        return RGBtoRYB(RGB[0], RGB[1], RGB[2]);
        
    }
    /**
    * Convert a red-green-blue system to a red-yellow-blue system.
     * R : 0, 1
     * Y : 0, 1
     * B : 0, 1
    */
    public static double[] RGBtoRYB(int R, int G, int B) {

        double rec = 1.0 / 255.0;
        double r = R * rec, g = G * rec, b = B * rec;
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
            b *= 0.5;
            g *= 0.5;
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
    
    public static int[] RYBtoRGB(double[] RYB) {
        
        return RYBtoRGB(RYB[0], RYB[1], RYB[2]);
        
    }
    
    /**
    * Convert a red-yellow-blue system to a red-green-blue system.
    */
    public static int[] RYBtoRGB(double r, double y, double b) {

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

    public static double[] RGBtoHWB(int[] RGB) {
        return RGBtoHWB(RGB[0], RGB[1], RGB[2]);
    }

    public static double[] RGBtoHWB(int r, int g, int b) {
        double[] hsb = RGBtoHSB(r, g, b);
        return new double[] {hsb[0], (1 - hsb[1]) * hsb[2], 1 - hsb[2]};
    }

    public static int[] HWBtoRGB(double[] HWB) {
        return HWBtoRGB(HWB[0], HWB[1], HWB[2]);
    }

    public static int[] HWBtoRGB(double H, double W, double B) {
        B = 1 - B;
        double S = 1 - W / B;
        return HSBtoRGB(H, S, B);
    }
    
    public static double clamp(double c) {
        
        if(c < 0) {
            return 0;
        }
	if(c > 1) {
            return 1;
        }
	return c;
                
    }
    
    /*    
     double finv(double t) {
    return ((t > (216.f / 24389.f)) ?
            t * t * t : (108.f / 841.f * (t - 4.f / 29.f)));
    }

 double K(double g) {
    if(g > 0.0031308) {
        return 1.055 * Math.pow(g, 1. / 2.4) - 0.055;
    } else {
        return 12.92 * g;
    }
}

    double f(double t) {
    return ((t > (216. / 24389.)) ?
            Math.cbrt(t) : (841. / 108. * t + 4. / 29.));
    }

double g(double K) {
    if(K > 0.04045) {
        return Math.pow((K + 0.055) / 1.055f, 2.4);
    } else {
        return K / 12.92;
    }
}

    double[] rgb2lab(int[] rgb) {
    // fixme
    // clamp values


    // Convert to sRGB
    double r0 = g(rgb[0] / 255.0);
    double g0 = g(rgb[1] / 255.0);
    double b0 = g(rgb[2] / 255.0);

    // see http://www.brucelindbloom.com/index.html?Eqn_RGB_XYZ_Matrix.html
    double X = 0.4124564 * r0 + 0.3575761 * g0 + 0.1804375 * b0;
    double Y = 0.2126729 * r0 + 0.7151522 * g0 + 0.0721750 * b0;
    double Z = 0.0193339 * r0 + 0.1191920 * g0 + 0.9503041 * b0;

    double fx = f(X / 0.9505);
    double fy = f(Y / 1.);
    double fz = f(Z / 1.0890);

    return new double[] {116. * fy - 16., 500. * (fx - fy), 200. * (fy - fz)};

}
    
    int[] lab2rgb(double[] lab) {
    double fx = (lab[0] + 16.) / 116. + lab[1] / 500.;
    double fy = (lab[0] + 16.) / 116.;
    double fz = (lab[0] + 16.) / 116. - lab[2] / 200.;

    double X = 0.9505 * finv(fx);
    double Y = 1. * finv(fy);
    double Z = 1.0890 * finv(fz);

    double r0 = X * 3.2404542 - Y * 1.5371385 - Z * 0.4985314; // red
    double g0 = -X * 0.9692660 + Y * 1.8760108 + Z * 0.0415560; // green
    double b0 = X * 0.0556434 - Y * 0.2040259 + Z * 1.0572252; // blue

    int r = (int) (255. * K(r0) + 0.5);
    int g = (int) (255. * K(g0) + 0.5);
    int b = (int) (255. * K(b0) + 0.5);

    // clamp values
    if(r < 0) r = 0; else if(r > 255) r = 255;
    if(g < 0) g = 0; else if(g > 255) g = 255;
    if(b < 0) b = 0; else if(b > 255) b = 255;
    
    return new int[] {r, g, b};
}*/

      /*public static void main(String[] args) {

          double minL = Double.MAX_VALUE;
          double minU = Double.MAX_VALUE;
          double minV = Double.MAX_VALUE;
          double maxL = -Double.MAX_VALUE;
          double maxU = -Double.MAX_VALUE;
          double maxV = -Double.MAX_VALUE;

          for(int r = 0; r < 256; r++) {
              for(int g = 0; g < 256; g++) {
                  for(int b = 0; b < 256; b++) {
                      double[] res = ColorSpaceConverter.RGBtoJzAzBz(r, g, b);

                      if(res[0] > maxL) {
                          maxL = res[0];
                      }

                      if(res[1] > maxU) {
                          maxU = res[1];
                      }

                      if(res[2] > maxV) {
                          maxV = res[2];
                      }

                      if(res[0] < minL) {
                          minL = res[0];
                      }

                      if(res[1] < minU) {
                          minU = res[1];
                      }

                      if(res[2] < minV) {
                          minV = res[2];
                      }

                      int[] newrgb = ColorSpaceConverter.JzAzBztoRGB(res);

                      if(r != newrgb[0]) {
                          System.out.println(newrgb[0] + " " + r);
                      }

                      if(g != newrgb[1]) {
                          System.out.println(newrgb[1] + " " + g);
                      }

                      if(b != newrgb[2]) {
                          System.out.println(newrgb[2] + " " + b);
                      }
                  }
              }
          }

          System.out.println("Min L= " + minL + " C= " + minU + " H= " + minV);
          System.out.println("Max L= " + maxL + " C= " + maxU + " H= " + maxV);


      }*/
  }

