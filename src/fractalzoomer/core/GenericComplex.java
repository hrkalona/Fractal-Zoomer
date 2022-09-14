package fractalzoomer.core;

import fractalzoomer.utils.NormComponents;
import org.apfloat.Apfloat;

public class GenericComplex {

    public Complex toComplex() {return null;}

    public GenericComplex square_plus_c(GenericComplex c) { return null;}

    public GenericComplex squareFast_plus_c(NormComponents normComponents, GenericComplex c) { return null;}
    public GenericComplex squareFast(NormComponents normComponents) { return null;}
    public GenericComplex cubeFast(NormComponents normComponents) { return null;}
    public GenericComplex fourthFast(NormComponents normComponents) { return null;}
    public GenericComplex fifthFast(NormComponents normComponents) { return null;}

    public GenericComplex abs() {return null;}

    public GenericComplex sub(GenericComplex v) { return null; }

    public int compare(GenericComplex z2) { return 2; }

    public GenericComplex square() {return null;}

    public GenericComplex cube() {return null;}

    public GenericComplex fourth() {return null;}

    public GenericComplex fifth() {return null;}

    public GenericComplex plus(GenericComplex z2) {return null;}

    public GenericComplex conjugate() {return null;}

    public GenericComplex times(Apfloat a) {return null;}

    public GenericComplex sub(Apfloat a) {return null;}

    public GenericComplex r_sub(Apfloat a) {return null;}

    public GenericComplex times(BigNum a) {return null;}

    public GenericComplex sub(BigNum a) {return null;}

    public GenericComplex r_sub(BigNum a) {return null;}

    public GenericComplex times(GenericComplex c) {return null;}

    public Object normSquared() { return null;}
    public Object distanceSquared(GenericComplex z) { return null;}

    public NormComponents normSquaredWithComponents() {return null;}

    public BigNumComplex toBigNumComplex() { return null; }


}
