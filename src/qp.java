// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class qp extends cs {

    private final DateFormat j = new SimpleDateFormat();
    protected cs a;
    protected String i;
    private boolean l;
    private int m;
    private List n;
    private lj o;
    private String p;
    private String q;
    private boolean r;
    private jk s;
    private jk t;
    private jk u;

    public qp(cs cs1) {
        i = "Select world";
        l = false;
        a = cs1;
    }

    public void a() {
        ml ml1 = ml.a();
        i = ml1.a("selectWorld.title");
        p = ml1.a("selectWorld.world");
        q = ml1.a("selectWorld.conversion");
        l();
        o = new lj(this);
        o.a(e, 4, 5);
        k();
    }

    private void l() {
        mp mp1 = b.c();
        n = mp1.b();
        Collections.sort(n);
        m = -1;
    }

    protected String c(int i1) {
        return ((tu)n.get(i1)).a();
    }

    protected String d(int i1) {
        String s1 = ((tu)n.get(i1)).b();

        if(s1 == null || hy.a(s1)) {
            ml ml1 = ml.a();
            s1 = (new StringBuilder()).append(ml1.a("selectWorld.world")).append(" ").append(i1 + 1).toString();
        }

        return s1;
    }

    public void k() {
        ml ml1 = ml.a();
        e.add(((Object) (t = new jk(1, c / 2 - 154, d - 52, 150, 20, ml1.a("selectWorld.select")))));
        e.add(((Object) (s = new jk(6, c / 2 - 154, d - 28, 70, 20, ml1.a("selectWorld.rename")))));
        e.add(((Object) (u = new jk(2, c / 2 - 74, d - 28, 70, 20, ml1.a("selectWorld.delete")))));
        e.add(((Object) (new jk(3, c / 2 + 4, d - 52, 150, 20, ml1.a("selectWorld.create")))));
        e.add(((Object) (new jk(0, c / 2 + 4, d - 28, 150, 20, ml1.a("gui.cancel")))));
        t.g = false;
        s.g = false;
        u.g = false;
    }

    protected void a(jk jk1) {
        if(!jk1.g)
            return;

        if(jk1.f == 2) {
            String s1 = d(m);

            if(s1 != null) {
                r = true;
                ml ml1 = ml.a();
                String s2 = ml1.a("selectWorld.deleteQuestion");
                String s3 = (new StringBuilder()).append("'").append(s1).append("' ").append(ml1.a("selectWorld.deleteWarning")).toString();
                String s4 = ml1.a("selectWorld.deleteButton");
                String s5 = ml1.a("gui.cancel");
                pt pt1 = new pt(((cs) (this)), s2, s3, s4, s5, m);
                b.a(((cs) (pt1)));
            }
        } else if(jk1.f == 1)
            e(m);
        else if(jk1.f == 3)
            b.a(((cs) (new ez(((cs) (this))))));
        else if(jk1.f == 6)
            b.a(((cs) (new ir(((cs) (this)), c(m)))));
        else if(jk1.f == 0)
            b.a(a);
        else
            o.a(jk1);
    }

    public void e(int i1) {
        b.a(((cs) (null)));

        if(l)
            return;

        l = true;
        b.b = ((nf) (new nu(b)));
        String s1 = c(i1);

        if(s1 == null)
            s1 = (new StringBuilder()).append("World").append(i1).toString();

        b.a(s1, d(i1), 0L);
        b.a(((cs) (null)));
        
        //TODO: update
        ModSettings.setContext("SP"+b.e.s.j(), "saves/"+b.e.s.j()+"/mods/");
    }

    public void a(boolean flag, int i1) {
        if(r) {
            r = false;

            if(flag) {
                mp mp1 = b.c();
                mp1.c();
                mp1.c(c(i1));
                l();
            }

            b.a(((cs) (this)));
        }
    }

    public void a(int i1, int j1, float f1) {
        o.a(i1, j1, f1);
        a(g, i, c / 2, 20, 0xffffff);
        super.a(i1, j1, f1);
    }

    static List a(qp qp1) {
        return qp1.n;
    }

    static int a(qp qp1, int i1) {
        return qp1.m = i1;
    }

    static int b(qp qp1) {
        return qp1.m;
    }

    static jk c(qp qp1) {
        return qp1.t;
    }

    static jk d(qp qp1) {
        return qp1.s;
    }

    static jk e(qp qp1) {
        return qp1.u;
    }

    static String f(qp qp1) {
        return qp1.p;
    }

    static DateFormat g(qp qp1) {
        return qp1.j;
    }

    static String h(qp qp1) {
        return qp1.q;
    }
}
