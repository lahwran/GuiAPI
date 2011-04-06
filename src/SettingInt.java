import java.util.HashMap;


public class SettingInt extends Setting<Integer> {
	public int step;
	public int min;
	public int max;
	
	/**
	 * 
	 * @param title name used in config file
	 */
	public SettingInt(String title)
	{

		this(title, 0, 0, 1, 100);
	}
	/**
	 * 
	 * @param title name used in config file
	 * @param _value default value
	 */
	public SettingInt(String title, int _value)
	{
		this(title, _value, 0, 1, 100);
	}
	/**
	 * 
	 * @param title name used in config file
	 * @param _value default value
	 * @param _min minimum
	 * @param _max maximum
	 */
	public SettingInt(String title, int _value, int _min, int _max)
	{

		this(title, _value, _min, 1, _max);
	}
	/**
	 * 
	 * @param title name used in config file
	 * @param _value default value
	 * @param _min minimum
	 * @param _step stepsize
	 * @param _max maximum
	 */
	public SettingInt(String title, int _value, int _min, int _step,  int _max)
	{
		values.put("",_value);
		defvalue = _value;
		min=_min;
		step=_step;
		max=_max;
		backendname = title;
		
		if (min > max)
		{
			int t = min;
			min = max;
			max = t;
		}
	}
	

	public String toString(String context) {
		return ""+get(context);
	}
	public void fromString(String s, String context)
	{
		values.put(context, new Integer(s));
		if(gui != null)
		{
			gui.update();
		}
		ModSettings.dbgout("fromstring " +s);
	} 

	/**
	 * set and save value
	 * @param v value
	 */
	@Override
	public void set(Integer v, String context)
	{
		ModSettings.dbgout("set " +v);
		if (step > 1)
			values.put(context, (int)(Math.round( (float)v /(float)step)*(float)step));
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
	public Integer get(String context)
	{
		if(values.get(context) != null)
			return values.get(context);
		else if (values.get("") != null)
			return values.get("");
		else return defvalue;
	}

	

}
