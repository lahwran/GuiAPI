/**
 * GuiModScreen is the minecraft screen subclass that controls and renders TWL.
 * normally you will want to call it's static methods to use it, though
 * subclassing it and/or instantiating it
 * are also possible. however, to do so would use unsafe api (I still might
 * change things.)
 * 
 * @author lahwran
 * @version 0.9.5
 * @see show
 */

import net.minecraft.client.Minecraft;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.renderer.lwjgl.RenderScale;

public class GuiModScreen extends cy {
    /**
     * reference to parent screen, is used to go back()
     * @see back()
     */
    public cy parentScreen;
    public boolean drawbg = true;
    /**
     * actual main widget of this guiscreen
     */
    public Widget mainwidget;

    /*
     * public static int lastMouseX=0;
     * public static int lastMouseY=0;
     * public static boolean mousepressed[];
     * 
     * static {
     * mousepressed=new boolean[Mouse.getButtonCount()];
     * for(int i=0;i<Mouse.getButtonCount();i++)
     * {
     * mousepressed[i]=false;
     * }
     * }
     */
    /**
     * used by static methods.
     */
    public static GuiModScreen currentscreen;

    /**
     * only use this constructor from subclasses. does not take a widget, so
     * that the subclass
     * can build the widget before storing it. put your main widget in
     * mainwidget, of course.
     * @param by1 parent screen
     */
    protected GuiModScreen(cy by1)
    {
        parentScreen = by1;
        currentscreen = this;
        f = false;
    }

    /**
     * main constructor, to be used if you are instantiating this class.
     * @param by1 parent screen - make sure this is right!
     * @param w main widget to display
     */
    public GuiModScreen(cy by1, Widget w)
    {
        mainwidget = w;
        parentScreen = by1;
        currentscreen = this;
        f = false;
    }

    /**
     * hide current screen and show parent screen.
     */
    public static void back()
    {
        if (currentscreen != null)
        {
            Minecraft m = ModLoader.getMinecraftInstance();
            m.a(currentscreen.parentScreen);
            if (currentscreen.parentScreen instanceof GuiModScreen)
            {
                currentscreen = (GuiModScreen)currentscreen.parentScreen;
                currentscreen.setActive();
            }
            else
                currentscreen = null;
        }
    }

    /**
     * show a screen - TWL widget version. this is the recommended way to show a
     * TWL widget as a screen.
     * @param screen widget to show - will be sized to size of screen when twl
     *            was started.
     */
    public static void show(Widget screen)
    {
        Minecraft m = ModLoader.getMinecraftInstance();
        GuiModScreen scr = new GuiModScreen(currentscreen, screen);
        m.a(scr);
        scr.setActive();
    }

    /**
     * show a screen - GuiModScreen version. show an instance of GuiModScreen -
     * does not set the parent screen,
     * you have to deal with that yourself!
     * @param screen GuiModScreen instance or subclass to show with parent
     *            screen already set to current screen.
     */
    public static void show(GuiModScreen screen)
    {
        // screen.parentScreen = currentscreen; // >8|
        Minecraft m = ModLoader.getMinecraftInstance();
        m.a(screen);
        screen.setActive();
    }

    /**
     * play a click sound. call after the user performs an action. already
     * called from setting widgets.
     */
    public static void clicksound()
    {
        Minecraft m = ModLoader.getMinecraftInstance();
        m.B.a("random.click", 1.0F, 1.0F);

    }

    /**
     * internal - actually sets the TWL screen.
     */
    private void setActive()
    {
        GuiWidgetScreen.getInstance().setScreen(mainwidget);
    }

    // protected void a(int x, int y, int button){}
    private int t = 0;
    
    //handleInput - is empty as this is where input is normally handled and we handle it elsewhere
    public void e(){}
    
    public void a(int j, int k, float f)
    {
        if (drawbg) i();// render default background
        // GL11.glClearColor(0.96f, 0.97f, 1.0f, 1.0f);
        // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // mouse doesn't like to respond unless we do this
        /*
         * if(t==20)
         * {
         * System.out.println(j);
         * System.out.println(Mouse.getX());
         * System.out.println(k);
         * System.out.println(Mouse.getY());
         * 
         * System.out.println(Mouse.isButtonDown(0));
         * t=0;
         * }
         * else
         * {
         * t++;
         * }
         */
        // LWJGLRenderer r = (LWJGLRenderer)
        // GuiWidgetScreen.getInstance().gui.getRenderer();
        // r.syncViewportSize();
        // GuiWidgetScreen.getInstance().layout();
        // for (int i=0; i<Mouse.getButtonCount(); i++)
        // GuiWidgetScreen.getInstance().gui.handleMouse(Mouse.getX(),
        // GuiWidgetScreen.screenheight-Mouse.getY(), i, Mouse.isButtonDown(i));
        /*
         * GUI gui = GuiWidgetScreen.getInstance().gui;
         * int wheelDelta = Mouse.getDWheel();
         * if(wheelDelta != 0) {
         * gui.handleMouseWheel(wheelDelta / 120);
         * }
         * int curMouseX = Mouse.getX();
         * int curMouseY = Mouse.getY();
         * for(int i = 0; i < Mouse.getButtonCount(); i++)
         * {
         * if (mousepressed[i] != Mouse.isButtonDown(i))
         * {
         * mousepressed[i] = Mouse.isButtonDown(i);
         * 
         * 
         * //System.out.println("Mouse button state change "+i+" to "+mousepressed
         * [i]);
         * if(mousepressed[i])
         * {
         * gui.handleMouse(curMouseX, gui.getHeight() - curMouseY - 1, i,
         * mousepressed[i]);
         * gui.handleMouse(curMouseX, gui.getHeight() - curMouseY - 1, i,
         * !mousepressed[i]);
         * }
         * }
         * }
         * 
         * if ((lastMouseX != curMouseX || lastMouseY != curMouseY))
         * {
         * //Mouse.
         * gui.handleMouse(curMouseX, gui.getHeight() - curMouseY - 1,-1,
         * false);
         * }
         * while(Mouse.next());
         */
        LWJGLRenderer r = (LWJGLRenderer)GuiWidgetScreen.getInstance().gui.getRenderer();

        ScreenScaleProxy screensize = new ScreenScaleProxy(GuiWidgetScreen.getInstance().mcinstance.d, GuiWidgetScreen.getInstance().mcinstance.e);

        //int width = GuiWidgetScreen.getInstance().mcinstance.d / screensize.c;
        //int height = GuiWidgetScreen.getInstance().mcinstance.e / screensize.c;
        //int posy = GuiWidgetScreen.getInstance().mcinstance.e - height;
        // GL11.glViewport(0, posy, width, height);
        // System.out.println(""+width+" "+height+" "+screensize.a()+" "+screensize.b());

        // GL11.glViewport(0, 0, width, height);
        // GL11.glViewport(0, posy, width, height);
        // GL11.glScalef(2.0f, 2.0f, 2.0f);

        RenderScale.scale = screensize.c;
        r.syncViewportSize();
        // GL11.glViewport(0, 0, GuiWidgetScreen.getInstance().mcinstance.c,
        // GuiWidgetScreen.getInstance().mcinstance.d);
        // GuiWidgetScreen.getInstance().layout();
        GuiWidgetScreen.getInstance().gui.update();

        /*
         * width=screensize.a()*screensize.a;
         * height=screensize.b()*screensize.a;
         * GL11.glViewport(0, 0, width, height);
         */

    }
    /*
     * //input handling
     * public void e()
     * {
     * 
     * GuiWidgetScreen.getInstance().gui.handleMouse(
     * Mouse.getEventX(), GuiWidgetScreen.getInstance().gui.getHeight() -
     * Mouse.getEventY() - 1,
     * Mouse.getEventButton(), Mouse.getEventButtonState());
     * 
     * int wheelDelta = Mouse.getEventDWheel();
     * if(wheelDelta != 0) {
     * GuiWidgetScreen.getInstance().gui.handleMouseWheel(wheelDelta / 120);
     * }
     * /*
     * ArrayList<Object> E = new ArrayList<Object>();
     * E.add(new Integer(Mouse.getEventX()));
     * E.add(new Integer(Mouse.getEventY()));
     * E.add(new Integer(Mouse.getEventButton()));
     * E.add(new Boolean(Mouse.getEventButtonState()));
     * int wheeldelta = Mouse.getEventDWheel();
     * if(wheeldelta>0)
     * E.add(new Integer(wheeldelta));
     * MCTWLInput.instance.MouseEvents.add(E);* /
     * }
     * public void f()
     * {
     * GuiWidgetScreen.getInstance().gui.handleKey(
     * Keyboard.getEventKey(),
     * Keyboard.getEventCharacter(),
     * Keyboard.getEventKeyState());
     * /*
     * ArrayList<Object> E = new ArrayList<Object>();
     * E.add(new Integer(Keyboard.getEventKey()));
     * E.add(new Character(Keyboard.getEventCharacter()));
     * E.add(new Boolean(Keyboard.getEventKeyState()));
     * MCTWLInput.instance.KeyEvents.add(E);* /
     * }
     */

}
