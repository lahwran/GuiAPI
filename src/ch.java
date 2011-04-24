// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name: SourceFile

public class ch extends cs {

    private cs i;
    protected String a;
    private jz j;
    private static hg l[];

    public ch(cs cs1, jz jz1)
    {
        a = "Options";
        i = cs1;
        j = jz1;
    }

    public void a()
    {
        ml ml1 = ml.a();
        a = ml1.a("options.title");
        int k = 0;
        hg ahg[] = l;
        int i1 = ahg.length;

        for (int j1 = 0; j1 < i1; j1++)
        {
            hg hg1 = ahg[j1];

            if (!hg1.a())
                e.add(((Object)(new z(hg1.c(), (c / 2 - 155) + (k % 2) * 160, d / 6 + 24 * (k >> 1), hg1, j.c(hg1)))));
            else
                e.add(((Object)(new us(hg1.c(), (c / 2 - 155) + (k % 2) * 160, d / 6 + 24 * (k >> 1), hg1, j.c(hg1), j.a(hg1)))));

            k++;
        }
        // TODO: update
        e.add(((Object)(new jk(101, c / 2 - 100, d / 6 + 96, ml1.a("options.video")))));
        // TODO: update
        e.add(((Object)(new jk(100, c / 2 - 100, d / 6 + 120, ml1.a("options.controls")))));
        // TODO: update
        e.add(((Object)(new jk(300, c / 2 - 100, d / 6 + 144, "Global Mod Settings"))));
        e.add(((Object)(new jk(200, c / 2 - 100, d / 6 + 168, ml1.a("gui.done")))));
    }

    protected void a(jk jk1)
    {
        if (!jk1.g) return;

        if (jk1.f < 100 && (jk1 instanceof z))
        {
            j.a(((z)jk1).a(), 1);
            jk1.e = j.c(hg.a(jk1.f));
        }

        if (jk1.f == 101)
        {
            b.y.b();
            b.a(((cs)(new mn(((cs)(this)), j))));
        }

        if (jk1.f == 100)
        {
            b.y.b();
            b.a(((cs)(new tk(((cs)(this)), j))));
        }

        if (jk1.f == 200)
        {
            b.y.b();
            b.a(i);
        }

        if (jk1.f == 300)
        {
            b.y.b();
            ModSettingScreen.guicontext = "";
            WidgetSetting.updateAll();
            GuiModScreen.show(new GuiModSelect(this));
        }
    }

    public void a(int k, int i1, float f)
    {
        i();
        a(g, a, c / 2, 20, 0xffffff);
        super.a(k, i1, f);
    }

    static
    {
        l = (new hg[]{hg.a, hg.b, hg.c, hg.d, hg.j});
    }
}
