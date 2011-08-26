import java.util.AbstractMap;
import java.util.ArrayList;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import de.matthiasmann.twl.textarea.SimpleTextAreaModel;


public class GuiApiHelper
{
    public static GuiApiHelper createChoiceMenu(String displayText)
    {
        return new GuiApiHelper(displayText);
    }
    
    public static Widget createChoiceMenu(String displayText, Object... args)
    {
        if (args.length % 2 == 1)
        {
            throw new IllegalArgumentException(
            "Arguments not in correct format. You need to have an even number of arguments, in the form of String, ModAction for each button.");
        }
        GuiApiHelper helper = new GuiApiHelper(displayText);
        try
        {
            for (int i = 0; i < args.length; i += 2)
            {
                helper.addButton((String) args[i], (ModAction) args[i + 1]);
            }
        }
        catch (Throwable e)
        {
            throw new IllegalArgumentException(
                    "Arguments not in correct format. You need to have an even number of arguments, in the form of String, ModAction for each button.",
                    e);
        }
        return helper.genWidget();
    }
    
    public static Widget createChoiceMenu(String displayText,
            String[] buttonTexts, ModAction[] buttonActions)
    {
        if (buttonTexts.length != buttonActions.length)
        {
            throw new IllegalArgumentException(
            "Arguments not in correct format. buttonTexts needs to be the same size as buttonActions.");
        }
        GuiApiHelper helper = new GuiApiHelper(displayText);
        for (int i = 0; i < buttonTexts.length; i += 2)
        {
            helper.addButton(buttonTexts[i], buttonActions[i]);
        }
        return helper.genWidget();
    }
    
    public static Button makeButton(String displayText, ModAction action)
    {
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        simplebuttonmodel.addActionCallback(action);
        Button button = new Button(simplebuttonmodel);
        button.setText(displayText);
        return button;
    }
    
    public static Button makeButton(String displayText, String methodName,
            Object me)
    {
        return GuiApiHelper.makeButton(displayText, new ModAction(me,
                methodName, new Class[0]));
    }
    
    @SuppressWarnings("rawtypes")
    public static Button makeButton(String displayText, String methodName,
            Object me, Class[] types, Object... arguments)
    {
        return GuiApiHelper.makeButton(displayText, new ModAction(me,
                methodName, types).setDefaultArguments(arguments));
    }
    
    private ArrayList<AbstractMap.SimpleEntry<String, ModAction>> buttonInfo_;
    private String displayText_;
    
    private GuiApiHelper(String displayText)
    {
        displayText_ = displayText;
        buttonInfo_ = new ArrayList<AbstractMap.SimpleEntry<String, ModAction>>();
    }
    
    public void addButton(String text, ModAction action)
    {
        buttonInfo_.add(new AbstractMap.SimpleEntry<String, ModAction>(text,
                action));
    }
    
    public void addButton(String text, String methodName, Object me)
    {
        buttonInfo_.add(new AbstractMap.SimpleEntry<String, ModAction>(text,
                new ModAction(me, methodName, new Class[0])));
    }
    
    @SuppressWarnings("rawtypes")
    public void addButton(String text, String methodName, Object me,
            Class[] types, Object... arguments)
    {
        buttonInfo_.add(new AbstractMap.SimpleEntry<String, ModAction>(text,
                (new ModAction(me, methodName, types)
                .setDefaultArguments(arguments))));
    }
    
    public Widget genWidget()
    {
        WidgetSinglecolumn widget = new WidgetSinglecolumn(new Widget[0]);
        SimpleTextAreaModel model = new SimpleTextAreaModel();
        model.setText(displayText_, false);
        TextArea textarea = new TextArea(model);
        widget.add(textarea);
        for (AbstractMap.SimpleEntry<String, ModAction> entry : buttonInfo_)
        {
            GuiApiHelper.makeButton(entry.getKey(), entry.getValue());
        }
        ScrollPane pane = new ScrollPane(widget);
        pane.setFixed(ScrollPane.Fixed.HORIZONTAL);
        return pane;
    }
}
