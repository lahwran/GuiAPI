package sharose.mods.guiapi;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.lwjgl.opengl.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.input.lwjgl.LWJGLInput;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 * TWL Widget that switches out child widgets. the Minecraft gui end and twl
 * rendering is managed from GuiModScreen.
 * 
 * @author lahwran
 */
public class GuiWidgetScreen extends Widget {
	/**
	 * The initialized instance of GuiWidgetScreen.
	 */
	public static GuiWidgetScreen instance;
	/**
	 * The height of the screen that the widget will render on.
	 */
	public static int screenheight;
	/**
	 * The width of the screen that the widget will render on.
	 */
	public static int screenwidth;

	public static URL themeURL;

	/**
	 * get the instance of GuiWidget, creating it if needed
	 * 
	 * @return GuiWidgetScreen singleton
	 */
	public static GuiWidgetScreen getInstance() {
		if (GuiWidgetScreen.instance != null) {
			return GuiWidgetScreen.instance;
		}

		try {
			Util.checkGLError();
			GuiWidgetScreen.instance = new GuiWidgetScreen();
			Util.checkGLError();
			GuiWidgetScreen.instance.renderer = new LWJGLRenderer();
			Util.checkGLError();
			String themename = "twlGuiTheme.xml";
			Util.checkGLError();
			GuiWidgetScreen.instance.gui = new GUI(GuiWidgetScreen.instance,
					GuiWidgetScreen.instance.renderer, new LWJGLInput());
			Util.checkGLError();
			GuiWidgetScreen.themeURL = new URL("classloader","",-1,themename,new URLStreamHandler(){
				@Override
				protected URLConnection openConnection(URL paramURL) throws IOException {
					String file = paramURL.getFile();
					if(file.startsWith("/")) { file = file.substring(1); }
					return GuiWidgetScreen.class.getClassLoader().getResource(file).openConnection();
				}
			});
			Util.checkGLError();
			//GuiWidgetScreen.themeURL = new URL("file:\\G:\\MineCraft\\GitHub\\GuiAPI\\theme\\twlGuiTheme.xml");
			Util.checkGLError();
			GuiWidgetScreen.instance.theme = ThemeManager
					.createThemeManager(GuiWidgetScreen.themeURL,
							GuiWidgetScreen.instance.renderer);
			Util.checkGLError();
			if (GuiWidgetScreen.instance.theme == null) {
				throw new RuntimeException(
						"I don't think you installed the theme correctly ...");
			}
			GuiWidgetScreen.instance.setTheme("");
			GuiWidgetScreen.instance.gui
					.applyTheme(GuiWidgetScreen.instance.theme);
			GuiWidgetScreen.instance.minecraftInstance = ModSettings
					.getMcinst();
			GuiWidgetScreen.instance.screenSize = new ScaledResolution(
					GuiWidgetScreen.instance.minecraftInstance.gameSettings,
					GuiWidgetScreen.instance.minecraftInstance.displayWidth,
					GuiWidgetScreen.instance.minecraftInstance.displayHeight);
		} catch (Throwable e) {
			e.printStackTrace();
			RuntimeException e2 = new RuntimeException("error loading theme");
			e2.initCause(e);
			throw e2;
		}
		return GuiWidgetScreen.instance;
	}

	/**
	 * The widget that is currently displayed.
	 */
	public Widget currentWidget = null;
	/**
	 * This is a reference to a TWL class that is used to render the widgets.
	 */
	public GUI gui = null;
	/**
	 * This is a reference to Minecraft.
	 */
	public Minecraft minecraftInstance;
	/**
	 * This is the rendered used by TWL.
	 */
	public LWJGLRenderer renderer = null;
	/**
	 * This is the ScaledResolution class that is used to scale all of the
	 * widgets.
	 */
	public ScaledResolution screenSize = null;
	/**
	 * This the the ThemeManager for GuiAPI.
	 */
	public ThemeManager theme = null;

	/**
	 * This creates a new instance of GuiWidgetScreen. It should only be used
	 * internally. Please use the static method getInstance() instead.
	 */
	public GuiWidgetScreen() {
	}

	@Override
	public void layout() {
		screenSize = new ScaledResolution(minecraftInstance.gameSettings,
				minecraftInstance.displayWidth, minecraftInstance.displayHeight);
		if (currentWidget != null) {
			GuiWidgetScreen.screenwidth = screenSize.getScaledWidth();
			GuiWidgetScreen.screenheight = screenSize.getScaledHeight();
			currentWidget.setSize(GuiWidgetScreen.screenwidth,
					GuiWidgetScreen.screenheight);
			currentWidget.setPosition(0, 0);
		}
	}

	/**
	 * Removes all children and clears the current widget.
	 */
	public void resetScreen() {
		removeAllChildren();
		currentWidget = null;
	}

	/**
	 * to be called only from GuiModScreen, sets the widget to display.
	 * GuiModScreen manages this.
	 * 
	 * @param widget
	 *            widget to display
	 */
	public void setScreen(Widget widget) {
		gui.resyncTimerAfterPause();
		gui.clearKeyboardState();
		gui.clearMouseState();
		removeAllChildren();
		add(widget);
		GuiApiFontHelper.resyncCustomFonts();

		currentWidget = widget;
	}
}
