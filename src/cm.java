// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import java.util.List;
import net.minecraft.client.Minecraft;

public class cm extends cy {

    private cy i;
    protected String a;
    private kr j;
    private static hr l[];

    public cm(cy cy1, kr kr1) {
        a = "Options";
        i = cy1;
        j = kr1;
    }

    public void b() {
        nd nd1 = nd.a();
        a = nd1.a("options.title");
        int k = 0;
        hr ahr[] = l;
        int i1 = ahr.length;

        for(int j1 = 0; j1 < i1; j1++) {
            hr hr1 = ahr[j1];

            if(!hr1.a())
                e.add(((Object) (new aa(hr1.c(), (c / 2 - 155) + (k % 2) * 160, d / 6 + 24 * (k >> 1), hr1, j.c(hr1)))));
            else
                e.add(((Object) (new vr(hr1.c(), (c / 2 - 155) + (k % 2) * 160, d / 6 + 24 * (k >> 1), hr1, j.c(hr1), j.a(hr1)))));

            k++;
        }

        // TODO: update (d / 6 + 96 + 12)
        e.add(((Object) (new ka(101, c / 2 - 100, d / 6 + 96, nd1.a("options.video")))));
        // TODO: update (d / 6 + 120 + 12)
        e.add(((Object) (new ka(100, c / 2 - 100, d / 6 + 120, nd1.a("options.controls")))));
        // TODO: update (new)
        e.add(((Object) (new ka(300, c / 2 - 100, d / 6 + 144, "Global Mod Settings"))));
        e.add(((Object) (new ka(200, c / 2 - 100, d / 6 + 168, nd1.a("gui.done")))));
    }

    protected void a(ka ka1) {
        if(!ka1.g)
            return;

        if(ka1.f < 100 && (ka1 instanceof aa)) {
            j.a(((aa)ka1).a(), 1);
            ka1.e = j.c(hr.a(ka1.f));
        }

        if(ka1.f == 101) {
            b.z.b();
            b.a(((cy) (new nf(((cy) (this)), j))));
        }

        if(ka1.f == 100) {
            b.z.b();
            b.a(((cy) (new uj(((cy) (this)), j))));
        }

        if(ka1.f == 200) {
            b.z.b();
            b.a(i);
        }
        
        //TODO: update
        if (ka1.f == 300)
        {
            b.z.b();
            ModSettingScreen.guicontext = "";
            WidgetSetting.updateAll();
            GuiModScreen.show(new GuiModSelect(this));
        }
    }

    public void a(int k, int i1, float f) {
        i();
        a(g, a, c / 2, 20, 0xffffff);
        super.a(k, i1, f);
    }

    static {
        l = (new hr[] {
                 hr.a, hr.b, hr.c, hr.d, hr.j
             });
    }
}
