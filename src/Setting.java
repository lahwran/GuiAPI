import java.util.HashMap;

import de.matthiasmann.twl.Widget;

/**
 * Base class for settings
 * @author lahwran
 * @version 0.9.5
 */

public abstract class Setting<T> extends Widget {
    
    /**
     * value. do not write directly if you want things to update!
     */
    public HashMap<String,T> values = new HashMap<String,T>();
    /**
     * default value
     */
    public T defvalue;
    
    /**
     * name used to save setting by ModSettings
     */
    public String backendname;
    
    /**
     * reference to frontend, so it can tell the gui when to update - set from gui constructor
     */
    public WidgetSetting gui = null;
    
    /**
     * reference to modsettings - set from ModSettings.append - used to save when something has changed
     */
    public ModSettings parent = null;
    public Setting()
    {
        
    }
    
    /**
     * return string to save, called from ModSettings.save()
     */
    public abstract String toString(String context);
    
    /**
     * load back a string from toString()
     */
    public abstract void fromString(String s, String context);
    /*{
        return;
    }*/
    
    public void reset()
    {
        reset(ModSettings.currentContext);
    }
    
    /**
     * reset setting to default value, including saving and updating the display
     */
    public void reset(String context) {
        set(defvalue,context);
    }
    
    public void copyContext(String srccontext, String destcontext) {
        values.put(destcontext,values.get(srccontext));
    }
    
    public abstract void set(T v,String context);
    public void set(T v)
    {
        set(v, ModSettings.currentContext);
    }
    
    
    public abstract T get(String context);
    public T get()
    {
        return get(ModSettings.currentContext);
    }
    
    //public boolean hasGlobal()
}
