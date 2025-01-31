

package fractalzoomer.bailout_conditions;

import fractalzoomer.core.*;
import fractalzoomer.core.mpfr.LibMpfr;
import fractalzoomer.core.mpfr.MpfrBigNum;
import fractalzoomer.core.mpir.MpirBigNum;
import fractalzoomer.core.norms.NormN;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;

/**
 *
 * @author hrkalona2
 */
public class NNormBailoutCondition extends BailoutCondition {
  protected double n_norm;
  protected double n_norm_reciprocal;
  protected Apfloat ddn_norm;
  protected Apfloat ddn_norm_reciprocal;
  protected MpfrBigNum mpfrbn_norm;

  private MpfrBigNum temp1;
  private MpfrBigNum temp2;
  private MpfrBigNum mpfrbn_norm_reciprocal;

  public static double A = 1;
  public static double B = 1;
  private DoubleDouble Add;
  private DoubleDouble Bdd;

  private Apfloat Aaf;
  private Apfloat Baf;

  protected DoubleDouble dddn_norm;
  protected DoubleDouble dddn_norm_reciprocal;

    public NNormBailoutCondition(double bound, double n_norm) {
        
        super(bound);
        this.n_norm = n_norm;
        n_norm_reciprocal = 1 / n_norm;

        normImpl = new NormN(n_norm, A, B);

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddn_norm = new MyApfloat(n_norm);
            dddn_norm = new DoubleDouble(n_norm);

            if(n_norm != 0) {
                ddn_norm_reciprocal = MyApfloat.reciprocal(ddn_norm);
                dddn_norm_reciprocal = dddn_norm.reciprocal();
            }

            Add = new DoubleDouble(A);
            Bdd = new DoubleDouble(B);

            Aaf = new MyApfloat(A);
            Baf = new MyApfloat(B);


            if(!LibMpfr.mpfrHasError()) {
                mpfrbn_norm = new MpfrBigNum(n_norm);
                temp1 = new MpfrBigNum();
                temp2 = new MpfrBigNum();

                if(n_norm != 0) {
                    mpfrbn_norm_reciprocal = mpfrbn_norm.reciprocal();
                }
            }
        }

    }
    
     @Override //N norm
     public boolean escaped(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, double norm_squared, Complex pixel) {
         
        return z.nnorm(n_norm, A, B, n_norm_reciprocal) >= bound;
         
     }

    @Override //N norm
    public boolean escaped(BigComplex z, BigComplex zold, BigComplex zold2, int iterations, BigComplex c, BigComplex start, BigComplex c0, Apfloat norm_squared, BigComplex pixel) {

        if(ddn_norm.compareTo(Apcomplex.ZERO) == 0) {
            return false;
        }

        return z.nnorm(ddn_norm, Aaf, Baf, ddn_norm_reciprocal).compareTo(ddbound) >= 0;

    }

    @Override
    public boolean escaped(BigNumComplex z, BigNumComplex zold, BigNumComplex zold2, int iterations, BigNumComplex c, BigNumComplex start, BigNumComplex c0, BigNum norm_squared, BigNumComplex pixel) {
        double normSquared = norm_squared != null ? norm_squared.doubleValue() : 0;
        return escaped(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), normSquared, pixel.toComplex());
    }

    @Override
    public boolean escaped(BigIntNumComplex z, BigIntNumComplex zold, BigIntNumComplex zold2, int iterations, BigIntNumComplex c, BigIntNumComplex start, BigIntNumComplex c0, BigIntNum norm_squared, BigIntNumComplex pixel) {
        double normSquared = norm_squared != null ? norm_squared.doubleValue() : 0;
        return escaped(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), normSquared, pixel.toComplex());
    }

    @Override
    public boolean escaped(MpfrBigNumComplex z, MpfrBigNumComplex zold, MpfrBigNumComplex zold2, int iterations, MpfrBigNumComplex c, MpfrBigNumComplex start, MpfrBigNumComplex c0, MpfrBigNum norm_squared, MpfrBigNumComplex pixel) {
        if(mpfrbn_norm.isZero()) {
            return false;
        }

        return z.nnorm(mpfrbn_norm, A, B, temp1, temp2, mpfrbn_norm_reciprocal).compare(bound) >= 0;
    }

    @Override
    public boolean escaped(MpirBigNumComplex z, MpirBigNumComplex zold, MpirBigNumComplex zold2, int iterations, MpirBigNumComplex c, MpirBigNumComplex start, MpirBigNumComplex c0, MpirBigNum norm_squared, MpirBigNumComplex pixel) {
        double normSquared = norm_squared != null ? norm_squared.doubleValue() : 0;
        return escaped(z.toComplex(), zold.toComplex(), zold2.toComplex(), iterations, c.toComplex(), start.toComplex(), c0.toComplex(), normSquared, pixel.toComplex());
    }

    @Override
    public boolean escaped(DDComplex z, DDComplex zold, DDComplex zold2, int iterations, DDComplex c, DDComplex start, DDComplex c0, DoubleDouble norm_squared, DDComplex pixel) {

        if(dddn_norm.isZero()) {
            return false;
        }

        return z.nnorm(dddn_norm, Add, Bdd, dddn_norm_reciprocal).compareTo(ddcbound) >= 0;

    }
}
    