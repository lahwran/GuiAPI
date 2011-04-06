/**
 * class that GuiModSelect uses to show subscreens. instantiate to add your own subscreen.
 * @author lahwran
 * @version 0.9.5
 */
import java.util.ArrayList;

import de.matthiasmann.twl.Widget;


public class ModSettingScreen {
	/**
	 * actual list of mod screens
	 */
	
	public static ArrayList<ModSettingScreen> modscreens = new ArrayList<ModSettingScreen>();
	public static String guicontext = "";
	
	/**
	 * the main widget to pass into GuiModScreen.show()
	 */
	public Widget thewidget;
	/**
	 * the twocolumn widget to show the child widgets in
	 */
	public WidgetClassicTwocolumn w;
	
	/**
	 * title to show on button to this modscreen
	 */
	public String buttontitle;
	/**
	 * name to show at top of screen
	 */
	public String nicename;
	/**
	 * convenience constructor for when you want to show the same name on the button and screen title
	 * @param name mod nice name
	 */
	public ModSettingScreen(String name)
	{
		this(name, name);
	}
	
	/**
	 * 
	 * @param _nicename screen title
	 * @param _buttontitle button-to-screen title
	 */
	public ModSettingScreen(String _nicename, String _buttontitle)
	{
		modscreens.add(this); //// is this a bad idea?
		
		buttontitle=_buttontitle;
		nicename = _nicename;
		
		w = new WidgetClassicTwocolumn(); 
		
		
		thewidget = new WidgetClassicWindow(w, nicename);
		
		
	}
	public ModSettingScreen(Widget _w, String _buttontitle)
	{
		modscreens.add(this);
		buttontitle=_buttontitle;
		thewidget = _w;
	}
	/**
	 * add a widget
	 * @param newwidget the widget to add
	 */
	public void append(Widget newwidget)
	{
		if(w != null)
			w.add(newwidget);
		else
			thewidget.add(newwidget);
	}
	
	/**
	 * remove a widget
	 * @param child widget to remove
	 */
	public void remove(Widget child)
	{
		if(w != null)
			w.removeChild(child);
		else
			thewidget.removeChild(child);
	}
	
	
	
}
