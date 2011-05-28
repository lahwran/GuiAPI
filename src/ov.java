// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import java.util.List;
import net.minecraft.client.Minecraft;

public class ov extends cy {

    private int a;
    private int i;

    public ov() {
        a = 0;
        i = 0;
    }

    public void b() {
        a = 0;
        e.clear();
        byte byte0 = -16;
        e.add(((Object) (new ka(1, c / 2 - 100, d / 4 + 120 + byte0, "Save and quit to title"))));

        if(b.l())
            ((ka)e.get(0)).e = "Disconnect";

        e.add(((Object) (new ka(4, c / 2 - 100, d / 4 + 24 + byte0, "Back to game"))));
        e.add(((Object) (new ka(0, c / 2 - 100, d / 4 + 96 + byte0, "Options..."))));
        e.add(((Object) (new ka(5, c / 2 - 100, d / 4 + 48 + byte0, 98, 20, dm.a("gui.achievements")))));
        e.add(((Object) (new ka(6, c / 2 + 2, d / 4 + 48 + byte0, 98, 20, dm.a("gui.stats")))));
        // TODO: update
        e.add(((Object) (new ka(15, c / 2 - 100, d / 4 + byte0 + 144, "World Mod Options"))));
    }

    protected void a(ka ka1) {
        if(ka1.f == 0)
            b.a(((cy) (new cm(((cy) (this)), b.z))));

        if(ka1.f == 1) {
            b.I.a(ji.j, 1);

            if(b.l())
                b.f.q();

            b.a(((fb) (null)));
            b.a(((cy) (new fs())));
        }

        if(ka1.f == 4) {
            b.a(((cy) (null)));
            b.g();
        }

        if(ka1.f == 5)
            b.a(((cy) (new xd(b.I))));

        if(ka1.f == 6)
            b.a(((cy) (new dt(((cy) (this)), b.I))));
        // TODO: update
        if (ka1.f == 15)
        {
            ModSettingScreen.guicontext = ModSettings.currentContext;
            WidgetSetting.updateAll();
            GuiModScreen.show(new GuiModSelect(this));
        }
    }

    public void a() {
        super.a();
        i++;
    }

    public void a(int j, int k, float f) {
        i();
        boolean flag = !b.f.a(a++);

        if(flag || i < 20) {
            float f1 = ((float)(i % 10) + f) / 10F;
            f1 = ik.a(f1 * 3.141593F * 2.0F) * 0.2F + 0.8F;
            int l = (int)(255F * f1);
            b(g, "Saving level..", 8, d - 16, l << 16 | l << 8 | l);
        }

        a(g, "Game menu", c / 2, 40, 0xffffff);
        super.a(j, k, f);
    }
}
