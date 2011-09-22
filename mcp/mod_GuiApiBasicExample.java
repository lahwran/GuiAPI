package net.minecraft.src;

/**
 * This is the BASIC example of GuiAPI usage. We are going to create a
 * ModSettings object, and use the 'easy' way of getting settings. Note, the
 * easy was is slower than the intermediate way of doing things, so if you are
 * getting your setting values several times a second you might want to read
 * that after this tutorial. As well, this tutorial will show you some usage of
 * the 'makeButton' and 'showTextDisplay' method in the GuiApiHelper class.
 * 
 * @author ShaRose
 */
public class mod_GuiApiBasicExample extends BaseMod
{
    /** The mod screen. */
    public ModSettingScreen myModScreen;
    /** The settings. */
    public ModSettings mySettings;
    
    /**
     * Instantiates a new mod_GuiApiBasicExample.
     */
    public mod_GuiApiBasicExample()
    {
        // First, create the settings class. The string in question is the
        // 'backend' name, usually the actual mod class name.
        mySettings = new ModSettings("mod_GuiApiBasicExample");
        // This is the Mod screen. It will automatically register the button in
        // the settings menu. This is two seperate steps, because you don't HAVE
        // to have this step there, like if you only want to store the settings
        // without a user-accessible gui.
        // Since we are adding one with longish names, we are going to make this
        // in single column mode.
        myModScreen = new ModSettingScreen("GuiAPI Basic Example");
        // If you want to leave it in two column mode, you don't need this part.
        // Now let's add a few settings. Since we are doing it in 'easy' mode,
        // we'll just discard the return values in this case.
        myModScreen.setSingleColumn(true);
        // default is 5, min is 1, max is 10
        mySettings.addSetting(myModScreen, "Nice Name for Int A",
                "backendIntA", 5, 1, 10);
        // default is 0.5, min is 0.1, step is 0.01, max is 1.0.
        mySettings.addSetting(myModScreen, "Nice Name for Float B",
                "backendFloatB", 0.5f, 0.1f, 0.01f, 1.0f);
        // Multi options, default at 'Option A', go between Option A-F
        mySettings.addSetting(myModScreen, "Nice Name for Multi C",
                "backendMultiC", 0, "Option A", "Option B", "Option C",
                "Option D", "Option E", "Option F");
        // boolean, default at true
        mySettings.addSetting(myModScreen, "Nice Name for Boolean D",
                "backendBooleanD", true);
        // boolean, default at true, with custom names
        mySettings.addSetting(myModScreen, "Nice Name for Boolean E",
                "backendBooleanE", false, "Yes!", "Noo!");
        // Text setting
        mySettings.addSetting(myModScreen, "Nice Name for Text F",
                "backendTextF", "This is the Default Value");
        // This makes a button which will call the 'ShowAllTheSettings' method
        // below. If the method in question is a static method, please use the
        // class. For example, mod_GuiAPIBasicExample.class instead of this.
        // Note you don't have to use this either, it can be any object, and the
        // method does not need to be public, as this can and will call private
        // methods as well. The boolean at the end controls whether or not to
        // automatically add the click sound. Use false if you want it to be
        // silent, or if you want to call it manually with GuiModScreen.back()
        myModScreen.append(GuiApiHelper.makeButton("Display all my settings!",
                "ShowAllTheSettings", this, true));
        // We'll also add a button to reset all of the settings to default. You
        // can do this on a per setting basis as well, but we'll learn that in
        // the Intermediate example mod. As well, you can see that I am calling
        // mySettings.resetAll() with this button, and it will click.
        myModScreen.append(GuiApiHelper.makeButton("Default all my settings!",
                "resetAll", mySettings, true));
        // And finally, don't forget to load any settings you saved earlier!
        mySettings.load();
    }
    
    /**
     * This is the method that will be called if you press the
     * "Display Settings" button. It doesn't have to be public, so you can make
     * this private and it will work. However, since we are calling this from a
     * button, it needs to return void.
     */
    public void ShowAllTheSettings()
    {
        StringBuilder displayTextBuilder = new StringBuilder();
        displayTextBuilder.append("Int A: ");
        // this finds the setting by backend name. It makes sure that it is an
        // int type, and gets the value of the current context (like a global
        // context, or a per world context. By default, the context will not
        // change, but you can specify specific ones if you want). You can
        // specify one as an argument if you want as well.
        displayTextBuilder.append(mySettings.getIntSettingValue("backendIntA"));
        displayTextBuilder.append("\r\n\r\n");
        displayTextBuilder.append("Float B: ");
        // Now Float B.
        displayTextBuilder.append(mySettings.getFloatSettingValue("backendFloatB"));
        displayTextBuilder.append("\r\n\r\n");
        displayTextBuilder.append("Multi C: ");
        // Now Multi C, as an int which you can use in your code.
        displayTextBuilder.append(mySettings.getMultiSettingValue("backendMultiC"));
        displayTextBuilder.append("\r\n\r\n");
        displayTextBuilder.append("Multi C (Displayed): ");
        // Now Multi C, as the displayed string on the menu.
        displayTextBuilder.append(mySettings
                .getMultiSettingLabel("backendMultiC"));
        displayTextBuilder.append("\r\n\r\n");
        displayTextBuilder.append("Boolean D: ");
        // Now Boolean D.
        displayTextBuilder.append(mySettings
                .getBooleanSettingValue("backendBooleanD"));
        displayTextBuilder.append("\r\n\r\n");
        displayTextBuilder.append("Boolean E: ");
        // Now Boolean E. Note that it is not any different whether you
        // specified display names or not.
        displayTextBuilder.append(mySettings
                .getBooleanSettingValue("backendBooleanE"));
        displayTextBuilder.append("\r\n\r\n");
        displayTextBuilder.append("Text F: ");
        // Now String F.
        displayTextBuilder.append(mySettings.getTextSettingValue("backendTextF"));
        // Display your menu. It will have a title bar that says 'My Current
        // Settings', will use all the text you just created using the
        // StringBuilder and the settings helpers in a long textbox, and at the
        // bottom will have a button that says 'OK, Go back to the settings
        // now.' which, when pressed, will just go back to the last menu. In
        // this case, your settings menu. It automatically sets up a scrollbar
        // for you as well. Of course, we also call GuiModScreen.show to display
        // the widget that makeTextDisplayAndGoBack creates.
        GuiModScreen.show(GuiApiHelper.makeTextDisplayAndGoBack(
                "My Current Settings", displayTextBuilder.toString(),
                "OK, Go back to the settings now.", false));
    }
    
    @Override
    public String Version()
    {
        return "1.0";
    }
}
