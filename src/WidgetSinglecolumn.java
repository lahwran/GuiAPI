import de.matthiasmann.twl.Widget;


public class WidgetSinglecolumn extends WidgetClassicTwocolumn {
	
	public WidgetSinglecolumn(Widget... w)
	{
		super(w);
		defaultwidth = 200;
	}
	
	@Override
	public void layout()
	{
		int ycounter = defaultheight;
		for(int i=0; i<getNumChildren(); i++)
		{
			Widget w = getChild(i);
			int height = defaultheight;
			if (!overrideheight || classheightexceptions.contains(w.getClass()) || indexheightexceptions.contains(i))
			{
				height = w.getPreferredHeight();
			}
			int width = defaultwidth;
			if(!overridewidth)
			{
				width = w.getPreferredWidth();
			}
			w.setSize(width,height);
			w.setPosition(getX()+getWidth()/2-w.getWidth()/2, getY() + ycounter );
			ycounter += height + defaultpad;
		}
	}

	public int getPreferredWidth()
	{
		return Math.max(getParent().getWidth(), defaultwidth);
	}
	
	public int getPreferredHeight()
	{
		return 24 * getNumChildren() + 1;
	}
}
