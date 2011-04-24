// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name: SourceFile

public class oa extends cs {

    private int a;
    private int i;

    public oa()
    {
        a = 0;
        i = 0;
    }

    public void a()
    {
        a = 0;
        e.clear();
        byte byte0 = -16;
        e.add(((Object)(new jk(1, c / 2 - 100, d / 4 + 120 + byte0, "Save and quit to title"))));

        if (b.k()) ((jk)e.get(0)).e = "Disconnect";

        e.add(((Object)(new jk(4, c / 2 - 100, d / 4 + 24 + byte0, "Back to game"))));
        e.add(((Object)(new jk(0, c / 2 - 100, d / 4 + 96 + byte0, "Options..."))));
        e.add(((Object)(new jk(5, c / 2 - 100, d / 4 + 48 + byte0, 98, 20, dg.a("gui.achievements")))));
        e.add(((Object)(new jk(6, c / 2 + 2, d / 4 + 48 + byte0, 98, 20, dg.a("gui.stats")))));
        // TODO: update
        e.add(((Object)(new jk(15, c / 2 - 100, d / 4 + byte0 + 144, "World Mod Options"))));
    }

    protected void a(jk jk1)
    {
        if (jk1.f == 0) b.a(((cs)(new ch(((cs)(this)), b.y))));

        if (jk1.f == 1)
        {
            b.G.a(is.j, 1);

            if (b.k()) b.e.q();

            b.a(((et)(null)));
            b.a(((cs)(new fh())));
        }

        if (jk1.f == 4)
        {
            b.a(((cs)(null)));
            b.f();
        }

        if (jk1.f == 5) b.a(((cs)(new wc(b.G))));

        if (jk1.f == 6) b.a(((cs)(new dn(((cs)(this)), b.G))));
        // TODO: update
        if (jk1.f == 15)
        {
            ModSettingScreen.guicontext = ModSettings.currentContext;
            WidgetSetting.updateAll();
            GuiModScreen.show(new GuiModSelect(this));
        }
    }

    public void g()
    {
        super.g();
        i++;
    }

    public void a(int j, int k, float f)
    {
        i();
        boolean flag = !b.e.a(a++);

        if (flag || i < 20)
        {
            float f1 = ((float)(i % 10) + f) / 10F;
            f1 = hy.a(f1 * 3.141593F * 2.0F) * 0.2F + 0.8F;
            int l = (int)(255F * f1);
            b(g, "Saving level..", 8, d - 16, l << 16 | l << 8 | l);
        }

        a(g, "Game menu", c / 2, 40, 0xffffff);
        super.a(j, k, f);
    }
}
