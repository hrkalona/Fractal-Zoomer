package fractalzoomer.core.approximation.mip_la_zhuoran;

public class MipLAPair {
    public MipLAStep step;
    public MipLADeepStep stepDeep;
    public int length;

    public MipLAPair() {
        step = null;
        stepDeep = null;
        length = 0;
    }
}
