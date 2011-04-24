// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst safe
// Source File Name:   SourceFile

import net.minecraft.client.Minecraft;

public class uq extends cs {

    private mg a;
    private boolean i;

    public uq(Minecraft minecraft, String s, int j) {
        i = false;
        minecraft.a(((et) (null)));
        (new vq(this, minecraft, s, j)).start();
        //TODO: update
        ModSettings.setContext("MP"+ModLoader.getMinecraftInstance().y.C.toLowerCase(), "mods/per_server/"+"MP"+ModLoader.getMinecraftInstance().y.C.toLowerCase()+"/");
   }

    public void g() {
        if(a != null)
            a.a();
    }

    protected void a(char c, int j) {
    }

    public void a() {
        ml ml1 = ml.a();
        e.clear();
        e.add(((Object) (new jk(0, c / 2 - 100, d / 4 + 120 + 12, ml1.a("gui.cancel")))));
    }

    protected void a(jk jk1) {
        if(jk1.f == 0) {
            i = true;

            if(a != null)
                a.b();

            b.a(((cs) (new fh())));
        }
    }

    public void a(int j, int k, float f) {
        i();
        ml ml1 = ml.a();

        if(a == null) {
            a(g, ml1.a("connect.connecting"), c / 2, d / 2 - 50, 0xffffff);
            a(g, "", c / 2, d / 2 - 10, 0xffffff);
        } else {
            a(g, ml1.a("connect.authorizing"), c / 2, d / 2 - 50, 0xffffff);
            a(g, a.a, c / 2, d / 2 - 10, 0xffffff);
        }

        super.a(j, k, f);
    }

    static mg a(uq uq1, mg mg1) {
        return uq1.a = mg1;
    }

    static boolean a(uq uq1) {
        return uq1.i;
    }

    static mg b(uq uq1) {
        return uq1.a;
    }
}
