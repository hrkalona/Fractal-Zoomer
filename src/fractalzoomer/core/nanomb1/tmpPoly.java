package fractalzoomer.core.nanomb1;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

//temporary poly class for solving for nucleus relative position. It is initialized with the part of the SSA that depends only on c.
public class tmpPoly {
    int m_m;
    MantExpComplex[] b;

    public tmpPoly(biPoly p) {
        m_m = p.m_n;
        b = new MantExpComplex[m_m + 1];
        for(int i = 0; i <= m_m; i++) {
            b[i] = new MantExpComplex(p.tab[0][i]);
        }
    }

    MantExpComplex eval(MantExpComplex u){
        MantExpComplex r = new MantExpComplex();
        MantExpComplex ui = new MantExpComplex(MantExp.ONE, MantExp.ZERO);
        for(int i=0; i <= m_m; i++){
            r.plus_mutable(b[i].times(ui));
            r.Reduce();
            ui.times_mutable(u);
            ui.Reduce();
        }
        return r;
    }

    //evaluate derivative.
    MantExpComplex evalD(MantExpComplex u){
        MantExpComplex r = new MantExpComplex();
        MantExpComplex ui = new MantExpComplex(MantExp.ONE, MantExp.ZERO);
        for(int i=1; i <= m_m; i++){
            r.plus_mutable(b[i].times(new MantExp(i)).times_mutable(ui));
            r.Reduce();
            ui.times_mutable(u);
            ui.Reduce();
        }
        return r;
    }

    //Gives the nearest root to the 0. To use if and when applicable (that is the reference is near 0... atom domain thing!)
    //Newton should do the job (otherwise IA-Newton ?).
    public static final int max_iters = 30;
    public MantExpComplex getRoot(){
        MantExpComplex rt = new MantExpComplex();
        //R_lo t = abs(eval(rt));
        for(int i=0; i<max_iters; i++){
            MantExpComplex num = eval(rt);
            MantExpComplex den = evalD(rt);
            MantExpComplex delta = num.divide(den);
            delta.Reduce();
            num = new MantExpComplex(rt);
            rt.sub_mutable(delta);
            rt.Reduce();
            if(rt.equals(num)) break;
        }
        return rt;
    }
}
