package net.minecraft.src;

import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.Widget;


/**
 * This is the INTERMEDIATE example of GuiAPI usage. We are going to do the more
 * correct, but slightly more complex, way of retrieving settings, learn about
 * Callback usage within GuiAPI, and some more interesting usages of ModAction
 * relating to that. We will also learn about subscreens and how to use them
 * within your mod. We'll also be looking at the createChoiceMenu method in
 * GuiApiHelper.
 * 
 * @author ShaRose
 */
public class mod_GuiApiIntermediateExample extends BaseMod
{
    /**
     * This is a method designed to update the text area, depending on what kind of setting is passed. Please view the source code comments for more.
     * 
     * @param textArea The textarea to update.
     * @param setting The setting to get info from. This particular method supports SettingInt, SettingFloat, and SettingText.
     */
    private static void updateTextArea(TextArea textArea,
            @SuppressWarnings("rawtypes") Setting setting)
    {
        String text = "";
        // Instead of making different method for each one, We'll just check
        // what type of setting each is, place the text you want in the text
        // variable, and then set the TextArea.
        if (setting instanceof SettingInt)
        {
            SettingInt settingint = (SettingInt) setting;
            text = (settingint.get() - settingint.minimumValue)
                    / (float) (settingint.maximumValue - settingint.minimumValue) * 100 + "%";
        }
        if (setting instanceof SettingFloat)
        {
            SettingFloat settingfloat = (SettingFloat) setting;
            float val = (settingfloat.get() - settingfloat.minimumValue)
                    / (settingfloat.maximumValue - settingfloat.minimumValue);
            if (val < 0)
            {
                val = 0;
            }
            text = val * 100 + "%";
        }
        if (setting instanceof SettingText)
        {
            SettingText settingtext = (SettingText) setting;
            text = settingtext.get();
        }
        // Here's how you normally would set the TextArea's text, but we are
        // going to use one of the helpers for it. It's still one line, but this
        // way is a bit messy.
        // ((SimpleTextAreaModel) textArea.getModel()).setText(text, false);
        // Here's the cleaner way to do it, and this also works if you set it to
        // use HTML mode.
        GuiApiHelper.setTextAreaText(textArea, text);
    }
    
    /** The mod screen. */
    public ModSettingScreen myModScreen;
    
    /** The settings. */
    public ModSettings mySettings;
    
    /** The setting boolean d. */
    public SettingBoolean settingBooleanD;
    
    /** The setting boolean e. */
    public SettingBoolean settingBooleanE;
    
    /** The setting float b. */
    public SettingFloat settingFloatB;
    
    /** The setting int a. */
    public SettingInt settingIntA;
    
    /** The setting multi c. */
    public SettingMulti settingMultiC;
    
    /** The setting text f. */
    public SettingText settingTextF;
    
    /** The subscreen for booleans. */
    public WidgetSimplewindow subscreenBooleans;
    
    /** The subscreen for numberics. */
    public WidgetSimplewindow subscreenNumberics;
    
    /** The subscreen for others. */
    public WidgetSimplewindow subscreenOthers;
    
    /**
     * Instantiates a new mod_GuiApiIntermediateExample.
     */
    public mod_GuiApiIntermediateExample()
    {
        // We need to set up our settings and modscreen, so let's do that.
        mySettings = new ModSettings("mod_GuiApiIntermediateExample");
        myModScreen = new ModSettingScreen("GuiAPI Intermediate Example");
        // Now we are going to start setting up our subscreens. I want it in one
        // column, so I'm using the WidgetSinglecolumn class. If you want to use
        // the normal two column version, use WidgetClassicTwocolumn instead.
        WidgetSinglecolumn numbericBaseWidget = new WidgetSinglecolumn();
        // Note that this time we are saving the SettingInt that is returned.
        // This is a faster way to get settings than the easy way shown in the
        // basic example, but it takes up more space for field declarations. As
        // well note instead of adding it to the modscreen, we are telling it to
        // use the WidgetSinglecolumn we made just previous.
        settingIntA = mySettings.addSetting(numbericBaseWidget,
                "Nice Name for Int A", "backendIntA", 0, -100, 100);
        // We are also going to have a TextArea display the percentage of what's
        // selected. I'm leave the text blank for now, we will update it after
        // we load the saved settings. As well, the reason for the false at the
        // end is because we are just using simple text mode. If you want a
        // TextBox to render HTML, set that to true.
        TextArea textAreaA = GuiApiHelper.makeTextArea("", false);
        // Add it to the subscreen we are making as well.
        numbericBaseWidget.add(textAreaA);
        // This is how you set up a callback when the value is changed. This
        // code creates a ModAction that calls the static updateTextArea method
        // in this class. It has two arguments: a TextArea and a Setting, so we
        // specify that. Since there's no way for it to specify that kind of
        // argument (The callback has no arguments to send), we are also going
        // to tell it to use the references to the textarea we created for this
        // setting, and the setting itself. After we create the setting, we use
        // the setting to get a reference to the widget that is actually
        // displayed, and add a callback.
        settingIntA.displayWidget.addCallback(new ModAction(
                mod_GuiApiIntermediateExample.class, "updateTextArea",
                "Callback for TextArea A", TextArea.class, Setting.class)
                .setDefaultArguments(textAreaA, settingIntA));
        // You should already know what this is.
        settingFloatB = mySettings.addSetting(numbericBaseWidget,
                "Nice Name for Float B", "backendFloatB", 0f, -2.0f, 0.01f,
                2.0f);
        TextArea textAreaB = GuiApiHelper.makeTextArea("", false);
        numbericBaseWidget.add(textAreaB);
        // And again, this is much the same. Note that for both of these
        // ModActions, we specifically set a 'name' for it, so in case anything
        // happens and an exception is thrown, you will see 'Callback for
        // TextArea B' in the stack trace. This is useful for debugging, as
        // usually you won't see what the method that actually crashed is, but
        // now you can see what method it was, where the ModAction was created,
        // etc. As long as you set your names that is.
        settingFloatB.displayWidget.addCallback(new ModAction(
                mod_GuiApiIntermediateExample.class, "updateTextArea",
                "Callback for TextArea B", TextArea.class, Setting.class)
                .setDefaultArguments(textAreaB, settingFloatB));
        // Now we are going to merge the reset ModActions for the two 'numberic'
        // settings.
        ModAction mergedResetNumberics = new ModAction(settingIntA, "reset")
                .mergeAction(new ModAction(settingFloatB, "reset"));
        // And add a button for it to the subscreen.
        numbericBaseWidget.add(GuiApiHelper.makeButton("Reset Numberic Values",
                mergedResetNumberics, true));
        // Now we finish creating the subscreen. The WidgetSimplewindow sets up
        // a title bar on top, and a button bar to go back on the bottom. There
        // is an option to disable the back button as well.
        subscreenNumberics = new WidgetSimplewindow(numbericBaseWidget,
                "Numberic Settings");
        // And now create a Button to open your new SubScreen. We are going to
        // use use the makeButton overloads this time, as we don't need to merge
        // any ModActions. This one calls the static GuiModScreen.show method,
        // which takes a Widget class as an parameter. When the button is
        // clicked, it will call that method using subscreenNumberics as the
        // argument.
        myModScreen.append(GuiApiHelper.makeButton("Open Numberic Settings",
                "show", GuiModScreen.class, true, new Class[]
                { Widget.class }, subscreenNumberics));
        // And now we will do 2 other subscreens. There won't be anything
        // special for these, so you should be able to follow along with what I
        // am doing easily.
        WidgetSinglecolumn booleanBaseWidget = new WidgetSinglecolumn();
        settingBooleanD = mySettings.addSetting(booleanBaseWidget,
                "Nice Name for Boolean D", "backendBooleanD", true);
        settingBooleanE = mySettings.addSetting(booleanBaseWidget,
                "Nice Name for Boolean E", "backendBooleanE", false, "Yes!",
                "Noo!");
        ModAction mergedResetBooleans = new ModAction(settingBooleanD, "reset")
                .mergeAction(new ModAction(settingBooleanE, "reset"));
        booleanBaseWidget.add(GuiApiHelper.makeButton("Reset Boolean Values",
                mergedResetBooleans, true));
        subscreenBooleans = new WidgetSimplewindow(booleanBaseWidget,
                "Boolean Settings");
        myModScreen.append(GuiApiHelper.makeButton("Open Boolean Settings",
                "show", GuiModScreen.class, true, new Class[]
                { Widget.class }, subscreenBooleans));
        WidgetSinglecolumn otherBaseWidget = new WidgetSinglecolumn();
        settingMultiC = mySettings.addSetting(otherBaseWidget,
                "Nice Name for Multi C", "backendMultiC", 0, "Option A",
                "Option B", "Option C", "Option D", "Option E", "Option F");
        // Actually, let's do something nice for the text widget. It's a bit
        // small to see when editing, so we'll add a TextArea below it that
        // shows what you have.
        settingTextF = mySettings.addSetting(otherBaseWidget,
                "Nice Name for Text F", "backendTextF",
                "This is the Default Value");
        TextArea textAreaF = GuiApiHelper.makeTextArea("", false);
        otherBaseWidget.add(textAreaF);
        // This line of code adds the TextArea to the override list so it won't
        // be overridden to anything. Normally, WidgetClassicTwocolumn and
        // WidgetSinglecolumn override the height and width of all widgets.
        // There's a boolean to not do that for everything, but if you do, it
        // makes buttons thin and ugly looking. So, we are going to add it
        // specifically so it doesn't override the height. As well, you can use
        // this to specify a height to override to, by changing the integer.
        otherBaseWidget.heightOverrideExceptions.put(textAreaF, 0);
        // And we'll add the callback. It's pretty much the same as the numberic
        // callbacks, since in this case we are using a single method for each.
        settingTextF.displayWidget.addCallback(new ModAction(
                mod_GuiApiIntermediateExample.class, "updateTextArea",
                "Callback for TextArea F", TextArea.class, Setting.class)
                .setDefaultArguments(textAreaF, settingTextF));
        ModAction mergedResetOthers = new ModAction(settingMultiC, "reset")
                .mergeAction(new ModAction(settingTextF, "reset"));
        otherBaseWidget.add(GuiApiHelper.makeButton("Reset Other Values",
                mergedResetOthers, true));
        subscreenOthers = new WidgetSimplewindow(otherBaseWidget,
                "Other Settings");
        myModScreen.append(GuiApiHelper.makeButton("Open Other Settings",
                "show", GuiModScreen.class, true, new Class[]
                { Widget.class }, subscreenOthers));
        myModScreen.append(GuiApiHelper.makeButton("Reset ALL settings",
                "resetAll", mySettings, true));
        // And as well, we'll use GuiApiHelper.createChoiceMenu for a reset menu
        // as well.
        Widget choiceMenu = GuiApiHelper
                .createChoiceMenu(
                        "Which settings would you like to reset? You can pick to reset any of the groups from here, or you can also reset all the settings as once.",
                        true, true, "Reset the Numberic Settings.",
                        mergedResetNumberics, "Reset the Boolean Settings.",
                        mergedResetBooleans, "Reset the Other settings.",
                        mergedResetOthers, "Reset everything.", new ModAction(
                                mySettings, "resetAll"));
        // And a button to show the choice menu.
        myModScreen.append(GuiApiHelper.makeButton("Reset Settings with Menu",
                "show", GuiModScreen.class, true, new Class[]
                { Widget.class }, choiceMenu));
        mySettings.load();
        // Finally, make sure all of those TextAreas are updated with the loaded
        // values.
        mod_GuiApiIntermediateExample.updateTextArea(textAreaA, settingIntA);
        mod_GuiApiIntermediateExample.updateTextArea(textAreaB, settingFloatB);
        mod_GuiApiIntermediateExample.updateTextArea(textAreaF, settingTextF);
    }
    
    @Override
    public String Version()
    {
        return "1.0";
    }
}
