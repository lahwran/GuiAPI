// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import java.util.List;
import net.minecraft.client.Minecraft;

public class co extends da {

    private da i;
    protected String a;
    private kv j;
    private static ht l[];

    public co(da da1, kv kv1) {
        a = "Options";
        i = da1;
        j = kv1;
    }

    public void b() {
        nh nh1 = nh.a();
        a = nh1.a("options.title");
        int k = 0;
        ht aht[] = l;
        int i1 = aht.length;

        for(int j1 = 0; j1 < i1; j1++) {
            ht ht1 = aht[j1];

            if(!ht1.a())
                e.add(((Object) (new ab(ht1.c(), (c / 2 - 155) + (k % 2) * 160, d / 6 + 24 * (k >> 1), ht1, j.c(ht1)))));
            else
                e.add(((Object) (new vz(ht1.c(), (c / 2 - 155) + (k % 2) * 160, d / 6 + 24 * (k >> 1), ht1, j.c(ht1), j.a(ht1)))));

            k++;
        }

        // TODO: update (d / 6 + 96 + 12)
        e.add(((Object) (new ke(101, c / 2 - 100, d / 6 + 96, nh1.a("options.video")))));
        // TODO: update (d / 6 + 120 + 12)
        e.add(((Object) (new ke(100, c / 2 - 100, d / 6 + 120, nh1.a("options.controls")))));
        // TODO: update (new)
        e.add(((Object) (new ke(300, c / 2 - 100, d / 6 + 144, "Global Mod Settings"))));
        e.add(((Object) (new ke(200, c / 2 - 100, d / 6 + 168, nh1.a("gui.done")))));
    }

    protected void a(ke ke1) {
        if(!ke1.g)
            return;

        if(ke1.f < 100 && (ke1 instanceof ab)) {
            j.a(((ab)ke1).a(), 1);
            ke1.e = j.c(ht.a(ke1.f));
        }

        if(ke1.f == 101) {
            b.z.b();
            b.a(((da) (new nj(((da) (this)), j))));
        }

        if(ke1.f == 100) {
            b.z.b();
            b.a(((da) (new up(((da) (this)), j))));
        }

        if(ke1.f == 200) {
            b.z.b();
            b.a(i);
        }
        
        //TODO: update
        if (ke1.f == 300)
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
        l = (new ht[] {
                 ht.a, ht.b, ht.c, ht.d, ht.j
             });
    }
}
