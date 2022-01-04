import fractalzoomer.core.Complex;
import fractalzoomer.core.Derivative;

public class TestDerivative {
    public static void main(String[] args) {

        Complex a = new Complex(-4.3242342, 5.534543);

        double epsilon = 1e-1;

        //z^2
        Complex zsqr = a.square();

        //1/3*z^3
        Complex first_der_forward = Derivative.numericalForwardDerivativeFirstOrder(a.cube().times(1/3.0), a.plus(Derivative.DZ).cube().times(1/3.0));
        Complex first_der_backward = Derivative.numericalBackwardDerivativeFirstOrder(a.cube().times(1/3.0), a.sub(Derivative.DZ).cube().times(1/3.0));
        Complex first_der_central = Derivative.numericalCentralDerivativeFirstOrder(a.plus(Derivative.DZ).cube().times(1/3.0), a.sub(Derivative.DZ).cube().times(1/3.0));

        assert zsqr.distance(first_der_forward) <= epsilon;
        assert zsqr.distance(first_der_backward) <= epsilon;
        assert zsqr.distance(first_der_central) <= epsilon;

        //1/12*z^4
        Complex second_der_forward = Derivative.numericalForwardDerivativeSecondOrder(a.fourth().times(1/12.0), a.plus(Derivative.DZ).fourth().times(1/12.0), a.plus(Derivative.DZ_2).fourth().times(1/12.0));
        Complex second_der_backward = Derivative.numericalBackwardDerivativeSecondOrder(a.fourth().times(1/12.0), a.sub(Derivative.DZ).fourth().times(1/12.0), a.sub(Derivative.DZ_2).fourth().times(1/12.0));
        Complex second_der_central = Derivative.numericalCentralDerivativeSecondOrder(a.fourth().times(1/12.0), a.plus(Derivative.DZ).fourth().times(1/12.0), a.sub(Derivative.DZ).fourth().times(1/12.0));

        assert zsqr.distance(second_der_forward) <= epsilon;
        assert zsqr.distance(second_der_backward) <= epsilon;
        assert zsqr.distance(second_der_central) <= epsilon;

        //1/60*z^5

        Complex third_der_forward = Derivative.numericalForwardDerivativeThirdOrder(a.fifth().times(1/60.0), a.plus(Derivative.DZ).fifth().times(1/60.0), a.plus(Derivative.DZ_2).fifth().times(1/60.0), a.plus(Derivative.DZ_3).fifth().times(1/60.0));
        Complex third_der_backward = Derivative.numericalBackwardDerivativeThirdOrder(a.fifth().times(1/60.0), a.sub(Derivative.DZ).fifth().times(1/60.0), a.sub(Derivative.DZ_2).fifth().times(1/60.0), a.sub(Derivative.DZ_3).fifth().times(1/60.0));
        Complex third_der_central = Derivative.numericalCentralDerivativeThirdOrder(a.plus(Derivative.DZ).fifth().times(1/60.0), a.plus(Derivative.DZ_2).fifth().times(1/60.0), a.sub(Derivative.DZ).fifth().times(1/60.0), a.sub(Derivative.DZ_2).fifth().times(1/60.0));

        assert zsqr.distance(third_der_forward) <= epsilon;
        assert zsqr.distance(third_der_backward) <= epsilon;
        assert zsqr.distance(third_der_central) <= epsilon;
    }
}
