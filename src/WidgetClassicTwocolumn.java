import java.util.ArrayList;

import de.matthiasmann.twl.Widget;


public class WidgetClassicTwocolumn extends Widget
{
    public int defaultwidth = 150;
    public int defaultheight = 20;
    public int defaultpad = 4;
    public boolean overrideheight = true;
    public int split = 10;
    public int vpad = 0;
    public ArrayList<Widget> heightOverrideExceptions = new ArrayList<Widget>();
    
    public WidgetClassicTwocolumn(Widget... ws)
    {
        for (int i = 0; i < ws.length; ++i)
        {
            this.add(ws[i]);
        }
        this.setTheme("");
    }
    
    public void layout()
    {
        if (this.getParent().getTheme().equals("scrollpane-notch"))
        {
            this.vpad = 10;
        }
        int totalheight = vpad;
        for (int i = 0; i < this.getNumChildren(); i += 2)
        {
            Widget w = this.getChild(i);
            Widget w2 = null;
            try
            {
                w2 = this.getChild(i + 1);
            }
            catch (IndexOutOfBoundsException e)
            {
                // do nothing, just means it's uneven.
            }
            int height = this.defaultheight;
            if (!this.overrideheight || heightOverrideExceptions.contains(w))
            {
                height = w.getPreferredHeight();
            }
            if (w2 != null)
            {
                if (!this.overrideheight
                        || heightOverrideExceptions.contains(w2))
                {
                    int temp = w2.getPreferredHeight();
                    if (temp > height)
                        height = temp;
                }
            }
            w.setSize(defaultwidth, height);
            w.setPosition(this.getX() + this.getWidth() / 2
                    - (150 + this.split / 2), this.getY() + totalheight);
            if (w2 != null)
            {
                w2.setSize(defaultwidth, height);
                w2.setPosition(this.getX() + this.getWidth() / 2 + this.split
                        / 2, this.getY() + totalheight);
            }
            totalheight += height + defaultpad;
        }
    }
    
    public int getPreferredWidth()
    {
        return this.getParent().getWidth();
    }
    
    public int getPreferredHeight()
    {
        int totalheight = vpad;
        for (int i = 0; i < this.getNumChildren(); i += 2)
        {
            Widget w = this.getChild(i);
            Widget w2 = null;
            if (i + 1 != getNumChildren())
                w2 = this.getChild(i + 1);
            int height = this.defaultheight;
            if (!this.overrideheight || heightOverrideExceptions.contains(w))
            {
                height = w.getPreferredHeight();
            }
            if (w2 != null)
            {
                if (!this.overrideheight
                        || heightOverrideExceptions.contains(w2))
                {
                    int temp = w2.getPreferredHeight();
                    if (temp > height)
                        height = temp;
                }
            }
            totalheight += height + defaultpad;
        }
        return totalheight;
    }
}
