import java.util.HashMap;

/**
 * float setting
 * @author lahwran
 * @version 0.9.5
 *
 */
public class SettingFloat extends Setting<Float> {
    public float step;
    public float min;
    public float max;
    
    /**
     * 
     * @param title name used in config file
     */
    public SettingFloat(String title)
    {
        this(title, 0.0f, 0.0f, 0.1f, 1.0f);
    }
    /**
     * 
     * @param title name used in config file
     * @param _value default value
     */
    public SettingFloat(String title, float _value)
    {
        this(title, _value, 0.0f, 0.1f, 1.0f);
    }
    /**
     * 
     * @param title name used in config file
     * @param _value default value
     * @param _min minimum
     * @param _max maximum
     */
    public SettingFloat(String title, float _value, float _min, float _max)
    {

        this(title, _value, _min, 0.1f, _max);
    }
    /**
     * 
     * @param title name used in config file
     * @param _value default value
     * @param _min minimum
     * @param _step stepsize
     * @param _max maximum
     */
    public SettingFloat(String title, float _value, float _min, float _step,  float _max)
    {
        values.put("",_value);
        defvalue = _value;
        min=_min;
        step=_step;
        max=_max;
        backendname = title;
        
        if (min > max)
        {
            float t = min;
            min = max;
            max = t;
        }
    }
    

    public String toString(String context) {
        return ""+get(context);
    }
    public void fromString(String s, String context)
    {
        values.put(context, new Float(s));
        if(gui != null)
        {
            gui.update();
        }
    }
    
    /**
     * set and save value
     * @param v value
     */
    @Override
    public void set(Float v, String context)
    {
        if (step > 0)
            values.put(context, Math.round(v/step)*step);
        else
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
     * return current value. not really needed because value is public, but is nice to have
     * @return
     */
    @Override
    public Float get(String context)
    {
        if(values.get(context) != null)
            return values.get(context);
        else if (values.get("") != null)
            return values.get("");
        else return defvalue;
    }
    

}
