
package fractalzoomer.utils;

import fractalzoomer.utils.space_filling_curves.*;

/**
 *
 * @author hrkalona2
 */
public class Pixel implements Comparable<Pixel> {
    public static int customX;
    public static int customY;

    public static boolean useCustom;

    public static int midX;
    public static int midY;
    public static int COMPARE_ALG;
    public static boolean REVERT;
    public static double n;
    public static double nreciprocal;

    public final int x;
    public final int y;

    public static double SPACING;

    public static boolean REPEAT;

    public static boolean CENTER;

    public static int WIDTH;
    
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean supportsRepeat() {
        return COMPARE_ALG >= 0 && COMPARE_ALG <= 15 && COMPARE_ALG != 4;
    }

    @Override
    public int compareTo(Pixel o) {

        double aX = x + (useCustom ? customX : midX);
        double aY = y + (useCustom ? customY : midY);

        double bX = o.x + (useCustom ? customX : midX);
        double bY = o.y + (useCustom ? customY : midY);

        if(REPEAT && supportsRepeat()) {
            aX = Math.sin(aX * SPACING) + 1;
            aY = Math.sin(aY * SPACING) + 1;
            bX = Math.sin(bX * SPACING) + 1;
            bY = Math.sin(bY * SPACING) + 1;
        }

        if(COMPARE_ALG == 0) {
            return !REVERT ? Double.compare(aX * aX + aY * aY, bX * bX + bY * bY) : Double.compare( bX * bX + bY * bY, aX * aX + aY * aY);
        }
        else if(COMPARE_ALG == 1) {
            return !REVERT ? Double.compare(Math.max(Math.abs(aX), Math.abs(aY)), Math.max(Math.abs(bX), Math.abs(bY))) : Double.compare(Math.max(Math.abs(bX), Math.abs(bY)), Math.max(Math.abs(aX), Math.abs(aY)));
        }
        else if(COMPARE_ALG == 2) {
            return !REVERT ? Double.compare(Math.abs(aX) + Math.abs(aY), Math.abs(bX) + Math.abs(bY)) : Double.compare(Math.abs(bX) + Math.abs(bY), Math.abs(aX) + Math.abs(aY));
        }
        else if (COMPARE_ALG == 3) {
            return !REVERT ? Double.compare(Math.pow(Math.pow(Math.abs(aX), n) + Math.pow(Math.abs(aY), n), nreciprocal),
                    Math.pow(Math.pow(Math.abs(bX), n) + Math.pow(Math.abs(bY), n), nreciprocal)
                    ) :
                    Double.compare(Math.pow(Math.pow(Math.abs(bX), n) + Math.pow(Math.abs(bY), n), nreciprocal), Math.pow(Math.pow(Math.abs(aX), n) + Math.pow(Math.abs(aY), n), nreciprocal));
        }
        //COMPARE_ALG : 4 is shuffle
        else if (COMPARE_ALG == 5) {
            return !REVERT ? Double.compare(Math.abs(aX), Math.abs(bX)) : Double.compare(Math.abs(bX), Math.abs(aX));
        }
        else if (COMPARE_ALG == 6) {
            return !REVERT ? Double.compare(Math.abs(aY), Math.abs(bY)) : Double.compare(Math.abs(bY), Math.abs(aY));
        }
        else if (COMPARE_ALG == 7) {
            return !REVERT ? Double.compare(Math.atan2(aY, aX), Math.atan2(bY, bX)) : Double.compare(Math.atan2(bY, bX), Math.atan2(aY, aX));
        }
        else if (COMPARE_ALG == 8) {
            return !REVERT ? Double.compare(Math.abs(Math.atan2(aY, aX)), Math.abs(Math.atan2(bY, bX))) : Double.compare(Math.abs(Math.atan2(bY, bX)), Math.abs(Math.atan2(aY, aX)));
        }
        else if (COMPARE_ALG == 9) {
            return !REVERT ? Double.compare(aX, bX) : Double.compare(bX, aX);
        }
        else if (COMPARE_ALG == 10) {
            return !REVERT ? Double.compare(aY, bY) : Double.compare(bY, aY);
        }
        else if (COMPARE_ALG == 11) {
            return !REVERT ? Double.compare(aX + aY, bX + bY) : Double.compare(bX + bY, aX + aY);
        }
        else if (COMPARE_ALG == 12) {
            return !REVERT ? Double.compare(aX - aY, bX - bY) : Double.compare(bX - bY, aX - aY);
        }
        else if (COMPARE_ALG == 13) {
            return !REVERT ? Double.compare(aX * aY, bX * bY) : Double.compare(bX * bY, aX * aY);
        }
        else if (COMPARE_ALG == 14) {
            return !REVERT ? Double.compare(Math.abs(aX) * Math.abs(aY), Math.abs(bX) * Math.abs(bY)) : Double.compare(Math.abs(bX) * Math.abs(bY), Math.abs(aX) * Math.abs(aY));
        }
        else if (COMPARE_ALG == 15) {
            return !REVERT ? Double.compare(Math.min(Math.abs(aX), Math.abs(aY)), Math.min(Math.abs(bX), Math.abs(bY))) : Double.compare(Math.min(Math.abs(bX), Math.abs(bY)), Math.min(Math.abs(aX), Math.abs(aY)));
        }
        //COMPARE_ALG == 16 is interleaved
        else if(COMPARE_ALG == 17) {
            return CENTER ? Long.compare(ZCurve.interleaveBits((int)Math.abs(aX), (int)Math.abs(aY)), ZCurve.interleaveBits((int)Math.abs(bX), (int)Math.abs(bY)))
            : Long.compare(ZCurve.interleaveBits(x, y), ZCurve.interleaveBits(o.x, o.y));
        }
        else if (COMPARE_ALG == 18) {
            return CENTER ? Integer.compare(HilbertCurve.defaultCurve((int)Math.abs(aX), (int)Math.abs(aY)), HilbertCurve.defaultCurve((int)Math.abs(bX), (int)Math.abs(bY)))
            : Integer.compare(HilbertCurve.defaultCurve(x, y), HilbertCurve.defaultCurve(o.x, o.y));
        }
        else if (COMPARE_ALG == 19) {
            //3^19
            return CENTER ? Integer.compare(PeanoCurve.defaultCurve((int)Math.abs(aX), (int)Math.abs(aY)), PeanoCurve.defaultCurve((int)Math.abs(bX), (int)Math.abs(bY)))
                    : Integer.compare(PeanoCurve.defaultCurve(x, y), PeanoCurve.defaultCurve(o.x, o.y));
        }
        else if (COMPARE_ALG == -1) {//Unused
            return CENTER ? Integer.compare(SnakeCurve.xyToSnake(WIDTH, (int)Math.abs(aX), (int)Math.abs(aY)), SnakeCurve.xyToSnake(WIDTH, (int)Math.abs(bX), (int)Math.abs(bY)))
                    : Integer.compare(SnakeCurve.xyToSnake(WIDTH, x, y), SnakeCurve.xyToSnake(WIDTH, o.x, o.y));
        }
        else if (COMPARE_ALG == 20) {
            return CENTER ? Integer.compare(DiagonalZigZagCurve.xyToDiagonal((int)Math.abs(aX), (int)Math.abs(aY)), DiagonalZigZagCurve.xyToDiagonal((int)Math.abs(bX), (int)Math.abs(bY)))
                    : Integer.compare(DiagonalZigZagCurve.xyToDiagonal(x, y), DiagonalZigZagCurve.xyToDiagonal(o.x, o.y));
        }
        else if (COMPARE_ALG == 21) {
            return CENTER ? Integer.compare(GrayCodeCurve.defaultCurve((int)Math.abs(aX), (int)Math.abs(aY)), GrayCodeCurve.defaultCurve( (int)Math.abs(bX), (int)Math.abs(bY)))
                    : Integer.compare(GrayCodeCurve.defaultCurve(x, y), GrayCodeCurve.defaultCurve(o.x, o.y));
        }
        else if (COMPARE_ALG == 22) {
            return CENTER ? Integer.compare(PeanoGenericCurve.defaultCurve((int)Math.abs(aX), (int)Math.abs(aY)), PeanoGenericCurve.defaultCurve((int)Math.abs(bX), (int)Math.abs(bY)))
                    : Integer.compare(PeanoGenericCurve.defaultCurve(x, y), PeanoGenericCurve.defaultCurve(o.x, o.y));
        }
        else if (COMPARE_ALG == 23) {
            return Integer.compare(y * WIDTH + x, o.y * WIDTH + o.x);
        }

        return 0;

    }
}
