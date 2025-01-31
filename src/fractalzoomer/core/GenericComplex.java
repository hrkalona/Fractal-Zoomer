package fractalzoomer.core;

import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.unused.BigDecNumComplex;
import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public abstract class GenericComplex {

    public Complex toComplex() {return null;}
    public BigComplex toBigComplex() {return null;}


    public Object re() {
        return null;
    }

    public Object im() {
        return null;
    }

    public Object Norm() {
        return null;
    }

    public MantExpComplex toMantExpComplex() {return null;}

    public GenericComplex square_plus_c_mutable(GenericComplex c) { return null;}
    public GenericComplex square_plus_c_mutable_no_threads(GenericComplex c) { return null;}

    public GenericComplex squareFast_plus_c_mutable(NormComponents normComponents, GenericComplex c) { return null;}

    public GenericComplex square_plus_c_mutable(GenericComplex c, MpfrBigNum temp, MpfrBigNum temp2, MpfrBigNum temp3) {return null;}

    public GenericComplex square_plus_c_mutable_with_reduction(GenericComplex c, MpfrBigNum temp, MpfrBigNum temp2, MpfrBigNum temp3, boolean deepZoom, Complex cz, MantExpComplex mcz) {return null;}

    public GenericComplex square_mutable(MpfrBigNum temp1, MpfrBigNum temp2) { return null; }

    public GenericComplex square_mutable(MpirBigNum temp1, MpirBigNum temp2) { return null; }

    public GenericComplex square_plus_c_mutable(GenericComplex c, MpirBigNum temp, MpirBigNum temp2, MpirBigNum temp3) {return null;}

    public GenericComplex square_plus_c_mutable_with_reduction(GenericComplex c, MpirBigNum temp, MpirBigNum temp2, MpirBigNum temp3, boolean deepZoom, Complex cz, MantExpComplex mcz) {return null;}

    public GenericComplex square_plus_c_mutable_no_threads(GenericComplex c, MpfrBigNum temp, MpfrBigNum temp2, MpfrBigNum temp3) {return null;}
    public GenericComplex square_plus_c_mutable_no_threads(GenericComplex c, MpirBigNum temp, MpirBigNum temp2, MpirBigNum temp3) {return null;}
    public GenericComplex sub(GenericComplex v, MpfrBigNum temp, MpfrBigNum temp2) {return null;}

    public GenericComplex sub(GenericComplex v, MpirBigNum temp, MpirBigNum temp2) {return null;}

    public GenericComplex cube_mutable(MpfrBigNum temp, MpfrBigNum temp2, MpfrBigNum temp3, MpfrBigNum temp4) { return null;}

    public GenericComplex cube_mutable(MpirBigNum temp, MpirBigNum temp2, MpirBigNum temp3, MpirBigNum temp4) { return null;}

    public GenericComplex squareFast(NormComponents normComponents) { return null;}
    public GenericComplex cubeFast(NormComponents normComponents) { return null;}

    public GenericComplex squareFast_mutable(NormComponents normComponents) { return null;}
    public GenericComplex cubeFast_mutable(NormComponents normComponents) { return null;}
    public GenericComplex fourthFast_mutable(NormComponents normComponents) { return null;}
    public GenericComplex fifthFast_mutable(NormComponents normComponents) { return null;}


    public GenericComplex abs_mutable() { return null; }

    public GenericComplex sub(GenericComplex v) { return null; }

    public GenericComplex sub_mutable(GenericComplex v) { return null; }

    public int compare(GenericComplex z2) { return 2; }

    public GenericComplex square() {return null;}

    public GenericComplex square_mutable() {return null;}

    public GenericComplex reciprocal() {return null;}

    public GenericComplex cube() {return null;}

    public GenericComplex fourth() {return null;}

    public GenericComplex fifth() {return null;}

    public GenericComplex plus(GenericComplex z2) {return null;}

    public GenericComplex plus_mutable(GenericComplex z2) {return null;}

    public GenericComplex conjugate_mutable() {return null;}

    public GenericComplex times2() {return null;}

    public GenericComplex times2(MpfrBigNum temp1, MpfrBigNum temp2) {return null;}

    public GenericComplex times2(MpirBigNum temp1, MpirBigNum temp2) {return null;}

    public GenericComplex times2_mutable() {return null;}

    public GenericComplex times4() {return null;}

    public GenericComplex times(Apfloat a) {return null;}

    public GenericComplex divide(Apfloat a) {return null;}

    public GenericComplex divide(int a) {return null;}
    public GenericComplex times(int a) {return null;}
    public GenericComplex times_mutable(int a) {return null;}

    public GenericComplex times_mutable(GenericComplex a) {return null;}

    public GenericComplex negative() { return null;}
    public GenericComplex negative_mutable() { return null;}

    public GenericComplex negate_re() { return null;}

    public GenericComplex negate_re_mutable() { return null;}

    public GenericComplex plus(Apfloat a) {return null;}
    public GenericComplex plus(int a) {return null;}
    public GenericComplex plus_mutable(int a) {return null;}
    public GenericComplex sub(Apfloat a) {return null;}

    public GenericComplex sub(int a) {return null;}

    public GenericComplex r_sub(int a) {return null;}

    public GenericComplex r_sub(Apfloat a) {return null;}
    public GenericComplex sub_mutable(int a) {return null;}

    public GenericComplex times(GenericComplex c) {return null;}

    public Object normSquared() { return null;}
    public Object distanceSquared(GenericComplex z) { return null;}

    public NormComponents normSquaredWithComponents(NormComponents n) {return null;}

    public BigNumComplex toBigNumComplex() { return null; }

    public BigIntNumComplex toBigIntNumComplex() { return null; }

    public BigDecNumComplex toBigDecNumComplex() { return null; }

    public MpfrBigNumComplex toMpfrBigNumComplex() { return null; }

    public MpirBigNumComplex toMpirBigNumComplex() { return null; }

    public DDComplex toDDComplex() { return null; }


    public void set(GenericComplex z) {}

    public GenericComplex divide(GenericComplex z) {return null;}

    public GenericComplex divide_mutable(GenericComplex z) {return null;}

    public void assign(GenericComplex z) {}

    public GenericComplex absNegateRe_mutable() {return null;}

    public GenericComplex absNegateIm_mutable() {return null;}

    public GenericComplex absre_mutable() { return null; }

    public abstract boolean isZero();

}
