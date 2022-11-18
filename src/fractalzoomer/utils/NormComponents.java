package fractalzoomer.utils;

public class NormComponents {
    public Object normSquared;
    public Object reSqr;
    public Object imSqr;

    public Object tempRe;

    public Object tempIm;

    public Object temp1;

    public NormComponents(Object reSqr, Object imSqr, Object normSquared) {
        this.normSquared = normSquared;
        this.reSqr = reSqr;
        this.imSqr = imSqr;
    }

    public NormComponents(Object reSqr, Object imSqr, Object normSquared, Object tempRe, Object tempIm, Object temp1) {
        this.normSquared = normSquared;
        this.reSqr = reSqr;
        this.imSqr = imSqr;
        this.tempRe = tempRe;
        this.tempIm = tempIm;
        this.temp1 = temp1;
    }
}
