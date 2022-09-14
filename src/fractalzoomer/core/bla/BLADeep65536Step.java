package fractalzoomer.core.bla;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

public class BLADeep65536Step extends BLADeepGenericStep {

    public BLADeep65536Step(MantExp r2, MantExpComplex A, MantExpComplex B) {
        super(r2, A, B);
    }

    @Override
    public int getL() {
        return 65536;
    }

}
