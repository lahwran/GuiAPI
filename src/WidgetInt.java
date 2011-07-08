import de.matthiasmann.twl.ValueAdjusterFloat;
import de.matthiasmann.twl.ValueAdjusterInt;
import de.matthiasmann.twl.model.SimpleFloatModel;
import de.matthiasmann.twl.model.SimpleIntegerModel;


public class WidgetInt extends WidgetSetting implements Runnable {

    //public boolean isSlider;
    //public String name;
    public int displaylen;
    public String formatstring;
    //public static final boolean hasValue = true;
    //public ValueAdjusterFloat s;
    public WidgetSlider s;
    
    public SettingInt value;
    
    public WidgetInt(SettingInt setting, String title)
    {

        this(setting, title, 15);
    }
    public WidgetInt(SettingInt setting, String title, int _displaylen)
    {
        super(title);
        setTheme("");
        displaylen=_displaylen;
        value = setting;
        
        value.gui = this;
        
        SimpleFloatModel smodel = new SimpleFloatModel((float)value.min, (float)value.max, (float)value.get());
        s = new WidgetSlider(smodel);
        s.setFormat(String.format("%s: %%.0f",nicename));
        
        if(value.step > 1 && value.step <= value.max)
            s.setStepSize((float)value.step);
        //smodel.
        //s.
        smodel.addCallback(this);
        //l.setLabelFor(s);
        //s.getChild(index)
        //setDepthFocusTraversal(false);
        //s.setTheme("");
        add(s);
        update();
    }
    /*
    public IntSliderSetting(int _value, int _min, int _step,  int _max, String formatstr)
    {
        super("");
        value=_value;
        min=_min;
        step=_step;
        max=_max;
        displaylen=5;
        formatstring=formatstr;
        isSlider = true;
        init();
    }*/
    
    /*public boolean handleEvent(Event e)
    {
        return s.handleEvent(e);
    }*/
    
    public String userString()
    {
        String l = String.format("%d", displaylen);
        return String.format("%s: %."+l+"d", nicename, value.get(ModSettingScreen.guicontext));
    }
    
    /*public String toString() {
        return ""+value;
    }
    public void fromString(String s)
    {
        value = new Integer(s);
    }*/
    //@Override
    public void update() {
        // TODO Auto-generated method stub
        s.setValue((float)value.get(ModSettingScreen.guicontext));
        s.setFormat(String.format("%s: %%.0f",nicename));
        ModSettings.dbgout("update " +value.get(ModSettingScreen.guicontext)+" -> "+(int)s.getValue());
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        //System.out.println(s.getValue());
        ModSettings.dbgout("run " +(int)s.getValue());
        value.set((int)s.getValue(), ModSettingScreen.guicontext);
        //if(parentmod != null)
        //    parentmod.save();
    }
    
    
}
/*

public class IntSliderSetting extends FloatSliderSetting {
    public int value;
    public int step;
    public int min;
    public int max;
    public int displaylen;
    public static final boolean hasValue = true;
    
    
    public IntSliderSetting()
    {
        value=0;
        min=0;
        step=0;
        max=100;
        displaylen=0;
        isSlider = true;
    }
    public IntSliderSetting(int _value)
    {
        value=_value;
        min=0;
        step=0;
        max=100;
        displaylen=0;
        isSlider = true;
    }
    public IntSliderSetting(int _value, int _min, int _max)
    {
        value=_value;
        min=_min;
        max=_max;
        step=0;
        displaylen=0;
        isSlider = true;
    }
    public IntSliderSetting(int _value, int _min, int _step, int _max)
    {
        value=_value;
        min=_min;
        step=_step;
        max=_max;
        displaylen=0;
        isSlider = true;
    }
    public IntSliderSetting(int _value, int _min, int _step,  int _max, int _displaylen)
    {
        value=_value;
        min=_min;
        step=_step;
        max=_max;
        displaylen=_displaylen;
        isSlider = true;
    }
    
    
    
    
    public String userString()
    {
        String l = "";
        if(displaylen > 0)
        {
        l = String.format("%02d", displaylen);
        }
        return String.format("%s: %"+l+"d", nicename, value);
    }
    
    public float floatValue()
    {
        return Math.min(Math.max(((float)value-(float)min)/((float)max-(float)min),0.0f),1.0f);
    }
    @Override
    public void setFloatValue(float f)
    {
        f *= (float)max - (float)min;
        if ((float)step > 0.0)
            f = (float)((int)(f/(float)step))*(float)step;
        f += (float)min;
        value = (int)f;
        
    }
    public String toString() {
        return ""+value;
    }
    public void fromString(String s)
    {
        value = new Float(s).intValue();
    }
}
*/