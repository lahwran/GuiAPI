import java.util.ArrayList;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;


public class Subscreen extends Button implements Runnable {
	public ArrayList<Widget> children = new ArrayList<Widget>();
	
	public Widget subscreenwindow;
	public Widget subsubWindow;
	public Subscreen(String menutitle, String buttontitle)
	{
		super(buttontitle);
		setTheme("/button");
		subsubWindow = new WidgetClassicTwocolumn();
		subscreenwindow = new WidgetSimplewindow(subsubWindow,menutitle);
		SimpleButtonModel s = new SimpleButtonModel();
		s.addActionCallback(this);
		setModel(s);
	}
	public Subscreen(String menutitle, String buttontitle, Widget subwidget)
	{
		super(buttontitle);
		setTheme("/button");
		subsubWindow = subwidget;
		subscreenwindow = new WidgetSimplewindow(subsubWindow,menutitle);
		SimpleButtonModel s = new SimpleButtonModel();
		s.addActionCallback(this);
		setModel(s);
	}
	public Subscreen(String buttontitle, Widget subwidget)
	{
		super(buttontitle);
		setTheme("/button");
		subsubWindow = subwidget;
		subscreenwindow = subwidget;
		SimpleButtonModel s = new SimpleButtonModel();
		s.addActionCallback(this);
		setModel(s);
	}
	
	public void add(Widget w)
	{
		subsubWindow.add(w);
	}
	
	public void run()
	{
		
		GuiModScreen.show(subscreenwindow); 
	}
	
	
}
