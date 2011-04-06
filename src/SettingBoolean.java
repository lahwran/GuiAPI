import java.util.HashMap;

/**
 * boolean setting, goes with WidgetBoolean
 * @author lahwran
 * @version 0.9.5
 */
public class SettingBoolean extends Setting<Boolean> {

	
	/**
	 * 
	 * @param name name to use in config file
	 * @param _value default value
	 */
	public SettingBoolean(String name, Boolean _defvalue)
	{
		defvalue = _defvalue;
		values.put("",defvalue);
		backendname = name;
	}
	/**
	 * you'd call this if the default is "false"
	 * @param name name to pass to other constructor
	 */
	public SettingBoolean(String name)
	{
		this(name, false);
	}

	@Override
	public String toString(String context) {
		return (get(context) ? "true" : "false");
	}
	
	@Override
	public void fromString(String s, String context)
	{
		values.put(context, s.equals("true"));

		if(gui != null)
		{
			gui.update();
		}
	}

	/**
	 * return current value. value is no longer accessible, please use this.
	 * @return
	 */
	@Override
	public Boolean get(String context)
	{
		if(values.get(context) != null)
			return values.get(context);
		else if (values.get("") != null)
			return values.get("");
		else return defvalue;
	}
	
	/**
	 * set and save value
	 * @param v value
	 */
	@Override
	public void set(Boolean v,String context)
	{
		values.put(context,v);
		if (parent != null)
		{
			parent.save(context);
			
		}
		if(gui != null)
		{
			gui.update();
		}
	}

}
