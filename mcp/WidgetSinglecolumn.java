package net.minecraft.src;

import de.matthiasmann.twl.Widget;


/**
 * This is a widget designed to arrange other widgets into a single column.
 * 
 * @author lahwran
 * @author ShaRose
 */
public class WidgetSinglecolumn extends WidgetClassicTwocolumn
{
    /**
     * This creates the WidgetSinglecolumn with the specified Widgets. It chooses a default Width of 200.
     * @param w The widgets to add.
     */
    public WidgetSinglecolumn(Widget... widgets)
    {
        super(widgets);
        childWidth = 200;
    }
    
    @Override
    public int getPreferredHeight()
    {
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); ++i)
        {
            Widget widget = getChild(i);
            int height = childDefaultHeight;
            if (!overrideHeight)
            {
                height = widget.getPreferredHeight();
            }
            if (heightOverrideExceptions.containsKey(widget))
            {
                Integer heightSet = heightOverrideExceptions.get(widget);
                if (heightSet == 0)
                {
                    height = widget.getPreferredHeight();
                }
                else
                {
                    height = heightSet;
                }
            }
            totalheight += height + defaultPadding;
        }
        return totalheight;
    }
    
    @Override
    public int getPreferredWidth()
    {
        return Math.max(getParent().getWidth(), childWidth);
    }
    
    @Override
    public void layout()
    {
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); ++i)
        {
            Widget w = getChild(i);
            int height = childDefaultHeight;
            if (!overrideHeight)
            {
                height = w.getPreferredHeight();
            }
            if (heightOverrideExceptions.containsKey(w))
            {
                Integer heightSet = heightOverrideExceptions.get(w);
                if (heightSet == 0)
                {
                    height = w.getPreferredHeight();
                }
                else
                {
                    height = heightSet;
                }
            }
            w.setSize(childWidth, height);
            w.setPosition(getX() + getWidth() / 2 - w.getWidth() / 2, getY()
                    + totalheight);
            totalheight += height + defaultPadding;
        }
    }
}
