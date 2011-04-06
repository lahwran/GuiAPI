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
			w.setPosition(getX()+getWidth()/2-w.getWidth()/2, getY() + 24 * i );
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
