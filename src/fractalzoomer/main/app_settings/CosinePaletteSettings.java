package fractalzoomer.main.app_settings;

import java.io.Serializable;

public class CosinePaletteSettings implements Serializable {
    private static final long serialVersionUID =  -4896175689810736751L;
    public double redA;
    public double redB;
    public double redC;
    public double redD;

    public double redG;

    public double greenA;
    public double greenB;
    public double greenC;
    public double greenD;

    public double greenG;

    public double blueA;
    public double blueB;
    public double blueC;
    public double blueD;

    public double blueG;

    public CosinePaletteSettings() {
        redA = 0.5;
        redB = 0.5;
        greenA = 0.5;
        greenB = 0.5;
        blueA = 0.5;
        blueB = 0.5;

        redC = 1;
        greenC = 1;
        blueC = 1;

        redD = 0;
        greenD = 0;
        blueD = 0;

        redG = 1.0;
        greenG = 0.5450980392156862;
        blueG = 0.16470588235294117;

    }

    public CosinePaletteSettings(CosinePaletteSettings other) {
        redA = other.redA;
        redB = other.redB;
        greenA = other.greenA;
        greenB = other.greenB;
        blueA = other.blueA;
        blueB = other.blueB;

        redC = other.redC;
        greenC = other.greenC;
        blueC = other.blueC;

        redD = other.redD;
        greenD = other.greenD;
        blueD = other.blueD;

        redG = other.redG;
        greenG = other.greenG;
        blueG = other.blueG;
    }
}
