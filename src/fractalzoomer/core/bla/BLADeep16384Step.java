package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeep16384Step extends BLADeepGenericStep {

    public BLADeep16384Step(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 16384;
    }

}
