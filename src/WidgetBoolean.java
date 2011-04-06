import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;




public class WidgetBoolean extends WidgetSetting implements Runnable {
	
	public String ttext;
	public String ftext;
	public Button b;
	
	public SettingBoolean value = null;
	
	public WidgetBoolean(SettingBoolean setting, String title)
	{
		this(setting, title, "true", "false");
	}
	public WidgetBoolean(SettingBoolean setting, String title, String _ttext, String _ftext)
	{
		super(title);
		setTheme("");
		ttext = _ttext;
		ftext = _ftext;
		SimpleButtonModel bmodel = new SimpleButtonModel();
		b = new Button(bmodel);
		bmodel.addActionCallback(this);
		add(b);
		value = setting;
		value.gui = this;
		update();
	}
	
	
	public String userString()
	{
		if(value != null)
		{
			if (nicename.length() > 0)
				return String.format("%s: %s", nicename, value.get(ModSettingScreen.guicontext) ? ttext : ftext);
			else
				return value.get(ModSettingScreen.guicontext) ? ttext : ftext;
		}
		else
		{
			if (nicename.length() > 0)
				return String.format("%s: %s", nicename, "no value");
			else
				return "no value or title";
		}
	}
	/*
	public String toString() {
		return (value ? "true" : "false");
	}
	public void fromString(String s)
	{
		value = s.equals("true");
	}*/
	//@Override
	public void update() {
		// TO DO Auto-generated method stub
		b.setText(userString());
	}
	@Override
	public void run() {
		// TO DO Auto-generated method stub
		if(value != null)
			value.set(!value.get(ModSettingScreen.guicontext), ModSettingScreen.guicontext);
		update();
		//if(parentmod != null)
		//	parentmod.save();
		GuiModScreen.clicksound();
		//ModSettings.dbgout("safe");
	}
	
}
