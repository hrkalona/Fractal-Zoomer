package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeepLStep extends BLADeepGenericStep {
    private int l;

    public BLADeepLStep(MantExp r2, MantExpComplex A, MantExpComplex B, int l) {
        super(r2, A, B);
        this.l = l;
    }

    @Override
    public int getL() {
        return l;
    }

}
