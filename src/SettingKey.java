import java.util.HashMap;

import org.lwjgl.input.Keyboard;


public class SettingKey extends Setting<Integer> {

    /**
     * 
     * @param title name used in conf file
     * @param key default keycode
     */
    public SettingKey(String title,int key)
    {
        defvalue = key;
        values.put("",key);
        backendname = title;
    }
    /**
     * 
     * @param title name used in conf file
     * @param key default key
     */
    public SettingKey(String title, String key)
    {
        this(title, Keyboard.getKeyIndex(key));
    }
    
    @Override
    public String toString(String context) {
        return Keyboard.getKeyName(get(context));
    }

    

    @Override
    public void fromString(String s, String context) {
        
        if (s.equals("UNBOUND"))
            values.put(context, Keyboard.KEY_NONE);
        else
            values.put(context,Keyboard.getKeyIndex(s));

        if(gui != null)
        {
            gui.update();
        }
    }
    /**
     * set and save value keycode version - used internally
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

    /**
     * set and save value keyname version
     * @param v value
     */
    public void set(String v, String context)
    {
        set(Keyboard.getKeyIndex(v), context);
    }
    public void set(String v)
    {
        set(v, ModSettings.currentContext);
    }

    /**
     * return current value. not really needed because value is public, but is nice to have
     * @return
     */
    @Override
    public Integer get(String context)
    {
        if(values.get(context) != null)
        {
            return values.get(context);
        }
        else if (values.get("") != null)
        {
            return values.get("");
        }
        else
        {
            return defvalue;
        }
            
    }

    /**
     * 
     * @return true if the key is down. ya think?
     */
    public boolean isKeyDown(String context)
    {
        if (get(context) != -1)
        {
            return Keyboard.isKeyDown(get(context));
        }
        return false; //mildly redundant
    }
    public boolean isKeyDown()
    {
        return isKeyDown(ModSettings.currentContext);
    }
}
