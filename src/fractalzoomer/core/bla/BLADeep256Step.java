package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeep256Step extends BLADeepGenericStep {

    public BLADeep256Step(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 256;
    }

}
