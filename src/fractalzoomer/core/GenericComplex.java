package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public class GenericComplex {

    public Complex toComplex() {return null;}

    public MantExpComplex toMantExpComplex() {return null;}

    public GenericComplex square_plus_c(GenericComplex c) { return null;}

    public GenericComplex squareFast_plus_c(NormComponents normComponents, GenericComplex c) { return null;}
    public GenericComplex squareFast(NormComponents normComponents) { return null;}
    public GenericComplex cubeFast(NormComponents normComponents) { return null;}
    public GenericComplex fourthFast(NormComponents normComponents) { return null;}
    public GenericComplex fifthFast(NormComponents normComponents) { return null;}

    public GenericComplex squareFast_non_mutable(NormComponents normComponents) { return null;}

    public GenericComplex abs() {return null;}

    public GenericComplex sub(GenericComplex v) { return null; }

    public GenericComplex sub_mutable(GenericComplex v) { return null; }

    public int compare(GenericComplex z2) { return 2; }

    public GenericComplex square() {return null;}

    public GenericComplex square_mutable() {return null;}

    public GenericComplex cube() {return null;}

    public GenericComplex fourth() {return null;}

    public GenericComplex fifth() {return null;}

    public GenericComplex plus(GenericComplex z2) {return null;}

    public GenericComplex plus_mutable(GenericComplex z2) {return null;}

    public GenericComplex conjugate() {return null;}

    public GenericComplex times2() {return null;}

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

    public GenericComplex plus(Apfloat a) {return null;}
    public GenericComplex plus(int a) {return null;}
    public GenericComplex plus_mutable(int a) {return null;}
    public GenericComplex sub(Apfloat a) {return null;}

    public GenericComplex sub(int a) {return null;}
    public GenericComplex sub_mutable(int a) {return null;}

    public GenericComplex times(GenericComplex c) {return null;}

    public Object normSquared() { return null;}
    public Object distanceSquared(GenericComplex z) { return null;}

    public NormComponents normSquaredWithComponents(NormComponents n) {return null;}

    public BigNumComplex toBigNumComplex() { return null; }

    public MpfrBigNumComplex toMpfrBigNumComplex() { return null; }

    public DDComplex toDDComplex() { return null; }


    public void set(GenericComplex z) {};

    public GenericComplex divide(GenericComplex z) {return null;}

    public GenericComplex divide_mutable(GenericComplex z) {return null;}


}
