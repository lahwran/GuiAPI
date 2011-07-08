import java.util.HashMap;

import org.lwjgl.input.Keyboard;


public class SettingMulti extends Setting<Integer> {

    public String[] labels;
    /**
     * 
     * @param title name to use in config file
     * @param def default index
     * @param _labels labels of each option - must be at least one
     */
    public SettingMulti(String title, int def, String... _labels)
    {
        if (_labels.length == 0)
            return;//should cause an error ... damnit can't actually throw
        values.put("", def);
        defvalue=def;
        
        labels = _labels;
        
        backendname = title;
    }
    /**
     * 
     * @param title name to use in config file
     * @param _labels labels of each option - must be at least one
     */
    public SettingMulti(String title, String... _labels)
    {
        this(title, 0, _labels);
    }

    /**
     * now uses the label value instead of label idx
     */
    @Override
    public String toString(String context) {
        return labels[get(context)];
    }
    /**
     * now uses the label value instead of label idx
     */
    @Override
    public void fromString(String s, String context)
    {
        int x=-1;
        for (int i=0;i<labels.length;i++)
        {
            if (labels[i].equals(s))
            {
                x=i;
            }
        }
        if(x != -1)
        {
            values.put(context,x);
        }
        else
        {
            values.put(context,new Float(s).intValue());
        }
        ModSettings.dbgout("fromstring multi "+s);
        if(gui != null)
        {
            gui.update();
        }
    }


    /**
     * set and save value label version
     * @param v value
     */
    public void set(String v, String context)
    {
        int x=-1;
        for (int i=0;i<labels.length;i++)
        {
            if (labels[i].equals(v))
            {
                x=i;
            }
        }
        if(x != -1)
        {
            set(x, context);
        }
    }
    /**
     * set and save value index version - used internally
     * @param v value
     */
    @Override
    public void set(Integer v, String context)
    {
        values.put(context, v);
        if (parent != null)
        {
            parent.save(context);
            
        }
        if(gui != null)
        {
            gui.update();
        }
    }
    public void set(String v)
    {
        set(v, ModSettings.currentContext);
    }

    @Override
    public Integer get(String context)
    {
        if(values.get(context) != null)
            return values.get(context);
        else if (values.get("") != null)
            return values.get("");
        else return defvalue;
    }
    
    public String getLabel(String context)
    {
        return labels[get(context)];
    }
    public String getLabel()
    {
        return labels[get()];
    }

    public void next(String context)
    {
        int tempvalue = get(context) + 1;
        while (tempvalue >= labels.length)
        {
            tempvalue -= labels.length;
        }
        set(tempvalue, context);
    }
    public void next()
    {
        next(ModSettings.currentContext);
    }
}
