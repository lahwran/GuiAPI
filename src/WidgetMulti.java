import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;




public class WidgetMulti extends WidgetSetting implements Runnable {
    
    //public boolean isSlider;
    //public String name;
    public SettingMulti value;
    public Button b = null;
    
    
    
    public WidgetMulti(SettingMulti setting, String title)
    {
        super(title);
        setTheme("");
        value=setting;
        value.gui=this;
        
        
        SimpleButtonModel model = new SimpleButtonModel();
        b = new Button(model);
        model.addActionCallback(this);
        add(b);
        update();
    }
    
    
    
    
    public String userString()
    {
        if (nicename.length() > 0)
            return String.format("%s: %s", nicename, value.getLabel(ModSettingScreen.guicontext));
        else
            return value.getLabel(ModSettingScreen.guicontext);
    }
    
    /* public String toString() {
        return ""+value;
    }
    public void fromString(String s)
    {
        value = new Float(s).intValue();
    } */

    //@Override
    public void update() {
        // TODO Auto-generated method stub
        b.setText(userString());
        ModSettings.dbgout("multi update "+userString());
    }
    
    @Override
    public void run() { // change
        // TODO Auto-generated method stub
        value.next(ModSettingScreen.guicontext);
        update();
        
        //if(parentmod != null)
        //    parentmod.save();
        GuiModScreen.clicksound();
    }





}
