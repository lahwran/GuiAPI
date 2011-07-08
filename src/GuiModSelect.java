/**
 * subclass of guimodscreen, is the entry point from the button in the options menu.
 * @author lahwran
 * @version 0.9.5
 */
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.model.SimpleButtonModel;


public class GuiModSelect extends GuiModScreen {

    protected GuiModSelect(da by1) {
        super(by1);
        WidgetClassicTwocolumn w=new WidgetClassicTwocolumn();
        w.vpad = 10;
        for(int i=0; i<ModSettingScreen.modscreens.size();i++)
        {
            ModSettingScreen m = ModSettingScreen.modscreens.get(i);
            Button b = new Button(m.buttontitle);
            SimpleButtonModel z = new SimpleButtonModel();
            z.addActionCallback(new ModCallback(ModCallback.SELECTMOD, (Object) new Integer(i)));
            b.setModel(z);
            w.add(b);
        }
        /*w.add(new Button("button 1"));
        w.add(new Button("button 2"));
        w.add(new Button("button 3"));
        w.add(new Button("button 4"));*/
        WidgetSimplewindow mainwidget = new WidgetSimplewindow(w, "Select a Mod");
        mainwidget.hpad = 0;
        mainwidget.mainwidget.setTheme("scrollpane-notch");
        this.mainwidget = mainwidget;
        // TODO Auto-generated constructor stub
    }

}
