// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;

public class rl extends cy {

    private final DateFormat j = new SimpleDateFormat();
    protected cy a;
    protected String i;
    private boolean l;
    private int m;
    private List n;
    private mb o;
    private String p;
    private String q;
    private boolean r;
    private ka s;
    private ka t;
    private ka u;

    public rl(cy cy1) {
        i = "Select world";
        l = false;
        a = cy1;
    }

    public void b() {
        nd nd1 = nd.a();
        i = nd1.a("selectWorld.title");
        p = nd1.a("selectWorld.world");
        q = nd1.a("selectWorld.conversion");
        l();
        o = new mb(this);
        o.a(e, 4, 5);
        k();
    }

    private void l() {
        nh nh1 = b.c();
        n = nh1.b();
        Collections.sort(n);
        m = -1;
    }

    protected String c(int i1) {
        return ((ut)n.get(i1)).a();
    }

    protected String d(int i1) {
        String s1 = ((ut)n.get(i1)).b();

        if(s1 == null || ik.a(s1)) {
            nd nd1 = nd.a();
            s1 = (new StringBuilder()).append(nd1.a("selectWorld.world")).append(" ").append(i1 + 1).toString();
        }

        return s1;
    }

    public void k() {
        nd nd1 = nd.a();
        e.add(((Object) (t = new ka(1, c / 2 - 154, d - 52, 150, 20, nd1.a("selectWorld.select")))));
        e.add(((Object) (s = new ka(6, c / 2 - 154, d - 28, 70, 20, nd1.a("selectWorld.rename")))));
        e.add(((Object) (u = new ka(2, c / 2 - 74, d - 28, 70, 20, nd1.a("selectWorld.delete")))));
        e.add(((Object) (new ka(3, c / 2 + 4, d - 52, 150, 20, nd1.a("selectWorld.create")))));
        e.add(((Object) (new ka(0, c / 2 + 4, d - 28, 150, 20, nd1.a("gui.cancel")))));
        t.g = false;
        s.g = false;
        u.g = false;
    }

    protected void a(ka ka1) {
        if(!ka1.g)
            return;

        if(ka1.f == 2) {
            String s1 = d(m);

            if(s1 != null) {
                r = true;
                nd nd1 = nd.a();
                String s2 = nd1.a("selectWorld.deleteQuestion");
                String s3 = (new StringBuilder()).append("'").append(s1).append("' ").append(nd1.a("selectWorld.deleteWarning")).toString();
                String s4 = nd1.a("selectWorld.deleteButton");
                String s5 = nd1.a("gui.cancel");
                qo qo1 = new qo(((cy) (this)), s2, s3, s4, s5, m);
                b.a(((cy) (qo1)));
            }
        } else if(ka1.f == 1)
            e(m);
        else if(ka1.f == 3)
            b.a(((cy) (new fh(((cy) (this))))));
        else if(ka1.f == 6)
            b.a(((cy) (new jh(((cy) (this)), c(m)))));
        else if(ka1.f == 0)
            b.a(a);
        else
            o.a(ka1);
    }

    public void e(int i1) {
        b.a(((cy) (null)));

        if(l)
            return;

        l = true;
        b.c = ((nx) (new oo(b)));
        String s1 = c(i1);

        if(s1 == null)
            s1 = (new StringBuilder()).append("World").append(i1).toString();

        b.a(s1, d(i1), 0L);
        b.a(((cy) (null)));
        
        //TODO: update
        ModSettings.setContext("SP"+b.f.x.j(), "saves/"+b.f.x.j()+"/mods/");
    }

    public void a(boolean flag, int i1) {
        if(r) {
            r = false;

            if(flag) {
                nh nh1 = b.c();
                nh1.c();
                nh1.c(c(i1));
                l();
            }

            b.a(((cy) (this)));
        }
    }

    public void a(int i1, int j1, float f1) {
        o.a(i1, j1, f1);
        a(g, i, c / 2, 20, 0xffffff);
        super.a(i1, j1, f1);
    }

    static List a(rl rl1) {
        return rl1.n;
    }

    static int a(rl rl1, int i1) {
        return rl1.m = i1;
    }

    static int b(rl rl1) {
        return rl1.m;
    }

    static ka c(rl rl1) {
        return rl1.t;
    }

    static ka d(rl rl1) {
        return rl1.s;
    }

    static ka e(rl rl1) {
        return rl1.u;
    }

    static String f(rl rl1) {
        return rl1.p;
    }

    static DateFormat g(rl rl1) {
        return rl1.j;
    }

    static String h(rl rl1) {
        return rl1.q;
    }
}
