import fractalzoomer.core.BigNum;
import fractalzoomer.core.MyApfloat;
import org.apfloat.Apfloat;

public class TestBigNum {

    public static void main(String[] args) {

        MyApfloat.precision = 1000000;
        BigNum.reinitializeTest(10000);

        Apfloat a = new MyApfloat("1.999999999999999999999999");
        BigNum k = new BigNum(a);

        assert k.multFull(k).compare(k.multFullOLD(k)) == 0;
        assert k.multKaratsuba(k).compare(k.multKaratsubaOLD(k)) == 0;
        assert k.squareFull().compare(k.squareFullOLD()) == 0;
        assert k.squareFull().compare(k.squareFullBackwards()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaOLD()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaBackwards()) == 0;

        assert k.multFull(k).compare(k.multKaratsuba(k)) == 0;
        assert k.multFull(k).compare(k.squareFull()) == 0;
        assert k.multFull(k).compare(k.squareKaratsuba()) == 0;

        a = new MyApfloat("11111111.999999999999999999999999");
        k = new BigNum(a);

        assert k.multFull(k).compare(k.multFullOLD(k)) == 0;
        assert k.multKaratsuba(k).compare(k.multKaratsubaOLD(k)) == 0;
        assert k.squareFull().compare(k.squareFullOLD()) == 0;
        assert k.squareFull().compare(k.squareFullBackwards()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaOLD()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaBackwards()) == 0;

        assert k.multFull(k).compare(k.multKaratsuba(k)) == 0;
        assert k.multFull(k).compare(k.squareFull()) == 0;
        assert k.multFull(k).compare(k.squareKaratsuba()) == 0;


        BigNum.reinitializeTest(10000 + 30);

        a = new MyApfloat("1.999999999999999999999999");
        k = new BigNum(a);

        assert k.multFull(k).compare(k.multFullOLD(k)) == 0;
        assert k.multKaratsuba(k).compare(k.multKaratsubaOLD(k)) == 0;
        assert k.squareFull().compare(k.squareFullOLD()) == 0;
        assert k.squareFull().compare(k.squareFullBackwards()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaOLD()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaBackwards()) == 0;

        assert k.multFull(k).compare(k.multKaratsuba(k)) == 0;
        assert k.multFull(k).compare(k.squareFull()) == 0;
        assert k.multFull(k).compare(k.squareKaratsuba()) == 0;

        a = new MyApfloat("11111111.999999999999999999999999");
        k = new BigNum(a);

        assert k.multFull(k).compare(k.multFullOLD(k)) == 0;
        assert k.multKaratsuba(k).compare(k.multKaratsubaOLD(k)) == 1;
        assert k.squareFull().compare(k.squareFullOLD()) == 0;
        assert k.squareFull().compare(k.squareFullBackwards()) == 0;
        assert k.squareKaratsuba().compare(k.squareKaratsubaOLD()) == 1;
        assert k.squareKaratsuba().compare(k.squareKaratsubaBackwards()) == 0;

        assert k.multFull(k).compare(k.multKaratsuba(k)) == -1;
        assert k.multFull(k).compare(k.squareFull()) == 0;
        assert k.multFull(k).compare(k.squareKaratsuba()) == -1;

    }
}
