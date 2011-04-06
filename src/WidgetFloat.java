
import de.matthiasmann.twl.model.SimpleFloatModel;




public class WidgetFloat extends WidgetSetting implements Runnable {

	//public boolean isSlider;
	//public String name;
	
	
	public int displaylen;
	public String formatstring;
	public WidgetSlider slider;
	
	public SettingFloat value;
	
	public WidgetFloat(SettingFloat s,String title)
	{

		this( s,title, 2, "");
	}
	public WidgetFloat(SettingFloat s, String title,  int _displaylen)
	{
		this( s,title,  _displaylen, "");
	}
	public WidgetFloat(SettingFloat setting, String title, int _displaylen, String formatstr)
	{
		super(title);
		setTheme("");
		displaylen=_displaylen;
		formatstring=formatstr;
		
		
		
		
		value = setting;
		value.gui = this;
		SimpleFloatModel smodel = new SimpleFloatModel(value.min, value.max, value.get());
		smodel.addCallback(this);
		
		slider = new WidgetSlider(smodel);

		if(value.step > 0.0f && value.step <= value.max)
			slider.setStepSize(value.step);
		slider.setFormat(String.format("%s: %%.%df",nicename,displaylen));
		
		//smodel.
		//s.
		//l.setLabelFor(s);
		//s.getChild(index)
        //setDepthFocusTraversal(false);
        //s.setTheme("");
		add(slider);
		update();
	}
	
	/*public boolean handleEvent(Event e)
	{
		return s.handleEvent(e);
	}*/
	
	public String userString()
	{
		String l = String.format("%02d", displaylen);
		return String.format("%s: %."+l+"f", nicename, value);
	}
	
	/*public float floatValue()
	{
		//System.out.println("floatValue() returning "+((value-min)/(max-min)));
		//System.out.println(""+max+" "+min+" "+value+" "+(max-min)+" "+(value-min));
		return Math.min(Math.max((value-min)/(max-min),0.0f),1.0f);
	}
	public void setFloatValue(float f)
	{
		f -= min;
		//if (step > 0.0)
		//	f = (float)((int)(f/step))*step;
		f += min;
		value = f;
		//update();
	}*/
	/*
	*/
	//@Override
	public void update() {
		// TODO Auto-generated method stub
		slider.setValue(value.get(ModSettingScreen.guicontext));
		slider.setFormat(String.format("%s: %%.%df",nicename,displaylen));
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println(s.getValue());
		value.set(slider.getValue(), ModSettingScreen.guicontext);
		//if(parentmod != null)
		//	parentmod.save();
	}
	
	
}
