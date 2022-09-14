package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeep536870912Step extends BLADeepGenericStep {

    public BLADeep536870912Step(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 536870912;
    }

}
