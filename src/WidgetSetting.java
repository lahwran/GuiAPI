import java.util.ArrayList;

import de.matthiasmann.twl.Widget;


public abstract class WidgetSetting extends Widget {

    public String nicename;
    
    //public Setting backend;
    
    public static ArrayList<WidgetSetting> all = new ArrayList<WidgetSetting>();
    
    public WidgetSetting(String _nicename)
    {
        nicename = _nicename;
        all.add(this);
    }
    
    public void add(Widget child)
    {
        String T=child.getTheme();
        if (T.length() == 0)
            child.setTheme("/-defaults");
        else if(!T.substring(0, 1).equals("/"))
            child.setTheme("/"+T);
        super.add(child);
    }
    @Override
    public void layout()
    {
        for(int i=0; i<getNumChildren(); i++)
        {
            Widget w = getChild(i);
            w.setPosition(getX(), getY());
            w.setSize(getWidth(),getHeight());
        }
    }
    
    public static void updateAll()
    {
        for(int i=0; i<all.size();i++)
        {
            all.get(i).update();
        }
    }
    
    public abstract void update();
}
