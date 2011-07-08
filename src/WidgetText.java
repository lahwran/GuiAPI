import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.StringModel;


public class WidgetText extends WidgetSetting implements StringModel {
    public SettingText value;
    public EditField e;
    public Label l;
    public WidgetText(SettingText setting, String title)
    {
        super(title);
        setTheme("");
        value = setting;
        value.gui=this;

         ModSettings.dbgout("0");
         e = new EditField();
         ModSettings.dbgout("1");
         ModSettings.dbgout("2");
        
        add(e);
        //e.setTheme("");
        
        l = new Label();
        l.setText(String.format("%s: ",nicename));
        add(l);
         e.setModel(this);
        update();
         ModSettings.dbgout("3");
    }
    
    @Override
    public void layout()
    {
        
        l.setPosition(getX(), getY() + getHeight()/2 - l.computeTextHeight()/2);
        l.setSize(l.computeTextWidth(),l.computeTextHeight());
        e.setPosition(getX()+l.computeTextWidth(), getY());
        e.setSize(getWidth()-l.computeTextWidth(), getHeight());
    }
/*
    @Override
    public void fromString(String s) {
        // TODO Auto-generated method stub
        value = s;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return value;
    } */

    //@Override
    public String userString() {
        // TODO Auto-generated method stub
        return String.format("%s: %s",nicename,value.get(ModSettingScreen.guicontext));
    }
    
    /*public boolean handleEvent(Event e)
    {
        if(e.isKeyPressedEvent())
            GuiModScreen.clicksound();
        return false;
    }*/
    
    //// StringModel ////

    
    
    @Override
    public void addCallback(Runnable callback) {
        // TODO Auto-generated method stub
        ModSettings.dbgout("TextinputSetting.addcallback is a noop right now");
    }

    @Override
    public String getValue() {
        // TODO Auto-generated method stub
        return value.get();
    }

    @Override
    public void removeCallback(Runnable callback) {
        // TODO Auto-generated method stub
        ModSettings.dbgout("TextinputSetting.removecallback is a noop right now");
        
    }
    
    public int setmode = 0; // 0 = both can access. -1 = setValue only. +1 = update() only.

    @Override
    public void setValue(String _value) {
        // TODO Auto-generated method stub
        GuiModScreen.clicksound(); //I find this annoying...
        ModSettings.dbgout(String.format("setvalue %s", e.getText()));
        if(setmode <= 0)
        {
            setmode = -1;
            value.set(e.getText(), ModSettingScreen.guicontext);
            setmode = 0;
        }
        /*if(parentmod != null)
        {
            parentmod.save();
        }*/
    }

    //@Override
    public void update() {
        // TODO Auto-generated method stub
        //if(l == null) return;
        ModSettings.dbgout("update");
        l.setText(String.format("%s: ",nicename));    
        if(setmode >= 0)
        {
            setmode = 1;
            e.setText(value.get(ModSettingScreen.guicontext)); 
            setmode = 0;
        }
        ModSettings.dbgout(String.format("update %s", e.getText()));
    }


}
