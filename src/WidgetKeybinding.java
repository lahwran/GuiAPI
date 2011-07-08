
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.lwjgl.input.*;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.BooleanModel;
import de.matthiasmann.twl.model.SimpleBooleanModel;

public class WidgetKeybinding extends WidgetSetting implements Runnable {

    public SettingKey value;
    
    //public boolean isdown = false;
    public SimpleBooleanModel bmodel;
    public ToggleButton b;
    
    public int CLEARKEY = Keyboard.KEY_DELETE;
    public int NEVERMINDKEY = Keyboard.KEY_ESCAPE;
        
    public WidgetKeybinding(SettingKey setting, String title)
    {
        super(title);
        setTheme("");
        value = setting;
        
        value.gui = this;
        
        bmodel = new SimpleBooleanModel(false);
        b = new ToggleButton(bmodel); //bs?
        //b.addPropertyChangeListener("value",this);
        //this.handleEvent(
        //this.keyboardFocusLost()
        add(b);
        update();
    }
    
    public boolean handleEvent(Event evt)
    {
        if((evt.isKeyEvent() && !evt.isKeyPressedEvent()) && bmodel.getValue())
        {
            System.out.println(Keyboard.getKeyName(evt.getKeyCode()));
            int tmpvalue = evt.getKeyCode();
            if (tmpvalue == CLEARKEY)
            {
                value.set(Keyboard.KEY_NONE, ModSettingScreen.guicontext);
            }
            else if (tmpvalue != NEVERMINDKEY)
            {
                value.set(tmpvalue, ModSettingScreen.guicontext);
            }
            bmodel.setValue(false);
            //if (parentmod != null)
            //    parentmod.save();
            update();
            GuiModScreen.clicksound();
            return true;
        }
        
        return false;
    }
    
    public void keyboardFocusLost()
    {
        GuiModScreen.clicksound();
        bmodel.setValue(false);
    }
    //@Override
    public String userString() {
        
        return String.format("%s: %s",nicename,Keyboard.getKeyName(value.get(ModSettingScreen.guicontext)));
    }
    /*@Override
    public String toString() {
        return Keyboard.getKeyName(value);
    }

    

    @Override
    public void fromString(String s) {
        
        if (s.equals("UNBOUND"))
            value = Keyboard.KEY_NONE;
        else
            value = Keyboard.getKeyIndex(s);
    }
    
    */
    
    //@Override
    public void update() {
        // TODO Auto-generated method stub
        b.setText(userString());
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        //boolean isdown = bmodel.getValue();
        //b//oolean isfocused = b.isActive();
        GuiModScreen.clicksound();

        //ModSettings.dbgout("run: "+isdown+" "+isfocused);
    }
    
    
    /*@Override
    public void addCallback(Runnable callback) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public boolean getValue() {
        return isdown;
    }
    @Override
    public void removeCallback(Runnable callback) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void setValue(boolean value) {
        isdown = value;
    }*/

}
