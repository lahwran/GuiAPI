import java.util.ArrayList;

import de.matthiasmann.twl.Widget;


public class WidgetSingleRow extends Widget {

	public int xSpacing = 3;
	public int defaultwidth = 150;
	public int defaultheight = 20;
	public boolean overridewidth = false;
	public boolean overrideheight = false;
	
	protected ArrayList<Widget> wiggets = new ArrayList<Widget>();
	protected ArrayList<Integer> heights = new ArrayList<Integer>();
	protected ArrayList<Integer> widths = new ArrayList<Integer>();
	
	public WidgetSingleRow(int defwidth, int defheight, Widget... widgets_)
	{
		setTheme("");
		defaultwidth = defwidth;
		defaultheight = defheight;
		for(int i=0; i<widgets_.length; i++)
		{
			add(widgets_[i]);
		}
	}
	public void add(Widget w)
	{
		add(w, defaultwidth, defaultheight);
	}
	public void add(Widget w, int width, int height)
	{
		wiggets.add(w);
		heights.add(height);
		widths.add(width);
		super.add(w);
		
	}
	@Override
	public boolean removeChild(Widget w)
	{
		int idx=wiggets.indexOf(w);
		wiggets.remove(idx);
		heights.remove(idx);
		widths.remove(idx);
		
		return super.removeChild(w);
	}
	@Override
	public Widget removeChild(int idx)
	{
		wiggets.remove(idx);
		heights.remove(idx);
		widths.remove(idx);
		
		return super.removeChild(idx);
	}
	
	private int getHeight(int idx)
	{
		if(heights.get(idx)>=0)
			return heights.get(idx);
		else
			return wiggets.get(idx).getPreferredHeight();
	}
	private int getWidth(int idx)
	{
		if(widths.get(idx)>=0)
			return widths.get(idx);
		else
			return wiggets.get(idx).getPreferredWidth();
	}
	@Override
	public int getPreferredWidth()
	{
		int totalwidth=(widths.size()-1)*xSpacing;
		totalwidth = totalwidth>=0?totalwidth:0;
		for(int i=0; i<widths.size(); i++)
		{
			totalwidth+=getWidth(i);
		}
		return totalwidth;
	}
	@Override
	public int getPreferredHeight()
	{
		int maxheights = 0;
		for(int i=0; i<heights.size();i++)
			if(getHeight(i)>maxheights)
				maxheights = getHeight(i);
		return maxheights;
	}
	public void layout()
	{
		int curXpos=0;
		for(int i=0; i<wiggets.size(); i++)
		{
			Widget w = wiggets.get(i);
			w.setPosition(curXpos+getX(), getY());
			w.setSize(getWidth(i), getHeight(i));
			curXpos += getWidth(i)+xSpacing;
		}
	}
	
}
