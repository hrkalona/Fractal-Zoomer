package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeep131072Step extends BLADeepGenericStep {

    public BLADeep131072Step(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 131072;
    }

}
