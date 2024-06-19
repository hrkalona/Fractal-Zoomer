
package fractalzoomer.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author hrkalona2
 */
public class ColorBrewerPalette {

    public static final int[] GREEN1 = {-16759511, -16750537, -14449597, -12473507, -8862087, -5382770, -2494301, -525127, -27};
    public static final int[] GREEN2 = {-16759781, -16749268, -14447803, -12472714, -10042716, -6694711, -3347226, -1706503, -525059};
    public static final int[] GREEN3 = {-16759781, -16749268, -14447803, -12473507, -9124746, -6170213, -3675712, -1706528, -525067};
    public static final int[] BLUE1 = {-16245416, -14338924, -14524760, -14839360, -12470588, -8401477, -3675724, -1181519, -39};
    public static final int[] BLUE2 = {-16236415, -16226132, -13923138, -11619373, -8663868, -5710411, -3347515, -2034725, -525072};
    public static final int[] BLUE3 = {-16693706, -16683943, -16612982, -13201216, -9983537, -5849637, -3091994, -1252624, -2053};
    public static final int[] BLUE4 = {-16631720, -16491891, -16420688, -13201216, -9131569, -5849637, -3091994, -1251342, -2053};
    public static final int[] BLUE5 = {-16240533, -16232036, -14585419, -12414266, -9720106, -6370591, -3744785, -2167817, -525313};
    public static final int[] PURPLE1 = {-11730869, -8319108, -7847523, -7574607, -7563578, -6374182, -4205594, -2036492, -525059};
    public static final int[] PURPLE2 = {-11992982, -8781449, -5373570, -2280297, -563039, -352331, -211520, -139043, -2061};
    public static final int[] PURPLE3 = {-10026977, -6815677, -3272106, -1627766, -2136656, -3566393, -2836006, -1580561, -527111};
    public static final int[] PURPLE4 = {-12648323, -11262065, -9809501, -8356422, -6382904, -4407844, -2434325, -1053195, -197635};
    public static final int[] RED1 = {-8454144, -5046272, -2674657, -1088184, -225959, -148604, -142178, -71480, -2068};
    public static final int[] RED2 = {-8388570, -4390874, -1893860, -242134, -160452, -85428, -75402, -4704, -52};
    public static final int[] RED3 = {-10026995, -5959915, -3467235, -1098964, -300470, -224654, -214111, -73518, -2576};
    public static final int[] ORANGE1 = {-10083066, -6736892, -3388414, -1282028, -91863, -80817, -72815, -2116, -27};
    public static final int[] ORANGE2 = {-8444156, -5884413, -2537471, -956141, -160452, -151957, -143198, -71986, -2581};
    public static final int[] BLACK = {-16777216, -14342875, -11382190, -9211021, -6908266, -4342339, -2500135, -986896, -1};

    private static final int[] MIXED1 = {-13828021, -11262072, -8358996, -5067822, -2565397, -526345, -73546, -149405, -2063852, -5023738, -8439032};
    private static final int[] MIXED2 = {-16761808, -16685474, -13265009, -8335935, -3675419, -657931, -595773, -2112899, -4226771, -7581430, -11259899};
    private static final int[] MIXED3 = {-16759781, -14976969, -10834335, -5842016, -2494253, -526345, -1583896, -4020785, -6721365, -9033085, -12582837};
    private static final int[] MIXED4 = {-14195687, -11693535, -8405951, -4660858, -1641008, -526345, -139025, -936230, -2197586, -3859587, -7470766};
    private static final int[] MIXED5 = {-16437151, -14588244, -12348477, -7158306, -3021328, -526345, -140345, -744062, -2727859, -5105621, -10026977};
    private static final int[] MIXED6 = {-15066598, -11711155, -7895161, -4539718, -2039584, -1, -140345, -744062, -2727859, -5105621, -10026977};
    private static final int[] MIXED7 = {-13551979, -12225100, -9130543, -5514775, -2034696, -65, -73584, -151967, -758461, -2674649, -5963738};
    private static final int[] MIXED8 = {-10596446, -13465411, -10042715, -5513820, -1641064, -65, -73589, -151967, -758461, -2802097, -6422206};
    private static final int[] MIXED9 = {-16750537, -15034288, -10044061, -5842582, -2494581, -65, -73589, -151967, -758461, -2674649, -5963738};

    public static Color[] generate2(int max_colors) {
        ArrayList<int[]> greens = new ArrayList<>();
        greens.add(GREEN1);
        greens.add(GREEN2);
        greens.add(GREEN3);

        ArrayList<int[]> blues = new ArrayList<>();
        blues.add(BLUE1);
        blues.add(BLUE2);
        blues.add(BLUE3);
        blues.add(BLUE4);
        blues.add(BLUE5);

        ArrayList<int[]> reds = new ArrayList<>();
        reds.add(RED1);
        reds.add(RED2);
        reds.add(RED3);

        ArrayList<int[]> purples = new ArrayList<>();
        purples.add(PURPLE1);
        purples.add(PURPLE2);
        purples.add(PURPLE3);
        purples.add(PURPLE4);

        ArrayList<int[]> oranges = new ArrayList<>();
        oranges.add(ORANGE1);
        oranges.add(ORANGE2);

        ArrayList<int[]> blacks = new ArrayList<>();
        blacks.add(BLACK);

        ArrayList<ArrayList<int[]>> colors = new ArrayList<>();
        colors.add(greens);
        colors.add(blues);
        colors.add(purples);
        colors.add(reds);
        colors.add(oranges);
        colors.add(blacks);

        int count = 0;

        Random generator = new Random();

        Color[] palette = new Color[max_colors];

        while(true) {

            if(count >= max_colors - 1 || colors.isEmpty()) {
                break;
            }

            int k = generator.nextInt(colors.size());

            ArrayList<int[]> category = colors.get(k);

            int r = generator.nextInt(category.size());

            int[] subcategory = category.get(r);

            colors.remove(k);

            int from = 0, to = 0;

            while(Math.abs(from - to) < 2) {
                from = generator.nextInt(subcategory.length);
                to = generator.nextInt(subcategory.length);
            }

            if(from > to) {
                for(int i = from; i >= to && count < max_colors; i--, count++) {
                    palette[count] = new Color(subcategory[i]);
                }
            }
            else {
                for(int i = from; i <= to && count < max_colors; i++, count++) {
                    palette[count] = new Color(subcategory[i]);
                }
            }

        }

        return palette;
    }

    public static Color[] generate(int max_colors) {
        ArrayList<int[]> mixed = new ArrayList<>();

        mixed.add(MIXED1);
        mixed.add(MIXED2);
        mixed.add(MIXED3);
        mixed.add(MIXED4);
        mixed.add(MIXED5);
        mixed.add(MIXED6);
        mixed.add(MIXED7);
        mixed.add(MIXED8);
        mixed.add(MIXED9);

        int count = 0;

        Random generator = new Random();

        Color[] palette = new Color[max_colors];

        while(true) {

            if(count >= max_colors) {
                break;
            }

            int k = generator.nextInt(mixed.size());

            int[] colors = mixed.get(k);

            int asc = generator.nextInt(2);
            
            if(asc == 0) {
                for(int i = 0; i < colors.length && count < max_colors; i++, count++) {
                    palette[count] = new Color(colors[i]);
                }
            }
            else {
                for(int i = colors.length - 1; i >= 0 && count < max_colors; i--, count++) {
                    palette[count] = new Color(colors[i]);
                } 
            }

            mixed.remove(k);
        }

        return palette;

    }
}
