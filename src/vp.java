// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import java.io.PrintStream;
import java.util.List;
import net.minecraft.client.Minecraft;

public class vp extends cy {

    private mx a;
    private boolean i;

    public vp(Minecraft minecraft, String s, int j) {
        i = false;
        System.out.println((new StringBuilder()).append("Connecting to ").append(s).append(", ").append(j).toString());
        minecraft.a(((fb) (null)));
        (new wq(this, minecraft, s, j)).start();
        //TODO: update
        ModSettings.setContext("MP"+ModLoader.getMinecraftInstance().z.C.toLowerCase(), "mods/per_server/"+"MP"+ModLoader.getMinecraftInstance().z.C.toLowerCase()+"/");
   
    }

    public void a() {
        if(a != null)
            a.a();
    }

    protected void a(char c, int j) {
    }

    public void b() {
        nd nd1 = nd.a();
        e.clear();
        e.add(((Object) (new ka(0, c / 2 - 100, d / 4 + 120 + 12, nd1.a("gui.cancel")))));
    }

    protected void a(ka ka1) {
        if(ka1.f == 0) {
            i = true;

            if(a != null)
                a.b();

            b.a(((cy) (new fs())));
        }
    }

    public void a(int j, int k, float f) {
        i();
        nd nd1 = nd.a();

        if(a == null) {
            a(g, nd1.a("connect.connecting"), c / 2, d / 2 - 50, 0xffffff);
            a(g, "", c / 2, d / 2 - 10, 0xffffff);
        } else {
            a(g, nd1.a("connect.authorizing"), c / 2, d / 2 - 50, 0xffffff);
            a(g, a.a, c / 2, d / 2 - 10, 0xffffff);
        }

        super.a(j, k, f);
    }

    static mx a(vp vp1, mx mx1) {
        return vp1.a = mx1;
    }

    static boolean a(vp vp1) {
        return vp1.i;
    }

    static mx b(vp vp1) {
        return vp1.a;
    }
}
