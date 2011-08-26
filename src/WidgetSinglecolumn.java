import de.matthiasmann.twl.Widget;
import net.minecraft.src.WidgetClassicTwocolumn;


public class WidgetSinglecolumn extends WidgetClassicTwocolumn
{
    public WidgetSinglecolumn(Widget... w)
    {
        super(w);
        this.defaultwidth = 200;
    }
    
    public void layout()
    {
        int totalheight = 0;
        for (int i = 0; i < this.getNumChildren(); ++i)
        {
            Widget w = this.getChild(i);
            int height = this.defaultheight;
            if (!this.overrideheight || heightOverrideExceptions.contains(w))
            {
                height = w.getPreferredHeight();
            }
            w.setSize(defaultwidth, height);
            w.setPosition(this.getX() + this.getWidth() / 2 - w.getWidth() / 2,
                    this.getY() + totalheight);
            totalheight += height + defaultpad;
        }
    }
    
    public int getPreferredWidth()
    {
        return Math.max(this.getParent().getWidth(), this.defaultwidth);
    }
    
    public int getPreferredHeight()
    {
        int height = 1;
        for (int i = 0; i < this.getNumChildren(); ++i)
        {
            Widget widget = this.getChild(i);
            if (!heightOverrideExceptions.contains(widget))
            {
                height += defaultheight + defaultpad;
            }
            else
            {
                height += widget.getPreferredHeight() + defaultpad;
            }
        }
        return height;
    }
}
