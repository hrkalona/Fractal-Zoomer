package fractalzoomer.core.nanomb1;

import fractalzoomer.core.MantExp;
import fractalzoomer.core.MantExpComplex;

// https://fractalforums.org/f/28/t/277/msg7952#msg7952
public class uniPoly {
    int m_m;
    MantExpComplex[] b;
    MantExpComplex[] dbdc;

    MantExpComplex[] ddbdcdc;
    public uniPoly(biPoly p, MantExpComplex c, int derivatives) {

        if(derivatives == 0) {
            initializeNoDerivatives(p, c);
        }
        else if (derivatives == 1){
            initializeOneDerivative(p, c);
        }
        else {
            initializeTwoDerivatives(p, c);
        }

    }

    public void initializeNoDerivatives(biPoly p, MantExpComplex c) {
        m_m = p.m_m;
        b = new MantExpComplex[m_m + 1];
        for(int i = 0; i <= m_m; i += 2)
        {
            MantExpComplex s = MantExpComplex.create();
            MantExpComplex cj = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
            for (int j = 0; j <= p.m_n; ++j)
            {
                s.plus_mutable(p.tab[i][j].times(cj));
                s.Normalize();
                cj.times_mutable(c);
                cj.Normalize();
            }
            b[i] = s;
        }
    }

    public void initializeOneDerivative(biPoly p, MantExpComplex c) {
        m_m = p.m_m;
        b = new MantExpComplex[m_m + 1];
        dbdc = new MantExpComplex[m_m + 1];
        for(int i = 0; i <= m_m; i += 2)
        {
            MantExpComplex s = MantExpComplex.create();
            MantExpComplex ds = MantExpComplex.create();
            MantExpComplex cj = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
            MantExpComplex cj1 = MantExpComplex.create();
            for (int j = 0; j <= p.m_n; ++j)
            {
                s.plus_mutable(p.tab[i][j].times(cj));
                s.Normalize();
                ds.plus_mutable(p.tab[i][j].times(new MantExp(j)).times_mutable(cj1));
                ds.Normalize();
                cj.times_mutable(c);
                cj.Normalize();
                cj1.times_mutable(c);
                cj1.Normalize();
                if (j == 0) cj1 = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
            }
            b[i] = s;
            dbdc[i] = ds;
        }
    }


    public void initializeTwoDerivatives(biPoly p, MantExpComplex c) {
        m_m = p.m_m;
        b = new MantExpComplex[m_m + 1];
        dbdc = new MantExpComplex[m_m + 1];
        ddbdcdc = new MantExpComplex[m_m + 1];
        for(int i = 0; i <= m_m; i += 2)
        {
            MantExpComplex s = MantExpComplex.create();
            MantExpComplex ds = MantExpComplex.create();
            MantExpComplex dds = MantExpComplex.create();

            MantExpComplex cj = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
            MantExpComplex cj1 = MantExpComplex.create();
            MantExpComplex cj2 = MantExpComplex.create();
            for (int j = 0; j <= p.m_n; ++j)
            {
                s.plus_mutable(p.tab[i][j].times(cj));
                s.Normalize();

                ds.plus_mutable(p.tab[i][j].times(new MantExp(j)).times_mutable(cj1));
                ds.Normalize();

                dds.plus_mutable(p.tab[i][j].times(new MantExp(j * (j - 1))).times_mutable(cj2));
                dds.Normalize();

                cj.times_mutable(c);
                cj.Normalize();
                cj1.times_mutable(c);
                cj1.Normalize();
                cj2.times_mutable(c);
                cj2.Normalize();
                if (j == 0) cj1 = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
                if (j == 1) cj2 = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
            }
            b[i] = s;
            dbdc[i] = ds;
            ddbdcdc[i] = dds;
        }
    }

    public void eval(MantExpComplex z) {
        MantExpComplex zs = MantExpComplex.create();
        MantExpComplex zi = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
        MantExpComplex zsqr = z.square();
        for (int i = 0; i <= m_m; i += 2)
        {
            zs.plus_mutable(b[i].times(zi));
            zs.Normalize();
            zi.times_mutable(zsqr);
            zi.Normalize();
        }
        z.assign(zs);
    }

    public void eval(MantExpComplex z, MantExpComplex dc) {

        MantExpComplex zs = MantExpComplex.create();
        MantExpComplex dcs = MantExpComplex.create();
        MantExpComplex zi = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
        MantExpComplex zi1 = MantExpComplex.create();

        MantExpComplex zsqr = z.square();

        for (int i = 0; i <= m_m; i += 2)
        {
            dcs.plus_mutable(b[i].times(zi1).times_mutable(dc).times_mutable(new MantExp(i))).plus_mutable(dbdc[i].times(zi));
            dcs.Normalize();
            zs.plus_mutable(b[i].times(zi));
            zs.Normalize();
            zi.times_mutable(zsqr);
            zi.Normalize();
            zi1.times_mutable(zsqr);
            zi1.Normalize();
            if (i == 0) zi1 = MantExpComplex.copy(z);
        }
        z.assign(zs);
        dc.assign(dcs);
    }

    public void eval(MantExpComplex z, MantExpComplex dc, MantExpComplex ddc) {

        MantExpComplex zs = MantExpComplex.create();
        MantExpComplex dcs = MantExpComplex.create();
        MantExpComplex ddcs = MantExpComplex.create();
        MantExpComplex zi = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
        MantExpComplex zi1 = MantExpComplex.create();
        MantExpComplex zi2 = MantExpComplex.create();

        MantExpComplex zsqr = z.square();

        for (int i = 0; i <= m_m; i += 2)
        {
            ddcs.plus_mutable(dbdc[i].times2().times_mutable(zi1).times_mutable(dc)
                    .plus_mutable(b[i].times(zi2.times(dc.square()).times_mutable(new MantExp(i - 1)).plus_mutable(zi1.times(ddc)))).times_mutable(new MantExp(i)))
                    .plus_mutable(ddbdcdc[i].times(zi));
            ddcs.Normalize();

            dcs.plus_mutable(b[i].times(zi1).times_mutable(dc).times_mutable(new MantExp(i))).plus_mutable(dbdc[i].times(zi));
            dcs.Normalize();
            zs.plus_mutable(b[i].times(zi));
            zs.Normalize();
            zi.times_mutable(zsqr);
            zi.Normalize();
            zi1.times_mutable(zsqr);
            zi1.Normalize();
            zi2.times_mutable(zsqr);
            zi2.Normalize();
            if (i == 0) zi1 = MantExpComplex.copy(z);
            if (i == 0) zi2 = MantExpComplex.create(MantExp.ONE, MantExp.ZERO);
        }
        z.assign(zs);
        dc.assign(dcs);
        ddc.assign(ddcs);
    }

    /*public void eval_dz(MantExpComplex z, MantExpComplex dz) {
        MantExpComplex zs = new MantExpComplex();
        MantExpComplex dzs = new MantExpComplex();
        MantExpComplex zi = new MantExpComplex(MantExp.ONE, MantExp.ZERO);
        MantExpComplex zi1 = new MantExpComplex();

        MantExpComplex zsqr = z.square();

        for (int i = 0; i <= m_m; i += 2)
        {
            dzs.plus_mutable(b[i].times(zi1).times_mutable(dz).times_mutable(new MantExp(i)));
            zs.plus_mutable(b[i].times(zi));
            zi.times_mutable(zsqr);
            zi1.times_mutable(zsqr);
            if (i == 0) zi1 = new MantExpComplex(z);
        }
        z.assign(zs);
        dz.assign(dzs);
    }


    void eval(MantExpComplex z, MantExpComplex dz, MantExpComplex dc, MantExpComplex dzdz, MantExpComplex dcdz) {

        MantExpComplex zs = new MantExpComplex();
        MantExpComplex dzs = new MantExpComplex();
        MantExpComplex dcs = new MantExpComplex();
        MantExpComplex dzdzs = new MantExpComplex();
        MantExpComplex dcdzs = new MantExpComplex();
        MantExpComplex zi = new MantExpComplex(MantExp.ONE, MantExp.ZERO);
        MantExpComplex zi1 = new MantExpComplex();
        MantExpComplex zi2 = new MantExpComplex();

        MantExpComplex zsqr = z.square();

        for (int i = 0; i <= m_m; i += 2)
        {
            dcdzs.plus_mutable(b[i].times(zi2).times_mutable(dz).times_mutable(dc).times_mutable(new MantExp(i * (i - 1))))
                    .plus_mutable(b[i].times(zi1).times_mutable(dcdz).times_mutable(new MantExp(i)))
                    .plus_mutable(dbdc[i].times(zi1).times_mutable(dz).times_mutable(new MantExp(i)));

            dzdzs.plus_mutable(b[i].times(zi2).times_mutable(dz.square()).times_mutable(new MantExp(i * (i - 1))))
                    .plus_mutable(b[i].times(zi1).times_mutable(dzdz).times_mutable(new MantExp(i)));

            dcs.plus_mutable(b[i].times(zi1).times_mutable(dc).times_mutable(new MantExp(i))).plus_mutable(dbdc[i].times(zi));

            dzs.plus_mutable(b[i].times(zi1).times_mutable(dz).times_mutable(new MantExp(i)));

            zs.plus_mutable(b[i].times(zi));
            zi.times_mutable(zsqr);
            zi1.times_mutable(zsqr);
            zi2.times_mutable(zsqr);
            if (i == 0) zi1 = new MantExpComplex(z);
            if (i == 0) zi2 = new MantExpComplex(MantExp.ONE, MantExp.ZERO);
        }
        z.assign(zs);
        dz.assign(dzs);
        dc.assign(dcs);
        dzdz.assign(dzdzs);
        dcdz.assign(dcdzs);
    }*/
}
