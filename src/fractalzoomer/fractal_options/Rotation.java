

package fractalzoomer.fractal_options;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import org.apfloat.Apfloat;


/**
 *
 * @author hrkalona2
 */
public class Rotation {
  private Complex rotation;
  private Complex inv_rotation;
  private Complex center;

  private Apfloat ddrotationA;
  private Apfloat ddrotationApB;
  private Apfloat ddrotationAsB;
  private BigComplex ddcenter;

  private DoubleDouble ddcrotationA;
  private DoubleDouble ddcrotationApB;
  private DoubleDouble ddcrotationAsB;
  private DDComplex ddccenter;

  private BigNum bnRotationA;
  private BigNum bnrotationApB;
  private BigNum bnrotationAsB;

  private BigIntNum binRotationA;
  private BigIntNum binrotationApB;
  private BigIntNum binrotationAsB;
  private BigNumComplex bncenter;

  private BigIntNumComplex bincenter;

  private MpfrBigNum mpfrbnrotationA;
  private MpfrBigNum mpfrbnrotationApB;
  private MpfrBigNum mpfrbnrotationAsB;

  private MpfrBigNum F;
  private MpfrBigNum tempRe;
    private MpfrBigNum tempIm;
  private MpfrBigNumComplex mpfrbncenter;

    private MpirBigNumComplex mpirbncenter;

    private MpirBigNum Fp;
    private MpirBigNum tempRep;
    private MpirBigNum tempImp;

    private MpirBigNum mpirbnrotationA;
    private MpirBigNum mpirbnrotationApB;
    private MpirBigNum mpirbnrotationAsB;
    private boolean hasRotation = false;
    private boolean hasRotationCenter = false;

  
    public Rotation(double cos_theta, double sin_theta, double x, double y) {
        
        rotation = new Complex(cos_theta, sin_theta);
        inv_rotation = rotation.conjugate();

        center = new Complex(x, y);
        
    }

    public Rotation(Complex rotation, Complex center) {
        this.rotation = rotation;
        this.center = center;
    }

    public Rotation(BigComplex ddrotation, BigComplex ddcenter) {
        this.ddcenter = ddcenter;
        hasRotation = !ddrotation.isOne();
        hasRotationCenter = !ddcenter.isZero();

        if(hasRotation) {
            ddrotationA = ddrotation.getRe();
            ddrotationApB = ddrotationA.add(ddrotation.getIm());
            ddrotationAsB = ddrotationA.subtract(ddrotation.getIm());
        }
    }

    public Rotation(DDComplex ddrotation, DDComplex ddcenter) {
        this.ddccenter = ddcenter;
        hasRotation = !ddrotation.isOne();
        hasRotationCenter = !ddcenter.isZero();

        if(hasRotation) {
            ddcrotationA = ddrotation.getRe();
            ddcrotationApB = ddcrotationA.add(ddrotation.getIm());
            ddcrotationAsB = ddcrotationA.subtract(ddrotation.getIm());
        }
    }

    public Rotation(BigNumComplex bnrotation, BigNumComplex bncenter) {
        this.bncenter = bncenter;
        hasRotation = !bnrotation.isOne();
        hasRotationCenter = !bncenter.isZero();

        if(hasRotation) {
            bnRotationA = bnrotation.getRe();
            bnrotationApB = bnRotationA.add(bnrotation.getIm());
            bnrotationAsB = bnRotationA.sub(bnrotation.getIm());
        }
    }

    public Rotation(BigIntNumComplex bnrotation, BigIntNumComplex bincenter) {
        this.bincenter = bincenter;
        hasRotation = !bnrotation.isOne();
        hasRotationCenter = !bincenter.isZero();

        if(hasRotation) {
            binRotationA = bnrotation.getRe();
            binrotationApB = binRotationA.add(bnrotation.getIm());
            binrotationAsB = binRotationA.sub(bnrotation.getIm());
        }
    }

    public Rotation(MpfrBigNumComplex mpfrbnrotation, MpfrBigNumComplex mpfrbncenter) {

        this.mpfrbncenter = mpfrbncenter;
        hasRotation = !mpfrbnrotation.isOne();
        hasRotationCenter = !mpfrbncenter.isZero();

        if(hasRotation) {
            mpfrbnrotationA = mpfrbnrotation.getRe();
            mpfrbnrotationApB = mpfrbnrotationA.add(mpfrbnrotation.getIm());
            mpfrbnrotationAsB = mpfrbnrotationA.sub(mpfrbnrotation.getIm());
            F = new MpfrBigNum();
            tempRe = new MpfrBigNum();
            tempIm = new MpfrBigNum();
        }
    }

    public Rotation(MpirBigNumComplex mpirbnrotation, MpirBigNumComplex mpirbncenter) {

        this.mpirbncenter = mpirbncenter;
        hasRotation = !mpirbnrotation.isOne();
        hasRotationCenter = !mpirbncenter.isZero();

        if(hasRotation) {
            mpirbnrotationA = mpirbnrotation.getRe();
            mpirbnrotationApB = mpirbnrotationA.add(mpirbnrotation.getIm());
            mpirbnrotationAsB = mpirbnrotationA.sub(mpirbnrotation.getIm());
            Fp = new MpirBigNum();
            tempRep = new MpirBigNum();
            tempImp = new MpirBigNum();
        }
    }
    
    public Complex rotate(Complex pixel) {
        
         Complex temp = pixel.sub(center);
         return temp.times_mutable(rotation).plus_mutable(center);
         
    }
    
    public Complex rotateInverse(Complex pixel) {
        
         Complex temp = pixel.sub(center);
         return temp.times_mutable(inv_rotation).plus_mutable(center);
         
    }

    public BigComplex rotate(BigComplex pixel) {
        if(hasRotationCenter) {
            pixel = pixel.sub(ddcenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            Apfloat X = pixel.getRe();
            Apfloat Y = pixel.getIm();
            Apfloat F = MyApfloat.fp.multiply(ddrotationA, MyApfloat.fp.subtract(X, Y));
            pixel = new BigComplex(MyApfloat.fp.add(MyApfloat.fp.multiply(ddrotationAsB, Y), F), MyApfloat.fp.subtract(MyApfloat.fp.multiply(ddrotationApB, X), F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(ddcenter);
        }
        return pixel;
    }

    public DDComplex rotate(DDComplex pixel) {
        if(hasRotationCenter) {
            pixel = pixel.sub(ddccenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            DoubleDouble X = pixel.getRe();
            DoubleDouble Y = pixel.getIm();
            DoubleDouble F = ddcrotationA.multiply(X.subtract(Y));
            pixel = new DDComplex(ddcrotationAsB.multiply(Y).add(F), ddcrotationApB.multiply(X).subtract(F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(ddccenter);
        }

        return pixel;
    }

    public BigNumComplex rotate(BigNumComplex pixel) {

        if(hasRotationCenter) {
            pixel = pixel.sub(bncenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            BigNum X = pixel.getRe();
            BigNum Y = pixel.getIm();
            BigNum F = bnRotationA.mult(X.sub(Y));
            pixel = new BigNumComplex(bnrotationAsB.mult(Y).add(F), bnrotationApB.mult(X).sub(F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(bncenter);
        }

        return pixel;
    }

    public BigIntNumComplex rotate(BigIntNumComplex pixel) {

        if(hasRotationCenter) {
            pixel = pixel.sub(bincenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            BigIntNum X = pixel.getRe();
            BigIntNum Y = pixel.getIm();
            BigIntNum F = binRotationA.mult(X.sub(Y));
            pixel = new BigIntNumComplex(binrotationAsB.mult(Y).add(F), binrotationApB.mult(X).sub(F));
        }

        if(hasRotationCenter) {
            pixel = pixel.plus(bincenter);
        }

        return pixel;
    }

    public MpfrBigNumComplex rotate(MpfrBigNumComplex pixel) {
        if(hasRotationCenter) {
            pixel.sub_mutable(mpfrbncenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            MpfrBigNum X = pixel.getRe();
            MpfrBigNum Y = pixel.getIm();

//            X.sub(Y, F);
//            mpfrbnrotationA.mult(F, F);
//
//            mpfrbnrotationAsB.mult(Y, tempRe);
//            mpfrbnrotationApB.mult(X, tempIm);
//
//            tempRe.add(F, X);
//            tempIm.sub(F, Y);
            MpfrBigNum.rotation(X, Y, tempRe, tempIm, F, mpfrbnrotationA, mpfrbnrotationAsB, mpfrbnrotationApB);
        }

        if(hasRotationCenter) {
            pixel.plus_mutable(mpfrbncenter);
        }
        return pixel;
    }


    public MpirBigNumComplex rotate(MpirBigNumComplex pixel) {
        if(hasRotationCenter) {
            pixel.sub_mutable(mpirbncenter);
        }

        if(hasRotation) { //pixel * rotation but more efficient
            MpirBigNum X = pixel.getRe();
            MpirBigNum Y = pixel.getIm();

//            X.sub(Y, Fp);
//            mpirbnrotationA.mult(Fp, Fp);
//
//            mpirbnrotationAsB.mult(Y, tempRep);
//            mpirbnrotationApB.mult(X, tempImp);
//
//            tempRep.add(Fp, X);
//            tempImp.sub(Fp, Y);
            MpirBigNum.rotation(X, Y, tempRep, tempImp, Fp, mpirbnrotationA, mpirbnrotationAsB, mpirbnrotationApB);
        }

        if(hasRotationCenter) {
            pixel.plus_mutable(mpirbncenter);
        }
        return pixel;
    }

    public boolean shouldRotate(double xCenter, double yCenter) {
        return xCenter != center.getRe() || yCenter != center.getIm();
    }

    public boolean shouldRotate(DoubleDouble xCenter, DoubleDouble yCenter) {
        return xCenter.compareTo(ddccenter.getRe()) != 0 || yCenter.compareTo(ddccenter.getIm()) != 0;
    }

    public boolean shouldRotate(BigNum xCenter, BigNum yCenter) {
        return xCenter.compare(bncenter.getRe()) != 0 || yCenter.compare(bncenter.getIm()) != 0;
    }

    public boolean shouldRotate(BigIntNum xCenter, BigIntNum yCenter) {
        return xCenter.compare(bincenter.getRe()) != 0 || yCenter.compare(bincenter.getIm()) != 0;
    }

    public boolean shouldRotate(MpirBigNum xCenter, MpirBigNum yCenter) {
        return xCenter.compare(mpirbncenter.getRe()) != 0 || yCenter.compare(mpirbncenter.getIm()) != 0;
    }

    public boolean shouldRotate(MpfrBigNum xCenter, MpfrBigNum yCenter) {
        return xCenter.compare(mpfrbncenter.getRe()) != 0 || yCenter.compare(mpfrbncenter.getIm()) != 0;
    }

    public boolean shouldRotate(Apfloat xCenter, Apfloat yCenter) {
        return xCenter.compareTo(ddcenter.getRe()) != 0 || yCenter.compareTo(ddcenter.getIm()) != 0;
    }

    public static boolean usesRotation(Apfloat[] rotation_center, Apfloat[] rotation_vals) {
        boolean hasRotation = !(rotation_vals[0].compareTo(Apfloat.ONE) == 0 && rotation_vals[1].compareTo(Apfloat.ZERO) == 0);
        boolean hasRotationCenter = !(rotation_center[0].compareTo(Apfloat.ZERO) == 0 && rotation_center[1].compareTo(Apfloat.ZERO) == 0);

        return hasRotation || hasRotationCenter;
    }
    
}
