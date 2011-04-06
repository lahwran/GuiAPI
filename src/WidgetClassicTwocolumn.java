import de.matthiasmann.twl.Widget;


public class WidgetClassicTwocolumn extends Widget {
	public int defaultwidth = 150;
	public int defaultheight = 20;
	public int defaultpad = 4;
	public boolean overridewidth = true;
	public boolean overrideheight = true;
	
	public WidgetClassicTwocolumn(Widget... ws)
	{
		for(int i=0; i<ws.length; i++)
			add(ws[i]); 
		setTheme("");
		//do stuff here?
	}
	/*
	public void add(Widget child)
	{
		String T=child.getTheme();
		if (T.length() == 0)
			child.setTheme("/-defaults");
		else if(!T.substring(0, 1).equals("/"))
			child.setTheme("/"+T);
		super.add(child);
	}*/
	public int split = 10;
	@Override
	public void layout()
	{
		
		for(int i=0; i<getNumChildren(); i++)
		{
			
			Widget w = getChild(i);
			int height = defaultheight;
			if (!overrideheight)
			{
				height = w.getPreferredHeight();
			}
			int width = defaultwidth;
			if(!overridewidth)
			{
				width = w.getPreferredWidth();
			}
			w.setSize(width,height);
			if(i % 2 == 0)
			{
				w.setPosition(getX()+getWidth()/2 - (150+split/2), getY() + (defaultheight+defaultpad)  * (i >> 1));
			}
			else
			{
				w.setPosition( getX()+getWidth()/2 + (split/2), getY() + (defaultheight+defaultpad) * (i >> 1));
			}
			
		}
	}
	
	public int getPreferredWidth()
	{
		return getParent().getWidth();
	}
	
	public int getPreferredHeight()
	{
		return (defaultheight+defaultpad) * (1 * (getNumChildren() + 1)>>1);
	}
}
