import java.util.HashMap;


public class SettingText extends Setting<String> {
	
	/**
	 * 
	 * @param title name in config file
	 * @param defaulttext default text
	 */
	public SettingText(String title, String defaulttext)
	{
		values.put("", defaulttext);
		defvalue = defaulttext;
		
		backendname = title;
	}
	
	@Override
	public void fromString(String s, String context) {
		values.put(context, s);
		if(gui != null)
		{
			gui.update();
		}
	}

	@Override
	public String toString(String context) {
		return get(context);
	}
	/**
	 * return current value. not really needed because value is public, but is nice to have
	 * @return
	 */
	@Override
	public String get(String context)
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
	public void set(String v, String context)
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

}
