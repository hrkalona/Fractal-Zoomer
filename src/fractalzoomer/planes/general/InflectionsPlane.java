package fractalzoomer.planes.general;

import fractalzoomer.core.*;
import fractalzoomer.planes.Plane;

import java.util.ArrayList;

public class InflectionsPlane extends Plane {
    private Complex[] inflections;
    private BigComplex[] ddinflections;

    private BigNumComplex[] bninflections;
    private BigIntNumComplex[] bniinflections;
    private DDComplex[] ddcinflections;

    private MpfrBigNumComplex[] mpfrbninflections;
    private MpirBigNumComplex[] mpirbninflections;
    private double power;

    public InflectionsPlane(ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionPower) {
        super();
        power = inflectionPower;
        inflections = new Complex[inflections_re.size()];
        for(int i = 0; i < inflections.length; i++) {
            inflections[i] = new Complex(inflections_re.get(i), inflections_im.get(i));
        }

        if(TaskRender.PERTURBATION_THEORY || TaskRender.HIGH_PRECISION_CALCULATION) {
            ddinflections = new BigComplex[inflections.length];
            ddcinflections = new DDComplex[inflections.length];
            bninflections = new BigNumComplex[inflections.length];
            bniinflections = new BigIntNumComplex[inflections.length];

            if (TaskRender.allocateMPFR()) {
                mpfrbninflections = new MpfrBigNumComplex[inflections.length];
            } else if (TaskRender.allocateMPIR()) {
                mpirbninflections = new MpirBigNumComplex[inflections.length];
            }

            for(int i = 0; i < inflections.length; i++) {
                ddinflections[i] = new BigComplex(inflections[i]);
                ddcinflections[i] = new DDComplex(inflections[i]);
                bninflections[i] = new BigNumComplex(inflections[i]);
                bniinflections[i] = new BigIntNumComplex(inflections[i]);

                if (TaskRender.allocateMPFR()) {
                    mpfrbninflections[i] = new MpfrBigNumComplex(inflections[i]);
                } else if (TaskRender.allocateMPIR()) {
                    mpirbninflections[i] = new MpirBigNumComplex(inflections[i]);
                }
            }
        }
    }


    @Override
    public Complex transform(Complex pixel) {

        Complex result = pixel;
        for(int i = inflections.length - 1; i >= 0; i--) {
            result = result.inflectionPower(inflections[i], power);
        }
        return result;

    }

    @Override
    public BigComplex transform(BigComplex pixel) {
        if(power == 1 || power == 2 || power == 3 || power == 4 || power == 5) {
            BigComplex result = pixel;
            for(int i = ddinflections.length - 1; i >= 0; i--) {
                result = result.inflectionPower(ddinflections[i], power);
            }
            return result;
        }

        return new BigComplex(transform(pixel.toComplex()));

    }

    @Override
    public BigNumComplex transform(BigNumComplex pixel) {

        if(power == 1 || power == 2 || power == 3 || power == 4 || power == 5) {
            BigNumComplex result = pixel;
            for(int i = bninflections.length - 1; i >= 0; i--) {
                result = result.inflectionPower(bninflections[i], power);
            }
            return result;
        }

        return new BigNumComplex(transform(pixel.toComplex()));

    }

    @Override
    public BigIntNumComplex transform(BigIntNumComplex pixel) {

        if(power == 1 || power == 2 || power == 3 || power == 4 || power == 5) {
            BigIntNumComplex result = pixel;
            for(int i = bniinflections.length - 1; i >= 0; i--) {
                result = result.inflectionPower(bniinflections[i], power);
            }
            return result;
        }

        return new BigIntNumComplex(transform(pixel.toComplex()));

    }

    @Override
    public MpfrBigNumComplex transform(MpfrBigNumComplex pixel) {

        MpfrBigNumComplex result = pixel;
        for(int i = mpfrbninflections.length - 1; i >= 0; i--) {
            result = result.inflectionPower(mpfrbninflections[i], power);
        }
        return result;


    }

    @Override
    public MpirBigNumComplex transform(MpirBigNumComplex pixel) {

        if(power == 1 || power == 2 || power == 3 || power == 4 || power == 5) {
            MpirBigNumComplex result = pixel;
            for(int i = mpirbninflections.length - 1; i >= 0; i--) {
                result = result.inflectionPower(mpirbninflections[i], power);
            }
            return result;
        }

        return new MpirBigNumComplex(transform(pixel.toComplex()));

    }


    @Override
    public DDComplex transform(DDComplex pixel) {

        DDComplex result = pixel;
        for(int i = ddcinflections.length - 1; i >= 0; i--) {
            result = result.inflectionPower(ddcinflections[i], power);
        }
        return result;

    }
}
