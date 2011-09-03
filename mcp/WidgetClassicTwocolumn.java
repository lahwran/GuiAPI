package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

import de.matthiasmann.twl.Widget;


/**
 * This widget is designed to arrange widgets into two columns. The width and
 * height is enforced, but they can be configured by the programmer.
 * 
 * @author lahwran
 * @author ShaRose
 */
public class WidgetClassicTwocolumn extends Widget
{
    /**
     * This is the default height to enforce for widgets.
     */
    public int childDefaultHeight = 20;
    /**
     * This dictates the width to set each of the widgets to.
     */
    public int childWidth = 150;
    /**
     * This is the amount of padding to use between widgets vertically.
     */
    public int defaultPadding = 4;
    /**
     * This is a map to override the heights of specific widgets. It is an
     * override to overrideHeight. If you set the Integer as 0, it will use what
     * the widget wants as it's height. If it is set negative, it will keep the
     * positive part as the minimum size, but if the widget wants to grow it
     * can. If you set anything else, it will use that height. Note that with
     * TwoColumn widgets it will try and keep the height the same between two
     * widgets opposite each other, so the one with the biggest height will
     * override the other.
     */
    public Map<Widget, Integer> heightOverrideExceptions = new HashMap<Widget, Integer>();
    /**
     * This says whether it should override the height for all widgets.
     */
    public boolean overrideHeight = true;
    /**
     * This is the amount of room between the two columns.
     */
    public int splitDistance = 10;
    /**
     * This is the amount of padding to have before any widgets are positioned.
     */
    public int verticalPadding = 0;
    
    /**
     * This creates the WidgetClassicTwocolumn and adds the requested widgets.
     * 
     * @param widgets
     */
    public WidgetClassicTwocolumn(Widget... widgets)
    {
        for (int i = 0; i < widgets.length; ++i)
        {
            add(widgets[i]);
        }
        setTheme("");
    }
    
    @Override
    public int getPreferredHeight()
    {
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); i += 2)
        {
            Widget w = getChild(i);
            Widget w2 = null;
            if (i + 1 != getNumChildren())
            {
                w2 = getChild(i + 1);
            }
            int height = childDefaultHeight;
            if (!overrideHeight)
            {
                height = w.getPreferredHeight();
            }
            if (heightOverrideExceptions.containsKey(w))
            {
                Integer heightSet = heightOverrideExceptions.get(w);
                if (heightSet < 1)
                {
                    height = w.getPreferredHeight();
                }
                heightSet = -heightSet;
                if(heightSet != 0 && heightSet > height)
                {
                    height = heightSet;
                }
            }
            if (w2 != null)
            {
                int temp = height;
                if (!overrideHeight)
                {
                    temp = w2.getPreferredHeight();
                }
                if (heightOverrideExceptions.containsKey(w2))
                {
                    Integer heightSet = heightOverrideExceptions.get(w2);
                    if (heightSet < 1)
                    {
                        height = w2.getPreferredHeight();
                    }
                    heightSet = -heightSet;
                    if(heightSet != 0 && heightSet > height)
                    {
                        height = heightSet;
                    }
                }
                if (temp > height)
                {
                    height = temp;
                }
            }
            totalheight += height + defaultPadding;
        }
        return totalheight;
    }
    
    @Override
    public int getPreferredWidth()
    {
        return getParent().getWidth();
    }
    
    @Override
    public void layout()
    {
        if (getParent().getTheme().equals("scrollpane-notch"))
        {
            verticalPadding = 10;
        }
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); i += 2)
        {
            Widget w = getChild(i);
            Widget w2 = null;
            try
            {
                w2 = getChild(i + 1);
            }
            catch (IndexOutOfBoundsException e)
            {
                // do nothing, just means it's uneven.
            }
            int height = childDefaultHeight;
            if (!overrideHeight)
            {
                height = w.getPreferredHeight();
            }
            if (heightOverrideExceptions.containsKey(w))
            {
                Integer heightSet = heightOverrideExceptions.get(w);
                
                if (heightSet < 1)
                {
                    height = w.getPreferredHeight();
                }
                heightSet = -heightSet;
                if(heightSet != 0 && heightSet > height)
                {
                    height = heightSet;
                }
            }
            if (w2 != null)
            {
                int temp = height;
                if (!overrideHeight)
                {
                    temp = w2.getPreferredHeight();
                }
                if (heightOverrideExceptions.containsKey(w2))
                {
                    Integer heightSet = heightOverrideExceptions.get(w2);
                    if (heightSet < 1)
                    {
                        height = w2.getPreferredHeight();
                    }
                    heightSet = -heightSet;
                    if(heightSet != 0 && heightSet > height)
                    {
                        height = heightSet;
                    }
                }
                if (temp > height)
                {
                    height = temp;
                }
            }
            w.setSize(childWidth, height);
            w.setPosition(getX() + getWidth() / 2
                    - (childWidth + splitDistance / 2), getY() + totalheight);
            if (w2 != null)
            {
                w2.setSize(childWidth, height);
                w2.setPosition(getX() + getWidth() / 2 + splitDistance / 2,
                        getY() + totalheight);
            }
            totalheight += height + defaultPadding;
        }
    }
}
