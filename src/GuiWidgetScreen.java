/**
 * TWL Widget that switches out child widgets. the minecraft gui end and twl rendering is managed from GuiModScreen.
 * @author lahwran
 * @version 0.9.5
 */
import java.io.IOException;
import java.util.Date;

import net.minecraft.client.Minecraft;

import org.lwjgl.LWJGLException;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.input.lwjgl.LWJGLInput;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;


public class GuiWidgetScreen extends Widget {
    
    public static GuiWidgetScreen instance;
    
    public GUI gui = null;
    
    public LWJGLRenderer renderer = null;
    public Widget currentwidget = null;
    public ThemeManager theme = null;
    public Minecraft mcinstance; 
    public ScreenScaleProxy screensize = null;
    public static int screenwidth;
    public static int screenheight;
    
    /**
     * get the instance of GuiWidget, creating it if needed
     * @return GuiWidgetScreen singleton
     */
    public static GuiWidgetScreen getInstance()
    {
        if (instance != null)
        {
            return instance;
        }
        else
        {
            try {
                instance = new GuiWidgetScreen();
                instance.renderer = new LWJGLRenderer();
                
                String themename = "twlGuiTheme.xml";
                instance.gui = new GUI(instance, instance.renderer, new LWJGLInput());//,new MCTWLInput());
                ModSettings.dbgout(GuiWidgetScreen.class.getClassLoader().getResource(themename).toString());
            
                instance.theme = ThemeManager.createThemeManager(GuiWidgetScreen.class.getClassLoader().getResource(themename), instance.renderer);
                if(instance.theme == null)
                {
                    throw new RuntimeException("I don't think you installed the theme correctly ...");
                }

                instance.setTheme("");
                instance.gui.applyTheme(instance.theme);
                
                
                instance.mcinstance = ModSettings.getMcinst();
                instance.screensize=new ScreenScaleProxy( instance.mcinstance.d, instance.mcinstance.e);
            
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                RuntimeException e2 =  new RuntimeException("error loading theme");
                e2.initCause(e);
                throw e2;
                
            }
            return instance;
        }
    }
    
    public GuiWidgetScreen()
    {
    }
    
    /**
     * to be called only from GuiModScreen, sets the widget to display. GuiModScreen manages this.
     * @param w widget to display
     */
    public void setScreen(Widget w)
    {
        gui.resyncTimerAfterPause();
        gui.clearKeyboardState();
        gui.clearMouseState();
        removeAllChildren();
        add(w);
        currentwidget = w;
    }
    /**
     * do not call, may be removed next update - not sure if it should even be here :P
     */
    public void resetScreen()
    {
        removeAllChildren();
        currentwidget = null; //doesn't really do much, does it?
    }
    
    /**
     * twl layout callback. simply makes the widget fill the screen. do not call.
     */
    public void layout()
    {
        screensize=new ScreenScaleProxy( mcinstance.d, mcinstance.e);
        if (currentwidget != null)
        {
            screenwidth=screensize.a();
            screenheight=screensize.b();
            currentwidget.setSize(screenwidth,screenheight);
            
            currentwidget.setPosition(0, 0);
        }
    }

}
